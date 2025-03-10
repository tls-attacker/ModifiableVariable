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
 * across all supported variable types:
 *
 * <ul>
 *   <li>Numeric types: byte, integer, long, BigInteger
 *   <li>Binary data: byte arrays
 *   <li>Text: strings
 *   <li>Logical: booleans
 * </ul>
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
@SuppressWarnings("unused")
public final class Modifiable {

    /** Private constructor to prevent instantiation of this utility class. */
    private Modifiable() {
        super();
    }

    /**
     * Creates a new ModifiableByteArray with the specified modification applied.
     *
     * <p>This is a helper method used by the public factory methods to create properly configured
     * byte array variables with the given modification.
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
     * <p>This is a helper method used by the public factory methods to create properly configured
     * byte variables with the given modification.
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
     * <p>This is a helper method used by the public factory methods to create properly configured
     * integer variables with the given modification.
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
     * <p>This is a helper method used by the public factory methods to create properly configured
     * BigInteger variables with the given modification.
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
     * <p>This is a helper method used by the public factory methods to create properly configured
     * long variables with the given modification.
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
     * <p>This is a helper method used by the public factory methods to create properly configured
     * boolean variables with the given modification.
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
     * <p>This is a helper method used by the public factory methods to create properly configured
     * string variables with the given modification.
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
     * <p>This modification adds the specified bytes at the beginning of the original byte array.
     *
     * <p>Example usage:
     *
     * <pre>{@code
     * ModifiableByteArray mod = Modifiable.prepend(new byte[]{1, 2});
     * mod.setOriginalValue(new byte[]{3, 4, 5});
     * byte[] result = mod.getValue(); // Returns {1, 2, 3, 4, 5}
     * }</pre>
     *
     * @param perpendValue The byte array to prepend to the original value
     * @return A ModifiableByteArray that prepends the specified value
     */
    public static ModifiableByteArray prepend(byte[] perpendValue) {
        return getModifiableByteArrayWithModification(
                new ByteArrayPrependValueModification(perpendValue));
    }

    /**
     * Creates a ModifiableString that prepends the specified value to the original string.
     *
     * <p>This modification adds the specified string at the beginning of the original string.
     *
     * <p>Example usage:
     *
     * <pre>{@code
     * ModifiableString mod = Modifiable.prepend("Hello ");
     * mod.setOriginalValue("World");
     * String result = mod.getValue(); // Returns "Hello World"
     * }</pre>
     *
     * @param perpendValue The string to prepend to the original value
     * @return A ModifiableString that prepends the specified value
     */
    public static ModifiableString prepend(String perpendValue) {
        return getModifiableStringWithModification(
                new StringPrependValueModification(perpendValue));
    }

    /**
     * Creates a ModifiableByteArray that appends the specified value to the original byte array.
     *
     * <p>This modification adds the specified bytes at the end of the original byte array.
     *
     * <p>Example usage:
     *
     * <pre>{@code
     * ModifiableByteArray mod = Modifiable.append(new byte[]{4, 5});
     * mod.setOriginalValue(new byte[]{1, 2, 3});
     * byte[] result = mod.getValue(); // Returns {1, 2, 3, 4, 5}
     * }</pre>
     *
     * @param appendValue The byte array to append to the original value
     * @return A ModifiableByteArray that appends the specified value
     */
    public static ModifiableByteArray append(byte[] appendValue) {
        return getModifiableByteArrayWithModification(
                new ByteArrayAppendValueModification(appendValue));
    }

    /**
     * Creates a ModifiableString that appends the specified value to the original string.
     *
     * <p>This modification adds the specified string at the end of the original string.
     *
     * <p>Example usage:
     *
     * <pre>{@code
     * ModifiableString mod = Modifiable.append(" World");
     * mod.setOriginalValue("Hello");
     * String result = mod.getValue(); // Returns "Hello World"
     * }</pre>
     *
     * @param appendValue The string to append to the original value
     * @return A ModifiableString that appends the specified value
     */
    public static ModifiableString append(String appendValue) {
        return getModifiableStringWithModification(new StringAppendValueModification(appendValue));
    }

