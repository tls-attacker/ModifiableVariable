/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.string;

import de.rub.nds.modifiablevariable.VariableModification;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

/**
 * A modification that deletes a portion of a string from the original value.
 * 
 * <p>This modification removes a specified number of characters from the original string
 * starting at a specified position. It's useful for testing string handling in protocol 
 * implementations, especially for identifying issues with string parsing, length validation,
 * or content verification.
 * 
 * <p>The modification handles various edge cases:
 * <ul>
 *   <li>If the start position is negative, it wraps around to delete from the end of the string</li>
 *   <li>If the start position exceeds the string length, it wraps around using modulo arithmetic</li>
 *   <li>If the deletion would extend beyond the end of the string, it's truncated at the end</li>
 *   <li>Empty strings are returned unchanged</li>
 * </ul>
 * 
 * <p>Example usage:
 * <pre>{@code
 * // Create a modification that deletes 5 characters starting at position 6
 * StringDeleteModification mod = new StringDeleteModification(6, 5);
 * 
 * // Apply to a variable
 * ModifiableString var = new ModifiableString();
 * var.setOriginalValue("Hello, beautiful world!");
 * var.setModification(mod);
 * 
 * // Results in "Hello, world!"
 * String result = var.getValue();
 * }</pre>
 * 
 * <p>This class is serializable through JAXB annotations, allowing it to be
 * used in XML configurations for testing.
 */
@XmlRootElement
public class StringDeleteModification extends VariableModification<String> {

    /** The number of characters to delete */
    private int count;

    /** The position from which to start deletion (0-based index) */
    private int startPosition;

    /**
     * Default constructor for JAXB deserialization.
     * 
     * <p>When using this constructor, the count and start position must be set
     * via {@link #setCount(int)} and {@link #setStartPosition(int)} before 
     * applying the modification.
     */
    public StringDeleteModification() {
        super();
    }

    /**
     * Creates a new modification with the specified parameters.
     * 
     * <p>This constructor sets the position at which to start deletion and
     * the number of characters to delete.
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
    public StringDeleteModification createCopy() {
        return new StringDeleteModification(this);
    }

    /**
     * Implements the modification by deleting characters from the input string.
     * 
     * <p>This method removes the specified number of characters from the input string
     * starting at the specified position. If the input is null or empty, it returns
     * the input unchanged to preserve null-safety.
     * 
     * <p>The method handles edge cases gracefully:
     * <ul>
     *   <li>If the start position is negative, it wraps around to delete from the end of the string</li>
     *   <li>If the start position exceeds the string length, it wraps around using modulo arithmetic</li>
     *   <li>If the deletion would extend beyond the end of the string, it's truncated at the end</li>
     * </ul>
     *
     * @param input The original string
     * @return A new string with the specified portion deleted, or the input unchanged if it's null or empty
     */
    @Override
    protected String modifyImplementationHook(String input) {
        if (input == null) {
            return null;
        }
        if (input.isEmpty()) {
            return input;
        }

        // Wrap around and also allow to delete at the end of the original value
        int deleteStartPosition = startPosition % input.length();
        if (startPosition < 0) {
            deleteStartPosition += input.length() - 1;
        }

        // If the end position overflows, it is fixed at the end of the string
        int deleteEndPosition = deleteStartPosition + Math.max(0, count);
        if (deleteEndPosition > input.length()) {
            deleteEndPosition = input.length();
        }

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
     * <p>Two StringDeleteModification objects are considered equal if
     * they have the same count and start position.
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
