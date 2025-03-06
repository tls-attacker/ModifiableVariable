/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.mlong;

import static org.junit.jupiter.api.Assertions.assertEquals;

import de.rub.nds.modifiablevariable.VariableModification;
import de.rub.nds.modifiablevariable.longint.LongAddModification;
import de.rub.nds.modifiablevariable.longint.LongExplicitValueModification;
import de.rub.nds.modifiablevariable.longint.LongSubtractModification;
import de.rub.nds.modifiablevariable.longint.LongXorModification;
import de.rub.nds.modifiablevariable.longint.ModifiableLong;
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

        VariableModification<Long> modifier = new LongAddModification(1L);
        start.setModifications(modifier);
        expectedResult = 11L;
        result = start.getValue();
        assertEquals(expectedResult, result);
        assertEquals(Long.valueOf(10L), start.getOriginalValue());
    }

    @Test
    public void testSub() {
        VariableModification<Long> modifier = new LongSubtractModification(1L);
        start.setModifications(modifier);
        expectedResult = 9L;
        result = start.getValue();
        assertEquals(expectedResult, result);
        assertEquals(Long.valueOf(10L), start.getOriginalValue());
    }

    @Test
    public void testXor() {
        VariableModification<Long> modifier = new LongXorModification(2L);
        start.setModifications(modifier);
        expectedResult = 8L;
        result = start.getValue();
        assertEquals(expectedResult, result);
        assertEquals(Long.valueOf(10L), start.getOriginalValue());
    }

    @Test
    public void testExplicitValue() {
        VariableModification<Long> modifier = new LongExplicitValueModification(7L);
        start.setModifications(modifier);
        expectedResult = 7L;
        result = start.getValue();
        assertEquals(expectedResult, result);
        assertEquals(Long.valueOf(10L), start.getOriginalValue());
    }
}
