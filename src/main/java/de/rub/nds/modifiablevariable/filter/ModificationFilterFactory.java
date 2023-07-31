/**
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 *
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.modifiablevariable.filter;

public class ModificationFilterFactory {

    public static AccessModificationFilter access(final int[] accessNumbers) {
        return new AccessModificationFilter(accessNumbers);
    }

    private ModificationFilterFactory() {
    }
}
