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

    private Long explicitValue;

    public LongExplicitValueModification() {}

    public LongExplicitValueModification(Long bi) {
        this.explicitValue = bi;
    }

    @Override
    protected Long modifyImplementationHook(final Long input) {
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
        int hash = 3;
        hash = 43 * hash + Objects.hashCode(this.explicitValue);
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
        final LongExplicitValueModification other = (LongExplicitValueModification) obj;
        if (!Objects.equals(this.explicitValue, other.explicitValue)) {
            return false;
        }
        return true;
    }
}
