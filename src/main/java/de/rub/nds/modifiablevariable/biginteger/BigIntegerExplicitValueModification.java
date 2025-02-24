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
import java.util.Random;

@XmlRootElement
@XmlType(propOrder = {"explicitValue", "modificationFilter"})
public class BigIntegerExplicitValueModification extends VariableModification<BigInteger> {

    private static final int MAX_EXPLICIT_LENGTH = 8;

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
        hash = 31 * hash + Objects.hashCode(explicitValue);;
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
