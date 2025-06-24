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

class IntegerShiftRightModificationTest {

    private ModifiableInteger modifiableInteger;
    private int originalValue;

    @BeforeEach
    void setUp() {
        modifiableInteger = new ModifiableInteger();
        originalValue = 123456;
        modifiableInteger.setOriginalValue(originalValue);
    }

    @Test
    void testShiftRightModification() {
        int shift = 4;
        IntegerShiftRightModification modification = new IntegerShiftRightModification(shift);
        modifiableInteger.setModifications(modification);

        int expected = originalValue >> shift;
        int result = modifiableInteger.getValue();

        assertEquals(expected, result);
    }

    @Test
    void testShiftRightWithNegativeNumber() {
        int negativeValue = -98765;
        modifiableInteger.setOriginalValue(negativeValue);

        int shift = 4;
        IntegerShiftRightModification modification = new IntegerShiftRightModification(shift);
        modifiableInteger.setModifications(modification);

        int expected = negativeValue >> shift;
        int result = modifiableInteger.getValue();

        assertEquals(expected, result);
    }

    @Test
    void testShiftRightWithZeroShift() {
        int shift = 0;
        IntegerShiftRightModification modification = new IntegerShiftRightModification(shift);
        modifiableInteger.setModifications(modification);

        int expected = originalValue; // Shifting by 0 should return the same value
        int result = modifiableInteger.getValue();

        assertEquals(expected, result);
    }

    @Test
    void testShiftRightWithMaxShift() {
        int shift = 31; // Max shift for int is 31 bits
        IntegerShiftRightModification modification = new IntegerShiftRightModification(shift);
        modifiableInteger.setModifications(modification);

        int expected = originalValue >> shift;
        int result = modifiableInteger.getValue();

        assertEquals(expected, result);
    }

    @Test
    void testShiftRightWithNullInput() {
        modifiableInteger.setOriginalValue(null);

        int shift = 4;
        IntegerShiftRightModification modification = new IntegerShiftRightModification(shift);
        modifiableInteger.setModifications(modification);
        assertNull(modifiableInteger.getValue());
    }

    @Test
    void testShiftRightGetterAndSetter() {
        IntegerShiftRightModification modification = new IntegerShiftRightModification(5);

        int shift = 15;
        modification.setShift(shift);

        assertEquals(shift, modification.getShift());
    }

    @Test
    void testCopyConstructor() {
        int shift = 7;
        IntegerShiftRightModification original = new IntegerShiftRightModification(shift);
        IntegerShiftRightModification copy = new IntegerShiftRightModification(original);

        assertEquals(original.getShift(), copy.getShift());
        assertEquals(shift, copy.getShift());
    }

    @Test
    void testCreateCopy() {
        int shift = 7;
        IntegerShiftRightModification original = new IntegerShiftRightModification(shift);
        IntegerShiftRightModification copy = original.createCopy();

        assertEquals(original.getShift(), copy.getShift());
        assertEquals(original, copy, "Objects with the same shift should be equal");
        assertNotSame(original, copy, "Copy should not be the same reference");
    }

    @Test
    void testEqualsAndHashCode() {
        int shift1 = 5;
        int shift2 = 5;
        int shift3 = 10;

        IntegerShiftRightModification modification1 = new IntegerShiftRightModification(shift1);
        IntegerShiftRightModification modification2 = new IntegerShiftRightModification(shift2);
        IntegerShiftRightModification modification3 = new IntegerShiftRightModification(shift3);
        IntegerShiftLeftModification differentModification =
                new IntegerShiftLeftModification(shift1);

        // Test equals
        assertEquals(modification1, modification1, "Object should be equal to itself");
        assertEquals(modification1, modification2, "Objects with same shift should be equal");
        assertNotEquals(
                modification1, modification3, "Objects with different shifts should not be equal");
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
        int shift = 42;
        IntegerShiftRightModification modification = new IntegerShiftRightModification(shift);
        String toString = modification.toString();

        assertEquals("IntegerShiftRightModification{shift=" + shift + "}", toString);
    }
}
