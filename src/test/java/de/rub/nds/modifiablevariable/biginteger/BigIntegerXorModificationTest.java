/**
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Copyright 2014-2022 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 *
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.modifiablevariable.biginteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

/**
 *
 * @author captain
 */
public class BigIntegerXorModificationTest {

    private BigIntegerXorModification b1;
    private BigIntegerXorModification b2;
    private BigIntegerXorModification b3;
    private Integer integer1;

    @BeforeEach
    public void setUp() {
        b1 = new BigIntegerXorModification(BigInteger.ONE);
        b2 = new BigIntegerXorModification(BigInteger.TEN);
        b3 = new BigIntegerXorModification(BigInteger.ONE);
        integer1 = 1;
    }

    /**
     * Test of modifyImplementationHook method, of class BigIntegerXorModification.
     */
    @Disabled("Not yet implemented")
    @Test
    public void testModifyImplementationHook() {
    }

    /**
     * Test of getXor method, of class BigIntegerXorModification.
     */
    @Disabled("Not yet implemented")
    @Test
    public void testGetXor() {
    }

    /**
     * Test of setXor method, of class BigIntegerXorModification.
     */
    @Disabled("Not yet implemented")
    @Test
    public void testSetXor() {
    }

    /**
     * Test of getModifiedCopy method, of class BigIntegerXorModification.
     */
    @Disabled("Not yet implemented")
    @Test
    public void testGetModifiedCopy() {
    }

    /**
     * Test of hashCode method, of class BigIntegerXorModification.
     */
    @Disabled("Not yet implemented")
    @Test
    public void testHashCode() {
    }

    /**
     * Test of equals method, of class BigIntegerXorModification.
     */
    @Test
    public void testEquals() {
        assertNotEquals(b1, b2);
        assertNotEquals(b1, integer1);
        assertEquals(b1, b1);
        assertEquals(b1, b3);
    }

}
