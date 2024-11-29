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
 * A fake random number generator for testing. This generator will always return the byte "retVal"
 * passed to the constructor.
 */
public class BadFixedRandom extends Random {

    byte retVal;

    public BadFixedRandom(byte retVal) {
        super();
        this.retVal = retVal;
    }

    /**
     * Fills a user-supplied byte array with the fixed byte given at object initialization. The
     * number of "random" bytes produced is equal to the length of the byte array.
     */
    @Override
    public void nextBytes(byte[] bytes) {
        for (int i = 0, len = bytes.length; i < len; ) {
            bytes[i++] = retVal;
        }
    }
}
