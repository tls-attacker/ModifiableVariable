/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.longint;

import de.rub.nds.modifiablevariable.FileConfigurationException;
import de.rub.nds.modifiablevariable.VariableModification;
import de.rub.nds.modifiablevariable.integer.IntegerModificationFactory;
import de.rub.nds.modifiablevariable.util.RandomHelper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class LongModificationFactory {

    private enum ModificationType {
        ADD, SUBTRACT, MULTIPLY, XOR, EXPLICIT,
        SHIFT_LEFT,
        SHIFT_RIGHT, EXPLICIT_FROM_FILE,
        APPEND,
        INSERT,
        PREPEND
    }
    private static final int MODIFICATION_COUNT = ModificationType.values().length;

    private static final int MAX_MODIFICATION_VALUE = 32000;

    private static final int MAX_FILE_ENTRIES = 200;

    private static final int MAX_MODIFICATION_SHIFT_VALUE = 40;

    private static final int MAX_MODIFICATION_MULTIPLY_VALUE = 256;

    private static final int MAX_MODIFICATION_INSERT_VALUE = 256;

    private static final int MAX_MODIFICATION_INSERT_POSITION_VALUE = 64;

    private static List<VariableModification<Long>> modificationsFromFile;

    public static final String FILE_NAME = "de/rub/nds/modifiablevariable/explicit/long.vec";

    public static LongAddModification add(final String summand) {
        return add(Long.parseLong(summand));
    }

    public static LongAddModification add(final Long summand) {
        return new LongAddModification(summand);
    }

    public static VariableModification<Long> sub(final String subtrahend) {
        return sub(Long.parseLong(subtrahend));
    }

    public static VariableModification<Long> sub(final Long subtrahend) {
        return new LongSubtractModification(subtrahend);
    }

    public static VariableModification<Long> xor(final String xor) {
        return xor(Long.parseLong(xor));
    }

    public static VariableModification<Long> xor(final Long xor) {
        return new LongXorModification(xor);
    }

    public static VariableModification<Long> explicitValue(final String value) {
        return explicitValue(Long.parseLong(value));
    }

    public static VariableModification<Long> explicitValue(final Long value) {
        return new LongExplicitValueModification(value);
    }

    public static VariableModification<Long> explicitValueFromFile(int value) {
        List<VariableModification<Long>> modifications = modificationsFromFile();
        int pos = value % modifications.size();
        return modifications.get(pos);
    }

    public static VariableModification<Long> appendValue(final Long value) {
        return new LongAppendValueModification(value);
    }

    public static VariableModification<Long> insertValue(final Long value, final int position) {
        return new LongInsertValueModification(value, position);
    }

    public static VariableModification<Long> prependValue(final Long value) {
        return new LongPrependValueModification(value);
    }

    public static VariableModification<Long> multiply(final Long factor) {
        return new LongMultiplyModification(factor);
    }

    public static VariableModification<Long> shiftLeft(final int shift) {
        return new LongShiftLeftModification(shift);
    }

    public static VariableModification<Long> shiftRight(final int shift) {
        return new LongShiftRightModification(shift);
    }


    public static synchronized List<VariableModification<Long>> modificationsFromFile() {
        try {
            if (modificationsFromFile == null) {
                modificationsFromFile = new LinkedList<>();
                ClassLoader classLoader = IntegerModificationFactory.class.getClassLoader();
                InputStream is =
                        classLoader.getResourceAsStream(FILE_NAME);
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String line;
                while ((line = br.readLine()) != null) {
                    String value = line.trim().split(" ")[0];
                    modificationsFromFile.add(explicitValue(value));
                }
            }
            return modificationsFromFile;
        } catch (IOException ex) {
            throw new FileConfigurationException(
                    "Modifiable variable file name could not have been found.", ex);
        }
    }

    public static VariableModification<Long> createRandomModification() {
        Random random = RandomHelper.getRandom();
        ModificationType randomType = ModificationType.values()[random.nextInt(MODIFICATION_COUNT)];
        long modification = random.nextInt(MAX_MODIFICATION_VALUE);
        long insert_modification = random.nextInt( MAX_MODIFICATION_INSERT_VALUE);
        int shiftModification = random.nextInt(MAX_MODIFICATION_SHIFT_VALUE);
        switch (randomType) {
            case ADD:
                return new LongAddModification(modification);
            case SUBTRACT:
                return new LongSubtractModification(modification);
            case MULTIPLY:
                return new LongMultiplyModification((long)random.nextInt(MAX_MODIFICATION_MULTIPLY_VALUE));
            case XOR:
                return new LongXorModification(modification);
            case EXPLICIT:
                return new LongExplicitValueModification(modification);
            case SHIFT_LEFT:
                return new LongShiftLeftModification(shiftModification);
            case SHIFT_RIGHT:
                return new LongShiftRightModification(shiftModification);
            case EXPLICIT_FROM_FILE:
                return explicitValueFromFile(random.nextInt(MAX_FILE_ENTRIES));
            case APPEND:
                return new LongAppendValueModification(insert_modification);
            case INSERT:
                return new LongInsertValueModification(insert_modification, random.nextInt(MAX_MODIFICATION_INSERT_POSITION_VALUE));
            case PREPEND:
                return new LongPrependValueModification(insert_modification);
            default:
                throw new IllegalStateException("Unexpected modification type: " + randomType);
        }
    }

    private LongModificationFactory() {}
}
