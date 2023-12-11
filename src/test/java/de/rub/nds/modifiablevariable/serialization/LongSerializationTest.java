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
import de.rub.nds.modifiablevariable.bytearray.ByteArrayModificationFactory;
import de.rub.nds.modifiablevariable.filter.AccessModificationFilter;
import de.rub.nds.modifiablevariable.filter.ModificationFilterFactory;
import de.rub.nds.modifiablevariable.mlong.LongAddModification;
import de.rub.nds.modifiablevariable.mlong.LongModificationFactory;
import de.rub.nds.modifiablevariable.mlong.ModifiableLong;
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

public class LongSerializationTest {

    private static final Logger LOGGER = LogManager.getLogger(LongSerializationTest.class);

    private ModifiableLong start;

    private Long expectedResult, result;

    private StringWriter writer;

    private JAXBContext context;

    private Marshaller m;

    private Unmarshaller um;

    @BeforeEach
    public void setUp() throws JAXBException {
        start = new ModifiableLong();
        start.setOriginalValue(10L);
        expectedResult = null;
        result = null;

        writer = new StringWriter();
        context =
                JAXBContext.newInstance(
                        ModifiableLong.class,
                        LongAddModification.class,
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
        int[] filtered = {1, 3};
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
