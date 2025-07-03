/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.mlong;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import de.rub.nds.modifiablevariable.VariableModification;
import de.rub.nds.modifiablevariable.longint.LongAddModification;
import de.rub.nds.modifiablevariable.longint.LongExplicitValueModification;
import de.rub.nds.modifiablevariable.longint.LongMultiplyModification;
import de.rub.nds.modifiablevariable.longint.LongShiftLeftModification;
import de.rub.nds.modifiablevariable.longint.LongShiftRightModification;
import de.rub.nds.modifiablevariable.longint.LongSubtractModification;
import de.rub.nds.modifiablevariable.longint.LongSwapEndianModification;
import de.rub.nds.modifiablevariable.longint.LongXorModification;
import org.junit.jupiter.api.Test;

class LongModificationFactoryTest {

    @Test
    void testAddWithLong() {
        Long summand = 10L;
        VariableModification<Long> modification = new LongAddModification(summand);

        assertNotNull(modification);
        assertTrue(modification instanceof LongAddModification);
        assertEquals(summand, ((LongAddModification) modification).getSummand());
    }

    @Test
    void testSubWithLong() {
        Long subtrahend = 10L;
        VariableModification<Long> modification = new LongSubtractModification(subtrahend);

        assertNotNull(modification);
        assertTrue(modification instanceof LongSubtractModification);
        assertEquals(subtrahend, ((LongSubtractModification) modification).getSubtrahend());
    }

    @Test
    void testXorWithLong() {
        Long xorValue = 10L;
        VariableModification<Long> modification = new LongXorModification(xorValue);

        assertNotNull(modification);
        assertTrue(modification instanceof LongXorModification);
        assertEquals(xorValue, ((LongXorModification) modification).getXor());
    }

    @Test
    void testSwapEndian() {
        VariableModification<Long> modification = new LongSwapEndianModification();

        assertNotNull(modification);
        assertTrue(modification instanceof LongSwapEndianModification);
    }

    @Test
    void testExplicitValueWithLong() {
        Long explicitValue = 10L;
        VariableModification<Long> modification = new LongExplicitValueModification(explicitValue);

        assertNotNull(modification);
        assertTrue(modification instanceof LongExplicitValueModification);
        assertEquals(
                explicitValue, ((LongExplicitValueModification) modification).getExplicitValue());
    }

    @Test
    void testMultiply() {
        Long factor = 10L;
        VariableModification<Long> modification = new LongMultiplyModification(factor);

        assertNotNull(modification);
        assertTrue(modification instanceof LongMultiplyModification);
        assertEquals(factor, ((LongMultiplyModification) modification).getFactor());
    }

    @Test
    void testShiftLeft() {
        int shift = 5;
        VariableModification<Long> modification = new LongShiftLeftModification(shift);

        assertNotNull(modification);
        assertTrue(modification instanceof LongShiftLeftModification);
        assertEquals(shift, ((LongShiftLeftModification) modification).getShift());
    }

    @Test
    void testShiftRight() {
        int shift = 5;
        VariableModification<Long> modification = new LongShiftRightModification(shift);

        assertNotNull(modification);
        assertTrue(modification instanceof LongShiftRightModification);
        assertEquals(shift, ((LongShiftRightModification) modification).getShift());
    }
}
