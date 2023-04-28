/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.serialization;

import static org.junit.jupiter.api.Assertions.assertEquals;

import de.rub.nds.modifiablevariable.singlebyte.ByteAddModification;
import de.rub.nds.modifiablevariable.singlebyte.ModifiableByte;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.StringReader;
import java.io.StringWriter;

public class ByteSerializationTest {

    private static final Logger LOGGER = LogManager.getLogger(ByteSerializationTest.class);

    private ModifiableByte start;

    private Byte expectedResult, result;

    private StringWriter writer;

    private JAXBContext context;

    private Marshaller m;

    private Unmarshaller um;

    @BeforeEach
    public void setUp() throws JAXBException {
        start = new ModifiableByte();
        start.setOriginalValue((byte) 10);

        writer = new StringWriter();
        context = JAXBContext.newInstance(ModifiableByte.class, ByteAddModification.class);
        m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        um = context.createUnmarshaller();
    }

    @Test
    public void testSerializeDeserializeSimple() throws Exception {
        start.setModification(null);
        m.marshal(start, writer);

        String xmlString = writer.toString();

        um = context.createUnmarshaller();
        ModifiableByte mv = (ModifiableByte) um.unmarshal(new StringReader(xmlString));

        expectedResult = 10;
        result = mv.getValue();
        assertEquals(expectedResult, result);
    }

    @Disabled("Not yet implemented")
    @Test
    public void testSerializeDeserializeWithDoubleModification() {
        // TODO

    }

    @Disabled("Not yet implemented")
    @Test
    public void testSerializeDeserializeWithDoubleModificationFilter() {
        // TODO

    }
}
