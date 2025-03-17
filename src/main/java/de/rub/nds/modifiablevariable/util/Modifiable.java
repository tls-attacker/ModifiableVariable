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

/**
 * A factory class providing convenient methods for creating modifiable variables with various
 * modifications.
 *
 * <p>This utility class serves as a central access point for creating variables that can be
 * dynamically modified at runtime. It provides a fluent API for common modification operations
 * across all supported variable types.
 *
 * <p>Each factory method creates a new modifiable variable with the specified modification
 * pre-configured. The methods are organized by operation type (add, xor, explicit, etc.) and
 * overloaded to support different data types.
 *
 * <p>Usage example:
 *
 * <pre>{@code
 * // Create a modifiable integer that will add 42 to its input
 * ModifiableInteger addMod = Modifiable.add(42);
 * addMod.setOriginalValue(10);
 * Integer result = addMod.getValue(); // Returns 52
 *
 * // Create a modifiable byte array that explicitly sets a value
 * ModifiableByteArray explicitMod = Modifiable.explicit(new byte[]{1, 2, 3});
 * explicitMod.setOriginalValue(new byte[]{4, 5, 6});
 * byte[] result = explicitMod.getValue(); // Returns {1, 2, 3} regardless of original value
 * }</pre>
 *
 * <p>This class is not meant to be instantiated as it only provides static factory methods.
 */
public final class Modifiable {

    /** Private constructor to prevent instantiation of this utility class. */
    private Modifiable() {
        super();
    }

    /**
     * Creates a new ModifiableByteArray with the specified modification applied.
     *
     * @param modification The modification to apply to the byte array
     * @return A new ModifiableByteArray with the specified modification
     */
    private static ModifiableByteArray getModifiableByteArrayWithModification(
            VariableModification<byte[]> modification) {
        ModifiableByteArray modifiableByteArray = new ModifiableByteArray();
        modifiableByteArray.setModifications(modification);
        return modifiableByteArray;
    }

    /**
     * Creates a new ModifiableByte with the specified modification applied.
     *
     * @param modification The modification to apply to the byte
     * @return A new ModifiableByte with the specified modification
     */
    private static ModifiableByte getModifiableByteWithModification(
            VariableModification<Byte> modification) {
        ModifiableByte modifiableByte = new ModifiableByte();
        modifiableByte.setModifications(modification);
        return modifiableByte;
    }

    /**
     * Creates a new ModifiableInteger with the specified modification applied.
     *
     * @param modification The modification to apply to the integer
     * @return A new ModifiableInteger with the specified modification
     */
    private static ModifiableInteger getModifiableIntegerWithModification(
            VariableModification<Integer> modification) {
        ModifiableInteger modifiableInteger = new ModifiableInteger();
        modifiableInteger.setModifications(modification);
        return modifiableInteger;
    }

    /**
     * Creates a new ModifiableBigInteger with the specified modification applied.
     *
     * @param modification The modification to apply to the BigInteger
     * @return A new ModifiableBigInteger with the specified modification
     */
    private static ModifiableBigInteger getModifiableBigIntegerWithModification(
            VariableModification<BigInteger> modification) {
        ModifiableBigInteger modifiableBigInteger = new ModifiableBigInteger();
        modifiableBigInteger.setModifications(modification);
        return modifiableBigInteger;
    }

    /**
     * Creates a new ModifiableLong with the specified modification applied.
     *
     * @param modification The modification to apply to the long
     * @return A new ModifiableLong with the specified modification
     */
    private static ModifiableLong getModifiableLongWithModification(
            VariableModification<Long> modification) {
        ModifiableLong modifiableLong = new ModifiableLong();
        modifiableLong.setModifications(modification);
        return modifiableLong;
    }

    /**
     * Creates a new ModifiableBoolean with the specified modification applied.
     *
     * @param modification The modification to apply to the boolean
     * @return A new ModifiableBoolean with the specified modification
     */
    private static ModifiableBoolean getModifiableBooleanWithModification(
            VariableModification<Boolean> modification) {
        ModifiableBoolean modifiableBoolean = new ModifiableBoolean();
        modifiableBoolean.setModifications(modification);
        return modifiableBoolean;
    }

    /**
     * Creates a new ModifiableString with the specified modification applied.
     *
     * @param modification The modification to apply to the string
     * @return A new ModifiableString with the specified modification
     */
    private static ModifiableString getModifiableStringWithModification(
            VariableModification<String> modification) {
        ModifiableString modifiableString = new ModifiableString();
        modifiableString.setModifications(modification);
        return modifiableString;
    }

