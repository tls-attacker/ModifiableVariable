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
 * A modification that appends a specified string value to the end of the input string.
 *
 * <p>This modification performs string concatenation, adding the specified appendValue to the end
 * of the input string. This is a common operation in string manipulation and can be used for
 * testing string handling in protocols, particularly for:
 *
 * <ul>
 *   <li>Testing boundary conditions by appending additional characters
 *   <li>Modifying string-based identifiers or names
 *   <li>Creating malformed string values for security testing
 * </ul>
 *
 * <p>The class uses {@link IllegalStringAdapter} for XML serialization to handle special characters
 * that might be problematic in XML.
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
     */
    public StringAppendValueModification(String appendValue) {
        super();
        this.appendValue = appendValue;
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
     * Modifies the input by appending the specified value.
     *
     * <p>This method uses Java's string concatenation operator (+) to append the specified value to
     * the end of the input string.
     *
     * @param input The string value to modify
     * @return The result of appending the specified value to the input, or null if the input is
     *     null
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
