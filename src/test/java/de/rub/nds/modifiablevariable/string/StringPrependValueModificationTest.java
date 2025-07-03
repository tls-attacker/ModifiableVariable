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

class StringPrependValueModificationTest {

    private ModifiableString modifiableString;
    private final String originalString = "testString";
    private final String prependValue = "PREPEND";

    @BeforeEach
    void setUp() {
        modifiableString = new ModifiableString();
        modifiableString.setOriginalValue(originalString);
    }

    /** Test basic prepend functionality */
    @Test
    void testBasicPrepend() {
        StringPrependValueModification modifier = new StringPrependValueModification(prependValue);
        modifiableString.setModifications(modifier);
        assertEquals(prependValue + originalString, modifiableString.getValue());
        assertEquals(originalString, modifiableString.getOriginalValue());
    }

    /** Test prepend with empty string */
    @Test
    void testPrependEmptyString() {
        StringPrependValueModification modifier = new StringPrependValueModification("");
        modifiableString.setModifications(modifier);
        assertEquals(originalString, modifiableString.getValue());
    }

    /** Test prepend to empty string */
    @Test
    void testPrependToEmptyString() {
        ModifiableString emptyString = new ModifiableString();
        emptyString.setOriginalValue("");

        StringPrependValueModification modifier = new StringPrependValueModification(prependValue);
        emptyString.setModifications(modifier);

        assertEquals(prependValue, emptyString.getValue());
    }

    /** Test prepend to null string */
    @Test
    void testPrependToNullString() {
        ModifiableString nullString = new ModifiableString();
        nullString.setOriginalValue(null);

        StringPrependValueModification modifier = new StringPrependValueModification(prependValue);
        nullString.setModifications(modifier);

        assertNull(nullString.getValue());
    }

    /** Test that null prepend value is not allowed */
    @Test
    void testNullPrependValue() {
        assertThrows(
                NullPointerException.class,
                () -> {
                    // Explicitly cast null to String to disambiguate constructor call
                    new StringPrependValueModification((String) null);
                });
    }

    /** Test the getter and setter for prependValue */
    @Test
    void testPrependValueGetterAndSetter() {
        StringPrependValueModification modifier = new StringPrependValueModification("initial");
        assertEquals("initial", modifier.getPrependValue());

        modifier.setPrependValue("updated");
        assertEquals("updated", modifier.getPrependValue());

        // Setting null should throw NullPointerException
        assertThrows(
                NullPointerException.class,
                () -> {
                    modifier.setPrependValue(null);
                });
    }

    /** Test prepend with special characters */
    @Test
    void testPrependWithSpecialCharacters() {
        String specialChars = "@#$%^&*()";
        StringPrependValueModification modifier = new StringPrependValueModification(specialChars);
        modifiableString.setModifications(modifier);
        assertEquals(specialChars + originalString, modifiableString.getValue());
    }

    /** Test the equals method */
    @Test
    void testEqualsMethod() {
        StringPrependValueModification mod1 = new StringPrependValueModification("value");
        StringPrependValueModification mod2 = new StringPrependValueModification("value");
        StringPrependValueModification mod3 = new StringPrependValueModification("different");

        // Test equality with identical objects
        assertEquals(mod1, mod2);

        // Test self equality
        assertEquals(mod1, mod1);

        // Test inequality with different prepend values
        assertNotEquals(mod1, mod3);

        // Test inequality with null and different types
        assertNotEquals(mod1, null);
        assertNotEquals(mod1, "Not a StringPrependValueModification");
    }

    /** Test the hashCode method */
    @Test
    void testHashCodeMethod() {
        StringPrependValueModification mod1 = new StringPrependValueModification("value");
        StringPrependValueModification mod2 = new StringPrependValueModification("value");

        // Equal objects should have equal hash codes
        assertEquals(mod1.hashCode(), mod2.hashCode());

        // Different objects should have different hash codes
        StringPrependValueModification mod3 = new StringPrependValueModification("other");
        assertNotEquals(mod1.hashCode(), mod3.hashCode());
    }

    /** Test the toString method */
    @Test
    void testToStringMethod() {
        StringPrependValueModification modifier = new StringPrependValueModification("test");
        String toString = modifier.toString();

        // The toString output should contain the value
        assertTrue(toString.contains("prependValue='test'"));
    }

    /** Test the copy constructor */
    @Test
    void testCopyConstructor() {
        StringPrependValueModification original = new StringPrependValueModification("original");
        StringPrependValueModification copy = new StringPrependValueModification(original);

        // The copy should have the same value
        assertEquals(original.getPrependValue(), copy.getPrependValue());

        // Verify the copy is independent
        copy.setPrependValue("changed");
        assertEquals("original", original.getPrependValue());
        assertEquals("changed", copy.getPrependValue());
    }

    /** Test the createCopy method */
    @Test
    void testCreateCopyMethod() {
        StringPrependValueModification original = new StringPrependValueModification("original");
        StringPrependValueModification copy = original.createCopy();

        // The copy should have the same value
        assertEquals(original.getPrependValue(), copy.getPrependValue());

        // Verify the copy is independent
        copy.setPrependValue("changed");
        assertEquals("original", original.getPrependValue());
        assertEquals("changed", copy.getPrependValue());
    }
}
