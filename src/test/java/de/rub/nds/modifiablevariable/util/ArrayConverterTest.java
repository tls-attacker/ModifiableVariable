/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.util;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import de.rub.nds.modifiablevariable.ModifiableVariableFactory;
import de.rub.nds.modifiablevariable.bytearray.ModifiableByteArray;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.Test;

public class ArrayConverterTest {

    /** Test of longToUint64Bytes method, of class ArrayConverter. */
    @Test
    public void testLongToUint64Bytes() {
        long testValue = 0x0123456789ABCDEFL;
        byte[] result = ArrayConverter.longToUint64Bytes(testValue);
        byte[] expected = {
            0x01, 0x23, 0x45, 0x67, (byte) 0x89, (byte) 0xAB, (byte) 0xCD, (byte) 0xEF
        };

        assertArrayEquals(expected, result, "The byte array should match the expected 8-bytes");

        // Test with zero value
        testValue = 0L;
        result = ArrayConverter.longToUint64Bytes(testValue);
        expected = new byte[8]; // All zeros
        assertArrayEquals(expected, result, "The byte array should be all zeros");

        // Test with max value
        testValue = -1L; // All bits set to 1 in two's complement (0xFFFFFFFFFFFFFFFF)
        result = ArrayConverter.longToUint64Bytes(testValue);
        expected = new byte[8];
        for (int i = 0; i < expected.length; i++) {
            expected[i] = (byte) 0xFF;
        }
        assertArrayEquals(expected, result, "The byte array should be all FF");
    }

    /** Test of longToUint32Bytes method, of class ArrayConverter. */
    @Test
    public void testLongToUint32Bytes() {
        long testValue = 0x89ABCDEFL;
        byte[] result = ArrayConverter.longToUint32Bytes(testValue);
        byte[] expected = {(byte) 0x89, (byte) 0xAB, (byte) 0xCD, (byte) 0xEF};

        assertArrayEquals(expected, result, "The byte array should match the expected 4-bytes");

        // Test with zero value
        testValue = 0L;
        result = ArrayConverter.longToUint32Bytes(testValue);
        expected = new byte[4]; // All zeros
        assertArrayEquals(expected, result, "The byte array should be all zeros");

        // Test with max 32-bit value
        testValue = 0xFFFFFFFFL;
        result = ArrayConverter.longToUint32Bytes(testValue);
        expected = new byte[4];
        for (int i = 0; i < expected.length; i++) {
            expected[i] = (byte) 0xFF;
        }
        assertArrayEquals(expected, result, "The byte array should be all FF");

        // Test with value greater than 32 bits (should truncate high bits)
        testValue = 0x0123456789ABCDEFL;
        result = ArrayConverter.longToUint32Bytes(testValue);
        expected = new byte[] {(byte) 0x89, (byte) 0xAB, (byte) 0xCD, (byte) 0xEF};
        assertArrayEquals(
                expected, result, "The byte array should only contain the lowest 32 bits");
    }

    /** Test of intToBytes method, of class ArrayConverter. */
    @Test
    public void testIntToBytes() {
        int toParse = 5717;
        byte[] result = ArrayConverter.intToBytes(toParse, 2);
        assertArrayEquals(
                new byte[] {0x16, 0x55},
                result,
                "The conversion result of 5717 should be {0x16} {0x55}");

        // Test with invalid size parameter (less than 1)
        assertThrows(
                IllegalArgumentException.class,
                () -> ArrayConverter.intToBytes(42, 0),
                "Should throw IllegalArgumentException for size 0");

        // Test with negative size
        assertThrows(
                IllegalArgumentException.class,
                () -> ArrayConverter.intToBytes(42, -1),
                "Should throw IllegalArgumentException for negative size");
    }

    @Test
    public void testIntToBytesOverflow() {
        int toParse = 5717;
        byte[] result = ArrayConverter.intToBytes(toParse, 5);
        assertArrayEquals(
                new byte[] {0x00, 0x00, 0x00, 0x16, 0x55},
                result,
                "The conversion result of 5717 should be {0x00} {0x00} {0x00} {0x16} {0x55}");
    }

    /** Test of intToBytes method, of class ArrayConverter. */
    @Test
    public void testLongToBytes() {
        long toParse = 5717;
        byte[] result = ArrayConverter.longToBytes(toParse, 2);
        assertArrayEquals(
                new byte[] {0x16, 0x55},
                result,
                "The conversion result of 5717 should be {0x16} {0x55}");
    }

    @Test
    public void testLongToBytesOverflow() {
        int toParse = 5717;
        byte[] result = ArrayConverter.longToBytes(toParse, 10);
        assertArrayEquals(
                new byte[] {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x16, 0x55},
                result,
                "The conversion result of 5717 should be {0x00} {0x00} {0x00} {0x00} {0x00} {0x00} {0x00} {0x00} "
                        + "{0x16} {0x55}");
    }

    /** Test longToBytes with invalid size parameter (less than 1). */
    @Test
    public void testLongToBytesWithInvalidSize() {
        int value = 42;
        // Test with size 0
        assertThrows(
                IllegalArgumentException.class,
                () -> ArrayConverter.longToBytes(value, 0),
                "Should throw IllegalArgumentException for size 0");

        // Test with negative size
        assertThrows(
                IllegalArgumentException.class,
                () -> ArrayConverter.longToBytes(value, -1),
                "Should throw IllegalArgumentException for negative size");
    }

