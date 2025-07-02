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
import de.rub.nds.modifiablevariable.bytearray.ModifiableByteArray;
import de.rub.nds.modifiablevariable.integer.IntegerAddModification;
import de.rub.nds.modifiablevariable.length.ModifiableLengthField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LengthFieldSerializationTest {

    private static final Logger LOGGER = LogManager.getLogger();
    private static ObjectMapper mapper;

    private ModifiableLengthField lengthField;
    private ModifiableByteArray byteArray;

    @BeforeAll
    public static void setUpClass() {
        mapper = new ObjectMapper();
        mapper.registerModule(new ModifiableVariableModule());
        mapper.setVisibility(ModifiableVariableModule.getFieldVisibilityChecker());
    }

    @BeforeEach
    public void setUp() {
        byteArray = new ModifiableByteArray();
        byteArray.setOriginalValue(new byte[] {1, 2, 3, 4, 5});
        lengthField = new ModifiableLengthField(byteArray);
    }

    @Test
    public void testSerializeDeserializeSimple() throws Exception {
        String jsonString = mapper.writeValueAsString(lengthField);
        LOGGER.debug("Serialized JSON: {}", jsonString);

        ModifiableLengthField deserialized =
                mapper.readValue(jsonString, ModifiableLengthField.class);

        assertNotNull(deserialized);
        assertEquals(5, (int) deserialized.getOriginalValue());
        assertEquals(5, (int) deserialized.getValue());
    }

    @Test
    public void testSerializeDeserializeWithModification() throws Exception {
        lengthField.setModifications(new IntegerAddModification(10));
        assertEquals(15, (int) lengthField.getValue());

        String jsonString = mapper.writeValueAsString(lengthField);
        LOGGER.debug("Serialized JSON with modification: {}", jsonString);

        ModifiableLengthField deserialized =
                mapper.readValue(jsonString, ModifiableLengthField.class);

        assertNotNull(deserialized);
        assertEquals(5, (int) deserialized.getOriginalValue());
        assertEquals(15, (int) deserialized.getValue());
    }

    @Test
    public void testSerializeDeserializeWithNullByteArrayValue() throws Exception {
        byteArray.setOriginalValue(null);
        assertNull(lengthField.getOriginalValue());
        assertNull(lengthField.getValue());

        String jsonString = mapper.writeValueAsString(lengthField);
        LOGGER.debug("Serialized JSON with null byte array: {}", jsonString);

        ModifiableLengthField deserialized =
                mapper.readValue(jsonString, ModifiableLengthField.class);

        assertNotNull(deserialized);
        assertNull(deserialized.getOriginalValue());
        assertNull(deserialized.getValue());
    }

    @Test
    public void testSerializedFieldReferencesArePreserved() throws Exception {
        lengthField.setModifications(new IntegerAddModification(3));

        String jsonString = mapper.writeValueAsString(lengthField);
        ModifiableLengthField deserialized =
                mapper.readValue(jsonString, ModifiableLengthField.class);

        byteArray.setOriginalValue(new byte[] {1, 2});
        assertEquals(2, (int) lengthField.getOriginalValue());
        assertEquals(5, (int) lengthField.getValue());

        assertEquals(5, (int) deserialized.getOriginalValue());
        assertEquals(8, (int) deserialized.getValue());
    }
}
