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
import de.rub.nds.modifiablevariable.biginteger.ModifiableBigInteger;
import de.rub.nds.modifiablevariable.bytearray.ModifiableByteArray;
import de.rub.nds.modifiablevariable.integer.ModifiableInteger;
import de.rub.nds.modifiablevariable.string.ModifiableString;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Comprehensive test class for ModifiableVariableAnalyzer.
 *
 * <p>Tests all public methods of the ModifiableVariableAnalyzer class with various inputs including
 * edge cases like null objects, empty collections, and nested object hierarchies.
 */
class ModifiableVariableAnalyzerTest {

    private SimpleClassWithModVariables simpleObject;
    private ComplexClassWithModVariables complexObject;
    private SimpleClassWithModVariablesList listObject;
    private ClassWithModVariableArray arrayObject;
    private ClassWithNoModVariables emptyObject;

    @BeforeEach
    void setUp() {
        // Initialize test objects
        simpleObject = new SimpleClassWithModVariables();
        simpleObject.bi = new ModifiableBigInteger();
        simpleObject.array = new ModifiableByteArray();
        simpleObject.i = new ModifiableInteger();

        complexObject = new ComplexClassWithModVariables();
        complexObject.str = new ModifiableString();
        complexObject.nested = new SimpleClassWithModVariables();
        complexObject.nested.bi = new ModifiableBigInteger();

        listObject = new SimpleClassWithModVariablesList();
        listObject.bi = new ModifiableBigInteger();
        listObject.list = new LinkedList<>();
        listObject.list.add(new SimpleClassWithModVariables());
        listObject.list.add(new SimpleClassWithModVariables());

        arrayObject = new ClassWithModVariableArray();
        arrayObject.objects = new SimpleClassWithModVariables[2];
        arrayObject.objects[0] = new SimpleClassWithModVariables();
        arrayObject.objects[1] = new SimpleClassWithModVariables();
        arrayObject.objects[0].bi = new ModifiableBigInteger();
        arrayObject.objects[1].bi = new ModifiableBigInteger();

        emptyObject = new ClassWithNoModVariables();
    }

    /** Test of getAllModifiableVariableFields method with a simple object. */
    @Test
    void testGetAllModifiableVariableFields() {
        String[] fieldNames = {"bi", "array", "i"};
        List<Field> fields =
                ModifiableVariableAnalyzer.getAllModifiableVariableFields(simpleObject);

        // Check size
        assertEquals(3, fields.size());

        // Check field names
        for (String fn : fieldNames) {
            assertTrue(containsFieldName(fn, fields), "Missing field: " + fn);
        }

        // Check against non-existent field
        assertFalse(containsFieldName("nonExistentField", fields));
    }

    /** Test getAllModifiableVariableFields with object having no modifiable variables. */
    @Test
    void testGetAllModifiableVariableFieldsEmpty() {
        List<Field> fields = ModifiableVariableAnalyzer.getAllModifiableVariableFields(emptyObject);
        assertTrue(fields.isEmpty(), "Empty object should have no modifiable fields");
    }

    /** Test of getRandomModifiableVariableField method. */
    @Test
    void testGetRandomModifiableVariableField() {
        // Test with object containing modifiable variables
        Field randomField =
                ModifiableVariableAnalyzer.getRandomModifiableVariableField(simpleObject);
        assertNotNull(randomField, "Should return a random field");
        assertTrue(
                randomField.getName().equals("bi")
                        || randomField.getName().equals("array")
                        || randomField.getName().equals("i"),
                "Field should be one of the modifiable variables");

        // Test with object having no modifiable variables
        Field nullField = ModifiableVariableAnalyzer.getRandomModifiableVariableField(emptyObject);
        assertNull(nullField, "Should return null for object with no modifiable variables");
    }

    /** Test of isModifiableVariableHolder method. */
    @Test
    void testIsModifiableVariableHolder() {
        // Test with object containing modifiable variables
        assertTrue(
                ModifiableVariableAnalyzer.isModifiableVariableHolder(simpleObject),
                "Should return true for object with modifiable variables");

        // Test with object having no modifiable variables
        assertFalse(
                ModifiableVariableAnalyzer.isModifiableVariableHolder(emptyObject),
                "Should return false for object with no modifiable variables");

        // Test with null object (should throw NullPointerException)
        assertThrows(
                NullPointerException.class,
                () -> ModifiableVariableAnalyzer.isModifiableVariableHolder(null),
                "Should throw NullPointerException for null input");
    }

