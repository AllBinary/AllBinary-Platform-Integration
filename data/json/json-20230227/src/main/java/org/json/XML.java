package org.json;

/*
Public Domain.
*/

import java.io.Reader;
import java.io.StringReader;
//import java.math.BigDecimal;
//import java.math.BigInteger;
import java.util.ArrayList;
import org.allbinary.logic.java.bool.BooleanFactory;

import org.allbinary.logic.string.StringUtil;
import org.allbinary.string.CommonSeps;

/**
 * This provides static methods to convert an XML text into a JSONObject, and to
 * covert a JSONObject into an XML text.
 *
 * @author JSON.org
 * @version 2016-08-10
 */
@SuppressWarnings("boxing")
public class XML {

    /** The Character '&amp;'. */
    public static final Character AMP = '&';

    /** The Character '''. */
    public static final Character APOS = '\'';

    /** The Character '!'. */
    public static final Character BANG = '!';

    /** The Character '='. */
    public static final Character EQ = '=';

    /** The Character <pre>{@code '>'. }</pre>*/
    public static final Character GT = '>';

    /** The Character '&lt;'. */
    public static final Character LT = '<';

    /** The Character '?'. */
    public static final Character QUEST = '?';

    /** The Character '"'. */
    public static final Character QUOT = '"';

    /** The Character '/'. */
    public static final Character SLASH = '/';

    /**
     * Null attribute name
     */
    public static final String NULL_ATTR = "xsi:nil";

    public static final String TYPE_ATTR = "xsi:type";

    /**
     * Creates an iterator for navigating Code Points in a string instead of
     * characters. Once Java7 support is dropped, this can be replaced with
     * <code>
     * string.codePoints()
     * </code>
     * which is available in Java8 and above.
     *
     * @see <a href=
     *      "http://stackoverflow.com/a/21791059/6030888">http://stackoverflow.com/a/21791059/6030888</a>
     */
    private static Iterable<Integer> codePointIterator(final String string) {
        final ArrayList<Integer> codePoints = new ArrayList<Integer>();
        int nextIndex = 0;
        while (nextIndex < string.length()) {
            int result = string.codePointAt(nextIndex);
            codePoints.add(result);
            nextIndex += Character.charCount(result);
        }
        return codePoints;
    }

    /**
     * Replace special characters with XML escapes:
     *
     * <pre>{@code
     * &amp; (ampersand) is replaced by &amp;amp;
     * &lt; (less than) is replaced by &amp;lt;
     * &gt; (greater than) is replaced by &amp;gt;
     * &quot; (double quote) is replaced by &amp;quot;
     * &apos; (single quote / apostrophe) is replaced by &amp;apos;
     * }</pre>
     *
     * @param string
     *            The string to be escaped.
     * @return The escaped string.
     */
    public static String escape(String string) {
        StringBuilder sb = new StringBuilder(string.length());
        for (final int cp : codePointIterator(string)) {
            switch (cp) {
            case '&':
                sb.append("&amp;");
                break;
            case '<':
                sb.append("&lt;");
                break;
            case '>':
                sb.append("&gt;");
                break;
            case '"':
                sb.append("&quot;");
                break;
            case '\'':
                sb.append("&apos;");
                break;
            default:
                if (XML.mustEscape(cp)) {
                    sb.append("&#x");
                    sb.append(Integer.toHexString(cp));
                    sb.append(';');
                } else {
                    sb.appendCodePoint(cp);
                }
            }
        }
        return sb.toString();
    }

    /**
     * @param cp code point to test
     * @return true if the code point is not valid for an XML
     */
    private static boolean mustEscape(int cp) {
        /* Valid range from https://www.w3.org/TR/REC-xml/#charsets
         *
         * #x9 | #xA | #xD | [#x20-#xD7FF] | [#xE000-#xFFFD] | [#x10000-#x10FFFF]
         *
         * any Unicode character, excluding the surrogate blocks, FFFE, and FFFF.
         */
        // isISOControl is true when (cp >= 0 && cp <= 0x1F) || (cp >= 0x7F && cp <= 0x9F)
        // all ISO control characters are out of range except tabs and new lines
        return (Character.isISOControl(cp)
                && cp != 0x9
                && cp != 0xA
                && cp != 0xD
            ) || !(
                // valid the range of acceptable characters that aren't control
                (cp >= 0x20 && cp <= 0xD7FF)
                || (cp >= 0xE000 && cp <= 0xFFFD)
                || (cp >= 0x10000 && cp <= 0x10FFFF)
            )
        ;
    }