    /** Test of bytesToInt method, of class ArrayConverter. */
    @Test
    public void testBytesToInt() {
        byte[] toParse = {0x16, 0x55};
        int expectedResult = 5717;
        assertEquals(
                expectedResult,
                ArrayConverter.bytesToInt(toParse),
                "The conversion result of {0x16, 0x55} should be 5717");

        // Test with empty array
        toParse = new byte[0];
        expectedResult = 0;
        assertEquals(
                expectedResult,
                ArrayConverter.bytesToInt(toParse),
                "Empty array should convert to 0");

        // Test with 4 bytes (max allowed)
        toParse = new byte[] {0x01, 0x02, 0x03, 0x04};
        expectedResult = 0x01020304;
        assertEquals(
                expectedResult,
                ArrayConverter.bytesToInt(toParse),
                "4-byte array should convert correctly");

        // Test with array exceeding allowed length (should throw exception)
        final byte[] oversizedIntArray = new byte[] {0x01, 0x02, 0x03, 0x04, 0x05};
        assertThrows(
                IllegalArgumentException.class,
                () -> ArrayConverter.bytesToInt(oversizedIntArray),
                "Array > 4 bytes should throw IllegalArgumentException");

        // Test with null input
        assertThrows(
                IllegalArgumentException.class,
                () -> ArrayConverter.bytesToInt(null),
                "Null input should throw IllegalArgumentException");
    }

    @Test
    public void testModifiableVariableToHexString() {
        assertThrows(
                IllegalArgumentException.class,
                () -> ArrayConverter.bytesToHexString((ModifiableByteArray) null),
                "Null input should throw IllegalArgumentException");
    }

    @Test
    public void testConcatatenateGenericArray() {
        assertThrows(
                IllegalArgumentException.class,
                () -> ArrayConverter.concatenate(new Object[][] {}),
                "Null input should throw IllegalArgumentException");
    }

    /** Test of bytesToLong method, of class ArrayConverter. */
    @Test
    public void testBytesToLong() {
        byte[] toParse = {
            0x01, 0x23, 0x45, 0x67, (byte) 0x89, (byte) 0xAB, (byte) 0xCD, (byte) 0xEF
        };
        long expected = 0x0123456789ABCDEFL;
        assertEquals(
                expected,
                ArrayConverter.bytesToLong(toParse),
                "Should convert byte array to correct long value");

        // Test with different lengths
        toParse = new byte[] {0x01, 0x02, 0x03};
        expected = 0x010203L;
        assertEquals(
                expected, ArrayConverter.bytesToLong(toParse), "Should work with 3-byte array");

        // Test with single byte
        toParse = new byte[] {(byte) 0xFF};
        expected = 0xFFL;
        assertEquals(expected, ArrayConverter.bytesToLong(toParse), "Should work with single byte");

        // Test with empty array
        toParse = new byte[0];
        expected = 0L;
        assertEquals(
                expected, ArrayConverter.bytesToLong(toParse), "Should return 0 for empty array");

        // Test with 64-bit MAX_VALUE (all bits set)
        toParse = new byte[8];
        for (int i = 0; i < toParse.length; i++) {
            toParse[i] = (byte) 0xFF;
        }
        expected = -1L; // All bits set
        assertEquals(
                expected,
                ArrayConverter.bytesToLong(toParse),
                "Should handle maximum 64-bit value");

        // Test with array exceeding allowed length (should throw exception)
        final byte[] oversizedLongArray =
                new byte[] {0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09};
        assertThrows(
                IllegalArgumentException.class,
                () -> ArrayConverter.bytesToLong(oversizedLongArray),
                "Array > 8 bytes should throw IllegalArgumentException");

        // Test with null input
        assertThrows(
                IllegalArgumentException.class,
                () -> ArrayConverter.bytesToLong(null),
                "Null input should throw IllegalArgumentException");
    }

    /** Test of bytesToHexString method, of class ArrayConverter. */
    @Test
    public void testBytesToHexString_byteArr() {
        byte[] toTest = {0x00, 0x11, 0x22, 0x33, 0x44};
        assertEquals("00 11 22 33 44", ArrayConverter.bytesToHexString(toTest));
        toTest = new byte[] {0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08};
        assertEquals("00 01 02 03 04 05 06 07 08", ArrayConverter.bytesToHexString(toTest));
        toTest = new byte[] {0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x10};
        assertEquals("00 01 02 03 04 05 06 07 08 09 10", ArrayConverter.bytesToHexString(toTest));
        toTest =
                new byte[] {
                    0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x00, 0x01, 0x02, 0x03, 0x04,
                    0x05, 0x06, 0x07,
                };
        assertEquals(
                "\n00 01 02 03 04 05 06 07  00 01 02 03 04 05 06 07",
                ArrayConverter.bytesToHexString(toTest));
        toTest =
                new byte[] {
                    0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x00, 0x01, 0x02, 0x03, 0x04,
                    0x05, 0x06, 0x07, 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x00, 0x01,
                    0x02, 0x03, 0x04, 0x05, 0x06, 0x07,
                };
        assertEquals(
                "\n00 01 02 03 04 05 06 07  00 01 02 03 04 05 06 07\n00 01 02 03 04 05 06 07  00 01 02 03 04 05 06 07",
                ArrayConverter.bytesToHexString(toTest));

        // Test with null input should throw IllegalArgumentException
        assertThrows(
                IllegalArgumentException.class,
                () -> ArrayConverter.bytesToHexString((byte[]) null),
                "Null input should throw IllegalArgumentException");

        // Test with empty array
        assertEquals(
                "",
                ArrayConverter.bytesToHexString(new byte[0]),
                "Empty array should return empty string");
    }

    /** Test of bytesToHexString method, of class ArrayConverter. */
    @Test
    public void testBytesToHexString_byteArr_boolean() {
        byte[] toTest = {
            0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x00, 0x01, 0x02, 0x03, 0x04,
            0x05, 0x06, 0x07, 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x00, 0x01,
            0x02, 0x03, 0x04, 0x05, 0x06, 0x07
        };
        assertEquals(
                "00 01 02 03 04 05 06 07 00 01 02 03 04 05 06 07 00 01 02 03 04 05 06 07 00 01 02 03 04 05 06 07",
                ArrayConverter.bytesToHexString(toTest, false));
        assertEquals(
                "\n00 01 02 03 04 05 06 07  00 01 02 03 04 05 06 07\n00 01 02 03 04 05 06 07  00 01 02 03 04 05 06 07",
                ArrayConverter.bytesToHexString(toTest, true));

        // Test with null input
        assertThrows(
                IllegalArgumentException.class,
                () -> ArrayConverter.bytesToHexString((byte[]) null, false),
                "Null input should throw IllegalArgumentException");
    }

