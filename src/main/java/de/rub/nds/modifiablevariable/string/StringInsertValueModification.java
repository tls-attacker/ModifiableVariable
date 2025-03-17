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
 * <p>This modification inserts additional text at the specified position in the input string. It
 * handles special cases such as negative positions and positions beyond the string length.
 *
 * @see ModifiableString
 * @see StringDeleteModification
 * @see StringAppendValueModification
 * @see StringPrependValueModification
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
     * Creates a new insertion modification with the specified value and position.
     *
     * @param insertValue The string to insert into the original string
     * @param startPosition The position at which to insert the string (0-based index)
     */
    public StringInsertValueModification(String insertValue, int startPosition) {
        super();
        this.insertValue = Objects.requireNonNull(insertValue, "InsertValue must not be null");
        this.startPosition = startPosition;
    }

    /**
     * Copy constructor.
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
     * Modifies the input by inserting the specified string at the given position.
     *
     * <p>Special cases are handled as follows:
     *
     * <ul>
     *   <li>If the input is null, returns null
     *   <li>If the input is empty, returns the insert value
     *   <li>If the start position is negative, it counts from the end of the string (e.g., -1 means
     *       before the last character)
     *   <li>If the start position is beyond the string length, it appends to the end of the string
     *   <li>Normal case: inserts the value at the specified position (0-based index)
     * </ul>
     *
     * @param input The string to modify
     * @return A new string with the insertion applied, or null if input was null
     */
    @Override
    protected String modifyImplementationHook(String input) {
        if (input == null) {
            return null;
        }
        if (input.isEmpty()) {
            return insertValue;
        }

        int insertPosition;
        if (startPosition < 0) {
            // For negative positions, count from the end
            insertPosition = Math.max(0, input.length() + startPosition);
        } else if (startPosition >= input.length()) {
            // If position is beyond the string length, append to the end
            insertPosition = input.length();
        } else {
            // Normal case: insert at the specified position
            insertPosition = startPosition;
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
        this.insertValue = Objects.requireNonNull(insertValue, "InsertValue must not be null");
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
     * Computes a hash code for this modification. The hash code is based on the insert value and
     * position.
     *
     * @return The hash code value
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(insertValue);
        hash = 31 * hash + startPosition;
        return hash;
    }

    /**
     * Checks if this modification is equal to another object. Two StringInsertValueModification
     * instances are considered equal if they have the same insert value and start position.
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
        StringInsertValueModification other = (StringInsertValueModification) obj;
        if (startPosition != other.startPosition) {
            return false;
        }
        return Objects.equals(insertValue, other.insertValue);
    }

    /**
     * Returns a string representation of this modification.
     *
     * @return A string containing the modification type, insert value, and position
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
