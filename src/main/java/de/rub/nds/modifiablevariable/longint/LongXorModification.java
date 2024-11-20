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
import jakarta.xml.bind.annotation.XmlType;
import java.util.Objects;
import java.util.Random;

@XmlRootElement
@XmlType(propOrder = {"xor", "modificationFilter"})
public class LongXorModification extends VariableModification<Long> {

    private static final int MAX_XOR_MODIFIER = 256;

    private Long xor;

    public LongXorModification() {}

    public LongXorModification(Long bi) {
        this.xor = bi;
    }

    @Override
    protected Long modifyImplementationHook(final Long input) {
        return (input == null) ? xor : input ^ xor;
    }

    public Long getXor() {
        return xor;
    }

    public void setXor(Long xor) {
        this.xor = xor;
    }

    @Override
    public VariableModification<Long> getModifiedCopy() {
        Random r = new Random();
        if (r.nextBoolean()) {
            return new LongXorModification(xor + new Random().nextInt(MAX_XOR_MODIFIER));
        } else {
            return new LongXorModification(xor - new Random().nextInt(MAX_XOR_MODIFIER));
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 44 * hash + Objects.hashCode(this.xor);
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
        final LongXorModification other = (LongXorModification) obj;
        return Objects.equals(this.xor, other.xor);
    }
}