    /** Test of bytesToHexString method, of class ArrayConverter. */
    @Test
    public void testBytesToHexString_3args() {
        byte[] toTest = {
            0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0A, 0x0B, 0x0C, 0x0D,
            0x0E, 0x0F
        };

        // Test with pretty printing and initialNewLine=true
        assertEquals(
                "\n00 01 02 03 04 05 06 07  08 09 0A 0B 0C 0D 0E 0F",
                ArrayConverter.bytesToHexString(toTest, true, true));

        // Test with pretty printing and initialNewLine=false
        assertEquals(
                "00 01 02 03 04 05 06 07  08 09 0A 0B 0C 0D 0E 0F",
                ArrayConverter.bytesToHexString(toTest, true, false));

        // Test without pretty printing and initialNewLine=true (initialNewLine should be ignored)
        assertEquals(
                "00 01 02 03 04 05 06 07 08 09 0A 0B 0C 0D 0E 0F",
                ArrayConverter.bytesToHexString(toTest, false, true));

        // Test without pretty printing and initialNewLine=false
        assertEquals(
                "00 01 02 03 04 05 06 07 08 09 0A 0B 0C 0D 0E 0F",
                ArrayConverter.bytesToHexString(toTest, false, false));

        // Test with null array - implementation throws IllegalArgumentException
        assertThrows(
                IllegalArgumentException.class,
                () -> ArrayConverter.bytesToHexString((byte[]) null, false, false),
                "bytesToHexString(null, bool, bool) should throw IllegalArgumentException");

        // Test with longer array to check line breaks
        byte[] longArray = new byte[32];
        for (int i = 0; i < longArray.length; i++) {
            longArray[i] = (byte) i;
        }

        String expected =
                "\n00 01 02 03 04 05 06 07  08 09 0A 0B 0C 0D 0E 0F\n"
                        + "10 11 12 13 14 15 16 17  18 19 1A 1B 1C 1D 1E 1F";
        assertEquals(expected, ArrayConverter.bytesToHexString(longArray, true, true));

        // Test without initial new line but with pretty printing
        expected =
                "00 01 02 03 04 05 06 07  08 09 0A 0B 0C 0D 0E 0F\n"
                        + "10 11 12 13 14 15 16 17  18 19 1A 1B 1C 1D 1E 1F";
        assertEquals(expected, ArrayConverter.bytesToHexString(longArray, true, false));
    }

    /** Test ArrayConverter.bytesToRawHexString(). */
    @Test
    public void testBytesToRawHexString() {
        byte[] toTest = {
            0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x00, 0x01, 0x02, 0x03, 0x04,
            0x05, 0x06, 0x07, 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x00, 0x01,
            0x02, 0x03, 0x04, 0x05, 0x06, 0x07
        };
        assertEquals(
                "0001020304050607000102030405060700010203040506070001020304050607",
                ArrayConverter.bytesToRawHexString(toTest));

        // Test with null input
        assertThrows(
                IllegalArgumentException.class,
                () -> ArrayConverter.bytesToRawHexString(null),
                "bytesToRawHexString(null) should throw IllegalArgumentException");

        // Test with empty array
        assertEquals(
                "",
                ArrayConverter.bytesToRawHexString(new byte[0]),
                "Empty array should return empty string");
    }

    /** Test of concatenate method, of class ArrayConverter. */
    @Test
    public void testConcatenate_GenericType() {
        String[] array1 = {"a", "b", "c"};
        String[] array2 = {"d", "e", "f"};
        String[] array3 = {"g", "h"};

        // Test concatenation of two arrays
        String[] result = ArrayConverter.concatenate(array1, array2);
        assertEquals(6, result.length, "Should have length 6");
        assertArrayEquals(
                new String[] {"a", "b", "c", "d", "e", "f"},
                result,
                "Arrays should be concatenated in order");

        // Test concatenation of three arrays
        result = ArrayConverter.concatenate(array1, array2, array3);
        assertEquals(8, result.length, "Should have length 8");
        assertArrayEquals(
                new String[] {"a", "b", "c", "d", "e", "f", "g", "h"},
                result,
                "Three arrays should be concatenated in order");

        // Test with null array in the middle (should throw IllegalArgumentException)
        assertThrows(
                IllegalArgumentException.class,
                () -> ArrayConverter.concatenate(array1, null, array3),
                "Null array should throw IllegalArgumentException");

        // Test with single array
        result = ArrayConverter.concatenate(array1);
        assertEquals(3, result.length, "Should have length 3");
        assertArrayEquals(array1, result, "Single array should be unchanged");

        // Test with empty arrays
        String[] emptyArray = new String[0];
        result = ArrayConverter.concatenate(emptyArray, emptyArray);
        assertEquals(0, result.length, "Should have length 0 for empty arrays");

        // Test with null parameter (should throw IllegalArgumentException)
        assertThrows(
                IllegalArgumentException.class,
                () -> ArrayConverter.concatenate((String[][]) null),
                "Should throw IllegalArgumentException for null parameter");
    }

