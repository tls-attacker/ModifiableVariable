/**
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 *
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.modifiablevariable.singlebyte;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ModifiableByteTest {

    private ModifiableByte byte1;
    private ModifiableByte byte2;

    @Before
    public void setUp() {
        byte1 = new ModifiableByte();
        byte1.setOriginalValue((byte) 3);
        byte2 = new ModifiableByte();
        byte2.setOriginalValue((byte) 3);
    }

    /**
     * Test of createRandomModification method, of class ModifiableByte.
     */
    @Test
    public void testCreateRandomModification() {
    }

    /**
     * Test of getAssertEquals method, of class ModifiableByte.
     */
    @Test
    public void testGetAssertEquals() {
    }

    /**
     * Test of setAssertEquals method, of class ModifiableByte.
     */
    @Test
    public void testSetAssertEquals() {
    }

    /**
     * Test of isOriginalValueModified method, of class ModifiableByte.
     */
    @Test
    public void testIsOriginalValueModified() {
    }

    /**
     * Test of validateAssertions method, of class ModifiableByte.
     */
    @Test
    public void testValidateAssertions() {
    }

    /**
     * Test of getOriginalValue method, of class ModifiableByte.
     */
    @Test
    public void testGetOriginalValue() {
    }

    /**
     * Test of setOriginalValue method, of class ModifiableByte.
     */
    @Test
    public void testSetOriginalValue() {
    }

    /**
     * Test of toString method, of class ModifiableByte.
     */
    @Test
    public void testToString() {
    }

    /**
     * Test of equals method, of class ModifiableByte.
     */
    @Test
    public void testEquals() {
        assertEquals(byte1, byte2);
        byte2.setOriginalValue((byte) 4);
        assertNotEquals(byte1, byte2);
    }

    /**
     * Test of hashCode method, of class ModifiableByte.
     */
    @Test
    public void testHashCode() {
    }

}
