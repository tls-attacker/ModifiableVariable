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
import de.rub.nds.modifiablevariable.path.ModifiablePath;
import de.rub.nds.modifiablevariable.path.PathModificationFactory;
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

    private static ModifiablePath getModifiablePathWithModification(
            VariableModification<String> modification) {
        ModifiablePath modifiablePath = new ModifiablePath();
        modifiablePath.setModification(modification);
        return modifiablePath;
    }

    public static ModifiableBigInteger prepend(BigInteger i) {
        return getModifiableBigIntegerWithModification(
                BigIntegerModificationFactory.prependValue(i));
    }

    public static ModifiableByteArray prepend(byte[] b) {
        return getModifiableByteArrayWithModification(ByteArrayModificationFactory.prependValue(b));
    }

    public static ModifiableInteger prepend(Integer i) {
        return getModifiableIntegerWithModification(IntegerModificationFactory.prependValue(i));
    }

    public static ModifiableLong prepend(Long l) {
        return getModifiableLongWithModification(LongModificationFactory.prependValue(l));
    }

    public static ModifiableString prepend(String s) {
        return getModifiableStringWithModification(StringModificationFactory.prependValue(s));
    }

    public static ModifiablePath prependPath(String s) {
        return getModifiablePathWithModification(PathModificationFactory.prependValue(s));
    }

    public static ModifiableBigInteger append(BigInteger i) {
        return getModifiableBigIntegerWithModification(
                BigIntegerModificationFactory.appendValue(i));
    }

    public static ModifiableByteArray append(byte[] b) {
        return getModifiableByteArrayWithModification(ByteArrayModificationFactory.appendValue(b));
    }

    public static ModifiableInteger append(Integer i) {
        return getModifiableIntegerWithModification(IntegerModificationFactory.appendValue(i));
    }

    public static ModifiableLong append(Long l) {
        return getModifiableLongWithModification(LongModificationFactory.appendValue(l));
    }

    public static ModifiableString append(String s) {
        return getModifiableStringWithModification(StringModificationFactory.appendValue(s));
    }

    public static ModifiablePath appendPath(String s) {
        return getModifiablePathWithModification(PathModificationFactory.appendValue(s));
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
        return getModifiableBooleanWithModification(BooleanModificationFactory.explicitValue(b));
    }

    public static ModifiableString explicit(String s) {
        return getModifiableStringWithModification(StringModificationFactory.explicitValue(s));
    }

    public static ModifiableBigInteger insert(BigInteger i, int position) {
        return getModifiableBigIntegerWithModification(
                BigIntegerModificationFactory.insertValue(i, position));
    }

    public static ModifiableByteArray insert(byte[] b, int position) {
        return getModifiableByteArrayWithModification(
                ByteArrayModificationFactory.insertValue(b, position));
    }

    public static ModifiableInteger insert(Integer i, int position) {
        return getModifiableIntegerWithModification(
                IntegerModificationFactory.insertValue(i, position));
    }

    public static ModifiableLong insert(Long l, int position) {
        return getModifiableLongWithModification(LongModificationFactory.insertValue(l, position));
    }

    public static ModifiableString insert(String s, int position) {
        return getModifiableStringWithModification(
                StringModificationFactory.insertValue(s, position));
    }

    public static ModifiablePath insertPath(String s, int position) {
        return getModifiablePathWithModification(PathModificationFactory.insertValue(s, position));
    }

    public static ModifiablePath insertDirectoryTraversal(int count, int position) {
        return getModifiablePathWithModification(
                PathModificationFactory.insertDirectoryTraversal(count, position));
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

    public static ModifiablePath toggleRoot() {
        return getModifiablePathWithModification(PathModificationFactory.toggleRoot());
    }

    public static ModifiableBigInteger shiftLeftBigInteger(Integer i) {
        return getModifiableBigIntegerWithModification(BigIntegerModificationFactory.shiftLeft(i));
    }

    public static ModifiableInteger shiftLeft(Integer i) {
        return getModifiableIntegerWithModification(IntegerModificationFactory.shiftLeft(i));
    }

    public static ModifiableLong shiftLeftLong(Integer i) {
        return getModifiableLongWithModification(LongModificationFactory.shiftLeft(i));
    }

    public static ModifiableBigInteger shiftRightBigInteger(Integer i) {
        return getModifiableBigIntegerWithModification(BigIntegerModificationFactory.shiftRight(i));
    }

    public static ModifiableBigInteger multiplyBigInteger(BigInteger i) {
        return getModifiableBigIntegerWithModification(BigIntegerModificationFactory.multiply(i));
    }

    public static ModifiableInteger multiply(Integer i) {
        return getModifiableIntegerWithModification(IntegerModificationFactory.multiply(i));
    }

    public static ModifiableLong multiply(Long l) {
        return getModifiableLongWithModification(LongModificationFactory.multiply(l));
    }

    public static ModifiableInteger shiftRight(Integer i) {
        return getModifiableIntegerWithModification(IntegerModificationFactory.shiftRight(i));
    }

    public static ModifiableLong shiftRightLong(Integer i) {
        return getModifiableLongWithModification(LongModificationFactory.shiftRight(i));
    }
}
