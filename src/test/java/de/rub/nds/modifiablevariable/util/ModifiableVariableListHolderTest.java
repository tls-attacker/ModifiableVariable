/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.util;

import static org.junit.jupiter.api.Assertions.*;

import de.rub.nds.modifiablevariable.HoldsModifiableVariable;
import de.rub.nds.modifiablevariable.ModifiableVariable;
import de.rub.nds.modifiablevariable.bytearray.ModifiableByteArray;
import de.rub.nds.modifiablevariable.integer.ModifiableInteger;
import de.rub.nds.modifiablevariable.string.ModifiableString;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;

/** Tests for the {@link ModifiableVariableListHolder} class. */
public class ModifiableVariableListHolderTest {

    /** Test class with modifiable variable fields for testing. */
    private static class TestClass {
        @HoldsModifiableVariable private ModifiableInteger intValue;

        @HoldsModifiableVariable private ModifiableString stringValue;

        private String regularString; // Not a modifiable variable

        @HoldsModifiableVariable private ModifiableByteArray byteArrayValue;
    }

    /** Test constructor creates a properly initialized object. */
    @Test
    public void testConstructor() {
        TestClass testObject = new TestClass();
        List<Field> fieldList = new ArrayList<>();

        try {
            fieldList.add(TestClass.class.getDeclaredField("intValue"));
            fieldList.add(TestClass.class.getDeclaredField("stringValue"));
        } catch (NoSuchFieldException e) {
            fail("Could not initialize test: " + e.getMessage());
        }

        ModifiableVariableListHolder holder =
                new ModifiableVariableListHolder(testObject, fieldList);

        assertNotNull(holder, "Holder should not be null");
        assertEquals(testObject, holder.getObject(), "Object should match the constructor input");
        assertEquals(
                fieldList, holder.getFields(), "Fields list should match the constructor input");
        assertEquals(2, holder.getFields().size(), "Fields list should contain exactly 2 fields");
    }

    /** Test constructor with null object. */
    @Test
    public void testConstructorWithNullObject() {
        List<Field> fieldList = new ArrayList<>();

        ModifiableVariableListHolder holder = new ModifiableVariableListHolder(null, fieldList);

        assertNull(holder.getObject(), "Object should be null");
        assertEquals(
                fieldList, holder.getFields(), "Fields list should match the constructor input");
    }

    /** Test constructor with null fields list. */
    @Test
    public void testConstructorWithNullFields() {
        TestClass testObject = new TestClass();

        ModifiableVariableListHolder holder = new ModifiableVariableListHolder(testObject, null);

        assertEquals(testObject, holder.getObject(), "Object should match the constructor input");
        assertNull(holder.getFields(), "Fields list should be null");
    }

    /** Test constructor with empty fields list. */
    @Test
    public void testConstructorWithEmptyFields() {
        TestClass testObject = new TestClass();
        List<Field> fieldList = Collections.emptyList();

        ModifiableVariableListHolder holder =
                new ModifiableVariableListHolder(testObject, fieldList);

        assertEquals(testObject, holder.getObject(), "Object should match the constructor input");
        assertEquals(
                fieldList, holder.getFields(), "Fields list should match the constructor input");
        assertTrue(holder.getFields().isEmpty(), "Fields list should be empty");
    }

    /** Test setter for object. */
    @Test
    public void testSetObject() {
        TestClass testObject1 = new TestClass();
        TestClass testObject2 = new TestClass();
        List<Field> fieldList = new ArrayList<>();

        ModifiableVariableListHolder holder =
                new ModifiableVariableListHolder(testObject1, fieldList);
        assertEquals(testObject1, holder.getObject(), "Object should match initial value");

        holder.setObject(testObject2);
        assertEquals(testObject2, holder.getObject(), "Object should be updated");
    }

    /** Test setter for fields. */
    @Test
    public void testSetFields() {
        TestClass testObject = new TestClass();
        List<Field> fieldList1 = new ArrayList<>();
        List<Field> fieldList2 = new ArrayList<>();

        try {
            fieldList1.add(TestClass.class.getDeclaredField("intValue"));
            fieldList2.add(TestClass.class.getDeclaredField("stringValue"));
            fieldList2.add(TestClass.class.getDeclaredField("byteArrayValue"));
        } catch (NoSuchFieldException e) {
            fail("Could not initialize test: " + e.getMessage());
        }

        ModifiableVariableListHolder holder =
                new ModifiableVariableListHolder(testObject, fieldList1);
        assertEquals(fieldList1, holder.getFields(), "Fields should match initial value");
        assertEquals(1, holder.getFields().size(), "Fields list should contain exactly 1 field");

        holder.setFields(fieldList2);
        assertEquals(fieldList2, holder.getFields(), "Fields should be updated");
        assertEquals(2, holder.getFields().size(), "Fields list should contain exactly 2 fields");
    }

    /** Test setting fields to null. */
    @Test
    public void testSetFieldsToNull() {
        TestClass testObject = new TestClass();
        List<Field> fieldList = new ArrayList<>();

        try {
            fieldList.add(TestClass.class.getDeclaredField("intValue"));
        } catch (NoSuchFieldException e) {
            fail("Could not initialize test: " + e.getMessage());
        }

        ModifiableVariableListHolder holder =
                new ModifiableVariableListHolder(testObject, fieldList);
        assertNotNull(holder.getFields(), "Fields should not be null initially");

        holder.setFields(null);
        assertNull(holder.getFields(), "Fields should be null after update");
    }

    /** Test that all fields are actually ModifiableVariable fields. */
    @Test
    public void testFieldsAreModifiableVariables() {
        TestClass testObject = new TestClass();
        testObject.intValue = new ModifiableInteger();
        testObject.stringValue = new ModifiableString();
        testObject.byteArrayValue = new ModifiableByteArray();

        List<Field> fieldList = new ArrayList<>();

        try {
            Field intField = TestClass.class.getDeclaredField("intValue");
            Field stringField = TestClass.class.getDeclaredField("stringValue");
            Field byteArrayField = TestClass.class.getDeclaredField("byteArrayValue");

            fieldList.add(intField);
            fieldList.add(stringField);
            fieldList.add(byteArrayField);
        } catch (NoSuchFieldException e) {
            fail("Could not initialize test: " + e.getMessage());
        }

        ModifiableVariableListHolder holder =
                new ModifiableVariableListHolder(testObject, fieldList);

        // Verify that all fields in the holder are ModifiableVariable fields
        for (Field field : holder.getFields()) {
            assertTrue(
                    ModifiableVariable.class.isAssignableFrom(field.getType()),
                    "Field " + field.getName() + " should be a ModifiableVariable");

            try {
                // Make the field accessible for testing
                field.setAccessible(true);
                Object fieldValue = field.get(testObject);
                assertNotNull(fieldValue, "ModifiableVariable field value should not be null");
                assertTrue(
                        fieldValue instanceof ModifiableVariable,
                        "Field "
                                + field.getName()
                                + " value should be instance of ModifiableVariable");
            } catch (IllegalAccessException e) {
                fail("Could not access field: " + e.getMessage());
            }
        }
    }
}
