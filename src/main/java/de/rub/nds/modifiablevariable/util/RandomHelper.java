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
 * Utility class for obtaining and managing random number generators.
 *
 * <p>This class provides access to a singleton Random instance with a fixed seed (0), ensuring
 * reproducible "random" behavior across test runs. It also provides access to specialized random
 * number generators used for testing purposes.
 *
 * <p>The fixed seed ensures that any test using this generator will produce consistent results
 * across multiple runs, which is essential for deterministic and reproducible testing.
 */
public final class RandomHelper {

    /** Singleton Random instance with a fixed seed */
    private static Random random;

    /**
     * Gets the singleton Random instance with a fixed seed of 0.
     *
     * <p>The fixed seed ensures reproducible "random" behavior across test runs. If the Random
     * instance hasn't been initialized yet, this method initializes it.
     *
     * @return A Random instance with a fixed seed of 0
     */
    public static Random getRandom() {
        if (random == null) {
            random = new Random(0);
        }
        return random;
    }

    /**
     * Gets a BadRandom instance that can be used for security testing.
     *
     * <p>BadRandom extends SecureRandom but uses the deterministic Random internally, making it
     * predictable while still conforming to the SecureRandom API. This is useful for testing
     * cryptographic protocols with controlled "randomness".
     *
     * @return A BadRandom instance that uses the singleton Random internally
     */
    public static BadRandom getBadSecureRandom() {
        return new BadRandom(getRandom(), null);
    }

    /**
     * Sets the singleton Random instance to a specific Random object.
     *
     * <p>This method allows for replacing the default Random instance with a custom one, which can
     * be useful for testing with different seeds or alternative random number generators.
     *
     * @param random The Random instance to use as the singleton
     */
    public static void setRandom(Random random) {
        RandomHelper.random = random;
    }

    /** Private constructor to prevent instantiation of this utility class. */
    private RandomHelper() {
        super();
    }
}
