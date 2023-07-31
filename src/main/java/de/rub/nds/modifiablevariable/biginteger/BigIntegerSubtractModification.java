/**
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
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
@XmlType(propOrder = { "subtrahend", "modificationFilter" })
@XmlAccessorType(XmlAccessType.FIELD)
public class BigIntegerSubtractModification extends VariableModification<BigInteger> {

    private static final int MAX_SUBTRACT_LENGTH = 8;

    private BigInteger subtrahend;

    public BigIntegerSubtractModification() {

    }

    public BigIntegerSubtractModification(BigInteger bi) {
        this.subtrahend = bi;
    }

    @Override
    protected BigInteger modifyImplementationHook(BigInteger input) {
        if (input == null) {
            input = BigInteger.ZERO;
        }
        return input.subtract(subtrahend);
    }

    public BigInteger getSubtrahend() {
        return subtrahend;
    }

    public void setSubtrahend(BigInteger subtrahend) {
        this.subtrahend = subtrahend;
    }

    @Override
    public VariableModification<BigInteger> getModifiedCopy() {
        return new BigIntegerSubtractModification(subtrahend.add(new BigInteger(MAX_SUBTRACT_LENGTH, new Random())));
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 61 * hash + Objects.hashCode(this.subtrahend);
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
        final BigIntegerSubtractModification other = (BigIntegerSubtractModification) obj;
        if (!Objects.equals(this.subtrahend, other.subtrahend)) {
            return false;
        }
        return true;
    }
}
