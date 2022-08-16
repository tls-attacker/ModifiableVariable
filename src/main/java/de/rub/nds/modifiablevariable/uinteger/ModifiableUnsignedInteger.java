/**
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Copyright 2014-2022 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 *
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.modifiablevariable.uinteger;

import com.google.common.primitives.UnsignedInteger;
import de.rub.nds.modifiablevariable.ModifiableVariable;
import de.rub.nds.modifiablevariable.VariableModification;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ModifiableUnsignedInteger extends ModifiableVariable<UnsignedInteger> {

    private UnsignedInteger originalValue;

    @Override
    protected void createRandomModification() {
        VariableModification<UnsignedInteger> vm = UnsignedIntegerModificationFactory.createRandomModification();
        setModification(vm);
    }

    public UnsignedInteger getAssertEquals() {
        return assertEquals;
    }

    public void setAssertEquals(UnsignedInteger assertEquals) {
        this.assertEquals = assertEquals;
    }

    @Override
    public boolean isOriginalValueModified() {
        return getOriginalValue() != null && getOriginalValue().compareTo(getValue()) != 0;
    }

    @Override
    public boolean validateAssertions() {
        return assertEquals == null || assertEquals.compareTo(getValue()) == 0;
    }

    @Override
    public UnsignedInteger getOriginalValue() {
        return originalValue;
    }

    @Override
    public void setOriginalValue(UnsignedInteger originalValue) {
        this.originalValue = originalValue;
    }

    public void setOriginalValue(Integer originalValue) {
        this.originalValue = originalValue != null ? UnsignedInteger.fromIntBits(originalValue) : null;
    }

    @Override
    public String toString() {
        return "ModifiableUnsignedInteger{originalValue=" + originalValue + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ModifiableUnsignedInteger)) {
            return false;
        }

        ModifiableUnsignedInteger that = (ModifiableUnsignedInteger) o;

        return getValue() != null ? getValue().equals(that.getValue()) : that.getValue() == null;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (getValue() != null ? getValue().hashCode() : 0);
        return result;
    }
}
