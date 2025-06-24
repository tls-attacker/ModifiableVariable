/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.validation;

import static org.junit.jupiter.api.Assertions.*;

import de.rub.nds.modifiablevariable.ModifiableVariableProperty;
import de.rub.nds.modifiablevariable.ModifiableVariableProperty.Encoding;
import de.rub.nds.modifiablevariable.ModifiableVariableProperty.Purpose;
import de.rub.nds.modifiablevariable.bytearray.ModifiableByteArray;
import de.rub.nds.modifiablevariable.integer.ModifiableInteger;
import de.rub.nds.modifiablevariable.string.ModifiableString;
import java.lang.annotation.Annotation;
import org.junit.jupiter.api.Test;

public class ModifiableVariableValidatorTest {

    // Helper method to create ModifiableVariableProperty annotations
    private static ModifiableVariableProperty createProperty(
            Purpose purpose, Encoding encoding, int minLength, int maxLength) {
        return new ModifiableVariableProperty() {
            @Override
            public Purpose purpose() {
                return purpose;
            }

            @Override
            public Encoding encoding() {
                return encoding;
            }

            @Override
            public int minLength() {
                return minLength;
            }

            @Override
            public int maxLength() {
                return maxLength;
            }

            @Override
            public Class<? extends Annotation> annotationType() {
                return ModifiableVariableProperty.class;
            }
        };
    }

    @Test
    public void testValidateByteArrayWithinBounds() {
        ModifiableByteArray byteArray = new ModifiableByteArray();
        byteArray.setOriginalValue(new byte[] {1, 2, 3, 4});

        ModifiableVariableProperty property =
                createProperty(Purpose.RANDOM, Encoding.BINARY, 2, 10);

        ValidationResult result = ModifiableVariableValidator.validateVariable(byteArray, property);
        assertTrue(result.isValid());
        assertTrue(result.getErrors().isEmpty());
    }

    @Test
    public void testValidateByteArrayBelowMinLength() {
        ModifiableByteArray byteArray = new ModifiableByteArray();
        byteArray.setOriginalValue(new byte[] {1});

        ModifiableVariableProperty property =
                createProperty(Purpose.RANDOM, Encoding.BINARY, 2, 10);

        ValidationResult result = ModifiableVariableValidator.validateVariable(byteArray, property);
        assertFalse(result.isValid());
        assertEquals(1, result.getErrors().size());
        assertTrue(result.getErrors().get(0).contains("less than minimum required length"));
    }

    @Test
    public void testValidateByteArrayAboveMaxLength() {
        ModifiableByteArray byteArray = new ModifiableByteArray();
        byteArray.setOriginalValue(new byte[] {1, 2, 3, 4, 5, 6});

        ModifiableVariableProperty property = createProperty(Purpose.RANDOM, Encoding.BINARY, 2, 5);

        ValidationResult result = ModifiableVariableValidator.validateVariable(byteArray, property);
        assertFalse(result.isValid());
        assertEquals(1, result.getErrors().size());
        assertTrue(result.getErrors().get(0).contains("exceeds maximum allowed length"));
    }

    @Test
    public void testValidateByteArrayWithBothViolations() {
        ModifiableByteArray byteArray = new ModifiableByteArray();
        byteArray.setOriginalValue(new byte[] {1});

        // Create property where value violates both min and max (min > max for testing)
        ModifiableVariableProperty property = createProperty(Purpose.RANDOM, Encoding.BINARY, 5, 0);

        ValidationResult result = ModifiableVariableValidator.validateVariable(byteArray, property);
        assertFalse(result.isValid());
        assertEquals(2, result.getErrors().size());
    }

    @Test
    public void testValidateByteArrayNoConstraints() {
        ModifiableByteArray byteArray = new ModifiableByteArray();
        byteArray.setOriginalValue(new byte[] {1, 2, 3});

        ModifiableVariableProperty property =
                createProperty(Purpose.RANDOM, Encoding.BINARY, -1, -1);

        ValidationResult result = ModifiableVariableValidator.validateVariable(byteArray, property);
        assertTrue(result.isValid());
    }

    @Test
    public void testValidateByteArrayNullValue() {
        ModifiableByteArray byteArray = new ModifiableByteArray();
        // No original value set

        ModifiableVariableProperty property =
                createProperty(Purpose.RANDOM, Encoding.BINARY, 2, 10);

        ValidationResult result = ModifiableVariableValidator.validateVariable(byteArray, property);
        assertTrue(result.isValid()); // Null values are considered valid
    }

    @Test
    public void testValidateStringWithinBounds() {
        ModifiableString modString = new ModifiableString();
        modString.setOriginalValue("test");

        ModifiableVariableProperty property =
                createProperty(Purpose.PLAINTEXT, Encoding.UTF8, 2, 10);

        ValidationResult result = ModifiableVariableValidator.validateVariable(modString, property);
        assertTrue(result.isValid());
    }

    @Test
    public void testValidateStringBelowMinLength() {
        ModifiableString modString = new ModifiableString();
        modString.setOriginalValue("a");

        ModifiableVariableProperty property =
                createProperty(Purpose.PLAINTEXT, Encoding.UTF8, 2, 10);

        ValidationResult result = ModifiableVariableValidator.validateVariable(modString, property);
        assertFalse(result.isValid());
        assertTrue(result.getErrors().get(0).contains("less than minimum required length"));
    }

    @Test
    public void testValidateStringAboveMaxLength() {
        ModifiableString modString = new ModifiableString();
        modString.setOriginalValue("This is a very long string");

        ModifiableVariableProperty property =
                createProperty(Purpose.PLAINTEXT, Encoding.UTF8, 2, 10);

        ValidationResult result = ModifiableVariableValidator.validateVariable(modString, property);
        assertFalse(result.isValid());
        assertTrue(result.getErrors().get(0).contains("exceeds maximum allowed length"));
    }

