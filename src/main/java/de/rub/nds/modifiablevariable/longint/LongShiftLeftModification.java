/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.longint;

import de.rub.nds.modifiablevariable.VariableModification;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import java.util.Objects;

@XmlRootElement
@XmlType(propOrder = {"shift", "modificationFilter"})
public class LongShiftLeftModification extends VariableModification<Long> {

    private static final int MAX_SHIFT_MODIFIER = 64;

    private int shift;

    public LongShiftLeftModification() {
        super();
    }

    public LongShiftLeftModification(int shift) {
        super();
        this.shift = shift;
    }

    public LongShiftLeftModification(LongShiftLeftModification other) {
        super(other);
        shift = other.shift;
    }

    @Override
    public LongShiftLeftModification createCopy() {
        return new LongShiftLeftModification(this);
    }

    @Override
    protected Long modifyImplementationHook(Long input) {
        return input == null ? 0L : input << shift % MAX_SHIFT_MODIFIER;
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
        LongShiftLeftModification other = (LongShiftLeftModification) obj;
        return Objects.equals(shift, other.shift);
    }

    @Override
    public String toString() {
        return "LongShiftLeftModification{" + "shift=" + shift + '}';
    }
}
