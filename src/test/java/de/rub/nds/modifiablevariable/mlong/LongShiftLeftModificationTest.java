/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.mlong;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import de.rub.nds.modifiablevariable.longint.LongShiftLeftModification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LongShiftLeftModificationTest {

    private LongShiftLeftModification modification;
    private final int shift = 3;

    @BeforeEach
    public void setUp() {
        modification = new LongShiftLeftModification(shift);
    }

    @Test
    public void testCreateCopy() {
        LongShiftLeftModification copy = modification.createCopy();
        assertNotNull(copy);
        assertEquals(modification, copy);
        assertEquals(modification.getShift(), copy.getShift());
    }

    @Test
    public void testEquals() {
        LongShiftLeftModification equalModification = new LongShiftLeftModification(shift);
        LongShiftLeftModification differentModification = new LongShiftLeftModification(10);

        // Test reflexivity
        assertEquals(modification, modification);

        // Test symmetry
        assertEquals(modification, equalModification);
        assertEquals(equalModification, modification);

        // Test with different values
        assertNotEquals(modification, differentModification);

        // Test with null and different object type
        assertNotEquals(modification, null);
        assertNotEquals(modification, "string");
    }

    @Test
    public void testHashCode() {
        LongShiftLeftModification equalModification = new LongShiftLeftModification(shift);

        // Equal objects should have equal hash codes
        assertEquals(modification.hashCode(), equalModification.hashCode());
    }

    @Test
    public void testGetShift() {
        assertEquals(shift, modification.getShift());
    }

    @Test
    public void testSetShift() {
        int newShift = 20;
        modification.setShift(newShift);
        assertEquals(newShift, modification.getShift());
    }

    @Test
    public void testToString() {
        String expected = "LongShiftLeftModification{shift=3}";
        assertEquals(expected, modification.toString());
    }

    @Test
    public void testConstructors() {
        LongShiftLeftModification constructor = new LongShiftLeftModification(5);
        assertNotNull(constructor);
        assertEquals(5, constructor.getShift());

        // Test copy constructor
        LongShiftLeftModification copy = new LongShiftLeftModification(modification);
        assertEquals(modification.getShift(), copy.getShift());
    }
}