    /**
     * Creates a ModifiableByteArray that replaces the original value with the specified explicit
     * value.
     *
     * <p>This modification ignores the original value and always returns the explicit value.
     *
     * <p>Example usage:
     *
     * <pre>{@code
     * ModifiableByteArray mod = Modifiable.explicit(new byte[]{1, 2, 3});
     * mod.setOriginalValue(new byte[]{4, 5, 6});
     * byte[] result = mod.getValue(); // Always returns {1, 2, 3}
     * }</pre>
     *
     * @param explicitValue The byte array to use as the explicit value
     * @return A ModifiableByteArray that returns the explicit value
     */
    public static ModifiableByteArray explicit(byte[] explicitValue) {
        return getModifiableByteArrayWithModification(
                new ByteArrayExplicitValueModification(explicitValue));
    }

    /**
     * Creates a ModifiableByte that replaces the original value with the specified explicit value.
     *
     * <p>This modification ignores the original value and always returns the explicit value.
     *
     * @param explicitValue The byte to use as the explicit value
     * @return A ModifiableByte that returns the explicit value
     */
    public static ModifiableByte explicit(Byte explicitValue) {
        return getModifiableByteWithModification(new ByteExplicitValueModification(explicitValue));
    }

    /**
     * Creates a ModifiableInteger that replaces the original value with the specified explicit
     * value.
     *
     * <p>This modification ignores the original value and always returns the explicit value.
     *
     * @param explicitValue The integer to use as the explicit value
     * @return A ModifiableInteger that returns the explicit value
     */
    public static ModifiableInteger explicit(Integer explicitValue) {
        return getModifiableIntegerWithModification(
                new IntegerExplicitValueModification(explicitValue));
    }

    /**
     * Creates a ModifiableBigInteger that replaces the original value with the specified explicit
     * value.
     *
     * <p>This modification ignores the original value and always returns the explicit value.
     *
     * @param explicitValue The BigInteger to use as the explicit value
     * @return A ModifiableBigInteger that returns the explicit value
     */
    public static ModifiableBigInteger explicit(BigInteger explicitValue) {
        return getModifiableBigIntegerWithModification(
                new BigIntegerExplicitValueModification(explicitValue));
    }

    /**
     * Creates a ModifiableLong that replaces the original value with the specified explicit value.
     *
     * <p>This modification ignores the original value and always returns the explicit value.
     *
     * @param explicitValue The long to use as the explicit value
     * @return A ModifiableLong that returns the explicit value
     */
    public static ModifiableLong explicit(Long explicitValue) {
        return getModifiableLongWithModification(new LongExplicitValueModification(explicitValue));
    }

    /**
     * Creates a ModifiableBoolean that replaces the original value with the specified explicit
     * value.
     *
     * <p>This modification ignores the original value and always returns the explicit value.
     *
     * @param explicitValue The boolean to use as the explicit value
     * @return A ModifiableBoolean that returns the explicit value
     */
    public static ModifiableBoolean explicit(Boolean explicitValue) {
        return getModifiableBooleanWithModification(
                new BooleanExplicitValueModification(explicitValue));
    }

    /**
     * Creates a ModifiableString that replaces the original value with the specified explicit
     * value.
     *
     * <p>This modification ignores the original value and always returns the explicit value.
     *
     * @param explicitValue The string to use as the explicit value
     * @return A ModifiableString that returns the explicit value
     */
    public static ModifiableString explicit(String explicitValue) {
        return getModifiableStringWithModification(
                new StringExplicitValueModification(explicitValue));
    }

    /**
     * Creates a ModifiableByteArray that inserts the specified value at the given position.
     *
     * <p>This modification inserts the specified bytes at the specified position in the original
     * byte array.
     *
     * <p>Example usage:
     *
     * <pre>{@code
     * ModifiableByteArray mod = Modifiable.insert(new byte[]{3, 4}, 1);
     * mod.setOriginalValue(new byte[]{1, 2, 5, 6});
     * byte[] result = mod.getValue(); // Returns {1, 3, 4, 2, 5, 6}
     * }</pre>
     *
     * @param insertValue The byte array to insert
     * @param position The position at which to insert the value (0-indexed)
     * @return A ModifiableByteArray that inserts the specified value at the given position
     */
    public static ModifiableByteArray insert(byte[] insertValue, int position) {
        return getModifiableByteArrayWithModification(
                new ByteArrayInsertValueModification(insertValue, position));
    }

