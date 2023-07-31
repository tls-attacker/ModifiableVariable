/**
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 *
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.modifiablevariable.bool;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ModifiableBooleanTest {

    private ModifiableBoolean boolean1;

    private ModifiableBoolean boolean2;

    @Before
    public void setUp() {
        boolean1 = new ModifiableBoolean();
        boolean1.setOriginalValue(Boolean.TRUE);
        boolean2 = new ModifiableBoolean();
        boolean2.setOriginalValue(Boolean.TRUE);
    }

    /**
     * Test of getOriginalValue method, of class ModifiableBoolean.
     */
    @Test
    public void testGetOriginalValue() {
    }

    /**
     * Test of setOriginalValue method, of class ModifiableBoolean.
     */
    @Test
    public void testSetOriginalValue() {
    }

    /**
     * Test of createRandomModification method, of class ModifiableBoolean.
     */
    @Test
    public void testCreateRandomModification() {
    }

    /**
     * Test of isOriginalValueModified method, of class ModifiableBoolean.
     */
    @Test
    public void testIsOriginalValueModified() {
    }

    /**
     * Test of validateAssertions method, of class ModifiableBoolean.
     */
    @Test
    public void testValidateAssertions() {
    }

    /**
     * Test of toString method, of class ModifiableBoolean.
     */
    @Test
    public void testToString() {
    }

    /**
     * Test of equals method, of class ModifiableBoolean.
     */
    @Test
    public void testEquals() {
        assertEquals(boolean1, boolean2);
        boolean2.setOriginalValue(Boolean.FALSE);
        assertNotEquals(boolean1, boolean2);
    }

    /**
     * Test of hashCode method, of class ModifiableBoolean.
     */
    @Test
    public void testHashCode() {
    }

}
