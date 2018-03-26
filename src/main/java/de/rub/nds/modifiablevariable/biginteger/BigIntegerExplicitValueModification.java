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
@XmlType(propOrder = { "explicitValue", "modificationFilter", "postModification" })
public class BigIntegerExplicitValueModification extends VariableModification<BigInteger> {

    private BigInteger explicitValue;

    public BigIntegerExplicitValueModification() {
    }

    public BigIntegerExplicitValueModification(BigInteger bi) {
        this.explicitValue = bi;
    }

    @Override
    protected BigInteger modifyImplementationHook(final BigInteger input) {
        return explicitValue;
    }

    public BigInteger getExplicitValue() {
        return explicitValue;
    }

    public void setExplicitValue(BigInteger explicitValue) {
        this.explicitValue = explicitValue;
    }

    @Override
    public VariableModification<BigInteger> getModifiedCopy() {
        Random r = new Random();
        if (r.nextBoolean()) {
            return new BigIntegerExplicitValueModification(explicitValue.add(new BigInteger(8, r)));
        } else {
            return new BigIntegerExplicitValueModification(explicitValue.subtract(new BigInteger(8, r)));
        }
    }
}
