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
import java.util.Objects;

@XmlRootElement
public class LongSubtractModification extends VariableModification<Long> {

    private Long subtrahend;

    public LongSubtractModification() {
        super();
    }

    public LongSubtractModification(Long subtrahend) {
        super();
        this.subtrahend = subtrahend;
    }

    public LongSubtractModification(LongSubtractModification other) {
        super(other);
        subtrahend = other.subtrahend;
    }

    @Override
    public LongSubtractModification createCopy() {
        return new LongSubtractModification(this);
    }

    @Override
    protected Long modifyImplementationHook(Long input) {
        if (input == null) {
            return null;
        }
        return input - subtrahend;
    }

    public Long getSubtrahend() {
        return subtrahend;
    }

    public void setSubtrahend(Long subtrahend) {
        this.subtrahend = subtrahend;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(subtrahend);
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
        LongSubtractModification other = (LongSubtractModification) obj;
        return Objects.equals(subtrahend, other.subtrahend);
    }

    @Override
    public String toString() {
        return "LongSubtractModification{" + "subtrahend=" + subtrahend + '}';
    }
}
