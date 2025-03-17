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
 * A modification that subtracts a constant value from a ModifiableBigInteger.
 *
 * <p>This modification subtracts a specified BigInteger value (subtrahend) from the input value
 * when applied. It can be used to decrement BigInteger values at runtime.
 *
 * @see ModifiableBigInteger
 */
@XmlRootElement
public class BigIntegerSubtractModification extends VariableModification<BigInteger> {

    /** The value to subtract from the original BigInteger */
    private BigInteger subtrahend;

    /** Default constructor for serialization. */
    @SuppressWarnings("unused")
    private BigIntegerSubtractModification() {
        super();
    }

    /**
     * Creates a new subtraction modification with the specified subtrahend.
     *
     * @param subtrahend The value to subtract from the original BigInteger
     * @throws NullPointerException if subtrahend is null
     */
    public BigIntegerSubtractModification(BigInteger subtrahend) {
        super();
        this.subtrahend = Objects.requireNonNull(subtrahend, "Subtrahend must not be null");
    }

    /**
     * Copy constructor.
     *
     * @param other The modification to copy
     */
    public BigIntegerSubtractModification(BigIntegerSubtractModification other) {
        super(other);
        subtrahend = other.subtrahend;
    }

    /**
     * Creates a deep copy of this modification.
     *
     * @return A new instance with the same subtrahend
     */
    @Override
    public BigIntegerSubtractModification createCopy() {
        return new BigIntegerSubtractModification(this);
    }

    /**
     * Modifies the input by subtracting the subtrahend.
     *
     * <p>Note that the result can be negative if the subtrahend is larger than the input value.
     *
     * @param input The BigInteger value to modify
     * @return The result of subtracting the subtrahend from the input, or null if input is null
     */
    @Override
    protected BigInteger modifyImplementationHook(BigInteger input) {
        if (input == null) {
            return null;
        }
        return input.subtract(subtrahend);
    }

    /**
     * Gets the subtrahend used for the subtraction.
     *
     * @return The value that will be subtracted from the original BigInteger
     */
    public BigInteger getSubtrahend() {
        return subtrahend;
    }

    /**
     * Sets the subtrahend for the subtraction.
     *
     * @param subtrahend The value that will be subtracted from the original BigInteger
     */
    public void setSubtrahend(BigInteger subtrahend) {
        this.subtrahend = Objects.requireNonNull(subtrahend, "Subtrahend must not be null");
    }

    /**
     * Computes a hash code for this modification. The hash code is based on the subtrahend value.
     *
     * @return The hash code value
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(subtrahend);
        return hash;
    }

    /**
     * Checks if this modification is equal to another object. Two BigIntegerSubtractModification
     * instances are considered equal if they have the same subtrahend.
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
        BigIntegerSubtractModification other = (BigIntegerSubtractModification) obj;
        return Objects.equals(subtrahend, other.subtrahend);
    }

    /**
     * Returns a string representation of this modification.
     *
     * @return A string containing the modification type and subtrahend
     */
    @Override
    public String toString() {
        return "BigIntegerSubtractModification{" + "subtrahend=" + subtrahend + '}';
    }
}
