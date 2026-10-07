package org.json;

import org.allbinary.logic.string.StringUtil;

/*
Public Domain.
*/

/**
 * This provides static methods to convert an XML text into a JSONArray or
 * JSONObject, and to covert a JSONArray or JSONObject into an XML text using
 * the JsonML transform.
 *
 * @author JSON.org
 * @version 2016-01-30
 */
public class JSONML {
    /**
     * Parse XML values and store them in a JSONArray.
     * @param x       The XMLTokener containing the source string.
     * @param arrayForm true if array form, false if object form.
     * @param ja      The JSONArray that is containing the current tag or null
     *     if we are at the outermost level.
     * @param keepStrings	Don't type-convert text nodes and attribute values
     * @return A JSONArray if the value is the outermost tag, otherwise null.
     * @throws JSONException if a parsing error occurs
     */
    private static Object parse(
        XMLTokener x,
        boolean    arrayForm,
        JSONArray  jaCanBeNull,
        boolean keepStrings,
        int currentNestingDepth
    ) throws JSONException {
        final Object resultCanBeNull = JSONML.parse(x,arrayForm, jaCanBeNull,
            keepStrings ? JSONMLParserConfiguration.KEEP_STRINGS : JSONMLParserConfiguration.ORIGINAL,
            currentNestingDepth);
        return resultCanBeNull;
    }

