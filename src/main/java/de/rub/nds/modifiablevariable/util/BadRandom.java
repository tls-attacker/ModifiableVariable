/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.util;

import java.security.Provider;
import java.security.SecureRandom;
import java.security.SecureRandomSpi;
import java.util.Random;

/**
 * A deterministic, insecure implementation of SecureRandom for testing purposes.
 *
 * <p>This class extends SecureRandom but delegates all random number generation to a
 * java.util.Random instance, which is not cryptographically secure but is deterministic when
 * initialized with a fixed seed. This makes it useful for testing security protocols where
 * reproducible "randomness" is needed.
 *
 * <p><strong>WARNING:</strong> This class is NOT cryptographically secure and should NEVER be used
 * in production code or for any security-sensitive operations. It is designed exclusively for
 * testing and debugging purposes.
 *
 * <p>The primary use case is testing protocol implementations that rely on SecureRandom where we
 * need to control the "random" values to create reproducible test cases.
 */
public class BadRandom extends SecureRandom {

    /** The underlying Random instance that provides the "random" values */
    private Random random;

    /**
     * Default constructor that creates a BadRandom with a fixed seed of 0.
     *
     * <p>This ensures reproducible behavior across test runs.
     */
    public BadRandom() {
        super();
        random = new Random(0);
    }

    /**
     * Creates a BadRandom that delegates to the specified Random instance.
     *
     * <p>The seed parameter is ignored, as the provided Random instance is used directly.
     *
     * @param random The Random instance to use for generating values
     * @param seed Ignored parameter (for API compatibility)
     */
    public BadRandom(Random random, byte[] seed) {
        super();
        this.random = random;
    }

    /**
     * Creates a BadRandom with custom Random, SecureRandomSpi, and Provider.
     *
     * <p>Only the Random instance is actually used; the other parameters are ignored.
     *
     * @param random The Random instance to use for generating values
     * @param secureRandomSpi Ignored parameter (for API compatibility)
     * @param provider Ignored parameter (for API compatibility)
     */
    public BadRandom(Random random, SecureRandomSpi secureRandomSpi, Provider provider) {
        super();
        this.random = random;
    }

    /**
     * Generates a seed byte array using the underlying Random instance.
     *
     * @param numBytes The number of bytes to generate
     * @return A byte array of the specified length with "random" values
     */
    @Override
    public byte[] generateSeed(int numBytes) {
        byte[] ray = new byte[numBytes];
        random.nextBytes(ray);
        return ray;
    }

    /**
     * Fills the provided byte array with random bytes from the underlying Random instance.
     *
     * @param bytes The byte array to fill with random values
     */
    @Override
    public synchronized void nextBytes(byte[] bytes) {
        random.nextBytes(bytes);
    }

    /**
     * Sets a new seed for the random number generation by creating a new Random instance.
     *
     * @param seed The seed value for the new Random instance
     */
    @Override
    public void setSeed(long seed) {
        random = new Random(seed);
    }

    /**
     * Setting seed with a byte array is not supported in this implementation.
     *
     * @param seed The seed byte array (ignored)
     * @throws UnsupportedOperationException Always thrown when this method is called
     */
    @Override
    public synchronized void setSeed(byte[] seed) {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns a warning message instead of an algorithm name to make it clear that this is not a
     * secure random implementation.
     *
     * @return A warning message about the insecurity of this class
     */
    @Override
    public String getAlgorithm() {
        return "WARNING: We use a very insecure PRNG. THIS IS NOT A SECURE RANDOM OBJECT. USE FOR TESTING ONLY";
    }

    /**
     * Returns the next pseudorandom integer from the underlying Random instance.
     *
     * @return A pseudorandom integer
     */
    @Override
    public int nextInt() {
        return random.nextInt();
    }

    /**
     * Returns a pseudorandom integer between 0 (inclusive) and the specified bound (exclusive).
     *
     * @param bound The upper bound (exclusive) for the random value
     * @return A pseudorandom integer in the range [0, bound)
     */
    @Override
    public int nextInt(int bound) {
        return random.nextInt(bound);
    }

    /**
     * Returns the next pseudorandom long value from the underlying Random instance.
     *
     * @return A pseudorandom long value
     */
    @Override
    public long nextLong() {
        return random.nextLong();
    }

    /**
     * Returns the next pseudorandom boolean value from the underlying Random instance.
     *
     * @return A pseudorandom boolean value
     */
    @Override
    public boolean nextBoolean() {
        return random.nextBoolean();
    }

    /**
     * Returns the next pseudorandom float value from the underlying Random instance.
     *
     * @return A pseudorandom float in the range [0.0, 1.0)
     */
    @Override
    public float nextFloat() {
        return random.nextFloat();
    }

    /**
     * Returns the next pseudorandom double value from the underlying Random instance.
     *
     * @return A pseudorandom double in the range [0.0, 1.0)
     */
    @Override
    public double nextDouble() {
        return random.nextDouble();
    }

    /**
     * Returns the next pseudorandom, Gaussian ("normally") distributed double value from the
     * underlying Random instance.
     *
     * @return A pseudorandom double from a Gaussian distribution with mean 0.0 and standard
     *     deviation 1.0
     */
    @Override
    public synchronized double nextGaussian() {
        return random.nextGaussian();
    }
}
