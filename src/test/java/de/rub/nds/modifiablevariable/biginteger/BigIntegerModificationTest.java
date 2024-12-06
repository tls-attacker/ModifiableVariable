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

    /** Test of add method, of class BigIntegerModificationFactory. */
    @Test
    public void testAdd() {
        VariableModification<BigInteger> modifier =
                BigIntegerModificationFactory.add(BigInteger.ONE);
        start.setModification(modifier);
        expectedResult = new BigInteger("11");
        result = start.getValue();
        assertEquals(expectedResult, result);
        assertNotSame(expectedResult, result);
        assertEquals(BigInteger.TEN, start.getOriginalValue());
    }

    /** Test of sub method, of class BigIntegerModificationFactory. */
    @Test
    public void testSub() {
        VariableModification<BigInteger> modifier =
                BigIntegerModificationFactory.sub(BigInteger.ONE);
        start.setModification(modifier);
        expectedResult = new BigInteger("9");
        result = start.getValue();
        assertEquals(expectedResult, result);
        assertNotSame(expectedResult, result);
        assertEquals(BigInteger.TEN, start.getOriginalValue());
    }

    /** Test of xor method, of class BigIntegerModificationFactory. */
    @Test
    public void testXor() {
        VariableModification<BigInteger> modifier =
                BigIntegerModificationFactory.xor(new BigInteger("2"));
        start.setModification(modifier);
        expectedResult = new BigInteger("8");
        result = start.getValue();
        assertEquals(expectedResult, result);
        assertNotSame(expectedResult, result);
        assertEquals(BigInteger.TEN, start.getOriginalValue());
    }

    /** Test of explicitValue method, of class BigIntegerModificationFactory. */
    @Test
    public void testExplicitValue() {
        VariableModification<BigInteger> modifier =
                BigIntegerModificationFactory.explicitValue(new BigInteger("7"));
        start.setModification(modifier);
        expectedResult = new BigInteger("7");
        result = start.getValue();
        assertEquals(expectedResult, result);
        assertNotSame(expectedResult, result);
        assertEquals(BigInteger.TEN, start.getOriginalValue());
    }

    @Test
    public void testInsertValue() {
        // expect: 111xxxx
        VariableModification<BigInteger> modifier =
                BigIntegerModificationFactory.insertValue(BigInteger.valueOf(7), 4);
        start.setModification(modifier);
        BigInteger mask = BigInteger.valueOf((1L << 3) - 1).shiftLeft(4);
        expectedResult = BigInteger.valueOf(7);
        result = start.getValue().and(mask).shiftRight(4);
        assertEquals(expectedResult, result);
        assertEquals(BigInteger.valueOf(10), start.getOriginalValue());

        // expect: x111xxx
        modifier = BigIntegerModificationFactory.insertValue(BigInteger.valueOf(7), 3);
        start.setModification(modifier);
        mask = BigInteger.valueOf((1L << 3) - 1).shiftLeft(3);
        expectedResult = BigInteger.valueOf(7);
        result = start.getValue().and(mask).shiftRight(3);
        assertEquals(expectedResult, result);
    }

    /** Test of add method, of class BigIntegerModificationFactory. */
    @Test
    public void testIsOriginalValueModified() {
        assertFalse(start.isOriginalValueModified());
        VariableModification<BigInteger> modifier =
                BigIntegerModificationFactory.add(BigInteger.ZERO);
        start.setModification(modifier);
        assertFalse(start.isOriginalValueModified());
        modifier = BigIntegerModificationFactory.add(BigInteger.ONE);
        start.setModification(modifier);
        assertTrue(start.isOriginalValueModified());
    }

    @Test
    public void testShiftLeft() {
        VariableModification<BigInteger> modifier = BigIntegerModificationFactory.shiftLeft(2);
        start.setModification(modifier);
        expectedResult = new BigInteger("40");
        result = start.getValue();
        assertEquals(expectedResult, result);
        assertNotSame(expectedResult, result);
        assertEquals(BigInteger.TEN, start.getOriginalValue());
    }

    @Test
    public void testShiftRight() {
        VariableModification<BigInteger> modifier = BigIntegerModificationFactory.shiftRight(1);
        start.setModification(modifier);
        expectedResult = new BigInteger("5");
        result = start.getValue();
        assertEquals(expectedResult, result);
        assertNotSame(expectedResult, result);
        assertEquals(BigInteger.TEN, start.getOriginalValue());
    }

    /** Test of explicitValue from file method */
    @Test
    public void testExplicitValueFromFile() {
        VariableModification<BigInteger> modifier =
                BigIntegerModificationFactory.explicitValueFromFile(0);
        start.setModification(modifier);
        expectedResult = BigInteger.valueOf(-128);
        result = start.getValue();
        assertEquals(expectedResult, result);

        modifier = BigIntegerModificationFactory.explicitValueFromFile(1);
        start.setModification(modifier);
        expectedResult = BigInteger.valueOf(-1);
        result = start.getValue();
        assertEquals(expectedResult, result);

        modifier = BigIntegerModificationFactory.explicitValueFromFile(27);
        start.setModification(modifier);
        expectedResult = BigInteger.valueOf(2147483647);
        result = start.getValue();
        assertEquals(expectedResult, result);
    }
}
