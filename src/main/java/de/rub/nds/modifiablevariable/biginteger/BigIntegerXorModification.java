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
@XmlType(propOrder = {"xor", "modificationFilter"})
@XmlAccessorType(XmlAccessType.FIELD)
public class BigIntegerXorModification extends VariableModification<BigInteger> {

    private static final int MAX_XOR_LENGTH = 8;

    private BigInteger xor;

    public BigIntegerXorModification() {}

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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 57 * hash + Objects.hashCode(this.xor);
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
        final BigIntegerXorModification other = (BigIntegerXorModification) obj;
        return Objects.equals(this.xor, other.xor);
    }
}
