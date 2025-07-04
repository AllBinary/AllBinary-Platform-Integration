/**
 * Copyright (c) 2008 Motorola.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Diego Sandin (Motorola) - Initial implementation
 */
package antenna.preprocessor.v3.parser;

import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTree;

/**
 * <p>
 * Basic <b>abstract syntax tree</b> (AST) node implementation.
 * </p>
 * <p>
 * The AST differs from a parse tree by omitting nodes and edges for syntax
 * rules that do not affect the semantics of the program.
 * </p>
 * 
 * @author Diego Sandin
 */
public class CommonAST extends CommonTree {

    /**
     * Walks trough a valid AST tree filing each node with information about the
     * the node's {@link #parent} and index.
     * 
     * @param ast the AST tree to fill information.
     */
    public static void fillParentInfo(CommonAST ast) {

        if (ast != null) {
            fillParentInfo(ast, null, 0);
        }

    }

    /**
     * Walks trough a valid AST tree filing each node with information about the
     * the node's {@link #parent} and index.
     * 
     * @param ast the AST tree to fill information
     * @param astParent the current node parent. The parent can be
     *                <code>null</code>.
     * @param index the current node index. Index must be 0 or higher.
     */
    private static void fillParentInfo(CommonAST ast, CommonAST astParent,
            int index) {

        if ((ast != null) && (index >= 0)) {
            ast.setParent(astParent);

            ast.setIndex(index);

            int childCount = ast.getChildCount();
            if (childCount > 0) {
                for (int i = 0; i < childCount; i++) {
                    fillParentInfo((CommonAST) ast.getChild(i), ast, i);
                }
            }
        }
    }

    /**
     * The node index in the AST tree
     */
    private int index = 0;

    /**
     * The node's parent
     */
    private CommonAST parent = null;

    /**
     * Creates a new abstract syntax tree (AST) node.
     */
    public CommonAST() {
        super();
    }

    /**
     * Creates a new abstract syntax tree (AST) node
     * 
     * @param node tree node that wrappers a Token object.
     */
    public CommonAST(CommonTree node) {
        super(node);
    }

    /**
     * Creates a new abstract syntax tree (AST) node.
     * 
     * @param t atomic parse element
     */
    public CommonAST(Token t) {
        super(t);
    }

    /**
     * The node index in the tree.
     * 
     * @return the index
     */
    public int getIndex() {
        return index;
    }

    /**
     * Return the node's parent.
     * 
     * @return the parent
     */
    public CommonAST getParent() {
        return parent;
    }

    /**
     * Specifies the node's index in the tree.
     * 
     * @param index the index to set
     */
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * Specifies the node's parent.
     * 
     * @param parent the parent to set
     */
    public void setParent(CommonAST parent) {
        this.parent = parent;
    }

}