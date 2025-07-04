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

/**
 * @author Omry Yadan
 */
public interface IPreprocessorListener {

    /**
     * Used to indicate unknown int value (line line, offset etc).
     */
    public static final int UNKNOWN = -1;

    /**
     * @param message
     * @param lineNumber
     * @param offset
     * @param length
     */
    public void warning(String message, int lineNumber, int offset, int length);

    /**
     * @param e
     * @param lineNumber
     * @param offset
     * @param length
     */
    public void error(Exception e, int lineNumber, int offset, int length);
}
