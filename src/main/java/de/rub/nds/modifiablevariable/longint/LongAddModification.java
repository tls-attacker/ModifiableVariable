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
public class LongAddModification extends VariableModification<Long> {

    private Long summand;

    public LongAddModification() {
        super();
    }

    public LongAddModification(Long summand) {
        super();
        this.summand = summand;
    }

    public LongAddModification(LongAddModification other) {
        super(other);
        summand = other.summand;
    }

    @Override
    public LongAddModification createCopy() {
        return new LongAddModification(this);
    }

    @Override
    protected Long modifyImplementationHook(Long input) {
        return input + summand;
    }

    public Long getSummand() {
        return summand;
    }

    public void setSummand(Long summand) {
        this.summand = summand;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(summand);
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
        LongAddModification other = (LongAddModification) obj;
        return Objects.equals(summand, other.summand);
    }

    @Override
    public String toString() {
        return "LongAddModification{" + "summand=" + summand + '}';
    }
}
