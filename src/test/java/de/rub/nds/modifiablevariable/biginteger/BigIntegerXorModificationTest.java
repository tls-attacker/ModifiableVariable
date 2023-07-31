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
public class BigIntegerXorModificationTest {

    private BigIntegerXorModification b1;
    private BigIntegerXorModification b2;
    private BigIntegerXorModification b3;
    private Integer integer1;

    @Before
    public void setUp() {
        b1 = new BigIntegerXorModification(BigInteger.ONE);
        b2 = new BigIntegerXorModification(BigInteger.TEN);
        b3 = new BigIntegerXorModification(BigInteger.ONE);
        integer1 = 1;
    }

    /**
     * Test of modifyImplementationHook method, of class BigIntegerXorModification.
     */
    @Test
    public void testModifyImplementationHook() {
    }

    /**
     * Test of getXor method, of class BigIntegerXorModification.
     */
    @Test
    public void testGetXor() {
    }

    /**
     * Test of setXor method, of class BigIntegerXorModification.
     */
    @Test
    public void testSetXor() {
    }

    /**
     * Test of getModifiedCopy method, of class BigIntegerXorModification.
     */
    @Test
    public void testGetModifiedCopy() {
    }

    /**
     * Test of hashCode method, of class BigIntegerXorModification.
     */
    @Test
    public void testHashCode() {
    }

    /**
     * Test of equals method, of class BigIntegerXorModification.
     */
    @Test
    public void testEquals() {
        assertFalse(b1.equals(b2));
        assertFalse(b1.equals(integer1));
        assertTrue(b1.equals(b1));
        assertTrue(b1.equals(b3));
    }

}
