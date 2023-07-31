/**
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 *
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.modifiablevariable.util;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class IllegalStringAdapterTest {

    private Logger LOGGER = LogManager.getLogger();

    public IllegalStringAdapterTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of unmarshal method, of class IllegalStringAdapter.
     */
    @Test
    public void testUnmarshal() {
        IllegalStringAdapter instance = new IllegalStringAdapter();
        byte[] data = new byte[256];
        for (int i = 0; i < data.length; i++) {
            data[i] = (byte) i;
        }
        LOGGER.info("Bytes :"
            + Arrays.toString(new String(data, StandardCharsets.ISO_8859_1).getBytes(StandardCharsets.ISO_8859_1)));
        String marshal = instance.marshal(new String(data, StandardCharsets.ISO_8859_1));
        LOGGER.info("Marshal: " + marshal);
        String unmarshal = instance.unmarshal(marshal);
        LOGGER.info("Unescaped:" + StringEscapeUtils.unescapeXml(unmarshal));
        LOGGER.info("Unmarshal: " + unmarshal);
        assertArrayEquals(data, unmarshal.getBytes(StandardCharsets.ISO_8859_1));
    }
}
