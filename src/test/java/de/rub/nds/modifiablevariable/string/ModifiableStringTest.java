/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.string;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import de.rub.nds.modifiablevariable.VariableModification;
import java.nio.charset.StandardCharsets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ModifiableStringTest {

    private static final Logger LOGGER = LogManager.getLogger();
    private ModifiableString string;

    @BeforeEach
    void setUp() {
        string = new ModifiableString();
        string.setOriginalValue("TestString");
    }

    /** Test default constructor */
    @Test
    void testDefaultConstructor() {
        ModifiableString defaultString = new ModifiableString();
        assertNull(defaultString.getOriginalValue());
        assertNull(defaultString.getValue());
    }

    /** Test constructor with original value */
    @Test
    void testConstructorWithValue() {
        ModifiableString valueString = new ModifiableString("InitialValue");
        assertEquals("InitialValue", valueString.getOriginalValue());
        assertEquals("InitialValue", valueString.getValue());
    }

    /** Test copy constructor */
    @Test
    void testCopyConstructor() {
        // Set up a string with modifications
        string.setModifications(new StringAppendValueModification("_Appended"));
        string.setAssertEquals("TestString_Appended");

        // Create a copy
        ModifiableString copy = new ModifiableString(string);

        // Verify properties are copied
        assertEquals(string.getOriginalValue(), copy.getOriginalValue());
        assertEquals(string.getValue(), copy.getValue());
        assertEquals("TestString_Appended", copy.getValue());
        assertTrue(copy.validateAssertions());
    }

    /** Test the createCopy method */
    @Test
    void testCreateCopy() {
        // Set up a string with modifications
        string.setModifications(new StringPrependValueModification("Prepended_"));
        string.setAssertEquals("Prepended_TestString");

        // Create a copy using the createCopy method
        ModifiableString copy = string.createCopy();

        // Verify properties are copied
        assertEquals(string.getOriginalValue(), copy.getOriginalValue());
        assertEquals(string.getValue(), copy.getValue());
        assertEquals("Prepended_TestString", copy.getValue());
        assertEquals(string.getAssertEquals(), copy.getAssertEquals());
    }

    /** Test isOriginalValueModified method */
    @Test
    void testIsOriginalValueModified() {
        // Without modifications
        assertFalse(string.isOriginalValueModified());

        // With modifications
        VariableModification<String> modifier = new StringAppendValueModification("_Modified");
        string.setModifications(modifier);
        assertTrue(string.isOriginalValueModified());

        // With explicit value same as original
        string = new ModifiableString("TestString");
        string.setModifications(new StringExplicitValueModification("TestString"));
        assertFalse(string.isOriginalValueModified());

        // With null original value
        ModifiableString nullString = new ModifiableString();
        assertFalse(nullString.isOriginalValueModified());
    }

    /** Test getter and setter methods */
    @Test
    void testGetterAndSetterMethods() {
        assertEquals("TestString", string.getOriginalValue());
        assertEquals("TestString", string.getValue());

        string.setOriginalValue("NewTestString");
        assertEquals("NewTestString", string.getOriginalValue());
        assertEquals("NewTestString", string.getValue());

        // Test setting null value
        string.setOriginalValue(null);
        assertNull(string.getOriginalValue());
        assertNull(string.getValue());
    }

    /** Test assertion methods */
    @Test
    void testAssertions() {
        // When assertion equals original value
        string.setAssertEquals("TestString");
        assertTrue(string.validateAssertions());

        // When assertion doesn't match
        string.setAssertEquals("WrongValue");
        assertFalse(string.validateAssertions());

        // When assertion is null
        string.setAssertEquals(null);
        assertTrue(string.validateAssertions());
    }

    /** Test assertions with null values */
    @Test
    void testAssertionsWithNull() {
        // When original value is null and no assertion is set
        ModifiableString nullString = new ModifiableString();
        assertTrue(nullString.validateAssertions());
    }

    /** Test getByteArray method */
    @Test
    void testGetByteArray() {
        // Test with regular string
        byte[] expected = "TestString".getBytes(StandardCharsets.ISO_8859_1);
        byte[] actual = string.getByteArray();
        assertArrayEquals(expected, actual);

        // Test with modified string
        string.setModifications(new StringAppendValueModification("_Modified"));
        byte[] expectedModified = "TestString_Modified".getBytes(StandardCharsets.ISO_8859_1);
        byte[] actualModified = string.getByteArray();
        assertArrayEquals(expectedModified, actualModified);
    }

    /** Test getByteArray with null value */
    @Test
    void testGetByteArrayWithNull() {
        ModifiableString nullString = new ModifiableString();
        assertThrows(NullPointerException.class, () -> nullString.getByteArray());
    }

    /** Test equals method with same object */
    @Test
    void testEqualsSameObject() {
        // Same object reference should be equal
        assertTrue(string.equals(string));
    }

    /** Test equals method with same values */
    @Test
    void testEqualsSameValues() {
        // Equal objects with same original value
        ModifiableString string2 = new ModifiableString();
        string2.setOriginalValue("TestString");
        assertEquals(string, string2);
        assertEquals(string2, string);
    }

    /** Test equals method with modifications */
    @Test
    void testEqualsWithModifications() {
        // Equal objects with same modifications
        ModifiableString string2 = new ModifiableString();
        string2.setOriginalValue("TestString");

        string.setModifications(new StringAppendValueModification("_Appended"));
        string2.setModifications(new StringAppendValueModification("_Appended"));
        assertEquals(string, string2);

        // Different original values but same end result after modification
        ModifiableString string3 = new ModifiableString();
        string3.setOriginalValue("Test");
        string3.setModifications(new StringAppendValueModification("String_Appended"));

        // Should be equal because equals() compares getValue() result, not originalValue
        assertNotEquals(string.getOriginalValue(), string3.getOriginalValue());
        assertEquals(string.getValue(), string3.getValue());
        assertEquals(string, string3);
    }

    /** Test equals method with different string values */
    @Test
    void testEqualsWithDifferentValues() {
        // Different objects with different modifications
        ModifiableString string2 = new ModifiableString();
        string2.setOriginalValue("TestString");
        string2.setModifications(new StringPrependValueModification("Prepended_"));

        assertNotEquals(string, string2);
    }

    /** Test equals method with different types of modification */
    @Test
    void testEqualsWithDifferentModificationTypes() {
        // Objects with different types of modifications but same final value
        ModifiableString string1 = new ModifiableString();
        string1.setOriginalValue("Test");
        string1.setModifications(new StringAppendValueModification("String"));

        ModifiableString string2 = new ModifiableString();
        string2.setOriginalValue("TestSt");
        string2.setModifications(new StringAppendValueModification("ring"));

        // Should be equal because equals() compares getValue() result, not modification type
        assertEquals(string1, string2);
    }

    /** Test equals method with null values */
    @Test
    void testEqualsWithNullValues() {
        // Equal objects with null values
        ModifiableString nullString1 = new ModifiableString();
        ModifiableString nullString2 = new ModifiableString();
        assertEquals(nullString1, nullString2);

        // One object with null value, one with non-null
        ModifiableString nonNullString = new ModifiableString();
        nonNullString.setOriginalValue("SomeValue");
        assertNotEquals(nullString1, nonNullString);
        assertNotEquals(nonNullString, nullString1);
    }

    /** Test equals method with null and different types */
    @Test
    void testEqualsWithNullAndDifferentTypes() {
        // Inequality with null and different types
        assertFalse(string.equals(null));
        assertFalse(string.equals("TestString"));
        assertFalse(string.equals(new Object()));
        assertFalse(string.equals(123));
    }

    /** Test equals method with explicit values */
    @Test
    void testEqualsWithExplicitValues() {
        // Two objects with different original values but same explicit value
        ModifiableString string1 = new ModifiableString("Original1");
        ModifiableString string2 = new ModifiableString("Original2");

        string1.setModifications(new StringExplicitValueModification("Explicit"));
        string2.setModifications(new StringExplicitValueModification("Explicit"));

        assertEquals(string1, string2);
    }

    /** Test hashCode method with basic values */
    @Test
    void testHashCode() {
        // Equal objects should have same hash code
        ModifiableString string2 = new ModifiableString();
        string2.setOriginalValue("TestString");
        assertEquals(string.hashCode(), string2.hashCode());
    }

    /** Test hashCode method with modifications */
    @Test
    void testHashCodeWithModifications() {
        // Objects with same modified value should have same hash code
        ModifiableString string1 = new ModifiableString();
        string1.setOriginalValue("TestString");
        string1.setModifications(new StringAppendValueModification("_Modified"));

        ModifiableString string2 = new ModifiableString();
        string2.setOriginalValue("TestString");
        string2.setModifications(new StringAppendValueModification("_Modified"));

        assertEquals(string1.hashCode(), string2.hashCode());
    }

    /** Test hashCode method with different values */
    @Test
    void testHashCodeWithDifferentValues() {
        // Objects with different modified values should have different hash codes
        ModifiableString string1 = new ModifiableString();
        string1.setOriginalValue("TestString");

        ModifiableString string2 = new ModifiableString();
        string2.setOriginalValue("DifferentString");

        assertNotEquals(string1.hashCode(), string2.hashCode());
    }

    /** Test hashCode method with same result from different original values */
    @Test
    void testHashCodeWithSameResult() {
        // Different original values but same final value should have same hash code
        ModifiableString string1 = new ModifiableString();
        string1.setOriginalValue("Test");
        string1.setModifications(new StringAppendValueModification("String"));

        ModifiableString string2 = new ModifiableString();
        string2.setOriginalValue("TestSt");
        string2.setModifications(new StringAppendValueModification("ring"));

        assertEquals(string1.getValue(), string2.getValue());
        assertEquals(string1.hashCode(), string2.hashCode());
    }

    /** Test hashCode method with explicit values */
    @Test
    void testHashCodeWithExplicitValues() {
        // Different original values but same explicit value should have same hash code
        ModifiableString string1 = new ModifiableString("Original1");
        ModifiableString string2 = new ModifiableString("Original2");

        string1.setModifications(new StringExplicitValueModification("Explicit"));
        string2.setModifications(new StringExplicitValueModification("Explicit"));

        assertEquals(string1.getValue(), string2.getValue());
        assertEquals(string1.hashCode(), string2.hashCode());
    }

    /** Test hashCode method with null value */
    @Test
    void testHashCodeWithNull() {
        // Null value handling
        ModifiableString nullString = new ModifiableString();
        int hashCode = nullString.hashCode();

        // Test that hashCode is consistent for multiple calls
        assertEquals(hashCode, nullString.hashCode());
        assertEquals(hashCode, nullString.hashCode());

        // Should be the same for different instances with null value
        ModifiableString anotherNullString = new ModifiableString();
        assertEquals(nullString.hashCode(), anotherNullString.hashCode());
    }

    /** Test toString method */
    @Test
    void testToString() {
        String expectedToString = "ModifiableString{originalValue='TestString'}";
        assertEquals(expectedToString, string.toString());

        // With escape characters
        string.setOriginalValue("Test\\String");
        String expectedEscaped = "ModifiableString{originalValue='Test\\\\String'}";
        assertEquals(expectedEscaped, string.toString());

        // With modifications
        string.setOriginalValue("TestString");
        string.setModifications(new StringAppendValueModification("_Appended"));
        assertTrue(string.toString().contains("originalValue='TestString'"));
        assertTrue(string.toString().contains("StringAppendValueModification"));

        // With null value
        ModifiableString nullString = new ModifiableString();
        assertEquals("ModifiableString{originalValue='null'}", nullString.toString());
    }

    /** Test with complex modification chains */
    @Test
    void testComplexModifications() {
        // Create a chain of modifications
        StringAppendValueModification append = new StringAppendValueModification("_Append");
        StringPrependValueModification prepend = new StringPrependValueModification("Prepend_");

        // Add both modifications to create a chain
        string.setModifications(append, prepend);

        // Check the result
        assertEquals("Prepend_TestString_Append", string.getValue());
    }

    /** Test modification reset */
    @Test
    void testModificationReset() {
        string.setModifications(new StringAppendValueModification("_Appended"));
        assertEquals("TestString_Appended", string.getValue());

        // Reset the modifications
        string.clearModifications();
        assertEquals("TestString", string.getValue());
    }
}
