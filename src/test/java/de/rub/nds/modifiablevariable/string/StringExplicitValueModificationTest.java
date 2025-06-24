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

class StringExplicitValueModificationTest {

    private ModifiableString modifiableString;
    private final String originalString = "testString";
    private final String explicitValue = "EXPLICIT";

    @BeforeEach
    void setUp() {
        modifiableString = new ModifiableString();
        modifiableString.setOriginalValue(originalString);
    }

    /** Test basic explicit value modification */
    @Test
    void testBasicExplicitValue() {
        StringExplicitValueModification modifier =
                new StringExplicitValueModification(explicitValue);
        modifiableString.setModifications(modifier);
        assertEquals(explicitValue, modifiableString.getValue());
        assertEquals(originalString, modifiableString.getOriginalValue());
    }

    /** Test with empty explicit value */
    @Test
    void testEmptyExplicitValue() {
        StringExplicitValueModification modifier = new StringExplicitValueModification("");
        modifiableString.setModifications(modifier);
        assertEquals("", modifiableString.getValue());
    }

    /** Test with explicit value applied to empty string */
    @Test
    void testExplicitValueOnEmptyString() {
        ModifiableString emptyString = new ModifiableString();
        emptyString.setOriginalValue("");

        StringExplicitValueModification modifier =
                new StringExplicitValueModification(explicitValue);
        emptyString.setModifications(modifier);

        assertEquals(explicitValue, emptyString.getValue());
    }

    /** Test with explicit value applied to null string */
    @Test
    void testExplicitValueOnNullString() {
        ModifiableString nullString = new ModifiableString();
        nullString.setOriginalValue(null);

        StringExplicitValueModification modifier =
                new StringExplicitValueModification(explicitValue);
        nullString.setModifications(modifier);

        // null input returns null regardless of explicit value
        assertNull(nullString.getValue());
    }

    /** Test that null explicit value is not allowed */
    @Test
    void testNullExplicitValue() {
        assertThrows(
                NullPointerException.class,
                () -> {
                    // Explicitly cast null to String to disambiguate constructor call
                    new StringExplicitValueModification((String) null);
                });
    }

    /** Test the getter and setter for explicitValue */
    @Test
    void testExplicitValueGetterAndSetter() {
        StringExplicitValueModification modifier = new StringExplicitValueModification("initial");
        assertEquals("initial", modifier.getExplicitValue());

        modifier.setExplicitValue("updated");
        assertEquals("updated", modifier.getExplicitValue());

        // Setting null should throw NullPointerException
        assertThrows(
                NullPointerException.class,
                () -> {
                    modifier.setExplicitValue(null);
                });
    }

    /** Test with special characters in explicit value */
    @Test
    void testExplicitValueWithSpecialCharacters() {
        String specialChars = "@#$%^&*()";
        StringExplicitValueModification modifier =
                new StringExplicitValueModification(specialChars);
        modifiableString.setModifications(modifier);
        assertEquals(specialChars, modifiableString.getValue());
    }

    /** Test the equals method */
    @Test
    void testEqualsMethod() {
        StringExplicitValueModification mod1 = new StringExplicitValueModification("value");
        StringExplicitValueModification mod2 = new StringExplicitValueModification("value");
        StringExplicitValueModification mod3 = new StringExplicitValueModification("different");

        // Test equality with identical objects
        assertEquals(mod1, mod2);

        // Test self equality
        assertEquals(mod1, mod1);

        // Test inequality with different explicit values
        assertNotEquals(mod1, mod3);

        // Test inequality with null and different types
        assertNotEquals(mod1, null);
        assertNotEquals(mod1, "Not a StringExplicitValueModification");
    }

    /** Test the hashCode method */
    @Test
    void testHashCodeMethod() {
        StringExplicitValueModification mod1 = new StringExplicitValueModification("value");
        StringExplicitValueModification mod2 = new StringExplicitValueModification("value");

        // Equal objects should have equal hash codes
        assertEquals(mod1.hashCode(), mod2.hashCode());

        // Different objects should have different hash codes
        StringExplicitValueModification mod3 = new StringExplicitValueModification("other");
        assertNotEquals(mod1.hashCode(), mod3.hashCode());
    }

    /** Test the toString method */
    @Test
    void testToStringMethod() {
        StringExplicitValueModification modifier = new StringExplicitValueModification("test");
        String toString = modifier.toString();

        // The toString output should contain the value
        assertTrue(toString.contains("explicitValue='test'"));
    }

    /** Test the copy constructor */
    @Test
    void testCopyConstructor() {
        StringExplicitValueModification original = new StringExplicitValueModification("original");
        StringExplicitValueModification copy = new StringExplicitValueModification(original);

        // The copy should have the same value
        assertEquals(original.getExplicitValue(), copy.getExplicitValue());

        // Verify the copy is independent
        copy.setExplicitValue("changed");
        assertEquals("original", original.getExplicitValue());
        assertEquals("changed", copy.getExplicitValue());
    }

    /** Test the createCopy method */
    @Test
    void testCreateCopyMethod() {
        StringExplicitValueModification original = new StringExplicitValueModification("original");
        StringExplicitValueModification copy = original.createCopy();

        // The copy should have the same value
        assertEquals(original.getExplicitValue(), copy.getExplicitValue());

        // Verify the copy is independent
        copy.setExplicitValue("changed");
        assertEquals("original", original.getExplicitValue());
        assertEquals("changed", copy.getExplicitValue());
    }
}
