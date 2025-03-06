/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.integer;

import static org.junit.jupiter.api.Assertions.*;

import de.rub.nds.modifiablevariable.VariableModification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ModifiableIntegerTest {

    private ModifiableInteger integer1;
    private ModifiableInteger integer2;
    private ModifiableInteger nullInteger;

    @BeforeEach
    void setUp() {
        integer1 = new ModifiableInteger();
        integer1.setOriginalValue(2);
        integer2 = new ModifiableInteger();
        integer2.setOriginalValue(2);
        nullInteger = new ModifiableInteger();
    }

    /** Test of default constructor, of class ModifiableInteger. */
    @Test
    void testDefaultConstructor() {
        ModifiableInteger instance = new ModifiableInteger();
        assertNull(instance.getOriginalValue());
        assertNull(instance.getValue());
    }

    /** Test of parameterized constructor, of class ModifiableInteger. */
    @Test
    void testParameterizedConstructor() {
        Integer originalValue = 42;
        ModifiableInteger instance = new ModifiableInteger(originalValue);
        assertEquals(originalValue, instance.getOriginalValue());
        assertEquals(originalValue, instance.getValue());
    }

    /** Test of copy constructor, of class ModifiableInteger. */
    @Test
    void testCopyConstructor() {
        Integer originalValue = 42;
        ModifiableInteger original = new ModifiableInteger(originalValue);
        original.setAssertEquals(100);

        ModifiableInteger copy = new ModifiableInteger(original);
        assertEquals(original.getOriginalValue(), copy.getOriginalValue());
        assertEquals(original.getValue(), copy.getValue());
        assertEquals(original.getAssertEquals(), copy.getAssertEquals());
    }

    /** Test of createCopy method, of class ModifiableInteger. */
    @Test
    void testCreateCopy() {
        ModifiableInteger copy = integer1.createCopy();

        assertEquals(integer1.getOriginalValue(), copy.getOriginalValue());
        assertEquals(integer1.getValue(), copy.getValue());
        assertNotSame(integer1, copy);
        // Verify it's a deep copy - modifying the copy doesn't affect the original
        copy.setOriginalValue(999);
        assertNotEquals(integer1.getOriginalValue(), copy.getOriginalValue());
    }

    /** Test of getAssertEquals method, of class ModifiableInteger. */
    @Test
    void testGetAssertEquals() {
        integer1.setAssertEquals(2);
        assertEquals(2, integer1.getAssertEquals());
        assertNull(nullInteger.getAssertEquals());
    }

    /** Test of setAssertEquals method, of class ModifiableInteger. */
    @Test
    void testSetAssertEquals() {
        assertNull(integer1.getAssertEquals());
        integer1.setAssertEquals(3);
        assertEquals(3, integer1.getAssertEquals());

        integer1.setAssertEquals(null);
        assertNull(integer1.getAssertEquals());
    }

    /** Test of getByteArray method, of class ModifiableInteger. */
    @Test
    void testGetByteArray() {
        assertArrayEquals(integer1.getByteArray(2), integer2.getByteArray(2));

        integer1.setOriginalValue(258); // 0x00000102
        byte[] expected = new byte[] {0x01, 0x02}; // Big-endian, 2 bytes
        assertArrayEquals(expected, integer1.getByteArray(2));

        // Test with different sizes
        assertArrayEquals(new byte[] {0x00, 0x00, 0x01, 0x02}, integer1.getByteArray(4));
        assertArrayEquals(new byte[] {0x02}, integer1.getByteArray(1));
    }

    /** Test of validateAssertions method, of class ModifiableInteger. */
    @Test
    void testValidateAssertions() {
        // No assertions set - should be valid
        assertTrue(integer1.validateAssertions());
        assertTrue(nullInteger.validateAssertions());

        // Set matching assertion
        integer1.setAssertEquals(2);
        assertTrue(integer1.validateAssertions());

        // Set non-matching assertion
        integer1.setAssertEquals(3);
        assertFalse(integer1.validateAssertions());

        // Test with modification
        integer1 = new ModifiableInteger(10);
        integer1.setAssertEquals(11);
        assertFalse(integer1.validateAssertions());

        VariableModification<Integer> modifier = new IntegerAddModification(1);
        integer1.setModifications(modifier);
        assertTrue(integer1.validateAssertions());
    }

    /** Test of isOriginalValueModified method, of class ModifiableInteger. */
    @Test
    void testIsOriginalValueModified() {
        // No modification
        assertFalse(integer1.isOriginalValueModified());

        // With modification that changes value
        VariableModification<Integer> modifier = new IntegerAddModification(1);
        integer1.setModifications(modifier);
        assertTrue(integer1.isOriginalValueModified());

        // With modification that doesn't change value
        modifier = new IntegerAddModification(0);
        integer2.setModifications(modifier);
        assertFalse(integer2.isOriginalValueModified());

        // Null original value - if null, then getValue() is also null, so not modified
        assertFalse(nullInteger.isOriginalValueModified());

        // Following the implementation in ModifiableInteger.isOriginalValueModified(),
        // a null originalValue will always return false for isOriginalValueModified()
        // even when the getValue() returns a non-null value
        ModifiableInteger nullIntWithExplicit = new ModifiableInteger((Integer) null);
        nullIntWithExplicit.setModifications(new IntegerExplicitValueModification(5));

        // Verify getValue() is 5
        assertEquals(Integer.valueOf(5), nullIntWithExplicit.getValue());

        // The implementation returns false when originalValue is null, even if getValue() != null
        // This is because the check in isOriginalValueModified() is:
        //   getOriginalValue() != null && getOriginalValue().compareTo(getValue()) != 0;
        assertFalse(nullIntWithExplicit.isOriginalValueModified());
    }

    /** Test of getOriginalValue method, of class ModifiableInteger. */
    @Test
    void testGetOriginalValue() {
        assertEquals(2, integer1.getOriginalValue());
        assertNull(nullInteger.getOriginalValue());
    }

    /** Test of setOriginalValue method, of class ModifiableInteger. */
    @Test
    void testSetOriginalValue() {
        integer1.setOriginalValue(3);
        assertEquals(3, integer1.getOriginalValue());
        assertEquals(3, integer1.getValue());

        integer1.setOriginalValue(null);
        assertNull(integer1.getOriginalValue());
        assertNull(integer1.getValue());
    }

    /** Test of toString method, of class ModifiableInteger. */
    @Test
    void testToString() {
        assertEquals(integer1.toString(), integer2.toString());

        integer1.setOriginalValue(4);
        assertNotEquals(integer1.toString(), integer2.toString());

        String nullString = nullInteger.toString();
        assertTrue(nullString.contains("originalValue=null"));

        // Test with modification
        VariableModification<Integer> modifier = new IntegerAddModification(1);
        integer2.setModifications(modifier);
        String modString = integer2.toString();
        assertEquals(
                "ModifiableInteger{originalValue=2, modifications=[IntegerAddModification{summand=1}]}",
                modString);
    }

    /** Test of equals method, of class ModifiableInteger. */
    @Test
    void testEquals() {
        // Same value
        assertEquals(integer1, integer2);

        // Same object
        assertEquals(integer1, integer1);

        // Different class
        assertNotEquals(integer1, new Object());

        // Null
        assertNotEquals(integer1, null);

        // Different values
        integer2.setOriginalValue(3);
        assertNotEquals(integer1, integer2);

        // Both null value
        ModifiableInteger nullInt1 = new ModifiableInteger();
        ModifiableInteger nullInt2 = new ModifiableInteger();
        assertEquals(nullInt1, nullInt2);

        // One null value
        assertNotEquals(integer1, nullInt1);

        // Same after modification
        ModifiableInteger int1 = new ModifiableInteger(5);
        ModifiableInteger int2 = new ModifiableInteger(4);
        int2.setModifications(new IntegerAddModification(1));
        assertEquals(int1, int2);
    }

    /** Test of hashCode method, of class ModifiableInteger. */
    @Test
    void testHashCode() {
        // Same value, same hash code
        assertEquals(integer1.hashCode(), integer2.hashCode());

        // Different value, different hash code
        integer2.setOriginalValue(3);
        assertNotEquals(integer1.hashCode(), integer2.hashCode());

        // Consistent hash code
        int hash1 = integer1.hashCode();
        int hash2 = integer1.hashCode();
        assertEquals(hash1, hash2);
    }
}