    /**
     * Creates a ModifiableString that inserts the specified value at the given position.
     *
     * <p>This modification inserts the specified string at the specified position in the original
     * string.
     *
     * <p>Example usage:
     *
     * <pre>{@code
     * ModifiableString mod = Modifiable.insert(" beautiful", 5);
     * mod.setOriginalValue("Hello world");
     * String result = mod.getValue(); // Returns "Hello beautiful world"
     * }</pre>
     *
     * @param insertValue The string to insert
     * @param position The position at which to insert the value (0-indexed)
     * @return A ModifiableString that inserts the specified value at the given position
     */
    public static ModifiableString insert(String insertValue, int position) {
        return getModifiableStringWithModification(
                new StringInsertValueModification(insertValue, position));
    }

    /**
     * Creates a ModifiableByteArray that applies an XOR operation with the specified value.
     *
     * <p>This modification performs a bitwise XOR operation between the original byte array
     * (starting at the specified position) and the provided XOR value.
     *
     * <p>Example usage:
     *
     * <pre>{@code
     * ModifiableByteArray mod = Modifiable.xor(new byte[]{0x0F, 0x0F}, 1);
     * mod.setOriginalValue(new byte[]{0x00, 0xF0, 0xF0, 0x00});
     * byte[] result = mod.getValue(); // Returns {0x00, 0xFF, 0xFF, 0x00}
     * }</pre>
     *
     * @param xor The byte array to use in the XOR operation
     * @param position The position at which to start the XOR operation
     * @return A ModifiableByteArray that applies the XOR operation
     */
    public static ModifiableByteArray xor(byte[] xor, int position) {
        return getModifiableByteArrayWithModification(new ByteArrayXorModification(xor, position));
    }

    /**
     * Creates a ModifiableByte that applies an XOR operation with the specified value.
     *
     * <p>This modification performs a bitwise XOR operation between the original byte and the
     * provided XOR value.
     *
     * <p>Example usage:
     *
     * <pre>{@code
     * ModifiableByte mod = Modifiable.xor((byte)0x0F);
     * mod.setOriginalValue((byte)0xF0);
     * Byte result = mod.getValue(); // Returns 0xFF
     * }</pre>
     *
     * @param xor The byte to use in the XOR operation
     * @return A ModifiableByte that applies the XOR operation
     */
    public static ModifiableByte xor(Byte xor) {
        return getModifiableByteWithModification(new ByteXorModification(xor));
    }

    /**
     * Creates a ModifiableInteger that applies an XOR operation with the specified value.
     *
     * <p>This modification performs a bitwise XOR operation between the original integer and the
     * provided XOR value.
     *
     * @param xor The integer to use in the XOR operation
     * @return A ModifiableInteger that applies the XOR operation
     */
    public static ModifiableInteger xor(Integer xor) {
        return getModifiableIntegerWithModification(new IntegerXorModification(xor));
    }

    /**
     * Creates a ModifiableBigInteger that applies an XOR operation with the specified value.
     *
     * <p>This modification performs a bitwise XOR operation between the original BigInteger and the
     * provided XOR value.
     *
     * @param xor The BigInteger to use in the XOR operation
     * @return A ModifiableBigInteger that applies the XOR operation
     */
    public static ModifiableBigInteger xor(BigInteger xor) {
        return getModifiableBigIntegerWithModification(new BigIntegerXorModification(xor));
    }

    /**
     * Creates a ModifiableLong that applies an XOR operation with the specified value.
     *
     * <p>This modification performs a bitwise XOR operation between the original long and the
     * provided XOR value.
     *
     * @param xor The long to use in the XOR operation
     * @return A ModifiableLong that applies the XOR operation
     */
    public static ModifiableLong xor(Long xor) {
        return getModifiableLongWithModification(new LongXorModification(xor));
    }

