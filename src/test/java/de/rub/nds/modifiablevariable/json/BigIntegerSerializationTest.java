/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.json;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.rub.nds.modifiablevariable.VariableModification;
import de.rub.nds.modifiablevariable.biginteger.BigIntegerAddModification;
import de.rub.nds.modifiablevariable.biginteger.ModifiableBigInteger;
import java.math.BigInteger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BigIntegerSerializationTest {

    private static final Logger LOGGER = LogManager.getLogger();
    private static ObjectMapper mapper;

    private ModifiableBigInteger start;
    private BigInteger expectedResult, result;

    @BeforeAll
    static void setUpClass() {
        mapper = new ObjectMapper();
        mapper.registerModule(new ModifiableVariableModule());
        mapper.setVisibility(ModifiableVariableModule.getFieldVisibilityChecker());
    }

    @BeforeEach
    void setUp() {
        start = new ModifiableBigInteger();
        start.setOriginalValue(BigInteger.TEN);
        expectedResult = null;
        result = null;
    }

    @Test
    void testSerializeDeserializeSimple() throws Exception {
        start.clearModifications();

        String jsonString = mapper.writeValueAsString(start);
        LOGGER.debug(jsonString);
        ModifiableBigInteger mv = mapper.readValue(jsonString, ModifiableBigInteger.class);

        expectedResult = new BigInteger("10");
        result = mv.getValue();
        assertEquals(expectedResult, result);
        assertNotSame(expectedResult, result);
    }

    @Test
    void testSerializeDeserializeWithModification() throws Exception {
        VariableModification<BigInteger> modifier = new BigIntegerAddModification(BigInteger.ONE);
        start.setModifications(modifier);

        String jsonString = mapper.writeValueAsString(start);
        LOGGER.debug(jsonString);
        ModifiableBigInteger mv = mapper.readValue(jsonString, ModifiableBigInteger.class);

        expectedResult = new BigInteger("11");
        result = mv.getValue();
        assertEquals(expectedResult, result);
        assertNotSame(expectedResult, result);
    }
}
