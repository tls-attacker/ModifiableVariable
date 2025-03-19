/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.util;

/**
 * Utility class for string manipulation operations.
 *
 * <p>This class provides utility methods for handling strings in ways that are commonly needed
 * across the ModifiableVariable framework, particularly for formatting and escaping strings for
 * logging or display purposes.
 *
 * <p>The primary function is to handle non-printable or non-ASCII characters by converting them to
 * their escape sequence equivalents, ensuring that all string content can be safely represented in
 * logs, debug output, or other text-based contexts.
 */
public final class StringUtil {
    /** Private constructor to prevent instantiation of this utility class. */
    private StringUtil() {
        super();
    }

    /**
     * Replace any non-printable (or non-ascii) characters other than space with their
     * backslash-escaped equivalents.
     *
     * @param value string that may contain non-printable or non-ascii chars
     * @return string with non-printable or non-ascii characters replaced
     */
    public static String backslashEscapeString(String value) {
        if (value == null) {
            return null;
        }
        StringBuilder builder = new StringBuilder(value);
        for (int i = 0; i < builder.length(); i++) {
            int codePoint = builder.codePointAt(i);
            String replacement;
            int numCodePoints = 1;
            switch (codePoint) {
                case (int) '\r':
                    replacement = "\\r";
                    break;
                case (int) '\n':
                    replacement = "\\n";
                    break;
                case (int) '\t':
                    replacement = "\\t";
                    break;
                case (int) '\f':
                    replacement = "\\f";
                    break;
                case (int) '\b':
                    replacement = "\\b";
                    break;
                case (int) '\\':
                    replacement = "\\\\";
                    break;
                default:
                    if (Character.isSupplementaryCodePoint(codePoint)) {
                        // These characters consist of more than two bytes and
                        // thus require UTF-16 surrogate pairs to display
                        // properly.
                        replacement =
                                String.format(
                                        "\\u%04X\\u%04X",
                                        (int) Character.highSurrogate(codePoint),
                                        (int) Character.lowSurrogate(codePoint));
                        numCodePoints = 2;
                    } else if (codePoint < 0x20 || codePoint > 0x7F) {
                        // Validate that the code point is valid (between 0 and
                        // Character.MAX_CODE_POINT)
                        if (codePoint > Character.MAX_CODE_POINT) {
                            // If an invalid code point is encountered, represent it as replacement
                            // character
                            replacement = "\\uFFFD"; // Unicode replacement character
                        } else {
                            replacement = String.format("\\u%04X", codePoint);
                        }
                    } else {
                        continue;
                    }
            }

            builder.replace(i, i + numCodePoints, replacement);
            i += replacement.length() - 1;
        }

        return builder.toString();
    }
}
