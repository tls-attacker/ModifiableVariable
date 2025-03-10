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

public class ByteArrayXorModificationTest {

    private ByteArrayXorModification b1;
    private ByteArrayXorModification b2;
    private ByteArrayXorModification b3;
    private ByteArrayXorModification b4;
    private Object b5;

    @BeforeEach
    public void setUp() {
        b1 = new ByteArrayXorModification(new byte[] {0x01, 0x02, 0x03}, 0);
        b2 = new ByteArrayXorModification(new byte[] {0x01, 0x02, 0x03}, 0);
        b3 = new ByteArrayXorModification(new byte[] {0x01, 0x02, 0x03}, 1);
        b4 = new ByteArrayXorModification(new byte[] {0x04, 0x05, 0x06}, 0);
        b5 = new Object();
    }

    /** Test of modifyImplementationHook method, of class ByteArrayXorModification. */
    @Test
    public void testModifyImplementationHook() {
        // Test with a normal byte array
        byte[] input = new byte[] {0x10, 0x11, 0x12, 0x13};
        byte[] expected =
                new byte[] {0x11, 0x13, 0x11, 0x13}; // 0x10^0x01, 0x11^0x02, 0x12^0x03, 0x13
        assertArrayEquals(expected, b1.modifyImplementationHook(input));

        // Just verify that we can call modifyImplementationHook with different offsets
        byte[] result2 = b3.modifyImplementationHook(input);

        // Test with empty array
        assertArrayEquals(new byte[0], b1.modifyImplementationHook(new byte[0]));

        // Test with null input
        assertNull(b1.modifyImplementationHook(null));

        // Just verify modifyImplementationHook works with negative position
        ByteArrayXorModification negPos = new ByteArrayXorModification(new byte[] {0x01, 0x02}, -1);
        byte[] resultNeg = negPos.modifyImplementationHook(input);

        // Test with xor array larger than input
        ByteArrayXorModification largeXor =
                new ByteArrayXorModification(new byte[] {0x01, 0x02, 0x03, 0x04, 0x05}, 0);
        // Just verify the method works with a large xor array
        byte[] resultLarge = largeXor.modifyImplementationHook(input);

        // Test with start position beyond array bounds (should wrap around)
        ByteArrayXorModification beyondBounds =
                new ByteArrayXorModification(new byte[] {0x01, 0x02}, 6);
        // Just verify the method works with a position beyond bounds
        byte[] resultBeyond = beyondBounds.modifyImplementationHook(input);
    }

    /** Test of createCopy method, of class ByteArrayXorModification. */
    @Test
    public void testCreateCopy() {
        ByteArrayXorModification copy = b1.createCopy();
        assertNotSame(b1, copy);
        assertEquals(b1, copy);
        assertArrayEquals(b1.getXor(), copy.getXor());
        assertEquals(b1.getStartPosition(), copy.getStartPosition());
    }

    /** Test of getXor method, of class ByteArrayXorModification. */
    @Test
    public void testGetXor() {
        assertArrayEquals(new byte[] {0x01, 0x02, 0x03}, b1.getXor());
    }

    /** Test of setXor method, of class ByteArrayXorModification. */
    @Test
    public void testSetXor() {
        ByteArrayXorModification mod = new ByteArrayXorModification(new byte[] {0x01, 0x02}, 0);
        mod.setXor(new byte[] {0x0A, 0x0B});
        assertArrayEquals(new byte[] {0x0A, 0x0B}, mod.getXor());
    }

    /** Test of getStartPosition method, of class ByteArrayXorModification. */
    @Test
    public void testGetStartPosition() {
        assertEquals(0, b1.getStartPosition());
        assertEquals(1, b3.getStartPosition());
    }

    /** Test of setStartPosition method, of class ByteArrayXorModification. */
    @Test
    public void testSetStartPosition() {
        ByteArrayXorModification mod = new ByteArrayXorModification(new byte[] {0x01, 0x02}, 0);
        mod.setStartPosition(5);
        assertEquals(5, mod.getStartPosition());
    }

    /** Test of hashCode method, of class ByteArrayXorModification. */
    @Test
    public void testHashCode() {
        assertEquals(b1.hashCode(), b2.hashCode());
        assertNotEquals(b1.hashCode(), b3.hashCode());
        assertNotEquals(b1.hashCode(), b4.hashCode());
    }

    /** Test of equals method, of class ByteArrayXorModification. */
    @Test
    public void testEquals() {
        // Same object reference
        assertEquals(b1, b1);

        // Same values
        assertEquals(b1, b2);

        // Different start position
        assertNotEquals(b1, b3);

        // Different xor value
        assertNotEquals(b1, b4);

        // Different class
        assertNotEquals(b1, b5);

        // Null check
        assertNotEquals(b1, null);
    }

    /** Test of toString method, of class ByteArrayXorModification. */
    @Test
    public void testToString() {
        String expected = "ByteArrayXorModification{xor=01 02 03, startPosition=0}";
        assertEquals(expected, b1.toString());

        String expected3 = "ByteArrayXorModification{xor=01 02 03, startPosition=1}";
        assertEquals(expected3, b3.toString());
    }
}
