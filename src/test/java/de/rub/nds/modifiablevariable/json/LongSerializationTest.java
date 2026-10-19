/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.json;

import static org.junit.jupiter.api.Assertions.assertEquals;

import de.rub.nds.modifiablevariable.VariableModification;
import de.rub.nds.modifiablevariable.longint.LongAddModification;
import de.rub.nds.modifiablevariable.longint.ModifiableLong;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;

class LongSerializationTest {

    private static final Logger LOGGER = LogManager.getLogger();
    private static ObjectMapper mapper;

    private ModifiableLong start;
    private Long expectedResult, result;

    @BeforeAll
    public static void setUpClass() {
        mapper =
                JsonMapper.builder()
                        .addModule(new ModifiableVariableModule())
                        .changeDefaultVisibility(
                                vc -> ModifiableVariableModule.getFieldVisibilityChecker())
                        .build();
    }

    @BeforeEach
    void setUp() {
        start = new ModifiableLong();
        start.setOriginalValue(10L);
        expectedResult = null;
        result = null;
    }

    @Test
    void testSerializeDeserializeSimple() throws Exception {
        start.clearModifications();

        String jsonString = mapper.writeValueAsString(start);
        LOGGER.debug(jsonString);
        ModifiableLong mv = mapper.readValue(jsonString, ModifiableLong.class);

        expectedResult = 10L;
        result = mv.getValue();
        assertEquals(expectedResult, result);
    }

    @Test
    void testSerializeDeserializeWithModification() throws Exception {
        VariableModification<Long> modifier = new LongAddModification(1L);
        start.setModifications(modifier);

        String jsonString = mapper.writeValueAsString(start);
        LOGGER.debug(jsonString);
        ModifiableLong mv = mapper.readValue(jsonString, ModifiableLong.class);

        expectedResult = 11L;
        result = mv.getValue();
        assertEquals(expectedResult, result);
    }
}
