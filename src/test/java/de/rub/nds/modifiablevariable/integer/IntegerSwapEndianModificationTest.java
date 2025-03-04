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

public class IntegerSwapEndianModificationTest {

    private ModifiableInteger modifiableInteger;

    @BeforeEach
    public void setUp() {
        modifiableInteger = new ModifiableInteger();
    }

    @Test
    public void testSwapEndianModification() {
        int originalValue = 0x12345678;
        modifiableInteger.setOriginalValue(originalValue);

        IntegerSwapEndianModification modification = new IntegerSwapEndianModification();
        modifiableInteger.setModifications(modification);

        int expected = Integer.reverseBytes(originalValue); // 0x78563412
        int result = modifiableInteger.getValue();

        assertEquals(expected, result);
        assertEquals(0x78563412, result);
    }

    @Test
    public void testSwapEndianWithSymmetricValue() {
        int originalValue = 0x12344321; // Value that reads the same after swapping middle bytes
        modifiableInteger.setOriginalValue(originalValue);

        IntegerSwapEndianModification modification = new IntegerSwapEndianModification();
        modifiableInteger.setModifications(modification);

        int expected = Integer.reverseBytes(originalValue);
        int result = modifiableInteger.getValue();

        assertEquals(expected, result);
    }

    @Test
    public void testSwapEndianWithZero() {
        int originalValue = 0;
        modifiableInteger.setOriginalValue(originalValue);

        IntegerSwapEndianModification modification = new IntegerSwapEndianModification();
        modifiableInteger.setModifications(modification);

        int expected = 0; // Swapping bytes of 0 is still 0
        int result = modifiableInteger.getValue();

        assertEquals(expected, result);
    }

    @Test
    public void testSwapEndianWithNullInput() {
        modifiableInteger.setOriginalValue(null);

        IntegerSwapEndianModification modification = new IntegerSwapEndianModification();
        modifiableInteger.setModifications(modification);

        Integer result = modifiableInteger.getValue();

        assertNull(result);
    }

    @Test
    public void testSwapEndianWithMaxValue() {
        int originalValue = Integer.MAX_VALUE;
        modifiableInteger.setOriginalValue(originalValue);

        IntegerSwapEndianModification modification = new IntegerSwapEndianModification();
        modifiableInteger.setModifications(modification);

        int expected = Integer.reverseBytes(originalValue);
        int result = modifiableInteger.getValue();

        assertEquals(expected, result);
    }

    @Test
    public void testSwapEndianWithMinValue() {
        int originalValue = Integer.MIN_VALUE;
        modifiableInteger.setOriginalValue(originalValue);

        IntegerSwapEndianModification modification = new IntegerSwapEndianModification();
        modifiableInteger.setModifications(modification);

        int expected = Integer.reverseBytes(originalValue);
        int result = modifiableInteger.getValue();

        assertEquals(expected, result);
    }

    @Test
    public void testCreateCopy() {
        IntegerSwapEndianModification modification = new IntegerSwapEndianModification();
        IntegerSwapEndianModification copy = modification.createCopy();

        // In this implementation, equals() checks for the same class type
        // but doesn't check for instance equality, so we don't use assertNotEquals
        assertEquals(modification.getClass(), copy.getClass(), "Copy should be the same class");
        assertEquals(modification.hashCode(), copy.hashCode(), "Hash codes should be equal");
        assertEquals(modification, copy, "Objects should be equal");

        // We can verify it's a copy by checking they're not the same reference
        assertNotSame(modification, copy, "Copy should not be the same reference");
    }

    @Test
    public void testEqualsAndHashCode() {
        IntegerSwapEndianModification modification1 = new IntegerSwapEndianModification();
        IntegerSwapEndianModification modification2 = new IntegerSwapEndianModification();
        IntegerAddModification differentModification = new IntegerAddModification(1);

        // Test equals
        assertEquals(modification1, modification2, "Equal objects should be equal");
        assertEquals(modification1, modification1, "Object should be equal to itself");
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
    }

    @Test
    public void testToString() {
        IntegerSwapEndianModification modification = new IntegerSwapEndianModification();
        String toString = modification.toString();

        assertEquals("IntegerSwapEndianModification{}", toString);
    }

    @Test
    public void testApplyMultipleTimes() {
        int originalValue = 0x12345678;
        modifiableInteger.setOriginalValue(originalValue);

        IntegerSwapEndianModification modification = new IntegerSwapEndianModification();
        modifiableInteger.setModifications(modification);

        int result1 = modifiableInteger.getValue(); // Apply once
        assertEquals(0x78563412, result1);

        // Apply twice (should be back to original)
        ModifiableInteger modifiableInteger2 = new ModifiableInteger(result1);
        modifiableInteger2.setModifications(modification);
        int result2 = modifiableInteger2.getValue();

        assertEquals(originalValue, result2);
    }
}
