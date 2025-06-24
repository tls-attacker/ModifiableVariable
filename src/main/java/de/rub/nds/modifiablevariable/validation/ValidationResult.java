/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.validation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents the result of validating a ModifiableVariable against its property constraints.
 *
 * <p>This class encapsulates the validation outcome, including whether the validation passed and
 * any validation errors that occurred. It provides a fluent API for building validation results.
 */
public class ValidationResult implements Serializable {

    /** Indicates whether the validation passed */
    private final boolean valid;

    /** List of validation error messages */
    private final List<String> errors;

    /** The field name being validated, if available */
    private final String fieldName;

    /**
     * Private constructor for creating validation results.
     *
     * @param valid Whether the validation passed
     * @param fieldName The field name being validated
     * @param errors List of error messages
     */
    private ValidationResult(boolean valid, String fieldName, List<String> errors) {
        this.valid = valid;
        this.fieldName = fieldName;
        this.errors = Collections.unmodifiableList(new ArrayList<>(errors));
    }

    /**
     * Creates a successful validation result.
     *
     * @return A ValidationResult indicating success
     */
    public static ValidationResult success() {
        return new ValidationResult(true, null, Collections.emptyList());
    }

    /**
     * Creates a successful validation result for a specific field.
     *
     * @param fieldName The name of the field that passed validation
     * @return A ValidationResult indicating success
     */
    public static ValidationResult success(String fieldName) {
        return new ValidationResult(true, fieldName, Collections.emptyList());
    }

    /**
     * Creates a failed validation result with a single error message.
     *
     * @param error The error message
     * @return A ValidationResult indicating failure
     */
    public static ValidationResult failure(String error) {
        return new ValidationResult(false, null, Collections.singletonList(error));
    }

    /**
     * Creates a failed validation result for a specific field with a single error message.
     *
     * @param fieldName The name of the field that failed validation
     * @param error The error message
     * @return A ValidationResult indicating failure
     */
    public static ValidationResult failure(String fieldName, String error) {
        return new ValidationResult(false, fieldName, Collections.singletonList(error));
    }

    /**
     * Creates a failed validation result with multiple error messages.
     *
     * @param errors List of error messages
     * @return A ValidationResult indicating failure
     */
    public static ValidationResult failure(List<String> errors) {
        return new ValidationResult(false, null, errors);
    }

    /**
     * Creates a failed validation result for a specific field with multiple error messages.
     *
     * @param fieldName The name of the field that failed validation
     * @param errors List of error messages
     * @return A ValidationResult indicating failure
     */
    public static ValidationResult failure(String fieldName, List<String> errors) {
        return new ValidationResult(false, fieldName, errors);
    }

    /**
     * Combines multiple validation results into a single result.
     *
     * @param results The validation results to combine
     * @return A combined ValidationResult
     */
    public static ValidationResult combine(ValidationResult... results) {
        boolean allValid = true;
        List<String> allErrors = new ArrayList<>();

        for (ValidationResult result : results) {
            if (!result.isValid()) {
                allValid = false;
                allErrors.addAll(result.getErrors());
            }
        }

        return new ValidationResult(allValid, null, allErrors);
    }

    /**
     * Checks if the validation passed.
     *
     * @return true if validation passed, false otherwise
     */
    public boolean isValid() {
        return valid;
    }

    /**
     * Gets the list of validation error messages.
     *
     * @return An unmodifiable list of error messages
     */
    public List<String> getErrors() {
        return errors;
    }

    /**
     * Gets the field name associated with this validation result.
     *
     * @return The field name, or null if not specified
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * Gets a formatted error message combining all errors.
     *
     * @return A formatted string containing all error messages
     */
    public String getFormattedErrors() {
        if (errors.isEmpty()) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        if (fieldName != null) {
            sb.append("Validation failed for field '").append(fieldName).append("': ");
        } else {
            sb.append("Validation failed: ");
        }

        if (errors.size() == 1) {
            sb.append(errors.get(0));
        } else {
            sb.append("\n");
            for (int i = 0; i < errors.size(); i++) {
                sb.append("  ").append(i + 1).append(". ").append(errors.get(i));
                if (i < errors.size() - 1) {
                    sb.append("\n");
                }
            }
        }

        return sb.toString();
    }

    @Override
    public String toString() {
        if (valid) {
            return fieldName != null
                    ? "ValidationResult{valid=true, field='" + fieldName + "'}"
                    : "ValidationResult{valid=true}";
        } else {
            return fieldName != null
                    ? "ValidationResult{valid=false, field='"
                            + fieldName
                            + "', errors="
                            + errors
                            + "}"
                    : "ValidationResult{valid=false, errors=" + errors + "}";
        }
    }
}
