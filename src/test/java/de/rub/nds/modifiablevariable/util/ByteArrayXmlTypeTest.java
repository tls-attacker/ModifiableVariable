/*
 * Copyright 2017 Lucas Hartmann <lucas.hartmann@rub.de>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.rub.nds.modifiablevariable.util;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Lucas Hartmann <lucas.hartmann@rub.de>
 */
public class ByteArrayXmlTypeTest {

    private static final Logger LOGGER = LogManager.getLogger(ByteArrayXmlTypeTest.class);

    @XmlRootElement
    @XmlAccessorType(XmlAccessType.FIELD)
    private static class SerializeMe extends ByteArrayXmlType {
    }

    private StringWriter writer;
    private JAXBContext context;
    private Marshaller m;
    private Unmarshaller um;

    @Before
    public void setUp() throws JAXBException {
        writer = new StringWriter();
        context = JAXBContext.newInstance(SerializeMe.class);
        m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        um = context.createUnmarshaller();
    }

    /**
     * Verify that the ByteArrayXmlType can be serialized properly.
     * 
     * @throws Exception
     */
    @Test
    public void testSerialize() throws Exception {

        SerializeMe o = new SerializeMe();
        List<String> b = new ArrayList<>();
        b.add("C0 FF EE");
        b.add("DE AD BE EF");
        o.setBytes(b);

        m.marshal(o, writer);
        String xmlString = writer.toString();
        LOGGER.debug(xmlString);

        SerializeMe result = (SerializeMe) um.unmarshal(new StringReader(xmlString));
        SerializeMe expectedResult = o;

        assertEquals(expectedResult, result);
        assertNotSame(expectedResult, result);
    }

}
