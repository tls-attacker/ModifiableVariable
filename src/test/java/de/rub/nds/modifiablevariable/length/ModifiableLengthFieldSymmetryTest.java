/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.length;

import static org.junit.jupiter.api.Assertions.*;

import de.rub.nds.modifiablevariable.bytearray.ModifiableByteArray;
import de.rub.nds.modifiablevariable.integer.ModifiableInteger;
import org.junit.jupiter.api.Test;

class ModifiableLengthFieldSymmetryTest {

    /**
     * Test that equals() is symmetric between ModifiableLengthField and ModifiableInteger. If
     * a.equals(b) is true, then b.equals(a) must also be true.
     */
    @Test
    void testEqualsSymmetryWithModifiableInteger() {
        // Create a ModifiableByteArray with 4 bytes
        ModifiableByteArray array = new ModifiableByteArray();
        array.setOriginalValue(new byte[] {1, 2, 3, 4});

        // Create a ModifiableLengthField that references this array
        ModifiableLengthField lengthField = new ModifiableLengthField(array);

        // Create a ModifiableInteger with the same value (4)
        ModifiableInteger integerField = new ModifiableInteger(4);

        // Test symmetry: both directions should be equal
        assertTrue(
                lengthField.equals(integerField),
                "ModifiableLengthField should equal ModifiableInteger with same value");
        assertTrue(
                integerField.equals(lengthField),
                "ModifiableInteger should equal ModifiableLengthField with same value");

        // Test with different values
        ModifiableInteger differentInteger = new ModifiableInteger(5);
        assertFalse(
                lengthField.equals(differentInteger),
                "ModifiableLengthField should not equal ModifiableInteger with different value");
        assertFalse(
                differentInteger.equals(lengthField),
                "ModifiableInteger should not equal ModifiableLengthField with different value");
    }

    /** Test that equals() is symmetric with null values */
    @Test
    void testEqualsSymmetryWithNullValues() {
        // Create a ModifiableByteArray without setting original value
        ModifiableByteArray array = new ModifiableByteArray();
        ModifiableLengthField lengthFieldNull = new ModifiableLengthField(array);

        // Create a ModifiableInteger with null value
        ModifiableInteger integerNull = new ModifiableInteger((Integer) null);

        // Both should have null values
        assertNull(lengthFieldNull.getValue());
        assertNull(integerNull.getValue());

        // Test symmetry with null values
        assertTrue(
                lengthFieldNull.equals(integerNull),
                "ModifiableLengthField with null should equal ModifiableInteger with null");
        assertTrue(
                integerNull.equals(lengthFieldNull),
                "ModifiableInteger with null should equal ModifiableLengthField with null");
    }

    /**
     * Test that ModifiableLengthField still checks reference equality when comparing with another
     * ModifiableLengthField
     */
    @Test
    void testReferenceEqualityBetweenModifiableLengthFields() {
        // Create two different arrays with same length
        ModifiableByteArray array1 = new ModifiableByteArray();
        array1.setOriginalValue(new byte[] {1, 2, 3, 4});
        ModifiableByteArray array2 = new ModifiableByteArray();
        array2.setOriginalValue(new byte[] {5, 6, 7, 8});

        ModifiableLengthField field1 = new ModifiableLengthField(array1);
        ModifiableLengthField field2 = new ModifiableLengthField(array2);

        // Fields should NOT be equal because they reference different arrays
        assertFalse(
                field1.equals(field2),
                "ModifiableLengthFields with different references should not be equal");

        // Create two fields with the same reference
        ModifiableLengthField field3 = new ModifiableLengthField(array1);
        ModifiableLengthField field4 = new ModifiableLengthField(array1);

        // Fields should be equal because they reference the same array and have same value
        assertTrue(
                field3.equals(field4),
                "ModifiableLengthFields with same reference and value should be equal");
    }

    /** Test transitivity: if a.equals(b) and b.equals(c), then a.equals(c) */
    @Test
    void testEqualsTransitivity() {
        // Create a ModifiableByteArray with 5 bytes
        ModifiableByteArray array = new ModifiableByteArray();
        array.setOriginalValue(new byte[] {1, 2, 3, 4, 5});

        ModifiableLengthField lengthField = new ModifiableLengthField(array);
        ModifiableInteger integer1 = new ModifiableInteger(5);
        ModifiableInteger integer2 = new ModifiableInteger(5);

        // Check all three are equal to each other
        assertTrue(lengthField.equals(integer1));
        assertTrue(integer1.equals(integer2));
        assertTrue(lengthField.equals(integer2));

        // Check symmetry
        assertTrue(integer1.equals(lengthField));
        assertTrue(integer2.equals(integer1));
        assertTrue(integer2.equals(lengthField));
    }

    /** Test that equals() correctly handles inheritance hierarchy */
    @Test
    void testEqualsWithSubclasses() {
        // Create a custom subclass of ModifiableInteger
        class CustomModifiableInteger extends ModifiableInteger {
            CustomModifiableInteger(Integer value) {
                super(value);
            }
        }

        ModifiableByteArray array = new ModifiableByteArray();
        array.setOriginalValue(new byte[] {1, 2, 3});

        ModifiableLengthField lengthField = new ModifiableLengthField(array);
        CustomModifiableInteger customInteger = new CustomModifiableInteger(3);

        // Should still maintain symmetry with subclasses
        assertTrue(
                lengthField.equals(customInteger),
                "ModifiableLengthField should equal ModifiableInteger subclass with same value");
        assertTrue(
                customInteger.equals(lengthField),
                "ModifiableInteger subclass should equal ModifiableLengthField with same value");
    }

    /** Test edge cases for equals() */
    @Test
    void testEqualsEdgeCases() {
        ModifiableByteArray array = new ModifiableByteArray();
        array.setOriginalValue(new byte[] {1, 2});
        ModifiableLengthField lengthField = new ModifiableLengthField(array);

        // Test with non-ModifiableInteger objects
        assertFalse(lengthField.equals("2"), "Should not equal String");
        assertFalse(lengthField.equals(2), "Should not equal raw Integer");
        assertFalse(lengthField.equals(new Object()), "Should not equal Object");
        assertFalse(lengthField.equals(null), "Should not equal null");

        // Test self-equality
        assertTrue(lengthField.equals(lengthField), "Should equal itself");
    }
}
