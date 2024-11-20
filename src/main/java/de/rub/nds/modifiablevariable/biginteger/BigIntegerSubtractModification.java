/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
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
import java.util.Objects;
import java.util.Random;

@XmlRootElement
@XmlType(propOrder = {"subtrahend", "modificationFilter"})
@XmlAccessorType(XmlAccessType.FIELD)
public class BigIntegerSubtractModification extends VariableModification<BigInteger> {

    private static final int MAX_SUBTRACT_LENGTH = 8;

    private BigInteger subtrahend;

    public BigIntegerSubtractModification() {}

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
        return new BigIntegerSubtractModification(
                subtrahend.add(new BigInteger(MAX_SUBTRACT_LENGTH, new Random())));
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 56 * hash + Objects.hashCode(this.subtrahend);
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
        return Objects.equals(this.subtrahend, other.subtrahend);
    }
}
