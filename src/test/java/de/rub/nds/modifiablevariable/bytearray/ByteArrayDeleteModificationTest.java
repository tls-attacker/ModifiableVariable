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

public class ByteArrayDeleteModificationTest {

    private ByteArrayDeleteModification b1;
    private ByteArrayDeleteModification b2;
    private ByteArrayDeleteModification b3;
    private ByteArrayDeleteModification b4;
    private Object differentType;

    @BeforeEach
    public void setUp() {
        b1 = new ByteArrayDeleteModification(0, 0);
        b2 = new ByteArrayDeleteModification(0, 0);
        b3 = new ByteArrayDeleteModification(0, 1);
        b4 = new ByteArrayDeleteModification(1, 0);
        differentType = new Object();
    }

    /** Test of modifyImplementationHook method with normal byte array */
    @Test
    public void testModifyImplementationHookNormal() {
        byte[] input = new byte[] {0, 1, 2, 3, 4};
        byte[] expected = new byte[] {1, 2, 3, 4};
        ByteArrayDeleteModification mod = new ByteArrayDeleteModification(0, 1);
        assertArrayEquals(expected, mod.modifyImplementationHook(input));
    }

    /** Test of modifyImplementationHook method with deletion in the middle */
    @Test
    public void testModifyImplementationHookMiddle() {
        byte[] input = new byte[] {0, 1, 2, 3, 4};
        byte[] expected = new byte[] {0, 1, 4};
        ByteArrayDeleteModification mod = new ByteArrayDeleteModification(2, 2);
        assertArrayEquals(expected, mod.modifyImplementationHook(input));
    }

    /** Test of modifyImplementationHook method with deletion at the end */
    @Test
    public void testModifyImplementationHookEnd() {
        byte[] input = new byte[] {0, 1, 2, 3, 4};
        byte[] expected = new byte[] {0, 1, 2};
        ByteArrayDeleteModification mod = new ByteArrayDeleteModification(3, 2);
        assertArrayEquals(expected, mod.modifyImplementationHook(input));
    }

    /** Test of modifyImplementationHook method with zero count (no deletion) */
    @Test
    public void testModifyImplementationHookZeroCount() {
        byte[] input = new byte[] {0, 1, 2, 3, 4};
        ByteArrayDeleteModification mod = new ByteArrayDeleteModification(2, 0);
        assertArrayEquals(input, mod.modifyImplementationHook(input));
    }

    /** Test of modifyImplementationHook method with negative count (should be treated as 0) */
    @Test
    public void testModifyImplementationHookNegativeCount() {
        byte[] input = new byte[] {0, 1, 2, 3, 4};
        ByteArrayDeleteModification mod = new ByteArrayDeleteModification(2, -1);
        // Negative count should be treated as 0 due to Math.max(0, count)
        assertArrayEquals(input, mod.modifyImplementationHook(input));
    }

    /** Test of modifyImplementationHook method with negative start position */
    @Test
    public void testModifyImplementationHookNegativePosition() {
        byte[] input = new byte[] {0, 1, 2, 3, 4};
        // -1 should wrap to position 3 (length-1 + position)
        ByteArrayDeleteModification mod = new ByteArrayDeleteModification(-1, 1);
        byte[] expected = new byte[] {0, 1, 2, 4};
        assertArrayEquals(expected, mod.modifyImplementationHook(input));
    }

    /** Test of modifyImplementationHook method with start position beyond array length */
    @Test
    public void testModifyImplementationHookPositionBeyondLength() {
        byte[] input = new byte[] {0, 1, 2, 3, 4};
        // Position 10 should wrap to position 0 (10 % 5)
        ByteArrayDeleteModification mod = new ByteArrayDeleteModification(10, 2);
        byte[] expected = new byte[] {2, 3, 4};
        assertArrayEquals(expected, mod.modifyImplementationHook(input));
    }

    /** Test of modifyImplementationHook method with count extending beyond array length */
    @Test
    public void testModifyImplementationHookCountBeyondLength() {
        byte[] input = new byte[] {0, 1, 2, 3, 4};
        // Delete from position 3 with count 10 should delete to the end
        ByteArrayDeleteModification mod = new ByteArrayDeleteModification(3, 10);
        byte[] expected = new byte[] {0, 1, 2};
        assertArrayEquals(expected, mod.modifyImplementationHook(input));
    }

    /** Test of modifyImplementationHook method with null input */
    @Test
    public void testModifyImplementationHookNullInput() {
        ByteArrayDeleteModification mod = new ByteArrayDeleteModification(0, 1);
        assertNull(mod.modifyImplementationHook(null));
    }

    /** Test of modifyImplementationHook method with empty array */
    @Test
    public void testModifyImplementationHookEmptyArray() {
        byte[] emptyArray = new byte[0];
        ByteArrayDeleteModification mod = new ByteArrayDeleteModification(0, 1);
        assertArrayEquals(emptyArray, mod.modifyImplementationHook(emptyArray));
    }

