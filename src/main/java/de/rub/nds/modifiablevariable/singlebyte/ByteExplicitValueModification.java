/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.singlebyte;

import de.rub.nds.modifiablevariable.VariableModification;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@XmlRootElement
public class ByteExplicitValueModification extends VariableModification<Byte> {

    protected Byte explicitValue;

    public ByteExplicitValueModification() {
        super();
    }

    public ByteExplicitValueModification(Byte explicitValue) {
        super();
        this.explicitValue = explicitValue;
    }

    public ByteExplicitValueModification(ByteExplicitValueModification other) {
        super(other);
        explicitValue = other.explicitValue;
    }

    @Override
    public ByteExplicitValueModification createCopy() {
        return new ByteExplicitValueModification(this);
    }

    @Override
    protected Byte modifyImplementationHook(Byte input) {
        if (input == null) {
            throw new NullPointerException("original value must not be null");
        }
        return explicitValue;
    }

    public Byte getExplicitValue() {
        return explicitValue;
    }

    public void setExplicitValue(Byte explicitValue) {
        this.explicitValue = explicitValue;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + explicitValue;
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
        ByteExplicitValueModification other = (ByteExplicitValueModification) obj;
        return Objects.equals(explicitValue, other.explicitValue);
    }

    @Override
    public String toString() {
        return "ByteExplicitValueModification{" + "explicitValue=" + explicitValue + '}';
    }
}
