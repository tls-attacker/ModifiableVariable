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
import jakarta.xml.bind.annotation.XmlType;
import java.math.BigInteger;
import java.util.Objects;

@XmlRootElement
@XmlType(propOrder = "index")
public class BigIntegerExplicitValueFromFileModification
        extends BigIntegerExplicitValueModification {
    private int index;

    public BigIntegerExplicitValueFromFileModification() {
        super();
    }

    public BigIntegerExplicitValueFromFileModification(int index, BigInteger explicitValue) {
        super(explicitValue);
        this.index = index;
    }

    public BigIntegerExplicitValueFromFileModification(
            BigIntegerExplicitValueFromFileModification other) {
        super(other);
        index = other.index;
    }

    @Override
    public BigIntegerExplicitValueFromFileModification createCopy() {
        return new BigIntegerExplicitValueFromFileModification(this);
    }

    public int getIndex() {
        return index;
    }

    @Override
    public VariableModification<BigInteger> getModifiedCopy() {
        throw new UnsupportedOperationException(
                "Cannot set modify Value of BigIntegerExplicitValueFromFileModification");
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + index;
        hash = 31 * hash + explicitValue.hashCode();
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
        BigIntegerExplicitValueFromFileModification other =
                (BigIntegerExplicitValueFromFileModification) obj;
        if (index != other.index) {
            return false;
        }
        return Objects.equals(explicitValue, other.explicitValue);
    }

    @Override
    public String toString() {
        return "BigIntegerExplicitValueFromFileModification{"
                + "index="
                + index
                + ", explicitValue="
                + explicitValue
                + '}';
    }
}
