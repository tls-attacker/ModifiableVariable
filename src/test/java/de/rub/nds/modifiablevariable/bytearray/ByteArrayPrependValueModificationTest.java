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

public class ByteArrayPrependValueModificationTest {

    private ByteArrayPrependValueModification b1;
    private ByteArrayPrependValueModification b2;
    private ByteArrayPrependValueModification b3;
    private Object b4;

    @BeforeEach
    public void setUp() {
        b1 = new ByteArrayPrependValueModification(new byte[] {0x01, 0x02, 0x03});
        b2 = new ByteArrayPrependValueModification(new byte[] {0x01, 0x02, 0x03});
        b3 = new ByteArrayPrependValueModification(new byte[] {0x04, 0x05, 0x06});
        b4 = new Object();
    }

    /** Test of modifyImplementationHook method, of class ByteArrayPrependValueModification. */
    @Test
    public void testModifyImplementationHook() {
        // Test with a normal byte array
        byte[] input = new byte[] {0x10, 0x11, 0x12};
        byte[] expected = new byte[] {0x01, 0x02, 0x03, 0x10, 0x11, 0x12}; // bytesToPrepend + Input
        assertArrayEquals(expected, b1.modifyImplementationHook(input));

        // Test with empty array
        byte[] emptyInput = new byte[0];
        byte[] expectedEmpty = new byte[] {0x01, 0x02, 0x03}; // Just the bytesToPrepend
        assertArrayEquals(expectedEmpty, b1.modifyImplementationHook(emptyInput));

        // Test with null input
        assertNull(b1.modifyImplementationHook(null));
    }

    /** Test of createCopy method, of class ByteArrayPrependValueModification. */
    @Test
    public void testCreateCopy() {
        ByteArrayPrependValueModification copy = b1.createCopy();
        assertNotSame(b1, copy);
        assertEquals(b1, copy);
        assertArrayEquals(b1.getBytesToPrepend(), copy.getBytesToPrepend());
    }

    /** Test copy constructor */
    @Test
    public void testCopyConstructor() {
        ByteArrayPrependValueModification copy = new ByteArrayPrependValueModification(b1);
        assertNotSame(b1, copy);
        assertEquals(b1, copy);
        assertArrayEquals(b1.getBytesToPrepend(), copy.getBytesToPrepend());
    }

    /** Test of getBytesToPrepend method, of class ByteArrayPrependValueModification. */
    @Test
    public void testGetBytesToPrepend() {
        assertArrayEquals(new byte[] {0x01, 0x02, 0x03}, b1.getBytesToPrepend());
    }

    /** Test of setBytesToPrepend method, of class ByteArrayPrependValueModification. */
    @Test
    public void testSetBytesToPrepend() {
        ByteArrayPrependValueModification mod =
                new ByteArrayPrependValueModification(new byte[] {0x01, 0x02});
        mod.setBytesToPrepend(new byte[] {0x0A, 0x0B, 0x0C});
        assertArrayEquals(new byte[] {0x0A, 0x0B, 0x0C}, mod.getBytesToPrepend());
    }

    /** Test setBytesToPrepend with null value */
    @Test
    public void testSetBytesToPrependNull() {
        ByteArrayPrependValueModification mod =
                new ByteArrayPrependValueModification(new byte[] {0x01, 0x02});
        assertThrows(NullPointerException.class, () -> mod.setBytesToPrepend(null));
    }

    /** Test constructor with null value */
    @Test
    public void testConstructorNull() {
        assertThrows(
                NullPointerException.class,
                () -> new ByteArrayPrependValueModification((byte[]) null));
    }

    /** Test of hashCode method, of class ByteArrayPrependValueModification. */
    @Test
    public void testHashCode() {
        assertEquals(b1.hashCode(), b2.hashCode()); // Same values
        assertNotEquals(b1.hashCode(), b3.hashCode()); // Different values
    }

    /** Test of equals method, of class ByteArrayPrependValueModification. */
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

    /** Test of toString method, of class ByteArrayPrependValueModification. */
    @Test
    public void testToString() {
        String expected = "ByteArrayPrependValueModification{bytesToPrepend=01 02 03}";
        assertEquals(expected, b1.toString());
    }
}
