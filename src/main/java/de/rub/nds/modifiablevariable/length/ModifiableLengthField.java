/**
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Copyright 2014-2017 Ruhr University Bochum / Hackmanit GmbH
 *
 * Licensed under Apache License 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.length;

import de.rub.nds.modifiablevariable.integer.ModifiableInteger;
import de.rub.nds.modifiablevariable.bytearray.ModifiableByteArray;
import de.rub.nds.modifiablevariable.integer.IntegerAddModification;
import de.rub.nds.modifiablevariable.integer.IntegerExplicitValueModification;
import de.rub.nds.modifiablevariable.integer.IntegerSubtractModification;
import de.rub.nds.modifiablevariable.integer.IntegerXorModification;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

/**
 * 
 * @author Robert Merget - robert.merget@rub.de Highly Experimental
 */
@XmlRootElement
@XmlSeeAlso({ IntegerAddModification.class, IntegerExplicitValueModification.class, IntegerSubtractModification.class,
        IntegerXorModification.class })
@XmlType(propOrder = { "originalValue", "modification", "assertEquals" })
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
        if (this == o)
            return true;
        if (!(o instanceof ModifiableLengthField))
            return false;

        ModifiableLengthField that = (ModifiableLengthField) o;

        return ref != null ? getValue().equals(that.ref) : that.getValue() == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getValue() != null ? getValue().hashCode() : 0);
        return result;
    }
}
