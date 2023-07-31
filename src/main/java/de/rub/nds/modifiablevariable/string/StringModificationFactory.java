/**
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 *
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.modifiablevariable.string;

import de.rub.nds.modifiablevariable.VariableModification;
import de.rub.nds.modifiablevariable.util.RandomHelper;

/**
 *
 */
public class StringModificationFactory {

    private static final int MAX_BYTE_LENGTH = 1000;

    public static VariableModification<String> explicitValue(final String value) {
        return new StringExplicitValueModification(value);
    }

    public static VariableModification<String> createRandomModification() {
        int i = RandomHelper.getRandom().nextInt(1000);
        byte[] randomBytes = new byte[i];
        RandomHelper.getRandom().nextBytes(randomBytes);
        return explicitValue(new String(randomBytes));
    }
}
