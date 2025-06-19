/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.serialization;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.rub.nds.modifiablevariable.VariableModification;
import de.rub.nds.modifiablevariable.longint.LongAddModification;
import de.rub.nds.modifiablevariable.longint.ModifiableLong;
import de.rub.nds.modifiablevariable.util.ModifiableVariableModule;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LongSerializationTest {

    private static final Logger LOGGER = LogManager.getLogger();
    private static ObjectMapper mapper;

    private ModifiableLong start;
    private Long expectedResult, result;

    @BeforeAll
    public static void setUpClass() {
        mapper = new ObjectMapper();
        mapper.registerModule(new ModifiableVariableModule());
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
