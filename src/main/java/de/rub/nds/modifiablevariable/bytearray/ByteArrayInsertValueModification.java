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
 * A modification that inserts a byte array at a specified position within the original byte array.
 *
 * <p>This modification takes the original byte array and inserts additional bytes at a specified
 * position. It's useful for testing binary protocol handling, especially for identifying issues
 * with packet parsing, length validation, or content verification.
 *
 * <p>The modification handles various edge cases:
 *
 * <ul>
 *   <li>If the start position is negative, it wraps around to insert from the end of the array
 *   <li>If the start position exceeds the array length, it wraps around using modulo arithmetic
 *   <li>It allows insertion at the end of the array
 * </ul>
 *
 * <p>Example usage:
 *
 * <pre>{@code
 * // Create a modification that inserts [0x11, 0x22] at position 2
 * ByteArrayInsertValueModification mod = new ByteArrayInsertValueModification(
 *     new byte[]{0x11, 0x22}, 2);
 *
 * // Apply to a variable
 * ModifiableByteArray var = new ModifiableByteArray();
 * var.setOriginalValue(new byte[]{0x01, 0x02, 0x03, 0x04});
 * var.setModification(mod);
 *
 * // Results in [0x01, 0x02, 0x11, 0x22, 0x03, 0x04]
 * byte[] result = var.getValue();
 * }</pre>
 *
 * <p>This class is serializable through JAXB annotations, allowing it to be used in XML
 * configurations for testing. The insertion value is adapted using {@link
 * UnformattedByteArrayAdapter} to handle binary data properly in XML.
 */
@XmlRootElement
public class ByteArrayInsertValueModification extends VariableModification<byte[]> {

    /** The bytes to insert into the original byte array */
    @XmlJavaTypeAdapter(UnformattedByteArrayAdapter.class)
    private byte[] bytesToInsert;

    /** The position at which to insert the bytes (0-based index) */
    private int startPosition;

    /**
     * Default constructor for JAXB deserialization.
     *
     * <p>When using this constructor, the bytes to insert and start position must be set via {@link
     * #setBytesToInsert(byte[])} and {@link #setStartPosition(int)} before applying the
     * modification.
     */
    public ByteArrayInsertValueModification() {
        super();
    }

    /**
     * Creates a new modification with the specified bytes and position.
     *
     * <p>This constructor sets the bytes to insert and the position at which to insert them when
     * the modification is applied.
     *
     * @param bytesToInsert The bytes to insert into the original array
     * @param startPosition The position at which to insert the bytes (0-based index)
     */
    public ByteArrayInsertValueModification(byte[] bytesToInsert, int startPosition) {
        super();
        this.bytesToInsert = bytesToInsert;
        this.startPosition = startPosition;
    }

    /**
     * Copy constructor for creating a deep copy of an existing modification.
     *
     * @param other The modification to copy
     */
    public ByteArrayInsertValueModification(ByteArrayInsertValueModification other) {
        super(other);
        bytesToInsert = other.bytesToInsert != null ? other.bytesToInsert.clone() : null;
        startPosition = other.startPosition;
    }

    /**
     * Creates a deep copy of this modification.
     *
     * @return A new instance with the same insertion parameters
     */
    @Override
    public ByteArrayInsertValueModification createCopy() {
        return new ByteArrayInsertValueModification(this);
    }

    /**
     * Implements the modification by inserting bytes at the specified position.
     *
     * <p>This method inserts the specified bytes at the position specified during initialization or
     * via {@link #setStartPosition(int)}. If the input is null, it returns null to preserve
     * null-safety.
     *
     * <p>The method handles edge cases gracefully:
     *
     * <ul>
     *   <li>If the position is negative, it wraps around to insert from the end of the array
     *   <li>If the position exceeds the array length, it's adjusted using modulo arithmetic
     *   <li>Insertion at the end of the array is supported
     * </ul>
     *
     * @param input The original byte array
     * @return A new byte array with the insertion applied, or null if input was null
     */
    @Override
    protected byte[] modifyImplementationHook(byte[] input) {
        if (input == null) {
            return null;
        }
        // Wrap around and also allow to insert at the end of the original value
        int insertPosition = startPosition % (input.length + 1);
        if (startPosition < 0) {
            insertPosition += input.length;
        }

        byte[] ret1 = Arrays.copyOf(input, insertPosition);
        if (insertPosition < input.length) {
            byte[] ret2 = Arrays.copyOfRange(input, insertPosition, input.length);
            return ArrayConverter.concatenate(ret1, bytesToInsert, ret2);
        }
        return ArrayConverter.concatenate(ret1, bytesToInsert);
    }

    /**
     * Gets the bytes that will be inserted into the original array.
     *
     * @return The bytes to insert
     */
    public byte[] getBytesToInsert() {
        return bytesToInsert;
    }

    /**
     * Sets the bytes that will be inserted into the original array.
     *
     * @param bytesToInsert The new bytes to insert
     */
    public void setBytesToInsert(byte[] bytesToInsert) {
        this.bytesToInsert = bytesToInsert;
    }

    /**
     * Gets the position at which the bytes will be inserted.
     *
     * @return The insertion position (0-based index)
     */
    public int getStartPosition() {
        return startPosition;
    }

    /**
     * Sets the position at which the bytes will be inserted.
     *
     * @param startPosition The new insertion position (0-based index)
     */
    public void setStartPosition(int startPosition) {
        this.startPosition = startPosition;
    }

    /**
     * Computes a hash code for this modification.
     *
     * <p>The hash code is based on the bytes to insert and start position.
     *
     * @return A hash code value for this object
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Arrays.hashCode(bytesToInsert);
        hash = 31 * hash + startPosition;
        return hash;
    }

    /**
     * Compares this modification with another object for equality.
     *
     * <p>Two ByteArrayInsertValueModification objects are considered equal if they have the same
     * bytes to insert and start position.
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
        ByteArrayInsertValueModification other = (ByteArrayInsertValueModification) obj;
        if (startPosition != other.startPosition) {
            return false;
        }
        return Arrays.equals(bytesToInsert, other.bytesToInsert);
    }

    /**
     * Returns a string representation of this modification.
     *
     * <p>The string includes the class name, bytes to insert (in hexadecimal format), and start
     * position.
     *
     * @return A string representation of this object
     */
    @Override
    public String toString() {
        return "ByteArrayInsertModification{"
                + "bytesToInsert="
                + ArrayConverter.bytesToHexString(bytesToInsert)
                + ", startPosition="
                + startPosition
                + '}';
    }
}
