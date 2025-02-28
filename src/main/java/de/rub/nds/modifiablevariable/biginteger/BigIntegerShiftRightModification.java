/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.biginteger;

import de.rub.nds.modifiablevariable.VariableModification;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.math.BigInteger;

@XmlRootElement
public class BigIntegerShiftRightModification extends VariableModification<BigInteger> {

    private int shift;

    public BigIntegerShiftRightModification() {
        super();
    }

    public BigIntegerShiftRightModification(int shift) {
        super();
        this.shift = shift;
    }

    public BigIntegerShiftRightModification(BigIntegerShiftRightModification other) {
        super(other);
        shift = other.shift;
    }

    @Override
    public BigIntegerShiftRightModification createCopy() {
        return new BigIntegerShiftRightModification(this);
    }

    @Override
    protected BigInteger modifyImplementationHook(BigInteger input) {
        if (input == null) {
            input = BigInteger.ZERO;
        }
        return input.shiftRight(shift);
    }

    public int getShift() {
        return shift;
    }

    public void setShift(int shift) {
        this.shift = shift;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + shift;
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
        BigIntegerShiftRightModification other = (BigIntegerShiftRightModification) obj;
        return shift == other.shift;
    }

    @Override
    public String toString() {
        return "BigIntegerShiftRightModification{" + "shift=" + shift + '}';
    }
}
