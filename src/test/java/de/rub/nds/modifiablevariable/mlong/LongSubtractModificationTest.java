/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.mlong;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Objects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.rub.nds.modifiablevariable.longint.LongAddModification;
import de.rub.nds.modifiablevariable.longint.LongSubtractModification;
import de.rub.nds.modifiablevariable.longint.ModifiableLong;

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
    public void testEqualsWithDifferentTypes() {
        // Test equality with different modification types that have same numeric value
        LongAddModification addMod = new LongAddModification(subtrahend);

        // Should not be equal despite same numeric value because they're different
        // types
        assertNotEquals(modification, addMod);
    }

    @Test
    public void testEqualsTransitivity() {
        // Test transitivity property of equals
        LongSubtractModification mod1 = new LongSubtractModification(subtrahend);
        LongSubtractModification mod2 = new LongSubtractModification(subtrahend);
        LongSubtractModification mod3 = new LongSubtractModification(subtrahend);

        // If mod1 equals mod2 and mod2 equals mod3, then mod1 should equal mod3
        assertEquals(mod1, mod2);
        assertEquals(mod2, mod3);
        assertEquals(mod1, mod3);
    }

    @Test
    public void testEqualsAfterModification() {
        // Create equal modifications
        LongSubtractModification mod1 = new LongSubtractModification(subtrahend);
        LongSubtractModification mod2 = new LongSubtractModification(subtrahend);

        // Initially equal
        assertEquals(mod1, mod2);

        // Modify one of them
        mod2.setSubtrahend(100L);

        // Should no longer be equal
        assertNotEquals(mod1, mod2);

        // Make them equal again
        mod1.setSubtrahend(100L);

        // Should be equal again
        assertEquals(mod1, mod2);
    }

    @Test
    public void testEqualsWithBoundaryValues() {
        // Test with boundary values
        LongSubtractModification minMod = new LongSubtractModification(Long.MIN_VALUE);
        LongSubtractModification maxMod = new LongSubtractModification(Long.MAX_VALUE);
        LongSubtractModification minMod2 = new LongSubtractModification(Long.MIN_VALUE);
        LongSubtractModification maxMod2 = new LongSubtractModification(Long.MAX_VALUE);

        // Same boundary values should be equal
        assertEquals(minMod, minMod2);
        assertEquals(maxMod, maxMod2);

        // Different boundary values should not be equal
        assertNotEquals(minMod, maxMod);
    }

    @Test
    public void testEqualsCopyConstructor() {
        // Test equals with copy constructor
        LongSubtractModification original = new LongSubtractModification(42L);
        LongSubtractModification copy = new LongSubtractModification(original);

        // Copy should equal original
        assertEquals(original, copy);
        assertEquals(original.hashCode(), copy.hashCode());

        // Modifying copy should not affect equality with original
        original.setSubtrahend(100L);
        assertNotEquals(original, copy);
        assertNotEquals(original.hashCode(), copy.hashCode());
    }

    @Test
    public void testEqualsWithNull() {
        // Explicitly test the null case in equals
        LongSubtractModification mod = new LongSubtractModification(42L);

        // Direct call to equals with null should return false
        assertFalse(mod.equals(null));

        // Also test with assertNotEquals to verify consistent behavior
        assertNotEquals(null, mod);
        assertNotEquals(mod, null);
    }

    @Test
    public void testHashCode() {
        LongSubtractModification equalModification = new LongSubtractModification(subtrahend);

        // Equal objects should have equal hash codes
        assertEquals(modification.hashCode(), equalModification.hashCode());
    }

    @Test
    public void testHashCodeConsistency() {
        // Test consistency: hashCode should return same value when called multiple
        // times
        int firstHashCode = modification.hashCode();
        int secondHashCode = modification.hashCode();
        assertEquals(firstHashCode, secondHashCode);

        // Compute expected hash code for verification
        int expectedHash = 7;
        expectedHash = 31 * expectedHash + Objects.hashCode(subtrahend);
        assertEquals(expectedHash, modification.hashCode());
    }

    @Test
    public void testHashCodeWithDifferentValues() {
        // Different values should have different hash codes (not guaranteed but
        // expected in this
        // case)
        LongSubtractModification mod1 = new LongSubtractModification(1L);
        LongSubtractModification mod2 = new LongSubtractModification(2L);

        assertNotEquals(mod1.hashCode(), mod2.hashCode());
    }

    @Test
    public void testHashCodeAfterModification() {
        // Hash code should change when the object state changes
        LongSubtractModification mutableMod = new LongSubtractModification(subtrahend);
        int initialHash = mutableMod.hashCode();

        // Modify the object
        mutableMod.setSubtrahend(999L);

        // Hash code should differ after modification
        int newHash = mutableMod.hashCode();
        assertNotEquals(initialHash, newHash);
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
