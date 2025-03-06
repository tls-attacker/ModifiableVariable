/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import de.rub.nds.modifiablevariable.integer.ModifiableInteger;
import de.rub.nds.modifiablevariable.util.ReflectionHelper;
import java.lang.reflect.Field;
import java.util.List;
import org.junit.jupiter.api.Test;

public class HoldsModifiableVariableTest {

    // Test class with HoldsModifiableVariable annotations
    private static class TestClass {
        @HoldsModifiableVariable private ModifiableVariableHolder holder;

        @HoldsModifiableVariable private List<ModifiableInteger> integerList;

        // No annotation
        private ModifiableVariableHolder regularHolder;
    }

    @Test
    void testAnnotationPresence() throws NoSuchFieldException {
        Field holderField = TestClass.class.getDeclaredField("holder");
        HoldsModifiableVariable annotation =
                holderField.getAnnotation(HoldsModifiableVariable.class);

        assertNotNull(annotation);
    }

    @Test
    void testListFieldAnnotation() throws NoSuchFieldException {
        Field listField = TestClass.class.getDeclaredField("integerList");
        HoldsModifiableVariable annotation = listField.getAnnotation(HoldsModifiableVariable.class);

        assertNotNull(annotation);
    }

    @Test
    void testNoAnnotation() throws NoSuchFieldException {
        Field regularField = TestClass.class.getDeclaredField("regularHolder");
        HoldsModifiableVariable annotation =
                regularField.getAnnotation(HoldsModifiableVariable.class);

        assertNull(annotation);
    }

    @Test
    void testFindAnnotatedFields() {
        // Use ReflectionHelper to find fields with the annotation
        List<Field> fields = ReflectionHelper.getFieldsUpTo(TestClass.class, Object.class, null);

        int annotatedCount = 0;
        for (Field field : fields) {
            if (field.isAnnotationPresent(HoldsModifiableVariable.class)) {
                annotatedCount++;
            }
        }

        // We should find 2 annotated fields: holder and integerList
        assertEquals(2, annotatedCount);
    }
}
