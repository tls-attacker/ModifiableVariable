/**
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 *
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.modifiablevariable.bytearray;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author captain
 */
public class ByteArrayDeleteModificationTest {

    private ByteArrayDeleteModification b1;
    private ByteArrayDeleteModification b2;
    private ByteArrayDeleteModification b3;
    private ByteArrayDeleteModification b4;
    private int b5;

    @Before
    public void setUp() {
        b1 = new ByteArrayDeleteModification(0, 0);
        b2 = b1;
        b3 = new ByteArrayDeleteModification(0, 1);
        b4 = new ByteArrayDeleteModification(1, 0);
        b5 = 10;
    }

    /**
     * Test of modifyImplementationHook method, of class ByteArrayDeleteModification.
     */
    @Test
    public void testModifyImplementationHook() {
    }

    /**
     * Test of getStartPosition method, of class ByteArrayDeleteModification.
     */
    @Test
    public void testGetStartPosition() {
    }

    /**
     * Test of setStartPosition method, of class ByteArrayDeleteModification.
     */
    @Test
    public void testSetStartPosition() {
    }

    /**
     * Test of getCount method, of class ByteArrayDeleteModification.
     */
    @Test
    public void testGetCount() {
    }

    /**
     * Test of setCount method, of class ByteArrayDeleteModification.
     */
    @Test
    public void testSetCount() {
    }

    /**
     * Test of getModifiedCopy method, of class ByteArrayDeleteModification.
     */
    @Test
    public void testGetModifiedCopy() {
    }

    /**
     * Test of hashCode method, of class ByteArrayDeleteModification.
     */
    @Test
    public void testHashCode() {
    }

    /**
     * Test of equals method, of class ByteArrayDeleteModification.
     */
    @Test
    public void testEquals() {
        assertTrue(b1.equals(b1));
        assertTrue(b1.equals(b2));
        assertFalse(b1.equals(b3));
        assertFalse(b1.equals(b4));
        assertFalse(b1.equals(b5));
    }

    /**
     * Test of toString method, of class ByteArrayDeleteModification.
     */
    @Test
    public void testToString() {
    }

}
