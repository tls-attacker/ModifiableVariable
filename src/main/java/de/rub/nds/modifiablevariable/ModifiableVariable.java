/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable;

import jakarta.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The base abstract class for modifiable variables, including the getValue function. The class
 * needs to be defined transient to allow propOrder definition in subclasses, see: <a
 * href="http://blog.bdoughan.com/2011/06/ignoring-inheritance-with-xmltransient.html">docs</a>
 *
 * @param <E>
 */
@XmlTransient
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class ModifiableVariable<E> implements Serializable {

    @XmlElementWrapper
    @XmlAnyElement(lax = true)
    private LinkedList<VariableModification<E>> modifications;

    protected E assertEquals;

    protected ModifiableVariable() {
        super();
    }

    protected ModifiableVariable(ModifiableVariable<E> other) {
        super();
        if (other.modifications != null) {
            modifications = new LinkedList<>();
            for (VariableModification<E> item : other.modifications) {
                modifications.add(item != null ? item.createCopy() : null);
            }
        }
        // Warning: Make sure to copy assertEquals in subclass correctly
        assertEquals = other.assertEquals;
    }

    /** Sets multiple modifications, all previously set modifications are removed */
    public void setModifications(List<VariableModification<E>> modifications) {
        this.modifications = new LinkedList<>(modifications);
    }

    /** Sets multiple modifications, all previously set modifications are removed */
    @SafeVarargs
    public final void setModifications(VariableModification<E>... modifications) {
        this.modifications = new LinkedList<>(List.of(modifications));
    }

    /** Removes all modifications */
    public void clearModifications() {
        modifications = null;
    }

    /** Adds a modification to this modifiable variable */
    public void addModification(VariableModification<E> modification) {
        if (modification != null) {
            if (modifications == null) {
                modifications = new LinkedList<>();
            }
            modifications.add(modification);
        }
    }

    /** Returns all modifications that are set for this modifiable variable */
    public LinkedList<VariableModification<E>> getModifications() {
        return modifications;
    }

    public E getValue() {
        return getModifiedValue();
    }

    private E getModifiedValue() {
        E resultValue = getOriginalValue();
        if (modifications != null) {
            for (VariableModification<E> modification : modifications) {
                resultValue = modification.modify(resultValue);
            }
        }
        return resultValue;
    }

    public abstract E getOriginalValue();

    public abstract void setOriginalValue(E originalValue);

    public abstract ModifiableVariable<E> createCopy();

    public abstract boolean isOriginalValueModified();

    public abstract boolean validateAssertions();

    public boolean containsAssertion() {
        return assertEquals != null;
    }

    protected String innerToString() {
        StringBuilder result = new StringBuilder();
        if (modifications != null) {
            result.append(", modifications=[")
                    .append(
                            modifications.stream()
                                    .map(Object::toString)
                                    .collect(Collectors.joining(", ")))
                    .append("]");
        }

        if (assertEquals != null) {
            result.append(", assertEquals=").append(assertEquals);
        }

        return result.toString();
    }
}
