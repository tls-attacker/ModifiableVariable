/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.bytearray;

import de.rub.nds.modifiablevariable.FileConfigurationException;
import de.rub.nds.modifiablevariable.VariableModification;
import de.rub.nds.modifiablevariable.util.ArrayConverter;
import de.rub.nds.modifiablevariable.util.RandomHelper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class ByteArrayModificationFactory {

    private enum ModificationType {
        XOR, INSERT, DELETE, EXPLICIT, DUPLICATE, EXPLICIT_FROM_FILE, SHUFFLE
    }
    private static final int MODIFICATION_COUNT = ModificationType.values().length;

    private static final int MAX_CONFIG_PARAMETER = 200;

    private static final int EXPLICIT_VALUE_RANDOM = 1000;

    private static final int MODIFIED_ARRAY_LENGTH_ESTIMATION = 50;

    private static List<VariableModification<byte[]>> modificationsFromFile;

    public static final String FILE_NAME = "de/rub/nds/modifiablevariable/explicit/array.vec";

    /**
     * @param xor bytes to xor
     * @param startPosition negative numbers mean that the position is taken from the end
     * @return variable modification
     */
    public static VariableModification<byte[]> xor(final byte[] xor, final int startPosition) {
        return new ByteArrayXorModification(xor, startPosition);
    }

    /**
     * *
     *
     * @param bytesToInsert bytes to xor
     * @param startPosition negative numbers mean that the position is taken from the end
     * @return variable modification
     */
    public static VariableModification<byte[]> insert(
            final byte[] bytesToInsert, final int startPosition) {
        return new ByteArrayInsertModification(bytesToInsert, startPosition);
    }

    /**
     * * Deletes $count bytes from the input array beginning at $startPosition
     *
     * @param startPosition negative numbers mean that the position is taken from the end
     * @param count number of bytes to be deleted
     * @return variable modification
     */
    public static VariableModification<byte[]> delete(final int startPosition, final int count) {
        return new ByteArrayDeleteModification(startPosition, count);
    }

    /**
     * Duplicates the byte array
     *
     * @return duplicate variable modification
     */
    public static VariableModification<byte[]> duplicate() {
        return new ByteArrayDuplicateModification();
    }

    public static VariableModification<byte[]> explicitValue(final byte[] explicitValue) {
        return new ByteArrayExplicitValueModification(explicitValue);
    }

    public static VariableModification<byte[]> explicitValueFromFile(int value) {
        List<VariableModification<byte[]>> modifications = modificationsFromFile();
        int pos = value % modifications.size();
        return modifications.get(pos);
    }

    /**
     * Shuffles the bytes in the array, given a specified array of positions.
     *
     * @param shuffle positions that define shuffling
     * @return shuffling variable modification
     */
    public static VariableModification<byte[]> shuffle(final byte[] shuffle) {
        return new ByteArrayShuffleModification(shuffle);
    }

    public static synchronized List<VariableModification<byte[]>> modificationsFromFile() {
        try {
            if (modificationsFromFile == null) {
                modificationsFromFile = new LinkedList<>();
                ClassLoader classLoader = ByteArrayModificationFactory.class.getClassLoader();
                InputStream is = classLoader.getResourceAsStream(FILE_NAME);
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String line;
                while ((line = br.readLine()) != null) {
                    line = line.replaceAll("\\s+", "");
                    byte[] value = ArrayConverter.hexStringToByteArray(line);
                    modificationsFromFile.add(explicitValue(value));
                }
            }
            return modificationsFromFile;
        } catch (IOException ex) {
            throw new FileConfigurationException(
                    "Modifiable variable file name could not have been found.", ex);
        }
    }

    public static VariableModification<byte[]> createRandomModification(byte[] originalValue) {
        Random random = RandomHelper.getRandom();
        ModificationType randomType = ModificationType.values()[random.nextInt(MODIFICATION_COUNT)];
        int modifiedArrayLength;
        int modificationArrayLength;
        int startPosition;
        if (originalValue == null) {
            modifiedArrayLength = MODIFIED_ARRAY_LENGTH_ESTIMATION;
        } else {
            modifiedArrayLength = originalValue.length;
            if (originalValue.length == 0 || originalValue.length == 1) {
                randomType = ModificationType.EXPLICIT;
            }
        }
        switch (randomType) {
            case XOR:
                modificationArrayLength = random.nextInt(modifiedArrayLength);
                if (modificationArrayLength == 0) {
                    modificationArrayLength++;
                }
                byte[] xor = new byte[modificationArrayLength];
                random.nextBytes(xor);
                startPosition = random.nextInt(modifiedArrayLength - modificationArrayLength);
                return new ByteArrayXorModification(xor, startPosition);
            case INSERT:
                modificationArrayLength = random.nextInt(MAX_CONFIG_PARAMETER);
                if (modificationArrayLength == 0) {
                    modificationArrayLength++;
                }
                byte[] bytesToInsert = new byte[modificationArrayLength];
                random.nextBytes(bytesToInsert);
                int insertPosition = random.nextInt(modifiedArrayLength);
                return new ByteArrayInsertModification(bytesToInsert, insertPosition);
            case DELETE:
                startPosition = random.nextInt(modifiedArrayLength - 1);
                int count = random.nextInt(modifiedArrayLength - startPosition);
                count++;
                return new ByteArrayDeleteModification(startPosition, count);
            case EXPLICIT:
                modificationArrayLength = random.nextInt(MAX_CONFIG_PARAMETER);
                if (modificationArrayLength == 0) {
                    modificationArrayLength++;
                }
                byte[] explicitValue = new byte[modificationArrayLength];
                random.nextBytes(explicitValue);
                return new ByteArrayExplicitValueModification(explicitValue);
            case DUPLICATE:
                return new ByteArrayDuplicateModification();
            case EXPLICIT_FROM_FILE:
                return explicitValueFromFile(random.nextInt(EXPLICIT_VALUE_RANDOM));
            case SHUFFLE:
                int shuffleSize = random.nextInt(MAX_CONFIG_PARAMETER);
                byte[] shuffle = new byte[shuffleSize];
                random.nextBytes(shuffle);
                return new ByteArrayShuffleModification(shuffle);
            default:
                throw new IllegalStateException("Unexpected modification type: " + randomType);
        }
    }

    private ByteArrayModificationFactory() {}
}
