/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.bool;

import de.rub.nds.modifiablevariable.VariableModification;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * A modification that replaces the original boolean value with an explicitly defined value.
 *
 * <p>This modification ignores the original value of a {@link ModifiableBoolean} and always returns
 * a predefined boolean value specified at initialization or via setter. It's useful for testing
 * scenarios where a specific boolean state needs to be injected regardless of the original value.
 *
 * <p>Example usage:
 *
 * <pre>{@code
 * // Create a modification that always returns true
 * BooleanExplicitValueModification mod = new BooleanExplicitValueModification(true);
 *
 * // Apply to a variable
 * ModifiableBoolean var = new ModifiableBoolean();
 * var.setOriginalValue(false);
 * var.setModification(mod);
 *
 * // Will always return the explicit value (true), not the original value (false)
 * Boolean result = var.getValue();
 * }</pre>
 *
 * <p>This class is serializable through JAXB annotations, allowing it to be used in XML
 * configurations for testing.
 *
 * <p>This modification is particularly useful for testing boolean-based control flow paths in
 * protocol implementations, such as flag fields that enable or disable specific features or
 * behaviors.
 */
@XmlRootElement
public class BooleanExplicitValueModification extends VariableModification<Boolean> {

    /** The explicit boolean value that will replace the original value */
    private boolean explicitValue;

    /**
     * Default constructor for JAXB deserialization.
     *
     * <p>When using this constructor, the explicit value must be set via {@link
     * #setExplicitValue(boolean)} before applying the modification.
     */
    public BooleanExplicitValueModification() {
        super();
    }

    /**
     * Creates a new modification with the specified explicit value.
     *
     * <p>This constructor sets the boolean value that will replace the original value when the
     * modification is applied.
     *
     * @param explicitValue The boolean value that will replace the original value
     */
    public BooleanExplicitValueModification(boolean explicitValue) {
        super();
        this.explicitValue = explicitValue;
    }

    /**
     * Copy constructor for creating a deep copy of an existing modification.
     *
     * @param other The modification to copy
     */
    public BooleanExplicitValueModification(BooleanExplicitValueModification other) {
        super(other);
        explicitValue = other.explicitValue;
    }

    /**
     * Creates a deep copy of this modification.
     *
     * @return A new instance with the same explicit value
     */
    @Override
    public BooleanExplicitValueModification createCopy() {
        return new BooleanExplicitValueModification(this);
    }

    /**
     * Implements the modification by returning the explicit value.
     *
     * <p>This method ignores the input value and always returns the explicit value set during
     * initialization or via {@link #setExplicitValue(boolean)}. If the input is null, it returns
     * null to preserve null-safety.
     *
     * @param input The original value (ignored except for null check)
     * @return The explicit value, or null if input was null
     */
    @Override
    protected Boolean modifyImplementationHook(Boolean input) {
        if (input == null) {
            return null;
        }
        return explicitValue;
    }

    /**
     * Gets the explicit value that will replace the original value.
     *
     * @return The explicit boolean value
     */
    public boolean getExplicitValue() {
        return explicitValue;
    }

    /**
     * Sets the explicit value that will replace the original value.
     *
     * @param explicitValue The new explicit boolean value to use
     */
    public void setExplicitValue(boolean explicitValue) {
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
        hash = 31 * hash + (explicitValue ? 1231 : 1237);
        return hash;
    }

    /**
     * Compares this modification with another object for equality.
     *
     * <p>Two BooleanExplicitValueModification objects are considered equal if they have the same
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
        BooleanExplicitValueModification other = (BooleanExplicitValueModification) obj;
        return explicitValue == other.explicitValue;
    }

    /**
     * Returns a string representation of this modification.
     *
     * <p>The string includes the class name and the explicit value.
     *
     * @return A string representation of this object
     */
    @Override
    public String toString() {
        return "BooleanExplicitValueModification{" + "explicitValue=" + explicitValue + '}';
    }
}
