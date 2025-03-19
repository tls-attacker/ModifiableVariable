/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.util;

import static org.junit.jupiter.api.Assertions.*;

import de.rub.nds.modifiablevariable.biginteger.ModifiableBigInteger;
import de.rub.nds.modifiablevariable.bool.ModifiableBoolean;
import de.rub.nds.modifiablevariable.bytearray.ModifiableByteArray;
import de.rub.nds.modifiablevariable.integer.ModifiableInteger;
import de.rub.nds.modifiablevariable.longint.ModifiableLong;
import de.rub.nds.modifiablevariable.singlebyte.ModifiableByte;
import de.rub.nds.modifiablevariable.string.ModifiableString;
import java.math.BigInteger;
import org.junit.jupiter.api.Test;

class ModifiableTest {

    @Test
    void testExplicitValueModification() {
        // Test Integer explicit value
        Integer testInt = 42;
        ModifiableInteger intVar = Modifiable.explicit(testInt);
        intVar.setOriginalValue(10);
        assertEquals(testInt, intVar.getValue());

        // Test byte array explicit value
        byte[] testArray = new byte[] {1, 2, 3};
        ModifiableByteArray byteArrayVar = Modifiable.explicit(testArray);
        byteArrayVar.setOriginalValue(new byte[] {4, 5, 6});
        assertArrayEquals(testArray, byteArrayVar.getValue());

        // Test String explicit value
        String testString = "test";
        ModifiableString stringVar = Modifiable.explicit(testString);
        stringVar.setOriginalValue("original");
        assertEquals(testString, stringVar.getValue());

        // Test BigInteger explicit value
        BigInteger bigInt = BigInteger.valueOf(123);
        ModifiableBigInteger bigIntVar = Modifiable.explicit(bigInt);
        bigIntVar.setOriginalValue(BigInteger.valueOf(456));
        assertEquals(bigInt, bigIntVar.getValue());

        // Test Long explicit value
        Long longVal = 9876543210L;
        ModifiableLong longVar = Modifiable.explicit(longVal);
        longVar.setOriginalValue(1234567890L);
        assertEquals(longVal, longVar.getValue());

        // Test Boolean explicit value
        Boolean boolVal = true;
        ModifiableBoolean boolVar = Modifiable.explicit(boolVal);
        boolVar.setOriginalValue(false);
        assertEquals(boolVal, boolVar.getValue());

        // Test Byte explicit value
        Byte byteVal = (byte) 0xAA;
        ModifiableByte byteVar = Modifiable.explicit(byteVal);
        byteVar.setOriginalValue((byte) 0x55);
        assertEquals(byteVal, byteVar.getValue());
    }

    @Test
    void testAddModification() {
        // Test Integer add
        Integer summand = 5;
        ModifiableInteger intVar = Modifiable.add(summand);
        intVar.setOriginalValue(10);
        assertEquals(15, intVar.getValue());

        // Test Byte add
        Byte byteSummand = 3;
        ModifiableByte byteVar = Modifiable.add(byteSummand);
        byteVar.setOriginalValue((byte) 7);
        assertEquals((byte) 10, byteVar.getValue());

        // Test BigInteger add
        BigInteger bigSummand = BigInteger.valueOf(100);
        ModifiableBigInteger bigIntVar = Modifiable.add(bigSummand);
        bigIntVar.setOriginalValue(BigInteger.valueOf(50));
        assertEquals(BigInteger.valueOf(150), bigIntVar.getValue());

        // Test Long add
        Long longSummand = 1000L;
        ModifiableLong longVar = Modifiable.add(longSummand);
        longVar.setOriginalValue(234L);
        assertEquals(1234L, longVar.getValue());

        // Test with negative values
        ModifiableInteger negativeTest = Modifiable.add(-20);
        negativeTest.setOriginalValue(50);
        assertEquals(30, negativeTest.getValue());
    }

