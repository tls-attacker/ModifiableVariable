/**
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 *
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.modifiablevariable.singlebyte;

import de.rub.nds.modifiablevariable.singlebyte.ByteModificationFactory;
import de.rub.nds.modifiablevariable.singlebyte.ModifiableByte;
import de.rub.nds.modifiablevariable.VariableModification;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import org.junit.Before;
import org.junit.Test;

public class ByteModificationTest {

    private ModifiableByte start;

    private Byte expectedResult, result;

    public ByteModificationTest() {
    }

    @Before
    public void setUp() {
        start = new ModifiableByte();
        start.setOriginalValue(new Byte("10"));
        expectedResult = null;
        result = null;
    }

    /**
     * Test of add method, of class ByteModificationFactory.
     */
    @Test
    public void testAdd() {
        VariableModification<Byte> modifier = ByteModificationFactory.add(new Byte("1"));
        start.setModification(modifier);
        expectedResult = new Byte("11");
        result = start.getValue();
        assertEquals(expectedResult, result);
        assertNotSame(expectedResult, result);
        assertEquals(new Byte("10"), start.getOriginalValue());
    }

    /**
     * Test of sub method, of class ByteModificationFactory.
     */
    @Test
    public void testSub() {
        VariableModification<Byte> modifier = ByteModificationFactory.sub(new Byte("1"));
        start.setModification(modifier);
        expectedResult = new Byte("9");
        result = start.getValue();
        assertEquals(expectedResult, result);
        assertNotSame(expectedResult, result);
        assertEquals(new Byte("10"), start.getOriginalValue());
    }

    /**
     * Test of xor method, of class ByteModificationFactory.
     */
    @Test
    public void testXor() {
        VariableModification<Byte> modifier = ByteModificationFactory.xor(new Byte("2"));
        start.setModification(modifier);
        expectedResult = new Byte("8");
        result = start.getValue();
        assertEquals(expectedResult, result);
        assertNotSame(expectedResult, result);
        assertEquals(new Byte("10"), start.getOriginalValue());
    }

    /**
     * Test of explicitValue method, of class ByteModificationFactory.
     */
    @Test
    public void testExplicitValue() {
        VariableModification<Byte> modifier = ByteModificationFactory.explicitValue(new Byte("7"));
        start.setModification(modifier);
        expectedResult = new Byte("7");
        result = start.getValue();
        assertEquals(expectedResult, result);
        assertNotSame(expectedResult, result);
        assertEquals(new Byte("10"), start.getOriginalValue());
    }

    /**
     * Test of explicitValue from file method
     */
    @Test
    public void testExplicitValueFromFile() {
        VariableModification<Byte> modifier = ByteModificationFactory.explicitValueFromFile(0);
        start.setModification(modifier);
        expectedResult = -128;
        result = start.getValue();
        assertEquals(expectedResult, result);

        modifier = ByteModificationFactory.explicitValueFromFile(1);
        start.setModification(modifier);
        expectedResult = -1;
        result = start.getValue();
        assertEquals(expectedResult, result);
    }

}
