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
import de.rub.nds.modifiablevariable.bool.BooleanExplicitValueModification;
import de.rub.nds.modifiablevariable.bool.BooleanToggleModification;
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
public class Modifiable {

    private static ModifiableByteArray getModifiableByteArrayWithModification(
            VariableModification<byte[]> modification) {
        ModifiableByteArray modifiableByteArray = new ModifiableByteArray();
        modifiableByteArray.setModification(modification);
        return modifiableByteArray;
    }

    private static ModifiableByte getModifiableByteWithModification(
            VariableModification<Byte> modification) {
        ModifiableByte modifiableByte = new ModifiableByte();
        modifiableByte.setModification(modification);
        return modifiableByte;
    }

    private static ModifiableInteger getModifiableIntegerWithModification(
            VariableModification<Integer> modification) {
        ModifiableInteger modifiableInteger = new ModifiableInteger();
        modifiableInteger.setModification(modification);
        return modifiableInteger;
    }

    private static ModifiableBigInteger getModifiableBigIntegerWithModification(
            VariableModification<BigInteger> modification) {
        ModifiableBigInteger modifiableBigInteger = new ModifiableBigInteger();
        modifiableBigInteger.setModification(modification);
        return modifiableBigInteger;
    }

    private static ModifiableLong getModifiableLongWithModification(
            VariableModification<Long> modification) {
        ModifiableLong modifiableLong = new ModifiableLong();
        modifiableLong.setModification(modification);
        return modifiableLong;
    }

    private static ModifiableBoolean getModifiableBooleanWithModification(
            VariableModification<Boolean> modification) {
        ModifiableBoolean modifiableBoolean = new ModifiableBoolean();
        modifiableBoolean.setModification(modification);
        return modifiableBoolean;
    }

    private static ModifiableString getModifiableStringWithModification(
            VariableModification<String> modification) {
        ModifiableString modifiableString = new ModifiableString();
        modifiableString.setModification(modification);
        return modifiableString;
    }

    public static ModifiableString prepend(final String s) {
        return getModifiableStringWithModification(StringModificationFactory.prependValue(s));
    }

    public static ModifiableString append(final String s) {
        return getModifiableStringWithModification(StringModificationFactory.appendValue(s));
    }

    public static ModifiableByteArray explicit(byte[] b) {
        return getModifiableByteArrayWithModification(
                ByteArrayModificationFactory.explicitValue(b));
    }

    public static ModifiableByte explicit(Byte b) {
        return getModifiableByteWithModification(ByteModificationFactory.explicitValue(b));
    }

    public static ModifiableInteger explicit(Integer i) {
        return getModifiableIntegerWithModification(IntegerModificationFactory.explicitValue(i));
    }

    public static ModifiableBigInteger explicit(BigInteger i) {
        return getModifiableBigIntegerWithModification(
                BigIntegerModificationFactory.explicitValue(i));
    }

    public static ModifiableLong explicit(Long l) {
        return getModifiableLongWithModification(LongModificationFactory.explicitValue(l));
    }

    public static ModifiableBoolean explicit(Boolean b) {
        return getModifiableBooleanWithModification(new BooleanExplicitValueModification(b));
    }

    public static ModifiableString explicit(String s) {
        return getModifiableStringWithModification(StringModificationFactory.explicitValue(s));
    }
    public static ModifiableString insert(String s, int position) {
        return getModifiableStringWithModification(StringModificationFactory.insertValue(s, position));
    }


    public static ModifiableByteArray xor(byte[] b, int position) {
        return getModifiableByteArrayWithModification(
                ByteArrayModificationFactory.xor(b, position));
    }

    public static ModifiableByte xor(Byte b) {
        return getModifiableByteWithModification(ByteModificationFactory.xor(b));
    }

    public static ModifiableInteger xor(Integer i) {
        return getModifiableIntegerWithModification(IntegerModificationFactory.xor(i));
    }

    public static ModifiableBigInteger xor(BigInteger i) {
        return getModifiableBigIntegerWithModification(BigIntegerModificationFactory.xor(i));
    }

    public static ModifiableLong xor(Long l) {
        return getModifiableLongWithModification(LongModificationFactory.xor(l));
    }

    public static ModifiableByte add(Byte b) {
        return getModifiableByteWithModification(ByteModificationFactory.add(b));
    }

    public static ModifiableInteger add(Integer i) {
        return getModifiableIntegerWithModification(IntegerModificationFactory.add(i));
    }

    public static ModifiableBigInteger add(BigInteger i) {
        return getModifiableBigIntegerWithModification(BigIntegerModificationFactory.add(i));
    }

    public static ModifiableLong add(Long l) {
        return getModifiableLongWithModification(LongModificationFactory.add(l));
    }

    public static ModifiableByte sub(Byte b) {
        return getModifiableByteWithModification(ByteModificationFactory.sub(b));
    }

    public static ModifiableInteger sub(Integer i) {
        return getModifiableIntegerWithModification(IntegerModificationFactory.sub(i));
    }

    public static ModifiableBigInteger sub(BigInteger i) {
        return getModifiableBigIntegerWithModification(BigIntegerModificationFactory.sub(i));
    }

    public static ModifiableLong sub(Long l) {
        return getModifiableLongWithModification(LongModificationFactory.sub(l));
    }

    public static ModifiableByteArray insert(byte[] b, int position) {
        return getModifiableByteArrayWithModification(
                ByteArrayModificationFactory.insert(b, position));
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
        return getModifiableBooleanWithModification(new BooleanToggleModification());
    }

    public static ModifiableBigInteger shiftLeftBigInteger(Integer i) {
        return getModifiableBigIntegerWithModification(BigIntegerModificationFactory.shiftLeft(i));
    }

    public static ModifiableInteger shiftLeft(Integer i) {
        return getModifiableIntegerWithModification(IntegerModificationFactory.shiftLeft(i));
    }

    public static ModifiableBigInteger shiftRightBigInteger(Integer i) {
        return getModifiableBigIntegerWithModification(BigIntegerModificationFactory.shiftRight(i));
    }

    public static ModifiableBigInteger multiplyBigInteger(BigInteger i) {
        return getModifiableBigIntegerWithModification(BigIntegerModificationFactory.multiply(i));
    }

    public static ModifiableInteger shiftRight(Integer i) {
        return getModifiableIntegerWithModification(IntegerModificationFactory.shiftRight(i));
    }
}
