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

import de.rub.nds.modifiablevariable.ModifiableVariableProperty.Format;
import de.rub.nds.modifiablevariable.ModifiableVariableProperty.Type;
import de.rub.nds.modifiablevariable.integer.ModifiableInteger;
import java.lang.reflect.Field;
import org.junit.jupiter.api.Test;

public class ModifiableVariablePropertyTest {

    // Test class with annotated fields
    private static class TestClass {
        @ModifiableVariableProperty(type = Type.LENGTH)
        private ModifiableInteger length;

        @ModifiableVariableProperty(type = Type.COUNT)
        private ModifiableInteger count;

        @ModifiableVariableProperty(type = Type.SIGNATURE, format = Format.ASN1)
        private ModifiableInteger signatureWithFormat;

        @ModifiableVariableProperty private ModifiableInteger defaultProperty;
    }

    @Test
    public void testLengthProperty() throws NoSuchFieldException {
        Field lengthField = TestClass.class.getDeclaredField("length");
        ModifiableVariableProperty annotation =
                lengthField.getAnnotation(ModifiableVariableProperty.class);

        assertNotNull(annotation);
        assertEquals(Type.LENGTH, annotation.type());
        assertEquals(Format.NONE, annotation.format());
    }

    @Test
    public void testCountProperty() throws NoSuchFieldException {
        Field countField = TestClass.class.getDeclaredField("count");
        ModifiableVariableProperty annotation =
                countField.getAnnotation(ModifiableVariableProperty.class);

        assertNotNull(annotation);
        assertEquals(Type.COUNT, annotation.type());
        assertEquals(Format.NONE, annotation.format());
    }

    @Test
    public void testSignatureWithFormatProperty() throws NoSuchFieldException {
        Field signatureField = TestClass.class.getDeclaredField("signatureWithFormat");
        ModifiableVariableProperty annotation =
                signatureField.getAnnotation(ModifiableVariableProperty.class);

        assertNotNull(annotation);
        assertEquals(Type.SIGNATURE, annotation.type());
        assertEquals(Format.ASN1, annotation.format());
    }

    @Test
    public void testDefaultProperty() throws NoSuchFieldException {
        Field defaultField = TestClass.class.getDeclaredField("defaultProperty");
        ModifiableVariableProperty annotation =
                defaultField.getAnnotation(ModifiableVariableProperty.class);

        assertNotNull(annotation);
        assertEquals(Type.NONE, annotation.type());
        assertEquals(Format.NONE, annotation.format());
    }
}
