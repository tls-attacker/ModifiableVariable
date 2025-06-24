/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.validation;

import de.rub.nds.modifiablevariable.ModifiableVariable;
import de.rub.nds.modifiablevariable.ModifiableVariableProperty;
import de.rub.nds.modifiablevariable.bytearray.ModifiableByteArray;
import de.rub.nds.modifiablevariable.string.ModifiableString;
import de.rub.nds.modifiablevariable.util.ModifiableVariableAnalyzer;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Validator for ModifiableVariable instances that enforces constraints defined by
 * ModifiableVariableProperty annotations.
 *
 * <p>This class provides static methods to validate individual ModifiableVariable instances against
 * their property constraints, as well as validate all annotated fields within an object.
 */
public class ModifiableVariableValidator {

    /** Private constructor to prevent instantiation */
    private ModifiableVariableValidator() {}

    /**
     * Validates a ModifiableVariable against the constraints defined in its
     * ModifiableVariableProperty annotation.
     *
     * @param variable The ModifiableVariable to validate
     * @param property The property annotation containing constraints
     * @return A ValidationResult indicating success or failure
     */
    public static ValidationResult validateVariable(
            ModifiableVariable<?> variable, ModifiableVariableProperty property) {
        if (variable == null || property == null) {
            return ValidationResult.success();
        }

        List<String> errors = new ArrayList<>();

        // Validate length constraints for applicable types
        if (variable instanceof ModifiableByteArray) {
            validateByteArrayLength((ModifiableByteArray) variable, property, errors);
        } else if (variable instanceof ModifiableString) {
            validateStringLength((ModifiableString) variable, property, errors);
        }

        return errors.isEmpty() ? ValidationResult.success() : ValidationResult.failure(errors);
    }

    /**
     * Validates a ModifiableVariable with a field context.
     *
     * @param variable The ModifiableVariable to validate
     * @param property The property annotation containing constraints
     * @param fieldName The name of the field being validated
     * @return A ValidationResult indicating success or failure
     */
    public static ValidationResult validateVariable(
            ModifiableVariable<?> variable, ModifiableVariableProperty property, String fieldName) {
        if (variable == null || property == null) {
            return ValidationResult.success(fieldName);
        }

        List<String> errors = new ArrayList<>();

        // Validate length constraints for applicable types
        if (variable instanceof ModifiableByteArray) {
            validateByteArrayLength((ModifiableByteArray) variable, property, errors);
        } else if (variable instanceof ModifiableString) {
            validateStringLength((ModifiableString) variable, property, errors);
        }

        return errors.isEmpty()
                ? ValidationResult.success(fieldName)
                : ValidationResult.failure(fieldName, errors);
    }

    /**
     * Validates all ModifiableVariable fields in an object that have ModifiableVariableProperty
     * annotations.
     *
     * @param object The object containing ModifiableVariable fields to validate
     * @return A combined ValidationResult for all annotated fields
     */
    public static ValidationResult validateObject(Object object) {
        if (object == null) {
            return ValidationResult.success();
        }

        List<ValidationResult> results = new ArrayList<>();
        List<Field> annotatedFields =
                ModifiableVariableAnalyzer.getAnnotatedFields(object.getClass());

        for (Field field : annotatedFields) {
            try {
                field.setAccessible(true);
                Object value = field.get(object);

                if (value instanceof ModifiableVariable<?>) {
                    ModifiableVariableProperty property =
                            field.getAnnotation(ModifiableVariableProperty.class);
                    ValidationResult result =
                            validateVariable(
                                    (ModifiableVariable<?>) value, property, field.getName());
                    results.add(result);
                }
            } catch (IllegalAccessException e) {
                results.add(
                        ValidationResult.failure(
                                field.getName(), "Failed to access field: " + e.getMessage()));
            }
        }

        return ValidationResult.combine(results.toArray(new ValidationResult[0]));
    }

