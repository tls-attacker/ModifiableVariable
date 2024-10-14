/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.bool;

import de.rub.nds.modifiablevariable.VariableModification;
import de.rub.nds.modifiablevariable.util.RandomHelper;
import java.util.Random;

public class BooleanModificationFactory {

    private enum ModificationType {
        EXPLICIT_TRUE, EXPLICIT_FALSE, TOGGLE
    }
    private static final int MODIFICATION_COUNT = ModificationType.values().length;

    public static VariableModification<Boolean> createRandomModification() {
        Random random = RandomHelper.getRandom();
        ModificationType randomType = ModificationType.values()[random.nextInt(MODIFICATION_COUNT)];
        switch (randomType) {
            case EXPLICIT_TRUE:
                return new BooleanExplicitValueModification(true);
            case EXPLICIT_FALSE:
                return new BooleanExplicitValueModification(false);
            case TOGGLE:
                return new BooleanToggleModification();
            default:
                throw new IllegalStateException("Unexpected modification type: " + randomType);
        }
    }

    public static VariableModification<Boolean> toggle() {
        return new BooleanToggleModification();
    }

    public static VariableModification<Boolean> explicitValue(final boolean explicitValue) {
        return new BooleanExplicitValueModification(explicitValue);
    }
}
