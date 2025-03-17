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
 * A modification that prepends a string to the beginning of a ModifiableString.
 *
 * <p>This modification takes the original string and adds a specified string to its beginning when
 * applied. It can be used to add text to the start of string values at runtime, which is
 * particularly useful for testing protocol implementations.
 * 
 * @see ModifiableString
 * @see StringAppendValueModification
 * @see StringInsertValueModification
 */
@XmlRootElement
public class StringPrependValueModification extends VariableModification<String> {

    /** The string value to be prepended to the original string */
    @XmlJavaTypeAdapter(IllegalStringAdapter.class)
    private String prependValue;

    /** Default constructor for serialization. */
    @SuppressWarnings("unused")
    private StringPrependValueModification() {
        super();
    }

    /**
     * Creates a new prepend modification with the specified value.
     *
     * @param prependValue The string to prepend to the original string
     * @throws NullPointerException if prependValue is null
     */
    public StringPrependValueModification(String prependValue) {
        super();
        this.prependValue = Objects.requireNonNull(prependValue, "PrependValue must not be null");
    }

    /**
     * Copy constructor.
     *
     * @param other The StringPrependValueModification to copy from
     */
    public StringPrependValueModification(StringPrependValueModification other) {
        super(other);
        prependValue = other.prependValue;
    }

    /**
     * Creates a copy of this modification.
     *
     * @return A new StringPrependValueModification instance with the same properties
     */
    @Override
    public StringPrependValueModification createCopy() {
        return new StringPrependValueModification(this);
    }

    /**
     * Modifies the input by prepending the specified string value.
     *
     * <p>This method creates a new string by concatenating the prepend value with the original
     * input string. The implementation uses Java's built-in string concatenation, which creates a
     * new string object, preserving the immutability of the original string.
     *
     * <p>This concatenation approach ensures efficient string creation while maintaining correct
     * behavior even with special characters or Unicode strings.
     *
     * @param input The original string to modify
     * @return A new string with the prepend value added at the beginning, or null if the input is
     *     null
     */
    @Override
    protected String modifyImplementationHook(String input) {
        if (input == null) {
            return null;
        }
        return prependValue + input;
    }

    /**
     * Gets the string value used for prepending.
     *
     * @return The prepend value
     */
    public String getPrependValue() {
        return prependValue;
    }

    /**
     * Sets the string value to be prepended to the original value.
     *
     * @param prependValue The string to prepend to the original string
     * @throws NullPointerException if prependValue is null
     */
    public void setPrependValue(String prependValue) {
        this.prependValue = Objects.requireNonNull(prependValue, "PrependValue must not be null");
    }

    /**
     * Computes the hash code for this object.
     *
     * @return A hash code value for this object
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(prependValue);
        return hash;
    }

    /**
     * Compares this StringPrependValueModification with another object for equality.
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
        StringPrependValueModification other = (StringPrependValueModification) obj;
        return Objects.equals(prependValue, other.prependValue);
    }

    /**
     * Returns a string representation of this modification. The prepend value is escaped to make
     * non-printable characters visible.
     *
     * @return A string containing the modification type and escaped prepend value
     */
    @Override
    public String toString() {
        return "StringPrependValueModification{"
                + "prependValue='"
                + backslashEscapeString(prependValue)
                + '\''
                + '}';
    }
}
