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
@XmlType(propOrder = {"explicitValue", "modificationFilter"})
@XmlAccessorType(XmlAccessType.FIELD)
public class BigIntegerExplicitValueModification extends VariableModification<BigInteger> {

    private static final int MAX_EXPLICIT_LENGTH = 8;

    private BigInteger explicitValue;

    public BigIntegerExplicitValueModification() {}

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
            return new BigIntegerExplicitValueModification(
                    explicitValue.add(new BigInteger(MAX_EXPLICIT_LENGTH, r)));
        } else {
            return new BigIntegerExplicitValueModification(
                    explicitValue.subtract(new BigInteger(MAX_EXPLICIT_LENGTH, r)));
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
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
        final BigIntegerExplicitValueModification other = (BigIntegerExplicitValueModification) obj;
        return Objects.equals(this.explicitValue, other.explicitValue);
    }
}
