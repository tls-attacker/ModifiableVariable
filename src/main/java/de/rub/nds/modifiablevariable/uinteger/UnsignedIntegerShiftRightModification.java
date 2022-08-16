/**
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Copyright 2014-2022 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 *
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.modifiablevariable.uinteger;

import com.google.common.primitives.UnsignedInteger;
import de.rub.nds.modifiablevariable.VariableModification;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Random;

@XmlRootElement
@XmlType(propOrder = { "shift", "modificationFilter" })
public class UnsignedIntegerShiftRightModification extends VariableModification<UnsignedInteger> {

    private static final int MAX_SHIFT_MODIFIER = 32;

    private int shift;

    public UnsignedIntegerShiftRightModification() {

    }

    public UnsignedIntegerShiftRightModification(int shift) {
        this.shift = shift;
    }

    @Override
    protected UnsignedInteger modifyImplementationHook(final UnsignedInteger input) {
        if (input == null) {
            return UnsignedInteger.ZERO;
        }
        return UnsignedInteger.fromIntBits(input.intValue() >> shift);
    }

    public int getShift() {
        return shift;
    }

    public void setShift(int shift) {
        this.shift = shift;
    }

    @Override
    public VariableModification<UnsignedInteger> getModifiedCopy() {
        Random r = new Random();
        int newShift;
        if (r.nextBoolean()) {
            newShift = shift + r.nextInt(MAX_SHIFT_MODIFIER);
        } else {
            newShift = shift - r.nextInt(MAX_SHIFT_MODIFIER);
        }
        if (newShift < 0) {
            newShift = MAX_SHIFT_MODIFIER - 1;
        } else if (newShift > MAX_SHIFT_MODIFIER - 1) {
            newShift = 0;
        }
        return new UnsignedIntegerShiftRightModification(newShift);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + this.shift;
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
        final UnsignedIntegerShiftRightModification other = (UnsignedIntegerShiftRightModification) obj;
        if (this.shift != other.shift) {
            return false;
        }
        return true;
    }
}
