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

public class ByteArrayAppendValueModificationTest {

    private ByteArrayAppendValueModification b1;
    private ByteArrayAppendValueModification b2;
    private ByteArrayAppendValueModification b3;
    private Object b4;

    @BeforeEach
    public void setUp() {
        b1 = new ByteArrayAppendValueModification(new byte[] {0x01, 0x02, 0x03});
        b2 = new ByteArrayAppendValueModification(new byte[] {0x01, 0x02, 0x03});
        b3 = new ByteArrayAppendValueModification(new byte[] {0x04, 0x05, 0x06});
        b4 = new Object();
    }

    /** Test of modifyImplementationHook method, of class ByteArrayAppendValueModification. */
    @Test
    public void testModifyImplementationHook() {
        // Test with a normal byte array
        byte[] input = new byte[] {0x10, 0x11, 0x12};
        byte[] expected = new byte[] {0x10, 0x11, 0x12, 0x01, 0x02, 0x03}; // Input + bytesToAppend
        assertArrayEquals(expected, b1.modifyImplementationHook(input));

        // Test with empty array
        byte[] emptyInput = new byte[0];
        byte[] expectedEmpty = new byte[] {0x01, 0x02, 0x03}; // Just the bytesToAppend
        assertArrayEquals(expectedEmpty, b1.modifyImplementationHook(emptyInput));

        // Test with null input
        assertNull(b1.modifyImplementationHook(null));
    }

    /** Test of createCopy method, of class ByteArrayAppendValueModification. */
    @Test
    public void testCreateCopy() {
        ByteArrayAppendValueModification copy = b1.createCopy();
        assertNotSame(b1, copy);
        assertEquals(b1, copy);
        assertArrayEquals(b1.getBytesToAppend(), copy.getBytesToAppend());
    }

    /** Test copy constructor */
    @Test
    public void testCopyConstructor() {
        ByteArrayAppendValueModification copy = new ByteArrayAppendValueModification(b1);
        assertNotSame(b1, copy);
        assertEquals(b1, copy);
        assertArrayEquals(b1.getBytesToAppend(), copy.getBytesToAppend());
    }

    /** Test of getBytesToAppend method, of class ByteArrayAppendValueModification. */
    @Test
    public void testGetBytesToAppend() {
        assertArrayEquals(new byte[] {0x01, 0x02, 0x03}, b1.getBytesToAppend());
    }

    /** Test of setBytesToAppend method, of class ByteArrayAppendValueModification. */
    @Test
    public void testSetBytesToAppend() {
        ByteArrayAppendValueModification mod =
                new ByteArrayAppendValueModification(new byte[] {0x01, 0x02});
        mod.setBytesToAppend(new byte[] {0x0A, 0x0B, 0x0C});
        assertArrayEquals(new byte[] {0x0A, 0x0B, 0x0C}, mod.getBytesToAppend());
    }

    /** Test setBytesToAppend with null value */
    @Test
    public void testSetBytesToAppendNull() {
        ByteArrayAppendValueModification mod =
                new ByteArrayAppendValueModification(new byte[] {0x01, 0x02});
        assertThrows(NullPointerException.class, () -> mod.setBytesToAppend(null));
    }

    /** Test constructor with null value */
    @Test
    public void testConstructorNull() {
        assertThrows(
                NullPointerException.class,
                () -> new ByteArrayAppendValueModification((byte[]) null));
    }

    /** Test of hashCode method, of class ByteArrayAppendValueModification. */
    @Test
    public void testHashCode() {
        assertEquals(b1.hashCode(), b2.hashCode()); // Same values
        assertNotEquals(b1.hashCode(), b3.hashCode()); // Different values
    }

    /** Test of equals method, of class ByteArrayAppendValueModification. */
    @Test
    public void testEquals() {
        // Same object reference
        assertEquals(b1, b1);

        // Same values
        assertEquals(b1, b2);

        // Different values
        assertNotEquals(b1, b3);

        // Different class
        assertNotEquals(b1, b4);

        // Null check
        assertNotEquals(b1, null);
    }

    /** Test of toString method, of class ByteArrayAppendValueModification. */
    @Test
    public void testToString() {
        String expected = "ByteArrayAppendValueModification{bytesToAppend=01 02 03}";
        assertEquals(expected, b1.toString());
    }
}
