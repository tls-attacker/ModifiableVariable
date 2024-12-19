/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.path;

public final class PathUtil {

    private PathUtil() {
        super();
    }

    public static String insertValueAsPathPart(
            String input, String insertValue, int startPosition) {
        if (input == null) {
            return null;
        }

        if (input.isEmpty()) {
            return insertValue;
        }
        String[] pathParts = input.split("/");
        boolean leadingSlash = pathParts[0].isEmpty();

        // Wrap around and also allow to insert at the end of the original value
        int insertPosition = getInsertPosition(startPosition, leadingSlash, pathParts);

        if (insertPosition == 0 && leadingSlash) {
            pathParts[insertPosition] = "/" + insertValue;
        } else if (insertPosition == pathParts.length) {
            pathParts[insertPosition - 1] = pathParts[insertPosition - 1] + "/" + insertValue;
        } else {
            pathParts[insertPosition] = insertValue + "/" + pathParts[insertPosition];
        }
        if (input.endsWith("/")) {
            pathParts[pathParts.length - 1] += "/";
        }
        return String.join("/", pathParts);
    }

    private static int getInsertPosition(
            int startPosition, boolean leadingSlash, String[] pathParts) {
        int insertPosition;
        if (leadingSlash) {
            // If the path starts with a slash, skip the first empty path part.
            insertPosition = startPosition % pathParts.length;
            if (startPosition < 0) {
                insertPosition += pathParts.length - 1;
            }
            insertPosition++;
        } else {
            insertPosition = startPosition % (pathParts.length + 1);
            if (startPosition < 0) {
                insertPosition += pathParts.length;
            }
        }
        return insertPosition;
    }

    public static int getPathPartPosition(
            int startPosition, boolean leadingSlash, String[] pathParts) {
        int pathPartPosition;
        if (leadingSlash) {
            // If the path starts with a slash, skip the first empty path part.
            pathPartPosition = startPosition % (pathParts.length - 1);
            if (startPosition < 0) {
                pathPartPosition += pathParts.length - 2;
            }
            pathPartPosition++;
        } else {
            pathPartPosition = startPosition % pathParts.length;
            if (startPosition < 0) {
                pathPartPosition += pathParts.length - 1;
            }
        }
        return pathPartPosition;
    }
}
