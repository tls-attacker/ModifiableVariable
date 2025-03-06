/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.assertion;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import de.rub.nds.modifiablevariable.VariableModification;
import de.rub.nds.modifiablevariable.bytearray.ModifiableByteArray;
import de.rub.nds.modifiablevariable.integer.IntegerAddModification;
import de.rub.nds.modifiablevariable.integer.ModifiableInteger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AssertionTest {

    private ModifiableInteger mi;

    private ModifiableByteArray mba;

    @BeforeEach
    public void setUp() {
        mi = new ModifiableInteger();
        mi.setOriginalValue(10);
        mba = new ModifiableByteArray();
        mba.setOriginalValue(new byte[] {0, 1});
    }

    @Test
    public void testAssertionInteger() {
        mi.setAssertEquals(10);
        assertTrue(mi.validateAssertions());
        mi.setAssertEquals(0);
        assertFalse(mi.validateAssertions());
    }

    @Test
    public void testAddInteger() {
        VariableModification<Integer> modifier = new IntegerAddModification(1);
        mi.setModifications(modifier);
        mi.setAssertEquals(11);
        assertTrue(mi.validateAssertions());
    }

    @Test
    public void testAssertionByteArray() {
        mba.setAssertEquals(new byte[] {0, 1});
        assertTrue(mba.validateAssertions());
        mba.setAssertEquals(new byte[] {0, 0});
        assertFalse(mba.validateAssertions());
    }
}
