/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.util;

/**
 * XML adapter that suppresses false boolean values in XML serialization.
 * 
 * <p>This concrete implementation of {@link SuppressingBooleanAdapter} specifically
 * suppresses the {@code false} value when marshaling to XML. This means that boolean
 * fields with a value of {@code false} will be omitted from the XML output, making
 * the XML more concise when {@code false} is the default or common value.
 * 
 * <p>When unmarshaling XML, if an element is missing, this adapter will interpret
 * that as a {@code false} value.
 * 
 * <p>Usage example:
 * <pre>{@code
 * public class MyClass {
 *     @XmlElement
 *     @XmlJavaTypeAdapter(SuppressingFalseBooleanAdapter.class)
 *     private Boolean disabled = false; // This will not appear in XML unless true
 * }
 * }</pre>
 * 
 * <p>With this adapter, in the example above, the "disabled" element will only
 * appear in the XML if its value is {@code true}. If it's {@code false} (the default),
 * the element will be omitted, resulting in more compact XML.
 * 
 * <p>This adapter is complementary to {@link SuppressingTrueBooleanAdapter}, which
 * suppresses {@code true} values instead.
 */
public class SuppressingFalseBooleanAdapter extends SuppressingBooleanAdapter {

    /**
     * Returns the boolean value to suppress in the XML representation.
     * 
     * <p>This implementation suppresses the {@code false} value.
     *
     * @return {@code Boolean.FALSE}
     */
    @Override
    public Boolean getValueToSuppress() {
        return Boolean.FALSE;
    }
}
