/**
 * Copyright (c) 2007,2008 Omry Yadan and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Omry Yadan (Individual)  - Initial implementation
 *     Diego Sandin (Motorola)  - Updates after adopting ANTLR library 
 *                                version 3.0.1
 */
package antenna.preprocessor.v3;

import java.util.StringTokenizer;
import java.util.Vector;

import org.antlr.runtime.RecognitionException;

import antenna.preprocessor.v3.parser.APPLexer;
import antenna.preprocessor.v3.parser.Define;
import antenna.preprocessor.v3.parser.Defines;
import antenna.preprocessor.v3.parser.Literal;
import antenna.preprocessor.v3.parser.PPLineAST;

/**
 * @author Omry Yadan
 */
public class CommandEvaluator {

    /**
     * 
     */
    private static final String DEBUG_KEY = "DEBUG";

    /**
     * 
     */
    public static final int UNKNOWN_LINE = -1;

    /**
     * 
     */
    private final Defines m_defines;

    /**
     * @param defines
     */
    public CommandEvaluator(Defines defines) {
        m_defines = defines;
    }

    /**
     * @param ppl
     * @param ast
     * @param listener
     * @return
     * @throws Exception
     * @throws PPException
     */
    public boolean evaluate(PPLine ppl, PPLineAST ast,
            IPreprocessorListener listener) throws Exception, PPException {
        Eval eval = new Eval(ppl, ast, listener);
        return evaluate(eval);
    }

    /**
     * @param ast
     * @return
     * @throws Exception
     * @throws PPException
     */
    private boolean evaluate(Eval ast) throws Exception, PPException {
        int type = ast.getType();

        switch (type) {

        case APPLexer.IFDEF:
        case APPLexer.ELIFDEF:
            return evalDefined(ast.getNextSibling());

        case APPLexer.IF:
        case APPLexer.ELIF:
        case APPLexer.CONDITION:
            return evaluate(ast.getNextSibling());

        case APPLexer.DEBUG:
        case APPLexer.MDEBUG:
            return evaluateDebug(ast);

        case APPLexer.IFNDEF:
        case APPLexer.ELIFNDEF:
            return !evaluate(ast.getNextSibling());

        case APPLexer.SYMBOL: {

            Define define = m_defines.getDefine(ast.getText());

            if (define == null) {
                return false; // if not defined, assume false.
            }
            // if is a boolean, return it's value.
            if (define.getLiteral().isBoolean()) {
                return define.getLiteral().isTrue();
            }
            // else (since it's defined) return true.
            return true;

        }
        case APPLexer.EQ: {
            return EQ(ast);
        }

        case APPLexer.NEQ: {
            return NEQ(ast);
        }

        case APPLexer.GT: {
            return GT(ast);
        }

        case APPLexer.LT: {
            return LT(ast);
        }

        case APPLexer.GTE: {
            return GTE(ast);
        }

        case APPLexer.LTE: {
            return LTE(ast);
        }

        case APPLexer.AT: {
            return AT(ast);
        }

        case APPLexer.AND: {
            Eval left = ast.getFirstChild();
            Eval right = left.getNextSibling();
            return evaluate(left) && evaluate(right);
        }

        case APPLexer.OR: {
            Eval left = ast.getFirstChild();
            Eval right = left.getNextSibling();
            return evaluate(left) || evaluate(right);
        }

        case APPLexer.XOR: {
            Eval left = ast.getFirstChild();
            Eval right = left.getNextSibling();
            return evaluate(left) ^ evaluate(right);
        }

        case APPLexer.NOT: {
            return !evaluate(ast.getFirstChild());
        }

        case APPLexer.DEFINE: {
            m_defines.define(ast.getNextSibling().ast);
            return true;
        }

        case APPLexer.UNDEFINE: {
            String def = ast.getNextSibling().getText();
            boolean removed = m_defines.undefine(def);

            if (!removed) {
                System.err.println("Warning: attempting to undefine \"" + def
                        + "\" which is not defined");
            }
            return true;
        }

        default:
            break;

        }

        throw new UnsupportedOperationException("Error evaluating expression "
                + ((ast != null) ? ast.ppl.getSource() : ""));
    }

    /**
     * @param eval
     * @return
     * @throws PPException
     */
    private boolean evalDefined(Eval eval) {
        return m_defines.isDefined(eval.getText());
    }

