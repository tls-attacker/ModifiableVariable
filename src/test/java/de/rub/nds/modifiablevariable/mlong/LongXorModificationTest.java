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

import de.rub.nds.modifiablevariable.longint.LongXorModification;
import de.rub.nds.modifiablevariable.longint.ModifiableLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LongXorModificationTest {

    private LongXorModification modification;
    private final Long xor = 5L;

    @BeforeEach
    void setUp() {
        modification = new LongXorModification(xor);
    }

    @Test
    void testCreateCopy() {
        LongXorModification copy = modification.createCopy();
        assertNotNull(copy);
        assertEquals(modification, copy);
        assertEquals(modification.getXor(), copy.getXor());
        assertNotSame(modification, copy);
    }

    @Test
    void testEquals() {
        LongXorModification equalModification = new LongXorModification(xor);
        LongXorModification differentModification = new LongXorModification(10L);

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
        LongXorModification equalModification = new LongXorModification(xor);

        // Equal objects should have equal hash codes
        assertEquals(modification.hashCode(), equalModification.hashCode());
    }

    @Test
    void testGetXor() {
        assertEquals(xor, modification.getXor());
    }

    @Test
    void testSetXor() {
        Long newXor = 20L;
        modification.setXor(newXor);
        assertEquals(newXor, modification.getXor());
    }

    @Test
    void testToString() {
        String expected = "LongXorModification{xor=5}";
        assertEquals(expected, modification.toString());
    }

    @Test
    void testConstructors() {
        LongXorModification constuctor = new LongXorModification(2L);
        assertNotNull(constuctor);
        assertEquals(2L, constuctor.getXor());

        // Test copy constructor
        LongXorModification copy = new LongXorModification(modification);
        assertEquals(modification.getXor(), copy.getXor());
    }

    // We can't directly test the protected modifyImplementationHook method,
    // but we can indirectly test its behavior through public methods

    @Test
    void testModifyWithNull() {
        // Create a ModifiableLong with null value
        ModifiableLong modifiable = new ModifiableLong();
        modifiable.setOriginalValue(null);

        // Add our xor modification
        modifiable.addModification(modification);

        // The result should be null since the input is null
        assertNull(modifiable.getValue());
    }
}
