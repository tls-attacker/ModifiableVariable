/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.bytearray;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
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
        b2 = new ByteArrayDeleteModification(0, 0);
        b3 = new ByteArrayDeleteModification(0, 1);
        b4 = new ByteArrayDeleteModification(1, 0);
        b5 = 10;
    }

    /** Test of modifyImplementationHook method, of class ByteArrayDeleteModification. */
    @Test
    public void testModifyImplementationHook() {
        assertArrayEquals(
                new byte[] {1, 2, 3, 4}, b3.modifyImplementationHook(new byte[] {0, 1, 2, 3, 4}));
    }

    /** Test of getStartPosition method, of class ByteArrayDeleteModification. */
    @Test
    public void testGetStartPosition() {
        assertEquals(b1.getStartPosition(), b3.getStartPosition());
        assertNotEquals(b2.getStartPosition(), b4.getStartPosition());
    }

    /** Test of setStartPosition method, of class ByteArrayDeleteModification. */
    @Test
    public void testSetStartPosition() {
        assertNotEquals(1, b1.getStartPosition());
        b1.setStartPosition(1);
        assertEquals(1, b1.getStartPosition());
    }

    /** Test of getCount method, of class ByteArrayDeleteModification. */
    @Test
    public void testGetCount() {
        assertEquals(0, b1.getCount());
        b1.setCount(1);
        assertNotEquals(0, b1.getCount());
    }

    /** Test of setCount method, of class ByteArrayDeleteModification. */
    @Test
    public void testSetCount() {
        assertNotEquals(1, b1.getCount());
        b1.setCount(1);
        assertEquals(1, b1.getCount());
    }

    /** Test of hashCode method, of class ByteArrayDeleteModification. */
    @Test
    public void testHashCode() {
        assertEquals(b1.hashCode(), b2.hashCode());
    }

    /** Test of equals method, of class ByteArrayDeleteModification. */
    @Test
    public void testEquals() {
        assertEquals(b1, b1);
        assertEquals(b1, b2);
        assertNotEquals(b1, b3);
        assertNotEquals(b1, b4);
        assertNotEquals(b1, b5);
    }

    /** Test of toString method, of class ByteArrayDeleteModification. */
    @Test
    public void testToString() {
        assertEquals(b1.toString(), b2.toString());
        b1.setCount(5);
        assertNotEquals(b1.toString(), b2.toString());
    }
}
