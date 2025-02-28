/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.integer;

import de.rub.nds.modifiablevariable.VariableModification;

public final class IntegerModificationFactory {

    public static IntegerAddModification add(String summand) {
        return add(Integer.parseInt(summand));
    }

    public static IntegerAddModification add(Integer summand) {
        return new IntegerAddModification(summand);
    }

    public static IntegerShiftLeftModification shiftLeft(String shift) {
        return shiftLeft(Integer.parseInt(shift));
    }

    public static IntegerShiftLeftModification shiftLeft(Integer shift) {
        return new IntegerShiftLeftModification(shift);
    }

    public static IntegerShiftRightModification shiftRight(String shift) {
        return shiftRight(Integer.parseInt(shift));
    }

    public static IntegerShiftRightModification shiftRight(Integer shift) {
        return new IntegerShiftRightModification(shift);
    }

    public static VariableModification<Integer> sub(String subtrahend) {
        return sub(Integer.parseInt(subtrahend));
    }

    public static VariableModification<Integer> sub(Integer subtrahend) {
        return new IntegerSubtractModification(subtrahend);
    }

    public static VariableModification<Integer> xor(String xor) {
        return xor(Integer.parseInt(xor));
    }

    public static VariableModification<Integer> xor(Integer xor) {
        return new IntegerXorModification(xor);
    }

    public static VariableModification<Integer> swapEndian() {
        return new IntegerSwapEndianModification();
    }

    public static VariableModification<Integer> explicitValue(String value) {
        return explicitValue(Integer.parseInt(value));
    }

    public static VariableModification<Integer> explicitValue(Integer value) {
        return new IntegerExplicitValueModification(value);
    }

    public static VariableModification<Integer> multiply(Integer value) {
        return new IntegerMultiplyModification(value);
    }

    private IntegerModificationFactory() {
        super();
    }
}
