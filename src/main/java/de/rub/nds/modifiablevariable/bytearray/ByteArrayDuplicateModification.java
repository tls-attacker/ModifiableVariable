/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.bytearray;

import de.rub.nds.modifiablevariable.VariableModification;
import de.rub.nds.modifiablevariable.util.DataConverter;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * A modification that duplicates a ModifiableByteArray by concatenating it with itself.
 *
 * <p>This modification takes the original byte array and appends a copy of itself, effectively
 * doubling its length. It can be used to create repeated data sequences at runtime.
 *
 * <p>For example, given the byte array {@code {0x01, 0x02, 0x03}}, this modification would produce
 * {@code {0x01, 0x02, 0x03, 0x01, 0x02, 0x03}}.
 *
 * @see ModifiableByteArray
 * @see ByteArrayAppendValueModification
 */
@XmlRootElement
public class ByteArrayDuplicateModification extends VariableModification<byte[]> {

    /** Default constructor */
    public ByteArrayDuplicateModification() {
        super();
    }

    /**
     * Creates a deep copy of this modification.
     *
     * <p>Since this modification is stateless, all instances are equivalent.
     *
     * @return A new instance of this modification
     */
    @Override
    public ByteArrayDuplicateModification createCopy() {
        return new ByteArrayDuplicateModification();
    }

    /**
     * Modifies the input by duplicating the byte array.
     *
     * <p>This method creates a new byte array that consists of the input array concatenated with a
     * copy of itself, effectively doubling the length of the array. The operation preserves the
     * original array's contents and ordering, simply repeating it.
     *
     * <p>The implementation uses the ArrayConverter's concatenate method for efficient array
     * manipulation and guarantees that the original array is not modified, maintaining
     * immutability.
     *
     * @param input The original byte array
     * @return A new byte array consisting of the input array concatenated with itself, or null if
     *     input was null
     */
    @Override
    protected byte[] modifyImplementationHook(byte[] input) {
        if (input == null) {
            return null;
        }
        return DataConverter.concatenate(input, input);
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
     * @return {@code true} if the object is also a ByteArrayDuplicateModification, {@code false}
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
     * <p>Since this modification is stateless, the string only includes the class name.
     *
     * @return A string representation of this object
     */
    @Override
    public String toString() {
        return "ByteArrayDuplicateModification{}";
    }
}
