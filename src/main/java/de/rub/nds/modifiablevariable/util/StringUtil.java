/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.util;

public final class StringUtil {
    // Private constructor, because this is a utility class that is not meant
    // to be instantiated.
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
                        // FIXME: This theoretically includes codePoints >=
                        // Character.MAX_CODE_POINT. Unsure if this is actually
                        // possible and if we need to handle that case in a
                        // special way.
                        replacement = String.format("\\u%04X", codePoint);
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
