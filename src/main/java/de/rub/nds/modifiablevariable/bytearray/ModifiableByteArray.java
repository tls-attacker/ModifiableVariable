/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.bytearray;

import de.rub.nds.modifiablevariable.ModifiableVariable;
import de.rub.nds.modifiablevariable.util.DataConverter;
import de.rub.nds.modifiablevariable.util.UnformattedByteArrayAdapter;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Arrays;

/**
 * A modifiable variable implementation for byte arrays.
 *
 * <p>This class extends {@link ModifiableVariable} to provide runtime modification capabilities for
 * byte array values. It supports various byte array-specific modifications such as:
 *
 * <ul>
 *   <li>Appending or prepending bytes
 *   <li>Inserting bytes at specific positions
 *   <li>Deleting bytes
 *   <li>Duplicating bytes
 *   <li>XOR operations
 *   <li>Shuffling bytes
 * </ul>
 *
 * @see ModifiableVariable
 * @see ByteArrayAppendValueModification
 * @see ByteArrayPrependValueModification
 * @see ByteArrayInsertValueModification
 * @see ByteArrayDeleteModification
 * @see ByteArrayDuplicateModification
 * @see ByteArrayXorModification
 * @see ByteArrayShuffleModification
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class ModifiableByteArray extends ModifiableVariable<byte[]> {

    /** The original byte array value before any modifications */
    private byte[] originalValue;

    /** Default constructor that creates an empty ModifiableByteArray with no original value. */
    public ModifiableByteArray() {
        super();
    }

    /**
     * Constructor that creates a ModifiableByteArray with the specified original value.
     *
     * @param originalValue The original byte array value
     */
    public ModifiableByteArray(byte[] originalValue) {
        super();
        this.originalValue = originalValue;
    }

    /**
     * Copy constructor that creates a new ModifiableByteArray with the same original value and
     * modifications as the provided instance.
     *
     * <p>Both the original value and assertion value are deep-copied using clone() to prevent
     * unintended modifications.
     *
     * @param other The ModifiableByteArray to copy
     */
    public ModifiableByteArray(ModifiableByteArray other) {
        super(other);
        originalValue = other.originalValue.clone();
        if (other.assertEquals != null) {
            assertEquals = other.assertEquals.clone();
        }
    }

    /**
     * Creates a deep copy of this ModifiableByteArray.
     *
     * @return A new ModifiableByteArray instance with the same properties
     */
    @Override
    public ModifiableByteArray createCopy() {
        return new ModifiableByteArray(this);
    }

    /**
     * Gets the original, unmodified byte array value.
     *
     * <p>The XML adapter ensures proper serialization of the byte array.
     *
     * @return The original byte array value
     */
    @Override
    @XmlJavaTypeAdapter(UnformattedByteArrayAdapter.class)
    public byte[] getOriginalValue() {
        return originalValue;
    }

    /**
     * Sets the original byte array value.
     *
     * @param originalValue The new original byte array value
     */
    @Override
    public void setOriginalValue(byte[] originalValue) {
        this.originalValue = originalValue;
    }

    /**
     * Gets the expected value for assertion validation.
     *
     * <p>The XML adapter ensures proper serialization of the byte array.
     *
     * @return The assertion value
     */
    @XmlJavaTypeAdapter(UnformattedByteArrayAdapter.class)
    public byte[] getAssertEquals() {
        return assertEquals;
    }

    /**
     * Sets the expected value for assertion validation.
     *
     * @param assertEquals The expected byte array value
     */
    public void setAssertEquals(byte[] assertEquals) {
        this.assertEquals = assertEquals;
    }

    /**
     * Checks if the modified value differs from the original value.
     *
     * <p>This method uses Arrays.equals() to properly compare the byte arrays.
     *
     * @return true if the value has been modified, false otherwise
     * @throws IllegalStateException if the original value is null
     */
    @Override
    public boolean isOriginalValueModified() {
        if (originalValue == null) {
            throw new IllegalStateException("Original value must not be null");
        } else {
            return !Arrays.equals(originalValue, getValue());
        }
    }

    /**
     * Validates whether the modified value matches the expected value (if set).
     *
     * <p>This method uses Arrays.equals() to properly compare the byte arrays.
     *
     * @return true if no assertion is set or if the current value equals the expected value
     */
    @Override
    public boolean validateAssertions() {
        boolean valid = true;
        if (assertEquals != null && !Arrays.equals(assertEquals, getValue())) {
            valid = false;
        }
        return valid;
    }

    /**
     * Checks if this ModifiableByteArray is equal to another object. Two ModifiableByteArray
     * instances are considered equal if they have the same modified value.
     *
     * <p>This method uses Arrays.equals() to properly compare the byte arrays.
     *
     * @param obj The object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ModifiableByteArray that)) {
            return false;
        }

        return Arrays.equals(getValue(), that.getValue());
    }

    /**
     * Computes a hash code for this ModifiableByteArray. The hash code is based on the modified
     * value rather than the original value.
     *
     * <p>This method uses Arrays.hashCode() to properly compute the hash code for byte arrays.
     *
     * @return The hash code value
     */
    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + Arrays.hashCode(getValue());
        return result;
    }

    /**
     * Returns a string representation of this ModifiableByteArray.
     *
     * <p>The byte arrays are converted to hexadecimal string representation for better readability.
     *
     * @return A string containing the original value and modifications
     */
    @Override
    public String toString() {
        return "ModifiableByteArray{"
                + "originalValue="
                + (originalValue != null ? DataConverter.bytesToHexString(originalValue) : "")
                + innerToString()
                + '}';
    }
}
