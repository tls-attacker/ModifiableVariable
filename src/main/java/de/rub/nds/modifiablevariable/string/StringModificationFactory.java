/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.string;

import de.rub.nds.modifiablevariable.VariableModification;

public final class StringModificationFactory {

    private StringModificationFactory() {
        super();
    }

    public static VariableModification<String> prependValue(String value) {
        return new StringPrependValueModification(value);
    }

    public static VariableModification<String> appendValue(String value) {
        return new StringAppendValueModification(value);
    }

    public static VariableModification<String> explicitValue(String value) {
        return new StringExplicitValueModification(value);
    }

    public static VariableModification<String> insertValue(String value, int position) {
        return new StringInsertValueModification(value, position);
    }

    public static VariableModification<String> delete(int startPosition, int count) {
        return new StringDeleteModification(startPosition, count);
    }
}
