/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.integer;

import de.rub.nds.modifiablevariable.VariableModification;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

/**
 * A modification that replaces the original integer value with an explicitly specified value.
 *
 * <p>This modification ignores the original value completely and always returns the explicit value
 * that was configured during initialization. It's one of the most straightforward and powerful
 * modifications for testing protocol implementations as it allows direct control over values sent
 * in protocol messages.
 *
 * <p>Explicit value modifications are particularly useful for:
 *
 * <ul>
 *   <li>Testing specific boundary values (0, 1, -1, Integer.MIN_VALUE, Integer.MAX_VALUE)
 *   <li>Testing protocol behavior with reserved, special, or invalid values
 *   <li>Creating reproducible test cases with precisely controlled values
 *   <li>Forcing protocol paths that might be difficult to trigger otherwise
 * </ul>
 *
 * <p>Example usage:
 *
 * <pre>{@code
 * // Create a modification that always returns Integer.MAX_VALUE
 * IntegerExplicitValueModification mod = new IntegerExplicitValueModification(Integer.MAX_VALUE);
 *
 * // Apply to a variable
 * ModifiableInteger var = new ModifiableInteger();
 * var.setOriginalValue(42);
 * var.setModification(mod);
 *
 * // Results in Integer.MAX_VALUE (2147483647), regardless of the original value
 * Integer result = var.getValue();
 * }</pre>
 *
 * <p>This class is serializable through JAXB annotations, allowing it to be used in XML
 * configurations for testing.
 */
@XmlRootElement
public class IntegerExplicitValueModification extends VariableModification<Integer> {

    /** The explicit value that will replace the original value */
    protected Integer explicitValue;

    /** Default constructor for serialization. */
    @SuppressWarnings("unused")
    private IntegerExplicitValueModification() {
        super();
    }

    /**
     * Creates a new modification with the specified explicit value.
     *
     * @param explicitValue The value that will replace the original value
     */
    public IntegerExplicitValueModification(Integer explicitValue) {
        super();
        this.explicitValue = explicitValue;
    }

    /**
     * Copy constructor for creating a deep copy of an existing modification.
     *
     * @param other The modification to copy
     */
    public IntegerExplicitValueModification(IntegerExplicitValueModification other) {
        super(other);
        explicitValue = other.explicitValue;
    }

    /**
     * Creates a deep copy of this modification.
     *
     * @return A new instance with the same explicit value
     */
    @Override
    public IntegerExplicitValueModification createCopy() {
        return new IntegerExplicitValueModification(this);
    }

    /**
     * Implements the modification by replacing the input with the explicit value.
     *
     * <p>This method simply returns the explicit value, completely ignoring the input parameter
     * (except for null checks). This provides a straightforward way to override values in a
     * protocol message.
     *
     * <p>If the input is null, it preserves null-safety by returning null.
     *
     * @param input The original Integer value (ignored except for null check)
     * @return The explicit value, or null if input was null
     */
    @Override
    protected Integer modifyImplementationHook(Integer input) {
        if (input == null) {
            return null;
        }
        return explicitValue;
    }

    /**
     * Gets the explicit value that will replace the original value.
     *
     * @return The explicit value
     */
    public Integer getExplicitValue() {
        return explicitValue;
    }

    /**
     * Sets the explicit value that will replace the original value.
     *
     * @param explicitValue The new explicit value
     */
    public void setExplicitValue(Integer explicitValue) {
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
     * <p>Two IntegerExplicitValueModification objects are considered equal if they have the same
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
        IntegerExplicitValueModification other = (IntegerExplicitValueModification) obj;
        return Objects.equals(explicitValue, other.explicitValue);
    }

    /**
     * Returns a string representation of this modification.
     *
     * <p>The string includes the class name and explicit value.
     *
     * @return A string representation of this object
     */
    @Override
    public String toString() {
        return "IntegerExplicitValueModification{" + "explicitValue=" + explicitValue + '}';
    }
}
