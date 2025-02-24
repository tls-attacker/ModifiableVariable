/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.bool;

import de.rub.nds.modifiablevariable.ModifiableVariable;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ModifiableBoolean extends ModifiableVariable<Boolean> {

    private Boolean originalValue;

    public ModifiableBoolean() {
        super();
    }

    public ModifiableBoolean(Boolean originalValue) {
        super();
        this.originalValue = originalValue;
    }

    public ModifiableBoolean(ModifiableBoolean other) {
        super(other);
        originalValue = other.originalValue;
    }

    @Override
    public ModifiableBoolean createCopy() {
        return new ModifiableBoolean(this);
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
    public boolean isOriginalValueModified() {
        return originalValue != null && originalValue.compareTo(getValue()) != 0;
    }

    @Override
    public boolean validateAssertions() {
        if (assertEquals != null) {
            return assertEquals.compareTo(getValue()) == 0;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ModifiableBoolean{" + "originalValue=" + originalValue + innerToString() + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ModifiableBoolean)) {
            return false;
        }

        ModifiableBoolean that = (ModifiableBoolean) obj;

        return getValue() != null ? getValue().equals(that.getValue()) : that.getValue() == null;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (getValue() != null ? getValue().hashCode() : 0);
        return result;
    }
}