    /**
     * Creates a ModifiableInteger that swaps the byte order (endianness) of the original integer.
     *
     * <p>This modification reverses the byte order of the original integer, converting between
     * little-endian and big-endian representations.
     *
     * <p>Example usage:
     *
     * <pre>{@code
     * ModifiableInteger mod = Modifiable.swapEndianIntger();
     * mod.setOriginalValue(0x12345678);
     * Integer result = mod.getValue(); // Returns 0x78563412
     * }</pre>
     *
     * @return A ModifiableInteger that swaps the byte order of the original value
     */
    public static ModifiableInteger swapEndianIntger() {
        return getModifiableIntegerWithModification(new IntegerSwapEndianModification());
    }

    /**
     * Creates a ModifiableLong that swaps the byte order (endianness) of the original long.
     *
     * <p>This modification reverses the byte order of the original long, converting between
     * little-endian and big-endian representations.
     *
     * @return A ModifiableLong that swaps the byte order of the original value
     */
    public static ModifiableLong swapEndianLong() {
        return getModifiableLongWithModification(new LongSwapEndianModification());
    }

    /**
     * Creates a ModifiableByte that adds the specified value to the original byte.
     *
     * <p>This modification performs addition on the original byte with the provided summand.
     *
     * <p>Example usage:
     *
     * <pre>{@code
     * ModifiableByte mod = Modifiable.add((byte)5);
     * mod.setOriginalValue((byte)10);
     * Byte result = mod.getValue(); // Returns 15
     * }</pre>
     *
     * @param summand The byte to add to the original value
     * @return A ModifiableByte that adds the specified value
     */
    public static ModifiableByte add(Byte summand) {
        return getModifiableByteWithModification(new ByteAddModification(summand));
    }

    /**
     * Creates a ModifiableInteger that adds the specified value to the original integer.
     *
     * <p>This modification performs addition on the original integer with the provided summand.
     *
     * @param summand The integer to add to the original value
     * @return A ModifiableInteger that adds the specified value
     */
    public static ModifiableInteger add(Integer summand) {
        return getModifiableIntegerWithModification(new IntegerAddModification(summand));
    }

    /**
     * Creates a ModifiableBigInteger that adds the specified value to the original BigInteger.
     *
     * <p>This modification performs addition on the original BigInteger with the provided summand.
     *
     * @param summand The BigInteger to add to the original value
     * @return A ModifiableBigInteger that adds the specified value
     */
    public static ModifiableBigInteger add(BigInteger summand) {
        return getModifiableBigIntegerWithModification(new BigIntegerAddModification(summand));
    }

    /**
     * Creates a ModifiableLong that adds the specified value to the original long.
     *
     * <p>This modification performs addition on the original long with the provided summand.
     *
     * @param summand The long to add to the original value
     * @return A ModifiableLong that adds the specified value
     */
    public static ModifiableLong add(Long summand) {
        return getModifiableLongWithModification(new LongAddModification(summand));
    }

    /**
     * Creates a ModifiableByte that subtracts the specified value from the original byte.
     *
     * <p>This modification performs subtraction on the original byte with the provided subtrahend.
     *
     * @param subtrahend The byte to subtract from the original value
     * @return A ModifiableByte that subtracts the specified value
     */
    public static ModifiableByte sub(Byte subtrahend) {
        return getModifiableByteWithModification(new ByteSubtractModification(subtrahend));
    }

    /**
     * Creates a ModifiableInteger that subtracts the specified value from the original integer.
     *
     * <p>This modification performs subtraction on the original integer with the provided
     * subtrahend.
     *
     * @param subtrahend The integer to subtract from the original value
     * @return A ModifiableInteger that subtracts the specified value
     */
    public static ModifiableInteger sub(Integer subtrahend) {
        return getModifiableIntegerWithModification(new IntegerSubtractModification(subtrahend));
    }

    /**
     * Creates a ModifiableBigInteger that subtracts the specified value from the original
     * BigInteger.
     *
     * <p>This modification performs subtraction on the original BigInteger with the provided
     * subtrahend.
     *
     * @param subtrahend The BigInteger to subtract from the original value
     * @return A ModifiableBigInteger that subtracts the specified value
     */
    public static ModifiableBigInteger sub(BigInteger subtrahend) {
        return getModifiableBigIntegerWithModification(
                new BigIntegerSubtractModification(subtrahend));
    }

