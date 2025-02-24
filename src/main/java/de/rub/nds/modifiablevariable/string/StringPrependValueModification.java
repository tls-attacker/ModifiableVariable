/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.string;

import static de.rub.nds.modifiablevariable.util.StringUtil.backslashEscapeString;

import de.rub.nds.modifiablevariable.VariableModification;
import de.rub.nds.modifiablevariable.util.IllegalStringAdapter;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Objects;
import java.util.Random;

/** Modification that prepends a string to the original value. */
@XmlRootElement
@XmlType(propOrder = {"prependValue", "modificationFilter"})
public class StringPrependValueModification extends VariableModification<String> {

    private static final int MAX_EXPLICIT_VALUE = 256;

    @XmlJavaTypeAdapter(IllegalStringAdapter.class)
    private String prependValue;

    public StringPrependValueModification() {
        super();
    }

    public StringPrependValueModification(String prependValue) {
        super();
        this.prependValue = prependValue;
    }

    public StringPrependValueModification(StringPrependValueModification other) {
        super(other);
        prependValue = other.prependValue;
    }

    @Override
    public StringPrependValueModification createCopy() {
        return new StringPrependValueModification(this);
    }

    @Override
    protected String modifyImplementationHook(String input) {
        return input != null ? prependValue + input : prependValue;
    }

    public String getPrependValue() {
        return prependValue;
    }

    public void setPrependValue(String prependValue) {
        this.prependValue = prependValue;
    }

    @Override
    public VariableModification<String> getModifiedCopy() {
        Random r = new Random();
        int index = r.nextInt(prependValue.length());
        char randomChar = (char) r.nextInt(MAX_EXPLICIT_VALUE);
        StringBuilder modifiedString = new StringBuilder(prependValue);
        modifiedString.setCharAt(index, randomChar);
        return new StringPrependValueModification(modifiedString.toString());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(prependValue);;
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
        StringPrependValueModification other = (StringPrependValueModification) obj;
        return Objects.equals(prependValue, other.prependValue);
    }

    @Override
    public String toString() {
        return "StringPrependValueModification{"
                + "prependValue='"
                + backslashEscapeString(prependValue)
                + '\''
                + '}';
    }
}
