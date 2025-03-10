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
 * A modification that performs a signed right bit shift on an integer value.
 *
 * <p>This modification applies a signed right shift operation to the original integer value using
 * the {@code >>} operator. It moves the bits in the binary representation to the right by a
 * specified number of positions, effectively dividing the value by 2 raised to the power of the
 * shift amount. The sign bit is preserved and propagated during the shift operation.
 *
 * <p>In Java, right shifts on integers are performed modulo 32 (the width of an integer). This
 * class enforces this behavior by applying a modulo 32 operation on the shift amount before
 * performing the shift. This ensures that very large shift values still produce valid results
 * consistent with Java's built-in shift behavior.
 *
 * <p>Importantly, this class uses the signed right shift operator ({@code >>}), which preserves the
 * sign of the original value. Negative values remain negative after shifting, with the sign bit
 * being copied into the positions vacated by the shift.
 *
 * <p>Right shift operations are useful for testing protocol implementations, particularly when
 * testing:
 *
 * <ul>
 *   <li>Value truncation (right shift removes least significant bits)
 *   <li>Partial information loss in binary formats
 *   <li>Sign bit handling and negative value behavior
 *   <li>Bit-level protocol operations
 * </ul>
 *
 * <p>Example usage:
 *
 * <pre>{@code
 * // Create a modification that shifts right by 2 bits
 * IntegerShiftRightModification mod = new IntegerShiftRightModification(2);
 *
 * // Apply to a variable
 * ModifiableInteger var = new ModifiableInteger();
 * var.setOriginalValue(20); // Binary: 10100
 * var.setModification(mod);
 *
 * // Results in 5 (Binary: 101)
 * Integer result = var.getValue();
 * }</pre>
 *
 * <p>This class is serializable through JAXB annotations, allowing it to be used in XML
 * configurations for testing.
 */
@XmlRootElement
public class IntegerShiftRightModification extends VariableModification<Integer> {

    /**
     * The maximum shift modifier for integers, equal to the bit width of an integer (32). This is
     * used to ensure that shift operations follow Java's behavior of modulo 32 for integer shifts.
     */
    private static final int MAX_SHIFT_MODIFIER = 32;

    /** The number of bit positions to shift right */
    private int shift;

    /** Default constructor for serialization. */
    @SuppressWarnings("unused")
    private IntegerShiftRightModification() {
        super();
    }

    /**
     * Creates a new modification with the specified right shift amount.
     *
     * @param shift The number of bit positions to shift the value right
     */
    public IntegerShiftRightModification(int shift) {
        super();
        this.shift = shift;
    }

    /**
     * Copy constructor for creating a deep copy of an existing modification.
     *
     * @param other The modification to copy
     */
    public IntegerShiftRightModification(IntegerShiftRightModification other) {
        super(other);
        shift = other.shift;
    }

    /**
     * Creates a deep copy of this modification.
     *
     * @return A new instance with the same shift amount
     */
    @Override
    public IntegerShiftRightModification createCopy() {
        return new IntegerShiftRightModification(this);
    }

    /**
     * Implements the modification by shifting the input right by the specified number of bits.
     *
     * <p>This method performs the signed right shift operation on the input integer using the
     * {@code >>} operator. If the input is null, it returns null to preserve null-safety.
     *
     * <p>The shift amount is taken modulo 32 (the bit width of an integer) to match Java's built-in
     * behavior for shift operations and to prevent undefined behavior with large shift values.
     *
     * <p>A right shift by n bits is equivalent to dividing by 2^n (with truncation toward negative
     * infinity for negative numbers). For signed right shifts, the sign bit is propagated, meaning
     * that negative values remain negative after shifting.
     *
     * @param input The original integer value
     * @return The result of shifting the input right by the specified amount, or null if input is
     *     null
     */
    @Override
    protected Integer modifyImplementationHook(Integer input) {
        if (input == null) {
            return null;
        }
        return input >> shift % MAX_SHIFT_MODIFIER;
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
     * <p>Two IntegerShiftRightModification objects are considered equal if they have the same shift
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
        IntegerShiftRightModification other = (IntegerShiftRightModification) obj;
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
        return "IntegerShiftRightModification{" + "shift=" + shift + '}';
    }
}
