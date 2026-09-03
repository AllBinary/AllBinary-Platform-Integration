package org.apache.xmlrpc;

/*
 * The Apache Software License, Version 1.1
 *
 *
 * Copyright (c) 2001 The Apache Software Foundation.  All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution,
 *    if any, must include the following acknowledgment:
 *       "This product includes software developed by the
 *        Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowledgment may appear in the software itself,
 *    if and wherever such third-party acknowledgments normally appear.
 *
 * 4. The names "XML-RPC" and "Apache Software Foundation" must
 *    not be used to endorse or promote products derived from this
 *    software without prior written permission. For written
 *    permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache",
 *    nor may "Apache" appear in their name, without prior written
 *    permission of the Apache Software Foundation.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 */

import java.io.InputStream;
import java.util.Hashtable;
import java.util.Stack;
import org.allbinary.util.BasicArrayList;
import org.allbinary.util.BasicArrayListD;

import org.xml.sax.AttributeList;
import org.xml.sax.HandlerBase;
import org.xml.sax.InputSource;
import org.xml.sax.Parser;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import uk.co.wilson.xml.MinML;

//import abcs.logic.communication.log.LogUtil;
/**
 * This abstract base class provides basic capabilities for XML-RPC,
 * like parsing of parameters or encoding Java objects into XML-RPC
 * format.  Any XML parser with a <a
 * href="http://www.megginson.com/SAX/">SAX</a> interface can be used.
 *
 * <p>XmlRpcServer and XmlRpcClient are the classes that actually
 * implement an XML-RPC server and client.
 *
 * @see org.apache.xmlrpc.XmlRpcServer
 * @see org.apache.xmlrpc.XmlRpcClient
 *
 * @author <a href="mailto:hannes@apache.org">Hannes Wallnoefer</a>
 * @author <a href="mailto:dlr@finemaltcoding.com">Daniel Rall</a>
 * @author <a href="mailto:andrew@kungfoocoder.org">Andrew Evers</a>
 * @version $Id: XmlRpc.java,v 1.35 2002/11/21 01:28:16 dlr Exp $
 */
public abstract class XmlRpc extends HandlerBase
{
    /**
     * The version string used in HTTP communication.
     */
    // FIXME: Use Ant <filter> to preprocess during compilation
    public static final String version = "Apache XML-RPC 1.2-a3-dev";

    /**
     * The default parser to use (MinML).
     */
    private static final String DEFAULT_PARSER = MinML.class.getName();

    /**
     * The maximum number of threads which can be used concurrently.
     */
    private static int maxThreads = 100;

    String methodName;

    /**
     * The class name of SAX parser to use.
     */
    private static Class parserClass;
    private static Hashtable saxDrivers = new Hashtable (8);

    static
    {
        // A mapping of short identifiers to the fully qualified class
        // names of common SAX parsers.  If more mappings are added
        // here, increase the size of the saxDrivers Map used to store
        // them.
        XmlRpc.saxDrivers.put("xerces", "org.apache.xerces.parsers.SAXParser");
        XmlRpc.saxDrivers.put("xp", "com.jclark.xml.sax.Driver");
        XmlRpc.saxDrivers.put("ibm1", "com.ibm.xml.parser.SAXDriver");
        XmlRpc.saxDrivers.put("ibm2", "com.ibm.xml.parsers.SAXParser");
        XmlRpc.saxDrivers.put("aelfred", "com.microstar.xml.SAXDriver");
        XmlRpc.saxDrivers.put("oracle1", "oracle.xml.parser.XMLParser");
        XmlRpc.saxDrivers.put("oracle2", "oracle.xml.parser.v2.SAXParser");
        XmlRpc.saxDrivers.put("openxml", "org.openxml.parser.XMLSAXParser");
    }

    // the stack we're parsing our values into.
    Stack values;
    Value currentValue;

