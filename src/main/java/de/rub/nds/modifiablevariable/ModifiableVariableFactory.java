/**
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Copyright 2014-2017 Ruhr University Bochum / Hackmanit GmbH
 *
 * Licensed under Apache License 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable;

import de.rub.nds.modifiablevariable.biginteger.ModifiableBigInteger;
import de.rub.nds.modifiablevariable.bool.ModifiableBoolean;
import de.rub.nds.modifiablevariable.bytearray.ModifiableByteArray;
import de.rub.nds.modifiablevariable.integer.ModifiableInteger;
import de.rub.nds.modifiablevariable.mlong.ModifiableLong;
import de.rub.nds.modifiablevariable.singlebyte.ModifiableByte;
import de.rub.nds.modifiablevariable.string.ModifiableString;
import java.math.BigInteger;

public class ModifiableVariableFactory {

    public static ModifiableBigInteger createBigIntegerModifiableVariable() {
        return new ModifiableBigInteger();
    }

    public static ModifiableInteger createIntegerModifiableVariable() {
        return new ModifiableInteger();
    }

    public static ModifiableByte createByteModifiableVariable() {
        return new ModifiableByte();
    }

    public static ModifiableByteArray createByteArrayModifiableVariable() {
        return new ModifiableByteArray();
    }

    public static ModifiableLong createLongModifiableVariable() {
        return new ModifiableLong();
    }

    public static ModifiableBigInteger safelySetValue(ModifiableBigInteger mv, BigInteger value) {
        if (mv == null) {
            mv = new ModifiableBigInteger();
        }
        mv.setOriginalValue(value);
        return mv;
    }

    public static ModifiableString safelySetValue(ModifiableString mv, String value) {
        if (mv == null) {
            mv = new ModifiableString();
        }
        mv.setOriginalValue(value);
        return mv;
    }

    public static ModifiableInteger safelySetValue(ModifiableInteger mv, Integer value) {
        if (mv == null) {
            mv = new ModifiableInteger();
        }
        mv.setOriginalValue(value);
        return mv;
    }

    public static ModifiableByte safelySetValue(ModifiableByte mv, Byte value) {
        if (mv == null) {
            mv = new ModifiableByte();
        }
        mv.setOriginalValue(value);
        return mv;
    }

    public static ModifiableByteArray safelySetValue(ModifiableByteArray mv, byte[] value) {
        if (mv == null) {
            mv = new ModifiableByteArray();
        }
        mv.setOriginalValue(value);
        return mv;
    }

    public static ModifiableLong safelySetValue(ModifiableLong mv, Long value) {
        if (mv == null) {
            mv = new ModifiableLong();
        }
        mv.setOriginalValue(value);
        return mv;
    }

    public static ModifiableBoolean safelySetValue(ModifiableBoolean mv, Boolean value) {
        if (mv == null) {
            mv = new ModifiableBoolean();
        }
        mv.setOriginalValue(value);
        return mv;
    }

    private ModifiableVariableFactory() {

    }
}