    /**
     * Creates a ModifiableByteArray that prepends the specified value to the original byte array.
     *
     * @param perpendValue The byte array to prepend to the original value
     * @return A ModifiableByteArray that prepends the specified value
     * @see de.rub.nds.modifiablevariable.bytearray.ModifiableByteArray
     * @see de.rub.nds.modifiablevariable.bytearray.ByteArrayPrependValueModification
     */
    public static ModifiableByteArray prepend(byte[] perpendValue) {
        return getModifiableByteArrayWithModification(
                new ByteArrayPrependValueModification(perpendValue));
    }

    /**
     * Creates a ModifiableString that prepends the specified value to the original string.
     *
     * @param perpendValue The string to prepend to the original value
     * @return A ModifiableString that prepends the specified value
     * @see de.rub.nds.modifiablevariable.string.ModifiableString
     * @see de.rub.nds.modifiablevariable.string.StringPrependValueModification
     */
    public static ModifiableString prepend(String perpendValue) {
        return getModifiableStringWithModification(
                new StringPrependValueModification(perpendValue));
    }

    /**
     * Creates a ModifiableByteArray that appends the specified value to the original byte array.
     *
     * @param appendValue The byte array to append to the original value
     * @return A ModifiableByteArray that appends the specified value
     * @see de.rub.nds.modifiablevariable.bytearray.ModifiableByteArray
     * @see de.rub.nds.modifiablevariable.bytearray.ByteArrayAppendValueModification
     */
    public static ModifiableByteArray append(byte[] appendValue) {
        return getModifiableByteArrayWithModification(
                new ByteArrayAppendValueModification(appendValue));
    }

    /**
     * Creates a ModifiableString that appends the specified value to the original string.
     *
     * @param appendValue The string to append to the original value
     * @return A ModifiableString that appends the specified value
     * @see de.rub.nds.modifiablevariable.string.ModifiableString
     * @see de.rub.nds.modifiablevariable.string.StringAppendValueModification
     */
    public static ModifiableString append(String appendValue) {
        return getModifiableStringWithModification(new StringAppendValueModification(appendValue));
    }

    /**
     * Creates a ModifiableByteArray that replaces the original value with the specified explicit
     * value.
     *
     * @param explicitValue The byte array to use as the explicit value
     * @return A ModifiableByteArray that returns the explicit value
     * @see de.rub.nds.modifiablevariable.bytearray.ModifiableByteArray
     * @see de.rub.nds.modifiablevariable.bytearray.ByteArrayExplicitValueModification
     */
    public static ModifiableByteArray explicit(byte[] explicitValue) {
        return getModifiableByteArrayWithModification(
                new ByteArrayExplicitValueModification(explicitValue));
    }

    /**
     * Creates a ModifiableByte that replaces the original value with the specified explicit value.
     *
     * @param explicitValue The byte to use as the explicit value
     * @return A ModifiableByte that returns the explicit value
     * @see de.rub.nds.modifiablevariable.singlebyte.ModifiableByte
     * @see de.rub.nds.modifiablevariable.singlebyte.ByteExplicitValueModification
     */
    public static ModifiableByte explicit(Byte explicitValue) {
        return getModifiableByteWithModification(new ByteExplicitValueModification(explicitValue));
    }

    /**
     * Creates a ModifiableInteger that replaces the original value with the specified explicit
     * value.
     *
     * @param explicitValue The integer to use as the explicit value
     * @return A ModifiableInteger that returns the explicit value
     * @see de.rub.nds.modifiablevariable.integer.ModifiableInteger
     * @see de.rub.nds.modifiablevariable.integer.IntegerExplicitValueModification
     */
    public static ModifiableInteger explicit(Integer explicitValue) {
        return getModifiableIntegerWithModification(
                new IntegerExplicitValueModification(explicitValue));
    }

    /**
     * Creates a ModifiableBigInteger that replaces the original value with the specified explicit
     * value.
     *
     * @param explicitValue The BigInteger to use as the explicit value
     * @return A ModifiableBigInteger that returns the explicit value
     * @see de.rub.nds.modifiablevariable.biginteger.ModifiableBigInteger
     * @see de.rub.nds.modifiablevariable.biginteger.BigIntegerExplicitValueModification
     */
    public static ModifiableBigInteger explicit(BigInteger explicitValue) {
        return getModifiableBigIntegerWithModification(
                new BigIntegerExplicitValueModification(explicitValue));
    }

