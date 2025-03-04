/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

public class FileConfigurationExceptionTest {

    @Test
    public void testDefaultConstructor() {
        FileConfigurationException exception = new FileConfigurationException();

        assertNull(exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    public void testConstructorWithException() {
        IllegalArgumentException cause = new IllegalArgumentException("Test cause");
        FileConfigurationException exception = new FileConfigurationException(cause);

        assertNotNull(exception.getMessage());
        assertSame(cause, exception.getCause());
    }

    @Test
    public void testConstructorWithMessageAndException() {
        String message = "Test error message";
        IllegalArgumentException cause = new IllegalArgumentException("Test cause");
        FileConfigurationException exception = new FileConfigurationException(message, cause);

        assertEquals(message, exception.getMessage());
        assertSame(cause, exception.getCause());
    }
}