    /**
     * Used to collect character data (<code>CDATA</code>) of
     * parameter values.
     */
    StringBuffer cdata;
    boolean readCdata;

    // XML RPC parameter types used for dataMode
    static final int STRING = 0;
    static final int INTEGER = 1;
    static final int BOOLEAN = 2;
    static final int DOUBLE = 3;
    static final int DATE = 4;
    static final int BASE64 = 5;
    static final int STRUCT = 6;
    static final int ARRAY = 7;

    // Error level + message
    int errorLevel;
    String errorMsg;

    static final int NONE = 0;
    static final int RECOVERABLE = 1;
    static final int FATAL = 2;

    /**
     * Wheter to use HTTP Keep-Alive headers.
     */
    static boolean keepalive = false;

    /**
     * Whether to log debugging output.
     */
    //best to unremark BasicCryptUtil logging as well
    public static boolean debug = false;

    /**
     * The list of valid XML elements used for RPC.
     */
    final static String types[] =
    {
        "String",
        "Integer",
        "Boolean",
        "Double",
        "Date",
        "Base64",
        "Struct",
        "Array"
    };

    /**
     * Java's name for the encoding we're using.  Defaults to
     * <code>ISO8859_1</code>.
     */
    static String encoding = XmlWriter.ISO8859_1;

    private TypeFactory typeFactory;

    /**
     * Creates a new instance with the {@link
     * org.apache.xmlrpc.TypeFactory} set to an instance of the class
     * named by the <code>org.apache.xmlrpc.TypeFactory</code> System
     * property.  If property not set or class is unavailable, uses
     * the default.
     */
    protected XmlRpc()
    {
        //TWB - Applets don't have System.getProperty without special permissions
        //this(System.getProperty(TypeFactory.class.getName()));
        //this("org.apache.xmlrpc.DefaultTypeFactory");
        this(org.apache.xmlrpc.DefaultTypeFactory.class);
    }

    /**
     * Creates a new instance with the specified {@link
     * org.apache.xmlrpc.TypeFactory}.
     *
     * @param typeFactory The implementation to use.
     */
    protected XmlRpc(Class c)
    {
        this.typeFactory = this.createTypeFactory(c);
    }
    
//    protected XmlRpc(String typeFactory)
//    {
//        Class c = null;
//        if (typeFactory != null && typeFactory.length() > 0)
//        {
//            try
//            {
//                c = Class.forName(typeFactory);
//            }
//            catch (ClassNotFoundException e)
//            {
//                System.err.println("Error loading TypeFactory specified by " +
//                                   "the " + TypeFactory.class.getName() +
//                                   " property, using default instead: " +
//                                   e.getMessage());
//            }
//        }
//        this.typeFactory = createTypeFactory(c);
//    }

    /**
     * Creates a new instance of the specified {@link
     * org.apache.xmlrpc.TypeFactory}.
     *
     * @param typeFactory The implementation to use.
     * @return The new type mapping.
     */
    private TypeFactory createTypeFactory(Class typeFactory)
    {
        // If we're using the default, serve it up immediately.
        if (typeFactory == null ||
            DefaultTypeFactory.class.equals(typeFactory))
        {
            return new DefaultTypeFactory();
        }

        try
        {
            return (TypeFactory) typeFactory.newInstance();
        }
        catch (Exception e)
        {
            System.err.println("Unable to create configured TypeFactory '" +
                               typeFactory.getName() + "': " + e.getMessage() +
                               ": Using default");
            // Call self recursively to acquire default.
            return this.createTypeFactory(null);
        }
    }

