/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.biginteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigInteger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BigIntegerAddModificationTest {

    private BigIntegerAddModification b1;
    private BigIntegerAddModification b2;
    private BigIntegerAddModification b3;

    @BeforeEach
    void setUp() {
        b1 = new BigIntegerAddModification(BigInteger.ONE);
        b2 = new BigIntegerAddModification(BigInteger.TEN);
        b3 = new BigIntegerAddModification(BigInteger.ONE);
    }

    /** Test of modifyImplementationHook method, of class BigIntegerAddModification. */
    @Test
    void testModifyImplementationHook() {
        assertEquals(BigInteger.valueOf(11), b1.modifyImplementationHook(BigInteger.TEN));
        assertEquals(BigInteger.valueOf(2), b3.modifyImplementationHook(BigInteger.ONE));
        assertEquals(null, b1.modifyImplementationHook(null));
    }

    /** Test of getSummand method, of class BigIntegerAddModification. */
    @Test
    void testGetSummand() {
        assertEquals(BigInteger.ONE, b1.getSummand());
        assertEquals(BigInteger.TEN, b2.getSummand());
    }

    /** Test of setSummand method, of class BigIntegerAddModification. */
    @Test
    void testSetSummand() {
        assertNotEquals(BigInteger.ONE, b2.getSummand());
        b2.setSummand(BigInteger.ONE);
        assertEquals(BigInteger.ONE, b2.getSummand());
    }

    /** Test that setSummand throws NullPointerException if given null */
    @Test
    void testSetSummandWithNull() {
        assertThrows(NullPointerException.class, () -> b1.setSummand(null));
    }

    /** Test that constructor throws NullPointerException if given null */
    @Test
    static void testConstructorWithNull() {
        assertThrows(
                NullPointerException.class, () -> new BigIntegerAddModification((BigInteger) null));
    }

    /** Test of createCopy method */
    @Test
    void testCreateCopy() {
        BigIntegerAddModification copy = b1.createCopy();

        // Verify the copy has the same properties
        assertEquals(b1.getSummand(), copy.getSummand());
        assertEquals(b1, copy);

        // Verify it's a different instance
        assertNotEquals(System.identityHashCode(b1), System.identityHashCode(copy));

        // Modify the copy and verify the original is unchanged
        copy.setSummand(BigInteger.valueOf(99));
        assertEquals(BigInteger.ONE, b1.getSummand());
        assertEquals(BigInteger.valueOf(99), copy.getSummand());
        assertNotEquals(b1, copy);
    }

    /** Test of hashCode method, of class BigIntegerAddModification. */
    @Test
    void testHashCode() {
        // Equal objects should have same hash code
        assertEquals(b1.hashCode(), b3.hashCode());

        // Different objects should have different hash codes
        assertNotEquals(b1.hashCode(), b2.hashCode());

        // Hash code should be consistent across multiple calls
        int hashCode = b1.hashCode();
        assertEquals(hashCode, b1.hashCode());
        assertEquals(hashCode, b1.hashCode());
    }

    /** Test of equals method with reflexivity property */
    @Test
    void testEqualsReflexivity() {
        // An object must equal itself
        assertTrue(b1.equals(b1));
        assertTrue(b2.equals(b2));
        assertTrue(b3.equals(b3));
    }

    /** Test of equals method with symmetry property */
    @Test
    void testEqualsSymmetry() {
        // If a equals b, then b equals a
        assertTrue(b1.equals(b3));
        assertTrue(b3.equals(b1));

        assertFalse(b1.equals(b2));
        assertFalse(b2.equals(b1));
    }

    /** Test of equals method with transitivity property */
    @Test
    void testEqualsTransitivity() {
        // If a equals b and b equals c, then a equals c
        BigIntegerAddModification b4 = new BigIntegerAddModification(BigInteger.ONE);

        assertTrue(b1.equals(b3));
        assertTrue(b3.equals(b4));
        assertTrue(b1.equals(b4));
    }

    /** Test of equals method with null comparison */
    @Test
    void testEqualsNull() {
        // Comparison with null should return false
        assertFalse(b1.equals(null));
        assertFalse(b2.equals(null));
        assertFalse(b3.equals(null));
    }

    /** Test of equals method with different object types */
    @Test
    void testEqualsDifferentTypes() {
        // Comparison with different types should return false
        assertFalse(b1.equals("Not a BigIntegerAddModification"));
        assertFalse(b1.equals(BigInteger.ONE));
        assertFalse(b1.equals(new Object()));
    }

    /** Test of equals method after state change */
    @Test
    void testEqualsAfterStateChange() {
        // Initially equal
        assertTrue(b1.equals(b3));

        // Change state of one object
        b3.setSummand(BigInteger.valueOf(42));

        // Should no longer be equal
        assertFalse(b1.equals(b3));

        // Change state back
        b3.setSummand(BigInteger.ONE);

        // Should be equal again
        assertTrue(b1.equals(b3));
    }

    /** Test of toString method */
    @Test
    void testToString() {
        String toString = b1.toString();

        // Verify the string contains the class name and summand value
        assertTrue(toString.contains("BigIntegerAddModification"));
        assertTrue(toString.contains("summand=1"));

        // Test with a different summand
        b1.setSummand(BigInteger.valueOf(42));
        String newToString = b1.toString();
        assertTrue(newToString.contains("summand=42"));
    }
}
