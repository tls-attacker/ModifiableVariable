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

public class BigIntegerAddModificationTest {

    private BigIntegerAddModification b1;

    private BigIntegerAddModification b2;

    private BigIntegerAddModification b3;

    private Integer integer1;

    @BeforeEach
    public void setUp() {
        b1 = new BigIntegerAddModification(BigInteger.ONE);
        b2 = new BigIntegerAddModification(BigInteger.TEN);
        b3 = new BigIntegerAddModification(BigInteger.ONE);
        integer1 = 1;
    }

    /**
     * Test of modifyImplementationHook method, of class BigIntegerAddModification.
     */
    @Disabled("Not yet implemented")
    @Test
    public void testModifyImplementationHook() {
    }

    /**
     * Test of getSummand method, of class BigIntegerAddModification.
     */
    @Disabled("Not yet implemented")
    @Test
    public void testGetSummand() {
    }

    /**
     * Test of setSummand method, of class BigIntegerAddModification.
     */
    @Disabled("Not yet implemented")
    @Test
    public void testSetSummand() {
    }

    /**
     * Test of getModifiedCopy method, of class BigIntegerAddModification.
     */
    @Disabled("Not yet implemented")
    @Test
    public void testGetModifiedCopy() {
    }

    /**
     * Test of hashCode method, of class BigIntegerAddModification.
     */
    @Disabled("Not yet implemented")
    @Test
    public void testHashCode() {
    }

    /**
     * Test of equals method, of class BigIntegerAddModification.
     */
    @Test
    public void testEquals() {
        assertNotEquals(b1, b2);
        assertNotEquals(b1, integer1);
        assertEquals(b1, b1);
        assertEquals(b1, b3);
    }

}
