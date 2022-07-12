/**
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Copyright 2014-2022 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 *
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.modifiablevariable;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import de.rub.nds.modifiablevariable.biginteger.ModifiableBigInteger;
import de.rub.nds.modifiablevariable.bytearray.ModifiableByteArray;
import de.rub.nds.modifiablevariable.integer.ModifiableInteger;
import de.rub.nds.modifiablevariable.mlong.ModifiableLong;
import de.rub.nds.modifiablevariable.singlebyte.ModifiableByte;
import de.rub.nds.modifiablevariable.util.ArrayConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

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
    public void testRandomByteArrayModification() {
        ModifiableByteArray array = ModifiableVariableFactory.createByteArrayModifiableVariable();
        array.setOriginalValue(new byte[] { 0, 1, 2 });
        array.createRandomModificationAtRuntime();
        LOGGER.info("Randomly modified byte array: " + ArrayConverter.bytesToHexString(array.getValue()));
        assertNotNull(array.getModification());
    }

    @Test
    public void testRandomSingleByteModification() {
        ModifiableByte singleByte = ModifiableVariableFactory.createByteModifiableVariable();
        singleByte.setOriginalValue((byte) 0);
        singleByte.createRandomModificationAtRuntime();
        LOGGER.info("Randomly modified byte: " + ArrayConverter.bytesToHexString(new byte[] { singleByte.getValue() }));
        assertNotNull(singleByte.getModification());
    }

    @Test
    public void testRandomLongModification() {
        ModifiableLong modLong = ModifiableVariableFactory.createLongModifiableVariable();
        modLong.setOriginalValue(0L);
        modLong.createRandomModificationAtRuntime();
        LOGGER.info("Randomly modified Long: " + modLong.getValue());
        assertNotNull(modLong.getModification());
    }
}
