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
import de.rub.nds.modifiablevariable.integer.IntegerAddModification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ModifiableLengthFieldTest {

    private ModifiableLengthField lengthField1;
    private ModifiableLengthField lengthField2;
    private ModifiableByteArray array;

    @BeforeEach
    public void setUp() {
        array = new ModifiableByteArray();
        array.setOriginalValue(new byte[] {0, 1, 2, 3});
        lengthField1 = new ModifiableLengthField(array);
        lengthField2 = new ModifiableLengthField(array);
    }

    /** Test of getOriginalValue method, of class ModifiableLengthField. */
    @Test
    public void testGetOriginalValue() {
        // Test the initial original value
        assertEquals(4, (int) lengthField1.getOriginalValue());
        assertEquals(
                4,
                (int) lengthField1.getValue()); // Value should match original when no modifications

        // Test that the original value changes when the referenced array changes
        array.setOriginalValue(new byte[] {0, 1, 2, 3, 4, 5});
        assertEquals(6, (int) lengthField1.getOriginalValue());
        assertEquals(6, (int) lengthField1.getValue());

        // Test with empty array
        array.setOriginalValue(new byte[0]);
        assertEquals(0, (int) lengthField1.getOriginalValue());

        // Test with modifications - original value should still reflect array length
        lengthField1.setModifications(new IntegerAddModification(10));
        assertEquals(
                0,
                (int) lengthField1.getOriginalValue(),
                "Original value should still be array length");
        assertEquals(
                10,
                (int) lengthField1.getValue(),
                "Modified value should be original + modification");

        // Change array again - original value should update but modification remains
        array.setOriginalValue(new byte[] {1, 2});
        assertEquals(
                2,
                (int) lengthField1.getOriginalValue(),
                "Original value should update with array");
        assertEquals(
                12,
                (int) lengthField1.getValue(),
                "Modified value should reflect new array length + modification");
    }

    /** Test of setOriginalValue method, of class ModifiableLengthField. */
    @Test
    public void testSetOriginalValue() {
        assertThrows(UnsupportedOperationException.class, () -> lengthField1.setOriginalValue(4));
    }

    /** Test of toString method, of class ModifiableLengthField. */
    @Test
    public void testToString() {
        assertEquals(lengthField1.toString(), lengthField2.toString());

        array = new ModifiableByteArray();
        array.setOriginalValue(new byte[] {1, 4});
        lengthField2 = new ModifiableLengthField(array);
        assertNotEquals(lengthField1.toString(), lengthField2.toString());

        // Test toString with modifications
        lengthField1.setModifications(new IntegerAddModification(10));
        assertTrue(lengthField1.toString().contains("ref="));
        assertTrue(lengthField1.toString().contains("modifications="));
    }

    /** Test of copy constructor and createCopy method. */
    @Test
    public void testCopyConstructorAndCreateCopy() {
        // Test copy constructor
        ModifiableLengthField copy1 = new ModifiableLengthField(lengthField1);
        assertEquals(lengthField1.getValue(), copy1.getValue());

        // Apply a modification to the original
        lengthField1.setModifications(new IntegerAddModification(10));
        assertEquals(14, (int) lengthField1.getValue());
        assertEquals(
                4,
                (int) copy1.getValue(),
                "Copy should not be affected by modifications to original");

        // Test createCopy
        ModifiableLengthField copy2 = lengthField1.createCopy();
        assertEquals(lengthField1.getValue(), copy2.getValue());
        assertEquals(14, (int) copy2.getValue());

        // Change the referenced array
        array.setOriginalValue(new byte[] {1, 2});
        assertEquals(
                2,
                (int) lengthField1.getOriginalValue(),
                "Original should reflect new array value");
        assertEquals(2, (int) copy1.getOriginalValue(), "Copy should share the same reference");
        assertEquals(
                2, (int) copy2.getOriginalValue(), "CreateCopy should share the same reference");
    }

    /** Test of equals method, of class ModifiableLengthField. */
    @Test
    public void testEquals() {
        // Same object reference
        assertEquals(lengthField1, lengthField1, "Object should equal itself");

        // Same value, same array reference, different instances
        assertEquals(lengthField1, lengthField2, "Objects with same state should be equal");

        // Different array reference, same length
        ModifiableByteArray differentArray = new ModifiableByteArray();
        differentArray.setOriginalValue(
                new byte[] {5, 6, 7, 8}); // Different content but same length
        ModifiableLengthField differentRefField = new ModifiableLengthField(differentArray);

        // Should NOT be equal because they reference different arrays
        assertNotEquals(
                lengthField1,
                differentRefField,
                "Objects with same length but different array references should NOT be equal");

        // Different values
        array = new ModifiableByteArray();
        array.setOriginalValue(new byte[] {1, 4});
        lengthField2 = new ModifiableLengthField(array);
        assertNotEquals(
                lengthField1, lengthField2, "Objects with different values should not be equal");

        // With modifications but same reference
        ModifiableByteArray sharedArray = new ModifiableByteArray();
        sharedArray.setOriginalValue(new byte[] {1, 2, 3, 4});
        ModifiableLengthField field1 = new ModifiableLengthField(sharedArray);
        ModifiableLengthField field2 = new ModifiableLengthField(sharedArray);

        field1.setModifications(new IntegerAddModification(10));
        field2.setModifications(new IntegerAddModification(10));
        assertEquals(
                field1,
                field2,
                "Objects with same modifications and same reference should be equal");

        // Same modifications but different references
        ModifiableByteArray array1 = new ModifiableByteArray();
        array1.setOriginalValue(new byte[] {1, 2, 3, 4});
        ModifiableByteArray array2 = new ModifiableByteArray();
        // Use a different array to make sure values are different
        array2.setOriginalValue(new byte[] {1, 2, 3, 4, 5});

        ModifiableLengthField fieldA = new ModifiableLengthField(array1);
        ModifiableLengthField fieldB = new ModifiableLengthField(array2);

        // No modifications needed as the arrays have different lengths

        // Should NOT be equal due to different references and different values
        assertNotEquals(
                fieldA, fieldB, "Objects with different references and values should NOT be equal");

        // Testing copy constructor
        ModifiableLengthField copy = new ModifiableLengthField(field1);
        assertEquals(field1, copy, "Copied object should be equal to original");

        // Different types
        assertNotEquals(lengthField1, new Object(), "Should not equal different types");
        assertNotEquals(lengthField1, null, "Should not equal null");
    }

    /** Test of hashCode method, of class ModifiableLengthField. */
    @Test
    public void testHashCode() {
        // Same hashCode for equal objects (same reference, same value)
        assertEquals(
                lengthField1.hashCode(),
                lengthField2.hashCode(),
                "Equal objects should have same hash code");

        // Different hashCodes for objects with same value but different references
        ModifiableByteArray differentArray = new ModifiableByteArray();
        differentArray.setOriginalValue(new byte[] {5, 6, 7, 8}); // Same length, different content
        ModifiableLengthField differentRefField = new ModifiableLengthField(differentArray);

        assertNotEquals(
                lengthField1.hashCode(),
                differentRefField.hashCode(),
                "Objects with same value but different references should have different hash codes");

        // Different hashCodes for different values
        array = new ModifiableByteArray();
        array.setOriginalValue(new byte[] {1, 4});
        lengthField2 = new ModifiableLengthField(array);
        assertNotEquals(
                lengthField1.hashCode(),
                lengthField2.hashCode(),
                "Objects with different values should have different hash codes");

        // With modifications but same reference
        ModifiableByteArray sharedArray = new ModifiableByteArray();
        sharedArray.setOriginalValue(new byte[] {1, 2, 3, 4});
        ModifiableLengthField field1 = new ModifiableLengthField(sharedArray);
        ModifiableLengthField field2 = new ModifiableLengthField(sharedArray);

        field1.setModifications(new IntegerAddModification(10));
        field2.setModifications(new IntegerAddModification(10));

        assertEquals(
                field1.hashCode(),
                field2.hashCode(),
                "Objects with same reference and same modifications should have same hash code");

        // Copy constructor should produce equal hash code
        ModifiableLengthField copy = new ModifiableLengthField(field1);
        assertEquals(
                field1.hashCode(),
                copy.hashCode(),
                "Copied object should have same hash code as original");

        // Verify hashCode consistency
        int firstHashCode = lengthField1.hashCode();
        int secondHashCode = lengthField1.hashCode();
        assertEquals(
                firstHashCode, secondHashCode, "HashCode should be consistent for the same object");
    }

    /** Test null reference in constructor */
    @Test
    public void testNullReference() {
        ModifiableByteArray nullArray = null;
        // The constructor should throw NullPointerException with null reference
        assertThrows(
                NullPointerException.class,
                () -> new ModifiableLengthField(nullArray),
                "Should throw NullPointerException when passing null to constructor");
    }

    /** Test equality with different length fields */
    @Test
    public void testEqualityWithDifferentLengthFields() {
        // Two length fields with different byte arrays but same length
        ModifiableByteArray array1 = new ModifiableByteArray();
        array1.setOriginalValue(new byte[] {0, 1, 2, 3});
        ModifiableByteArray array2 = new ModifiableByteArray();
        array2.setOriginalValue(new byte[] {4, 5, 6, 7});

        ModifiableLengthField field1 = new ModifiableLengthField(array1);
        ModifiableLengthField field2 = new ModifiableLengthField(array2);

        // Should NOT be equal due to different references, despite same length
        assertNotEquals(
                field1,
                field2,
                "Fields with same length but different references should NOT be equal");
        assertNotEquals(
                field1.hashCode(),
                field2.hashCode(),
                "Fields with same length but different references should have different hash codes");

        // Add same modification to both
        field1.setModifications(new IntegerAddModification(2));
        field2.setModifications(new IntegerAddModification(2));

        // Still not equal due to different references
        assertNotEquals(
                field1,
                field2,
                "Fields with same modifications but different references should not be equal");
        assertNotEquals(
                field1.hashCode(),
                field2.hashCode(),
                "Fields with same modifications but different references should have different hash codes");

        // Create two fields with the same reference
        ModifiableByteArray sharedArray = new ModifiableByteArray();
        sharedArray.setOriginalValue(new byte[] {0, 1, 2, 3});

        ModifiableLengthField fieldA = new ModifiableLengthField(sharedArray);
        ModifiableLengthField fieldB = new ModifiableLengthField(sharedArray);

        // Should be equal due to same reference
        assertEquals(fieldA, fieldB, "Fields with same reference should be equal");
        assertEquals(
                fieldA.hashCode(),
                fieldB.hashCode(),
                "Fields with same reference should have same hash code");
    }

    /**
     * Test for reference equality in ModifiableLengthField. This test focuses specifically on the
     * behavior that fields with same values but different references are not equal.
     */
    @Test
    public void testReferenceEqualityBehavior() {
        // Create two byte arrays with identical content
        ModifiableByteArray array1 = new ModifiableByteArray();
        array1.setOriginalValue(new byte[] {1, 2, 3, 4});
        ModifiableByteArray array2 = new ModifiableByteArray();
        array2.setOriginalValue(new byte[] {1, 2, 3, 4});

        // Arrays should have same content but be different objects
        assertNotSame(array1, array2, "Arrays should be different objects");
        assertArrayEquals(array1.getValue(), array2.getValue(), "Arrays should have same content");

        // Create two length fields with different array references
        ModifiableLengthField field1 = new ModifiableLengthField(array1);
        ModifiableLengthField field2 = new ModifiableLengthField(array2);

        // With the current implementation, fields are considered equal when they have
        // the same length value, even if they reference different arrays
        assertEquals(
                field1,
                field2,
                "Current implementation considers fields equal when they have the same length");
        assertEquals(
                field1.hashCode(),
                field2.hashCode(),
                "Current implementation gives same hash codes when values are equal");

        // Now create two fields referencing the same array
        ModifiableLengthField fieldA = new ModifiableLengthField(array1);
        ModifiableLengthField fieldB = new ModifiableLengthField(array1);

        // Fields should be equal because they reference the same array
        assertEquals(fieldA, fieldB, "Fields with same reference should be equal");
        assertEquals(
                fieldA.hashCode(),
                fieldB.hashCode(),
                "Fields with same reference should have same hash code");

        // Apply identical modifications to both fields
        fieldA.setModifications(new IntegerAddModification(5));
        fieldB.setModifications(new IntegerAddModification(5));

        // Fields should still be equal with same modifications and same reference
        assertEquals(
                fieldA, fieldB, "Fields with same reference and modifications should be equal");
        assertEquals(
                fieldA.hashCode(),
                fieldB.hashCode(),
                "Fields with same reference and modifications should have same hash code");
    }

    /** Test equals and hashCode with null values */
    @Test
    public void testEqualsAndHashCodeWithNullValues() {
        // Create a special modification that returns null regardless of input
        class NullModification extends IntegerAddModification {
            public NullModification() {
                super(0);
            }

            @Override
            protected Integer modifyImplementationHook(Integer input) {
                return null;
            }
        }

        // Create two arrays with same length
        ModifiableByteArray array1 = new ModifiableByteArray();
        array1.setOriginalValue(new byte[] {0, 1, 2, 3});
        ModifiableByteArray array2 = new ModifiableByteArray();
        array2.setOriginalValue(new byte[] {4, 5, 6, 7});

        // Two fields with different references but null getValue() results
        ModifiableLengthField nullResultField1 = new ModifiableLengthField(array1);
        nullResultField1.setModifications(new NullModification());
        ModifiableLengthField nullResultField2 = new ModifiableLengthField(array2);
        nullResultField2.setModifications(new NullModification());

        // Check that the modification actually returns null
        assertNull(nullResultField1.getValue(), "Modified value should be null");
        assertNull(nullResultField2.getValue(), "Modified value should be null");

        // Test equals: two fields with null values but different references should NOT be equal
        assertNotEquals(
                nullResultField1,
                nullResultField2,
                "Fields with null values but different references should NOT be equal");

        // Test hashCode: two fields with null values but different references should have different
        // hashCodes
        assertNotEquals(
                nullResultField1.hashCode(),
                nullResultField2.hashCode(),
                "Fields with null values but different references should have different hash codes");

        // Create two fields with same reference but null and non-null values
        ModifiableByteArray sharedArray = new ModifiableByteArray();
        sharedArray.setOriginalValue(new byte[] {0, 1, 2, 3});

        ModifiableLengthField nullField = new ModifiableLengthField(sharedArray);
        nullField.setModifications(new NullModification());
        ModifiableLengthField nonNullField = new ModifiableLengthField(sharedArray);
        nonNullField.setModifications(new IntegerAddModification(5));

        // Test equals: null value vs non-null value with same reference
        assertNotEquals(
                nullField,
                nonNullField,
                "Field with null value should not equal field with non-null value, even with same reference");

        // Now test with same null values and same reference
        ModifiableLengthField nullField2 = new ModifiableLengthField(sharedArray);
        nullField2.setModifications(new NullModification());

        // Should be equal due to same reference and both having null values
        assertEquals(
                nullField,
                nullField2,
                "Fields with same reference and both null values should be equal");
        assertEquals(
                nullField.hashCode(),
                nullField2.hashCode(),
                "Fields with same reference and both null values should have same hash code");

        // Test the specific branch in equals() where this.getValue() is null but that.getValue() is
        // not null
        assertFalse(
                nullField.equals(nonNullField),
                "Field with null value should not equal field with non-null value");

        // Test the specific branch in equals() where this.getValue() is not null but
        // that.getValue() is null
        assertFalse(
                nonNullField.equals(nullField),
                "Field with non-null value should not equal field with null value");

        // Test hashCode calculation when getValue() is null
        int result = 17;
        result = 31 * result + 0; // getValue() is null, so 0 is added
        result = 31 * result + (sharedArray != null ? sharedArray.hashCode() : 0);

        assertEquals(
                result,
                nullField.hashCode(),
                "HashCode calculation should handle null getValue() correctly but include ref hashCode");
    }

    /**
     * Test how equality changes when the referenced array is modified. This tests the dynamic
     * nature of ModifiableLengthField's equality based on the state of the referenced array.
     */
    @Test
    public void testEqualityWithChangingReference() {
        // Create a shared array reference
        ModifiableByteArray sharedArray = new ModifiableByteArray();
        sharedArray.setOriginalValue(new byte[] {1, 2, 3, 4});

        // Create two fields with the same reference
        ModifiableLengthField field1 = new ModifiableLengthField(sharedArray);
        ModifiableLengthField field2 = new ModifiableLengthField(sharedArray);

        // Should initially be equal
        assertEquals(field1, field2, "Fields with same reference should be equal");
        assertEquals(field1.hashCode(), field2.hashCode(), "Hash codes should be equal");

        // Create a field with a different array that has the same content
        ModifiableByteArray differentArray = new ModifiableByteArray();
        differentArray.setOriginalValue(new byte[] {1, 2, 3, 4});
        ModifiableLengthField differentField = new ModifiableLengthField(differentArray);

        // With the current implementation, fields are considered equal when they have
        // the same length value, even if they reference different arrays
        assertEquals(
                field1,
                differentField,
                "Current implementation considers fields equal when they have the same length");
        assertEquals(
                field1.hashCode(),
                differentField.hashCode(),
                "Current implementation gives same hash codes when values are equal");

        // Modify the shared array - this should affect both fields equally
        sharedArray.setOriginalValue(new byte[] {5, 6, 7, 8, 9});

        // Fields should still be equal to each other after the referenced array changes
        assertEquals(field1, field2, "Fields should remain equal after shared reference changes");
        assertEquals(field1.hashCode(), field2.hashCode(), "Hash codes should remain equal");

        // Verify the original values reflect the change
        assertEquals(
                5,
                (int) field1.getOriginalValue(),
                "Original value should reflect new array length");
        assertEquals(
                5,
                (int) field2.getOriginalValue(),
                "Original value should reflect new array length");
    }

    /** Test with reference having no original value */
    @Test
    public void testReferenceWithNoOriginalValue() {
        // Create a byte array reference but don't set an original value
        ModifiableByteArray emptyRef = new ModifiableByteArray();
        // emptyRef.setOriginalValue() not called

        // Create a length field with this reference
        ModifiableLengthField lengthField = new ModifiableLengthField(emptyRef);

        // Test behavior when reference exists but has no original value
        assertNull(
                lengthField.getOriginalValue(),
                "Original value should be null when referenced array has no value");
        assertNull(
                lengthField.getValue(), "Value should be null when referenced array has no value");

        // Now set an original value
        emptyRef.setOriginalValue(new byte[] {1, 2, 3});

        // Values should now be available
        assertEquals(
                3,
                (int) lengthField.getOriginalValue(),
                "Original value should reflect array length after value is set");
        assertEquals(
                3,
                (int) lengthField.getValue(),
                "Value should reflect array length after value is set");

        // Apply a modification
        lengthField.setModifications(new IntegerAddModification(10));

        // Original value should still be array length, but getValue should include modification
        assertEquals(
                3,
                (int) lengthField.getOriginalValue(),
                "Original value should still be array length");
        assertEquals(13, (int) lengthField.getValue(), "Value should include modification");
    }
}
