/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
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

    /** Test of getAssertEquals method, of class ModifiableInteger. */
    @Test
    public void testGetAssertEquals() {
        integer1.setAssertEquals(2);
        assertEquals(2, integer1.getAssertEquals());
    }

    /** Test of setAssertEquals method, of class ModifiableInteger. */
    @Test
    public void testSetAssertEquals() {
        assertNotEquals(3, integer1.getAssertEquals());
        integer1.setAssertEquals(3);
        assertEquals(3, integer1.getAssertEquals());
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
        integer1.setAssertEquals(3);
        assertFalse(integer1.validateAssertions());
    }

    /** Test of getOriginalValue method, of class ModifiableInteger. */
    @Test
    public void testGetOriginalValue() {
        assertEquals(2, integer1.getOriginalValue());
    }

    /** Test of setOriginalValue method, of class ModifiableInteger. */
    @Test
    public void testSetOriginalValue() {
        integer1.setOriginalValue(3);
        assertEquals(3, integer1.getOriginalValue());
    }

    /** Test of toString method, of class ModifiableInteger. */
    @Test
    public void testToString() {
        assertEquals(integer1.toString(), integer2.toString());
        integer1.setOriginalValue(4);
        assertNotEquals(integer1.toString(), integer2.toString());
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
    }
}