    /**
     * Validates length constraints for a ModifiableByteArray.
     *
     * @param byteArray The byte array to validate
     * @param property The property annotation containing constraints
     * @param errors List to accumulate error messages
     */
    private static void validateByteArrayLength(
            ModifiableByteArray byteArray,
            ModifiableVariableProperty property,
            List<String> errors) {
        byte[] value = byteArray.getValue();
        if (value == null) {
            return; // Null values are considered valid unless explicitly constrained
        }

        int length = value.length;

        if (property.minLength() != -1 && length < property.minLength()) {
            errors.add(
                    String.format(
                            "Byte array length %d is less than minimum required length %d",
                            length, property.minLength()));
        }

        if (property.maxLength() != -1 && length > property.maxLength()) {
            errors.add(
                    String.format(
                            "Byte array length %d exceeds maximum allowed length %d",
                            length, property.maxLength()));
        }
    }

    /**
     * Validates length constraints for a ModifiableString.
     *
     * @param modString The string to validate
     * @param property The property annotation containing constraints
     * @param errors List to accumulate error messages
     */
    private static void validateStringLength(
            ModifiableString modString, ModifiableVariableProperty property, List<String> errors) {
        String value = modString.getValue();
        if (value == null) {
            return; // Null values are considered valid unless explicitly constrained
        }

        // For strings, we validate based on UTF-8 byte length by default
        int byteLength = value.getBytes(java.nio.charset.StandardCharsets.UTF_8).length;

        if (property.minLength() != -1 && byteLength < property.minLength()) {
            errors.add(
                    String.format(
                            "String byte length %d is less than minimum required length %d",
                            byteLength, property.minLength()));
        }

        if (property.maxLength() != -1 && byteLength > property.maxLength()) {
            errors.add(
                    String.format(
                            "String byte length %d exceeds maximum allowed length %d",
                            byteLength, property.maxLength()));
        }
    }

    /**
     * Creates a validator context for fluent validation with custom rules.
     *
     * @param variable The ModifiableVariable to validate
     * @return A new ValidatorContext for the variable
     */
    public static ValidatorContext validate(ModifiableVariable<?> variable) {
        return new ValidatorContext(variable);
    }

    /** Context class for building custom validation rules in a fluent manner. */
    public static class ValidatorContext {
        private final ModifiableVariable<?> variable;
        private final List<String> errors = new ArrayList<>();
        private String fieldName;

        private ValidatorContext(ModifiableVariable<?> variable) {
            this.variable = variable;
        }

        /**
         * Sets the field name for error messages.
         *
         * @param fieldName The field name
         * @return This context for chaining
         */
        public ValidatorContext withFieldName(String fieldName) {
            this.fieldName = fieldName;
            return this;
        }

        /**
         * Validates against a ModifiableVariableProperty annotation.
         *
         * @param property The property annotation
         * @return This context for chaining
         */
        public ValidatorContext againstProperty(ModifiableVariableProperty property) {
            ValidationResult result =
                    fieldName != null
                            ? validateVariable(variable, property, fieldName)
                            : validateVariable(variable, property);

            if (!result.isValid()) {
                errors.addAll(result.getErrors());
            }
            return this;
        }

        /**
         * Adds a custom validation rule.
         *
         * @param condition The condition that must be true for validation to pass
         * @param errorMessage The error message if the condition is false
         * @return This context for chaining
         */
        public ValidatorContext mustSatisfy(boolean condition, String errorMessage) {
            if (!condition) {
                errors.add(errorMessage);
            }
            return this;
        }

        /**
         * Completes the validation and returns the result.
         *
         * @return The validation result
         */
        public ValidationResult getResult() {
            return errors.isEmpty()
                    ? (fieldName != null
                            ? ValidationResult.success(fieldName)
                            : ValidationResult.success())
                    : (fieldName != null
                            ? ValidationResult.failure(fieldName, errors)
                            : ValidationResult.failure(errors));
        }
    }
}
