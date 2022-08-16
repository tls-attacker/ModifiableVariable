/**
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Copyright 2014-2022 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 *
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.modifiablevariable.util;

import com.google.common.primitives.UnsignedInteger;
import de.rub.nds.modifiablevariable.ModifiableVariable;
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
import de.rub.nds.modifiablevariable.mlong.LongModificationFactory;
import de.rub.nds.modifiablevariable.mlong.ModifiableLong;
import de.rub.nds.modifiablevariable.singlebyte.ByteModificationFactory;
import de.rub.nds.modifiablevariable.singlebyte.ModifiableByte;
import de.rub.nds.modifiablevariable.string.ModifiableString;
import de.rub.nds.modifiablevariable.string.StringModificationFactory;
import de.rub.nds.modifiablevariable.uinteger.ModifiableUnsignedInteger;
import de.rub.nds.modifiablevariable.uinteger.UnsignedIntegerModificationFactory;

import java.math.BigInteger;

@SuppressWarnings("unused")
public class Modifiable {

    private static ModifiableByteArray
        getModifiableByteArrayWithModification(VariableModification<byte[]> modification) {
        ModifiableByteArray modifiableByteArray = new ModifiableByteArray();
        modifiableByteArray.setModification(modification);
        return modifiableByteArray;
    }

    private static ModifiableByte getModifiableByteWithModification(VariableModification<Byte> modification) {
        ModifiableByte modifiableByte = new ModifiableByte();
        modifiableByte.setModification(modification);
        return modifiableByte;
    }

    private static ModifiableInteger getModifiableIntegerWithModification(VariableModification<Integer> modification) {
        ModifiableInteger modifiableInteger = new ModifiableInteger();
        modifiableInteger.setModification(modification);
        return modifiableInteger;
    }

    private static ModifiableUnsignedInteger
        getModifiableUnsignedIntegerWithModification(VariableModification<UnsignedInteger> modification) {
        ModifiableUnsignedInteger modifiableUnsignedInteger = new ModifiableUnsignedInteger();
        modifiableUnsignedInteger.setModification(modification);
        return modifiableUnsignedInteger;
    }

    private static ModifiableBigInteger
        getModifiableBigIntegerWithModification(VariableModification<BigInteger> modification) {
        ModifiableBigInteger modifiableBigInteger = new ModifiableBigInteger();
        modifiableBigInteger.setModification(modification);
        return modifiableBigInteger;
    }

    private static ModifiableLong getModifiableLongWithModification(VariableModification<Long> modification) {
        ModifiableLong modifiableLong = new ModifiableLong();
        modifiableLong.setModification(modification);
        return modifiableLong;
    }

    private static ModifiableBoolean getModifiableBooleanWithModification(VariableModification<Boolean> modification) {
        ModifiableBoolean modifiableBoolean = new ModifiableBoolean();
        modifiableBoolean.setModification(modification);
        return modifiableBoolean;
    }

    private static ModifiableString getModifiableStringWithModification(VariableModification<String> modification) {
        ModifiableString modifiableString = new ModifiableString();
        modifiableString.setModification(modification);
        return modifiableString;
    }

    public static ModifiableByteArray explicit(byte[] b) {
        return getModifiableByteArrayWithModification(ByteArrayModificationFactory.explicitValue(b));
    }

    public static ModifiableByte explicit(Byte b) {
        return getModifiableByteWithModification(ByteModificationFactory.explicitValue(b));
    }

    public static ModifiableInteger explicit(Integer i) {
        return getModifiableIntegerWithModification(IntegerModificationFactory.explicitValue(i));
    }

    public static ModifiableUnsignedInteger explicit(UnsignedInteger i) {
        return getModifiableUnsignedIntegerWithModification(UnsignedIntegerModificationFactory.explicitValue(i));
    }

    public static ModifiableBigInteger explicit(BigInteger i) {
        return getModifiableBigIntegerWithModification(BigIntegerModificationFactory.explicitValue(i));
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

    /**
     * Creates a new modifiable variable with an explicit value modification which replaces the original value.
     *
     * @param  value
     *                                  The explicit value which replaces the original value.
     * @param  clazz
     *                                  Class of the modifiable variable to create. If the class is not supported an
     *                                  {@link IllegalArgumentException} will be thrown.
     * @return                          A new modifiable variable of the given class with an explicit value
     *                                  modification.
     * @param  <N>
     *                                  Type of the explicit value.
     * @param  <MV>
     *                                  Type of the modifiable variable to return.
     * @throws IllegalArgumentException
     *                                  Thrown whenever the provided class is not supported.
     */
    public static <N extends Number, MV extends ModifiableVariable<? extends Number>> MV explicit(N value,
        Class<MV> clazz) {
        if (clazz.equals(ModifiableByte.class)) {
            return clazz.cast(explicit(value != null ? value.byteValue() : null));
        } else if (clazz.equals(ModifiableInteger.class)) {
            return clazz.cast(explicit(value != null ? value.intValue() : null));
        } else if (clazz.equals(ModifiableLong.class)) {
            return clazz.cast(explicit(value != null ? value.longValue() : null));
        }
        // No ModifiableVariable of primitive type => best effort
        // We just assume here that the toString() method does a good job at returning a parsable string value
        String valueString = value != null ? value.toString() : null;
        if (clazz.equals(ModifiableBigInteger.class)) {
            return clazz.cast(explicit(valueString != null ? new BigInteger(valueString) : null));
        } else if (clazz.equals(ModifiableUnsignedInteger.class)) {
            return clazz.cast(explicit(valueString != null ? UnsignedInteger.valueOf(valueString) : null));
        } else {
            // May happen if the user implements its own subclass and tries to call this method with it
            // TODO: This can be avoided by implementing a sealed class with all supported classes as permitted
            // subclasses (requires Java 17)
            throw new IllegalArgumentException("Unable to create modifiable variable of class " + clazz.getSimpleName()
                + " with explicit value modification");
        }
    }

    public static ModifiableByteArray xor(byte[] b, int position) {
        return getModifiableByteArrayWithModification(ByteArrayModificationFactory.xor(b, position));
    }

    public static ModifiableByte xor(Byte b) {
        return getModifiableByteWithModification(ByteModificationFactory.xor(b));
    }

    public static ModifiableInteger xor(Integer i) {
        return getModifiableIntegerWithModification(IntegerModificationFactory.xor(i));
    }

    public static ModifiableUnsignedInteger xor(UnsignedInteger i) {
        return getModifiableUnsignedIntegerWithModification(UnsignedIntegerModificationFactory.xor(i));
    }

    public static ModifiableBigInteger xor(BigInteger i) {
        return getModifiableBigIntegerWithModification(BigIntegerModificationFactory.xor(i));
    }

    public static ModifiableLong xor(Long l) {
        return getModifiableLongWithModification(LongModificationFactory.xor(l));
    }

    /**
     * Creates a new modifiable variable with an XOR modification which returns the XOR result of xor and the original
     * vlaue.
     *
     * @param  xor
     *                                  The xor value which is being xor'ed to the original value.
     * @param  clazz
     *                                  Class of the modifiable variable to create. If the class is not supported an
     *                                  {@link IllegalArgumentException} will be thrown.
     * @return                          A new modifiable variable of the given class with an XOR modification.
     * @param  <N>
     *                                  Type of the xor value.
     * @param  <MV>
     *                                  Type of the modifiable variable to return.
     * @throws IllegalArgumentException
     *                                  Thrown whenever the provided class is not supported.
     */
    public static <N extends Number, MV extends ModifiableVariable<? extends Number>> MV xor(N xor, Class<MV> clazz) {
        if (clazz.equals(ModifiableByte.class)) {
            return clazz.cast(xor(xor != null ? xor.byteValue() : null));
        } else if (clazz.equals(ModifiableInteger.class)) {
            return clazz.cast(xor(xor != null ? xor.intValue() : null));
        } else if (clazz.equals(ModifiableLong.class)) {
            return clazz.cast(xor(xor != null ? xor.longValue() : null));
        }
        // No ModifiableVariable of primitive type => best effort
        // We just assume here that the toString() method does a good job at returning a parsable string value
        String xorString = xor != null ? xor.toString() : null;
        if (clazz.equals(ModifiableBigInteger.class)) {
            return clazz.cast(xor(xorString != null ? new BigInteger(xorString) : null));
        } else if (clazz.equals(ModifiableUnsignedInteger.class)) {
            return clazz.cast(xor(xorString != null ? UnsignedInteger.valueOf(xorString) : null));
        } else {
            // May happen if the user implements its own subclass and tries to call this method with it
            // TODO: This can be avoided by implementing a sealed class with all supported classes as permitted
            // subclasses (requires Java 17)
            throw new IllegalArgumentException(
                "Unable to create modifiable variable of class " + clazz.getSimpleName() + " with xor modification");
        }
    }

    public static ModifiableByte add(Byte b) {
        return getModifiableByteWithModification(ByteModificationFactory.add(b));
    }

    public static ModifiableInteger add(Integer i) {
        return getModifiableIntegerWithModification(IntegerModificationFactory.add(i));
    }

    public static ModifiableUnsignedInteger add(UnsignedInteger i) {
        return getModifiableUnsignedIntegerWithModification(UnsignedIntegerModificationFactory.add(i));
    }

    public static ModifiableBigInteger add(BigInteger i) {
        return getModifiableBigIntegerWithModification(BigIntegerModificationFactory.add(i));
    }

    public static ModifiableLong add(Long l) {
        return getModifiableLongWithModification(LongModificationFactory.add(l));
    }

    /**
     * Creates a new modifiable variable with an add modification which adds the given summand to the original value.
     *
     * @param  summand
     *                                  Value to add to the original value.
     * @param  clazz
     *                                  Class of the modifiable variable to create. If the class is not supported an
     *                                  {@link IllegalArgumentException} will be thrown.
     * @return                          A new modifiable variable of the given class with an add modification.
     * @param  <N>
     *                                  Type of the summand.
     * @param  <MV>
     *                                  Type of the modifiable variable to return.
     * @throws IllegalArgumentException
     *                                  Thrown whenever the provided class is not supported.
     */
    public static <N extends Number, MV extends ModifiableVariable<? extends Number>> MV add(N summand,
        Class<MV> clazz) {
        if (clazz.equals(ModifiableByte.class)) {
            return clazz.cast(add(summand != null ? summand.byteValue() : null));
        } else if (clazz.equals(ModifiableInteger.class)) {
            return clazz.cast(add(summand != null ? summand.intValue() : null));
        } else if (clazz.equals(ModifiableLong.class)) {
            return clazz.cast(add(summand != null ? summand.longValue() : null));
        }
        // No ModifiableVariable of primitive type => best effort
        // We just assume here that the toString() method does a good job at returning a parsable string value
        String summandString = summand != null ? summand.toString() : null;
        if (clazz.equals(ModifiableBigInteger.class)) {
            return clazz.cast(add(summandString != null ? new BigInteger(summandString) : null));
        } else if (clazz.equals(ModifiableUnsignedInteger.class)) {
            return clazz.cast(add(summandString != null ? UnsignedInteger.valueOf(summandString) : null));
        } else {
            // May happen if the user implements its own subclass and tries to call this method with it
            // TODO: This can be avoided by implementing a sealed class with all supported classes as permitted
            // subclasses (requires Java 17)
            throw new IllegalArgumentException(
                "Unable to create modifiable variable of class " + clazz.getSimpleName() + " with add modification");
        }
    }

    public static ModifiableByte sub(Byte b) {
        return getModifiableByteWithModification(ByteModificationFactory.sub(b));
    }

    public static ModifiableInteger sub(Integer i) {
        return getModifiableIntegerWithModification(IntegerModificationFactory.sub(i));
    }

    public static ModifiableUnsignedInteger sub(UnsignedInteger i) {
        return getModifiableUnsignedIntegerWithModification(UnsignedIntegerModificationFactory.sub(i));
    }

    public static ModifiableBigInteger sub(BigInteger i) {
        return getModifiableBigIntegerWithModification(BigIntegerModificationFactory.sub(i));
    }

    public static ModifiableLong sub(Long l) {
        return getModifiableLongWithModification(LongModificationFactory.sub(l));
    }

    /**
     * Creates a new modifiable variable with a subtract modification which subtracts the given subtrahend from the
     * original value.
     *
     * @param  subtrahend
     *                                  Value to subtract from the original value.
     * @param  clazz
     *                                  Class of the modifiable variable to create. If the class is not supported an
     *                                  {@link IllegalArgumentException} will be thrown.
     * @return                          A new modifiable variable of the given class with a subtract modification.
     * @param  <N>
     *                                  Type of the subtrahend.
     * @param  <MV>
     *                                  Type of the modifiable variable to return.
     * @throws IllegalArgumentException
     *                                  Thrown whenever the provided class is not supported.
     */
    public static <N extends Number, MV extends ModifiableVariable<? extends Number>> MV sub(N subtrahend,
        Class<MV> clazz) {
        if (clazz.equals(ModifiableByte.class)) {
            return clazz.cast(sub(subtrahend != null ? subtrahend.byteValue() : null));
        } else if (clazz.equals(ModifiableInteger.class)) {
            return clazz.cast(sub(subtrahend != null ? subtrahend.intValue() : null));
        } else if (clazz.equals(ModifiableLong.class)) {
            return clazz.cast(sub(subtrahend != null ? subtrahend.longValue() : null));
        }
        // No ModifiableVariable of primitive type => best effort
        // We just assume here that the toString() method does a good job at returning a parsable string value
        String subtrahendString = subtrahend != null ? subtrahend.toString() : null;
        if (clazz.equals(ModifiableBigInteger.class)) {
            return clazz.cast(sub(subtrahendString != null ? new BigInteger(subtrahendString) : null));
        } else if (clazz.equals(ModifiableUnsignedInteger.class)) {
            return clazz.cast(sub(subtrahendString != null ? UnsignedInteger.valueOf(subtrahendString) : null));
        } else {
            // May happen if the user implements its own subclass and tries to call this method with it
            // TODO: This can be avoided by implementing a sealed class with all supported classes as permitted
            // subclasses (requires Java 17)
            throw new IllegalArgumentException("Unable to create modifiable variable of class " + clazz.getSimpleName()
                + " with subtract modification");
        }
    }

    public static ModifiableByteArray insert(byte[] b, int position) {
        return getModifiableByteArrayWithModification(ByteArrayModificationFactory.insert(b, position));
    }

    public static ModifiableByteArray delete(int startPosition, int count) {
        return getModifiableByteArrayWithModification(ByteArrayModificationFactory.delete(startPosition, count));
    }

    public static ModifiableByteArray shuffle(byte[] shuffle) {
        return getModifiableByteArrayWithModification(ByteArrayModificationFactory.shuffle(shuffle));
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

    public static ModifiableUnsignedInteger shiftLeftUnsignedInteger(Integer i) {
        return getModifiableUnsignedIntegerWithModification(UnsignedIntegerModificationFactory.shiftLeft(i));
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

    public static ModifiableUnsignedInteger shiftRightUnsignedInteger(Integer i) {
        return getModifiableUnsignedIntegerWithModification(UnsignedIntegerModificationFactory.shiftRight(i));
    }
}
