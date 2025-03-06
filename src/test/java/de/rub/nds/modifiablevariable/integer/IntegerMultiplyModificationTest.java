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

public class IntegerMultiplyModificationTest {

    private ModifiableInteger modifiableInteger;
    private int originalValue;

    @BeforeEach
    public void setUp() {
        modifiableInteger = new ModifiableInteger();
        originalValue = 123;
        modifiableInteger.setOriginalValue(originalValue);
    }

    @Test
    public void testMultiplyModification() {
        int factor = 2;
        IntegerMultiplyModification modification = new IntegerMultiplyModification(factor);
        modifiableInteger.setModifications(modification);

        int expected = originalValue * factor;
        int result = modifiableInteger.getValue();

        assertEquals(expected, result);
        assertEquals(246, result);
    }

    @Test
    public void testMultiplyByZero() {
        int factor = 0;
        IntegerMultiplyModification modification = new IntegerMultiplyModification(factor);
        modifiableInteger.setModifications(modification);

        int expected = 0;
        int result = modifiableInteger.getValue();

        assertEquals(expected, result);
    }

    @Test
    public void testMultiplyByOne() {
        int factor = 1;
        IntegerMultiplyModification modification = new IntegerMultiplyModification(factor);
        modifiableInteger.setModifications(modification);

        int expected = originalValue;
        int result = modifiableInteger.getValue();

        assertEquals(expected, result);
    }

    @Test
    public void testMultiplyByNegative() {
        int factor = -1;
        IntegerMultiplyModification modification = new IntegerMultiplyModification(factor);
        modifiableInteger.setModifications(modification);

        int expected = -originalValue;
        int result = modifiableInteger.getValue();

        assertEquals(expected, result);
    }

    @Test
    public void testMultiplyWithNullInput() {
        modifiableInteger.setOriginalValue(null);

        int factor = 5;
        IntegerMultiplyModification modification = new IntegerMultiplyModification(factor);
        modifiableInteger.setModifications(modification);
        assertNull(modifiableInteger.getValue());
    }

    @Test
    public void testMultiplyMaxValue() {
        modifiableInteger.setOriginalValue(Integer.MAX_VALUE);

        int factor = 2;
        IntegerMultiplyModification modification = new IntegerMultiplyModification(factor);
        modifiableInteger.setModifications(modification);

        int expected = Integer.MAX_VALUE * 2;
        int result = modifiableInteger.getValue();

        assertEquals(expected, result);
    }

    @Test
    public void testMultiplyMinValue() {
        modifiableInteger.setOriginalValue(Integer.MIN_VALUE);

        int factor = 2;
        IntegerMultiplyModification modification = new IntegerMultiplyModification(factor);
        modifiableInteger.setModifications(modification);

        int expected = Integer.MIN_VALUE * 2;
        int result = modifiableInteger.getValue();

        assertEquals(expected, result);
    }

    @Test
    public void testDefaultConstructor() {
        IntegerMultiplyModification modification = new IntegerMultiplyModification();

        // Set factor after construction
        modification.setFactor(3);

        modifiableInteger.setModifications(modification);
        int expected = originalValue * 3;
        int result = modifiableInteger.getValue();

        assertEquals(expected, result);
    }

    @Test
    public void testGetFactor() {
        int factor = 5;
        IntegerMultiplyModification modification = new IntegerMultiplyModification(factor);

        assertEquals(factor, modification.getFactor());
    }

    @Test
    public void testSetFactor() {
        IntegerMultiplyModification modification = new IntegerMultiplyModification(2);

        int newFactor = 10;
        modification.setFactor(newFactor);

        assertEquals(newFactor, modification.getFactor());
    }

    @Test
    public void testCopyConstructor() {
        int factor = 7;
        IntegerMultiplyModification original = new IntegerMultiplyModification(factor);
        IntegerMultiplyModification copy = new IntegerMultiplyModification(original);

        assertEquals(original.getFactor(), copy.getFactor());
    }

    @Test
    public void testCreateCopy() {
        int factor = 7;
        IntegerMultiplyModification original = new IntegerMultiplyModification(factor);
        IntegerMultiplyModification copy = original.createCopy();

        assertEquals(original.getFactor(), copy.getFactor());

        // In this implementation, equals() only checks values, not instance identity
        assertEquals(original, copy, "Objects with the same factor should be equal");

        // We can verify it's a copy by checking they're not the same reference
        assertNotSame(original, copy, "Copy should not be the same reference");
    }

    @Test
    public void testEqualsAndHashCode() {
        int factor1 = 5;
        int factor2 = 5;
        int factor3 = 10;

        IntegerMultiplyModification modification1 = new IntegerMultiplyModification(factor1);
        IntegerMultiplyModification modification2 = new IntegerMultiplyModification(factor2);
        IntegerMultiplyModification modification3 = new IntegerMultiplyModification(factor3);
        IntegerAddModification differentModification = new IntegerAddModification(factor1);

        // Test equals
        assertEquals(modification1, modification1, "Object should be equal to itself");
        assertEquals(modification1, modification2, "Objects with same factor should be equal");
        assertNotEquals(
                modification1, modification3, "Objects with different factors should not be equal");
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
        int factor = 42;
        IntegerMultiplyModification modification = new IntegerMultiplyModification(factor);
        String toString = modification.toString();

        assertEquals("IntegerMultiplyModification{factor=" + factor + "}", toString);
    }
}
