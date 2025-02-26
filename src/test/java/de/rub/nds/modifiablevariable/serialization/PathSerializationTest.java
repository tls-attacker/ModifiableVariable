/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.serialization;

import static org.junit.jupiter.api.Assertions.assertEquals;

import de.rub.nds.modifiablevariable.VariableModification;
import de.rub.nds.modifiablevariable.path.ModifiablePath;
import de.rub.nds.modifiablevariable.path.PathModificationFactory;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PathSerializationTest {

    private static final Logger LOGGER = LogManager.getLogger();

    private ModifiablePath start;

    private String expectedResult, result;

    private StringWriter writer;

    private JAXBContext context;

    private Marshaller m;

    private Unmarshaller um;

    @BeforeEach
    public void setUp() throws JAXBException {
        start = new ModifiablePath("Hello from Test ❤️\\ \u0000 \u0001 \u0006");
        expectedResult = null;
        result = null;

        writer = new StringWriter();
        context = JAXBContext.newInstance(ModifiablePath.class);
        m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        um = context.createUnmarshaller();
    }

    @Test
    public void testSerializeDeserializeSimple() throws Exception {
        start.setModification(null);
        start.setAssertEquals("Hello from Test 2 \\ \u0000 \u0001 \u0006");
        m.marshal(start, writer);

        String xmlString = writer.toString();
        LOGGER.debug(xmlString);

        um = context.createUnmarshaller();
        ModifiablePath unmarshalled = (ModifiablePath) um.unmarshal(new StringReader(xmlString));

        expectedResult = "Hello from Test ❤️\\ \u0000 \u0001 \u0006";
        result = unmarshalled.getValue();
        assertEquals(expectedResult, result);
    }

    @Test
    public void testSerializeDeserializeWithModification() throws Exception {
        VariableModification<String> modifier = PathModificationFactory.insertValue("Uff! ", 0);
        start.setModification(modifier);
        m.marshal(start, writer);

        String xmlString = writer.toString();
        LOGGER.debug(xmlString);

        um = context.createUnmarshaller();
        ModifiablePath unmarshalled = (ModifiablePath) um.unmarshal(new StringReader(xmlString));

        expectedResult = "Uff! /Hello from Test ❤️\\ \u0000 \u0001 \u0006";
        result = unmarshalled.getValue();
        assertEquals(expectedResult, result);
    }
}
