/**
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Copyright 2014-2017 Ruhr University Bochum / Hackmanit GmbH
 *
 * Licensed under Apache License 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.util;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 */
public class XMLPrettyPrinterTest {

    /**
     * Test of prettyPrintXML method, of class XMLPrettyPrinter.
     */
    @Test
    public void testPrettyPrintXML() throws Exception {
        String input = "<modifiableByteArray autoformat=\"true\">\n" + "    <originalValue>\n"
                + "FF 01 02 03 FF 01 02 03  FF 01 02 03 FF 01 02 03\n" + "FF 01 02 03\n" + "</originalValue>\n"
                + "</modifiableByteArray>";
        String expected = "<modifiableByteArray>\n        \n" + "    <originalValue>\n"
                + "        FF 01 02 03 FF 01 02 03  FF 01 02 03 FF 01 02 03\n" + "        FF 01 02 03\n"
                + "    </originalValue>\n    \n" + "</modifiableByteArray>";
        String result = XMLPrettyPrinter.prettyPrintXML(input);
        assertEquals(expected.trim(), result.trim());
    }
}