    /**
     * Removes XML escapes from the string.
     *
     * @param string
     *            string to remove escapes from
     * @return string with converted entities
     */
    public static String unescape(String string) {
        StringBuilder sb = new StringBuilder(string.length());
        final int length = string.length();
        int i = 0;
        while (i < length) {
            char c = string.charAt(i);
            if (c == '&') {
                final int semic = string.indexOf(';', i);
                if (semic > i) {
                    final String entity = string.substring(i + 1, semic);
                    sb.append(XMLTokener.unescapeEntity(entity));
                    // skip past the entity we just parsed.
                    i += entity.length() + 1;
                } else {
                    // this shouldn't happen in most cases since the parser
                    // errors on unclosed entries.
                    sb.append(c);
                }
            } else {
                // not part of an entity
                sb.append(c);
            }
            i++;
        }
        return sb.toString();
    }

    /**
     * Throw an exception if the string contains whitespace. Whitespace is not
     * allowed in tagNames and attributes.
     *
     * @param string
     *            A string.
     * @throws JSONException Thrown if the string contains whitespace or is empty.
     */
    public static void noSpace(String string) throws JSONException {
        int i, length = string.length();
        if (length == 0) {
            throw new JSONException("Empty string.");
        }
        for (i = 0; i < length; i += 1) {
            if (Character.isWhitespace(string.charAt(i))) {
                throw new JSONException("'" + string
                        + "' contains a space character.");
            }
        }
    }

