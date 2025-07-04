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

/**
 * This class represents any notation for representing a value within the source
 * code.
 * 
 * @author Omry Yadan
 */
public class Literal {

    /**
     * True string used for comparisons
     */
    private static final String BOOLEAN_TRUE = "true";

    /**
     * False string used for comparisons
     */
    private static final String BOOLEAN_FALSE = "false";

    /**
     * Representation of a numerical value within the source code
     */
    public static final int NUMBER = APPLexer.NUMBER;

    /**
     * Representation of a string value within the source code
     */
    public static final int STRING = APPLexer.STRING;

    /**
     * Representation of a symbol value within the source code
     */
    public static final int SYMBOL = APPLexer.SYMBOL;

    /**
     * Representation of a boolean value within the source code
     */
    public static final int BOOLEAN = -1;

    /**
     * Representation of a debug level value within the source code
     */
    public static final int DEBUG_LEVEL = -2;

    /**
     * The literal type
     */
    private int m_type;

    /**
     * The literal value
     */
    private String m_value;

    /**
     * Creates a new literal representation.
     * 
     * @param type the literal value
     * @param value the literal type
     */
    public Literal(int type, String value) {

        m_type = type;

        /* check if value is not invalid */
        if (value == null) {
            throw new IllegalArgumentException("Unsupported : empty value");
        }

        switch (type) {

        case SYMBOL:
            break;

        case NUMBER: {
            try {
                Double.parseDouble(value);
            } catch (NumberFormatException e1) {
                throw new IllegalArgumentException(
                        "Unsupported numerical value : " + value);
            }
            break;
        }

        case STRING: {

            /* Remove " and ' from the string */
            if (value.startsWith("\"") || value.startsWith("\'")) {
                value = value.substring(1);
            }
            if (value.endsWith("\"") || value.endsWith("\'")) {
                value = value.substring(0, value.length() - 1);
            }

            if (value.toLowerCase().equals(BOOLEAN_FALSE)
                    || value.toLowerCase().equals(BOOLEAN_TRUE)) {
                m_type = BOOLEAN;
            } else {
                try {
                    Double.parseDouble(value);
                    m_type = NUMBER;
                } catch (NumberFormatException e) {
                }
            }
            break;
        }

        case APPLexer.FALSE:
        case APPLexer.TRUE:
        case BOOLEAN: {

            m_type = BOOLEAN;
            if (!value.toLowerCase().equals(BOOLEAN_FALSE)
                    && !value.toLowerCase().equals(BOOLEAN_TRUE))
                throw new IllegalArgumentException("Invalid boolean value : "
                        + value);
            value = value.toLowerCase();
            break;
        }

        case APPLexer.DEBUG:
        case APPLexer.INFO:
        case APPLexer.WARN:
        case APPLexer.ERROR:
        case APPLexer.FATAL:
        case DEBUG_LEVEL: {

            m_type = DEBUG_LEVEL;
            break;
        }

        default:
            throw new IllegalArgumentException("Unsupported type : " + type);
        }

        m_value = value;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    public String toString() {
        switch (m_type) {
        case STRING:
            return "\"" + m_value + "\"";
        case NUMBER:
        case BOOLEAN:
        case SYMBOL:
        case DEBUG_LEVEL:
        default:
            return m_value;

        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object obj) {
        if (obj instanceof Literal) {
            Literal other = (Literal) obj;
            return other.m_type == m_type && other.m_value.equals(m_value);
        }
        return false;
    }

    /**
     * Check if the literal value is <code>false</code>.
     * 
     * @return if the literal value is <code>false</code>
     */
    public boolean isFalse() {
        return m_type == BOOLEAN && m_value.equals(BOOLEAN_FALSE);
    }

    /**
     * Check if the literal value is <code>true</code>.
     * 
     * @return if the literal value is <code>true</code>
     */
    public boolean isTrue() {
        return m_type == BOOLEAN && m_value.equals(BOOLEAN_TRUE);
    }

    /**
     * Gets the value of the literal.
     * 
     * @return the Literal value
     */
    public String getValue() {
        return m_value;
    }

    /**
     * Check if the literal is a debug level directive.
     * 
     * @return if the literal is a debug level
     */
    public boolean isDebugLevel() {
        return m_type == DEBUG_LEVEL;
    }

    /**
     * Check if the literal is a number.
     * 
     * @return if the literal is a number
     */
    public boolean isNumber() {
        return m_type == NUMBER;
    }

    /**
     * Check if the literal is a String.
     * 
     * @return if the literal is a String
     */
    public boolean isString() {
        return m_type == STRING;
    }

    /**
     * Check if the literal is a Symbol.
     * 
     * @return if the literal is a Symbol
     */
    public boolean isSymbol() {
        return m_type == SYMBOL;
    }

    /**
     * Check if the literal is a Boolean.
     * 
     * @return if the literal is a Boolean
     */
    public boolean isBoolean() {
        return m_type == BOOLEAN;
    }

}