    /**
     * Creates a ModifiableLong that replaces the original value with the specified explicit value.
     *
     * @param explicitValue The long to use as the explicit value
     * @return A ModifiableLong that returns the explicit value
     * @see de.rub.nds.modifiablevariable.longint.ModifiableLong
     * @see de.rub.nds.modifiablevariable.longint.LongExplicitValueModification
     */
    public static ModifiableLong explicit(Long explicitValue) {
        return getModifiableLongWithModification(new LongExplicitValueModification(explicitValue));
    }

    /**
     * Creates a ModifiableBoolean that replaces the original value with the specified explicit
     * value.
     *
     * @param explicitValue The boolean to use as the explicit value
     * @return A ModifiableBoolean that returns the explicit value
     * @see de.rub.nds.modifiablevariable.bool.ModifiableBoolean
     * @see de.rub.nds.modifiablevariable.bool.BooleanExplicitValueModification
     */
    public static ModifiableBoolean explicit(Boolean explicitValue) {
        return getModifiableBooleanWithModification(
                new BooleanExplicitValueModification(explicitValue));
    }

    /**
     * Creates a ModifiableString that replaces the original value with the specified explicit
     * value.
     *
     * @param explicitValue The string to use as the explicit value
     * @return A ModifiableString that returns the explicit value
     * @see de.rub.nds.modifiablevariable.string.ModifiableString
     * @see de.rub.nds.modifiablevariable.string.StringExplicitValueModification
     */
    public static ModifiableString explicit(String explicitValue) {
        return getModifiableStringWithModification(
                new StringExplicitValueModification(explicitValue));
    }

    /**
     * Creates a ModifiableByteArray that inserts the specified value at the given position.
     *
     * @param insertValue The byte array to insert
     * @param position The position at which to insert the value (0-indexed)
     * @return A ModifiableByteArray that inserts the specified value at the given position
     * @see de.rub.nds.modifiablevariable.bytearray.ModifiableByteArray
     * @see de.rub.nds.modifiablevariable.bytearray.ByteArrayInsertValueModification
     */
    public static ModifiableByteArray insert(byte[] insertValue, int position) {
        return getModifiableByteArrayWithModification(
                new ByteArrayInsertValueModification(insertValue, position));
    }

    /**
     * Creates a ModifiableString that inserts the specified value at the given position.
     *
     * @param insertValue The string to insert
     * @param position The position at which to insert the value (0-indexed)
     * @return A ModifiableString that inserts the specified value at the given position
     * @see de.rub.nds.modifiablevariable.string.ModifiableString
     * @see de.rub.nds.modifiablevariable.string.StringInsertValueModification
     */
    public static ModifiableString insert(String insertValue, int position) {
        return getModifiableStringWithModification(
                new StringInsertValueModification(insertValue, position));
    }

    /**
     * Creates a ModifiableByteArray that applies an XOR operation with the specified value.
     *
     * @param xor The byte array to use in the XOR operation
     * @param position The position at which to start the XOR operation
     * @return A ModifiableByteArray that applies the XOR operation
     * @see de.rub.nds.modifiablevariable.bytearray.ModifiableByteArray
     * @see de.rub.nds.modifiablevariable.bytearray.ByteArrayXorModification
     */
    public static ModifiableByteArray xor(byte[] xor, int position) {
        return getModifiableByteArrayWithModification(new ByteArrayXorModification(xor, position));
    }

    /**
     * Creates a ModifiableByte that applies an XOR operation with the specified value.
     *
     * @param xor The byte to use in the XOR operation
     * @return A ModifiableByte that applies the XOR operation
     * @see de.rub.nds.modifiablevariable.singlebyte.ModifiableByte
     * @see de.rub.nds.modifiablevariable.singlebyte.ByteXorModification
     */
    public static ModifiableByte xor(Byte xor) {
        return getModifiableByteWithModification(new ByteXorModification(xor));
    }

    /**
     * Creates a ModifiableInteger that applies an XOR operation with the specified value.
     *
     * @param xor The integer to use in the XOR operation
     * @return A ModifiableInteger that applies the XOR operation
     * @see de.rub.nds.modifiablevariable.integer.ModifiableInteger
     * @see de.rub.nds.modifiablevariable.integer.IntegerXorModification
     */
    public static ModifiableInteger xor(Integer xor) {
        return getModifiableIntegerWithModification(new IntegerXorModification(xor));
    }