    /**
     * Scan the content following the named tag, attaching it to the context.
     *
     * @param x
     *            The XMLTokener containing the source string.
     * @param context
     *            The JSONObject that will include the new material.
     * @param name
     *            The tag name.
     * @param config
     *            The XML parser configuration.
     * @param currentNestingDepth
     *            The current nesting depth.
     * @return true if the close tag is processed.
     * @throws JSONException Thrown if any parsing error occurs.
     */
    private static boolean parse(XMLTokener x, JSONObject context, String nameCanBeNull, XMLParserConfiguration config, int currentNestingDepth)
            throws JSONException {
        char c;
        int i;
        JSONObject jsonObjectCanBeNull = null;
        String string;
        String tagName;
        Object tokenCanBeNull;
        XMLXsiTypeConverter<?> xmlXsiTypeConverterCanBeNull;

        // Test for and skip past these forms:
        // <!-- ... -->
        // <! ... >
        // <![ ... ]]>
        // <? ... ?>
        // Report errors for these forms:
        // <>
        // <=
        // <<

        tokenCanBeNull = x.nextToken();

        // <!

        if (tokenCanBeNull == XML.BANG) {
            c = x.next();
            if (c == '-') {
                if (x.next() == '-') {
                    x.skipPast("-->");
                    return false;
                }
                x.back();
            } else if (c == '[') {
                tokenCanBeNull = x.nextToken();
                if ("CDATA".equals(tokenCanBeNull)) {
                    if (x.next() == '[') {
                        string = x.nextCDATA();
                        if (string.length() > 0) {
                            context.accumulate(config.getcDataTagName(), string);
                        }
                        return false;
                    }
                }
                throw x.syntaxError("Expected 'CDATA['");
            }
            i = 1;
            do {
                tokenCanBeNull = x.nextMeta();
                if (tokenCanBeNull == null) {
                    throw x.syntaxError("Missing '>' after '<!'.");
                } else if (tokenCanBeNull == XML.LT) {
                    i += 1;
                } else if (tokenCanBeNull == XML.GT) {
                    i -= 1;
                }
            } while (i > 0);
            return false;
        } else if (tokenCanBeNull == XML.QUEST) {

            // <?
            x.skipPast("?>");
            return false;
        } else if (tokenCanBeNull == XML.SLASH) {

            // Close tag </

            tokenCanBeNull = x.nextToken();
            if (nameCanBeNull == null) {
                throw x.syntaxError("Mismatched close tag " + tokenCanBeNull);
            }
            if (!tokenCanBeNull.equals(nameCanBeNull)) {
                throw x.syntaxError("Mismatched " + nameCanBeNull + " and " + tokenCanBeNull);
            }
            if (x.nextToken() != XML.GT) {
                throw x.syntaxError("Misshaped close tag");
            }
            return true;

        } else if (tokenCanBeNull instanceof Character) {
            throw x.syntaxError("Misshaped tag");

            // Open tag <

        } else {
            tagName = (String) tokenCanBeNull;
            tokenCanBeNull = null;
            jsonObjectCanBeNull = new JSONObject();
            boolean nilAttributeFound = false;
            xmlXsiTypeConverterCanBeNull = null;
            while (true) {
                if (tokenCanBeNull == null) {
                    tokenCanBeNull = x.nextToken();
                }
                // attribute = value
                if (tokenCanBeNull instanceof String) {
                    string = (String) tokenCanBeNull;
                    tokenCanBeNull = x.nextToken();
                    if (tokenCanBeNull == XML.EQ) {
                        tokenCanBeNull = x.nextToken();
                        if (!(tokenCanBeNull instanceof String)) {
                            throw x.syntaxError("Missing value");
                        }

                        if (config.isConvertNilAttributeToNull()
                                && XML.NULL_ATTR.equals(string)
                                && Boolean.parseBoolean((String) tokenCanBeNull)) {
                            nilAttributeFound = true;
                        } else if(config.getXsiTypeMap() != null && !config.getXsiTypeMap().isEmpty()
                                && XML.TYPE_ATTR.equals(string)) {
                            xmlXsiTypeConverterCanBeNull = config.getXsiTypeMap().get(tokenCanBeNull);
                        } else if (!nilAttributeFound) {
                            jsonObjectCanBeNull.accumulate(string,
                                    config.isKeepStrings()
                                            ? ((String) tokenCanBeNull)
                                            : stringToValue((String) tokenCanBeNull));
                        }
                        tokenCanBeNull = null;
                    } else {
                        jsonObjectCanBeNull.accumulate(string, StringUtil.getInstance().EMPTY_STRING);
                    }


                } else if (tokenCanBeNull == XML.SLASH) {
                    // Empty tag <.../>
                    if (x.nextToken() != XML.GT) {
                        throw x.syntaxError("Misshaped tag");
                    }
                    if (config.getForceList().contains(tagName)) {
                        // Force the value to be an array
                        if (nilAttributeFound) {
                            context.append(tagName, JSONObject.NULL);
                        } else if (jsonObjectCanBeNull.length() > 0) {
                            context.append(tagName, jsonObjectCanBeNull);
                        } else {
                            context.put(tagName, new JSONArray());
                        }
                    } else {
                        if (nilAttributeFound) {
                            context.accumulate(tagName, JSONObject.NULL);
                        } else if (jsonObjectCanBeNull.length() > 0) {
                            context.accumulate(tagName, jsonObjectCanBeNull);
                        } else {
                            context.accumulate(tagName, StringUtil.getInstance().EMPTY_STRING);
                        }
                    }
                    return false;

                } else if (tokenCanBeNull == XML.GT) {
                    // Content, between <...> and </...>
                    while (true) {
                        tokenCanBeNull = x.nextContent();
                        if (tokenCanBeNull == null) {
                            if (tagName != null) {
                                throw x.syntaxError("Unclosed tag " + tagName);
                            }
                            return false;
                        } else if (tokenCanBeNull instanceof String) {
                            string = (String) tokenCanBeNull;
                            if (string.length() > 0) {
                                if(xmlXsiTypeConverterCanBeNull != null) {
                                    jsonObjectCanBeNull.accumulate(config.getcDataTagName(),
                                            stringToValue(string, xmlXsiTypeConverterCanBeNull));
                                } else {
                                    jsonObjectCanBeNull.accumulate(config.getcDataTagName(),
                                            config.isKeepStrings() ? string : stringToValue(string));
                                }
                            }

                        } else if (tokenCanBeNull == XML.LT) {
                            // Nested element
                            if (currentNestingDepth == config.getMaxNestingDepth()) {
                                throw x.syntaxError("Maximum nesting depth of " + config.getMaxNestingDepth() + " reached");
                            }

                            if (XML.parse(x, jsonObjectCanBeNull, tagName, config, currentNestingDepth + 1)) {
                                if (config.getForceList().contains(tagName)) {
                                    // Force the value to be an array
                                    if (jsonObjectCanBeNull.length() == 0) {
                                        context.put(tagName, new JSONArray());
                                    } else if (jsonObjectCanBeNull.length() == 1
                                            && jsonObjectCanBeNull.opt(config.getcDataTagName()) != null) {
                                        context.append(tagName, jsonObjectCanBeNull.opt(config.getcDataTagName()));
                                    } else {
                                        context.append(tagName, jsonObjectCanBeNull);
                                    }
                                } else {
                                    if (jsonObjectCanBeNull.length() == 0) {
                                        context.accumulate(tagName, StringUtil.getInstance().EMPTY_STRING);
                                    } else if (jsonObjectCanBeNull.length() == 1
                                            && jsonObjectCanBeNull.opt(config.getcDataTagName()) != null) {
                                        context.accumulate(tagName, jsonObjectCanBeNull.opt(config.getcDataTagName()));
                                    } else {
                                        context.accumulate(tagName, jsonObjectCanBeNull);
                                    }
                                }

                                return false;
                            }
                        }
                    }
                } else {
                    throw x.syntaxError("Misshaped tag");
                }
            }
        }
    }