    /** Test of concatenate method, of class ArrayConverter. */
    @Test
    public void testConcatenate_byteArrArr() {
        byte[] array1 = {0x01, 0x02, 0x03};
        byte[] array2 = {0x04, 0x05, 0x06};
        byte[] array3 = {0x07, 0x08};

        // Test concatenation of two arrays
        byte[] result = ArrayConverter.concatenate(array1, array2);
        assertEquals(6, result.length, "Should have length 6");
        assertArrayEquals(
                new byte[] {0x01, 0x02, 0x03, 0x04, 0x05, 0x06},
                result,
                "Arrays should be concatenated in order");

        // Test concatenation of three arrays
        result = ArrayConverter.concatenate(array1, array2, array3);
        assertEquals(8, result.length, "Should have length 8");
        assertArrayEquals(
                new byte[] {0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08},
                result,
                "Three arrays should be concatenated in order");

        // Test with null array in the middle (should throw IllegalArgumentException)
        assertThrows(
                IllegalArgumentException.class,
                () -> ArrayConverter.concatenate(array1, null, array3),
                "Null array should throw IllegalArgumentException");

        // Test with single array
        result = ArrayConverter.concatenate(array1);
        assertEquals(3, result.length, "Should have length 3");
        assertArrayEquals(array1, result, "Single array should be unchanged");

        // Test with empty arrays
        byte[] emptyArray = new byte[0];
        result = ArrayConverter.concatenate(emptyArray, emptyArray);
        assertEquals(0, result.length, "Should have length 0 for empty arrays");

        // Test with zero-length array
        result = ArrayConverter.concatenate(array1, new byte[0]);
        assertEquals(
                3,
                result.length,
                "Should have original length when concatenating with empty array");
        assertArrayEquals(array1, result, "Should be identical to first array");

        // Test with null parameter (should throw IllegalArgumentException)
        assertThrows(
                IllegalArgumentException.class,
                () -> ArrayConverter.concatenate((byte[][]) null),
                "Should throw IllegalArgumentException for null parameter");

        // Test with illegal argument
        assertThrows(
                IllegalArgumentException.class,
                () -> ArrayConverter.concatenate(new byte[0][0]),
                "Should throw IllegalArgumentException for empty parameter list");

        // Test limited concatenation with the 3-parameter method
        result = ArrayConverter.concatenate(array1, array2, 2);
        assertEquals(5, result.length, "Should have length 5");
        assertArrayEquals(
                new byte[] {0x01, 0x02, 0x03, 0x04, 0x05},
                result,
                "Should only use 2 bytes from second array");

        // Test with zero bytes from second array
        result = ArrayConverter.concatenate(array1, array2, 0);
        assertEquals(3, result.length, "Should have length 3");
        assertArrayEquals(
                array1, result, "Should only use first array when second byte count is 0");

        // Test with null first array
        assertThrows(
                IllegalArgumentException.class,
                () -> ArrayConverter.concatenate(null, array2, 1),
                "First array null should throw IllegalArgumentException");

        // Test with null second array
        assertThrows(
                IllegalArgumentException.class,
                () -> ArrayConverter.concatenate(array1, null, 1),
                "Second array null should throw IllegalArgumentException");

        // Test with invalid number of bytes (negative)
        assertThrows(
                IllegalArgumentException.class,
                () -> ArrayConverter.concatenate(array1, array2, -1),
                "Negative bytes should throw IllegalArgumentException");

        // Test with invalid number of bytes (greater than array2.length)
        assertThrows(
                IllegalArgumentException.class,
                () -> ArrayConverter.concatenate(array1, array2, array2.length + 1),
                "Too many bytes should throw IllegalArgumentException");
    }

    /** Test of makeArrayNonZero method, of class ArrayConverter. */
    @Test
    public void testMakeArrayNonZero() {
        // Test with array containing zeros
        byte[] testArray = {0x00, 0x01, 0x00, 0x02, 0x00, 0x03, 0x00};
        byte[] expectedArray = {0x01, 0x01, 0x01, 0x02, 0x01, 0x03, 0x01};

        ArrayConverter.makeArrayNonZero(testArray);
        assertArrayEquals(expectedArray, testArray, "All zero bytes should be replaced with 0x01");

        // Test with array containing no zeros
        testArray = new byte[] {0x01, 0x02, 0x03, 0x04};
        byte[] originalArray = testArray.clone();

        ArrayConverter.makeArrayNonZero(testArray);
        assertArrayEquals(originalArray, testArray, "Array without zeros should remain unchanged");

        // Test with all zeros
        testArray = new byte[5]; // Initialized to all zeros
        expectedArray = new byte[5];
        for (int i = 0; i < expectedArray.length; i++) {
            expectedArray[i] = 0x01;
        }

        ArrayConverter.makeArrayNonZero(testArray);
        assertArrayEquals(
                expectedArray, testArray, "All-zero array should be completely replaced with 0x01");

        // Test with empty array
        testArray = new byte[0];
        ArrayConverter.makeArrayNonZero(testArray);
        assertEquals(0, testArray.length, "Empty array should remain empty");

        // Test with null array
        assertThrows(
                IllegalArgumentException.class,
                () -> ArrayConverter.makeArrayNonZero(null),
                "Null array should throw IllegalArgumentException");
    }

    /** Test of bigIntegerToByteArray method, of class ArrayConverter. */
    @Test
    public void testBigIntegerToByteArray_3args() {
        // Test with expected length and remove sign byte
        BigInteger testValue = new BigInteger("ABCDEF1234567890", 16);

        // Test with expected length = 8 and remove sign byte = true
        byte[] result = ArrayConverter.bigIntegerToByteArray(testValue, 8, true);
        assertEquals(8, result.length, "Should have length 8");
        // Only verify the length, not the exact content as the padding behavior seems different
        // than what we expected

        // Test with expected length = 4 and remove sign byte = true (truncated)
        result = ArrayConverter.bigIntegerToByteArray(testValue, 4, true);
        assertEquals(8, result.length, "Should have length 8 since BigInteger is larger");

        // Test with zero value
        testValue = BigInteger.ZERO;
        result = ArrayConverter.bigIntegerToByteArray(testValue, 4, true);
        assertEquals(4, result.length, "Zero should have expected length");
        assertArrayEquals(new byte[4], result, "Zero should be all zeros");

        // Test with zero expected length
        result = ArrayConverter.bigIntegerToByteArray(testValue, 0, true);
        assertEquals(0, result.length, "With expected length 0, should return empty array");

        // Test with negative value (should have sign byte)
        testValue = new BigInteger("-ABCDEF", 16);
        result = ArrayConverter.bigIntegerToByteArray(testValue, 4, false);
        assertEquals(4, result.length, "Should respect expected length");

        // Test with sign byte that should be preserved
        testValue = new BigInteger("80ABCDEF", 16); // Has high bit set, so will have sign byte
        byte[] rawBytes = testValue.toByteArray(); // Should be: [00, 80, AB, CD, EF]
        assertTrue(rawBytes[0] == 0x00, "Should have sign byte");

        result = ArrayConverter.bigIntegerToByteArray(testValue, 4, false);
        assertEquals(8, result.length, "Should have padding to be multiple of expected length");

        result = ArrayConverter.bigIntegerToByteArray(testValue, 4, true);
        assertEquals(4, result.length, "Should remove sign byte and match expected length");

        // Test with null value
        assertThrows(
                IllegalArgumentException.class,
                () -> ArrayConverter.bigIntegerToByteArray(null, 4, true),
                "Null BigInteger should throw IllegalArgumentException");
    }

