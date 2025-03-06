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
 * A modification that subtracts a fixed value from a byte variable.
 *
 * <p>This class modifies a byte by subtracting a specific subtrahend from it. It's useful in
 * security testing for exploring edge cases and boundary conditions in protocol handling,
 * particularly when testing for underflow conditions or boundary value analysis.
 *
 * <p>Example use cases:
 *
 * <ul>
 *   <li>Testing underflow behavior by subtracting values close to byte limits
 *   <li>Modifying version bytes in protocols to test backward compatibility
 *   <li>Decreasing counter or sequence bytes to test protocol resilience against out-of-order
 *       packets
 *   <li>Manipulating length fields to create malformed protocol messages
 * </ul>
 *
 * <p>Usage example:
 *
 * <pre>
 *   ModifiableByte variable = new ModifiableByte();
 *   variable.setOriginalValue((byte) 10);
 *   ByteSubtractModification modification = new ByteSubtractModification((byte) 15);
 *   variable.setModification(modification);
 *   byte result = variable.getValue(); // result will be (byte) -5
 * </pre>
 */
@XmlRootElement
public class ByteSubtractModification extends VariableModification<Byte> {

    /** The value to be subtracted from the original byte */
    private Byte subtrahend;

    /** Default constructor for serialization. */
    public ByteSubtractModification() {
        super();
    }

    /**
     * Constructor with a specified subtrahend value.
     *
     * @param subtrahend The byte value to subtract from the original value
     */
    public ByteSubtractModification(Byte subtrahend) {
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
     * Implements the byte subtraction modification.
     *
     * @param input The original byte value to be modified
     * @return The modified byte value (original - subtrahend), or null if input is null
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
    public Byte getSubtrahend() {
        return subtrahend;
    }

    /**
     * Sets the subtrahend value to be used for the subtraction.
     *
     * @param subtrahend The byte value to subtract from the original value
     */
    public void setSubtrahend(Byte subtrahend) {
        this.subtrahend = subtrahend;
    }

    /**
     * Computes the hash code for this object.
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
     * Compares this ByteSubtractModification with another object for equality.
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
