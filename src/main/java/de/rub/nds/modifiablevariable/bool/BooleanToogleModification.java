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
@XmlType(propOrder = { "xor", "modificationFilter", "postModification" })
public class BooleanToogleModification extends VariableModification<Boolean> {

    public BooleanToogleModification() {
    }

    @Override
    protected Boolean modifyImplementationHook(Boolean input) {
        if (input == null) {
            input = Boolean.FALSE;
        }
        return !input;
    }
}
