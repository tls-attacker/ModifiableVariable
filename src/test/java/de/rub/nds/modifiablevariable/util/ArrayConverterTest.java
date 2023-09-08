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

import de.rub.nds.modifiablevariable.ModifiableVariableFactory;
import de.rub.nds.modifiablevariable.bytearray.ModifiableByteArray;
import java.math.BigInteger;
import java.util.Random;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class ArrayConverterTest {

    /** Test of longToUint64Bytes method, of class ArrayConverter. */
    @Disabled("Not yet implemented")
    @Test
    public void testLongToUint64Bytes() {}

    /** Test of longToUint32Bytes method, of class ArrayConverter. */
    @Disabled("Not yet implemented")
    @Test
    public void testLongToUint32Bytes() {}

    /** Test of intToBytes method, of class ArrayConverter. */
    @Test
    public void testIntToBytes() {
        int toParse = 5717;
        byte[] result = ArrayConverter.intToBytes(toParse, 2);
        assertArrayEquals(
                new byte[] {0x16, 0x55},
                result,
                "The conversion result of 5717 should be {0x16} {0x55}");
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

    /** Test of bytesToInt method, of class ArrayConverter. */
    @Test
    public void testBytesToInt() {
        byte[] toParse = {0x16, 0x55};
        int expectedResult = 5717;
        assertEquals(
                expectedResult,
                ArrayConverter.bytesToInt(toParse),
                "The conversion result of {0x16, 0x55} should be 5717");
    }

    /** Test of bytesToLong method, of class ArrayConverter. */
    @Disabled("Not yet implemented")
    @Test
    public void testBytesToLong() {
        /*
         * TODO get the casting correct. long result = 571714867398058L; byte[] toParse = {0x02, 0x07, (byte) 0xf8,
         * (byte) 0xbd, (byte) 0x95, (byte) 0x85, (byte) 0xaa}; byte[] test = ArrayConverter.longToBytes(result, 7); int
         * a = 0; //assertEquals(result, ArrayConverter.bytesToLong(toParse));
         */

    }

    /** Test of bytesToHexString method, of class ArrayConverter. */
    @Test
    public void testBytesToHexString_byteArr() {
        byte[] toTest = new byte[] {0x00, 0x11, 0x22, 0x33, 0x44};
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
    }

    /** Test of bytesToHexString method, of class ArrayConverter. */
    @Test
    public void testBytesToHexString_byteArr_boolean() {
        byte[] toTest =
                new byte[] {
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
    }

    /** Test of bytesToHexString method, of class ArrayConverter. */
    @Disabled("Not yet implemented")
    @Test
    public void testBytesToHexString_3args() {}

    /** Test ArrayConverter.bytesToRawHexString(). */
    @Test
    public void testBytesToRawHexString() {
        byte[] toTest =
                new byte[] {
                    0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x00, 0x01, 0x02, 0x03, 0x04,
                    0x05, 0x06, 0x07, 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x00, 0x01,
                    0x02, 0x03, 0x04, 0x05, 0x06, 0x07
                };
        assertEquals(
                "0001020304050607000102030405060700010203040506070001020304050607",
                ArrayConverter.bytesToRawHexString(toTest));
    }

    /** Test of concatenate method, of class ArrayConverter. */
    @Disabled("Not yet implemented")
    @Test
    public void testConcatenate_GenericType() {}

    /** Test of concatenate method, of class ArrayConverter. */
    @Disabled("Not yet implemented")
    @Test
    public void testConcatenate_byteArrArr() {}

    /** Test of makeArrayNonZero method, of class ArrayConverter. */
    @Disabled("Not yet implemented")
    @Test
    public void testMakeArrayNonZero() {}

    /** Test of bigIntegerToByteArray method, of class ArrayConverter. */
    @Disabled("Not yet implemented")
    @Test
    public void testBigIntegerToByteArray_3args() {}

    /** Test of bigIntegerToByteArray method, of class ArrayConverter. */
    @Disabled("Not yet implemented")
    @Test
    public void testBigIntegerToByteArray_BigInteger() {}

    /** Test of convertListToArray method, of class ArrayConverter. */
    @Disabled("Not yet implemented")
    @Test
    public void testConvertListToArray() {}

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
        assertEquals("00 11 22 33 44", ArrayConverter.bytesToHexString(toTest));

        toTest =
                ModifiableVariableFactory.safelySetValue(
                        toTest, new byte[] {0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08});
        assertEquals("00 01 02 03 04 05 06 07 08", ArrayConverter.bytesToHexString(toTest));

        toTest =
                ModifiableVariableFactory.safelySetValue(
                        toTest,
                        new byte[] {
                            0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x10
                        });
        assertEquals("00 01 02 03 04 05 06 07 08 09 10", ArrayConverter.bytesToHexString(toTest));

        toTest =
                ModifiableVariableFactory.safelySetValue(
                        toTest,
                        new byte[] {
                            0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x00, 0x01, 0x02, 0x03,
                            0x04, 0x05, 0x06, 0x07,
                        });
        assertEquals(
                "\n00 01 02 03 04 05 06 07  00 01 02 03 04 05 06 07",
                ArrayConverter.bytesToHexString(toTest));

        toTest =
                ModifiableVariableFactory.safelySetValue(
                        toTest,
                        new byte[] {
                            0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x00, 0x01, 0x02, 0x03,
                            0x04, 0x05, 0x06, 0x07, 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07,
                            0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07,
                        });
        assertEquals(
                "\n00 01 02 03 04 05 06 07  00 01 02 03 04 05 06 07\n00 01 02 03 04 05 06 07  00 01 02 03 04 05 06 07",
                ArrayConverter.bytesToHexString(toTest));
    }

    /** Test of reverseByteOrder method, of class ArrayConverter. */
    @Test
    public void testReverseByteOrder() {
        byte[] array = new byte[] {0x00, 0x01, 0x02, 0x03, 0x04};

        assertArrayEquals(
                new byte[] {0x04, 0x03, 0x02, 0x01, 0x00},
                ArrayConverter.reverseByteOrder(array),
                "Testing byte order reversion");
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

        assertEquals(1, ArrayConverter.indexOf(outerArray, innerArray1));
        assertEquals(-1, ArrayConverter.indexOf(outerArray, innerArray2));
        assertEquals(-1, ArrayConverter.indexOf(outerArray, innerArray3));
    }
}
