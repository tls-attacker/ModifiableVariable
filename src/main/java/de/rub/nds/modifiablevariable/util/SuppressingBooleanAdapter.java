/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.util;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import java.util.Objects;

/**
 * Abstract base adapter for boolean values that suppresses specific boolean values in XML.
 *
 * <p>This adapter provides a mechanism to omit specific boolean values (true or false) from the
 * serialized XML, making the XML more concise by not including elements with default values. The
 * specific value to suppress is determined by concrete subclasses.
 *
 * <p>When unmarshaling (reading XML), if the element is missing or null, the adapter returns the
 * value that was suppressed during marshaling. This ensures that even if a value is not present in
 * XML, the correct default value is used.
 *
 * <p>When marshaling (writing XML), if the boolean value matches the value to suppress, the adapter
 * returns null, which causes JAXB to omit the element from the XML output entirely.
 *
 * <p>This pattern is useful for reducing XML verbosity by omitting default values. For example, if
 * a field defaults to true, using {@link SuppressingTrueBooleanAdapter} will only include the field
 * in XML if its value is false.
 */
public abstract class SuppressingBooleanAdapter extends XmlAdapter<String, Boolean> {

    /**
     * Returns the boolean value that should be suppressed in the XML representation.
     *
     * <p>This method must be implemented by concrete subclasses to specify which value (true or
     * false) should be omitted from the XML output.
     *
     * @return The boolean value to suppress in XML
     */
    public abstract Boolean getValueToSuppress();

    /**
     * Converts a string representation to a Boolean value.
     *
     * <p>If the input is null (meaning the element was missing from the XML), this method returns
     * the value that was suppressed during marshaling. Otherwise, it parses the string as a boolean
     * value.
     *
     * @param v The string representation to convert
     * @return The corresponding Boolean value, or the suppressed value if v is null
     */
    @Override
    public Boolean unmarshal(String v) {
        if (v == null) {
            return getValueToSuppress();
        } else {
            return Boolean.parseBoolean(v);
        }
    }

    /**
     * Converts a Boolean value to its string representation for XML.
     *
     * <p>If the input value matches the value to suppress (or is null), this method returns null,
     * which causes JAXB to omit the element from the XML output entirely. Otherwise, it returns the
     * string representation of the boolean value.
     *
     * @param v The Boolean value to convert
     * @return The string representation, or null if the value should be suppressed
     */
    @Override
    public String marshal(Boolean v) {
        if (Objects.equals(v, getValueToSuppress()) || v == null) {
            return null;
        }
        return v.toString();
    }
}
