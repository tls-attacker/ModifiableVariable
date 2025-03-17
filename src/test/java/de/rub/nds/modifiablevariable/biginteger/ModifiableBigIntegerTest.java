/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.biginteger;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigInteger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ModifiableBigIntegerTest {

    private ModifiableBigInteger integer1;
    private ModifiableBigInteger integer2;

    @BeforeEach
    public void setUp() {
        integer1 = new ModifiableBigInteger();
        integer1.setOriginalValue(BigInteger.ONE);
        integer2 = new ModifiableBigInteger();
        integer2.setOriginalValue(BigInteger.TEN);
    }

    /** Test default constructor */
    @Test
    public void testDefaultConstructor() {
        ModifiableBigInteger defaultInteger = new ModifiableBigInteger();
        assertNull(defaultInteger.getOriginalValue());
        assertNull(defaultInteger.getValue());
    }

    /** Test constructor with original value */
    @Test
    public void testConstructorWithValue() {
        ModifiableBigInteger valueInteger = new ModifiableBigInteger(BigInteger.valueOf(42));
        assertEquals(BigInteger.valueOf(42), valueInteger.getOriginalValue());
        assertEquals(BigInteger.valueOf(42), valueInteger.getValue());
    }

    /** Test copy constructor */
    @Test
    public void testCopyConstructor() {
        // Set up an integer with modifications
        integer1.setModifications(new BigIntegerAddModification(BigInteger.valueOf(5)));
        integer1.setAssertEquals(BigInteger.valueOf(6));

        // Create a copy
        ModifiableBigInteger copy = new ModifiableBigInteger(integer1);

        // Verify properties are copied
        assertEquals(integer1.getOriginalValue(), copy.getOriginalValue());
        assertEquals(integer1.getValue(), copy.getValue());
        assertEquals(BigInteger.valueOf(6), copy.getValue());
        assertTrue(copy.validateAssertions());
    }

    /** Test the createCopy method */
    @Test
    public void testCreateCopy() {
        // Set up an integer with modifications
        integer1.setModifications(new BigIntegerMultiplyModification(BigInteger.valueOf(3)));
        integer1.setAssertEquals(BigInteger.valueOf(3));

        // Create a copy using the createCopy method
        ModifiableBigInteger copy = integer1.createCopy();

        // Verify properties are copied
        assertEquals(integer1.getOriginalValue(), copy.getOriginalValue());
        assertEquals(integer1.getValue(), copy.getValue());
        assertEquals(BigInteger.valueOf(3), copy.getValue());
        assertEquals(integer1.getAssertEquals(), copy.getAssertEquals());
    }

    /** Test of getAssertEquals and setAssertEquals methods */
    @Test
    public void testGetAndSetAssertEquals() {
        // Initially null
        assertNull(integer1.getAssertEquals());

        // Set a value and verify
        BigInteger expectedValue = BigInteger.valueOf(42);
        integer1.setAssertEquals(expectedValue);
        assertEquals(expectedValue, integer1.getAssertEquals());

        // Set null and verify
        integer1.setAssertEquals(null);
        assertNull(integer1.getAssertEquals());
    }

    /** Test of isOriginalValueModified method */
    @Test
    public void testIsOriginalValueModified() {
        // Not modified initially
        assertFalse(integer1.isOriginalValueModified());

        // Modified after applying modifications
        integer1.setModifications(new BigIntegerAddModification(BigInteger.valueOf(1)));
        assertTrue(integer1.isOriginalValueModified());

        // Not modified when explicit value equals original
        integer1.setModifications(new BigIntegerExplicitValueModification(BigInteger.ONE));
        assertFalse(integer1.isOriginalValueModified());

        // Throws exception when original value is null
        ModifiableBigInteger nullInteger = new ModifiableBigInteger();
        assertThrows(IllegalStateException.class, () -> nullInteger.isOriginalValueModified());
    }

    /** Test of getByteArray method without arguments */
    @Test
    public void testGetByteArray_0args() {
        // Test with positive value
        byte[] expected = new byte[] {0x0A}; // BigInteger.TEN as byte array
        byte[] actual = integer2.getByteArray();
        assertArrayEquals(expected, actual);

        // Test with modified value
        integer2.setModifications(new BigIntegerAddModification(BigInteger.valueOf(246)));
        byte[] expectedModified = new byte[] {0x01, 0x00}; // 256 as byte array
        byte[] actualModified = integer2.getByteArray();
        assertArrayEquals(expectedModified, actualModified);
    }

    /** Test of getByteArray method with size parameter */
    @Test
    public void testGetByteArray_int() {
        // Test with specific size
        byte[] expected = new byte[] {0x00, 0x00, 0x0A}; // BigInteger.TEN as 3-byte array
        byte[] actual = integer2.getByteArray(3);
        assertArrayEquals(expected, actual);

        // Test with size smaller than required (should pad)
        byte[] expected2 = new byte[] {0x0A}; // BigInteger.TEN as 1-byte array
        byte[] actual2 = integer2.getByteArray(1);
        assertArrayEquals(expected2, actual2);

        // Test with modified value
        integer1.setModifications(new BigIntegerShiftLeftModification(8)); // 1 << 8 = 256
        byte[] expectedModified = new byte[] {0x00, 0x01, 0x00}; // 256 as 3-byte array
        byte[] actualModified = integer1.getByteArray(3);
        assertArrayEquals(expectedModified, actualModified);
    }

    /** Test of validateAssertions method */
    @Test
    public void testValidateAssertions() {
        // No assertions set initially
        assertTrue(integer1.validateAssertions());

        // Assertion equals value
        integer1.setAssertEquals(BigInteger.ONE);
        assertTrue(integer1.validateAssertions());

        // Assertion doesn't match
        integer1.setAssertEquals(BigInteger.TEN);
        assertFalse(integer1.validateAssertions());

        // After modification, assertion matches
        integer1.setModifications(new BigIntegerAddModification(BigInteger.valueOf(9)));
        assertTrue(integer1.validateAssertions());
    }

    /** Test of getOriginalValue and setOriginalValue methods */
    @Test
    public void testGetAndSetOriginalValue() {
        assertEquals(BigInteger.ONE, integer1.getOriginalValue());

        // Set a different value
        BigInteger newValue = BigInteger.valueOf(42);
        integer1.setOriginalValue(newValue);
        assertEquals(newValue, integer1.getOriginalValue());
        assertEquals(newValue, integer1.getValue());

        // Set null
        integer1.setOriginalValue(null);
        assertNull(integer1.getOriginalValue());
        assertNull(integer1.getValue());
    }

    /** Test of toString method */
    @Test
    public void testToString() {
        String expectedToString = "ModifiableBigInteger{originalValue=1}";
        assertEquals(expectedToString, integer1.toString());

        // With modifications
        integer1.setModifications(new BigIntegerAddModification(BigInteger.valueOf(5)));
        assertTrue(integer1.toString().contains("originalValue=1"));
        assertTrue(integer1.toString().contains("BigIntegerAddModification"));

        // With null value
        ModifiableBigInteger nullInteger = new ModifiableBigInteger();
        assertTrue(nullInteger.toString().contains("originalValue=null"));
    }

    /** Test equals method with same object */
    @Test
    public void testEqualsSameObject() {
        // Same object reference should be equal
        assertTrue(integer1.equals(integer1));
    }

    /** Test equals method with same values */
    @Test
    public void testEqualsSameValues() {
        // Equal objects with same original value
        ModifiableBigInteger integer3 = new ModifiableBigInteger();
        integer3.setOriginalValue(BigInteger.ONE);
        assertEquals(integer1, integer3);
        assertEquals(integer3, integer1);
    }

    /** Test equals method with modifications */
    @Test
    public void testEqualsWithModifications() {
        // Equal objects with same modifications
        ModifiableBigInteger integer3 = new ModifiableBigInteger();
        integer3.setOriginalValue(BigInteger.ONE);

        integer1.setModifications(new BigIntegerAddModification(BigInteger.valueOf(5)));
        integer3.setModifications(new BigIntegerAddModification(BigInteger.valueOf(5)));
        assertEquals(integer1, integer3);

        // Different original values but same end result after modification
        ModifiableBigInteger integer4 = new ModifiableBigInteger();
        integer4.setOriginalValue(BigInteger.ZERO);
        integer4.setModifications(new BigIntegerAddModification(BigInteger.valueOf(6)));

        // Should be equal because equals() compares getValue() result, not originalValue
        assertNotEquals(integer1.getOriginalValue(), integer4.getOriginalValue());
        assertEquals(integer1.getValue(), integer4.getValue());
        assertEquals(integer1, integer4);
    }

    /** Test equals method with different values */
    @Test
    public void testEqualsWithDifferentValues() {
        // Different objects with different modifications
        ModifiableBigInteger integer3 = new ModifiableBigInteger();
        integer3.setOriginalValue(BigInteger.ONE);
        integer3.setModifications(new BigIntegerMultiplyModification(BigInteger.valueOf(2)));

        assertNotEquals(integer1, integer3);
    }

    /** Test equals method with different types of modification */
    @Test
    public void testEqualsWithDifferentModificationTypes() {
        // Objects with different types of modifications but same final value
        ModifiableBigInteger integer3 = new ModifiableBigInteger();
        integer3.setOriginalValue(BigInteger.ONE);
        integer3.setModifications(new BigIntegerAddModification(BigInteger.valueOf(5)));

        ModifiableBigInteger integer4 = new ModifiableBigInteger();
        integer4.setOriginalValue(BigInteger.valueOf(3));
        integer4.setModifications(new BigIntegerAddModification(BigInteger.valueOf(3)));

        // Should be equal because equals() compares getValue() result, not modification type
        assertEquals(integer3, integer4);
    }

    /** Test equals method with null values */
    @Test
    public void testEqualsWithNullValues() {
        // Equal objects with null values
        ModifiableBigInteger nullInteger1 = new ModifiableBigInteger();
        ModifiableBigInteger nullInteger2 = new ModifiableBigInteger();
        assertEquals(nullInteger1, nullInteger2);

        // One object with null value, one with non-null
        ModifiableBigInteger nonNullInteger = new ModifiableBigInteger();
        nonNullInteger.setOriginalValue(BigInteger.valueOf(42));
        assertNotEquals(nullInteger1, nonNullInteger);
        assertNotEquals(nonNullInteger, nullInteger1);
    }

    /** Test equals method with null and different types */
    @Test
    public void testEqualsWithNullAndDifferentTypes() {
        // Inequality with null and different types
        assertFalse(integer1.equals(null));
        assertFalse(integer1.equals("NotABigInteger"));
        assertFalse(integer1.equals(new Object()));
        assertFalse(integer1.equals(123));
    }

    /** Test equals method with explicit values */
    @Test
    public void testEqualsWithExplicitValues() {
        // Two objects with different original values but same explicit value
        ModifiableBigInteger integer3 = new ModifiableBigInteger(BigInteger.valueOf(42));
        ModifiableBigInteger integer4 = new ModifiableBigInteger(BigInteger.valueOf(100));

        integer3.setModifications(
                new BigIntegerExplicitValueModification(BigInteger.valueOf(1000)));
        integer4.setModifications(
                new BigIntegerExplicitValueModification(BigInteger.valueOf(1000)));

        assertEquals(integer3, integer4);
    }

    /** Test equals method with different modification types but same result */
    @Test
    public void testEqualsWithDifferentModificationTypesSameResult() {
        // Different modification types resulting in the same final value
        ModifiableBigInteger integer1 = new ModifiableBigInteger(BigInteger.valueOf(10));
        ModifiableBigInteger integer2 = new ModifiableBigInteger(BigInteger.valueOf(5));
        ModifiableBigInteger integer3 = new ModifiableBigInteger(BigInteger.valueOf(2));

        // 10 + 10 = 20
        integer1.setModifications(new BigIntegerAddModification(BigInteger.valueOf(10)));
        // 5 * 4 = 20
        integer2.setModifications(new BigIntegerMultiplyModification(BigInteger.valueOf(4)));
        // 2 << 3 = 16, then + 4 = 20
        integer3.setModifications(new BigIntegerShiftLeftModification(3));
        integer3.addModification(new BigIntegerAddModification(BigInteger.valueOf(4)));

        // All should be equal since they result in the same value (20)
        assertEquals(integer1.getValue(), integer2.getValue());
        assertEquals(integer2.getValue(), integer3.getValue());
        assertEquals(integer1, integer2);
        assertEquals(integer2, integer3);
        assertEquals(integer1, integer3);
    }

    /** Test equals method with chained modifications */
    @Test
    public void testEqualsWithChainedModifications() {
        // Two objects with the same chain of modifications
        ModifiableBigInteger integer1 = new ModifiableBigInteger(BigInteger.valueOf(5));
        ModifiableBigInteger integer2 = new ModifiableBigInteger(BigInteger.valueOf(5));

        // Apply a sequence of modifications to both integers
        integer1.setModifications(new BigIntegerAddModification(BigInteger.valueOf(5)));
        integer1.addModification(new BigIntegerMultiplyModification(BigInteger.valueOf(2)));
        integer1.addModification(new BigIntegerXorModification(BigInteger.valueOf(3)));

        integer2.setModifications(new BigIntegerAddModification(BigInteger.valueOf(5)));
        integer2.addModification(new BigIntegerMultiplyModification(BigInteger.valueOf(2)));
        integer2.addModification(new BigIntegerXorModification(BigInteger.valueOf(3)));

        // Should be equal
        assertEquals(integer1.getValue(), integer2.getValue());
        assertEquals(integer1, integer2);

        // Modify one by adding another modification
        integer1.addModification(new BigIntegerAddModification(BigInteger.valueOf(1)));

        // No longer equal
        assertNotEquals(integer1.getValue(), integer2.getValue());
        assertNotEquals(integer1, integer2);
    }

    /** Test equals method with modified null and non-null original values */
    @Test
    public void testEqualsWithMixedNullOriginalValues() {
        // Initialize with different original values (one null, one non-null)
        ModifiableBigInteger nullOriginal = new ModifiableBigInteger();
        ModifiableBigInteger nonNullOriginal = new ModifiableBigInteger(BigInteger.valueOf(5));

        // Initially not equal
        assertNotEquals(nullOriginal, nonNullOriginal);

        // Set original value in the null instance
        nullOriginal.setOriginalValue(BigInteger.ZERO);

        // Now both have non-null original values, but they're different
        assertNotEquals(nullOriginal.getOriginalValue(), nonNullOriginal.getOriginalValue());

        // Set explicit values to the same value
        nullOriginal.setModifications(
                new BigIntegerExplicitValueModification(BigInteger.valueOf(42)));
        nonNullOriginal.setModifications(
                new BigIntegerExplicitValueModification(BigInteger.valueOf(42)));

        // Should be equal despite different original values
        assertEquals(BigInteger.valueOf(42), nullOriginal.getValue());
        assertEquals(BigInteger.valueOf(42), nonNullOriginal.getValue());
        assertEquals(nullOriginal, nonNullOriginal);
    }

    /** Test hashCode method with basic values */
    @Test
    public void testHashCode() {
        // Equal objects should have same hash code
        ModifiableBigInteger integer3 = new ModifiableBigInteger();
        integer3.setOriginalValue(BigInteger.ONE);
        assertEquals(integer1.hashCode(), integer3.hashCode());
    }

    /** Test hashCode method with modifications */
    @Test
    public void testHashCodeWithModifications() {
        // Objects with same modified value should have same hash code
        ModifiableBigInteger integer3 = new ModifiableBigInteger();
        integer3.setOriginalValue(BigInteger.ONE);
        integer3.setModifications(new BigIntegerAddModification(BigInteger.valueOf(5)));

        ModifiableBigInteger integer4 = new ModifiableBigInteger();
        integer4.setOriginalValue(BigInteger.ONE);
        integer4.setModifications(new BigIntegerAddModification(BigInteger.valueOf(5)));

        assertEquals(integer3.hashCode(), integer4.hashCode());
    }

    /** Test hashCode method with different values */
    @Test
    public void testHashCodeWithDifferentValues() {
        // Objects with different modified values should have different hash codes
        ModifiableBigInteger integer3 = new ModifiableBigInteger();
        integer3.setOriginalValue(BigInteger.valueOf(42));

        assertNotEquals(integer1.hashCode(), integer3.hashCode());
    }

    /** Test hashCode method with same result from different original values */
    @Test
    public void testHashCodeWithSameResult() {
        // Different original values but same final value should have same hash code
        ModifiableBigInteger integer3 = new ModifiableBigInteger();
        integer3.setOriginalValue(BigInteger.ZERO);
        integer3.setModifications(new BigIntegerAddModification(BigInteger.valueOf(6)));

        ModifiableBigInteger integer4 = new ModifiableBigInteger();
        integer4.setOriginalValue(BigInteger.ONE);
        integer4.setModifications(new BigIntegerAddModification(BigInteger.valueOf(5)));

        assertEquals(integer3.getValue(), integer4.getValue());
        assertEquals(integer3.hashCode(), integer4.hashCode());
    }

    /** Test hashCode method with explicit values */
    @Test
    public void testHashCodeWithExplicitValues() {
        // Different original values but same explicit value should have same hash code
        ModifiableBigInteger integer3 = new ModifiableBigInteger(BigInteger.valueOf(42));
        ModifiableBigInteger integer4 = new ModifiableBigInteger(BigInteger.valueOf(100));

        integer3.setModifications(
                new BigIntegerExplicitValueModification(BigInteger.valueOf(1000)));
        integer4.setModifications(
                new BigIntegerExplicitValueModification(BigInteger.valueOf(1000)));

        assertEquals(integer3.getValue(), integer4.getValue());
        assertEquals(integer3.hashCode(), integer4.hashCode());
    }

    /** Test hashCode method with null value */
    @Test
    public void testHashCodeWithNull() {
        // Null value handling
        ModifiableBigInteger nullInteger = new ModifiableBigInteger();
        int hashCode = nullInteger.hashCode();

        // Test that hashCode is consistent for multiple calls
        assertEquals(hashCode, nullInteger.hashCode());
        assertEquals(hashCode, nullInteger.hashCode());

        // Should be the same for different instances with null value
        ModifiableBigInteger anotherNullInteger = new ModifiableBigInteger();
        assertEquals(nullInteger.hashCode(), anotherNullInteger.hashCode());
    }

    /** Test hashCode with chained modifications */
    @Test
    public void testHashCodeWithChainedModifications() {
        // Same chain of modifications should produce same hash code
        ModifiableBigInteger integer1 = new ModifiableBigInteger(BigInteger.valueOf(5));
        ModifiableBigInteger integer2 = new ModifiableBigInteger(BigInteger.valueOf(5));

        // Create identical modification chains
        integer1.setModifications(new BigIntegerAddModification(BigInteger.valueOf(5)));
        integer1.addModification(new BigIntegerMultiplyModification(BigInteger.valueOf(2)));

        integer2.setModifications(new BigIntegerAddModification(BigInteger.valueOf(5)));
        integer2.addModification(new BigIntegerMultiplyModification(BigInteger.valueOf(2)));

        // Hash codes should be equal
        assertEquals(integer1.getValue(), integer2.getValue());
        assertEquals(integer1.hashCode(), integer2.hashCode());

        // Different modifications with same result should have same hash code
        ModifiableBigInteger integer3 = new ModifiableBigInteger(BigInteger.valueOf(5));
        integer3.setModifications(new BigIntegerExplicitValueModification(integer1.getValue()));

        assertEquals(integer1.getValue(), integer3.getValue());
        assertEquals(integer1.hashCode(), integer3.hashCode());
    }

    /** Test hashCode consistency with equals method */
    @Test
    public void testHashCodeEqualsConsistency() {
        // Objects that are equal must have the same hash code

        // Different original values, same final result through different means
        ModifiableBigInteger integer1 = new ModifiableBigInteger(BigInteger.valueOf(10));
        ModifiableBigInteger integer2 = new ModifiableBigInteger(BigInteger.valueOf(5));
        ModifiableBigInteger integer3 = new ModifiableBigInteger(BigInteger.valueOf(2));

        // 10 + 10 = 20
        integer1.setModifications(new BigIntegerAddModification(BigInteger.valueOf(10)));
        // 5 * 4 = 20
        integer2.setModifications(new BigIntegerMultiplyModification(BigInteger.valueOf(4)));
        // 2 << 3 = 16, then + 4 = 20
        integer3.setModifications(new BigIntegerShiftLeftModification(3));
        integer3.addModification(new BigIntegerAddModification(BigInteger.valueOf(4)));

        // All should have the same hash code since they're equal
        assertEquals(integer1, integer2);
        assertEquals(integer1.hashCode(), integer2.hashCode());

        assertEquals(integer2, integer3);
        assertEquals(integer2.hashCode(), integer3.hashCode());

        assertEquals(integer1, integer3);
        assertEquals(integer1.hashCode(), integer3.hashCode());
    }

    /** Test hashCode with large values */
    @Test
    public void testHashCodeWithLargeValues() {
        // Test with very large BigInteger values
        BigInteger large1 = new BigInteger("9999999999999999999999999999999999999999");
        BigInteger large2 = new BigInteger("9999999999999999999999999999999999999999");

        ModifiableBigInteger largeInteger1 = new ModifiableBigInteger(large1);
        ModifiableBigInteger largeInteger2 = new ModifiableBigInteger(large2);

        // Same large values should have same hash code
        assertEquals(largeInteger1.hashCode(), largeInteger2.hashCode());

        // Different large values should have different hash codes
        largeInteger2.setModifications(new BigIntegerAddModification(BigInteger.ONE));

        assertNotEquals(largeInteger1.hashCode(), largeInteger2.hashCode());
    }
}