    /**
     * Set the SAX Parser to be used. The argument can either be the
     * full class name or a user friendly shortcut if the parser is
     * known to this class. The parsers that can currently be set by
     * shortcut are listed in the main documentation page. If you are
     * using another parser please send me the name of the SAX driver
     * and I'll include it in a future release.  If setDriver() is
     * never called then the System property "sax.driver" is
     * consulted. If that is not defined the driver defaults to
     * OpenXML.
     */
    public static void setDriver(String driver) throws ClassNotFoundException
    {
        String parserClassName = null;
        try
        {
            parserClassName = (String) XmlRpc.saxDrivers.get(driver);
            //System.out.println("Using driver: " + driver + " parserClass: saxDriver: " + parserClassName);
            if (parserClassName == null)
            {
                // Identifier lookup failed, assuming we were provided
                // with the fully qualified class name.
                parserClassName = driver;
                //System.out.println("Using driver: " + driver + " parserClass: saxDriver: " + parserClassName);
            }
            XmlRpc.parserClass = Class.forName(parserClassName);
        }
        catch (ClassNotFoundException x)
        {
            throw new ClassNotFoundException ("SAX driver not found: "
                    + parserClassName);
        }
    }

    /**
     * Set the SAX Parser to be used by directly passing the Class object.
     */
    public static void setDriver(Class driver)
    {
        //System.out.println("Using parserClass driver: " + driver.getName() + " " + driver.getClass().getName());
        XmlRpc.parserClass = driver;
    }

    /**
     * Set the encoding of the XML.
     *
     * @param enc The Java name of the encoding.
     */
    public static void setEncoding(String enc)
    {
        XmlRpc.encoding = enc;
    }

    /**
     * Return the encoding, transforming to the canonical name if
     * possible.
     *
     * @see org.apache.xmlrpc.XmlWriter#canonicalizeEncoding(String)
     */
    public String getEncoding ()
    {
        return XmlWriter.canonicalizeEncoding(XmlRpc.encoding);
    }

    /**
     * Gets the maximum number of threads used at any given moment.
     */
    public static int getMaxThreads()
    {
        return XmlRpc.maxThreads;
    }

    /**
     * Sets the maximum number of threads used at any given moment.
     */
    public static void setMaxThreads(int maxThreads)
    {
        XmlRpc.maxThreads = maxThreads;
    }

    /**
     * Switch debugging output on/off.
     */
    public static void setDebug(boolean val)
    {
        XmlRpc.debug = val;
    }

    /**
     * Switch HTTP keepalive on/off.
     */
    public static void setKeepAlive(boolean val)
    {
        XmlRpc.keepalive = val;
    }

    /**
     * get current HTTP keepalive mode.
     */
    public static boolean getKeepAlive()
    {
        return XmlRpc.keepalive;
    }

    /**
     * Parse the input stream. For each root level object, method
     * <code>objectParsed</code> is called.
     */
    synchronized void parse(InputStream is) throws Exception
    {
       try
       {           
        // reset values (XmlRpc objects are reusable)
        this.errorLevel = XmlRpc.NONE;
        this.errorMsg = null;
        this.values = new Stack ();
        if (this.cdata == null)
        {
            this.cdata = new StringBuffer(128);
        }
        else
        {
            this.cdata.setLength(0);
        }
        
        this.readCdata = false;
        this.currentValue = null;

        long now = System.currentTimeMillis();
        if (XmlRpc.parserClass == null)
        {
            // try to get the name of the SAX driver from the System properties
            String driver;
            try
            {
                //TWB - Hack to deal with not having access to files
                //driver = System.getProperty("sax.driver", DEFAULT_PARSER);
                driver = XmlRpc.DEFAULT_PARSER;
            }
            catch (SecurityException e)
            {
                // An unsigned applet may not access system properties.
                driver = XmlRpc.DEFAULT_PARSER;
            }
            //System.out.println("Using default driver: " + driver);
            XmlRpc.setDriver(driver);
        }

        Parser parser = null;

        try
        {
            parser = (Parser) XmlRpc.parserClass.newInstance();
        }
        catch (NoSuchMethodError nsm)
        {
            // This is thrown if no constructor exists for the parser class
            // and is transformed into a regular exception.
            throw new Exception("Can't create Parser: " + XmlRpc.parserClass);
        }

        parser.setDocumentHandler(this);
        parser.setErrorHandler(this);

        if (XmlRpc.debug)
        {
            System.out.println("Beginning parsing XML input stream");
        }
        try
        {
            parser.parse(new InputSource (is));
        }
        finally
        {
            // Clear any huge buffers.
            if (this.cdata.length() > 128 * 4)
            {
                // Exceeded original capacity by greater than 4x; release
                // buffer to prevent leakage.
                this.cdata = null;
            }
        }
        if (XmlRpc.debug)
        {
            System.out.println ("Spent " + (System.currentTimeMillis() - now) + " millis parsing");
        }
        
       }        
        catch (Exception e)
        {
           //System.out.println ("Parsing Error: " + Log.get(e));
           throw e;
        }
        
    }

