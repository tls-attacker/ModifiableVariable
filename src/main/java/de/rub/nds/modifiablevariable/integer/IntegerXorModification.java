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
import java.util.Objects;

@XmlRootElement
public class IntegerXorModification extends VariableModification<Integer> {

    private Integer xor;

    public IntegerXorModification() {
        super();
    }

    public IntegerXorModification(Integer xor) {
        super();
        this.xor = xor;
    }

    public IntegerXorModification(IntegerXorModification other) {
        super(other);
        xor = other.xor;
    }

    @Override
    public IntegerXorModification createCopy() {
        return new IntegerXorModification(this);
    }

    @Override
    protected Integer modifyImplementationHook(Integer input) {
        if (input == null) {
            return null;
        }
        return input ^ xor;
    }

    public Integer getXor() {
        return xor;
    }

    public void setXor(Integer xor) {
        this.xor = xor;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + xor;
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
        IntegerXorModification other = (IntegerXorModification) obj;
        return Objects.equals(xor, other.xor);
    }

    @Override
    public String toString() {
        return "IntegerXorModification{" + "xor=" + xor + '}';
    }
}
