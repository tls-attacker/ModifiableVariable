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
public class StringModificationFactory {

    private enum ModificationType {
        APPEND, PREPEND, EXPLICIT
    }
    private static final int MODIFICATION_COUNT = ModificationType.values().length;

    private static final int MAX_BYTE_LENGTH_INSERT = 200;

    private static final int MAX_BYTE_LENGTH_EXPLICIT = 1000;


    public static VariableModification<String> prependValue(final String value) {
        return new StringPrependValueModification(value);
    }

    public static VariableModification<String> appendValue(final String value) {
        return new StringAppendValueModification(value);
    }

    public static VariableModification<String> explicitValue(final String value) {
        return new StringExplicitValueModification(value);
    }

    public static VariableModification<String> createRandomModification() {
        Random random = RandomHelper.getRandom();
        ModificationType randomType = ModificationType.values()[random.nextInt(MODIFICATION_COUNT)];
        int modificationArrayLength;
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
            default:
                throw new IllegalStateException("Unexpected modification type: " + randomType);
        }
    }
}
