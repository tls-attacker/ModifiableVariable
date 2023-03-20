/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Copyright 2014-2022 Ruhr University Bochum, Paderborn University, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.string;

import de.rub.nds.modifiablevariable.VariableModification;
import de.rub.nds.modifiablevariable.util.RandomHelper;

/** */
public class StringModificationFactory {

    private static final int MAX_BYTE_LENGTH = 1000;

    public static VariableModification<String> prependValue(final String value) {
        return new StringPrependValueModification(value);
    }

    public static VariableModification<String> appendValue(final String value) {
        return new StringAppendValueModification(value);
    }

    public static VariableModification<String> explicitValue(final String value) {
        return new StringExplicitValueModification(value);
    }

    public static VariableModification<String> createRandomModification() {
        int i = RandomHelper.getRandom().nextInt(MAX_BYTE_LENGTH);
        byte[] randomBytes = new byte[i];
        RandomHelper.getRandom().nextBytes(randomBytes);
        return explicitValue(new String(randomBytes));
    }
}
