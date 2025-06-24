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
import de.rub.nds.modifiablevariable.bytearray.ByteArrayDeleteModification;
import de.rub.nds.modifiablevariable.bytearray.ByteArrayPrependValueModification;
import de.rub.nds.modifiablevariable.bytearray.ModifiableByteArray;
import de.rub.nds.modifiablevariable.string.ModifiableString;
import de.rub.nds.modifiablevariable.string.StringAppendValueModification;
import java.lang.annotation.Annotation;
import org.junit.jupiter.api.Test;

/**
 * Integration tests demonstrating the property validation functionality in real-world scenarios.
 */
public class PropertyValidationIntegrationTest {

    // Helper to create property annotations
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
    public void testValidatePropertyMethodOnModifiableVariable() {
        // Test the validateProperty method added to ModifiableVariable
        ModifiableByteArray nonce = new ModifiableByteArray();
        nonce.setOriginalValue(new byte[] {1, 2, 3, 4, 5, 6, 7, 8});

        // Property requiring exactly 8 bytes for a nonce
        ModifiableVariableProperty property = createProperty(Purpose.RANDOM, Encoding.BINARY, 8, 8);

        ValidationResult result = nonce.validateProperty(property);
        assertTrue(result.isValid());
    }

    @Test
    public void testValidatePropertyWithFieldName() {
        ModifiableString sessionId = new ModifiableString();
        sessionId.setOriginalValue("ABC123");

        // Session ID must be between 6 and 32 characters
        ModifiableVariableProperty property =
                createProperty(Purpose.IDENTIFIER, Encoding.UTF8, 6, 32);

        ValidationResult result = sessionId.validateProperty(property, "sessionId");
        assertTrue(result.isValid());
        assertEquals("sessionId", result.getFieldName());
    }

    @Test
    public void testValidationWithModifications() {
        // Create a byte array with valid initial length
        ModifiableByteArray signature = new ModifiableByteArray();
        signature.setOriginalValue(new byte[] {1, 2, 3, 4, 5, 6, 7, 8});

        // Signature must be between 6 and 10 bytes
        ModifiableVariableProperty property =
                createProperty(Purpose.SIGNATURE, Encoding.ASN1_DER, 6, 10);

        // Initially valid
        assertTrue(signature.validateProperty(property).isValid());

        // Add modification that makes it too short
        signature.setModifications(new ByteArrayDeleteModification(0, 5));
        ValidationResult result = signature.validateProperty(property);
        assertFalse(result.isValid());
        assertTrue(result.getErrors().get(0).contains("less than minimum"));

        // Add modification that makes it too long
        signature.clearModifications();
        signature.setModifications(new ByteArrayPrependValueModification(new byte[] {0, 0, 0, 0}));
        result = signature.validateProperty(property);
        assertFalse(result.isValid());
        assertTrue(result.getErrors().get(0).contains("exceeds maximum"));
    }

    @Test
    public void testRealWorldScenario_TlsRandom() {
        // TLS Random structure: 4 bytes timestamp + 28 bytes random
        ModifiableByteArray tlsRandom = new ModifiableByteArray();
        byte[] randomValue = new byte[32];
        randomValue[0] = (byte) 0x5A; // Timestamp bytes
        randomValue[1] = (byte) 0x6B;
        randomValue[2] = (byte) 0x7C;
        randomValue[3] = (byte) 0x8D;
        // Rest is random
        for (int i = 4; i < 32; i++) {
            randomValue[i] = (byte) i;
        }
        tlsRandom.setOriginalValue(randomValue);

        // TLS Random must be exactly 32 bytes
        ModifiableVariableProperty property =
                createProperty(Purpose.RANDOM, Encoding.BINARY, 32, 32);

        ValidationResult result = tlsRandom.validateProperty(property, "ClientRandom");
        assertTrue(result.isValid());
    }

