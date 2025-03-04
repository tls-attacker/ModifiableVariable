/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.serialization;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import de.rub.nds.modifiablevariable.VariableModification;
import de.rub.nds.modifiablevariable.filter.AccessModificationFilter;
import de.rub.nds.modifiablevariable.filter.ModificationFilterFactory;
import de.rub.nds.modifiablevariable.string.ModifiableString;
import de.rub.nds.modifiablevariable.string.StringAppendValueModification;
import de.rub.nds.modifiablevariable.string.StringExplicitValueModification;
import de.rub.nds.modifiablevariable.string.StringModificationFactory;
import de.rub.nds.modifiablevariable.string.StringPrependValueModification;
import de.rub.nds.modifiablevariable.util.IllegalStringAdapter;
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

public class StringSerializationTest {

    private static final Logger LOGGER = LogManager.getLogger(StringSerializationTest.class);

    private ModifiableString start;
    private String expectedResult, result;
    private StringWriter writer;
    private JAXBContext context;
    private Marshaller m;
    private Unmarshaller um;

    @BeforeEach
    public void setUp() throws JAXBException {
        start = new ModifiableString();
        start.setOriginalValue("OriginalString");
        expectedResult = null;
        result = null;

        writer = new StringWriter();
        context =
                JAXBContext.newInstance(
                        ModifiableString.class,
                        StringExplicitValueModification.class,
                        StringAppendValueModification.class,
                        StringPrependValueModification.class,
                        IllegalStringAdapter.class);
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
        ModifiableString ms = (ModifiableString) um.unmarshal(new StringReader(xmlString));

        expectedResult = "OriginalString";
        result = ms.getValue();
        assertEquals(expectedResult, result);
        assertNotSame(expectedResult, result);
    }

    @Test
    public void testSerializeDeserializeWithAppendModification() throws Exception {
        VariableModification<String> modifier =
                StringModificationFactory.appendValue(" - Appended");
        start.setModification(modifier);
        m.marshal(start, writer);

        String xmlString = writer.toString();
        LOGGER.debug(xmlString);

        um = context.createUnmarshaller();
        ModifiableString ms = (ModifiableString) um.unmarshal(new StringReader(xmlString));

        expectedResult = "OriginalString - Appended";
        result = ms.getValue();
        assertEquals(expectedResult, result);
    }

    @Test
    public void testSerializeDeserializeWithPrependModification() throws Exception {
        VariableModification<String> modifier =
                StringModificationFactory.prependValue("Prepended - ");
        start.setModification(modifier);
        m.marshal(start, writer);

        String xmlString = writer.toString();
        LOGGER.debug(xmlString);

        um = context.createUnmarshaller();
        ModifiableString ms = (ModifiableString) um.unmarshal(new StringReader(xmlString));

        expectedResult = "Prepended - OriginalString";
        result = ms.getValue();
        assertEquals(expectedResult, result);
    }

    @Test
    public void testSerializeDeserializeWithExplicitValueModification() throws Exception {
        VariableModification<String> modifier =
                StringModificationFactory.explicitValue("ExplicitValue");
        start.setModification(modifier);
        m.marshal(start, writer);

        String xmlString = writer.toString();
        LOGGER.debug(xmlString);

        um = context.createUnmarshaller();
        ModifiableString ms = (ModifiableString) um.unmarshal(new StringReader(xmlString));

        expectedResult = "ExplicitValue";
        result = ms.getValue();
        assertEquals(expectedResult, result);
    }

    @Test
    public void testSerializeDeserializeWithModificationFilter() throws Exception {
        VariableModification<String> modifier =
                StringModificationFactory.appendValue(" - Filtered");
        int[] filtered = {1, 3};
        AccessModificationFilter filter = ModificationFilterFactory.access(filtered);
        modifier.setModificationFilter(filter);
        start.setModification(modifier);
        m.marshal(start, writer);

        String xmlString = writer.toString();
        LOGGER.debug(xmlString);

        um = context.createUnmarshaller();
        ModifiableString ms = (ModifiableString) um.unmarshal(new StringReader(xmlString));

        // No modification should be applied because of the filter
        expectedResult = "OriginalString";
        result = ms.getValue();
        assertEquals(expectedResult, result);
    }
}