    /**
     * This method tries to convert the given string value to the target object
     * @param string String to convert
     * @param typeConverter value converter to convert string to integer, boolean e.t.c
     * @return JSON value of this string or the string
     */
    public static Object stringToValue(String string, XMLXsiTypeConverter<?> typeConverterCanBeNull) {
        if (typeConverterCanBeNull != null) {
            return typeConverterCanBeNull.convert(string);
        }
        return XML.stringToValue(string);
    }

    /**
     * This method is the same as {@link JSONObject#stringToValue(String)}.
     *
     * @param string String to convert
     * @return JSON value of this string or the string
     */
    // To maintain compatibility with the Android API, this method is a direct copy of
    // the one in JSONObject. Changes made here should be reflected there.
    // This method should not make calls out of the XML object.
    public static Object stringToValue(String string) {
        if (StringUtil.getInstance().EMPTY_STRING.equals(string)) {
            return string;
        }

        final BooleanFactory booleanFactory = BooleanFactory.getInstance();
        
        // check JSON key words true/false/null
        if ("true".equalsIgnoreCase(string)) {
            return booleanFactory.TRUE;
        }
        if ("false".equalsIgnoreCase(string)) {
            return booleanFactory.FALSE;
        }
        if ("null".equalsIgnoreCase(string)) {
            return JSONObject.NULL;
        }

        /*
         * If it might be a number, try converting it. If a number cannot be
         * produced, then the value will just be a string.
         */

        char initial = string.charAt(0);
        if ((initial >= '0' && initial <= '9') || initial == '-') {
            try {
                return XML.stringToNumber(string);
            } catch (Exception ignore) {
            }
        }
        return string;
    }

    /**
     * direct copy of {@link JSONObject#stringToNumber(String)} to maintain Android support.
     */
    private static Number stringToNumber(final String value) throws NumberFormatException {
        char initial = value.charAt(0);
        if ((initial >= '0' && initial <= '9') || initial == '-') {
            // decimal representation
            if (isDecimalNotation(value)) {
                // Use a BigDecimal all the time so we keep the original
                // representation. BigDecimal doesn't support -0.0, ensure we
                // keep that by forcing a decimal.
//                try {
//                    BigDecimal bd = new BigDecimal(value);
//                    if(initial == '-' && BigDecimal.ZERO.compareTo(bd)==0) {
//                        return Double.valueOf(-0.0);
//                    }
//                    return bd;
//                } catch (NumberFormatException retryAsDouble) {
                    // this is to support "Hex Floats" like this: 0x1.0P-1074
                    try {
                        Double d = Double.parseDouble(value);
                        if(d.isNaN() || d.isInfinite()) {
                            throw new NumberFormatException("value ["+value+"] is not a valid number.");
                        }
                        return d;
                    } catch (NumberFormatException ignore) {
                        throw new NumberFormatException("value ["+value+"] is not a valid number.");
                    }
//                }
            }
            // block items like 00 01 etc. Java number parsers treat these as Octal.
            if(initial == '0' && value.length() > 1) {
                char at1 = value.charAt(1);
                if(at1 >= '0' && at1 <= '9') {
                    throw new NumberFormatException("value ["+value+"] is not a valid number.");
                }
            } else if (initial == '-' && value.length() > 2) {
                char at1 = value.charAt(1);
                char at2 = value.charAt(2);
                if(at1 == '0' && at2 >= '0' && at2 <= '9') {
                    throw new NumberFormatException("value ["+value+"] is not a valid number.");
                }
            }
            // integer representation.
            // This will narrow any values to the smallest reasonable Object representation
            // (Integer, Long, or BigInteger)

            // BigInteger down conversion: We use a similar bitLength compare as
            // BigInteger#intValueExact uses. Increases GC, but objects hold
            // only what they need. i.e. Less runtime overhead if the value is
            // long lived.
//            BigInteger bi = new BigInteger(value);
//            if(bi.bitLength() <= 31){
//                return Integer.valueOf(bi.intValue());
//            }
//            if(bi.bitLength() <= 63){
//                return Long.valueOf(bi.longValue());
//            }
//            return bi;
        }
        throw new NumberFormatException("value ["+value+"] is not a valid number.");
    }

