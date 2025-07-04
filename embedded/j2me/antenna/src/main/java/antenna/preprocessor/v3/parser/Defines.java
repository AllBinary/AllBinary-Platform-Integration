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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import org.antlr.runtime.ANTLRReaderStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.TokenRewriteStream;

import antenna.preprocessor.v3.ILineFilter;

/**
 * @author Omry Yadan
 */
public class Defines {

    /**
     * Hash table with the list of <code>#Defines</code>
     */
    private Hashtable m_defines;

    /**
     * A line filter used to modify a line before its being preprocessed.
     */
    private final ILineFilter m_lineFilter;

    /**
     * 
     */
    public Defines() {
        m_lineFilter = null;
        clear();
    }

    /**
     * @param lineFilter
     */
    public Defines(ILineFilter lineFilter) {
        m_lineFilter = lineFilter;
        clear();
    }

    /**
     * @param defines
     * @throws IOException 
     * @throws RecognitionException 
     */
    public Defines(String defines) throws RecognitionException, IOException  {
        m_lineFilter = null;
        clear();
        addDefines(defines);
    }

    /**
     * @param defines
     * @param lineFilter
     * @throws IOException 
     * @throws RecognitionException 
     */
    public Defines(String defines, ILineFilter lineFilter) throws RecognitionException, IOException  {
        m_lineFilter = lineFilter;
        clear();
        addDefines(defines);
    }

