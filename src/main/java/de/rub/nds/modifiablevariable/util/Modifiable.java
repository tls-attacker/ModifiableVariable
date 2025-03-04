/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.util;

import de.rub.nds.modifiablevariable.VariableModification;
import de.rub.nds.modifiablevariable.biginteger.BigIntegerModificationFactory;
import de.rub.nds.modifiablevariable.biginteger.ModifiableBigInteger;
import de.rub.nds.modifiablevariable.bool.BooleanModificationFactory;
import de.rub.nds.modifiablevariable.bool.ModifiableBoolean;
import de.rub.nds.modifiablevariable.bytearray.ByteArrayModificationFactory;
import de.rub.nds.modifiablevariable.bytearray.ModifiableByteArray;
import de.rub.nds.modifiablevariable.integer.IntegerModificationFactory;
import de.rub.nds.modifiablevariable.integer.ModifiableInteger;
import de.rub.nds.modifiablevariable.longint.LongModificationFactory;
import de.rub.nds.modifiablevariable.longint.ModifiableLong;
import de.rub.nds.modifiablevariable.singlebyte.ByteModificationFactory;
import de.rub.nds.modifiablevariable.singlebyte.ModifiableByte;
import de.rub.nds.modifiablevariable.string.ModifiableString;
import de.rub.nds.modifiablevariable.string.StringModificationFactory;
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
                ByteArrayModificationFactory.prependValue(perpendValue));
    }

    public static ModifiableString prepend(String perpendValue) {
        return getModifiableStringWithModification(
                StringModificationFactory.prependValue(perpendValue));
    }

    public static ModifiableByteArray append(byte[] appendValue) {
        return getModifiableByteArrayWithModification(
                ByteArrayModificationFactory.appendValue(appendValue));
    }

    public static ModifiableString append(String appendValue) {
        return getModifiableStringWithModification(
                StringModificationFactory.appendValue(appendValue));
    }

    public static ModifiableByteArray explicit(byte[] explicitValue) {
        return getModifiableByteArrayWithModification(
                ByteArrayModificationFactory.explicitValue(explicitValue));
    }

    public static ModifiableByte explicit(Byte explicitValue) {
        return getModifiableByteWithModification(
                ByteModificationFactory.explicitValue(explicitValue));
    }

    public static ModifiableInteger explicit(Integer explicitValue) {
        return getModifiableIntegerWithModification(
                IntegerModificationFactory.explicitValue(explicitValue));
    }

    public static ModifiableBigInteger explicit(BigInteger explicitValue) {
        return getModifiableBigIntegerWithModification(
                BigIntegerModificationFactory.explicitValue(explicitValue));
    }

    public static ModifiableLong explicit(Long explicitValue) {
        return getModifiableLongWithModification(
                LongModificationFactory.explicitValue(explicitValue));
    }

    public static ModifiableBoolean explicit(Boolean explicitValue) {
        return getModifiableBooleanWithModification(
                BooleanModificationFactory.explicitValue(explicitValue));
    }

    public static ModifiableString explicit(String explicitValue) {
        return getModifiableStringWithModification(
                StringModificationFactory.explicitValue(explicitValue));
    }

    public static ModifiableByteArray insert(byte[] insertValue, int position) {
        return getModifiableByteArrayWithModification(
                ByteArrayModificationFactory.insertValue(insertValue, position));
    }

    public static ModifiableString insert(String insertValue, int position) {
        return getModifiableStringWithModification(
                StringModificationFactory.insertValue(insertValue, position));
    }

    public static ModifiableByteArray xor(byte[] xor, int position) {
        return getModifiableByteArrayWithModification(
                ByteArrayModificationFactory.xor(xor, position));
    }

    public static ModifiableByte xor(Byte xor) {
        return getModifiableByteWithModification(ByteModificationFactory.xor(xor));
    }

    public static ModifiableInteger xor(Integer xor) {
        return getModifiableIntegerWithModification(IntegerModificationFactory.xor(xor));
    }

    public static ModifiableBigInteger xor(BigInteger xor) {
        return getModifiableBigIntegerWithModification(BigIntegerModificationFactory.xor(xor));
    }

    public static ModifiableLong xor(Long xor) {
        return getModifiableLongWithModification(LongModificationFactory.xor(xor));
    }

    public static ModifiableInteger swapEndianIntger() {
        return getModifiableIntegerWithModification(IntegerModificationFactory.swapEndian());
    }

    public static ModifiableLong swapEndianLong() {
        return getModifiableLongWithModification(LongModificationFactory.swapEndian());
    }

    public static ModifiableByte add(Byte summand) {
        return getModifiableByteWithModification(ByteModificationFactory.add(summand));
    }

    public static ModifiableInteger add(Integer summand) {
        return getModifiableIntegerWithModification(IntegerModificationFactory.add(summand));
    }

    public static ModifiableBigInteger add(BigInteger summand) {
        return getModifiableBigIntegerWithModification(BigIntegerModificationFactory.add(summand));
    }

    public static ModifiableLong add(Long summand) {
        return getModifiableLongWithModification(LongModificationFactory.add(summand));
    }

    public static ModifiableByte sub(Byte subtrahend) {
        return getModifiableByteWithModification(ByteModificationFactory.sub(subtrahend));
    }

    public static ModifiableInteger sub(Integer subtrahend) {
        return getModifiableIntegerWithModification(IntegerModificationFactory.sub(subtrahend));
    }

    public static ModifiableBigInteger sub(BigInteger subtrahend) {
        return getModifiableBigIntegerWithModification(
                BigIntegerModificationFactory.sub(subtrahend));
    }

    public static ModifiableLong sub(Long subtrahend) {
        return getModifiableLongWithModification(LongModificationFactory.sub(subtrahend));
    }

    public static ModifiableByteArray delete(int startPosition, int count) {
        return getModifiableByteArrayWithModification(
                ByteArrayModificationFactory.delete(startPosition, count));
    }

    public static ModifiableByteArray shuffle(byte[] shuffle) {
        return getModifiableByteArrayWithModification(
                ByteArrayModificationFactory.shuffle(shuffle));
    }

    public static ModifiableByteArray duplicate() {
        return getModifiableByteArrayWithModification(ByteArrayModificationFactory.duplicate());
    }

    public static ModifiableBoolean toggle() {
        return getModifiableBooleanWithModification(BooleanModificationFactory.toggle());
    }

    public static ModifiableBigInteger shiftLeftBigInteger(Integer shift) {
        return getModifiableBigIntegerWithModification(
                BigIntegerModificationFactory.shiftLeft(shift));
    }

    public static ModifiableInteger shiftLeft(Integer shift) {
        return getModifiableIntegerWithModification(IntegerModificationFactory.shiftLeft(shift));
    }

    public static ModifiableLong shiftLeftLong(Integer shift) {
        return getModifiableLongWithModification(LongModificationFactory.shiftLeft(shift));
    }

    public static ModifiableBigInteger shiftRightBigInteger(Integer shift) {
        return getModifiableBigIntegerWithModification(
                BigIntegerModificationFactory.shiftRight(shift));
    }

    public static ModifiableInteger shiftRight(Integer shift) {
        return getModifiableIntegerWithModification(IntegerModificationFactory.shiftRight(shift));
    }

    public static ModifiableLong shiftRightLong(Integer shift) {
        return getModifiableLongWithModification(LongModificationFactory.shiftRight(shift));
    }

    public static ModifiableBigInteger multiplyBigInteger(BigInteger factor) {
        return getModifiableBigIntegerWithModification(
                BigIntegerModificationFactory.multiply(factor));
    }

    public static ModifiableInteger multiply(Integer factor) {
        return getModifiableIntegerWithModification(IntegerModificationFactory.multiply(factor));
    }

    public static ModifiableLong multiply(Long factor) {
        return getModifiableLongWithModification(LongModificationFactory.multiply(factor));
    }
}