    /**
     * @param eval
     * @return
     * @throws PPException
     */
    private boolean evaluateDebug(Eval eval) throws PPException {
        boolean debugDefined = m_defines.isDefined(DEBUG_KEY);
        if (!debugDefined) {
            return false;
        }

        PPLineAST nextSibling = (PPLineAST) eval.ast.getParent().getChild(
                eval.ast.getIndex() + 1);
        // looks like //#debug
        if (nextSibling == null) {
            return debugDefined; // always true here
        } else {
            Define define = m_defines.getDefine(DEBUG_KEY);
            String currentValue = define.getLiteral().getValue();
            int currentLevel = getDebugLevelNumber(currentValue);
            if (currentLevel == -1) {
                throw new PPException("Unknown debug value " + currentValue);
            }
            // line level:
            String level = nextSibling.getText();
            int lineLevel = getDebugLevelNumber(level);
            if (lineLevel == -1) {
                throw new PPException("Unknown debug value " + level);
            }
            return lineLevel >= currentLevel;
        }
    }

    /**
     * @param value
     * @return
     */
    private int getDebugLevelNumber(String value) {
        int level = -1;
        if (value.equalsIgnoreCase("debug") || value.equalsIgnoreCase("true")) {
            level = 0;
        }
        if (value.equalsIgnoreCase("info")) {
            level = 1;
        }
        if (value.equalsIgnoreCase("warn")) {
            level = 2;
        }
        if (value.equalsIgnoreCase("error")) {
            level = 3;
        }
        if (value.equalsIgnoreCase("fatal")) {
            level = 4;
        }
        return level;
    }

