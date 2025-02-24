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
import jakarta.xml.bind.annotation.XmlType;
import java.util.Objects;

@XmlRootElement
@XmlType(propOrder = {"summand", "modificationFilter"})
public class ByteAddModification extends VariableModification<Byte> {

    private Byte summand;

    public ByteAddModification() {
        super();
    }

    public ByteAddModification(Byte summand) {
        super();
        this.summand = summand;
    }

    public ByteAddModification(ByteAddModification other) {
        super(other);
        summand = other.summand;
    }

    @Override
    public ByteAddModification createCopy() {
        return new ByteAddModification(this);
    }

    @Override
    protected Byte modifyImplementationHook(Byte input) {
        if (input == null) {
            input = 0;
        }
        return (byte) (input + summand);
    }

    public Byte getSummand() {
        return summand;
    }

    public void setSummand(Byte summand) {
        this.summand = summand;
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
        ByteAddModification other = (ByteAddModification) obj;
        return Objects.equals(summand, other.summand);
    }

    @Override
    public String toString() {
        return "ByteAddModification{" + "summand=" + summand + '}';
    }
}
