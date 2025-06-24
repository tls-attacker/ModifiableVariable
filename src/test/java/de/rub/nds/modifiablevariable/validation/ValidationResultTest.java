/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.validation;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

public class ValidationResultTest {

    @Test
    public void testSuccessCreation() {
        ValidationResult result = ValidationResult.success();
        assertTrue(result.isValid());
        assertTrue(result.getErrors().isEmpty());
        assertNull(result.getFieldName());
    }

    @Test
    public void testSuccessWithFieldName() {
        ValidationResult result = ValidationResult.success("testField");
        assertTrue(result.isValid());
        assertTrue(result.getErrors().isEmpty());
        assertEquals("testField", result.getFieldName());
    }

    @Test
    public void testFailureWithSingleError() {
        String error = "Validation failed";
        ValidationResult result = ValidationResult.failure(error);

        assertFalse(result.isValid());
        assertEquals(1, result.getErrors().size());
        assertEquals(error, result.getErrors().get(0));
        assertNull(result.getFieldName());
    }

    @Test
    public void testFailureWithFieldNameAndError() {
        String fieldName = "testField";
        String error = "Field validation failed";
        ValidationResult result = ValidationResult.failure(fieldName, error);

        assertFalse(result.isValid());
        assertEquals(1, result.getErrors().size());
        assertEquals(error, result.getErrors().get(0));
        assertEquals(fieldName, result.getFieldName());
    }

    @Test
    public void testFailureWithMultipleErrors() {
        List<String> errors = Arrays.asList("Error 1", "Error 2", "Error 3");
        ValidationResult result = ValidationResult.failure(errors);

        assertFalse(result.isValid());
        assertEquals(3, result.getErrors().size());
        assertEquals(errors, result.getErrors());
        assertNull(result.getFieldName());
    }

    @Test
    public void testFailureWithFieldNameAndMultipleErrors() {
        String fieldName = "testField";
        List<String> errors = Arrays.asList("Error 1", "Error 2");
        ValidationResult result = ValidationResult.failure(fieldName, errors);

        assertFalse(result.isValid());
        assertEquals(2, result.getErrors().size());
        assertEquals(errors, result.getErrors());
        assertEquals(fieldName, result.getFieldName());
    }

    @Test
    public void testCombineAllSuccessful() {
        ValidationResult result1 = ValidationResult.success();
        ValidationResult result2 = ValidationResult.success("field1");
        ValidationResult result3 = ValidationResult.success("field2");

        ValidationResult combined = ValidationResult.combine(result1, result2, result3);

        assertTrue(combined.isValid());
        assertTrue(combined.getErrors().isEmpty());
    }

    @Test
    public void testCombineMixedResults() {
        ValidationResult success1 = ValidationResult.success();
        ValidationResult failure1 = ValidationResult.failure("Error 1");
        ValidationResult success2 = ValidationResult.success("field1");
        ValidationResult failure2 =
                ValidationResult.failure("field2", Arrays.asList("Error 2", "Error 3"));

        ValidationResult combined =
                ValidationResult.combine(success1, failure1, success2, failure2);

        assertFalse(combined.isValid());
        assertEquals(3, combined.getErrors().size());
        assertTrue(combined.getErrors().contains("Error 1"));
        assertTrue(combined.getErrors().contains("Error 2"));
        assertTrue(combined.getErrors().contains("Error 3"));
    }

    @Test
    public void testCombineAllFailures() {
        ValidationResult failure1 = ValidationResult.failure("Error 1");
        ValidationResult failure2 = ValidationResult.failure("Error 2");
        ValidationResult failure3 = ValidationResult.failure("Error 3");

        ValidationResult combined = ValidationResult.combine(failure1, failure2, failure3);

        assertFalse(combined.isValid());
        assertEquals(3, combined.getErrors().size());
    }

    @Test
    public void testGetFormattedErrorsSingleError() {
        ValidationResult result = ValidationResult.failure("Single error message");
        String formatted = result.getFormattedErrors();

        assertEquals("Validation failed: Single error message", formatted);
    }

    @Test
    public void testGetFormattedErrorsSingleErrorWithField() {
        ValidationResult result = ValidationResult.failure("testField", "Field error message");
        String formatted = result.getFormattedErrors();

        assertEquals("Validation failed for field 'testField': Field error message", formatted);
    }

    @Test
    public void testGetFormattedErrorsMultipleErrors() {
        List<String> errors = Arrays.asList("Error 1", "Error 2", "Error 3");
        ValidationResult result = ValidationResult.failure(errors);
        String formatted = result.getFormattedErrors();

        assertTrue(formatted.startsWith("Validation failed: \n"));
        assertTrue(formatted.contains("1. Error 1"));
        assertTrue(formatted.contains("2. Error 2"));
        assertTrue(formatted.contains("3. Error 3"));
    }

    @Test
    public void testGetFormattedErrorsMultipleErrorsWithField() {
        List<String> errors = Arrays.asList("Error 1", "Error 2");
        ValidationResult result = ValidationResult.failure("testField", errors);
        String formatted = result.getFormattedErrors();

        assertTrue(formatted.startsWith("Validation failed for field 'testField': \n"));
        assertTrue(formatted.contains("1. Error 1"));
        assertTrue(formatted.contains("2. Error 2"));
    }

    @Test
    public void testGetFormattedErrorsNoErrors() {
        ValidationResult result = ValidationResult.success();
        assertEquals("", result.getFormattedErrors());
    }

    @Test
    public void testToStringSuccess() {
        ValidationResult result1 = ValidationResult.success();
        assertEquals("ValidationResult{valid=true}", result1.toString());

        ValidationResult result2 = ValidationResult.success("testField");
        assertEquals("ValidationResult{valid=true, field='testField'}", result2.toString());
    }

    @Test
    public void testToStringFailure() {
        ValidationResult result1 = ValidationResult.failure("Error");
        assertEquals("ValidationResult{valid=false, errors=[Error]}", result1.toString());

        ValidationResult result2 = ValidationResult.failure("testField", "Error");
        assertEquals(
                "ValidationResult{valid=false, field='testField', errors=[Error]}",
                result2.toString());
    }

    @Test
    public void testErrorListIsImmutable() {
        List<String> mutableErrors = Arrays.asList("Error 1", "Error 2");
        ValidationResult result = ValidationResult.failure(mutableErrors);

        // Should not be able to modify the errors list
        assertThrows(
                UnsupportedOperationException.class, () -> result.getErrors().add("New Error"));
    }
}
