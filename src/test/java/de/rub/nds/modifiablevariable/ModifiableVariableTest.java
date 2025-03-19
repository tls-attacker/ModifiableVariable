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
