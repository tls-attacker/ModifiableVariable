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

class StringInsertValueModificationTest {

    private ModifiableString modifiableString;
    private final String originalString = "testString";
    private final String insertValue = "INSERT";

    @BeforeEach
    void setUp() {
        modifiableString = new ModifiableString();
        modifiableString.setOriginalValue(originalString);
    }

    /** Test basic insertion at the beginning of the string */
    @Test
    void testInsertAtBeginning() {
        StringInsertValueModification modifier = new StringInsertValueModification(insertValue, 0);
        modifiableString.setModifications(modifier);
        assertEquals("INSERTtestString", modifiableString.getValue());
        assertEquals(originalString, modifiableString.getOriginalValue());
    }

    /** Test insertion in the middle of the string */
    @Test
    void testInsertInMiddle() {
        StringInsertValueModification modifier = new StringInsertValueModification(insertValue, 4);
        modifiableString.setModifications(modifier);
        assertEquals("testINSERTString", modifiableString.getValue());
    }

    /** Test insertion at the end of the string */
    @Test
    void testInsertAtEnd() {
        StringInsertValueModification modifier = new StringInsertValueModification(insertValue, 10);
        modifiableString.setModifications(modifier);
        assertEquals("testStringINSERT", modifiableString.getValue());
    }

    /** Test insertion with negative position (inserts from the end) */
    @Test
    void testInsertWithNegativePosition() {
        StringInsertValueModification modifier = new StringInsertValueModification(insertValue, -1);
        modifiableString.setModifications(modifier);
        // -1 means to insert before the last character
        assertEquals("testStrinINSERTg", modifiableString.getValue());
    }

    /** Test insertion with position exceeding string length (appends to the end) */
    @Test
    void testInsertWithPositionOverflow() {
        StringInsertValueModification modifier = new StringInsertValueModification(insertValue, 22);
        modifiableString.setModifications(modifier);
        // Position beyond string length should append to the end
        assertEquals("testStringINSERT", modifiableString.getValue());
    }

    /** Test insertion with empty string as the original value */
    @Test
    void testInsertIntoEmptyString() {
        ModifiableString emptyString = new ModifiableString();
        emptyString.setOriginalValue("");

        StringInsertValueModification modifier = new StringInsertValueModification(insertValue, 0);
        emptyString.setModifications(modifier);

        assertEquals(insertValue, emptyString.getValue());
    }

    /** Test insertion with empty string as the insert value */
    @Test
    void testInsertEmptyString() {
        StringInsertValueModification modifier = new StringInsertValueModification("", 5);
        modifiableString.setModifications(modifier);

        // Inserting an empty string should not change the original
        assertEquals(originalString, modifiableString.getValue());
    }

    /** Test with null input */
    @Test
    void testInsertIntoNullString() {
        ModifiableString nullString = new ModifiableString();
        nullString.setOriginalValue(null);

        StringInsertValueModification modifier = new StringInsertValueModification(insertValue, 0);
        nullString.setModifications(modifier);

        // Null input should return null
        assertNull(nullString.getValue());
    }

    /** Test that null insert value is not allowed */
    @Test
    void testNullInsertValue() {
        assertThrows(
                NullPointerException.class,
                () -> {
                    new StringInsertValueModification(null, 0);
                });
    }

    /** Test the getter and setter for insertValue */
    @Test
    void testInsertValueGetterAndSetter() {
        StringInsertValueModification modifier = new StringInsertValueModification("initial", 0);
        assertEquals("initial", modifier.getInsertValue());

        modifier.setInsertValue("updated");
        assertEquals("updated", modifier.getInsertValue());

        // Setting null should throw NullPointerException
        assertThrows(
                NullPointerException.class,
                () -> {
                    modifier.setInsertValue(null);
                });
    }

    /** Test the getter and setter for startPosition */
    @Test
    void testStartPositionGetterAndSetter() {
        StringInsertValueModification modifier = new StringInsertValueModification(insertValue, 3);
        assertEquals(3, modifier.getStartPosition());

        modifier.setStartPosition(7);
        assertEquals(7, modifier.getStartPosition());
    }

    /** Test the equals method */
    @Test
    void testEqualsMethod() {
        StringInsertValueModification mod1 = new StringInsertValueModification("value", 3);
        StringInsertValueModification mod2 = new StringInsertValueModification("value", 3);
        StringInsertValueModification mod3 = new StringInsertValueModification("value", 4);
        StringInsertValueModification mod4 = new StringInsertValueModification("different", 3);

        // Test equality with identical objects
        assertEquals(mod1, mod2);

        // Test self equality
        assertEquals(mod1, mod1);

        // Test inequality with different start positions
        assertNotEquals(mod1, mod3);

        // Test inequality with different insert values
        assertNotEquals(mod1, mod4);

        // Test inequality with null and different types
        assertNotEquals(mod1, null);
        assertNotEquals(mod1, "Not a StringInsertValueModification");
    }

    /** Test the hashCode method */
    @Test
    void testHashCodeMethod() {
        StringInsertValueModification mod1 = new StringInsertValueModification("value", 3);
        StringInsertValueModification mod2 = new StringInsertValueModification("value", 3);

        // Equal objects should have equal hash codes
        assertEquals(mod1.hashCode(), mod2.hashCode());

        // Different objects should have different hash codes
        StringInsertValueModification mod3 = new StringInsertValueModification("other", 5);
        assertNotEquals(mod1.hashCode(), mod3.hashCode());
    }

    /** Test the toString method */
    @Test
    void testToStringMethod() {
        StringInsertValueModification modifier = new StringInsertValueModification("test", 2);
        String toString = modifier.toString();

        // The toString output should contain the values
        assertTrue(toString.contains("insertValue='test'"));
        assertTrue(toString.contains("startPosition=2"));
    }

    /** Test the copy constructor */
    @Test
    void testCopyConstructor() {
        StringInsertValueModification original = new StringInsertValueModification("original", 4);
        StringInsertValueModification copy = new StringInsertValueModification(original);

        // The copy should have the same values
        assertEquals(original.getInsertValue(), copy.getInsertValue());
        assertEquals(original.getStartPosition(), copy.getStartPosition());

        // Verify the copy is independent
        copy.setInsertValue("changed");
        assertEquals("original", original.getInsertValue());
        assertEquals("changed", copy.getInsertValue());
    }

    /** Test the createCopy method */
    @Test
    void testCreateCopyMethod() {
        StringInsertValueModification original = new StringInsertValueModification("original", 3);
        StringInsertValueModification copy = original.createCopy();

        // The copy should have the same values
        assertEquals(original.getInsertValue(), copy.getInsertValue());
        assertEquals(original.getStartPosition(), copy.getStartPosition());

        // Verify the copy is independent
        copy.setStartPosition(8);
        assertEquals(3, original.getStartPosition());
        assertEquals(8, copy.getStartPosition());
    }
}
