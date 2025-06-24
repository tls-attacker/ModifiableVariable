/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.length;

import static org.junit.jupiter.api.Assertions.*;

import de.rub.nds.modifiablevariable.bytearray.ModifiableByteArray;
import de.rub.nds.modifiablevariable.integer.IntegerAddModification;
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

public class ModifiableLengthFieldSerializationTest {

    private static final Logger LOGGER =
            LogManager.getLogger(ModifiableLengthFieldSerializationTest.class);

    private ModifiableLengthField lengthField;
    private ModifiableByteArray byteArray;
    private JAXBContext context;
    private Marshaller marshaller;
    private Unmarshaller unmarshaller;

    @BeforeEach
    public void setUp() throws JAXBException {
        byteArray = new ModifiableByteArray();
        byteArray.setOriginalValue(new byte[] {1, 2, 3, 4, 5});
        lengthField = new ModifiableLengthField(byteArray);

        context =
                JAXBContext.newInstance(
                        ModifiableLengthField.class,
                        ModifiableByteArray.class,
                        IntegerAddModification.class);
        marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        unmarshaller = context.createUnmarshaller();
    }

    @Test
    public void testSerializeDeserializeSimple() throws Exception {
        StringWriter writer = new StringWriter();
        marshaller.marshal(lengthField, writer);

        String xmlString = writer.toString();
        LOGGER.debug("Serialized XML: {}", xmlString);

        ModifiableLengthField deserialized =
                (ModifiableLengthField) unmarshaller.unmarshal(new StringReader(xmlString));

        assertNotNull(deserialized);
        assertEquals(5, (int) deserialized.getOriginalValue());
        assertEquals(5, (int) deserialized.getValue());
    }

    @Test
    public void testSerializeDeserializeWithModification() throws Exception {
        lengthField.setModifications(new IntegerAddModification(10));
        assertEquals(15, (int) lengthField.getValue());

        StringWriter writer = new StringWriter();
        marshaller.marshal(lengthField, writer);

        String xmlString = writer.toString();
        LOGGER.debug("Serialized XML with modification: {}", xmlString);

        ModifiableLengthField deserialized =
                (ModifiableLengthField) unmarshaller.unmarshal(new StringReader(xmlString));

        assertNotNull(deserialized);
        assertEquals(5, (int) deserialized.getOriginalValue());
        assertEquals(15, (int) deserialized.getValue());
    }

    @Test
    public void testSerializeDeserializeWithNullByteArrayValue() throws Exception {
        byteArray.setOriginalValue(null);
        assertNull(lengthField.getOriginalValue());
        assertNull(lengthField.getValue());

        StringWriter writer = new StringWriter();
        marshaller.marshal(lengthField, writer);

        String xmlString = writer.toString();
        LOGGER.debug("Serialized XML with null byte array: {}", xmlString);

        ModifiableLengthField deserialized =
                (ModifiableLengthField) unmarshaller.unmarshal(new StringReader(xmlString));

        assertNotNull(deserialized);
        assertNull(deserialized.getOriginalValue());
        assertNull(deserialized.getValue());
    }

    @Test
    public void testSerializedFieldReferencesArePreserved() throws Exception {
        lengthField.setModifications(new IntegerAddModification(3));

        StringWriter writer = new StringWriter();
        marshaller.marshal(lengthField, writer);
        String xmlString = writer.toString();

        ModifiableLengthField deserialized =
                (ModifiableLengthField) unmarshaller.unmarshal(new StringReader(xmlString));

        byteArray.setOriginalValue(new byte[] {1, 2});
        assertEquals(2, (int) lengthField.getOriginalValue());
        assertEquals(5, (int) lengthField.getValue());

        assertEquals(5, (int) deserialized.getOriginalValue());
        assertEquals(8, (int) deserialized.getValue());
    }
}
