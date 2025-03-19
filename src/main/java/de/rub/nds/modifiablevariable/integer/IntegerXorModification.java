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
import java.util.Objects;

/**
 * A modification that applies a bitwise XOR operation to a ModifiableInteger.
 *
 * <p>This modification performs a bitwise XOR (exclusive OR) operation between the original integer
 * value and a specified XOR mask when applied. It can be used to selectively flip specific bits in
 * integer values at runtime.
 *
 * @see ModifiableInteger
 */
@XmlRootElement
public class IntegerXorModification extends VariableModification<Integer> {

    /** The XOR mask to apply to the original integer */
    private int xor;

    /** Default constructor for serialization. */
    @SuppressWarnings("unused")
    private IntegerXorModification() {
        super();
    }

    /**
     * Creates a new modification with the specified XOR mask.
     *
     * @param xor The integer value to use as the XOR mask
     */
    public IntegerXorModification(int xor) {
        super();
        this.xor = xor;
    }

    /**
     * Copy constructor for creating a deep copy of an existing modification.
     *
     * @param other The modification to copy
     */
    public IntegerXorModification(IntegerXorModification other) {
        super();
        xor = other.xor;
    }

    /**
     * Creates a deep copy of this modification.
     *
     * @return A new instance with the same XOR mask
     */
    @Override
    public IntegerXorModification createCopy() {
        return new IntegerXorModification(this);
    }

    /**
     * Implements the modification by applying an XOR operation to the input.
     *
     * <p>This method performs the bitwise XOR operation on the input integer using the {@code ^}
     * operator. If the input is null, it returns null to preserve null-safety.
     *
     * @param input The original integer value
     * @return The result of XORing the input with the XOR mask, or null if input is null
     */
    @Override
    protected Integer modifyImplementationHook(Integer input) {
        if (input == null) {
            return null;
        }
        return input ^ xor;
    }

    /**
     * Gets the XOR mask applied to the original value.
     *
     * @return The XOR mask
     */
    public int getXor() {
        return xor;
    }

    /**
     * Sets the XOR mask to apply to the original value.
     *
     * @param xor The new XOR mask
     */
    public void setXor(int xor) {
        this.xor = xor;
    }

    /**
     * Computes a hash code for this modification.
     *
     * <p>The hash code is based solely on the XOR mask.
     *
     * @return A hash code value for this object
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(xor);
        return hash;
    }

    /**
     * Compares this modification with another object for equality.
     *
     * <p>Two IntegerXorModification objects are considered equal if they have the same XOR mask.
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
        IntegerXorModification other = (IntegerXorModification) obj;
        return Objects.equals(xor, other.xor);
    }

    /**
     * Returns a string representation of this modification.
     *
     * <p>The string includes the class name and XOR mask value.
     *
     * @return A string representation of this object
     */
    @Override
    public String toString() {
        return "IntegerXorModification{" + "xor=" + xor + '}';
    }
}
