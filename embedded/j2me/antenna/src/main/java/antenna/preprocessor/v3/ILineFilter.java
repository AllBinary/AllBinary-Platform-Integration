/**
 * Copyright (c) 2007,2008 Omry Yadan and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Omry Yadan (Individual)  - Initial implementation
 *     Diego Sandin (Motorola)  - Extracted the interface from the Preprocessor 
 *                                Class
 */
package antenna.preprocessor.v3;

/**
 * <p>
 * A small abstraction away from the line filter used to provide fine grain
 * control over what is being preprocessed.
 * </p>
 * <p>
 * Line filters may modify a line before it's preprocessed. This can be useful
 * to expand some macros like <b>${VERSION}</b> for example.
 * </p>
 * 
 * @author Omry Yadan
 */
public interface ILineFilter {

    /**
     * Filter the line before the preprocessor process.
     * 
     * @param line the line to be modified
     * @return the result line
     */
    public abstract String filter(String line);

}