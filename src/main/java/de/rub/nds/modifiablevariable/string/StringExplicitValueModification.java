/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
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
import java.util.Random;

/** */
@XmlRootElement
@XmlType(propOrder = {"explicitValue", "modificationFilter"})
public class StringExplicitValueModification extends VariableModification<String> {

    private static final int MAX_EXPLICIT_VALUE = 256;

    @XmlJavaTypeAdapter(IllegalStringAdapter.class)
    protected String explicitValue;

    public StringExplicitValueModification() {
        super();
    }

    public StringExplicitValueModification(String explicitValue) {
        super();
        this.explicitValue = explicitValue;
    }

    public StringExplicitValueModification(StringExplicitValueModification other) {
        super(other);
        explicitValue = other.explicitValue;
    }

    @Override
    public StringExplicitValueModification createCopy() {
        return new StringExplicitValueModification(this);
    }

    @Override
    protected String modifyImplementationHook(String input) {
        return explicitValue;
    }

    public String getExplicitValue() {
        return explicitValue;
    }

    public void setExplicitValue(String explicitValue) {
        this.explicitValue = explicitValue;
    }

    @Override
    public VariableModification<String> getModifiedCopy() {
        if (explicitValue.isEmpty()) {
            return this;
        }
        Random r = new Random();
        int index = r.nextInt(explicitValue.length());
        char randomChar = (char) r.nextInt(MAX_EXPLICIT_VALUE);
        StringBuilder modifiedString = new StringBuilder(explicitValue);
        modifiedString.setCharAt(index, randomChar);
        return new StringExplicitValueModification(modifiedString.toString());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + explicitValue.hashCode();
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
        StringExplicitValueModification other = (StringExplicitValueModification) obj;
        return Objects.equals(explicitValue, other.explicitValue);
    }
}
