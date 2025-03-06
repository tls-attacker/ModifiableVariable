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
 * A modification that shifts a BigInteger value to the left by a specified number of bits.
 *
 * <p>This modification performs a left bit shift operation on the original BigInteger value. It
 * effectively multiplies the value by 2^shift, which can be useful for testing protocol
 * implementations with scaled values or for detecting issues related to numeric boundary conditions
 * or binary processing.
 *
 * <p>For example, shifting the decimal value 5 left by 2 bits results in 20 (5 * 2²).
 *
 * <p>Example usage:
 *
 * <pre>{@code
 * // Create a modification that shifts left by 3 bits
 * BigIntegerShiftLeftModification mod = new BigIntegerShiftLeftModification(3);
 *
 * // Apply to a variable
 * ModifiableBigInteger var = new ModifiableBigInteger();
 * var.setOriginalValue(BigInteger.valueOf(10));
 * var.setModification(mod);
 *
 * // Results in 80 (10 * 2³)
 * BigInteger result = var.getValue();
 * }</pre>
 *
 * <p>This class is serializable through JAXB annotations, allowing it to be used in XML
 * configurations for testing.
 *
 * <p>Unlike the built-in integer types, BigInteger does not have overflow concerns when performing
 * shift operations, as it can grow to accommodate arbitrarily large values.
 */
@XmlRootElement
public class BigIntegerShiftLeftModification extends VariableModification<BigInteger> {

    /** The number of bits to shift left */
    private int shift;

    /**
     * Default constructor for JAXB deserialization.
     *
     * <p>When using this constructor, the shift amount must be set via {@link #setShift(int)}
     * before applying the modification.
     */
    public BigIntegerShiftLeftModification() {
        super();
    }

    /**
     * Creates a new modification with the specified shift amount.
     *
     * <p>This constructor sets the number of bits by which the original BigInteger will be shifted
     * left when the modification is applied.
     *
     * @param shift The number of bits to shift left
     */
    public BigIntegerShiftLeftModification(int shift) {
        super();
        this.shift = shift;
    }

    /**
     * Copy constructor for creating a deep copy of an existing modification.
     *
     * @param other The modification to copy
     */
    public BigIntegerShiftLeftModification(BigIntegerShiftLeftModification other) {
        super(other);
        shift = other.shift;
    }

    /**
     * Creates a deep copy of this modification.
     *
     * @return A new instance with the same shift amount
     */
    @Override
    public BigIntegerShiftLeftModification createCopy() {
        return new BigIntegerShiftLeftModification(this);
    }

    /**
     * Implements the modification by shifting the input BigInteger left.
     *
     * <p>This method shifts the input BigInteger left by the number of bits specified during
     * initialization or via {@link #setShift(int)}. If the input is null, it returns null to
     * preserve null-safety.
     *
     * <p>The implementation uses {@link BigInteger#shiftLeft(int)}, which effectively multiplies
     * the value by 2 raised to the power of the shift amount.
     *
     * @param input The original BigInteger value
     * @return The shifted BigInteger value, or null if input was null
     */
    @Override
    protected BigInteger modifyImplementationHook(BigInteger input) {
        if (input == null) {
            return null;
        }
        return input.shiftLeft(shift);
    }

    /**
     * Gets the number of bits to shift left.
     *
     * @return The shift amount
     */
    public int getShift() {
        return shift;
    }

    /**
     * Sets the number of bits to shift left.
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
     * <p>Two BigIntegerShiftLeftModification objects are considered equal if they have the same
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
        BigIntegerShiftLeftModification other = (BigIntegerShiftLeftModification) obj;
        return shift == other.shift;
    }

    /**
     * Returns a string representation of this modification.
     *
     * <p>The string includes the class name and the shift amount.
     *
     * @return A string representation of this object
     */
    @Override
    public String toString() {
        return "BigIntegerShiftLeftModification{" + "shift=" + shift + '}';
    }
}
