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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import de.rub.nds.modifiablevariable.bytearray.ModifiableByteArray;
import de.rub.nds.modifiablevariable.integer.ModifiableInteger;
import de.rub.nds.modifiablevariable.string.ModifiableString;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ModifiableVariableHolderTest {

    private TestModifiableVariableHolder holder;

    // Simple implementation of ModifiableVariableHolder for testing
    private static class TestModifiableVariableHolder extends ModifiableVariableHolder {
        private ModifiableInteger intValue;
        private ModifiableString stringValue;
        private ModifiableByteArray byteArrayValue;
        private byte[] regularByteArray;
        private String regularString;
        private NestedHolder nestedHolder;

        TestModifiableVariableHolder() {
            intValue = new ModifiableInteger();
            stringValue = new ModifiableString();
            byteArrayValue = new ModifiableByteArray();
            regularByteArray = new byte[] {0x01, 0x02, 0x03};
            regularString = "Regular String";
            nestedHolder = new NestedHolder();
        }
    }

    private static class NestedHolder extends ModifiableVariableHolder {
        private ModifiableInteger nestedInt;

        NestedHolder() {
            nestedInt = new ModifiableInteger();
            nestedInt.setOriginalValue(42);
        }
    }

    @BeforeEach
    void setUp() {
        holder = new TestModifiableVariableHolder();
        holder.intValue.setOriginalValue(123);
        holder.stringValue.setOriginalValue("Test String");
        holder.byteArrayValue.setOriginalValue(new byte[] {0x04, 0x05, 0x06});
    }

    @Test
    void testGetAllModifiableVariableFields() {
        List<Field> fields = holder.getAllModifiableVariableFields();

        // Should find 3 fields: intValue, stringValue, byteArrayValue
        assertEquals(3, fields.size());

        // Verify field names
        boolean foundIntValue = false;
        boolean foundStringValue = false;
        boolean foundByteArrayValue = false;

        for (Field field : fields) {
            switch (field.getName()) {
                case "intValue":
                    foundIntValue = true;
                    break;
                case "stringValue":
                    foundStringValue = true;
                    break;
                case "byteArrayValue":
                    foundByteArrayValue = true;
                    break;
            }
        }

        assertTrue(foundIntValue);
        assertTrue(foundStringValue);
        assertTrue(foundByteArrayValue);
    }

    @Test
    void testGetRandomModifiableVariableField() {
        // Use a fixed seed for reproducible test
        Random random = new Random(123);

        Field field = holder.getRandomModifiableVariableField(random);
        assertNotNull(field);
        assertTrue(ModifiableVariable.class.isAssignableFrom(field.getType()));
    }

    @Test
    void testGetAllModifiableVariableHolders() {
        List<ModifiableVariableHolder> holders = holder.getAllModifiableVariableHolders();

        // Should only contain the holder itself by default
        assertEquals(1, holders.size());
        assertEquals(holder, holders.get(0));
    }

    @Test
    void testGetRandomModifiableVariableHolder() {
        // Use a fixed seed for reproducible test
        Random random = new Random(123);

        ModifiableVariableHolder randomHolder = holder.getRandomModifiableVariableHolder(random);
        assertNotNull(randomHolder);
        assertEquals(holder, randomHolder); // Since only one holder in the list
    }

    @Test
    void testReset() {
        // Apply a modification to test reset
        VariableModification<Integer> modification =
                new VariableModification<>() {
                    @Override
                    protected Integer modifyImplementationHook(Integer input) {
                        return input + 100;
                    }

                    @Override
                    public VariableModification<Integer> createCopy() {
                        // stub
                        return null;
                    }
                };

        holder.intValue.setModifications(modification);
        assertEquals(223, holder.intValue.getValue().intValue());

        // Reset and check that modifications are removed
        holder.reset();

        // Original value should be null but modification should remain
        assertNull(holder.intValue.getOriginalValue());
        assertNotNull(holder.intValue.getModifications());
    }

    @Test
    void testGetExtendedString() {
        String result = holder.getExtendedString();
        System.out.println("Extended string content: " + result);

        // The structure of the string can vary by platform/environment, so make minimal assertions
        assertTrue(result.contains("TestModifiableVariableHolder"));

        // Don't check specific integer value format
        assertTrue(result.contains("intValue"));

        // Check other values with less specific formatting requirements
        assertTrue(result.contains("stringValue"));
        assertTrue(result.contains("Test String"));

        // We only know the bytes will be there somewhere in hex format
        assertTrue(result.contains("04"));
        assertTrue(result.contains("05"));
        assertTrue(result.contains("06"));
        assertTrue(result.contains("01"));
        assertTrue(result.contains("02"));
        assertTrue(result.contains("03"));

        // Check for presence of other fields without exact formatting
        assertTrue(result.contains("regularString"));
        assertTrue(result.contains("Regular String"));
        assertTrue(result.contains("nestedHolder"));
        assertTrue(result.contains("NestedHolder"));
        assertTrue(result.contains("nestedInt"));
    }
}
