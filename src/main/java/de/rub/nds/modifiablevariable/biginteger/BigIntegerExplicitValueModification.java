/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.biginteger;

import de.rub.nds.modifiablevariable.VariableModification;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.math.BigInteger;
import java.util.Objects;

/**
 * A modification that replaces the original BigInteger value with an explicitly defined value.
 *
 * <p>This modification ignores the original value of a {@link ModifiableBigInteger} and always
 * returns a predefined value specified at initialization or via setter. It's useful for testing
 * scenarios where a specific BigInteger value needs to be injected regardless of the original
 * value.
 *
 * <p>Example usage:
 *
 * <pre>{@code
 * // Create a modification that always returns BigInteger.ONE
 * BigIntegerExplicitValueModification mod = new BigIntegerExplicitValueModification(BigInteger.ONE);
 *
 * // Apply to a variable
 * ModifiableBigInteger var = new ModifiableBigInteger();
 * var.setOriginalValue(BigInteger.valueOf(42));
 * var.setModification(mod);
 *
 * // Will always return the explicit value (1), not the original value (42)
 * BigInteger result = var.getValue();
 * }</pre>
 *
 * <p>This class is serializable through JAXB annotations, allowing it to be used in XML
 * configurations for testing.
 */
@XmlRootElement
public class BigIntegerExplicitValueModification extends VariableModification<BigInteger> {

    /** The explicit value that will replace the original value */
    protected BigInteger explicitValue;

    /** Default constructor for XML serialization. */
    @SuppressWarnings("unused")
    private BigIntegerExplicitValueModification() {
        super();
    }

    /**
     * Creates a new modification with the specified explicit value.
     *
     * <p>This constructor sets the value that will replace the original value when the modification
     * is applied.
     *
     * @param explicitValue The value that will replace the original value
     * @throws NullPointerException if explicitValue is null
     */
    public BigIntegerExplicitValueModification(BigInteger explicitValue) {
        super();
        this.explicitValue =
                Objects.requireNonNull(explicitValue, "ExplicitValue must not be null");
    }

    /**
     * Copy constructor for creating a deep copy of an existing modification.
     *
     * @param other The modification to copy
     */
    public BigIntegerExplicitValueModification(BigIntegerExplicitValueModification other) {
        super(other);
        explicitValue = other.explicitValue;
    }

    /**
     * Creates a deep copy of this modification.
     *
     * @return A new instance with the same explicit value
     */
    @Override
    public BigIntegerExplicitValueModification createCopy() {
        return new BigIntegerExplicitValueModification(this);
    }

    /**
     * Implements the modification by returning the explicit value.
     *
     * <p>This method ignores the input value and always returns the explicit value set during
     * initialization or via {@link #setExplicitValue(BigInteger)}. If the input is null, it returns
     * null to preserve null-safety.
     *
     * @param input The original value (ignored except for null check)
     * @return The explicit value, or null if input was null
     */
    @Override
    protected BigInteger modifyImplementationHook(BigInteger input) {
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
    public BigInteger getExplicitValue() {
        return explicitValue;
    }

    /**
     * Sets the explicit value that will replace the original value.
     *
     * @param explicitValue The new explicit value to use
     */
    public void setExplicitValue(BigInteger explicitValue) {
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
     * <p>Two BigIntegerExplicitValueModification objects are considered equal if they have the same
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
        BigIntegerExplicitValueModification other = (BigIntegerExplicitValueModification) obj;
        return Objects.equals(explicitValue, other.explicitValue);
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
        return "BigIntegerExplicitValueModification{" + "explicitValue=" + explicitValue + '}';
    }
}
