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
 * A modification that applies an XOR operation to a ModifiableByte.
 *
 * <p>This modification performs a bitwise XOR operation between a specified byte value (the xor
 * mask) and the input byte when applied.
 *
 * @see ModifiableByte
 */
@XmlRootElement
public class ByteXorModification extends VariableModification<Byte> {

    /** The byte value to XOR with the input byte */
    private byte xor;

    /** Default constructor for serialization. */
    @SuppressWarnings("unused")
    private ByteXorModification() {
        super();
    }

    /**
     * Creates a new XOR modification with the specified byte value.
     *
     * @param xor The byte value to XOR with the input
     */
    public ByteXorModification(byte xor) {
        super();
        this.xor = xor;
    }

    /**
     * Copy constructor.
     *
     * @param other The modification to copy
     */
    public ByteXorModification(ByteXorModification other) {
        super(other);
        xor = other.xor;
    }

    /**
     * Creates a deep copy of this modification.
     *
     * @return A new instance with the same XOR value
     */
    @Override
    public ByteXorModification createCopy() {
        return new ByteXorModification(this);
    }

    /**
     * Modifies the input by applying an XOR operation with the configured XOR value.
     *
     * <p>This method uses Java's bitwise XOR operator (^) to perform the operation. 
     *
     * <p>Note that this operation may cause byte overflow according to Java's two's complement
     * arithmetic. For example, if a bit is set in both the input and XOR mask, the result will have
     * that bit cleared.
     *
     * @param input The byte value to modify
     * @return The result of XORing the input with the configured value, or null if the input is
     *     null
     */
    @Override
    protected Byte modifyImplementationHook(Byte input) {
        if (input == null) {
            return null;
        }
        return (byte) (input ^ xor);
    }

    /**
     * Gets the byte value used for the XOR operation.
     *
     * @return The XOR byte value
     */
    public byte getXor() {
        return xor;
    }

    /**
     * Sets the byte value to use for the XOR operation.
     *
     * @param xor The new XOR byte value
     */
    public void setXor(byte xor) {
        this.xor = xor;
    }

    /**
     * Computes a hash code for this modification. The hash code is based on the XOR value.
     *
     * @return The hash code value
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + xor;
        return hash;
    }

    /**
     * Checks if this modification is equal to another object. Two ByteXorModification instances are
     * considered equal if they have the same XOR value.
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
        ByteXorModification other = (ByteXorModification) obj;
        return Objects.equals(xor, other.xor);
    }

    /**
     * Returns a string representation of this modification.
     *
     * @return A string describing this modification and its XOR value
     */
    @Override
    public String toString() {
        return "ByteXorModification{" + "xor=" + xor + '}';
    }
}
