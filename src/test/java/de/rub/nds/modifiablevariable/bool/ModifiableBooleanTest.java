/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.bool;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import de.rub.nds.modifiablevariable.string.ModifiableString;
import java.lang.reflect.Field;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ModifiableBooleanTest {

    private ModifiableBoolean boolean1;
    private ModifiableBoolean boolean2;
    private ModifiableBoolean boolean3;

    @BeforeEach
    void setUp() {
        boolean1 = new ModifiableBoolean();
        boolean1.setOriginalValue(Boolean.TRUE);

        boolean2 = new ModifiableBoolean();
        boolean2.setOriginalValue(Boolean.TRUE);

        boolean3 = new ModifiableBoolean(Boolean.FALSE);
    }

    /** Test of getOriginalValue method, of class ModifiableBoolean. */
    @Test
    void testGetOriginalValue() {
        assertEquals(Boolean.TRUE, boolean1.getOriginalValue());
        assertEquals(Boolean.FALSE, boolean3.getOriginalValue());
    }

    /** Test of setOriginalValue method, of class ModifiableBoolean. */
    @Test
    void testSetOriginalValue() {
        boolean1.setOriginalValue(Boolean.FALSE);
        assertEquals(Boolean.FALSE, boolean1.getOriginalValue());
    }

    /** Test of isOriginalValueModified method with unmodified value */
    @Test
    void testIsOriginalValueModifiedFalse() {
        assertFalse(boolean1.isOriginalValueModified());
    }

    /** Test of isOriginalValueModified method with modified value */
    @Test
    void testIsOriginalValueModifiedTrue() {
        BooleanToggleModification modification = new BooleanToggleModification();
        boolean1.addModification(modification);
        assertTrue(boolean1.isOriginalValueModified());
    }

    /** Test of isOriginalValueModified method with null original value */
    @Test
    static void testIsOriginalValueModifiedNull() {
        ModifiableBoolean nullBoolean = new ModifiableBoolean();
        assertThrows(IllegalStateException.class, nullBoolean::isOriginalValueModified);
    }

    /** Test of validateAssertions method with no assertions */
    @Test
    void testValidateAssertionsNoAssertions() {
        assertTrue(boolean1.validateAssertions());
    }

    /** Test of validateAssertions method with assertions equal to value */
    @Test
    void testValidateAssertionsEqualValue() throws Exception {
        setAssertEquals(boolean1, Boolean.TRUE);
        assertTrue(boolean1.validateAssertions());
    }

    /** Test of validateAssertions method with assertions not equal to value */
    @Test
    void testValidateAssertionsNotEqualValue() throws Exception {
        setAssertEquals(boolean1, Boolean.FALSE);
        assertFalse(boolean1.validateAssertions());
    }

    /** Helper method to set the protected assertEquals field using reflection */
    private static void setAssertEquals(ModifiableBoolean variable, Boolean value)
            throws Exception {
        Field field = variable.getClass().getSuperclass().getDeclaredField("assertEquals");
        field.setAccessible(true);
        field.set(variable, value);
    }

    /** Test of toString method */
    @Test
    void testToString() {
        String expected = "ModifiableBoolean{originalValue=true}";
        assertEquals(expected, boolean1.toString());

        BooleanToggleModification modification = new BooleanToggleModification();
        boolean1.addModification(modification);
        assertTrue(boolean1.toString().contains("originalValue=true"));
        // Just check for the modification class name, not for "modification=" prefix
        assertTrue(boolean1.toString().contains("BooleanToggleModification"));
    }

    /** Test of equals method with equal objects */
    @Test
    void testEquals() {
        // Same values
        assertEquals(boolean1, boolean2);

        // Different values
        boolean2.setOriginalValue(Boolean.FALSE);
        assertNotEquals(boolean1, boolean2);

        // Back to same values
        boolean2.setOriginalValue(Boolean.TRUE);
        assertEquals(boolean1, boolean2);

        // Verify symmetry
        assertTrue(boolean1.equals(boolean2));
        assertTrue(boolean2.equals(boolean1));
    }

    /** Test of equals method with same object (reflexivity) */
    @Test
    void testEqualsSameObject() {
        assertEquals(boolean1, boolean1);
        assertTrue(boolean1.equals(boolean1));
    }

    /** Test of equals method with null */
    @Test
    void testEqualsNull() {
        assertNotEquals(null, boolean1);
        assertFalse(boolean1.equals(null));
    }

    /** Test of equals method with different class */
    @Test
    void testEqualsDifferentClass() {
        // With Object
        assertNotEquals(boolean1, new Object());
        assertFalse(boolean1.equals(new Object()));

        // With another ModifiableVariable type
        ModifiableString stringVar = new ModifiableString();
        assertNotEquals(boolean1, stringVar);
        assertFalse(boolean1.equals(stringVar));
    }

    /** Test equals with transitivity property */
    @Test
    void testEqualsTransitivity() {
        ModifiableBoolean a = new ModifiableBoolean(Boolean.TRUE);
        ModifiableBoolean b = new ModifiableBoolean(Boolean.TRUE);
        ModifiableBoolean c = new ModifiableBoolean(Boolean.TRUE);

        // Test all combinations to verify transitivity
        assertTrue(a.equals(b));
        assertTrue(b.equals(c));
        assertTrue(a.equals(c));
    }

    /** Test of equals method with modified values */
    @Test
    void testEqualsModifiedValues() {
        BooleanToggleModification modification = new BooleanToggleModification();
        boolean1.addModification(modification);
        boolean2.addModification(modification);
        assertEquals(boolean1, boolean2);
        assertTrue(boolean1.equals(boolean2));

        // Different modifications that produce the same final value should result in equal objects
        ModifiableBoolean bool3 = new ModifiableBoolean(Boolean.TRUE);
        BooleanExplicitValueModification explicitMod = new BooleanExplicitValueModification(false);
        bool3.addModification(explicitMod);

        // Should still be equal because the values are equal, even with different modifications
        assertEquals(bool3.getValue(), boolean2.getValue());
        assertEquals(bool3, boolean2);

        // Change the value of one with a different modification
        BooleanExplicitValueModification trueMod = new BooleanExplicitValueModification(true);
        bool3.clearModifications();
        bool3.addModification(trueMod);
        // Now bool3 should have value true, while boolean1 has value false (toggled from true)
        assertNotEquals(boolean1.getValue(), bool3.getValue());
        assertNotEquals(boolean1, bool3);
    }

    /** Test equals with both null values */
    @Test
    static void testEqualsWithNullValues() {
        ModifiableBoolean nullBool1 = new ModifiableBoolean();
        ModifiableBoolean nullBool2 = new ModifiableBoolean();

        // Two uninitialized objects should be equal
        assertEquals(nullBool1, nullBool2);
        assertTrue(nullBool1.equals(nullBool2));

        // Initialized but with null value
        nullBool1.setOriginalValue(null);
        nullBool2.setOriginalValue(null);
        assertEquals(nullBool1, nullBool2);
        assertTrue(nullBool1.equals(nullBool2));

        // One null, one not null
        ModifiableBoolean notNullBool = new ModifiableBoolean(Boolean.TRUE);
        assertNotEquals(nullBool1, notNullBool);
        assertFalse(nullBool1.equals(notNullBool));
    }

    /** Test equals with assertions */
    @Test
    void testEqualsWithAssertions() throws Exception {
        ModifiableBoolean bool1WithAssertion = new ModifiableBoolean(Boolean.TRUE);
        ModifiableBoolean bool2WithAssertion = new ModifiableBoolean(Boolean.TRUE);

        // Initially equal
        assertEquals(bool1WithAssertion, bool2WithAssertion);

        // Add assertion to one
        setAssertEquals(bool1WithAssertion, Boolean.TRUE);

        // Should still compare only the values, not the assertions
        assertEquals(bool1WithAssertion, bool2WithAssertion);

        // Add different assertion
        setAssertEquals(bool2WithAssertion, Boolean.FALSE);

        // Still equal because assertions are not part of equals
        assertEquals(bool1WithAssertion, bool2WithAssertion);
    }

    /** Test of hashCode method for equal objects */
    @Test
    void testHashCode() {
        // Equal objects should have equal hash codes
        assertEquals(boolean1.hashCode(), boolean2.hashCode());

        // Same object returns same hash code
        assertEquals(boolean1.hashCode(), boolean1.hashCode());

        // Different objects with same values have same hash code
        ModifiableBoolean anotherBool = new ModifiableBoolean(Boolean.TRUE);
        assertEquals(boolean1.hashCode(), anotherBool.hashCode());

        // When modification is added, hash code should change
        BooleanToggleModification modification = new BooleanToggleModification();
        boolean1.addModification(modification);
        assertNotEquals(boolean1.hashCode(), boolean2.hashCode());

        // Objects with same modifications should have same hash code
        boolean2.addModification(modification);
        assertEquals(boolean1.hashCode(), boolean2.hashCode());
    }

    /** Test of hashCode with null original value */
    @Test
    void testHashCodeWithNullValue() {
        ModifiableBoolean nullBool1 = new ModifiableBoolean();
        ModifiableBoolean nullBool2 = new ModifiableBoolean();

        // Two objects with null values should have same hash code
        assertEquals(nullBool1.hashCode(), nullBool2.hashCode());

        // After setting to same value, hash codes should be equal
        nullBool1.setOriginalValue(Boolean.TRUE);
        nullBool2.setOriginalValue(Boolean.TRUE);
        assertEquals(nullBool1.hashCode(), nullBool2.hashCode());

        // Should match hash code of other objects with same value
        assertEquals(boolean1.hashCode(), nullBool1.hashCode());
    }

    /** Test hashCode consistency with modifications */
    @Test
    void testHashCodeConsistencyWithModifications() {
        // Capture original hash code
        int originalHash = boolean1.hashCode();

        // Add a modification that doesn't change the boolean value (TRUE remains TRUE)
        BooleanExplicitValueModification explicitMod = new BooleanExplicitValueModification(true);
        boolean1.addModification(explicitMod);

        // Hash code is based on value, so should match when value doesn't change
        int modifiedHash = boolean1.hashCode();
        assertEquals(originalHash, modifiedHash);

        // Add a modification that changes value (TRUE to FALSE)
        boolean1.clearModifications();
        BooleanToggleModification toggleMod = new BooleanToggleModification();
        boolean1.addModification(toggleMod);

        // Hash code should change
        int toggledHash = boolean1.hashCode();
        assertNotEquals(originalHash, toggledHash);

        // Should match hash code of an object with FALSE
        assertEquals(boolean3.hashCode(), toggledHash);
    }

    /** Test of constructor with initial value */
    @Test
    void testConstructorWithValue() {
        ModifiableBoolean booleanVar = new ModifiableBoolean(Boolean.TRUE);
        assertEquals(Boolean.TRUE, booleanVar.getOriginalValue());
    }

    /** Test of copy constructor */
    @Test
    void testCopyConstructor() {
        BooleanToggleModification modification = new BooleanToggleModification();
        boolean1.addModification(modification);

        ModifiableBoolean copy = new ModifiableBoolean(boolean1);
        assertEquals(boolean1.getOriginalValue(), copy.getOriginalValue());
        assertEquals(boolean1.getValue(), copy.getValue());
        assertEquals(boolean1, copy);
    }

    /** Test of createCopy method */
    @Test
    void testCreateCopy() {
        BooleanToggleModification modification = new BooleanToggleModification();
        boolean1.addModification(modification);

        ModifiableBoolean copy = boolean1.createCopy();
        assertEquals(boolean1.getOriginalValue(), copy.getOriginalValue());
        assertEquals(boolean1.getValue(), copy.getValue());
        assertEquals(boolean1, copy);
        assertNotEquals(System.identityHashCode(boolean1), System.identityHashCode(copy));
    }

    /** Test of containsAssertion method */
    @Test
    void testContainsAssertion() throws Exception {
        assertFalse(boolean1.containsAssertion());

        setAssertEquals(boolean1, Boolean.TRUE);
        assertTrue(boolean1.containsAssertion());
    }
}
