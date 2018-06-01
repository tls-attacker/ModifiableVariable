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
@XmlType(propOrder = { "subtrahend", "modificationFilter", "postModification" })
public class BigIntegerSubtractModification extends VariableModification<BigInteger> {

    private final static int MAX_SUBTRACT_LENGTH = 8;

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
}
