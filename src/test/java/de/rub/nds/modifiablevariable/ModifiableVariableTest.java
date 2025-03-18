/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable;

import static org.junit.jupiter.api.Assertions.*;

import de.rub.nds.modifiablevariable.biginteger.*;
import de.rub.nds.modifiablevariable.bytearray.*;
import de.rub.nds.modifiablevariable.integer.*;
import de.rub.nds.modifiablevariable.singlebyte.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

public class ModifiableVariableTest {

    /** Test with BigIntegerAddModification. */
    @Test
    public void testBigIntegerAddModification() {
        ModifiableBigInteger bigInteger = new ModifiableBigInteger();
        bigInteger.setOriginalValue(BigInteger.valueOf(10));

        // Create and apply add modification
        BigIntegerAddModification modifier = new BigIntegerAddModification(BigInteger.valueOf(5));
        bigInteger.addModification(modifier);

        assertEquals(BigInteger.valueOf(15), bigInteger.getValue());
    }

    /** Test with ByteArrayXorModification. */
    @Test
    public void testByteArrayXorModification() {
        ModifiableByteArray byteArray = new ModifiableByteArray();
        byte[] original = new byte[] {0x01, 0x02, 0x03, 0x04};
        byteArray.setOriginalValue(original);

        // Create and apply XOR modification
        byte[] xorBytes = new byte[] {0x10, 0x20, 0x30, 0x40};
        ByteArrayXorModification modifier = new ByteArrayXorModification(xorBytes, 0);
        byteArray.addModification(modifier);

        // Expected: 0x01 ^ 0x10, 0x02 ^ 0x20, 0x03 ^ 0x30, 0x04 ^ 0x40
        byte[] expected = new byte[] {0x11, 0x22, 0x33, 0x44};
        assertArrayEquals(expected, byteArray.getValue());
    }

    /** Test with IntegerAddModification. */
    @Test
    public void testIntegerAddModification() {
        ModifiableInteger integer = new ModifiableInteger();
        integer.setOriginalValue(100);

        // Create and apply add modification
        IntegerAddModification modifier = new IntegerAddModification(50);
        integer.addModification(modifier);

        assertEquals(150, integer.getValue());
    }

    /** Test with ByteAddModification. */
    @Test
    public void testByteAddModification() {
        ModifiableByte byteVar = new ModifiableByte();
        byteVar.setOriginalValue((byte) 0x10);

        // Create and apply add modification
        ByteAddModification modifier = new ByteAddModification((byte) 0x05);
        byteVar.addModification(modifier);

        assertEquals((byte) 0x15, byteVar.getValue());
    }

    /** Test explicit value modification on different types. */
    @Test
    public void testExplicitValueModification() {
        // Test BigInteger explicit value
        ModifiableBigInteger bigInteger = new ModifiableBigInteger();
        bigInteger.setOriginalValue(BigInteger.valueOf(10));
        bigInteger.addModification(new BigIntegerExplicitValueModification(BigInteger.valueOf(42)));
        assertEquals(BigInteger.valueOf(42), bigInteger.getValue());

        // Test Integer explicit value
        ModifiableInteger integer = new ModifiableInteger();
        integer.setOriginalValue(10);
        integer.addModification(new IntegerExplicitValueModification(42));
        assertEquals(42, integer.getValue());

        // Test ByteArray explicit value
        ModifiableByteArray byteArray = new ModifiableByteArray();
        byteArray.setOriginalValue(new byte[] {0x01, 0x02, 0x03});
        byteArray.addModification(
                new ByteArrayExplicitValueModification(new byte[] {0x0A, 0x0B, 0x0C}));
        assertArrayEquals(new byte[] {0x0A, 0x0B, 0x0C}, byteArray.getValue());

        // Test Byte explicit value
        ModifiableByte byteVar = new ModifiableByte();
        byteVar.setOriginalValue((byte) 0x01);
        byteVar.addModification(new ByteExplicitValueModification((byte) 0x0A));
        assertEquals((byte) 0x0A, byteVar.getValue());
    }

    /** Test setModifications with a list of modifications. */
    @Test
    public void testSetModifications() {
        ModifiableInteger integer = new ModifiableInteger();
        integer.setOriginalValue(100);

        // Create a list of modifications
        List<VariableModification<Integer>> modifications = new ArrayList<>();
        modifications.add(new IntegerAddModification(50));
        modifications.add(new IntegerMultiplyModification(2));

        // Set the modifications list
        integer.setModifications(modifications);

        // Expected: (100 + 50) * 2 = 300
        assertEquals(300, integer.getValue());
    }

    /** Test clear modifications. */
    @Test
    public void testClearModifications() {
        ModifiableInteger integer = new ModifiableInteger();
        integer.setOriginalValue(100);

        // Add some modification
        integer.addModification(new IntegerAddModification(50));
        assertEquals(150, integer.getValue());

        // Clear modifications
        integer.clearModifications();

        // Should return original value
        assertEquals(100, integer.getValue());
    }

    /** Test addModification with null. */
    @Test
    public void testAddModificationWithNull() {
        ModifiableInteger integer = new ModifiableInteger();
        integer.setOriginalValue(100);

        // Add null modification - should be ignored
        integer.addModification(null);

        // Should return original value
        assertEquals(100, integer.getValue());
    }

    /** Test copy constructor with modifications. */
    @Test
    public void testCopyConstructorWithModifications() {
        // Create and set up original instance
        ModifiableInteger original = new ModifiableInteger();
        original.setOriginalValue(100);
        original.addModification(new IntegerAddModification(50));
        assertEquals(150, original.getValue());

        // Create copy using copy constructor
        ModifiableInteger copy = new ModifiableInteger(original);

        // Original should not be affected by changes to copy
        copy.addModification(new IntegerMultiplyModification(2));
        assertEquals(150, original.getValue());
        assertEquals(300, copy.getValue());
    }
}