    /**
     * Parse XML values and store them in a JSONArray.
     * @param x       The XMLTokener containing the source string.
     * @param arrayForm true if array form, false if object form.
     * @param ja      The JSONArray that is containing the current tag or null
     *     if we are at the outermost level.
     * @param config  The parser configuration:
     *     JSONMLParserConfiguration.ORIGINAL is the default behaviour;
     *     JSONMLParserConfiguration.KEEP_STRINGS means Don't type-convert text nodes and attribute values.
     * @return A JSONArray if the value is the outermost tag, otherwise null.
     * @throws JSONException if a parsing error occurs
     */
    private static Object parse(
        XMLTokener x,
        boolean    arrayForm,
        JSONArray  jaCanBeNull,
        JSONMLParserConfiguration config,
        int currentNestingDepth
    ) throws JSONException {
        String     attribute;
        char       c;
        String     closeTagCanBeNull = null;
        int        i;
        JSONArray  newja = new JSONArray();
        JSONObject newjo = new JSONObject();
        Object     tokenCanBeNull;
        String     tagName = StringUtil.getInstance().EMPTY_STRING;

// Test for and skip past these forms:
//      <!-- ... -->
//      <![  ... ]]>
//      <!   ...   >
//      <?   ...  ?>

       while (true) {
            if (!x.more()) {
                throw x.syntaxError("Bad XML");
            }
            tokenCanBeNull = x.nextContent();
            if (tokenCanBeNull == XML.LT) {
                tokenCanBeNull = x.nextToken();
                if (tokenCanBeNull instanceof Character) {
                    if (tokenCanBeNull == XML.SLASH) {

// Close tag </

                        tokenCanBeNull = x.nextToken();
                        if (!(tokenCanBeNull instanceof String)) {
                            throw new JSONException(
                                    "Expected a closing name instead of '" +
                                    tokenCanBeNull + "'.");
                        }
                        if (x.nextToken() != XML.GT) {
                            throw x.syntaxError("Misshaped close tag");
                        }
                        return tokenCanBeNull;
                    } else if (tokenCanBeNull == XML.BANG) {

// <!

                        c = x.next();
                        if (c == '-') {
                            if (x.next() == '-') {
                                x.skipPast("-->");
                            } else {
                                x.back();
                            }
                        } else if (c == '[') {
                            tokenCanBeNull = x.nextToken();
                            if (tokenCanBeNull.equals("CDATA") && x.next() == '[') {
                                if (jaCanBeNull != null) {
                                    jaCanBeNull.put(x.nextCDATA());
                                }
                            } else {
                                throw x.syntaxError("Expected 'CDATA['");
                            }
                        } else {
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
                        }
                    } else if (tokenCanBeNull == XML.QUEST) {

// <?

                        x.skipPast("?>");
                    } else {
                        throw x.syntaxError("Misshaped tag");
                    }

// Open tag <

                } else {
                    if (!(tokenCanBeNull instanceof String)) {
                        throw x.syntaxError("Bad tagName '" + tokenCanBeNull + "'.");
                    }
                    tagName = (String)tokenCanBeNull;
                    newja = new JSONArray();
                    newjo = new JSONObject();
                    if (arrayForm) {
                        newja.put(tagName);
                        if (jaCanBeNull != null) {
                            jaCanBeNull.put(newja);
                        }
                    } else {
                        newjo.put("tagName", tagName);
                        if (jaCanBeNull != null) {
                            jaCanBeNull.put(newjo);
                        }
                    }
                    tokenCanBeNull = null;
                    while (true) {
                        if (tokenCanBeNull == null) {
                            tokenCanBeNull = x.nextToken();
                        }
                        if (tokenCanBeNull == null) {
                            throw x.syntaxError("Misshaped tag");
                        }
                        if (!(tokenCanBeNull instanceof String)) {
                            break;
                        }

// attribute = value

                        attribute = (String)tokenCanBeNull;
                        if (!arrayForm && ("tagName".equals(attribute) || "childNode".equals(attribute))) {
                            throw x.syntaxError("Reserved attribute.");
                        }
                        tokenCanBeNull = x.nextToken();
                        if (tokenCanBeNull == XML.EQ) {
                            tokenCanBeNull = x.nextToken();
                            if (!(tokenCanBeNull instanceof String)) {
                                throw x.syntaxError("Missing value");
                            }
                            newjo.accumulate(attribute, config.isKeepStrings() ? ((String)tokenCanBeNull) :XML.stringToValue((String)tokenCanBeNull));
                            tokenCanBeNull = null;
                        } else {
                            newjo.accumulate(attribute, StringUtil.getInstance().EMPTY_STRING);
                        }
                    }
                    if (arrayForm && newjo.length() > 0) {
                        newja.put(newjo);
                    }

// Empty tag <.../>

                    if (tokenCanBeNull == XML.SLASH) {
                        if (x.nextToken() != XML.GT) {
                            throw x.syntaxError("Misshaped tag");
                        }
                        if (jaCanBeNull == null) {
                            if (arrayForm) {
                                return newja;
                            }
                            return newjo;
                        }

// Content, between <...> and </...>

                    } else {
                        if (tokenCanBeNull != XML.GT) {
                            throw x.syntaxError("Misshaped tag");
                        }

                        if (currentNestingDepth == config.getMaxNestingDepth()) {
                            throw x.syntaxError("Maximum nesting depth of " + config.getMaxNestingDepth() + " reached");
                        }

                        closeTagCanBeNull = (String)JSONML.parse(x, arrayForm, newja, config, currentNestingDepth + 1);
                        if (closeTagCanBeNull != null) {
                            if (!closeTagCanBeNull.equals(tagName)) {
                                throw x.syntaxError("Mismatched '" + tagName +
                                        "' and '" + closeTagCanBeNull + "'");
                            }
                            tagName = StringUtil.getInstance().EMPTY_STRING;
                            if (!arrayForm && newja.length() > 0) {
                                newjo.put("childNodes", newja);
                            }
                            if (jaCanBeNull == null) {
                                if (arrayForm) {
                                    return newja;
                                }
                                return newjo;
                            }
                        }
                    }
                }
            } else {
                if (jaCanBeNull != null) {
                    jaCanBeNull.put(tokenCanBeNull instanceof String
                        ? (config.isKeepStrings() ? XML.unescape((String)tokenCanBeNull) : XML.stringToValue((String)tokenCanBeNull))
                        : (tokenCanBeNull == null ? JSONObject.NULL : tokenCanBeNull));
                }
            }
        }
    }


