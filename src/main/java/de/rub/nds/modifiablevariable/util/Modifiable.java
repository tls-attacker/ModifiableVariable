/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.util;

import de.rub.nds.modifiablevariable.VariableModification;
import de.rub.nds.modifiablevariable.biginteger.BigIntegerAddModification;
import de.rub.nds.modifiablevariable.biginteger.BigIntegerExplicitValueModification;
import de.rub.nds.modifiablevariable.biginteger.BigIntegerMultiplyModification;
import de.rub.nds.modifiablevariable.biginteger.BigIntegerShiftLeftModification;
import de.rub.nds.modifiablevariable.biginteger.BigIntegerShiftRightModification;
import de.rub.nds.modifiablevariable.biginteger.BigIntegerSubtractModification;
import de.rub.nds.modifiablevariable.biginteger.BigIntegerXorModification;
import de.rub.nds.modifiablevariable.biginteger.ModifiableBigInteger;
import de.rub.nds.modifiablevariable.bool.BooleanExplicitValueModification;
import de.rub.nds.modifiablevariable.bool.BooleanToggleModification;
import de.rub.nds.modifiablevariable.bool.ModifiableBoolean;
import de.rub.nds.modifiablevariable.bytearray.ByteArrayAppendValueModification;
import de.rub.nds.modifiablevariable.bytearray.ByteArrayDeleteModification;
import de.rub.nds.modifiablevariable.bytearray.ByteArrayDuplicateModification;
import de.rub.nds.modifiablevariable.bytearray.ByteArrayExplicitValueModification;
import de.rub.nds.modifiablevariable.bytearray.ByteArrayInsertValueModification;
import de.rub.nds.modifiablevariable.bytearray.ByteArrayPrependValueModification;
import de.rub.nds.modifiablevariable.bytearray.ByteArrayShuffleModification;
import de.rub.nds.modifiablevariable.bytearray.ByteArrayXorModification;
import de.rub.nds.modifiablevariable.bytearray.ModifiableByteArray;
import de.rub.nds.modifiablevariable.integer.IntegerAddModification;
import de.rub.nds.modifiablevariable.integer.IntegerExplicitValueModification;
import de.rub.nds.modifiablevariable.integer.IntegerMultiplyModification;
import de.rub.nds.modifiablevariable.integer.IntegerShiftLeftModification;
import de.rub.nds.modifiablevariable.integer.IntegerShiftRightModification;
import de.rub.nds.modifiablevariable.integer.IntegerSubtractModification;
import de.rub.nds.modifiablevariable.integer.IntegerSwapEndianModification;
import de.rub.nds.modifiablevariable.integer.IntegerXorModification;
import de.rub.nds.modifiablevariable.integer.ModifiableInteger;
import de.rub.nds.modifiablevariable.longint.LongAddModification;
import de.rub.nds.modifiablevariable.longint.LongExplicitValueModification;
import de.rub.nds.modifiablevariable.longint.LongMultiplyModification;
import de.rub.nds.modifiablevariable.longint.LongShiftLeftModification;
import de.rub.nds.modifiablevariable.longint.LongShiftRightModification;
import de.rub.nds.modifiablevariable.longint.LongSubtractModification;
import de.rub.nds.modifiablevariable.longint.LongSwapEndianModification;
import de.rub.nds.modifiablevariable.longint.LongXorModification;
import de.rub.nds.modifiablevariable.longint.ModifiableLong;
import de.rub.nds.modifiablevariable.singlebyte.ByteAddModification;
import de.rub.nds.modifiablevariable.singlebyte.ByteExplicitValueModification;
import de.rub.nds.modifiablevariable.singlebyte.ByteSubtractModification;
import de.rub.nds.modifiablevariable.singlebyte.ByteXorModification;
import de.rub.nds.modifiablevariable.singlebyte.ModifiableByte;
import de.rub.nds.modifiablevariable.string.ModifiableString;
import de.rub.nds.modifiablevariable.string.StringAppendValueModification;
import de.rub.nds.modifiablevariable.string.StringExplicitValueModification;
import de.rub.nds.modifiablevariable.string.StringInsertValueModification;
import de.rub.nds.modifiablevariable.string.StringPrependValueModification;
import java.math.BigInteger;

