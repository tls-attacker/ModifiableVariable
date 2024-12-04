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
import java.util.Random;

@XmlRootElement
@XmlType(propOrder = {"subtrahend", "modificationFilter"})
public class ByteSubtractModification extends VariableModification<Byte> {

    private static final int MAX_SUBTRACT_MODIFIER = 16;

    private Byte subtrahend;

    public ByteSubtractModification() {
        super();
    }

    public ByteSubtractModification(Byte subtrahend) {
        super();
        this.subtrahend = subtrahend;
    }

    public ByteSubtractModification(ByteSubtractModification other) {
        super(other);
        subtrahend = other.subtrahend;
    }

    @Override
    public ByteSubtractModification createCopy() {
        return new ByteSubtractModification(this);
    }

    @Override
    protected Byte modifyImplementationHook(Byte input) {
        if (input == null) {
            input = 0;
        }
        return (byte) (input - subtrahend);
    }

    public Byte getSubtrahend() {
        return subtrahend;
    }

    public void setSubtrahend(Byte subtrahend) {
        this.subtrahend = subtrahend;
    }

    @Override
    public VariableModification<Byte> getModifiedCopy() {
        return new ByteSubtractModification(
                (byte) (subtrahend + new Random().nextInt(MAX_SUBTRACT_MODIFIER)));
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + subtrahend;
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
        ByteSubtractModification other = (ByteSubtractModification) obj;
        return Objects.equals(subtrahend, other.subtrahend);
    }
}
