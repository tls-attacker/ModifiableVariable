/**
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Copyright 2014-2017 Ruhr University Bochum / Hackmanit GmbH
 *
 * Licensed under Apache License 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.integer;

import de.rub.nds.modifiablevariable.VariableModification;
import java.util.Random;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(propOrder = { "explicitValue", "modificationFilter", "postModification" })
public class IntegerExplicitValueModification extends VariableModification<Integer> {

    private Integer explicitValue;

    public IntegerExplicitValueModification() {

    }

    public IntegerExplicitValueModification(Integer bi) {
        this.explicitValue = bi;
    }

    @Override
    protected Integer modifyImplementationHook(final Integer input) {
        return explicitValue;
    }

    public Integer getExplicitValue() {
        return explicitValue;
    }

    public void setExplicitValue(Integer explicitValue) {
        this.explicitValue = explicitValue;
    }

    @Override
    public VariableModification<Integer> getModifiedCopy() {
        Random r = new Random();
        if (r.nextBoolean()) {
            return new IntegerExplicitValueModification(explicitValue + r.nextInt(256));
        } else {
            return new IntegerExplicitValueModification(explicitValue - r.nextInt(256));
        }
    }
}