@SuppressWarnings("unused")
public final class Modifiable {

    private Modifiable() {
        super();
    }

    private static ModifiableByteArray getModifiableByteArrayWithModification(
            VariableModification<byte[]> modification) {
        ModifiableByteArray modifiableByteArray = new ModifiableByteArray();
        modifiableByteArray.setModifications(modification);
        return modifiableByteArray;
    }

    private static ModifiableByte getModifiableByteWithModification(
            VariableModification<Byte> modification) {
        ModifiableByte modifiableByte = new ModifiableByte();
        modifiableByte.setModifications(modification);
        return modifiableByte;
    }

    private static ModifiableInteger getModifiableIntegerWithModification(
            VariableModification<Integer> modification) {
        ModifiableInteger modifiableInteger = new ModifiableInteger();
        modifiableInteger.setModifications(modification);
        return modifiableInteger;
    }

    private static ModifiableBigInteger getModifiableBigIntegerWithModification(
            VariableModification<BigInteger> modification) {
        ModifiableBigInteger modifiableBigInteger = new ModifiableBigInteger();
        modifiableBigInteger.setModifications(modification);
        return modifiableBigInteger;
    }

    private static ModifiableLong getModifiableLongWithModification(
            VariableModification<Long> modification) {
        ModifiableLong modifiableLong = new ModifiableLong();
        modifiableLong.setModifications(modification);
        return modifiableLong;
    }

    private static ModifiableBoolean getModifiableBooleanWithModification(
            VariableModification<Boolean> modification) {
        ModifiableBoolean modifiableBoolean = new ModifiableBoolean();
        modifiableBoolean.setModifications(modification);
        return modifiableBoolean;
    }

    private static ModifiableString getModifiableStringWithModification(
            VariableModification<String> modification) {
        ModifiableString modifiableString = new ModifiableString();
        modifiableString.setModifications(modification);
        return modifiableString;
    }

    public static ModifiableByteArray prepend(byte[] perpendValue) {
        return getModifiableByteArrayWithModification(
                new ByteArrayPrependValueModification(perpendValue));
    }

    public static ModifiableString prepend(String perpendValue) {
        return getModifiableStringWithModification(
                new StringPrependValueModification(perpendValue));
    }

    public static ModifiableByteArray append(byte[] appendValue) {
        return getModifiableByteArrayWithModification(
                new ByteArrayAppendValueModification(appendValue));
    }

    public static ModifiableString append(String appendValue) {
        return getModifiableStringWithModification(new StringAppendValueModification(appendValue));
    }

    public static ModifiableByteArray explicit(byte[] explicitValue) {
        return getModifiableByteArrayWithModification(
                new ByteArrayExplicitValueModification(explicitValue));
    }

    public static ModifiableByte explicit(Byte explicitValue) {
        return getModifiableByteWithModification(new ByteExplicitValueModification(explicitValue));
    }

    public static ModifiableInteger explicit(Integer explicitValue) {
        return getModifiableIntegerWithModification(
                new IntegerExplicitValueModification(explicitValue));
    }

    public static ModifiableBigInteger explicit(BigInteger explicitValue) {
        return getModifiableBigIntegerWithModification(
                new BigIntegerExplicitValueModification(explicitValue));
    }

    public static ModifiableLong explicit(Long explicitValue) {
        return getModifiableLongWithModification(new LongExplicitValueModification(explicitValue));
    }

    public static ModifiableBoolean explicit(Boolean explicitValue) {
        return getModifiableBooleanWithModification(
                new BooleanExplicitValueModification(explicitValue));
    }

    public static ModifiableString explicit(String explicitValue) {
        return getModifiableStringWithModification(
                new StringExplicitValueModification(explicitValue));
    }

    public static ModifiableByteArray insert(byte[] insertValue, int position) {
        return getModifiableByteArrayWithModification(
                new ByteArrayInsertValueModification(insertValue, position));
    }

    public static ModifiableString insert(String insertValue, int position) {
        return getModifiableStringWithModification(
                new StringInsertValueModification(insertValue, position));
    }

