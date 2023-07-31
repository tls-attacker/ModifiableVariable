/**
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 *
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.modifiablevariable.biginteger;

import java.math.BigInteger;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author captain
 */
public class BigIntegerAddModificationTest {

    private BigIntegerAddModification b1;

    private BigIntegerAddModification b2;

    private BigIntegerAddModification b3;

    private Integer integer1;

    @Before
    public void setUp() {
        b1 = new BigIntegerAddModification(BigInteger.ONE);
        b2 = new BigIntegerAddModification(BigInteger.TEN);
        b3 = new BigIntegerAddModification(BigInteger.ONE);
        integer1 = 1;
    }

    /**
     * Test of modifyImplementationHook method, of class BigIntegerAddModification.
     */
    @Test
    public void testModifyImplementationHook() {
    }

    /**
     * Test of getSummand method, of class BigIntegerAddModification.
     */
    @Test
    public void testGetSummand() {
    }

    /**
     * Test of setSummand method, of class BigIntegerAddModification.
     */
    @Test
    public void testSetSummand() {
    }

    /**
     * Test of getModifiedCopy method, of class BigIntegerAddModification.
     */
    @Test
    public void testGetModifiedCopy() {
    }

    /**
     * Test of hashCode method, of class BigIntegerAddModification.
     */
    @Test
    public void testHashCode() {
    }

    /**
     * Test of equals method, of class BigIntegerAddModification.
     */
    @Test
    public void testEquals() {
        assertFalse(b1.equals(b2));
        assertFalse(b1.equals(integer1));
        assertTrue(b1.equals(b1));
        assertTrue(b1.equals(b3));
    }

}
