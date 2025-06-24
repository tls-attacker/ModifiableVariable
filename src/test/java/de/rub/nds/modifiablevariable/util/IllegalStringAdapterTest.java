/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.util;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

class IllegalStringAdapterTest {

    private static final Logger LOGGER = LogManager.getLogger();

    /** Test of unmarshal method, of class IllegalStringAdapter. */
    @Test
    void testUnmarshal() {
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
        String unmarshal = instance.unmarshal(marshal);
        assertArrayEquals(data, unmarshal.getBytes(StandardCharsets.ISO_8859_1));
    }
}
