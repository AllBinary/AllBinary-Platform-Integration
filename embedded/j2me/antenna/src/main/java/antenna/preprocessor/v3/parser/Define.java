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
 * <p>
 * This class represents the <code>#Define</code> directive.
 * </p>
 * <code>#define [identifier=value]</code> and
 * <code>#define [identifier value]</code>
 * <p>
 * Adds temporary abilities or variables to the preprocessor memory. Can not be
 * used in nested blocks. Global variables defined in the project configuration
 * properties override these temporary variables.
 * </p>
 * 
 * @author Omry Yadan
 */
public class Define {

    /**
     * The Key defined by the <code>#Define</code> directive
     */
    private final String m_key;

    /**
     * The Literal representation to the <code>#Define</code> directive
     */
    private final Literal m_value;

    /**
     * Creates a new representation for the <code>#Define</code> directive.
     * 
     * @param key the defined key
     */
    public Define(String key) {
        this.m_key = key;
        this.m_value = null;
    }

    /**
     * Creates a new representation for the <code>#Define</code> directive.
     * 
     * @param key the defined key
     * @param value the Literal representation for the <code>#Define</code>
     *                directive.
     */
    public Define(String key, Literal value) {
        this.m_key = key;
        this.m_value = value;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object obj) {
        if (obj != null) {
            if (obj instanceof Define) {
                Define d = (Define) obj;
                if (!m_key.equals(d.m_key))
                    return false;
                Literal l1 = m_value;
                Literal l2 = d.m_value;
                return (l1.equals(l2));
            }
        }
        return false;
    }

    /**
     * Get the key from the <code>#Define</code> directive.
     * 
     * @return the key value
     */
    public String getKey() {
        return this.m_key;
    }

    /**
     * Get the Literal representation for the <code>#Define</code> directive.
     * 
     * @return the Literal representation
     */
    public Literal getLiteral() {
        return this.m_value;

    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    public String toString() {
        if (m_value != null) {
            return m_key + "=" + m_value;
        } else {
            return m_key;
        }
    }

}
