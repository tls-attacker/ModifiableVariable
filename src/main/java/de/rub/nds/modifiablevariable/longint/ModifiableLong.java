/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.longint;

import de.rub.nds.modifiablevariable.ModifiableVariable;
import de.rub.nds.modifiablevariable.util.ArrayConverter;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ModifiableLong extends ModifiableVariable<Long> {

    private Long originalValue;

    public ModifiableLong() {
        super();
    }

    public ModifiableLong(Long originalValue) {
        super();
        this.originalValue = originalValue;
    }

    public ModifiableLong(ModifiableLong other) {
        super(other);
        originalValue = other.originalValue;
    }

    @Override
    public ModifiableLong createCopy() {
        return new ModifiableLong(this);
    }

    public Long getAssertEquals() {
        return assertEquals;
    }

    public void setAssertEquals(Long assertEquals) {
        this.assertEquals = assertEquals;
    }

    @Override
    public boolean isOriginalValueModified() {
        return originalValue != null && originalValue.compareTo(getValue()) != 0;
    }

    public byte[] getByteArray(int size) {
        return ArrayConverter.longToBytes(getValue(), size);
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
    public Long getOriginalValue() {
        return originalValue;
    }

    @Override
    public void setOriginalValue(Long originalValue) {
        this.originalValue = originalValue;
    }

    @Override
    public String toString() {
        return "ModifiableLong{" + "originalValue=" + originalValue + innerToString() + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ModifiableLong)) {
            return false;
        }

        ModifiableLong that = (ModifiableLong) obj;

        return getValue() != null ? getValue().equals(that.getValue()) : that.getValue() == null;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (getValue() != null ? getValue().hashCode() : 0);
        return result;
    }
}
