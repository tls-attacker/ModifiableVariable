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
@XmlType(propOrder = {"explicitValue", "modificationFilter"})
public class IntegerExplicitValueModification extends VariableModification<Integer> {

    private static final int MAX_VALUE_MODIFIER = 256;

    private Integer explicitValue;

    public IntegerExplicitValueModification() {}

    public IntegerExplicitValueModification(Integer bi) {
        this.explicitValue = bi;
    }

    @Override
    protected Integer modifyImplementationHook(final Integer input) {
        return explicitValue;
    }

    public Integer getExplicitValue() {
        return explicitValue;
    }

    public void setExplicitValue(Integer explicitValue) {
        this.explicitValue = explicitValue;
    }

    @Override
    public VariableModification<Integer> getModifiedCopy() {
        Random r = new Random();
        if (r.nextBoolean()) {
            return new IntegerExplicitValueModification(
                    explicitValue + r.nextInt(MAX_VALUE_MODIFIER));
        } else {
            return new IntegerExplicitValueModification(
                    explicitValue - r.nextInt(MAX_VALUE_MODIFIER));
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.explicitValue);
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
        final IntegerExplicitValueModification other = (IntegerExplicitValueModification) obj;
        if (!Objects.equals(this.explicitValue, other.explicitValue)) {
            return false;
        }
        return true;
    }
}
