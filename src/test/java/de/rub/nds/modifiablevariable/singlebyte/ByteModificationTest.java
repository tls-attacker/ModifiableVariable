/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.singlebyte;

import static org.junit.jupiter.api.Assertions.*;

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

    /** Test of add method, of class ByteAddModification. */
    @Test
    public void testAdd() {
        VariableModification<Byte> modifier = new ByteAddModification(Byte.valueOf("1"));
        start.setModifications(modifier);
        expectedResult = Byte.valueOf("11");
        result = start.getValue();
        assertEquals(expectedResult, result);
        assertEquals(Byte.valueOf("10"), start.getOriginalValue());
    }

    /** Test edge cases for ByteAddModification */
    @Test
    public void testAddEdgeCases() {
        // Test overflow: 127 + 1 = -128 (byte overflow)
        ModifiableByte maxByte = new ModifiableByte(Byte.MAX_VALUE);
        ByteAddModification overflowModifier = new ByteAddModification((byte) 1);
        maxByte.setModifications(overflowModifier);
        assertEquals(Byte.MIN_VALUE, maxByte.getValue());

        // Test with adding zero
        ByteAddModification zeroModifier = new ByteAddModification((byte) 0);
        start.setModifications(zeroModifier);
        assertEquals((byte) 10, start.getValue());

        // Test with null input
        ByteAddModification nullModifier = new ByteAddModification((byte) 0);
        start.setModifications(nullModifier);
        start.setOriginalValue(null);
        assertNull(start.getValue());

        // Test with null original value
        ModifiableByte nullByte = new ModifiableByte();
        nullByte.setModifications(new ByteAddModification((byte) 5));
        assertNull(nullByte.getValue());

        // More comprehensive equals and hashCode tests
        ByteAddModification original = new ByteAddModification((byte) 10);

        // Same value
        ByteAddModification similar = new ByteAddModification((byte) 10);
        assertEquals(original, similar);
        assertEquals(original.hashCode(), similar.hashCode());

        // Different value
        ByteAddModification different = new ByteAddModification((byte) 20);
        assertNotEquals(original, different);
        assertNotEquals(original.hashCode(), different.hashCode());

        // Self equality
        assertEquals(original, original);

        // Null and other types
        assertNotEquals(original, null);
        assertNotEquals(original, "not a ByteAddModification");

        // Test toString
        assertTrue(original.toString().contains("summand"));
        assertTrue(original.toString().contains("10"));

        // Test getter/setter
        original.setSummand((byte) 20);
        assertEquals((byte) 20, original.getSummand());
    }

    /** Test of sub method, of class ByteSubtractModification. */
    @Test
    public void testSub() {
        VariableModification<Byte> modifier = new ByteSubtractModification(Byte.valueOf("1"));
        start.setModifications(modifier);
        expectedResult = Byte.valueOf("9");
        result = start.getValue();
        assertEquals(expectedResult, result);
        assertEquals(Byte.valueOf("10"), start.getOriginalValue());
    }

    /** Test edge cases for ByteSubtractModification */
    @Test
    public void testSubEdgeCases() {
        // Test underflow: -128 - 1 = 127 (byte underflow)
        ModifiableByte minByte = new ModifiableByte(Byte.MIN_VALUE);
        ByteSubtractModification underflowModifier = new ByteSubtractModification((byte) 1);
        minByte.setModifications(underflowModifier);
        assertEquals(Byte.MAX_VALUE, minByte.getValue());

        // Test with subtracting zero
        ByteSubtractModification zeroModifier = new ByteSubtractModification((byte) 0);
        start.setModifications(zeroModifier);
        assertEquals((byte) 10, start.getValue());

        // Test with null input
        ByteSubtractModification nullModifier = new ByteSubtractModification((byte) 0);
        start.setModifications(nullModifier);
        start.setOriginalValue(null);
        assertNull(start.getValue());

        // Test with null original value
        ModifiableByte nullByte = new ModifiableByte();
        nullByte.setModifications(new ByteSubtractModification((byte) 5));
        assertNull(nullByte.getValue());

        // More comprehensive equals and hashCode tests
        ByteSubtractModification original = new ByteSubtractModification((byte) 10);

        // Same value
        ByteSubtractModification similar = new ByteSubtractModification((byte) 10);
        assertEquals(original, similar);
        assertEquals(original.hashCode(), similar.hashCode());

        // Different value
        ByteSubtractModification different = new ByteSubtractModification((byte) 20);
        assertNotEquals(original, different);
        assertNotEquals(original.hashCode(), different.hashCode());

        // Self equality
        assertEquals(original, original);

        // Null and other types
        assertNotEquals(original, null);
        assertNotEquals(original, "not a ByteSubtractModification");
        assertNotEquals(original, new ByteAddModification((byte) 10));

        // Test toString
        assertTrue(original.toString().contains("subtrahend"));
        assertTrue(original.toString().contains("10"));

        // Test getter/setter
        original.setSubtrahend((byte) 20);
        assertEquals((byte) 20, original.getSubtrahend());
    }

    /** Test of xor method, of class ByteXorModification. */
    @Test
    public void testXor() {
        VariableModification<Byte> modifier = new ByteXorModification(Byte.valueOf("2"));
        start.setModifications(modifier);
        expectedResult = Byte.valueOf("8");
        result = start.getValue();
        assertEquals(expectedResult, result);
        assertEquals(Byte.valueOf("10"), start.getOriginalValue());
    }

    /** Test edge cases for ByteXorModification */
    @Test
    public void testXorEdgeCases() {

        // Test XOR with same value (should be 0)
        ByteXorModification sameModifier = new ByteXorModification((byte) 10);
        start.setModifications(sameModifier);
        assertEquals((byte) 0, start.getValue());

        // Test with null input
        ByteXorModification nullModifier = new ByteXorModification((byte) 0);
        start.setModifications(nullModifier);
        start.setOriginalValue(null);
        assertNull(start.getValue());

        // Test with null original value
        ModifiableByte nullByte = new ModifiableByte();
        nullByte.setModifications(new ByteXorModification((byte) 5));
        assertNull(nullByte.getValue());

        // More comprehensive equals and hashCode tests
        ByteXorModification original = new ByteXorModification((byte) 10);

        // Same value
        ByteXorModification similar = new ByteXorModification((byte) 10);
        assertEquals(original, similar);
        assertEquals(original.hashCode(), similar.hashCode());

        // Different value
        ByteXorModification different = new ByteXorModification((byte) 20);
        assertNotEquals(original, different);
        assertNotEquals(original.hashCode(), different.hashCode());

        // Self equality
        assertEquals(original, original);

        // Null and other types
        assertNotEquals(original, null);
        assertNotEquals(original, "not a ByteXorModification");
        assertNotEquals(original, new ByteAddModification((byte) 10));

        // Test toString
        assertTrue(original.toString().contains("xor"));
        assertTrue(original.toString().contains("10"));

        // Test getter/setter
        original.setXor((byte) 20);
        assertEquals((byte) 20, original.getXor());
    }

    /** Test of explicitValue method, of class ByteExplicitValueModification. */
    @Test
    public void testExplicitValue() {
        VariableModification<Byte> modifier = new ByteExplicitValueModification(Byte.valueOf("7"));
        start.setModifications(modifier);
        expectedResult = Byte.valueOf("7");
        result = start.getValue();
        assertEquals(expectedResult, result);
        assertEquals(Byte.valueOf("10"), start.getOriginalValue());
    }

    /** Test edge cases for ByteExplicitValueModification */
    @Test
    public void testExplicitValueEdgeCases() {
        // Test with extreme values
        ByteExplicitValueModification maxModifier =
                new ByteExplicitValueModification(Byte.MAX_VALUE);
        start.setModifications(maxModifier);
        assertEquals(Byte.MAX_VALUE, start.getValue());

        ByteExplicitValueModification minModifier =
                new ByteExplicitValueModification(Byte.MIN_VALUE);
        start.setModifications(minModifier);
        assertEquals(Byte.MIN_VALUE, start.getValue());

        // Test with null input
        ByteExplicitValueModification nullModifier = new ByteExplicitValueModification((byte) 0);
        start.setModifications(nullModifier);
        start.setOriginalValue(null);
        assertNull(start.getValue());

        // The ByteExplicitValueModification should work with a null original value
        // (it's different from other modifications that rely on operations on the original value)
        ModifiableByte nullByte = new ModifiableByte();
        nullByte.setOriginalValue(null);
        // When we set a modification that provides an explicit value, it should work even with null
        // original
        nullByte.setModifications(new ByteExplicitValueModification((byte) 5));
        // For explicit value modifications, null inputs are still treated as null outputs for
        // consistency
        assertNull(nullByte.getValue());

        // More comprehensive equals and hashCode tests
        ByteExplicitValueModification original = new ByteExplicitValueModification((byte) 10);

        // Same value
        ByteExplicitValueModification similar = new ByteExplicitValueModification((byte) 10);
        assertEquals(original, similar);
        assertEquals(original.hashCode(), similar.hashCode());

        // Different value
        ByteExplicitValueModification different = new ByteExplicitValueModification((byte) 20);
        assertNotEquals(original, different);
        assertNotEquals(original.hashCode(), different.hashCode());

        // Self equality
        assertEquals(original, original);

        // Null and other types
        assertNotEquals(original, null);
        assertNotEquals(original, "not a ByteExplicitValueModification");
        assertNotEquals(original, new ByteAddModification((byte) 10));

        // Boundary values
        ByteExplicitValueModification min = new ByteExplicitValueModification(Byte.MIN_VALUE);
        ByteExplicitValueModification max = new ByteExplicitValueModification(Byte.MAX_VALUE);
        assertNotEquals(min, max);

        // Test toString
        assertTrue(original.toString().contains("explicitValue"));
        assertTrue(original.toString().contains("10"));

        // Test getter/setter
        original.setExplicitValue((byte) 30);
        assertEquals((byte) 30, original.getExplicitValue());
    }

    /** Test chaining multiple modifications together */
    @Test
    public void testModificationChaining() {
        // Chain add and XOR
        ByteAddModification addMod = new ByteAddModification((byte) 2);
        ByteXorModification xorMod = new ByteXorModification((byte) 3);

        start.setModifications(addMod, xorMod);
        // Original: 10, +2 = 12, XOR 3 = 15
        assertEquals((byte) 15, start.getValue());

        // Chain subtract and explicit value
        ByteSubtractModification subMod = new ByteSubtractModification((byte) 5);
        ByteExplicitValueModification explicitMod = new ByteExplicitValueModification((byte) 42);

        start.setModifications(subMod, explicitMod);
        // The subtract is applied, but then explicit value overwrites
        assertEquals((byte) 42, start.getValue());

        // Alternative chaining method using addModification
        start.clearModifications();
        start.setModifications(addMod);
        start.addModification(xorMod);
        assertEquals((byte) 15, start.getValue());
    }

    /** Test CreateCopy methods for all modification types */
    @Test
    public void testCreateCopyForModifications() {
        // Test ByteAddModification
        ByteAddModification addMod = new ByteAddModification((byte) 5);
        VariableModification<Byte> addCopy = addMod.createCopy();
        assertTrue(addCopy instanceof ByteAddModification);
        assertEquals(addMod, addCopy);

        // Test ByteSubtractModification
        ByteSubtractModification subMod = new ByteSubtractModification((byte) 5);
        VariableModification<Byte> subCopy = subMod.createCopy();
        assertTrue(subCopy instanceof ByteSubtractModification);
        assertEquals(subMod, subCopy);

        // Test ByteXorModification
        ByteXorModification xorMod = new ByteXorModification((byte) 5);
        VariableModification<Byte> xorCopy = xorMod.createCopy();
        assertTrue(xorCopy instanceof ByteXorModification);
        assertEquals(xorMod, xorCopy);

        // Test ByteExplicitValueModification
        ByteExplicitValueModification explicitMod = new ByteExplicitValueModification((byte) 5);
        VariableModification<Byte> explicitCopy = explicitMod.createCopy();
        assertTrue(explicitCopy instanceof ByteExplicitValueModification);
        assertEquals(explicitMod, explicitCopy);
    }
}
