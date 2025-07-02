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
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import de.rub.nds.modifiablevariable.ModifiableVariable;
import de.rub.nds.modifiablevariable.integer.ModifiableInteger;
import de.rub.nds.modifiablevariable.string.ModifiableString;
import java.lang.reflect.Field;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ModifiableVariableFieldTest {

    private static class TestClass {
        private ModifiableInteger integerField = new ModifiableInteger(42);
        private ModifiableString stringField = new ModifiableString("test");
        private String nonModifiableField = "not modifiable";
    }

    private TestClass testObject;
    private Field integerField;
    private Field stringField;
    private Field nonModifiableField;

    @BeforeEach
    void setUp() throws NoSuchFieldException {
        testObject = new TestClass();
        integerField = TestClass.class.getDeclaredField("integerField");
        stringField = TestClass.class.getDeclaredField("stringField");
        nonModifiableField = TestClass.class.getDeclaredField("nonModifiableField");
    }

    @Test
    void testConstructor() {
        ModifiableVariableField field = new ModifiableVariableField(testObject, integerField);

        assertNotNull(field);
        assertSame(testObject, field.getObject());
        assertSame(integerField, field.getField());
    }

    @Test
    void testGetObject() {
        ModifiableVariableField field = new ModifiableVariableField(testObject, integerField);

        Object result = field.getObject();

        assertSame(testObject, result);
    }

    @Test
    void testSetObject() {
        ModifiableVariableField field = new ModifiableVariableField(testObject, integerField);
        TestClass newObject = new TestClass();

        field.setObject(newObject);

        assertSame(newObject, field.getObject());
    }

    @Test
    void testGetField() {
        ModifiableVariableField field = new ModifiableVariableField(testObject, integerField);

        Field result = field.getField();

        assertSame(integerField, result);
    }

    @Test
    void testSetField() {
        ModifiableVariableField field = new ModifiableVariableField(testObject, integerField);

        field.setField(stringField);

        assertSame(stringField, field.getField());
    }

    @Test
    void testGetModifiableVariable_Integer()
            throws IllegalArgumentException, IllegalAccessException {
        ModifiableVariableField field = new ModifiableVariableField(testObject, integerField);

        ModifiableVariable<?> result = field.getModifiableVariable();

        assertNotNull(result);
        assertEquals(ModifiableInteger.class, result.getClass());
        assertEquals(42, ((ModifiableInteger) result).getValue().intValue());
    }

    @Test
    void testGetModifiableVariable_String()
            throws IllegalArgumentException, IllegalAccessException {
        ModifiableVariableField field = new ModifiableVariableField(testObject, stringField);

        ModifiableVariable<?> result = field.getModifiableVariable();

        assertNotNull(result);
        assertEquals(ModifiableString.class, result.getClass());
        assertEquals("test", ((ModifiableString) result).getValue());
    }

    @Test
    void testGetModifiableVariable_NonModifiableField() {
        ModifiableVariableField field = new ModifiableVariableField(testObject, nonModifiableField);

        assertThrows(
                ClassCastException.class,
                () -> {
                    field.getModifiableVariable();
                });
    }

    @Test
    void testGetModifiableVariable_NullObject() {
        ModifiableVariableField field = new ModifiableVariableField(null, integerField);

        assertThrows(
                NullPointerException.class,
                () -> {
                    field.getModifiableVariable();
                });
    }
}