    @Test
    public void testRealWorldScenario_Certificate() {
        // Simulate a certificate field
        ModifiableByteArray certificate = new ModifiableByteArray();
        // DER-encoded certificate (simplified)
        certificate.setOriginalValue(new byte[300]); // Typical cert size

        // Certificates typically range from 200 to 2048 bytes
        ModifiableVariableProperty property =
                createProperty(Purpose.CERTIFICATE, Encoding.X509, 200, 2048);

        ValidationResult result = certificate.validateProperty(property, "ServerCertificate");
        assertTrue(result.isValid());
    }

    @Test
    public void testRealWorldScenario_Padding() {
        // Simulate PKCS padding
        ModifiableByteArray padding = new ModifiableByteArray();
        padding.setOriginalValue(new byte[] {4, 4, 4, 4}); // PKCS padding with value 4

        // Padding can be 1 to 255 bytes
        ModifiableVariableProperty property =
                createProperty(Purpose.PADDING, Encoding.BINARY, 1, 255);

        ValidationResult result = padding.validateProperty(property);
        assertTrue(result.isValid());
    }

    @Test
    public void testComplexValidationScenario() {
        // Test with multiple validation criteria
        ModifiableString username = new ModifiableString();
        username.setOriginalValue("alice");

        // Username requirements: 3-20 characters
        ModifiableVariableProperty property =
                createProperty(Purpose.IDENTIFIER, Encoding.UTF8, 3, 20);

        // Custom validation with additional rules
        ValidationResult result =
                ModifiableVariableValidator.validate(username)
                        .withFieldName("username")
                        .againstProperty(property)
                        .mustSatisfy(
                                !username.getValue().contains(" "),
                                "Username cannot contain spaces")
                        .mustSatisfy(
                                username.getValue().matches("^[a-zA-Z0-9]+$"),
                                "Username must be alphanumeric")
                        .getResult();

        assertTrue(result.isValid());

        // Test with invalid username
        username.setModifications(new StringAppendValueModification("@#$"));
        result =
                ModifiableVariableValidator.validate(username)
                        .withFieldName("username")
                        .againstProperty(property)
                        .mustSatisfy(
                                !username.getValue().contains(" "),
                                "Username cannot contain spaces")
                        .mustSatisfy(
                                username.getValue().matches("^[a-zA-Z0-9]+$"),
                                "Username must be alphanumeric")
                        .getResult();

        assertFalse(result.isValid());
        assertTrue(result.getErrors().stream().anyMatch(e -> e.contains("must be alphanumeric")));
    }

    @Test
    public void testValidationInProtocolContext() {
        // Simulate a protocol message with multiple fields
        class ProtocolMessage {
            @ModifiableVariableProperty(
                    purpose = Purpose.LENGTH,
                    encoding = Encoding.UNSIGNED_BIG_ENDIAN,
                    minLength = 2,
                    maxLength = 2)
            public ModifiableByteArray messageLength;

            @ModifiableVariableProperty(
                    purpose = Purpose.CONSTANT,
                    encoding = Encoding.BINARY,
                    minLength = 1,
                    maxLength = 1)
            public ModifiableByteArray messageType;

            @ModifiableVariableProperty(
                    purpose = Purpose.PLAINTEXT,
                    encoding = Encoding.UTF8,
                    minLength = 0,
                    maxLength = 65535)
            public ModifiableByteArray payload;

            public ProtocolMessage() {
                messageLength = new ModifiableByteArray();
                messageLength.setOriginalValue(new byte[] {0x00, 0x10}); // Length = 16

                messageType = new ModifiableByteArray();
                messageType.setOriginalValue(new byte[] {0x01}); // Type = 1

                payload = new ModifiableByteArray();
                payload.setOriginalValue("Hello, World!".getBytes());
            }
        }

        ProtocolMessage message = new ProtocolMessage();
        ValidationResult result = ModifiableVariableValidator.validateObject(message);
        assertTrue(result.isValid());

        // Make message invalid by changing length field
        message.messageLength.setOriginalValue(new byte[] {0x00}); // Only 1 byte
        result = ModifiableVariableValidator.validateObject(message);
        assertFalse(result.isValid());
    }
}
