/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.length;

import de.rub.nds.modifiablevariable.bytearray.ModifiableByteArray;
import de.rub.nds.modifiablevariable.integer.ModifiableInteger;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ModifiableLengthField extends ModifiableInteger {

    private final ModifiableByteArray ref;

    public ModifiableLengthField(ModifiableByteArray ref) {
        super();
        this.ref = ref;
    }

    public ModifiableLengthField(ModifiableLengthField other) {
        super(other);
        ref = other.ref;
    }

    @Override
    public ModifiableLengthField createCopy() {
        return new ModifiableLengthField(this);
    }

    @Override
    public Integer getOriginalValue() {
        return ref.getValue().length;
    }

    @Override
    public void setOriginalValue(Integer originalValue) {
        throw new UnsupportedOperationException(
                "Cannot set original Value of ModifiableLengthField");
    }

    @Override
    public String toString() {
        return "ModifiableLengthField{" + "ref=" + ref + super.toString() + "} ";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ModifiableLengthField)) {
            return false;
        }

        ModifiableLengthField that = (ModifiableLengthField) obj;

        return ref != null ? getValue().equals(that.getValue()) : that.getValue() == null;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (getValue() != null ? getValue().hashCode() : 0);
        return result;
    }
}