    /**
     * direct copy of {@link JSONObject#isDecimalNotation(String)} to maintain Android support.
     */
    private static boolean isDecimalNotation(final String value) {
        return value.indexOf('.') > -1 || value.indexOf('e') > -1
                || value.indexOf('E') > -1 || "-0".equals(value);
    }


    /**
     * Convert a well-formed (but not necessarily valid) XML string into a
     * JSONObject. Some information may be lost in this transformation because
     * JSON is a data format and XML is a document format. XML uses elements,
     * attributes, and content text, while JSON uses unordered collections of
     * name/value pairs and arrays of values. JSON does not does not like to
     * distinguish between elements and attributes. Sequences of similar
     * elements are represented as JSONArrays. Content text may be placed in a
     * "content" member. Comments, prologs, DTDs, and <pre>{@code
     * &lt;[ [ ]]>}</pre>
     * are ignored.
     *
     * @param string
     *            The source string.
     * @return A JSONObject containing the structured data from the XML string.
     * @throws JSONException Thrown if there is an errors while parsing the string
     */
    public static JSONObject toJSONObject(String string) throws JSONException {
        return XML.toJSONObject(string, XMLParserConfiguration.ORIGINAL);
    }

    /**
     * Convert a well-formed (but not necessarily valid) XML into a
     * JSONObject. Some information may be lost in this transformation because
     * JSON is a data format and XML is a document format. XML uses elements,
     * attributes, and content text, while JSON uses unordered collections of
     * name/value pairs and arrays of values. JSON does not does not like to
     * distinguish between elements and attributes. Sequences of similar
     * elements are represented as JSONArrays. Content text may be placed in a
     * "content" member. Comments, prologs, DTDs, and <pre>{@code
     * &lt;[ [ ]]>}</pre>
     * are ignored.
     *
     * @param reader The XML source reader.
     * @return A JSONObject containing the structured data from the XML string.
     * @throws JSONException Thrown if there is an errors while parsing the string
     */
    public static JSONObject toJSONObject(Reader reader) throws JSONException {
        return XML.toJSONObject(reader, XMLParserConfiguration.ORIGINAL);
    }

    /**
     * Convert a well-formed (but not necessarily valid) XML into a
     * JSONObject. Some information may be lost in this transformation because
     * JSON is a data format and XML is a document format. XML uses elements,
     * attributes, and content text, while JSON uses unordered collections of
     * name/value pairs and arrays of values. JSON does not does not like to
     * distinguish between elements and attributes. Sequences of similar
     * elements are represented as JSONArrays. Content text may be placed in a
     * "content" member. Comments, prologs, DTDs, and <pre>{@code
     * &lt;[ [ ]]>}</pre>
     * are ignored.
     *
     * All values are converted as strings, for 1, 01, 29.0 will not be coerced to
     * numbers but will instead be the exact value as seen in the XML document.
     *
     * @param reader The XML source reader.
     * @param keepStrings If true, then values will not be coerced into boolean
     *  or numeric values and will instead be left as strings
     * @return A JSONObject containing the structured data from the XML string.
     * @throws JSONException Thrown if there is an errors while parsing the string
     */
    public static JSONObject toJSONObject(Reader reader, boolean keepStrings) throws JSONException {
        if(keepStrings) {
            return XML.toJSONObject(reader, XMLParserConfiguration.KEEP_STRINGS);
        }
        return XML.toJSONObject(reader, XMLParserConfiguration.ORIGINAL);
    }

