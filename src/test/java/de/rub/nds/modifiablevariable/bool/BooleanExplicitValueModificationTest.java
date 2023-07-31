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

/**
 *
 * @author captain
 */
public class BooleanExplicitValueModificationTest {

    private BooleanExplicitValueModification b1;
    private BooleanExplicitValueModification b2;
    private BooleanExplicitValueModification b3;
    private Integer integer1;

    @Before
    public void setUp() {
        b1 = new BooleanExplicitValueModification(true);
        b2 = new BooleanExplicitValueModification(false);
        b3 = new BooleanExplicitValueModification(true);
        integer1 = 1;
    }

    /**
     * Test of modifyImplementationHook method, of class BooleanExplicitValueModification.
     */
    @Test
    public void testModifyImplementationHook() {
    }

    /**
     * Test of isExplicitValue method, of class BooleanExplicitValueModification.
     */
    @Test
    public void testIsExplicitValue() {
    }

    /**
     * Test of setExplicitValue method, of class BooleanExplicitValueModification.
     */
    @Test
    public void testSetExplicitValue() {
    }

    /**
     * Test of getModifiedCopy method, of class BooleanExplicitValueModification.
     */
    @Test
    public void testGetModifiedCopy() {
    }

    /**
     * Test of hashCode method, of class BooleanExplicitValueModification.
     */
    @Test
    public void testHashCode() {
    }

    /**
     * Test of equals method, of class BooleanExplicitValueModification.
     */
    @Test
    public void testEquals() {
    }

    /**
     * Test of toString method, of class BooleanExplicitValueModification.
     */
    @Test
    public void testToString() {
        assertFalse(b1.equals(b2));
        assertFalse(b1.equals(integer1));
        assertTrue(b1.equals(b1));
        assertTrue(b1.equals(b3));
    }

}
