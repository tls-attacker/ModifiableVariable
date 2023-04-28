/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.util;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.apache.commons.text.StringEscapeUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class IllegalStringAdapterTest {

    private static final Logger LOGGER = LogManager.getLogger();

    /** Test of unmarshal method, of class IllegalStringAdapter. */
    @Test
    public void testUnmarshal() {
        IllegalStringAdapter instance = new IllegalStringAdapter();
        byte[] data = new byte[256];
        for (int i = 0; i < data.length; i++) {
            data[i] = (byte) i;
        }
        LOGGER.info(
                "Bytes :"
                        + Arrays.toString(
                                new String(data, StandardCharsets.ISO_8859_1)
                                        .getBytes(StandardCharsets.ISO_8859_1)));
        String marshal = instance.marshal(new String(data, StandardCharsets.ISO_8859_1));
        LOGGER.info("Marshal: " + marshal);
        String unmarshal = instance.unmarshal(marshal);
        LOGGER.info("Unescaped:" + StringEscapeUtils.unescapeXml(unmarshal));
        LOGGER.info("Unmarshal: " + unmarshal);
        assertArrayEquals(data, unmarshal.getBytes(StandardCharsets.ISO_8859_1));
    }
}
