/**
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Copyright 2014-2017 Ruhr University Bochum / Hackmanit GmbH
 *
 * Licensed under Apache License 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.biginteger;

import de.rub.nds.modifiablevariable.ModifiableVariable;
import de.rub.nds.modifiablevariable.VariableModification;
import de.rub.nds.modifiablevariable.util.ArrayConverter;
import java.io.Serializable;
import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ModifiableBigInteger extends ModifiableVariable<BigInteger> implements Serializable {

    private BigInteger originalValue;

    @Override
    protected void createRandomModification() {
        VariableModification<BigInteger> vm = BigIntegerModificationFactory.createRandomModification();
        setModification(vm);
    }

    public BigInteger getAssertEquals() {
        return assertEquals;
    }

    public void setAssertEquals(BigInteger assertEquals) {
        this.assertEquals = assertEquals;
    }

    @Override
    public boolean isOriginalValueModified() {
        return getOriginalValue() != null && (getOriginalValue().compareTo(getValue()) != 0);
    }

    public byte[] getByteArray() {
        return ArrayConverter.bigIntegerToByteArray(getValue());
    }

    public byte[] getByteArray(int size) {
        return ArrayConverter.bigIntegerToByteArray(getValue(), size, true);
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
    public BigInteger getOriginalValue() {
        return originalValue;
    }

    @Override
    public void setOriginalValue(BigInteger originalValue) {
        this.originalValue = originalValue;
    }

    @Override
    public String toString() {
        return "ModifiableBigInteger{" + "originalValue=" + originalValue + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof ModifiableBigInteger))
            return false;

        ModifiableBigInteger that = (ModifiableBigInteger) o;

        return getValue() != null ? getValue().equals(that.getValue()) : that.getValue() == null;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (getValue() != null ? getValue().hashCode() : 0);
        return result;
    }
}
