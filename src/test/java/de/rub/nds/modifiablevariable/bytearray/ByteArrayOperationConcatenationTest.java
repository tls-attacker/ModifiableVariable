/**
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Copyright 2014-2017 Ruhr University Bochum / Hackmanit GmbH
 *
 * Licensed under Apache License 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package de.rub.nds.modifiablevariable.bytearray;

import org.junit.Before;

public class ByteArrayOperationConcatenationTest {

    private ModifiableByteArray start;

    private byte[] expectedResult, result;

    public ByteArrayOperationConcatenationTest() {
    }

    @Before
    public void setUp() {
        start = new ModifiableByteArray();
        start.setOriginalValue(new byte[] { 1, 10 });
    }
}
