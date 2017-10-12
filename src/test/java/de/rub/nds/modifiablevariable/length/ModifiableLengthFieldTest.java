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
package de.rub.nds.modifiablevariable.length;

import de.rub.nds.modifiablevariable.bytearray.ModifiableByteArray;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ModifiableLengthFieldTest {

    private ModifiableLengthField lengthField1;

    private ModifiableLengthField lengthField2;

    private ModifiableByteArray array;

    @Before
    public void setUp() {
        array = new ModifiableByteArray();
        array.setOriginalValue(new byte[] { 0, 1, 2, 3 });
        lengthField1 = new ModifiableLengthField(array);
        lengthField2 = new ModifiableLengthField(array);
    }

    /**
     * Test of getOriginalValue method, of class ModifiableLengthField.
     */
    @Test
    public void testGetOriginalValue() {
        assertTrue(lengthField1.getValue() == 4);
    }

    /**
     * Test of setOriginalValue method, of class ModifiableLengthField.
     */
    @Test
    public void testSetOriginalValue() {

    }

    /**
     * Test of toString method, of class ModifiableLengthField.
     */
    @Test
    public void testToString() {
    }

    /**
     * Test of equals method, of class ModifiableLengthField.
     */
    @Test
    public void testEquals() {
        assertEquals(lengthField1, lengthField2);
        array = new ModifiableByteArray();
        array.setOriginalValue(new byte[] { 1, 4 });
        lengthField2 = new ModifiableLengthField(array);
        assertNotEquals(lengthField1, lengthField2);
    }

    /**
     * Test of hashCode method, of class ModifiableLengthField.
     */
    @Test
    public void testHashCode() {
    }

}
