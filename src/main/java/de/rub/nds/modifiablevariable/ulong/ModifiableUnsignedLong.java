/**
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Copyright 2014-2022 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 *
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.modifiablevariable.ulong;

import com.google.common.primitives.UnsignedLong;
import de.rub.nds.modifiablevariable.ModifiableVariable;
import de.rub.nds.modifiablevariable.VariableModification;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ModifiableUnsignedLong extends ModifiableVariable<UnsignedLong> implements Serializable {

    private UnsignedLong originalValue;

    @Override
    protected void createRandomModification() {
        VariableModification<UnsignedLong> vm = UnsignedLongModificationFactory.createRandomModification();
        setModification(vm);
    }

    public UnsignedLong getAssertEquals() {
        return assertEquals;
    }

    public void setAssertEquals(UnsignedLong assertEquals) {
        this.assertEquals = assertEquals;
    }

    public void setAssertEquals(Long assertEquals) {
        this.assertEquals = assertEquals != null ? UnsignedLong.fromLongBits(assertEquals) : null;
    }

    @Override
    public boolean isOriginalValueModified() {
        return originalValue != null && originalValue.compareTo(getValue()) != 0;
    }

    @Override
    public boolean validateAssertions() {
        boolean valid = true;
        if (assertEquals != null) {
            if (assertEquals.compareTo(getValue()) != 0) {
                valid = false;
            }
        }
        return valid;
    }

    @Override
    public UnsignedLong getOriginalValue() {
        return originalValue;
    }

    @Override
    public void setOriginalValue(UnsignedLong originalValue) {
        this.originalValue = originalValue;
    }

    public void setOriginalValue(Long originalValue) {
        this.originalValue = originalValue != null ? UnsignedLong.fromLongBits(originalValue) : null;
    }

    @Override
    public String toString() {
        return "ModifiableUnsignedLong{" + "originalValue=" + originalValue + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ModifiableUnsignedLong)) {
            return false;
        }

        ModifiableUnsignedLong that = (ModifiableUnsignedLong) o;

        return getValue() != null ? getValue().equals(that.getValue()) : that.getValue() == null;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (getValue() != null ? getValue().hashCode() : 0);
        return result;
    }
}
