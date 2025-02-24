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

@XmlRootElement
@XmlType(propOrder = {"explicitValue", "modificationFilter"})
public class PathExplicitValueModification extends VariableModification<String> {

    @XmlJavaTypeAdapter(IllegalStringAdapter.class)
    protected String explicitValue;

    public PathExplicitValueModification() {
        super();
    }

    public PathExplicitValueModification(String explicitValue) {
        super();
        this.explicitValue = explicitValue;
    }

    public PathExplicitValueModification(PathExplicitValueModification other) {
        super(other);
        explicitValue = other.explicitValue;
    }

    @Override
    public PathExplicitValueModification createCopy() {
        return new PathExplicitValueModification(this);
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
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(explicitValue);
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
        PathExplicitValueModification other = (PathExplicitValueModification) obj;
        return Objects.equals(explicitValue, other.explicitValue);
    }

    @Override
    public String toString() {
        return "PathExplicitValueModification{"
                + "explicitValue='"
                + backslashEscapeString(explicitValue)
                + '\''
                + '}';
    }
}
