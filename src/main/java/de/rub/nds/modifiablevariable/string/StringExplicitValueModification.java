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
 * A modification that replaces the original string with an explicitly defined value.
 *
 * <p>This modification ignores the original value and always returns a predefined string specified
 * at initialization or via setter. It can be used to inject specific string content regardless of
 * the original value.
 *
 * @see ModifiableString
 */
@XmlRootElement
public class StringExplicitValueModification extends VariableModification<String> {

    /** The explicit string that will replace the original value */
    @XmlJavaTypeAdapter(IllegalStringAdapter.class)
    private String explicitValue;

    /** Default constructor for serialization. */
    @SuppressWarnings("unused")
    private StringExplicitValueModification() {
        super();
    }

    /**
     * Creates a new explicit value modification with the specified value.
     *
     * @param explicitValue The string that will replace the original value
     */
    public StringExplicitValueModification(String explicitValue) {
        super();
        this.explicitValue =
                Objects.requireNonNull(explicitValue, "ExplicitValue must not be null");
    }

    /**
     * Copy constructor.
     *
     * @param other The modification to copy
     */
    public StringExplicitValueModification(StringExplicitValueModification other) {
        super(other);
        explicitValue = other.explicitValue;
    }

    /**
     * Creates a deep copy of this modification.
     *
     * @return A new instance with the same explicit value
     */
    @Override
    public StringExplicitValueModification createCopy() {
        return new StringExplicitValueModification(this);
    }

    /**
     * Modifies the input by replacing it with the explicit value.
     *
     * <p>This method ignores the input value and always returns the explicit value. Strings in Java
     * are immutable, so no defensive copy is needed.
     *
     * @param input The original string (ignored except for null check)
     * @return The explicit value, or null if input was null
     */
    @Override
    protected String modifyImplementationHook(String input) {
        if (input == null) {
            return null;
        }
        return explicitValue;
    }

    /**
     * Gets the explicit value that will replace the original value.
     *
     * @return The explicit string
     */
    public String getExplicitValue() {
        return explicitValue;
    }

    /**
     * Sets the explicit value that will replace the original value.
     *
     * @param explicitValue The new explicit string to use
     */
    public void setExplicitValue(String explicitValue) {
        this.explicitValue = explicitValue;
    }

    /**
     * Computes a hash code for this modification. The hash code is based on the explicit value.
     *
     * @return The hash code value
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(explicitValue);
        return hash;
    }

    /**
     * Checks if this modification is equal to another object. Two StringExplicitValueModification
     * instances are considered equal if they have the same explicit value.
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
        StringExplicitValueModification other = (StringExplicitValueModification) obj;
        return Objects.equals(explicitValue, other.explicitValue);
    }

    /**
     * Returns a string representation of this modification.
     *
     * @return A string containing the modification type and explicit value
     */
    @Override
    public String toString() {
        return "StringExplicitValueModification{"
                + "explicitValue='"
                + backslashEscapeString(explicitValue)
                + '\''
                + '}';
    }
}
