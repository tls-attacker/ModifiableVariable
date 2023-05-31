/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.serialization;

import de.rub.nds.modifiablevariable.integer.ModifiableInteger;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import java.io.StringWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

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
