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
 * A modification that multiplies a ModifiableBigInteger by a constant factor.
 *
 * <p>This modification multiplies the input value by a specified BigInteger factor when applied. It
 * can be used to scale BigInteger values at runtime.
 *
 * @see ModifiableBigInteger
 */
@XmlRootElement
public class BigIntegerMultiplyModification extends VariableModification<BigInteger> {

    /** The factor to multiply by */
    private BigInteger factor;

    /** Default constructor for serialization. */
    @SuppressWarnings("unused")
    private BigIntegerMultiplyModification() {
        super();
    }

    /**
     * Creates a new multiplication modification with the specified factor.
     *
     * @param factor The value to multiply the original BigInteger by
     * @throws NullPointerException if factor is null
     */
    public BigIntegerMultiplyModification(BigInteger factor) {
        super();
        this.factor = Objects.requireNonNull(factor, "Factor must not be null");
    }

    /**
     * Copy constructor.
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
     * @return A new instance with the same factor
     */
    @Override
    public BigIntegerMultiplyModification createCopy() {
        return new BigIntegerMultiplyModification(this);
    }

    /**
     * Modifies the input by multiplying it by the factor.
     *
     * @param input The BigInteger value to modify
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
     * @return The value that will multiply the original BigInteger
     */
    public BigInteger getFactor() {
        return factor;
    }

    /**
     * Sets the factor for multiplication.
     *
     * @param factor The value that will multiply the original BigInteger
     */
    public void setFactor(BigInteger factor) {
        this.factor = Objects.requireNonNull(factor, "Factor must not be null");
    }

    /**
     * Computes a hash code for this modification. The hash code is based on the factor value.
     *
     * @return The hash code value
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(factor);
        return hash;
    }

    /**
     * Checks if this modification is equal to another object. Two BigIntegerMultiplyModification
     * instances are considered equal if they have the same factor.
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
        BigIntegerMultiplyModification other = (BigIntegerMultiplyModification) obj;
        return Objects.equals(factor, other.factor);
    }

    /**
     * Returns a string representation of this modification.
     *
     * @return A string containing the modification type and factor
     */
    @Override
    public String toString() {
        return "BigIntegerMultiplyModification{" + "factor=" + factor + '}';
    }
}
