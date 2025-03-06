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
import java.util.Objects;

@XmlRootElement
public class BigIntegerMultiplyModification extends VariableModification<BigInteger> {

    private BigInteger factor;

    public BigIntegerMultiplyModification() {
        super();
    }

    public BigIntegerMultiplyModification(BigInteger factor) {
        super();
        this.factor = factor;
    }

    public BigIntegerMultiplyModification(BigIntegerMultiplyModification other) {
        super(other);
        factor = other.factor;
    }

    @Override
    public BigIntegerMultiplyModification createCopy() {
        return new BigIntegerMultiplyModification(this);
    }

    @Override
    protected BigInteger modifyImplementationHook(BigInteger input) {
        if (input == null) {
            return null;
        }
        return input.multiply(factor);
    }

    public BigInteger getFactor() {
        return factor;
    }

    public void setFactor(BigInteger factor) {
        this.factor = factor;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(factor);
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
        BigIntegerMultiplyModification other = (BigIntegerMultiplyModification) obj;
        return Objects.equals(factor, other.factor);
    }

    @Override
    public String toString() {
        return "BigIntegerMultiplyModification{" + "factor=" + factor + '}';
    }
}
