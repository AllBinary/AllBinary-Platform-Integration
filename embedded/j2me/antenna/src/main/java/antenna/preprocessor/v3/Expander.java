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

import antenna.preprocessor.v3.parser.Define;
import antenna.preprocessor.v3.parser.Defines;

/**
 * @author Omry Yadan
 */
public class Expander {

    /**
     * replace defines wrapped in ${define} with their value.
     */
    public static String expandMacros(String text, Defines defines) {
        int macroStart = -1;
        macroStart = text.indexOf('%', macroStart + 1);
        while (macroStart != -1) {
            int macroEnd = text.indexOf('%', macroStart + 1);
            if (macroEnd == -1) {
                return text;
            } else {
                String macro = text.substring(macroStart + 1, macroEnd);
                Define define = defines.getDefine(macro);
                if (define != null && define.getLiteral() != null) {
                    String value = define.getLiteral().getValue();
                    String macro2 = "%" + macro + "%";
                    int delta = value.length() - macro2.length();
                    text = replaceAll(text, macro2, value);
                    macroEnd += delta;
                    macroStart += delta;
                }
            }

            macroStart = text.indexOf("%", macroEnd + 1); // next macro
        }

        return text;
    }

    /**
     * @param where
     * @param what
     * @param with
     * @return
     */
    private static String replaceAll(String where, String what, String with) {
        return where.replaceAll(what, with);
    }

}