    /**
     * Creates a ModifiableBigInteger that applies an XOR operation with the specified value.
     *
     * @param xor The BigInteger to use in the XOR operation
     * @return A ModifiableBigInteger that applies the XOR operation
     * @see de.rub.nds.modifiablevariable.biginteger.ModifiableBigInteger
     * @see de.rub.nds.modifiablevariable.biginteger.BigIntegerXorModification
     */
    public static ModifiableBigInteger xor(BigInteger xor) {
        return getModifiableBigIntegerWithModification(new BigIntegerXorModification(xor));
    }

    /**
     * Creates a ModifiableLong that applies an XOR operation with the specified value.
     *
     * @param xor The long to use in the XOR operation
     * @return A ModifiableLong that applies the XOR operation
     * @see de.rub.nds.modifiablevariable.longint.ModifiableLong
     * @see de.rub.nds.modifiablevariable.longint.LongXorModification
     */
    public static ModifiableLong xor(Long xor) {
        return getModifiableLongWithModification(new LongXorModification(xor));
    }

    /**
     * Creates a ModifiableInteger that swaps the byte order (endianness) of the original integer.
     *
     * @return A ModifiableInteger that swaps the byte order of the original value
     * @see de.rub.nds.modifiablevariable.integer.ModifiableInteger
     * @see de.rub.nds.modifiablevariable.integer.IntegerSwapEndianModification
     */
    public static ModifiableInteger swapEndianIntger() {
        return getModifiableIntegerWithModification(new IntegerSwapEndianModification());
    }

    /**
     * Creates a ModifiableLong that swaps the byte order (endianness) of the original long.
     *
     * @return A ModifiableLong that swaps the byte order of the original value
     * @see de.rub.nds.modifiablevariable.longint.ModifiableLong
     * @see de.rub.nds.modifiablevariable.longint.LongSwapEndianModification
     */
    public static ModifiableLong swapEndianLong() {
        return getModifiableLongWithModification(new LongSwapEndianModification());
    }

    /**
     * Creates a ModifiableByte that adds the specified value to the original byte.
     *
     * @param summand The byte to add to the original value
     * @return A ModifiableByte that adds the specified value
     * @see de.rub.nds.modifiablevariable.singlebyte.ModifiableByte
     * @see de.rub.nds.modifiablevariable.singlebyte.ByteAddModification
     */
    public static ModifiableByte add(Byte summand) {
        return getModifiableByteWithModification(new ByteAddModification(summand));
    }

    /**
     * Creates a ModifiableInteger that adds the specified value to the original integer.
     *
     * @param summand The integer to add to the original value
     * @return A ModifiableInteger that adds the specified value
     * @see de.rub.nds.modifiablevariable.integer.ModifiableInteger
     * @see de.rub.nds.modifiablevariable.integer.IntegerAddModification
     */
    public static ModifiableInteger add(Integer summand) {
        return getModifiableIntegerWithModification(new IntegerAddModification(summand));
    }

    /**
     * Creates a ModifiableBigInteger that adds the specified value to the original BigInteger.
     *
     * @param summand The BigInteger to add to the original value
     * @return A ModifiableBigInteger that adds the specified value
     * @see de.rub.nds.modifiablevariable.biginteger.ModifiableBigInteger
     * @see de.rub.nds.modifiablevariable.biginteger.BigIntegerAddModification
     */
    public static ModifiableBigInteger add(BigInteger summand) {
        return getModifiableBigIntegerWithModification(new BigIntegerAddModification(summand));
    }

    /**
     * Creates a ModifiableLong that adds the specified value to the original long.
     *
     * @param summand The long to add to the original value
     * @return A ModifiableLong that adds the specified value
     * @see de.rub.nds.modifiablevariable.longint.ModifiableLong
     * @see de.rub.nds.modifiablevariable.longint.LongAddModification
     */
    public static ModifiableLong add(Long summand) {
        return getModifiableLongWithModification(new LongAddModification(summand));
    }

