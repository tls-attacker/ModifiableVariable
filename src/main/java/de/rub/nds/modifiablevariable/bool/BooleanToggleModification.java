/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.bool;

import de.rub.nds.modifiablevariable.VariableModification;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(propOrder = {"modificationFilter"})
public class BooleanToggleModification extends VariableModification<Boolean> {

    public BooleanToggleModification() {}

    @Override
    protected Boolean modifyImplementationHook(Boolean input) {
        if (input == null) {
            input = Boolean.FALSE;
        }
        return !input;
    }

    @Override
    public VariableModification<Boolean> getModifiedCopy() {
        return new BooleanToggleModification();
    }

    @Override
    public int hashCode() {
        int hash = 7;
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
        return true;
    }

    @Override
    public String toString() {
        return "BooleanToggleModification{" + '}';
    }
}
