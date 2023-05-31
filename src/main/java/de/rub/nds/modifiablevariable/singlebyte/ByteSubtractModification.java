/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, and Hackmanit GmbH
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

    public ByteSubtractModification() {}

    public ByteSubtractModification(Byte bi) {
        this.subtrahend = bi;
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
        return new ByteAddModification(
                (byte) (subtrahend + new Random().nextInt(MAX_SUBTRACT_MODIFIER)));
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + Objects.hashCode(this.subtrahend);
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
        final ByteSubtractModification other = (ByteSubtractModification) obj;
        if (!Objects.equals(this.subtrahend, other.subtrahend)) {
            return false;
        }
        return true;
    }
}
