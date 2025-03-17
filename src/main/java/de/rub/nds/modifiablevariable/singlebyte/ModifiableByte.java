/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.singlebyte;

import de.rub.nds.modifiablevariable.ModifiableVariable;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * A modifiable variable implementation for single byte values.
 *
 * <p>This class extends {@link ModifiableVariable} to provide runtime modification capabilities for
 * Byte values. It supports various byte-specific modifications such as addition, subtraction, XOR
 * operations, and more.
 * 
 * I many cases it is beneficial to use ModifiableByteArray instead of ModifiableByte, as it allows for
 * more uniform treatment in the code.
 */
@XmlRootElement
public class ModifiableByte extends ModifiableVariable<Byte> {

    /** The original byte value before any modifications */
    private Byte originalValue;

    /** Default constructor that creates an empty ModifiableByte with no original value. */
    public ModifiableByte() {
        super();
    }

    /**
     * Constructor that creates a ModifiableByte with the specified original value.
     *
     * @param originalValue The original byte value
     */
    public ModifiableByte(Byte originalValue) {
        super();
        this.originalValue = originalValue;
    }

    /**
     * Copy constructor that creates a new ModifiableByte with the same original value and
     * modifications as the provided instance.
     *
     * @param other The ModifiableByte to copy
     */
    public ModifiableByte(ModifiableByte other) {
        super(other);
        originalValue = other.originalValue;
    }

    /**
     * Creates a deep copy of this ModifiableByte.
     *
     * @return A new ModifiableByte instance with the same properties
     */
    @Override
    public ModifiableByte createCopy() {
        return new ModifiableByte(this);
    }

    /**
     * Gets the expected value for assertion validation.
     *
     * @return The assertion value
     */
    public Byte getAssertEquals() {
        return assertEquals;
    }

    /**
     * Sets the expected value for assertion validation.
     *
     * @param assertEquals The expected byte value
     */
    public void setAssertEquals(Byte assertEquals) {
        this.assertEquals = assertEquals;
    }

    /**
     * Checks if the modified value differs from the original value.
     *
     * <p>This method compares bytes using their natural ordering via the compareTo method.
     *
     * @return true if the value has been modified, false otherwise
     * @throws IllegalStateException if the original value is not set
     */
    @Override
    public boolean isOriginalValueModified() {
        if (originalValue == null) {
            throw new IllegalStateException("Original value must be set before modification");
        }
        return originalValue.compareTo(getValue()) != 0;
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
     * Gets the original, unmodified byte value.
     *
     * @return The original value
     */
    @Override
    public Byte getOriginalValue() {
        return originalValue;
    }

    /**
     * Sets the original byte value.
     *
     * @param originalValue The new original value
     */
    @Override
    public void setOriginalValue(Byte originalValue) {
        this.originalValue = originalValue;
    }

    /**
     * Returns a string representation of this ModifiableByte.
     *
     * @return A string containing the original value and modifications
     */
    @Override
    public String toString() {
        return "ModifiableByte{" + "originalValue=" + originalValue + innerToString() + '}';
    }

    /**
     * Checks if this ModifiableByte is equal to another object. Two ModifiableByte instances are
     * considered equal if they have the same modified value.
     *
     * @param obj The object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ModifiableByte that)) {
            return false;
        }

        return getValue() != null ? getValue().equals(that.getValue()) : that.getValue() == null;
    }

    /**
     * Computes a hash code for this ModifiableByte. The hash code is based on the modified value
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
