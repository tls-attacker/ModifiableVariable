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
import static org.junit.jupiter.api.Assertions.assertNull;

import de.rub.nds.modifiablevariable.longint.LongMultiplyModification;
import de.rub.nds.modifiablevariable.longint.ModifiableLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LongMultiplyModificationTest {

    private LongMultiplyModification modification;
    private final Long factor = 5L;

    @BeforeEach
    void setUp() {
        modification = new LongMultiplyModification(factor);
    }

    @Test
    void testCreateCopy() {
        LongMultiplyModification copy = modification.createCopy();
        assertNotNull(copy);
        assertEquals(modification, copy);
        assertEquals(modification.getFactor(), copy.getFactor());
        assertNotSame(modification, copy);
    }

    @Test
    void testEquals() {
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
    void testHashCode() {
        LongMultiplyModification equalModification = new LongMultiplyModification(factor);

        // Equal objects should have equal hash codes
        assertEquals(modification.hashCode(), equalModification.hashCode());
    }

    @Test
    void testGetFactor() {
        assertEquals(factor, modification.getFactor());
    }

    @Test
    void testSetFactor() {
        Long newFactor = 20L;
        modification.setFactor(newFactor);
        assertEquals(newFactor, modification.getFactor());
    }

    @Test
    void testToString() {
        String expected = "LongMultiplyModification{factor=5}";
        assertEquals(expected, modification.toString());
    }

    @Test
    void testConstructors() {
        LongMultiplyModification constructor = new LongMultiplyModification(5L);
        assertNotNull(constructor);
        assertEquals(5L, constructor.getFactor());

        // Test copy constructor
        LongMultiplyModification copy = new LongMultiplyModification(modification);
        assertEquals(modification.getFactor(), copy.getFactor());
    }

    @Test
    void testModifyWithNull() {
        // Create a ModifiableLong with null value
        ModifiableLong modifiable = new ModifiableLong();
        modifiable.setOriginalValue(null);

        // Add our multiplication modification
        modifiable.addModification(modification);

        // The result should be null since the input is null
        assertNull(modifiable.getValue());
    }
}
