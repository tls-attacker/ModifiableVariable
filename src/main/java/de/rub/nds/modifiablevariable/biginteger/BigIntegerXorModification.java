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
public class BigIntegerXorModification extends VariableModification<BigInteger> {

    private BigInteger xor;

    public BigIntegerXorModification() {
        super();
    }

    public BigIntegerXorModification(BigInteger xor) {
        super();
        this.xor = xor;
    }

    public BigIntegerXorModification(BigIntegerXorModification other) {
        super(other);
        xor = other.xor;
    }

    @Override
    public BigIntegerXorModification createCopy() {
        return new BigIntegerXorModification(this);
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
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(xor);
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
        BigIntegerXorModification other = (BigIntegerXorModification) obj;
        return Objects.equals(xor, other.xor);
    }

    @Override
    public String toString() {
        return "BigIntegerXorModification{" + "xor=" + xor + '}';
    }
}
