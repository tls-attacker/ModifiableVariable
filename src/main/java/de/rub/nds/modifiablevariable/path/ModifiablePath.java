/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.path;

import static de.rub.nds.modifiablevariable.util.StringUtil.backslashEscapeString;

import de.rub.nds.modifiablevariable.VariableModification;
import de.rub.nds.modifiablevariable.string.ModifiableString;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

/** */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ModifiablePath extends ModifiableString {

    public ModifiablePath() {
        super();
    }

    public ModifiablePath(ModifiablePath other) {
        super(other);
    }

    @Override
    public ModifiablePath createCopy() {
        return new ModifiablePath(this);
    }

    @Override
    protected void createRandomModification() {
        VariableModification<String> vm = PathModificationFactory.createRandomModification(null);
        setModification(vm);
    }

    @Override
    public String toString() {
        return "ModifiablePath{originalValue=" + backslashEscapeString(originalValue) + "}";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ModifiablePath)) {
            return false;
        }

        ModifiablePath that = (ModifiablePath) obj;

        return getValue() != null ? getValue().equals(that.getValue()) : that.getValue() == null;
    }
}
