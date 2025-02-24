/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.biginteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

import de.rub.nds.modifiablevariable.VariableModification;
import java.math.BigInteger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BigIntegerOperationConcatenationTest {

    private ModifiableBigInteger start;

    private BigInteger expectedResult, result;

    @BeforeEach
    public void setUp() {
        start = new ModifiableBigInteger();
        start.setOriginalValue(BigInteger.TEN);
    }

    @Test
    public void testAddThenMultiplyWithInnerClass() {
        // (input + 4) ^ 3 = (10 + 4) ^ 3 = 13
        start.setModification(
                new VariableModification<>() {

                    @Override
                    protected BigInteger modifyImplementationHook(BigInteger input) {
                        return input.add(new BigInteger("4")).xor(new BigInteger("3"));
                    }

                    @Override
                    public VariableModification<BigInteger> createCopy() {
                        throw new UnsupportedOperationException("Not supported yet.");
                    }
                });
        expectedResult = new BigInteger("13");
        result = start.getValue();
        assertEquals(expectedResult, result);
    }
}
