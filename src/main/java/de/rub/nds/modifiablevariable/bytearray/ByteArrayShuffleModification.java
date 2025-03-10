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
import java.util.Objects;

/**
 * A modification that shuffles elements within a ModifiableByteArray.
 *
 * <p>This modification reorders elements within the byte array by performing a series of 
 * swap operations based on a predefined shuffle pattern. It can be used to create data with
 * the same content but in a different order at runtime.
 *
 * <p>The shuffling pattern is specified as a byte array where:
 * 
 * <ul>
 *   <li>For arrays with at most 255 elements, each consecutive pair of bytes in the shuffle array
 *       is interpreted as two indices to swap.
 *   <li>For larger arrays (more than 255 elements), each consecutive 4 bytes in the shuffle array
 *       are interpreted as two 16-bit indices to swap.
 * </ul>
 *
 * <p>This modification is particularly useful for:
 * 
 * <ul>
 *   <li>Testing protocol implementations against malformed but content-preserving inputs
 *   <li>Testing parser robustness when field order is changed
 *   <li>Verifying that implementations don't make inappropriate assumptions about data ordering
 *   <li>Checking protocol tolerance to reordered data (e.g., sequence numbers)
 * </ul>
 *
 * <p>The transformation always produces a byte array with the same length and the same elements 
 * as the input, just in a different order, making it valuable for testing protocol sensitivity 
 * to element ordering without changing the actual content.
 *
 * @see ModifiableByteArray
 */
@XmlRootElement
public class ByteArrayShuffleModification extends VariableModification<byte[]> {

    /**
     * The byte array containing the shuffle pattern.
     *
     * <p>For arrays up to 255 elements, each consecutive pair of bytes defines two indices to swap.
     * For larger arrays, each consecutive 4 bytes define two 16-bit indices to swap. Each index is
     * taken modulo the length of the input array to ensure bounds checking.
     */
    @XmlJavaTypeAdapter(UnformattedByteArrayAdapter.class)
    private byte[] shuffle;

    /** Default constructor for serialization. */
    @SuppressWarnings("unused")
    private ByteArrayShuffleModification() {
        super();
    }

    /**
     * Creates a new shuffle modification with the specified shuffle pattern.
     *
     * @param shuffle The byte array containing the shuffle pattern defining which indices to swap
     * @throws NullPointerException if shuffle is null
     */
    public ByteArrayShuffleModification(byte[] shuffle) {
        super();
        this.shuffle = Objects.requireNonNull(shuffle, "Shuffle pattern must not be null");
    }

    /**
     * Copy constructor creating a deep copy of the provided modification.
     *
     * <p>This creates a new instance with a copy of the shuffle pattern array.
     *
     * @param other The modification to copy
     */
    public ByteArrayShuffleModification(ByteArrayShuffleModification other) {
        super(other);
        shuffle = other.shuffle != null ? other.shuffle.clone() : null;
    }

    /**
     * Creates a deep copy of this modification.
     *
     * @return A new instance with a copy of the shuffle pattern
     */
    @Override
    public ByteArrayShuffleModification createCopy() {
        return new ByteArrayShuffleModification(this);
    }

    /**
     * Modifies the input by shuffling bytes according to the specified pattern.
     *
     * <p>This method creates a copy of the input array and performs a series of swap operations as
     * defined by the shuffle pattern. The original array is not modified, preserving immutability.
     *
     * <p>The implementation automatically adapts to the input array size:
     *
     * <ul>
     *   <li>For arrays with more than 255 elements, it uses 16-bit indices (4 bytes per swap)
     *   <li>For smaller arrays, it uses 8-bit indices (2 bytes per swap)
     * </ul>
     *
     * <p>Each index in the shuffle pattern is taken modulo the length of the input array to ensure
     * that all swaps are performed within bounds, even if the shuffle pattern was designed for
     * arrays of a different size.
     *
     * @param input The byte array to shuffle
     * @return A new byte array containing the same elements as the input but in shuffled order,
     *     or null if the input is null
     */
    @Override
    protected byte[] modifyImplementationHook(byte[] input) {
        if (input == null) {
            return null;
        }
        byte[] result = input.clone();
        int size = input.length;
        if (size > 255) {
            for (int i = 0; i < shuffle.length - 3; i += 4) {
                // Combine two consecutive bytes to form 16-bit values
                int p1 = ((shuffle[i] & 0xff) << 8 | shuffle[i + 1] & 0xff) % size;
                int p2 = ((shuffle[i + 2] & 0xff) << 8 | shuffle[i + 3] & 0xff) % size;
                byte tmp = result[p1];
                result[p1] = result[p2];
                result[p2] = tmp;
            }
        } else if (size > 0) {
            for (int i = 0; i < shuffle.length - 1; i += 2) {
                int p1 = (shuffle[i] & 0xff) % size;
                int p2 = (shuffle[i + 1] & 0xff) % size;
                byte tmp = result[p1];
                result[p1] = result[p2];
                result[p2] = tmp;
            }
        }
        return result;
    }

    /**
     * Gets the shuffle pattern array.
     *
     * @return The byte array containing the shuffle pattern
     */
    public byte[] getShuffle() {
        return shuffle;
    }

    /**
     * Sets the shuffle pattern array.
     *
     * @param shuffle The byte array containing the shuffle pattern defining which indices to swap
     * @throws NullPointerException if shuffle is null
     */
    public void setShuffle(byte[] shuffle) {
        this.shuffle = Objects.requireNonNull(shuffle, "Shuffle pattern must not be null");
    }

    /**
     * Computes a hash code for this shuffle modification.
     *
     * <p>The hash code is based solely on the shuffle pattern array.
     *
     * @return A hash code value for this object
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Arrays.hashCode(shuffle);
        return hash;
    }

    /**
     * Compares this shuffle modification with another object for equality.
     *
     * <p>Two ByteArrayShuffleModification objects are considered equal if they have identical
     * shuffle pattern arrays.
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
        ByteArrayShuffleModification other = (ByteArrayShuffleModification) obj;
        return Arrays.equals(shuffle, other.shuffle);
    }

    /**
     * Returns a string representation of this shuffle modification.
     *
     * <p>The string includes the shuffle pattern array in hexadecimal format.
     *
     * @return A string representation of this object
     */
    @Override
    public String toString() {
        return "ByteArrayShuffleModification{"
                + "shuffle="
                + ArrayConverter.bytesToHexString(shuffle)
                + '}';
    }
}
