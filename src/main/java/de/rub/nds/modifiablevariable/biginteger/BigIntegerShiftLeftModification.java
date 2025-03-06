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
public class BigIntegerShiftLeftModification extends VariableModification<BigInteger> {

    private int shift;

    public BigIntegerShiftLeftModification() {
        super();
    }

    public BigIntegerShiftLeftModification(int shift) {
        super();
        this.shift = shift;
    }

    public BigIntegerShiftLeftModification(BigIntegerShiftLeftModification other) {
        super(other);
        shift = other.shift;
    }

    @Override
    public BigIntegerShiftLeftModification createCopy() {
        return new BigIntegerShiftLeftModification(this);
    }

    @Override
    protected BigInteger modifyImplementationHook(BigInteger input) {
        if (input == null) {
            return null;
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
        BigIntegerShiftLeftModification other = (BigIntegerShiftLeftModification) obj;
        return shift == other.shift;
    }

    @Override
    public String toString() {
        return "BigIntegerShiftLeftModification{" + "shift=" + shift + '}';
    }
}
