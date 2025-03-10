/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.longint;

import de.rub.nds.modifiablevariable.VariableModification;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

/**
 * A modification that performs a left bit shift on a ModifiableLong.
 *
 * <p>This modification shifts the bits of the input long to the left by a specified number of 
 * positions when applied. It effectively multiplies the value by 2 raised to the power of the shift
 * amount, which can be used to rapidly scale long values at runtime.
 *
 * <p>In Java, left shifts on longs are performed modulo 64 (the width of a long). This class
 * enforces this behavior by applying a modulo 64 operation on the shift amount before performing
 * the shift, ensuring consistent results even with large shift values.
 *
 * <p>Left shift operations are useful for testing protocol implementations, particularly when
 * testing:
 *
 * <ul>
 *   <li>Handling of large values (left shift can quickly generate large numbers)
 *   <li>Long overflow behavior (left shifts can cause overflow)
 *   <li>Sign bit manipulation (shifting into the sign bit changes value sign)
 *   <li>Bit-level protocol operations and binary format handling
 * </ul>
 *
 * <p>This modification is especially useful for testing with 64-bit values common in cryptographic 
 * protocols, timestamp handling, and other areas where large numeric values are manipulated.
 *
 * @see ModifiableLong
 * @see LongShiftRightModification
 */
@XmlRootElement
public class LongShiftLeftModification extends VariableModification<Long> {

    /**
     * The maximum shift modifier for longs, equal to the bit width of a long (64). This is used to
     * ensure that shift operations follow Java's behavior of modulo 64 for long shifts.
     */
    private static final int MAX_SHIFT_MODIFIER = 64;

    /** The number of bit positions to shift left */
    private int shift;

    /** Default constructor for serialization. */
    @SuppressWarnings("unused")
    private LongShiftLeftModification() {
        super();
    }

    /**
     * Creates a new modification with the specified left shift amount.
     *
     * @param shift The number of bit positions to shift the value left
     */
    public LongShiftLeftModification(int shift) {
        super();
        this.shift = shift;
    }

    /**
     * Copy constructor for creating a deep copy of an existing modification.
     *
     * @param other The modification to copy
     */
    public LongShiftLeftModification(LongShiftLeftModification other) {
        super(other);
        shift = other.shift;
    }

    /**
     * Creates a deep copy of this modification.
     *
     * @return A new instance with the same shift amount
     */
    @Override
    public LongShiftLeftModification createCopy() {
        return new LongShiftLeftModification(this);
    }

    /**
     * Implements the modification by shifting the input left by the specified number of bits.
     *
     * <p>This method performs the left shift operation on the input long using the {@code <<}
     * operator. If the input is null, it returns null to preserve null-safety.
     *
     * <p>The shift amount is taken modulo 64 (the bit width of a long) to match Java's built-in
     * behavior for shift operations and to prevent undefined behavior with large shift values.
     *
     * <p>A left shift by n bits is equivalent to multiplying by 2^n. Note that for longs, shifts
     * beyond 63 bits will overflow and produce unexpected results.
     *
     * @param input The original long value
     * @return The result of shifting the input left by the specified amount, or null if input is
     *     null
     */
    @Override
    protected Long modifyImplementationHook(Long input) {
        if (input == null) {
            return null;
        }
        return input << shift % MAX_SHIFT_MODIFIER;
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
     * <p>Two LongShiftLeftModification objects are considered equal if they have the same shift
     * amount.
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
        LongShiftLeftModification other = (LongShiftLeftModification) obj;
        return Objects.equals(shift, other.shift);
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
        return "LongShiftLeftModification{" + "shift=" + shift + '}';
    }
}
