/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.string;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import de.rub.nds.modifiablevariable.VariableModification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ModifiableStringTest {

    private static final Logger LOGGER = LogManager.getLogger();
    private ModifiableString string;

    @BeforeEach
    public void setUp() {
        string = new ModifiableString();
        string.setOriginalValue("TestString");
    }

    @Test
    public void testIsOriginalValueModified() {
        assertFalse(string.isOriginalValueModified());
        VariableModification<String> modifier = new StringAppendValueModification("_Modified");
        string.setModifications(modifier);
        assertTrue(string.isOriginalValueModified());
    }

    @Test
    public void testGetterAndSetterMethods() {
        assertEquals("TestString", string.getOriginalValue());
        assertEquals("TestString", string.getValue());

        string.setOriginalValue("NewTestString");
        assertEquals("NewTestString", string.getOriginalValue());
        assertEquals("NewTestString", string.getValue());
    }

    @Test
    public void testAssertions() {
        string.setAssertEquals("TestString");
        assertTrue(string.validateAssertions());

        string.setAssertEquals("WrongValue");
        assertFalse(string.validateAssertions());
    }

    @Test
    public void testGetByteArray() {
        byte[] expected = "TestString".getBytes(java.nio.charset.StandardCharsets.ISO_8859_1);
        byte[] actual = string.getByteArray();

        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], actual[i]);
        }
    }

    @Test
    public void testEquals() {
        ModifiableString string2 = new ModifiableString();
        string2.setOriginalValue("TestString");
        assertEquals(string, string2);
        assertEquals(string2, string);
        string2.setModifications(new StringAppendValueModification("_Modified"));
        assertNotEquals(string, string2);
        assertNotEquals(string2, string);
        assertFalse(string.equals(null));
        assertFalse(string.equals("TestString"));
    }

    @Test
    public void testToString() {
        String expectedToString = "ModifiableString{originalValue='TestString'}";
        assertEquals(expectedToString, string.toString());

        string.setOriginalValue("Test\\String");
        String expectedEscaped = "ModifiableString{originalValue='Test\\\\String'}";
        assertEquals(expectedEscaped, string.toString());
    }
}
