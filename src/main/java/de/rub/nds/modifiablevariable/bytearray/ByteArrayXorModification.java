/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.bytearray;

import de.rub.nds.modifiablevariable.VariableModification;
import de.rub.nds.modifiablevariable.util.ArrayConverter;
import de.rub.nds.modifiablevariable.util.UnformattedByteArrayAdapter;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Arrays;

/**
 * A modification that applies an XOR operation to a portion of a byte array.
 *
 * <p>This modification performs a bitwise XOR operation between a specified byte array and the
 * input byte array, starting at a specified position. The XOR operation can be used to flip
 * specific bits in the byte array, which is a common technique in cryptographic testing and
 * fuzzing.
 *
 * <p>If the XOR operation would extend beyond the end of the input array, only the overlapping
 * portion is modified.
 */
@XmlRootElement
public class ByteArrayXorModification extends VariableModification<byte[]> {

    /** The byte array to XOR with the original byte array */
    @XmlJavaTypeAdapter(UnformattedByteArrayAdapter.class)
    private byte[] xor;

    /** The position in the original byte array where the XOR operation starts */
    private int startPosition;

    /** Default constructor for serialization. */
    @SuppressWarnings("unused")
    private ByteArrayXorModification() {
        super();
    }

    /**
     * Creates a new XOR modification with the specified parameters.
     *
     * @param xor The byte array to XOR with the original value
     * @param startPosition The position in the original array to start the XOR operation
     */
    public ByteArrayXorModification(byte[] xor, int startPosition) {
        super();
        this.xor = xor;
        this.startPosition = startPosition;
    }

    /**
     * Copy constructor.
     *
     * <p>Creates a deep copy of the XOR byte array to prevent unintended modifications.
     *
     * @param other The modification to copy
     */
    public ByteArrayXorModification(ByteArrayXorModification other) {
        super(other);
        xor = other.xor != null ? other.xor.clone() : null;
        startPosition = other.startPosition;
    }

    /**
     * Creates a deep copy of this modification.
     *
     * @return A new instance with the same XOR array and start position
     */
    @Override
    public ByteArrayXorModification createCopy() {
        return new ByteArrayXorModification(this);
    }

    /**
     * Modifies the input by applying an XOR operation with the configured XOR array.
     *
     * <p>The XOR operation starts at the specified position in the input array. If the operation
     * would extend beyond the end of the input, only the overlapping portion is modified.
     *
     * <p>The modification handles special cases:
     *
     * <ul>
     *   <li>If the input is null, returns null
     *   <li>If the input is empty, returns the empty array unchanged
     *   <li>Negative start positions are wrapped around to the end of the array
     *   <li>Start positions larger than the array length are wrapped using modulo
     * </ul>
     *
     * @param input The byte array to modify
     * @return A new byte array with the XOR operation applied, or null if the input is null
     */
    @Override
    protected byte[] modifyImplementationHook(byte[] input) {
        if (input == null) {
            return null;
        }
        if (input.length == 0) {
            return input;
        }
        byte[] result = input.clone();

        // Wrap around and also allow to xor at the end of the original value
        int xorPosition = startPosition % input.length;
        if (startPosition < 0) {
            xorPosition += input.length - 1;
        }
        int endPosition = xorPosition + xor.length;
        if (endPosition > result.length) {
            // Fix the end position to the length of the original value
            // This may not match the expected behavior of a user
            // But for fuzzing purpose, that's fine
            endPosition = result.length;
        }

        for (int i = 0; i < endPosition - xorPosition; ++i) {
            result[xorPosition + i] = (byte) (input[xorPosition + i] ^ xor[i]);
        }
        return result;
    }

    /**
     * Gets the byte array used for the XOR operation.
     *
     * @return The XOR byte array
     */
    public byte[] getXor() {
        return xor;
    }

    /**
     * Sets the byte array to use for the XOR operation.
     *
     * @param xor The new XOR byte array
     */
    public void setXor(byte[] xor) {
        this.xor = xor;
    }

    /**
     * Gets the position where the XOR operation starts in the original array.
     *
     * @return The start position for the XOR operation
     */
    public int getStartPosition() {
        return startPosition;
    }

    /**
     * Sets the position where the XOR operation should start in the original array.
     *
     * @param startPosition The new start position for the XOR operation
     */
    public void setStartPosition(int startPosition) {
        this.startPosition = startPosition;
    }

    /**
     * Computes a hash code for this modification. The hash code is based on the XOR array and start
     * position.
     *
     * @return The hash code value
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Arrays.hashCode(xor);
        hash = 31 * hash + startPosition;
        return hash;
    }

    /**
     * Checks if this modification is equal to another object. Two ByteArrayXorModification
     * instances are considered equal if they have the same XOR array and start position.
     *
     * @param obj The object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ByteArrayXorModification other = (ByteArrayXorModification) obj;
        if (startPosition != other.startPosition) {
            return false;
        }
        return Arrays.equals(xor, other.xor);
    }

    /**
     * Returns a string representation of this modification. The XOR array is formatted as a
     * hexadecimal string for readability.
     *
     * @return A string describing this modification
     */
    @Override
    public String toString() {
        return "ByteArrayXorModification{"
                + "xor="
                + ArrayConverter.bytesToHexString(xor)
                + ", startPosition="
                + startPosition
                + '}';
    }
}
