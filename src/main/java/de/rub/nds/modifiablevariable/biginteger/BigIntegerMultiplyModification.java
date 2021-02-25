/**
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Copyright 2014-2021 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 *
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.modifiablevariable.biginteger;

import de.rub.nds.modifiablevariable.VariableModification;
import java.math.BigInteger;
import java.util.Objects;
import java.util.Random;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(propOrder = { "factor", "modificationFilter" })
@XmlAccessorType(XmlAccessType.FIELD)
public class BigIntegerMultiplyModification extends VariableModification<BigInteger> {

    private final static int MAX_FACTOR_LENGTH = 8;

    private BigInteger factor;

    public BigIntegerMultiplyModification() {

    }

    public BigIntegerMultiplyModification(BigInteger bi) {
        this.factor = bi;
    }

    @Override
    protected BigInteger modifyImplementationHook(BigInteger input) {
        if (input == null) {
            input = BigInteger.ZERO;
        }
        return input.multiply(factor);
    }

    public BigInteger getFactor() {
        return factor;
    }

    public void setFactor(BigInteger factor) {
        this.factor = factor;
    }

    @Override
    public VariableModification<BigInteger> getModifiedCopy() {
        return new BigIntegerMultiplyModification(factor.add(new BigInteger(MAX_FACTOR_LENGTH, new Random())));
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 61 * hash + Objects.hashCode(this.factor);
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
        final BigIntegerMultiplyModification other = (BigIntegerMultiplyModification) obj;
        if (!Objects.equals(this.factor, other.factor)) {
            return false;
        }
        return true;
    }
}
