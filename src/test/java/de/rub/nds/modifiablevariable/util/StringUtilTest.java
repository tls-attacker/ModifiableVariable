/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Comprehensive test class for StringUtil.
 *
 * <p>Tests all methods and edge cases in the StringUtil class.
 */
class StringUtilTest {

    /** Test backslashEscapeString with null input. */
    @Test
    void testBackslashEscapeStringWithNull() {
        assertNull(StringUtil.backslashEscapeString(null), "Null input should return null");
    }

    /** Test backslashEscapeString with empty string. */
    @Test
    void testBackslashEscapeStringWithEmptyString() {
        assertEquals("", StringUtil.backslashEscapeString(""), "Empty string should remain empty");
    }

    /**
     * Test backslashEscapeString with plain ASCII text. This should not change the input string as
     * all characters are printable ASCII.
     */
    @ParameterizedTest
    @ValueSource(
            strings = {
                "hello world, this is some plain text!",
                "ABCDEFGHIJKLMNOPQRSTUVWXYZ",
                "abcdefghijklmnopqrstuvwxyz",
                "0123456789",
                "!@#$%^&*()_+-={}[]|:;'<>,.?/~`"
            })
    void testBackslashEscapeStringWithPlainText(String input) {
        assertEquals(
                input,
                StringUtil.backslashEscapeString(input),
                "Plain ASCII text should not be modified");
    }

    /** Test backslashEscapeString with common escape characters. */
    @Test
    void testBackslashEscapeStringWithEscapeChars() {
        // Test backspace character
        assertEquals(
                "a\\b",
                StringUtil.backslashEscapeString("a\b"),
                "Backspace should be properly escaped");

        // Test common escape sequences
        assertEquals(
                "\\r\\n\\t\\f\\b",
                StringUtil.backslashEscapeString("\r\n\t\f\b"),
                "Common escape sequences should be properly escaped");

        // Test newline
        assertEquals(
                "String with line-\\nbreak",
                StringUtil.backslashEscapeString("String with line-\nbreak"),
                "Newline should be properly escaped");

        // Test tab
        assertEquals(
                "String with tab\\tstop",
                StringUtil.backslashEscapeString("String with tab\tstop"),
                "Tab should be properly escaped");

        // Test backslash
        assertEquals(
                "String with backslash \\\\",
                StringUtil.backslashEscapeString("String with backslash \\"),
                "Backslash should be properly escaped");
    }

    /** Test backslashEscapeString with non-ASCII and control characters. */
    @Test
    void testBackslashEscapeStringWithNonAsciiAndControlChars() {
        // Test string with various control characters and non-ASCII characters
        assertEquals(
                "Null byte \\u0000 and bell \\u0007",
                StringUtil.backslashEscapeString("Null byte \u0000 and bell \u0007"),
                "Control characters should be properly escaped");

        assertEquals(
                "Non-ASCII chars: \\u00E4\\u00F6\\u00FC\\u00DF",
                StringUtil.backslashEscapeString("Non-ASCII chars: äöüß"),
                "Non-ASCII characters should be properly escaped");
    }

    /** Test backslashEscapeString with supplementary characters (characters outside BMP). */
    @Test
    void testBackslashEscapeStringWithSupplementaryChars() {
        // Test string with emoji and other supplementary characters
        assertEquals(
                "Emoji: \\uD83D\\uDE00 \\uD83D\\uDE04 \\uD83D\\uDE2D",
                StringUtil.backslashEscapeString("Emoji: 😀 😄 😭"),
                "Supplementary characters should be escaped as surrogate pairs");

        // Mathematical symbols from supplementary planes
        assertEquals(
                "Math: \\uD835\\uDC00 \\uD835\\uDC01 \\uD835\\uDC02",
                StringUtil.backslashEscapeString("Math: 𝐀 𝐁 𝐂"),
                "Mathematical symbols should be escaped as surrogate pairs");
    }

    /**
     * Test backslashEscapeString with a complex mixed string containing various character types.
     */
    @Test
    void testBackslashEscapeStringWithComplexString() {
        String input =
                "ASCII with control chars \n\t\r\f\b\\ and non-ASCII äöü and emoji 👨‍👩‍👧‍👦";
        String expected =
                "ASCII with control chars \\n\\t\\r\\f\\b\\\\ and non-ASCII \\u00E4\\u00F6\\u00FC and emoji \\uD83D\\uDC68\\u200D\\uD83D\\uDC69\\u200D\\uD83D\\uDC67\\u200D\\uD83D\\uDC66";

        assertEquals(
                expected,
                StringUtil.backslashEscapeString(input),
                "Complex strings with mixed character types should be properly escaped");
    }

    /** Test backslashEscapeString with real-world examples from previous test. */
    @Test
    void testBackslashEscapeStringWithExistingExamples() {
        // Existing test cases for reference and regression
        assertEquals(
                "String with line-\\nbreak, tab\\tstop and backslash \\\\",
                StringUtil.backslashEscapeString(
                        "String with line-\nbreak, tab\tstop and backslash \\"));

        assertEquals(
                "Null byte \\u0000 and some emojis \\uD83D\\uDC41\\uD83D\\uDC44\\uD83D\\uDC41\\u2615\\uFE0F\\uD83D\\uDC4C",
                StringUtil.backslashEscapeString("Null byte \u0000 and some emojis 👁👄👁☕️👌"));

        assertEquals(
                "\\u0007\\u001A\\u001F\\u00DCf\\u00FD\\tu\\u0011\\u001D\\\\\\u00A2\\u0086T\\u009D\\u00AC\\u001F5\\u00E1\\u00AD",
                StringUtil.backslashEscapeString(
                        "\u0007\u001a\u001fÜfý\tu\u0011\u001d\\¢\u0086T\u009d¬\u001f5á\u00ad"));
    }

    /**
     * Test behavior with the highest valid Unicode code point. This verifies our code can handle
     * edge cases at the boundary of valid code points.
     */
    @Test
    void testBackslashEscapeStringWithMaximumValidCodePoint() {
        // Create a StringBuilder with the maximum valid code point
        StringBuilder input = new StringBuilder("Test");
        input.appendCodePoint(Character.MAX_CODE_POINT);

        String result = StringUtil.backslashEscapeString(input.toString());

        // The maximum code point should be properly escaped
        assertTrue(
                result.contains("\\uDBFF\\uDFFF"),
                "Maximum valid code point should be properly escaped");
    }
}
