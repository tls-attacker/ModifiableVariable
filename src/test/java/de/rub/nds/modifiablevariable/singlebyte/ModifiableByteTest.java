/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.singlebyte;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class ModifiableByteTest {

    private ModifiableByte byte1;
    private ModifiableByte byte2;

    @BeforeEach
    public void setUp() {
        byte1 = new ModifiableByte();
        byte1.setOriginalValue((byte) 3);
        byte2 = new ModifiableByte();
        byte2.setOriginalValue((byte) 3);
    }

    /** Test of getAssertEquals method, of class ModifiableByte. */
    @Disabled("Not yet implemented")
    @Test
    public void testGetAssertEquals() {}

    /** Test of setAssertEquals method, of class ModifiableByte. */
    @Disabled("Not yet implemented")
    @Test
    public void testSetAssertEquals() {}

    /** Test of isOriginalValueModified method, of class ModifiableByte. */
    @Disabled("Not yet implemented")
    @Test
    public void testIsOriginalValueModified() {}

    /** Test of validateAssertions method, of class ModifiableByte. */
    @Disabled("Not yet implemented")
    @Test
    public void testValidateAssertions() {}

    /** Test of getOriginalValue method, of class ModifiableByte. */
    @Disabled("Not yet implemented")
    @Test
    public void testGetOriginalValue() {}

    /** Test of setOriginalValue method, of class ModifiableByte. */
    @Disabled("Not yet implemented")
    @Test
    public void testSetOriginalValue() {}

    /** Test of toString method, of class ModifiableByte. */
    @Disabled("Not yet implemented")
    @Test
    public void testToString() {}

    /** Test of equals method, of class ModifiableByte. */
    @Test
    public void testEquals() {
        assertEquals(byte1, byte2);
        byte2.setOriginalValue((byte) 4);
        assertNotEquals(byte1, byte2);
    }

    /** Test of hashCode method, of class ModifiableByte. */
    @Disabled("Not yet implemented")
    @Test
    public void testHashCode() {}
}