    @Test
    public void testValidateStringUtf8ByteLength() {
        ModifiableString modString = new ModifiableString();
        // Unicode character that takes 3 bytes in UTF-8
        modString.setOriginalValue("€"); // Euro sign is 3 bytes in UTF-8

        ModifiableVariableProperty property =
                createProperty(Purpose.PLAINTEXT, Encoding.UTF8, 1, 2);

        ValidationResult result = ModifiableVariableValidator.validateVariable(modString, property);
        assertFalse(result.isValid()); // 3 bytes exceeds max of 2
    }

    @Test
    public void testValidateWithFieldName() {
        ModifiableByteArray byteArray = new ModifiableByteArray();
        byteArray.setOriginalValue(new byte[] {1});

        ModifiableVariableProperty property =
                createProperty(Purpose.RANDOM, Encoding.BINARY, 2, 10);

        ValidationResult result =
                ModifiableVariableValidator.validateVariable(byteArray, property, "testField");

        assertFalse(result.isValid());
        assertEquals("testField", result.getFieldName());
    }

    @Test
    public void testValidateNonConstrainedType() {
        // Test with a type that doesn't have length constraints (e.g., ModifiableInteger)
        ModifiableInteger modInt = new ModifiableInteger();
        modInt.setOriginalValue(42);

        ModifiableVariableProperty property =
                createProperty(Purpose.COUNT, Encoding.UNSIGNED_BIG_ENDIAN, 1, 100);

        ValidationResult result = ModifiableVariableValidator.validateVariable(modInt, property);
        assertTrue(result.isValid()); // No length validation for integers
    }

    @Test
    public void testValidateNullVariable() {
        ValidationResult result =
                ModifiableVariableValidator.validateVariable(
                        null, createProperty(Purpose.RANDOM, Encoding.BINARY, 1, 10));
        assertTrue(result.isValid());
    }

    @Test
    public void testValidateNullProperty() {
        ModifiableByteArray byteArray = new ModifiableByteArray();
        byteArray.setOriginalValue(new byte[] {1, 2, 3});

        ValidationResult result = ModifiableVariableValidator.validateVariable(byteArray, null);
        assertTrue(result.isValid());
    }

    @Test
    public void testValidatorContextFluentApi() {
        ModifiableByteArray byteArray = new ModifiableByteArray();
        byteArray.setOriginalValue(new byte[] {1, 2, 3});

        ModifiableVariableProperty property =
                createProperty(Purpose.RANDOM, Encoding.BINARY, 2, 10);

        ValidationResult result =
                ModifiableVariableValidator.validate(byteArray)
                        .withFieldName("testField")
                        .againstProperty(property)
                        .mustSatisfy(byteArray.getValue()[0] == 1, "First byte must be 1")
                        .mustSatisfy(byteArray.getValue().length % 2 == 1, "Length must be odd")
                        .getResult();

        assertTrue(result.isValid());
        assertEquals("testField", result.getFieldName());
    }

    @Test
    public void testValidatorContextWithFailures() {
        ModifiableByteArray byteArray = new ModifiableByteArray();
        byteArray.setOriginalValue(new byte[] {1, 2});

        ModifiableVariableProperty property =
                createProperty(Purpose.RANDOM, Encoding.BINARY, 3, 10);

        ValidationResult result =
                ModifiableVariableValidator.validate(byteArray)
                        .withFieldName("testField")
                        .againstProperty(property)
                        .mustSatisfy(false, "Custom validation failed")
                        .getResult();

        assertFalse(result.isValid());
        assertEquals(2, result.getErrors().size()); // Length violation + custom failure
    }

    @Test
    public void testValidateObjectWithAnnotatedFields() {
        TestClassWithAnnotations testObject = new TestClassWithAnnotations();

        ValidationResult result = ModifiableVariableValidator.validateObject(testObject);

        // The validField should pass, invalidField should fail
        assertFalse(result.isValid());
        assertFalse(result.getErrors().isEmpty());
    }

    @Test
    public void testValidateObjectAllValid() {
        TestClassAllValid testObject = new TestClassAllValid();

        ValidationResult result = ModifiableVariableValidator.validateObject(testObject);

        assertTrue(result.isValid());
        assertTrue(result.getErrors().isEmpty());
    }

    @Test
    public void testValidateObjectNull() {
        ValidationResult result = ModifiableVariableValidator.validateObject(null);
        assertTrue(result.isValid());
    }

    // Test helper classes
    private static class TestClassWithAnnotations {
        @ModifiableVariableProperty(minLength = 2, maxLength = 10)
        private ModifiableByteArray validField;

        @ModifiableVariableProperty(minLength = 5, maxLength = 10)
        private ModifiableByteArray invalidField;

        public TestClassWithAnnotations() {
            validField = new ModifiableByteArray();
            validField.setOriginalValue(new byte[] {1, 2, 3});

            invalidField = new ModifiableByteArray();
            invalidField.setOriginalValue(new byte[] {1}); // Too short
        }
    }

    private static class TestClassAllValid {
        @ModifiableVariableProperty(minLength = 1, maxLength = 10)
        private ModifiableByteArray field1;

        @ModifiableVariableProperty(minLength = 2, maxLength = 20)
        private ModifiableString field2;

        public TestClassAllValid() {
            field1 = new ModifiableByteArray();
            field1.setOriginalValue(new byte[] {1, 2, 3});

            field2 = new ModifiableString();
            field2.setOriginalValue("test");
        }
    }
}
