/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.util;

public class StringUtil {
    // Private constructor, because this is a utility class that is not meant
    // to be instantiated.
    private StringUtil() {}

    static final int HI_SURROGATE_START = 0xD800;

    /**
     * Replace any non-printable (or non-ascii) characters other than space with their
     * backslash-escaped equivalents.
     *
     * @param value string that may contain non-printable or non-ascii chars
     * @return string with non-printable or non-ascii characters replaced
     */
    public static String backslashEscapeString(final String value) {
        final StringBuffer buffer = new StringBuffer(value);
        for (int i = 0; i < buffer.length(); i++) {
            final int codePoint = buffer.codePointAt(i);
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

            buffer.replace(i, i + numCodePoints, replacement);
            i += replacement.length() - 1;
        }

        return buffer.toString();
    }
}
