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
@XmlType(propOrder = { "xor", "modificationFilter", "postModification" })
public class BigIntegerXorModification extends VariableModification<BigInteger> {

    private final static int MAX_XOR_LENGTH = 8;

    private BigInteger xor;

    public BigIntegerXorModification() {

    }

    public BigIntegerXorModification(BigInteger bi) {
        this.xor = bi;
    }

    @Override
    protected BigInteger modifyImplementationHook(BigInteger input) {
        if (input == null) {
            input = BigInteger.ZERO;
        }
        return input.xor(xor);
    }

    public BigInteger getXor() {
        return xor;
    }

    public void setXor(BigInteger xor) {
        this.xor = xor;
    }

    @Override
    public VariableModification<BigInteger> getModifiedCopy() {
        return new BigIntegerXorModification(xor.add(new BigInteger(MAX_XOR_LENGTH, new Random())));
    }
}