    /** Test of getAllModifiableVariableFieldsRecursively method with simple object. */
    @Test
    void testGetAllModifiableVariableFieldsRecursively() {
        // Test with simple object
        List<ModifiableVariableField> fields =
                ModifiableVariableAnalyzer.getAllModifiableVariableFieldsRecursively(simpleObject);
        assertEquals(3, fields.size(), "Should find all 3 fields in simple object");

        // Make sure we can access the modifiable variables
        for (ModifiableVariableField field : fields) {
            try {
                ModifiableVariable<?> modVar = field.getModifiableVariable();
                assertNotNull(modVar, "Modifiable variable should not be null");
            } catch (Exception e) {
                fail("Should be able to access modifiable variable: " + e.getMessage());
            }
        }
    }

    /** Test getAllModifiableVariableFieldsRecursively with nested objects. */
    @Test
    void testGetAllModifiableVariableFieldsRecursivelyNested() {
        List<ModifiableVariableField> fields =
                ModifiableVariableAnalyzer.getAllModifiableVariableFieldsRecursively(complexObject);

        // Complex object has 1 field + 3 from nested = 4 fields
        assertEquals(4, fields.size(), "Should find all fields in complex object hierarchy");
    }

    /** Test getAllModifiableVariableFieldsRecursively with a List. */
    @Test
    void testGetAllModifiableVariableFieldsRecursivelyWithList() {
        List<ModifiableVariableField> fields =
                ModifiableVariableAnalyzer.getAllModifiableVariableFieldsRecursively(listObject);

        // The actual number of fields depends on the implementation details
        // 1 field in ListObject + 4 fields * 2 objects in list = 9 fields
        assertEquals(9, fields.size(), "Should find all fields in list and contained objects");
    }

    /** Test getAllModifiableVariableFieldsRecursively with an array. */
    @Test
    void testGetAllModifiableVariableFieldsRecursivelyWithArray() {
        List<ModifiableVariableField> fields =
                ModifiableVariableAnalyzer.getAllModifiableVariableFieldsRecursively(arrayObject);

        // 0 fields in ArrayObject + 3 fields * 2 objects in array = 6 fields
        assertEquals(6, fields.size(), "Should find all fields in array and contained objects");
    }

    /** Test that the order of fields is consistent when analyzing the same type of object. */
    @Test
    void testGetAllModifiableVariableFieldsRecursivelyOrder() {
        // Create two identical objects with nested structure
        SimpleClassWithModVariables test1 = new SimpleClassWithModVariables();
        test1.bi = new ModifiableBigInteger();
        test1.test = new SimpleClassWithModVariables();

        SimpleClassWithModVariables test2 = new SimpleClassWithModVariables();
        test2.bi = new ModifiableBigInteger();
        test2.test = new SimpleClassWithModVariables();

        List<ModifiableVariableField> fields1 =
                ModifiableVariableAnalyzer.getAllModifiableVariableFieldsRecursively(test1);
        List<ModifiableVariableField> fields2 =
                ModifiableVariableAnalyzer.getAllModifiableVariableFieldsRecursively(test2);

        assertEquals(
                fields1.size(), fields2.size(), "Both objects should have same number of fields");

        // Check that field order is consistent
        for (int i = 0; i < fields1.size(); i++) {
            String name1 = fields1.get(i).getField().getName();
            String name2 = fields2.get(i).getField().getName();
            assertEquals(name1, name2, "Field order should be consistent");
        }
    }

    /** Test of getAllModifiableVariableHoldersRecursively method. */
    @Test
    void testGetAllModifiableVariableHoldersRecursively() {
        // Test with simple object
        List<ModifiableVariableListHolder> holders =
                ModifiableVariableAnalyzer.getAllModifiableVariableHoldersRecursively(simpleObject);
        assertEquals(1, holders.size(), "Should find 1 holder object");
        assertEquals(3, holders.get(0).getFields().size(), "Holder should contain 3 fields");

        // Test with complex object
        holders =
                ModifiableVariableAnalyzer.getAllModifiableVariableHoldersRecursively(
                        complexObject);
        assertEquals(2, holders.size(), "Should find 2 holder objects");

        // Test with empty object
        holders =
                ModifiableVariableAnalyzer.getAllModifiableVariableHoldersRecursively(emptyObject);
        assertTrue(holders.isEmpty(), "Should find no holders in empty object");
    }

