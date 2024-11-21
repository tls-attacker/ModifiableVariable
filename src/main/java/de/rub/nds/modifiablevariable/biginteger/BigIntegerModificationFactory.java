/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.biginteger;

import de.rub.nds.modifiablevariable.FileConfigurationException;
import de.rub.nds.modifiablevariable.VariableModification;
import de.rub.nds.modifiablevariable.bytearray.ByteArrayModificationFactory;
import de.rub.nds.modifiablevariable.integer.IntegerModificationFactory;
import de.rub.nds.modifiablevariable.longint.LongModificationFactory;
import de.rub.nds.modifiablevariable.util.RandomHelper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class BigIntegerModificationFactory {

    private enum ModificationType {
        ADD,
        SUBTRACT,
        MULTIPLY,
        XOR,
        EXPLICIT,
        SHIFT_LEFT,
        SHIFT_RIGHT,
        EXPLICIT_FROM_FILE,
        APPEND,
        INSERT,
        PREPEND
    }

    private static final int MODIFICATION_COUNT = ModificationType.values().length;

    private static final int MAX_MODIFICATION_VALUE = 320000;

    private static final int MAX_FILE_ENTRIES = 200;

    private static final int MAX_MODIFICATION_SHIFT_VALUE = 50;

    private static final int MAX_MODIFICATION_MULTIPLY_VALUE = 256;

    private static final int MAX_MODIFICATION_INSERT_VALUE = 256;

    private static final int MAX_MODIFICATION_INSERT_POSITION_VALUE = 50;

    private static List<VariableModification<BigInteger>> modificationsFromFile;

    public static BigIntegerAddModification add(final String summand) {
        return add(new BigInteger(summand));
    }

    public static BigIntegerAddModification add(final BigInteger summand) {
        return new BigIntegerAddModification(summand);
    }

    public static BigIntegerShiftLeftModification shiftLeft(final String shift) {
        return shiftLeft(Integer.parseInt(shift));
    }

    public static BigIntegerShiftLeftModification shiftLeft(final Integer shift) {
        return new BigIntegerShiftLeftModification(shift);
    }

    public static BigIntegerShiftRightModification shiftRight(final String shift) {
        return shiftRight(Integer.parseInt(shift));
    }

    public static BigIntegerShiftRightModification shiftRight(final Integer shift) {
        return new BigIntegerShiftRightModification(shift);
    }

    public static BigIntegerMultiplyModification multiply(final BigInteger factor) {
        return new BigIntegerMultiplyModification(factor);
    }

    public static VariableModification<BigInteger> sub(final String subtrahend) {
        return sub(new BigInteger(subtrahend));
    }

    public static VariableModification<BigInteger> sub(final BigInteger subtrahend) {
        return new BigIntegerSubtractModification(subtrahend);
    }

    public static VariableModification<BigInteger> xor(final String xor) {
        return xor(new BigInteger(xor));
    }

    public static VariableModification<BigInteger> xor(final BigInteger xor) {
        return new BigIntegerXorModification(xor);
    }

    public static VariableModification<BigInteger> explicitValue(final String value) {
        return explicitValue(new BigInteger(value));
    }

    public static VariableModification<BigInteger> explicitValue(final BigInteger value) {
        return new BigIntegerExplicitValueModification(value);
    }

    public static VariableModification<BigInteger> explicitValueFromFile(int value) {
        List<VariableModification<BigInteger>> modifications = modificationsFromFile();
        int pos = value % modifications.size();
        return modifications.get(pos);
    }

    public static VariableModification<BigInteger> appendValue(final BigInteger value) {
        return new BigIntegerAppendValueModification(value);
    }

    public static VariableModification<BigInteger> insertValue(
            final BigInteger value, final int startPosition) {
        return new BigIntegerInsertValueModification(value, startPosition);
    }

    public static VariableModification<BigInteger> prependValue(final BigInteger value) {
        return new BigIntegerPrependValueModification(value);
    }

    /*
     * Interactive modification
     */
    private static BigIntegerInteractiveModification.InteractiveBigIntegerModification
            standardInteractiveModification =
                    new BigIntegerInteractiveModification.InteractiveBigIntegerModification() {
                        private BigInteger value;

                        @Override
                        public BigInteger modify(BigInteger oldVal) {
                            if (value == null) {
                                System.out.println("Enter new value for BigInt: ");
                                Scanner scanner = new Scanner(System.in);
                                try {
                                    value = scanner.nextBigInteger();
                                } finally {
                                    scanner.close();
                                }
                            }
                            return value;
                        }
                    };

    public static void setStandardInteractiveModification(
            BigIntegerInteractiveModification.InteractiveBigIntegerModification modification) {
        standardInteractiveModification = modification;
    }

    protected static BigIntegerInteractiveModification.InteractiveBigIntegerModification
            getStandardInteractiveModification() {
        return standardInteractiveModification;
    }

    public static VariableModification<BigInteger> interactive() {
        return new BigIntegerInteractiveModification();
    }

    public static synchronized List<VariableModification<BigInteger>> modificationsFromFile() {
        try {
            if (modificationsFromFile == null) {
                modificationsFromFile = new LinkedList<>();
                ClassLoader classLoader = ByteArrayModificationFactory.class.getClassLoader();
                InputStream is =
                        classLoader.getResourceAsStream(LongModificationFactory.FILE_NAME);
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String line;
                while ((line = br.readLine()) != null) {
                    String value = line.trim().split(" ")[0];
                    if (!value.isEmpty()) {
                        modificationsFromFile.add(explicitValue(value));
                    }
                }
            }
            return modificationsFromFile;
        } catch (IOException ex) {
            throw new FileConfigurationException(
                    "Modifiable variable file name could not have been found.", ex);
        }
    }

    public static VariableModification<BigInteger> createRandomModification() {
        Random random = RandomHelper.getRandom();
        ModificationType randomType = ModificationType.values()[random.nextInt(MODIFICATION_COUNT)];
        BigInteger modification = BigInteger.valueOf(random.nextInt(MAX_MODIFICATION_VALUE));
        BigInteger insert_modification =
                BigInteger.valueOf(random.nextInt(MAX_MODIFICATION_INSERT_VALUE));
        int shiftModification = random.nextInt(MAX_MODIFICATION_SHIFT_VALUE);
        switch (randomType) {
            case ADD:
                return new BigIntegerAddModification(modification);
            case SUBTRACT:
                return new BigIntegerSubtractModification(modification);
            case MULTIPLY:
                return new BigIntegerMultiplyModification(
                        BigInteger.valueOf(random.nextInt(MAX_MODIFICATION_MULTIPLY_VALUE)));
            case XOR:
                return new BigIntegerXorModification(modification);
            case EXPLICIT:
                return new BigIntegerExplicitValueModification(modification);
            case SHIFT_LEFT:
                return new BigIntegerShiftLeftModification(shiftModification);
            case SHIFT_RIGHT:
                return new BigIntegerShiftRightModification(shiftModification);
            case EXPLICIT_FROM_FILE:
                return explicitValueFromFile(MAX_FILE_ENTRIES);
            case APPEND:
                return new BigIntegerAppendValueModification(insert_modification);
            case INSERT:
                return new BigIntegerInsertValueModification(
                        insert_modification,
                        random.nextInt(MAX_MODIFICATION_INSERT_POSITION_VALUE));
            case PREPEND:
                return new BigIntegerPrependValueModification(insert_modification);
            default:
                throw new IllegalStateException("Unexpected modification type: " + randomType);
        }
    }

    private BigIntegerModificationFactory() {}
}
