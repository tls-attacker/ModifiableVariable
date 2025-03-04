/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.longint;

import de.rub.nds.modifiablevariable.VariableModification;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@XmlRootElement
public class LongXorModification extends VariableModification<Long> {

    private Long xor;

    public LongXorModification() {
        super();
    }

    public LongXorModification(Long xor) {
        super();
        this.xor = xor;
    }

    public LongXorModification(LongXorModification other) {
        super(other);
        xor = other.xor;
    }

    @Override
    public LongXorModification createCopy() {
        return new LongXorModification(this);
    }

    @Override
    protected Long modifyImplementationHook(Long input) {
        return input == null ? xor : input ^ xor;
    }

    public Long getXor() {
        return xor;
    }

    public void setXor(Long xor) {
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
        LongXorModification other = (LongXorModification) obj;
        return Objects.equals(xor, other.xor);
    }

    @Override
    public String toString() {
        return "LongXorModification{" + "xor=" + xor + '}';
    }
}
