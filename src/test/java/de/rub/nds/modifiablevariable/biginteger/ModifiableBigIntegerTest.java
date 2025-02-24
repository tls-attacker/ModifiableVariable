/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.biginteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.math.BigInteger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class ModifiableBigIntegerTest {

    private ModifiableBigInteger integer1;

    private ModifiableBigInteger integer2;

    @BeforeEach
    public void setUp() {
        integer1 = new ModifiableBigInteger();
        integer1.setOriginalValue(BigInteger.ONE);
        integer2 = new ModifiableBigInteger();
        integer2.setOriginalValue(BigInteger.TEN);
    }

    /** Test of getAssertEquals method, of class ModifiableBigInteger. */
    @Disabled("Not yet implemented")
    @Test
    public void testGetAssertEquals() {}

    /** Test of setAssertEquals method, of class ModifiableBigInteger. */
    @Disabled("Not yet implemented")
    @Test
    public void testSetAssertEquals() {}

    /** Test of isOriginalValueModified method, of class ModifiableBigInteger. */
    @Disabled("Not yet implemented")
    @Test
    public void testIsOriginalValueModified() {}

    /** Test of getByteArray method, of class ModifiableBigInteger. */
    @Disabled("Not yet implemented")
    @Test
    public void testGetByteArray_0args() {}

    /** Test of getByteArray method, of class ModifiableBigInteger. */
    @Disabled("Not yet implemented")
    @Test
    public void testGetByteArray_int() {}

    /** Test of validateAssertions method, of class ModifiableBigInteger. */
    @Disabled("Not yet implemented")
    @Test
    public void testValidateAssertions() {}

    /** Test of getOriginalValue method, of class ModifiableBigInteger. */
    @Disabled("Not yet implemented")
    @Test
    public void testGetOriginalValue() {}

    /** Test of setOriginalValue method, of class ModifiableBigInteger. */
    @Disabled("Not yet implemented")
    @Test
    public void testSetOriginalValue() {}

    /** Test of toString method, of class ModifiableBigInteger. */
    @Disabled("Not yet implemented")
    @Test
    public void testToString() {}

    /** Test of equals method, of class ModifiableBigInteger. */
    @Test
    public void testEquals() {
        assertNotEquals(integer1, integer2);
        integer2.setOriginalValue(BigInteger.ONE);
        assertEquals(integer1, integer2);
    }

    /** Test of hashCode method, of class ModifiableBigInteger. */
    @Disabled("Not yet implemented")
    @Test
    public void testHashCode() {}
}
