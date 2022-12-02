/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Copyright 2014-2022 Ruhr University Bochum, Paderborn University, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.bool;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class BooleanExplicitValueModificationTest {

    private BooleanExplicitValueModification b1;
    private BooleanExplicitValueModification b2;
    private BooleanExplicitValueModification b3;
    private BooleanExplicitValueModification b4;
    private Integer integer1 = 1;

    @BeforeEach
    public void setUp() {
        b1 = new BooleanExplicitValueModification(true);
        b2 = new BooleanExplicitValueModification(false);
        b3 = new BooleanExplicitValueModification(true);
        b4 = new BooleanExplicitValueModification(false);
    }

    /** Test of modifyImplementationHook method, of class BooleanExplicitValueModification. */
    @Test
    public void testModifyImplementationHook() {}

    /** Test of isExplicitValue method, of class BooleanExplicitValueModification. */
    @Test
    public void testIsExplicitValue() {
        assertTrue(b1.isExplicitValue());
        assertFalse(b2.isExplicitValue());
    }

    /** Test of setExplicitValue method, of class BooleanExplicitValueModification. */
    @Test
    public void testSetExplicitValue() {
        b4.setExplicitValue(true);
        assertEquals(b1, b4);
        b4.setExplicitValue(false);
        assertEquals(b2, b4);
    }

    /** Test of getModifiedCopy method, of class BooleanExplicitValueModification. */
    @Test
    public void testGetModifiedCopy() {
        assertNotEquals(b4, b4.getModifiedCopy());
        assertEquals(b1,b4.getModifiedCopy());
    }

    /** Test of hashCode method, of class BooleanExplicitValueModification. */
    @Test
    public void testHashCode() {
        assertEquals(204,b1.hashCode());
        assertEquals(203,b2.hashCode());
    }

    /** Test of equals method, of class BooleanExplicitValueModification. */
    @Test
    public void testEquals() {
        assertTrue(b1.equals(b3));
        assertFalse(b1.equals(b2));
    }

    /** Test of toString method, of class BooleanExplicitValueModification. */
    @Test
    public void testToString() {
        assertNotEquals(b1, b2);
        assertNotEquals(b1, integer1);
        assertEquals(b1, b1);
        assertEquals(b1, b3);
    }
}