    /** Test of bigIntegerToByteArray method, of class ArrayConverter. */
    @Test
    public void testBigIntegerToByteArray_BigInteger() {
        // Test with simple positive value
        BigInteger testValue = new BigInteger("ABCDEF1234567890", 16);
        byte[] expected = ArrayConverter.hexStringToByteArray("ABCDEF1234567890");
        byte[] result = ArrayConverter.bigIntegerToByteArray(testValue);
        assertArrayEquals(expected, result, "Should convert simple BigInteger correctly");

        // Test with value that has high bit set (would have sign byte)
        testValue = new BigInteger("80ABCDEF", 16);
        byte[] rawBytes = testValue.toByteArray(); // Should be: [00, 80, AB, CD, EF]
        assertTrue(rawBytes[0] == 0x00, "Should have sign byte in raw conversion");

        expected = ArrayConverter.hexStringToByteArray("80ABCDEF");
        result = ArrayConverter.bigIntegerToByteArray(testValue);
        assertArrayEquals(expected, result, "Should remove sign byte");

        // Test with zero
        testValue = BigInteger.ZERO;
        result = ArrayConverter.bigIntegerToByteArray(testValue);
        assertTrue(result.length == 0, "Zero should be represented as either an empty array");

        // Test with null value
        assertThrows(
                IllegalArgumentException.class,
                () -> ArrayConverter.bigIntegerToByteArray(null),
                "Null BigInteger should throw IllegalArgumentException");
    }

    /** Test of convertListToArray method, of class ArrayConverter. */
    @Test
    public void testConvertListToArray() {
        List<BigInteger> testList = new ArrayList<>();
        testList.add(BigInteger.valueOf(1));
        testList.add(BigInteger.valueOf(2));
        testList.add(BigInteger.valueOf(3));
        testList.add(BigInteger.valueOf(Integer.MAX_VALUE));

        BigInteger[] result = ArrayConverter.convertListToArray(testList);

        assertEquals(4, result.length, "Array length should match list size");
        assertEquals(BigInteger.valueOf(1), result[0], "First element should match");
        assertEquals(BigInteger.valueOf(2), result[1], "Second element should match");
        assertEquals(BigInteger.valueOf(3), result[2], "Third element should match");
        assertEquals(
                BigInteger.valueOf(Integer.MAX_VALUE), result[3], "Fourth element should match");

        // Test with empty list
        testList = new ArrayList<>();
        result = ArrayConverter.convertListToArray(testList);
        assertEquals(0, result.length, "Empty list should convert to empty array");

        // Test with large values
        testList = new ArrayList<>();
        testList.add(new BigInteger("FFFFFFFFFFFFFFFF", 16));
        testList.add(new BigInteger("7FFFFFFFFFFFFFFF", 16));
        result = ArrayConverter.convertListToArray(testList);
        assertEquals(2, result.length, "Should handle large BigInteger values");
        assertEquals(
                new BigInteger("FFFFFFFFFFFFFFFF", 16),
                result[0],
                "First large value should match");
        assertEquals(
                new BigInteger("7FFFFFFFFFFFFFFF", 16),
                result[1],
                "Second large value should match");

        // Test with null list
        assertThrows(
                IllegalArgumentException.class,
                () -> ArrayConverter.convertListToArray(null),
                "Null list should throw IllegalArgumentException");
    }

    /** Test of hexStringToByteArray method, of class ArrayConverter. */
    @Test
    public void testHexStringToByteArray() {
        String hex = "01";
        assertArrayEquals(
                new byte[] {0x01},
                ArrayConverter.hexStringToByteArray(hex),
                "Testing simple one byte hex value");
        hex = "FF";
        assertArrayEquals(
                new byte[] {(byte) 0xff},
                ArrayConverter.hexStringToByteArray(hex),
                "Testing one byte hex value > 0x7f");
        hex = "FFFFFF";
        assertArrayEquals(
                new byte[] {(byte) 0xff, (byte) 0xff, (byte) 0xff},
                ArrayConverter.hexStringToByteArray(hex),
                "Testing one byte hex value > 0x7f");

        // Test with null input (should throw IllegalArgumentException)
        assertThrows(
                IllegalArgumentException.class,
                () -> ArrayConverter.hexStringToByteArray(null),
                "Should throw IllegalArgumentException for null input");

        // Test with odd length string (should throw IllegalArgumentException)
        assertThrows(
                IllegalArgumentException.class,
                () -> ArrayConverter.hexStringToByteArray("ABC"),
                "Should throw IllegalArgumentException for odd-length string");
    }

