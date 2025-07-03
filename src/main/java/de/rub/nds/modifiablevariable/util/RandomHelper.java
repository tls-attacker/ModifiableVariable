/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.util;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
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
     * <p><b>Note:</b> This method intentionally returns the mutable Random instance directly to
     * allow for flexible testing scenarios. The setRandom() method is also provided to replace the
     * instance entirely. This design is intentional for testing frameworks that need full control
     * over randomness.
     *
     * @return A Random instance with a fixed seed of 0
     */
    @SuppressFBWarnings(
            value = "MS_EXPOSE_REP",
            justification =
                    "Intentionally exposing mutable static Random for testing flexibility. "
                            + "This class is designed for test environments where controlled randomness "
                            + "is required, and the ability to modify or replace the Random instance "
                            + "is a feature, not a bug.")
    public static synchronized Random getRandom() {
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
        return new BadRandom(getRandom());
    }

    /**
     * Sets the singleton Random instance to a specific Random object.
     *
     * <p>This method allows for replacing the default Random instance with a custom one, which can
     * be useful for testing with different seeds or alternative random number generators.
     *
     * @param randomInstance The Random instance to use as the singleton
     */
    @SuppressFBWarnings(
            value = "EI_EXPOSE_STATIC_REP2",
            justification =
                    "Intentionally allowing external Random instances to be set for testing flexibility. "
                            + "This class is designed for test environments where controlled randomness "
                            + "is required, and the ability to replace the Random instance "
                            + "is a feature, not a bug.")
    public static synchronized void setRandom(Random randomInstance) {
        random = randomInstance;
    }

    /** Private constructor to prevent instantiation of this utility class. */
    private RandomHelper() {
        super();
    }
}
