/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class StringUtilTest {

    @Test
    public void testBackslashEscapeString() {
        final String plainText = "hello world, this is some plain text!";
        assertEquals(plainText, StringUtil.backslashEscapeString(plainText));

        assertEquals(
                "String with line-\\nbreak, tab\\tstop and backslash \\\\",
                StringUtil.backslashEscapeString(
                        "String with line-\nbreak, tab\tstop and backslash \\"));
        // This string includes some emojis in the supplementary character range that require two
        // unicode escapes.
        assertEquals(
                "Null byte \\u0000 and some emojis \\uD83D\\uDC41\\uD83D\\uDC44\\uD83D\\uDC41\\u2615\\uFE0F\\uD83D\\uDC4C",
                StringUtil.backslashEscapeString("Null byte \u0000 and some emojis 👁👄👁☕️👌"));

        // Escape some random bytes read from /dev/urandom.
        assertEquals(
                "\\u0007\\u001A\\u001F\\u00DCf\\u00FD\\tu\\u0011\\u001D\\\\\\u00A2\\u0086T\\u009D\\u00AC\\u001F5\\u00E1\\u00AD",
                StringUtil.backslashEscapeString(
                        "\u0007\u001a\u001fÜfý\tu\u0011\u001d\\¢\u0086T\u009d¬\u001f5á\u00ad"));
    }
}
