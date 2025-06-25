/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.json;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.rub.nds.modifiablevariable.VariableModification;
import de.rub.nds.modifiablevariable.string.ModifiableString;
import de.rub.nds.modifiablevariable.string.StringInsertValueModification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StringSerializationTest {

    private static final Logger LOGGER = LogManager.getLogger();
    private static ObjectMapper mapper;

    private ModifiableString start;
    private String expectedResult, result;

    @BeforeAll
    public static void setUpClass() {
        mapper = new ObjectMapper();
        mapper.registerModule(new ModifiableVariableModule());
        mapper.setVisibility(ModifiableVariableModule.getFieldVisibilityChecker());
    }

    @BeforeEach
    void setUp() {
        start = new ModifiableString("Hello from Test ❤️\\ \u0000 \u0001 \u0006");
        expectedResult = null;
        result = null;
    }

    @Test
    void testSerializeDeserializeSimple() throws Exception {
        start.clearModifications();
        start.setAssertEquals("Hello from Test 2 \\ \u0000 \u0001 \u0006");

        String jsonString = mapper.writeValueAsString(start);
        LOGGER.debug(jsonString);
        ModifiableString unmarshalled = mapper.readValue(jsonString, ModifiableString.class);

        expectedResult = "Hello from Test ❤️\\ \u0000 \u0001 \u0006";
        result = unmarshalled.getValue();
        assertEquals(expectedResult, result);
    }

    @Test
    void testSerializeDeserializeWithModification() throws Exception {
        VariableModification<String> modifier = new StringInsertValueModification("Uff! ", 0);
        start.setModifications(modifier);

        String jsonString = mapper.writeValueAsString(start);
        LOGGER.debug(jsonString);
        ModifiableString unmarshalled = mapper.readValue(jsonString, ModifiableString.class);

        expectedResult = "Uff! Hello from Test ❤️\\ \u0000 \u0001 \u0006";
        result = unmarshalled.getValue();
        assertEquals(expectedResult, result);
    }
}
