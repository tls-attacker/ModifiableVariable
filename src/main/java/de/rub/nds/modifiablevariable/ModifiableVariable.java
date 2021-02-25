/**
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Copyright 2014-2021 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 *
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.modifiablevariable;

import de.rub.nds.modifiablevariable.biginteger.BigIntegerAddModification;
import de.rub.nds.modifiablevariable.biginteger.BigIntegerExplicitValueModification;
import de.rub.nds.modifiablevariable.biginteger.BigIntegerInteractiveModification;
import de.rub.nds.modifiablevariable.biginteger.BigIntegerMultiplyModification;
import de.rub.nds.modifiablevariable.biginteger.BigIntegerShiftLeftModification;
import de.rub.nds.modifiablevariable.biginteger.BigIntegerShiftRightModification;
import de.rub.nds.modifiablevariable.biginteger.BigIntegerSubtractModification;
import de.rub.nds.modifiablevariable.biginteger.BigIntegerXorModification;
import de.rub.nds.modifiablevariable.bool.BooleanExplicitValueModification;
import de.rub.nds.modifiablevariable.bool.BooleanToggleModification;
import de.rub.nds.modifiablevariable.bytearray.ByteArrayDeleteModification;
import de.rub.nds.modifiablevariable.bytearray.ByteArrayDuplicateModification;
import de.rub.nds.modifiablevariable.bytearray.ByteArrayExplicitValueModification;
import de.rub.nds.modifiablevariable.bytearray.ByteArrayInsertModification;
import de.rub.nds.modifiablevariable.bytearray.ByteArrayPayloadModification;
import de.rub.nds.modifiablevariable.bytearray.ByteArrayShuffleModification;
import de.rub.nds.modifiablevariable.bytearray.ByteArrayXorModification;
import de.rub.nds.modifiablevariable.integer.IntegerAddModification;
import de.rub.nds.modifiablevariable.integer.IntegerExplicitValueModification;
import de.rub.nds.modifiablevariable.integer.IntegerShiftLeftModification;
import de.rub.nds.modifiablevariable.integer.IntegerShiftRightModification;
import de.rub.nds.modifiablevariable.integer.IntegerSubtractModification;
import de.rub.nds.modifiablevariable.integer.IntegerXorModification;
import de.rub.nds.modifiablevariable.mlong.LongAddModification;
import de.rub.nds.modifiablevariable.mlong.LongExplicitValueModification;
import de.rub.nds.modifiablevariable.mlong.LongSubtractModification;
import de.rub.nds.modifiablevariable.mlong.LongXorModification;
import de.rub.nds.modifiablevariable.singlebyte.ByteAddModification;
import de.rub.nds.modifiablevariable.singlebyte.ByteExplicitValueModification;
import de.rub.nds.modifiablevariable.singlebyte.ByteSubtractModification;
import de.rub.nds.modifiablevariable.singlebyte.ByteXorModification;
import de.rub.nds.modifiablevariable.string.StringExplicitValueModification;
import de.rub.nds.modifiablevariable.util.UnformattedByteArrayAdapter;
import java.io.Serializable;
import java.util.Objects;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * The base abstract class for modifiable variables, including the getValue function.The class needs to be defined
 * transient to allow propOrder definition in subclasses, see:
 * http://blog.bdoughan.com/2011/06/ignoring-inheritance-with-xmltransient.html
 *
 *
 * @param <E>
 */
