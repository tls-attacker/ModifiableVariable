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

class ByteArrayDuplicateModificationTest {

    private ByteArrayDuplicateModification b1;
    private ByteArrayDuplicateModification b2;
    private Object b3;

    @BeforeEach
    void setUp() {
        b1 = new ByteArrayDuplicateModification();
        b2 = new ByteArrayDuplicateModification();
        b3 = new Object();
    }

    /** Test of modifyImplementationHook method, of class ByteArrayDuplicateModification. */
    @Test
    void testModifyImplementationHook() {
        // Test with a normal byte array
        byte[] input = new byte[] {1, 2, 3, 4};
        byte[] expected = new byte[] {1, 2, 3, 4, 1, 2, 3, 4};
        assertArrayEquals(expected, b1.modifyImplementationHook(input));

        // Test with empty array
        assertArrayEquals(new byte[0], b1.modifyImplementationHook(new byte[0]));

        // Test with null input
        assertNull(b1.modifyImplementationHook(null));
    }

    /** Test of createCopy method, of class ByteArrayDuplicateModification. */
    @Test
    void testCreateCopy() {
        ByteArrayDuplicateModification copy = b1.createCopy();
        assertNotSame(b1, copy);
        assertEquals(b1, copy);
    }

    /** Test of hashCode method, of class ByteArrayDuplicateModification. */
    @Test
    void testHashCode() {
        assertEquals(b1.hashCode(), b2.hashCode());
    }

    /** Test of equals method, of class ByteArrayDuplicateModification. */
    @Test
    void testEquals() {
        // Same object reference
        assertEquals(b1, b1);

        // Same class, different object
        assertEquals(b1, b2);

        // Different class
        assertNotEquals(b1, b3);

        // Null check
        assertNotEquals(b1, null);
    }

    /** Test of toString method, of class ByteArrayDuplicateModification. */
    @Test
    void testToString() {
        assertEquals(b1.toString(), b2.toString());
        assertEquals("ByteArrayDuplicateModification{}", b1.toString());
    }
}
