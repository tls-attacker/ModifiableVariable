/**
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Copyright 2014-2017 Ruhr University Bochum / Hackmanit GmbH
 *
 * Licensed under Apache License 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.integer;

import de.rub.nds.modifiablevariable.ModifiableVariable;
import de.rub.nds.modifiablevariable.VariableModification;
import de.rub.nds.modifiablevariable.util.ArrayConverter;
import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ModifiableInteger extends ModifiableVariable<Integer> implements Serializable {

    private Integer originalValue;

    @Override
    protected void createRandomModification() {
        VariableModification<Integer> vm = IntegerModificationFactory.createRandomModification();
        setModification(vm);
    }

    public Integer getAssertEquals() {
        return assertEquals;
    }

    public void setAssertEquals(Integer assertEquals) {
        this.assertEquals = assertEquals;
    }

    @Override
    public boolean isOriginalValueModified() {
        return getOriginalValue() != null && getOriginalValue().compareTo(getValue()) != 0;
    }

    public byte[] getByteArray(int size) {
        return ArrayConverter.intToBytes(getValue(), size);
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
    public Integer getOriginalValue() {
        return originalValue;
    }

    @Override
    public void setOriginalValue(Integer originalValue) {
        this.originalValue = originalValue;
    }

    @Override
    public String toString() {
        return "ModifiableInteger{" + "originalValue=" + originalValue + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof ModifiableInteger))
            return false;

        ModifiableInteger that = (ModifiableInteger) o;

        return getValue() != null ? getValue().equals(that.getValue()) : that.getValue() == null;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (getValue() != null ? getValue().hashCode() : 0);
        return result;
    }
}
