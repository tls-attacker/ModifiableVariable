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
@XmlType(propOrder = { "shift", "modificationFilter", "postModification" })
public class IntegerShiftRightModification extends VariableModification<Integer> {

    private int shift;

    public IntegerShiftRightModification() {

    }

    public IntegerShiftRightModification(int shift) {
        this.shift = shift;
    }

    @Override
    protected Integer modifyImplementationHook(final Integer input) {
        return (input == null) ? 0 : input >> shift;
    }

    public int getShift() {
        return shift;
    }

    public void setShift(int shift) {
        this.shift = shift;
    }

    @Override
    public VariableModification<Integer> getModifiedCopy() {
        Random r = new Random();
        int newShift;
        if (r.nextBoolean()) {
            newShift = shift + r.nextInt(32);
        } else {
            newShift = shift - r.nextInt(32);
        }
        if (newShift < 0) {
            newShift = 31;
        } else if (newShift > 31) {
            newShift = 0;
        }
        return new IntegerShiftRightModification(newShift);
    }
}
