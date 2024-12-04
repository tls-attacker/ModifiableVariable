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
@XmlType(propOrder = {"appendValue", "modificationFilter"})
public class LongAppendValueModification extends VariableModification<Long> {

    private static final int MAX_VALUE_MODIFIER = 256;

    private Long appendValue;

    public LongAppendValueModification() {
        super();
    }

    public LongAppendValueModification(Long appendValue) {
        super();
        this.appendValue = appendValue;
    }

    public LongAppendValueModification(LongAppendValueModification other) {
        super(other);
        appendValue = other.appendValue;
    }

    @Override
    public LongAppendValueModification createCopy() {
        return new LongAppendValueModification(this);
    }

    @Override
    protected Long modifyImplementationHook(Long input) {
        if (input == null) {
            input = 0L;
        }
        return input << Long.SIZE - Long.numberOfLeadingZeros(appendValue) | appendValue;
    }

    public Long getAppendValue() {
        return appendValue;
    }

    public void setAppendValue(Long appendValue) {
        this.appendValue = appendValue;
    }

    @Override
    public VariableModification<Long> getModifiedCopy() {
        return new LongAppendValueModification(
                appendValue + new Random().nextInt(MAX_VALUE_MODIFIER));
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + appendValue.hashCode();
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
        LongAppendValueModification other = (LongAppendValueModification) obj;
        return Objects.equals(appendValue, other.appendValue);
    }
}
