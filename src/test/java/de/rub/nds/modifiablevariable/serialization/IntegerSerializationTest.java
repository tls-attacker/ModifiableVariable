/**
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Copyright 2014-2022 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 *
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.modifiablevariable.serialization;

import de.rub.nds.modifiablevariable.integer.ModifiableInteger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringWriter;

public class IntegerSerializationTest {

    private static final Logger LOGGER = LogManager.getLogger(IntegerSerializationTest.class);

    private ModifiableInteger start;

    private int expectedResult, result;

    private StringWriter writer;

    private JAXBContext context;

    private Marshaller m;

    private Unmarshaller um;

    @BeforeEach
    public void setUp() {
        // todo
    }

    @Disabled("Not yet implemented")
    @Test
    public void testSerializeDeserializeSimple() {
        // TODO
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
