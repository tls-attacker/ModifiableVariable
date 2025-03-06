/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.util;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import org.apache.commons.text.StringEscapeUtils;

/**
 * XML adapter for handling strings that may contain illegal or escape characters.
 * 
 * <p>This adapter provides a mechanism to safely include strings containing special characters,
 * control characters, or characters that would be illegal in XML in JAXB-serialized XML documents.
 * It works by escaping such characters using Java string escape sequences when writing to XML,
 * and unescaping them when reading from XML.
 * 
 * <p>The adapter uses Apache Commons Text's {@link StringEscapeUtils} to perform
 * the escaping and unescaping. This handles escaping of common special characters like:
 * <ul>
 *   <li>Control characters (e.g., \n, \r, \t)</li>
 *   <li>Non-printable characters</li>
 *   <li>Unicode characters</li>
 *   <li>Characters that have special meaning in XML (e.g., &lt;, &gt;, &amp;)</li>
 * </ul>
 * 
 * <p>Usage example:
 * <pre>{@code
 * public class MyClass {
 *     @XmlElement
 *     @XmlJavaTypeAdapter(IllegalStringAdapter.class)
 *     private String data = "Line 1\nLine 2"; // Contains a newline that needs escaping
 * }
 * }</pre>
 * 
 * <p>With this adapter, the string will be properly escaped in XML, allowing for
 * safe serialization and deserialization of strings containing special characters.
 */
public class IllegalStringAdapter extends XmlAdapter<String, String> {

    /**
     * Converts an escaped string representation from XML back to its original form.
     * 
     * <p>This method unescapes Java escape sequences to restore the original string
     * with its special characters.
     *
     * @param value The escaped string to convert
     * @return The unescaped string
     */
    @Override
    public String unmarshal(String value) {
        return StringEscapeUtils.unescapeJava(value);
    }

    /**
     * Converts a string to an escaped representation for XML.
     * 
     * <p>This method escapes special characters using Java escape sequences
     * to ensure they can be safely included in XML.
     *
     * @param value The string to escape
     * @return The escaped string
     */
    @Override
    public String marshal(String value) {
        return StringEscapeUtils.escapeJava(value);
    }
}
