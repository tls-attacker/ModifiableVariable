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
package de.rub.nds.modifiablevariable.bool;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ModifiableBooleanTest {

    private ModifiableBoolean boolean1;

    private ModifiableBoolean boolean2;

    @Before
    public void setUp() {
        boolean1 = new ModifiableBoolean();
        boolean1.setOriginalValue(Boolean.TRUE);
        boolean2 = new ModifiableBoolean();
        boolean2.setOriginalValue(Boolean.TRUE);
    }

    /**
     * Test of getOriginalValue method, of class ModifiableBoolean.
     */
    @Test
    public void testGetOriginalValue() {
    }

    /**
     * Test of setOriginalValue method, of class ModifiableBoolean.
     */
    @Test
    public void testSetOriginalValue() {
    }

    /**
     * Test of createRandomModification method, of class ModifiableBoolean.
     */
    @Test
    public void testCreateRandomModification() {
    }

    /**
     * Test of isOriginalValueModified method, of class ModifiableBoolean.
     */
    @Test
    public void testIsOriginalValueModified() {
    }

    /**
     * Test of validateAssertions method, of class ModifiableBoolean.
     */
    @Test
    public void testValidateAssertions() {
    }

    /**
     * Test of toString method, of class ModifiableBoolean.
     */
    @Test
    public void testToString() {
    }

    /**
     * Test of equals method, of class ModifiableBoolean.
     */
    @Test
    public void testEquals() {
        assertEquals(boolean1, boolean2);
        boolean2.setOriginalValue(Boolean.FALSE);
        assertNotEquals(boolean1, boolean2);
    }

    /**
     * Test of hashCode method, of class ModifiableBoolean.
     */
    @Test
    public void testHashCode() {
    }

}
