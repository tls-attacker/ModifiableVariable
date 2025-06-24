/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import de.rub.nds.modifiablevariable.biginteger.ModifiableBigInteger;
import de.rub.nds.modifiablevariable.bool.ModifiableBoolean;
import de.rub.nds.modifiablevariable.bytearray.ModifiableByteArray;
import de.rub.nds.modifiablevariable.integer.ModifiableInteger;
import de.rub.nds.modifiablevariable.longint.ModifiableLong;
import de.rub.nds.modifiablevariable.singlebyte.ModifiableByte;
import de.rub.nds.modifiablevariable.string.ModifiableString;
import java.math.BigInteger;
import org.junit.jupiter.api.Test;

class ModifiableVariableFactoryTest {

    @Test
    void testSafelySetValueBigInteger() {
        // Test with null ModifiableBigInteger
        BigInteger originalValue = new BigInteger("12345");
        ModifiableBigInteger result = ModifiableVariableFactory.safelySetValue(null, originalValue);

        assertNotNull(result);
        assertEquals(originalValue, result.getValue());

        // Test with existing ModifiableBigInteger
        ModifiableBigInteger existing = new ModifiableBigInteger();
        ModifiableBigInteger updated =
                ModifiableVariableFactory.safelySetValue(existing, originalValue);

        assertSame(existing, updated);
        assertEquals(originalValue, updated.getValue());
    }

    @Test
    void testSafelySetValueString() {
        // Test with null ModifiableString
        String originalValue = "test string";
        ModifiableString result = ModifiableVariableFactory.safelySetValue(null, originalValue);

        assertNotNull(result);
        assertEquals(originalValue, result.getValue());

        // Test with existing ModifiableString
        ModifiableString existing = new ModifiableString();
        ModifiableString updated =
                ModifiableVariableFactory.safelySetValue(existing, originalValue);

        assertSame(existing, updated);
        assertEquals(originalValue, updated.getValue());
    }

    @Test
    void testSafelySetValueInteger() {
        // Test with null ModifiableInteger
        Integer originalValue = 42;
        ModifiableInteger result = ModifiableVariableFactory.safelySetValue(null, originalValue);

        assertNotNull(result);
        assertEquals(originalValue, result.getValue());

        // Test with existing ModifiableInteger
        ModifiableInteger existing = new ModifiableInteger();
        ModifiableInteger updated =
                ModifiableVariableFactory.safelySetValue(existing, originalValue);

        assertSame(existing, updated);
        assertEquals(originalValue, updated.getValue());
    }

    @Test
    void testSafelySetValueByte() {
        // Test with null ModifiableByte
        Byte originalValue = (byte) 0xAB;
        ModifiableByte result = ModifiableVariableFactory.safelySetValue(null, originalValue);

        assertNotNull(result);
        assertEquals(originalValue, result.getValue());

        // Test with existing ModifiableByte
        ModifiableByte existing = new ModifiableByte();
        ModifiableByte updated = ModifiableVariableFactory.safelySetValue(existing, originalValue);

        assertSame(existing, updated);
        assertEquals(originalValue, updated.getValue());
    }

    @Test
    void testSafelySetValueByteArray() {
        // Test with null ModifiableByteArray
        byte[] originalValue = new byte[] {0x01, 0x02, 0x03};
        ModifiableByteArray result = ModifiableVariableFactory.safelySetValue(null, originalValue);

        assertNotNull(result);

        assertEquals(originalValue[0], result.getValue()[0]);
        assertEquals(originalValue[1], result.getValue()[1]);
        assertEquals(originalValue[2], result.getValue()[2]);

        // Test with existing ModifiableByteArray
        ModifiableByteArray existing = new ModifiableByteArray();
        ModifiableByteArray updated =
                ModifiableVariableFactory.safelySetValue(existing, originalValue);

        assertSame(existing, updated);

        assertEquals(originalValue[0], updated.getValue()[0]);
        assertEquals(originalValue[1], updated.getValue()[1]);
        assertEquals(originalValue[2], updated.getValue()[2]);
    }

    @Test
    void testSafelySetValueLong() {
        // Test with null ModifiableLong
        Long originalValue = 9999999999L;
        ModifiableLong result = ModifiableVariableFactory.safelySetValue(null, originalValue);

        assertNotNull(result);
        assertEquals(originalValue, result.getValue());

        // Test with existing ModifiableLong
        ModifiableLong existing = new ModifiableLong();
        ModifiableLong updated = ModifiableVariableFactory.safelySetValue(existing, originalValue);

        assertSame(existing, updated);
        assertEquals(originalValue, updated.getValue());
    }

    @Test
    void testSafelySetValueBoolean() {
        // Test with null ModifiableBoolean
        Boolean originalValue = Boolean.TRUE;
        ModifiableBoolean result = ModifiableVariableFactory.safelySetValue(null, originalValue);

        assertNotNull(result);
        assertEquals(originalValue, result.getValue());

        // Test with existing ModifiableBoolean
        ModifiableBoolean existing = new ModifiableBoolean();
        ModifiableBoolean updated =
                ModifiableVariableFactory.safelySetValue(existing, originalValue);

        assertSame(existing, updated);
        assertEquals(originalValue, updated.getValue());
    }
}
