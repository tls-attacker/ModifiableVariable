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
 * A modification that appends a string to the end of a ModifiableString.
 *
 * <p>This modification takes the original string and adds a specified string to its end when
 * applied. It can be used to add text to the end of string values at runtime, which is particularly
 * useful for testing protocol implementations.
 *
 * <p>This modification is especially valuable for:
 *
 * <ul>
 *   <li>Testing boundary conditions by appending additional characters
 *   <li>Modifying string-based identifiers or names
 *   <li>Creating malformed string values for security testing
 *   <li>Adding suffixes or terminators to protocol messages
 *   <li>Testing string handling with unexpected trailing data
 * </ul>
 *
 * <p>When applied, this modification creates a new string that is the concatenation of the original
 * string followed by the append value. The original string remains unchanged, preserving
 * immutability.
 *
 * <p>Unlike insertion modifications, appending always adds content at the very end of the string,
 * making it useful for adding suffixes, terminators, or trailing data.
 *
 * @see ModifiableString
 * @see StringPrependValueModification
 * @see StringInsertValueModification
 */
@XmlRootElement
public class StringAppendValueModification extends VariableModification<String> {

    /** The string value to append to the input */
    @XmlJavaTypeAdapter(IllegalStringAdapter.class)
    private String appendValue;

    /** Default constructor for serialization. */
    @SuppressWarnings("unused")
    private StringAppendValueModification() {
        super();
    }

    /**
     * Creates a new append modification with the specified string value.
     *
     * @param appendValue The string to append to the input
     * @throws NullPointerException if appendValue is null
     */
    public StringAppendValueModification(String appendValue) {
        super();
        this.appendValue = Objects.requireNonNull(appendValue, "AppendValue must not be null");
    }

    /**
     * Copy constructor.
     *
     * @param other The modification to copy
     */
    public StringAppendValueModification(StringAppendValueModification other) {
        super(other);
        appendValue = other.appendValue;
    }

    /**
     * Creates a deep copy of this modification.
     *
     * @return A new instance with the same append value
     */
    @Override
    public StringAppendValueModification createCopy() {
        return new StringAppendValueModification(this);
    }

    /**
     * Modifies the input by appending the specified string value.
     *
     * <p>This method creates a new string by concatenating the original input string with the
     * append value. The implementation uses Java's built-in string concatenation, which creates a
     * new string object, preserving the immutability of the original string.
     *
     * <p>This concatenation approach ensures efficient string creation while maintaining correct
     * behavior even with special characters or Unicode strings.
     *
     * @param input The original string to modify
     * @return A new string with the append value added at the end, or null if the input is null
     */
    @Override
    protected String modifyImplementationHook(String input) {
        if (input == null) {
            return null;
        }
        return input + appendValue;
    }

    /**
     * Gets the string value that will be appended to the input.
     *
     * @return The string to append
     */
    public String getAppendValue() {
        return appendValue;
    }

    /**
     * Sets the string value that will be appended to the input.
     *
     * @param appendValue The new string to append
     */
    public void setAppendValue(String appendValue) {
        this.appendValue = appendValue;
    }

    /**
     * Computes a hash code for this modification. The hash code is based on the append value.
     *
     * @return The hash code value
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(appendValue);
        return hash;
    }

    /**
     * Checks if this modification is equal to another object. Two StringAppendValueModification
     * instances are considered equal if they have the same append value.
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
        StringAppendValueModification other = (StringAppendValueModification) obj;
        return Objects.equals(appendValue, other.appendValue);
    }

    /**
     * Returns a string representation of this modification.
     *
     * <p>The append value is escaped using backslash escaping to make control characters and other
     * special characters readable.
     *
     * @return A string describing this modification
     */
    @Override
    public String toString() {
        return "StringAppendValueModification{"
                + "appendValue='"
                + backslashEscapeString(appendValue)
                + '\''
                + '}';
    }
}
