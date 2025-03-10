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

/**
 * A modification that performs a right bit shift on a ModifiableBigInteger.
 *
 * <p>This modification applies a logical right shift operation to the input value, effectively
 * dividing the value by 2 raised to the power of the shift amount. It can be used to manipulate
 * binary data at runtime.
 *
 * @see BigIntegerShiftLeftModification
 * @see ModifiableBigInteger
 */
@XmlRootElement
public class BigIntegerShiftRightModification extends VariableModification<BigInteger> {

    /** The number of bit positions to shift right */
    private int shift;

    /** Default constructor for serialization. */
    @SuppressWarnings("unused")
    private BigIntegerShiftRightModification() {
        super();
    }

    /**
     * Creates a new right shift modification with the specified shift amount.
     *
     * @param shift The number of bit positions to shift the value right
     */
    public BigIntegerShiftRightModification(int shift) {
        super();
        this.shift = shift;
    }

    /**
     * Copy constructor.
     *
     * @param other The modification to copy
     */
    public BigIntegerShiftRightModification(BigIntegerShiftRightModification other) {
        super(other);
        shift = other.shift;
    }

    /**
     * Creates a deep copy of this modification.
     *
     * @return A new instance with the same shift amount
     */
    @Override
    public BigIntegerShiftRightModification createCopy() {
        return new BigIntegerShiftRightModification(this);
    }

    /**
     * Modifies the input by shifting it right by the specified number of bits.
     *
     * <p>A right shift by n bits is equivalent to dividing by 2^n (integer division with truncation
     * toward zero). This operation preserves the sign of the original value.
     *
     * @param input The BigInteger value to modify
     * @return The result of shifting the input right by the specified amount, or null if input is
     *     null
     */
    @Override
    protected BigInteger modifyImplementationHook(BigInteger input) {
        if (input == null) {
            return null;
        }
        return input.shiftRight(shift);
    }

    /**
     * Gets the number of bits to shift right.
     *
     * @return The shift amount used for this modification
     */
    public int getShift() {
        return shift;
    }

    /**
     * Sets the number of bits to shift right.
     *
     * @param shift The new shift amount to use
     */
    public void setShift(int shift) {
        this.shift = shift;
    }

    /**
     * Computes a hash code for this modification. The hash code is based on the shift amount.
     *
     * @return The hash code value
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + shift;
        return hash;
    }

    /**
     * Checks if this modification is equal to another object. Two BigIntegerShiftRightModification
     * instances are considered equal if they have the same shift amount.
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
        BigIntegerShiftRightModification other = (BigIntegerShiftRightModification) obj;
        return shift == other.shift;
    }

    /**
     * Returns a string representation of this modification.
     *
     * @return A string containing the modification type and shift amount
     */
    @Override
    public String toString() {
        return "BigIntegerShiftRightModification{" + "shift=" + shift + '}';
    }
}
