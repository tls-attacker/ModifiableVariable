/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.integer;

import de.rub.nds.modifiablevariable.FileConfigurationException;
import de.rub.nds.modifiablevariable.VariableModification;
import de.rub.nds.modifiablevariable.util.RandomHelper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class IntegerModificationFactory {

    private enum ModificationType {
        ADD,
        SUBTRACT,
        MULTIPLY,
        XOR,
        SWAP_ENDIAN,
        EXPLICIT,
        SHIFT_LEFT,
        SHIFT_RIGHT,
        EXPLICIT_FROM_FILE,
        APPEND,
        INSERT,
        PREPEND
    }

    private static final int MODIFICATION_COUNT = ModificationType.values().length;

    private static final int MAX_MODIFICATION_VALUE = 32000;

    private static final int MAX_FILE_ENTRIES = 200;

    private static final int MAX_MODIFICATION_SHIFT_VALUE = 20;

    private static final int MAX_MODIFICATION_MULTIPLY_VALUE = 256;

    private static final int MAX_MODIFICATION_INSERT_VALUE = 256;

    private static final int MAX_MODIFICATION_INSERT_POSITION_VALUE = 32;

    private static List<VariableModification<Integer>> modificationsFromFile;

    public static final String FILE_NAME = "de/rub/nds/modifiablevariable/explicit/integer.vec";

    public static IntegerAddModification add(String summand) {
        return add(Integer.parseInt(summand));
    }

    public static IntegerAddModification add(Integer summand) {
        return new IntegerAddModification(summand);
    }

    public static IntegerShiftLeftModification shiftLeft(String shift) {
        return shiftLeft(Integer.parseInt(shift));
    }

    public static IntegerShiftLeftModification shiftLeft(Integer shift) {
        return new IntegerShiftLeftModification(shift);
    }

    public static IntegerShiftRightModification shiftRight(String shift) {
        return shiftRight(Integer.parseInt(shift));
    }

    public static IntegerShiftRightModification shiftRight(Integer shift) {
        return new IntegerShiftRightModification(shift);
    }

    public static VariableModification<Integer> sub(String subtrahend) {
        return sub(Integer.parseInt(subtrahend));
    }

    public static VariableModification<Integer> sub(Integer subtrahend) {
        return new IntegerSubtractModification(subtrahend);
    }

    public static VariableModification<Integer> xor(String xor) {
        return xor(Integer.parseInt(xor));
    }

    public static VariableModification<Integer> xor(Integer xor) {
        return new IntegerXorModification(xor);
    }

    public static VariableModification<Integer> swapEndian() {
        return new IntegerSwapEndianModification();
    }

    public static VariableModification<Integer> explicitValue(String value) {
        return explicitValue(Integer.parseInt(value));
    }

    public static VariableModification<Integer> explicitValue(Integer value) {
        return new IntegerExplicitValueModification(value);
    }

    public static VariableModification<Integer> explicitValueFromFile(int value) {
        List<VariableModification<Integer>> modifications = modificationsFromFile();
        int pos = value % modifications.size();
        return modifications.get(pos);
    }

    public static VariableModification<Integer> appendValue(Integer value) {
        return new IntegerAppendValueModification(value);
    }

    public static VariableModification<Integer> insertValue(Integer value, int position) {
        return new IntegerInsertValueModification(value, position);
    }

    public static VariableModification<Integer> prependValue(Integer value) {
        return new IntegerPrependValueModification(value);
    }

    public static VariableModification<Integer> multiply(Integer value) {
        return new IntegerMultiplyModification(value);
    }

    public static synchronized List<VariableModification<Integer>> modificationsFromFile() {
        try {
            if (modificationsFromFile == null) {
                modificationsFromFile = new ArrayList<>();
                ClassLoader classLoader = IntegerModificationFactory.class.getClassLoader();
                InputStream is = classLoader.getResourceAsStream(FILE_NAME);
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String line;
                int index = 0;
                while ((line = br.readLine()) != null) {
                    String value = line.trim().split(" ")[0];
                    modificationsFromFile.add(
                            new IntegerExplicitValueFromFileModification(
                                    index, Integer.parseInt(value)));
                    index++;
                }
            }
            return modificationsFromFile;
        } catch (IOException ex) {
            throw new FileConfigurationException(
                    "Modifiable variable file name could not have been found.", ex);
        }
    }

    public static VariableModification<Integer> createRandomModification() {
        Random random = RandomHelper.getRandom();
        ModificationType randomType = ModificationType.values()[random.nextInt(MODIFICATION_COUNT)];
        int modification = random.nextInt(MAX_MODIFICATION_VALUE);
        int insert_modification = random.nextInt(MAX_MODIFICATION_INSERT_VALUE);
        int shiftModification = random.nextInt(MAX_MODIFICATION_SHIFT_VALUE);
        switch (randomType) {
            case ADD:
                return new IntegerAddModification(modification);
            case SUBTRACT:
                return new IntegerSubtractModification(modification);
            case MULTIPLY:
                return new IntegerSubtractModification(
                        random.nextInt(MAX_MODIFICATION_MULTIPLY_VALUE));
            case XOR:
                return new IntegerXorModification(modification);
            case SWAP_ENDIAN:
                return new IntegerSwapEndianModification();
            case EXPLICIT:
                return new IntegerExplicitValueModification(modification);
            case SHIFT_LEFT:
                return new IntegerShiftLeftModification(shiftModification);
            case SHIFT_RIGHT:
                return new IntegerShiftRightModification(shiftModification);
            case EXPLICIT_FROM_FILE:
                return explicitValueFromFile(random.nextInt(MAX_FILE_ENTRIES));
            case APPEND:
                return new IntegerAppendValueModification(insert_modification);
            case INSERT:
                return new IntegerInsertValueModification(
                        insert_modification,
                        random.nextInt(MAX_MODIFICATION_INSERT_POSITION_VALUE));
            case PREPEND:
                return new IntegerPrependValueModification(insert_modification);
            default:
                throw new IllegalStateException("Unexpected modification type: " + randomType);
        }
    }

    private IntegerModificationFactory() {
        super();
    }
}
