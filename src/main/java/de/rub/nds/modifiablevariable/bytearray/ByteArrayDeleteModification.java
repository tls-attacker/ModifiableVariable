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
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.Arrays;

/**
 * A modification that deletes a portion of a byte array.
 *
 * <p>This modification removes a specified number of bytes from the original byte array starting at
 * a specified position. It handles various edge cases such as:
 *
 * <ul>
 *   <li>Empty input arrays
 *   <li>Negative start positions (wraps around to the end of the array)
 *   <li>Deletion that extends beyond the array bounds (truncates at the end)
 * </ul>
 *
 * <p>The result is a new byte array with the specified portion removed. The original byte array is
 * not modified.
 *
 * <p>This class is used for testing protocol implementations against manipulated data where
 * sections have been removed.
 */
@XmlRootElement
public class ByteArrayDeleteModification extends VariableModification<byte[]> {

    /** The number of bytes to delete */
    private int count;

    /** The position from which to start deletion (0-based index) */
    private int startPosition;

    /**
     * Default constructor. Initializes an uninitialized modification.
     *
     * <p>When using this constructor, the startPosition and count must be set using the setter
     * methods before applying the modification.
     */
    public ByteArrayDeleteModification() {
        super();
    }

    /**
     * Creates a new delete modification with the specified parameters.
     *
     * <p>Example:
     *
     * <pre>{@code
     * // Create a modification that deletes 2 bytes starting at position 3
     * ByteArrayDeleteModification mod = new ByteArrayDeleteModification(3, 2);
     * byte[] original = new byte[]{0, 1, 2, 3, 4, 5, 6};
     * byte[] modified = mod.modify(original); // Result: {0, 1, 2, 5, 6}
     * }</pre>
     *
     * @param startPosition The position from which to start deleting (0-based index)
     * @param count The number of bytes to delete
     */
    public ByteArrayDeleteModification(int startPosition, int count) {
        super();
        this.startPosition = startPosition;
        this.count = count;
    }

    /**
     * Copy constructor creating a deep copy of the provided modification.
     *
     * @param other The modification to copy
     */
    public ByteArrayDeleteModification(ByteArrayDeleteModification other) {
        super(other);
        count = other.count;
        startPosition = other.startPosition;
    }

    /**
     * Creates a deep copy of this modification.
     *
     * @return A new instance with the same deletion parameters
     */
    @Override
    public ByteArrayDeleteModification createCopy() {
        return new ByteArrayDeleteModification(this);
    }

    /**
     * Applies the delete modification to the input byte array.
     *
     * <p>This method removes the specified number of bytes from the input array starting at the
     * specified position. It handles several edge cases:
     *
     * <ul>
     *   <li>If input is null, returns null
     *   <li>If input is empty, returns the empty array
     *   <li>If startPosition is negative, it wraps around to the end of the array
     *   <li>If deletion would extend beyond the array bounds, it's truncated at the end
     * </ul>
     *
     * @param input The byte array to modify
     * @return A new byte array with the specified portion removed, or null if input was null
     */
    @Override
    protected byte[] modifyImplementationHook(byte[] input) {
        if (input == null) {
            return null;
        }
        if (input.length == 0) {
            return input;
        }

        // Wrap around and also allow to delete at the end of the original value
        int deleteStartPosition = startPosition % input.length;
        if (startPosition < 0) {
            deleteStartPosition += input.length - 1;
        }

        // If the end position overflows, it is fixed at the end of the byte array
        int deleteEndPosition = deleteStartPosition + Math.max(0, count);
        if (deleteEndPosition > input.length) {
            deleteEndPosition = input.length;
        }

        byte[] ret1 = Arrays.copyOf(input, deleteStartPosition);
        if (deleteEndPosition < input.length) {
            byte[] ret2 = Arrays.copyOfRange(input, deleteEndPosition, input.length);
            return ArrayConverter.concatenate(ret1, ret2);
        }
        return ret1;
    }

    /**
     * Gets the starting position for deletion.
     *
     * @return The position from which to start deleting (0-based index)
     */
    public int getStartPosition() {
        return startPosition;
    }

    /**
     * Sets the starting position for deletion.
     *
     * @param startPosition The position from which to start deleting (0-based index)
     */
    public void setStartPosition(int startPosition) {
        this.startPosition = startPosition;
    }

    /**
     * Gets the number of bytes to delete.
     *
     * @return The number of bytes to delete
     */
    public int getCount() {
        return count;
    }

    /**
     * Sets the number of bytes to delete.
     *
     * @param count The number of bytes to delete
     */
    public void setCount(int count) {
        this.count = count;
    }

    /**
     * Computes a hash code for this modification.
     *
     * <p>The hash code is based on the count and startPosition fields.
     *
     * @return A hash code value for this object
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + count;
        hash = 31 * hash + startPosition;
        return hash;
    }

    /**
     * Compares this modification with another object for equality.
     *
     * <p>Two ByteArrayDeleteModification objects are considered equal if they have the same count
     * and startPosition values.
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
        ByteArrayDeleteModification other = (ByteArrayDeleteModification) obj;
        if (count != other.count) {
            return false;
        }
        return startPosition == other.startPosition;
    }

    /**
     * Returns a string representation of this modification.
     *
     * <p>The string includes the class name and the values of the count and startPosition fields.
     *
     * @return A string representation of this object
     */
    @Override
    public String toString() {
        return "ByteArrayDeleteModification{"
                + "count="
                + count
                + ", startPosition="
                + startPosition
                + '}';
    }
}
