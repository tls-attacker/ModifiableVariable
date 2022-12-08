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

/** Modification that appends a string to the original value. */
@XmlRootElement
@XmlType(propOrder = {"appendValue", "modificationFilter"})
public class StringAppendValueModification extends VariableModification<String> {

    @XmlJavaTypeAdapter(IllegalStringAdapter.class)
    private String appendValue;

    public StringAppendValueModification() {}

    public StringAppendValueModification(final String appendValue) {
        this.appendValue = appendValue;
    }

    @Override
    protected String modifyImplementationHook(final String input) {
        return input + this.appendValue;
    }

    public String getAppendValue() {
        return this.appendValue;
    }

    public void setAppendValue(final String appendValue) {
        this.appendValue = appendValue;
    }

    @Override
    public VariableModification<String> getModifiedCopy() {
        return new StringAppendValueModification(appendValue);
    }

    @Override
    public int hashCode() {
        int hash = 4;
        hash = 83 * hash + Objects.hashCode(this.appendValue);
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
        final StringAppendValueModification other = (StringAppendValueModification) obj;
        return Objects.equals(this.appendValue, other.getAppendValue());
    }
}