    /**
     * Convert a well-formed (but not necessarily valid) XML into a
     * JSONObject. Some information may be lost in this transformation because
     * JSON is a data format and XML is a document format. XML uses elements,
     * attributes, and content text, while JSON uses unordered collections of
     * name/value pairs and arrays of values. JSON does not does not like to
     * distinguish between elements and attributes. Sequences of similar
     * elements are represented as JSONArrays. Content text may be placed in a
     * "content" member. Comments, prologs, DTDs, and <pre>{@code
     * &lt;[ [ ]]>}</pre>
     * are ignored.
     *
     * All values are converted as strings, for 1, 01, 29.0 will not be coerced to
     * numbers but will instead be the exact value as seen in the XML document.
     *
     * @param reader The XML source reader.
     * @param config Configuration options for the parser
     * @return A JSONObject containing the structured data from the XML string.
     * @throws JSONException Thrown if there is an errors while parsing the string
     */
    public static JSONObject toJSONObject(Reader reader, XMLParserConfiguration config) throws JSONException {
        JSONObject jo = new JSONObject();
        XMLTokener x = new XMLTokener(reader);
        while (x.more()) {
            x.skipPast("<");
            if(x.more()) {
                XML.parse(x, jo, null, config, 0);
            }
        }
        return jo;
    }

    /**
     * Convert a well-formed (but not necessarily valid) XML string into a
     * JSONObject. Some information may be lost in this transformation because
     * JSON is a data format and XML is a document format. XML uses elements,
     * attributes, and content text, while JSON uses unordered collections of
     * name/value pairs and arrays of values. JSON does not does not like to
     * distinguish between elements and attributes. Sequences of similar
     * elements are represented as JSONArrays. Content text may be placed in a
     * "content" member. Comments, prologs, DTDs, and <pre>{@code
     * &lt;[ [ ]]>}</pre>
     * are ignored.
     *
     * All values are converted as strings, for 1, 01, 29.0 will not be coerced to
     * numbers but will instead be the exact value as seen in the XML document.
     *
     * @param string
     *            The source string.
     * @param keepStrings If true, then values will not be coerced into boolean
     *  or numeric values and will instead be left as strings
     * @return A JSONObject containing the structured data from the XML string.
     * @throws JSONException Thrown if there is an errors while parsing the string
     */
    public static JSONObject toJSONObject(String string, boolean keepStrings) throws JSONException {
        return XML.toJSONObject(new StringReader(string), keepStrings);
    }

    /**
     * Convert a well-formed (but not necessarily valid) XML string into a
     * JSONObject. Some information may be lost in this transformation because
     * JSON is a data format and XML is a document format. XML uses elements,
     * attributes, and content text, while JSON uses unordered collections of
     * name/value pairs and arrays of values. JSON does not does not like to
     * distinguish between elements and attributes. Sequences of similar
     * elements are represented as JSONArrays. Content text may be placed in a
     * "content" member. Comments, prologs, DTDs, and <pre>{@code
     * &lt;[ [ ]]>}</pre>
     * are ignored.
     *
     * All values are converted as strings, for 1, 01, 29.0 will not be coerced to
     * numbers but will instead be the exact value as seen in the XML document.
     *
     * @param string
     *            The source string.
     * @param config Configuration options for the parser.
     * @return A JSONObject containing the structured data from the XML string.
     * @throws JSONException Thrown if there is an errors while parsing the string
     */
    public static JSONObject toJSONObject(String string, XMLParserConfiguration config) throws JSONException {
        return XML.toJSONObject(new StringReader(string), config);
    }

    /**
     * Convert a JSONObject into a well-formed, element-normal XML string.
     *
     * @param object
     *            A JSONObject.
     * @return A string.
     * @throws JSONException Thrown if there is an error parsing the string
     */
    public static String toString(Object objectCanBeNull) throws JSONException {
        return XML.toString(objectCanBeNull, null, XMLParserConfiguration.ORIGINAL);
    }

