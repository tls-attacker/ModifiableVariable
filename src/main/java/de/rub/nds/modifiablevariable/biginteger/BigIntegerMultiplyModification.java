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
 * A modification that multiplies a BigInteger by a specified factor.
 *
 * <p>This modification takes the original BigInteger value and multiplies it by a given factor
 * using the {@link BigInteger#multiply(BigInteger)} method. It's useful for testing cryptographic
 * protocols where scaling or magnifying values can help identify vulnerabilities, especially in
 * implementations that don't properly validate numeric ranges.
 *
 * <p>Example usage:
 *
 * <pre>{@code
 * // Create a modification that multiplies by 2
 * BigIntegerMultiplyModification mod = new BigIntegerMultiplyModification(BigInteger.valueOf(2));
 *
 * // Apply to a variable
 * ModifiableBigInteger var = new ModifiableBigInteger();
 * var.setOriginalValue(BigInteger.valueOf(42));
 * var.setModification(mod);
 *
 * // Results in 84
 * BigInteger result = var.getValue();
 * }</pre>
 *
 * <p>This class is serializable through JAXB annotations, allowing it to be used in XML
 * configurations for testing.
 */
@XmlRootElement
public class BigIntegerMultiplyModification extends VariableModification<BigInteger> {

    /** The factor to multiply by */
    private BigInteger factor;

    /** Default constructor for XML serialization. */
    @SuppressWarnings("unused")
    private BigIntegerMultiplyModification() {
        super();
    }

    /**
     * Creates a new modification with the specified multiplication factor.
     *
     * @param factor The BigInteger to multiply the original value by
     * @throws NullPointerException if factor is null
     */
    public BigIntegerMultiplyModification(BigInteger factor) {
        super();
        this.factor = Objects.requireNonNull(factor, "Factor must not be null");
    }

    /**
     * Copy constructor for creating a deep copy of an existing modification.
     *
     * @param other The modification to copy
     */
    public BigIntegerMultiplyModification(BigIntegerMultiplyModification other) {
        super(other);
        factor = other.factor;
    }

    /**
     * Creates a deep copy of this modification.
     *
     * @return A new instance with the same multiplication factor
     */
    @Override
    public BigIntegerMultiplyModification createCopy() {
        return new BigIntegerMultiplyModification(this);
    }

    /**
     * Implements the modification by multiplying the input by the factor.
     *
     * <p>This method performs the multiplication operation on the input BigInteger using {@link
     * BigInteger#multiply(BigInteger)}. If the input is null, it returns null to preserve
     * null-safety.
     *
     * @param input The original BigInteger value
     * @return The result of multiplying the input by the factor, or null if input is null
     */
    @Override
    protected BigInteger modifyImplementationHook(BigInteger input) {
        if (input == null) {
            return null;
        }
        return input.multiply(factor);
    }

    /**
     * Gets the factor used for multiplication.
     *
     * @return The multiplication factor
     */
    public BigInteger getFactor() {
        return factor;
    }

    /**
     * Sets the factor to use for multiplication.
     *
     * @param factor The new multiplication factor
     */
    public void setFactor(BigInteger factor) {
        this.factor = factor;
    }

    /**
     * Computes a hash code for this modification.
     *
     * <p>The hash code is based solely on the multiplication factor.
     *
     * @return A hash code value for this object
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(factor);
        return hash;
    }

    /**
     * Compares this modification with another object for equality.
     *
     * <p>Two BigIntegerMultiplyModification objects are considered equal if they have the same
     * multiplication factor.
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
        BigIntegerMultiplyModification other = (BigIntegerMultiplyModification) obj;
        return Objects.equals(factor, other.factor);
    }

    /**
     * Returns a string representation of this modification.
     *
     * <p>The string includes the class name and factor value.
     *
     * @return A string representation of this object
     */
    @Override
    public String toString() {
        return "BigIntegerMultiplyModification{" + "factor=" + factor + '}';
    }
}
