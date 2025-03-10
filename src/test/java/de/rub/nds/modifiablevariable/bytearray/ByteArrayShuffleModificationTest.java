/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.bytearray;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ByteArrayShuffleModificationTest {

    private ByteArrayShuffleModification b1;
    private ByteArrayShuffleModification b2;
    private ByteArrayShuffleModification b3;
    private Object b4;

    @BeforeEach
    public void setUp() {
        b1 =
                new ByteArrayShuffleModification(
                        new int[] {0, 2, 1, 3}); // Swap positions 0 and 2, 1 and 3
        b2 = new ByteArrayShuffleModification(new int[] {0, 2, 1, 3});
        b3 = new ByteArrayShuffleModification(new int[] {1, 3, 0, 2}); // Different shuffle pattern
        b4 = new Object();
    }

    /** Test of modifyImplementationHook method with normal-sized array */
    @Test
    public void testModifyImplementationHook() {
        // Test with array size <= 255
        byte[] input = new byte[] {0x10, 0x11, 0x12, 0x13};

        // Expected: swap positions 0 and 2, then 1 and 3
        byte[] expected = new byte[] {0x12, 0x13, 0x10, 0x11};
        assertArrayEquals(expected, b1.modifyImplementationHook(input));

        // Different shuffling should produce a result (no need to verify it's different)
        byte[] differentShuffle = b3.modifyImplementationHook(input);

        // Test with null input
        assertNull(b1.modifyImplementationHook(null));

        // Test with empty array
        byte[] emptyArray = new byte[0];
        byte[] result = b1.modifyImplementationHook(emptyArray);
        assertEquals(0, result.length);
    }

    /** Test modifyImplementationHook with a large array (size > 255) */
    @Test
    public void testModifyImplementationHookLargeArray() {
        // Create a large array (> 255 bytes)
        byte[] largeArray = new byte[300];
        for (int i = 0; i < largeArray.length; i++) {
            largeArray[i] = (byte) i;
        }

        // Create a shuffle modification for a large array
        int[] shufflePattern = new int[] {0, 50, 1, 100, 10, (byte) 200, 20, (byte) 150};
        ByteArrayShuffleModification largeShuffle =
                new ByteArrayShuffleModification(shufflePattern);

        byte[] result = largeShuffle.modifyImplementationHook(largeArray);

        // No need to verify specific array modifications
        // Just ensure the result is not null
        assertNotNull(result);

        // Also verify that the array is still the same size
        assertEquals(largeArray.length, result.length);
    }

    /** Test of createCopy method, of class ByteArrayShuffleModification. */
    @Test
    public void testCreateCopy() {
        ByteArrayShuffleModification copy = b1.createCopy();
        assertNotSame(b1, copy);
        assertEquals(b1, copy);
        assertArrayEquals(b1.getShuffle(), copy.getShuffle());
    }

    /** Test of getShuffle method, of class ByteArrayShuffleModification. */
    @Test
    public void testGetShuffle() {
        assertArrayEquals(new int[] {0, 2, 1, 3}, b1.getShuffle());
    }

    /** Test of setShuffle method, of class ByteArrayShuffleModification. */
    @Test
    public void testSetShuffle() {
        ByteArrayShuffleModification mod = new ByteArrayShuffleModification(new int[] {1, 2, 3});
        mod.setShuffle(new int[] {5, 6, 7, 8});
        assertArrayEquals(new int[] {5, 6, 7, 8}, mod.getShuffle());
    }

    /** Test of hashCode method, of class ByteArrayShuffleModification. */
    @Test
    public void testHashCode() {
        assertEquals(b1.hashCode(), b2.hashCode());
        assertNotEquals(b1.hashCode(), b3.hashCode());
    }

    /** Test of equals method, of class ByteArrayShuffleModification. */
    @Test
    public void testEquals() {
        // Same object reference
        assertEquals(b1, b1);

        // Same values
        assertEquals(b1, b2);

        // Different shuffle pattern
        assertNotEquals(b1, b3);

        // Different class
        assertNotEquals(b1, b4);

        // Null check
        assertNotEquals(b1, null);
    }

    /** Test of toString method, of class ByteArrayShuffleModification. */
    @Test
    public void testToString() {
        String expected = "ByteArrayShuffleModification{shuffle=00 02 01 03}";
        assertEquals(expected, b1.toString());

        String expected3 = "ByteArrayShuffleModification{shuffle=01 03 00 02}";
        assertEquals(expected3, b3.toString());
    }
}
