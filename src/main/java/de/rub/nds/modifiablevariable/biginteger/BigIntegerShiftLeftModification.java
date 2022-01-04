/**
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Copyright 2014-2022 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 *
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.modifiablevariable.biginteger;

import de.rub.nds.modifiablevariable.VariableModification;
import java.math.BigInteger;
import java.util.Random;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(propOrder = { "shift", "modificationFilter" })
@XmlAccessorType(XmlAccessType.FIELD)
public class BigIntegerShiftLeftModification extends VariableModification<BigInteger> {

    private static final int MAX_SHIFT_LENGTH = 32;

    private int shift;

    public BigIntegerShiftLeftModification() {

    }

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
