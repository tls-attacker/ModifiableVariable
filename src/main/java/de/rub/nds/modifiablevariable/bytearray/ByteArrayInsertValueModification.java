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
import java.util.Objects;

/**
 * A modification that inserts bytes at a specified position within a ModifiableByteArray.
 *
 * <p>This modification takes the original byte array and inserts additional bytes at a specified
 * position when applied. It can be used to inject data into specific locations at runtime.
 *
 * <p>When applied, this modification creates a new byte array with the specified bytes inserted at
 * the designated position, preserving all original data before and after the insertion point.
 *
 * @see ModifiableByteArray
 * @see ByteArrayAppendValueModification
 * @see ByteArrayPrependValueModification
 */
@XmlRootElement
public class ByteArrayInsertValueModification extends VariableModification<byte[]> {

    /** The bytes to insert into the original byte array */
    @XmlJavaTypeAdapter(UnformattedByteArrayAdapter.class)
    private byte[] bytesToInsert;

    /** The position at which to insert the bytes (0-based index) */
    private int startPosition;

    /** Default constructor for serialization. */
    @SuppressWarnings("unused")
    private ByteArrayInsertValueModification() {
        super();
    }

    /**
     * Creates a new modification with the specified bytes and position.
     *
     * <p>This constructor sets the bytes to insert and the position at which to insert them when
     * the modification is applied.
     *
     * @param bytesToInsert The bytes to insert into the original array
     * @param startPosition The position at which to insert the bytes (0-based index)
     * @throws NullPointerException if bytesToInsert is null
     */
    public ByteArrayInsertValueModification(byte[] bytesToInsert, int startPosition) {
        super();
        this.bytesToInsert =
                Objects.requireNonNull(bytesToInsert, "BytesToInsert must not be null");
        this.startPosition = startPosition;
    }

    /**
     * Copy constructor for creating a deep copy of an existing modification.
     *
     * @param other The modification to copy
     */
    public ByteArrayInsertValueModification(ByteArrayInsertValueModification other) {
        super(other);
        bytesToInsert = other.bytesToInsert.clone();
        startPosition = other.startPosition;
    }

    /**
     * Creates a deep copy of this modification.
     *
     * @return A new instance with the same insertion parameters
     */
    @Override
    public ByteArrayInsertValueModification createCopy() {
        return new ByteArrayInsertValueModification(this);
    }

    /**
     * Modifies the input by inserting bytes at the specified position.
     *
     * <p>This method creates a new byte array with the specified bytes inserted at the designated
     * position. It works by: 1. Copying the portion of the input array before the insertion point
     * 2. Adding the bytes to insert 3. Appending the remainder of the input array after the
     * insertion point
     *
     * <p>The position calculation handles various edge cases gracefully:
     *
     * <ul>
     *   <li>If the position is negative, it wraps around to insert from the end of the array
     *   <li>If the position exceeds the array length, it's adjusted using modulo arithmetic
     * </ul>
     *
     * <p>The implementation uses ArrayConverter for efficient concatenation operations, ensuring
     * optimal performance even with large arrays.
     *
     * @param input The original byte array
     * @return A new byte array with the bytes inserted at the specified position, or null if input
     *     was null
     */
    @Override
    protected byte[] modifyImplementationHook(byte[] input) {
        if (input == null) {
            return null;
        }
        // Wrap around and also allow to insert at the end of the original value
        int insertPosition = startPosition % (input.length + 1);
        if (startPosition < 0) {
            insertPosition += input.length;
        }

        byte[] ret1 = Arrays.copyOf(input, insertPosition);
        if (insertPosition < input.length) {
            byte[] ret2 = Arrays.copyOfRange(input, insertPosition, input.length);
            return ArrayConverter.concatenate(ret1, bytesToInsert, ret2);
        }
        return ArrayConverter.concatenate(ret1, bytesToInsert);
    }

    /**
     * Gets the bytes that will be inserted into the original array.
     *
     * @return The bytes to insert
     */
    public byte[] getBytesToInsert() {
        return bytesToInsert;
    }

    /**
     * Sets the bytes that will be inserted into the original array.
     *
     * @param bytesToInsert The new bytes to insert
     * @throws NullPointerException if bytesToInsert is null
     */
    public void setBytesToInsert(byte[] bytesToInsert) {
        this.bytesToInsert =
                Objects.requireNonNull(bytesToInsert, "bytesToInsert must not be null");
    }

    /**
     * Gets the position at which the bytes will be inserted.
     *
     * @return The insertion position (0-based index)
     */
    public int getStartPosition() {
        return startPosition;
    }

    /**
     * Sets the position at which the bytes will be inserted.
     *
     * @param startPosition The new insertion position (0-based index)
     */
    public void setStartPosition(int startPosition) {
        this.startPosition = startPosition;
    }

    /**
     * Computes a hash code for this modification.
     *
     * <p>The hash code is based on the bytes to insert and start position.
     *
     * @return A hash code value for this object
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Arrays.hashCode(bytesToInsert);
        hash = 31 * hash + startPosition;
        return hash;
    }

    /**
     * Compares this modification with another object for equality.
     *
     * <p>Two ByteArrayInsertValueModification objects are considered equal if they have the same
     * bytes to insert and start position.
     *
     * @param obj The object to compare with
     * @return {@code true} if the objects are equal, {@code false} otherwise
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
        ByteArrayInsertValueModification other = (ByteArrayInsertValueModification) obj;
        if (startPosition != other.startPosition) {
            return false;
        }
        return Arrays.equals(bytesToInsert, other.bytesToInsert);
    }

    /**
     * Returns a string representation of this modification.
     *
     * <p>The string includes the class name, bytes to insert (in hexadecimal format), and start
     * position.
     *
     * @return A string representation of this object
     */
    @Override
    public String toString() {
        return "ByteArrayInsertModification{"
                + "bytesToInsert="
                + ArrayConverter.bytesToHexString(bytesToInsert)
                + ", startPosition="
                + startPosition
                + '}';
    }
}
