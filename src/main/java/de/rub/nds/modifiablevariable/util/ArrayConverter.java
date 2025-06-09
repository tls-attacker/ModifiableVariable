/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.util;

import de.rub.nds.modifiablevariable.bytearray.ModifiableByteArray;
import java.math.BigInteger;
import java.util.List;

/**
 * A utility class for array conversions and manipulations.
 *
 * @deprecated This class has been renamed to {@link DataConverter} to better reflect its purpose.
 *     Use {@link DataConverter} instead. This class will be removed in a future version.
 *     <p>This class provides methods for:
 *     <ul>
 *       <li>Converting between various numeric types and byte arrays
 *       <li>Converting between hexadecimal strings and byte arrays
 *       <li>Concatenating arrays
 *       <li>Manipulating byte arrays
 *       <li>BigInteger conversions
 *     </ul>
 *     <p>All methods are static, and the class cannot be instantiated.
 */
@Deprecated
public final class ArrayConverter {

    /** Private constructor to prevent instantiation of this utility class. */
    private ArrayConverter() {
        super();
    }

    /**
     * @deprecated Use {@link DataConverter#longToUint64Bytes(long)} instead.
     */
    @Deprecated
    public static byte[] longToUint64Bytes(long value) {
        return DataConverter.longToUint64Bytes(value);
    }

    /**
     * @deprecated Use {@link DataConverter#longToUint48Bytes(long)} instead.
     */
    @Deprecated
    public static byte[] longToUint48Bytes(long value) {
        return DataConverter.longToUint48Bytes(value);
    }

    /**
     * @deprecated Use {@link DataConverter#longToUint32Bytes(long)} instead.
     */
    @Deprecated
    public static byte[] longToUint32Bytes(long value) {
        return DataConverter.longToUint32Bytes(value);
    }

    /**
     * @deprecated Use {@link DataConverter#uInt64BytesToLong(byte[])} instead.
     */
    @Deprecated
    public static long uInt64BytesToLong(byte[] bytes) {
        return DataConverter.uInt64BytesToLong(bytes);
    }

    /**
     * @deprecated Use {@link DataConverter#uInt32BytesToLong(byte[])} instead.
     */
    @Deprecated
    public static long uInt32BytesToLong(byte[] bytes) {
        return DataConverter.uInt32BytesToLong(bytes);
    }

    /**
     * @deprecated Use {@link DataConverter#intToBytes(int, int)} instead.
     */
    @Deprecated
    public static byte[] intToBytes(int value, int size) {
        return DataConverter.intToBytes(value, size);
    }

    /**
     * @deprecated Use {@link DataConverter#longToBytes(long, int)} instead.
     */
    @Deprecated
    public static byte[] longToBytes(long value, int size) {
        return DataConverter.longToBytes(value, size);
    }

    /**
     * @deprecated Use {@link DataConverter#bytesToInt(byte[])} instead.
     */
    @Deprecated
    public static int bytesToInt(byte[] value) {
        return DataConverter.bytesToInt(value);
    }

    /**
     * @deprecated Use {@link DataConverter#bytesToLong(byte[])} instead.
     */
    @Deprecated
    public static long bytesToLong(byte[] value) {
        return DataConverter.bytesToLong(value);
    }

    /**
     * @deprecated Use {@link DataConverter#bytesToHexString(byte[])} instead.
     */
    @Deprecated
    public static String bytesToHexString(byte[] array) {
        return DataConverter.bytesToHexString(array);
    }

    /**
     * @deprecated Use {@link DataConverter#bytesToHexString(byte[], boolean)} instead.
     */
    @Deprecated
    public static String bytesToHexString(byte[] array, boolean usePrettyPrinting) {
        return DataConverter.bytesToHexString(array, usePrettyPrinting);
    }

    /**
     * @deprecated Use {@link DataConverter#bytesToHexString(byte[], boolean, boolean)} instead.
     */
    @Deprecated
    public static String bytesToHexString(
            byte[] array, boolean usePrettyPrinting, boolean initialNewLine) {
        return DataConverter.bytesToHexString(array, usePrettyPrinting, initialNewLine);
    }

    /**
     * @deprecated Use {@link DataConverter#bytesToHexString(ModifiableByteArray)} instead.
     */
    @Deprecated
    public static String bytesToHexString(ModifiableByteArray array) {
        return DataConverter.bytesToHexString(array);
    }

