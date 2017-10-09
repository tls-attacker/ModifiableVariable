/**
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Copyright 2014-2017 Ruhr University Bochum / Hackmanit GmbH
 *
 * Licensed under Apache License 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.mlong;

import de.rub.nds.modifiablevariable.VariableModification;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(propOrder = { "xor", "modificationFilter", "postModification" })
public class LongXorModification extends VariableModification<Long> {

    private Long xor;

    public LongXorModification() {

    }

    public LongXorModification(Long bi) {
        this.xor = bi;
    }

    @Override
    protected Long modifyImplementationHook(final Long input) {
        return (input == null) ? xor : input ^ xor;
    }

    public Long getXor() {
        return xor;
    }

    public void setXor(Long xor) {
        this.xor = xor;
    }
}
