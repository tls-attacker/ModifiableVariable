/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.integer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IntegerSubtractModificationTest {

    private ModifiableInteger modifiableInteger;
    private int originalValue;

    @BeforeEach
    public void setUp() {
        modifiableInteger = new ModifiableInteger();
        originalValue = 123;
        modifiableInteger.setOriginalValue(originalValue);
    }

    @Test
    public void testSubtractModification() {
        int subtrahend = 50;
        IntegerSubtractModification modification = new IntegerSubtractModification(subtrahend);
        modifiableInteger.setModifications(modification);

        int expected = originalValue - subtrahend;
        int result = modifiableInteger.getValue();

        assertEquals(expected, result);
        assertEquals(73, result);
    }

    @Test
    public void testSubtractZero() {
        int subtrahend = 0;
        IntegerSubtractModification modification = new IntegerSubtractModification(subtrahend);
        modifiableInteger.setModifications(modification);

        int expected = originalValue;
        int result = modifiableInteger.getValue();

        assertEquals(expected, result);
    }

    @Test
    public void testSubtractNegative() {
        int subtrahend = -25;
        IntegerSubtractModification modification = new IntegerSubtractModification(subtrahend);
        modifiableInteger.setModifications(modification);

        int expected = originalValue - subtrahend;
        int result = modifiableInteger.getValue();

        assertEquals(expected, result);
        assertEquals(148, result);
    }

    @Test
    public void testSubtractWithNullInput() {
        modifiableInteger.setOriginalValue(null);

        int subtrahend = 50;
        IntegerSubtractModification modification = new IntegerSubtractModification(subtrahend);
        modifiableInteger.setModifications(modification);
        assertNull(modifiableInteger.getValue());
    }

    @Test
    public void testSubtractMinValue() {
        modifiableInteger.setOriginalValue(Integer.MIN_VALUE);

        int subtrahend = 1;
        IntegerSubtractModification modification = new IntegerSubtractModification(subtrahend);
        modifiableInteger.setModifications(modification);

        int expected = Integer.MIN_VALUE - 1; // Will overflow to Integer.MAX_VALUE
        int result = modifiableInteger.getValue();

        assertEquals(expected, result);
        assertEquals(Integer.MAX_VALUE, result);
    }

    @Test
    public void testSubtractMaxValue() {
        modifiableInteger.setOriginalValue(Integer.MAX_VALUE);

        int subtrahend = -1;
        IntegerSubtractModification modification = new IntegerSubtractModification(subtrahend);
        modifiableInteger.setModifications(modification);

        int expected = Integer.MAX_VALUE - (-1); // Will overflow to Integer.MIN_VALUE
        int result = modifiableInteger.getValue();

        assertEquals(expected, result);
        assertEquals(Integer.MIN_VALUE, result);
    }

    @Test
    public void testGetSubtrahend() {
        int subtrahend = 42;
        IntegerSubtractModification modification = new IntegerSubtractModification(subtrahend);

        assertEquals(subtrahend, modification.getSubtrahend());
    }

    @Test
    public void testSetSubtrahend() {
        IntegerSubtractModification modification = new IntegerSubtractModification(10);

        int newSubtrahend = 42;
        modification.setSubtrahend(newSubtrahend);

        assertEquals(newSubtrahend, modification.getSubtrahend());
    }

    @Test
    public void testCopyConstructor() {
        int subtrahend = 42;
        IntegerSubtractModification original = new IntegerSubtractModification(subtrahend);
        IntegerSubtractModification copy = new IntegerSubtractModification(original);

        assertEquals(original.getSubtrahend(), copy.getSubtrahend());
    }

    @Test
    public void testCreateCopy() {
        int subtrahend = 42;
        IntegerSubtractModification original = new IntegerSubtractModification(subtrahend);
        IntegerSubtractModification copy = original.createCopy();

        assertEquals(original.getSubtrahend(), copy.getSubtrahend());
        assertEquals(original, copy, "Objects with the same subtrahend should be equal");
        assertNotSame(original, copy, "Copy should not be the same reference");
    }

    @Test
    public void testEqualsAndHashCode() {
        int subtrahend1 = 42;
        int subtrahend2 = 42;
        int subtrahend3 = 100;

        IntegerSubtractModification modification1 = new IntegerSubtractModification(subtrahend1);
        IntegerSubtractModification modification2 = new IntegerSubtractModification(subtrahend2);
        IntegerSubtractModification modification3 = new IntegerSubtractModification(subtrahend3);
        IntegerAddModification differentModification = new IntegerAddModification(subtrahend1);

        // Test equals
        assertEquals(modification1, modification1, "Object should be equal to itself");
        assertEquals(modification1, modification2, "Objects with same subtrahend should be equal");
        assertNotEquals(
                modification1,
                modification3,
                "Objects with different subtrahends should not be equal");
        assertNotEquals(
                modification1, differentModification, "Different types should not be equal");
        assertNotEquals(modification1, null, "Object should not be equal to null");
        assertNotEquals(
                modification1, new Object(), "Object should not be equal to different class");

        // Test hashCode
        assertEquals(
                modification1.hashCode(),
                modification2.hashCode(),
                "Equal objects should have same hash code");
        assertNotEquals(
                modification1.hashCode(),
                modification3.hashCode(),
                "Different objects should have different hash codes");
    }

    @Test
    public void testToString() {
        int subtrahend = 42;
        IntegerSubtractModification modification = new IntegerSubtractModification(subtrahend);
        String toString = modification.toString();

        assertEquals("IntegerSubtractModification{subtrahend=" + subtrahend + "}", toString);
    }
}
