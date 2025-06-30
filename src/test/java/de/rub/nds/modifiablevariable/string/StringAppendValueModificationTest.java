/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.string;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StringAppendValueModificationTest {

    private ModifiableString modifiableString;
    private final String originalString = "testString";
    private final String appendValue = "APPEND";

    @BeforeEach
    void setUp() {
        modifiableString = new ModifiableString();
        modifiableString.setOriginalValue(originalString);
    }

    /** Test basic append functionality */
    @Test
    void testBasicAppend() {
        StringAppendValueModification modifier = new StringAppendValueModification(appendValue);
        modifiableString.setModifications(modifier);
        assertEquals(originalString + appendValue, modifiableString.getValue());
        assertEquals(originalString, modifiableString.getOriginalValue());
    }

    /** Test append with empty string */
    @Test
    void testAppendEmptyString() {
        StringAppendValueModification modifier = new StringAppendValueModification("");
        modifiableString.setModifications(modifier);
        assertEquals(originalString, modifiableString.getValue());
    }

    /** Test append to empty string */
    @Test
    void testAppendToEmptyString() {
        ModifiableString emptyString = new ModifiableString();
        emptyString.setOriginalValue("");

        StringAppendValueModification modifier = new StringAppendValueModification(appendValue);
        emptyString.setModifications(modifier);

        assertEquals(appendValue, emptyString.getValue());
    }

    /** Test append to null string */
    @Test
    void testAppendToNullString() {
        ModifiableString nullString = new ModifiableString();
        nullString.setOriginalValue(null);

        StringAppendValueModification modifier = new StringAppendValueModification(appendValue);
        nullString.setModifications(modifier);

        assertNull(nullString.getValue());
    }

    /** Test that null append value is not allowed */
    @Test
    static void testNullAppendValue() {
        assertThrows(
                NullPointerException.class,
                () -> {
                    // Explicitly cast null to String to disambiguate constructor call
                    new StringAppendValueModification((String) null);
                });
    }

    /** Test the getter and setter for appendValue */
    @Test
    void testAppendValueGetterAndSetter() {
        StringAppendValueModification modifier = new StringAppendValueModification("initial");
        assertEquals("initial", modifier.getAppendValue());

        modifier.setAppendValue("updated");
        assertEquals("updated", modifier.getAppendValue());

        // Setting null should throw NullPointerException
        assertThrows(
                NullPointerException.class,
                () -> {
                    modifier.setAppendValue(null);
                });
    }

    /** Test append with special characters */
    @Test
    void testAppendWithSpecialCharacters() {
        String specialChars = "@#$%^&*()";
        StringAppendValueModification modifier = new StringAppendValueModification(specialChars);
        modifiableString.setModifications(modifier);
        assertEquals(originalString + specialChars, modifiableString.getValue());
    }

    /** Test the equals method */
    @Test
    void testEqualsMethod() {
        StringAppendValueModification mod1 = new StringAppendValueModification("value");
        StringAppendValueModification mod2 = new StringAppendValueModification("value");
        StringAppendValueModification mod3 = new StringAppendValueModification("different");

        // Test equality with identical objects
        assertEquals(mod1, mod2);

        // Test self equality
        assertEquals(mod1, mod1);

        // Test inequality with different append values
        assertNotEquals(mod1, mod3);

        // Test inequality with null and different types
        assertNotEquals(mod1, null);
        assertNotEquals(mod1, "Not a StringAppendValueModification");
    }

    /** Test the hashCode method */
    @Test
    void testHashCodeMethod() {
        StringAppendValueModification mod1 = new StringAppendValueModification("value");
        StringAppendValueModification mod2 = new StringAppendValueModification("value");

        // Equal objects should have equal hash codes
        assertEquals(mod1.hashCode(), mod2.hashCode());

        // Different objects should have different hash codes
        StringAppendValueModification mod3 = new StringAppendValueModification("other");
        assertNotEquals(mod1.hashCode(), mod3.hashCode());
    }

    /** Test the toString method */
    @Test
    void testToStringMethod() {
        StringAppendValueModification modifier = new StringAppendValueModification("test");
        String toString = modifier.toString();

        // The toString output should contain the value
        assertTrue(toString.contains("appendValue='test'"));
    }

    /** Test the copy constructor */
    @Test
    void testCopyConstructor() {
        StringAppendValueModification original = new StringAppendValueModification("original");
        StringAppendValueModification copy = new StringAppendValueModification(original);

        // The copy should have the same value
        assertEquals(original.getAppendValue(), copy.getAppendValue());

        // Verify the copy is independent
        copy.setAppendValue("changed");
        assertEquals("original", original.getAppendValue());
        assertEquals("changed", copy.getAppendValue());
    }

    /** Test the createCopy method */
    @Test
    void testCreateCopyMethod() {
        StringAppendValueModification original = new StringAppendValueModification("original");
        StringAppendValueModification copy = original.createCopy();

        // The copy should have the same value
        assertEquals(original.getAppendValue(), copy.getAppendValue());

        // Verify the copy is independent
        copy.setAppendValue("changed");
        assertEquals("original", original.getAppendValue());
        assertEquals("changed", copy.getAppendValue());
    }
}