    public static ModifiableByteArray xor(byte[] xor, int position) {
        return getModifiableByteArrayWithModification(new ByteArrayXorModification(xor, position));
    }

    public static ModifiableByte xor(Byte xor) {
        return getModifiableByteWithModification(new ByteXorModification(xor));
    }

    public static ModifiableInteger xor(Integer xor) {
        return getModifiableIntegerWithModification(new IntegerXorModification(xor));
    }

    public static ModifiableBigInteger xor(BigInteger xor) {
        return getModifiableBigIntegerWithModification(new BigIntegerXorModification(xor));
    }

    public static ModifiableLong xor(Long xor) {
        return getModifiableLongWithModification(new LongXorModification(xor));
    }

    public static ModifiableInteger swapEndianIntger() {
        return getModifiableIntegerWithModification(new IntegerSwapEndianModification());
    }

    public static ModifiableLong swapEndianLong() {
        return getModifiableLongWithModification(new LongSwapEndianModification());
    }

    public static ModifiableByte add(Byte summand) {
        return getModifiableByteWithModification(new ByteAddModification(summand));
    }

    public static ModifiableInteger add(Integer summand) {
        return getModifiableIntegerWithModification(new IntegerAddModification(summand));
    }

    public static ModifiableBigInteger add(BigInteger summand) {
        return getModifiableBigIntegerWithModification(new BigIntegerAddModification(summand));
    }

    public static ModifiableLong add(Long summand) {
        return getModifiableLongWithModification(new LongAddModification(summand));
    }

    public static ModifiableByte sub(Byte subtrahend) {
        return getModifiableByteWithModification(new ByteSubtractModification(subtrahend));
    }

    public static ModifiableInteger sub(Integer subtrahend) {
        return getModifiableIntegerWithModification(new IntegerSubtractModification(subtrahend));
    }

    public static ModifiableBigInteger sub(BigInteger subtrahend) {
        return getModifiableBigIntegerWithModification(
                new BigIntegerSubtractModification(subtrahend));
    }

    public static ModifiableLong sub(Long subtrahend) {
        return getModifiableLongWithModification(new LongSubtractModification(subtrahend));
    }

    public static ModifiableByteArray delete(int startPosition, int count) {
        return getModifiableByteArrayWithModification(
                new ByteArrayDeleteModification(startPosition, count));
    }

    public static ModifiableByteArray shuffle(byte[] shuffle) {
        return getModifiableByteArrayWithModification(new ByteArrayShuffleModification(shuffle));
    }

    public static ModifiableByteArray duplicate() {
        return getModifiableByteArrayWithModification(new ByteArrayDuplicateModification());
    }

    public static ModifiableBoolean toggle() {
        return getModifiableBooleanWithModification(new BooleanToggleModification());
    }

    public static ModifiableBigInteger shiftLeftBigInteger(Integer shift) {
        return getModifiableBigIntegerWithModification(new BigIntegerShiftLeftModification(shift));
    }

    public static ModifiableInteger shiftLeft(Integer shift) {
        return getModifiableIntegerWithModification(new IntegerShiftLeftModification(shift));
    }

    public static ModifiableLong shiftLeftLong(Integer shift) {
        return getModifiableLongWithModification(new LongShiftLeftModification(shift));
    }

    public static ModifiableBigInteger shiftRightBigInteger(Integer shift) {
        return getModifiableBigIntegerWithModification(new BigIntegerShiftRightModification(shift));
    }

    public static ModifiableInteger shiftRight(Integer shift) {
        return getModifiableIntegerWithModification(new IntegerShiftRightModification(shift));
    }

    public static ModifiableLong shiftRightLong(Integer shift) {
        return getModifiableLongWithModification(new LongShiftRightModification(shift));
    }

    public static ModifiableBigInteger multiplyBigInteger(BigInteger factor) {
        return getModifiableBigIntegerWithModification(new BigIntegerMultiplyModification(factor));
    }

    public static ModifiableInteger multiply(Integer factor) {
        return getModifiableIntegerWithModification(new IntegerMultiplyModification(factor));
    }

    public static ModifiableLong multiply(Long factor) {
        return getModifiableLongWithModification(new LongMultiplyModification(factor));
    }
}
