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
@XmlType(propOrder = { "summand", "modificationFilter", "postModification" })
public class IntegerAddModification extends VariableModification<Integer> {

    private Integer summand;

    public IntegerAddModification() {

    }

    public IntegerAddModification(Integer bi) {
        this.summand = bi;
    }

    @Override
    protected Integer modifyImplementationHook(Integer input) {
        return (input == null) ? summand : input + summand;
    }

    public Integer getSummand() {
        return summand;
    }

    public void setSummand(Integer summand) {
        this.summand = summand;
    }

    @Override
    public VariableModification<Integer> getModifiedCopy() {
        return new IntegerAddModification(summand + new Random().nextInt(256));
    }
}
