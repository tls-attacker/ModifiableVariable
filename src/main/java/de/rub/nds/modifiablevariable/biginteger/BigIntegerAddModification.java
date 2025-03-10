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
 * A modification that adds a constant value to a BigInteger.
 *
 * <p>This modification adds a specified BigInteger value (summand) to the input value when applied.
 * It can be used to increment or decrement BigInteger values at runtime, which is particularly
 * useful for manipulating large integer values like those used in cryptographic operations.
 */
@XmlRootElement
public class BigIntegerAddModification extends VariableModification<BigInteger> {

    /** The value to add to the original BigInteger */
    private BigInteger summand;

    /** Default constructor for serialization. */
    @SuppressWarnings("unused")
    private BigIntegerAddModification() {
        super();
    }

    /**
     * Creates a new addition modification with the specified summand.
     *
     * @param summand The value to add to the original BigInteger
     * @throws NullPointerException if summand is null
     */
    public BigIntegerAddModification(BigInteger summand) {
        super();
        this.summand = Objects.requireNonNull(summand, "Summand must not be null");
    }

    /**
     * Copy constructor.
     *
     * @param other The modification to copy
     */
    public BigIntegerAddModification(BigIntegerAddModification other) {
        super(other);
        summand = other.summand;
    }

    /**
     * Creates a deep copy of this modification.
     *
     * @return A new instance with the same summand
     */
    @Override
    public BigIntegerAddModification createCopy() {
        return new BigIntegerAddModification(this);
    }

    /**
     * Modifies the input by adding the summand.
     *
     * @param input The BigInteger value to modify
     * @return The result of adding the summand to the input, or null if the input is null
     */
    @Override
    protected BigInteger modifyImplementationHook(BigInteger input) {
        if (input == null) {
            return null;
        }
        return input.add(summand);
    }

    /**
     * Gets the summand used for the addition.
     *
     * @return The value that will be added to the original BigInteger
     */
    public BigInteger getSummand() {
        return summand;
    }

    /**
     * Sets the summand for the addition.
     *
     * @param summand The value that will be added to the original BigInteger
     */
    public void setSummand(BigInteger summand) {
        this.summand = summand;
    }

    /**
     * Computes a hash code for this modification. The hash code is based on the summand value.
     *
     * @return The hash code value
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(summand);
        return hash;
    }

    /**
     * Checks if this modification is equal to another object. Two BigIntegerAddModification
     * instances are considered equal if they have the same summand.
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
        BigIntegerAddModification other = (BigIntegerAddModification) obj;
        return Objects.equals(summand, other.summand);
    }

    /**
     * Returns a string representation of this modification.
     *
     * @return A string containing the modification type and summand
     */
    @Override
    public String toString() {
        return "BigIntegerAddModification{" + "summand=" + summand + '}';
    }
}
