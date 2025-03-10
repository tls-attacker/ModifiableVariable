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

import de.rub.nds.modifiablevariable.longint.LongXorModification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LongXorModificationTest {

    private LongXorModification modification;
    private final Long xor = 5L;

    @BeforeEach
    public void setUp() {
        modification = new LongXorModification(xor);
    }

    @Test
    public void testCreateCopy() {
        LongXorModification copy = modification.createCopy();
        assertNotNull(copy);
        assertEquals(modification, copy);
        assertEquals(modification.getXor(), copy.getXor());
        assertNotSame(modification, copy);
    }

    @Test
    public void testEquals() {
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
    public void testHashCode() {
        LongXorModification equalModification = new LongXorModification(xor);

        // Equal objects should have equal hash codes
        assertEquals(modification.hashCode(), equalModification.hashCode());
    }

    @Test
    public void testGetXor() {
        assertEquals(xor, modification.getXor());
    }

    @Test
    public void testSetXor() {
        Long newXor = 20L;
        modification.setXor(newXor);
        assertEquals(newXor, modification.getXor());
    }

    @Test
    public void testToString() {
        String expected = "LongXorModification{xor=5}";
        assertEquals(expected, modification.toString());
    }

    @Test
    public void testConstructors() {
        LongXorModification constuctor = new LongXorModification(2L);
        assertNotNull(constuctor);
        assertEquals(2L, constuctor.getXor());

        // Test copy constructor
        LongXorModification copy = new LongXorModification(modification);
        assertEquals(modification.getXor(), copy.getXor());
    }
}
