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

public class BigIntegerExplicitValueModificationTest {

    private BigIntegerExplicitValueModification b1;
    private BigIntegerExplicitValueModification b2;
    private BigIntegerExplicitValueModification b3;
    private Integer integer1;

    @BeforeEach
    public void setUp() {
        b1 = new BigIntegerExplicitValueModification(BigInteger.ONE);
        b2 = new BigIntegerExplicitValueModification(BigInteger.TEN);
        b3 = new BigIntegerExplicitValueModification(BigInteger.ONE);
        integer1 = 1;
    }

    /** Test of modifyImplementationHook method, of class BigIntegerExplicitValueModification. */
    @Test
    public void testModifyImplementationHook() {
        // Should always return explicit value, regardless of input
        assertEquals(BigInteger.ONE, b1.modifyImplementationHook(BigInteger.ZERO));
        assertEquals(BigInteger.ONE, b1.modifyImplementationHook(BigInteger.valueOf(-42)));

        assertEquals(BigInteger.TEN, b2.modifyImplementationHook(BigInteger.ONE));

        // Handle null input
        assertNull(b1.modifyImplementationHook(null));
    }

    /** Test of getExplicitValue method, of class BigIntegerExplicitValueModification. */
    @Test
    public void testGetExplicitValue() {
        assertEquals(BigInteger.ONE, b1.getExplicitValue());
    }

    /** Test of setExplicitValue method, of class BigIntegerExplicitValueModification. */
    @Test
    public void testSetExplicitValue() {
        // Change explicit value and verify
        assertNotEquals(BigInteger.valueOf(42), b1.getExplicitValue());
        b1.setExplicitValue(BigInteger.valueOf(42));
        assertEquals(BigInteger.valueOf(42), b1.getExplicitValue());
    }

    /** Test of hashCode method, of class BigIntegerExplicitValueModification. */
    @Test
    public void testHashCode() {
        // Same explicit value should have same hash code
        assertEquals(b1.hashCode(), b3.hashCode());

        // Different explicit values should have different hash codes
        assertNotEquals(b1.hashCode(), b2.hashCode());

        // Hash code should be deterministic
        int hashCode = b1.hashCode();
        assertEquals(hashCode, b1.hashCode());

        // Changing the explicit value should change the hash code
        BigIntegerExplicitValueModification mutable =
                new BigIntegerExplicitValueModification(BigInteger.ONE);
        int originalHashCode = mutable.hashCode();
        mutable.setExplicitValue(BigInteger.valueOf(42));
        assertNotEquals(originalHashCode, mutable.hashCode());

        // Hash code should be based on the explicit value
        BigIntegerExplicitValueModification mod1 =
                new BigIntegerExplicitValueModification(BigInteger.valueOf(100));
        BigIntegerExplicitValueModification mod2 =
                new BigIntegerExplicitValueModification(BigInteger.valueOf(100));
        assertEquals(mod1.hashCode(), mod2.hashCode());
    }

    /** Test of equals method, of class BigIntegerExplicitValueModification. */
    @Test
    public void testEquals() {
        // Same instance
        assertTrue(b1.equals(b1));

        // Same explicit value
        assertTrue(b1.equals(b3));
        assertTrue(b3.equals(b1));

        // Different explicit value
        assertFalse(b1.equals(b2));

        // Different type
        assertFalse(b1.equals("Not a modification"));
        assertFalse(b1.equals(new BigIntegerAddModification(BigInteger.ONE)));
        assertFalse(b1.equals(integer1));

        // Null check
        assertFalse(b1.equals(null));

        // Verify symmetry
        BigIntegerExplicitValueModification other =
                new BigIntegerExplicitValueModification(BigInteger.ONE);
        assertTrue(b1.equals(other));
        assertTrue(other.equals(b1));

        // Verify consistency after changing explicit value
        other.setExplicitValue(BigInteger.valueOf(42));
        assertFalse(b1.equals(other));
        other.setExplicitValue(BigInteger.ONE);
        assertTrue(b1.equals(other));
    }

    /** Test of createCopy method */
    @Test
    public void testCreateCopy() {
        BigIntegerExplicitValueModification copy = b1.createCopy();

        // Verify it's a different instance but equal
        assertNotEquals(System.identityHashCode(b1), System.identityHashCode(copy));
        assertEquals(b1, copy);
        assertEquals(b1.getExplicitValue(), copy.getExplicitValue());

        // Verify modifications to copy don't affect original
        copy.setExplicitValue(BigInteger.valueOf(100));
        assertNotEquals(b1.getExplicitValue(), copy.getExplicitValue());
        assertEquals(BigInteger.ONE, b1.getExplicitValue());
        assertEquals(BigInteger.valueOf(100), copy.getExplicitValue());
    }

    /** Test of toString method */
    @Test
    public void testToString() {
        String toString = b1.toString();
        assertTrue(toString.contains("BigIntegerExplicitValueModification"));
        assertTrue(toString.contains("explicitValue=1"));

        // Verify toString changes with explicit value
        b1.setExplicitValue(BigInteger.valueOf(42));
        String newToString = b1.toString();
        assertTrue(newToString.contains("explicitValue=42"));
    }

    /** Test constructor with null value */
    @Test
    public void testConstructorWithNullValue() {
        // Using try-catch because we expect an exception
        try {
            // Explicitly specify null as BigInteger to avoid ambiguity with copy constructor
            BigInteger nullValue = null;
            new BigIntegerExplicitValueModification(nullValue);
            fail("Constructor should throw NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception
            assertTrue(e.getMessage().contains("null"));
        }
    }

    /** Test setExplicitValue with null value */
    @Test
    public void testSetExplicitValueWithNullValue() {
        // Using try-catch because we expect an exception
        try {
            b1.setExplicitValue(null);
            // Should not reach here
            fail("setExplicitValue should throw NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception
            assertTrue(e.getMessage().contains("null"));
        }
    }
}
