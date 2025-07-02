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

class BooleanToggleModificationTest {

    private BooleanToggleModification modification1;
    private BooleanToggleModification modification2;

    @BeforeEach
    void setUp() {
        modification1 = new BooleanToggleModification();
        modification2 = new BooleanToggleModification();
    }

    /** Test of modifyImplementationHook method with true input */
    @Test
    void testModifyImplementationHookTrue() {
        assertFalse(modification1.modifyImplementationHook(true));
    }

    /** Test of modifyImplementationHook method with false input */
    @Test
    void testModifyImplementationHookFalse() {
        assertTrue(modification1.modifyImplementationHook(false));
    }

    /** Test of modifyImplementationHook method with null input */
    @Test
    void testModifyImplementationHookNull() {
        assertNull(modification1.modifyImplementationHook(null));
    }

    /** Test of createCopy method */
    @Test
    void testCreateCopy() {
        BooleanToggleModification copy = modification1.createCopy();
        assertEquals(modification1, copy);
        assertNotEquals(System.identityHashCode(modification1), System.identityHashCode(copy));
    }

    /** Test of hashCode method */
    @Test
    void testHashCode() {
        assertEquals(modification1.hashCode(), modification2.hashCode());
    }

    /** Test of equals method with equal objects */
    @Test
    void testEqualsTrue() {
        assertEquals(modification1, modification2);
        assertEquals(modification1, modification1);

        // Create copies and ensure equals works with different instances
        BooleanToggleModification copy1 = modification1.createCopy();
        BooleanToggleModification copy2 = modification2.createCopy();
        assertEquals(copy1, copy2);
        assertEquals(modification1, copy1);
    }

    /** Test of equals method with null */
    @Test
    void testEqualsNull() {
        assertNotEquals(null, modification1);
        assertFalse(modification1.equals(null));
    }

    /** Test of equals method with different class */
    @Test
    void testEqualsDifferentClass() {
        assertNotEquals(modification1, new Object());
        assertNotEquals(modification1, new BooleanExplicitValueModification(true));
        assertFalse(modification1.equals(new Object()));
    }

    /** Test equals with a subclass */
    @Test
    void testEqualsWithSubclass() {
        BooleanToggleModification regularMod = new BooleanToggleModification();
        SubclassToggleModification subclassMod = new SubclassToggleModification();

        // Should not be equal since equals checks for exact class match
        assertNotEquals(regularMod, subclassMod);
    }

    // Helper subclass to test equality with subclasses
    private static class SubclassToggleModification extends BooleanToggleModification {
        // No additional functionality, just a subclass
    }

    /** Test of toString method */
    @Test
    void testToString() {
        assertEquals("BooleanToggleModification{}", modification1.toString());
    }
}
