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
@XmlType(propOrder = {"explicitValue", "modificationFilter"})
public class LongExplicitValueModification extends VariableModification<Long> {

    private static final int MAX_EXPLICIT_MODIFIER = 256;

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
        return explicitValue;
    }

    public Long getExplicitValue() {
        return explicitValue;
    }

    public void setExplicitValue(Long explicitValue) {
        this.explicitValue = explicitValue;
    }

    @Override
    public VariableModification<Long> getModifiedCopy() {
        Random r = new Random();
        if (r.nextBoolean()) {
            return new LongExplicitValueModification(
                    explicitValue + r.nextInt(MAX_EXPLICIT_MODIFIER));
        } else {
            return new LongExplicitValueModification(
                    explicitValue - r.nextInt(MAX_EXPLICIT_MODIFIER));
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
        LongExplicitValueModification other = (LongExplicitValueModification) obj;
        return Objects.equals(explicitValue, other.explicitValue);
    }

    @Override
    public String toString() {
        return "LongExplicitValueModification{" + "explicitValue=" + explicitValue + '}';
    }
}