    /**
     * Convert a well-formed (but not necessarily valid) XML string into a
     * JSONArray using the JsonML transform. Each XML tag is represented as
     * a JSONArray in which the first element is the tag name. If the tag has
     * attributes, then the second element will be JSONObject containing the
     * name/value pairs. If the tag contains children, then strings and
     * JSONArrays will represent the child tags.
     * Comments, prologs, DTDs, and <pre>{@code &lt;[ [ ]]>}</pre> are ignored.
     * @param string The source string.
     * @return A JSONArray containing the structured data from the XML string.
     * @throws JSONException Thrown on error converting to a JSONArray
     */
    public static JSONArray toJSONArray(String string) throws JSONException {
        return (JSONArray)JSONML.parse(new XMLTokener(string), true, null, JSONMLParserConfiguration.ORIGINAL, 0);
    }


    /**
     * Convert a well-formed (but not necessarily valid) XML string into a
     * JSONArray using the JsonML transform. Each XML tag is represented as
     * a JSONArray in which the first element is the tag name. If the tag has
     * attributes, then the second element will be JSONObject containing the
     * name/value pairs. If the tag contains children, then strings and
     * JSONArrays will represent the child tags.
     * As opposed to toJSONArray this method does not attempt to convert
     * any text node or attribute value to any type
     * but just leaves it as a string.
     * Comments, prologs, DTDs, and <pre>{@code &lt;[ [ ]]>}</pre> are ignored.
     * @param string The source string.
     * @param keepStrings If true, then values will not be coerced into boolean
     *  or numeric values and will instead be left as strings
     * @return A JSONArray containing the structured data from the XML string.
     * @throws JSONException Thrown on error converting to a JSONArray
     */
    public static JSONArray toJSONArray(String string, boolean keepStrings) throws JSONException {
        return (JSONArray)JSONML.parse(new XMLTokener(string), true, null, keepStrings, 0);
    }



    /**
     * Convert a well-formed (but not necessarily valid) XML string into a
     * JSONArray using the JsonML transform. Each XML tag is represented as
     * a JSONArray in which the first element is the tag name. If the tag has
     * attributes, then the second element will be JSONObject containing the
     * name/value pairs. If the tag contains children, then strings and
     * JSONArrays will represent the child tags.
     * As opposed to toJSONArray this method does not attempt to convert
     * any text node or attribute value to any type
     * but just leaves it as a string.
     * Comments, prologs, DTDs, and <pre>{@code &lt;[ [ ]]>}</pre> are ignored.
     * @param string The source string.
     * @param config  The parser configuration:
     *     JSONMLParserConfiguration.ORIGINAL is the default behaviour;
     *     JSONMLParserConfiguration.KEEP_STRINGS means values will not be coerced into boolean
     *       or numeric values and will instead be left as strings
     * @return A JSONArray containing the structured data from the XML string.
     * @throws JSONException Thrown on error converting to a JSONArray
     */
    public static JSONArray toJSONArray(String string, JSONMLParserConfiguration config) throws JSONException {
        return (JSONArray)JSONML.parse(new XMLTokener(string), true, null, config, 0);
    }


    /**
     * Convert a well-formed (but not necessarily valid) XML string into a
     * JSONArray using the JsonML transform. Each XML tag is represented as
     * a JSONArray in which the first element is the tag name. If the tag has
     * attributes, then the second element will be JSONObject containing the
     * name/value pairs. If the tag contains children, then strings and
     * JSONArrays will represent the child content and tags.
     * As opposed to toJSONArray this method does not attempt to convert
     * any text node or attribute value to any type
     * but just leaves it as a string.
     * Comments, prologs, DTDs, and <pre>{@code &lt;[ [ ]]>}</pre> are ignored.
     * @param x An XMLTokener.
     * @param config  The parser configuration:
     *     JSONMLParserConfiguration.ORIGINAL is the default behaviour;
     *     JSONMLParserConfiguration.KEEP_STRINGS means values will not be coerced into boolean
     *       or numeric values and will instead be left as strings
     * @return A JSONArray containing the structured data from the XML string.
     * @throws JSONException Thrown on error converting to a JSONArray
     */
    public static JSONArray toJSONArray(XMLTokener x, JSONMLParserConfiguration config) throws JSONException {
        return (JSONArray)JSONML.parse(x, true, null, config, 0);
    }


