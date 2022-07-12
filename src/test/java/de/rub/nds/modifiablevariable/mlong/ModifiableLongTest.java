/**
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Copyright 2014-2022 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 *
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.modifiablevariable.mlong;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class ModifiableLongTest {

    private ModifiableLong long1;

    private ModifiableLong long2;

    @BeforeEach
    public void setUp() {
        long1 = new ModifiableLong();
        long1.setOriginalValue(2L);
        long2 = new ModifiableLong();
        long2.setOriginalValue(2L);
    }

    /**
     * Test of createRandomModification method, of class ModifiableLong.
     */
    @Disabled("Not yet implemented")
    @Test
    public void testCreateRandomModification() {
    }

    /**
     * Test of getAssertEquals method, of class ModifiableLong.
     */
    @Disabled("Not yet implemented")
    @Test
    public void testGetAssertEquals() {
    }

    /**
     * Test of setAssertEquals method, of class ModifiableLong.
     */
    @Disabled("Not yet implemented")
    @Test
    public void testSetAssertEquals() {
    }

    /**
     * Test of isOriginalValueModified method, of class ModifiableLong.
     */
    @Disabled("Not yet implemented")
    @Test
    public void testIsOriginalValueModified() {
    }

    /**
     * Test of getByteArray method, of class ModifiableLong.
     */
    @Disabled("Not yet implemented")
    @Test
    public void testGetByteArray() {
    }

    /**
     * Test of validateAssertions method, of class ModifiableLong.
     */
    @Disabled("Not yet implemented")
    @Test
    public void testValidateAssertions() {
    }

    /**
     * Test of getOriginalValue method, of class ModifiableLong.
     */
    @Disabled("Not yet implemented")
    @Test
    public void testGetOriginalValue() {
    }

    /**
     * Test of setOriginalValue method, of class ModifiableLong.
     */
    @Disabled("Not yet implemented")
    @Test
    public void testSetOriginalValue() {
    }

    /**
     * Test of toString method, of class ModifiableLong.
     */
    @Disabled("Not yet implemented")
    @Test
    public void testToString() {
    }

    /**
     * Test of equals method, of class ModifiableLong.
     */
    @Test
    public void testEquals() {
        assertEquals(long1, long2);
        long2.setOriginalValue(3L);
        assertNotEquals(long1, long2);
    }

    /**
     * Test of hashCode method, of class ModifiableLong.
     */
    @Disabled("Not yet implemented")
    @Test
    public void testHashCode() {
    }

}
