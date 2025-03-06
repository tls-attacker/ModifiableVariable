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
 * A modification that shuffles elements within a byte array.
 *
 * <p>This modification randomly reorders elements within the target byte array by performing a
 * series of swap operations. The swap locations are determined by a pre-defined byte array ({@code
 * shuffle}) that contains pairs of indices to be swapped.
 *
 * <p>The class supports two modes of operation:
 *
 * <ul>
 *   <li>For arrays with at most 255 elements, each consecutive pair of bytes in the shuffle array
 *       is interpreted as two indices to swap.
 *   <li>For larger arrays (more than 255 elements), each consecutive 4 bytes in the shuffle array
 *       are interpreted as two 16-bit indices to swap.
 * </ul>
 *
 * <p>This modification is useful for testing protocol implementations against malformed inputs
 * where the ordering of elements matters. The transformation always produces a byte array with the
 * same length and the same elements as the input, just in a different order.
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

    /**
     * Default constructor creating an uninitialized modification.
     *
     * <p>The shuffle array must be set via {@link #setShuffle(byte[])} before this modification can
     * be applied.
     */
    public ByteArrayShuffleModification() {
        super();
    }

    /**
     * Creates a new shuffle modification with the specified shuffle pattern.
     *
     * @param shuffle The byte array containing the shuffle pattern defining which indices to swap
     */
    public ByteArrayShuffleModification(byte[] shuffle) {
        super();
        this.shuffle = shuffle;
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
     * Applies the shuffle modification to the input byte array.
     *
     * <p>This method creates a copy of the input array and performs a series of swap operations as
     * defined by the shuffle pattern. The original array is not modified.
     *
     * <p>The implementation has two modes:
     *
     * <ul>
     *   <li>For arrays with more than 255 elements, it uses 16-bit indices (4 bytes per swap)
     *   <li>For smaller arrays, it uses 8-bit indices (2 bytes per swap)
     * </ul>
     *
     * @param input The byte array to shuffle
     * @return A shuffled copy of the input array, or null if the input is null
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
     */
    public void setShuffle(byte[] shuffle) {
        this.shuffle = shuffle;
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
