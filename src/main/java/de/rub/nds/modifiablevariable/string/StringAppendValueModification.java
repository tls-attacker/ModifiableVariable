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

/** Modification that appends a string to the original value. */
@XmlRootElement
@XmlType(propOrder = {"appendValue", "modificationFilter"})
public class StringAppendValueModification extends VariableModification<String> {

    private static final int MAX_EXPLICIT_VALUE = 256;

    @XmlJavaTypeAdapter(IllegalStringAdapter.class)
    private String appendValue;

    public StringAppendValueModification() {
        super();
    }

    public StringAppendValueModification(String appendValue) {
        super();
        this.appendValue = appendValue;
    }

    @Override
    protected String modifyImplementationHook(String input) {
        return input + appendValue;
    }

    public String getAppendValue() {
        return appendValue;
    }

    public void setAppendValue(String appendValue) {
        this.appendValue = appendValue;
    }

    @Override
    public VariableModification<String> getModifiedCopy() {
        Random r = new Random();
        int index = r.nextInt(appendValue.length());
        char randomChar = (char) r.nextInt(MAX_EXPLICIT_VALUE);
        StringBuilder modifiedString = new StringBuilder(appendValue);
        modifiedString.setCharAt(index, randomChar);
        return new StringAppendValueModification(modifiedString.toString());
    }

    @Override
    public VariableModification<String> createCopy() {
        return new StringAppendValueModification(appendValue);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + appendValue.hashCode();
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
        StringAppendValueModification other = (StringAppendValueModification) obj;
        return Objects.equals(appendValue, other.appendValue);
    }
}
