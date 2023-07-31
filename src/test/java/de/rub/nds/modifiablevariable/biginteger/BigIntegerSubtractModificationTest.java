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
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author captain
 */
public class BigIntegerSubtractModificationTest {

    private BigIntegerSubtractModification b1;
    private BigIntegerSubtractModification b2;
    private BigIntegerSubtractModification b3;
    private Integer integer1;

    @Before
    public void setUp() {
        b1 = new BigIntegerSubtractModification(BigInteger.ONE);
        b2 = new BigIntegerSubtractModification(BigInteger.TEN);
        b3 = new BigIntegerSubtractModification(BigInteger.ONE);
        integer1 = 1;
    }

    /**
     * Test of modifyImplementationHook method, of class BigIntegerSubtractModification.
     */
    @Test
    public void testModifyImplementationHook() {
    }

    /**
     * Test of getSubtrahend method, of class BigIntegerSubtractModification.
     */
    @Test
    public void testGetSubtrahend() {
    }

    /**
     * Test of setSubtrahend method, of class BigIntegerSubtractModification.
     */
    @Test
    public void testSetSubtrahend() {
    }

    /**
     * Test of getModifiedCopy method, of class BigIntegerSubtractModification.
     */
    @Test
    public void testGetModifiedCopy() {
    }

    /**
     * Test of hashCode method, of class BigIntegerSubtractModification.
     */
    @Test
    public void testHashCode() {
    }

    /**
     * Test of equals method, of class BigIntegerSubtractModification.
     */
    @Test
    public void testEquals() {
        assertFalse(b1.equals(b2));
        assertFalse(b1.equals(integer1));
        assertTrue(b1.equals(b1));
        assertTrue(b1.equals(b3));
    }

}
