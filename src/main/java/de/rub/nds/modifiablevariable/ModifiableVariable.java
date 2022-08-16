/**
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Copyright 2014-2022 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 *
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.modifiablevariable;

import de.rub.nds.modifiablevariable.biginteger.*;
import de.rub.nds.modifiablevariable.bool.BooleanExplicitValueModification;
import de.rub.nds.modifiablevariable.bool.BooleanToggleModification;
import de.rub.nds.modifiablevariable.bytearray.*;
import de.rub.nds.modifiablevariable.integer.*;
import de.rub.nds.modifiablevariable.mlong.LongAddModification;
import de.rub.nds.modifiablevariable.mlong.LongExplicitValueModification;
import de.rub.nds.modifiablevariable.mlong.LongSubtractModification;
import de.rub.nds.modifiablevariable.mlong.LongXorModification;
import de.rub.nds.modifiablevariable.singlebyte.ByteAddModification;
import de.rub.nds.modifiablevariable.singlebyte.ByteExplicitValueModification;
import de.rub.nds.modifiablevariable.singlebyte.ByteSubtractModification;
import de.rub.nds.modifiablevariable.singlebyte.ByteXorModification;
import de.rub.nds.modifiablevariable.string.StringExplicitValueModification;
import de.rub.nds.modifiablevariable.uinteger.*;
import de.rub.nds.modifiablevariable.ulong.UnsignedLongAddModification;
import de.rub.nds.modifiablevariable.ulong.UnsignedLongExplicitValueModification;
import de.rub.nds.modifiablevariable.ulong.UnsignedLongSubtractModification;
import de.rub.nds.modifiablevariable.ulong.UnsignedLongXorModification;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.Objects;

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
        @XmlElement(type = UnsignedIntegerAddModification.class, name = "UnsignedIntegerAddModification"),
        @XmlElement(type = UnsignedIntegerSubtractModification.class, name = "UnsignedIntegerSubtractModification"),
        @XmlElement(type = UnsignedIntegerXorModification.class, name = "UnsignedIntegerXorModification"),
        @XmlElement(type = UnsignedIntegerExplicitValueModification.class,
            name = "UnsignedIntegerExplicitValueModification"),
        @XmlElement(type = UnsignedIntegerShiftLeftModification.class, name = "UnsignedIntegerShiftLeftModification"),
        @XmlElement(type = UnsignedIntegerShiftRightModification.class, name = "UnsignedIntegerShiftRightModification"),
        @XmlElement(type = LongXorModification.class, name = "LongXorModification"),
        @XmlElement(type = LongSubtractModification.class, name = "LongSubtractModification"),
        @XmlElement(type = LongExplicitValueModification.class, name = "LongExplicitValueModification"),
        @XmlElement(type = LongAddModification.class, name = "LongAddModification"),
        @XmlElement(type = UnsignedLongAddModification.class, name = "UnsignedLongAddModification"),
        @XmlElement(type = UnsignedLongSubtractModification.class, name = "UnsignedLongSubtractModification"),
        @XmlElement(type = UnsignedLongXorModification.class, name = "UnsignedLongXorModification"),
        @XmlElement(type = UnsignedLongExplicitValueModification.class, name = "UnsignedLongExplicitValueModification"),
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
