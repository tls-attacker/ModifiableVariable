/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.serialization;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import de.rub.nds.modifiablevariable.VariableModification;
import de.rub.nds.modifiablevariable.bytearray.*;
import de.rub.nds.modifiablevariable.filter.AccessModificationFilter;
import de.rub.nds.modifiablevariable.filter.ModificationFilterFactory;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.StringReader;
import java.io.StringWriter;

public class ByteArraySerializationTest {

    private static final Logger LOGGER = LogManager.getLogger(ByteArraySerializationTest.class);

    private ModifiableByteArray start;

    private byte[] expectedResult, result;

    private StringWriter writer;

    private JAXBContext context;

    private Marshaller m;

    private Unmarshaller um;

    @BeforeEach
    public void setUp() throws JAXBException {
        start = new ModifiableByteArray();
        start.setOriginalValue(new byte[] {(byte) 0xff, 1, 2, 3});
        expectedResult = null;
        result = null;

        writer = new StringWriter();
        context =
                JAXBContext.newInstance(
                        ModifiableByteArray.class,
                        ByteArrayDeleteModification.class,
                        ByteArrayExplicitValueModification.class,
                        ByteArrayInsertModification.class,
                        ByteArrayXorModification.class);
        m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        um = context.createUnmarshaller();
    }

    @Test
    public void testSerializeDeserializeSimple() throws Exception {
        start.setModification(null);
        m.marshal(start, writer);

        String xmlString = writer.toString();
        LOGGER.debug(xmlString);

        um = context.createUnmarshaller();
        ModifiableByteArray mba = (ModifiableByteArray) um.unmarshal(new StringReader(xmlString));

        expectedResult = new byte[] {(byte) 0xff, 1, 2, 3};
        result = mba.getValue();
        assertArrayEquals(expectedResult, result);
        assertNotSame(expectedResult, result);
    }

    @Test
    public void testSerializeDeserializeWithModification() throws Exception {
        VariableModification<byte[]> modifier =
                ByteArrayModificationFactory.insert(new byte[] {1, 2}, 0);

        start.setModification(modifier);
        m.marshal(start, writer);

        String xmlString = writer.toString();
        LOGGER.debug(xmlString);

        um = context.createUnmarshaller();
        ModifiableByteArray mba = (ModifiableByteArray) um.unmarshal(new StringReader(xmlString));

        expectedResult = new byte[] {1, 2, (byte) 0xff, 1, 2, 3};
        result = mba.getValue();
        assertArrayEquals(expectedResult, result);
        assertNotSame(expectedResult, result);
    }

    @Test
    public void testSerializeDeserializeWithModificationFilter() throws Exception {
        VariableModification<byte[]> modifier = ByteArrayModificationFactory.delete(1, 1);
        int[] filtered = {1, 3};
        AccessModificationFilter filter = ModificationFilterFactory.access(filtered);
        modifier.setModificationFilter(filter);
        start.setModification(modifier);
        m.marshal(start, writer);

        String xmlString = writer.toString();
        LOGGER.debug(xmlString);

        um = context.createUnmarshaller();
        ModifiableByteArray mv = (ModifiableByteArray) um.unmarshal(new StringReader(xmlString));

        // it happens nothing, because the modification is filtered
        expectedResult = new byte[] {(byte) 0xff, 1, 2, 3};
        result = mv.getValue();
        assertArrayEquals(expectedResult, result);
        assertNotSame(expectedResult, result);
    }
}
