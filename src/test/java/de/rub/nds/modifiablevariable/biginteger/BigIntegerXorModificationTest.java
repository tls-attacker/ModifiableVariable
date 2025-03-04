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

/**
 * @author captain
 */
public class BigIntegerXorModificationTest {

    private BigIntegerXorModification b1;
    private BigIntegerXorModification b2;
    private BigIntegerXorModification b3;
    private Integer integer1;

    @BeforeEach
    public void setUp() {
        b1 = new BigIntegerXorModification(BigInteger.ONE);
        b2 = new BigIntegerXorModification(BigInteger.TEN);
        b3 = new BigIntegerXorModification(BigInteger.ONE);
        integer1 = 1;
    }

    /** Test of modifyImplementationHook method, of class BigIntegerXorModification. */
    @Disabled("Not yet implemented")
    @Test
    public void testModifyImplementationHook() {}

    /** Test of getXor method, of class BigIntegerXorModification. */
    @Disabled("Not yet implemented")
    @Test
    public void testGetXor() {}

    /** Test of setXor method, of class BigIntegerXorModification. */
    @Disabled("Not yet implemented")
    @Test
    public void testSetXor() {}

    /** Test of hashCode method, of class BigIntegerXorModification. */
    @Disabled("Not yet implemented")
    @Test
    public void testHashCode() {}

    /** Test of equals method, of class BigIntegerXorModification. */
    @Test
    public void testEquals() {
        assertNotEquals(b1, b2);
        assertNotEquals(b1, integer1);
        assertEquals(b1, b1);
        assertEquals(b1, b3);
    }
}