    /**
     * Convert a JSONObject into a well-formed, element-normal XML string.
     *
     * @param object
     *            A JSONObject.
     * @param tagName
     *            The optional name of the enclosing tag.
     * @return A string.
     * @throws JSONException Thrown if there is an error parsing the string
     */
    public static String toString(final Object objectCanBeNull, final String tagNameCanBeNull) {
        return XML.toString(objectCanBeNull, tagNameCanBeNull, XMLParserConfiguration.ORIGINAL);
    }

    /**
     * Convert a JSONObject into a well-formed, element-normal XML string.
     *
     * @param object
     *            A JSONObject.
     * @param tagName
     *            The optional name of the enclosing tag.
     * @param config
     *            Configuration that can control output to XML.
     * @return A string.
     * @throws JSONException Thrown if there is an error parsing the string
     */
    public static String toString(final Object objectCanBeNull, final String tagNameCanBeNull, final XMLParserConfiguration config)
            throws JSONException {
        return XML.toString(objectCanBeNull, tagNameCanBeNull, config, 0, 0);
    }

    /**
     * Convert a JSONObject into a well-formed, element-normal XML string,
     * either pretty print or single-lined depending on indent factor.
     *
     * @param object
     *            A JSONObject.
     * @param tagName
     *            The optional name of the enclosing tag.
     * @param config
     *            Configuration that can control output to XML.
     * @param indentFactor
     *            The number of spaces to add to each level of indentation.
     * @param indent
     *            The current ident level in spaces.
     * @return
     * @throws JSONException
     */
    private static String toString(final Object objectCanBeNull, final String tagNameCanBeNull, final XMLParserConfiguration config, int indentFactor, int indent)
            throws JSONException {
        StringBuilder sb = new StringBuilder();
        JSONArray ja;
        JSONObject jo;
        String string;

        if (objectCanBeNull instanceof JSONObject) {

            // Emit <tagName>
            if (tagNameCanBeNull != null) {
                sb.append(indent(indent));
                sb.append('<');
                sb.append(tagNameCanBeNull);
                sb.append('>');
                if(indentFactor > 0){
                    sb.append(CommonSeps.getInstance().NEW_LINE);
                    indent += indentFactor;
                }
            }

            // Loop thru the keys.
            // don't use the new entrySet accessor to maintain Android Support
            jo = (JSONObject) objectCanBeNull;
            for (final String key : jo.keySet()) {
                Object value = jo.opt(key);
                if (value == null) {
                    value = StringUtil.getInstance().EMPTY_STRING;
                } else if (value.getClass().isArray()) {
                    value = new JSONArray(value);
                }

                // Emit content in body
                if (key.equals(config.getcDataTagName())) {
                    if (value instanceof JSONArray) {
                        ja = (JSONArray) value;
                        int jaLength = ja.length();
                        // don't use the new iterator API to maintain support for Android
						for (int i = 0; i < jaLength; i++) {
                            if (i > 0) {
                                sb.append('\n');
                            }
                            Object value2 = ja.opt(i);
                            sb.append(XML.escape(value2.toString()));
                        }
                    } else {
                        sb.append(XML.escape(value.toString()));
                    }

                    // Emit an array of similar keys

                } else if (value instanceof JSONArray) {
                    ja = (JSONArray) value;
                    int jaLength = ja.length();
                    // don't use the new iterator API to maintain support for Android
					for (int i = 0; i < jaLength; i++) {
                        Object value2 = ja.opt(i);
                        if (value instanceof JSONArray) {
                            sb.append('<');
                            sb.append(key);
                            sb.append('>');
                            sb.append(XML.toString(value2, null, config, indentFactor, indent));
                            sb.append("</");
                            sb.append(key);
                            sb.append('>');
                        } else {
                            sb.append(XML.toString(value2, key, config, indentFactor, indent));
                        }
                    }
                } else if (StringUtil.getInstance().EMPTY_STRING.equals(value)) {
                    sb.append(indent(indent));
                    sb.append('<');
                    sb.append(key);
                    sb.append("/>");
                    if(indentFactor > 0){
                        sb.append(CommonSeps.getInstance().NEW_LINE);
                    }

                    // Emit a new tag <k>

                } else {
                    sb.append(XML.toString(value, key, config, indentFactor, indent));
                }
            }
            if (tagNameCanBeNull != null) {

                // Emit the </tagName> close tag
                sb.append(indent(indent - indentFactor));
                sb.append("</");
                sb.append(tagNameCanBeNull);
                sb.append('>');
                if(indentFactor > 0){
                    sb.append(CommonSeps.getInstance().NEW_LINE);
                }
            }
            return sb.toString();

        }

        if (objectCanBeNull != null && (objectCanBeNull instanceof JSONArray ||  objectCanBeNull.getClass().isArray())) {
            if(objectCanBeNull.getClass().isArray()) {
                ja = new JSONArray(objectCanBeNull);
            } else {
                ja = (JSONArray) objectCanBeNull;
            }
            int jaLength = ja.length();
            // don't use the new iterator API to maintain support for Android
			for (int i = 0; i < jaLength; i++) {
                Object value = ja.opt(i);
                // XML does not have good support for arrays. If an array
                // appears in a place where XML is lacking, synthesize an
                // <array> element.
                sb.append(XML.toString(value, tagNameCanBeNull == null ? "array" : tagNameCanBeNull, config, indentFactor, indent));
            }
            return sb.toString();
        }


        string = (objectCanBeNull == null) ? "null" : XML.escape(objectCanBeNull.toString());

        if(tagNameCanBeNull == null){
            return XML.indent(indent) + CommonSeps.getInstance().QUOTE + string + CommonSeps.getInstance().QUOTE + ((indentFactor > 0) ? CommonSeps.getInstance().NEW_LINE : StringUtil.getInstance().EMPTY_STRING);
        } else if(string.length() == 0){
            return XML.indent(indent) + "<" + tagNameCanBeNull + "/>" + ((indentFactor > 0) ? CommonSeps.getInstance().NEW_LINE : StringUtil.getInstance().EMPTY_STRING);
        } else {
            return XML.indent(indent) + "<" + tagNameCanBeNull
                    + ">" + string + "</" + tagNameCanBeNull + ">" + ((indentFactor > 0) ? CommonSeps.getInstance().NEW_LINE : StringUtil.getInstance().EMPTY_STRING);
        }
    }

