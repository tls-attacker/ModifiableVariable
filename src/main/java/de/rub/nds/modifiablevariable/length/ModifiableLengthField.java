/**
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Copyright 2014-2022 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 *
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
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
        this.ref = ref;
    }

    @Override
    public Integer getOriginalValue() {
        return ref.getValue().length;
    }

    @Override
    public void setOriginalValue(Integer originalValue) {
        throw new UnsupportedOperationException("Cannot set original Value of ModifiableLengthField");
    }

    @Override
    public String toString() {
        return "ModifiableLengthField{" + "ref=" + ref + "} " + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ModifiableLengthField)) {
            return false;
        }

        ModifiableLengthField that = (ModifiableLengthField) o;

        return ref != null ? getValue().equals(that.getValue()) : that.getValue() == null;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (getValue() != null ? getValue().hashCode() : 0);
        return result;
    }
}
