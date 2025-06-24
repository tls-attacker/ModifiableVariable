/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.mlong;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import de.rub.nds.modifiablevariable.longint.LongAddModification;
import de.rub.nds.modifiablevariable.longint.ModifiableLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ModifiableLongTest {

    private ModifiableLong long1;
    private ModifiableLong long2;

    @BeforeEach
    void setUp() {
        long1 = new ModifiableLong();
        long1.setOriginalValue(2L);
        long2 = new ModifiableLong();
        long2.setOriginalValue(2L);
    }

    /** Test of getAssertEquals method, of class ModifiableLong. */
    @Test
    void testGetAssertEquals() {
        assertNull(long1.getAssertEquals());
        long1.setAssertEquals(42L);
        assertEquals(Long.valueOf(42L), long1.getAssertEquals());
    }

    /** Test of setAssertEquals method, of class ModifiableLong. */
    @Test
    void testSetAssertEquals() {
        long1.setAssertEquals(42L);
        assertEquals(Long.valueOf(42L), long1.getAssertEquals());

        long1.setAssertEquals(null);
        assertNull(long1.getAssertEquals());
    }

    /** Test of isOriginalValueModified method, of class ModifiableLong. */
    @Test
    void testIsOriginalValueModified() {
        // Initial state - not modified
        assertFalse(long1.isOriginalValueModified());

        // After modification
        long1.setModifications(new LongAddModification(1L));
        assertTrue(long1.isOriginalValueModified());

        // Null original value
        ModifiableLong nullLong = new ModifiableLong();
        assertThrows(IllegalStateException.class, nullLong::isOriginalValueModified);
    }

    /** Test of getByteArray method, of class ModifiableLong. */
    @Test
    void testGetByteArray() {
        // Test 8-byte representation
        byte[] expected8 = new byte[] {0, 0, 0, 0, 0, 0, 0, 2};
        assertArrayEquals(expected8, long1.getByteArray(8));

        // Test 4-byte representation (truncation)
        byte[] expected4 = new byte[] {0, 0, 0, 2};
        assertArrayEquals(expected4, long1.getByteArray(4));

        // Test with modified value
        long1.setModifications(new LongAddModification(1L));
        byte[] expectedModified = new byte[] {0, 0, 0, 0, 0, 0, 0, 3};
        assertArrayEquals(expectedModified, long1.getByteArray(8));
    }

    /** Test of validateAssertions method, of class ModifiableLong. */
    @Test
    void testValidateAssertions() {
        // No assertions set
        assertTrue(long1.validateAssertions());

        // Matching assertion
        long1.setAssertEquals(2L);
        assertTrue(long1.validateAssertions());

        // Non-matching assertion
        long1.setAssertEquals(3L);
        assertFalse(long1.validateAssertions());

        // Modified value matching assertion
        long1.setAssertEquals(3L);
        long1.setModifications(new LongAddModification(1L));
        assertTrue(long1.validateAssertions());
    }

    /** Test of getOriginalValue method, of class ModifiableLong. */
    @Test
    void testGetOriginalValue() {
        assertEquals(2L, long1.getOriginalValue());

        ModifiableLong nullLong = new ModifiableLong();
        assertNull(nullLong.getOriginalValue());
    }

    /** Test of setOriginalValue method, of class ModifiableLong. */
    @Test
    void testSetOriginalValue() {
        long1.setOriginalValue(42L);
        assertEquals(42L, long1.getOriginalValue());

        long1.setOriginalValue(null);
        assertNull(long1.getOriginalValue());
    }

    /** Test of toString method, of class ModifiableLong. */
    @Test
    void testToString() {
        String expected = "ModifiableLong{originalValue=2}";
        assertEquals(expected, long1.toString());

        // With modification
        long1.setModifications(new LongAddModification(1L));
        String expectedWithMod =
                "ModifiableLong{originalValue=2, modifications=[LongAddModification{summand=1}]}";
        assertEquals(expectedWithMod, long1.toString());

        // With assertion
        long1.setAssertEquals(3L);
        String expectedWithAssert =
                "ModifiableLong{originalValue=2, modifications=[LongAddModification{summand=1}], assertEquals=3}";
        assertEquals(expectedWithAssert, long1.toString());
    }

    /** Test of equals method, of class ModifiableLong. */
    @Test
    void testEquals() {
        assertEquals(long1, long2);

        // Different original value but same computed value
        long2.setOriginalValue(1L);
        long2.setModifications(new LongAddModification(1L));
        assertEquals(long1, long2);

        // Different computed value
        long2.setOriginalValue(3L);
        assertNotEquals(long1, long2);

        // Test with null
        assertNotEquals(long1, null);

        // Test with different object type
        assertNotEquals(long1, "not a ModifiableLong");

        // Test reflexivity
        assertEquals(long1, long1);

        // Test with null values - both instances have getValue() == null
        ModifiableLong nullLong1 = new ModifiableLong();
        ModifiableLong nullLong2 = new ModifiableLong();
        assertEquals(nullLong1, nullLong2);

        // Test with one null and one non-null getValue()
        ModifiableLong nonNullLong = new ModifiableLong();
        nonNullLong.setOriginalValue(5L);

        // Test both directions to ensure symmetry in equals()
        assertNotEquals(
                nullLong1,
                nonNullLong,
                "Object with null value should not equal object with non-null value");
        assertNotEquals(
                nonNullLong,
                nullLong1,
                "Object with non-null value should not equal object with null value");

        // Explicitly test both branches where one object has getValue() == null and the other
        // doesn't
        assertFalse(
                nullLong1.equals(nonNullLong),
                "Object with null value should not equal object with non-null value");
        assertFalse(
                nonNullLong.equals(nullLong1),
                "Object with non-null value should not equal object with null value");
    }

    /** Test of hashCode method, of class ModifiableLong. */
    @Test
    void testHashCode() {
        assertEquals(long1.hashCode(), long2.hashCode());

        // Same computed value, different original
        long2.setOriginalValue(1L);
        long2.setModifications(new LongAddModification(1L));
        assertEquals(long1.hashCode(), long2.hashCode());

        // Different computed value
        long2.setOriginalValue(3L);
        assertNotEquals(long1.hashCode(), long2.hashCode());

        // Test with null value (getValue() returns null)
        ModifiableLong nullLong1 = new ModifiableLong();
        ModifiableLong nullLong2 = new ModifiableLong();

        // Two objects with null values should have the same hash code
        assertEquals(nullLong1.hashCode(), nullLong2.hashCode());

        // Check that the implementation correctly handles null values
        int expectedHashCode = 17 * 31; // Initial value in hashCode() multiplied by 31
        assertEquals(expectedHashCode, nullLong1.hashCode());
    }

    @Test
    void testCreateCopy() {
        ModifiableLong copy = long1.createCopy();
        assertEquals(long1, copy);
        assertEquals(long1.getOriginalValue(), copy.getOriginalValue());

        // Modify the original and verify copy is unchanged
        long1.setModifications(new LongAddModification(1L));
        assertNotEquals(long1.getValue(), copy.getValue());
        assertNotSame(long1, copy);
    }

    @Test
    void testConstructor() {
        ModifiableLong defaultConstructor = new ModifiableLong();
        assertNull(defaultConstructor.getOriginalValue());

        ModifiableLong valueConstructor = new ModifiableLong(5L);
        assertEquals(5L, valueConstructor.getOriginalValue());

        ModifiableLong modLong = new ModifiableLong(5L);
        modLong.setModifications(new LongAddModification(10L));
        modLong.setAssertEquals(15L);

        ModifiableLong copyConstructor = new ModifiableLong(modLong);
        assertEquals(modLong.getOriginalValue(), copyConstructor.getOriginalValue());
        assertEquals(modLong.getValue(), copyConstructor.getValue());
        assertEquals(modLong.getAssertEquals(), copyConstructor.getAssertEquals());
    }
}
