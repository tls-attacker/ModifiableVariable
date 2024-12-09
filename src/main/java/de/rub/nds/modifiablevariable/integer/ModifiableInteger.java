/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.integer;

import de.rub.nds.modifiablevariable.ModifiableVariable;
import de.rub.nds.modifiablevariable.VariableModification;
import de.rub.nds.modifiablevariable.util.ArrayConverter;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ModifiableInteger extends ModifiableVariable<Integer> {

    private Integer originalValue;

    public ModifiableInteger() {
        super();
    }

    public ModifiableInteger(ModifiableInteger other) {
        super(other);
        originalValue = other.originalValue;
    }

    @Override
    public ModifiableInteger createCopy() {
        return new ModifiableInteger(this);
    }

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
        return "ModifiableInteger{" + "originalValue=" + originalValue + innerToString() + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ModifiableInteger)) {
            return false;
        }

        ModifiableInteger that = (ModifiableInteger) obj;

        return getValue() != null ? getValue().equals(that.getValue()) : that.getValue() == null;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (getValue() != null ? getValue().hashCode() : 0);
        return result;
    }
}
