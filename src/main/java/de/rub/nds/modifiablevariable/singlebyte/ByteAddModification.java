/**
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Copyright 2014-2022 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 *
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.modifiablevariable.singlebyte;

import de.rub.nds.modifiablevariable.VariableModification;
import java.util.Objects;
import java.util.Random;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(propOrder = { "summand", "modificationFilter" })
public class ByteAddModification extends VariableModification<Byte> {

    private static final int MAX_ADD_MODIFIER = 16;

    private Byte summand;

    public ByteAddModification() {

    }

    public ByteAddModification(Byte bi) {
        this.summand = bi;
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
    public VariableModification<Byte> getModifiedCopy() {
        return new ByteAddModification((byte) (summand + new Random().nextInt(MAX_ADD_MODIFIER)));
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + Objects.hashCode(this.summand);
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
        final ByteAddModification other = (ByteAddModification) obj;
        if (!Objects.equals(this.summand, other.summand)) {
            return false;
        }
        return true;
    }
}
