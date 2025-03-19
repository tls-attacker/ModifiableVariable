/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/** Tests for the {@link BadFixedRandom} class. */
public class BadFixedRandomTest {

    @Test
    public void testConstructor() {
        byte fixedValue = 42;
        BadFixedRandom badFixedRandom = new BadFixedRandom(fixedValue);

        // The test proves constructor works by testing nextBytes
        byte[] bytes = new byte[10];
        badFixedRandom.nextBytes(bytes);

        for (byte b : bytes) {
            assertEquals(fixedValue, b);
        }
    }

    @Test
    public void testNextBytesWithZeroLength() {
        byte fixedValue = 42;
        BadFixedRandom badFixedRandom = new BadFixedRandom(fixedValue);

        // Test with empty array
        byte[] emptyArray = new byte[0];
        badFixedRandom.nextBytes(emptyArray);

        // No assertions needed - just ensure no exceptions are thrown
        assertEquals(0, emptyArray.length);
    }

    @Test
    public void testNextBytesWithLargeArray() {
        byte fixedValue = (byte) 0xFF; // Using -1 in two's complement
        BadFixedRandom badFixedRandom = new BadFixedRandom(fixedValue);

        // Test with large array to check for any issues with larger sizes
        byte[] largeArray = new byte[1000];
        badFixedRandom.nextBytes(largeArray);

        // All bytes should be set to the fixed value
        for (byte b : largeArray) {
            assertEquals(fixedValue, b);
        }
    }

    @Test
    public void testMultipleNextBytesCall() {
        byte fixedValue = 123;
        BadFixedRandom badFixedRandom = new BadFixedRandom(fixedValue);

        // First call
        byte[] firstArray = new byte[10];
        badFixedRandom.nextBytes(firstArray);

        // Second call with different size
        byte[] secondArray = new byte[5];
        badFixedRandom.nextBytes(secondArray);

        // Verify both arrays have the fixed value
        for (byte b : firstArray) {
            assertEquals(fixedValue, b);
        }

        for (byte b : secondArray) {
            assertEquals(fixedValue, b);
        }
    }

    @Test
    public void testSetSeed() {
        byte fixedValue = 77;
        BadFixedRandom badFixedRandom = new BadFixedRandom(fixedValue);

        // Get initial values
        byte[] initialArray = new byte[5];
        badFixedRandom.nextBytes(initialArray);

        // Set a new seed - should not affect the fixed value
        badFixedRandom.setSeed(123456789L);

        // Get values after setting seed
        byte[] afterSeedArray = new byte[5];
        badFixedRandom.nextBytes(afterSeedArray);

        // Values should still be the fixed value
        for (int i = 0; i < initialArray.length; i++) {
            assertEquals(fixedValue, initialArray[i]);
            assertEquals(fixedValue, afterSeedArray[i]);
        }
    }

    @Test
    public void testNegativeFixedValue() {
        byte fixedValue = -128; // Minimum value for a byte
        BadFixedRandom badFixedRandom = new BadFixedRandom(fixedValue);

        byte[] bytes = new byte[10];
        badFixedRandom.nextBytes(bytes);

        for (byte b : bytes) {
            assertEquals(fixedValue, b);
        }
    }

    @Test
    public void testZeroFixedValue() {
        byte fixedValue = 0;
        BadFixedRandom badFixedRandom = new BadFixedRandom(fixedValue);

        byte[] bytes = new byte[10];
        // Fill with non-zero values first
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = 1;
        }

        badFixedRandom.nextBytes(bytes);

        for (byte b : bytes) {
            assertEquals(fixedValue, b);
        }
    }
}
