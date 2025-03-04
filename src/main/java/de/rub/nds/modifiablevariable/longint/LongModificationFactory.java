/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.longint;

import de.rub.nds.modifiablevariable.VariableModification;

public final class LongModificationFactory {

    public static LongAddModification add(String summand) {
        return add(Long.parseLong(summand));
    }

    public static LongAddModification add(Long summand) {
        return new LongAddModification(summand);
    }

    public static VariableModification<Long> sub(String subtrahend) {
        return sub(Long.parseLong(subtrahend));
    }

    public static VariableModification<Long> sub(Long subtrahend) {
        return new LongSubtractModification(subtrahend);
    }

    public static VariableModification<Long> xor(String xor) {
        return xor(Long.parseLong(xor));
    }

    public static VariableModification<Long> xor(Long xor) {
        return new LongXorModification(xor);
    }

    public static VariableModification<Long> swapEndian() {
        return new LongSwapEndianModification();
    }

    public static VariableModification<Long> explicitValue(String value) {
        return explicitValue(Long.parseLong(value));
    }

    public static VariableModification<Long> explicitValue(Long value) {
        return new LongExplicitValueModification(value);
    }

    public static VariableModification<Long> multiply(Long factor) {
        return new LongMultiplyModification(factor);
    }

    public static VariableModification<Long> shiftLeft(int shift) {
        return new LongShiftLeftModification(shift);
    }

    public static VariableModification<Long> shiftRight(int shift) {
        return new LongShiftRightModification(shift);
    }

    private LongModificationFactory() {
        super();
    }
}
