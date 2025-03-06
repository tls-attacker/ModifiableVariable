/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.biginteger;

import static org.junit.jupiter.api.Assertions.*;

import de.rub.nds.modifiablevariable.VariableModification;
import java.math.BigInteger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BigIntegerModificationTest {

    private ModifiableBigInteger start;

    private BigInteger expectedResult, result;

    @BeforeEach
    public void setUp() {
        start = new ModifiableBigInteger();
        start.setOriginalValue(BigInteger.TEN);
        expectedResult = null;
        result = null;
    }

    /** Test of add method, of class BigIntegerAddModification. */
    @Test
    public void testAdd() {
        VariableModification<BigInteger> modifier = new BigIntegerAddModification(BigInteger.ONE);
        start.setModifications(modifier);
        expectedResult = new BigInteger("11");
        result = start.getValue();
        assertEquals(expectedResult, result);
        assertNotSame(expectedResult, result);
        assertEquals(BigInteger.TEN, start.getOriginalValue());
    }

    /** Test of sub method, of class BigIntegerSubtractModification. */
    @Test
    public void testSub() {
        VariableModification<BigInteger> modifier =
                new BigIntegerSubtractModification(BigInteger.ONE);
        start.setModifications(modifier);
        expectedResult = new BigInteger("9");
        result = start.getValue();
        assertEquals(expectedResult, result);
        assertNotSame(expectedResult, result);
        assertEquals(BigInteger.TEN, start.getOriginalValue());
    }

    /** Test of xor method, of class BigIntegerXorModification. */
    @Test
    public void testXor() {
        VariableModification<BigInteger> modifier =
                new BigIntegerXorModification(new BigInteger("2"));
        start.setModifications(modifier);
        expectedResult = new BigInteger("8");
        result = start.getValue();
        assertEquals(expectedResult, result);
        assertNotSame(expectedResult, result);
        assertEquals(BigInteger.TEN, start.getOriginalValue());
    }

    /** Test of explicitValue method, of class BigIntegerExplicitValueModification. */
    @Test
    public void testExplicitValue() {
        VariableModification<BigInteger> modifier =
                new BigIntegerExplicitValueModification(new BigInteger("7"));
        start.setModifications(modifier);
        expectedResult = new BigInteger("7");
        result = start.getValue();
        assertEquals(expectedResult, result);
        assertNotSame(expectedResult, result);
        assertEquals(BigInteger.TEN, start.getOriginalValue());
    }

    /** Test of add method, of class BigIntegerAddModification. */
    @Test
    public void testIsOriginalValueModified() {
        assertFalse(start.isOriginalValueModified());
        VariableModification<BigInteger> modifier = new BigIntegerAddModification(BigInteger.ZERO);
        start.setModifications(modifier);
        assertFalse(start.isOriginalValueModified());
        modifier = new BigIntegerAddModification(BigInteger.ONE);
        start.setModifications(modifier);
        assertTrue(start.isOriginalValueModified());
    }

    @Test
    public void testShiftLeft() {
        VariableModification<BigInteger> modifier = new BigIntegerShiftLeftModification(2);
        start.setModifications(modifier);
        expectedResult = new BigInteger("40");
        result = start.getValue();
        assertEquals(expectedResult, result);
        assertNotSame(expectedResult, result);
        assertEquals(BigInteger.TEN, start.getOriginalValue());
    }

    @Test
    public void testShiftRight() {
        VariableModification<BigInteger> modifier = new BigIntegerShiftRightModification(1);
        start.setModifications(modifier);
        expectedResult = new BigInteger("5");
        result = start.getValue();
        assertEquals(expectedResult, result);
        assertNotSame(expectedResult, result);
        assertEquals(BigInteger.TEN, start.getOriginalValue());
    }
}
