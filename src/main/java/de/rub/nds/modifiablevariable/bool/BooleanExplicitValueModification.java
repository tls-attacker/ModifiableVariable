/**
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Copyright 2014-2017 Ruhr University Bochum / Hackmanit GmbH
 *
 * Licensed under Apache License 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.bool;

import de.rub.nds.modifiablevariable.VariableModification;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Robert Merget <robert.merget@rub.de>
 */
@XmlRootElement
@XmlType(propOrder = { "explicitValue", "modificationFilter", "postModification" })
public class BooleanExplicitValueModification extends VariableModification<Boolean> {

    private boolean explicitValue;

    public BooleanExplicitValueModification() {
    }

    public BooleanExplicitValueModification(boolean explicitValue) {
        this.explicitValue = explicitValue;
    }

    @Override
    protected Boolean modifyImplementationHook(final Boolean input) {
        return explicitValue;
    }

    public boolean isExplicitValue() {
        return explicitValue;
    }

    public void setExplicitValue(boolean explicitValue) {
        this.explicitValue = explicitValue;
    }
}
