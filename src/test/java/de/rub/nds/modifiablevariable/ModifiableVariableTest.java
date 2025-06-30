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
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class ModifiableVariableTest {

    /** Test setModifications with a list of modifications. */
    @Test
    void testSetModifications() {
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
    void testClearModifications() {
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
    static void testAddModificationWithNull() {
        ModifiableInteger integer = new ModifiableInteger();
        integer.setOriginalValue(100);

        // Add null modification - should be ignored
        integer.addModification(null);

        // Should return original value
        assertEquals(100, integer.getValue());
    }

    /** Test copy constructor with modifications. */
    @Test
    void testCopyConstructorWithModifications() {
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

    /** Test getModifications method. */
    @Test
    void testGetModifications() {
        ModifiableInteger integer = new ModifiableInteger();
        integer.setOriginalValue(100);

        // Initially no modifications
        assertNull(integer.getModifications());

        // Add modifications
        IntegerAddModification addMod = new IntegerAddModification(50);
        IntegerMultiplyModification multiplyMod = new IntegerMultiplyModification(2);
        integer.addModification(addMod);
        integer.addModification(multiplyMod);

        // Get modifications
        List<VariableModification<Integer>> mods = integer.getModifications();
        assertNotNull(mods);
        assertEquals(2, mods.size());
        assertEquals(addMod, mods.get(0));
        assertEquals(multiplyMod, mods.get(1));

        // Clear modifications
        integer.clearModifications();
        assertNull(integer.getModifications());
    }

    /** Test containsAssertion method. */
    @Test
    void testContainsAssertion() {
        ModifiableInteger integer = new ModifiableInteger();

        // Initially no assertion
        assertFalse(integer.containsAssertion());

        // Set assertEquals value
        integer.setAssertEquals(150);
        assertTrue(integer.containsAssertion());

        // Create new instance without assertion
        ModifiableInteger noAssertion = new ModifiableInteger();
        assertFalse(noAssertion.containsAssertion());
    }

    /** Test innerToString method through subclass toString. */
    @Test
    void testInnerToString() {
        // Test with no modifications or assertions
        ModifiableInteger integer1 = new ModifiableInteger();
        integer1.setOriginalValue(100);
        String str1 = integer1.toString();
        assertTrue(str1.contains("originalValue=100"));
        assertFalse(str1.contains("modifications="));
        assertFalse(str1.contains("assertEquals="));

        // Test with modifications
        ModifiableInteger integer2 = new ModifiableInteger();
        integer2.setOriginalValue(100);
        integer2.addModification(new IntegerAddModification(50));
        integer2.addModification(new IntegerMultiplyModification(2));
        String str2 = integer2.toString();
        assertTrue(str2.contains("originalValue=100"));
        assertTrue(str2.contains("modifications=["));
        assertTrue(str2.contains("IntegerAddModification"));
        assertTrue(str2.contains("IntegerMultiplyModification"));

        // Test with assertions
        ModifiableInteger integer3 = new ModifiableInteger();
        integer3.setOriginalValue(100);
        integer3.setAssertEquals(150);
        String str3 = integer3.toString();
        assertTrue(str3.contains("originalValue=100"));
        assertTrue(str3.contains("assertEquals=150"));

        // Test with both modifications and assertions
        ModifiableInteger integer4 = new ModifiableInteger();
        integer4.setOriginalValue(100);
        integer4.addModification(new IntegerAddModification(50));
        integer4.setAssertEquals(150);
        String str4 = integer4.toString();
        assertTrue(str4.contains("originalValue=100"));
        assertTrue(str4.contains("modifications=["));
        assertTrue(str4.contains("assertEquals=150"));
    }
}