    /**
     * This method is called when a root level object has been parsed.
     * Sub-classes implement this callback to receive the fully parsed
     * object.
     */
    protected abstract void objectParsed(Object what);


    ////////////////////////////////////////////////////////////////
    // methods called by XML parser

    /**
     * Method called by SAX driver.
     */
    @Override
    public void characters(char ch[], int start, int length)
            throws SAXException
    {
        if (this.readCdata)
        {
            this.cdata.append(ch, start, length);
        }
    }

    /**
     * Method called by SAX driver.
     */
    public void endElement(String name) throws SAXException
    {

        if (XmlRpc.debug)
        {
            System.out.println("endElement: " + name);
        }

        // finalize character data, if appropriate
        if (this.currentValue != null && this.readCdata)
        {
            this.currentValue.characterData(this.cdata.toString());
            this.cdata.setLength(0);
            this.readCdata = false;
        }

        if ("value".equals(name))
        {
            // Only handle top level objects or objects contained in
            // arrays here.  For objects contained in structs, wait
            // for </member> (see code below).
            int depth = this.values.size ();
            if (depth < 2 || this.values.get(depth - 2).hashCode() != XmlRpc.STRUCT)
            {
                Value v = this.currentValue;
                this.values.pop();
                if (depth < 2)
                {
                    // This is a top-level object
                    this.objectParsed(v.value);
                    this.currentValue = null;
                }
                else
                {
                    // Add object to sub-array; if current container
                    // is a struct, add later (at </member>).
                    this.currentValue = (Value) this.values.peek();
                    this.currentValue.endElement(v);
                }
            }
        }

        // Handle objects contained in structs.
        if ("member".equals(name))
        {
            Value v = this.currentValue;
            this.values.pop();
            this.currentValue = (Value) this.values.peek();
            this.currentValue.endElement(v);
        }

        else if ("methodName".equals(name))
        {
            this.methodName = this.cdata.toString();
            this.cdata.setLength(0);
            this.readCdata = false;
        }
    }

    /**
     * Method called by SAX driver.
     */
    public void startElement(String name, AttributeList atts)
            throws SAXException
    {
        if (XmlRpc.debug)
        {
            System.out.println("startElement: " + name);
        }

        if ("value".equals(name))
        {
            Value v = new Value();
            this.values.push(v);
            this.currentValue = v;
            // cdata object is reused
            this.cdata.setLength(0);
            this.readCdata = true;
        }
        else if ("methodName".equals(name))
        {
            this.cdata.setLength(0);
            this.readCdata = true;
        }
        else if ("name".equals(name))
        {
            this.cdata.setLength(0);
            this.readCdata = true;
        }
        else if ("string".equals(name))
        {
            // currentValue.setType (STRING);
            this.cdata.setLength(0);
            this.readCdata = true;
        }
        else if ("i4".equals(name) || "int".equals(name))
        {
            this.currentValue.setType(XmlRpc.INTEGER);
            this.cdata.setLength(0);
            this.readCdata = true;
        }
        else if ("boolean".equals(name))
        {
            this.currentValue.setType(XmlRpc.BOOLEAN);
            this.cdata.setLength(0);
            this.readCdata = true;
        }
        else if ("double".equals(name))
        {
            this.currentValue.setType(XmlRpc.DOUBLE);
            this.cdata.setLength(0);
            this.readCdata = true;
        }
        else if ("dateTime.iso8601".equals(name))
        {
            this.currentValue.setType(XmlRpc.DATE);
            this.cdata.setLength(0);
            this.readCdata = true;
        }
        else if ("base64".equals(name))
        {
            this.currentValue.setType(XmlRpc.BASE64);
            this.cdata.setLength(0);
            this.readCdata = true;
        }
        else if ("struct".equals(name))
        {
            this.currentValue.setType(XmlRpc.STRUCT);
        }
        else if ("array".equals(name))
        {
            this.currentValue.setType(XmlRpc.ARRAY);
        }
    }

