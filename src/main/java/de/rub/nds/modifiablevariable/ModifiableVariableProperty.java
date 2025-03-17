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
 * type (what kind of data it represents) and format (how the data is encoded). This information can
 * be used for reflection-based analysis, serialization, or other operations that need to understand
 * the purpose of different variables.
 *
 * <p>//TODO This class has not been touched or used much for a while and needs refactoring.
 *
 * <p>The annotation is retained at runtime and can only be applied to fields.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ModifiableVariableProperty {

    /**
     * Semantic types that can be assigned to modifiable variables. These values describe the
     * purpose or role of the variable.
     */
    enum Type {
        /** Variable representing a length field */
        LENGTH,
        /** Variable representing a count field */
        COUNT,
        /** Variable representing padding */
        PADDING,
        /** Variable representing one or more (array of) TLS constants */
        TLS_CONSTANT,
        /** Variable representing a cryptographic signature */
        SIGNATURE,
        /** Variable representing encrypted data */
        CIPHERTEXT,
        /** Variable representing a message authentication code */
        HMAC,
        /** Variable representing a public key */
        PUBLIC_KEY,
        /** Variable representing a private key */
        PRIVATE_KEY,
        /** Variable representing key material */
        KEY_MATERIAL,
        /** Variable representing a certificate */
        CERTIFICATE,
        /** Variable representing a plain protocol message, always in a decrypted state */
        PLAIN_PROTOCOL_MESSAGE,
        /** Variable representing a plain record */
        PLAIN_RECORD,
        /** Variable representing a cookie */
        COOKIE,
        /** Default type when no specific type is applicable */
        NONE,
        /** Variable that switches behavior */
        BEHAVIOR_SWITCH
    }

    /**
     * Encoding formats that can be used for modifiable variables. These values describe how the
     * data is encoded.
     */
    enum Format {
        /** ASN.1 encoding format */
        ASN1,
        /** PKCS#1 encoding format */
        PKCS1,
        /** Default format when no specific format is applicable */
        NONE
    }

    /**
     * Specifies the semantic type of the annotated variable.
     *
     * @return The type of the variable
     */
    Type type() default Type.NONE;

    /**
     * Specifies the encoding format of the annotated variable.
     *
     * @return The format of the variable
     */
    Format format() default Format.NONE;
}
