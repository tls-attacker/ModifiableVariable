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

package de.rub.nds.modifiablevariable.integer;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ModifiableIntegerTest {

    private ModifiableInteger integer1;

    private ModifiableInteger integer2;

    @Before
    public void setUp() {
        integer1 = new ModifiableInteger();
        integer1.setOriginalValue(2);
        integer2 = new ModifiableInteger();
        integer2.setOriginalValue(2);
    }

    /**
     * Test of createRandomModification method, of class ModifiableInteger.
     */
    @Test
    public void testCreateRandomModification() {
    }

    /**
     * Test of getAssertEquals method, of class ModifiableInteger.
     */
    @Test
    public void testGetAssertEquals() {
    }

    /**
     * Test of setAssertEquals method, of class ModifiableInteger.
     */
    @Test
    public void testSetAssertEquals() {
    }

    /**
     * Test of isOriginalValueModified method, of class ModifiableInteger.
     */
    @Test
    public void testIsOriginalValueModified() {
    }

    /**
     * Test of getByteArray method, of class ModifiableInteger.
     */
    @Test
    public void testGetByteArray() {
    }

    /**
     * Test of validateAssertions method, of class ModifiableInteger.
     */
    @Test
    public void testValidateAssertions() {
    }

    /**
     * Test of getOriginalValue method, of class ModifiableInteger.
     */
    @Test
    public void testGetOriginalValue() {
    }

    /**
     * Test of setOriginalValue method, of class ModifiableInteger.
     */
    @Test
    public void testSetOriginalValue() {
    }

    /**
     * Test of toString method, of class ModifiableInteger.
     */
    @Test
    public void testToString() {
    }

    /**
     * Test of equals method, of class ModifiableInteger.
     */
    @Test
    public void testEquals() {
        assertEquals(integer1, integer2);
        integer2.setOriginalValue(3);
        assertNotEquals(integer1, integer2);
    }

    /**
     * Test of hashCode method, of class ModifiableInteger.
     */
    @Test
    public void testHashCode() {
    }

}
