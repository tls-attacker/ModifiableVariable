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
import de.rub.nds.modifiablevariable.integer.IntegerExplicitValueModification;
import java.util.Random;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(propOrder = {"explicitValue", "modificationFilter", "postModification"})
public class LongExplicitValueModification extends VariableModification<Long> {

    private Long explicitValue;

    public LongExplicitValueModification() {

    }

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
    protected VariableModification<Long> getModifiedCopy() {
        Random r = new Random();
        if (r.nextBoolean()) {
            return new LongExplicitValueModification(explicitValue + r.nextInt(256));
        } else {
            return new LongExplicitValueModification(explicitValue - r.nextInt(256));
        }
    }
}
