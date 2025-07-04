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

import java.io.File;

/**
 * Provides a specialized exception type for use inside the preprocessor.
 */
public class PPException extends Exception {

    /**
     * Default serial version UID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constant to indicate that the line where the error occurred is unknown.
     */
    public static final int UNKNOWN_LINE = -1;

    /**
     * The line where the error occurred
     */
    private final int m_lineNumber;

    /**
     * The file being preprocessed.
     */
    private File m_file;

    /**
     * Constructs a new exception with the specified detail message.
     * 
     * @param message the detail message
     */
    public PPException(String message) {
        this(message, null, null);
    }

    /**
     * Constructs a new exception with the specified detail message and cause.
     * 
     * @param message the detail message
     * @param cause the cause. (A null value is permitted, and indicates that
     *                the cause is nonexistent or unknown.)
     */
    public PPException(String message, Throwable cause) {
        this(message, null, cause);
    }

    /**
     * Constructs a new exception with the specified detail message and file
     * where it occurred.
     * 
     * @param message the detail message
     * @param file the file being preprocessed when the exception was thrown
     */
    public PPException(String message, File file) {
        this(message, file, null, UNKNOWN_LINE);
    }

    /**
     * Constructs a new exception with the specified detail message, file and
     * line where it occurred.
     * 
     * @param message the detail message
     * @param file the file being preprocessed when the exception was thrown
     * @param lineNumber the line where the error occurred
     */
    public PPException(String message, File file, int lineNumber) {
        this(message, file, null, lineNumber);
    }

    /**
     * Constructs a new exception with the specified detail message, cause and
     * file where it occurred.
     * 
     * @param message the detail message
     * @param file the file being preprocessed when the exception was thrown
     * @param cause the cause. (A null value is permitted, and indicates that
     *                the cause is nonexistent or unknown.)
     */
    public PPException(String message, File file, Throwable cause) {
        this(message, file, cause, UNKNOWN_LINE);
    }

    /**
     * Constructs a new exception with the specified detail message, cause, file
     * and line where it occurred.
     * 
     * @param message the detail message
     * @param file the file being preprocessed when the exception was thrown
     * @param cause the cause. (A null value is permitted, and indicates that
     *                the cause is nonexistent or unknown.)
     * @param lineNumber the line where the error occurred
     */
    public PPException(String message, File file, Throwable cause,
            int lineNumber) {
        super(message, cause);
        m_lineNumber = lineNumber;
        m_file = file;
    }

    /**
     * Get the line where the error occurred.
     * 
     * @return line number
     */
    public int getLineNumber() {
        return m_lineNumber;
    }

    /**
     * Get the file being preprocessed when the exception was thrown.
     * 
     * @return the file
     */
    public File getFile() {
        return m_file;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Throwable#getMessage()
     */
    public String getMessage() {
        if (m_file != null) {
            String ln = m_lineNumber != UNKNOWN_LINE ? ":" + m_lineNumber : "";
            return m_file + ln + " : " + super.getMessage();
        } else {
            if (m_lineNumber != UNKNOWN_LINE) {
                return "Line #" + m_lineNumber + " : " + super.getMessage();
            } else {
                return super.getMessage();
            }
        }

    }
}
