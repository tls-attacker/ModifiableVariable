/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.Property;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/** Tests for the {@link VariableModification} abstract class. */
public class VariableModificationTest {

    private TestAppender testAppender;
    private Logger logger;

    /** Custom appender to capture log messages */
    private static class TestAppender extends AbstractAppender {
        private final List<LogEvent> logEvents = new ArrayList<>();

        protected TestAppender() {
            super(
                    "TestAppender",
                    null,
                    PatternLayout.createDefaultLayout(),
                    true,
                    Property.EMPTY_ARRAY);
        }

        @Override
        public void append(LogEvent event) {
            logEvents.add(event.toImmutable());
        }

        public List<LogEvent> getLogEvents() {
            return new ArrayList<>(logEvents);
        }

        public void clear() {
            logEvents.clear();
        }
    }

    @BeforeEach
    public void setUp() {
        // Set up logger with a test appender to capture log events
        logger =
                (org.apache.logging.log4j.core.Logger)
                        LogManager.getLogger(VariableModification.class);
        testAppender = new TestAppender();
        testAppender.start();
        logger.addAppender(testAppender);
        logger.setLevel(Level.DEBUG);
    }

    @AfterEach
    public void tearDown() {
        logger.removeAppender(testAppender);
        testAppender.stop();
    }

    /** Test implementation of {@link VariableModification} for testing. */
    private static class TestModification extends VariableModification<String> {
        @Override
        protected String modifyImplementationHook(String input) {
            if (input == null) {
                return null;
            }
            return input + "-modified";
        }

        @Override
        public VariableModification<String> createCopy() {
            return new TestModification();
        }
    }

    /** Test implementation that returns null even for non-null inputs. */
    private static class NullReturningModification extends VariableModification<String> {
        @Override
        protected String modifyImplementationHook(String input) {
            return null;
        }

        @Override
        public VariableModification<String> createCopy() {
            return new NullReturningModification();
        }
    }

    /** Test implementation that works with byte arrays. */
    private static class ByteArrayModification extends VariableModification<byte[]> {
        @Override
        protected byte[] modifyImplementationHook(byte[] input) {
            if (input == null) {
                return null;
            }
            // Add 1 to each byte
            byte[] result = new byte[input.length];
            for (int i = 0; i < input.length; i++) {
                result[i] = (byte) (input[i] + 1);
            }
            return result;
        }

        @Override
        public VariableModification<byte[]> createCopy() {
            return new ByteArrayModification();
        }
    }

    @Test
    public void testModifyWithNonNullInput() {
        VariableModification<String> modification = new TestModification();
        String input = "test";
        String result = modification.modify(input);

        assertEquals("test-modified", result);
    }

    @Test
    public void testModifyWithNullInput() {
        VariableModification<String> modification = new TestModification();
        String result = modification.modify(null);

        assertNull(result);
    }

    @Test
    public void testModifyReturningNull() {
        VariableModification<String> modification = new NullReturningModification();
        String result = modification.modify("any input");

        assertNull(result);
    }

    @Test
    public void testDebugLoggingWithNonNullValue() {
        VariableModification<String> modification = new TestModification();
        modification.modify("test");

        List<LogEvent> logEvents = testAppender.getLogEvents();
        assertFalse(logEvents.isEmpty(), "Should have logged a debug message");
        LogEvent lastLog = logEvents.get(logEvents.size() - 1);
        assertEquals(Level.DEBUG, lastLog.getLevel());
        assertTrue(
                lastLog.getMessage().getFormattedMessage().contains("New value: test-modified"),
                "Log should contain the modified value");
        assertTrue(
                lastLog.getMessage().getFormattedMessage().contains("TestModification"),
                "Log should contain the class name");
    }

    @Test
    public void testDebugLoggingWithNullValue() {
        VariableModification<String> modification = new NullReturningModification();
        modification.modify("will return null");

        List<LogEvent> logEvents = testAppender.getLogEvents();
        assertFalse(logEvents.isEmpty(), "Should have logged a debug message");
        LogEvent lastLog = logEvents.get(logEvents.size() - 1);
        assertEquals(Level.DEBUG, lastLog.getLevel());
        assertTrue(
                lastLog.getMessage().getFormattedMessage().contains("New value is unset"),
                "Log should indicate null value");
        assertTrue(
                lastLog.getMessage().getFormattedMessage().contains("NullReturningModification"),
                "Log should contain the class name");
    }

    @Test
    public void testModifyByteArray() {
        VariableModification<byte[]> modification = new ByteArrayModification();
        byte[] input = new byte[] {1, 2, 3};
        byte[] result = modification.modify(input);

        assertArrayEquals(new byte[] {2, 3, 4}, result);

        // Verify input array is not modified (immutability)
        assertArrayEquals(new byte[] {1, 2, 3}, input);
    }

    @Test
    public void testCreateCopy() {
        VariableModification<String> original = new TestModification();
        VariableModification<String> copy = original.createCopy();

        assertNotSame(original, copy, "Copy should be a new instance");
        assertEquals(original.getClass(), copy.getClass(), "Copy should be the same class");

        // Verify the copy works the same as the original
        assertEquals(original.modify("test"), copy.modify("test"));
    }

    @Test
    public void testDebugLoggingWithStringValue() {
        VariableModification<String> modification = new TestModification();
        modification.modify("test\nwith\tspecial\"chars");

        List<LogEvent> logEvents = testAppender.getLogEvents();
        assertFalse(logEvents.isEmpty(), "Should have logged a debug message");
        LogEvent lastLog = logEvents.get(logEvents.size() - 1);
        String message = lastLog.getMessage().getFormattedMessage();

        // Just verify the log contains the right class name and includes a new value message
        assertTrue(message.contains("TestModification"), "Log should contain class name");
        assertTrue(message.contains("New value:"), "Log should mention a new value");
    }
}
