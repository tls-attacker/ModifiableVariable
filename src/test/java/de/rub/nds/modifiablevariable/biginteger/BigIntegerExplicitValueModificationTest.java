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
public class BigIntegerExplicitValueModificationTest {

    private BigIntegerExplicitValueModification b1;
    private BigIntegerExplicitValueModification b2;
    private BigIntegerExplicitValueModification b3;
    private Integer integer1;

    @Before
    public void setUp() {
        b1 = new BigIntegerExplicitValueModification(BigInteger.ONE);
        b2 = new BigIntegerExplicitValueModification(BigInteger.TEN);
        b3 = new BigIntegerExplicitValueModification(BigInteger.ONE);
        integer1 = 1;
    }

    /**
     * Test of modifyImplementationHook method, of class BigIntegerExplicitValueModification.
     */
    @Test
    public void testModifyImplementationHook() {
    }

    /**
     * Test of getExplicitValue method, of class BigIntegerExplicitValueModification.
     */
    @Test
    public void testGetExplicitValue() {
    }

    /**
     * Test of setExplicitValue method, of class BigIntegerExplicitValueModification.
     */
    @Test
    public void testSetExplicitValue() {
    }

    /**
     * Test of getModifiedCopy method, of class BigIntegerExplicitValueModification.
     */
    @Test
    public void testGetModifiedCopy() {
    }

    /**
     * Test of hashCode method, of class BigIntegerExplicitValueModification.
     */
    @Test
    public void testHashCode() {
    }

    /**
     * Test of equals method, of class BigIntegerExplicitValueModification.
     */
    @Test
    public void testEquals() {
        assertFalse(b1.equals(b2));
        assertFalse(b1.equals(integer1));
        assertTrue(b1.equals(b1));
        assertTrue(b1.equals(b3));
    }

}
