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
@XmlType(propOrder = {"explicitValue", "modificationFilter"})
public class ByteExplicitValueModification extends VariableModification<Byte> {

    private static final int MAX_EXPLICIT_MODIFIER = 16;

    private Byte explicitValue;

    public ByteExplicitValueModification() {}

    public ByteExplicitValueModification(Byte bi) {
        this.explicitValue = bi;
    }

    @Override
    protected Byte modifyImplementationHook(final Byte input) {
        return explicitValue;
    }

    public Byte getExplicitValue() {
        return explicitValue;
    }

    public void setExplicitValue(Byte explicitValue) {
        this.explicitValue = explicitValue;
    }

    @Override
    public VariableModification<Byte> getModifiedCopy() {
        Random r = new Random();
        if (r.nextBoolean()) {
            return new ByteExplicitValueModification(
                    (byte) (explicitValue + r.nextInt(MAX_EXPLICIT_MODIFIER)));
        } else {
            return new ByteExplicitValueModification(
                    (byte) (explicitValue - r.nextInt(MAX_EXPLICIT_MODIFIER)));
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.explicitValue);
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
        final ByteExplicitValueModification other = (ByteExplicitValueModification) obj;
        if (!Objects.equals(this.explicitValue, other.explicitValue)) {
            return false;
        }
        return true;
    }
}
