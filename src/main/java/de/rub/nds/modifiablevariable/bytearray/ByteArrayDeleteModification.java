/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.bytearray;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.rub.nds.modifiablevariable.VariableModification;
import de.rub.nds.modifiablevariable.util.DataConverter;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.Arrays;

/**
 * A modification that deletes a portion of a ModifiableByteArray.
 *
 * <p>This modification removes a specified number of bytes from the original byte array starting at
 * a specified position when applied. It can be used to create truncated or partial data at runtime.
 *
 * <p>The result is always a new byte array with the specified portion removed, preserving the
 * immutability of the original data.
 *
 * @see ModifiableByteArray
 */
@XmlRootElement
public class ByteArrayDeleteModification extends VariableModification<byte[]> {

    /** The number of bytes to delete */
    @JsonProperty(required = true)
    private int count;

    /** The position from which to start deletion (0-based index) */
    @JsonProperty(required = true)
    private int startPosition;

    /** Default constructor for serialization. */
    @SuppressWarnings("unused")
    private ByteArrayDeleteModification() {
        super();
    }

    /**
     * Creates a new delete modification with the specified parameters.
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
        super();
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
     * Modifies the input by removing bytes from the specified position.
     *
     * <p>This method creates a new byte array with the specified section removed. It effectively
     * takes the bytes before the deletion point, then concatenates them with the bytes after the
     * deletion point, creating a shorter array.
     *
     * <p>The implementation handles several edge cases to ensure robustness:
     *
     * <ul>
     *   <li>If input is null, returns null (preserving null-safety)
     *   <li>If input is empty, returns the empty array (unchanged)
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
            return DataConverter.concatenate(ret1, ret2);
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