    /**
     * Creates a ModifiableLong that subtracts the specified value from the original long.
     *
     * <p>This modification performs subtraction on the original long with the provided subtrahend.
     *
     * @param subtrahend The long to subtract from the original value
     * @return A ModifiableLong that subtracts the specified value
     */
    public static ModifiableLong sub(Long subtrahend) {
        return getModifiableLongWithModification(new LongSubtractModification(subtrahend));
    }

    /**
     * Creates a ModifiableByteArray that deletes a portion of the original byte array.
     *
     * <p>This modification removes a specified number of bytes starting at the given position.
     *
     * <p>Example usage:
     *
     * <pre>{@code
     * ModifiableByteArray mod = Modifiable.delete(1, 2);
     * mod.setOriginalValue(new byte[]{1, 2, 3, 4, 5});
     * byte[] result = mod.getValue(); // Returns {1, 4, 5}
     * }</pre>
     *
     * @param startPosition The position from which to start deleting (0-indexed)
     * @param count The number of bytes to delete
     * @return A ModifiableByteArray that deletes the specified portion
     */
    public static ModifiableByteArray delete(int startPosition, int count) {
        return getModifiableByteArrayWithModification(
                new ByteArrayDeleteModification(startPosition, count));
    }

    /**
     * Creates a ModifiableByteArray that shuffles the original byte array.
     *
     * <p>This modification reorders the bytes in the original array according to the specified
     * shuffle pattern. The shuffle pattern defines pairs of indices to swap.
     *
     * <p>Example usage:
     *
     * <pre>{@code
     * ModifiableByteArray mod = Modifiable.shuffle(new byte[]{1, 0, 3, 2});
     * mod.setOriginalValue(new byte[]{0, 1, 2, 3});
     * byte[] result = mod.getValue(); // Returns {1, 0, 3, 2}
     * }</pre>
     *
     * @param shuffle The byte array defining the shuffle pattern
     * @return A ModifiableByteArray that shuffles according to the specified pattern
     */
    public static ModifiableByteArray shuffle(int[] shuffle) {
        return getModifiableByteArrayWithModification(new ByteArrayShuffleModification(shuffle));
    }

    /**
     * Creates a ModifiableByteArray that duplicates the original byte array.
     *
     * <p>This modification appends a copy of the original byte array to itself, effectively
     * doubling its size.
     *
     * <p>Example usage:
     *
     * <pre>{@code
     * ModifiableByteArray mod = Modifiable.duplicate();
     * mod.setOriginalValue(new byte[]{1, 2, 3});
     * byte[] result = mod.getValue(); // Returns {1, 2, 3, 1, 2, 3}
     * }</pre>
     *
     * @return A ModifiableByteArray that duplicates the original value
     */
    public static ModifiableByteArray duplicate() {
        return getModifiableByteArrayWithModification(new ByteArrayDuplicateModification());
    }

    /**
     * Creates a ModifiableBoolean that toggles the original boolean value.
     *
     * <p>This modification inverts the original boolean value (true becomes false, false becomes
     * true).
     *
     * <p>Example usage:
     *
     * <pre>{@code
     * ModifiableBoolean mod = Modifiable.toggle();
     * mod.setOriginalValue(true);
     * Boolean result = mod.getValue(); // Returns false
     * }</pre>
     *
     * @return A ModifiableBoolean that toggles the original value
     */
    public static ModifiableBoolean toggle() {
        return getModifiableBooleanWithModification(new BooleanToggleModification());
    }

    /**
     * Creates a ModifiableBigInteger that shifts the original BigInteger to the left.
     *
     * <p>This modification performs a left bit shift operation on the original BigInteger by the
     * specified number of bits.
     *
     * <p>Example usage:
     *
     * <pre>{@code
     * ModifiableBigInteger mod = Modifiable.shiftLeftBigInteger(2);
     * mod.setOriginalValue(BigInteger.valueOf(5));
     * BigInteger result = mod.getValue(); // Returns 20 (5 << 2)
     * }</pre>
     *
     * @param shift The number of bits to shift left
     * @return A ModifiableBigInteger that shifts the original value left
     */
    public static ModifiableBigInteger shiftLeftBigInteger(Integer shift) {
        return getModifiableBigIntegerWithModification(new BigIntegerShiftLeftModification(shift));
    }

