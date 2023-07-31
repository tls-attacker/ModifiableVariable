/**
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 *
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.modifiablevariable.assertion;

import de.rub.nds.modifiablevariable.VariableModification;
import de.rub.nds.modifiablevariable.bytearray.ModifiableByteArray;
import de.rub.nds.modifiablevariable.integer.IntegerModificationFactory;
import de.rub.nds.modifiablevariable.integer.ModifiableInteger;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class AssertionTest {

    private ModifiableInteger mi;

    private ModifiableByteArray mba;

    public AssertionTest() {
    }

    @Before
    public void setUp() {
        mi = new ModifiableInteger();
        mi.setOriginalValue(10);
        mba = new ModifiableByteArray();
        mba.setOriginalValue(new byte[] { 0, 1 });
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
        VariableModification<Integer> modifier = IntegerModificationFactory.add(1);
        mi.setModification(modifier);
        mi.setAssertEquals(11);
        assertTrue(mi.validateAssertions());
    }

    @Test
    public void testAssertionByteArray() {
        mba.setAssertEquals(new byte[] { 0, 1 });
        assertTrue(mba.validateAssertions());
        mba.setAssertEquals(new byte[] { 0, 0 });
        assertFalse(mba.validateAssertions());
    }

}
