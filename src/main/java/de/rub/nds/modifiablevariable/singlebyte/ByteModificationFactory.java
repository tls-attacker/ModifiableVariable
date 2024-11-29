/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.singlebyte;

import de.rub.nds.modifiablevariable.FileConfigurationException;
import de.rub.nds.modifiablevariable.VariableModification;
import de.rub.nds.modifiablevariable.util.RandomHelper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public final class ByteModificationFactory {

    private enum ModificationType {
        ADD,
        SUBTRACT,
        XOR,
        EXPLICIT,
        EXPLICIT_FROM_FILE
    }

    private static final int MODIFICATION_COUNT = ModificationType.values().length;

    private static List<VariableModification<Byte>> modificationsFromFile;

    public static final String FILE_NAME = "de/rub/nds/modifiablevariable/explicit/byte.vec";

    public static ByteAddModification add(String summand) {
        return add(Byte.parseByte(summand));
    }

    public static ByteAddModification add(Byte summand) {
        return new ByteAddModification(summand);
    }

    public static VariableModification<Byte> sub(String subtrahend) {
        return sub(Byte.parseByte(subtrahend));
    }

    public static VariableModification<Byte> sub(Byte subtrahend) {
        return new ByteSubtractModification(subtrahend);
    }

    public static VariableModification<Byte> xor(String xor) {
        return xor(Byte.parseByte(xor));
    }

    public static VariableModification<Byte> xor(Byte xor) {
        return new ByteXorModification(xor);
    }

    public static VariableModification<Byte> explicitValue(String value) {
        return explicitValue(Byte.parseByte(value));
    }

    public static VariableModification<Byte> explicitValue(Byte value) {
        return new ByteExplicitValueModification(value);
    }

    public static VariableModification<Byte> explicitValueFromFile(int value) {
        List<VariableModification<Byte>> modifications = modificationsFromFile();
        int pos = value % modifications.size();
        return modifications.get(pos);
    }

    public static synchronized List<VariableModification<Byte>> modificationsFromFile() {
        try {
            if (modificationsFromFile == null) {
                modificationsFromFile = new LinkedList<>();
                ClassLoader classLoader = ByteModificationFactory.class.getClassLoader();
                InputStream is = classLoader.getResourceAsStream(FILE_NAME);
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

    public static VariableModification<Byte> createRandomModification() {
        Random random = RandomHelper.getRandom();
        ModificationType randomType = ModificationType.values()[random.nextInt(MODIFICATION_COUNT)];
        byte modification = (byte) random.nextInt(Byte.MAX_VALUE);
        switch (randomType) {
            case ADD:
                return new ByteAddModification(modification);
            case SUBTRACT:
                return new ByteSubtractModification(modification);
            case XOR:
                return new ByteXorModification(modification);
            case EXPLICIT:
                return new ByteExplicitValueModification(modification);
            case EXPLICIT_FROM_FILE:
                return explicitValueFromFile(random.nextInt(Byte.MAX_VALUE));
            default:
                throw new IllegalStateException("Unexpected modification type: " + randomType);
        }
    }

    private ByteModificationFactory() {
        super();
    }
}
