/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.util;

/**
 * XML adapter that suppresses true boolean values in XML serialization.
 *
 * <p>This concrete implementation of {@link SuppressingBooleanAdapter} specifically suppresses the
 * {@code true} value when marshaling to XML. This means that boolean fields with a value of {@code
 * true} will be omitted from the XML output, making the XML more concise when {@code true} is the
 * default or common value.
 *
 * <p>When unmarshaling XML, if an element is missing, this adapter will interpret that as a {@code
 * true} value.
 *
 * <p>Usage example:
 *
 * <pre>{@code
 * public class MyClass {
 *     @XmlElement
 *     @XmlJavaTypeAdapter(SuppressingTrueBooleanAdapter.class)
 *     private Boolean enabled = true; // This will not appear in XML unless false
 * }
 * }</pre>
 *
 * <p>With this adapter, in the example above, the "enabled" element will only appear in the XML if
 * its value is {@code false}. If it's {@code true} (the default), the element will be omitted,
 * resulting in more compact XML.
 */
public class SuppressingTrueBooleanAdapter extends SuppressingBooleanAdapter {

    /**
     * Creates a new adapter that suppresses true boolean values in XML.
     *
     * <p>This constructor initializes an adapter that will omit the true value from XML output,
     * making true the default value when an element is missing.
     */
    public SuppressingTrueBooleanAdapter() {
        super();
    }

    /**
     * Returns the boolean value to suppress in the XML representation.
     *
     * <p>This implementation suppresses the {@code true} value.
     *
     * @return {@code Boolean.TRUE}
     */
    @Override
    public Boolean getValueToSuppress() {
        return Boolean.TRUE;
    }
}
