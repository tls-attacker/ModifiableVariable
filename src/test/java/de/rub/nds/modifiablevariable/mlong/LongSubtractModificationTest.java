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
import static org.junit.jupiter.api.Assertions.assertNull;

import de.rub.nds.modifiablevariable.longint.LongSubtractModification;
import de.rub.nds.modifiablevariable.longint.ModifiableLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LongSubtractModificationTest {

    private LongSubtractModification modification;
    private final Long subtrahend = 5L;

    @BeforeEach
    public void setUp() {
        modification = new LongSubtractModification(subtrahend);
    }

    @Test
    public void testCreateCopy() {
        LongSubtractModification copy = modification.createCopy();
        assertNotNull(copy);
        assertEquals(modification, copy);
        assertEquals(modification.getSubtrahend(), copy.getSubtrahend());
    }

    @Test
    public void testEquals() {
        LongSubtractModification equalModification = new LongSubtractModification(subtrahend);
        LongSubtractModification differentModification = new LongSubtractModification(10L);

        // Test reflexivity
        assertEquals(modification, modification);

        // Test symmetry
        assertEquals(modification, equalModification);
        assertEquals(equalModification, modification);

        // Test with different values
        assertNotEquals(modification, differentModification);

        // Test with null and different object type
        assertNotEquals(null, modification);
        assertNotEquals("string", modification);
    }

    @Test
    public void testHashCode() {
        LongSubtractModification equalModification = new LongSubtractModification(subtrahend);

        // Equal objects should have equal hash codes
        assertEquals(modification.hashCode(), equalModification.hashCode());
    }

    @Test
    public void testGetSubtrahend() {
        assertEquals(subtrahend, modification.getSubtrahend());
    }

    @Test
    public void testSetSubtrahend() {
        Long newSubtrahend = 20L;
        modification.setSubtrahend(newSubtrahend);
        assertEquals(newSubtrahend, modification.getSubtrahend());
    }

    @Test
    public void testToString() {
        String expected = "LongSubtractModification{subtrahend=5}";
        assertEquals(expected, modification.toString());
    }

    @Test
    public void testConstructors() {
        LongSubtractModification constructor = new LongSubtractModification(5L);
        assertNotNull(constructor);
        assertEquals(5, constructor.getSubtrahend());

        // Test copy constructor
        LongSubtractModification copy = new LongSubtractModification(modification);
        assertEquals(modification.getSubtrahend(), copy.getSubtrahend());
    }

    @Test
    public void testModifyWithNull() {
        // Create a ModifiableLong with null value
        ModifiableLong modifiable = new ModifiableLong();
        modifiable.setOriginalValue(null);

        // Add our subtraction modification
        modifiable.addModification(modification);

        // The result should be null since the input is null
        assertNull(modifiable.getValue());
    }

    @Test
    public void testGetValueWithNullInput() {
        // Test direct call to modify with null input
        Long result = modification.modify(null);

        // The result should be null as specified in the implementation
        assertNull(result);
    }
}
