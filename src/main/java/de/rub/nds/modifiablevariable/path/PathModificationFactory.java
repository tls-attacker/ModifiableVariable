/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.path;

import de.rub.nds.modifiablevariable.VariableModification;
import de.rub.nds.modifiablevariable.string.StringDeleteModification;
import de.rub.nds.modifiablevariable.util.RandomHelper;
import java.util.Random;

/** */
public final class PathModificationFactory {

    private PathModificationFactory() {
        super();
    }

    private enum ModificationType {
        APPEND,
        PREPEND,
        INSERT,
        DELETE,
        INSERT_DIRECTORY_TRAVERSAL,
        INSERT_DIRECTORY_SEPERATOR,
        TOGGLE_ROOT
    }

    private static final int MODIFICATION_COUNT = ModificationType.values().length;

    private static final int MAX_BYTE_LENGTH_INSERT = 200;

    private static final int NUMBER_OF_PATH_PARTS_ESTIMATION = 50;

    private static final int MAX_NUMBER_OF_DIRECTORY_TRAVERSAL_INSERT = 10;
    private static final int MAX_NUMBER_OF_DIRECTORY_SEPERATOR_INSERT = 10;

    public static VariableModification<String> prependValue(String value) {
        return new PathPrependValueModification(value);
    }

    public static VariableModification<String> explicitValue(String value) {
        return new PathExplicitValueModification(value);
    }

    public static VariableModification<String> appendValue(String value) {
        return new PathAppendValueModification(value);
    }

    public static VariableModification<String> insertValue(String value, int position) {
        return new PathInsertValueModification(value, position);
    }

    public static VariableModification<String> delete(int position, int count) {
        return new PathDeleteModification(position, count);
    }

    public static VariableModification<String> insertDirectoryTraversal(int count, int position) {
        return new PathInsertDirectoryTraversalModification(count, position);
    }

    public static VariableModification<String> insertDirectorySeperator(int count, int position) {
        return new PathInsertDirectorySeparatorModification(count, position);
    }

    public static VariableModification<String> toggleRoot() {
        return new PathToggleRootModification();
    }

    public static VariableModification<String> createRandomModification(String originalValue) {
        Random random = RandomHelper.getRandom();
        ModificationType randomType = ModificationType.values()[random.nextInt(MODIFICATION_COUNT)];
        int modificationStringLength;
        int numberOfPathParts;
        if (originalValue == null) {
            numberOfPathParts = NUMBER_OF_PATH_PARTS_ESTIMATION;
        } else {
            String[] pathParts = originalValue.split("/");
            if (pathParts.length == 0) {
                if (randomType == ModificationType.INSERT
                        || randomType == ModificationType.DELETE) {
                    randomType = ModificationType.APPEND;
                }
                numberOfPathParts = 0;
            } else {
                if (pathParts[0].isEmpty()) {
                    numberOfPathParts = originalValue.split("/").length - 1;
                } else {
                    numberOfPathParts = originalValue.split("/").length;
                }
            }
        }
        int insertPosition;
        switch (randomType) {
            case APPEND:
                modificationStringLength = random.nextInt(MAX_BYTE_LENGTH_INSERT);
                if (modificationStringLength == 0) {
                    modificationStringLength++;
                }
                byte[] bytesToAppend = new byte[modificationStringLength];
                random.nextBytes(bytesToAppend);
                return new PathAppendValueModification(new String(bytesToAppend));
            case PREPEND:
                modificationStringLength = random.nextInt(MAX_BYTE_LENGTH_INSERT);
                if (modificationStringLength == 0) {
                    modificationStringLength++;
                }
                byte[] bytesToPrepend = new byte[modificationStringLength];
                random.nextBytes(bytesToPrepend);
                return new PathPrependValueModification(new String(bytesToPrepend));
            case INSERT:
                modificationStringLength = random.nextInt(MAX_BYTE_LENGTH_INSERT);
                if (modificationStringLength == 0) {
                    modificationStringLength++;
                }
                byte[] bytesToInsert = new byte[modificationStringLength];
                random.nextBytes(bytesToInsert);
                insertPosition = random.nextInt(numberOfPathParts);
                return new PathInsertValueModification(new String(bytesToInsert), insertPosition);
            case INSERT_DIRECTORY_TRAVERSAL:
                int numberOfDirectoryTraversal =
                        random.nextInt(MAX_NUMBER_OF_DIRECTORY_TRAVERSAL_INSERT);
                insertPosition = random.nextInt(numberOfPathParts);
                return new PathInsertDirectoryTraversalModification(
                        numberOfDirectoryTraversal, insertPosition);
            case INSERT_DIRECTORY_SEPERATOR:
                int numberOfDirectorySeperator =
                        random.nextInt(MAX_NUMBER_OF_DIRECTORY_SEPERATOR_INSERT);
                insertPosition = random.nextInt(numberOfPathParts);
                return new PathInsertDirectorySeparatorModification(
                        numberOfDirectorySeperator, insertPosition);
            case TOGGLE_ROOT:
                return new PathToggleRootModification();
            case DELETE:
                int startPosition = random.nextInt(numberOfPathParts - 1);
                int count = random.nextInt(numberOfPathParts - startPosition);
                count++;
                return new StringDeleteModification(startPosition, count);
            default:
                throw new IllegalStateException("Unexpected modification type: " + randomType);
        }
    }
}
