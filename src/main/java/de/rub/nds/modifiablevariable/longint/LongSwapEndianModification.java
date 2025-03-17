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
 * A modification that swaps the byte order (endianness) of a ModifiableLong.
 *
 * <p>This modification reverses the byte order of a long value when applied, effectively converting
 * between big-endian and little-endian representations. It can be used to test how systems handle
 * different byte ordering conventions at runtime.
 *
 * <p>This modification is stateless as it has no configuration parameters. All instances of this
 * class behave identically and are considered equal when compared.
 *
 * @see ModifiableLong
 * @see de.rub.nds.modifiablevariable.integer.IntegerSwapEndianModification
 */
@XmlRootElement
public class LongSwapEndianModification extends VariableModification<Long> {

    /**
     * Default constructor.
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
