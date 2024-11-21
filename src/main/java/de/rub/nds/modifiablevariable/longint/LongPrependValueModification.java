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
@XmlType(propOrder = {"prependValue", "modificationFilter"})
public class LongPrependValueModification extends VariableModification<Long> {

    private static final int MAX_VALUE_MODIFIER = 256;

    private Long prependValue;

    public LongPrependValueModification() {}

    public LongPrependValueModification(Long prependValue) {
        this.prependValue = prependValue;
    }

    @Override
    protected Long modifyImplementationHook(Long input) {
        if (input == null) {
            input = 0L;
        }
        return (prependValue << (Long.SIZE - Long.numberOfLeadingZeros((input)))) | input;
    }

    public Long getPrependValue() {
        return prependValue;
    }

    public void setPrependValue(Long prependValue) {
        this.prependValue = prependValue;
    }

    @Override
    public VariableModification<Long> getModifiedCopy() {
        return new LongPrependValueModification(prependValue + new Random().nextInt(MAX_VALUE_MODIFIER));
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 46 * hash + Objects.hashCode(this.prependValue);
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
        final LongPrependValueModification other = (LongPrependValueModification) obj;
        return Objects.equals(this.prependValue, other.prependValue);
    }
}
