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
import java.util.Random;

@XmlRootElement
@XmlType(propOrder = {"shift", "modificationFilter"})
public class LongShiftRightModification extends VariableModification<Long> {

    private static final int MAX_SHIFT_MODIFIER = 64;

    private int shift;

    public LongShiftRightModification() {
        super();
    }

    public LongShiftRightModification(int shift) {
        super();
        this.shift = shift;
    }

    public LongShiftRightModification(LongShiftRightModification other) {
        super(other);
        shift = other.shift;
    }

    @Override
    public LongShiftRightModification createCopy() {
        return new LongShiftRightModification(this);
    }

    @Override
    protected Long modifyImplementationHook(Long input) {
        return input == null ? 0L : input >> shift % MAX_SHIFT_MODIFIER;
    }

    public int getShift() {
        return shift;
    }

    public void setShift(int shift) {
        this.shift = shift;
    }

    @Override
    public VariableModification<Long> getModifiedCopy() {
        Random r = new Random();
        int newShift;
        if (r.nextBoolean()) {
            newShift = shift + r.nextInt(MAX_SHIFT_MODIFIER);
        } else {
            newShift = shift - r.nextInt(MAX_SHIFT_MODIFIER);
        }
        if (newShift < 0) {
            newShift = MAX_SHIFT_MODIFIER - 1;
        } else if (newShift > MAX_SHIFT_MODIFIER - 1) {
            newShift = 0;
        }

        return new LongShiftRightModification(newShift);
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
        LongShiftRightModification other = (LongShiftRightModification) obj;
        return Objects.equals(shift, other.shift);
    }

    @Override
    public String toString() {
        return "LongShiftRightModification{" + "shift=" + shift + '}';
    }
}
