/**
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Copyright 2014-2017 Ruhr University Bochum / Hackmanit GmbH
 *
 * Licensed under Apache License 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.biginteger;

import de.rub.nds.modifiablevariable.VariableModification;
import java.math.BigInteger;
import java.util.Random;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(propOrder = { "shift", "modificationFilter", "postModification" })
public class BigIntegerShiftRightModification extends VariableModification<BigInteger> {

    private int shift;

    public BigIntegerShiftRightModification() {

    }

    public BigIntegerShiftRightModification(int shift) {
        this.shift = shift;
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
    protected VariableModification<BigInteger> getModifiedCopy() {
        return new BigIntegerShiftRightModification(shift + new Random().nextInt(32));
    }
}
