/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.util;

import java.util.Random;

public final class RandomHelper {

    private static Random random;

    public static Random getRandom() {
        if (random == null) {
            random = new Random(0);
        }
        return random;
    }

    public static BadRandom getBadSecureRandom() {
        return new BadRandom(getRandom(), null);
    }

    public static void setRandom(Random random) {
        RandomHelper.random = random;
    }

    private RandomHelper() {
        super();
    }
}
