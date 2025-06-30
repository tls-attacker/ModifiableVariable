/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.biginteger;

import de.rub.nds.modifiablevariable.ModifiableVariable;
import de.rub.nds.modifiablevariable.util.DataConverter;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.math.BigInteger;

/**
 * A modifiable variable implementation for BigInteger values.
 *
 * <p>This class extends {@link ModifiableVariable} to provide runtime modification capabilities for
 * BigInteger values. It supports various BigInteger-specific modifications like addition,
 * multiplication, bit-shifts, XOR operations, and more.
 *
 * @see ModifiableVariable
 * @see BigIntegerAddModification
 * @see BigIntegerSubtractModification
 * @see BigIntegerMultiplyModification
 * @see BigIntegerShiftLeftModification
 * @see BigIntegerShiftRightModification
 * @see BigIntegerXorModification
 */
@XmlRootElement
public class ModifiableBigInteger extends ModifiableVariable<BigInteger> {

    /** The original BigInteger value before any modifications */
    private BigInteger originalValue;

    /** Default constructor that creates an empty ModifiableBigInteger with no original value. */
    public ModifiableBigInteger() {
        super();
    }

    /**
     * Constructor that creates a ModifiableBigInteger with the specified original value.
     *
     * @param originalValue The original BigInteger value
     */
    public ModifiableBigInteger(BigInteger originalValue) {
        super();
        this.originalValue = originalValue;
    }

    /**
     * Copy constructor that creates a new ModifiableBigInteger with the same original value and
     * modifications as the provided instance.
     *
     * @param other The ModifiableBigInteger to copy
     */
    public ModifiableBigInteger(ModifiableBigInteger other) {
        super(other);
        originalValue = other.originalValue;
    }

    /**
     * Creates a deep copy of this ModifiableBigInteger.
     *
     * @return A new ModifiableBigInteger instance with the same properties
     */
    @Override
    public ModifiableBigInteger createCopy() {
        return new ModifiableBigInteger(this);
    }

    /**
     * Gets the expected value for assertion validation.
     *
     * @return The assertion value
     */
    public BigInteger getAssertEquals() {
        return assertEquals;
    }

    /**
     * Sets the expected value for assertion validation.
     *
     * @param assertEquals The expected BigInteger value
     */
    public void setAssertEquals(BigInteger assertEquals) {
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
            return originalValue.compareTo(getValue()) != 0;
        }
    }

    /**
     * Converts the BigInteger value to an unsigned byte array with minimum required size.
     *
     * @return The byte array representation of the BigInteger
     */
    public byte[] getByteArray() {
        return DataConverter.bigIntegerToByteArray(getValue());
    }

    /**
     * Converts the BigInteger value to an unsigned byte array of the specified size.
     *
     * @param size The size of the resulting byte array
     * @return The byte array representation of the BigInteger
     */
    public byte[] getByteArray(int size) {
        return DataConverter.bigIntegerToByteArray(getValue(), size, true);
    }

    /**
     * Validates whether the modified value matches the expected value (if set).
     *
     * @return true if no assertion is set or if the current value equals the expected value
     */
    @Override
    public boolean validateAssertions() {
        if (assertEquals != null) {
            return assertEquals.compareTo(getValue()) == 0;
        }
        return true;
    }

    /**
     * Gets the original, unmodified BigInteger value.
     *
     * @return The original value
     */
    @Override
    public BigInteger getOriginalValue() {
        return originalValue;
    }

    /**
     * Sets the original BigInteger value.
     *
     * @param originalValue The new original value
     */
    @Override
    public void setOriginalValue(BigInteger originalValue) {
        this.originalValue = originalValue;
    }

    /**
     * Returns a string representation of this ModifiableBigInteger.
     *
     * @return A string containing the original value and modifications
     */
    @Override
    public String toString() {
        return "ModifiableBigInteger{" + "originalValue=" + originalValue + innerToString() + '}';
    }

    /**
     * Checks if this ModifiableBigInteger is equal to another object. Two ModifiableBigInteger
     * instances are considered equal if they have the same modified value.
     *
     * @param obj The object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ModifiableBigInteger that)) {
            return false;
        }

        return getValue() != null ? getValue().equals(that.getValue()) : that.getValue() == null;
    }

    /**
     * Computes a hash code for this ModifiableBigInteger. The hash code is based on the modified
     * value rather than the original value.
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
