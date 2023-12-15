/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable;

public class FileConfigurationException extends RuntimeException {

    public FileConfigurationException() {}

    public FileConfigurationException(Exception ex) {
        super(ex);
    }

    public FileConfigurationException(String message, Exception ex) {
        super(message, ex);
    }
}
