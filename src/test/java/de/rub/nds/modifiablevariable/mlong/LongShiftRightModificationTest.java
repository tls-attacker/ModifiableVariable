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
import static org.junit.jupiter.api.Assertions.assertNotSame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.rub.nds.modifiablevariable.longint.LongShiftRightModification;

public class LongShiftRightModificationTest {

    private LongShiftRightModification modification;
    private final int shift = 3;

    @BeforeEach
    public void setUp() {
        modification = new LongShiftRightModification(shift);
    }

    @Test
    public void testCreateCopy() {
        LongShiftRightModification copy = modification.createCopy();
        assertNotNull(copy);
        assertEquals(modification, copy);
        assertEquals(modification.getShift(), copy.getShift());
        assertNotSame(modification, copy);
    }

    @Test
    public void testEquals() {
        LongShiftRightModification equalModification = new LongShiftRightModification(shift);
        LongShiftRightModification differentModification = new LongShiftRightModification(10);

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
        LongShiftRightModification equalModification = new LongShiftRightModification(shift);

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
        String expected = "LongShiftRightModification{shift=3}";
        assertEquals(expected, modification.toString());
    }

    @Test
    public void testConstructors() {
        // Test default constructor
        LongShiftRightModification defaultConstructor = new LongShiftRightModification();
        assertNotNull(defaultConstructor);

        // Test copy constructor
        LongShiftRightModification copy = new LongShiftRightModification(modification);
        assertEquals(modification.getShift(), copy.getShift());
    }
}