    /**
     * Creates a ModifiableByte that subtracts the specified value from the original byte.
     *
     * @param subtrahend The byte to subtract from the original value
     * @return A ModifiableByte that subtracts the specified value
     * @see de.rub.nds.modifiablevariable.singlebyte.ModifiableByte
     * @see de.rub.nds.modifiablevariable.singlebyte.ByteSubtractModification
     */
    public static ModifiableByte sub(Byte subtrahend) {
        return getModifiableByteWithModification(new ByteSubtractModification(subtrahend));
    }

    /**
     * Creates a ModifiableInteger that subtracts the specified value from the original integer.
     *
     * @param subtrahend The integer to subtract from the original value
     * @return A ModifiableInteger that subtracts the specified value
     * @see de.rub.nds.modifiablevariable.integer.ModifiableInteger
     * @see de.rub.nds.modifiablevariable.integer.IntegerSubtractModification
     */
    public static ModifiableInteger sub(Integer subtrahend) {
        return getModifiableIntegerWithModification(new IntegerSubtractModification(subtrahend));
    }

    /**
     * Creates a ModifiableBigInteger that subtracts the specified value from the original
     * BigInteger.
     *
     * @param subtrahend The BigInteger to subtract from the original value
     * @return A ModifiableBigInteger that subtracts the specified value
     * @see de.rub.nds.modifiablevariable.biginteger.ModifiableBigInteger
     * @see de.rub.nds.modifiablevariable.biginteger.BigIntegerSubtractModification
     */
    public static ModifiableBigInteger sub(BigInteger subtrahend) {
        return getModifiableBigIntegerWithModification(
                new BigIntegerSubtractModification(subtrahend));
    }

    /**
     * Creates a ModifiableLong that subtracts the specified value from the original long.
     *
     * @param subtrahend The long to subtract from the original value
     * @return A ModifiableLong that subtracts the specified value
     * @see de.rub.nds.modifiablevariable.longint.ModifiableLong
     * @see de.rub.nds.modifiablevariable.longint.LongSubtractModification
     */
    public static ModifiableLong sub(Long subtrahend) {
        return getModifiableLongWithModification(new LongSubtractModification(subtrahend));
    }

    /**
     * Creates a ModifiableByteArray that deletes a portion of the original byte array.
     *
     * @param startPosition The position from which to start deleting (0-indexed)
     * @param count The number of bytes to delete
     * @return A ModifiableByteArray that deletes the specified portion
     * @see de.rub.nds.modifiablevariable.bytearray.ModifiableByteArray
     * @see de.rub.nds.modifiablevariable.bytearray.ByteArrayDeleteModification
     */
    public static ModifiableByteArray delete(int startPosition, int count) {
        return getModifiableByteArrayWithModification(
                new ByteArrayDeleteModification(startPosition, count));
    }

    /**
     * Creates a ModifiableByteArray that shuffles the original byte array.
     *
     * @param shuffle The byte array defining the shuffle pattern
     * @return A ModifiableByteArray that shuffles according to the specified pattern
     * @see de.rub.nds.modifiablevariable.bytearray.ModifiableByteArray
     * @see de.rub.nds.modifiablevariable.bytearray.ByteArrayShuffleModification
     */
    public static ModifiableByteArray shuffle(int[] shuffle) {
        return getModifiableByteArrayWithModification(new ByteArrayShuffleModification(shuffle));
    }

    /**
     * Creates a ModifiableByteArray that duplicates the original byte array.
     *
     * @return A ModifiableByteArray that duplicates the original value
     * @see de.rub.nds.modifiablevariable.bytearray.ModifiableByteArray
     * @see de.rub.nds.modifiablevariable.bytearray.ByteArrayDuplicateModification
     */
    public static ModifiableByteArray duplicate() {
        return getModifiableByteArrayWithModification(new ByteArrayDuplicateModification());
    }

    /**
     * Creates a ModifiableBoolean that toggles the original boolean value.
     *
     * @return A ModifiableBoolean that toggles the original value
     * @see de.rub.nds.modifiablevariable.bool.ModifiableBoolean
     * @see de.rub.nds.modifiablevariable.bool.BooleanToggleModification
     */
    public static ModifiableBoolean toggle() {
        return getModifiableBooleanWithModification(new BooleanToggleModification());
    }

    /**
     * Creates a ModifiableBigInteger that shifts the original BigInteger to the left.
     *
     * @param shift The number of bits to shift left
     * @return A ModifiableBigInteger that shifts the original value left
     * @see de.rub.nds.modifiablevariable.biginteger.ModifiableBigInteger
     * @see de.rub.nds.modifiablevariable.biginteger.BigIntegerShiftLeftModification
     */
    public static ModifiableBigInteger shiftLeftBigInteger(Integer shift) {
        return getModifiableBigIntegerWithModification(new BigIntegerShiftLeftModification(shift));
    }

