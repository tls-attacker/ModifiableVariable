/**
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Copyright 2014-2022 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 *
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.modifiablevariable.uinteger;

import com.google.common.primitives.UnsignedInteger;
import de.rub.nds.modifiablevariable.VariableModification;
import de.rub.nds.modifiablevariable.util.RandomHelper;

import java.util.Random;

public class UnsignedIntegerModificationFactory {

    private static final int MODIFICATION_COUNT = 6;

    private static final int MAX_MODIFICATION_VALUE = 32000;

    private static final int MAX_MODIFICATION_SHIFT_VALUE = 20;

    public static UnsignedIntegerAddModification add(final String summand) {
        return new UnsignedIntegerAddModification(UnsignedInteger.valueOf(summand));
    }

    public static UnsignedIntegerAddModification add(final Integer summand) {
        return new UnsignedIntegerAddModification(summand);
    }

    public static UnsignedIntegerAddModification add(final UnsignedInteger summand) {
        return new UnsignedIntegerAddModification(summand);
    }

    public static UnsignedIntegerSubtractModification sub(final String subtrahend) {
        return new UnsignedIntegerSubtractModification(UnsignedInteger.valueOf(subtrahend));
    }

    public static UnsignedIntegerSubtractModification sub(final Integer subtrahend) {
        return new UnsignedIntegerSubtractModification(subtrahend);
    }

    public static UnsignedIntegerSubtractModification sub(final UnsignedInteger subtrahend) {
        return new UnsignedIntegerSubtractModification(subtrahend);
    }

    public static UnsignedIntegerXorModification xor(final String xor) {
        return new UnsignedIntegerXorModification(UnsignedInteger.valueOf(xor));
    }

    public static UnsignedIntegerXorModification xor(final Integer xor) {
        return new UnsignedIntegerXorModification(xor);
    }

    public static UnsignedIntegerXorModification xor(final UnsignedInteger xor) {
        return new UnsignedIntegerXorModification(xor);
    }

    public static UnsignedIntegerExplicitValueModification explicitValue(final String value) {
        return new UnsignedIntegerExplicitValueModification(UnsignedInteger.valueOf(value));
    }

    public static UnsignedIntegerExplicitValueModification explicitValue(final Integer value) {
        return new UnsignedIntegerExplicitValueModification(value);
    }

    public static UnsignedIntegerExplicitValueModification explicitValue(final UnsignedInteger value) {
        return new UnsignedIntegerExplicitValueModification(value);
    }

    public static UnsignedIntegerShiftLeftModification shiftLeft(final String shift) {
        return new UnsignedIntegerShiftLeftModification(Integer.parseInt(shift));
    }

    public static UnsignedIntegerShiftLeftModification shiftLeft(final Integer shift) {
        return new UnsignedIntegerShiftLeftModification(shift);
    }

    public static UnsignedIntegerShiftRightModification shiftRight(final String shift) {
        return new UnsignedIntegerShiftRightModification(Integer.parseInt(shift));
    }

    public static UnsignedIntegerShiftRightModification shiftRight(final Integer shift) {
        return new UnsignedIntegerShiftRightModification(shift);
    }

    public static VariableModification<UnsignedInteger> createRandomModification() {
        Random random = RandomHelper.getRandom();
        int r = random.nextInt(MODIFICATION_COUNT);
        int modification = random.nextInt(MAX_MODIFICATION_VALUE);
        int shiftModification = random.nextInt(MAX_MODIFICATION_SHIFT_VALUE);
        switch (r) {
            case 0:
                return add(modification);
            case 1:
                return sub(modification);
            case 2:
                return xor(modification);
            case 3:
                return explicitValue(modification);
            case 4:
                return shiftLeft(shiftModification);
            case 5:
                return shiftRight(shiftModification);
            default:
                return null;
        }
    }

    private UnsignedIntegerModificationFactory() {
    }
}
