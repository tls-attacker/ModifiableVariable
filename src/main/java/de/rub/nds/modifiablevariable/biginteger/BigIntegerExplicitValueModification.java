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
 * <p>This modification ignores the original value and always returns a predefined value specified
 * at initialization or via setter. It's useful for testing scenarios where a specific BigInteger
 * value needs to be injected regardless of the original value.
 *
 * @see ModifiableBigInteger
 */
@XmlRootElement
public class BigIntegerExplicitValueModification extends VariableModification<BigInteger> {

    /** The explicit value that will replace the original value */
    private BigInteger explicitValue;

    /** Default constructor for serialization. */
    @SuppressWarnings("unused")
    private BigIntegerExplicitValueModification() {
        super();
    }

    /**
     * Creates a new explicit value modification with the specified value.
     *
     * @param explicitValue The value that will replace the original value
     */
    public BigIntegerExplicitValueModification(BigInteger explicitValue) {
        super();
        this.explicitValue =
                Objects.requireNonNull(explicitValue, "ExplicitValue must not be null");
    }

    /**
     * Copy constructor.
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
     * Modifies the input by replacing it with the explicit value.
     *
     * <p>This method ignores the input value and always returns the explicit value set during
     * initialization or via setter. The original value is completely discarded.
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
     * Checks if this modification is equal to another object. Two
     * BigIntegerExplicitValueModification instances are considered equal if they have the same
     * explicit value.
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
        BigIntegerExplicitValueModification other = (BigIntegerExplicitValueModification) obj;
        return Objects.equals(explicitValue, other.explicitValue);
    }

    /**
     * Returns a string representation of this modification.
     *
     * @return A string containing the modification type and explicit value
     */
    @Override
    public String toString() {
        return "BigIntegerExplicitValueModification{" + "explicitValue=" + explicitValue + '}';
    }
}
