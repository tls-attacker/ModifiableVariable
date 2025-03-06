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

/**
 * A modification that swaps the byte order (endianness) of a long value.
 *
 * <p>This modification reverses the byte order of a long value, effectively converting between
 * big-endian and little-endian representations. It uses Java's {@link Long#reverseBytes(long)}
 * method to perform the actual byte order reversal.
 *
 * <p>In big-endian format, the most significant byte is stored at the lowest memory address, while
 * in little-endian format, the least significant byte is stored at the lowest memory address. This
 * modification is particularly useful for testing protocol implementations that may have
 * inconsistent handling of byte order.
 *
 * <p>Key testing scenarios where this modification is valuable include:
 *
 * <ul>
 *   <li>Testing protocol compatibility with different endian systems
 *   <li>Finding implementation bugs related to byte order assumptions
 *   <li>Verifying proper endianness handling in multi-platform code
 *   <li>Testing network protocol implementations where byte order conversion is critical
 * </ul>
 *
 * <p>Example usage:
 *
 * <pre>{@code
 * // Create a modification that swaps byte order
 * LongSwapEndianModification mod = new LongSwapEndianModification();
 *
 * // Apply to a variable
 * ModifiableLong var = new ModifiableLong();
 * var.setOriginalValue(0x0102030405060708L);
 * var.setModification(mod);
 *
 * // Results in 0x0807060504030201L
 * Long result = var.getValue();
 * }</pre>
 *
 * <p>This class is stateless (it has no fields) since the byte-swapping operation does not require
 * any parameters. It is serializable through JAXB annotations, allowing it to be used in XML
 * configurations for testing.
 */
@XmlRootElement
public class LongSwapEndianModification extends VariableModification<Long> {

    /**
     * Default constructor.
     *
     * <p>This constructor creates a modification that swaps the byte order of a long value. No
     * additional configuration is required.
     */
    public LongSwapEndianModification() {
        super();
    }

    /**
     * Copy constructor for creating a deep copy of an existing modification.
     *
     * @param other The modification to copy
     */
    public LongSwapEndianModification(LongSwapEndianModification other) {
        super(other);
    }

    /**
     * Creates a deep copy of this modification.
     *
     * @return A new instance of the same type
     */
    @Override
    public LongSwapEndianModification createCopy() {
        return new LongSwapEndianModification(this);
    }

    /**
     * Implements the modification by reversing the byte order of the input.
     *
     * <p>This method uses {@link Long#reverseBytes(long)} to swap the endianness of the input
     * value. If the input is null, it returns null to preserve null-safety.
     *
     * <p>The bytes are swapped in the following way:
     *
     * <pre>
     * Byte:  0  1  2  3  4  5  6  7
     *        ↓  ↓  ↓  ↓  ↓  ↓  ↓  ↓
     * Byte:  7  6  5  4  3  2  1  0
     * </pre>
     *
     * @param input The original long value
     * @return The value with bytes in reversed order, or null if input is null
     */
    @Override
    protected Long modifyImplementationHook(Long input) {
        if (input == null) {
            return null;
        }
        return Long.reverseBytes(input);
    }

    /**
     * Computes a hash code for this modification.
     *
     * <p>Since this modification is stateless (has no fields), it returns a fixed value as its hash
     * code.
     *
     * @return A fixed hash code value for this object
     */
    @Override
    public int hashCode() {
        return 7;
    }

    /**
     * Compares this modification with another object for equality.
     *
     * <p>Two LongSwapEndianModification objects are considered equal if they are of the same class,
     * since the modification is stateless.
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
        return getClass() == obj.getClass();
    }

    /**
     * Returns a string representation of this modification.
     *
     * <p>The string includes only the class name since this modification is stateless.
     *
     * @return A string representation of this object
     */
    @Override
    public String toString() {
        return "LongSwapEndianModification{" + '}';
    }
}