    @Test
    void testXorModification() {
        // Test Integer XOR
        Integer xorValue = 0x0F;
        ModifiableInteger intVar = Modifiable.xor(xorValue);
        intVar.setOriginalValue(0xF0);
        assertEquals(0xFF, intVar.getValue());

        // Test byte array XOR
        byte[] xorArray = new byte[] {0x0F, 0x0F};
        ModifiableByteArray byteArrayVar = Modifiable.xor(xorArray, 0);
        byteArrayVar.setOriginalValue(new byte[] {(byte) 0xF0, (byte) 0xF0});
        assertArrayEquals(new byte[] {(byte) 0xFF, (byte) 0xFF}, byteArrayVar.getValue());

        // Test BigInteger XOR
        BigInteger bigIntXor = BigInteger.valueOf(0x0F);
        ModifiableBigInteger bigIntVar = Modifiable.xor(bigIntXor);
        bigIntVar.setOriginalValue(BigInteger.valueOf(0xF0));
        assertEquals(BigInteger.valueOf(0xFF), bigIntVar.getValue());

        // Test Long XOR
        Long longXor = 0x0FL;
        ModifiableLong longVar = Modifiable.xor(longXor);
        longVar.setOriginalValue(0xF0L);
        assertEquals(0xFFL, longVar.getValue());

        // Test Byte XOR
        Byte byteXor = 0x0F;
        ModifiableByte byteVar = Modifiable.xor(byteXor);
        byteVar.setOriginalValue((byte) 0xF0);
        assertEquals((byte) 0xFF, byteVar.getValue());

        // Test byte array XOR at different position
        byte[] multiByteArray = new byte[] {(byte) 0xAA, (byte) 0xBB, (byte) 0xCC, (byte) 0xDD};
        byte[] xorPartial = new byte[] {(byte) 0x55, (byte) 0x55};
        ModifiableByteArray partialXorVar = Modifiable.xor(xorPartial, 1);
        partialXorVar.setOriginalValue(multiByteArray);
        byte[] expected = new byte[] {(byte) 0xAA, (byte) 0xEE, (byte) 0x99, (byte) 0xDD};
        assertArrayEquals(expected, partialXorVar.getValue());
    }

    @Test
    void testShiftModification() {
        // Test Integer left shift
        Integer shiftLeft = 4;
        ModifiableInteger intVarLeft = Modifiable.shiftLeft(shiftLeft);
        intVarLeft.setOriginalValue(1);
        assertEquals(16, intVarLeft.getValue());

        // Test Integer right shift
        Integer shiftRight = 2;
        ModifiableInteger intVarRight = Modifiable.shiftRight(shiftRight);
        intVarRight.setOriginalValue(20);
        assertEquals(5, intVarRight.getValue());

        // Test BigInteger left shift
        ModifiableBigInteger bigIntVarLeft = Modifiable.shiftLeftBigInteger(3);
        bigIntVarLeft.setOriginalValue(BigInteger.valueOf(5));
        assertEquals(BigInteger.valueOf(40), bigIntVarLeft.getValue());

        // Test BigInteger right shift
        ModifiableBigInteger bigIntVarRight = Modifiable.shiftRightBigInteger(1);
        bigIntVarRight.setOriginalValue(BigInteger.valueOf(19));
        assertEquals(BigInteger.valueOf(9), bigIntVarRight.getValue());

        // Test Long left shift
        ModifiableLong longVarLeft = Modifiable.shiftLeftLong(3);
        longVarLeft.setOriginalValue(2L);
        assertEquals(16L, longVarLeft.getValue());

        // Test Long right shift
        ModifiableLong longVarRight = Modifiable.shiftRightLong(1);
        longVarRight.setOriginalValue(15L);
        assertEquals(7L, longVarRight.getValue());

        // Test large shifts
        ModifiableInteger largeShift = Modifiable.shiftLeft(16);
        largeShift.setOriginalValue(1);
        assertEquals(65536, largeShift.getValue());
    }

