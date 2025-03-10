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

import de.rub.nds.modifiablevariable.longint.LongMultiplyModification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LongMultiplyModificationTest {

    private LongMultiplyModification modification;
    private final Long factor = 5L;

    @BeforeEach
    public void setUp() {
        modification = new LongMultiplyModification(factor);
    }

    @Test
    public void testCreateCopy() {
        LongMultiplyModification copy = modification.createCopy();
        assertNotNull(copy);
        assertEquals(modification, copy);
        assertEquals(modification.getFactor(), copy.getFactor());
        assertNotSame(modification, copy);
    }

    @Test
    public void testEquals() {
        LongMultiplyModification equalModification = new LongMultiplyModification(factor);
        LongMultiplyModification differentModification = new LongMultiplyModification(10L);

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
        LongMultiplyModification equalModification = new LongMultiplyModification(factor);

        // Equal objects should have equal hash codes
        assertEquals(modification.hashCode(), equalModification.hashCode());
    }

    @Test
    public void testGetFactor() {
        assertEquals(factor, modification.getFactor());
    }

    @Test
    public void testSetFactor() {
        Long newFactor = 20L;
        modification.setFactor(newFactor);
        assertEquals(newFactor, modification.getFactor());
    }

    @Test
    public void testToString() {
        String expected = "LongMultiplyModification{factor=5}";
        assertEquals(expected, modification.toString());
    }

    @Test
    public void testConstructors() {
        LongMultiplyModification constructor = new LongMultiplyModification(5L);
        assertNotNull(constructor);
        assertEquals(5L, constructor.getFactor());

        // Test copy constructor
        LongMultiplyModification copy = new LongMultiplyModification(modification);
        assertEquals(modification.getFactor(), copy.getFactor());
    }
}
