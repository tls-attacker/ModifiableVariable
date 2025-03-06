/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable;

/**
 * Exception thrown when there is a problem with file configuration operations.
 *
 * <p>This exception is typically thrown when there are issues with loading, saving, or processing
 * configuration files related to modifiable variables.
 */
public class FileConfigurationException extends RuntimeException {

    /** Constructs a new FileConfigurationException with no message or cause. */
    public FileConfigurationException() {
        super();
    }

    /**
     * Constructs a new FileConfigurationException with the specified cause.
     *
     * @param ex The cause of this exception
     */
    public FileConfigurationException(Exception ex) {
        super(ex);
    }

    /**
     * Constructs a new FileConfigurationException with the specified message and cause.
     *
     * @param message A description of the exception
     * @param ex The cause of this exception
     */
    public FileConfigurationException(String message, Exception ex) {
        super(message, ex);
    }
}
