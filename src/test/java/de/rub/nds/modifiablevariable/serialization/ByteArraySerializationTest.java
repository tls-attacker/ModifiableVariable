/**
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 *
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.modifiablevariable.serialization;

import de.rub.nds.modifiablevariable.VariableModification;
import de.rub.nds.modifiablevariable.bytearray.ByteArrayDeleteModification;
import de.rub.nds.modifiablevariable.bytearray.ByteArrayExplicitValueModification;
import de.rub.nds.modifiablevariable.bytearray.ByteArrayInsertModification;
import de.rub.nds.modifiablevariable.bytearray.ByteArrayModificationFactory;
import de.rub.nds.modifiablevariable.bytearray.ByteArrayXorModification;
import de.rub.nds.modifiablevariable.bytearray.ModifiableByteArray;
import de.rub.nds.modifiablevariable.filter.AccessModificationFilter;
import de.rub.nds.modifiablevariable.filter.ModificationFilterFactory;
import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotSame;
import org.junit.Before;
import org.junit.Test;

public class ByteArraySerializationTest {

    private static final Logger LOGGER = LogManager.getLogger(ByteArraySerializationTest.class);

    private ModifiableByteArray start;

    private byte[] expectedResult, result;

    private StringWriter writer;

    private JAXBContext context;

    private Marshaller m;

    private Unmarshaller um;

    public ByteArraySerializationTest() {
    }

    @Before
    public void setUp() throws JAXBException {
        start = new ModifiableByteArray();
        start.setOriginalValue(new byte[] { (byte) 0xff, 1, 2, 3 });
        expectedResult = null;
        result = null;

        writer = new StringWriter();
        context = JAXBContext.newInstance(ModifiableByteArray.class, ByteArrayDeleteModification.class,
            ByteArrayExplicitValueModification.class, ByteArrayInsertModification.class,
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

        expectedResult = new byte[] { (byte) 0xff, 1, 2, 3 };
        result = mba.getValue();
        assertArrayEquals(expectedResult, result);
        assertNotSame(expectedResult, result);
    }

    @Test
    public void testSerializeDeserializeWithModification() throws Exception {
        VariableModification<byte[]> modifier = ByteArrayModificationFactory.insert(new byte[] { 1, 2 }, 0);

        start.setModification(modifier);
        m.marshal(start, writer);

        String xmlString = writer.toString();
        LOGGER.debug(xmlString);

        um = context.createUnmarshaller();
        ModifiableByteArray mba = (ModifiableByteArray) um.unmarshal(new StringReader(xmlString));

        expectedResult = new byte[] { 1, 2, (byte) 0xff, 1, 2, 3 };
        result = mba.getValue();
        assertArrayEquals(expectedResult, result);
        assertNotSame(expectedResult, result);

    }

    @Test
    public void testSerializeDeserializeWithModificationFilter() throws Exception {
        VariableModification<byte[]> modifier = ByteArrayModificationFactory.delete(1, 1);
        int[] filtered = { 1, 3 };
        AccessModificationFilter filter = ModificationFilterFactory.access(filtered);
        modifier.setModificationFilter(filter);
        start.setModification(modifier);
        m.marshal(start, writer);

        String xmlString = writer.toString();
        LOGGER.debug(xmlString);

        um = context.createUnmarshaller();
        ModifiableByteArray mv = (ModifiableByteArray) um.unmarshal(new StringReader(xmlString));

        // it happens nothing, because the modification is filtered
        expectedResult = new byte[] { (byte) 0xff, 1, 2, 3 };
        result = mv.getValue();
        assertArrayEquals(expectedResult, result);
        assertNotSame(expectedResult, result);
    }
}