    /** Test getAllModifiableVariableHoldersFromList method. */
    @Test
    void testGetAllModifiableVariableHoldersFromList() {
        // Create a list with two objects containing modifiable variables
        List<Object> testList = new LinkedList<>();
        testList.add(new SimpleClassWithModVariables());
        testList.add(new SimpleClassWithModVariables());
        testList.add(null); // Add a null element to test null handling

        List<ModifiableVariableListHolder> holders =
                ModifiableVariableAnalyzer.getAllModifiableVariableHoldersFromList(testList);

        assertEquals(2, holders.size(), "Should find 2 holder objects, ignoring null");
    }

    /** Test getAllModifiableVariableHoldersFromList with empty list. */
    @Test
    void testGetAllModifiableVariableHoldersFromEmptyList() {
        List<Object> emptyList = Collections.emptyList();
        List<ModifiableVariableListHolder> holders =
                ModifiableVariableAnalyzer.getAllModifiableVariableHoldersFromList(emptyList);

        assertTrue(holders.isEmpty(), "Should return empty list for empty input");
    }

    /** Test getAllModifiableVariableHoldersFromArray method. */
    @Test
    void testGetAllModifiableVariableHoldersFromArray() {
        // Create an array with two objects containing modifiable variables
        Object[] testArray = new Object[2];
        testArray[0] = new SimpleClassWithModVariables();
        testArray[1] = new SimpleClassWithModVariables();

        List<ModifiableVariableListHolder> holders =
                ModifiableVariableAnalyzer.getAllModifiableVariableHoldersFromArray(testArray);

        assertEquals(2, holders.size(), "Should find 2 holder objects");
    }

    /** Test getAllModifiableVariableHoldersFromArray with empty array. */
    @Test
    void testGetAllModifiableVariableHoldersFromEmptyArray() {
        Object[] emptyArray = new Object[0];
        List<ModifiableVariableListHolder> holders =
                ModifiableVariableAnalyzer.getAllModifiableVariableHoldersFromArray(emptyArray);

        assertTrue(holders.isEmpty(), "Should return empty list for empty array");
    }

    /** Utility method to check if a list of Fields contains a field with the given name. */
    private static boolean containsFieldName(String name, List<Field> list) {
        for (Field f : list) {
            if (f.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    /** Simple class with ModifiableVariable fields. */
    private static class SimpleClassWithModVariables {
        @SuppressWarnings("unused")
        Integer x;

        @SuppressWarnings("unused")
        ModifiableBigInteger bi;

        @SuppressWarnings("unused")
        ModifiableByteArray array;

        @SuppressWarnings("unused")
        ModifiableInteger i;

        @HoldsModifiableVariable SimpleClassWithModVariables test;
    }

    /** Complex class with nested ModifiableVariable fields. */
    private static class ComplexClassWithModVariables {
        @SuppressWarnings("unused")
        String regularString;

        @SuppressWarnings("unused")
        ModifiableString str;

        @HoldsModifiableVariable SimpleClassWithModVariables nested;
    }

    /** Class with a List of objects containing ModifiableVariable fields. */
    private static class SimpleClassWithModVariablesList {
        @SuppressWarnings("unused")
        Integer x;

        @SuppressWarnings("unused")
        ModifiableBigInteger bi;

        @SuppressWarnings("unused")
        ModifiableByteArray array;

        @SuppressWarnings("unused")
        ModifiableInteger i;

        @HoldsModifiableVariable SimpleClassWithModVariables test;
        @HoldsModifiableVariable List<SimpleClassWithModVariables> list;
    }

    /** Class with an array of objects containing ModifiableVariable fields. */
    private static class ClassWithModVariableArray {
        @SuppressWarnings("unused")
        String name;

        @HoldsModifiableVariable SimpleClassWithModVariables[] objects;
    }

    /** Class with no ModifiableVariable fields. */
    private static class ClassWithNoModVariables {
        @SuppressWarnings("unused")
        String name;

        @SuppressWarnings("unused")
        int value;

        @SuppressWarnings("unused")
        BigInteger bigInt;
    }
}
