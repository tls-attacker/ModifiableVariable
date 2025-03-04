/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.util;

import de.rub.nds.modifiablevariable.bytearray.ModifiableByteArray;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.List;

/** A utility class to handle arrays and array conversions */
public final class ArrayConverter {

    private ArrayConverter() {
        super();
    }

    /**
     * Takes a long value and converts it to 8 bytes (needed for example to convert SQN numbers in
     * TLS records)
     *
     * @param value long value
     * @return long represented by 8 bytes
     */
    public static byte[] longToUint64Bytes(long value) {
        byte[] result = new byte[8];
        result[0] = (byte) (value >>> 56);
        result[1] = (byte) (value >>> 48);
        result[2] = (byte) (value >>> 40);
        result[3] = (byte) (value >>> 32);
        result[4] = (byte) (value >>> 24);
        result[5] = (byte) (value >>> 16);
        result[6] = (byte) (value >>> 8);
        result[7] = (byte) value;
        return result;
    }

    /** Note: This will truncate the long */
    public static byte[] longToUint48Bytes(long value) {
        byte[] output = new byte[6];
        output[0] = (byte) (value >>> 40);
        output[1] = (byte) (value >>> 32);
        output[2] = (byte) (value >>> 24);
        output[3] = (byte) (value >>> 16);
        output[4] = (byte) (value >>> 8);
        output[5] = (byte) value;
        return output;
    }

    /**
     * Takes an int value and converts it to 4 bytes
     *
     * @param value int value
     * @return int represented by 4 bytes
     */
    public static byte[] longToUint32Bytes(long value) {
        byte[] result = new byte[4];
        result[0] = (byte) (value >>> 24);
        result[1] = (byte) (value >>> 16);
        result[2] = (byte) (value >>> 8);
        result[3] = (byte) value;
        return result;
    }

    public static long uInt64BytesToLong(byte[] bytes) {
        return (long) (bytes[0] & 0xFF) << 56
                | (long) (bytes[1] & 0xFF) << 48
                | (long) (bytes[2] & 0xFF) << 40
                | (long) (bytes[3] & 0xFF) << 32
                | (long) (bytes[4] & 0xFF) << 24
                | (long) (bytes[5] & 0xFF) << 16
                | (long) (bytes[6] & 0xFF) << 8
                | (long) (bytes[7] & 0xFF);
    }

    public static long uInt32BytesToLong(byte[] bytes) {
        return (long) (bytes[0] & 0xFF) << 24
                | (bytes[1] & 0xFF) << 16
                | (bytes[2] & 0xFF) << 8
                | bytes[3] & 0xFF;
    }

    /**
     * Takes an integer value and stores its last bytes into a byte array
     *
     * @param value integer value
     * @param size byte size of the new integer byte array
     * @return Byte array representing the integer. If the array size is larger, 00 bytes are
     *     prepended. If the number are larger, MSBs are omitted.
     */
    public static byte[] intToBytes(int value, int size) {
        if (size < 1) {
            throw new IllegalArgumentException("The array must be at least of size 1");
        }
        byte[] result = new byte[size];
        int shift = 0;
        int finalPosition = size > Integer.BYTES ? size - Integer.BYTES : 0;
        for (int i = size - 1; i >= finalPosition; i--) {
            result[i] = (byte) (value >>> shift);
            shift += 8;
        }

        return result;
    }

    /**
     * Takes a long value and stores its last bytes into a byte array
     *
     * @param value long value
     * @param size byte size of the new integer byte array
     * @return Byte array representing the long. If the array size is larger, 00 bytes are
     *     prepended. If the number are larger, MSBs are omitted.
     */
    public static byte[] longToBytes(long value, int size) {
        if (size < 1) {
            throw new IllegalArgumentException("The array must be at least of size 1");
        }
        byte[] result = new byte[size];
        int shift = 0;
        int finalPosition = size > Long.BYTES ? size - Long.BYTES : 0;
        for (int i = size - 1; i >= finalPosition; i--) {
            result[i] = (byte) (value >>> shift);
            shift += 8;
        }

        return result;
    }

    /**
     * Converts multiple bytes into one int value
     *
     * @param value byte array
     * @return integer
     */
    public static int bytesToInt(byte[] value) {
        int result = 0;
        int shift = 0;
        for (int i = value.length - 1; i >= 0; i--) {
            result += (value[i] & 0xFF) << shift;
            shift += 8;
        }
        return result;
    }