    /** Test of createCopy method */
    @Test
    public void testCreateCopy() {
        ByteArrayDeleteModification original = new ByteArrayDeleteModification(2, 3);
        ByteArrayDeleteModification copy = original.createCopy();

        // Should be equal but not the same instance
        assertEquals(original, copy);
        assertNotSame(original, copy);

        // Verify properties were copied
        assertEquals(original.getStartPosition(), copy.getStartPosition());
        assertEquals(original.getCount(), copy.getCount());

        // Modifying copy should not affect original
        copy.setCount(5);
        assertNotEquals(original.getCount(), copy.getCount());
    }

    /** Test copy constructor */
    @Test
    public void testCopyConstructor() {
        ByteArrayDeleteModification original = new ByteArrayDeleteModification(2, 3);
        ByteArrayDeleteModification copy = new ByteArrayDeleteModification(original);

        // Should be equal but not the same instance
        assertEquals(original, copy);
        assertNotSame(original, copy);

        // Verify properties were copied
        assertEquals(original.getStartPosition(), copy.getStartPosition());
        assertEquals(original.getCount(), copy.getCount());
    }

    /** Test of getStartPosition method */
    @Test
    public void testGetStartPosition() {
        assertEquals(0, b1.getStartPosition());
        assertEquals(0, b3.getStartPosition());
        assertEquals(1, b4.getStartPosition());
    }

    /** Test of setStartPosition method */
    @Test
    public void testSetStartPosition() {
        b1.setStartPosition(5);
        assertEquals(5, b1.getStartPosition());

        // Set to negative value
        b1.setStartPosition(-3);
        assertEquals(-3, b1.getStartPosition());
    }

    /** Test of getCount method */
    @Test
    public void testGetCount() {
        assertEquals(0, b1.getCount());
        assertEquals(1, b3.getCount());
        assertEquals(0, b4.getCount());
    }

    /** Test of setCount method */
    @Test
    public void testSetCount() {
        b1.setCount(10);
        assertEquals(10, b1.getCount());

        // Set to negative value
        b1.setCount(-5);
        assertEquals(-5, b1.getCount());
    }

    /** Test of hashCode method with identical objects */
    @Test
    public void testHashCodeIdentical() {
        assertEquals(b1.hashCode(), b2.hashCode());
    }

    /** Test of hashCode method with different count */
    @Test
    public void testHashCodeDifferentCount() {
        assertNotEquals(b1.hashCode(), b3.hashCode());
    }

    /** Test of hashCode method with different position */
    @Test
    public void testHashCodeDifferentPosition() {
        assertNotEquals(b1.hashCode(), b4.hashCode());
    }

    /** Test of hashCode method consistency */
    @Test
    public void testHashCodeConsistency() {
        int hash1 = b1.hashCode();
        int hash2 = b1.hashCode();
        assertEquals(hash1, hash2);
    }

    /** Test of equals method with same object reference */
    @Test
    public void testEqualsSameReference() {
        assertTrue(b1.equals(b1));
    }

    /** Test of equals method with equal objects */
    @Test
    public void testEqualsEqualObjects() {
        assertTrue(b1.equals(b2));
        assertTrue(b2.equals(b1)); // symmetry
    }

    /** Test of equals method with different count */
    @Test
    public void testEqualsDifferentCount() {
        assertFalse(b1.equals(b3));
    }

    /** Test of equals method with different position */
    @Test
    public void testEqualsDifferentPosition() {
        assertFalse(b1.equals(b4));
    }

    /** Test of equals method with null */
    @Test
    public void testEqualsNull() {
        assertFalse(b1.equals(null));
    }

    /** Test of equals method with different type */
    @Test
    public void testEqualsDifferentType() {
        assertFalse(b1.equals(differentType));
    }

    /** Test of equals method transitivity */
    @Test
    public void testEqualsTransitivity() {
        ByteArrayDeleteModification a = new ByteArrayDeleteModification(5, 10);
        ByteArrayDeleteModification b = new ByteArrayDeleteModification(5, 10);
        ByteArrayDeleteModification c = new ByteArrayDeleteModification(5, 10);

        assertTrue(a.equals(b));
        assertTrue(b.equals(c));
        assertTrue(a.equals(c)); // transitivity
    }

    /** Test of toString method */
    @Test
    public void testToString() {
        String expected = "ByteArrayDeleteModification{count=0, startPosition=0}";
        assertEquals(expected, b1.toString());

        b1.setCount(5);
        b1.setStartPosition(3);
        String expectedModified = "ByteArrayDeleteModification{count=5, startPosition=3}";
        assertEquals(expectedModified, b1.toString());
    }
}
