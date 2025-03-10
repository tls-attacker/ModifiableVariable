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
 * A modification that prepends additional bytes to the beginning of a byte array.
 *
 * <p>This modification takes the original byte array and adds a specified array of bytes to its
 * beginning. It's useful for testing protocol implementations with prefixed data, which can help
 * identify issues with header processing, length validation, or data parsing.
 *
 * <p>Example usage:
 *
 * <pre>{@code
 * // Create a modification that prepends {0xAA, 0xBB} to any byte array
 * ByteArrayPrependValueModification mod = new ByteArrayPrependValueModification(
 *     new byte[]{(byte)0xAA, (byte)0xBB});
 *
 * // Apply to a variable
 * ModifiableByteArray var = new ModifiableByteArray();
 * var.setOriginalValue(new byte[]{0x01, 0x02, 0x03});
 * var.setModification(mod);
 *
 * // Results in {0xAA, 0xBB, 0x01, 0x02, 0x03}
 * byte[] result = var.getValue();
 * }</pre>
 *
 * <p>This class is serializable through JAXB annotations, allowing it to be used in XML
 * configurations for testing. The bytes to prepend are serialized using {@link
 * UnformattedByteArrayAdapter}, which provides a compact hexadecimal representation.
 */
@XmlRootElement
public class ByteArrayPrependValueModification extends VariableModification<byte[]> {

    /** The bytes to prepend to the beginning of the original byte array */
    @XmlJavaTypeAdapter(UnformattedByteArrayAdapter.class)
    private byte[] bytesToPrepend;

    /** Default constructor for serialization. */
    @SuppressWarnings("unused")
    private ByteArrayPrependValueModification() {
        super();
    }

    /**
     * Creates a new modification with the specified bytes to prepend.
     *
     * <p>This constructor sets the bytes that will be prepended to the original byte array when the
     * modification is applied.
     *
     * @param bytesToPrepend The bytes to prepend to the beginning of the original byte array
     */
    public ByteArrayPrependValueModification(byte[] bytesToPrepend) {
        super();
        this.bytesToPrepend = bytesToPrepend;
    }

    /**
     * Copy constructor for creating a deep copy of an existing modification.
     *
     * <p>This constructor creates a new instance with a clone of the bytes to prepend from the
     * provided modification.
     *
     * @param other The modification to copy
     */
    public ByteArrayPrependValueModification(ByteArrayPrependValueModification other) {
        super(other);
        bytesToPrepend = other.bytesToPrepend != null ? other.bytesToPrepend.clone() : null;
    }

    /**
     * Creates a deep copy of this modification.
     *
     * @return A new instance with a clone of the bytes to prepend
     */
    @Override
    public ByteArrayPrependValueModification createCopy() {
        return new ByteArrayPrependValueModification(this);
    }

    /**
     * Implements the modification by prepending bytes to the input.
     *
     * <p>This method concatenates the bytes to prepend with the input byte array using the
     * ArrayConverter's concatenate method. If the input is null, it returns null to preserve
     * null-safety.
     *
     * @param input The original byte array
     * @return A new byte array with the bytes prepended, or null if input was null
     */
    @Override
    protected byte[] modifyImplementationHook(byte[] input) {
        if (input == null) {
            return null;
        }
        return ArrayConverter.concatenate(bytesToPrepend, input);
    }

    /**
     * Gets the bytes that will be prepended to the original byte array.
     *
     * <p>Note that this method returns a direct reference to the internal bytes to prepend, not a
     * defensive copy. Callers should be careful not to modify the returned array.
     *
     * @return The bytes to prepend
     */
    public byte[] getBytesToPrepend() {
        return bytesToPrepend;
    }

    /**
     * Sets the bytes that will be prepended to the original byte array.
     *
     * <p>Note that this method stores a direct reference to the provided byte array, not a
     * defensive copy. Callers should be careful not to modify the array after passing it to this
     * method.
     *
     * @param bytesToPrepend The new bytes to prepend
     */
    public void setBytesToPrepend(byte[] bytesToPrepend) {
        this.bytesToPrepend = bytesToPrepend;
    }

    /**
     * Computes a hash code for this modification.
     *
     * <p>The hash code is based solely on the bytes to prepend.
     *
     * @return A hash code value for this object
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Arrays.hashCode(bytesToPrepend);
        return hash;
    }

    /**
     * Compares this modification with another object for equality.
     *
     * <p>Two ByteArrayPrependValueModification objects are considered equal if they have the same
     * bytes to prepend (compared by content, not reference).
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
        ByteArrayPrependValueModification other = (ByteArrayPrependValueModification) obj;
        return Arrays.equals(bytesToPrepend, other.bytesToPrepend);
    }

    /**
     * Returns a string representation of this modification.
     *
     * <p>The string includes the class name and the bytes to prepend as a hexadecimal string.
     *
     * @return A string representation of this object
     */
    @Override
    public String toString() {
        return "ByteArrayPrependValueModification{bytesToPrepend="
                + ArrayConverter.bytesToHexString(bytesToPrepend)
                + "}";
    }
}
