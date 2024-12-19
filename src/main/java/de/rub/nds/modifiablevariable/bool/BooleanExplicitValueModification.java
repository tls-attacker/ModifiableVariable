/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.bool;

import de.rub.nds.modifiablevariable.VariableModification;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(propOrder = {"explicitValue", "modificationFilter"})
public class BooleanExplicitValueModification extends VariableModification<Boolean> {

    private boolean explicitValue;

    public BooleanExplicitValueModification() {
        super();
    }

    public BooleanExplicitValueModification(boolean explicitValue) {
        super();
        this.explicitValue = explicitValue;
    }

    public BooleanExplicitValueModification(BooleanExplicitValueModification other) {
        super(other);
        explicitValue = other.explicitValue;
    }

    @Override
    public BooleanExplicitValueModification createCopy() {
        return new BooleanExplicitValueModification(this);
    }

    @Override
    protected Boolean modifyImplementationHook(Boolean input) {
        return explicitValue;
    }

    public boolean getExplicitValue() {
        return explicitValue;
    }

    public void setExplicitValue(boolean explicitValue) {
        this.explicitValue = explicitValue;
    }

    @Override
    public VariableModification<Boolean> getModifiedCopy() {
        return new BooleanExplicitValueModification(!explicitValue);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + (explicitValue ? 1231 : 1237);
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
        BooleanExplicitValueModification other = (BooleanExplicitValueModification) obj;
        return explicitValue == other.explicitValue;
    }

    @Override
    public String toString() {
        return "BooleanExplicitValueModification{" + "explicitValue=" + explicitValue + '}';
    }
}
