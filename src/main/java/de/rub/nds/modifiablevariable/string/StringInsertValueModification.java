/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.string;

import static de.rub.nds.modifiablevariable.util.StringUtil.backslashEscapeString;

import de.rub.nds.modifiablevariable.VariableModification;
import de.rub.nds.modifiablevariable.util.IllegalStringAdapter;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Objects;

/**
 * A modification that inserts a string at a specified position within the original string.
 *
 * <p>This modification takes the original string and inserts additional content at a specified
 * position. It's useful for testing string handling in protocol implementations, especially for
 * identifying issues with string parsing, content validation, or length checks.
 *
 * <p>The modification handles various edge cases:
 *
 * <ul>
 *   <li>If the start position is negative, it wraps around to insert from the end of the string
 *   <li>If the start position exceeds the string length, it wraps around using modulo arithmetic
 *   <li>It allows insertion at the end of the string
 * </ul>
 *
 * <p>Example usage:
 *
 * <pre>{@code
 * // Create a modification that inserts " modified" at position 5
 * StringInsertValueModification mod = new StringInsertValueModification(" modified", 5);
 *
 * // Apply to a variable
 * ModifiableString var = new ModifiableString();
 * var.setOriginalValue("Hello world");
 * var.setModification(mod);
 *
 * // Results in "Hello modified world"
 * String result = var.getValue();
 * }</pre>
 *
 * <p>This class is serializable through JAXB annotations, allowing it to be used in XML
 * configurations for testing. The insertion value is adapted using {@link IllegalStringAdapter} to
 * handle special characters properly in XML.
 */
@XmlRootElement
public class StringInsertValueModification extends VariableModification<String> {

    /** The string to insert into the original string */
    @XmlJavaTypeAdapter(IllegalStringAdapter.class)
    private String insertValue;

    /** The position at which to insert the string (0-based index) */
    private int startPosition;

    /** Default constructor for serialization. */
    @SuppressWarnings("unused")
    private StringInsertValueModification() {
        super();
    }

    /**
     * Creates a new modification with the specified insert value and position.
     *
     * <p>This constructor sets the string to insert and the position at which to insert it when the
     * modification is applied.
     *
     * @param insertValue The string to insert into the original string
     * @param startPosition The position at which to insert the string (0-based index)
     */
    public StringInsertValueModification(String insertValue, int startPosition) {
        super();
        this.insertValue = insertValue;
        this.startPosition = startPosition;
    }

    /**
     * Copy constructor for creating a deep copy of an existing modification.
     *
     * @param other The modification to copy
     */
    public StringInsertValueModification(StringInsertValueModification other) {
        super(other);
        insertValue = other.insertValue;
        startPosition = other.startPosition;
    }

    /**
     * Creates a deep copy of this modification.
     *
     * @return A new instance with the same insert value and position
     */
    @Override
    public StringInsertValueModification createCopy() {
        return new StringInsertValueModification(this);
    }

    /**
     * Implements the modification by inserting a string at the specified position.
     *
     * <p>This method inserts the specified string at the position specified during initialization
     * or via {@link #setStartPosition(int)}. If the input is null, it returns null to preserve
     * null-safety.
     *
     * <p>The method handles edge cases gracefully:
     *
     * <ul>
     *   <li>If the position is negative, it wraps around to insert from the end of the string
     *   <li>If the position exceeds the string length, it's adjusted using modulo arithmetic
     *   <li>Insertion at the end of the string is supported
     * </ul>
     *
     * @param input The original string
     * @return A new string with the insertion applied, or null if input was null
     */
    @Override
    protected String modifyImplementationHook(String input) {
        if (input == null) {
            return null;
        }
        // Wrap around and also allow to insert at the end of the original value
        int insertPosition = startPosition % (input.length() + 1);
        if (startPosition < 0) {
            insertPosition += input.length();
        }

        return new StringBuilder(input).insert(insertPosition, insertValue).toString();
    }

    /**
     * Gets the string that will be inserted into the original string.
     *
     * @return The string to insert
     */
    public String getInsertValue() {
        return insertValue;
    }

    /**
     * Sets the string that will be inserted into the original string.
     *
     * @param insertValue The new string to insert
     */
    public void setInsertValue(String insertValue) {
        this.insertValue = insertValue;
    }

    /**
     * Gets the position at which the string will be inserted.
     *
     * @return The insertion position (0-based index)
     */
    public int getStartPosition() {
        return startPosition;
    }

    /**
     * Sets the position at which the string will be inserted.
     *
     * @param startPosition The new insertion position (0-based index)
     */
    public void setStartPosition(int startPosition) {
        this.startPosition = startPosition;
    }

    /**
     * Computes a hash code for this modification.
     *
     * <p>The hash code is based on the insert value and start position.
     *
     * @return A hash code value for this object
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(insertValue);
        hash = 31 * hash + startPosition;
        return hash;
    }

    /**
     * Compares this modification with another object for equality.
     *
     * <p>Two StringInsertValueModification objects are considered equal if they have the same
     * insert value and start position.
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
        StringInsertValueModification other = (StringInsertValueModification) obj;
        if (startPosition != other.startPosition) {
            return false;
        }
        return Objects.equals(insertValue, other.insertValue);
    }

    /**
     * Returns a string representation of this modification.
     *
     * <p>The string includes the class name, insert value (with special characters escaped), and
     * start position.
     *
     * @return A string representation of this object
     */
    @Override
    public String toString() {
        return "StringInsertValueModification{"
                + "insertValue='"
                + backslashEscapeString(insertValue)
                + '\''
                + ", startPosition="
                + startPosition
                + '}';
    }
}
