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

import de.rub.nds.modifiablevariable.longint.LongAddModification;
import de.rub.nds.modifiablevariable.longint.ModifiableLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LongAddModificationTest {

    private LongAddModification modification;
    private final Long summand = 5L;

    @BeforeEach
    void setUp() {
        modification = new LongAddModification(summand);
    }

    @Test
    void testCreateCopy() {
        LongAddModification copy = modification.createCopy();
        assertNotNull(copy);
        assertEquals(modification, copy);
        assertEquals(modification.getSummand(), copy.getSummand());
        assertNotSame(modification, copy);
    }

    @Test
    void testEquals() {
        LongAddModification equalModification = new LongAddModification(summand);
        LongAddModification differentModification = new LongAddModification(10L);

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
        LongAddModification equalModification = new LongAddModification(summand);

        // Equal objects should have equal hash codes
        assertEquals(modification.hashCode(), equalModification.hashCode());
    }

    @Test
    void testGetSummand() {
        assertEquals(summand, modification.getSummand());
    }

    @Test
    void testSetSummand() {
        Long newSummand = 20L;
        modification.setSummand(newSummand);
        assertEquals(newSummand, modification.getSummand());
    }

    @Test
    void testToString() {
        String expected = "LongAddModification{summand=5}";
        assertEquals(expected, modification.toString());
    }

    @Test
    void testConstructors() {
        LongAddModification constructor = new LongAddModification(5L);
        assertNotNull(constructor);
        assertEquals(5L, constructor.getSummand());

        // Test copy constructor
        LongAddModification copy = new LongAddModification(modification);
        assertEquals(modification.getSummand(), copy.getSummand());
    }

    // We can't directly test the protected modifyImplementationHook method,
    // but we can indirectly test its behavior through public methods

    @Test
    void testModifyWithNull() {
        // Create a ModifiableLong with null value
        ModifiableLong modifiable = new ModifiableLong();
        modifiable.setOriginalValue(null);

        // Add our addition modification
        modifiable.addModification(modification);

        // The result should be null since the input is null
        assertNull(modifiable.getValue());
    }
}
