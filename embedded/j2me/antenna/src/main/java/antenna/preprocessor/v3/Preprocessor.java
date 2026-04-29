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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.Stack;
import java.util.Vector;

import org.antlr.runtime.ANTLRReaderStream;
import org.antlr.runtime.CommonToken;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.TokenRewriteStream;
import org.antlr.runtime.tree.CommonTree;

import antenna.preprocessor.v3.parser.APPLexer;
import antenna.preprocessor.v3.parser.APPParser;
import antenna.preprocessor.v3.parser.CommonAST;
import antenna.preprocessor.v3.parser.Defines;
import antenna.preprocessor.v3.parser.PPLineAST;

/**
 * @author Omry Yadan
 */
public class Preprocessor {

    /**
     * 
     */
    public static final int STATE_NO_CONDITIONAL = 0;

    /**
     * 
     */
    public static final int STATE_CAN_BECOME_TRUE = 1;

    /**
     * 
     */
    public static final int STATE_IS_TRUE = 2;

    /**
     * 
     */
    public static final int STATE_HAS_BEEN_TRUE = 3;

    /**
     * 
     */
    private Stack m_statsStack;

    /**
     * 
     */
    private int m_currentState;

    /**
     * 
     */
    private boolean m_verbose = false;

    /**
     * 
     */
    private Defines m_defines;

    /**
     * 
     */
    private File m_file;

    /**
     * 
     */
    public ILogger m_logger;

    /**
     * 
     */
    public ILineFilter m_lineFilter;

    /**
     * 
     */
    private IPreprocessorListener m_listener;

    /**
     * true if the current file is disabled by the //#condition directive at the
     * first line.
     */
    private boolean m_disabledByCondition = false;

    /**
     * true if the next line should be hidden due to a //#debug directive.
     */
    private boolean m_debugHideNextLine = false;

    /**
     * 
     */
    private int m_currentMdebugBlockStart = -1;

    /**
     * 
     */
    private boolean m_insideHiddenMdebugBlock = false;

    /**
     * <code>true</code> if the lines vector was modified.
     */
    private boolean m_modified;

    /**
     * 
     * @param logger
     * @param lineFilter
     */
    public Preprocessor(ILogger logger, ILineFilter lineFilter) {
        this.m_logger = logger;
        this.m_lineFilter = lineFilter;
        this.m_defines = new Defines(lineFilter);
    }

    /**
     * @param listener
     */
    public void setListener(IPreprocessorListener listener) {
        m_listener = listener;
    }

    /**
     * @param fileName
     */
    public void setFile(File fileName) {
        m_file = fileName;
    }

    /**
     * @param defines
     * @throws PPException
     */
    public void addDefines(String defines) throws PPException {
        try {
            this.m_defines.addDefines(defines);
        } catch (Exception e) {
            throw new PPException("Error evaluating symbols \"" + defines
                    + "\"", e);
        }
    }

    /**
     * @param in
     * @throws IOException
     * @throws PPException
     */
    public void addDefines(InputStream in) throws IOException, PPException {
        try {
            this.m_defines.loadDefines(in);
        } catch (Exception e) {
            throw new PPException("Error evaluating symbols from input stream",
                    e);
        }
    }

    /**
     * @param file
     * @throws IOException
     * @throws PPException
     */
    public void addDefines(File file) throws IOException, PPException {
        try {
            this.m_defines.loadDefines(file);
        } catch (Exception e) {
            throw new PPException("Error evaluating symbols from file " + file,
                    e);
        }
    }

    /**
     * @param msg
     */
    private void log(String msg) {
        if (this.m_verbose) {
            if (this.m_logger != null) {
                this.m_logger.log(msg);
            } else {
                System.err.println(msg);
            }
        }
    }

    /**
     * @param in
     * @param out
     * @param encoding
     * @return
     * @throws IOException
     * @throws PPException
     */
    public boolean preprocess(InputStream in, OutputStream out, String encoding)
            throws IOException, PPException {
        Vector lines = new Vector();
        loadStrings(lines, in, encoding);
        boolean changed = this.preprocess(lines, encoding);
        saveStrings(lines, out, encoding);
        return changed;
    }