    /**
     * Converts multiple bytes into one long value
     *
     * @param value byte array
     * @return long
     */
    public static long bytesToLong(byte[] value) {
        long result = 0;
        int shift = 0;
        for (int i = value.length - 1; i >= 0; i--) {
            result += (long) (value[i] & 0xFF) << shift;
            shift += 8;
        }
        return result;
    }

    public static String bytesToHexString(byte[] array) {
        if (array == null) {
            array = new byte[0];
        }
        boolean usePrettyPrinting = array.length > 15;
        return bytesToHexString(array, usePrettyPrinting);
    }

    public static String bytesToHexString(byte[] array, boolean usePrettyPrinting) {
        if (array == null) {
            array = new byte[0];
        }
        return bytesToHexString(array, usePrettyPrinting, true);
    }

    public static String bytesToHexString(
            byte[] array, boolean usePrettyPrinting, boolean initialNewLine) {
        StringBuilder result = new StringBuilder();
        if (initialNewLine && usePrettyPrinting) {
            result.append("\n");
        }
        for (int i = 0; i < array.length; i++) {
            if (i != 0) {
                if (usePrettyPrinting && i % 16 == 0) {
                    result.append("\n");
                } else {
                    if (usePrettyPrinting && i % 8 == 0) {
                        result.append(" ");
                    }
                    result.append(" ");
                }
            }
            byte b = array[i];
            result.append(String.format("%02X", b));
        }
        return result.toString();
    }

    public static String bytesToHexString(ModifiableByteArray array) {
        return bytesToHexString(array.getValue());
    }

    public static String bytesToHexString(ModifiableByteArray array, boolean usePrettyPrinting) {

        return bytesToHexString(array.getValue(), usePrettyPrinting, true);
    }

    public static String bytesToHexString(
            ModifiableByteArray array, boolean usePrettyPrinting, boolean initialNewLine) {
        return bytesToHexString(array.getValue(), usePrettyPrinting, initialNewLine);
    }

    /**
     * Like bytesToHexString() without any formatting.
     *
     * @param array byte array
     * @return hex string
     */
    public static String bytesToRawHexString(byte[] array) {
        StringBuilder result = new StringBuilder();
        for (byte b : array) {
            result.append(String.format("%02X", b));
        }
        return result.toString();
    }

    @SafeVarargs
    public static <T> T[] concatenate(T[]... arrays) {
        if (arrays == null || arrays.length == 0) {
            throw new IllegalArgumentException(
                    "The minimal number of parameters for this function is one");
        }
        int length = 0;
        for (T[] a : arrays) {
            if (a != null) {
                length += a.length;
            }
        }
        @SuppressWarnings("unchecked")
        T[] result = (T[]) Array.newInstance(arrays[0].getClass().getComponentType(), length);
        int currentOffset = 0;
        for (T[] a : arrays) {
            if (a != null) {
                System.arraycopy(a, 0, result, currentOffset, a.length);
                currentOffset += a.length;
            }
        }
        return result;
    }

    public static byte[] concatenate(byte[]... arrays) {
        if (arrays == null || arrays.length == 0) {
            throw new IllegalArgumentException(
                    "The minimal number of parameters for this function is one");
        }
        int length = 0;
        for (byte[] a : arrays) {
            if (a != null) {
                length += a.length;
            }
        }
        byte[] result = new byte[length];
        int currentOffset = 0;
        for (byte[] a : arrays) {
            if (a != null) {
                System.arraycopy(a, 0, result, currentOffset, a.length);
                currentOffset += a.length;
            }
        }
        return result;
    }

    public static byte[] concatenate(byte[] array1, byte[] array2, int numberOfArray2Bytes) {
        int length = array1.length + numberOfArray2Bytes;
        byte[] result = new byte[length];
        System.arraycopy(array1, 0, result, 0, array1.length);
        System.arraycopy(array2, 0, result, array1.length, numberOfArray2Bytes);
        return result;
    }

