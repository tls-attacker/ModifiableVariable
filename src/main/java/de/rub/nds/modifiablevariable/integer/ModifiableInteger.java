/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.integer;

import de.rub.nds.modifiablevariable.ModifiableVariable;
import de.rub.nds.modifiablevariable.util.ArrayConverter;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * A modifiable variable implementation for Integer values.
 * 
 * <p>This class extends {@link ModifiableVariable} to provide runtime modification
 * capabilities for Integer values. It supports various integer-specific modifications
 * like addition, subtraction, XOR operations, and more.
 */
@XmlRootElement
public class ModifiableInteger extends ModifiableVariable<Integer> {

    /** The original integer value before any modifications */
    private Integer originalValue;

    /**
     * Default constructor that creates an empty ModifiableInteger with no original value.
     */
    public ModifiableInteger() {
        super();
    }

    /**
     * Constructor that creates a ModifiableInteger with the specified original value.
     *
     * @param originalValue The original integer value
     */
    public ModifiableInteger(Integer originalValue) {
        super();
        this.originalValue = originalValue;
    }

    /**
     * Copy constructor that creates a new ModifiableInteger with the same original value
     * and modifications as the provided instance.
     *
     * @param other The ModifiableInteger to copy
     */
    public ModifiableInteger(ModifiableInteger other) {
        super(other);
        originalValue = other.originalValue;
    }

    /**
     * Creates a deep copy of this ModifiableInteger.
     *
     * @return A new ModifiableInteger instance with the same properties
     */
    @Override
    public ModifiableInteger createCopy() {
        return new ModifiableInteger(this);
    }

    /**
     * Gets the expected value for assertion validation.
     *
     * @return The assertion value
     */
    public Integer getAssertEquals() {
        return assertEquals;
    }

    /**
     * Sets the expected value for assertion validation.
     *
     * @param assertEquals The expected integer value
     */
    public void setAssertEquals(Integer assertEquals) {
        this.assertEquals = assertEquals;
    }

    /**
     * Checks if the modified value differs from the original value.
     *
     * @return true if the value has been modified, false otherwise
     * @throws IllegalStateException if the original value is null
     */
    @Override
    public boolean isOriginalValueModified() {
        if (getOriginalValue() == null) {
            throw new IllegalStateException("Original value must not be null");
        } else {
            return getOriginalValue().compareTo(getValue()) != 0;
        }
    }

    /**
     * Converts the integer value to a byte array of the specified size.
     *
     * @param size The size of the resulting byte array
     * @return The byte array representation of the integer
     */
    public byte[] getByteArray(int size) {
        return ArrayConverter.intToBytes(getValue(), size);
    }

    /**
     * Validates whether the modified value matches the expected value (if set).
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
     * Gets the original, unmodified integer value.
     *
     * @return The original value
     */
    @Override
    public Integer getOriginalValue() {
        return originalValue;
    }

    /**
     * Sets the original integer value.
     *
     * @param originalValue The new original value
     */
    @Override
    public void setOriginalValue(Integer originalValue) {
        this.originalValue = originalValue;
    }

    /**
     * Returns a string representation of this ModifiableInteger.
     *
     * @return A string containing the original value and modifications
     */
    @Override
    public String toString() {
        return "ModifiableInteger{" + "originalValue=" + originalValue + innerToString() + '}';
    }

    /**
     * Checks if this ModifiableInteger is equal to another object.
     * Two ModifiableInteger instances are considered equal if they have the same modified value.
     *
     * @param obj The object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ModifiableInteger that)) {
            return false;
        }

        return getValue() != null ? getValue().equals(that.getValue()) : that.getValue() == null;
    }

    /**
     * Computes a hash code for this ModifiableInteger.
     * The hash code is based on the modified value rather than the original value.
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
