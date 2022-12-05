/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Copyright 2014-2022 Ruhr University Bochum, Paderborn University, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.bool;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BooleanExplicitValueModificationTest {

    private BooleanExplicitValueModification b1;
    private BooleanExplicitValueModification b2;
    private BooleanExplicitValueModification b3;

    @BeforeEach
    public void setUp() {
        b1 = new BooleanExplicitValueModification(true);
        b2 = new BooleanExplicitValueModification(false);
        b3 = new BooleanExplicitValueModification(true);
    }

    /** Test of modifyImplementationHook method, of class BooleanExplicitValueModification. */
    @Test
    public void testModifyImplementationHook() {
        assertTrue(b1.modifyImplementationHook(true));
        assertTrue(b1.modifyImplementationHook(false));
    }

    /** Test of getExplicitValue method, of class BooleanExplicitValueModification. */
    @Test
    public void testGetExplicitValue() {
        assertTrue(b1.getExplicitValue());
        assertFalse(b2.getExplicitValue());
    }

    /** Test of setExplicitValue method, of class BooleanExplicitValueModification. */
    @Test
    public void testSetExplicitValue() {
        b2.setExplicitValue(true);
        assertTrue(b2.getExplicitValue());
    }

    /** Test of getModifiedCopy method, of class BooleanExplicitValueModification. */
    @Test
    public void testGetModifiedCopy() {
        assertNotEquals(b1, b1.getModifiedCopy());
    }

    /** Test of hashCode method, of class BooleanExplicitValueModification. */
    @Test
    public void testHashCode() {
        assertEquals(b3.hashCode(), b1.hashCode());
    }

    /** Test of equals method, of class BooleanExplicitValueModification. */
    @Test
    public void testEquals() {
        assertEquals(b1, b3);
        assertNotEquals(b1, b2);
    }

    /** Test of toString method, of class BooleanExplicitValueModification. */
    @Test
    public void testToString() {
        assertNotEquals(b1, b2);
        assertEquals(b1, b1);
        assertEquals(b1, b3);
    }
}
