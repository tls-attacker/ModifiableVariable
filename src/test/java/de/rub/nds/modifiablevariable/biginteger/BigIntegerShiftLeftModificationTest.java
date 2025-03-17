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

public class BigIntegerShiftLeftModificationTest {

    private ModifiableBigInteger modifiableBigInteger;
    private BigInteger originalValue;

    @BeforeEach
    public void setUp() {
        modifiableBigInteger = new ModifiableBigInteger();
        originalValue = new BigInteger("12345678901234567890");
        modifiableBigInteger.setOriginalValue(originalValue);
    }

    @Test
    public void testShiftLeftModification() {
        int shift = 8;
        BigIntegerShiftLeftModification modification = new BigIntegerShiftLeftModification(shift);
        modifiableBigInteger.setModifications(modification);

        BigInteger expected = originalValue.shiftLeft(shift);
        BigInteger result = modifiableBigInteger.getValue();

        assertEquals(expected, result);
    }

    @Test
    public void testShiftLeftNegativeNumber() {
        BigInteger negativeValue = new BigInteger("-987654321");
        modifiableBigInteger.setOriginalValue(negativeValue);

        int shift = 4;
        BigIntegerShiftLeftModification modification = new BigIntegerShiftLeftModification(shift);
        modifiableBigInteger.setModifications(modification);

        BigInteger expected = negativeValue.shiftLeft(shift);
        BigInteger result = modifiableBigInteger.getValue();

        assertEquals(expected, result);
    }

    @Test
    public void testShiftLeftWithZeroShift() {
        int shift = 0;
        BigIntegerShiftLeftModification modification = new BigIntegerShiftLeftModification(shift);
        modifiableBigInteger.setModifications(modification);

        BigInteger expected = originalValue; // Shifting by 0 should return the same value
        BigInteger result = modifiableBigInteger.getValue();

        assertEquals(expected, result);
    }

    @Test
    public void testShiftLeftWithLargeShift() {
        int shift = 128;
        BigIntegerShiftLeftModification modification = new BigIntegerShiftLeftModification(shift);
        modifiableBigInteger.setModifications(modification);

        BigInteger expected = originalValue.shiftLeft(shift);
        BigInteger result = modifiableBigInteger.getValue();

        assertEquals(expected, result);
    }

    @Test
    public void testShiftLeftWithNullInput() {
        modifiableBigInteger.setOriginalValue(null);

        int shift = 10;
        BigIntegerShiftLeftModification modification = new BigIntegerShiftLeftModification(shift);
        modifiableBigInteger.setModifications(modification);

        BigInteger expected = null;
        BigInteger result = modifiableBigInteger.getValue();

        assertEquals(expected, result);
    }

    @Test
    public void testEqualsAndHashCode() {
        int shift = 15;
        BigIntegerShiftLeftModification modification1 = new BigIntegerShiftLeftModification(shift);
        BigIntegerShiftLeftModification modification2 = new BigIntegerShiftLeftModification(shift);
        BigIntegerShiftLeftModification modification3 =
                new BigIntegerShiftLeftModification(shift + 1);

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
        BigIntegerShiftLeftModification modification = new BigIntegerShiftLeftModification(8);
        assertTrue(modification.equals(modification));

        // Equality with same shift value (symmetry)
        BigIntegerShiftLeftModification modification1 = new BigIntegerShiftLeftModification(42);
        BigIntegerShiftLeftModification modification2 = new BigIntegerShiftLeftModification(42);
        assertTrue(modification1.equals(modification2));
        assertTrue(modification2.equals(modification1));

        // Inequality with different shift values
        BigIntegerShiftLeftModification modification3 = new BigIntegerShiftLeftModification(24);
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
        BigIntegerShiftRightModification shiftRightMod = new BigIntegerShiftRightModification(42);
        assertFalse(modification1.equals(shiftRightMod));

        // Test with different object types
        assertFalse(modification1.equals("Not a modification"));
        assertFalse(modification1.equals(Integer.valueOf(42)));

        // Transitivity
        BigIntegerShiftLeftModification a = new BigIntegerShiftLeftModification(100);
        BigIntegerShiftLeftModification b = new BigIntegerShiftLeftModification(100);
        BigIntegerShiftLeftModification c = new BigIntegerShiftLeftModification(100);
        assertTrue(a.equals(b));
        assertTrue(b.equals(c));
        assertTrue(a.equals(c));

        // Test after state change
        BigIntegerShiftLeftModification mutable = new BigIntegerShiftLeftModification(1);
        BigIntegerShiftLeftModification other = new BigIntegerShiftLeftModification(1);
        assertTrue(mutable.equals(other));

        mutable.setShift(5);
        assertFalse(mutable.equals(other));

        other.setShift(5);
        assertTrue(mutable.equals(other));
    }

    @Test
    public void testGetterAndSetter() {
        BigIntegerShiftLeftModification modification = new BigIntegerShiftLeftModification(5);

        int shift = 25;
        modification.setShift(shift);

        assertEquals(shift, modification.getShift());
    }

    @Test
    public void testCreateCopy() {
        int shift = 12;
        BigIntegerShiftLeftModification original = new BigIntegerShiftLeftModification(shift);
        BigIntegerShiftLeftModification copy = original.createCopy();

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
        BigIntegerShiftLeftModification modification = new BigIntegerShiftLeftModification(shift);
        String toString = modification.toString();

        assertTrue(toString.contains("BigIntegerShiftLeftModification"));
        assertTrue(toString.contains("shift=" + shift));

        // Test with different shift value
        modification.setShift(99);
        String newToString = modification.toString();
        assertTrue(newToString.contains("shift=99"));
    }
}
