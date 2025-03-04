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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import de.rub.nds.modifiablevariable.VariableModification;
import de.rub.nds.modifiablevariable.longint.LongAddModification;
import de.rub.nds.modifiablevariable.longint.LongExplicitValueModification;
import de.rub.nds.modifiablevariable.longint.LongModificationFactory;
import de.rub.nds.modifiablevariable.longint.LongMultiplyModification;
import de.rub.nds.modifiablevariable.longint.LongShiftLeftModification;
import de.rub.nds.modifiablevariable.longint.LongShiftRightModification;
import de.rub.nds.modifiablevariable.longint.LongSubtractModification;
import de.rub.nds.modifiablevariable.longint.LongSwapEndianModification;
import de.rub.nds.modifiablevariable.longint.LongXorModification;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import org.junit.jupiter.api.Test;

public class LongModificationFactoryTest {

    @Test
    public void testAddWithLong() {
        Long summand = 10L;
        VariableModification<Long> modification = LongModificationFactory.add(summand);

        assertNotNull(modification);
        assertTrue(modification instanceof LongAddModification);
        assertEquals(summand, ((LongAddModification) modification).getSummand());
    }

    @Test
    public void testAddWithString() {
        String summandString = "10";
        Long summand = 10L;
        VariableModification<Long> modification = LongModificationFactory.add(summandString);

        assertNotNull(modification);
        assertTrue(modification instanceof LongAddModification);
        assertEquals(summand, ((LongAddModification) modification).getSummand());
    }

    @Test
    public void testAddWithInvalidString() {
        assertThrows(NumberFormatException.class, () -> LongModificationFactory.add("invalid"));
    }

    @Test
    public void testSubWithLong() {
        Long subtrahend = 10L;
        VariableModification<Long> modification = LongModificationFactory.sub(subtrahend);

        assertNotNull(modification);
        assertTrue(modification instanceof LongSubtractModification);
        assertEquals(subtrahend, ((LongSubtractModification) modification).getSubtrahend());
    }

    @Test
    public void testSubWithString() {
        String subtrahendString = "10";
        Long subtrahend = 10L;
        VariableModification<Long> modification = LongModificationFactory.sub(subtrahendString);

        assertNotNull(modification);
        assertTrue(modification instanceof LongSubtractModification);
        assertEquals(subtrahend, ((LongSubtractModification) modification).getSubtrahend());
    }

    @Test
    public void testSubWithInvalidString() {
        assertThrows(NumberFormatException.class, () -> LongModificationFactory.sub("invalid"));
    }

    @Test
    public void testXorWithLong() {
        Long xorValue = 10L;
        VariableModification<Long> modification = LongModificationFactory.xor(xorValue);

        assertNotNull(modification);
        assertTrue(modification instanceof LongXorModification);
        assertEquals(xorValue, ((LongXorModification) modification).getXor());
    }

    @Test
    public void testXorWithString() {
        String xorString = "10";
        Long xorValue = 10L;
        VariableModification<Long> modification = LongModificationFactory.xor(xorString);

        assertNotNull(modification);
        assertTrue(modification instanceof LongXorModification);
        assertEquals(xorValue, ((LongXorModification) modification).getXor());
    }

    @Test
    public void testXorWithInvalidString() {
        assertThrows(NumberFormatException.class, () -> LongModificationFactory.xor("invalid"));
    }

    @Test
    public void testSwapEndian() {
        VariableModification<Long> modification = LongModificationFactory.swapEndian();

        assertNotNull(modification);
        assertTrue(modification instanceof LongSwapEndianModification);
    }

    @Test
    public void testExplicitValueWithLong() {
        Long explicitValue = 10L;
        VariableModification<Long> modification =
                LongModificationFactory.explicitValue(explicitValue);

        assertNotNull(modification);
        assertTrue(modification instanceof LongExplicitValueModification);
        assertEquals(
                explicitValue, ((LongExplicitValueModification) modification).getExplicitValue());
    }

    @Test
    public void testExplicitValueWithString() {
        String valueString = "10";
        Long explicitValue = 10L;
        VariableModification<Long> modification =
                LongModificationFactory.explicitValue(valueString);

        assertNotNull(modification);
        assertTrue(modification instanceof LongExplicitValueModification);
        assertEquals(
                explicitValue, ((LongExplicitValueModification) modification).getExplicitValue());
    }

    @Test
    public void testExplicitValueWithInvalidString() {
        assertThrows(
                NumberFormatException.class,
                () -> LongModificationFactory.explicitValue("invalid"));
    }

    @Test
    public void testMultiply() {
        Long factor = 10L;
        VariableModification<Long> modification = LongModificationFactory.multiply(factor);

        assertNotNull(modification);
        assertTrue(modification instanceof LongMultiplyModification);
        assertEquals(factor, ((LongMultiplyModification) modification).getFactor());
    }

    @Test
    public void testShiftLeft() {
        int shift = 5;
        VariableModification<Long> modification = LongModificationFactory.shiftLeft(shift);

        assertNotNull(modification);
        assertTrue(modification instanceof LongShiftLeftModification);
        assertEquals(shift, ((LongShiftLeftModification) modification).getShift());
    }

    @Test
    public void testShiftRight() {
        int shift = 5;
        VariableModification<Long> modification = LongModificationFactory.shiftRight(shift);

        assertNotNull(modification);
        assertTrue(modification instanceof LongShiftRightModification);
        assertEquals(shift, ((LongShiftRightModification) modification).getShift());
    }

    @Test
    public void testPrivateConstructor() throws NoSuchMethodException {
        Constructor<LongModificationFactory> constructor =
                LongModificationFactory.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
    }
}
