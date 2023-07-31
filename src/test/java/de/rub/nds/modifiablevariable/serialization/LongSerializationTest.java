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
import de.rub.nds.modifiablevariable.bytearray.ByteArrayModificationFactory;
import de.rub.nds.modifiablevariable.filter.AccessModificationFilter;
import de.rub.nds.modifiablevariable.filter.ModificationFilterFactory;
import de.rub.nds.modifiablevariable.mlong.LongAddModification;
import de.rub.nds.modifiablevariable.mlong.LongModificationFactory;
import de.rub.nds.modifiablevariable.mlong.ModifiableLong;
import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class LongSerializationTest {

    private static final Logger LOGGER = LogManager.getLogger(LongSerializationTest.class);

    private ModifiableLong start;

    private Long expectedResult, result;

    private StringWriter writer;

    private JAXBContext context;

    private Marshaller m;

    private Unmarshaller um;

    public LongSerializationTest() {
    }

    @Before
    public void setUp() throws JAXBException {
        start = new ModifiableLong();
        start.setOriginalValue(10L);
        expectedResult = null;
        result = null;

        writer = new StringWriter();
        context = JAXBContext.newInstance(ModifiableLong.class, LongAddModification.class,
            ByteArrayModificationFactory.class);
        m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        um = context.createUnmarshaller();
    }

    @Test
    public void testSerializeDeserializeSimple() throws Exception {
        start.setModification(null);
        m.marshal(start, writer);

        String xmlString = writer.toString();
        LOGGER.info(xmlString);

        um = context.createUnmarshaller();
        ModifiableLong mv = (ModifiableLong) um.unmarshal(new StringReader(xmlString));

        expectedResult = 10L;
        result = mv.getValue();
        assertEquals(expectedResult, result);

    }

    @Test
    public void testSerializeDeserializeWithModification() throws Exception {
        VariableModification<Long> modifier = LongModificationFactory.add(1L);
        start.setModification(modifier);
        m.marshal(start, writer);

        String xmlString = writer.toString();
        LOGGER.debug(xmlString);

        um = context.createUnmarshaller();
        ModifiableLong mv = (ModifiableLong) um.unmarshal(new StringReader(xmlString));

        expectedResult = 11L;
        result = mv.getValue();
        assertEquals(expectedResult, result);

    }

    @Test
    public void testSerializeDeserializeWithModificationFilter() throws Exception {
        VariableModification<Long> modifier = LongModificationFactory.add(1L);
        int[] filtered = { 1, 3 };
        AccessModificationFilter filter = ModificationFilterFactory.access(filtered);
        modifier.setModificationFilter(filter);
        start.setModification(modifier);
        m.marshal(start, writer);

        String xmlString = writer.toString();
        LOGGER.debug(xmlString);

        um = context.createUnmarshaller();
        ModifiableLong mv = (ModifiableLong) um.unmarshal(new StringReader(xmlString));

        expectedResult = 10L;
        result = mv.getValue();
        assertEquals(expectedResult, result);

    }

}