    /**
     * Creates a ModifiableInteger that shifts the original integer to the left.
     *
     * @param shift The number of bits to shift left
     * @return A ModifiableInteger that shifts the original value left
     * @see de.rub.nds.modifiablevariable.integer.ModifiableInteger
     * @see de.rub.nds.modifiablevariable.integer.IntegerShiftLeftModification
     */
    public static ModifiableInteger shiftLeft(Integer shift) {
        return getModifiableIntegerWithModification(new IntegerShiftLeftModification(shift));
    }

    /**
     * Creates a ModifiableLong that shifts the original long to the left.
     *
     * @param shift The number of bits to shift left
     * @return A ModifiableLong that shifts the original value left
     * @see de.rub.nds.modifiablevariable.longint.ModifiableLong
     * @see de.rub.nds.modifiablevariable.longint.LongShiftLeftModification
     */
    public static ModifiableLong shiftLeftLong(Integer shift) {
        return getModifiableLongWithModification(new LongShiftLeftModification(shift));
    }

    /**
     * Creates a ModifiableBigInteger that shifts the original BigInteger to the right.
     *
     * @param shift The number of bits to shift right
     * @return A ModifiableBigInteger that shifts the original value right
     * @see de.rub.nds.modifiablevariable.biginteger.ModifiableBigInteger
     * @see de.rub.nds.modifiablevariable.biginteger.BigIntegerShiftRightModification
     */
    public static ModifiableBigInteger shiftRightBigInteger(Integer shift) {
        return getModifiableBigIntegerWithModification(new BigIntegerShiftRightModification(shift));
    }

    /**
     * Creates a ModifiableInteger that shifts the original integer to the right.
     *
     * @param shift The number of bits to shift right
     * @return A ModifiableInteger that shifts the original value right
     * @see de.rub.nds.modifiablevariable.integer.ModifiableInteger
     * @see de.rub.nds.modifiablevariable.integer.IntegerShiftRightModification
     */
    public static ModifiableInteger shiftRight(Integer shift) {
        return getModifiableIntegerWithModification(new IntegerShiftRightModification(shift));
    }

    /**
     * Creates a ModifiableLong that shifts the original long to the right.
     *
     * @param shift The number of bits to shift right
     * @return A ModifiableLong that shifts the original value right
     * @see de.rub.nds.modifiablevariable.longint.ModifiableLong
     * @see de.rub.nds.modifiablevariable.longint.LongShiftRightModification
     */
    public static ModifiableLong shiftRightLong(Integer shift) {
        return getModifiableLongWithModification(new LongShiftRightModification(shift));
    }

    /**
     * Creates a ModifiableBigInteger that multiplies the original BigInteger.
     *
     * @param factor The BigInteger to multiply with the original value
     * @return A ModifiableBigInteger that multiplies the original value
     * @see de.rub.nds.modifiablevariable.biginteger.ModifiableBigInteger
     * @see de.rub.nds.modifiablevariable.biginteger.BigIntegerMultiplyModification
     */
    public static ModifiableBigInteger multiplyBigInteger(BigInteger factor) {
        return getModifiableBigIntegerWithModification(new BigIntegerMultiplyModification(factor));
    }

    /**
     * Creates a ModifiableInteger that multiplies the original integer.
     *
     * @param factor The integer to multiply with the original value
     * @return A ModifiableInteger that multiplies the original value
     * @see de.rub.nds.modifiablevariable.integer.ModifiableInteger
     * @see de.rub.nds.modifiablevariable.integer.IntegerMultiplyModification
     */
    public static ModifiableInteger multiply(Integer factor) {
        return getModifiableIntegerWithModification(new IntegerMultiplyModification(factor));
    }

    /**
     * Creates a ModifiableLong that multiplies the original long.
     *
     * @param factor The long to multiply with the original value
     * @return A ModifiableLong that multiplies the original value
     * @see de.rub.nds.modifiablevariable.longint.ModifiableLong
     * @see de.rub.nds.modifiablevariable.longint.LongMultiplyModification
     */
    public static ModifiableLong multiply(Long factor) {
        return getModifiableLongWithModification(new LongMultiplyModification(factor));
    }
}
