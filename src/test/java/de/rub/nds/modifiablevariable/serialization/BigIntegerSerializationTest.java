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
import de.rub.nds.modifiablevariable.biginteger.BigIntegerAddModification;
import de.rub.nds.modifiablevariable.biginteger.BigIntegerInteractiveModification;
import de.rub.nds.modifiablevariable.biginteger.BigIntegerModificationFactory;
import de.rub.nds.modifiablevariable.biginteger.ModifiableBigInteger;
import de.rub.nds.modifiablevariable.bytearray.ByteArrayModificationFactory;
import de.rub.nds.modifiablevariable.filter.AccessModificationFilter;
import de.rub.nds.modifiablevariable.filter.ModificationFilterFactory;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigInteger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import org.junit.Before;
import org.junit.Test;

public class BigIntegerSerializationTest {

    private static final Logger LOGGER = LogManager.getLogger(BigIntegerSerializationTest.class);

    private ModifiableBigInteger start;

    private BigInteger expectedResult, result;

    private StringWriter writer;

    private JAXBContext context;

    private Marshaller m;

    private Unmarshaller um;

    public BigIntegerSerializationTest() {
    }

    @Before
    public void setUp() throws JAXBException {
        start = new ModifiableBigInteger();
        start.setOriginalValue(BigInteger.TEN);
        expectedResult = null;
        result = null;

        writer = new StringWriter();
        context = JAXBContext.newInstance(ModifiableBigInteger.class, BigIntegerAddModification.class,
            ByteArrayModificationFactory.class, BigIntegerInteractiveModification.class);
        m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        um = context.createUnmarshaller();

        BigIntegerModificationFactory.setStandardInteractiveModification(
            new BigIntegerInteractiveModification.InteractiveBigIntegerModification() {
                public BigInteger modify(BigInteger oldVal) {
                    return new BigInteger("12");
                }
            });
    }

    @Test
    public void testSerializeDeserializeSimple() throws Exception {
        start.setModification(null);
        m.marshal(start, writer);

        String xmlString = writer.toString();
        LOGGER.info(xmlString);

        um = context.createUnmarshaller();
        ModifiableBigInteger mv = (ModifiableBigInteger) um.unmarshal(new StringReader(xmlString));

        expectedResult = new BigInteger("10");
        result = mv.getValue();
        assertEquals(expectedResult, result);
        assertNotSame(expectedResult, result);
    }

    @Test
    public void testSerializeDeserializeWithModification() throws Exception {
        VariableModification<BigInteger> modifier = BigIntegerModificationFactory.add(BigInteger.ONE);
        start.setModification(modifier);
        m.marshal(start, writer);

        String xmlString = writer.toString();
        LOGGER.debug(xmlString);

        um = context.createUnmarshaller();
        ModifiableBigInteger mv = (ModifiableBigInteger) um.unmarshal(new StringReader(xmlString));

        expectedResult = new BigInteger("11");
        result = mv.getValue();
        assertEquals(expectedResult, result);
        assertNotSame(expectedResult, result);
    }

    @Test
    public void testSerializationWithInteractiveMod() throws Exception {
        VariableModification<BigInteger> mod = BigIntegerModificationFactory.interactive();
        start.setModification(mod);
        m.marshal(start, writer);

        String xmlString = writer.toString();
        LOGGER.debug(xmlString);

        um = context.createUnmarshaller();
        ModifiableBigInteger mv = (ModifiableBigInteger) um.unmarshal(new StringReader(xmlString));

        expectedResult = new BigInteger("12");
        result = mv.getValue();
        assertEquals(expectedResult, result);
        assertNotSame(expectedResult, result);
    }

    @Test
    public void testSerializeDeserializeWithModificationFilter() throws Exception {
        VariableModification<BigInteger> modifier = BigIntegerModificationFactory.add(BigInteger.ONE);
        int[] filtered = { 1, 3 };
        AccessModificationFilter filter = ModificationFilterFactory.access(filtered);
        modifier.setModificationFilter(filter);
        start.setModification(modifier);
        m.marshal(start, writer);

        String xmlString = writer.toString();
        LOGGER.debug(xmlString);

        um = context.createUnmarshaller();
        ModifiableBigInteger mv = (ModifiableBigInteger) um.unmarshal(new StringReader(xmlString));

        expectedResult = new BigInteger("10");
        result = mv.getValue();
        assertEquals(expectedResult, result);
        assertNotSame(expectedResult, result);

    }

}
