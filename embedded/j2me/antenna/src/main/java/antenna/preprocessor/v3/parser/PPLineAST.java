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
package antenna.preprocessor.v3.parser;

import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTreeAdaptor;
import org.antlr.runtime.tree.TreeAdaptor;

/**
 * The abstract syntax tree for the preprocessor line.
 * 
 * @author Omry Yadan
 */
public class PPLineAST extends CommonAST {

    /**
     * Interfaces with the rewrite code that keeps track of the data and its
     * location in the tree
     */
    public static final TreeAdaptor adaptor = new CommonTreeAdaptor() {
        public Object create(Token payload) {
            return new PPLineAST(payload);
        }
    };

    /**
     * The text of the preprocessor directive
     */
    private String text;

    /**
     * Indicates the line number being parsed
     */
    private int line;

    /**
     * Indicates the column number being parsed
     */
    private int column;

    /**
     * Creates a new preprocessor line abstract syntax tree representation
     * 
     * @param tok the line token
     */
    public PPLineAST(final Token tok) {
        super(tok);

        if (tok != null) {
            this.line = tok.getLine();
            this.column = tok.getCharPositionInLine();
        }
        this.text = (tok != null ? tok.getText() : "[]");
    }

    /**
     * Return the column number that is being parsed by preprocessor
     * 
     * @return column number
     */
    public int getColumn() {
        return this.column;
    }

    /**
     * Return the line number that is being parsed by preprocessor
     * 
     * @return line number
     */
    public int getLine() {
        return this.line;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.antlr.runtime.tree.CommonTree#getText()
     */
    public String getText() {
        return text;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.antlr.runtime.tree.CommonTree#toString()
     */
    public String toString() {
        StringBuffer result = new StringBuffer("");

        result.append(super.toString()).append("[").append(getLine()).append(
                ":").append(getColumn()).append(" - ").append(
                this.getClass().getName()).append("(").append(getType())
                .append(")] : ").append(getText());

        return result.toString();
    }
}
