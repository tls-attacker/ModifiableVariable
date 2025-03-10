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
import de.rub.nds.modifiablevariable.singlebyte.ModifiableByte;
import de.rub.nds.modifiablevariable.string.ModifiableString;
import java.math.BigInteger;

/**
 * Factory class for creating modifiable variables of different types.
 *
 * <p>This factory provides methods to create empty modifiable variables and safely set values on
 * existing or new modifiable variables. The factory ensures that null modifiable variables are
 * properly initialized when setting values.
 */
public final class ModifiableVariableFactory {

    /**
     * Creates a new empty ModifiableBigInteger.
     *
     * @return A new ModifiableBigInteger instance with no original value
     */
    public static ModifiableBigInteger createBigIntegerModifiableVariable() {
        return new ModifiableBigInteger();
    }

    /**
     * Creates a new empty ModifiableInteger.
     *
     * @return A new ModifiableInteger instance with no original value
     */
    public static ModifiableInteger createIntegerModifiableVariable() {
        return new ModifiableInteger();
    }

    /**
     * Creates a new empty ModifiableByte.
     *
     * @return A new ModifiableByte instance with no original value
     */
    public static ModifiableByte createByteModifiableVariable() {
        return new ModifiableByte();
    }

    /**
     * Creates a new empty ModifiableByteArray.
     *
     * @return A new ModifiableByteArray instance with no original value
     */
    public static ModifiableByteArray createByteArrayModifiableVariable() {
        return new ModifiableByteArray();
    }

    /**
     * Creates a new empty ModifiableLong.
     *
     * @return A new ModifiableLong instance with no original value
     */
    public static ModifiableLong createLongModifiableVariable() {
        return new ModifiableLong();
    }

    /**
     * Creates a new empty ModifiableBoolean.
     *
     * @return A new ModifiableBoolean instance with no original value
     */
    public static ModifiableBoolean createBooleanModifiableVariable() {
        return new ModifiableBoolean();
    }

    /**
     * Creates a new empty ModifiableString.
     *
     * @return A new ModifiableString instance with no original value
     */
    public static ModifiableString createStringModifiableVariable() {
        return new ModifiableString();
    }

    /**
     * Safely sets a value on a ModifiableBigInteger, creating a new instance if necessary.
     *
     * @param mv The ModifiableBigInteger to set the value on, or null to create a new one
     * @param value The BigInteger value to set
     * @return The ModifiableBigInteger with the value set
     */
    public static ModifiableBigInteger safelySetValue(ModifiableBigInteger mv, BigInteger value) {
        if (mv == null) {
            return new ModifiableBigInteger(value);
        }
        mv.setOriginalValue(value);
        return mv;
    }

    /**
     * Safely sets a value on a ModifiableString, creating a new instance if necessary.
     *
     * @param mv The ModifiableString to set the value on, or null to create a new one
     * @param value The String value to set
     * @return The ModifiableString with the value set
     */
    public static ModifiableString safelySetValue(ModifiableString mv, String value) {
        if (mv == null) {
            return new ModifiableString(value);
        }
        mv.setOriginalValue(value);
        return mv;
    }

    /**
     * Safely sets a value on a ModifiableInteger, creating a new instance if necessary.
     *
     * @param mv The ModifiableInteger to set the value on, or null to create a new one
     * @param value The Integer value to set
     * @return The ModifiableInteger with the value set
     */
    public static ModifiableInteger safelySetValue(ModifiableInteger mv, Integer value) {
        if (mv == null) {
            return new ModifiableInteger(value);
        }
        mv.setOriginalValue(value);
        return mv;
    }

    /**
     * Safely sets a value on a ModifiableByte, creating a new instance if necessary.
     *
     * @param mv The ModifiableByte to set the value on, or null to create a new one
     * @param value The Byte value to set
     * @return The ModifiableByte with the value set
     */
    public static ModifiableByte safelySetValue(ModifiableByte mv, Byte value) {
        if (mv == null) {
            return new ModifiableByte(value);
        }
        mv.setOriginalValue(value);
        return mv;
    }

    /**
     * Safely sets a value on a ModifiableByteArray, creating a new instance if necessary.
     *
     * @param mv The ModifiableByteArray to set the value on, or null to create a new one
     * @param value The byte array value to set
     * @return The ModifiableByteArray with the value set
     */
    public static ModifiableByteArray safelySetValue(ModifiableByteArray mv, byte[] value) {
        if (mv == null) {
            return new ModifiableByteArray(value);
        }
        mv.setOriginalValue(value);
        return mv;
    }

    /**
     * Safely sets a value on a ModifiableLong, creating a new instance if necessary.
     *
     * @param mv The ModifiableLong to set the value on, or null to create a new one
     * @param value The Long value to set
     * @return The ModifiableLong with the value set
     */
    public static ModifiableLong safelySetValue(ModifiableLong mv, Long value) {
        if (mv == null) {
            return new ModifiableLong(value);
        }
        mv.setOriginalValue(value);
        return mv;
    }

    /**
     * Safely sets a value on a ModifiableBoolean, creating a new instance if necessary.
     *
     * @param mv The ModifiableBoolean to set the value on, or null to create a new one
     * @param value The Boolean value to set
     * @return The ModifiableBoolean with the value set
     */
    public static ModifiableBoolean safelySetValue(ModifiableBoolean mv, Boolean value) {
        if (mv == null) {
            return new ModifiableBoolean(value);
        }
        mv.setOriginalValue(value);
        return mv;
    }

    /** Private constructor to prevent instantiation of utility class. */
    private ModifiableVariableFactory() {
        super();
    }
}
