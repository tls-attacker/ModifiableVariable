/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, and Hackmanit GmbH
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
@XmlType(propOrder = {"subtrahend", "modificationFilter"})
public class IntegerSubtractModification extends VariableModification<Integer> {

    private static final int MAX_SUBTRACT_MODIFIER = 256;

    private Integer subtrahend;

    public IntegerSubtractModification() {}

    public IntegerSubtractModification(Integer bi) {
        this.subtrahend = bi;
    }

    @Override
    protected Integer modifyImplementationHook(final Integer input) {
        return (input == null) ? -subtrahend : input - subtrahend;
    }

    public Integer getSubtrahend() {
        return subtrahend;
    }

    public void setSubtrahend(Integer subtrahend) {
        this.subtrahend = subtrahend;
    }

    @Override
    public VariableModification<Integer> getModifiedCopy() {
        return new IntegerSubtractModification(
                subtrahend + new Random().nextInt(MAX_SUBTRACT_MODIFIER));
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.subtrahend);
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
        final IntegerSubtractModification other = (IntegerSubtractModification) obj;
        if (!Objects.equals(this.subtrahend, other.subtrahend)) {
            return false;
        }
        return true;
    }
}
