/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.util;

import java.util.Arrays;

/**
 * A wrapper for byte arrays that provides proper content-based equality and hash code.
 *
 * <p>Java arrays are reference types that do not override {@link Object#equals(Object)} or {@link
 * Object#hashCode()}, making them unsuitable for use as keys in hash-based collections or for
 * content-based equality comparisons. This class wraps a byte array and provides implementations of
 * {@code equals()} and {@code hashCode()} that compare and hash based on the content of the array,
 * not its reference.
 *
 * <p>This class is particularly useful in:
 *
 * <ul>
 *   <li>Hash-based collections when the byte array content is the key
 *   <li>Equality assertions where content equality is intended
 *   <li>Caching scenarios where byte array content should be the cache key
 * </ul>
 */
public class ComparableByteArray {

    /** The wrapped byte array */
    private byte[] array;

    /**
     * Creates a new comparable wrapper for the given byte array.
     *
     * @param array The byte array to wrap
     */
    public ComparableByteArray(byte[] array) {
        super();
        this.array = array;
    }

    /**
     * Gets the wrapped byte array.
     *
     * @return The wrapped byte array
     */
    public byte[] getArray() {
        return array;
    }

    /**
     * Sets the wrapped byte array.
     *
     * @param array The new byte array to wrap
     */
    public void setArray(byte[] array) {
        this.array = array;
    }

    /**
     * Computes a hash code for this object based on the content of the wrapped array.
     *
     * <p>This implementation uses {@link Arrays#hashCode(byte[])} to ensure that arrays with the
     * same content produce the same hash code.
     *
     * @return A hash code value based on the content of the wrapped array
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Arrays.hashCode(array);
        return hash;
    }

    /**
     * Compares this object with another for equality based on array content.
     *
     * <p>Two ComparableByteArray objects are considered equal if their wrapped arrays contain the
     * same sequence of bytes. This implementation uses {@link Arrays#equals(byte[], byte[])} for
     * the comparison.
     *
     * @param obj The object to compare with
     * @return {@code true} if the objects are equal based on array content, {@code false} otherwise
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
        ComparableByteArray other = (ComparableByteArray) obj;
        return Arrays.equals(array, other.array);
    }
}
