/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, and Hackmanit GmbH
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
import java.util.Random;

@XmlRootElement
@XmlType(propOrder = {"shift", "modificationFilter"})
@XmlAccessorType(XmlAccessType.FIELD)
public class BigIntegerShiftLeftModification extends VariableModification<BigInteger> {

    private static final int MAX_SHIFT_LENGTH = 32;

    private int shift;

    public BigIntegerShiftLeftModification() {}

    public BigIntegerShiftLeftModification(int shift) {
        this.shift = shift;
    }

    @Override
    protected BigInteger modifyImplementationHook(BigInteger input) {
        if (input == null) {
            input = BigInteger.ZERO;
        }
        return input.shiftLeft(shift);
    }

    public int getShift() {
        return shift;
    }

    public void setShift(int shift) {
        this.shift = shift;
    }

    @Override
    public VariableModification<BigInteger> getModifiedCopy() {
        return new BigIntegerShiftLeftModification(shift + new Random().nextInt(MAX_SHIFT_LENGTH));
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.shift;
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
        final BigIntegerShiftLeftModification other = (BigIntegerShiftLeftModification) obj;
        if (this.shift != other.shift) {
            return false;
        }
        return true;
    }
}
