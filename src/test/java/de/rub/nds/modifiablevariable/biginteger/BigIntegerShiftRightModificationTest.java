/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.biginteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigInteger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BigIntegerShiftRightModificationTest {

    private ModifiableBigInteger modifiableBigInteger;
    private BigInteger originalValue;

    @BeforeEach
    public void setUp() {
        modifiableBigInteger = new ModifiableBigInteger();
        originalValue = new BigInteger("12345678901234567890");
        modifiableBigInteger.setOriginalValue(originalValue);
    }

    @Test
    public void testShiftRightModification() {
        int shift = 8;
        BigIntegerShiftRightModification modification = new BigIntegerShiftRightModification(shift);
        modifiableBigInteger.setModifications(modification);

        BigInteger expected = originalValue.shiftRight(shift);
        BigInteger result = modifiableBigInteger.getValue();

        assertEquals(expected, result);
    }

    @Test
    public void testShiftRightNegativeNumber() {
        BigInteger negativeValue = new BigInteger("-987654321");
        modifiableBigInteger.setOriginalValue(negativeValue);

        int shift = 4;
        BigIntegerShiftRightModification modification = new BigIntegerShiftRightModification(shift);
        modifiableBigInteger.setModifications(modification);

        BigInteger expected = negativeValue.shiftRight(shift);
        BigInteger result = modifiableBigInteger.getValue();

        assertEquals(expected, result);
    }

    @Test
    public void testShiftRightWithZeroShift() {
        int shift = 0;
        BigIntegerShiftRightModification modification = new BigIntegerShiftRightModification(shift);
        modifiableBigInteger.setModifications(modification);

        BigInteger expected = originalValue; // Shifting by 0 should return the same value
        BigInteger result = modifiableBigInteger.getValue();

        assertEquals(expected, result);
    }

    @Test
    public void testShiftRightWithLargeShift() {
        // First set a large value to have meaningful results after a large right shift
        BigInteger largeValue = originalValue.shiftLeft(200);
        modifiableBigInteger.setOriginalValue(largeValue);

        int shift = 128;
        BigIntegerShiftRightModification modification = new BigIntegerShiftRightModification(shift);
        modifiableBigInteger.setModifications(modification);

        BigInteger expected = largeValue.shiftRight(shift);
        BigInteger result = modifiableBigInteger.getValue();

        assertEquals(expected, result);
    }

    @Test
    public void testShiftRightWithNullInput() {
        modifiableBigInteger.setOriginalValue(null);

        int shift = 10;
        BigIntegerShiftRightModification modification = new BigIntegerShiftRightModification(shift);
        modifiableBigInteger.setModifications(modification);

        BigInteger expected = null;
        BigInteger result = modifiableBigInteger.getValue();

        assertEquals(expected, result);
    }

    @Test
    public void testEqualsAndHashCode() {
        int shift = 15;
        BigIntegerShiftRightModification modification1 =
                new BigIntegerShiftRightModification(shift);
        BigIntegerShiftRightModification modification2 =
                new BigIntegerShiftRightModification(shift);
        BigIntegerShiftRightModification modification3 =
                new BigIntegerShiftRightModification(shift + 1);

        // Test equals
        assertEquals(modification1, modification2);
        assertNotEquals(modification1, modification3);

        // Test hashCode
        assertEquals(modification1.hashCode(), modification2.hashCode());
        assertNotEquals(modification1.hashCode(), modification3.hashCode());
    }

    @Test
    public void testEqualsComprehensive() {
        // Same instance equality (reflexivity)
        BigIntegerShiftRightModification modification = new BigIntegerShiftRightModification(8);
        assertTrue(modification.equals(modification));

        // Equality with same shift value (symmetry)
        BigIntegerShiftRightModification modification1 = new BigIntegerShiftRightModification(42);
        BigIntegerShiftRightModification modification2 = new BigIntegerShiftRightModification(42);
        assertTrue(modification1.equals(modification2));
        assertTrue(modification2.equals(modification1));

        // Inequality with different shift values
        BigIntegerShiftRightModification modification3 = new BigIntegerShiftRightModification(24);
        assertFalse(modification1.equals(modification3));
        assertFalse(modification3.equals(modification1));

        // Consistency: Should return same result on multiple invocations
        assertTrue(modification1.equals(modification2));
        assertTrue(modification1.equals(modification2));

        // Null comparison
        assertFalse(modification1.equals(null));

        // Test with different modification types
        BigIntegerAddModification addMod = new BigIntegerAddModification(BigInteger.ONE);
        assertFalse(modification1.equals(addMod));

        // Test with similar but different class type (shift left vs shift right)
        BigIntegerShiftLeftModification shiftLeftMod = new BigIntegerShiftLeftModification(42);
        assertFalse(modification1.equals(shiftLeftMod));

        // Test with different object types
        assertFalse(modification1.equals("Not a modification"));
        assertFalse(modification1.equals(Integer.valueOf(42)));

        // Transitivity
        BigIntegerShiftRightModification a = new BigIntegerShiftRightModification(100);
        BigIntegerShiftRightModification b = new BigIntegerShiftRightModification(100);
        BigIntegerShiftRightModification c = new BigIntegerShiftRightModification(100);
        assertTrue(a.equals(b));
        assertTrue(b.equals(c));
        assertTrue(a.equals(c));

        // Test after state change
        BigIntegerShiftRightModification mutable = new BigIntegerShiftRightModification(1);
        BigIntegerShiftRightModification other = new BigIntegerShiftRightModification(1);
        assertTrue(mutable.equals(other));

        mutable.setShift(5);
        assertFalse(mutable.equals(other));

        other.setShift(5);
        assertTrue(mutable.equals(other));
    }

    @Test
    public void testGetterAndSetter() {
        BigIntegerShiftRightModification modification = new BigIntegerShiftRightModification(5);

        int shift = 25;
        modification.setShift(shift);

        assertEquals(shift, modification.getShift());
    }

    @Test
    public void testCreateCopy() {
        int shift = 12;
        BigIntegerShiftRightModification original = new BigIntegerShiftRightModification(shift);
        BigIntegerShiftRightModification copy = original.createCopy();

        // Verify copy has same properties
        assertEquals(original.getShift(), copy.getShift());
        assertEquals(original, copy);

        // Verify it's a different instance
        assertNotEquals(System.identityHashCode(original), System.identityHashCode(copy));

        // Modify copy and verify original is unchanged
        copy.setShift(42);
        assertEquals(shift, original.getShift());
        assertEquals(42, copy.getShift());
        assertNotEquals(original, copy);
    }

    @Test
    public void testToString() {
        int shift = 7;
        BigIntegerShiftRightModification modification = new BigIntegerShiftRightModification(shift);
        String toString = modification.toString();

        assertTrue(toString.contains("BigIntegerShiftRightModification"));
        assertTrue(toString.contains("shift=" + shift));

        // Test with different shift value
        modification.setShift(99);
        String newToString = modification.toString();
        assertTrue(newToString.contains("shift=99"));
    }
}
