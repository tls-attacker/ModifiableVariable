/**
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Copyright 2014-2022 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 *
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.modifiablevariable.ulong;

import com.google.common.primitives.UnsignedLong;
import de.rub.nds.modifiablevariable.VariableModification;
import de.rub.nds.modifiablevariable.util.RandomHelper;

import java.util.Random;

public class UnsignedLongModificationFactory {

    private static final int MODIFICATION_COUNT = 4;

    private static final int MAX_MODIFICATION_VALUE = 32000;

    public static UnsignedLongAddModification add(final String summand) {
        return new UnsignedLongAddModification(UnsignedLong.valueOf(summand));
    }

    public static UnsignedLongAddModification add(final Long summand) {
        return new UnsignedLongAddModification(summand);
    }

    public static UnsignedLongAddModification add(final UnsignedLong summand) {
        return new UnsignedLongAddModification(summand);
    }

    public static UnsignedLongSubtractModification sub(final String subtrahend) {
        return new UnsignedLongSubtractModification(UnsignedLong.valueOf(subtrahend));
    }

    public static UnsignedLongSubtractModification sub(final Long subtrahend) {
        return new UnsignedLongSubtractModification(subtrahend);
    }

    public static UnsignedLongSubtractModification sub(final UnsignedLong subtrahend) {
        return new UnsignedLongSubtractModification(subtrahend);
    }

    public static UnsignedLongXorModification xor(final String xor) {
        return new UnsignedLongXorModification(UnsignedLong.valueOf(xor));
    }

    public static UnsignedLongXorModification xor(final Long xor) {
        return new UnsignedLongXorModification(xor);
    }

    public static UnsignedLongXorModification xor(final UnsignedLong xor) {
        return new UnsignedLongXorModification(xor);
    }

    public static UnsignedLongExplicitValueModification explicitValue(final String value) {
        return new UnsignedLongExplicitValueModification(UnsignedLong.valueOf(value));
    }

    public static UnsignedLongExplicitValueModification explicitValue(final Long value) {
        return new UnsignedLongExplicitValueModification(value);
    }

    public static UnsignedLongExplicitValueModification explicitValue(final UnsignedLong value) {
        return new UnsignedLongExplicitValueModification(value);
    }

    public static VariableModification<UnsignedLong> createRandomModification() {
        Random random = RandomHelper.getRandom();
        int r = random.nextInt(MODIFICATION_COUNT);
        int modification = random.nextInt(MAX_MODIFICATION_VALUE);
        switch (r) {
            case 0:
                return add((long) modification);
            case 1:
                return sub((long) modification);
            case 2:
                return xor((long) modification);
            case 3:
                return explicitValue((long) modification);
            default:
                return null;
        }
    }

    private UnsignedLongModificationFactory() {
    }
}
