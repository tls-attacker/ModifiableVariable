/**
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 *
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.modifiablevariable;

public class FileConfigurationException extends RuntimeException {

    public FileConfigurationException() {

    }

    public FileConfigurationException(Exception ex) {
        super(ex);
    }

    public FileConfigurationException(String message, Exception ex) {
        super(message, ex);
    }
}
