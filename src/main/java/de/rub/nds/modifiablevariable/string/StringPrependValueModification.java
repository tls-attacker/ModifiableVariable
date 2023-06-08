/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Copyright 2014-2022 Ruhr University Bochum, Paderborn University, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.string;

import de.rub.nds.modifiablevariable.VariableModification;
import de.rub.nds.modifiablevariable.util.IllegalStringAdapter;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Objects;

/** Modification that prepends a string to the original value. */
@XmlRootElement
@XmlType(propOrder = {"prependValue", "modificationFilter"})
public class StringPrependValueModification extends VariableModification<String> {

    @XmlJavaTypeAdapter(IllegalStringAdapter.class)
    private String prependValue;

    public StringPrependValueModification() {}

    public StringPrependValueModification(final String prependValue) {
        this.prependValue = prependValue;
    }

    @Override
    protected String modifyImplementationHook(final String input) {
        return this.prependValue + input;
    }

    public String getPrependValue() {
        return this.prependValue;
    }

    public void setPrependValue(final String prependValue) {
        this.prependValue = prependValue;
    }

    @Override
    public VariableModification<String> getModifiedCopy() {
        return new StringPrependValueModification(prependValue);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + Objects.hashCode(this.prependValue);
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
        final StringPrependValueModification other = (StringPrependValueModification) obj;
        return Objects.equals(this.prependValue, other.getPrependValue());
    }
}
