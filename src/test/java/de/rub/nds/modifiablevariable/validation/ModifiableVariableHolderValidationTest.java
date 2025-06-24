/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.validation;

import static org.junit.jupiter.api.Assertions.*;

import de.rub.nds.modifiablevariable.HoldsModifiableVariable;
import de.rub.nds.modifiablevariable.ModifiableVariableHolder;
import de.rub.nds.modifiablevariable.ModifiableVariableProperty;
import de.rub.nds.modifiablevariable.ModifiableVariableProperty.Encoding;
import de.rub.nds.modifiablevariable.ModifiableVariableProperty.Purpose;
import de.rub.nds.modifiablevariable.bytearray.ModifiableByteArray;
import de.rub.nds.modifiablevariable.integer.ModifiableInteger;
import de.rub.nds.modifiablevariable.string.ModifiableString;
import org.junit.jupiter.api.Test;

/** Tests for validation functionality in ModifiableVariableHolder. */
public class ModifiableVariableHolderValidationTest {

    @Test
    public void testValidatePropertyAnnotations() {
        TestMessage message = new TestMessage();
        message.initializeValid();

        ValidationResult result = message.validatePropertyAnnotations();
        assertTrue(result.isValid());
        assertTrue(result.getErrors().isEmpty());
    }

    @Test
    public void testValidatePropertyAnnotationsWithErrors() {
        TestMessage message = new TestMessage();
        message.initializeInvalid();

        ValidationResult result = message.validatePropertyAnnotations();
        assertFalse(result.isValid());
        assertFalse(result.getErrors().isEmpty());
    }

    @Test
    public void testValidateAssertions() {
        TestMessage message = new TestMessage();
        message.initializeValid();

        // Set assertions that should pass
        message.messageType.setAssertEquals(new byte[] {0x01});
        message.payload.setAssertEquals("Hello, World!".getBytes());

        assertTrue(message.validateAssertions());
    }

    @Test
    public void testValidateAssertionsFailed() {
        TestMessage message = new TestMessage();
        message.initializeValid();

        // Set assertion that should fail
        message.messageType.setAssertEquals(new byte[] {0x02}); // Actual is 0x01

        assertFalse(message.validateAssertions());
    }

    @Test
    public void testValidateNestedHolders() {
        NestedMessage parent = new NestedMessage();
        parent.initializeValid();

        // Test property validation
        ValidationResult propResult = parent.validatePropertyAnnotations();
        assertTrue(propResult.isValid());

        // Test assertion validation
        parent.header.headerType.setAssertEquals(new byte[] {(byte) 0xAA});
        parent.content.contentData.setAssertEquals("Content".getBytes());

        assertTrue(parent.validateAssertions());

        // Make nested assertion fail
        parent.content.contentData.setAssertEquals("Wrong".getBytes());
        assertFalse(parent.validateAssertions());
    }

    @Test
    public void testValidateWithNoAnnotations() {
        NoAnnotationsMessage message = new NoAnnotationsMessage();
        message.initialize();

        // Should pass since there are no property annotations to validate
        ValidationResult result = message.validatePropertyAnnotations();
        assertTrue(result.isValid());

        // Assertion validation should still work
        message.data.setAssertEquals("test".getBytes());
        assertTrue(message.validateAssertions());
    }

    @Test
    public void testValidateMixedFields() {
        MixedFieldsMessage message = new MixedFieldsMessage();
        message.initialize();

        ValidationResult result = message.validatePropertyAnnotations();
        // Should validate only annotated fields
        assertFalse(result.isValid()); // shortData is too short
        assertEquals(1, result.getErrors().size());
    }

    // Test classes
    private static class TestMessage extends ModifiableVariableHolder {
        @ModifiableVariableProperty(
                purpose = Purpose.LENGTH,
                encoding = Encoding.UNSIGNED_BIG_ENDIAN,
                minLength = 2,
                maxLength = 2)
        private ModifiableByteArray messageLength;

        @ModifiableVariableProperty(purpose = Purpose.CONSTANT, minLength = 1, maxLength = 1)
        private ModifiableByteArray messageType;

        @ModifiableVariableProperty(
                purpose = Purpose.PLAINTEXT,
                encoding = Encoding.UTF8,
                maxLength = 100)
        private ModifiableByteArray payload;

        void initializeValid() {
            messageLength = new ModifiableByteArray();
            messageLength.setOriginalValue(new byte[] {0x00, 0x0D}); // Length = 13

            messageType = new ModifiableByteArray();
            messageType.setOriginalValue(new byte[] {0x01});

            payload = new ModifiableByteArray();
            payload.setOriginalValue("Hello, World!".getBytes());
        }

        void initializeInvalid() {
            messageLength = new ModifiableByteArray();
            messageLength.setOriginalValue(new byte[] {0x00}); // Too short

            messageType = new ModifiableByteArray();
            messageType.setOriginalValue(new byte[] {0x01, 0x02}); // Too long

            payload = new ModifiableByteArray();
            payload.setOriginalValue(new byte[101]); // Too long
        }
    }

    private static class NestedMessage extends ModifiableVariableHolder {
        @HoldsModifiableVariable private MessageHeader header;

        @HoldsModifiableVariable private MessageContent content;

        void initializeValid() {
            header = new MessageHeader();
            header.initialize();

            content = new MessageContent();
            content.initialize();
        }
    }

    private static class MessageHeader extends ModifiableVariableHolder {
        @ModifiableVariableProperty(minLength = 1, maxLength = 1)
        private ModifiableByteArray headerType;

        @ModifiableVariableProperty(minLength = 4, maxLength = 4)
        private ModifiableByteArray headerVersion;

        void initialize() {
            headerType = new ModifiableByteArray();
            headerType.setOriginalValue(new byte[] {(byte) 0xAA});

            headerVersion = new ModifiableByteArray();
            headerVersion.setOriginalValue(new byte[] {0x01, 0x00, 0x00, 0x00});
        }
    }

    private static class MessageContent extends ModifiableVariableHolder {
        @ModifiableVariableProperty(purpose = Purpose.PLAINTEXT, minLength = 1, maxLength = 1000)
        private ModifiableByteArray contentData;

        void initialize() {
            contentData = new ModifiableByteArray();
            contentData.setOriginalValue("Content".getBytes());
        }
    }

    private static class NoAnnotationsMessage extends ModifiableVariableHolder {
        private ModifiableByteArray data;
        private ModifiableInteger count;

        void initialize() {
            data = new ModifiableByteArray();
            data.setOriginalValue("test".getBytes());

            count = new ModifiableInteger();
            count.setOriginalValue(42);
        }
    }

    private static class MixedFieldsMessage extends ModifiableVariableHolder {
        // Annotated field
        @ModifiableVariableProperty(minLength = 4, maxLength = 8)
        private ModifiableByteArray shortData;

        // Non-annotated field
        private ModifiableByteArray longData;

        // Annotated field
        @ModifiableVariableProperty(
                purpose = Purpose.IDENTIFIER,
                encoding = Encoding.UTF8,
                minLength = 3,
                maxLength = 20)
        private ModifiableString identifier;

        void initialize() {
            shortData = new ModifiableByteArray();
            shortData.setOriginalValue(new byte[] {1, 2}); // Too short

            longData = new ModifiableByteArray();
            longData.setOriginalValue(new byte[100]); // No constraints

            identifier = new ModifiableString();
            identifier.setOriginalValue("ID123"); // Valid
        }
    }
}
