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

class IntegerAddModificationTest {

    private ModifiableInteger modifiableInteger;
    private int originalValue;

    @BeforeEach
    void setUp() {
        modifiableInteger = new ModifiableInteger();
        originalValue = 123;
        modifiableInteger.setOriginalValue(originalValue);
    }

    @Test
    void testAddModification() {
        int summand = 100;
        IntegerAddModification modification = new IntegerAddModification(summand);
        modifiableInteger.setModifications(modification);

        int expected = originalValue + summand;
        int result = modifiableInteger.getValue();

        assertEquals(expected, result);
        assertEquals(223, result);
    }

    @Test
    void testAddZero() {
        int summand = 0;
        IntegerAddModification modification = new IntegerAddModification(summand);
        modifiableInteger.setModifications(modification);

        int expected = originalValue;
        int result = modifiableInteger.getValue();

        assertEquals(expected, result);
    }

    @Test
    void testAddNegative() {
        int summand = -50;
        IntegerAddModification modification = new IntegerAddModification(summand);
        modifiableInteger.setModifications(modification);

        int expected = originalValue + summand;
        int result = modifiableInteger.getValue();

        assertEquals(expected, result);
        assertEquals(73, result);
    }

    @Test
    void testAddWithNullInput() {
        modifiableInteger.setOriginalValue(null);

        int summand = 100;
        IntegerAddModification modification = new IntegerAddModification(summand);
        modifiableInteger.setModifications(modification);
        assertNull(modifiableInteger.getValue());
    }

    @Test
    void testAddMaxValue() {
        modifiableInteger.setOriginalValue(Integer.MAX_VALUE);

        int summand = 1;
        IntegerAddModification modification = new IntegerAddModification(summand);
        modifiableInteger.setModifications(modification);

        int expected = Integer.MAX_VALUE + 1; // Will overflow to Integer.MIN_VALUE
        int result = modifiableInteger.getValue();

        assertEquals(expected, result);
        assertEquals(Integer.MIN_VALUE, result);
    }

    @Test
    void testAddMinValue() {
        modifiableInteger.setOriginalValue(Integer.MIN_VALUE);

        int summand = -1;
        IntegerAddModification modification = new IntegerAddModification(summand);
        modifiableInteger.setModifications(modification);

        int expected = Integer.MIN_VALUE - 1; // Will overflow to Integer.MAX_VALUE
        int result = modifiableInteger.getValue();

        assertEquals(expected, result);
        assertEquals(Integer.MAX_VALUE, result);
    }

    @Test
    void testGetSummand() {
        int summand = 42;
        IntegerAddModification modification = new IntegerAddModification(summand);

        assertEquals(summand, modification.getSummand());
    }

    @Test
    void testSetSummand() {
        IntegerAddModification modification = new IntegerAddModification(10);

        int newSummand = 42;
        modification.setSummand(newSummand);

        assertEquals(newSummand, modification.getSummand());
    }

    @Test
    void testCopyConstructor() {
        int summand = 42;
        IntegerAddModification original = new IntegerAddModification(summand);
        IntegerAddModification copy = new IntegerAddModification(original);

        assertEquals(original.getSummand(), copy.getSummand());
    }

    @Test
    void testCreateCopy() {
        int summand = 42;
        IntegerAddModification original = new IntegerAddModification(summand);
        IntegerAddModification copy = original.createCopy();

        assertEquals(original.getSummand(), copy.getSummand());
        assertEquals(original, copy, "Objects with the same summand should be equal");
        assertNotSame(original, copy, "Copy should not be the same reference");
    }

    @Test
    void testEqualsAndHashCode() {
        int summand1 = 42;
        int summand2 = 42;
        int summand3 = 100;

        IntegerAddModification modification1 = new IntegerAddModification(summand1);
        IntegerAddModification modification2 = new IntegerAddModification(summand2);
        IntegerAddModification modification3 = new IntegerAddModification(summand3);
        IntegerMultiplyModification differentModification =
                new IntegerMultiplyModification(summand1);

        // Test equals
        assertEquals(modification1, modification1, "Object should be equal to itself");
        assertEquals(modification1, modification2, "Objects with same summand should be equal");
        assertNotEquals(
                modification1,
                modification3,
                "Objects with different summands should not be equal");
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
    void testToString() {
        int summand = 42;
        IntegerAddModification modification = new IntegerAddModification(summand);
        String toString = modification.toString();

        assertEquals("IntegerAddModification{summand=" + summand + "}", toString);
    }
}
