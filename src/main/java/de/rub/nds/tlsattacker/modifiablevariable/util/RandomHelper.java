/**
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Copyright 2014-2017 Ruhr University Bochum / Hackmanit GmbH
 *
 * Licensed under Apache License 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.tlsattacker.modifiablevariable.util;

import java.util.Random;

/**
 * @author Juraj Somorovsky <juraj.somorovsky@rub.de>
 */
public class RandomHelper {

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

    private RandomHelper() {
    }
}
