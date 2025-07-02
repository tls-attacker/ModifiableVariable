/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.mlong;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import de.rub.nds.modifiablevariable.VariableModification;
import de.rub.nds.modifiablevariable.longint.LongAddModification;
import de.rub.nds.modifiablevariable.longint.LongExplicitValueModification;
import de.rub.nds.modifiablevariable.longint.LongMultiplyModification;
import de.rub.nds.modifiablevariable.longint.LongShiftLeftModification;
import de.rub.nds.modifiablevariable.longint.LongShiftRightModification;
import de.rub.nds.modifiablevariable.longint.LongSubtractModification;
import de.rub.nds.modifiablevariable.longint.LongSwapEndianModification;
import de.rub.nds.modifiablevariable.longint.LongXorModification;
import de.rub.nds.modifiablevariable.longint.ModifiableLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LongModificationTest {

    private ModifiableLong start;

    private Long expectedResult, result;

    @BeforeEach
    void setUp() {
        start = new ModifiableLong();
        start.setOriginalValue(10L);
        expectedResult = null;
        result = null;
    }

    @Test
    void testAdd() {

        VariableModification<Long> modifier = new LongAddModification(1L);
        start.setModifications(modifier);
        expectedResult = 11L;
        result = start.getValue();
        assertEquals(expectedResult, result);
        assertEquals(10L, start.getOriginalValue());
    }

    @Test
    void testSub() {
        VariableModification<Long> modifier = new LongSubtractModification(1L);
        start.setModifications(modifier);
        expectedResult = 9L;
        result = start.getValue();
        assertEquals(expectedResult, result);
        assertEquals(10L, start.getOriginalValue());
    }

    @Test
    void testXor() {
        VariableModification<Long> modifier = new LongXorModification(2L);
        start.setModifications(modifier);
        expectedResult = 8L;
        result = start.getValue();
        assertEquals(expectedResult, result);
        assertEquals(10L, start.getOriginalValue());
    }

    @Test
    void testExplicitValue() {
        VariableModification<Long> modifier = new LongExplicitValueModification(7L);
        start.setModifications(modifier);
        expectedResult = 7L;
        result = start.getValue();
        assertEquals(expectedResult, result);
        assertEquals(10L, start.getOriginalValue());
    }

    @Test
    void testMultiply() {
        VariableModification<Long> modifier = new LongMultiplyModification(3L);
        start.setModifications(modifier);
        expectedResult = 30L;
        result = start.getValue();
        assertEquals(expectedResult, result);
        assertEquals(10L, start.getOriginalValue());
    }

    @Test
    void testMultiplyWithZero() {
        VariableModification<Long> modifier = new LongMultiplyModification(0L);
        start.setModifications(modifier);
        expectedResult = 0L;
        result = start.getValue();
        assertEquals(expectedResult, result);
        assertEquals(10L, start.getOriginalValue());
    }

    @Test
    void testMultiplyWithNull() {
        ModifiableLong nullStart = new ModifiableLong();
        VariableModification<Long> modifier = new LongMultiplyModification(5L);
        nullStart.setModifications(modifier);
        assertNull(nullStart.getValue());
    }

    @Test
    void testShiftLeft() {
        VariableModification<Long> modifier = new LongShiftLeftModification(2);
        start.setModifications(modifier);
        expectedResult = 40L; // 10 << 2 = 40
        result = start.getValue();
        assertEquals(expectedResult, result);
        assertEquals(10L, start.getOriginalValue());
    }

    @Test
    void testShiftLeftWithLargeShift() {
        VariableModification<Long> modifier = new LongShiftLeftModification(65);
        start.setModifications(modifier);
        expectedResult = 20L; // (10 << 65) % 64 = (10 << 1) = 20
        result = start.getValue();
        assertEquals(expectedResult, result);
        assertEquals(10L, start.getOriginalValue());
    }

    @Test
    void testShiftLeftWithNull() {
        ModifiableLong nullStart = new ModifiableLong();
        VariableModification<Long> modifier = new LongShiftLeftModification(2);
        nullStart.setModifications(modifier);
        assertNull(nullStart.getValue());
    }

    @Test
    void testShiftRight() {
        start.setOriginalValue(40L);
        VariableModification<Long> modifier = new LongShiftRightModification(2);
        start.setModifications(modifier);
        expectedResult = 10L; // 40 >> 2 = 10
        result = start.getValue();
        assertEquals(expectedResult, result);
        assertEquals(40L, start.getOriginalValue());
    }

    @Test
    void testShiftRightWithLargeShift() {
        start.setOriginalValue(40L);
        VariableModification<Long> modifier = new LongShiftRightModification(66);
        start.setModifications(modifier);
        expectedResult = 10L; // (40 >> 66) % 64 = (40 >> 2) = 10
        result = start.getValue();
        assertEquals(expectedResult, result);
        assertEquals(40L, start.getOriginalValue());
    }

    @Test
    void testShiftRightWithNull() {
        ModifiableLong nullStart = new ModifiableLong();
        VariableModification<Long> modifier = new LongShiftRightModification(2);
        nullStart.setModifications(modifier);
        expectedResult = null;
        result = nullStart.getValue();
        assertEquals(expectedResult, result);
    }

    @Test
    void testSwapEndian() {
        start.setOriginalValue(0x1122334455667788L);
        VariableModification<Long> modifier = new LongSwapEndianModification();
        start.setModifications(modifier);
        expectedResult = 0x8877665544332211L; // Byte-swapped value
        result = start.getValue();
        assertEquals(expectedResult, result);
        assertEquals(Long.valueOf(0x1122334455667788L), start.getOriginalValue());
    }

    @Test
    void testSwapEndianWithNull() {
        ModifiableLong nullStart = new ModifiableLong();
        VariableModification<Long> modifier = new LongSwapEndianModification();
        nullStart.setModifications(modifier);
        assertNull(nullStart.getValue());
    }

    @Test
    void testMultipleModifications() {
        // Test add followed by multiply
        VariableModification<Long> addModifier = new LongAddModification(5L);
        VariableModification<Long> multiplyModifier = new LongMultiplyModification(2L);
        start.setModifications(addModifier);
        start.addModification(multiplyModifier);
        expectedResult = 30L; // (10 + 5) * 2 = 30
        result = start.getValue();
        assertEquals(expectedResult, result);
        assertEquals(10L, start.getOriginalValue());
    }

    @Test
    void testBoundaryValues() {
        // Test with Long.MAX_VALUE
        start.setOriginalValue(Long.MAX_VALUE);
        VariableModification<Long> addModifier = new LongAddModification(1L);
        start.setModifications(addModifier);
        expectedResult = Long.MIN_VALUE; // Overflow wraps around
        result = start.getValue();
        assertEquals(expectedResult, result);

        // Test with Long.MIN_VALUE
        start.setOriginalValue(Long.MIN_VALUE);
        VariableModification<Long> subModifier = new LongSubtractModification(1L);
        start.setModifications(subModifier);
        expectedResult = Long.MAX_VALUE; // Underflow wraps around
        result = start.getValue();
        assertEquals(expectedResult, result);
    }
}
