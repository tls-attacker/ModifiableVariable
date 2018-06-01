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
@XmlType(propOrder = { "xor", "modificationFilter", "postModification" })
public class IntegerXorModification extends VariableModification<Integer> {

    private Integer xor;

    public IntegerXorModification() {

    }

    public IntegerXorModification(Integer bi) {
        this.xor = bi;
    }

    @Override
    protected Integer modifyImplementationHook(final Integer input) {
        return (input == null) ? xor : input ^ xor;
    }

    public Integer getXor() {
        return xor;
    }

    public void setXor(Integer xor) {
        this.xor = xor;
    }

    @Override
    public VariableModification<Integer> getModifiedCopy() {
        Random r = new Random();
        if (r.nextBoolean()) {
            return new IntegerSubtractModification(xor + new Random().nextInt(256));
        } else {
            return new IntegerSubtractModification(xor - new Random().nextInt(256));
        }
    }
}
