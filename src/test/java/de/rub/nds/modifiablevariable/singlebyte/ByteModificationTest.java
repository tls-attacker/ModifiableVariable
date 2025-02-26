/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.singlebyte;

import static org.junit.jupiter.api.Assertions.assertEquals;

import de.rub.nds.modifiablevariable.VariableModification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ByteModificationTest {

    private ModifiableByte start;

    private Byte expectedResult, result;

    @BeforeEach
    public void setUp() {
        start = new ModifiableByte();
        start.setOriginalValue(Byte.valueOf("10"));
        expectedResult = null;
        result = null;
    }

    /** Test of add method, of class ByteModificationFactory. */
    @Test
    public void testAdd() {
        VariableModification<Byte> modifier = ByteModificationFactory.add(Byte.valueOf("1"));
        start.setModification(modifier);
        expectedResult = Byte.valueOf("11");
        result = start.getValue();
        assertEquals(expectedResult, result);
        assertEquals(Byte.valueOf("10"), start.getOriginalValue());
    }

    /** Test of sub method, of class ByteModificationFactory. */
    @Test
    public void testSub() {
        VariableModification<Byte> modifier = ByteModificationFactory.sub(Byte.valueOf("1"));
        start.setModification(modifier);
        expectedResult = Byte.valueOf("9");
        result = start.getValue();
        assertEquals(expectedResult, result);
        assertEquals(Byte.valueOf("10"), start.getOriginalValue());
    }

    /** Test of xor method, of class ByteModificationFactory. */
    @Test
    public void testXor() {
        VariableModification<Byte> modifier = ByteModificationFactory.xor(Byte.valueOf("2"));
        start.setModification(modifier);
        expectedResult = Byte.valueOf("8");
        result = start.getValue();
        assertEquals(expectedResult, result);
        assertEquals(Byte.valueOf("10"), start.getOriginalValue());
    }

    /** Test of explicitValue method, of class ByteModificationFactory. */
    @Test
    public void testExplicitValue() {
        VariableModification<Byte> modifier =
                ByteModificationFactory.explicitValue(Byte.valueOf("7"));
        start.setModification(modifier);
        expectedResult = Byte.valueOf("7");
        result = start.getValue();
        assertEquals(expectedResult, result);
        assertEquals(Byte.valueOf("10"), start.getOriginalValue());
    }
}
