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
 * <p>This modification ignores the original value of a {@link ModifiableString} and always returns
 * a predefined string specified at initialization or via setter. It's useful for testing scenarios
 * where a specific string content needs to be injected regardless of the original value.
 *
 * <p>Example usage:
 *
 * <pre>{@code
 * // Create a modification that always returns a specific string
 * StringExplicitValueModification mod = new StringExplicitValueModification("MODIFIED");
 *
 * // Apply to a variable
 * ModifiableString var = new ModifiableString();
 * var.setOriginalValue("ORIGINAL");
 * var.setModification(mod);
 *
 * // Will always return the explicit value ("MODIFIED"), not the original value
 * String result = var.getValue();
 * }</pre>
 *
 * <p>This class is serializable through JAXB annotations, allowing it to be used in XML
 * configurations for testing. The explicit value is adapted using {@link IllegalStringAdapter} to
 * handle special characters properly in XML.
 *
 * <p>This modification is particularly useful for testing string handling in protocol
 * implementations, especially for cases where specific string content may trigger different
 * behaviors or edge cases.
 */
@XmlRootElement
public class StringExplicitValueModification extends VariableModification<String> {

    /** The explicit string that will replace the original value */
    @XmlJavaTypeAdapter(IllegalStringAdapter.class)
    protected String explicitValue;

    /**
     * Default constructor for JAXB deserialization.
     *
     * <p>When using this constructor, the explicit value must be set via {@link
     * #setExplicitValue(String)} before applying the modification.
     */
    public StringExplicitValueModification() {
        super();
    }

    /**
     * Creates a new modification with the specified explicit value.
     *
     * <p>This constructor sets the string that will replace the original value when the
     * modification is applied.
     *
     * @param explicitValue The string that will replace the original value
     */
    public StringExplicitValueModification(String explicitValue) {
        super();
        this.explicitValue = explicitValue;
    }

    /**
     * Copy constructor for creating a deep copy of an existing modification.
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
     * Implements the modification by returning the explicit value.
     *
     * <p>This method ignores the input value and always returns the explicit value set during
     * initialization or via {@link #setExplicitValue(String)}. If the input is null, it returns
     * null to preserve null-safety.
     *
     * <p>Unlike byte arrays, strings are immutable in Java, so no defensive copy is needed.
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
     * Computes a hash code for this modification.
     *
     * <p>The hash code is based solely on the explicit value.
     *
     * @return A hash code value for this object
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(explicitValue);
        return hash;
    }

    /**
     * Compares this modification with another object for equality.
     *
     * <p>Two StringExplicitValueModification objects are considered equal if they have the same
     * explicit value.
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
        StringExplicitValueModification other = (StringExplicitValueModification) obj;
        return Objects.equals(explicitValue, other.explicitValue);
    }

    /**
     * Returns a string representation of this modification.
     *
     * <p>The string includes the class name and the explicit value with special characters escaped
     * for readability.
     *
     * @return A string representation of this object
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
