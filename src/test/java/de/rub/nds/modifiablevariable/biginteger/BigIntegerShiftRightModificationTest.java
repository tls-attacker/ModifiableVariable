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
        modifiableBigInteger.setModification(modification);

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
        modifiableBigInteger.setModification(modification);

        BigInteger expected = negativeValue.shiftRight(shift);
        BigInteger result = modifiableBigInteger.getValue();

        assertEquals(expected, result);
    }

    @Test
    public void testShiftRightWithZeroShift() {
        int shift = 0;
        BigIntegerShiftRightModification modification = new BigIntegerShiftRightModification(shift);
        modifiableBigInteger.setModification(modification);

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
        modifiableBigInteger.setModification(modification);

        BigInteger expected = largeValue.shiftRight(shift);
        BigInteger result = modifiableBigInteger.getValue();

        assertEquals(expected, result);
    }

    @Test
    public void testShiftRightWithNullInput() {
        modifiableBigInteger.setOriginalValue(null);

        int shift = 10;
        BigIntegerShiftRightModification modification = new BigIntegerShiftRightModification(shift);
        modifiableBigInteger.setModification(modification);

        BigInteger expected = BigInteger.ZERO.shiftRight(shift); // Should treat null as ZERO
        BigInteger result = modifiableBigInteger.getValue();

        assertEquals(expected, result);
    }

    @Test
    public void testGetModifiedCopy() {
        int shift = 5;
        BigIntegerShiftRightModification modification = new BigIntegerShiftRightModification(shift);

        VariableModification<BigInteger> modifiedCopy = modification.getModifiedCopy();

        assertNotNull(modifiedCopy);
        assertTrue(modifiedCopy instanceof BigIntegerShiftRightModification);

        BigIntegerShiftRightModification castCopy = (BigIntegerShiftRightModification) modifiedCopy;
        assertTrue(castCopy.getShift() > shift); // Should have a larger shift
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
    public void testGetterAndSetter() {
        BigIntegerShiftRightModification modification = new BigIntegerShiftRightModification();

        int shift = 25;
        modification.setShift(shift);

        assertEquals(shift, modification.getShift());
    }
}
