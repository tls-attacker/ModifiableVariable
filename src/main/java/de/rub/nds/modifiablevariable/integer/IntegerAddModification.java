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
@XmlType(propOrder = {"summand", "modificationFilter"})
public class IntegerAddModification extends VariableModification<Integer> {

    private static final int MAX_ADD_MODIFIER = 256;

    private Integer summand;

    public IntegerAddModification() {
        super();
    }

    public IntegerAddModification(Integer summand) {
        super();
        this.summand = summand;
    }

    public IntegerAddModification(IntegerAddModification other) {
        super(other);
        summand = other.summand;
    }

    @Override
    public IntegerAddModification createCopy() {
        return new IntegerAddModification(this);
    }

    @Override
    protected Integer modifyImplementationHook(Integer input) {
        return input == null ? summand : input + summand;
    }

    public Integer getSummand() {
        return summand;
    }

    public void setSummand(Integer summand) {
        this.summand = summand;
    }

    @Override
    public VariableModification<Integer> getModifiedCopy() {
        return new IntegerAddModification(summand + new Random().nextInt(MAX_ADD_MODIFIER));
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + summand;
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
        IntegerAddModification other = (IntegerAddModification) obj;
        return Objects.equals(summand, other.summand);
    }

    @Override
    public String toString() {
        return "IntegerAddModification{" + "summand=" + summand + '}';
    }
}
