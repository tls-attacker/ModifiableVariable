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

/**
 * A modification that swaps the byte order (endianness) of a ModifiableInteger.
 *
 * <p>This modification reverses the byte order of an integer value when applied, effectively
 * converting between big-endian and little-endian representations.
 *
 * <p>The implementation uses Java's built-in {@link Integer#reverseBytes(int)} method to perform an
 * efficient, single-operation byte swap.
 *
 * <p>This modification is stateless as it has no configuration parameters. All instances of this
 * class behave identically and are considered equal when compared.
 *
 * @see ModifiableInteger
 * @see de.rub.nds.modifiablevariable.longint.LongSwapEndianModification
 */
@XmlRootElement
public class IntegerSwapEndianModification extends VariableModification<Integer> {

    /** Default constructor. */
    public IntegerSwapEndianModification() {
        super();
    }

    /**
     * Creates a deep copy of this modification.
     *
     * @return A new instance of this modification
     */
    @Override
    public IntegerSwapEndianModification createCopy() {
        return new IntegerSwapEndianModification();
    }

    /**
     * Implements the modification by swapping the byte order of the input integer.
     *
     * <p>This method uses {@link Integer#reverseBytes(int)} to change the endianness of the input
     * value. If the input is null, it returns null to preserve null-safety.
     *
     * @param input The original integer value
     * @return The integer with reversed byte order, or null if input was null
     */
    @Override
    protected Integer modifyImplementationHook(Integer input) {
        if (input == null) {
            return null;
        }
        return Integer.reverseBytes(input);
    }

    /**
     * Computes a hash code for this modification.
     *
     * <p>Since this modification is stateless, a constant hash code is returned.
     *
     * @return A constant hash code value for all instances of this class
     */
    @Override
    public int hashCode() {
        return 7;
    }

    /**
     * Compares this modification with another object for equality.
     *
     * <p>Since this modification is stateless, all instances of this class are considered equal to
     * each other.
     *
     * @param obj The object to compare with
     * @return {@code true} if the object is also an IntegerSwapEndianModification, {@code false}
     *     otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        return getClass() == obj.getClass();
    }

    /**
     * Returns a string representation of this modification.
     *
     * @return A string representation of this object
     */
    @Override
    public String toString() {
        return "IntegerSwapEndianModification{" + '}';
    }
}
