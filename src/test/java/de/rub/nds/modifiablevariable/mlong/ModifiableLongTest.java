/**
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 *
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.modifiablevariable.mlong;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ModifiableLongTest {

    private ModifiableLong long1;

    private ModifiableLong long2;

    @Before
    public void setUp() {
        long1 = new ModifiableLong();
        long1.setOriginalValue(2l);
        long2 = new ModifiableLong();
        long2.setOriginalValue(2l);
    }

    /**
     * Test of createRandomModification method, of class ModifiableLong.
     */
    @Test
    public void testCreateRandomModification() {
    }

    /**
     * Test of getAssertEquals method, of class ModifiableLong.
     */
    @Test
    public void testGetAssertEquals() {
    }

    /**
     * Test of setAssertEquals method, of class ModifiableLong.
     */
    @Test
    public void testSetAssertEquals() {
    }

    /**
     * Test of isOriginalValueModified method, of class ModifiableLong.
     */
    @Test
    public void testIsOriginalValueModified() {
    }

    /**
     * Test of getByteArray method, of class ModifiableLong.
     */
    @Test
    public void testGetByteArray() {
    }

    /**
     * Test of validateAssertions method, of class ModifiableLong.
     */
    @Test
    public void testValidateAssertions() {
    }

    /**
     * Test of getOriginalValue method, of class ModifiableLong.
     */
    @Test
    public void testGetOriginalValue() {
    }

    /**
     * Test of setOriginalValue method, of class ModifiableLong.
     */
    @Test
    public void testSetOriginalValue() {
    }

    /**
     * Test of toString method, of class ModifiableLong.
     */
    @Test
    public void testToString() {
    }

    /**
     * Test of equals method, of class ModifiableLong.
     */
    @Test
    public void testEquals() {
        assertEquals(long1, long2);
        long2.setOriginalValue(3l);
        assertNotEquals(long1, long2);
    }

    /**
     * Test of hashCode method, of class ModifiableLong.
     */
    @Test
    public void testHashCode() {
    }

}
