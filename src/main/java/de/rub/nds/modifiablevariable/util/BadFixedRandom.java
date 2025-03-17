/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.util;

import java.util.Random;

/**
 * A deterministic random number generator that always returns the same byte value.
 *
 * <p>This class extends Java's {@link Random} class but overrides its behavior to always return a
 * fixed byte value instead of generating truly random values. It is designed specifically for
 * testing scenarios where predictable, reproducible "random" values are required.
 *
 * <p>Note that this implementation only overrides the {@link #nextBytes(byte[])} method; other
 * methods from the {@link Random} superclass are not overridden and may still produce pseudo-random
 * values that don't match the fixed byte value.
 */
public class BadFixedRandom extends Random {

    /** The fixed byte value that will be returned for all "random" bytes */
    private final byte retVal;

    /**
     * Creates a new fixed random generator with the specified return value.
     *
     * @param retVal The byte value that will be returned by this generator
     */
    public BadFixedRandom(byte retVal) {
        super();
        this.retVal = retVal;
    }

    /**
     * Fills a user-supplied byte array with the fixed byte given at object initialization.
     *
     * <p>Instead of generating random values, this method simply fills the entire array with the
     * fixed byte value specified in the constructor.
     *
     * <p>The number of "random" bytes produced is equal to the length of the byte array.
     *
     * @param bytes The byte array to be filled with the fixed value
     */
    @Override
    public void nextBytes(byte[] bytes) {
        for (int i = 0, len = bytes.length; i < len; ) {
            bytes[i++] = retVal;
        }
    }
}
