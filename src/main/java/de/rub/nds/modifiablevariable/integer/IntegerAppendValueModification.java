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
import jakarta.xml.bind.annotation.XmlType;
import java.util.Objects;

@XmlRootElement
@XmlType(propOrder = {"appendValue", "modificationFilter"})
public class IntegerAppendValueModification extends VariableModification<Integer> {

    private static final int MAX_VALUE_MODIFIER = 256;

    private Integer appendValue;

    public IntegerAppendValueModification() {
        super();
    }

    public IntegerAppendValueModification(Integer appendValue) {
        super();
        this.appendValue = appendValue;
    }

    public IntegerAppendValueModification(IntegerAppendValueModification other) {
        super(other);
        appendValue = other.appendValue;
    }

    @Override
    public IntegerAppendValueModification createCopy() {
        return new IntegerAppendValueModification(this);
    }

    @Override
    protected Integer modifyImplementationHook(Integer input) {
        if (input == null) {
            input = 0;
        }
        return input << Integer.SIZE - Integer.numberOfLeadingZeros(appendValue) | appendValue;
    }

    public Integer getAppendValue() {
        return appendValue;
    }

    public void setAppendValue(Integer appendValue) {
        this.appendValue = appendValue;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + appendValue;
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
        IntegerAppendValueModification other = (IntegerAppendValueModification) obj;
        return Objects.equals(appendValue, other.appendValue);
    }

    @Override
    public String toString() {
        return "IntegerAppendValueModification{" + "appendValue=" + appendValue + '}';
    }
}