    /**
     * Convert a well-formed (but not necessarily valid) XML string into a
     * JSONArray using the JsonML transform. Each XML tag is represented as
     * a JSONArray in which the first element is the tag name. If the tag has
     * attributes, then the second element will be JSONObject containing the
     * name/value pairs. If the tag contains children, then strings and
     * JSONArrays will represent the child content and tags.
     * As opposed to toJSONArray this method does not attempt to convert
     * any text node or attribute value to any type
     * but just leaves it as a string.
     * Comments, prologs, DTDs, and <pre>{@code &lt;[ [ ]]>}</pre> are ignored.
     * @param x An XMLTokener.
     * @param keepStrings If true, then values will not be coerced into boolean
     *  or numeric values and will instead be left as strings
     * @return A JSONArray containing the structured data from the XML string.
     * @throws JSONException Thrown on error converting to a JSONArray
     */
    public static JSONArray toJSONArray(XMLTokener x, boolean keepStrings) throws JSONException {
        return (JSONArray)JSONML.parse(x, true, null, keepStrings, 0);
    }


    /**
     * Convert a well-formed (but not necessarily valid) XML string into a
     * JSONArray using the JsonML transform. Each XML tag is represented as
     * a JSONArray in which the first element is the tag name. If the tag has
     * attributes, then the second element will be JSONObject containing the
     * name/value pairs. If the tag contains children, then strings and
     * JSONArrays will represent the child content and tags.
     * Comments, prologs, DTDs, and <pre>{@code &lt;[ [ ]]>}</pre> are ignored.
     * @param x An XMLTokener.
     * @return A JSONArray containing the structured data from the XML string.
     * @throws JSONException Thrown on error converting to a JSONArray
     */
    public static JSONArray toJSONArray(XMLTokener x) throws JSONException {
        return (JSONArray)JSONML.parse(x, true, null, false, 0);
    }


    /**
     * Convert a well-formed (but not necessarily valid) XML string into a
     * JSONObject using the JsonML transform. Each XML tag is represented as
     * a JSONObject with a "tagName" property. If the tag has attributes, then
     * the attributes will be in the JSONObject as properties. If the tag
     * contains children, the object will have a "childNodes" property which
     * will be an array of strings and JsonML JSONObjects.

     * Comments, prologs, DTDs, and <pre>{@code &lt;[ [ ]]>}</pre> are ignored.
     * @param string The XML source text.
     * @return A JSONObject containing the structured data from the XML string.
     * @throws JSONException Thrown on error converting to a JSONObject
     */
    public static JSONObject toJSONObject(String string) throws JSONException {
        return (JSONObject)JSONML.parse(new XMLTokener(string), false, null, false, 0);
    }


    /**
     * Convert a well-formed (but not necessarily valid) XML string into a
     * JSONObject using the JsonML transform. Each XML tag is represented as
     * a JSONObject with a "tagName" property. If the tag has attributes, then
     * the attributes will be in the JSONObject as properties. If the tag
     * contains children, the object will have a "childNodes" property which
     * will be an array of strings and JsonML JSONObjects.

     * Comments, prologs, DTDs, and <pre>{@code &lt;[ [ ]]>}</pre> are ignored.
     * @param string The XML source text.
     * @param keepStrings If true, then values will not be coerced into boolean
     *  or numeric values and will instead be left as strings
     * @return A JSONObject containing the structured data from the XML string.
     * @throws JSONException Thrown on error converting to a JSONObject
     */
    public static JSONObject toJSONObject(String string, boolean keepStrings) throws JSONException {
        return (JSONObject)JSONML.parse(new XMLTokener(string), false, null, keepStrings, 0);
    }


