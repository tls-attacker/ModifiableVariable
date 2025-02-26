/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.bool;

import de.rub.nds.modifiablevariable.VariableModification;

public final class BooleanModificationFactory {

    private BooleanModificationFactory() {
        super();
    }

    public static VariableModification<Boolean> toggle() {
        return new BooleanToggleModification();
    }

    public static VariableModification<Boolean> explicitValue(boolean explicitValue) {
        return new BooleanExplicitValueModification(explicitValue);
    }
}
