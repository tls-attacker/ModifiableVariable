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
 * A modification that performs a signed right bit shift on a ModifiableLong.
 *
 * <p>This modification shifts the bits of the input long to the right by a specified number of
 * positions when applied. It effectively divides the value by 2 raised to the power of the shift
 * amount, which can be used to reduce long values at runtime.
 *
 * @see ModifiableLong
 * @see LongShiftLeftModification
 */
@XmlRootElement
public class LongShiftRightModification extends VariableModification<Long> {

    /** The number of bit positions to shift right */
    private int shift;

    /** Default constructor for serialization. */
    @SuppressWarnings("unused")
    private LongShiftRightModification() {
        super();
    }

    /**
     * Creates a new modification with the specified right shift amount.
     *
     * @param shift The number of bit positions to shift the value right
     */
    public LongShiftRightModification(int shift) {
        super();
        this.shift = shift;
    }

    /**
     * Copy constructor for creating a deep copy of an existing modification.
     *
     * @param other The modification to copy
     */
    public LongShiftRightModification(LongShiftRightModification other) {
        super(other);
        shift = other.shift;
    }

    /**
     * Creates a deep copy of this modification.
     *
     * @return A new instance with the same shift amount
     */
    @Override
    public LongShiftRightModification createCopy() {
        return new LongShiftRightModification(this);
    }

    /**
     * Implements the modification by shifting the input right by the specified number of bits.
     *
     * <p>This method performs the signed right shift operation on the input long using the {@code
     * >>} operator. If the input is null, it returns null to preserve null-safety.
     *
     * @param input The original long value
     * @return The result of shifting the input right by the specified amount, or null if input is
     *     null
     */
    @Override
    protected Long modifyImplementationHook(Long input) {
        if (input == null) {
            return null;
        }
        return input >> shift;
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
     * <p>Two LongShiftRightModification objects are considered equal if they have the same shift
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
        LongShiftRightModification other = (LongShiftRightModification) obj;
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
        return "LongShiftRightModification{" + "shift=" + shift + '}';
    }
}
