/**
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Copyright 2014-2022 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 *
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.modifiablevariable.serialization;

import static org.junit.jupiter.api.Assertions.assertEquals;

import de.rub.nds.modifiablevariable.singlebyte.ByteAddModification;
import de.rub.nds.modifiablevariable.singlebyte.ModifiableByte;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
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
