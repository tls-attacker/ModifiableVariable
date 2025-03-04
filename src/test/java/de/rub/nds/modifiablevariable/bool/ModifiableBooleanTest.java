/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.bool;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class ModifiableBooleanTest {

    private ModifiableBoolean boolean1;

    private ModifiableBoolean boolean2;

    @BeforeEach
    public void setUp() {
        boolean1 = new ModifiableBoolean();
        boolean1.setOriginalValue(Boolean.TRUE);
        boolean2 = new ModifiableBoolean();
        boolean2.setOriginalValue(Boolean.TRUE);
    }

    /** Test of getOriginalValue method, of class ModifiableBoolean. */
    @Disabled("Not yet implemented")
    @Test
    public void testGetOriginalValue() {}

    /** Test of setOriginalValue method, of class ModifiableBoolean. */
    @Disabled("Not yet implemented")
    @Test
    public void testSetOriginalValue() {}

    /** Test of isOriginalValueModified method, of class ModifiableBoolean. */
    @Disabled("Not yet implemented")
    @Test
    public void testIsOriginalValueModified() {}

    /** Test of validateAssertions method, of class ModifiableBoolean. */
    @Disabled("Not yet implemented")
    @Test
    public void testValidateAssertions() {}

    /** Test of toString method, of class ModifiableBoolean. */
    @Disabled("Not yet implemented")
    @Test
    public void testToString() {}

    /** Test of equals method, of class ModifiableBoolean. */
    @Test
    public void testEquals() {
        assertEquals(boolean1, boolean2);
        boolean2.setOriginalValue(Boolean.FALSE);
        assertNotEquals(boolean1, boolean2);
    }

    /** Test of hashCode method, of class ModifiableBoolean. */
    @Disabled("Not yet implemented")
    @Test
    public void testHashCode() {}
}