@XmlRootElement
@XmlTransient
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class ModifiableVariable<E> implements Serializable {

    @XmlElements(value = { @XmlElement(type = BigIntegerXorModification.class, name = "BigIntegerXorModification"),
        @XmlElement(type = BigIntegerSubtractModification.class, name = "BigIntegerSubtractModification"),
        @XmlElement(type = BigIntegerShiftRightModification.class, name = "BigIntegerShiftRightModification"),
        @XmlElement(type = BigIntegerShiftLeftModification.class, name = "BigIntegerShiftLeftModification"),
        @XmlElement(type = BigIntegerExplicitValueModification.class, name = "BigIntegerExplicitValueModification"),
        @XmlElement(type = BigIntegerAddModification.class, name = "BigIntegerAddModification"),
        @XmlElement(type = BigIntegerInteractiveModification.class, name = "BigIntegerInteractiveModification"),
        @XmlElement(type = BigIntegerMultiplyModification.class, name = "BigIntegerMultiplyModification"),
        @XmlElement(type = BooleanToggleModification.class, name = "BooleanToggleModification"),
        @XmlElement(type = BooleanExplicitValueModification.class, name = "BooleanExplicitValueModification"),
        @XmlElement(type = ByteArrayXorModification.class, name = "ByteArrayXorModification"),
        @XmlElement(type = ByteArrayShuffleModification.class, name = "ByteArrayShuffleModification"),
        @XmlElement(type = ByteArrayPayloadModification.class, name = "ByteArrayPayloadModification"),
        @XmlElement(type = ByteArrayInsertModification.class, name = "ByteArrayInsertModification"),
        @XmlElement(type = ByteArrayExplicitValueModification.class, name = "ByteArrayExplicitValueModification"),
        @XmlElement(type = ByteArrayDuplicateModification.class, name = "ByteArrayDuplicateModification"),
        @XmlElement(type = ByteArrayDeleteModification.class, name = "ByteArrayDeleteModification"),
        @XmlElement(type = IntegerXorModification.class, name = "IntegerXorModification"),
        @XmlElement(type = IntegerSubtractModification.class, name = "IntegerSubtractModification"),
        @XmlElement(type = IntegerShiftRightModification.class, name = "IntegerShiftRightModification"),
        @XmlElement(type = IntegerShiftLeftModification.class, name = "IntegerShiftLeftModification"),
        @XmlElement(type = IntegerExplicitValueModification.class, name = "IntegerExplicitValueModification"),
        @XmlElement(type = IntegerAddModification.class, name = "IntegerAddModification"),
        @XmlElement(type = LongXorModification.class, name = "LongXorModification"),
        @XmlElement(type = LongSubtractModification.class, name = "LongSubtractModification"),
        @XmlElement(type = LongExplicitValueModification.class, name = "LongExplicitValueModification"),
        @XmlElement(type = LongAddModification.class, name = "LongAddModification"),
        @XmlElement(type = ByteXorModification.class, name = "ByteXorModification"),
        @XmlElement(type = ByteSubtractModification.class, name = "ByteSubtractModification"),
        @XmlElement(type = ByteAddModification.class, name = "ByteAddModification"),
        @XmlElement(type = ByteExplicitValueModification.class, name = "ByteExplicitValueModification"),
        @XmlElement(type = StringExplicitValueModification.class, name = "StringExplicitValueModification") })
    private VariableModification<E> modification = null;

    private Boolean createRandomModification;

    protected E assertEquals;

    public ModifiableVariable() {

    }

    public void setModification(VariableModification<E> modification) {
        this.modification = modification;
    }

    @XmlTransient
    public VariableModification<E> getModification() {
        return modification;
    }

    public E getValue() {
        if (Objects.equals(createRandomModification, Boolean.TRUE)) {
            createRandomModification();
            createRandomModification = false;
        }

        if (modification != null) {
            return modification.modify(getOriginalValue());
        }
        return getOriginalValue();
    }

    public abstract E getOriginalValue();

    public abstract void setOriginalValue(E originalValue);

    protected abstract void createRandomModification();

    public void createRandomModificationAtRuntime() {
        createRandomModification = true;
    }

    public abstract boolean isOriginalValueModified();

    public abstract boolean validateAssertions();

    public boolean containsAssertion() {
        return (assertEquals != null);
    }

    public Boolean isCreateRandomModification() {
        if (createRandomModification == null) {
            return false;
        }
        return createRandomModification;
    }
}
