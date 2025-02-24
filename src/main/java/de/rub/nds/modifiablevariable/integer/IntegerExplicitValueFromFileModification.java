/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.integer;

import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import java.util.Objects;

@XmlRootElement
@XmlType(propOrder = "index")
public class IntegerExplicitValueFromFileModification extends IntegerExplicitValueModification {
    private int index;

    public IntegerExplicitValueFromFileModification() {
        super();
    }

    public IntegerExplicitValueFromFileModification(int index, Integer explicitValue) {
        super(explicitValue);
        this.index = index;
    }

    public IntegerExplicitValueFromFileModification(
            IntegerExplicitValueFromFileModification other) {
        super(other);
        index = other.index;
    }

    @Override
    public IntegerExplicitValueFromFileModification createCopy() {
        return new IntegerExplicitValueFromFileModification(this);
    }

    public int getIndex() {
        return index;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + index;
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
        IntegerExplicitValueFromFileModification other =
                (IntegerExplicitValueFromFileModification) obj;
        if (index != other.index) {
            return false;
        }
        return Objects.equals(explicitValue, other.explicitValue);
    }

    @Override
    public String toString() {
        return "IntegerExplicitValueFromFileModification{"
                + "index="
                + index
                + ", explicitValue="
                + explicitValue
                + '}';
    }
}
