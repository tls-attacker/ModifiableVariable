/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.integer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ModifiableIntegerTest {

    private ModifiableInteger integer1;

    private ModifiableInteger integer2;

    @BeforeEach
    public void setUp() {
        integer1 = new ModifiableInteger();
        integer1.setOriginalValue(2);
        integer2 = new ModifiableInteger();
        integer2.setOriginalValue(2);
    }

    /** Test of createRandomModification method, of class ModifiableInteger. */
    @Test
    public void testCreateRandomModification() {
        integer1.createRandomModification();
        assertNotEquals(integer1.getValue(), integer2.getValue());
    }

    /** Test of getAssertEquals method, of class ModifiableInteger. */
    @Test
    public void testGetAssertEquals() {
        assertEquals(integer1.getAssertEquals(), integer2.getAssertEquals());
    }

    /** Test of setAssertEquals method, of class ModifiableInteger. */
    @Test
    public void testSetAssertEquals() {
        integer1.setAssertEquals(3);
        assertNotEquals(integer1.getAssertEquals(), integer2.getAssertEquals());
    }

    /** Test of isOriginalValueModified method, of class ModifiableInteger. */
    @Test
    public void testIsOriginalValueModified() {
        integer1.createRandomModification();
        assertTrue(integer1.isOriginalValueModified());
        assertFalse(integer2.isOriginalValueModified());
    }

    /** Test of getByteArray method, of class ModifiableInteger. */
    @Test
    public void testGetByteArray() {
        assertArrayEquals(integer1.getByteArray(2), integer2.getByteArray(2));
    }

    /** Test of validateAssertions method, of class ModifiableInteger. */
    @Test
    public void testValidateAssertions() {
        assertTrue(integer1.validateAssertions());
        integer1.setOriginalValue(null);
        assertTrue(integer1.validateAssertions());
    }

    /** Test of getOriginalValue method, of class ModifiableInteger. */
    @Test
    public void testGetOriginalValue() {
        integer1.createRandomModification();
        assertEquals(integer1.getOriginalValue(), integer2.getOriginalValue());
        assertNotEquals(integer1.getValue(), integer2.getValue());
    }

    /** Test of setOriginalValue method, of class ModifiableInteger. */
    @Test
    public void testSetOriginalValue() {
        integer2.setOriginalValue(3);
        assertNotEquals(integer1.getOriginalValue(), integer2.getOriginalValue());
    }

    /** Test of toString method, of class ModifiableInteger. */
    @Test
    public void testToString() {
        assertEquals(integer1.toString(), integer2.toString());
        assertNotEquals(integer1.getOriginalValue(), integer1.toString());
    }

    /** Test of equals method, of class ModifiableInteger. */
    @Test
    public void testEquals() {
        assertEquals(integer1, integer2);
        integer2.setOriginalValue(3);
        assertNotEquals(integer1, integer2);
    }

    /** Test of hashCode method, of class ModifiableInteger. */
    @Test
    public void testHashCode() {
        assertEquals(integer1.hashCode(), integer2.hashCode());
        integer1.setOriginalValue(4);
        assertNotEquals(integer1.hashCode(), integer2.hashCode());
    }
}
