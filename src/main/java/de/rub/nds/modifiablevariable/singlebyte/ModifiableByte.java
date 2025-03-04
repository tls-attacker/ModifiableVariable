/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.singlebyte;

import de.rub.nds.modifiablevariable.ModifiableVariable;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ModifiableByte extends ModifiableVariable<Byte> {

    private Byte originalValue;

    public ModifiableByte() {
        super();
    }

    public ModifiableByte(Byte originalValue) {
        super();
        this.originalValue = originalValue;
    }

    public ModifiableByte(ModifiableByte other) {
        super(other);
        originalValue = other.originalValue;
    }

    @Override
    public ModifiableByte createCopy() {
        return new ModifiableByte(this);
    }

    public Byte getAssertEquals() {
        return assertEquals;
    }

    public void setAssertEquals(Byte assertEquals) {
        this.assertEquals = assertEquals;
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
    public Byte getOriginalValue() {
        return originalValue;
    }

    @Override
    public void setOriginalValue(Byte originalValue) {
        this.originalValue = originalValue;
    }

    @Override
    public String toString() {
        return "ModifiableByte{" + "originalValue=" + originalValue + innerToString() + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ModifiableByte that)) {
            return false;
        }

        return getValue() != null ? getValue().equals(that.getValue()) : that.getValue() == null;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (getValue() != null ? getValue().hashCode() : 0);
        return result;
    }
}
