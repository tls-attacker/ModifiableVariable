/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.bytearray;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import de.rub.nds.modifiablevariable.ModifiableVariableFactory;
import de.rub.nds.modifiablevariable.VariableModification;
import de.rub.nds.modifiablevariable.util.ArrayConverter;
import java.util.Arrays;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ModifiableByteArrayTest {

    private static final Logger LOGGER = LogManager.getLogger();

    private ModifiableByteArray start;

    private byte[] originalValue;

    private byte[] modification1;

    private byte[] modification2;

    @BeforeEach
    public void setUp() {
        originalValue =
                new byte[] {(byte) 0, (byte) 1, (byte) 2, (byte) 3, (byte) 4, (byte) 5, (byte) 6};
        modification1 = new byte[] {(byte) 2, (byte) 3};
        modification2 =
                new byte[] {
                    (byte) 2, (byte) 1, (byte) 0, (byte) 1, (byte) 2, (byte) 3, (byte) 4, (byte) 5,
                    (byte) 6
                };
        start = new ModifiableByteArray();
        start.setOriginalValue(originalValue);
    }

    /** Test of setValue method, of class ModifiableByteArray. */
    @Test
    public void testSetValue() {
        LOGGER.info("testSetValue");
        ModifiableByteArray instance = new ModifiableByteArray();
        byte[] test = originalValue.clone();
        instance.setOriginalValue(test);
        assertArrayEquals(originalValue, instance.getValue());
    }

    /** Test of setExplicitValue method, of class ModifiableByteArray. */
    @Test
    public void testExplicitValue() {
        LOGGER.info("testExplicitValue");
        VariableModification<byte[]> modifier =
                new ByteArrayExplicitValueModification(modification1);
        start.setModifications(modifier);
        assertArrayEquals(modification1, start.getValue());
    }

    /** Test of setXorFirstBytes method, of class ModifiableByteArray. */
    @Test
    public void testXorFirstBytes() {
        LOGGER.info("testXorFirstBytes");
        VariableModification<byte[]> modifier = new ByteArrayXorModification(modification1, 0);
        start.setModifications(modifier);

        byte[] expResult = originalValue.clone();
        for (int i = 0; i < modification1.length; i++) {
            expResult[i] = (byte) (originalValue[i] ^ modification1[i]);
        }

        assertArrayEquals(expResult, start.getValue());

        byte[] expResult2 = originalValue.clone();
        for (int i = 0; i < originalValue.length; i++) {
            expResult2[i] = (byte) (originalValue[i] ^ modification2[i]);
        }
        VariableModification<byte[]> modifier2 = new ByteArrayXorModification(modification2, 0);
        start.setModifications(modifier2);
        assertArrayEquals(expResult2, start.getValue());
    }

    /** Test of setXorLastBytes method, of class ModifiableByteArray. */
    @Test
    public void testXorLastBytes() {
        LOGGER.info("testXorLastBytes");

        byte[] expResult = originalValue.clone();
        int first = expResult.length - modification1.length;
        for (int i = 0; i < modification1.length; i++) {
            expResult[first + i] = (byte) (originalValue[first + i] ^ modification1[i]);
        }

        VariableModification<byte[]> modifier = new ByteArrayXorModification(modification1, first);
        start.setModifications(modifier);

        LOGGER.debug("Expected: {}", expResult);
        LOGGER.debug("Computed: {}", () -> ArrayConverter.bytesToHexString(start.getValue()));
        assertArrayEquals(expResult, start.getValue());

        byte[] expResult2 = originalValue.clone();
        for (int i = 0; i < 2; i++) {
            expResult2[first + i] = (byte) (originalValue[first + i] ^ modification2[i]);
        }

        VariableModification<byte[]> modifier2 = new ByteArrayXorModification(modification2, first);
        start.setModifications(modifier2);
        assertArrayEquals(expResult2, start.getValue());
    }

    /** Test of setPrependBytes method, of class ModifiableByteArray. */
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

        VariableModification<byte[]> modifier =
                new ByteArrayInsertValueModification(modification1, 0);
        start.setModifications(modifier);

        LOGGER.debug("Expected: {}", expResult);
        LOGGER.debug("Computed: {}", () -> ArrayConverter.bytesToHexString(start.getValue()));
        assertArrayEquals(expResult, start.getValue());
    }

    /** Test of setAppendBytes method, of class ModifiableByteArray. */
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
                new ByteArrayInsertValueModification(modification1, originalValue.length);
        start.setModifications(modifier);

        LOGGER.debug("Expected: {}", expResult);
        LOGGER.debug("Computed: {}", () -> ArrayConverter.bytesToHexString(start.getValue()));
        assertArrayEquals(expResult, start.getValue());
    }

    /** Test of setDeleteLastBytes method, of class ModifiableByteArray. */
    @Test
    public void testDeleteLastBytes() {
        LOGGER.info("testDeleteLastBytes");
        // Deletes modification length many bytes
        assumeTrue(modification1.length < originalValue.length);
        int len = originalValue.length - modification1.length;
        byte[] expResult = new byte[len];
        System.arraycopy(originalValue, 0, expResult, 0, len);
        VariableModification<byte[]> modifier =
                new ByteArrayDeleteModification(len, modification1.length);
        start.setModifications(modifier);

        LOGGER.debug("Expected: {}", expResult);
        LOGGER.debug("Computed: {}", () -> ArrayConverter.bytesToHexString(start.getValue()));
        assertArrayEquals(expResult, start.getValue());
    }

    /** Test of setDeleteFirstBytes method, of class ModifiableByteArray. */
    @Test
    public void testDeleteFirstBytes() {
        LOGGER.info("testDeleteFirstBytes");
        // Deletes modification length many bytes
        assumeTrue(modification1.length < originalValue.length);

        int len = originalValue.length;
        byte[] expResult = new byte[len - modification1.length];
        System.arraycopy(
                originalValue, modification1.length, expResult, 0, len - modification1.length);
        VariableModification<byte[]> modifier =
                new ByteArrayDeleteModification(0, modification1.length);
        start.setModifications(modifier);

        LOGGER.debug("Expected: {}", expResult);
        LOGGER.debug("Computed: {}", () -> ArrayConverter.bytesToHexString(start.getValue()));
        assertArrayEquals(expResult, start.getValue());
    }

    /** Test of setDeleteBytes method, of class ModifiableByteArray. */
    @Test
    public void testDeleteBytes() {
        LOGGER.info("testDeleteBytes");
        // try to cover edge cases
        LOGGER.debug("Testing Delete all Bytes");
        int len = originalValue.length;
        byte[] expResult = new byte[0];
        byte[] expResult2 = {(byte) 0, (byte) 1, (byte) 2, (byte) 3, (byte) 4, (byte) 5};
        byte[] expResult3 = {(byte) 2, (byte) 3, (byte) 4, (byte) 5, (byte) 6};

        VariableModification<byte[]> modifier = new ByteArrayDeleteModification(0, len);
        start.setModifications(modifier);

        assertArrayEquals(expResult, start.getValue());
        start = new ModifiableByteArray();
        start.setOriginalValue(originalValue);
        LOGGER.debug("Testing Delete more Bytes than possible");
        modifier = new ByteArrayDeleteModification(0, len + 1);
        start.setModifications(modifier);

        assertArrayEquals(start.getValue(), expResult);
        start = new ModifiableByteArray();
        start.setOriginalValue(originalValue);
        LOGGER.debug("Testing Delete negative amount");
        modifier = new ByteArrayDeleteModification(0, -1);
        start.setModifications(modifier);
        assertArrayEquals(start.getValue(), originalValue);
        start = new ModifiableByteArray();
        start.setOriginalValue(originalValue);
        LOGGER.debug("Testing Delete 0 Bytes");
        modifier = new ByteArrayDeleteModification(0, 0);
        start.setModifications(modifier);
        assertArrayEquals(start.getValue(), originalValue);
        start = new ModifiableByteArray();
        start.setOriginalValue(originalValue);
        LOGGER.debug("Testing Delete from negative Start position");
        modifier = new ByteArrayDeleteModification(len * -2, 1);
        start.setModifications(modifier);
        assertArrayEquals(start.getValue(), expResult2);
        start = new ModifiableByteArray();
        start.setOriginalValue(originalValue);
        LOGGER.debug("Testing Delete from to big Start Position");
        modifier = new ByteArrayDeleteModification(len * 2, 2);
        start.setModifications(modifier);
        assertArrayEquals(start.getValue(), expResult3);
    }

    /** Test of setInsertBytes method, of class ModifiableByteArray. */
    @Test
    public void testInsertBytes() {
        LOGGER.info("testInsertBytes");
        // Insert at negative position -> wrap around
        assumeTrue(modification1.length < originalValue.length);
        LOGGER.debug("Inserting negative at position");
        VariableModification<byte[]> modifier =
                new ByteArrayInsertValueModification(modification1, -2 * originalValue.length);
        start.setModifications(modifier);
        byte[] expResult =
                ArrayConverter.concatenate(
                        Arrays.copyOf(originalValue, 1),
                        modification1,
                        Arrays.copyOfRange(originalValue, 1, originalValue.length));
        assertArrayEquals(start.getValue(), expResult);
        start = new ModifiableByteArray();
        start.setOriginalValue(originalValue);
        LOGGER.debug("Inserting empty Array");
        byte[] emptyArray = new byte[0];
        modifier = new ByteArrayInsertValueModification(emptyArray, 0);
        start.setModifications(modifier);
        assertArrayEquals(originalValue, start.getValue());

        // Insert at too positive position -> wrap around
        start = new ModifiableByteArray();
        start.setOriginalValue(originalValue);
        LOGGER.debug("Inserting at too large position");
        modifier = new ByteArrayInsertValueModification(modification1, originalValue.length * 2);
        start.setModifications(modifier);
        expResult =
                ArrayConverter.concatenate(
                        Arrays.copyOf(originalValue, originalValue.length - 1),
                        modification1,
                        Arrays.copyOfRange(
                                originalValue, originalValue.length - 1, originalValue.length));
        assertArrayEquals(expResult, start.getValue());
    }

    /** Test of add method, of class BigIntegerModificationFactory. */
    @Test
    public void testIsOriginalValueModified() {
        assertFalse(start.isOriginalValueModified());
        VariableModification<byte[]> modifier = new ByteArrayXorModification(new byte[] {}, 0);
        start.setModifications(modifier);
        assertFalse(start.isOriginalValueModified());
        modifier = new ByteArrayXorModification(new byte[] {1}, 0);
        start.setModifications(modifier);
        assertTrue(start.isOriginalValueModified());
        modifier = new ByteArrayXorModification(new byte[] {0, 0}, originalValue.length - 2);
        start.setModifications(modifier);
        assertFalse(start.isOriginalValueModified());
    }

    @Test
    public void testDuplicateModification() {
        LOGGER.info("testDuplicateModification");
        byte[] expResult = ArrayConverter.concatenate(originalValue, originalValue);

        VariableModification<byte[]> modifier = new ByteArrayDuplicateModification();
        start.setModifications(modifier);

        LOGGER.debug("Expected: {}", expResult);
        LOGGER.debug("Computed: {}", () -> ArrayConverter.bytesToHexString(start.getValue()));
        assertArrayEquals(expResult, start.getValue());
    }

    /** Test Shuffle */
    @Test
    public void testShuffle() {
        LOGGER.info("testShuffle");
        VariableModification<byte[]> modifier = new ByteArrayShuffleModification(new int[] {0, 1});
        start.setModifications(modifier);
        byte[] result = {1, 0, 2, 3, 4, 5, 6};
        assertArrayEquals(result, start.getValue());

        modifier = new ByteArrayShuffleModification(new int[] {0, 1, 2, 3, 4, 5, 6});
        start.setModifications(modifier);
        result = new byte[] {1, 0, 3, 2, 5, 4, 6};
        assertArrayEquals(result, start.getValue());

        modifier = new ByteArrayShuffleModification(new int[] {0, 1, 2, 3, 4, 5, 6, 7});
        start.setModifications(modifier);
        result = new byte[] {6, 0, 3, 2, 5, 4, 1};
        assertArrayEquals(result, start.getValue());
    }

    @Test
    public void toStringTest() {
        ModifiableByteArray toTest = new ModifiableByteArray();
        toTest =
                ModifiableVariableFactory.safelySetValue(
                        toTest, new byte[] {0x00, 0x11, 0x22, 0x33, 0x44});
        assertEquals("ModifiableByteArray{originalValue=00 11 22 33 44}", toTest.toString());
    }

    @Test
    public void testEquals() {
        ModifiableByteArray array1 = new ModifiableByteArray();
        array1.setOriginalValue(
                new byte[] {
                    1, 2, 3,
                });
        ModifiableByteArray array2 = new ModifiableByteArray();
        array2.setOriginalValue(
                new byte[] {
                    1, 2, 3,
                });
        assertEquals(array1, array2);
        array2.setOriginalValue(new byte[] {3, 4, 5});
        assertNotEquals(array1, array2);
    }
}
