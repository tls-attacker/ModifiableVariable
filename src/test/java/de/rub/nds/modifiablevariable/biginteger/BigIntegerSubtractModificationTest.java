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

    /**
     * Test of modifyImplementationHook method, of class BigIntegerSubtractModification.
     */
    @Disabled("Not yet implemented")
    @Test
    public void testModifyImplementationHook() {
    }

    /**
     * Test of getSubtrahend method, of class BigIntegerSubtractModification.
     */
    @Disabled("Not yet implemented")
    @Test
    public void testGetSubtrahend() {
    }

    /**
     * Test of setSubtrahend method, of class BigIntegerSubtractModification.
     */
    @Disabled("Not yet implemented")
    @Test
    public void testSetSubtrahend() {
    }

    /**
     * Test of getModifiedCopy method, of class BigIntegerSubtractModification.
     */
    @Disabled("Not yet implemented")
    @Test
    public void testGetModifiedCopy() {
    }

    /**
     * Test of hashCode method, of class BigIntegerSubtractModification.
     */
    @Disabled("Not yet implemented")
    @Test
    public void testHashCode() {
    }

    /**
     * Test of equals method, of class BigIntegerSubtractModification.
     */
    @Test
    public void testEquals() {
        assertNotEquals(b1, b2);
        assertNotEquals(b1, integer1);
        assertEquals(b1, b1);
        assertEquals(b1, b3);
    }

}