    @Test
    void testToggleModification() {
        ModifiableBoolean boolVar = Modifiable.toggle();
        boolVar.setOriginalValue(true);
        assertEquals(false, boolVar.getValue());

        boolVar.setOriginalValue(false);
        assertEquals(true, boolVar.getValue());
    }

    @Test
    void testByteArrayOperations() {
        // Test prepend
        byte[] prependValue = new byte[] {1, 2};
        ModifiableByteArray prependVar = Modifiable.prepend(prependValue);
        prependVar.setOriginalValue(new byte[] {3, 4});
        assertArrayEquals(new byte[] {1, 2, 3, 4}, prependVar.getValue());

        // Test append
        byte[] appendValue = new byte[] {5, 6};
        ModifiableByteArray appendVar = Modifiable.append(appendValue);
        appendVar.setOriginalValue(new byte[] {3, 4});
        assertArrayEquals(new byte[] {3, 4, 5, 6}, appendVar.getValue());

        // Test insert
        byte[] insertValue = new byte[] {9, 10};
        ModifiableByteArray insertVar = Modifiable.insert(insertValue, 1);
        insertVar.setOriginalValue(new byte[] {7, 8});
        assertArrayEquals(new byte[] {7, 9, 10, 8}, insertVar.getValue());
    }

    @Test
    void testStringOperations() {
        // Test prepend
        String prependValue = "Hello, ";
        ModifiableString prependVar = Modifiable.prepend(prependValue);
        prependVar.setOriginalValue("World!");
        assertEquals("Hello, World!", prependVar.getValue());

        // Test append
        String appendValue = " World!";
        ModifiableString appendVar = Modifiable.append(appendValue);
        appendVar.setOriginalValue("Hello,");
        assertEquals("Hello, World!", appendVar.getValue());

        // Test insert
        String insertValue = "beautiful ";
        ModifiableString insertVar = Modifiable.insert(insertValue, 7);
        insertVar.setOriginalValue("Hello, World!");
        assertEquals("Hello, beautiful World!", insertVar.getValue());
    }

    @Test
    void testSubtractModification() {
        // Test Integer subtract
        Integer subtrahend = 3;
        ModifiableInteger intVar = Modifiable.sub(subtrahend);
        intVar.setOriginalValue(10);
        assertEquals(7, intVar.getValue());

        // Test Long subtract
        Long longSubtrahend = 5L;
        ModifiableLong longVar = Modifiable.sub(longSubtrahend);
        longVar.setOriginalValue(15L);
        assertEquals(10L, longVar.getValue());

        // Test BigInteger subtract
        BigInteger bigSubtrahend = BigInteger.valueOf(30);
        ModifiableBigInteger bigIntVar = Modifiable.sub(bigSubtrahend);
        bigIntVar.setOriginalValue(BigInteger.valueOf(100));
        assertEquals(BigInteger.valueOf(70), bigIntVar.getValue());

        // Test Byte subtract
        Byte byteSubtrahend = 40;
        ModifiableByte byteVar = Modifiable.sub(byteSubtrahend);
        byteVar.setOriginalValue((byte) 100);
        assertEquals((byte) 60, byteVar.getValue());

        // Test with negative values
        ModifiableInteger negTest = Modifiable.sub(-10);
        negTest.setOriginalValue(5);
        assertEquals(15, negTest.getValue());
    }

