/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.serialization;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.rub.nds.modifiablevariable.VariableModification;
import de.rub.nds.modifiablevariable.bytearray.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ByteArraySerializationTest {

    private static final Logger LOGGER = LogManager.getLogger();
    private static ObjectMapper mapper;

    private ModifiableByteArray start;
    private byte[] expectedResult, result;

    @BeforeAll
    static void setUpClass() {
        mapper = new ObjectMapper();
        mapper.registerModule(new ModifiableVariableModule());
        mapper.setVisibility(ModifiableVariableModule.getFieldVisibilityChecker());
    }

    @BeforeEach
    void setUp() {
        start = new ModifiableByteArray();
        start.setOriginalValue(new byte[] {(byte) 0xff, 1, 2, 3});
        expectedResult = null;
        result = null;
    }

    @Test
    void testSerializeDeserializeSimple() throws Exception {
        start.clearModifications();
        start.setAssertEquals(new byte[] {(byte) 0xff, 5, 44, 3});

        String jsonString = mapper.writeValueAsString(start);
        LOGGER.debug(jsonString);
        ModifiableByteArray mba = mapper.readValue(jsonString, ModifiableByteArray.class);

        expectedResult = new byte[] {(byte) 0xff, 1, 2, 3};
        result = mba.getValue();
        assertArrayEquals(expectedResult, result);
        assertNotSame(expectedResult, result);
    }

    @Test
    void testSerializeDeserializeWithModification() throws Exception {
        VariableModification<byte[]> modifier =
                new ByteArrayInsertValueModification(new byte[] {1, 2}, 0);
        start.setModifications(modifier);

        String jsonString = mapper.writeValueAsString(start);
        LOGGER.debug(jsonString);
        ModifiableByteArray mba = mapper.readValue(jsonString, ModifiableByteArray.class);

        expectedResult = new byte[] {1, 2, (byte) 0xff, 1, 2, 3};
        result = mba.getValue();
        assertArrayEquals(expectedResult, result);
        assertNotSame(expectedResult, result);
    }
}
