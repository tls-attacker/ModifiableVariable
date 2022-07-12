/**
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Copyright 2014-2022 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 *
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.modifiablevariable.integer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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

    /**
     * Test of createRandomModification method, of class ModifiableInteger.
     */
    @Disabled("Not yet implemented")
    @Test
    public void testCreateRandomModification() {
    }

    /**
     * Test of getAssertEquals method, of class ModifiableInteger.
     */
    @Disabled("Not yet implemented")
    @Test
    public void testGetAssertEquals() {
    }

    /**
     * Test of setAssertEquals method, of class ModifiableInteger.
     */
    @Disabled("Not yet implemented")
    @Test
    public void testSetAssertEquals() {
    }

    /**
     * Test of isOriginalValueModified method, of class ModifiableInteger.
     */
    @Disabled("Not yet implemented")
    @Test
    public void testIsOriginalValueModified() {
    }

    /**
     * Test of getByteArray method, of class ModifiableInteger.
     */
    @Disabled("Not yet implemented")
    @Test
    public void testGetByteArray() {
    }

    /**
     * Test of validateAssertions method, of class ModifiableInteger.
     */
    @Disabled("Not yet implemented")
    @Test
    public void testValidateAssertions() {
    }

    /**
     * Test of getOriginalValue method, of class ModifiableInteger.
     */
    @Disabled("Not yet implemented")
    @Test
    public void testGetOriginalValue() {
    }

    /**
     * Test of setOriginalValue method, of class ModifiableInteger.
     */
    @Disabled("Not yet implemented")
    @Test
    public void testSetOriginalValue() {
    }

    /**
     * Test of toString method, of class ModifiableInteger.
     */
    @Disabled("Not yet implemented")
    @Test
    public void testToString() {
    }

    /**
     * Test of equals method, of class ModifiableInteger.
     */
    @Test
    public void testEquals() {
        assertEquals(integer1, integer2);
        integer2.setOriginalValue(3);
        assertNotEquals(integer1, integer2);
    }

    /**
     * Test of hashCode method, of class ModifiableInteger.
     */
    @Disabled("Not yet implemented")
    @Test
    public void testHashCode() {
    }

}
