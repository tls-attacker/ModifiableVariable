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
public class LongExplicitValueModification extends VariableModification<Long> {

    protected Long explicitValue;

    public LongExplicitValueModification() {
        super();
    }

    public LongExplicitValueModification(Long explicitValue) {
        super();
        this.explicitValue = explicitValue;
    }

    public LongExplicitValueModification(LongExplicitValueModification other) {
        super(other);
        explicitValue = other.explicitValue;
    }

    @Override
    public LongExplicitValueModification createCopy() {
        return new LongExplicitValueModification(this);
    }

    @Override
    protected Long modifyImplementationHook(Long input) {
        if (input == null) {
            throw new NullPointerException("original value must not be null");
        }
        return explicitValue;
    }

    public Long getExplicitValue() {
        return explicitValue;
    }

    public void setExplicitValue(Long explicitValue) {
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
        LongExplicitValueModification other = (LongExplicitValueModification) obj;
        return Objects.equals(explicitValue, other.explicitValue);
    }

    @Override
    public String toString() {
        return "LongExplicitValueModification{" + "explicitValue=" + explicitValue + '}';
    }
}
