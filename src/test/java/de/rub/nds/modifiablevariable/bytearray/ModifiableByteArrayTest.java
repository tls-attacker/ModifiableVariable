/**
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 *
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.modifiablevariable.bytearray;

import de.rub.nds.modifiablevariable.ModifiableVariableFactory;
import de.rub.nds.modifiablevariable.VariableModification;
import de.rub.nds.modifiablevariable.util.ArrayConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;

public class ModifiableByteArrayTest {

    private static final Logger LOGGER = LogManager.getLogger(ModifiableByteArray.class);

    private ModifiableByteArray start;

    private byte[] originalValue;

    private byte[] modification1;

    private byte[] modification2;

    @Before
    public void setUp() {
        originalValue = new byte[] { (byte) 0, (byte) 1, (byte) 2, (byte) 3, (byte) 4, (byte) 5, (byte) 6 };
        modification1 = new byte[] { (byte) 2, (byte) 3 };
        modification2 =
            new byte[] { (byte) 2, (byte) 1, (byte) 0, (byte) 1, (byte) 2, (byte) 3, (byte) 4, (byte) 5, (byte) 6 };
        start = new ModifiableByteArray();
        start.setOriginalValue(originalValue);
    }

    /**
     * Test of setValue method, of class ModifiableByteArray.
     */
    @Test
    public void testSetValue() {
        LOGGER.info("testSetValue");
        ModifiableByteArray instance = new ModifiableByteArray();
        byte[] test = originalValue.clone();
        instance.setOriginalValue(test);
        assertArrayEquals(originalValue, instance.getValue());
    }

    /**
     * Test of setExplicitValue method, of class ModifiableByteArray.
     */
    @Test
    public void testExplicitValue() {
        LOGGER.info("testExplicitValue");
        VariableModification<byte[]> modifier = ByteArrayModificationFactory.explicitValue(modification1);
        start.setModification(modifier);
        assertArrayEquals(modification1, start.getValue());
    }

    /**
     * Test of payload method, of class ModifiableByteArray.
     */
    @Test
    public void testPayload() {
        LOGGER.info("testPayload");
        VariableModification<byte[]> modifier = ByteArrayModificationFactory.payload(modification1);
        start.setModification(modifier);
        assertArrayEquals(modification1, start.getValue());
    }

    /**
     * Test of payload method, of class ModifiableByteArray.
     */
    @Test
    public void testPayloadWithInsert() {
        LOGGER.info("testPayload");
        VariableModification<byte[]> modifier = ByteArrayModificationFactory.payload(modification1);
        ((ByteArrayPayloadModification) modifier).setInsert(true);
        ((ByteArrayPayloadModification) modifier).setInsertPosition(0);
        start.setModification(modifier);
        assertArrayEquals(ArrayConverter.concatenate(modification1, originalValue), start.getValue());
    }

    /**
     * Test of setXorFirstBytes method, of class ModifiableByteArray.
     */
    @Test
    public void testXorFirstBytes() {
        LOGGER.info("testXorFirstBytes");
        VariableModification<byte[]> modifier = ByteArrayModificationFactory.xor(modification1, 0);
        start.setModification(modifier);

        byte[] expResult = originalValue.clone();
        for (int i = 0; i < modification1.length; i++) {
            expResult[i] = (byte) (originalValue[i] ^ modification1[i]);
        }

        assertArrayEquals(expResult, start.getValue());

        VariableModification<byte[]> modifier2 = ByteArrayModificationFactory.xor(modification2, 0);
        start.setModification(modifier2);
        assertArrayEquals(originalValue, start.getValue());
    }

    /**
     * Test of setXorLastBytes method, of class ModifiableByteArray.
     */
    @Test
    public void testXorLastBytes() {
        LOGGER.info("testXorLastBytes");

        byte[] expResult = originalValue.clone();
        int first = expResult.length - modification1.length;
        for (int i = 0; i < modification1.length; i++) {
            expResult[first + i] = (byte) (originalValue[first + i] ^ modification1[i]);
        }

        VariableModification<byte[]> modifier = ByteArrayModificationFactory.xor(modification1, first);
        start.setModification(modifier);

        LOGGER.debug("Expected: " + ArrayConverter.bytesToHexString(expResult));
        LOGGER.debug("Computed: " + ArrayConverter.bytesToHexString(start.getValue()));
        assertArrayEquals(expResult, start.getValue());

        VariableModification<byte[]> modifier2 = ByteArrayModificationFactory.xor(modification2, first);
        start.setModification(modifier2);
        assertArrayEquals(originalValue, start.getValue());
    }

    /**
     * Test of setPrependBytes method, of class ModifiableByteArray.
     */
    @Test
    public void testPrependBytes() {
        LOGGER.info("testPrepend");
        int len = originalValue.length + modification1.length;
        byte[] expResult = new byte[len];
        for (int i = 0; i < len; i++) {
            if (i < modification1.length) {
                expResult[i] = modification1[i];
            } else {
                expResult[i] = originalValue[i - modification1.length];
            }
        }

        VariableModification<byte[]> modifier = ByteArrayModificationFactory.insert(modification1, 0);
        start.setModification(modifier);

        LOGGER.debug("Expected: " + ArrayConverter.bytesToHexString(expResult));
        LOGGER.debug("Computed: " + ArrayConverter.bytesToHexString(start.getValue()));
        assertArrayEquals(expResult, start.getValue());
    }

    /**
     * Test of setAppendBytes method, of class ModifiableByteArray.
     */
    @Test
    public void testAppendBytes() {
        LOGGER.info("testAppendBytes");
        int len = originalValue.length + modification1.length;
        byte[] expResult = new byte[len];
        for (int i = 0; i < len; i++) {
            if (i < originalValue.length) {
                expResult[i] = originalValue[i];
            } else {
                expResult[i] = modification1[i - originalValue.length];
            }
        }

        VariableModification<byte[]> modifier =
            ByteArrayModificationFactory.insert(modification1, originalValue.length);
        start.setModification(modifier);

        LOGGER.debug("Expected: " + ArrayConverter.bytesToHexString(expResult));
        LOGGER.debug("Computed: " + ArrayConverter.bytesToHexString(start.getValue()));
        assertArrayEquals(expResult, start.getValue());
    }

    /**
     * Test of setDeleteLastBytes method, of class ModifiableByteArray.
     */
    @Test
    public void testDeleteLastBytes() {
        LOGGER.info("testDeleteLastBytes");
        // Deletes modification length many bytes
        Assume.assumeTrue(modification1.length < originalValue.length);
        int len = originalValue.length - modification1.length;
        byte[] expResult = new byte[len];
        System.arraycopy(originalValue, 0, expResult, 0, len);
        VariableModification<byte[]> modifier = ByteArrayModificationFactory.delete(len, modification1.length);
        start.setModification(modifier);

        LOGGER.debug("Expected: " + ArrayConverter.bytesToHexString(expResult));
        LOGGER.debug("Computed: " + ArrayConverter.bytesToHexString(start.getValue()));
        assertArrayEquals(expResult, start.getValue());

    }

    /**
     * Test of setDeleteFirstBytes method, of class ModifiableByteArray.
     */
    @Test
    public void testDeleteFirstBytes() {
        LOGGER.info("testDeleteFirstBytes");
        // Deletes modification length many bytes
        Assume.assumeTrue(modification1.length < originalValue.length);

        int len = originalValue.length;
        byte[] expResult = new byte[len - modification1.length];
        for (int i = modification1.length; i < len; i++) {
            expResult[i - modification1.length] = originalValue[i];

        }
        VariableModification<byte[]> modifier = ByteArrayModificationFactory.delete(0, modification1.length);
        start.setModification(modifier);

        LOGGER.debug("Expected: " + ArrayConverter.bytesToHexString(expResult));
        LOGGER.debug("Computed: " + ArrayConverter.bytesToHexString(start.getValue()));
        assertArrayEquals(expResult, start.getValue());

    }

    /**
     * Test of setDeleteBytes method, of class ModifiableByteArray.
     */
    @Test
    public void testDeleteBytes() {
        LOGGER.info("testDeleteBytes");
        // try to cover edge cases
        LOGGER.debug("Testing Delete all Bytes");
        int len = originalValue.length;
        byte[] expResult = new byte[0];

        VariableModification<byte[]> modifier = ByteArrayModificationFactory.delete(0, len);
        start.setModification(modifier);

        assertArrayEquals(expResult, start.getValue());
        start = new ModifiableByteArray();
        start.setOriginalValue(originalValue);
        LOGGER.debug("Testing Delete more Bytes than possible");
        modifier = ByteArrayModificationFactory.delete(0, len + 1);
        start.setModification(modifier);

        assertArrayEquals(start.getValue(), originalValue);
        start = new ModifiableByteArray();
        start.setOriginalValue(originalValue);
        LOGGER.debug("Testing Delete negative amount");
        modifier = ByteArrayModificationFactory.delete(0, -1);
        start.setModification(modifier);
        assertArrayEquals(start.getValue(), originalValue);
        start = new ModifiableByteArray();
        start.setOriginalValue(originalValue);
        LOGGER.debug("Testing Delete 0 Bytes");
        modifier = ByteArrayModificationFactory.delete(0, 0);
        start.setModification(modifier);
        assertArrayEquals(start.getValue(), originalValue);
        start = new ModifiableByteArray();
        start.setOriginalValue(originalValue);
        LOGGER.debug("Testing Delete from negative Start position");
        modifier = ByteArrayModificationFactory.delete(len * -2, modification1.length);
        start.setModification(modifier);
        assertArrayEquals(start.getValue(), originalValue);
        start = new ModifiableByteArray();
        start.setOriginalValue(originalValue);
        LOGGER.debug("Testing Delete from to big Start Position");
        modifier = ByteArrayModificationFactory.delete(len * 2, modification1.length);
        start.setModification(modifier);
        assertArrayEquals(start.getValue(), originalValue);
    }

    /**
     * Test of setInsertBytes method, of class ModifiableByteArray.
     */
    @Test
    public void testInsertBytes() {
        LOGGER.info("testInsertBytes");
        // Insert negative position, insert 0 bytes, insert too far
        Assume.assumeTrue(modification1.length < originalValue.length);
        LOGGER.debug("Inserting negative Position");
        VariableModification<byte[]> modifier =
            ByteArrayModificationFactory.insert(modification1, -2 * originalValue.length);
        start.setModification(modifier);
        assertArrayEquals(start.getValue(), originalValue);
        start = new ModifiableByteArray();
        start.setOriginalValue(originalValue);
        LOGGER.debug("Inserting empty Array");
        byte[] emptyArray = new byte[0];
        modifier = ByteArrayModificationFactory.insert(emptyArray, 0);
        start.setModification(modifier);
        assertArrayEquals(originalValue, start.getValue());

        start = new ModifiableByteArray();
        start.setOriginalValue(originalValue);
        LOGGER.debug("Inserting to big Start position");
        modifier = ByteArrayModificationFactory.insert(modification1, originalValue.length * 2);
        start.setModification(modifier);
        assertArrayEquals(originalValue, start.getValue());
    }

    /**
     * Test of add method, of class BigIntegerModificationFactory.
     */
    @Test
    public void testIsOriginalValueModified() {
        assertFalse(start.isOriginalValueModified());
        VariableModification<byte[]> modifier = ByteArrayModificationFactory.xor(new byte[] {}, 0);
        start.setModification(modifier);
        assertFalse(start.isOriginalValueModified());
        modifier = ByteArrayModificationFactory.xor(new byte[] { 1 }, 0);
        start.setModification(modifier);
        assertTrue(start.isOriginalValueModified());
        modifier = ByteArrayModificationFactory.xor(new byte[] { 0, 0 }, originalValue.length - 2);
        start.setModification(modifier);
        assertFalse(start.isOriginalValueModified());
    }

    @Test
    public void testDuplicateModification() {
        LOGGER.info("testDuplicateModification");
        byte[] expResult = ArrayConverter.concatenate(originalValue, originalValue);

        VariableModification<byte[]> modifier = ByteArrayModificationFactory.duplicate();
        start.setModification(modifier);

        LOGGER.debug("Expected: " + ArrayConverter.bytesToHexString(expResult));
        LOGGER.debug("Computed: " + ArrayConverter.bytesToHexString(start.getValue()));
        assertArrayEquals(expResult, start.getValue());
    }

    /**
     * Test of explicitValue from file method
     */
    @Test
    public void testExplicitValueFromFile() {
        VariableModification<byte[]> modifier = ByteArrayModificationFactory.explicitValueFromFile(0);
        start.setModification(modifier);
        byte[] expectedResult = new byte[0];
        byte[] result = start.getValue();
        assertArrayEquals(expectedResult, result);

        modifier = ByteArrayModificationFactory.explicitValueFromFile(1);
        start.setModification(modifier);
        expectedResult = new byte[] { 00 };
        result = start.getValue();
        assertArrayEquals(expectedResult, result);

        modifier = ByteArrayModificationFactory.explicitValueFromFile(17);
        start.setModification(modifier);
        expectedResult = new byte[] { (byte) 255 };
        result = start.getValue();
        assertArrayEquals(expectedResult, result);
    }

    /**
     * Test Shuffle
     */
    @Test
    public void testShuffle() {
        LOGGER.info("testShuffle");
        VariableModification<byte[]> modifier = ByteArrayModificationFactory.shuffle(new byte[] { 0, 1 });
        start.setModification(modifier);
        byte[] result = { 1, 0, 2, 3, 4, 5, 6 };
        assertArrayEquals(result, start.getValue());

        modifier = ByteArrayModificationFactory.shuffle(new byte[] { 0, 1, 2, 3, 4, 5, 6 });
        start.setModification(modifier);
        result = new byte[] { 1, 0, 3, 2, 5, 4, 6 };
        assertArrayEquals(result, start.getValue());

        modifier = ByteArrayModificationFactory.shuffle(new byte[] { 0, 1, 2, 3, 4, 5, 6, 7 });
        start.setModification(modifier);
        result = new byte[] { 6, 0, 3, 2, 5, 4, 1 };
        assertArrayEquals(result, start.getValue());
    }

    @Test
    public void toStringTest() {
        ModifiableByteArray toTest = new ModifiableByteArray();
        toTest = ModifiableVariableFactory.safelySetValue(toTest, new byte[] { 0x00, 0x11, 0x22, 0x33, 0x44 });
        assertEquals("Original byte value is: 00 11 22 33 44", toTest.toString());

        VariableModification modification =
            new ByteArrayExplicitValueModification(new byte[] { 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08 });
        toTest.setModification(modification);
        assertEquals("Actual byte value is: 00 01 02 03 04 05 06 07 08\nOriginal value was: 00 11 22 33 44",
            toTest.toString());
    }

    @Test
    public void testEquals() {
        ModifiableByteArray array1 = new ModifiableByteArray();
        array1.setOriginalValue(new byte[] { 1, 2, 3, });
        ModifiableByteArray array2 = new ModifiableByteArray();
        array2.setOriginalValue(new byte[] { 1, 2, 3, });
        assertEquals(array1, array2);
        array2.setOriginalValue(new byte[] { 3, 4, 5 });
        Assert.assertNotEquals(array1, array2);
    }
}
