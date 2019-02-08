/*
 * Copyright 2019 captain.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
     * Test of modifyImplementationHook method, of class
     * ByteArrayDeleteModification.
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
