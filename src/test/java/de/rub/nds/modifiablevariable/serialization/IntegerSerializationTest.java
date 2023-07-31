/**
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 *
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.modifiablevariable.serialization;

import de.rub.nds.modifiablevariable.integer.ModifiableInteger;
import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

public class IntegerSerializationTest {

    private static final Logger LOGGER = LogManager.getLogger(IntegerSerializationTest.class);

    private ModifiableInteger start;

    private int expectedResult, result;

    private StringWriter writer;

    private JAXBContext context;

    private Marshaller m;

    private Unmarshaller um;

    public IntegerSerializationTest() {
    }

    @Before
    public void setUp() throws JAXBException {
        // todo
    }

    @Test
    public void testSerializeDeserializeSimple() throws Exception {
        // TODO
    }

    @Test
    public void testSerializeDeserializeWithDoubleModification() throws Exception {
        // TODO

    }

    @Test
    public void testSerializeDeserializeWithDoubleModificationFilter() throws Exception {
        // TODO

    }
}
