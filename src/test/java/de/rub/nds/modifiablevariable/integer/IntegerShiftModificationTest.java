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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IntegerShiftModificationTest {

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

        org.junit.jupiter.api.Assertions.assertThrows(
                NullPointerException.class,
                () -> {
                    modifiableInteger.getValue();
                });
    }

    @Test
    public void testShiftLeftEqualsAndHashCode() {
        int shift = 10;
        IntegerShiftLeftModification modification1 = new IntegerShiftLeftModification(shift);
        IntegerShiftLeftModification modification2 = new IntegerShiftLeftModification(shift);
        IntegerShiftLeftModification modification3 = new IntegerShiftLeftModification(shift + 1);

        // Test equals
        assertEquals(modification1, modification2);
        assertNotEquals(modification1, modification3);

        // Test hashCode
        assertEquals(modification1.hashCode(), modification2.hashCode());
        assertNotEquals(modification1.hashCode(), modification3.hashCode());
    }

    @Test
    public void testShiftLeftGetterAndSetter() {
        IntegerShiftLeftModification modification = new IntegerShiftLeftModification();

        int shift = 15;
        modification.setShift(shift);

        assertEquals(shift, modification.getShift());
    }

    @Test
    public void testShiftRightModification() {
        int shift = 4;
        IntegerShiftRightModification modification = new IntegerShiftRightModification(shift);
        modifiableInteger.setModifications(modification);

        int expected = originalValue >> shift;
        int result = modifiableInteger.getValue();

        assertEquals(expected, result);
    }

    @Test
    public void testShiftRightWithNegativeNumber() {
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
    public void testShiftRightWithZeroShift() {
        int shift = 0;
        IntegerShiftRightModification modification = new IntegerShiftRightModification(shift);
        modifiableInteger.setModifications(modification);

        int expected = originalValue; // Shifting by 0 should return the same value
        int result = modifiableInteger.getValue();

        assertEquals(expected, result);
    }

    @Test
    public void testShiftRightWithMaxShift() {
        int shift = 31; // Max shift for int is 31 bits
        IntegerShiftRightModification modification = new IntegerShiftRightModification(shift);
        modifiableInteger.setModifications(modification);

        int expected = originalValue >> shift;
        int result = modifiableInteger.getValue();

        assertEquals(expected, result);
    }

    @Test
    public void testShiftRightWithNullInput() {
        modifiableInteger.setOriginalValue(null);

        int shift = 4;
        IntegerShiftRightModification modification = new IntegerShiftRightModification(shift);
        modifiableInteger.setModifications(modification);

        org.junit.jupiter.api.Assertions.assertThrows(
                NullPointerException.class,
                () -> {
                    modifiableInteger.getValue();
                });
    }

    @Test
    public void testShiftRightEqualsAndHashCode() {
        int shift = 10;
        IntegerShiftRightModification modification1 = new IntegerShiftRightModification(shift);
        IntegerShiftRightModification modification2 = new IntegerShiftRightModification(shift);
        IntegerShiftRightModification modification3 = new IntegerShiftRightModification(shift + 1);

        // Test equals
        assertEquals(modification1, modification2);
        assertNotEquals(modification1, modification3);

        // Test hashCode
        assertEquals(modification1.hashCode(), modification2.hashCode());
        assertNotEquals(modification1.hashCode(), modification3.hashCode());
    }

    @Test
    public void testShiftRightGetterAndSetter() {
        IntegerShiftRightModification modification = new IntegerShiftRightModification();

        int shift = 15;
        modification.setShift(shift);

        assertEquals(shift, modification.getShift());
    }
}
