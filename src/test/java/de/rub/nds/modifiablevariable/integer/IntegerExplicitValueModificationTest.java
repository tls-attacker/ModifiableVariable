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

public class IntegerExplicitValueModificationTest {

    private ModifiableInteger modifiableInteger;
    private int originalValue;

    @BeforeEach
    public void setUp() {
        modifiableInteger = new ModifiableInteger();
        originalValue = 123;
        modifiableInteger.setOriginalValue(originalValue);
    }

    @Test
    public void testExplicitValueModification() {
        int explicitValue = 999;
        IntegerExplicitValueModification modification =
                new IntegerExplicitValueModification(explicitValue);
        modifiableInteger.setModifications(modification);

        int result = modifiableInteger.getValue();

        assertEquals(explicitValue, result);
        assertEquals(999, result);
    }

    @Test
    public void testExplicitValueZero() {
        int explicitValue = 0;
        IntegerExplicitValueModification modification =
                new IntegerExplicitValueModification(explicitValue);
        modifiableInteger.setModifications(modification);

        int result = modifiableInteger.getValue();

        assertEquals(explicitValue, result);
        assertEquals(0, result);
    }

    @Test
    public void testExplicitValueNegative() {
        int explicitValue = -500;
        IntegerExplicitValueModification modification =
                new IntegerExplicitValueModification(explicitValue);
        modifiableInteger.setModifications(modification);

        int result = modifiableInteger.getValue();

        assertEquals(explicitValue, result);
        assertEquals(-500, result);
    }

    @Test
    public void testExplicitValueWithNullInput() {
        modifiableInteger.setOriginalValue(null);

        int explicitValue = 999;
        IntegerExplicitValueModification modification =
                new IntegerExplicitValueModification(explicitValue);
        modifiableInteger.setModifications(modification);
        assertNull(modifiableInteger.getValue());
    }

    @Test
    public void testExplicitValueMaxValue() {
        int explicitValue = Integer.MAX_VALUE;
        IntegerExplicitValueModification modification =
                new IntegerExplicitValueModification(explicitValue);
        modifiableInteger.setModifications(modification);

        int result = modifiableInteger.getValue();

        assertEquals(explicitValue, result);
        assertEquals(Integer.MAX_VALUE, result);
    }

    @Test
    public void testExplicitValueMinValue() {
        int explicitValue = Integer.MIN_VALUE;
        IntegerExplicitValueModification modification =
                new IntegerExplicitValueModification(explicitValue);
        modifiableInteger.setModifications(modification);

        int result = modifiableInteger.getValue();

        assertEquals(explicitValue, result);
        assertEquals(Integer.MIN_VALUE, result);
    }

    @Test
    public void testGetExplicitValue() {
        int explicitValue = 42;
        IntegerExplicitValueModification modification =
                new IntegerExplicitValueModification(explicitValue);

        assertEquals(explicitValue, modification.getExplicitValue());
    }

    @Test
    public void testSetExplicitValue() {
        IntegerExplicitValueModification modification = new IntegerExplicitValueModification(10);

        int newExplicitValue = 42;
        modification.setExplicitValue(newExplicitValue);

        assertEquals(newExplicitValue, modification.getExplicitValue());
    }

    @Test
    public void testCopyConstructor() {
        int explicitValue = 42;
        IntegerExplicitValueModification original =
                new IntegerExplicitValueModification(explicitValue);
        IntegerExplicitValueModification copy = new IntegerExplicitValueModification(original);

        assertEquals(original.getExplicitValue(), copy.getExplicitValue());
    }

    @Test
    public void testCreateCopy() {
        int explicitValue = 42;
        IntegerExplicitValueModification original =
                new IntegerExplicitValueModification(explicitValue);
        IntegerExplicitValueModification copy = original.createCopy();

        assertEquals(original.getExplicitValue(), copy.getExplicitValue());
        assertEquals(original, copy, "Objects with the same explicitValue should be equal");
        assertNotSame(original, copy, "Copy should not be the same reference");
    }

    @Test
    public void testEqualsAndHashCode() {
        int explicitValue1 = 42;
        int explicitValue2 = 42;
        int explicitValue3 = 100;

        IntegerExplicitValueModification modification1 =
                new IntegerExplicitValueModification(explicitValue1);
        IntegerExplicitValueModification modification2 =
                new IntegerExplicitValueModification(explicitValue2);
        IntegerExplicitValueModification modification3 =
                new IntegerExplicitValueModification(explicitValue3);
        IntegerAddModification differentModification = new IntegerAddModification(explicitValue1);

        // Test equals
        assertEquals(modification1, modification1, "Object should be equal to itself");
        assertEquals(
                modification1, modification2, "Objects with same explicitValue should be equal");
        assertNotEquals(
                modification1,
                modification3,
                "Objects with different explicitValues should not be equal");
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
        int explicitValue = 42;
        IntegerExplicitValueModification modification =
                new IntegerExplicitValueModification(explicitValue);
        String toString = modification.toString();

        assertEquals(
                "IntegerExplicitValueModification{explicitValue=" + explicitValue + "}", toString);
    }
}
