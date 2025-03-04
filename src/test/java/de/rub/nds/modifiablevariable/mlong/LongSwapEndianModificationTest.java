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

import de.rub.nds.modifiablevariable.longint.LongSwapEndianModification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LongSwapEndianModificationTest {

    private LongSwapEndianModification modification;

    @BeforeEach
    public void setUp() {
        modification = new LongSwapEndianModification();
    }

    @Test
    public void testCreateCopy() {
        LongSwapEndianModification copy = modification.createCopy();
        assertNotNull(copy);
        assertEquals(modification, copy);
    }

    @Test
    public void testEquals() {
        LongSwapEndianModification equalModification = new LongSwapEndianModification();

        // Test reflexivity
        assertEquals(modification, modification);

        // Test symmetry
        assertEquals(modification, equalModification);
        assertEquals(equalModification, modification);

        // Test with null and different object type
        assertNotEquals(modification, null);
        assertNotEquals(modification, "string");
    }

    @Test
    public void testHashCode() {
        LongSwapEndianModification equalModification = new LongSwapEndianModification();

        // Equal objects should have equal hash codes
        assertEquals(modification.hashCode(), equalModification.hashCode());

        // Should return the same hash code every time
        assertEquals(7, modification.hashCode());
    }

    @Test
    public void testToString() {
        String expected = "LongSwapEndianModification{}";
        assertEquals(expected, modification.toString());
    }

    @Test
    public void testConstructors() {
        // Test default constructor
        LongSwapEndianModification defaultConstructor = new LongSwapEndianModification();
        assertNotNull(defaultConstructor);

        // Test copy constructor
        LongSwapEndianModification copy = new LongSwapEndianModification(modification);
        assertEquals(modification, copy);
    }
}
