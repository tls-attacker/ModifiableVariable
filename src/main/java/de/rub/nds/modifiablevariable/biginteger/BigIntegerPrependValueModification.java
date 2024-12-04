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
@XmlType(propOrder = {"prependValue", "modificationFilter"})
@XmlAccessorType(XmlAccessType.FIELD)
public class BigIntegerPrependValueModification extends VariableModification<BigInteger> {

    private static final int MAX_PREPEND_LENGTH = 8;

    private BigInteger prependValue;

    public BigIntegerPrependValueModification() {
        super();
    }

    public BigIntegerPrependValueModification(BigInteger prependValue) {
        super();
        this.prependValue = prependValue;
    }

    public BigIntegerPrependValueModification(BigIntegerPrependValueModification other) {
        super(other);
        prependValue = other.prependValue;
    }

    @Override
    public BigIntegerPrependValueModification createCopy() {
        return new BigIntegerPrependValueModification(this);
    }

    @Override
    protected BigInteger modifyImplementationHook(BigInteger input) {
        if (input == null) {
            input = BigInteger.ZERO;
        }
        return prependValue.shiftLeft(input.bitLength()).or(input);
    }

    public BigInteger getPrependValue() {
        return prependValue;
    }

    public void setPrependValue(BigInteger prependValue) {
        this.prependValue = prependValue;
    }

    @Override
    public VariableModification<BigInteger> getModifiedCopy() {
        return new BigIntegerPrependValueModification(
                prependValue.add(new BigInteger(MAX_PREPEND_LENGTH, new Random())));
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + prependValue.hashCode();
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
        BigIntegerPrependValueModification other = (BigIntegerPrependValueModification) obj;
        return Objects.equals(prependValue, other.prependValue);
    }
}
