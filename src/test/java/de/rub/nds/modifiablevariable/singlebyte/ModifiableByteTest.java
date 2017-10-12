/*
 * Copyright 2017 Robert Merget <robert.merget@rub.de>.
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
package de.rub.nds.modifiablevariable.singlebyte;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ModifiableByteTest {

    private ModifiableByte byte1;
    private ModifiableByte byte2;

    @Before
    public void setUp() {
        byte1 = new ModifiableByte();
        byte1.setOriginalValue((byte) 3);
        byte2 = new ModifiableByte();
        byte2.setOriginalValue((byte) 3);
    }

    /**
     * Test of createRandomModification method, of class ModifiableByte.
     */
    @Test
    public void testCreateRandomModification() {
    }

    /**
     * Test of getAssertEquals method, of class ModifiableByte.
     */
    @Test
    public void testGetAssertEquals() {
    }

    /**
     * Test of setAssertEquals method, of class ModifiableByte.
     */
    @Test
    public void testSetAssertEquals() {
    }

    /**
     * Test of isOriginalValueModified method, of class ModifiableByte.
     */
    @Test
    public void testIsOriginalValueModified() {
    }

    /**
     * Test of validateAssertions method, of class ModifiableByte.
     */
    @Test
    public void testValidateAssertions() {
    }

    /**
     * Test of getOriginalValue method, of class ModifiableByte.
     */
    @Test
    public void testGetOriginalValue() {
    }

    /**
     * Test of setOriginalValue method, of class ModifiableByte.
     */
    @Test
    public void testSetOriginalValue() {
    }

    /**
     * Test of toString method, of class ModifiableByte.
     */
    @Test
    public void testToString() {
    }

    /**
     * Test of equals method, of class ModifiableByte.
     */
    @Test
    public void testEquals() {
        assertEquals(byte1, byte2);
        byte2.setOriginalValue((byte) 4);
        assertNotEquals(byte1, byte2);
    }

    /**
     * Test of hashCode method, of class ModifiableByte.
     */
    @Test
    public void testHashCode() {
    }

}
