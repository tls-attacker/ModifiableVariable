/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.bytearray;

import de.rub.nds.modifiablevariable.VariableModification;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.Arrays;
import java.util.Objects;

/**
 * A modification that shuffles elements within a ModifiableByteArray.
 *
 * <p>This modification reorders elements within the byte array by performing a series of swap
 * operations based on a predefined shuffle pattern. It can be used to create data with the same
 * content but in a different order at runtime.
 *
 * <p>The transformation always produces a byte array with the same length and the same elements as
 * the input, just in a different order, making it valuable for testing protocol sensitivity to
 * element ordering without changing the actual content.
 *
 * @see ModifiableByteArray
 */
@XmlRootElement
public class ByteArrayShuffleModification extends VariableModification<byte[]> {

    /** The shuffle pattern defining which indices to swap */
    private int[] shuffle;

    /** Default constructor for serialization. */
    @SuppressWarnings("unused")
    private ByteArrayShuffleModification() {
        super();
    }

    /**
     * Creates a new shuffle modification with the specified shuffle pattern.
     *
     * @param shuffle The int array containing the shuffle pattern defining which indices to swap
     * @throws NullPointerException if shuffle is null
     */
    public ByteArrayShuffleModification(int[] shuffle) {
        super();
        Objects.requireNonNull(shuffle, "Shuffle pattern must not be null");
        this.shuffle = shuffle.clone();
    }

    /**
     * Copy constructor creating a deep copy of the provided modification.
     *
     * <p>This creates a new instance with a copy of the shuffle pattern array.
     *
     * @param other The modification to copy
     */
    public ByteArrayShuffleModification(ByteArrayShuffleModification other) {
        super();
        shuffle = other.shuffle.clone();
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
     * <p>Each index in the shuffle pattern is taken modulo the length of the input array to ensure
     * that all swaps are performed within bounds, even if the shuffle pattern was designed for
     * arrays of a different size.
     *
     * <p>The shuffle pattern is interpreted as pairs of indices to swap. For example, the pattern
     * [0, 1, 2, 3] would swap [0] with [1] and [2] with [3].
     *
     * @param input The byte array to shuffle
     * @return A new byte array containing the same elements as the input but in shuffled order, or
     *     null if the input is null
     */
    @Override
    protected byte[] modifyImplementationHook(byte[] input) {
        if (input == null) {
            return null;
        }
        if (input.length == 0) {
            return input;
        }
        byte[] result = input.clone();
        int size = input.length;
        for (int i = 0; i < shuffle.length - 1; i += 2) {
            int p1 = (shuffle[i] & 0xff) % size;
            int p2 = (shuffle[i + 1] & 0xff) % size;
            byte tmp = result[p1];
            result[p1] = result[p2];
            result[p2] = tmp;
        }
        return result;
    }

    /**
     * Gets the shuffle pattern array.
     *
     * @return The int array containing the shuffle pattern
     */
    public int[] getShuffle() {
        return shuffle == null ? null : shuffle.clone();
    }

    /**
     * Sets the shuffle pattern array.
     *
     * @param shuffle The int array containing the shuffle pattern defining which indices to swap
     * @throws NullPointerException if shuffle is null
     */
    public void setShuffle(int[] shuffle) {
        Objects.requireNonNull(shuffle, "Shuffle pattern must not be null");
        this.shuffle = shuffle.clone();
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
     * <p>The string includes the shuffle pattern as a list of ints.
     *
     * @return A string representation of this object
     */
    @Override
    public String toString() {
        return "ByteArrayShuffleModification{" + "shuffle=" + Arrays.toString(shuffle) + '}';
    }
}