    /**
     * Creates a ModifiableInteger that shifts the original integer to the left.
     *
     * <p>This modification performs a left bit shift operation on the original integer by the
     * specified number of bits.
     *
     * @param shift The number of bits to shift left
     * @return A ModifiableInteger that shifts the original value left
     */
    public static ModifiableInteger shiftLeft(Integer shift) {
        return getModifiableIntegerWithModification(new IntegerShiftLeftModification(shift));
    }

    /**
     * Creates a ModifiableLong that shifts the original long to the left.
     *
     * <p>This modification performs a left bit shift operation on the original long by the
     * specified number of bits.
     *
     * @param shift The number of bits to shift left
     * @return A ModifiableLong that shifts the original value left
     */
    public static ModifiableLong shiftLeftLong(Integer shift) {
        return getModifiableLongWithModification(new LongShiftLeftModification(shift));
    }

    /**
     * Creates a ModifiableBigInteger that shifts the original BigInteger to the right.
     *
     * <p>This modification performs a right bit shift operation on the original BigInteger by the
     * specified number of bits.
     *
     * @param shift The number of bits to shift right
     * @return A ModifiableBigInteger that shifts the original value right
     */
    public static ModifiableBigInteger shiftRightBigInteger(Integer shift) {
        return getModifiableBigIntegerWithModification(new BigIntegerShiftRightModification(shift));
    }

    /**
     * Creates a ModifiableInteger that shifts the original integer to the right.
     *
     * <p>This modification performs a right bit shift operation on the original integer by the
     * specified number of bits.
     *
     * @param shift The number of bits to shift right
     * @return A ModifiableInteger that shifts the original value right
     */
    public static ModifiableInteger shiftRight(Integer shift) {
        return getModifiableIntegerWithModification(new IntegerShiftRightModification(shift));
    }

    /**
     * Creates a ModifiableLong that shifts the original long to the right.
     *
     * <p>This modification performs a right bit shift operation on the original long by the
     * specified number of bits.
     *
     * @param shift The number of bits to shift right
     * @return A ModifiableLong that shifts the original value right
     */
    public static ModifiableLong shiftRightLong(Integer shift) {
        return getModifiableLongWithModification(new LongShiftRightModification(shift));
    }

    /**
     * Creates a ModifiableBigInteger that multiplies the original BigInteger.
     *
     * <p>This modification performs multiplication on the original BigInteger with the provided
     * factor.
     *
     * <p>Example usage:
     *
     * <pre>{@code
     * ModifiableBigInteger mod = Modifiable.multiplyBigInteger(BigInteger.valueOf(10));
     * mod.setOriginalValue(BigInteger.valueOf(5));
     * BigInteger result = mod.getValue(); // Returns 50
     * }</pre>
     *
     * @param factor The BigInteger to multiply with the original value
     * @return A ModifiableBigInteger that multiplies the original value
     */
    public static ModifiableBigInteger multiplyBigInteger(BigInteger factor) {
        return getModifiableBigIntegerWithModification(new BigIntegerMultiplyModification(factor));
    }

    /**
     * Creates a ModifiableInteger that multiplies the original integer.
     *
     * <p>This modification performs multiplication on the original integer with the provided
     * factor.
     *
     * @param factor The integer to multiply with the original value
     * @return A ModifiableInteger that multiplies the original value
     */
    public static ModifiableInteger multiply(Integer factor) {
        return getModifiableIntegerWithModification(new IntegerMultiplyModification(factor));
    }

    /**
     * Creates a ModifiableLong that multiplies the original long.
     *
     * <p>This modification performs multiplication on the original long with the provided factor.
     *
     * @param factor The long to multiply with the original value
     * @return A ModifiableLong that multiplies the original value
     */
    public static ModifiableLong multiply(Long factor) {
        return getModifiableLongWithModification(new LongMultiplyModification(factor));
    }
}
