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
@XmlType(propOrder = {"summand", "modificationFilter"})
@XmlAccessorType(XmlAccessType.FIELD)
public class BigIntegerAddModification extends VariableModification<BigInteger> {

    private static final int MAX_ADD_LENGTH = 8;

    private BigInteger summand;

    public BigIntegerAddModification() {}

    public BigIntegerAddModification(BigInteger bi) {
        this.summand = bi;
    }

    @Override
    protected BigInteger modifyImplementationHook(BigInteger input) {
        return (input == null) ? summand : input.add(summand);
    }

    public BigInteger getSummand() {
        return summand;
    }

    public void setSummand(BigInteger summand) {
        this.summand = summand;
    }

    @Override
    public VariableModification<BigInteger> getModifiedCopy() {
        return new BigIntegerAddModification(
                summand.add(new BigInteger(MAX_ADD_LENGTH, new Random())));
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.summand);
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
        final BigIntegerAddModification other = (BigIntegerAddModification) obj;
        if (!Objects.equals(this.summand, other.summand)) {
            return false;
        }
        return true;
    }
}
