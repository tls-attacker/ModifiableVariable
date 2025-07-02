/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.bool;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BooleanExplicitValueModificationTest {

    private BooleanExplicitValueModification b1;
    private BooleanExplicitValueModification b2;
    private BooleanExplicitValueModification b3;

    @BeforeEach
    void setUp() {
        b1 = new BooleanExplicitValueModification(true);
        b2 = new BooleanExplicitValueModification(false);
        b3 = new BooleanExplicitValueModification(true);
    }

    /** Test constructor with value */
    @Test
    void testConstructorWithValue() {
        BooleanExplicitValueModification modification = new BooleanExplicitValueModification(true);
        assertTrue(modification.getExplicitValue());

        BooleanExplicitValueModification modification2 =
                new BooleanExplicitValueModification(false);
        assertFalse(modification2.getExplicitValue());
    }

    /** Test copy constructor */
    @Test
    void testCopyConstructor() {
        BooleanExplicitValueModification original = new BooleanExplicitValueModification(true);
        BooleanExplicitValueModification copy = new BooleanExplicitValueModification(original);

        assertEquals(original.getExplicitValue(), copy.getExplicitValue());
        assertEquals(original, copy);
        assertNotEquals(System.identityHashCode(original), System.identityHashCode(copy));
    }

    /** Test of modifyImplementationHook method, of class BooleanExplicitValueModification. */
    @Test
    void testModifyImplementationHook() {
        assertTrue(b1.modifyImplementationHook(true));
        assertTrue(b1.modifyImplementationHook(false));
    }

    /** Test of modifyImplementationHook method with null input. */
    @Test
    void testModifyImplementationHookNullInput() {
        assertNull(b1.modifyImplementationHook(null));
    }

    /** Test of getExplicitValue method, of class BooleanExplicitValueModification. */
    @Test
    void testGetExplicitValue() {
        assertTrue(b1.getExplicitValue());
        assertFalse(b2.getExplicitValue());
    }

    /** Test of setExplicitValue method, of class BooleanExplicitValueModification. */
    @Test
    void testSetExplicitValue() {
        b2.setExplicitValue(true);
        assertTrue(b2.getExplicitValue());
    }

    /** Test of createCopy method */
    @Test
    void testCreateCopy() {
        BooleanExplicitValueModification copy = b1.createCopy();

        assertEquals(b1.getExplicitValue(), copy.getExplicitValue());
        assertEquals(b1, copy);
        assertNotEquals(System.identityHashCode(b1), System.identityHashCode(copy));
    }

    /** Test of hashCode method, of class BooleanExplicitValueModification. */
    @Test
    void testHashCode() {
        assertEquals(b3.hashCode(), b1.hashCode());
        assertNotEquals(b1.hashCode(), b2.hashCode());
    }

    /** Test of equals method with equal objects */
    @Test
    void testEqualsTrue() {
        assertEquals(b1, b3);
        assertEquals(b1, b1); // Same object
    }

    /** Test of equals method with unequal objects */
    @Test
    void testEqualsFalse() {
        assertNotEquals(b1, b2);
        assertNotEquals(b1, null);
        assertNotEquals(b1, new Object());
    }

    /** Test of toString method, of class BooleanExplicitValueModification. */
    @Test
    void testToString() {
        String expected1 = "BooleanExplicitValueModification{explicitValue=true}";
        String expected2 = "BooleanExplicitValueModification{explicitValue=false}";

        assertEquals(expected1, b1.toString());
        assertEquals(expected2, b2.toString());
    }
}