    @Test
    void testMultiplyModification() {
        // Test Integer multiply
        Integer factor = 3;
        ModifiableInteger intVar = Modifiable.multiply(factor);
        intVar.setOriginalValue(5);
        assertEquals(15, intVar.getValue());

        // Test BigInteger multiply
        BigInteger bigFactor = BigInteger.valueOf(4);
        ModifiableBigInteger bigIntVar = Modifiable.multiplyBigInteger(bigFactor);
        bigIntVar.setOriginalValue(BigInteger.valueOf(5));
        assertEquals(BigInteger.valueOf(20), bigIntVar.getValue());

        // Test Long multiply
        Long longFactor = 6L;
        ModifiableLong longVar = Modifiable.multiply(longFactor);
        longVar.setOriginalValue(7L);
        assertEquals(42L, longVar.getValue());

        // Test with negative factor
        ModifiableInteger negFactor = Modifiable.multiply(-2);
        negFactor.setOriginalValue(10);
        assertEquals(-20, negFactor.getValue());

        // Test with zero factor
        ModifiableInteger zeroFactor = Modifiable.multiply(0);
        zeroFactor.setOriginalValue(999);
        assertEquals(0, zeroFactor.getValue());

        // Test with large numbers
        ModifiableBigInteger largeBigInt =
                Modifiable.multiplyBigInteger(BigInteger.valueOf(1000000));
        largeBigInt.setOriginalValue(BigInteger.valueOf(1000000));
        assertEquals(BigInteger.valueOf(1000000000000L), largeBigInt.getValue());
    }

    @Test
    void testSwapEndianModification() {
        // Test Integer swap endian
        ModifiableInteger intVar = Modifiable.swapEndianIntger();
        intVar.setOriginalValue(0x12345678);
        assertEquals(0x78563412, intVar.getValue());

        // Test Long swap endian
        ModifiableLong longVar = Modifiable.swapEndianLong();
        longVar.setOriginalValue(0x123456789ABCDEFL);
        assertEquals(0xEFCDAB8967452301L, longVar.getValue());
    }

    @Test
    void testByteArrayManipulations() {
        // Test delete
        ModifiableByteArray deleteVar = Modifiable.delete(1, 2);
        deleteVar.setOriginalValue(new byte[] {1, 2, 3, 4, 5});
        assertArrayEquals(new byte[] {1, 4, 5}, deleteVar.getValue());

        // Test duplicate
        ModifiableByteArray duplicateVar = Modifiable.duplicate();
        duplicateVar.setOriginalValue(new byte[] {1, 2, 3});
        assertArrayEquals(new byte[] {1, 2, 3, 1, 2, 3}, duplicateVar.getValue());

        // Test shuffle
        // The shuffle pattern defines pairs of indices to swap
        int[] shufflePattern = new int[] {0, 2}; // Swap positions 0 and 2
        ModifiableByteArray shuffleVar = Modifiable.shuffle(shufflePattern);
        shuffleVar.setOriginalValue(new byte[] {1, 2, 3});
        assertArrayEquals(new byte[] {3, 2, 1}, shuffleVar.getValue());

        // Test delete with boundary conditions
        // Delete from beginning
        ModifiableByteArray deleteFromStart = Modifiable.delete(0, 2);
        deleteFromStart.setOriginalValue(new byte[] {1, 2, 3, 4, 5});
        assertArrayEquals(new byte[] {3, 4, 5}, deleteFromStart.getValue());

        // Delete at end
        ModifiableByteArray deleteAtEnd = Modifiable.delete(3, 2);
        deleteAtEnd.setOriginalValue(new byte[] {1, 2, 3, 4, 5});
        assertArrayEquals(new byte[] {1, 2, 3}, deleteAtEnd.getValue());

        // Delete entire array
        ModifiableByteArray deleteAll = Modifiable.delete(0, 5);
        deleteAll.setOriginalValue(new byte[] {1, 2, 3, 4, 5});
        assertArrayEquals(new byte[] {}, deleteAll.getValue());

        // Test multiple swaps with shuffle
        int[] multiSwap = new int[] {0, 1, 1, 2, 0, 3};
        ModifiableByteArray multiSwapVar = Modifiable.shuffle(multiSwap);
        multiSwapVar.setOriginalValue(new byte[] {1, 2, 3, 4});
        // Expected operations:
        // 1. Swap positions 0 and 1: [2, 1, 3, 4]
        // 2. Swap positions 1 and 2: [2, 3, 1, 4]
        // 3. Swap positions 0 and 3: [4, 3, 1, 2]
        assertArrayEquals(new byte[] {4, 3, 1, 2}, multiSwapVar.getValue());
    }
}