    /**
     * Convert a well-formed (but not necessarily valid) XML string into a
     * JSONObject using the JsonML transform. Each XML tag is represented as
     * a JSONObject with a "tagName" property. If the tag has attributes, then
     * the attributes will be in the JSONObject as properties. If the tag
     * contains children, the object will have a "childNodes" property which
     * will be an array of strings and JsonML JSONObjects.

     * Comments, prologs, DTDs, and <pre>{@code &lt;[ [ ]]>}</pre> are ignored.
     * @param string The XML source text.
     * @param config  The parser configuration:
     *     JSONMLParserConfiguration.ORIGINAL is the default behaviour;
     *     JSONMLParserConfiguration.KEEP_STRINGS means values will not be coerced into boolean
     *       or numeric values and will instead be left as strings
     * @return A JSONObject containing the structured data from the XML string.
     * @throws JSONException Thrown on error converting to a JSONObject
     */
    public static JSONObject toJSONObject(String string, JSONMLParserConfiguration config) throws JSONException {
        return (JSONObject)JSONML.parse(new XMLTokener(string), false, null, config, 0);
    }


    /**
     * Convert a well-formed (but not necessarily valid) XML string into a
     * JSONObject using the JsonML transform. Each XML tag is represented as
     * a JSONObject with a "tagName" property. If the tag has attributes, then
     * the attributes will be in the JSONObject as properties. If the tag
     * contains children, the object will have a "childNodes" property which
     * will be an array of strings and JsonML JSONObjects.

     * Comments, prologs, DTDs, and <pre>{@code &lt;[ [ ]]>}</pre> are ignored.
     * @param x An XMLTokener of the XML source text.
     * @return A JSONObject containing the structured data from the XML string.
     * @throws JSONException Thrown on error converting to a JSONObject
     */
    public static JSONObject toJSONObject(XMLTokener x) throws JSONException {
           return (JSONObject)JSONML.parse(x, false, null, false, 0);
    }


    /**
     * Convert a well-formed (but not necessarily valid) XML string into a
     * JSONObject using the JsonML transform. Each XML tag is represented as
     * a JSONObject with a "tagName" property. If the tag has attributes, then
     * the attributes will be in the JSONObject as properties. If the tag
     * contains children, the object will have a "childNodes" property which
     * will be an array of strings and JsonML JSONObjects.

     * Comments, prologs, DTDs, and <pre>{@code &lt;[ [ ]]>}</pre> are ignored.
     * @param x An XMLTokener of the XML source text.
     * @param keepStrings If true, then values will not be coerced into boolean
     *  or numeric values and will instead be left as strings
     * @return A JSONObject containing the structured data from the XML string.
     * @throws JSONException Thrown on error converting to a JSONObject
     */
    public static JSONObject toJSONObject(XMLTokener x, boolean keepStrings) throws JSONException {
           return (JSONObject)JSONML.parse(x, false, null, keepStrings, 0);
    }


    /**
     * Convert a well-formed (but not necessarily valid) XML string into a
     * JSONObject using the JsonML transform. Each XML tag is represented as
     * a JSONObject with a "tagName" property. If the tag has attributes, then
     * the attributes will be in the JSONObject as properties. If the tag
     * contains children, the object will have a "childNodes" property which
     * will be an array of strings and JsonML JSONObjects.

     * Comments, prologs, DTDs, and <pre>{@code &lt;[ [ ]]>}</pre> are ignored.
     * @param x An XMLTokener of the XML source text.
     * @param config  The parser configuration:
     *     JSONMLParserConfiguration.ORIGINAL is the default behaviour;
     *     JSONMLParserConfiguration.KEEP_STRINGS means values will not be coerced into boolean
     *       or numeric values and will instead be left as strings
     * @return A JSONObject containing the structured data from the XML string.
     * @throws JSONException Thrown on error converting to a JSONObject
     */
    public static JSONObject toJSONObject(XMLTokener x, JSONMLParserConfiguration config) throws JSONException {
        return (JSONObject)JSONML.parse(x, false, null, config, 0);
    }