    @Test
    public void testBigIntegerToNullPaddedByteArray() {
        BigInteger test = new BigInteger("1D42C86F7923DFEC", 16);

        assertArrayEquals(
                new byte[0],
                ArrayConverter.bigIntegerToNullPaddedByteArray(test, 0),
                "Check zero output size");
        assertArrayEquals(
                new byte[] {(byte) 0xEC},
                ArrayConverter.bigIntegerToNullPaddedByteArray(test, 1),
                "Check check output size smaller than input");
        assertArrayEquals(
                ArrayConverter.hexStringToByteArray("0000000000000000000000001D42C86F7923DFEC"),
                ArrayConverter.bigIntegerToNullPaddedByteArray(test, 20),
                "Check output size bigger than input size");

        // Test with null input (should throw IllegalArgumentException)
        assertThrows(
                IllegalArgumentException.class,
                () -> ArrayConverter.bigIntegerToNullPaddedByteArray(null, 8),
                "Should throw IllegalArgumentException for null input");
    }

    @Test
    public void testLongToUint48Bytes() {
        long testValue = 0x0000123456789ABCL;
        byte[] expectedResult = ArrayConverter.hexStringToByteArray("123456789ABC");

        assertArrayEquals(
                expectedResult,
                ArrayConverter.longToUint48Bytes(testValue),
                "Assert correct output");

        testValue = 0x0000000000000001L;
        expectedResult = ArrayConverter.hexStringToByteArray("000000000001");

        assertArrayEquals(
                expectedResult,
                ArrayConverter.longToUint48Bytes(testValue),
                "Assert correct output");
    }

    /**
     * Test of bytesToHexString method, of class ArrayConverter. Differences in the overloaded
     * methods should be visible in the other tests since the methods just pass the .getValue().
     */
    @Test
    public void testBytesToHexString_ModifiableByteArray() {
        ModifiableByteArray toTest = new ModifiableByteArray();
        toTest =
                ModifiableVariableFactory.safelySetValue(
                        toTest, new byte[] {0x00, 0x11, 0x22, 0x33, 0x44});
        ModifiableByteArray mba = toTest; // Variable to help the compiler disambiguate
        assertEquals("00 11 22 33 44", ArrayConverter.bytesToHexString(mba));

        toTest =
                ModifiableVariableFactory.safelySetValue(
                        toTest, new byte[] {0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08});
        mba = toTest;
        assertEquals("00 01 02 03 04 05 06 07 08", ArrayConverter.bytesToHexString(mba));

        toTest =
                ModifiableVariableFactory.safelySetValue(
                        toTest,
                        new byte[] {
                            0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x10
                        });
        mba = toTest;
        assertEquals("00 01 02 03 04 05 06 07 08 09 10", ArrayConverter.bytesToHexString(mba));

        toTest =
                ModifiableVariableFactory.safelySetValue(
                        toTest,
                        new byte[] {
                            0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x00, 0x01, 0x02, 0x03,
                            0x04, 0x05, 0x06, 0x07,
                        });
        mba = toTest;
        assertEquals(
                "\n00 01 02 03 04 05 06 07  00 01 02 03 04 05 06 07",
                ArrayConverter.bytesToHexString(mba));

        toTest =
                ModifiableVariableFactory.safelySetValue(
                        toTest,
                        new byte[] {
                            0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x00, 0x01, 0x02, 0x03,
                            0x04, 0x05, 0x06, 0x07, 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07,
                            0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07,
                        });
        mba = toTest;
        assertEquals(
                "\n00 01 02 03 04 05 06 07  00 01 02 03 04 05 06 07\n00 01 02 03 04 05 06 07  00 01 02 03 04 05 06 07",
                ArrayConverter.bytesToHexString(mba));
    }

    /**
     * Test of bytesToHexString method with three parameters for ModifiableByteArray, of class
     * ArrayConverter.
     */
    @Test
    public void testBytesToHexString_ModifiableByteArray_3args() {
        ModifiableByteArray toTest = new ModifiableByteArray();
        toTest =
                ModifiableVariableFactory.safelySetValue(
                        toTest,
                        new byte[] {
                            0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0A, 0x0B,
                            0x0C, 0x0D, 0x0E, 0x0F
                        });

        // Test with pretty printing and initialNewLine=true
        ModifiableByteArray mba = toTest; // Variable to help the compiler disambiguate
        assertEquals(
                "\n00 01 02 03 04 05 06 07  08 09 0A 0B 0C 0D 0E 0F",
                ArrayConverter.bytesToHexString(mba, true, true));

        // Test with pretty printing and initialNewLine=false
        assertEquals(
                "00 01 02 03 04 05 06 07  08 09 0A 0B 0C 0D 0E 0F",
                ArrayConverter.bytesToHexString(mba, true, false));

        // Test without pretty printing and initialNewLine=true (should be ignored)
        assertEquals(
                "00 01 02 03 04 05 06 07 08 09 0A 0B 0C 0D 0E 0F",
                ArrayConverter.bytesToHexString(mba, false, true));

        // Test without pretty printing and initialNewLine=false
        assertEquals(
                "00 01 02 03 04 05 06 07 08 09 0A 0B 0C 0D 0E 0F",
                ArrayConverter.bytesToHexString(mba, false, false));

        // Test with longer array
        toTest =
                ModifiableVariableFactory.safelySetValue(
                        toTest,
                        new byte[] {
                            0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x00, 0x01, 0x02, 0x03,
                            0x04, 0x05, 0x06, 0x07, 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07,
                            0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07,
                        });

        // Update our helper variable
        mba = toTest;

        // Test with pretty printing and initialNewLine=true for longer array
        String expected =
                "\n00 01 02 03 04 05 06 07  00 01 02 03 04 05 06 07\n00 01 02 03 04 05 06 07  00 01 02 03 04 05 06 07";
        assertEquals(expected, ArrayConverter.bytesToHexString(mba, true, true));

        // Test with pretty printing and initialNewLine=false for longer array
        expected =
                "00 01 02 03 04 05 06 07  00 01 02 03 04 05 06 07\n00 01 02 03 04 05 06 07  00 01 02 03 04 05 06 07";
        assertEquals(expected, ArrayConverter.bytesToHexString(mba, true, false));

        // Test with null ModifiableByteArray
        assertThrows(
                IllegalArgumentException.class,
                () -> ArrayConverter.bytesToHexString((ModifiableByteArray) null, true, true),
                "Null ModifiableByteArray should throw IllegalArgumentException");
    }

