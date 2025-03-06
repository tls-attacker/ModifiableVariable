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
import java.util.Objects;

@XmlRootElement
public class IntegerSubtractModification extends VariableModification<Integer> {

    private Integer subtrahend;

    public IntegerSubtractModification() {
        super();
    }

    public IntegerSubtractModification(Integer subtrahend) {
        super();
        this.subtrahend = subtrahend;
    }

    public IntegerSubtractModification(IntegerSubtractModification other) {
        super(other);
        subtrahend = other.subtrahend;
    }

    @Override
    public IntegerSubtractModification createCopy() {
        return new IntegerSubtractModification(this);
    }

    @Override
    protected Integer modifyImplementationHook(Integer input) {
        return input - subtrahend;
    }

    public Integer getSubtrahend() {
        return subtrahend;
    }

    public void setSubtrahend(Integer subtrahend) {
        this.subtrahend = subtrahend;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + subtrahend;
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
        IntegerSubtractModification other = (IntegerSubtractModification) obj;
        return Objects.equals(subtrahend, other.subtrahend);
    }

    @Override
    public String toString() {
        return "IntegerSubtractModification{" + "subtrahend=" + subtrahend + '}';
    }
}
