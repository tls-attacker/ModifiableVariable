/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.integer;

import de.rub.nds.modifiablevariable.VariableModification;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class IntegerShiftLeftModification extends VariableModification<Integer> {

    private static final int MAX_SHIFT_MODIFIER = 32;

    private int shift;

    public IntegerShiftLeftModification() {
        super();
    }

    public IntegerShiftLeftModification(int shift) {
        super();
        this.shift = shift;
    }

    public IntegerShiftLeftModification(IntegerShiftLeftModification other) {
        super(other);
        shift = other.shift;
    }

    @Override
    public IntegerShiftLeftModification createCopy() {
        return new IntegerShiftLeftModification(this);
    }

    @Override
    protected Integer modifyImplementationHook(Integer input) {
        return input == null ? 0 : input << shift % MAX_SHIFT_MODIFIER;
    }

    public int getShift() {
        return shift;
    }

    public void setShift(int shift) {
        this.shift = shift;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + shift;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        IntegerShiftLeftModification other = (IntegerShiftLeftModification) obj;
        return shift == other.shift;
    }

    @Override
    public String toString() {
        return "IntegerShiftLeftModification{" + "shift=" + shift + '}';
    }
}