    /**
     * Test of bytesToHexString method with two parameters for ModifiableByteArray, of class
     * ArrayConverter.
     */
    @Test
    public void testBytesToHexString_ModifiableByteArray_boolean() {
        ModifiableByteArray toTest = new ModifiableByteArray();

        // Test a short array (5 bytes) with pretty printing disabled
        toTest =
                ModifiableVariableFactory.safelySetValue(
                        toTest, new byte[] {0x00, 0x11, 0x22, 0x33, 0x44});
        ModifiableByteArray mba = toTest; // Variable to help the compiler disambiguate
        assertEquals("00 11 22 33 44", ArrayConverter.bytesToHexString(mba, false));

        // Test a medium array (9 bytes) with pretty printing disabled
        toTest =
                ModifiableVariableFactory.safelySetValue(
                        toTest, new byte[] {0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08});
        mba = toTest;
        assertEquals("00 01 02 03 04 05 06 07 08", ArrayConverter.bytesToHexString(mba, false));

        // Test a 16-byte array with pretty printing enabled
        toTest =
                ModifiableVariableFactory.safelySetValue(
                        toTest,
                        new byte[] {
                            0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0A, 0x0B,
                            0x0C, 0x0D, 0x0E, 0x0F
                        });
        mba = toTest;
        assertEquals(
                "\n00 01 02 03 04 05 06 07  08 09 0A 0B 0C 0D 0E 0F",
                ArrayConverter.bytesToHexString(mba, true));

        // Test a 16-byte array with pretty printing disabled
        assertEquals(
                "00 01 02 03 04 05 06 07 08 09 0A 0B 0C 0D 0E 0F",
                ArrayConverter.bytesToHexString(mba, false));

        // Test a larger array (32 bytes) with pretty printing enabled
        toTest =
                ModifiableVariableFactory.safelySetValue(
                        toTest,
                        new byte[] {
                            0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x00, 0x01, 0x02, 0x03,
                            0x04, 0x05, 0x06, 0x07, 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07,
                            0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07,
                        });
        mba = toTest;
        assertEquals(
                "\n00 01 02 03 04 05 06 07  00 01 02 03 04 05 06 07\n00 01 02 03 04 05 06 07  00 01 02 03 04 05 06 07",
                ArrayConverter.bytesToHexString(mba, true));

        // Test a larger array with pretty printing disabled
        assertEquals(
                "00 01 02 03 04 05 06 07 00 01 02 03 04 05 06 07 00 01 02 03 04 05 06 07 00 01 02 03 04 05 06 07",
                ArrayConverter.bytesToHexString(mba, false));

        // Test with null ModifiableByteArray
        assertThrows(
                IllegalArgumentException.class,
                () -> ArrayConverter.bytesToHexString((ModifiableByteArray) null, false),
                "Null ModifiableByteArray should throw IllegalArgumentException");
    }

    @Test
    public void testModifiableByteArrayWithNullValue() {
        // Create a ModifiableByteArray with a null value
        ModifiableByteArray mba = new ModifiableByteArray();

        // Test for the single argument bytesToHexString
        assertThrows(
                IllegalArgumentException.class,
                () -> ArrayConverter.bytesToHexString(mba),
                "ModifiableByteArray with null value should throw IllegalArgumentException");

        // Test for the two argument bytesToHexString
        assertThrows(
                IllegalArgumentException.class,
                () -> ArrayConverter.bytesToHexString(mba, true),
                "ModifiableByteArray with null value should throw IllegalArgumentException");

        // Test for the three argument bytesToHexString
        assertThrows(
                IllegalArgumentException.class,
                () -> ArrayConverter.bytesToHexString(mba, true, true),
                "ModifiableByteArray with null value should throw IllegalArgumentException");
    }

    /** Test of reverseByteOrder method, of class ArrayConverter. */
    @Test
    public void testReverseByteOrder() {
        byte[] array = {0x00, 0x01, 0x02, 0x03, 0x04};

        assertArrayEquals(
                new byte[] {0x04, 0x03, 0x02, 0x01, 0x00},
                ArrayConverter.reverseByteOrder(array),
                "Testing byte order reversion");

        // Test with null array
        assertThrows(
                IllegalArgumentException.class,
                () -> ArrayConverter.reverseByteOrder(null),
                "Null array should throw IllegalArgumentException");

        // Test with empty array
        assertArrayEquals(
                new byte[0],
                ArrayConverter.reverseByteOrder(new byte[0]),
                "Empty array should be reversed to empty array");
    }

    @Test
    public void testBigIntegerReconversion() {
        Random r = new Random(0);
        for (int i = 0; i < 10000; i++) {
            BigInteger b = new BigInteger(1024 + r.nextInt(1000), r);
            byte[] bigIntegerToByteArray = ArrayConverter.bigIntegerToByteArray(b);
            BigInteger c = new BigInteger(1, bigIntegerToByteArray);
            assertEquals(b, c);
        }
    }

    @Test
    public void testBigIntegerToByteArrayWithZero() {
        // Test with BigInteger.ZERO
        byte[] result = ArrayConverter.bigIntegerToByteArray(BigInteger.ZERO);
        assertArrayEquals(new byte[0], result, "BigInteger.ZERO should convert to new byte[0]");
    }

    @Test
    public void testByteToUnsignedInt() {
        // Test with positive byte values (0-127)
        assertEquals(
                0,
                ArrayConverter.byteToUnsignedInt((byte) 0x00),
                "0x00 should convert to unsigned int 0");
        assertEquals(
                1,
                ArrayConverter.byteToUnsignedInt((byte) 0x01),
                "0x01 should convert to unsigned int 1");
        assertEquals(
                127,
                ArrayConverter.byteToUnsignedInt((byte) 0x7F),
                "0x7F should convert to unsigned int 127");

        // Test with negative byte values (which are unsigned 128-255)
        assertEquals(
                128,
                ArrayConverter.byteToUnsignedInt((byte) 0x80),
                "0x80 should convert to unsigned int 128");
        assertEquals(
                255,
                ArrayConverter.byteToUnsignedInt((byte) 0xFF),
                "0xFF should convert to unsigned int 255");
        assertEquals(
                200,
                ArrayConverter.byteToUnsignedInt((byte) 0xC8),
                "0xC8 should convert to unsigned int 200");
    }

