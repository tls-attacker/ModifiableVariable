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

public class BigIntegerExplicitValueModificationTest {

    private BigIntegerExplicitValueModification b1;
    private BigIntegerExplicitValueModification b2;
    private BigIntegerExplicitValueModification b3;
    private Integer integer1;

    @BeforeEach
    public void setUp() {
        b1 = new BigIntegerExplicitValueModification(BigInteger.ONE);
        b2 = new BigIntegerExplicitValueModification(BigInteger.TEN);
        b3 = new BigIntegerExplicitValueModification(BigInteger.ONE);
        integer1 = 1;
    }

    /** Test of modifyImplementationHook method, of class BigIntegerExplicitValueModification. */
    @Disabled("Not yet implemented")
    @Test
    public void testModifyImplementationHook() {}

    /** Test of getExplicitValue method, of class BigIntegerExplicitValueModification. */
    @Disabled("Not yet implemented")
    @Test
    public void testGetExplicitValue() {}

    /** Test of setExplicitValue method, of class BigIntegerExplicitValueModification. */
    @Disabled("Not yet implemented")
    @Test
    public void testSetExplicitValue() {}

    /** Test of hashCode method, of class BigIntegerExplicitValueModification. */
    @Disabled("Not yet implemented")
    @Test
    public void testHashCode() {}

    /** Test of equals method, of class BigIntegerExplicitValueModification. */
    @Test
    public void testEquals() {
        assertNotEquals(b1, b2);
        assertNotEquals(b1, integer1);
        assertEquals(b1, b1);
        assertEquals(b1, b3);
    }
}
