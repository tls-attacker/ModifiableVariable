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

@XmlRootElement
public class BooleanToggleModification extends VariableModification<Boolean> {

    public BooleanToggleModification() {
        super();
    }

    public BooleanToggleModification(BooleanToggleModification other) {
        super(other);
    }

    @Override
    public BooleanToggleModification createCopy() {
        return new BooleanToggleModification(this);
    }

    @Override
    protected Boolean modifyImplementationHook(Boolean input) {
        return !input;
    }

    @Override
    public int hashCode() {
        return 7;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        return getClass() == obj.getClass();
    }

    @Override
    public String toString() {
        return "BooleanToggleModification{" + '}';
    }
}
