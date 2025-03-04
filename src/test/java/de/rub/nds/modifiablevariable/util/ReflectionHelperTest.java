/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.List;
import org.junit.jupiter.api.Test;

public class ReflectionHelperTest {

    // Helper method to set private fields using reflection
    private static void setPrivateField(Object object, String fieldName, Object value)
            throws IllegalAccessException {
        try {
            Field field = findField(object.getClass(), fieldName);
            field.setAccessible(true);
            field.set(object, value);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    // Helper method to find a field in a class or its superclasses
    private static Field findField(Class<?> clazz, String fieldName) throws NoSuchFieldException {
        try {
            return clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            if (clazz.getSuperclass() != null) {
                return findField(clazz.getSuperclass(), fieldName);
            }
            throw e;
        }
    }

    private static class BaseClass {
        private String baseField;
        private int baseIntField;
    }

    private static class ChildClass extends BaseClass {
        private String childField;
        private double childDoubleField;
    }

    private static class GenericClass<T> {
        private T genericField;
    }

    private static class StringGenericImpl extends GenericClass<String> {
        private String anotherField;
    }

    @Test
    public void testGetFieldsUpTo() {
        // Get all fields from ChildClass up to but not including Object
        List<Field> fields = ReflectionHelper.getFieldsUpTo(ChildClass.class, Object.class, null);

        // Should have 4 fields: childField, childDoubleField from ChildClass
        // and baseField, baseIntField from BaseClass
        assertEquals(4, fields.size());

        // Verify field names
        boolean foundChildField = false;
        boolean foundChildDoubleField = false;
        boolean foundBaseField = false;
        boolean foundBaseIntField = false;

        for (Field field : fields) {
            switch (field.getName()) {
                case "childField":
                    foundChildField = true;
                    assertEquals(String.class, field.getType());
                    break;
                case "childDoubleField":
                    foundChildDoubleField = true;
                    assertEquals(double.class, field.getType());
                    break;
                case "baseField":
                    foundBaseField = true;
                    assertEquals(String.class, field.getType());
                    break;
                case "baseIntField":
                    foundBaseIntField = true;
                    assertEquals(int.class, field.getType());
                    break;
            }
        }

        assertTrue(foundChildField);
        assertTrue(foundChildDoubleField);
        assertTrue(foundBaseField);
        assertTrue(foundBaseIntField);
    }

    @Test
    public void testGetFieldsUpToWithFilter() {
        // Get only String fields
        List<Field> stringFields =
                ReflectionHelper.getFieldsUpTo(ChildClass.class, Object.class, String.class);

        assertEquals(2, stringFields.size());

        // Verify they are the string fields
        boolean foundChildField = false;
        boolean foundBaseField = false;

        for (Field field : stringFields) {
            switch (field.getName()) {
                case "childField":
                    foundChildField = true;
                    assertEquals(String.class, field.getType());
                    break;
                case "baseField":
                    foundBaseField = true;
                    assertEquals(String.class, field.getType());
                    break;
            }
        }

        assertTrue(foundChildField);
        assertTrue(foundBaseField);
    }

    @Test
    public void testGetValuesFromFieldList() throws IllegalAccessException {
        ChildClass childClass = new ChildClass();
        // Use reflection to set private fields instead of direct access
        setPrivateField(childClass, "baseField", "BaseValue");
        setPrivateField(childClass, "baseIntField", 42);
        setPrivateField(childClass, "childField", "ChildValue");
        setPrivateField(childClass, "childDoubleField", 3.14);

        List<Field> fields = ReflectionHelper.getFieldsUpTo(ChildClass.class, Object.class, null);
        List<Object> values = ReflectionHelper.getValuesFromFieldList(childClass, fields);

        assertEquals(4, values.size());
        assertTrue(values.contains("BaseValue"));
        assertTrue(values.contains(42));
        assertTrue(values.contains("ChildValue"));
        assertTrue(values.contains(3.14));
    }

    @Test
    public void testGetParameterizedTypes() {
        StringGenericImpl impl = new StringGenericImpl();
        Type[] types = ReflectionHelper.getParameterizedTypes(impl);

        assertNotNull(types);
        assertEquals(1, types.length);
        assertEquals(String.class, types[0]);

        // Test with a non-parameterized class
        BaseClass baseClass = new BaseClass();
        types = ReflectionHelper.getParameterizedTypes(baseClass);

        assertNull(types);
    }
}
