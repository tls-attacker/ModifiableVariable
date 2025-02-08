/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.path;

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
public class PathPrependValueModification extends VariableModification<String> {

    private static final int MAX_EXPLICIT_VALUE = 256;

    @XmlJavaTypeAdapter(IllegalStringAdapter.class)
    private String prependValue;

    public PathPrependValueModification() {
        super();
    }

    public PathPrependValueModification(String prependValue) {
        super();
        this.prependValue = prependValue;
    }

    public PathPrependValueModification(PathPrependValueModification other) {
        super(other);
        prependValue = other.prependValue;
    }

    @Override
    public PathPrependValueModification createCopy() {
        return new PathPrependValueModification(this);
    }

    @Override
    protected String modifyImplementationHook(String input) {
        if (input == null) {
            return null;
        }
        if (input.startsWith("/")) {
            return "/" + prependValue + input;
        }
        return prependValue + "/" + input;
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
        return new PathPrependValueModification(modifiedString.toString());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + prependValue.hashCode();
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
        PathPrependValueModification other = (PathPrependValueModification) obj;
        return Objects.equals(prependValue, other.prependValue);
    }

    @Override
    public String toString() {
        return "PathPrependValueModification{"
                + "prependValue='"
                + backslashEscapeString(prependValue)
                + '\''
                + '}';
    }
}
