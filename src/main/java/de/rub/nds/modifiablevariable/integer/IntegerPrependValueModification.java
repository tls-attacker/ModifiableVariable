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
import java.util.Random;

@XmlRootElement
@XmlType(propOrder = {"prependValue", "modificationFilter"})
public class IntegerPrependValueModification extends VariableModification<Integer> {

    private static final int MAX_VALUE_MODIFIER = 256;

    private Integer prependValue;

    public IntegerPrependValueModification() {
        super();
    }

    public IntegerPrependValueModification(Integer prependValue) {
        super();
        this.prependValue = prependValue;
    }

    public IntegerPrependValueModification(IntegerPrependValueModification other) {
        super(other);
        prependValue = other.prependValue;
    }

    @Override
    public IntegerPrependValueModification createCopy() {
        return new IntegerPrependValueModification(this);
    }

    @Override
    protected Integer modifyImplementationHook(Integer input) {
        if (input == null) {
            input = 0;
        }

        return prependValue << Integer.SIZE - Integer.numberOfLeadingZeros(input) | input;
    }

    public Integer getPrependValue() {
        return prependValue;
    }

    public void setPrependValue(Integer prependValue) {
        this.prependValue = prependValue;
    }

    @Override
    public VariableModification<Integer> getModifiedCopy() {
        return new IntegerPrependValueModification(
                prependValue + new Random().nextInt(MAX_VALUE_MODIFIER));
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + prependValue;
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
        IntegerPrependValueModification other = (IntegerPrependValueModification) obj;
        return Objects.equals(prependValue, other.prependValue);
    }
}
