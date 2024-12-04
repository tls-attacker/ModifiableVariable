/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.biginteger;

import de.rub.nds.modifiablevariable.ModifiableVariable;
import de.rub.nds.modifiablevariable.VariableModification;
import de.rub.nds.modifiablevariable.util.ArrayConverter;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.math.BigInteger;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ModifiableBigInteger extends ModifiableVariable<BigInteger> {

    private BigInteger originalValue;

    public ModifiableBigInteger() {
        super();
    }

    public ModifiableBigInteger(ModifiableBigInteger other) {
        super(other);
        originalValue = other.originalValue;
    }

    @Override
    public ModifiableBigInteger createCopy() {
        return new ModifiableBigInteger(this);
    }

    @Override
    protected void createRandomModification() {
        VariableModification<BigInteger> vm =
                BigIntegerModificationFactory.createRandomModification();
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
        return originalValue != null && originalValue.compareTo(getValue()) != 0;
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
            return assertEquals.compareTo(getValue()) == 0;
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
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ModifiableBigInteger)) {
            return false;
        }

        ModifiableBigInteger that = (ModifiableBigInteger) obj;

        return getValue() != null ? getValue().equals(that.getValue()) : that.getValue() == null;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (getValue() != null ? getValue().hashCode() : 0);
        return result;
    }
}
