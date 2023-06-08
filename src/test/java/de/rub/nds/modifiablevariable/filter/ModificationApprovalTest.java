/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.filter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import de.rub.nds.modifiablevariable.ModificationFilter;
import de.rub.nds.modifiablevariable.VariableModification;
import de.rub.nds.modifiablevariable.biginteger.BigIntegerModificationFactory;
import de.rub.nds.modifiablevariable.biginteger.ModifiableBigInteger;
import java.math.BigInteger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ModificationApprovalTest {

    private ModifiableBigInteger start;

    private ModificationFilter filter;

    private BigInteger expectedResult, result;

    @BeforeEach
    public void setUp() {
        start = new ModifiableBigInteger();
        start.setOriginalValue(BigInteger.TEN);
        int[] filtered = {1, 3};
        filter = ModificationFilterFactory.access(filtered);
        expectedResult = null;
        result = null;
    }

    /**
     * Test filter modification. The first and third modification are filtered out so that no
     * modification is visible.
     */
    @Test
    public void testAdd() {
        VariableModification<BigInteger> modifier =
                BigIntegerModificationFactory.add(BigInteger.ONE);
        start.setModification(modifier);
        modifier.setModificationFilter(filter);
        expectedResult = new BigInteger("10");
        result = start.getValue();
        assertEquals(expectedResult, result);
        assertNotSame(expectedResult, result);

        expectedResult = new BigInteger("11");
        result = start.getValue();
        assertEquals(expectedResult, result);

        expectedResult = new BigInteger("10");
        result = start.getValue();
        assertEquals(expectedResult, result);
        assertNotSame(expectedResult, result);
    }
}
