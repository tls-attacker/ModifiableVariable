/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.bool;

import de.rub.nds.modifiablevariable.VariableModification;
import de.rub.nds.modifiablevariable.util.RandomHelper;
import java.util.Random;

public class BooleanModificationFactory {

    private static final int MODIFICATION_COUNT = 3;

    public static VariableModification<Boolean> createRandomModification() {
        Random random = RandomHelper.getRandom();
        switch (random.nextInt(MODIFICATION_COUNT)) {
            case 0:
                return new BooleanExplicitValueModification(true);
            case 1:
                return new BooleanExplicitValueModification(false);
            case 2:
                return new BooleanToggleModification();
            default:
                return null;
        }
    }

    public static VariableModification<Boolean> toggle() {
        return new BooleanToggleModification();
    }

    public static VariableModification<Boolean> explicitValue(final boolean explicitValue) {
        return new BooleanExplicitValueModification(explicitValue);
    }
}
