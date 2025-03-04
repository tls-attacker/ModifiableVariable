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

import de.rub.nds.modifiablevariable.longint.LongExplicitValueModification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LongExplicitValueModificationTest {

    private LongExplicitValueModification modification;
    private final Long explicitValue = 42L;

    @BeforeEach
    public void setUp() {
        modification = new LongExplicitValueModification(explicitValue);
    }

    @Test
    public void testCreateCopy() {
        LongExplicitValueModification copy = modification.createCopy();
        assertNotNull(copy);
        assertEquals(modification, copy);
        assertEquals(modification.getExplicitValue(), copy.getExplicitValue());
    }

    @Test
    public void testEquals() {
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
    public void testHashCode() {
        LongExplicitValueModification equalModification =
                new LongExplicitValueModification(explicitValue);

        // Equal objects should have equal hash codes
        assertEquals(modification.hashCode(), equalModification.hashCode());
    }

    @Test
    public void testGetExplicitValue() {
        assertEquals(explicitValue, modification.getExplicitValue());
    }

    @Test
    public void testSetExplicitValue() {
        Long newValue = 20L;
        modification.setExplicitValue(newValue);
        assertEquals(newValue, modification.getExplicitValue());
    }

    @Test
    public void testToString() {
        String expected = "LongExplicitValueModification{explicitValue=42}";
        assertEquals(expected, modification.toString());
    }

    @Test
    public void testConstructors() {
        // Test default constructor
        LongExplicitValueModification defaultConstructor = new LongExplicitValueModification();
        assertNotNull(defaultConstructor);

        // Test copy constructor
        LongExplicitValueModification copy = new LongExplicitValueModification(modification);
        assertEquals(modification.getExplicitValue(), copy.getExplicitValue());
    }
}