    /**
     * Reverse the JSONML transformation, making an XML text from a JSONArray.
     * @param ja A JSONArray.
     * @return An XML string.
     * @throws JSONException Thrown on error converting to a string
     */
    public static String toString(JSONArray ja) throws JSONException {
        int                 i;
        JSONObject          jo;
        int                 length;
        Object              objectCanBeNull;
        StringBuilder        sb = new StringBuilder();
        String              tagName;

// Emit <tagName

        tagName = ja.getString(0);
        XML.noSpace(tagName);
        tagName = XML.escape(tagName);
        sb.append('<');
        sb.append(tagName);

        objectCanBeNull = ja.opt(1);
        if (objectCanBeNull instanceof JSONObject) {
            i = 2;
            jo = (JSONObject)objectCanBeNull;

// Emit the attributes

            // Don't use the new entrySet API to maintain Android support
            for (final String key : jo.keySet()) {
                final Object valueCanBeNull = jo.opt(key);
                XML.noSpace(key);
                if (valueCanBeNull != null) {
                    sb.append(' ');
                    sb.append(XML.escape(key));
                    sb.append('=');
                    sb.append('"');
                    sb.append(XML.escape(valueCanBeNull.toString()));
                    sb.append('"');
                }
            }
        } else {
            i = 1;
        }

// Emit content in body

        length = ja.length();
        if (i >= length) {
            sb.append('/');
            sb.append('>');
        } else {
            sb.append('>');
            do {
                objectCanBeNull = ja.get(i);
                i += 1;
                if (objectCanBeNull != null) {
                    if (objectCanBeNull instanceof String) {
                        sb.append(XML.escape(objectCanBeNull.toString()));
                    } else if (objectCanBeNull instanceof JSONObject) {
                        sb.append(JSONML.toString((JSONObject)objectCanBeNull));
                    } else if (objectCanBeNull instanceof JSONArray) {
                        sb.append(JSONML.toString((JSONArray)objectCanBeNull));
                    } else {
                        sb.append(objectCanBeNull.toString());
                    }
                }
            } while (i < length);
            sb.append('<');
            sb.append('/');
            sb.append(tagName);
            sb.append('>');
        }
        return sb.toString();
    }


    /**
     * Reverse the JSONML transformation, making an XML text from a JSONObject.
     * The JSONObject must contain a "tagName" property. If it has children,
     * then it must have a "childNodes" property containing an array of objects.
     * The other properties are attributes with string values.
     * @param jo A JSONObject.
     * @return An XML string.
     * @throws JSONException Thrown on error converting to a string
     */
    public static String toString(JSONObject jo) throws JSONException {
        StringBuilder sb = new StringBuilder();
        int                 i;
        JSONArray           jaCanBeNull;
        int                 length;
        Object              objectCanBeNull;
        String              tagName;
        Object              valueCanBeNull;

//Emit <tagName

        tagName = jo.optString("tagName");
        if (tagName == null) {
            return XML.escape(jo.toString());
        }
        XML.noSpace(tagName);
        tagName = XML.escape(tagName);
        sb.append('<');
        sb.append(tagName);

//Emit the attributes

        // Don't use the new entrySet API to maintain Android support
        for (final String key : jo.keySet()) {
            if (!"tagName".equals(key) && !"childNodes".equals(key)) {
                XML.noSpace(key);
                valueCanBeNull = jo.opt(key);
                if (valueCanBeNull != null) {
                    sb.append(' ');
                    sb.append(XML.escape(key));
                    sb.append('=');
                    sb.append('"');
                    sb.append(XML.escape(valueCanBeNull.toString()));
                    sb.append('"');
                }
            }
        }

//Emit content in body

        jaCanBeNull = jo.optJSONArray("childNodes");
        if (jaCanBeNull == null) {
            sb.append('/');
            sb.append('>');
        } else {
            sb.append('>');
            length = jaCanBeNull.length();
            for (i = 0; i < length; i += 1) {
                objectCanBeNull = jaCanBeNull.get(i);
                if (objectCanBeNull != null) {
                    if (objectCanBeNull instanceof String) {
                        sb.append(XML.escape(objectCanBeNull.toString()));
                    } else if (objectCanBeNull instanceof JSONObject) {
                        sb.append(JSONML.toString((JSONObject)objectCanBeNull));
                    } else if (objectCanBeNull instanceof JSONArray) {
                        sb.append(JSONML.toString((JSONArray)objectCanBeNull));
                    } else {
                        sb.append(objectCanBeNull.toString());
                    }
                }
            }
            sb.append('<');
            sb.append('/');
            sb.append(tagName);
            sb.append('>');
        }
        return sb.toString();
    }
}