    /**
     * @deprecated Use {@link DataConverter#bytesToHexString(ModifiableByteArray, boolean)} instead.
     */
    @Deprecated
    public static String bytesToHexString(ModifiableByteArray array, boolean usePrettyPrinting) {
        return DataConverter.bytesToHexString(array, usePrettyPrinting);
    }

    /**
     * @deprecated Use {@link DataConverter#bytesToHexString(ModifiableByteArray, boolean, boolean)}
     *     instead.
     */
    @Deprecated
    public static String bytesToHexString(
            ModifiableByteArray array, boolean usePrettyPrinting, boolean initialNewLine) {
        return DataConverter.bytesToHexString(array, usePrettyPrinting, initialNewLine);
    }

    /**
     * @deprecated Use {@link DataConverter#bytesToRawHexString(byte[])} instead.
     */
    @Deprecated
    public static String bytesToRawHexString(byte[] array) {
        return DataConverter.bytesToRawHexString(array);
    }

    /**
     * @deprecated Use {@link DataConverter#concatenate(Object[][])} instead.
     */
    @Deprecated
    @SafeVarargs
    public static <T> T[] concatenate(T[]... arrays) {
        return DataConverter.concatenate(arrays);
    }

    /**
     * @deprecated Use {@link DataConverter#concatenate(byte[][])} instead.
     */
    @Deprecated
    public static byte[] concatenate(byte[]... arrays) {
        return DataConverter.concatenate(arrays);
    }

    /**
     * @deprecated Use {@link DataConverter#concatenate(byte[], byte[], int)} instead.
     */
    @Deprecated
    public static byte[] concatenate(byte[] array1, byte[] array2, int numberOfArray2Bytes) {
        return DataConverter.concatenate(array1, array2, numberOfArray2Bytes);
    }

    /**
     * @deprecated Use {@link DataConverter#makeArrayNonZero(byte[])} instead.
     */
    @Deprecated
    public static void makeArrayNonZero(byte[] array) {
        DataConverter.makeArrayNonZero(array);
    }

    /**
     * @deprecated Use {@link DataConverter#bigIntegerToByteArray(BigInteger, int, boolean)}
     *     instead.
     */
    @Deprecated
    public static byte[] bigIntegerToByteArray(
            BigInteger value, int expectedLength, boolean removeSignByte) {
        return DataConverter.bigIntegerToByteArray(value, expectedLength, removeSignByte);
    }

    /**
     * @deprecated Use {@link DataConverter#bigIntegerToByteArray(BigInteger)} instead.
     */
    @Deprecated
    public static byte[] bigIntegerToByteArray(BigInteger value) {
        return DataConverter.bigIntegerToByteArray(value);
    }

    /**
     * @deprecated Use {@link DataConverter#convertListToArray(List)} instead.
     */
    @Deprecated
    public static BigInteger[] convertListToArray(List<BigInteger> list) {
        return DataConverter.convertListToArray(list);
    }

    /**
     * @deprecated Use {@link DataConverter#hexStringToByteArray(String)} instead.
     */
    @Deprecated
    public static byte[] hexStringToByteArray(String input) {
        return DataConverter.hexStringToByteArray(input);
    }

    /**
     * @deprecated Use {@link DataConverter#bigIntegerToNullPaddedByteArray(BigInteger, int)}
     *     instead.
     */
    @Deprecated
    public static byte[] bigIntegerToNullPaddedByteArray(BigInteger input, int outputSizeInBytes) {
        return DataConverter.bigIntegerToNullPaddedByteArray(input, outputSizeInBytes);
    }

    /**
     * @deprecated Use {@link DataConverter#reverseByteOrder(byte[])} instead.
     */
    @Deprecated
    public static byte[] reverseByteOrder(byte[] array) {
        return DataConverter.reverseByteOrder(array);
    }

    /**
     * @deprecated Use {@link DataConverter#indexOf(byte[], byte[])} instead.
     */
    @Deprecated
    public static Integer indexOf(byte[] outerArray, byte[] innerArray) {
        return DataConverter.indexOf(outerArray, innerArray);
    }

    /**
     * @deprecated Use {@link DataConverter#byteToUnsignedInt(byte)} instead.
     */
    @Deprecated
    public static int byteToUnsignedInt(byte b) {
        return DataConverter.byteToUnsignedInt(b);
    }
}
