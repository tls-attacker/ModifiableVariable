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
public class BigIntegerExplicitValueModification extends VariableModification<BigInteger> {

    protected BigInteger explicitValue;

    public BigIntegerExplicitValueModification() {
        super();
    }

    public BigIntegerExplicitValueModification(BigInteger explicitValue) {
        super();
        this.explicitValue = explicitValue;
    }

    public BigIntegerExplicitValueModification(BigIntegerExplicitValueModification other) {
        super(other);
        explicitValue = other.explicitValue;
    }

    @Override
    public BigIntegerExplicitValueModification createCopy() {
        return new BigIntegerExplicitValueModification(this);
    }

    @Override
    protected BigInteger modifyImplementationHook(BigInteger input) {
        if (input == null) {
            throw new NullPointerException("original value must not be null");
        }
        return explicitValue;
    }

    public BigInteger getExplicitValue() {
        return explicitValue;
    }

    public void setExplicitValue(BigInteger explicitValue) {
        this.explicitValue = explicitValue;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(explicitValue);
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
        BigIntegerExplicitValueModification other = (BigIntegerExplicitValueModification) obj;
        return Objects.equals(explicitValue, other.explicitValue);
    }

    @Override
    public String toString() {
        return "BigIntegerExplicitValueModification{" + "explicitValue=" + explicitValue + '}';
    }
}
