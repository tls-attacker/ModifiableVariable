/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.string;

import de.rub.nds.modifiablevariable.VariableModification;
import de.rub.nds.modifiablevariable.util.RandomHelper;
import java.util.Random;

/** */
public final class StringModificationFactory {

    private StringModificationFactory() {
        super();
    }

    private enum ModificationType {
        APPEND,
        PREPEND,
        EXPLICIT,
        INSERT,
        DELETE
    }

    private static final int MODIFICATION_COUNT = ModificationType.values().length;

    private static final int MAX_BYTE_LENGTH_INSERT = 200;

    private static final int MAX_BYTE_LENGTH_EXPLICIT = 1000;

    private static final int MODIFIED_STRING_LENGTH_ESTIMATION = 50;

    public static VariableModification<String> prependValue(String value) {
        return new StringPrependValueModification(value);
    }

    public static VariableModification<String> appendValue(String value) {
        return new StringAppendValueModification(value);
    }

    public static VariableModification<String> explicitValue(String value) {
        return new StringExplicitValueModification(value);
    }

    public static VariableModification<String> insertValue(String value, int position) {
        return new StringInsertValueModification(value, position);
    }

    public static VariableModification<String> delete(int startPosition, int count) {
        return new StringDeleteModification(startPosition, count);
    }

    public static VariableModification<String> createRandomModification(String originalValue) {
        Random random = RandomHelper.getRandom();
        ModificationType randomType = ModificationType.values()[random.nextInt(MODIFICATION_COUNT)];
        int modificationArrayLength;
        int modifiedArrayLength;
        if (originalValue == null) {
            modifiedArrayLength = MODIFIED_STRING_LENGTH_ESTIMATION;
        } else {
            modifiedArrayLength = originalValue.length();
            if (modifiedArrayLength == 0 || modifiedArrayLength == 1) {
                randomType = ModificationType.EXPLICIT;
            }
        }
        switch (randomType) {
            case APPEND:
                modificationArrayLength = random.nextInt(MAX_BYTE_LENGTH_INSERT);
                if (modificationArrayLength == 0) {
                    modificationArrayLength++;
                }
                byte[] bytesToAppend = new byte[modificationArrayLength];
                random.nextBytes(bytesToAppend);
                return new StringAppendValueModification(new String(bytesToAppend));
            case PREPEND:
                modificationArrayLength = random.nextInt(MAX_BYTE_LENGTH_INSERT);
                if (modificationArrayLength == 0) {
                    modificationArrayLength++;
                }
                byte[] bytesToPrepend = new byte[modificationArrayLength];
                random.nextBytes(bytesToPrepend);
                return new StringPrependValueModification(new String(bytesToPrepend));
            case EXPLICIT:
                modificationArrayLength = random.nextInt(MAX_BYTE_LENGTH_EXPLICIT);
                byte[] explicitValue = new byte[modificationArrayLength];
                random.nextBytes(explicitValue);
                return new StringExplicitValueModification(new String(explicitValue));
            case INSERT:
                modificationArrayLength = random.nextInt(MAX_BYTE_LENGTH_INSERT);
                if (modificationArrayLength == 0) {
                    modificationArrayLength++;
                }
                byte[] bytesToInsert = new byte[modificationArrayLength];
                random.nextBytes(bytesToInsert);
                int insertPosition = random.nextInt(modifiedArrayLength);
                return new StringInsertValueModification(new String(bytesToInsert), insertPosition);
            case DELETE:
                int startPosition = random.nextInt(modifiedArrayLength - 1);
                int count = random.nextInt(modifiedArrayLength - startPosition);
                count++;
                return new StringDeleteModification(startPosition, count);
            default:
                throw new IllegalStateException("Unexpected modification type: " + randomType);
        }
    }
}
