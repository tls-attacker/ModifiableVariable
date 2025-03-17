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
import static org.junit.jupiter.api.Assertions.fail;

import java.math.BigInteger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BigIntegerSubtractModificationTest {

    private BigIntegerSubtractModification b1;
    private BigIntegerSubtractModification b2;
    private BigIntegerSubtractModification b3;
    private Integer integer1;

    @BeforeEach
    public void setUp() {
        b1 = new BigIntegerSubtractModification(BigInteger.ONE);
        b2 = new BigIntegerSubtractModification(BigInteger.TEN);
        b3 = new BigIntegerSubtractModification(BigInteger.ONE);
        integer1 = 1;
    }

    /** Test of modifyImplementationHook method, of class BigIntegerSubtractModification. */
    @Test
    public void testModifyImplementationHook() {
        // Regular subtraction
        assertEquals(BigInteger.valueOf(9), b1.modifyImplementationHook(BigInteger.TEN));
        assertEquals(BigInteger.valueOf(0), b2.modifyImplementationHook(BigInteger.TEN));

        // Negative result
        assertEquals(BigInteger.valueOf(-9), b2.modifyImplementationHook(BigInteger.ONE));

        // Subtraction with zero
        BigIntegerSubtractModification zero = new BigIntegerSubtractModification(BigInteger.ZERO);
        assertEquals(BigInteger.TEN, zero.modifyImplementationHook(BigInteger.TEN));

        // Handle null input
        assertNull(b1.modifyImplementationHook(null));
    }

    /** Test of getSubtrahend method, of class BigIntegerSubtractModification. */
    @Test
    public void testGetSubtrahend() {
        assertEquals(BigInteger.ONE, b1.getSubtrahend());
        assertEquals(BigInteger.TEN, b2.getSubtrahend());

        // Verify same subtrahend for equal objects
        assertEquals(b1.getSubtrahend(), b3.getSubtrahend());
    }

    /** Test of setSubtrahend method, of class BigIntegerSubtractModification. */
    @Test
    public void testSetSubtrahend() {
        // Change subtrahend and verify
        assertNotEquals(BigInteger.valueOf(5), b1.getSubtrahend());
        b1.setSubtrahend(BigInteger.valueOf(5));
        assertEquals(BigInteger.valueOf(5), b1.getSubtrahend());

        // Verify modification behavior changed
        assertEquals(BigInteger.valueOf(5), b1.modifyImplementationHook(BigInteger.TEN));
    }

    /** Test of hashCode method, of class BigIntegerSubtractModification. */
    @Test
    public void testHashCode() {
        // Same subtrahend should have same hash code
        assertEquals(b1.hashCode(), b3.hashCode());

        // Different subtrahends should have different hash codes
        assertNotEquals(b1.hashCode(), b2.hashCode());

        // Hash code should be deterministic
        int hashCode = b1.hashCode();
        assertEquals(hashCode, b1.hashCode());

        // Changing the subtrahend should change the hash code
        BigIntegerSubtractModification mutable = new BigIntegerSubtractModification(BigInteger.ONE);
        int originalHashCode = mutable.hashCode();
        mutable.setSubtrahend(BigInteger.valueOf(42));
        assertNotEquals(originalHashCode, mutable.hashCode());

        // Hash code should be based on the subtrahend
        BigIntegerSubtractModification mod1 =
                new BigIntegerSubtractModification(BigInteger.valueOf(100));
        BigIntegerSubtractModification mod2 =
                new BigIntegerSubtractModification(BigInteger.valueOf(100));
        assertEquals(mod1.hashCode(), mod2.hashCode());
    }

    /** Test of equals method, of class BigIntegerSubtractModification. */
    @Test
    public void testEquals() {
        // Same instance
        assertTrue(b1.equals(b1));

        // Same subtrahend
        assertTrue(b1.equals(b3));
        assertTrue(b3.equals(b1));

        // Different subtrahend
        assertFalse(b1.equals(b2));

        // Different type
        assertFalse(b1.equals("Not a modification"));
        assertFalse(b1.equals(new BigIntegerAddModification(BigInteger.ONE)));
        assertFalse(b1.equals(integer1));

        // Null check
        assertFalse(b1.equals(null));

        // Verify symmetry
        BigIntegerSubtractModification other = new BigIntegerSubtractModification(BigInteger.ONE);
        assertTrue(b1.equals(other));
        assertTrue(other.equals(b1));

        // Verify consistency after changing subtrahend
        other.setSubtrahend(BigInteger.valueOf(42));
        assertFalse(b1.equals(other));
        other.setSubtrahend(BigInteger.ONE);
        assertTrue(b1.equals(other));
    }

    /** Test of createCopy method */
    @Test
    public void testCreateCopy() {
        BigIntegerSubtractModification copy = b1.createCopy();

        // Verify it's a different instance but equal
        assertNotEquals(System.identityHashCode(b1), System.identityHashCode(copy));
        assertEquals(b1, copy);
        assertEquals(b1.getSubtrahend(), copy.getSubtrahend());

        // Verify modifications to copy don't affect original
        copy.setSubtrahend(BigInteger.valueOf(100));
        assertNotEquals(b1.getSubtrahend(), copy.getSubtrahend());
        assertEquals(BigInteger.ONE, b1.getSubtrahend());
        assertEquals(BigInteger.valueOf(100), copy.getSubtrahend());
    }

    /** Test of toString method */
    @Test
    public void testToString() {
        String toString = b1.toString();
        assertTrue(toString.contains("BigIntegerSubtractModification"));
        assertTrue(toString.contains("subtrahend=1"));

        // Verify toString changes with subtrahend
        b1.setSubtrahend(BigInteger.valueOf(42));
        String newToString = b1.toString();
        assertTrue(newToString.contains("subtrahend=42"));
    }

    /** Test constructor with null value */
    @Test
    public void testConstructorWithNullValue() {
        // Using try-catch because we expect an exception
        try {
            // Explicitly specify null as BigInteger to avoid ambiguity with copy constructor
            BigInteger nullValue = null;
            BigIntegerSubtractModification mod = new BigIntegerSubtractModification(nullValue);
            // Should not reach here
            fail("Constructor should throw NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception
            assertTrue(e.getMessage().contains("null"));
        }
    }

    /** Test setSubtrahend with null value */
    @Test
    public void testSetSubtrahendWithNullValue() {
        // Using try-catch because we expect an exception
        try {
            b1.setSubtrahend(null);
            // Should not reach here
            fail("setSubtrahend should throw NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception
            assertTrue(e.getMessage().contains("null"));
        }
    }
}
