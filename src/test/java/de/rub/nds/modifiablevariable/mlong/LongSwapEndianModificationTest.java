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

import de.rub.nds.modifiablevariable.longint.LongSwapEndianModification;
import de.rub.nds.modifiablevariable.longint.ModifiableLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LongSwapEndianModificationTest {

    private LongSwapEndianModification modification;

    @BeforeEach
    void setUp() {
        modification = new LongSwapEndianModification();
    }

    @Test
    void testCreateCopy() {
        LongSwapEndianModification copy = modification.createCopy();
        assertNotNull(copy);
        assertEquals(modification, copy);
        assertNotSame(modification, copy);
    }

    @Test
    void testEquals() {
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
    void testHashCode() {
        LongSwapEndianModification equalModification = new LongSwapEndianModification();

        // Equal objects should have equal hash codes
        assertEquals(modification.hashCode(), equalModification.hashCode());
    }

    @Test
    void testToString() {
        String expected = "LongSwapEndianModification{}";
        assertEquals(expected, modification.toString());
    }

    @Test
    void testConstructors() {
        // Test default constructor
        LongSwapEndianModification defaultConstructor = new LongSwapEndianModification();
        assertNotNull(defaultConstructor);

        // Test copy constructor
        LongSwapEndianModification copy = new LongSwapEndianModification(modification);
        assertEquals(modification, copy);
    }

    @Test
    void testModifyWithNull() {
        // Create a ModifiableLong with null value
        ModifiableLong modifiable = new ModifiableLong();
        modifiable.setOriginalValue(null);

        // Add our swap endian modification
        modifiable.addModification(modification);

        // The result should be null since the input is null
        assertNull(modifiable.getValue());
    }
}
