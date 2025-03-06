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
 * A modification that performs a right bit shift on a BigInteger value.
 *
 * <p>This modification applies a logical right shift operation to the original BigInteger value
 * using the {@link BigInteger#shiftRight(int)} method. It moves the bits of the binary
 * representation to the right by a specified number of positions, effectively dividing the value by
 * 2 raised to the power of the shift amount.
 *
 * <p>Shift operations are useful for testing protocol implementations that manipulate binary data,
 * perform bit-level operations, or use values where bit positioning is significant (like
 * cryptographic values). This modification can help expose protocol weaknesses related to
 * insufficient validation of bit-shifted values.
 *
 * <p>Example usage:
 *
 * <pre>{@code
 * // Create a modification that shifts right by 3 bits
 * BigIntegerShiftRightModification mod = new BigIntegerShiftRightModification(3);
 *
 * // Apply to a variable
 * ModifiableBigInteger var = new ModifiableBigInteger();
 * var.setOriginalValue(new BigInteger("40")); // Binary: 101000
 * var.setModification(mod);
 *
 * // Results in 5 (Binary: 101)
 * BigInteger result = var.getValue();
 * }</pre>
 *
 * <p>This class is serializable through JAXB annotations, allowing it to be used in XML
 * configurations for testing.
 */
@XmlRootElement
public class BigIntegerShiftRightModification extends VariableModification<BigInteger> {

    /** The number of bit positions to shift right */
    private int shift;

    /**
     * Default constructor for JAXB deserialization.
     *
     * <p>When using this constructor, the shift amount must be set via {@link #setShift(int)}
     * before applying the modification.
     */
    public BigIntegerShiftRightModification() {
        super();
    }

    /**
     * Creates a new modification with the specified right shift amount.
     *
     * @param shift The number of bit positions to shift the value right
     */
    public BigIntegerShiftRightModification(int shift) {
        super();
        this.shift = shift;
    }

    /**
     * Copy constructor for creating a deep copy of an existing modification.
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
     * Implements the modification by shifting the input right by the specified number of bits.
     *
     * <p>This method performs the right shift operation on the input BigInteger using {@link
     * BigInteger#shiftRight(int)}. If the input is null, it returns null to preserve null-safety.
     *
     * <p>A right shift by n bits is equivalent to dividing by 2^n (integer division with truncation
     * toward zero).
     *
     * @param input The original BigInteger value
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
     * @return The shift amount
     */
    public int getShift() {
        return shift;
    }

    /**
     * Sets the number of bits to shift right.
     *
     * @param shift The new shift amount
     */
    public void setShift(int shift) {
        this.shift = shift;
    }

    /**
     * Computes a hash code for this modification.
     *
     * <p>The hash code is based solely on the shift amount.
     *
     * @return A hash code value for this object
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + shift;
        return hash;
    }

    /**
     * Compares this modification with another object for equality.
     *
     * <p>Two BigIntegerShiftRightModification objects are considered equal if they have the same
     * shift amount.
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
        BigIntegerShiftRightModification other = (BigIntegerShiftRightModification) obj;
        return shift == other.shift;
    }

    /**
     * Returns a string representation of this modification.
     *
     * <p>The string includes the class name and shift amount.
     *
     * @return A string representation of this object
     */
    @Override
    public String toString() {
        return "BigIntegerShiftRightModification{" + "shift=" + shift + '}';
    }
}
