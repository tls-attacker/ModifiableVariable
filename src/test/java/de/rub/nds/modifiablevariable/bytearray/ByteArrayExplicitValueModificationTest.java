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

class ByteArrayExplicitValueModificationTest {

    private ByteArrayExplicitValueModification b1;
    private ByteArrayExplicitValueModification b2;
    private ByteArrayExplicitValueModification b3;
    private Object b4;

    @BeforeEach
    void setUp() {
        b1 = new ByteArrayExplicitValueModification(new byte[] {0x01, 0x02, 0x03});
        b2 = new ByteArrayExplicitValueModification(new byte[] {0x01, 0x02, 0x03});
        b3 = new ByteArrayExplicitValueModification(new byte[] {0x04, 0x05, 0x06});
        b4 = new Object();
    }

    /** Test of modifyImplementationHook method, of class ByteArrayExplicitValueModification. */
    @Test
    void testModifyImplementationHook() {
        // Test with any input, should always return explicit value
        byte[] input = new byte[] {0x10, 0x11, 0x12, 0x13};
        assertArrayEquals(new byte[] {0x01, 0x02, 0x03}, b1.modifyImplementationHook(input));

        // Different input should have no effect
        byte[] differentInput = new byte[] {(byte) 0x99, (byte) 0x98, (byte) 0x97};
        assertArrayEquals(
                new byte[] {0x01, 0x02, 0x03}, b1.modifyImplementationHook(differentInput));

        // Empty input
        byte[] emptyInput = new byte[0];
        assertArrayEquals(new byte[] {0x01, 0x02, 0x03}, b1.modifyImplementationHook(emptyInput));

        // Null input
        assertNull(b1.modifyImplementationHook(null));

        // Verify returned array is a defensive copy
        byte[] result = b1.modifyImplementationHook(input);
        byte[] expected = new byte[] {0x01, 0x02, 0x03};
        assertArrayEquals(expected, result);

        // Modifying the result should not affect future calls
        result[0] = (byte) 0x99;
        byte[] result2 = b1.modifyImplementationHook(input);
        assertArrayEquals(expected, result2);
        assertNotEquals(result[0], result2[0]);
    }

    /** Test of createCopy method, of class ByteArrayExplicitValueModification. */
    @Test
    void testCreateCopy() {
        ByteArrayExplicitValueModification copy = b1.createCopy();
        assertNotSame(b1, copy);
        assertEquals(b1, copy);
        assertArrayEquals(b1.getExplicitValue(), copy.getExplicitValue());
    }

    /** Test copy constructor */
    @Test
    void testCopyConstructor() {
        ByteArrayExplicitValueModification copy = new ByteArrayExplicitValueModification(b1);
        assertNotSame(b1, copy);
        assertEquals(b1, copy);
        assertArrayEquals(b1.getExplicitValue(), copy.getExplicitValue());
    }

    /** Test of getExplicitValue method, of class ByteArrayExplicitValueModification. */
    @Test
    void testGetExplicitValue() {
        assertArrayEquals(new byte[] {0x01, 0x02, 0x03}, b1.getExplicitValue());
    }

    /** Test of setExplicitValue method, of class ByteArrayExplicitValueModification. */
    @Test
    void testSetExplicitValue() {
        ByteArrayExplicitValueModification mod =
                new ByteArrayExplicitValueModification(new byte[] {0x01, 0x02});
        mod.setExplicitValue(new byte[] {0x0A, 0x0B, 0x0C});
        assertArrayEquals(new byte[] {0x0A, 0x0B, 0x0C}, mod.getExplicitValue());
    }

    /** Test setExplicitValue with null value */
    @Test
    static void testSetExplicitValueNull() {
        ByteArrayExplicitValueModification mod =
                new ByteArrayExplicitValueModification(new byte[] {0x01, 0x02});
        assertThrows(NullPointerException.class, () -> mod.setExplicitValue(null));
    }

    /** Test constructor with null value */
    @Test
    static void testConstructorNull() {
        assertThrows(
                NullPointerException.class,
                () -> new ByteArrayExplicitValueModification((byte[]) null));
    }

    /** Test of hashCode method, of class ByteArrayExplicitValueModification. */
    @Test
    void testHashCode() {
        assertEquals(b1.hashCode(), b2.hashCode()); // Same explicit value
        assertNotEquals(b1.hashCode(), b3.hashCode()); // Different explicit value
    }

    /** Test of equals method, of class ByteArrayExplicitValueModification. */
    @Test
    void testEquals() {
        // Same object reference
        assertEquals(b1, b1);

        // Same value
        assertEquals(b1, b2);

        // Different value
        assertNotEquals(b1, b3);

        // Different class
        assertNotEquals(b1, b4);

        // Null check
        assertNotEquals(b1, null);
    }

    /** Test of toString method, of class ByteArrayExplicitValueModification. */
    @Test
    void testToString() {
        String expected = "ByteArrayExplicitValueModification{explicitValue=01 02 03}";
        assertEquals(expected, b1.toString());
    }
}
