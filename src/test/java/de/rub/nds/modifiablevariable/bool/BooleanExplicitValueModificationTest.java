/**
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Copyright 2014-2022 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 *
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.modifiablevariable.bool;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class BooleanExplicitValueModificationTest {

    private BooleanExplicitValueModification b1;
    private BooleanExplicitValueModification b2;
    private BooleanExplicitValueModification b3;
    private Integer integer1;

    @BeforeEach
    public void setUp() {
        b1 = new BooleanExplicitValueModification(true);
        b2 = new BooleanExplicitValueModification(false);
        b3 = new BooleanExplicitValueModification(true);
        integer1 = 1;
    }

    /**
     * Test of modifyImplementationHook method, of class BooleanExplicitValueModification.
     */
    @Disabled("Not yet implemented")
    @Test
    public void testModifyImplementationHook() {
    }

    /**
     * Test of isExplicitValue method, of class BooleanExplicitValueModification.
     */
    @Disabled("Not yet implemented")
    @Test
    public void testIsExplicitValue() {
    }

    /**
     * Test of setExplicitValue method, of class BooleanExplicitValueModification.
     */
    @Disabled("Not yet implemented")
    @Test
    public void testSetExplicitValue() {
    }

    /**
     * Test of getModifiedCopy method, of class BooleanExplicitValueModification.
     */
    @Disabled("Not yet implemented")
    @Test
    public void testGetModifiedCopy() {
    }

    /**
     * Test of hashCode method, of class BooleanExplicitValueModification.
     */
    @Disabled("Not yet implemented")
    @Test
    public void testHashCode() {
    }

    /**
     * Test of equals method, of class BooleanExplicitValueModification.
     */
    @Disabled("Not yet implemented")
    @Test
    public void testEquals() {
    }

    /**
     * Test of toString method, of class BooleanExplicitValueModification.
     */
    @Test
    public void testToString() {
        assertNotEquals(b1, b2);
        assertNotEquals(b1, integer1);
        assertEquals(b1, b1);
        assertEquals(b1, b3);
    }

}
