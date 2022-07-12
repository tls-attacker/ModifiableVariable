/**
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Copyright 2014-2022 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 *
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.modifiablevariable.mlong;

import static org.junit.jupiter.api.Assertions.assertEquals;

import de.rub.nds.modifiablevariable.VariableModification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LongModificationTest {

    private ModifiableLong start;

    private Long expectedResult, result;

    @BeforeEach
    public void setUp() {

        start = new ModifiableLong();
        start.setOriginalValue(10L);
        expectedResult = null;
        result = null;

    }

    @Test
    public void testAdd() {

        VariableModification<Long> modifier = LongModificationFactory.add(1L);
        start.setModification(modifier);
        expectedResult = 11L;
        result = start.getValue();
        assertEquals(expectedResult, result);
        assertEquals(Long.valueOf(10L), start.getOriginalValue());

    }

    @Test
    public void testSub() {
        VariableModification<Long> modifier = LongModificationFactory.sub(1L);
        start.setModification(modifier);
        expectedResult = 9L;
        result = start.getValue();
        assertEquals(expectedResult, result);
        assertEquals(Long.valueOf(10L), start.getOriginalValue());
    }

    @Test
    public void testXor() {
        VariableModification<Long> modifier = LongModificationFactory.xor(2L);
        start.setModification(modifier);
        expectedResult = 8L;
        result = start.getValue();
        assertEquals(expectedResult, result);
        assertEquals(Long.valueOf(10L), start.getOriginalValue());
    }

    @Test
    public void testExplicitValue() {
        VariableModification<Long> modifier = LongModificationFactory.explicitValue(7L);
        start.setModification(modifier);
        expectedResult = 7L;
        result = start.getValue();
        assertEquals(expectedResult, result);
        assertEquals(Long.valueOf(10L), start.getOriginalValue());
    }

    /**
     * Test of explicitValue from file method
     */
    @Test
    public void testExplicitValueFromFile() {
        VariableModification<Long> modifier = LongModificationFactory.explicitValueFromFile(0);
        start.setModification(modifier);
        expectedResult = -128L;
        result = start.getValue();
        assertEquals(expectedResult, result);

        modifier = LongModificationFactory.explicitValueFromFile(1);
        start.setModification(modifier);
        expectedResult = -1L;
        result = start.getValue();
        assertEquals(expectedResult, result);

        modifier = LongModificationFactory.explicitValueFromFile(26);
        start.setModification(modifier);
        expectedResult = 2147483647L;
        result = start.getValue();
        assertEquals(expectedResult, result);
    }

}
