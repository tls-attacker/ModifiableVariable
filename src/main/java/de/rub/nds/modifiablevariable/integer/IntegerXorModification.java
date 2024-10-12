/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.integer;

import de.rub.nds.modifiablevariable.VariableModification;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import java.util.Objects;
import java.util.Random;

@XmlRootElement
@XmlType(propOrder = {"xor", "modificationFilter"})
public class IntegerXorModification extends VariableModification<Integer> {

    private static final int MAX_VALUE_MODIFIER = 256;

    private Integer xor;

    public IntegerXorModification() {}

    public IntegerXorModification(Integer bi) {
        this.xor = bi;
    }

    @Override
    protected Integer modifyImplementationHook(final Integer input) {
        return (input == null) ? xor : input ^ xor;
    }

    public Integer getXor() {
        return xor;
    }

    public void setXor(Integer xor) {
        this.xor = xor;
    }

    @Override
    public VariableModification<Integer> getModifiedCopy() {
        Random r = new Random();
        if (r.nextBoolean()) {
            return new IntegerSubtractModification(xor + new Random().nextInt(MAX_VALUE_MODIFIER));
        } else {
            return new IntegerSubtractModification(xor - new Random().nextInt(MAX_VALUE_MODIFIER));
        }
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.xor);
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
        final IntegerXorModification other = (IntegerXorModification) obj;
        return Objects.equals(this.xor, other.xor);
    }
}
