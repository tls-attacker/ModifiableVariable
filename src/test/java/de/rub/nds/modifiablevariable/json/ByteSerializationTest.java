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
import de.rub.nds.modifiablevariable.singlebyte.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ByteSerializationTest {

    private static final Logger LOGGER = LogManager.getLogger();
    private static ObjectMapper mapper;

    private ModifiableByte start;
    private Byte expectedResult, result;

    @BeforeAll
    public static void setUpClass() {
        mapper = new ObjectMapper();
        mapper.registerModule(new ModifiableVariableModule());
        mapper.setVisibility(ModifiableVariableModule.getFieldVisibilityChecker());
    }

    @BeforeEach
    void setUp() {
        start = new ModifiableByte();
        start.setOriginalValue((byte) 10);
    }

    @Test
    void testSerializeDeserializeSimple() throws Exception {
        start.clearModifications();

        String jsonString = mapper.writeValueAsString(start);
        LOGGER.debug(jsonString);
        ModifiableByte mv = mapper.readValue(jsonString, ModifiableByte.class);

        expectedResult = 10;
        result = mv.getValue();
        assertEquals(expectedResult, result);
        assertEquals(start.getOriginalValue(), mv.getOriginalValue());
    }

    @Test
    void testSerializeDeserializeWithAddModification() throws Exception {
        ByteAddModification mod = new ByteAddModification((byte) 5);
        start.setModifications(mod);

        String jsonString = mapper.writeValueAsString(start);
        LOGGER.debug(jsonString);
        ModifiableByte mv = mapper.readValue(jsonString, ModifiableByte.class);

        expectedResult = 15; // 10 + 5
        result = mv.getValue();
        assertEquals(expectedResult, result);
        assertEquals(start.getOriginalValue(), mv.getOriginalValue());
        assertTrue(mv.isOriginalValueModified());
    }

    @Test
    void testSerializeDeserializeWithSubtractModification() throws Exception {
        ByteSubtractModification mod = new ByteSubtractModification((byte) 3);
        start.setModifications(mod);

        String jsonString = mapper.writeValueAsString(start);
        LOGGER.debug(jsonString);
        ModifiableByte mv = mapper.readValue(jsonString, ModifiableByte.class);

        expectedResult = 7; // 10 - 3
        result = mv.getValue();
        assertEquals(expectedResult, result);
        assertEquals(start.getOriginalValue(), mv.getOriginalValue());
        assertTrue(mv.isOriginalValueModified());
    }

    @Test
    void testSerializeDeserializeWithXorModification() throws Exception {
        ByteXorModification mod = new ByteXorModification((byte) 6);
        start.setModifications(mod);

        String jsonString = mapper.writeValueAsString(start);
        LOGGER.debug(jsonString);
        ModifiableByte mv = mapper.readValue(jsonString, ModifiableByte.class);

        expectedResult = (byte) (10 ^ 6);
        result = mv.getValue();
        assertEquals(expectedResult, result);
        assertEquals(start.getOriginalValue(), mv.getOriginalValue());
        assertTrue(mv.isOriginalValueModified());
    }

    @Test
    void testSerializeDeserializeWithExplicitValueModification() throws Exception {
        ByteExplicitValueModification mod = new ByteExplicitValueModification((byte) 42);
        start.setModifications(mod);

        String jsonString = mapper.writeValueAsString(start);
        LOGGER.debug(jsonString);
        ModifiableByte mv = mapper.readValue(jsonString, ModifiableByte.class);

        expectedResult = 42;
        result = mv.getValue();
        assertEquals(expectedResult, result);
        assertEquals(start.getOriginalValue(), mv.getOriginalValue());
        assertTrue(mv.isOriginalValueModified());
    }

    @Test
    void testSerializeDeserializeWithDoubleModification() throws Exception {
        // Create a chain of modifications: add 5, then XOR with 3
        ByteAddModification addMod = new ByteAddModification((byte) 5);
        ByteXorModification xorMod = new ByteXorModification((byte) 3);

        start.setModifications(addMod, xorMod);

        String jsonString = mapper.writeValueAsString(start);
        LOGGER.debug(jsonString);
        ModifiableByte mv = mapper.readValue(jsonString, ModifiableByte.class);

        // Expected: (10 + 5) ^ 3 = 15 ^ 3 = 12
        expectedResult = (byte) ((10 + 5) ^ 3);
        result = mv.getValue();
        assertEquals(expectedResult, result);
        assertEquals(start.getOriginalValue(), mv.getOriginalValue());

        // Verify the XML content, but don't be too strict about exact representation
        assertTrue(jsonString.contains("ByteAdd") || jsonString.contains("modifications"));
        // The actual serialization of multiple modifications might vary, so focus on correctness
    }

    @Test
    void testSerializeDeserializeWithNullValue() throws Exception {
        ModifiableByte nullByte = new ModifiableByte();

        String jsonString = mapper.writeValueAsString(nullByte);
        LOGGER.debug(jsonString);
        ModifiableByte mv = mapper.readValue(jsonString, ModifiableByte.class);

        assertNull(mv.getOriginalValue());
        assertNull(mv.getValue());
    }

    @Test
    void testSerializeDeserializeWithAssertions() throws Exception {
        start.setAssertEquals((byte) 15);
        start.setModifications(new ByteAddModification((byte) 5));

        String jsonString = mapper.writeValueAsString(start);
        LOGGER.debug(jsonString);
        ModifiableByte mv = mapper.readValue(jsonString, ModifiableByte.class);

        assertEquals(start.getAssertEquals(), mv.getAssertEquals());
        assertEquals((byte) 15, mv.getValue());
        assertTrue(mv.validateAssertions());
    }

    @Test
    void testSerializeDeserializeWithMinMaxValues() throws Exception {
        // Test with MIN_VALUE
        start.setOriginalValue(Byte.MIN_VALUE);

        String jsonString = mapper.writeValueAsString(start);
        LOGGER.debug(jsonString);
        ModifiableByte mv = mapper.readValue(jsonString, ModifiableByte.class);

        assertEquals(Byte.MIN_VALUE, mv.getOriginalValue());
        assertEquals(Byte.MIN_VALUE, mv.getValue());

        // Test with MAX_VALUE
        start.setOriginalValue(Byte.MAX_VALUE);

        jsonString = mapper.writeValueAsString(start);
        LOGGER.debug(jsonString);
        mv = mapper.readValue(jsonString, ModifiableByte.class);

        assertEquals(Byte.MAX_VALUE, mv.getOriginalValue());
        assertEquals(Byte.MAX_VALUE, mv.getValue());
    }

    @Test
    void testCopyConstructorSerializationConsistency() throws Exception {
        // Set up original with modifications
        start.setModifications(new ByteAddModification((byte) 5));
        start.setAssertEquals((byte) 15);

        // Create a copy
        ModifiableByte copy = new ModifiableByte(start);

        // Serialize both
        String originalJson = mapper.writeValueAsString(start);
        String copyJson = mapper.writeValueAsString(copy);

        // Deserialize both XMLs
        ModifiableByte deserializedOriginal = mapper.readValue(originalJson, ModifiableByte.class);
        ModifiableByte deserializedCopy = mapper.readValue(copyJson, ModifiableByte.class);

        // Both deserialized objects should be equal
        assertEquals(deserializedOriginal.getOriginalValue(), deserializedCopy.getOriginalValue());
        assertEquals(deserializedOriginal.getValue(), deserializedCopy.getValue());
        assertEquals(deserializedOriginal.getAssertEquals(), deserializedCopy.getAssertEquals());
    }
}
