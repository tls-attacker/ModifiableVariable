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
import de.rub.nds.modifiablevariable.util.UnformattedByteArrayAdapter;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Arrays;

/**
 * A modification that appends additional bytes to the end of a byte array.
 *
 * <p>This modification takes the original byte array and concatenates a specified array of bytes to
 * its end. It's useful for testing protocol implementations with data that has additional bytes
 * appended, which can help identify issues with length verification or parsing of variable-length
 * fields.
 *
 * <p>Example usage:
 *
 * <pre>{@code
 * // Create a modification that appends {0xAA, 0xBB} to any byte array
 * ByteArrayAppendValueModification mod = new ByteArrayAppendValueModification(new byte[]{(byte)0xAA, (byte)0xBB});
 *
 * // Apply to a variable
 * ModifiableByteArray var = new ModifiableByteArray();
 * var.setOriginalValue(new byte[]{0x01, 0x02, 0x03});
 * var.setModification(mod);
 *
 * // Results in {0x01, 0x02, 0x03, 0xAA, 0xBB}
 * byte[] result = var.getValue();
 * }</pre>
 *
 * <p>This class is serializable through JAXB annotations, allowing it to be used in XML
 * configurations for testing. The bytes to append are serialized using {@link
 * UnformattedByteArrayAdapter}, which provides a compact hexadecimal representation.
 */
@XmlRootElement
public class ByteArrayAppendValueModification extends VariableModification<byte[]> {

    /** The bytes to append to the end of the original byte array */
    @XmlJavaTypeAdapter(UnformattedByteArrayAdapter.class)
    private byte[] bytesToAppend;

    /**
     * Default constructor for JAXB deserialization.
     *
     * <p>When using this constructor, the bytes to append must be set via {@link
     * #setBytesToAppend(byte[])} before applying the modification.
     */
    public ByteArrayAppendValueModification() {
        super();
    }

    /**
     * Creates a new modification with the specified bytes to append.
     *
     * <p>This constructor sets the bytes that will be appended to the original byte array when the
     * modification is applied.
     *
     * @param bytesToAppend The bytes to append to the end of the original byte array
     */
    public ByteArrayAppendValueModification(byte[] bytesToAppend) {
        super();
        this.bytesToAppend = bytesToAppend;
    }

    /**
     * Copy constructor for creating a deep copy of an existing modification.
     *
     * <p>This constructor creates a new instance with a clone of the bytes to append from the
     * provided modification.
     *
     * @param other The modification to copy
     */
    public ByteArrayAppendValueModification(ByteArrayAppendValueModification other) {
        super(other);
        bytesToAppend = other.bytesToAppend != null ? other.bytesToAppend.clone() : null;
    }

    /**
     * Creates a deep copy of this modification.
     *
     * @return A new instance with a clone of the bytes to append
     */
    @Override
    public ByteArrayAppendValueModification createCopy() {
        return new ByteArrayAppendValueModification(this);
    }

    /**
     * Implements the modification by appending bytes to the input.
     *
     * <p>This method concatenates the bytes to append to the end of the input byte array using
     * {@link ArrayConverter#concatenate(byte[], byte[])}. If the input is null, it returns null to
     * preserve null-safety.
     *
     * @param input The original byte array
     * @return A new byte array with the bytes appended, or null if input was null
     */
    @Override
    protected byte[] modifyImplementationHook(byte[] input) {
        if (input == null) {
            return null;
        }
        return ArrayConverter.concatenate(input, bytesToAppend);
    }

    /**
     * Gets the bytes that will be appended to the original byte array.
     *
     * @return The bytes to append
     */
    public byte[] getBytesToAppend() {
        return bytesToAppend;
    }

    /**
     * Sets the bytes that will be appended to the original byte array.
     *
     * @param bytesToAppend The new bytes to append
     */
    public void setBytesToAppend(byte[] bytesToAppend) {
        this.bytesToAppend = bytesToAppend;
    }

    /**
     * Computes a hash code for this modification.
     *
     * <p>The hash code is based solely on the bytes to append.
     *
     * @return A hash code value for this object
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Arrays.hashCode(bytesToAppend);
        return hash;
    }

    /**
     * Compares this modification with another object for equality.
     *
     * <p>Two ByteArrayAppendValueModification objects are considered equal if they have the same
     * bytes to append.
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
        ByteArrayAppendValueModification other = (ByteArrayAppendValueModification) obj;
        return Arrays.equals(bytesToAppend, other.bytesToAppend);
    }

    /**
     * Returns a string representation of this modification.
     *
     * <p>The string includes the class name and the bytes to append as a hexadecimal string.
     *
     * @return A string representation of this object
     */
    @Override
    public String toString() {
        return "ByteArrayAppendValueModification{bytesToAppend="
                + ArrayConverter.bytesToHexString(bytesToAppend)
                + "}";
    }
}
