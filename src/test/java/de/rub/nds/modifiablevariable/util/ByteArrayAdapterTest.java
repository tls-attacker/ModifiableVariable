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
import java.util.Arrays;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
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
public class ByteArrayAdapterTest {

    private static final Logger LOGGER = LogManager.getLogger(ByteArrayAdapterTest.class);

    @XmlRootElement
    private static class SerializeMe {

        private byte[] bytes = null;

        @XmlJavaTypeAdapter(ByteArrayAdapter.class)
        public byte[] getBytes() {
            return bytes;
        }

        public void setBytes(byte[] bytes) {
            this.bytes = bytes;
        }

        @Override
        public int hashCode() {
            int hash = 5;
            hash = 53 * hash + Arrays.hashCode(this.bytes);
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final SerializeMe other = (SerializeMe) obj;
            if (!Arrays.equals(this.bytes, other.bytes)) {
                return false;
            }
            return true;
        }

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
     * Verify that the ByteArrayXmlType can be serialized properly. Tests a
     * short byte array which won't be broken up for pretty printing.
     * 
     * @throws Exception
     */
    @Test
    public void testSerializeShortByteArray() throws Exception {

        SerializeMe o = new SerializeMe();
        byte[] b = new byte[] { (byte) 0xc0, (byte) 0xff, (byte) 0xee };
        o.setBytes(b);

        m.marshal(o, writer);
        String xmlString = writer.toString();
        LOGGER.debug(xmlString);

        SerializeMe result = (SerializeMe) um.unmarshal(new StringReader(xmlString));
        SerializeMe expectedResult = o;

        assertEquals(expectedResult, result);
        assertNotSame(expectedResult, result);
    }

    /**
     * Verify that the ByteArrayXmlType can be serialized properly. Tests a long
     * byte array (more than 16 elements) which will be broken up for pretty
     * printing.
     * 
     * @throws Exception
     */
    @Test
    public void testSerializeLongByteArray() throws Exception {

        SerializeMe o = new SerializeMe();
        byte[] b = new byte[] { (byte) 0xc0, (byte) 0xff, (byte) 0xee, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0x0a, 0x0b, 0x0c,
                (byte) 0xde, (byte) 0xad, (byte) 0xbe, (byte) 0xef };
        o.setBytes(b);

        m.marshal(o, writer);
        String xmlString = writer.toString();
        LOGGER.debug(xmlString);

        SerializeMe result = (SerializeMe) um.unmarshal(new StringReader(xmlString));
        SerializeMe expectedResult = o;

        assertEquals(expectedResult, result);
        assertNotSame(expectedResult, result);
    }

    /**
     * Verify that empty byte arrays are un/marshaled correctly.
     * 
     * @throws Exception
     */
    @Test
    public void testSerializeEmptyByteArray() throws Exception {

        SerializeMe o = new SerializeMe();

        m.marshal(o, writer);
        String xmlString = writer.toString();
        LOGGER.debug(xmlString);

        SerializeMe result = (SerializeMe) um.unmarshal(new StringReader(xmlString));
        SerializeMe expectedResult = o;

        assertEquals(expectedResult, result);
        assertNotSame(expectedResult, result);
    }
}
