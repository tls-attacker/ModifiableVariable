/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.bool;

import de.rub.nds.modifiablevariable.ModifiableVariable;
import de.rub.nds.modifiablevariable.VariableModification;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ModifiableBoolean extends ModifiableVariable<Boolean> {

    private Boolean originalValue;

    public ModifiableBoolean() {}

    public ModifiableBoolean(Boolean originalValue) {
        this.originalValue = originalValue;
    }

    @Override
    public Boolean getOriginalValue() {
        return originalValue;
    }

    @Override
    public void setOriginalValue(Boolean originalValue) {
        this.originalValue = originalValue;
    }

    @Override
    protected void createRandomModification() {
        VariableModification<Boolean> vm = BooleanModificationFactory.createRandomModification();
        setModification(vm);
    }

    @Override
    public boolean isOriginalValueModified() {
        return getOriginalValue() != null && (getOriginalValue().compareTo(getValue()) != 0);
    }

    @Override
    public boolean validateAssertions() {
        if (assertEquals != null) {
            if (assertEquals.compareTo(getValue()) != 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return "ModifiableBoolean{" + "originalValue=" + originalValue + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ModifiableBoolean)) {
            return false;
        }

        ModifiableBoolean that = (ModifiableBoolean) o;

        return getValue() != null ? getValue().equals(that.getValue()) : that.getValue() == null;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (getValue() != null ? getValue().hashCode() : 0);
        return result;
    }
}
