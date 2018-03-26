/**
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Copyright 2014-2017 Ruhr University Bochum / Hackmanit GmbH
 *
 * Licensed under Apache License 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.biginteger;

import de.rub.nds.modifiablevariable.VariableModification;
import java.math.BigInteger;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class BigIntegerOperationConcartenationTest {

    private ModifiableBigInteger start;

    private BigInteger expectedResult, result;

    public BigIntegerOperationConcartenationTest() {
    }

    @Before
    public void setUp() {
        start = new ModifiableBigInteger();
        start.setOriginalValue(BigInteger.TEN);
    }

    @Test
    public void testAddThenMultiply() {
        // (input + 4) ^ 3 = (10 + 4) ^ 3 = 13
        VariableModification<BigInteger> modifier = BigIntegerModificationFactory.add("4");
        start.setModification(modifier);
        modifier.setPostModification(BigIntegerModificationFactory.xor("3"));
        expectedResult = new BigInteger("13");
        result = start.getValue();
        assertEquals(expectedResult, result);
    }

    @Test
    public void testAddThenMultiplyWithInnerClass() {
        // (input + 4) ^ 3 = (10 + 4) ^ 3 = 13
        start.setModification(new VariableModification<BigInteger>() {

            @Override
            protected BigInteger modifyImplementationHook(BigInteger input) {
                return input.add(new BigInteger("4")).xor(new BigInteger("3"));
            }

            @Override
            protected VariableModification<BigInteger> getModifiedCopy() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        expectedResult = new BigInteger("13");
        result = start.getValue();
        assertEquals(expectedResult, result);
    }
}
