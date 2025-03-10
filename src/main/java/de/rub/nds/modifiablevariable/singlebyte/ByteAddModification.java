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
 * A modification that adds a constant value to a ModifiableByte.
 *
 * <p>This modification adds a specified byte value (summand) to the input value when applied. It
 * can be used to increment or decrement byte values at runtime, which is particularly useful for
 * testing protocol implementations.
 *
 * <p>Example use cases for this modification include:
 *
 * <ul>
 *   <li>Testing overflow behavior by adding values close to byte limits (127/-128)
 *   <li>Modifying version bytes in protocols to test version compatibility
 *   <li>Incrementing counter or sequence bytes to test protocol robustness
 *   <li>Altering flag or option bytes to test different configuration combinations
 * </ul>
 *
 * <p>Since bytes are limited to 8 bits, additions that exceed the range of a byte (Byte.MIN_VALUE
 * to Byte.MAX_VALUE, or -128 to 127) will wrap around according to Java's two's complement
 * arithmetic. This wrapping behavior is particularly useful for testing overflow conditions.
 *
 * @see ModifiableByte
 * @see ByteSubtractModification
 */
@XmlRootElement
public class ByteAddModification extends VariableModification<Byte> {

    /** The value to be added to the original byte */
    private Byte summand;

    /** Default constructor for serialization. */
    @SuppressWarnings("unused")
    private ByteAddModification() {
        super();
    }

    /**
     * Creates a new addition modification with the specified summand.
     *
     * @param summand The value to add to the original byte
     */
    public ByteAddModification(byte summand) {
        super();
        this.summand = summand;
    }

    /**
     * Copy constructor.
     *
     * @param other The ByteAddModification to copy from
     */
    public ByteAddModification(ByteAddModification other) {
        super(other);
        summand = other.summand;
    }

    /**
     * Creates a copy of this modification.
     *
     * @return A new ByteAddModification instance with the same properties
     */
    @Override
    public ByteAddModification createCopy() {
        return new ByteAddModification(this);
    }

    /**
     * Modifies the input by adding the summand.
     *
     * <p>Note that this operation may cause byte overflow if the sum of the input and the summand
     * exceeds Byte.MAX_VALUE (127) or falls below Byte.MIN_VALUE (-128). In such cases, the result
     * will wrap around according to Java's two's complement arithmetic.
     *
     * @param input The byte value to modify
     * @return The result of adding the summand to the input, or null if the input is null
     */
    @Override
    protected Byte modifyImplementationHook(Byte input) {
        if (input == null) {
            return null;
        }
        return (byte) (input + summand);
    }

    /**
     * Gets the summand value used for the addition.
     *
     * @return The summand value
     */
    public Byte getSummand() {
        return summand;
    }

    /**
     * Sets the summand value to be used for the addition.
     *
     * @param summand The byte value to add to the original value
     */
    public void setSummand(Byte summand) {
        this.summand = summand;
    }

    /**
     * Computes the hash code for this object.
     *
     * @return A hash code value for this object
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + summand;
        return hash;
    }

    /**
     * Compares this ByteAddModification with another object for equality.
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
        ByteAddModification other = (ByteAddModification) obj;
        return Objects.equals(summand, other.summand);
    }

    /**
     * Returns a string representation of this modification.
     *
     * @return A string containing the modification type and summand value
     */
    @Override
    public String toString() {
        return "ByteAddModification{" + "summand=" + summand + '}';
    }
}
