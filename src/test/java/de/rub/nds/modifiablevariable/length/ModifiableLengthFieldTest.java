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
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
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
        assertEquals(4, (int) lengthField1.getValue());

        // Test that the original value changes when the referenced array changes
        array.setOriginalValue(new byte[] {0, 1, 2, 3, 4, 5});
        assertEquals(6, (int) lengthField1.getValue());
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

        // Same value, different instances
        assertEquals(lengthField1, lengthField2, "Objects with same state should be equal");

        // Different values
        array = new ModifiableByteArray();
        array.setOriginalValue(new byte[] {1, 4});
        lengthField2 = new ModifiableLengthField(array);
        assertNotEquals(
                lengthField1, lengthField2, "Objects with different values should not be equal");

        // With modifications
        lengthField1.setModifications(new IntegerAddModification(10));
        ModifiableLengthField copy = new ModifiableLengthField(lengthField1);
        assertEquals(lengthField1.getValue(), copy.getValue());
        assertEquals(lengthField1, copy, "Objects with same modifications should be equal");

        // Testing with empty arrays (instead of null)
        ModifiableByteArray emptyArray1 = new ModifiableByteArray();
        emptyArray1.setOriginalValue(new byte[0]);
        ModifiableByteArray emptyArray2 = new ModifiableByteArray();
        emptyArray2.setOriginalValue(new byte[0]);

        ModifiableLengthField emptyField1 = new ModifiableLengthField(emptyArray1);
        ModifiableLengthField emptyField2 = new ModifiableLengthField(emptyArray2);

        // Apply same modifications to both empty fields
        emptyField1.setModifications(new IntegerAddModification(5));
        emptyField2.setModifications(new IntegerAddModification(5));

        // They should be equal since they both evaluate to 0 + 5 modification
        assertEquals(
                emptyField1,
                emptyField2,
                "Objects with same empty arrays and modifications should be equal");

        // Different modifications
        emptyField2.setModifications(new IntegerAddModification(10));
        assertNotEquals(
                emptyField1,
                emptyField2,
                "Objects with different modifications should not be equal");

        // Different types
        assertNotEquals(lengthField1, new Object(), "Should not equal different types");
        assertNotEquals(lengthField1, null, "Should not equal null");

        // Case where one reference is null
        ModifiableByteArray justArray = new ModifiableByteArray();
        justArray.setOriginalValue(new byte[] {1, 2, 3, 4});
        ModifiableLengthField justLengthField = new ModifiableLengthField(justArray);

        // Create a modified length field with the same value but different array reference
        array = new ModifiableByteArray();
        array.setOriginalValue(new byte[] {5, 6, 7, 8}); // Different content but same length
        ModifiableLengthField otherLengthField = new ModifiableLengthField(array);

        // They should be equal because they have the same length value
        assertEquals(
                justLengthField,
                otherLengthField,
                "Fields with same length but different arrays should be equal");
    }

    /** Test of hashCode method, of class ModifiableLengthField. */
    @Test
    public void testHashCode() {
        // Same hashCode for equal objects
        assertEquals(
                lengthField1.hashCode(),
                lengthField2.hashCode(),
                "Equal objects should have same hash code");

        // Different hashCodes for different objects
        array = new ModifiableByteArray();
        array.setOriginalValue(new byte[] {1, 4});
        lengthField2 = new ModifiableLengthField(array);
        assertNotEquals(
                lengthField1.hashCode(),
                lengthField2.hashCode(),
                "Different objects should have different hash codes");

        // With modifications
        lengthField1.setModifications(new IntegerAddModification(10));
        ModifiableLengthField copy = new ModifiableLengthField(lengthField1);
        assertEquals(
                lengthField1.hashCode(),
                copy.hashCode(),
                "Objects with same modified value should have same hash code");

        // With empty arrays
        ModifiableByteArray emptyArray = new ModifiableByteArray();
        emptyArray.setOriginalValue(new byte[0]);
        ModifiableLengthField emptyField1 = new ModifiableLengthField(emptyArray);
        ModifiableLengthField emptyField2 = new ModifiableLengthField(emptyArray);

        // Test hashCode consistency with empty arrays
        assertEquals(
                emptyField1.hashCode(),
                emptyField2.hashCode(),
                "Equal objects with empty arrays should have same hash code");

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
        ModifiableLengthField nullField = new ModifiableLengthField(nullArray);
        assertThrows(
                NullPointerException.class,
                () -> nullField.getOriginalValue(),
                "Should throw NullPointerException when referenced array is null");
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

        assertEquals(field1, field2, "Fields with same length value should be equal");
        assertEquals(
                field1.hashCode(),
                field2.hashCode(),
                "Fields with same length value should have same hash code");

        // Add modification to one
        field1.setModifications(new IntegerAddModification(2));
        assertNotEquals(
                field1, field2, "Fields with different modified values should not be equal");
        assertNotEquals(
                field1.hashCode(),
                field2.hashCode(),
                "Fields with different modified values should have different hash codes");
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

        // Two fields that will have null getValue() results
        ModifiableLengthField nullResultField1 = new ModifiableLengthField(array1);
        nullResultField1.setModifications(new NullModification());
        ModifiableLengthField nullResultField2 = new ModifiableLengthField(array2);
        nullResultField2.setModifications(new NullModification());

        // Check that the modification actually returns null
        assertNull(nullResultField1.getValue(), "Modified value should be null");
        assertNull(nullResultField2.getValue(), "Modified value should be null");

        // Test equals: two fields with null modified values should be equal
        assertEquals(
                nullResultField1, nullResultField2, "Fields with same null values should be equal");

        // Test equals: a field with null value vs. field with non-null value
        ModifiableLengthField nonNullField = new ModifiableLengthField(array1);
        nonNullField.setModifications(new IntegerAddModification(5));

        assertNotEquals(
                nullResultField1,
                nonNullField,
                "Field with null value should not equal field with non-null value");

        // Check the other direction explicitly to test the other branch in equals()
        assertNotEquals(
                nonNullField,
                nullResultField1,
                "Field with non-null value should not equal field with null value");

        // Test the specific branch in equals() where this.getValue() is null but that.getValue() is
        // not null
        assertFalse(
                nullResultField1.equals(nonNullField),
                "Field with null value should not equal field with non-null value");

        // Test the specific branch in equals() where this.getValue() is not null but
        // that.getValue() is null
        assertFalse(
                nonNullField.equals(nullResultField1),
                "Field with non-null value should not equal field with null value");

        // Test hashCode: two fields with null modified values should have same hashCode
        assertEquals(
                nullResultField1.hashCode(),
                nullResultField2.hashCode(),
                "Fields with same null values should have same hash code");

        // Test hashCode: explicitly verify the branch where getValue() is null
        int expectedHashCode = 17; // Initial value in hashCode()
        expectedHashCode = 31 * expectedHashCode + 0; // getValue() is null, so 0 is added
        assertEquals(
                expectedHashCode,
                nullResultField1.hashCode(),
                "HashCode calculation should handle null getValue() correctly");

        // Test hashCode: a field with null value vs. field with non-null value
        assertNotEquals(
                nullResultField1.hashCode(),
                nonNullField.hashCode(),
                "Field with null value should have different hash code than field with non-null value");
    }

    /** Test the private constructor using reflection */
    @Test
    public void testPrivateConstructor()
            throws NoSuchMethodException,
                    IllegalAccessException,
                    InvocationTargetException,
                    InstantiationException,
                    NoSuchFieldException {
        // Get the private constructor
        Constructor<ModifiableLengthField> constructor =
                ModifiableLengthField.class.getDeclaredConstructor();

        // Verify it is private
        assertTrue(Modifier.isPrivate(constructor.getModifiers()), "Constructor should be private");

        // Make it accessible for testing
        constructor.setAccessible(true);

        // Create an instance using the private constructor
        ModifiableLengthField instance = constructor.newInstance();

        // Verify the ref field is null as specified in the constructor
        // Need to use reflection again to access the private field
        java.lang.reflect.Field refField = ModifiableLengthField.class.getDeclaredField("ref");
        refField.setAccessible(true);
        assertNull(refField.get(instance), "The ref field should be null");

        // Test the behavior of a ModifiableLengthField created with the private constructor

        // Test that getOriginalValue throws NullPointerException since ref is null
        assertThrows(
                NullPointerException.class,
                () -> instance.getOriginalValue(),
                "getOriginalValue should throw NullPointerException when ref is null");

        // Test that getValue throws a NullPointerException since ref is null
        assertThrows(
                NullPointerException.class,
                () -> instance.getValue(),
                "getValue should throw NullPointerException when ref is null");

        // Test that setOriginalValue still throws UnsupportedOperationException
        assertThrows(
                UnsupportedOperationException.class,
                () -> instance.setOriginalValue(5),
                "setOriginalValue should throw UnsupportedOperationException");

        // Test that the toString method handles null ref gracefully
        String toStringResult = instance.toString();
        assertTrue(
                toStringResult.contains("ref=null"),
                "toString should contain 'ref=null' for no-arg constructor instance");
    }
}
