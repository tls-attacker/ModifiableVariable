/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.util;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class ComparableByteArrayTest {

    @Test
    void testConstructorAndGetters() {
        byte[] array = new byte[] {1, 2, 3, 4, 5};
        ComparableByteArray comparableArray = new ComparableByteArray(array);

        assertArrayEquals(array, comparableArray.getArray());
    }

    @Test
    void testSetArray() {
        byte[] initialArray = new byte[] {1, 2, 3};
        ComparableByteArray comparableArray = new ComparableByteArray(initialArray);

        byte[] newArray = new byte[] {4, 5, 6, 7};
        comparableArray.setArray(newArray);

        assertArrayEquals(newArray, comparableArray.getArray());
    }

    @Test
    void testEqualsWithSameContent() {
        byte[] array1 = new byte[] {1, 2, 3, 4, 5};
        byte[] array2 = new byte[] {1, 2, 3, 4, 5}; // Same content but different object

        ComparableByteArray comparable1 = new ComparableByteArray(array1);
        ComparableByteArray comparable2 = new ComparableByteArray(array2);

        // Test equality
        assertTrue(comparable1.equals(comparable2));
        assertTrue(comparable2.equals(comparable1));

        // Test hashCode
        assertEquals(comparable1.hashCode(), comparable2.hashCode());
    }

    @Test
    void testEqualsWithDifferentContent() {
        byte[] array1 = new byte[] {1, 2, 3, 4, 5};
        byte[] array2 = new byte[] {1, 2, 3, 4, 6}; // Different at last position

        ComparableByteArray comparable1 = new ComparableByteArray(array1);
        ComparableByteArray comparable2 = new ComparableByteArray(array2);

        // Test inequality
        assertFalse(comparable1.equals(comparable2));
        assertFalse(comparable2.equals(comparable1));

        // Test hashCode should likely be different (not guaranteed but highly probable)
        assertNotEquals(comparable1.hashCode(), comparable2.hashCode());
    }

    @Test
    void testEqualsWithDifferentLength() {
        byte[] array1 = new byte[] {1, 2, 3};
        byte[] array2 = new byte[] {1, 2, 3, 4}; // Extra element

        ComparableByteArray comparable1 = new ComparableByteArray(array1);
        ComparableByteArray comparable2 = new ComparableByteArray(array2);

        // Test inequality
        assertFalse(comparable1.equals(comparable2));
        assertFalse(comparable2.equals(comparable1));
    }

    @Test
    void testEqualsWithSameInstance() {
        byte[] array = new byte[] {1, 2, 3, 4, 5};
        ComparableByteArray comparable = new ComparableByteArray(array);

        // Test equality with same instance
        assertTrue(comparable.equals(comparable));
    }

    @Test
    static void testEqualsWithNull() {
        byte[] array = new byte[] {1, 2, 3, 4, 5};
        ComparableByteArray comparable = new ComparableByteArray(array);

        // Test equality with null
        assertFalse(comparable.equals(null));
    }

    @Test
    void testEqualsWithDifferentClass() {
        byte[] array = new byte[] {1, 2, 3, 4, 5};
        ComparableByteArray comparable = new ComparableByteArray(array);

        // Test equality with object of different class
        assertFalse(comparable.equals("Not a ComparableByteArray"));
    }
}
