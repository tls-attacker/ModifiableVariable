/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation interface for marking and categorizing modifiable variables within a class.
 *
 * <p>This annotation provides metadata about a modifiable variable field, including its semantic
 * type (what kind of data it represents) and format (how the data is encoded). This information is
 * used for reflection-based analysis, serialization, and testing scenarios.
 *
 * <p>The annotation is retained at runtime and can only be applied to fields.
 *
 * <p>Usage examples:
 *
 * <pre>{@code
 * // Variable length field
 * @ModifiableVariableProperty(purpose = Purpose.LENGTH, minLength = 1, maxLength = 4)
 * private ModifiableInteger messageLength;
 *
 * // Signature with specific encoding and length
 * @ModifiableVariableProperty(
 *     purpose = Purpose.SIGNATURE,
 *     encoding = Encoding.ASN1_DER,
 *     minLength = 70,
 *     maxLength = 73)
 * private ModifiableByteArray digitalSignature;
 *
 * // Random value
 * @ModifiableVariableProperty(purpose = Purpose.RANDOM)
 * private ModifiableByteArray nonce;
 *
 * // Variable-length data with description
 * @ModifiableVariableProperty(
 *     purpose = Purpose.PLAINTEXT,
 *     encoding = Encoding.UTF8,
 *     maxLength = 1024)
 * private ModifiableByteArray applicationData;
 * }</pre>
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ModifiableVariableProperty {

    /**
     * Semantic purpose categories for modifiable variables. These describe the role or function of
     * the variable within any protocol or data structure.
     */
    enum Purpose {
        /** Variable representing a length field */
        LENGTH,
        /** Variable representing a count or quantity field */
        COUNT,
        /** Variable representing padding or filler bytes */
        PADDING,
        /** Variable representing a protocol constant or enumerated value */
        CONSTANT,
        /** Variable representing a cryptographic signature */
        SIGNATURE,
        /** Variable representing encrypted or ciphered data */
        CIPHERTEXT,
        /** Variable representing a message authentication code or hash */
        MAC,
        /** Variable representing cryptographic key material */
        KEY_MATERIAL,
        /** Variable representing a certificate */
        CERTIFICATE,
        /** Variable representing plaintext protocol data */
        PLAINTEXT,
        /** Variable representing a random value, nonce, or salt */
        RANDOM,
        /** Variable representing a session or connection identifier */
        IDENTIFIER,
        /** Variable representing a timestamp or temporal value */
        TIMESTAMP,
        /** Default purpose when no specific category applies */
        UNSPECIFIED
    }

    /**
     * Encoding formats for modifiable variables. These describe how the data is encoded,
     * structured, or represented.
     */
    enum Encoding {
        /** ASN.1 Distinguished Encoding Rules format */
        ASN1_DER,
        /** ASN.1 Basic Encoding Rules format */
        ASN1_BER,
        /** PKCS#1 format for RSA key encoding */
        PKCS1,
        /** PKCS#8 format for private key information */
        PKCS8,
        /** X.509 format for certificates */
        X509,
        /** PEM (Privacy-Enhanced Mail) text format */
        PEM,
        /** Base64 text encoding */
        BASE64,
        /** Hexadecimal string representation */
        HEX_STRING,
        /** Raw binary data */
        BINARY,
        /** UTF-8 text encoding */
        UTF8,
        /** Unsigned big-endian encoding */
        UNSIGNED_BIG_ENDIAN,
        /** Unsigned little-endian encoding */
        UNSIGNED_LITTLE_ENDIAN,
        /** Signed big-endian encoding */
        SIGNED_BIG_ENDIAN,
        /** Signed little-endian encoding */
        SIGNED_LITTLE_ENDIAN,

        /** JSON text format */
        JSON,
        /** XML text format */
        XML,
        /** Default encoding when not specified */
        UNSPECIFIED
    }

    /**
     * Specifies the semantic purpose of the annotated variable.
     *
     * @return The purpose category of the variable
     */
    Purpose purpose() default Purpose.UNSPECIFIED;

    /**
     * Specifies the encoding format of the annotated variable.
     *
     * @return The encoding format of the variable
     */
    Encoding encoding() default Encoding.UNSPECIFIED;

    /**
     * Specifies the minimum length (in bytes) of the variable's value. Use -1 to indicate no
     * minimum constraint.
     *
     * @return The minimum length in bytes, or -1 if unconstrained
     */
    int minLength() default -1;

    /**
     * Specifies the maximum length (in bytes) of the variable's value. Use -1 to indicate no
     * maximum constraint.
     *
     * @return The maximum length in bytes, or -1 if unconstrained
     */
    int maxLength() default -1;
}
