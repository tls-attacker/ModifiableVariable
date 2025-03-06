/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.integer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import de.rub.nds.modifiablevariable.VariableModification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IntegerModificationTest {

    private ModifiableInteger start;
    private Integer expectedResult, result;

    @BeforeEach
    void setUp() {
        start = new ModifiableInteger();
        start.setOriginalValue(10);
        expectedResult = null;
        result = null;
    }

    /** Test of add method, of class IntegerModification. */
    @Test
    void testAdd() {
        VariableModification<Integer> modifier = new IntegerAddModification(1);
        start.setModifications(modifier);
        expectedResult = 11;
        result = start.getValue();
        assertEquals(expectedResult, result);
        assertEquals(10, start.getOriginalValue());
    }

    /** Test of add method with String, of class IntegerModification. */
    @Test
    void testAddWithString() {
        VariableModification<Integer> modifier = new IntegerAddModification(1);
        start.setModifications(modifier);
        expectedResult = 11;
        result = start.getValue();
        assertEquals(expectedResult, result);
    }

    /** Test of add method with null input, of class IntegerModification. */
    @Test
    void testAddWithNullInput() {
        VariableModification<Integer> modifier = new IntegerAddModification(1);
        start.setOriginalValue(null);
        start.setModifications(modifier);
        expectedResult = 1;
        result = start.getValue();
        assertEquals(expectedResult, result);
    }

    /** Test of sub method, of class IntegerModification. */
    @Test
    void testSub() {
        VariableModification<Integer> modifier = new IntegerSubtractModification(1);
        start.setModifications(modifier);
        expectedResult = 9;
        result = start.getValue();
        assertEquals(expectedResult, result);
        assertEquals(10, start.getOriginalValue());
    }

    /** Test of sub method with null input, of class IntegerModification. */
    @Test
    void testSubWithNullInput() {
        VariableModification<Integer> modifier = new IntegerSubtractModification(1);
        start.setOriginalValue(null);
        start.setModifications(modifier);
        expectedResult = -1;
        result = start.getValue();
        assertEquals(expectedResult, result);
    }

    /** Test of xor method, of class IntegerModification. */
    @Test
    void testXor() {
        VariableModification<Integer> modifier = new IntegerXorModification(2);
        start.setModifications(modifier);
        expectedResult = 8;
        result = start.getValue();
        assertEquals(expectedResult, result);
        assertEquals(10, start.getOriginalValue());
    }

    /** Test of xor method with null input, of class IntegerModification. */
    @Test
    void testXorWithNullInput() {
        VariableModification<Integer> modifier = new IntegerXorModification(2);
        start.setOriginalValue(null);
        start.setModifications(modifier);
        expectedResult = 2;
        result = start.getValue();
        assertEquals(expectedResult, result);
    }

    /** Test of explicitValue method, of class IntegerModification. */
    @Test
    void testExplicitValue() {
        VariableModification<Integer> modifier = new IntegerExplicitValueModification(7);
        start.setModifications(modifier);
        expectedResult = 7;
        result = start.getValue();
        assertEquals(expectedResult, result);
        assertEquals(10, start.getOriginalValue());
    }

    /** Test of explicitValue method with String, of class IntegerModification. */
    @Test
    void testExplicitValueWithString() {
        VariableModification<Integer> modifier =
                new IntegerExplicitValueModification(7);
        start.setModifications(modifier);
        expectedResult = 7;
        result = start.getValue();
        assertEquals(expectedResult, result);
    }

    /** Test of explicitValue method with null input, of class IntegerModification. */
    @Test
    void testExplicitValueWithNullInput() {
        VariableModification<Integer> modifier = new IntegerExplicitValueModification(7);
        start.setOriginalValue(null);
        start.setModifications(modifier);
        expectedResult = 7;
        result = start.getValue();
        assertEquals(expectedResult, result);
    }

    /** Test of shiftLeft method, of class IntegerModification. */
    @Test
    void testShiftLeft() {
        VariableModification<Integer> modifier = new IntegerShiftLeftModification(2);
        start.setModifications(modifier);
        expectedResult = 40;
        result = start.getValue();
        assertEquals(expectedResult, result);
        assertEquals(10, start.getOriginalValue());
    }

    /** Test of shiftLeft method with String, of class IntegerModification. */
    @Test
    void testShiftLeftWithString() {
        VariableModification<Integer> modifier = new IntegerShiftLeftModification(2);
        start.setModifications(modifier);
        expectedResult = 40;
        result = start.getValue();
        assertEquals(expectedResult, result);
    }

    /** Test of shiftRight method, of class IntegerModification. */
    @Test
    void testShiftRight() {
        VariableModification<Integer> modifier = new IntegerShiftRightModification(2);
        start.setModifications(modifier);
        expectedResult = 2;
        result = start.getValue();
        assertEquals(expectedResult, result);
        assertEquals(10, start.getOriginalValue());
    }

    /** Test of shiftRight method with String, of class IntegerModification. */
    @Test
    void testShiftRightWithString() {
        VariableModification<Integer> modifier = new IntegerShiftRightModification(2);
        start.setModifications(modifier);
        expectedResult = 2;
        result = start.getValue();
        assertEquals(expectedResult, result);
    }

    /** Test of swapEndian method, of class IntegerModification. */
    @Test
    void testSwapEndian() {
        VariableModification<Integer> modifier = new IntegerSwapEndianModification();
        start.setOriginalValue(0x12345678);
        start.setModifications(modifier);
        expectedResult = 0x78563412;
        result = start.getValue();
        assertEquals(expectedResult, result);
    }

    /** Test of swapEndian method with null input, of class IntegerModification. */
    @Test
    void testSwapEndianWithNullInput() {
        VariableModification<Integer> modifier = new IntegerSwapEndianModification();
        start.setOriginalValue(null);
        start.setModifications(modifier);
        assertNull(start.getValue());
    }

    /** Test of multiply method, of class IntegerModification. */
    @Test
    void testMultiply() {
        VariableModification<Integer> modifier = new IntegerMultiplyModification(2);
        start.setModifications(modifier);
        expectedResult = 20;
        result = start.getValue();
        assertEquals(expectedResult, result);
        assertEquals(10, start.getOriginalValue());
    }

    /** Test of multiply method with negative value, of class IntegerModification. */
    @Test
    void testMultiplyWithNegativeValue() {
        VariableModification<Integer> modifier = new IntegerMultiplyModification(-1);
        start.setModifications(modifier);
        expectedResult = -10;
        result = start.getValue();
        assertEquals(expectedResult, result);
    }

    /** Test of multiply method with zero, of class IntegerModification. */
    @Test
    void testMultiplyWithZero() {
        VariableModification<Integer> modifier = new IntegerMultiplyModification(0);
        start.setModifications(modifier);
        expectedResult = 0;
        result = start.getValue();
        assertEquals(expectedResult, result);
    }

    /** Test of multiply method with null input, of class IntegerModification. */
    @Test
    void testMultiplyWithNullInput() {
        VariableModification<Integer> modifier = new IntegerMultiplyModification(2);
        start.setOriginalValue(null);
        start.setModifications(modifier);
        expectedResult = 0;
        result = start.getValue();
        assertEquals(expectedResult, result);
    }

    /** Test of multiple modifications, of class IntegerModification. */
    @Test
    void testMultipleModifications() {
        // Instead of chaining, apply modifications one after another
        VariableModification<Integer> addModifier = new IntegerAddModification(5);
        VariableModification<Integer> multiplyModifier = new IntegerMultiplyModification(2);
        start.setModifications(addModifier, multiplyModifier);
        expectedResult = 30; // (10 + 5) * 2
        result = start.getValue();
        assertEquals(expectedResult, result);
    }
}