    /**
     * Convert a JSONObject into a well-formed, pretty printed element-normal XML string.
     *
     * @param object
     *            A JSONObject.
     * @param indentFactor
     *            The number of spaces to add to each level of indentation.
     * @return A string.
     * @throws JSONException Thrown if there is an error parsing the string
     */
    public static String toString(Object objectCanBeNull, int indentFactor){
        return XML.toString(objectCanBeNull, null, XMLParserConfiguration.ORIGINAL, indentFactor);
    }

    /**
     * Convert a JSONObject into a well-formed, pretty printed element-normal XML string.
     *
     * @param object
     *            A JSONObject.
     * @param tagName
     *            The optional name of the enclosing tag.
     * @param indentFactor
     *            The number of spaces to add to each level of indentation.
     * @return A string.
     * @throws JSONException Thrown if there is an error parsing the string
     */
    public static String toString(final Object objectCanBeNull, final String tagNameCanBeNull, int indentFactor) {
        return XML.toString(objectCanBeNull, tagNameCanBeNull, XMLParserConfiguration.ORIGINAL, indentFactor);
    }

    /**
     * Convert a JSONObject into a well-formed, pretty printed element-normal XML string.
     *
     * @param object
     *            A JSONObject.
     * @param tagName
     *            The optional name of the enclosing tag.
     * @param config
     *            Configuration that can control output to XML.
     * @param indentFactor
     *            The number of spaces to add to each level of indentation.
     * @return A string.
     * @throws JSONException Thrown if there is an error parsing the string
     */
    public static String toString(final Object objectCanBeNull, final String tagNameCanBeNull, final XMLParserConfiguration config, int indentFactor)
            throws JSONException {
        return XML.toString(objectCanBeNull, tagNameCanBeNull, config, indentFactor, 0);
    }

    /**
     * Return a String consisting of a number of space characters specified by indent
     *
     * @param indent
     *          The number of spaces to be appended to the String.
     * @return
     */
    private static final String indent(int indent) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < indent; i++) {
            sb.append(' ');
        }
        return sb.toString();
    }
}
