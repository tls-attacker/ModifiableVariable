/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable;

import de.rub.nds.modifiablevariable.biginteger.ModifiableBigInteger;
import de.rub.nds.modifiablevariable.bool.ModifiableBoolean;
import de.rub.nds.modifiablevariable.bytearray.ModifiableByteArray;
import de.rub.nds.modifiablevariable.integer.ModifiableInteger;
import de.rub.nds.modifiablevariable.longint.ModifiableLong;
import de.rub.nds.modifiablevariable.path.ModifiablePath;
import de.rub.nds.modifiablevariable.singlebyte.ModifiableByte;
import de.rub.nds.modifiablevariable.string.ModifiableString;
import java.math.BigInteger;

public final class ModifiableVariableFactory {

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

    public static ModifiableBoolean createBooleanModifiableVariable() {
        return new ModifiableBoolean();
    }

    public static ModifiableString createStringModifiableVariable() {
        return new ModifiableString();
    }

    public static ModifiablePath createPathModifiableVariable() {
        return new ModifiablePath();
    }

    public static ModifiableBigInteger safelySetValue(ModifiableBigInteger mv, BigInteger value) {
        if (mv == null) {
            mv = new ModifiableBigInteger();
        }
        mv.setOriginalValue(value);
        return mv;
    }

    /**
     * Returns only a ModifiableBigInteger with the value, if the given mv is null or the original
     * value of the mv is null
     */
    public static ModifiableBigInteger softlySetValue(ModifiableBigInteger mv, BigInteger value) {
        if (mv == null) {
            return new ModifiableBigInteger(value);
        } else if (mv.getOriginalValue() == null) {
            mv.setOriginalValue(value);
        }
        return mv;
    }

    public static ModifiableString safelySetValue(ModifiableString mv, String value) {
        if (mv == null) {
            mv = new ModifiableString();
        }
        mv.setOriginalValue(value);
        return mv;
    }

    /**
     * Returns only a ModifiableString with the value, if the given mv is null or the original value
     * of the mv is null
     */
    public static ModifiableString softlySetValue(ModifiableString mv, String value) {
        if (mv == null) {
            return new ModifiableString(value);
        } else if (mv.getOriginalValue() == null) {
            mv.setOriginalValue(value);
        }
        return mv;
    }

    public static ModifiableInteger safelySetValue(ModifiableInteger mv, Integer value) {
        if (mv == null) {
            mv = new ModifiableInteger();
        }
        mv.setOriginalValue(value);
        return mv;
    }

    /**
     * Returns only a ModifiableInteger with the value, if the given mv is null or the original
     * value of the mv is null
     */
    public static ModifiableInteger softlySetValue(ModifiableInteger mv, Integer value) {
        if (mv == null) {
            return new ModifiableInteger(value);
        } else if (mv.getOriginalValue() == null) {
            mv.setOriginalValue(value);
        }
        return mv;
    }

    public static ModifiableByte safelySetValue(ModifiableByte mv, Byte value) {
        if (mv == null) {
            mv = new ModifiableByte();
        }
        mv.setOriginalValue(value);
        return mv;
    }

    /**
     * Returns only a ModifiableByte with the value, if the given mv is null or the original value
     * of the mv is null
     */
    public static ModifiableByte softlySetValue(ModifiableByte mv, Byte value) {
        if (mv == null) {
            return new ModifiableByte(value);
        } else if (mv.getOriginalValue() == null) {
            mv.setOriginalValue(value);
        }
        return mv;
    }

    public static ModifiableByteArray safelySetValue(ModifiableByteArray mv, byte[] value) {
        if (mv == null) {
            mv = new ModifiableByteArray();
        }
        mv.setOriginalValue(value);
        return mv;
    }

    /**
     * Returns only a ModifiableByteArray with the value, if the given mv is null or the original
     * value of the mv is null
     */
    public static ModifiableByteArray softlySetValue(ModifiableByteArray mv, byte[] value) {
        if (mv == null) {
            return new ModifiableByteArray(value);
        } else if (mv.getOriginalValue() == null) {
            mv.setOriginalValue(value);
        }
        return mv;
    }

    public static ModifiableLong safelySetValue(ModifiableLong mv, Long value) {
        if (mv == null) {
            mv = new ModifiableLong();
        }
        mv.setOriginalValue(value);
        return mv;
    }

    /**
     * Returns only a ModifiableLong with the value, if the given mv is null or the original value
     * of the mv is null
     */
    public static ModifiableLong softlySetValue(ModifiableLong mv, Long value) {
        if (mv == null) {
            return new ModifiableLong(value);
        } else if (mv.getOriginalValue() == null) {
            mv.setOriginalValue(value);
        }
        return mv;
    }

    public static ModifiableBoolean safelySetValue(ModifiableBoolean mv, Boolean value) {
        if (mv == null) {
            mv = new ModifiableBoolean();
        }
        mv.setOriginalValue(value);
        return mv;
    }

    /**
     * Returns only a ModifiableBoolean with the value, if the given mv is null or the original
     * value of the mv is null
     */
    public static ModifiableBoolean softlySetValue(ModifiableBoolean mv, Boolean value) {
        if (mv == null) {
            return new ModifiableBoolean(value);
        } else if (mv.getOriginalValue() == null) {
            mv.setOriginalValue(value);
        }
        return mv;
    }

    public static ModifiablePath safelySetValue(ModifiablePath mv, String value) {
        if (mv == null) {
            mv = new ModifiablePath();
        }
        mv.setOriginalValue(value);
        return mv;
    }

    /**
     * Returns only a ModifiablePath with the value, if the given mv is null or the original value
     * of the mv is null
     */
    public static ModifiablePath softlySetValue(ModifiablePath mv, String value) {
        if (mv == null) {
            return new ModifiablePath(value);
        } else if (mv.getOriginalValue() == null) {
            mv.setOriginalValue(value);
        }
        return mv;
    }

    private ModifiableVariableFactory() {
        super();
    }
}
