/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.longint;

import de.rub.nds.modifiablevariable.ModifiableVariable;
import de.rub.nds.modifiablevariable.util.DataConverter;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * A modifiable long integer variable.
 *
 * <p>This class extends {@link ModifiableVariable} to provide runtime modification capabilities for
 * Java's primitive {@code long} values (wrapped as {@link Long} objects). It allows for various
 * transformations to be applied to long values during program execution, making it useful for
 * testing scenarios where long-typed values need to be manipulated.
 *
 * <p>Modifications specific to long integers include addition, subtraction, multiplication, XOR
 * operations, bit shifts, endian swaps, and explicit value replacements. These modifications are
 * represented by corresponding classes in the same package.
 *
 * @see ModifiableVariable
 * @see LongAddModification
 * @see LongSubtractModification
 * @see LongMultiplyModification
 * @see LongXorModification
 * @see LongShiftLeftModification
 * @see LongShiftRightModification
 * @see LongSwapEndianModification
 * @see LongExplicitValueModification
 */
@XmlRootElement
public class ModifiableLong extends ModifiableVariable<Long> {

    /** The original, unmodified value of this variable */
    private Long originalValue;

    /**
     * Default constructor creating an uninitialized ModifiableLong.
     *
     * <p>The originalValue is set to null, requiring it to be set later before meaningful
     * modifications can be applied.
     */
    public ModifiableLong() {
        super();
    }

    /**
     * Creates a new ModifiableLong with the specified original value.
     *
     * @param originalValue The initial value to store and potentially modify
     */
    public ModifiableLong(Long originalValue) {
        super();
        this.originalValue = originalValue;
    }

    /**
     * Copy constructor creating a deep copy of the provided ModifiableLong.
     *
     * <p>This constructor copies both the modification sequence and the original value.
     *
     * @param other The ModifiableLong to copy
     */
    public ModifiableLong(ModifiableLong other) {
        super(other);
        originalValue = other.originalValue;
    }

    /**
     * Creates a deep copy of this ModifiableLong.
     *
     * @return A new ModifiableLong instance with the same modification sequence and original value
     */
    @Override
    public ModifiableLong createCopy() {
        return new ModifiableLong(this);
    }

    /**
     * Gets the assertion validation value for this variable.
     *
     * <p>This value is used in {@link #validateAssertions()} to verify that the modified value
     * matches the expected value.
     *
     * @return The expected value for assertion validation
     */
    public Long getAssertEquals() {
        return assertEquals;
    }

    /**
     * Sets the assertion validation value for this variable.
     *
     * <p>This value is used to validate that modifications produce the expected result through the
     * {@link #validateAssertions()} method.
     *
     * @param assertEquals The expected value for assertion validation
     */
    public void setAssertEquals(Long assertEquals) {
        this.assertEquals = assertEquals;
    }

    /**
     * Checks if the current value differs from the original value.
     *
     * <p>This method determines whether any modifications have effectively changed the value from
     * its original state.
     *
     * @return {@code true} if the original value has been modified, {@code false} otherwise
     * @throws IllegalStateException if the original value has not been set
     */
    @Override
    public boolean isOriginalValueModified() {
        if (originalValue == null) {
            throw new IllegalStateException(
                    "Original value must be set before checking for modifications");
        }
        return !originalValue.equals(getValue());
    }

    /**
     * Converts the modified long value to a byte array of the specified size.
     *
     * <p>This method uses {@link ArrayConverter} to convert the current value to a byte array. The
     * resulting array will have the specified size, with padding or truncation as necessary.
     *
     * @param size The desired size of the resulting byte array
     * @return A byte array representation of the current value with the specified size
     */
    public byte[] getByteArray(int size) {
        return DataConverter.longToBytes(getValue(), size);
    }

    /**
     * Validates that the current value matches the expected value.
     *
     * <p>If an assertion value has been set with {@link #setAssertEquals(Long)}, this method checks
     * if the current value equals that assertion value.
     *
     * @return {@code true} if no assertion is set or if the assertion passes, {@code false}
     *     otherwise
     */
    @Override
    public boolean validateAssertions() {
        boolean valid = true;
        if (assertEquals != null && assertEquals.compareTo(getValue()) != 0) {
            valid = false;
        }
        return valid;
    }

    /**
     * Gets the original, unmodified value of this variable.
     *
     * @return The original value as set during initialization or by {@link #setOriginalValue(Long)}
     */
    @Override
    public Long getOriginalValue() {
        return originalValue;
    }

    /**
     * Sets the original value of this variable.
     *
     * <p>This method updates the base value to which modifications are applied.
     *
     * @param originalValue The new original value
     */
    @Override
    public void setOriginalValue(Long originalValue) {
        this.originalValue = originalValue;
    }

    /**
     * Returns a string representation of this ModifiableLong.
     *
     * <p>The string includes the original value and details about any applied modifications.
     *
     * @return A string representation of this object
     */
    @Override
    public String toString() {
        return "ModifiableLong{" + "originalValue=" + originalValue + innerToString() + '}';
    }

    /**
     * Compares this ModifiableLong with another object for equality.
     *
     * <p>Two ModifiableLong objects are considered equal if their current values (after applying
     * modifications) are equal, regardless of the original values or the sequence of modifications
     * applied to reach those values.
     *
     * @param obj The object to compare with
     * @return {@code true} if the objects are equal, {@code false} otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ModifiableLong that)) {
            return false;
        }

        return getValue() != null ? getValue().equals(that.getValue()) : that.getValue() == null;
    }

    /**
     * Computes a hash code for this ModifiableLong.
     *
     * <p>The hash code is based on the current value after modifications have been applied, not on
     * the original value or the sequence of modifications.
     *
     * @return A hash code value for this object
     */
    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (getValue() != null ? getValue().hashCode() : 0);
        return result;
    }
}