    public static void makeArrayNonZero(byte[] array) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == 0) {
                array[i] = 1;
            }
        }
    }

    /**
     * Takes a BigInteger value and returns its byte array representation filled with 0x00 bytes to
     * achieve the block size length.
     *
     * @param value big integer to be converted
     * @param blockSize block size to be achieved
     * @param removeSignByte in a case the removeSignByte is set, the sign byte is removed (in case
     *     the byte array contains one)
     * @return big integer represented in bytes, padded to a specific block size
     */
    public static byte[] bigIntegerToByteArray(
            BigInteger value, int blockSize, boolean removeSignByte) {
        if (blockSize == 0) {
            return new byte[0];
        } else if (value.equals(BigInteger.ZERO)) {
            return new byte[blockSize];
        }
        byte[] array = value.toByteArray();
        int remainder = array.length % blockSize;
        byte[] result = array;
        byte[] tmp;

        if (removeSignByte && result[0] == 0x0) {
            tmp = new byte[result.length - 1];
            System.arraycopy(result, 1, tmp, 0, tmp.length);
            result = tmp;
            remainder = tmp.length % blockSize;
        }

        if (remainder > 0) {
            // add zeros to fit size
            tmp = new byte[result.length + blockSize - remainder];
            System.arraycopy(result, 0, tmp, blockSize - remainder, result.length);
            result = tmp;
        }

        return result;
    }

    /**
     * Takes a BigInteger value and returns its byte array representation, if necessary the sign
     * byte is removed.
     *
     * @param value big integer to be converted
     * @return big integer represented in bytes
     */
    public static byte[] bigIntegerToByteArray(BigInteger value) {
        byte[] result = value.toByteArray();

        if (result[0] == 0x0) {
            byte[] tmp = new byte[result.length - 1];
            System.arraycopy(result, 1, tmp, 0, tmp.length);
            result = tmp;
        }
        return result;
    }

    /**
     * Converts a list of BigIntegers to an array
     *
     * @param list list of big integers
     * @return array of big integers
     */
    public static BigInteger[] convertListToArray(List<BigInteger> list) {
        BigInteger[] result = new BigInteger[list.size()];
        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }
        return result;
    }

    /**
     * Converts a string with an even number of hexadecimal characters to a byte array.
     *
     * @param input hex string
     * @return byte array
     */
    public static byte[] hexStringToByteArray(String input) {
        if (input == null || input.length() % 2 != 0) {
            throw new IllegalArgumentException(
                    "The input must not be null and "
                            + "shall have an even number of hexadecimal characters. Found: "
                            + input);
        }
        byte[] output = new byte[input.length() / 2];
        for (int i = 0; i < output.length; i++) {
            output[i] =
                    (byte)
                            ((Character.digit(input.charAt(i * 2), 16) << 4)
                                    + Character.digit(input.charAt(i * 2 + 1), 16));
        }
        return output;
    }

    /**
     * Converts a BigInteger into a byte array of given size. If the BigInteger doesn't fit into the
     * byte array, bits of the BigInteger will simply be truncated, starting with the most
     * significant bit. If the array is larger than the BigInteger, prepending bytes in the array
     * will be 0x00.
     *
     * @param input big integer
     * @param outputSizeInBytes output size
     * @return big integer converted to a byte array
     */
    public static byte[] bigIntegerToNullPaddedByteArray(BigInteger input, int outputSizeInBytes) {
        if (input == null) {
            throw new IllegalArgumentException("'input' must not be null.");
        }
        byte[] output = new byte[outputSizeInBytes];

        int numByteBlocks = input.bitLength() / 8;
        int remainingBits;

        if (numByteBlocks < output.length) {
            remainingBits = input.bitLength() % 8;
        } else {
            remainingBits = 0;
            numByteBlocks = output.length;
        }

        int i;
        for (i = 0; i < numByteBlocks; i++) {
            output[output.length - 1 - i] = input.shiftRight(i * 8).byteValue();
        }
        if (remainingBits > 0) {
            output[output.length - 1 - i] = input.shiftRight(i * 8).byteValue();
        }
        return output;
    }

    /**
     * Reverses the order of a byte array: So, [0x00,0x01,0x02,0x03] will be returned as
     * [0x03,0x02,0x01,0x00]
     *
     * @param array the byte array to reverse
     * @return byte array with reversed byte order
     */
    public static byte[] reverseByteOrder(byte[] array) {
        int length = array.length;
        byte[] temp = new byte[length];
        int counter = length - 1;
        for (int i = 0; i < length; i++) {
            temp[i] = array[counter--];
        }
        return temp;
    }

    /**
     * Returns the starting index of the innerArray inside the outerArray if present. Returns null
     * if the innerArray is not present in the outerArray
     *
     * @param outerArray Outer byte array to search for inner array
     * @param innerArray byte array searched for
     * @return StartIndex of innerArray in outerArray or null if not present.
     */
    public static Integer indexOf(byte[] outerArray, byte[] innerArray) {
        for (int i = 0; i < outerArray.length - innerArray.length + 1; ++i) {
            boolean found = true;
            for (int j = 0; j < innerArray.length; ++j) {
                if (outerArray[i + j] != innerArray[j]) {
                    found = false;
                    break;
                }
            }
            if (found) return i;
        }
        return null;
    }

    public static int byteToUnsignedInt(byte b) {
        return b & 0xff;
    }
}
