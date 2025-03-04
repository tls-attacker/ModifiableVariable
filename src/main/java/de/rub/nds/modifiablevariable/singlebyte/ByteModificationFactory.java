/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.singlebyte;

import de.rub.nds.modifiablevariable.VariableModification;

public final class ByteModificationFactory {

    public static ByteAddModification add(String summand) {
        return add(Byte.parseByte(summand));
    }

    public static ByteAddModification add(Byte summand) {
        return new ByteAddModification(summand);
    }

    public static VariableModification<Byte> sub(String subtrahend) {
        return sub(Byte.parseByte(subtrahend));
    }

    public static VariableModification<Byte> sub(Byte subtrahend) {
        return new ByteSubtractModification(subtrahend);
    }

    public static VariableModification<Byte> xor(String xor) {
        return xor(Byte.parseByte(xor));
    }

    public static VariableModification<Byte> xor(Byte xor) {
        return new ByteXorModification(xor);
    }

    public static VariableModification<Byte> explicitValue(String value) {
        return explicitValue(Byte.parseByte(value));
    }

    public static VariableModification<Byte> explicitValue(Byte value) {
        return new ByteExplicitValueModification(value);
    }

    private ByteModificationFactory() {
        super();
    }
}
