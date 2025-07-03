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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigInteger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BigIntegerMultiplyModificationTest {

    private BigIntegerMultiplyModification m1;
    private BigIntegerMultiplyModification m2;
    private BigIntegerMultiplyModification m3;

    @BeforeEach
    void setUp() {
        m1 = new BigIntegerMultiplyModification(BigInteger.valueOf(2));
        m2 = new BigIntegerMultiplyModification(BigInteger.valueOf(5));
        m3 = new BigIntegerMultiplyModification(BigInteger.valueOf(2));
    }

    /** Test of modifyImplementationHook method */
    @Test
    void testModifyImplementationHook() {
        // Regular multiplication
        assertEquals(BigInteger.valueOf(20), m1.modifyImplementationHook(BigInteger.TEN));
        assertEquals(BigInteger.valueOf(50), m2.modifyImplementationHook(BigInteger.TEN));

        // Multiply by 0
        BigIntegerMultiplyModification zero = new BigIntegerMultiplyModification(BigInteger.ZERO);
        assertEquals(BigInteger.ZERO, zero.modifyImplementationHook(BigInteger.TEN));

        // Handle null input
        assertNull(m1.modifyImplementationHook(null));
    }

    /** Test of getFactor method */
    @Test
    void testGetFactor() {
        assertEquals(BigInteger.valueOf(2), m1.getFactor());
        assertEquals(BigInteger.valueOf(5), m2.getFactor());
    }

    /** Test of setFactor method */
    @Test
    void testSetFactor() {
        // Change factor and verify
        assertNotEquals(BigInteger.valueOf(3), m1.getFactor());
        m1.setFactor(BigInteger.valueOf(3));
        assertEquals(BigInteger.valueOf(3), m1.getFactor());

        // Verify modification behavior changed
        assertEquals(BigInteger.valueOf(30), m1.modifyImplementationHook(BigInteger.TEN));
    }

    /** Test of hashCode method */
    @Test
    void testHashCode() {
        // Same factor should have same hash code
        assertEquals(m1.hashCode(), m3.hashCode());

        // Different factors should have different hash codes
        assertNotEquals(m1.hashCode(), m2.hashCode());

        // Hash code should be deterministic
        int hashCode = m1.hashCode();
        assertEquals(hashCode, m1.hashCode());

        // Changing the factor should change the hash code
        BigIntegerMultiplyModification mutable =
                new BigIntegerMultiplyModification(BigInteger.valueOf(2));
        int originalHashCode = mutable.hashCode();
        mutable.setFactor(BigInteger.valueOf(42));
        assertNotEquals(originalHashCode, mutable.hashCode());
    }

    /** Test of equals method */
    @Test
    void testEquals() {
        // Same instance
        assertTrue(m1.equals(m1));

        // Same factor
        assertTrue(m1.equals(m3));
        assertTrue(m3.equals(m1));

        // Different factor
        assertFalse(m1.equals(m2));

        // Different type
        assertFalse(m1.equals("Not a modification"));
        assertFalse(m1.equals(new BigIntegerAddModification(BigInteger.ONE)));

        // Null check
        assertFalse(m1.equals(null));

        // Verify symmetry
        BigIntegerMultiplyModification other =
                new BigIntegerMultiplyModification(BigInteger.valueOf(2));
        assertTrue(m1.equals(other));
        assertTrue(other.equals(m1));

        // Verify consistency after changing factor
        other.setFactor(BigInteger.valueOf(10));
        assertFalse(m1.equals(other));
        other.setFactor(BigInteger.valueOf(2));
        assertTrue(m1.equals(other));
    }

    /** Test of createCopy method */
    @Test
    void testCreateCopy() {
        BigIntegerMultiplyModification copy = m1.createCopy();

        // Verify it's a different instance but equal
        assertNotEquals(System.identityHashCode(m1), System.identityHashCode(copy));
        assertEquals(m1, copy);
        assertEquals(m1.getFactor(), copy.getFactor());

        // Verify modifications to copy don't affect original
        copy.setFactor(BigInteger.valueOf(100));
        assertNotEquals(m1.getFactor(), copy.getFactor());
    }

    /** Test of toString method */
    @Test
    void testToString() {
        String toString = m1.toString();
        assertTrue(toString.contains("BigIntegerMultiplyModification"));
        assertTrue(toString.contains("factor=2"));
    }
}