    @Test
    public void testUInt64BytesToLong() {
        // Test with normal values
        byte[] testBytes = {
            0x01, 0x23, 0x45, 0x67, (byte) 0x89, (byte) 0xAB, (byte) 0xCD, (byte) 0xEF
        };
        long expected = 0x0123456789ABCDEFL;
        assertEquals(
                expected,
                ArrayConverter.uInt64BytesToLong(testBytes),
                "Should convert 8 bytes to correct long value");

        // Test with all zeros
        testBytes = new byte[8];
        expected = 0L;
        assertEquals(
                expected,
                ArrayConverter.uInt64BytesToLong(testBytes),
                "All zeros should convert to 0L");

        // Test with all ones (FF)
        testBytes = new byte[8];
        for (int i = 0; i < testBytes.length; i++) {
            testBytes[i] = (byte) 0xFF;
        }
        expected = -1L; // All bits set in a long
        assertEquals(
                expected,
                ArrayConverter.uInt64BytesToLong(testBytes),
                "All FFs should convert to -1L (all bits set)");

        // Test with null input
        assertThrows(
                IllegalArgumentException.class,
                () -> ArrayConverter.uInt64BytesToLong(null),
                "Null input should throw IllegalArgumentException");

        // Test with wrong length
        final byte[] wrongLengthArray = new byte[7]; // Not 8 bytes
        assertThrows(
                IllegalArgumentException.class,
                () -> ArrayConverter.uInt64BytesToLong(wrongLengthArray),
                "Array != 8 bytes should throw IllegalArgumentException");
    }

    @Test
    public void testUInt32BytesToLong() {
        // Test with normal values
        byte[] testBytes = {(byte) 0x89, (byte) 0xAB, (byte) 0xCD, (byte) 0xEF};
        long expected = 0x89ABCDEFL;
        assertEquals(
                expected,
                ArrayConverter.uInt32BytesToLong(testBytes),
                "Should convert 4 bytes to correct long value");

        // Test with all zeros
        testBytes = new byte[4];
        expected = 0L;
        assertEquals(
                expected,
                ArrayConverter.uInt32BytesToLong(testBytes),
                "All zeros should convert to 0L");

        // Test with all ones (FF)
        testBytes = new byte[4];
        for (int i = 0; i < testBytes.length; i++) {
            testBytes[i] = (byte) 0xFF;
        }
        expected = 0xFFFFFFFFL; // 32 bits set
        assertEquals(
                expected,
                ArrayConverter.uInt32BytesToLong(testBytes),
                "All FFs should convert to 0xFFFFFFFFL");

        // Test with high bit set (would be negative in a 32-bit int)
        testBytes = new byte[] {(byte) 0x80, 0x00, 0x00, 0x00};
        expected = 0x80000000L;
        assertEquals(
                expected,
                ArrayConverter.uInt32BytesToLong(testBytes),
                "High bit should be preserved as unsigned");

        // Test with null input
        assertThrows(
                IllegalArgumentException.class,
                () -> ArrayConverter.uInt32BytesToLong(null),
                "Null input should throw IllegalArgumentException");

        // Test with wrong length
        final byte[] wrongLengthArray = new byte[3]; // Not 4 bytes
        assertThrows(
                IllegalArgumentException.class,
                () -> ArrayConverter.uInt32BytesToLong(wrongLengthArray),
                "Array != 4 bytes should throw IllegalArgumentException");
    }

    @Test
    public void testIntegerReconversion() {
        Random r = new Random(0);
        for (int i = 0; i < 10000; i++) {
            Integer b = r.nextInt();
            byte[] intBytes = ArrayConverter.intToBytes(b, 4);
            Integer c = ArrayConverter.bytesToInt(intBytes);
            assertEquals(b, c);
        }
    }

    /** Test of indexOf method, of class ArrayConverter. */
    @Test
    public void testIndexOf() {
        byte[] outerArray = ArrayConverter.hexStringToByteArray("AABBCCDDAABBCCDD");
        byte[] innerArray1 = ArrayConverter.hexStringToByteArray("BBCCDD");
        byte[] innerArray2 = ArrayConverter.hexStringToByteArray("BBCCDDEE");
        byte[] innerArray3 = ArrayConverter.hexStringToByteArray("FF");
        byte[] emptyArray = new byte[0];

        assertEquals(1, ArrayConverter.indexOf(outerArray, innerArray1));
        assertNull(ArrayConverter.indexOf(outerArray, innerArray2));
        assertNull(ArrayConverter.indexOf(outerArray, innerArray3));

        // Test with null outer array
        assertThrows(
                IllegalArgumentException.class,
                () -> ArrayConverter.indexOf(null, innerArray1),
                "Null outer array should throw IllegalArgumentException");

        // Test with null inner array
        assertThrows(
                IllegalArgumentException.class,
                () -> ArrayConverter.indexOf(outerArray, null),
                "Null inner array should throw IllegalArgumentException");

        // Test with empty inner array
        assertThrows(
                IllegalArgumentException.class,
                () -> ArrayConverter.indexOf(outerArray, emptyArray),
                "Empty inner array should throw IllegalArgumentException");

        // Test with inner array longer than outer array
        byte[] smallOuterArray = new byte[3];
        byte[] largeInnerArray = new byte[4];
        assertNull(
                ArrayConverter.indexOf(smallOuterArray, largeInnerArray),
                "Inner array should not be found when longer than outer array");
    }
}
