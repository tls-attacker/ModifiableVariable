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

class ByteArrayInsertValueModificationTest {

    private ByteArrayInsertValueModification b1;
    private ByteArrayInsertValueModification b2;
    private ByteArrayInsertValueModification b3;
    private ByteArrayInsertValueModification b4;
    private Object b5;

    @BeforeEach
    void setUp() {
        b1 = new ByteArrayInsertValueModification(new byte[] {0x01, 0x02, 0x03}, 1);
        b2 = new ByteArrayInsertValueModification(new byte[] {0x01, 0x02, 0x03}, 1);
        b3 = new ByteArrayInsertValueModification(new byte[] {0x01, 0x02, 0x03}, 2);
        b4 = new ByteArrayInsertValueModification(new byte[] {0x04, 0x05, 0x06}, 1);
        b5 = new Object();
    }

    /** Test of modifyImplementationHook method, of class ByteArrayInsertValueModification. */
    @Test
    void testModifyImplementationHook() {
        // Test insert in the middle of array
        byte[] input = new byte[] {0x10, 0x11, 0x12, 0x13};
        byte[] expected = new byte[] {0x10, 0x01, 0x02, 0x03, 0x11, 0x12, 0x13};
        assertArrayEquals(expected, b1.modifyImplementationHook(input));

        // Test insert at different position
        byte[] expected2 = new byte[] {0x10, 0x11, 0x01, 0x02, 0x03, 0x12, 0x13};
        assertArrayEquals(expected2, b3.modifyImplementationHook(input));

        // Test insert at beginning (position 0)
        ByteArrayInsertValueModification insertAtBeginning =
                new ByteArrayInsertValueModification(new byte[] {0x01, 0x02}, 0);
        byte[] expectedAtBeginning = new byte[] {0x01, 0x02, 0x10, 0x11, 0x12, 0x13};
        assertArrayEquals(expectedAtBeginning, insertAtBeginning.modifyImplementationHook(input));

        // Test insert at end (position = length)
        ByteArrayInsertValueModification insertAtEnd =
                new ByteArrayInsertValueModification(new byte[] {0x01, 0x02}, 4);
        byte[] expectedAtEnd = new byte[] {0x10, 0x11, 0x12, 0x13, 0x01, 0x02};
        assertArrayEquals(expectedAtEnd, insertAtEnd.modifyImplementationHook(input));

        // Test insert with position beyond length (should wrap around)
        ByteArrayInsertValueModification insertBeyond =
                new ByteArrayInsertValueModification(new byte[] {0x01, 0x02}, 10);
        // Position 10 % (4+1) = 0, so it should insert at the beginning
        byte[] expectedBeyond = new byte[] {0x01, 0x02, 0x10, 0x11, 0x12, 0x13};
        assertArrayEquals(expectedBeyond, insertBeyond.modifyImplementationHook(input));

        // Test insert with negative position (should insert from end)
        ByteArrayInsertValueModification insertNegative =
                new ByteArrayInsertValueModification(new byte[] {(byte) 0xAA, (byte) 0xBB}, -1);
        byte[] expectedNegative = new byte[] {0x10, 0x11, 0x12, (byte) 0xAA, (byte) 0xBB, 0x13};
        assertArrayEquals(expectedNegative, insertNegative.modifyImplementationHook(input));

        // Test with empty array
        byte[] emptyInput = new byte[0];
        byte[] expectedEmpty = new byte[] {0x01, 0x02, 0x03};
        assertArrayEquals(expectedEmpty, b1.modifyImplementationHook(emptyInput));

        // Test with null input
        assertNull(b1.modifyImplementationHook(null));
    }

    /** Test of createCopy method, of class ByteArrayInsertValueModification. */
    @Test
    void testCreateCopy() {
        ByteArrayInsertValueModification copy = b1.createCopy();
        assertNotSame(b1, copy);
        assertEquals(b1, copy);
        assertArrayEquals(b1.getBytesToInsert(), copy.getBytesToInsert());
        assertEquals(b1.getStartPosition(), copy.getStartPosition());
    }

    /** Test copy constructor */
    @Test
    void testCopyConstructor() {
        ByteArrayInsertValueModification copy = new ByteArrayInsertValueModification(b1);
        assertNotSame(b1, copy);
        assertEquals(b1, copy);
        assertArrayEquals(b1.getBytesToInsert(), copy.getBytesToInsert());
        assertEquals(b1.getStartPosition(), copy.getStartPosition());
    }

    /** Test of getBytesToInsert method, of class ByteArrayInsertValueModification. */
    @Test
    void testGetBytesToInsert() {
        assertArrayEquals(new byte[] {0x01, 0x02, 0x03}, b1.getBytesToInsert());
    }

    /** Test of setBytesToInsert method, of class ByteArrayInsertValueModification. */
    @Test
    void testSetBytesToInsert() {
        ByteArrayInsertValueModification mod =
                new ByteArrayInsertValueModification(new byte[] {0x01, 0x02}, 1);
        mod.setBytesToInsert(new byte[] {0x0A, 0x0B, 0x0C});
        assertArrayEquals(new byte[] {0x0A, 0x0B, 0x0C}, mod.getBytesToInsert());
    }

    /** Test of getStartPosition method, of class ByteArrayInsertValueModification. */
    @Test
    void testGetStartPosition() {
        assertEquals(1, b1.getStartPosition());
        assertEquals(2, b3.getStartPosition());
    }

    /** Test of setStartPosition method, of class ByteArrayInsertValueModification. */
    @Test
    void testSetStartPosition() {
        ByteArrayInsertValueModification mod =
                new ByteArrayInsertValueModification(new byte[] {0x01, 0x02}, 1);
        mod.setStartPosition(5);
        assertEquals(5, mod.getStartPosition());
    }

    /** Test setBytesToInsert with null value */
    @Test
    void testSetBytesToInsertNull() {
        ByteArrayInsertValueModification mod =
                new ByteArrayInsertValueModification(new byte[] {0x01, 0x02}, 1);
        assertThrows(NullPointerException.class, () -> mod.setBytesToInsert(null));
    }

    /** Test constructor with null value */
    @Test
    void testConstructorNull() {
        assertThrows(
                NullPointerException.class,
                () -> new ByteArrayInsertValueModification((byte[]) null, 1));
    }

    /** Test of hashCode method, of class ByteArrayInsertValueModification. */
    @Test
    void testHashCode() {
        assertEquals(b1.hashCode(), b2.hashCode()); // Same values and position
        assertNotEquals(b1.hashCode(), b3.hashCode()); // Same values but different position
        assertNotEquals(b1.hashCode(), b4.hashCode()); // Different values but same position
    }

    /** Test of equals method, of class ByteArrayInsertValueModification. */
    @Test
    void testEquals() {
        // Same object reference
        assertEquals(b1, b1);

        // Same values and position
        assertEquals(b1, b2);

        // Same values but different position
        assertNotEquals(b1, b3);

        // Different values but same position
        assertNotEquals(b1, b4);

        // Different class
        assertNotEquals(b1, b5);

        // Null check
        assertNotEquals(b1, null);
    }

    /** Test of toString method, of class ByteArrayInsertValueModification. */
    @Test
    void testToString() {
        String expected = "ByteArrayInsertModification{bytesToInsert=01 02 03, startPosition=1}";
        assertEquals(expected, b1.toString());
    }
}
