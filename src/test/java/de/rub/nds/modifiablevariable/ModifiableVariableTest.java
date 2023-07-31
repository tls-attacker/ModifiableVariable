/**
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 *
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.modifiablevariable;

import de.rub.nds.modifiablevariable.biginteger.ModifiableBigInteger;
import de.rub.nds.modifiablevariable.bytearray.ModifiableByteArray;
import de.rub.nds.modifiablevariable.integer.ModifiableInteger;
import de.rub.nds.modifiablevariable.mlong.ModifiableLong;
import de.rub.nds.modifiablevariable.singlebyte.ModifiableByte;
import de.rub.nds.modifiablevariable.util.ArrayConverter;
import java.math.BigInteger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

public class ModifiableVariableTest {

    private static final Logger LOGGER = LogManager.getLogger(ModifiableVariableTest.class);

    @Test
    public void testRandomBigIntegerModification() {
        ModifiableBigInteger bigInteger = ModifiableVariableFactory.createBigIntegerModifiableVariable();
        bigInteger.setOriginalValue(BigInteger.ZERO);
        bigInteger.createRandomModificationAtRuntime();
        LOGGER.info("Randomly modified big integer: " + bigInteger.getValue());
        assertNotNull(bigInteger.getModification());
    }

    @Test
    public void testRandomIntegerModification() {
        ModifiableInteger integer = ModifiableVariableFactory.createIntegerModifiableVariable();
        integer.setOriginalValue(0);
        integer.createRandomModificationAtRuntime();
        LOGGER.info("Randomly modified integer: " + integer.getValue());
        assertNotNull(integer.getModification());
    }

    @Test
    public void testRandomByteArrayModification() throws Exception {
        ModifiableByteArray array = ModifiableVariableFactory.createByteArrayModifiableVariable();
        array.setOriginalValue(new byte[] { 0, 1, 2 });
        array.createRandomModificationAtRuntime();
        LOGGER.info("Randomly modified byte array: " + ArrayConverter.bytesToHexString(array.getValue()));
        assertNotNull(array.getModification());
    }

    @Test
    public void testRandomSingleByteModification() throws Exception {
        ModifiableByte singleByte = ModifiableVariableFactory.createByteModifiableVariable();
        singleByte.setOriginalValue((byte) 0);
        singleByte.createRandomModificationAtRuntime();
        LOGGER.info("Randomly modified byte: " + ArrayConverter.bytesToHexString(new byte[] { singleByte.getValue() }));
        assertNotNull(singleByte.getModification());
    }

    @Test
    public void testRandomLongModification() throws Exception {
        ModifiableLong modLong = ModifiableVariableFactory.createLongModifiableVariable();
        modLong.setOriginalValue(new Long(0));
        modLong.createRandomModificationAtRuntime();
        LOGGER.info("Randomly modified Long: " + modLong.getValue());
        assertNotNull(modLong.getModification());
    }
}
