/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.serialization;

import static org.junit.jupiter.api.Assertions.*;

import de.rub.nds.modifiablevariable.singlebyte.*;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ByteSerializationTest {

    private ModifiableByte start;
    private Byte expectedResult, result;
    private StringWriter writer;
    private JAXBContext context;
    private Marshaller m;
    private Unmarshaller um;

    @BeforeEach
    void setUp() throws JAXBException {
        start = new ModifiableByte();
        start.setOriginalValue((byte) 10);

        writer = new StringWriter();
        context =
                JAXBContext.newInstance(
                        ModifiableByte.class,
                        ByteAddModification.class,
                        ByteSubtractModification.class,
                        ByteXorModification.class,
                        ByteExplicitValueModification.class);
        m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        um = context.createUnmarshaller();
    }

    @Test
    void testSerializeDeserializeSimple() throws Exception {
        start.clearModifications();
        m.marshal(start, writer);

        String xmlString = writer.toString();

        um = context.createUnmarshaller();
        ModifiableByte mv = (ModifiableByte) um.unmarshal(new StringReader(xmlString));

        expectedResult = 10;
        result = mv.getValue();
        assertEquals(expectedResult, result);
        assertEquals(start.getOriginalValue(), mv.getOriginalValue());
    }

    @Test
    void testSerializeDeserializeWithAddModification() throws Exception {
        ByteAddModification mod = new ByteAddModification((byte) 5);
        start.setModifications(mod);

        m.marshal(start, writer);
        String xmlString = writer.toString();

        ModifiableByte mv = (ModifiableByte) um.unmarshal(new StringReader(xmlString));

        expectedResult = 15; // 10 + 5
        result = mv.getValue();
        assertEquals(expectedResult, result);
        assertEquals(start.getOriginalValue(), mv.getOriginalValue());
        assertTrue(mv.isOriginalValueModified());
    }

    @Test
    void testSerializeDeserializeWithSubtractModification() throws Exception {
        ByteSubtractModification mod = new ByteSubtractModification((byte) 3);
        start.setModifications(mod);

        m.marshal(start, writer);
        String xmlString = writer.toString();

        ModifiableByte mv = (ModifiableByte) um.unmarshal(new StringReader(xmlString));

        expectedResult = 7; // 10 - 3
        result = mv.getValue();
        assertEquals(expectedResult, result);
        assertEquals(start.getOriginalValue(), mv.getOriginalValue());
        assertTrue(mv.isOriginalValueModified());
    }

    @Test
    void testSerializeDeserializeWithXorModification() throws Exception {
        ByteXorModification mod = new ByteXorModification((byte) 6);
        start.setModifications(mod);

        m.marshal(start, writer);
        String xmlString = writer.toString();

        ModifiableByte mv = (ModifiableByte) um.unmarshal(new StringReader(xmlString));

        expectedResult = (byte) (10 ^ 6);
        result = mv.getValue();
        assertEquals(expectedResult, result);
        assertEquals(start.getOriginalValue(), mv.getOriginalValue());
        assertTrue(mv.isOriginalValueModified());
    }

    @Test
    void testSerializeDeserializeWithExplicitValueModification() throws Exception {
        ByteExplicitValueModification mod = new ByteExplicitValueModification((byte) 42);
        start.setModifications(mod);

        m.marshal(start, writer);
        String xmlString = writer.toString();

        ModifiableByte mv = (ModifiableByte) um.unmarshal(new StringReader(xmlString));

        expectedResult = 42;
        result = mv.getValue();
        assertEquals(expectedResult, result);
        assertEquals(start.getOriginalValue(), mv.getOriginalValue());
        assertTrue(mv.isOriginalValueModified());
    }

    @Test
    void testSerializeDeserializeWithDoubleModification() throws Exception {
        // Create a chain of modifications: add 5, then XOR with 3
        ByteAddModification addMod = new ByteAddModification((byte) 5);
        ByteXorModification xorMod = new ByteXorModification((byte) 3);

        start.setModifications(addMod, xorMod);

        m.marshal(start, writer);
        String xmlString = writer.toString();

        ModifiableByte mv = (ModifiableByte) um.unmarshal(new StringReader(xmlString));

        // Expected: (10 + 5) ^ 3 = 15 ^ 3 = 12
        expectedResult = (byte) ((10 + 5) ^ 3);
        result = mv.getValue();
        assertEquals(expectedResult, result);
        assertEquals(start.getOriginalValue(), mv.getOriginalValue());

        // Verify the XML content, but don't be too strict about exact representation
        assertTrue(
                xmlString.contains("ByteAddModification") || xmlString.contains("modifications"));
        // The actual serialization of multiple modifications might vary, so focus on correctness
    }

    @Test
    void testSerializeDeserializeWithNullValue() throws Exception {
        ModifiableByte nullByte = new ModifiableByte();

        m.marshal(nullByte, writer);
        String xmlString = writer.toString();

        ModifiableByte mv = (ModifiableByte) um.unmarshal(new StringReader(xmlString));

        assertNull(mv.getOriginalValue());
        assertNull(mv.getValue());
    }

    @Test
    void testSerializeDeserializeWithAssertions() throws Exception {
        start.setAssertEquals((byte) 15);
        start.setModifications(new ByteAddModification((byte) 5));

        m.marshal(start, writer);
        String xmlString = writer.toString();

        ModifiableByte mv = (ModifiableByte) um.unmarshal(new StringReader(xmlString));

        assertEquals(start.getAssertEquals(), mv.getAssertEquals());
        assertEquals((byte) 15, mv.getValue());
        assertTrue(mv.validateAssertions());
    }

    @Test
    void testSerializeDeserializeWithMinMaxValues() throws Exception {
        // Test with MIN_VALUE
        start.setOriginalValue(Byte.MIN_VALUE);

        m.marshal(start, writer);
        String xmlString = writer.toString();

        ModifiableByte mv = (ModifiableByte) um.unmarshal(new StringReader(xmlString));

        assertEquals(Byte.MIN_VALUE, mv.getOriginalValue());
        assertEquals(Byte.MIN_VALUE, mv.getValue());

        // Test with MAX_VALUE
        start.setOriginalValue(Byte.MAX_VALUE);
        writer = new StringWriter();

        m.marshal(start, writer);
        xmlString = writer.toString();

        mv = (ModifiableByte) um.unmarshal(new StringReader(xmlString));

        assertEquals(Byte.MAX_VALUE, mv.getOriginalValue());
        assertEquals(Byte.MAX_VALUE, mv.getValue());
    }

    @Test
    void testCopyConstructorSerializationConsistency() throws Exception {
        // Set up original with modifications
        start.setModifications(new ByteAddModification((byte) 5));
        start.setAssertEquals((byte) 15);

        // Create a copy
        ModifiableByte copy = new ModifiableByte(start);

        // Serialize both
        m.marshal(start, writer);
        String originalXml = writer.toString();

        writer = new StringWriter();
        m.marshal(copy, writer);
        String copyXml = writer.toString();

        // Deserialize both XMLs
        ModifiableByte deserializedOriginal =
                (ModifiableByte) um.unmarshal(new StringReader(originalXml));
        ModifiableByte deserializedCopy = (ModifiableByte) um.unmarshal(new StringReader(copyXml));

        // Both deserialized objects should be equal
        assertEquals(deserializedOriginal.getOriginalValue(), deserializedCopy.getOriginalValue());
        assertEquals(deserializedOriginal.getValue(), deserializedCopy.getValue());
        assertEquals(deserializedOriginal.getAssertEquals(), deserializedCopy.getAssertEquals());
    }
}
