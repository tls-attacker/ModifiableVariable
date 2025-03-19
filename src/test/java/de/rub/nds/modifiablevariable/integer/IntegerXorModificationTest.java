/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.integer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IntegerXorModificationTest {

    private ModifiableInteger modifiableInteger;
    private int originalValue;

    @BeforeEach
    public void setUp() {
        modifiableInteger = new ModifiableInteger();
        originalValue = 123; // 01111011 in binary
        modifiableInteger.setOriginalValue(originalValue);
    }

    @Test
    public void testXorModification() {
        int xor = 42; // 00101010 in binary
        IntegerXorModification modification = new IntegerXorModification(xor);
        modifiableInteger.setModifications(modification);

        int expected = originalValue ^ xor;
        int result = modifiableInteger.getValue();

        assertEquals(expected, result);
        assertEquals(81, result); // 01010001 in binary
    }

    @Test
    public void testXorZero() {
        int xor = 0;
        IntegerXorModification modification = new IntegerXorModification(xor);
        modifiableInteger.setModifications(modification);

        int expected = originalValue;
        int result = modifiableInteger.getValue();

        assertEquals(expected, result);
    }

    @Test
    public void testXorSelf() {
        int xor = originalValue;
        IntegerXorModification modification = new IntegerXorModification(xor);
        modifiableInteger.setModifications(modification);

        int expected = 0; // XOR with self results in 0
        int result = modifiableInteger.getValue();

        assertEquals(expected, result);
    }

    @Test
    public void testXorAllBitsSet() {
        int xor = -1; // All bits set to 1
        IntegerXorModification modification = new IntegerXorModification(xor);
        modifiableInteger.setModifications(modification);

        int expected = ~originalValue; // Bitwise NOT
        int result = modifiableInteger.getValue();

        assertEquals(expected, result);
    }

    @Test
    public void testXorIdempotent() {
        int xor = 42;
        IntegerXorModification modification1 = new IntegerXorModification(xor);
        IntegerXorModification modification2 = new IntegerXorModification(xor);
        modifiableInteger.setModifications(modification1, modification2);

        // XORing twice with the same value should result in the original
        int expected = originalValue;
        int result = modifiableInteger.getValue();

        assertEquals(expected, result);
    }

    @Test
    public void testXorWithNullInput() {
        modifiableInteger.setOriginalValue(null);

        int xor = 42;
        IntegerXorModification modification = new IntegerXorModification(xor);
        modifiableInteger.setModifications(modification);
        assertNull(modifiableInteger.getValue());
    }

    @Test
    public void testXorMinValue() {
        modifiableInteger.setOriginalValue(Integer.MIN_VALUE);

        int xor = 1; // Flip the sign bit
        IntegerXorModification modification = new IntegerXorModification(xor);
        modifiableInteger.setModifications(modification);

        int expected = Integer.MIN_VALUE ^ 1;
        int result = modifiableInteger.getValue();

        assertEquals(expected, result);
    }

    @Test
    public void testXorMaxValue() {
        modifiableInteger.setOriginalValue(Integer.MAX_VALUE);

        int xor = 1;
        IntegerXorModification modification = new IntegerXorModification(xor);
        modifiableInteger.setModifications(modification);

        int expected = Integer.MAX_VALUE ^ 1;
        int result = modifiableInteger.getValue();

        assertEquals(expected, result);
    }

    @Test
    public void testGetXor() {
        int xor = 42;
        IntegerXorModification modification = new IntegerXorModification(xor);

        assertEquals(xor, modification.getXor());
    }

    @Test
    public void testSetXor() {
        IntegerXorModification modification = new IntegerXorModification(10);

        int newXor = 42;
        modification.setXor(newXor);

        assertEquals(newXor, modification.getXor());
    }

    @Test
    public void testCopyConstructor() {
        int xor = 42;
        IntegerXorModification original = new IntegerXorModification(xor);
        IntegerXorModification copy = new IntegerXorModification(original);

        assertEquals(original.getXor(), copy.getXor());
    }

    @Test
    public void testCreateCopy() {
        int xor = 42;
        IntegerXorModification original = new IntegerXorModification(xor);
        IntegerXorModification copy = original.createCopy();

        assertEquals(original.getXor(), copy.getXor());
        assertEquals(original, copy, "Objects with the same xor should be equal");
        assertNotSame(original, copy, "Copy should not be the same reference");
    }

    @Test
    public void testEqualsAndHashCode() {
        int xor1 = 42;
        int xor2 = 42;
        int xor3 = 100;

        IntegerXorModification modification1 = new IntegerXorModification(xor1);
        IntegerXorModification modification2 = new IntegerXorModification(xor2);
        IntegerXorModification modification3 = new IntegerXorModification(xor3);
        IntegerAddModification differentModification = new IntegerAddModification(xor1);

        // Test equals
        assertEquals(modification1, modification1, "Object should be equal to itself");
        assertEquals(modification1, modification2, "Objects with same xor should be equal");
        assertNotEquals(
                modification1, modification3, "Objects with different xors should not be equal");
        assertNotEquals(
                modification1, differentModification, "Different types should not be equal");
        assertNotEquals(modification1, null, "Object should not be equal to null");
        assertNotEquals(
                modification1, new Object(), "Object should not be equal to different class");

        // Test hashCode
        assertEquals(
                modification1.hashCode(),
                modification2.hashCode(),
                "Equal objects should have same hash code");
        assertNotEquals(
                modification1.hashCode(),
                modification3.hashCode(),
                "Different objects should have different hash codes");
    }

    @Test
    public void testToString() {
        int xor = 42;
        IntegerXorModification modification = new IntegerXorModification(xor);
        String toString = modification.toString();

        assertEquals("IntegerXorModification{xor=" + xor + "}", toString);
    }
}