    /**
     * @param defines
     * @throws RecognitionException
     * @throws IOException
     */
    public void addDefines(String defines) throws RecognitionException,
            IOException {

        APPLexer lexer = new APPLexer(new ANTLRReaderStream(new StringReader(
                defines)));
        TokenRewriteStream tokens = new TokenRewriteStream(lexer);
        APPParser parser = new APPParser(tokens);

        parser.setTreeAdaptor(PPLineAST.adaptor);

        APPParser.defines_return defines_return = parser.defines();

        PPLineAST returnAST = (PPLineAST) defines_return.getTree();
        PPLineAST.fillParentInfo(returnAST);

        try {
            if (returnAST.isNil()) {
                for (int i = 0; i < returnAST.getChildCount(); i++) {
                    // (PPLineAST) t.getChild(0)
                    for (PPLineAST t = (PPLineAST) returnAST.getChild(i); t != null
                            && t.getType() != APPLexer.EOF; t = (PPLineAST) t
                            .getParent().getChild(t.getIndex() + 1)) {

                        define(t);

                    }
                }
            } else {
                for (PPLineAST node = returnAST; node != null
                        && node.getType() != APPLexer.EOF; node = (PPLineAST) node
                        .getParent().getChild(node.getIndex() + 1)) {
                    define(node);
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    /**
     * @param file
     * @throws IOException
     * @throws RecognitionException
     */
    public void loadDefines(File file) throws IOException, RecognitionException {
        FileInputStream in = new FileInputStream(file);
        try {
            loadDefines(in);
        } finally {
            in.close();
        }
    }

    /**
     * @param in
     * @throws RecognitionException
     * @throws IOException
     */
    public void loadDefines(InputStream in) throws RecognitionException,
            IOException {
        String line;
        Hashtable backup = (Hashtable) m_defines.clone();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        try {
            while ((line = reader.readLine()) != null) {
                if (m_lineFilter != null) {
                    line = m_lineFilter.filter(line);
                }

                APPLexer lexer = new APPLexer(new ANTLRReaderStream(
                        new StringReader(line)));
                TokenRewriteStream tokens = new TokenRewriteStream(lexer);
                APPParser parser = new APPParser(tokens);

                parser.setTreeAdaptor(PPLineAST.adaptor);

                APPParser.define_return define_return = parser.define();

                PPLineAST returnAST = (PPLineAST) define_return.getTree();
                PPLineAST.fillParentInfo(returnAST);

                define(returnAST);
            }
        } catch (RecognitionException e) {
            m_defines = backup;
            throw e;
        } catch (IOException e) {
            m_defines = backup;
            throw e;
        }
    }

    /**
     * @param key
     * @throws RecognitionException
     * @throws IOException
     */
    public void define(String key) throws RecognitionException, IOException {
        define(key, null);
    }

    /**
     * @param key
     * @param value
     * @throws RecognitionException
     * @throws IOException
     */
    public void define(String key, String value) throws RecognitionException,
            IOException {
        String def;
        if (value == null) {
            def = key;
        } else {
            def = key + "=" + value;
        }

        APPLexer lexer = new APPLexer(new ANTLRReaderStream(new StringReader(
                def)));

        TokenRewriteStream tokens = new TokenRewriteStream(lexer);

        APPParser parser = new APPParser(tokens);

        parser.setTreeAdaptor(PPLineAST.adaptor);

        APPParser.define_return define_return = parser.define();

        PPLineAST returnAST = (PPLineAST) define_return.getTree();
        PPLineAST.fillParentInfo(returnAST);

        define(returnAST);

    }

    /**
     * @param node
     */
    public void define(PPLineAST node) throws RecognitionException {
        // FIXME
        String key = node.getText();

        int action = -1;

        // FIXME
        if (node.getChild(0) != null
                && (node.getChild(0).getType() == APPLexer.ADD_IF_NEW /* add_if_new */
                || node.getChild(0).getType() == APPLexer.UNSET /* unset */)) {
            action = node.getChild(0).getType();

            node = (PPLineAST) node.getChild(0);
            node = (PPLineAST) node.getParent().getChild(node.getIndex() + 1);
            node = (PPLineAST) node.getParent().getChild(node.getIndex() + 1);

        } else {
            node = (PPLineAST) node.getChild(0);
        }

        Literal literal;
        if (node != null) {
            String st = node.getText();
            if (node.getType() == APPLexer.STRING) {
                st = st.substring(1, st.length() - 1);
            }
            literal = new Literal(node.getType(), st);
        } else {
            literal = new Literal(Literal.BOOLEAN, "true");
        }
        Define define = new Define(key, literal);

        switch (action) {
        case -1:
            m_defines.put(key, define);
            break;
        case APPLexer.ADD_IF_NEW: // LITERAL add_if_new:
            if (!m_defines.containsKey(key))
                m_defines.put(key, define);
            break;
        case APPLexer.UNSET: // LITERAL unset
            m_defines.remove(key);
            break;
        default:
            /* "Unsupported action " + action */
            throw new RecognitionException();
        }
    }

    /**
     * @param text
     * @return
     */
    public boolean isDefined(String text) {
        return m_defines.containsKey(text);
    }

    /**
     * @param text
     * @return
     */
    public Define getDefine(String text) {
        return (Define) m_defines.get(text);
    }

    /**
     * @param key
     * @return
     */
    public boolean undefine(String key) {
        return m_defines.remove(key) != null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    public String toString() {
        Enumeration keys = m_defines.keys();
        Vector v = new Vector(m_defines.size());
        while (keys.hasMoreElements()) {
            String key = (String) keys.nextElement();
            v.add(key);
        }

        Collections.sort(v, new Comparator() {

            /**
             * @param arg0
             * @param arg1
             * @return
             */
            public int compare(Object arg0, Object arg1) {
                String s1 = (String) arg0;
                String s2 = (String) arg1;
                if (s1 != null)
                    s1 = s1.toLowerCase();
                if (s2 != null)
                    s2 = s2.toLowerCase();
                if (s1 == null && s2 == null)
                    return 0;
                if (s1 == null)
                    return -1;
                if (s2 == null)
                    return +1;
                return s1.compareTo(s2);
            }
        });

        StringBuffer b = new StringBuffer();
        for (int i = 0; i < v.size(); i++) {
            String key = (String) v.get(i);
            Define def = (Define) m_defines.get(key);
            b.append(def);
            b.append("\n");
        }
        return b.toString();
    }

    /**
     * @return
     */
    public String[] keys() {
        String keys[] = new String[m_defines.size()];
        m_defines.keySet().toArray(keys);
        return keys;
    }

    /**
     * @return
     */
    public Define[] values() {
        Define defines[] = new Define[m_defines.size()];
        m_defines.values().toArray(defines);
        return defines;
    }

    /**
     * Creates a shallow copy of the Defines list
     * @return shallow copy of the Defines list
     */
    public Defines copy() {

        Defines copy = new Defines();
        copy.m_defines = (Hashtable) m_defines.clone();
        return copy;
    }

    /**
     * Clears the list of <code>#Defines</code> so that it contains no
     * definitions.
     */
    public void clear() {
        m_defines = new Hashtable();
    }
}