    /**
     * @param lines
     * @param out
     * @param encoding
     * @throws IOException
     */
    public static void saveStrings(Vector lines, OutputStream out,
            String encoding) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out,
                encoding));
        try {
            int size = lines.size();
            for (int i = 0; i < size; i++) {
                writer.write((String) lines.get(i));
                writer.newLine();
            }
        } finally {
            writer.close();
        }
    }


    /**
     * @param lines
     * @param in
     * @param encoding
     * @throws IOException
     */
    public static void loadStrings(Vector lines, InputStream in, String encoding)
            throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in,
                encoding));
        String s;
        while ((s = reader.readLine()) != null) {
            lines.add(s);
        }
        reader.close();
    }


    /**
     * @param lines
     * @param encoding
     * @return
     * @throws IOException
     * @throws PPException
     */
    public boolean preprocess(Vector lines, String encoding)
            throws IOException, PPException {
        try {
            return this.preprocessImpl(lines, encoding);
        } catch (IOException e) {
            if (this.m_listener != null) {
                this.m_listener.error(e, -1, -1, -1);
            }
            throw e;
        } catch (PPException e) {
            if (this.m_listener != null) {
                int lineNumber = e.getLineNumber();
                int ln = lineNumber != PPException.UNKNOWN_LINE ? (lineNumber + 1)
                        : lineNumber;
                this.m_listener.error(e, ln, -1, -1);
                return false;
            } else {
                // only throw if we didn't report to a listener.
                throw e;
            }
        }
    }

    /**
     * @param lines
     * @param encoding
     * @return
     * @throws IOException
     * @throws PPException
     */
    private boolean preprocessImpl(Vector lines, String encoding)
            throws IOException, PPException {

        this.m_modified = false;
        this.m_statsStack = new Stack();
        this.m_currentState = STATE_NO_CONDITIONAL;
        this.m_disabledByCondition = false;

        CommandEvaluator eval = new CommandEvaluator(this.m_defines);

        int i = 0;
        while (i < lines.size()) {

            String line = (String) lines.get(i);

            if (this.m_lineFilter != null) {
                line = this.m_lineFilter.filter(line);
            }

            PPLine lp = new PPLine(this.m_file, line, i);

            try {
                if ((lp.getType() == PPLine.TYPE_VISIBLE)
                        || (lp.getType() == PPLine.TYPE_HIDDEN)) {
                    if (isBlind()) {
                        String l = this.commentLine(lp);
                        if (!l.equals(line)) {
                            this.m_modified = true;
                        }

                        lines.set(i, l);
                        if (isVerbose()) {
                            this.log("(+)" + l);
                        }
                    } else {
                        // true to replace the current line, false to insert it.
                        boolean replace = true;
                        String str = this.uncommentLine(lp);

                        if (!str.equals(line)) {
                            this.m_modified = true;
                        }

                        if (replace) {
                            lines.set(i, str);
                        } else {
                            lines.insertElementAt(str, i);
                        }

                        if (isVerbose()) {
                            this.log("(-)" + str);
                        }
                    }
                    // reset debug hide for next line.
                    this.m_debugHideNextLine = false;
                } else {

                    PPLineAST ast = this.getAST(lp);
                    int includeLine = i; // for error handling

                    // if type is include,
                    // look for the endinclude, while removing the lines between
                    // the include and the endinclude.
                    if (ast.getType() == APPLexer.INCLUDE) { // include
                        boolean foundEndInclude = false;
                        i++;
                        int currentLine = i;
                        while (i < lines.size()) {
                            PPLine lp2 = new PPLine(this.m_file, (String) lines
                                    .get(i), currentLine);
                            if (lp2.getType() == PPLine.TYPE_COMMAND) {

                                CommonTree ast2 = this.getAST(lp2);
                                if (ast2.getType() == APPLexer.ENDINCLUDE) { // LITERAL_endinclude
                                    foundEndInclude = true;
                                    break;
                                }
                            }

                            lines.remove(i);
                            currentLine++;
                        }

                        if (!foundEndInclude) {
                            throw new PPException("Missing #endinclude",
                                    this.m_file, includeLine);
                        }

                        String file = this.getIncludeName(lp);
                        Vector includeLines = this.loadIncludedFile(includeLine,
                                file, encoding);
                        if (includeLines != null) {
                            Preprocessor includePreprocessor = new Preprocessor(
                                    this.m_logger, m_lineFilter);
                            includePreprocessor.setFile(this.m_file);
                            includePreprocessor.setListener(this.m_listener);
                            // make a copy to be sure changes (defines, undefine
                            // does not effect including file).
                            includePreprocessor.m_defines = this.m_defines.copy();
                            includePreprocessor.preprocess(includeLines,
                                    encoding);

                            for (int k = 0; k < includeLines.size(); k++) {
                                String s = (String) includeLines.get(k);
                                PPLine lp2 = new PPLine(this.m_file, s, k);
                                if (isBlind()) {
                                    s = this.commentLine(lp2);
                                }
                                lines.insertElementAt(s, i);
                                i++;
                                this.m_modified = true;
                            }
                        }
                    } else {
                        this.handleCommand(lines, lp, ast, eval, encoding,
                                i + 1 == lines.size());
                    }
                }
            } catch (IllegalStateException e) {
                throw new PPException(e.getMessage(), m_file, e, i);
            } catch (Exception e) {
                throw new PPException("Error parsing line : " + line, m_file,
                        e, i);
            }
            i++;

        }

        if (this.m_currentState != STATE_NO_CONDITIONAL) {
            throw new PPException("Missing #endif", m_file, -1);
        }

        if (this.m_insideHiddenMdebugBlock) {
            throw new PPException("Missing #enddebug", m_file,
                    this.m_currentMdebugBlockStart);
        }

        return this.m_modified;
    }

    /**
     * Returns a literal pattern <code>String</code> for the specified
     * <code>String</code>.
     * 
     * <p>
     * This method produces a <code>String</code> that can be used to create a
     * <code>Pattern</code> that would match the string <code>s</code> as if
     * it were a literal pattern.
     * </p>
     * Metacharacters or escape sequences in the input sequence will be given no
     * special meaning.
     * 
     * @param s The string to be literalized
     * @return A literal string replacement taken from jdk 1.5
     */
    public static String regExpQuote(String s) {
        int slashEIndex = s.indexOf("\\E");
        if (slashEIndex == -1) {
            return "\\Q" + s + "\\E";
        }

        StringBuilder sb = new StringBuilder(s.length() * 2);
        sb.append("\\Q");
        slashEIndex = 0;
        int current = 0;
        while ((slashEIndex = s.indexOf("\\E", current)) != -1) {
            sb.append(s.substring(current, slashEIndex));
            current = slashEIndex + 2;
            sb.append("\\E\\\\E\\Q");
        }
        sb.append(s.substring(current, s.length()));
        sb.append("\\E");
        return sb.toString();
    }

    /**
     * @param line
     * @return
     */
    private String toTemplate(String line) {
        // replace %VARIABLE% macros with regexp to match them (.*)
        return "\\s*"
                + regExpQuote(line.trim()).replaceAll("%.*%",
                        "\\\\E\\.\\*\\\\Q") + "\\s*";
    }

    /**
     * an ugly function
     * 
     * @throws TokenStreamException
     * @throws RecognitionException
     * @throws IOException
     */
    private String getIncludeName(PPLine line) throws RecognitionException {
        String text = line.getText();
        text = text.substring("include".length()).trim();
        return text;
    }

    /**
     * @param line
     * @return
     */
    private String getExpandLine(PPLine line) {

        String prevLine = line.getText();
        String tok = "expand"; // LITERAL_expand
        // tok = tok.substring(0, tok.length() - 1); // strip quotes
        prevLine = prevLine.substring(tok.length());
        if ((prevLine.charAt(0) == ' ') || (prevLine.charAt(0) == '\t')) {
            prevLine = prevLine.substring(1);
        }
        return line.getSpace() + prevLine;
    }

    /**
     * @param lp
     * @return
     * @throws PPException
     * @throws RecognitionException
     * @throws RecognitionException
     * @throws IOException
     */
    private PPLineAST getAST(PPLine lp) throws PPException,
            RecognitionException, IOException {
        String s = lp.getText();
        // special hack for expand and for include
        if (s.startsWith("expand")) {

            PPLineAST ast = new PPLineAST(new CommonToken(APPLexer.EXPAND, s)); // LITERAL_expand
            return ast;
        }
        if (s.startsWith("include")) {
            PPLineAST ast = new PPLineAST(new CommonToken(APPLexer.INCLUDE, s)); // LITERAL_include
            return ast;
        } else {

            APPLexer lexer = new APPLexer(new ANTLRReaderStream(
                    new StringReader(lp.getSource())));

            TokenRewriteStream tokens = new TokenRewriteStream(lexer);
            APPParser parser = new APPParser(tokens);

            parser.setTreeAdaptor(PPLineAST.adaptor);

            APPParser.line_return line_return = null;

            try {
                line_return = parser.line();
            } catch (Exception e) {
                throw new PPException("Error parsing " + lp.getSource(), lp
                        .getFileName(), e, lp.getLineNumber());
            }

            PPLineAST ast = (PPLineAST) line_return.getTree();
            CommonAST.fillParentInfo(ast);

            if (ast.isNil()) {
                ast = (PPLineAST) ast.getChild(1);
            }

            return ast;
        }
    }

    /**
     * @param lineNum
     * @param file
     * @param encoding
     * @return
     * @throws PPException
     */
    private Vector loadIncludedFile(int lineNum, String file, String encoding)
            throws PPException {
        File f;
        if (new File(file).isAbsolute()) {
            f = new File(file);
        } else {
            File parent = m_file.getParentFile();
            f = new File(parent, file);
        }
        if (!f.exists()) {
            throw new PPException("File not found : " + f, m_file, lineNum);
        }
        Vector v = new Vector();
        try {
            loadStrings(v, new FileInputStream(f), encoding);
            return v;
        } catch (IOException e) {
            throw new PPException("Error loading include file " + file, m_file,
                    e, lineNum);
        }

    }

    /**
     * Push a State into the stack.
     */
    private void pushState() {
        this.m_statsStack.push(new Integer(m_currentState));
    }

    /**
     * Pop a State from the stack.
     */
    private void popState() {
        m_currentState = ((Integer) m_statsStack.pop()).intValue();
    }

    /**
     * @return
     */
    public boolean isBlind() {
        return (this.m_currentState == STATE_CAN_BECOME_TRUE)
                || (this.m_currentState == STATE_HAS_BEEN_TRUE)
                || this.m_disabledByCondition || this.m_debugHideNextLine
                || this.m_insideHiddenMdebugBlock;
    }

    /**
     * @return
     */
    public boolean isVerbose() {
        return this.m_verbose;
    }

    /**
     * @param verbose
     */
    public void setVerbose(boolean verbose) {
        m_verbose = verbose;
    }

    /**
     * @param lp
     * @return
     */
    String commentLine(PPLine lp) {
        if ((lp.getType() == PPLine.TYPE_VISIBLE)
                || ((lp.prefixChar() != PPLine.HIDDEN_LINE_COMMENT_CHAR) && (lp
                        .getType() != PPLine.TYPE_COMMAND))) {
            return "//" + PPLine.HIDDEN_LINE_COMMENT_CHAR + lp.getSpace()
                    + lp.getText();
        } else {
            return lp.getSource();
        }
    }

    /**
     * @param lp
     * @return
     */
    String uncommentLine(PPLine lp) {
        return lp.getSpace() + lp.getText();
    }

    /**
     * Handles a new "IF"-like command. The old state is pushed on the stack, a
     * new "scope" is entered.
     */
    private void handleIf(boolean condition) {
        this.pushState();
        if (!isBlind()) {
            if (condition) {
                this.m_currentState = STATE_IS_TRUE;
            } else {
                this.m_currentState = STATE_CAN_BECOME_TRUE;
            }
        } else {
            this.m_currentState = STATE_HAS_BEEN_TRUE;
        }
    }

    /**
     * @param condition
     */
    private void handleElseIf(boolean condition) {
        if (this.m_currentState == STATE_NO_CONDITIONAL) {
            throw new IllegalStateException("Unexpected #elif");
        } else if (this.m_currentState == STATE_CAN_BECOME_TRUE) {
            if (condition) {
                this.m_currentState = STATE_IS_TRUE;
            }
        } else if (this.m_currentState == STATE_IS_TRUE) {
            this.m_currentState = STATE_HAS_BEEN_TRUE;
        }
    }

    /**
     * 
     */
    private void handleElse() {
        if (this.m_currentState == STATE_NO_CONDITIONAL) {
            throw new IllegalStateException("Unexpected #else");
        } else if (this.m_currentState == STATE_CAN_BECOME_TRUE) {
            this.m_currentState = STATE_IS_TRUE;
        } else if (this.m_currentState == STATE_IS_TRUE) {
            this.m_currentState = STATE_HAS_BEEN_TRUE;
        }
    }

    /**
     * 
     */
    private void handleEndIf() {
        if (this.m_currentState == STATE_NO_CONDITIONAL) {
            throw new IllegalStateException("Unexpected #endif");
        } else {
            this.popState();
        }
    }

    /**
     * @param lines
     * @param ppl
     * @param ast
     * @param evaluator
     * @param encoding
     * @param lastLine
     * @throws Exception
     * @throws PPException
     * @throws UnsupportedEncodingException
     */
    private void handleCommand(Vector lines, PPLine ppl, PPLineAST ast,
            CommandEvaluator evaluator, String encoding, boolean lastLine)
            throws Exception, PPException, UnsupportedEncodingException {

        if (isVerbose()) {
            this.log("(?)" + ppl.getSource());
        }

        int type = ast.getType();

        switch (type) {

        case APPLexer.DEFINE:
        case APPLexer.UNDEFINE: {
            if (!isBlind()) {
                evaluator.evaluate(ppl, ast, m_listener);
            }
            break;
        }

        case APPLexer.IF:
        case APPLexer.IFDEF:
        case APPLexer.IFNDEF: {
            boolean r = evaluator.evaluate(ppl, ast, m_listener);
            this.handleIf(r);
            break;
        }

        case APPLexer.CONDITION: {
            if (ppl.getLineNumber() != 0) {
                throw new PPException(
                        "//#condition is only allowed in the first line of the file",
                        this.m_file, ppl.getLineNumber());
            }
            boolean r = evaluator.evaluate(ppl, ast, m_listener);
            this.handleCondition(r);

            break;
        }

        case APPLexer.ELIF:
        case APPLexer.ELIFDEF:
        case APPLexer.ELIFNDEF: {
            boolean r = evaluator.evaluate(ppl, ast, m_listener);
            this.handleElseIf(r);
            break;
        }

        case APPLexer.ELSE: {
            this.handleElse();
            break;
        }

        case APPLexer.ENDIF: {
            this.handleEndIf();
            break;
        }

        case APPLexer.DEBUG: {
            boolean show = evaluator.evaluate(ppl, ast, m_listener);
            this.m_debugHideNextLine = !show;
            break;
        }

        case APPLexer.MDEBUG: {
            boolean show = evaluator.evaluate(ppl, ast, m_listener);
            this.handleMdebug(show, ppl.getLineNumber());
            break;
        }

        case APPLexer.ENDDEBUG: {
            this.handleEnddebug();
            break;
        }

        case APPLexer.EXPAND: {
            this.handleExpand(ppl, lines);
            break;
        }

        default:
            throw new PPException("Unexpected token "
                    + APPParser.tokenNames[type] + " at \"" + ppl.getSource()
                    + "\"", this.m_file, ppl.getLineNumber());
        }

        if (type != APPLexer.DEBUG) {
            // reset the debug hide
            this.m_debugHideNextLine = false;
        }
    }

    /**
     * @param ppl
     * @param lines
     */
    private void handleExpand(PPLine ppl, Vector lines) {
        String expLine = this.getExpandLine(ppl);
        String template = this.toTemplate(expLine);
        int nextIndex = ppl.getLineNumber() + 1;
        String str = lines.size() > nextIndex ? (String) lines.get(nextIndex)
                : "";
        PPLine nextPPline = new PPLine(this.m_file, str, nextIndex);

        String nextLine = this.uncommentLine(nextPPline);
        boolean replace = nextLine.matches(template);

        String expanded = Expander.expandMacros(expLine, m_defines);
        if (!nextLine.equals(expanded)) {
            this.m_modified = true;
        }

        if (replace) {
            lines.set(nextIndex, expanded);
        } else {
            lines.insertElementAt(expanded, nextIndex);
        }
    }

    /**
     * 
     */
    private void handleEnddebug() {
        this.m_currentMdebugBlockStart = -1;
        this.m_insideHiddenMdebugBlock = false;
    }

    /**
     * @param show
     * @param lineNumber
     */
    private void handleMdebug(boolean show, int lineNumber) {
        this.m_currentMdebugBlockStart = lineNumber;
        this.m_insideHiddenMdebugBlock = !show;
    }

    /**
     * @param conditionTrue
     */
    private void handleCondition(boolean conditionTrue) {
        this.m_disabledByCondition = !conditionTrue;
    }

    /**
     * Get the Defines list
     * 
     * @return the Defines list
     */
    public Defines getDefines() {
        return this.m_defines;
    }

    /**
     * Clear the Defines list
     */
    public void clearDefines() {
        this.m_defines.clear();
    }

}
