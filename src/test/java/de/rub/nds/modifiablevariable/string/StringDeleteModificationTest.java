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

public class StringDeleteModificationTest {

    private ModifiableString modifiableString;
    private final String originalString = "testString";

    @BeforeEach
    public void setUp() {
        modifiableString = new ModifiableString();
        modifiableString.setOriginalValue(originalString);
    }

    /** Test the basic deletion from the start of the string */
    @Test
    public void testBasicDeleteFromStart() {
        StringDeleteModification modifier = new StringDeleteModification(0, 4);
        modifiableString.setModifications(modifier);
        assertEquals("String", modifiableString.getValue());
        assertEquals(originalString, modifiableString.getOriginalValue());
    }

    /** Test deletion from the middle of the string */
    @Test
    public void testDeleteFromMiddle() {
        StringDeleteModification modifier = new StringDeleteModification(4, 2);
        modifiableString.setModifications(modifier);
        assertEquals("testring", modifiableString.getValue());
    }

    /** Test deletion from the end of the string */
    @Test
    public void testDeleteFromEnd() {
        StringDeleteModification modifier = new StringDeleteModification(6, 4);
        modifiableString.setModifications(modifier);
        assertEquals("testSt", modifiableString.getValue());
    }

    /** Test deletion with count exceeding string length */
    @Test
    public void testDeleteCountOverflow() {
        StringDeleteModification modifier = new StringDeleteModification(2, 100);
        modifiableString.setModifications(modifier);
        assertEquals("te", modifiableString.getValue());
    }

    /** Test deletion with negative start position */
    @Test
    public void testDeleteWithNegativeStart() {
        StringDeleteModification modifier = new StringDeleteModification(-1, 1);
        modifiableString.setModifications(modifier);
        // Looking at the implementation in line 105: deleteStartPosition += input.length() - 1;
        // For "testString" (length 10), position -1 wraps to 8 (the 'g' character)
        assertEquals("testStrig", modifiableString.getValue());
    }

    /** Test deletion with start position exceeding string length (wrap around using modulo) */
    @Test
    public void testDeleteWithStartOverflow() {
        StringDeleteModification modifier = new StringDeleteModification(20, 2);
        modifiableString.setModifications(modifier);
        // 20 % 10 = 0, so it should delete from position 0
        assertEquals("stString", modifiableString.getValue());
    }

    /** Test with empty string input */
    @Test
    public void testDeleteFromEmptyString() {
        ModifiableString emptyString = new ModifiableString();
        emptyString.setOriginalValue("");

        StringDeleteModification modifier = new StringDeleteModification(0, 5);
        emptyString.setModifications(modifier);

        // Empty string should remain unchanged
        assertEquals("", emptyString.getValue());
    }

    /** Test with null input */
    @Test
    public void testDeleteFromNullString() {
        ModifiableString nullString = new ModifiableString();
        nullString.setOriginalValue(null);

        StringDeleteModification modifier = new StringDeleteModification(0, 5);
        nullString.setModifications(modifier);

        // Null input should return null
        assertNull(nullString.getValue());
    }

    /** Test deletion with zero count */
    @Test
    public void testDeleteZeroCount() {
        StringDeleteModification modifier = new StringDeleteModification(2, 0);
        modifiableString.setModifications(modifier);
        // No characters should be deleted
        assertEquals(originalString, modifiableString.getValue());
    }

    /** Test deletion with negative count (should be treated as zero) */
    @Test
    public void testDeleteNegativeCount() {
        StringDeleteModification modifier = new StringDeleteModification(2, -3);
        modifiableString.setModifications(modifier);
        // Negative count should be treated as zero (line 109 in the implementation)
        assertEquals(originalString, modifiableString.getValue());
    }

    /** Test the getter and setter for count */
    @Test
    public void testCountGetterAndSetter() {
        StringDeleteModification modifier = new StringDeleteModification(0, 5);
        assertEquals(5, modifier.getCount());

        modifier.setCount(10);
        assertEquals(10, modifier.getCount());
    }

    /** Test the getter and setter for startPosition */
    @Test
    public void testStartPositionGetterAndSetter() {
        StringDeleteModification modifier = new StringDeleteModification(3, 2);
        assertEquals(3, modifier.getStartPosition());

        modifier.setStartPosition(7);
        assertEquals(7, modifier.getStartPosition());
    }

    /** Test the equals method */
    @Test
    public void testEqualsMethod() {
        StringDeleteModification mod1 = new StringDeleteModification(3, 4);
        StringDeleteModification mod2 = new StringDeleteModification(3, 4);
        StringDeleteModification mod3 = new StringDeleteModification(2, 4);
        StringDeleteModification mod4 = new StringDeleteModification(3, 5);

        // Test equality with identical objects
        assertEquals(mod1, mod2);

        // Test self equality
        assertEquals(mod1, mod1);

        // Test inequality with different start positions
        assertNotEquals(mod1, mod3);

        // Test inequality with different counts
        assertNotEquals(mod1, mod4);

        // Test inequality with null and different types
        assertNotEquals(mod1, null);
        assertNotEquals(mod1, "Not a StringDeleteModification");
    }

    /** Test the hashCode method */
    @Test
    public void testHashCodeMethod() {
        StringDeleteModification mod1 = new StringDeleteModification(3, 4);
        StringDeleteModification mod2 = new StringDeleteModification(3, 4);

        // Equal objects should have equal hash codes
        assertEquals(mod1.hashCode(), mod2.hashCode());

        // Different objects should have different hash codes
        StringDeleteModification mod3 = new StringDeleteModification(5, 7);
        assertNotEquals(mod1.hashCode(), mod3.hashCode());
    }

    /** Test the toString method */
    @Test
    public void testToStringMethod() {
        StringDeleteModification modifier = new StringDeleteModification(2, 3);
        String toString = modifier.toString();

        // The toString output should contain the values
        assertTrue(toString.contains("count=3"));
        assertTrue(toString.contains("startPosition=2"));
    }

    /** Test the copy constructor */
    @Test
    public void testCopyConstructor() {
        StringDeleteModification original = new StringDeleteModification(4, 6);
        StringDeleteModification copy = new StringDeleteModification(original);

        // The copy should have the same values
        assertEquals(original.getStartPosition(), copy.getStartPosition());
        assertEquals(original.getCount(), copy.getCount());

        // Verify the copy is independent
        copy.setCount(10);
        assertEquals(6, original.getCount());
        assertEquals(10, copy.getCount());
    }

    /** Test the createCopy method */
    @Test
    public void testCreateCopyMethod() {
        StringDeleteModification original = new StringDeleteModification(3, 5);
        StringDeleteModification copy = original.createCopy();

        // The copy should have the same values
        assertEquals(original.getStartPosition(), copy.getStartPosition());
        assertEquals(original.getCount(), copy.getCount());

        // Verify the copy is independent
        copy.setStartPosition(8);
        assertEquals(3, original.getStartPosition());
        assertEquals(8, copy.getStartPosition());
    }
}
