/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.string;

import static de.rub.nds.modifiablevariable.util.StringUtil.backslashEscapeString;

import de.rub.nds.modifiablevariable.ModifiableVariable;
import de.rub.nds.modifiablevariable.util.IllegalStringAdapter;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.nio.charset.StandardCharsets;

/**
 * A modifiable variable implementation for String values.
 *
 * <p>This class extends {@link ModifiableVariable} to provide runtime modification capabilities for
 * String values. It supports various string-specific modifications such as appending, prepending,
 * inserting and deleting.
 *
 * <p>This class uses property-based XML access and the {@link IllegalStringAdapter} to handle
 * proper serialization of strings that might contain characters that are problematic in XML.
 *
 * @see ModifiableVariable
 * @see de.rub.nds.modifiablevariable.string.StringAppendValueModification
 * @see de.rub.nds.modifiablevariable.string.StringPrependValueModification
 * @see de.rub.nds.modifiablevariable.string.StringInsertValueModification
 * @see de.rub.nds.modifiablevariable.string.StringDeleteModification
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class ModifiableString extends ModifiableVariable<String> {

    /** The original string value before any modifications */
    protected String originalValue;

    /** Default constructor that creates an empty ModifiableString with no original value. */
    public ModifiableString() {
        super();
    }

    /**
     * Constructor that creates a ModifiableString with the specified original value.
     *
     * @param originalValue The original string value
     */
    public ModifiableString(String originalValue) {
        super();
        this.originalValue = originalValue;
    }

    /**
     * Copy constructor that creates a new ModifiableString with the same original value and
     * modifications as the provided instance.
     *
     * @param other The ModifiableString to copy
     */
    public ModifiableString(ModifiableString other) {
        super(other);
        originalValue = other.originalValue;
    }

    /**
     * Creates a deep copy of this ModifiableString.
     *
     * @return A new ModifiableString instance with the same properties
     */
    @Override
    public ModifiableString createCopy() {
        return new ModifiableString(this);
    }

    /**
     * Gets the expected value for assertion validation.
     *
     * <p>Uses a special XML adapter to handle strings with characters that might be problematic in
     * XML serialization.
     *
     * @return The assertion value
     */
    @XmlJavaTypeAdapter(IllegalStringAdapter.class)
    public String getAssertEquals() {
        return assertEquals;
    }

    /**
     * Sets the expected value for assertion validation.
     *
     * @param assertEquals The expected string value
     */
    public void setAssertEquals(String assertEquals) {
        this.assertEquals = assertEquals;
    }

    /**
     * Checks if the modified value differs from the original value.
     *
     * <p>This method compares strings using their natural ordering via the compareTo method.
     *
     * @return true if the value has been modified, false otherwise
     */
    @Override
    public boolean isOriginalValueModified() {
        return originalValue != null && originalValue.compareTo(getValue()) != 0;
    }

    /**
     * Converts the string value to a byte array using ISO-8859-1 encoding.
     *
     * @return The byte array representation of the string using ISO-8859-1 encoding
     */
    public byte[] getByteArray() {
        return getValue().getBytes(StandardCharsets.ISO_8859_1);
    }

    /**
     * Validates whether the modified value matches the expected value (if set).
     *
     * <p>This method compares strings using their natural ordering via the compareTo method.
     *
     * @return true if no assertion is set or if the current value equals the expected value
     */
    @Override
    public boolean validateAssertions() {
        boolean valid = true;
        if (assertEquals != null) {
            if (assertEquals.compareTo(getValue()) != 0) {
                valid = false;
            }
        }
        return valid;
    }

    /**
     * Gets the original, unmodified string value.
     *
     * <p>Uses a special XML adapter to handle strings with characters that might be problematic in
     * XML serialization.
     *
     * @return The original value
     */
    @Override
    @XmlJavaTypeAdapter(IllegalStringAdapter.class)
    public String getOriginalValue() {
        return originalValue;
    }

    /**
     * Sets the original string value.
     *
     * @param originalValue The new original value
     */
    @Override
    public void setOriginalValue(String originalValue) {
        this.originalValue = originalValue;
    }

    /**
     * Returns a string representation of this ModifiableString.
     *
     * <p>The original value is escaped using backslash escaping to make control characters and
     * other special characters readable.
     *
     * @return A string containing the escaped original value and modifications
     */
    @Override
    public String toString() {
        return "ModifiableString{"
                + "originalValue='"
                + backslashEscapeString(originalValue)
                + '\''
                + innerToString()
                + '}';
    }

    /**
     * Checks if this ModifiableString is equal to another object. Two ModifiableString instances
     * are considered equal if they have the same modified value.
     *
     * @param obj The object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ModifiableString that)) {
            return false;
        }

        return getValue() != null ? getValue().equals(that.getValue()) : that.getValue() == null;
    }

    /**
     * Computes a hash code for this ModifiableString. The hash code is based on the modified value
     * rather than the original value.
     *
     * @return The hash code value
     */
    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (getValue() != null ? getValue().hashCode() : 0);
        return result;
    }
}
