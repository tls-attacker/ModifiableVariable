/**
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Copyright 2014-2022 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 *
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.modifiablevariable.bytearray;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class ByteArrayDeleteModificationTest {

    private ByteArrayDeleteModification b1;
    private ByteArrayDeleteModification b2;
    private ByteArrayDeleteModification b3;
    private ByteArrayDeleteModification b4;
    private int b5;

    @BeforeEach
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
    @Disabled("Not yet implemented")
    @Test
    public void testModifyImplementationHook() {
    }

    /**
     * Test of getStartPosition method, of class ByteArrayDeleteModification.
     */
    @Disabled("Not yet implemented")
    @Test
    public void testGetStartPosition() {
    }

    /**
     * Test of setStartPosition method, of class ByteArrayDeleteModification.
     */
    @Disabled("Not yet implemented")
    @Test
    public void testSetStartPosition() {
    }

    /**
     * Test of getCount method, of class ByteArrayDeleteModification.
     */
    @Disabled("Not yet implemented")
    @Test
    public void testGetCount() {
    }

    /**
     * Test of setCount method, of class ByteArrayDeleteModification.
     */
    @Disabled("Not yet implemented")
    @Test
    public void testSetCount() {
    }

    /**
     * Test of getModifiedCopy method, of class ByteArrayDeleteModification.
     */
    @Disabled("Not yet implemented")
    @Test
    public void testGetModifiedCopy() {
    }

    /**
     * Test of hashCode method, of class ByteArrayDeleteModification.
     */
    @Disabled("Not yet implemented")
    @Test
    public void testHashCode() {
    }

    /**
     * Test of equals method, of class ByteArrayDeleteModification.
     */
    @Test
    public void testEquals() {
        assertEquals(b1, b1);
        assertEquals(b1, b2);
        assertNotEquals(b1, b3);
        assertNotEquals(b1, b4);
        assertNotEquals(b1, b5);
    }

    /**
     * Test of toString method, of class ByteArrayDeleteModification.
     */
    @Disabled("Not yet implemented")
    @Test
    public void testToString() {
    }

}
