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
 * A small abstraction away from the actual logging method used to log messages
 * for the preprocessor system.
 * 
 * @author Omry Yadan
 */
public interface ILogger {

    /**
     * Log a message.
     * 
     * @param message The string message
     */
    public abstract void log(String message);

}