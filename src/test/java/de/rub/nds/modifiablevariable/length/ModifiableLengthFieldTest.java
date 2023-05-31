/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.length;

import static org.junit.jupiter.api.Assertions.*;

import de.rub.nds.modifiablevariable.bytearray.ModifiableByteArray;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ModifiableLengthFieldTest {

    private ModifiableLengthField lengthField1;

    private ModifiableLengthField lengthField2;

    private ModifiableByteArray array;

    @BeforeEach
    public void setUp() {
        array = new ModifiableByteArray();
        array.setOriginalValue(new byte[] {0, 1, 2, 3});
        lengthField1 = new ModifiableLengthField(array);
        lengthField2 = new ModifiableLengthField(array);
    }

    /** Test of getOriginalValue method, of class ModifiableLengthField. */
    @Test
    public void testGetOriginalValue() {
        assertEquals(4, (int) lengthField1.getValue());
    }

    /** Test of setOriginalValue method, of class ModifiableLengthField. */
    @Test
    public void testSetOriginalValue() {
        assertThrows(UnsupportedOperationException.class, () -> lengthField1.setOriginalValue(4));
    }

    /** Test of toString method, of class ModifiableLengthField. */
    @Test
    public void testToString() {
        assertEquals(lengthField1.toString(), lengthField2.toString());
        array = new ModifiableByteArray();
        array.setOriginalValue(new byte[] {1, 4});
        lengthField2 = new ModifiableLengthField(array);
        assertNotEquals(lengthField1, lengthField2);
    }

    /** Test of equals method, of class ModifiableLengthField. */
    @Test
    public void testEquals() {
        assertEquals(lengthField1, lengthField2);
        array = new ModifiableByteArray();
        array.setOriginalValue(new byte[] {1, 4});
        lengthField2 = new ModifiableLengthField(array);
        assertNotEquals(lengthField1, lengthField2);
    }

    /** Test of hashCode method, of class ModifiableLengthField. */
    @Test
    public void testHashCode() {
        assertEquals(lengthField1.hashCode(), lengthField2.hashCode());
    }
}
