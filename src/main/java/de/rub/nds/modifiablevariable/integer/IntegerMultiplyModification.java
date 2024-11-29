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
@XmlType(propOrder = {"factor", "modificationFilter"})
public class IntegerMultiplyModification extends VariableModification<Integer> {

    private static final int MAX_FACTOR_MODIFIER = 256;

    private Integer factor;

    public IntegerMultiplyModification() {
        super();
    }

    public IntegerMultiplyModification(Integer factor) {
        super();
        this.factor = factor;
    }

    @Override
    protected Integer modifyImplementationHook(Integer input) {
        return input == null ? 0 : input * factor;
    }

    public Integer getFactor() {
        return factor;
    }

    public void setFactor(Integer factor) {
        this.factor = factor;
    }

    @Override
    public VariableModification<Integer> getModifiedCopy() {
        Random r = new Random();

        return new IntegerMultiplyModification(factor + r.nextInt(MAX_FACTOR_MODIFIER));
    }

    @Override
    public VariableModification<Integer> createCopy() {
        return new IntegerMultiplyModification(factor);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + factor;
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
        IntegerMultiplyModification other = (IntegerMultiplyModification) obj;
        return Objects.equals(factor, other.factor);
    }
}
