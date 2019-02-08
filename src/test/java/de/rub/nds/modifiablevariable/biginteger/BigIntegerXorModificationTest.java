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
package de.rub.nds.modifiablevariable.biginteger;

import java.math.BigInteger;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author captain
 */
public class BigIntegerXorModificationTest {

    private BigIntegerXorModification b1;
    private BigIntegerXorModification b2;
    private BigIntegerXorModification b3;
    private Integer integer1;

    @Before
    public void setUp() {
        b1 = new BigIntegerXorModification(BigInteger.ONE);
        b2 = new BigIntegerXorModification(BigInteger.TEN);
        b3 = new BigIntegerXorModification(BigInteger.ONE);
        integer1 = 1;
    }

    /**
     * Test of modifyImplementationHook method, of class
     * BigIntegerXorModification.
     */
    @Test
    public void testModifyImplementationHook() {
    }

    /**
     * Test of getXor method, of class BigIntegerXorModification.
     */
    @Test
    public void testGetXor() {
    }

    /**
     * Test of setXor method, of class BigIntegerXorModification.
     */
    @Test
    public void testSetXor() {
    }

    /**
     * Test of getModifiedCopy method, of class BigIntegerXorModification.
     */
    @Test
    public void testGetModifiedCopy() {
    }

    /**
     * Test of hashCode method, of class BigIntegerXorModification.
     */
    @Test
    public void testHashCode() {
    }

    /**
     * Test of equals method, of class BigIntegerXorModification.
     */
    @Test
    public void testEquals() {
        assertFalse(b1.equals(b2));
        assertFalse(b1.equals(integer1));
        assertTrue(b1.equals(b1));
        assertTrue(b1.equals(b3));
    }

}