    /**
     * @param ast
     * @return
     * @throws Exception
     */
    private boolean AT(Eval ast) throws Exception {
        Eval left = ast.getFirstChild();
        Eval right = left.getNextSibling();
        Literal llist[] = values(left);

        Literal rlist[] = values(right);
        for (int i = 0; i < llist.length; i++) {
            Literal literal = llist[i];
            for (int j = 0; j < rlist.length; j++) {
                if (literal.getValue().equals(rlist[j].getValue())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param ast
     * @return
     * @throws Exception
     */
    private boolean GTE(Eval ast) throws Exception {
        Eval left = ast.getFirstChild();
        Eval right = left.getNextSibling();
        Literal lval = singleValue(left);
        Literal rval = singleValue(right);
        return !ltImpl(lval, rval);
    }

    /**
     * @param ast
     * @return
     * @throws Exception
     */
    private boolean LTE(Eval ast) throws Exception {
        Eval left = ast.getFirstChild();
        Eval right = left.getNextSibling();
        Literal lval = singleValue(left);
        Literal rval = singleValue(right);
        return !gtImpl(lval, rval);
    }

    /**
     * @param ast
     * @return
     * @throws RecognitionException
     */
    private boolean LT(Eval ast) throws RecognitionException {
        Eval left = ast.getFirstChild();
        Eval right = left.getNextSibling();
        Literal lval = singleValue(left);
        Literal rval = singleValue(right);
        return ltImpl(lval, rval);
    }

    /**
     * @param ast
     * @return
     * @throws RecognitionException
     */
    private boolean GT(Eval ast) throws RecognitionException {
        Eval left = ast.getFirstChild();
        Eval right = left.getNextSibling();
        Literal lval = singleValue(left);
        Literal rval = singleValue(right);
        return gtImpl(lval, rval);
    }

    /**
     * @param ast
     * @return
     * @throws RecognitionException
     */
    private boolean EQ(Eval ast) throws RecognitionException {
        return eqImpl(ast);
    }

    /**
     * @param ast
     * @return
     * @throws RecognitionException
     */
    private boolean NEQ(Eval ast) throws RecognitionException {
        return !eqImpl(ast);
    }

    /**
     * @param ast
     * @return
     * @throws Exception
     */
    private Literal[] values(Eval ast) throws Exception {
        return values(ast, true);
    }

    /**
     * @param ast
     * @param warnIfNotDefined
     * @return
     * @throws Exception
     */
    private Literal[] values(Eval ast, boolean warnIfNotDefined)
            throws Exception {
        
        int type = ast.getType();
        String text = ast.getText();
        
        switch (type) {

        case APPLexer.SYMBOL: {
            Define v = m_defines.getDefine(text);
            
            if (v != null) {
                Literal lit = v.getLiteral();
                return getValues(lit.getValue());
            } else {
                ast.warning(emptySymbolWarning(text));
                return literals(new Literal(Literal.STRING, ""));
            }
        }
        
        case APPLexer.STRING: {
            String str = text;
            return getValues(str);
        }

        case APPLexer.NUMBER:
            return literals(new Literal(type, text));
        }

        throw new RecognitionException();
    }

    /**
     * @param str
     * @return
     * @throws RecognitionException
     */
    private Literal[] getValues(String str) throws RecognitionException {

        StringTokenizer tok = new StringTokenizer(str, ", ");
        Vector vec = new Vector();

        while (tok.hasMoreElements()) {

            String t = tok.nextToken();
            Literal literal = new Literal(Literal.STRING, t);
            vec.addElement(literal);

        }

        Literal ls[] = new Literal[vec.size()];
        vec.copyInto(ls);
        return ls;
    }

    /**
     * @param ast
     * @return
     * @throws RecognitionException
     */
    private Literal singleValue(Eval ast) throws RecognitionException {

        int type = ast.getType();
        String text = ast.getText();

        switch (type) {

        case APPLexer.SYMBOL: {

            Define v = m_defines.getDefine(text);

            if (v != null) {
                return v.getLiteral();
            } else {
                ast.warning(emptySymbolWarning(text));
                return new Literal(Literal.STRING, "");
            }
        }

        case APPLexer.STRING:
        case APPLexer.NUMBER:
            return new Literal(type, text);

        }

        throw new RecognitionException();
    }

    private String emptySymbolWarning(String text) {
        return "Symbol " + text + " is not defined, using empty string";
    }

    /**
     * @param value
     * @return
     */
    private Literal[] literals(Literal value) {
        return new Literal[] { value };
    }

    /**
     * @param ast
     * @return
     * @throws RecognitionException
     */
    private boolean eqImpl(Eval ast) throws RecognitionException {
        Eval left = ast.getFirstChild();
        Eval right = left.getNextSibling();

        Literal lval = singleValue(left);
        Literal rval = singleValue(right);

        if ((lval.isNumber() ^ rval.isNumber())) {
            String number = lval.isNumber() ? left.getText() : right.getText();
            ast.warning("Number " + number + " is compared lexicographically");
        }

        if (lval.isNumber() && rval.isNumber()) {
            double d1 = Double.parseDouble(rval.getValue());
            double d2 = Double.parseDouble(lval.getValue());
            return d1 == d2;
        } else {
            return lval.getValue().equals(rval.getValue());
        }
    }

    /**
     * @param lval
     * @param rval
     * @return
     */
    private boolean gtImpl(Literal lval, Literal rval) {
        if (lval.isNumber() && rval.isNumber()) {
            double d1 = Double.parseDouble(rval.getValue());
            double d2 = Double.parseDouble(lval.getValue());
            return d1 < d2;
        } else {
            return lval.getValue().compareTo(rval.getValue()) > 0;
        }
    }

    /**
     * @param lval
     * @param rval
     * @return
     */
    private boolean ltImpl(Literal lval, Literal rval) {
        if (lval.isNumber() && rval.isNumber()) {
            double d1 = Double.parseDouble(rval.getValue());
            double d2 = Double.parseDouble(lval.getValue());
            return d1 > d2;
        } else {
            return lval.getValue().compareTo(rval.getValue()) < 0;
        }
    }

    /**
     * 
     */
    private static class Eval {
        public PPLine ppl;
        public PPLineAST ast;
        public IPreprocessorListener listener;

        public Eval(PPLine ppl, PPLineAST ast, IPreprocessorListener listener) {
            this.ppl = ppl;
            this.ast = ast;
            this.listener = listener;
        }

        /**
         * @param message
         */
        public void warning(String message) {
            if (listener != null) {
                int ln = ppl.getLineNumber() + 1; // use 1 based line number
                // system for the external world
                listener.warning(message, ln, ast.getCharPositionInLine(),
                        getText().length());
            }
        }

        /**
         * @return
         */
        public String getText() {
            return ast.getText();
        }

        /**
         * @return
         */
        public int getType() {
            return ast.getType();
        }

        /**
         * @return
         */
        public Eval getFirstChild() {
            return new Eval(ppl, (PPLineAST) ast.getChild(0), listener);
        }

        /**
         * @return
         */
        public Eval getNextSibling() {
            return new Eval(ppl, (PPLineAST) ast.getParent().getChild(
                    ast.getIndex() + 1), listener);
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Object#toString()
         */
        public String toString() {
            return ast.toString();
        }

        /**
         * @return
         */
        public int getColumn() {
            return ast.getCharPositionInLine();
        }
    }
}
