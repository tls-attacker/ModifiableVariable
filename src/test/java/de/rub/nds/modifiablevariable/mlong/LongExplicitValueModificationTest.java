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

import de.rub.nds.modifiablevariable.longint.LongExplicitValueModification;
import de.rub.nds.modifiablevariable.longint.ModifiableLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LongExplicitValueModificationTest {

    private LongExplicitValueModification modification;
    private final Long explicitValue = 42L;

    @BeforeEach
    void setUp() {
        modification = new LongExplicitValueModification(explicitValue);
    }

    @Test
    void testCreateCopy() {
        LongExplicitValueModification copy = modification.createCopy();
        assertNotNull(copy);
        assertEquals(modification, copy);
        assertEquals(modification.getExplicitValue(), copy.getExplicitValue());
        assertNotSame(modification, copy);
    }

    @Test
    void testEquals() {
        LongExplicitValueModification equalModification =
                new LongExplicitValueModification(explicitValue);
        LongExplicitValueModification differentModification =
                new LongExplicitValueModification(10L);

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
        LongExplicitValueModification equalModification =
                new LongExplicitValueModification(explicitValue);

        // Equal objects should have equal hash codes
        assertEquals(modification.hashCode(), equalModification.hashCode());
    }

    @Test
    void testGetExplicitValue() {
        assertEquals(explicitValue, modification.getExplicitValue());
    }

    @Test
    void testSetExplicitValue() {
        Long newValue = 20L;
        modification.setExplicitValue(newValue);
        assertEquals(newValue, modification.getExplicitValue());
    }

    @Test
    void testToString() {
        String expected = "LongExplicitValueModification{explicitValue=42}";
        assertEquals(expected, modification.toString());
    }

    @Test
    void testConstructors() {
        // Test default constructor
        LongExplicitValueModification constructor = new LongExplicitValueModification(5L);
        assertNotNull(constructor);
        assertEquals(constructor.getExplicitValue(), 5);

        // Test copy constructor
        LongExplicitValueModification copy = new LongExplicitValueModification(modification);
        assertEquals(modification.getExplicitValue(), copy.getExplicitValue());
    }

    // We can't directly test the protected modifyImplementationHook method,
    // but we can indirectly test its behavior through public methods

    @Test
    void testModifyWithNull() {
        // Create a ModifiableLong with null value
        ModifiableLong modifiable = new ModifiableLong();
        modifiable.setOriginalValue(null);

        // Add our explicit value modification
        modifiable.addModification(modification);

        // The result should be null since the input is null
        assertNull(modifiable.getValue());
    }
}
