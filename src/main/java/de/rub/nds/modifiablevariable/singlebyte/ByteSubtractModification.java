/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.singlebyte;

import de.rub.nds.modifiablevariable.VariableModification;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

/**
 * A modification that subtracts a constant value from a ModifiableByte.
 *
 * <p>This modification subtracts a specified byte value (subtrahend) from the input value when
 * applied. It can be used to decrement byte values at runtime.
 *
 * @see ModifiableByte
 */
@XmlRootElement
public class ByteSubtractModification extends VariableModification<Byte> {

    /** The value to be subtracted from the original byte */
    private byte subtrahend;

    /** Default constructor for serialization. */
    @SuppressWarnings("unused")
    private ByteSubtractModification() {
        super();
    }

    /**
     * Creates a new subtraction modification with the specified subtrahend.
     *
     * @param subtrahend The value to subtract from the original byte
     */
    public ByteSubtractModification(byte subtrahend) {
        super();
        this.subtrahend = subtrahend;
    }

    /**
     * Copy constructor.
     *
     * @param other The ByteSubtractModification to copy from
     */
    public ByteSubtractModification(ByteSubtractModification other) {
        super(other);
        subtrahend = other.subtrahend;
    }

    /**
     * Creates a copy of this modification.
     *
     * @return A new ByteSubtractModification instance with the same properties
     */
    @Override
    public ByteSubtractModification createCopy() {
        return new ByteSubtractModification(this);
    }

    /**
     * Modifies the input by subtracting the subtrahend.
     *
     * <p>Note that this operation may cause byte underflow if the result falls below
     * Byte.MIN_VALUE. In such cases, the result will wrap around according to Java's two's
     * complement arithmetic.
     *
     * @param input The byte value to modify
     * @return The result of subtracting the subtrahend from the input, or null if the input is null
     */
    @Override
    protected Byte modifyImplementationHook(Byte input) {
        if (input == null) {
            return null;
        }
        return (byte) (input - subtrahend);
    }

    /**
     * Gets the subtrahend value used for the subtraction.
     *
     * @return The subtrahend value
     */
    public byte getSubtrahend() {
        return subtrahend;
    }

    /**
     * Sets the subtrahend value to be used for the subtraction.
     *
     * @param subtrahend The byte value to subtract from the original value
     */
    public void setSubtrahend(byte subtrahend) {
        this.subtrahend = subtrahend;
    }

    /**
     * Computes the hash code for this object. The hash code is based solely on the subtrahend.
     *
     * @return A hash code value for this object
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + subtrahend;
        return hash;
    }

    /**
     * Compares this ByteSubtractModification with another object for equality. Two modifications
     * are considered equal if they are of the same class and their subtrahend values are equal.
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
        ByteSubtractModification other = (ByteSubtractModification) obj;
        return Objects.equals(subtrahend, other.subtrahend);
    }

    /**
     * Returns a string representation of this modification.
     *
     * @return A string containing the modification type and subtrahend value
     */
    @Override
    public String toString() {
        return "ByteSubtractModification{" + "subtrahend=" + subtrahend + '}';
    }
}
