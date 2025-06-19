/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.string;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.rub.nds.modifiablevariable.VariableModification;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

/**
 * A modification that deletes a portion of a ModifiableString.
 *
 * <p>This modification removes a specified number of characters from the original string starting
 * at a specified position when applied. It can be used to create truncated or partial string data
 * at runtime.
 *
 * @see ModifiableString
 * @see StringInsertValueModification
 */
@XmlRootElement
public class StringDeleteModification extends VariableModification<String> {

    /** The number of characters to delete */
    @JsonProperty(required = true)
    private int count;

    /** The position from which to start deletion (0-based index) */
    @JsonProperty(required = true)
    private int startPosition;

    /** Default constructor for serialization. */
    @SuppressWarnings("unused")
    private StringDeleteModification() {
        super();
    }

    /**
     * Creates a new modification with the specified parameters.
     *
     * <p>This constructor sets the position at which to start deletion and the number of characters
     * to delete.
     *
     * @param startPosition The position from which to start deletion (0-based index)
     * @param count The number of characters to delete
     */
    public StringDeleteModification(int startPosition, int count) {
        super();
        this.startPosition = startPosition;
        this.count = count;
    }

    /**
     * Copy constructor for creating a deep copy of an existing modification.
     *
     * @param other The modification to copy
     */
    public StringDeleteModification(StringDeleteModification other) {
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
    public StringDeleteModification createCopy() {
        return new StringDeleteModification(this);
    }

    /**
     * Modifies the input by removing characters from the specified position.
     *
     * <p>This method creates a new string with the specified section removed. It effectively takes
     * the characters before the deletion point, then concatenates them with the characters after
     * the deletion point, creating a shorter string.
     *
     * <p>Special cases are handled as follows:
     *
     * <ul>
     *   <li>If the input is null, returns null
     *   <li>If the input is empty, returns the empty string
     *   <li>If the count is zero or negative, returns the original string unchanged
     *   <li>If the start position is negative, it counts from the end of the string (e.g., -1 means
     *       the last character)
     *   <li>If the start position is beyond the string length, it wraps around using modulo (e.g.,
     *       for length 10, position 20 becomes position 0)
     *   <li>If the deletion would extend beyond the end of the string, it's truncated to the end
     * </ul>
     *
     * @param input The original string
     * @return A new string with the specified portion removed, or null if the input is null, or the
     *     original string if the input is empty
     */
    @Override
    protected String modifyImplementationHook(String input) {
        if (input == null) {
            return null;
        }
        if (input.isEmpty()) {
            return input;
        }
        if (count <= 0) {
            return input;
        }

        int deleteStartPosition;
        if (startPosition < 0) {
            // For negative positions, count from the end
            deleteStartPosition = Math.max(0, input.length() + startPosition);
        } else if (startPosition >= input.length()) {
            // Position beyond string length wrap using modulo operation
            deleteStartPosition = startPosition % input.length();
        } else {
            // Normal case: delete from the specified position
            deleteStartPosition = startPosition;
        }

        // Calculate end position, ensuring it doesn't go beyond string length
        int deleteEndPosition = Math.min(input.length(), deleteStartPosition + count);

        return new StringBuilder(input).delete(deleteStartPosition, deleteEndPosition).toString();
    }

    /**
     * Gets the number of characters to delete.
     *
     * @return The number of characters to delete
     */
    public int getCount() {
        return count;
    }

    /**
     * Sets the number of characters to delete.
     *
     * @param count The new number of characters to delete
     */
    public void setCount(int count) {
        this.count = count;
    }

    /**
     * Gets the position from which to start deletion.
     *
     * @return The start position (0-based index)
     */
    public int getStartPosition() {
        return startPosition;
    }

    /**
     * Sets the position from which to start deletion.
     *
     * @param startPosition The new start position (0-based index)
     */
    public void setStartPosition(int startPosition) {
        this.startPosition = startPosition;
    }

    /**
     * Computes a hash code for this modification.
     *
     * <p>The hash code is based on the count and start position.
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
     * <p>Two StringDeleteModification objects are considered equal if they have the same count and
     * start position.
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
        StringDeleteModification other = (StringDeleteModification) obj;
        if (startPosition != other.startPosition) {
            return false;
        }
        return Objects.equals(count, other.count);
    }

    /**
     * Returns a string representation of this modification.
     *
     * <p>The string includes the class name, count, and start position.
     *
     * @return A string representation of this object
     */
    @Override
    public String toString() {
        return "StringDeleteModification{"
                + "count="
                + count
                + ", startPosition="
                + startPosition
                + '}';
    }
}
