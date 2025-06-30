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

class BigIntegerXorModificationTest {

    private BigIntegerXorModification b1;
    private BigIntegerXorModification b2;
    private BigIntegerXorModification b3;
    private Integer integer1;

    @BeforeEach
    void setUp() {
        b1 = new BigIntegerXorModification(BigInteger.ONE);
        b2 = new BigIntegerXorModification(BigInteger.TEN);
        b3 = new BigIntegerXorModification(BigInteger.ONE);
        integer1 = 1;
    }

    /** Test of modifyImplementationHook method, of class BigIntegerXorModification. */
    @Test
    void testModifyImplementationHook() {
        // Basic XOR operations
        // 10 XOR 1 = 11 (binary: 1010 XOR 0001 = 1011)
        assertEquals(BigInteger.valueOf(11), b1.modifyImplementationHook(BigInteger.TEN));

        // XOR with zero mask leaves value unchanged
        BigIntegerXorModification zeroMask = new BigIntegerXorModification(BigInteger.ZERO);
        assertEquals(BigInteger.TEN, zeroMask.modifyImplementationHook(BigInteger.TEN));

        // Handle null input
        assertNull(b1.modifyImplementationHook(null));
    }

    /** Test of getXor method, of class BigIntegerXorModification. */
    @Test
    void testGetXor() {
        assertEquals(BigInteger.ONE, b1.getXor());
        assertEquals(BigInteger.TEN, b2.getXor());

        // Verify same XOR value for equal objects
        assertEquals(b1.getXor(), b3.getXor());
    }

    /** Test of setXor method, of class BigIntegerXorModification. */
    @Test
    void testSetXor() {
        // Change XOR value and verify
        assertNotEquals(BigInteger.valueOf(5), b1.getXor());
        b1.setXor(BigInteger.valueOf(5));
        assertEquals(BigInteger.valueOf(5), b1.getXor());

        // Verify modification behavior changed
        // 10 XOR 5 = 15 (binary: 1010 XOR 0101 = 1111)
        assertEquals(BigInteger.valueOf(15), b1.modifyImplementationHook(BigInteger.TEN));
    }

    /** Test of hashCode method, of class BigIntegerXorModification. */
    @Test
    void testHashCode() {
        // Same XOR value should have same hash code
        assertEquals(b1.hashCode(), b3.hashCode());

        // Different XOR values should have different hash codes
        assertNotEquals(b1.hashCode(), b2.hashCode());

        // Hash code should be deterministic
        int hashCode = b1.hashCode();
        assertEquals(hashCode, b1.hashCode());

        // Changing the XOR value should change the hash code
        BigIntegerXorModification mutable = new BigIntegerXorModification(BigInteger.ONE);
        int originalHashCode = mutable.hashCode();
        mutable.setXor(BigInteger.valueOf(42));
        assertNotEquals(originalHashCode, mutable.hashCode());

        // Hash code should be based on the XOR value
        BigIntegerXorModification mod1 = new BigIntegerXorModification(BigInteger.valueOf(100));
        BigIntegerXorModification mod2 = new BigIntegerXorModification(BigInteger.valueOf(100));
        assertEquals(mod1.hashCode(), mod2.hashCode());
    }

    /** Test of equals method, of class BigIntegerXorModification. */
    @Test
    void testEquals() {
        // Same instance
        assertTrue(b1.equals(b1));

        // Same XOR value
        assertTrue(b1.equals(b3));
        assertTrue(b3.equals(b1));

        // Different XOR value
        assertFalse(b1.equals(b2));

        // Different type
        assertFalse(b1.equals("Not a modification"));
        assertFalse(b1.equals(new BigIntegerAddModification(BigInteger.ONE)));
        assertFalse(b1.equals(integer1));

        // Null check
        assertFalse(b1.equals(null));

        // Verify symmetry
        BigIntegerXorModification other = new BigIntegerXorModification(BigInteger.ONE);
        assertTrue(b1.equals(other));
        assertTrue(other.equals(b1));

        // Verify consistency after changing XOR value
        other.setXor(BigInteger.valueOf(42));
        assertFalse(b1.equals(other));
        other.setXor(BigInteger.ONE);
        assertTrue(b1.equals(other));
    }

    /** Test of createCopy method */
    @Test
    void testCreateCopy() {
        BigIntegerXorModification copy = b1.createCopy();

        // Verify it's a different instance but equal
        assertNotEquals(System.identityHashCode(b1), System.identityHashCode(copy));
        assertEquals(b1, copy);
        assertEquals(b1.getXor(), copy.getXor());

        // Verify modifications to copy don't affect original
        copy.setXor(BigInteger.valueOf(100));
        assertNotEquals(b1.getXor(), copy.getXor());
        assertEquals(BigInteger.ONE, b1.getXor());
        assertEquals(BigInteger.valueOf(100), copy.getXor());
    }

    /** Test of toString method */
    @Test
    void testToString() {
        String toString = b1.toString();
        assertTrue(toString.contains("BigIntegerXorModification"));
        assertTrue(toString.contains("xor=1"));

        // Verify toString changes with XOR value
        b1.setXor(BigInteger.valueOf(42));
        String newToString = b1.toString();
        assertTrue(newToString.contains("xor=42"));
    }

    /** Test constructor with null value */
    @Test
    static void testConstructorWithNullValue() {
        // Using try-catch because we expect an exception
        try {
            // Explicitly specify null as BigInteger to avoid ambiguity with copy constructor
            BigInteger nullValue = null;
            new BigIntegerXorModification(nullValue);
            // Should not reach here
            fail("Constructor should throw NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception
            assertTrue(e.getMessage().contains("null"));
        }
    }

    /** Test setXor with null value */
    @Test
    void testSetXorWithNullValue() {
        // Using try-catch because we expect an exception
        try {
            b1.setXor(null);
            // Should not reach here
            fail("setXor should throw NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception
            assertTrue(e.getMessage().contains("null"));
        }
    }
}
