/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.bytearray;

import de.rub.nds.modifiablevariable.VariableModification;
import de.rub.nds.modifiablevariable.util.ArrayConverter;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * A modification that duplicates a byte array by concatenating it with itself.
 *
 * <p>This modification takes the original byte array and appends a copy of itself, effectively
 * doubling its length. It's useful for testing protocol implementations with repeated data
 * sequences, which can help identify issues with length validation, buffer handling, or payload
 * parsing.
 *
 * <p>For example, given the byte array {@code {0x01, 0x02, 0x03}}, this modification would produce
 * {@code {0x01, 0x02, 0x03, 0x01, 0x02, 0x03}}.
 *
 * <p>Example usage:
 *
 * <pre>{@code
 * // Create a modification that duplicates a byte array
 * ByteArrayDuplicateModification mod = new ByteArrayDuplicateModification();
 *
 * // Apply to a variable
 * ModifiableByteArray var = new ModifiableByteArray();
 * var.setOriginalValue(new byte[]{0x01, 0x02, 0x03});
 * var.setModification(mod);
 *
 * // Results in {0x01, 0x02, 0x03, 0x01, 0x02, 0x03}
 * byte[] result = var.getValue();
 * }</pre>
 *
 * <p>This class is serializable through JAXB annotations, allowing it to be used in XML
 * configurations for testing.
 *
 * <p>Note that this modification is stateless as it has no configuration parameters. All instances
 * of this class behave identically and are considered equal.
 */
@XmlRootElement
public class ByteArrayDuplicateModification extends VariableModification<byte[]> {

    /**
     * Default constructor.
     *
     * <p>This modification is stateless so no parameters are needed.
     */
    public ByteArrayDuplicateModification() {
        super();
    }

    /**
     * Copy constructor for creating a deep copy of an existing modification.
     *
     * <p>Since this modification is stateless, all instances are equivalent.
     *
     * @param other The modification to copy
     */
    public ByteArrayDuplicateModification(ByteArrayDuplicateModification other) {
        super(other);
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
        return new ByteArrayDuplicateModification(this);
    }

    /**
     * Implements the modification by duplicating the input byte array.
     *
     * <p>This method concatenates the input byte array with itself using {@link
     * ArrayConverter#concatenate(byte[], byte[])}. If the input is null, it returns null to
     * preserve null-safety.
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
        return ArrayConverter.concatenate(input, input);
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
        return "ByteArrayDuplicateModification{" + '}';
    }
}
