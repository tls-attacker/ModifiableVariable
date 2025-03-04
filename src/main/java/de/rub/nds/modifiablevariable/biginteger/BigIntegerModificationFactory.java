/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.biginteger;

import de.rub.nds.modifiablevariable.VariableModification;
import java.math.BigInteger;

public final class BigIntegerModificationFactory {

    public static BigIntegerAddModification add(String summand) {
        return add(new BigInteger(summand));
    }

    public static BigIntegerAddModification add(BigInteger summand) {
        return new BigIntegerAddModification(summand);
    }

    public static BigIntegerShiftLeftModification shiftLeft(String shift) {
        return shiftLeft(Integer.parseInt(shift));
    }

    public static BigIntegerShiftLeftModification shiftLeft(Integer shift) {
        return new BigIntegerShiftLeftModification(shift);
    }

    public static BigIntegerShiftRightModification shiftRight(String shift) {
        return shiftRight(Integer.parseInt(shift));
    }

    public static BigIntegerShiftRightModification shiftRight(Integer shift) {
        return new BigIntegerShiftRightModification(shift);
    }

    public static BigIntegerMultiplyModification multiply(BigInteger factor) {
        return new BigIntegerMultiplyModification(factor);
    }

    public static VariableModification<BigInteger> sub(String subtrahend) {
        return sub(new BigInteger(subtrahend));
    }

    public static VariableModification<BigInteger> sub(BigInteger subtrahend) {
        return new BigIntegerSubtractModification(subtrahend);
    }

    public static VariableModification<BigInteger> xor(String xor) {
        return xor(new BigInteger(xor));
    }

    public static VariableModification<BigInteger> xor(BigInteger xor) {
        return new BigIntegerXorModification(xor);
    }

    public static VariableModification<BigInteger> explicitValue(String value) {
        return explicitValue(new BigInteger(value));
    }

    public static VariableModification<BigInteger> explicitValue(BigInteger value) {
        return new BigIntegerExplicitValueModification(value);
    }

    private BigIntegerModificationFactory() {
        super();
    }
}