    /**
     *
     * @param e
     * @throws SAXException
     */
    public void error(SAXParseException e) throws SAXException
    {
        System.err.println("Error parsing XML: " + e);
        this.errorLevel = XmlRpc.RECOVERABLE;
        this.errorMsg = e.toString();
    }

    /**
     *
     * @param e
     * @throws SAXException
     */
    public void fatalError(SAXParseException e) throws SAXException
    {
        System.err.println("Fatal error parsing XML: " + e);
        this.errorLevel = XmlRpc.FATAL;
        this.errorMsg = e.toString();
    }

    /**
     * This represents a XML-RPC value parsed from the request.
     */
    class Value
    {
        int type;
        Object value;
        // the name to use for the next member of struct values
        String nextMemberName;

        Hashtable struct;
        BasicArrayList array;

        /**
         * Constructor.
         */
        public Value()
        {
            this.type = XmlRpc.STRING;
        }

        /**
         * Notification that a new child element has been parsed.
         */
        public void endElement(Value child)
        {
            switch (this.type)
            {
                case XmlRpc.ARRAY:
                    this.array.add(child.value);
                    break;
                case XmlRpc.STRUCT:
                    this.struct.put(this.nextMemberName, child.value);
            }
        }

        /**
         * Set the type of this value. If it's a container, create the
         * corresponding java container.
         */
        public void setType(int type)
        {
            //System.out.println ("setting type to "+types[type]);
            this.type = type;
            switch (type)
            {
                case XmlRpc.ARRAY:
                    this.value = this.array = new BasicArrayListD();
                    break;
                case XmlRpc.STRUCT:
                    this.value = this.struct = new Hashtable ();
                    break;
            }
        }

        /**
         * Set the character data for the element and interpret it
         * according to the element type.
         */
        public void characterData(String cdata)
        {
            final TypeFactory typeFactory = XmlRpc.this.typeFactory;
            switch (this.type)
            {
                case XmlRpc.INTEGER:
                    this.value = typeFactory.createInteger(cdata);
                    break;
                case XmlRpc.BOOLEAN:
                    this.value = typeFactory.createBoolean(cdata);
                    break;
                case XmlRpc.DOUBLE:
                    this.value = typeFactory.createDouble(cdata);
                    break;
                case XmlRpc.DATE:
                    this.value = typeFactory.createDate(cdata);
                    break;
                case XmlRpc.BASE64:
                    this.value = typeFactory.createBase64(cdata);
                    break;
                case XmlRpc.STRING:
                    this.value = typeFactory.createString(cdata);
                    break;
                case XmlRpc.STRUCT:
                    // this is the name to use for the next member of this struct
                    this.nextMemberName = cdata;
                    break;
            }
        }

        /**
         * This is a performance hack to get the type of a value
         * without casting the Object.  It breaks the contract of
         * method hashCode, but it doesn't matter since Value objects
         * are never used as keys in Hashtables.
         */
        public int hashCode()
        {
            return this.type;
        }

        /**
         *
         * @return
         */
        public String toString()
        {
            return (XmlRpc.types[this.type] + " element " + this.value);
        }
    }
}
