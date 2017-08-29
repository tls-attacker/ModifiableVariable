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
package de.rub.nds.modifiablevariable.biginteger;

import java.math.BigInteger;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Robert Merget <robert.merget@rub.de>
 */
public class ModifiableBigIntegerTest {

    private ModifiableBigInteger integer1;

    private ModifiableBigInteger integer2;

    @Before
    public void setUp() {
        integer1 = new ModifiableBigInteger();
        integer1.setOriginalValue(BigInteger.ONE);
        integer2 = new ModifiableBigInteger();
        integer2.setOriginalValue(BigInteger.TEN);
    }

    /**
     * Test of createRandomModification method, of class ModifiableBigInteger.
     */
    @Test
    public void testCreateRandomModification() {
    }

    /**
     * Test of getAssertEquals method, of class ModifiableBigInteger.
     */
    @Test
    public void testGetAssertEquals() {
    }

    /**
     * Test of setAssertEquals method, of class ModifiableBigInteger.
     */
    @Test
    public void testSetAssertEquals() {
    }

    /**
     * Test of isOriginalValueModified method, of class ModifiableBigInteger.
     */
    @Test
    public void testIsOriginalValueModified() {
    }

    /**
     * Test of getByteArray method, of class ModifiableBigInteger.
     */
    @Test
    public void testGetByteArray_0args() {
    }

    /**
     * Test of getByteArray method, of class ModifiableBigInteger.
     */
    @Test
    public void testGetByteArray_int() {
    }

    /**
     * Test of validateAssertions method, of class ModifiableBigInteger.
     */
    @Test
    public void testValidateAssertions() {
    }

    /**
     * Test of getOriginalValue method, of class ModifiableBigInteger.
     */
    @Test
    public void testGetOriginalValue() {
    }

    /**
     * Test of setOriginalValue method, of class ModifiableBigInteger.
     */
    @Test
    public void testSetOriginalValue() {
    }

    /**
     * Test of toString method, of class ModifiableBigInteger.
     */
    @Test
    public void testToString() {
    }

    /**
     * Test of equals method, of class ModifiableBigInteger.
     */
    @Test
    public void testEquals() {
        assertFalse(integer1.equals(integer2));
        integer2.setOriginalValue(BigInteger.ONE);
        assertTrue(integer1.equals(integer2));
    }

    /**
     * Test of hashCode method, of class ModifiableBigInteger.
     */
    @Test
    public void testHashCode() {
    }

}
