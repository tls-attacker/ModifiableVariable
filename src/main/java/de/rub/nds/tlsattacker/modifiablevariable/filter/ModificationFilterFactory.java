/**
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Copyright 2014-2017 Ruhr University Bochum / Hackmanit GmbH
 *
 * Licensed under Apache License 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.tlsattacker.modifiablevariable.filter;

public class ModificationFilterFactory {

    public static AccessModificationFilter access(final int[] accessNumbers) {
        return new AccessModificationFilter(accessNumbers);
    }

    private ModificationFilterFactory() {
    }
}
