/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.biginteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import de.rub.nds.modifiablevariable.VariableModification;
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
        modifiableBigInteger.setModification(modification);

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
        modifiableBigInteger.setModification(modification);

        BigInteger expected = negativeValue.shiftLeft(shift);
        BigInteger result = modifiableBigInteger.getValue();

        assertEquals(expected, result);
    }

    @Test
    public void testShiftLeftWithZeroShift() {
        int shift = 0;
        BigIntegerShiftLeftModification modification = new BigIntegerShiftLeftModification(shift);
        modifiableBigInteger.setModification(modification);

        BigInteger expected = originalValue; // Shifting by 0 should return the same value
        BigInteger result = modifiableBigInteger.getValue();

        assertEquals(expected, result);
    }

    @Test
    public void testShiftLeftWithLargeShift() {
        int shift = 128;
        BigIntegerShiftLeftModification modification = new BigIntegerShiftLeftModification(shift);
        modifiableBigInteger.setModification(modification);

        BigInteger expected = originalValue.shiftLeft(shift);
        BigInteger result = modifiableBigInteger.getValue();

        assertEquals(expected, result);
    }

    @Test
    public void testShiftLeftWithNullInput() {
        modifiableBigInteger.setOriginalValue(null);

        int shift = 10;
        BigIntegerShiftLeftModification modification = new BigIntegerShiftLeftModification(shift);
        modifiableBigInteger.setModification(modification);

        BigInteger expected = BigInteger.ZERO.shiftLeft(shift); // Should treat null as ZERO
        BigInteger result = modifiableBigInteger.getValue();

        assertEquals(expected, result);
    }

    @Test
    public void testGetModifiedCopy() {
        int shift = 5;
        BigIntegerShiftLeftModification modification = new BigIntegerShiftLeftModification(shift);

        VariableModification<BigInteger> modifiedCopy = modification.getModifiedCopy();

        assertNotNull(modifiedCopy);
        assertTrue(modifiedCopy instanceof BigIntegerShiftLeftModification);

        BigIntegerShiftLeftModification castCopy = (BigIntegerShiftLeftModification) modifiedCopy;
        assertTrue(castCopy.getShift() > shift); // Should have a larger shift
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
    public void testGetterAndSetter() {
        BigIntegerShiftLeftModification modification = new BigIntegerShiftLeftModification();

        int shift = 25;
        modification.setShift(shift);

        assertEquals(shift, modification.getShift());
    }
}
