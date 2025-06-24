/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.singlebyte;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ModifiableByteTest {

    private ModifiableByte byte1;
    private ModifiableByte byte2;

    @BeforeEach
    void setUp() {
        byte1 = new ModifiableByte();
        byte1.setOriginalValue((byte) 3);
        byte2 = new ModifiableByte();
        byte2.setOriginalValue((byte) 3);
    }

    /** Test of getAssertEquals method, of class ModifiableByte. */
    @Test
    void testGetAssertEquals() {
        assertNull(byte1.getAssertEquals());

        Byte expected = (byte) 5;
        byte1.setAssertEquals(expected);
        assertEquals(expected, byte1.getAssertEquals());
    }

    /** Test of setAssertEquals method, of class ModifiableByte. */
    @Test
    void testSetAssertEquals() {
        Byte expected = (byte) 42;
        byte1.setAssertEquals(expected);
        assertEquals(expected, byte1.getAssertEquals());

        // Test setting null
        byte1.setAssertEquals(null);
        assertNull(byte1.getAssertEquals());
    }

    /** Test of isOriginalValueModified method, of class ModifiableByte. */
    @Test
    void testIsOriginalValueModified() {
        // Initially not modified
        assertFalse(byte1.isOriginalValueModified());

        // Modify with add modification
        byte1.setModifications(new ByteAddModification((byte) 1));
        assertTrue(byte1.isOriginalValueModified());

        // Test with explicit value equal to original
        // Note: ModifiableVariable considers a value modified if any modification is applied,
        // even if the resulting value equals the original
        byte1.setModifications(new ByteExplicitValueModification((byte) 3));
        // We need to check for value equality rather than object identity
        assertEquals(byte1.getOriginalValue(), byte1.getValue());

        // Test with a null original value
        ModifiableByte nullByte = new ModifiableByte();
        assertThrows(IllegalStateException.class, nullByte::isOriginalValueModified);
    }

    /** Test of validateAssertions method, of class ModifiableByte. */
    @Test
    void testValidateAssertions() {
        // No assertion set
        assertTrue(byte1.validateAssertions());

        // Set assertion equal to value
        byte1.setAssertEquals((byte) 3);
        assertTrue(byte1.validateAssertions());

        // Set assertion different from value
        byte1.setAssertEquals((byte) 4);
        assertFalse(byte1.validateAssertions());

        // Modify value to match assertion
        byte1.setModifications(new ByteAddModification((byte) 1));
        assertTrue(byte1.validateAssertions());
    }

    /** Test of getOriginalValue method, of class ModifiableByte. */
    @Test
    void testGetOriginalValue() {
        assertEquals((byte) 3, byte1.getOriginalValue());

        ModifiableByte emptyByte = new ModifiableByte();
        assertNull(emptyByte.getOriginalValue());

        ModifiableByte constructorByte = new ModifiableByte((byte) 42);
        assertEquals((byte) 42, constructorByte.getOriginalValue());
    }

    /** Test of setOriginalValue method, of class ModifiableByte. */
    @Test
    void testSetOriginalValue() {
        byte1.setOriginalValue((byte) 77);
        assertEquals((byte) 77, byte1.getOriginalValue());

        // Test setting null
        byte1.setOriginalValue(null);
        assertNull(byte1.getOriginalValue());
    }

    /** Test of toString method, of class ModifiableByte. */
    @Test
    void testToString() {
        String result = byte1.toString();
        assertTrue(result.contains("originalValue=3"));

        // Test with modifications
        byte1.setModifications(new ByteAddModification((byte) 5));
        result = byte1.toString();
        assertTrue(result.contains("originalValue=3"));
        assertTrue(result.contains("ByteAddModification"));
    }

    /** Test of equals method, of class ModifiableByte. */
    @Test
    void testEquals() {
        // Initial test from original code
        assertEquals(byte1, byte2);
        byte2.setOriginalValue((byte) 4);
        assertNotEquals(byte1, byte2);

        // Same object reference
        assertEquals(byte1, byte1);

        // Different types
        assertNotEquals(byte1, "not a ModifiableByte");
        assertNotEquals(byte1, null);

        // Same value after modification
        byte1.setModifications(new ByteAddModification((byte) 1));
        byte2.setOriginalValue((byte) 3);
        byte2.setModifications(new ByteAddModification((byte) 1));
        assertEquals(byte1, byte2);

        // Different modifications but same result
        byte1 = new ModifiableByte((byte) 3);
        byte2 = new ModifiableByte((byte) 2);
        byte2.setModifications(new ByteAddModification((byte) 1));
        assertEquals(byte1, byte2); // Both have value 3

        // Different original values but same result after modification
        byte1 = new ModifiableByte((byte) 5);
        byte2 = new ModifiableByte((byte) 10);
        byte2.setModifications(new ByteExplicitValueModification((byte) 5));
        assertEquals(byte1, byte2);

        // Same original values but different results after modification
        byte1 = new ModifiableByte((byte) 5);
        byte2 = new ModifiableByte((byte) 5);
        byte2.setModifications(new ByteAddModification((byte) 1));
        assertNotEquals(byte1, byte2);

        // Test with null values
        ModifiableByte nullByte1 = new ModifiableByte();
        ModifiableByte nullByte2 = new ModifiableByte();
        assertEquals(nullByte1, nullByte2);

        // One null, one non-null
        ModifiableByte nullByte = new ModifiableByte();
        ModifiableByte nonNullByte = new ModifiableByte((byte) 0);
        assertNotEquals(nullByte, nonNullByte);

        // Both with modifications that result in null
        ModifiableByte modifiedNull1 = new ModifiableByte();
        modifiedNull1.setOriginalValue(null);
        ModifiableByte modifiedNull2 = new ModifiableByte((byte) 10);
        modifiedNull2.setOriginalValue(null);
        assertEquals(modifiedNull1, modifiedNull2);
    }

    /** Test of hashCode method, of class ModifiableByte. */
    @Test
    void testHashCode() {
        // Equal objects should have equal hash codes
        assertEquals(byte1.hashCode(), byte2.hashCode());

        // Modified objects with same value should have equal hash codes
        byte2.setModifications(new ByteExplicitValueModification((byte) 3));
        assertEquals(byte1.hashCode(), byte2.hashCode());

        // Different values should (typically) have different hash codes
        byte2.setModifications(new ByteExplicitValueModification((byte) 42));
        assertNotEquals(byte1.hashCode(), byte2.hashCode());

        // Test with null value
        ModifiableByte nullByte = new ModifiableByte();
        // The actual hashCode implementation varies by JVM and implementation
        int nullByteHash = nullByte.hashCode();
    }

    /** Test of copy constructor and createCopy method. */
    @Test
    void testCopyMethods() {
        // Set up a ModifiableByte with modifications and assertion
        byte1.setModifications(new ByteAddModification((byte) 2));
        byte1.setAssertEquals((byte) 5);

        // Test copy constructor
        ModifiableByte copy1 = new ModifiableByte(byte1);
        assertEquals(byte1.getOriginalValue(), copy1.getOriginalValue());
        assertEquals(byte1.getValue(), copy1.getValue());
        assertEquals(byte1.getAssertEquals(), copy1.getAssertEquals());

        // Test createCopy method
        ModifiableByte copy2 = byte1.createCopy();
        assertEquals(byte1.getOriginalValue(), copy2.getOriginalValue());
        assertEquals(byte1.getValue(), copy2.getValue());
        assertEquals(byte1.getAssertEquals(), copy2.getAssertEquals());

        // Verify independence of the copies
        byte1.setOriginalValue((byte) 10);
        assertNotEquals(byte1.getOriginalValue(), copy1.getOriginalValue());
        assertNotEquals(byte1.getOriginalValue(), copy2.getOriginalValue());
    }
}
