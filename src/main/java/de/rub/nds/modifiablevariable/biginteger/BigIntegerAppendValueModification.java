/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.biginteger;

import de.rub.nds.modifiablevariable.VariableModification;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import java.math.BigInteger;
import java.util.Objects;
import java.util.Random;

@XmlRootElement
@XmlType(propOrder = {"appendValue", "modificationFilter"})
@XmlAccessorType(XmlAccessType.FIELD)
public class BigIntegerAppendValueModification extends VariableModification<BigInteger> {

    private static final int MAX_APPEND_LENGTH = 8;

    private BigInteger appendValue;

    public BigIntegerAppendValueModification() {}

    public BigIntegerAppendValueModification(BigInteger bi) {
        this.appendValue = bi;
    }

    @Override
    protected BigInteger modifyImplementationHook(BigInteger input) {
        if (input == null) {
            input = BigInteger.ZERO;
        }
        return input.shiftLeft(appendValue.bitLength()).add(appendValue);
    }

    public BigInteger getAppendValue() {
        return appendValue;
    }

    public void setAppendValue(BigInteger appendValue) {
        this.appendValue = appendValue;
    }

    @Override
    public VariableModification<BigInteger> getModifiedCopy() {
        return new BigIntegerAppendValueModification(appendValue.add(new BigInteger(MAX_APPEND_LENGTH, new Random())));
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.appendValue);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BigIntegerAppendValueModification other = (BigIntegerAppendValueModification) obj;
        return Objects.equals(this.appendValue, other.appendValue);
    }
}
