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

@XmlRootElement
@XmlType(propOrder = "index")
public class LongExplicitValueFromFileModification extends LongExplicitValueModification {
    private int index;

    public LongExplicitValueFromFileModification() {
        super();
    }

    public LongExplicitValueFromFileModification(int index, Long explicitValue) {
        super(explicitValue);
        this.index = index;
    }

    public LongExplicitValueFromFileModification(LongExplicitValueFromFileModification other) {
        super(other);
        index = other.index;
    }

    @Override
    public LongExplicitValueFromFileModification createCopy() {
        return new LongExplicitValueFromFileModification(this);
    }

    public int getIndex() {
        return index;
    }

    @Override
    public VariableModification<Long> getModifiedCopy() {
        throw new UnsupportedOperationException(
                "Cannot set modify Value of LongExplicitValueFromFileModification");
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + index;
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
        LongExplicitValueFromFileModification other = (LongExplicitValueFromFileModification) obj;
        if (index != other.index) {
            return false;
        }
        return Objects.equals(explicitValue, other.explicitValue);
    }

    @Override
    public String toString() {
        return "LongExplicitValueFromFileModification{"
                + "index="
                + index
                + ", explicitValue="
                + explicitValue
                + '}';
    }
}
