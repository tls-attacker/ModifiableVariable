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

public class IntegerShiftLeftModificationTest {

    private ModifiableInteger modifiableInteger;
    private int originalValue;

    @BeforeEach
    public void setUp() {
        modifiableInteger = new ModifiableInteger();
        originalValue = 123456;
        modifiableInteger.setOriginalValue(originalValue);
    }

    @Test
    public void testShiftLeftModification() {
        int shift = 4;
        IntegerShiftLeftModification modification = new IntegerShiftLeftModification(shift);
        modifiableInteger.setModifications(modification);

        int expected = originalValue << shift;
        int result = modifiableInteger.getValue();

        assertEquals(expected, result);
    }

    @Test
    public void testShiftLeftWithZeroShift() {
        int shift = 0;
        IntegerShiftLeftModification modification = new IntegerShiftLeftModification(shift);
        modifiableInteger.setModifications(modification);

        int expected = originalValue; // Shifting by 0 should return the same value
        int result = modifiableInteger.getValue();

        assertEquals(expected, result);
    }

    @Test
    public void testShiftLeftWithMaxShift() {
        int shift = 31; // Max shift for int is 31 bits
        IntegerShiftLeftModification modification = new IntegerShiftLeftModification(shift);
        modifiableInteger.setModifications(modification);

        int expected = originalValue << shift;
        int result = modifiableInteger.getValue();

        assertEquals(expected, result);
    }

    @Test
    public void testShiftLeftWithNullInput() {
        modifiableInteger.setOriginalValue(null);

        int shift = 4;
        IntegerShiftLeftModification modification = new IntegerShiftLeftModification(shift);
        modifiableInteger.setModifications(modification);

        assertNull(modifiableInteger.getValue());
    }

    @Test
    public void testShiftLeftGetterAndSetter() {
        IntegerShiftLeftModification modification = new IntegerShiftLeftModification(5);

        int shift = 15;
        modification.setShift(shift);

        assertEquals(shift, modification.getShift());
    }

    @Test
    public void testCopyConstructor() {
        int shift = 7;
        IntegerShiftLeftModification original = new IntegerShiftLeftModification(shift);
        IntegerShiftLeftModification copy = new IntegerShiftLeftModification(original);

        assertEquals(original.getShift(), copy.getShift());
        assertEquals(shift, copy.getShift());
    }

    @Test
    public void testCreateCopy() {
        int shift = 7;
        IntegerShiftLeftModification original = new IntegerShiftLeftModification(shift);
        IntegerShiftLeftModification copy = original.createCopy();

        assertEquals(original.getShift(), copy.getShift());
        assertEquals(original, copy, "Objects with the same shift should be equal");
        assertNotSame(original, copy, "Copy should not be the same reference");
    }

    @Test
    public void testEqualsAndHashCode() {
        int shift1 = 5;
        int shift2 = 5;
        int shift3 = 10;

        IntegerShiftLeftModification modification1 = new IntegerShiftLeftModification(shift1);
        IntegerShiftLeftModification modification2 = new IntegerShiftLeftModification(shift2);
        IntegerShiftLeftModification modification3 = new IntegerShiftLeftModification(shift3);
        IntegerShiftRightModification differentModification =
                new IntegerShiftRightModification(shift1);

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
    public void testToString() {
        int shift = 42;
        IntegerShiftLeftModification modification = new IntegerShiftLeftModification(shift);
        String toString = modification.toString();

        assertEquals("IntegerShiftLeftModification{shift=" + shift + "}", toString);
    }
}
