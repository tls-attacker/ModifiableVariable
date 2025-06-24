/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.example;

import de.rub.nds.modifiablevariable.ModifiableVariableHolder;
import de.rub.nds.modifiablevariable.ModifiableVariableProperty;
import de.rub.nds.modifiablevariable.ModifiableVariableProperty.Encoding;
import de.rub.nds.modifiablevariable.ModifiableVariableProperty.Purpose;
import de.rub.nds.modifiablevariable.bytearray.ModifiableByteArray;
import de.rub.nds.modifiablevariable.string.ModifiableString;
import de.rub.nds.modifiablevariable.validation.ValidationResult;

/** Example demonstrating the property validation feature for ModifiableVariables. */
public class ValidationExample {

    public static void main(String[] args) {
        // Create a protocol message
        ProtocolMessage message = new ProtocolMessage();
        message.initialize();

        // Validate the entire message
        ValidationResult result = message.validatePropertyAnnotations();

        if (result.isValid()) {
            System.out.println("Message validation passed!");
        } else {
            System.out.println("Message validation failed:");
            System.out.println(result.getFormattedErrors());
        }

        // Example of individual field validation
        ModifiableByteArray sessionKey = new ModifiableByteArray();
        sessionKey.setOriginalValue(
                new byte[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16});

        ModifiableVariableProperty keyProperty =
                new ModifiableVariableProperty() {
                    @Override
                    public Purpose purpose() {
                        return Purpose.KEY_MATERIAL;
                    }

                    @Override
                    public Encoding encoding() {
                        return Encoding.BINARY;
                    }

                    @Override
                    public int minLength() {
                        return 16;
                    }

                    @Override
                    public int maxLength() {
                        return 32;
                    }

                    @Override
                    public Class<? extends java.lang.annotation.Annotation> annotationType() {
                        return ModifiableVariableProperty.class;
                    }
                };

        ValidationResult keyResult = sessionKey.validateProperty(keyProperty, "sessionKey");
        System.out.println(
                "\nSession key validation: " + (keyResult.isValid() ? "PASSED" : "FAILED"));
    }

    /** Example protocol message with validation constraints */
    static class ProtocolMessage extends ModifiableVariableHolder {

        @ModifiableVariableProperty(purpose = Purpose.CONSTANT, minLength = 2, maxLength = 2)
        private ModifiableByteArray protocolVersion;

        @ModifiableVariableProperty(
                purpose = Purpose.LENGTH,
                encoding = Encoding.UNSIGNED_BIG_ENDIAN,
                minLength = 2,
                maxLength = 2)
        private ModifiableByteArray messageLength;

        @ModifiableVariableProperty(purpose = Purpose.RANDOM, minLength = 32, maxLength = 32)
        private ModifiableByteArray nonce;

        @ModifiableVariableProperty(
                purpose = Purpose.IDENTIFIER,
                encoding = Encoding.UTF8,
                minLength = 1,
                maxLength = 255)
        private ModifiableString clientId;

        @ModifiableVariableProperty(
                purpose = Purpose.TIMESTAMP,
                encoding = Encoding.UNSIGNED_BIG_ENDIAN,
                minLength = 8,
                maxLength = 8)
        private ModifiableByteArray timestamp;

        public void initialize() {
            protocolVersion = new ModifiableByteArray();
            protocolVersion.setOriginalValue(new byte[] {0x03, 0x03}); // TLS 1.2

            messageLength = new ModifiableByteArray();
            messageLength.setOriginalValue(new byte[] {0x00, 0x64}); // 100 bytes

            nonce = new ModifiableByteArray();
            byte[] randomNonce = new byte[32];
            for (int i = 0; i < 32; i++) {
                randomNonce[i] = (byte) (i * 7);
            }
            nonce.setOriginalValue(randomNonce);

            clientId = new ModifiableString();
            clientId.setOriginalValue("client-12345");

            timestamp = new ModifiableByteArray();
            long currentTime = System.currentTimeMillis();
            timestamp.setOriginalValue(longToBytes(currentTime));
        }

        private byte[] longToBytes(long value) {
            byte[] result = new byte[8];
            for (int i = 7; i >= 0; i--) {
                result[i] = (byte) (value & 0xFF);
                value >>= 8;
            }
            return result;
        }
    }
}
