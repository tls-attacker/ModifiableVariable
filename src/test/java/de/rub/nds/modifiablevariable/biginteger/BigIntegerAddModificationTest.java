/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.biginteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.math.BigInteger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BigIntegerAddModificationTest {

    private BigIntegerAddModification b1;

    private BigIntegerAddModification b2;

    private BigIntegerAddModification b3;

    @BeforeEach
    public void setUp() {
        b1 = new BigIntegerAddModification(BigInteger.ONE);
        b2 = new BigIntegerAddModification(BigInteger.TEN);
        b3 = new BigIntegerAddModification(BigInteger.ONE);
    }

    /** Test of modifyImplementationHook method, of class BigIntegerAddModification. */
    @Test
    public void testModifyImplementationHook() {
        assertEquals(BigInteger.valueOf(11), b1.modifyImplementationHook(BigInteger.TEN));
        assertEquals(BigInteger.valueOf(2), b3.modifyImplementationHook(BigInteger.ONE));
        assertEquals(BigInteger.ONE, b1.modifyImplementationHook(null));
    }

    /** Test of getSummand method, of class BigIntegerAddModification. */
    @Test
    public void testGetSummand() {
        assertEquals(BigInteger.ONE, b1.getSummand());
        assertEquals(BigInteger.TEN, b2.getSummand());
    }

    /** Test of setSummand method, of class BigIntegerAddModification. */
    @Test
    public void testSetSummand() {
        assertNotEquals(BigInteger.ONE, b2.getSummand());
        b2.setSummand(BigInteger.ONE);
        assertEquals(BigInteger.ONE, b2.getSummand());
    }

    /** Test of hashCode method, of class BigIntegerAddModification. */
    @Test
    public void testHashCode() {
        assertEquals(b1.hashCode(), b3.hashCode());
        assertNotEquals(b1.hashCode(), b2.hashCode());
    }

    /** Test of equals method, of class BigIntegerAddModification. */
    @Test
    public void testEquals() {
        assertNotEquals(b1, b2);
        assertEquals(b1, b1);
        assertEquals(b1, b3);
    }
}
