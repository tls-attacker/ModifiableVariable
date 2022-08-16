/**
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Copyright 2014-2022 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 *
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.modifiablevariable.uinteger;

import com.google.common.primitives.UnsignedInteger;
import de.rub.nds.modifiablevariable.VariableModification;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Objects;
import java.util.Random;

@XmlRootElement
@XmlType(propOrder = { "subtrahend", "modificationFilter" })
public class UnsignedIntegerSubtractModification extends VariableModification<UnsignedInteger> {

    private static final int MAX_SUBTRACT_MODIFIER = 256;

    private UnsignedInteger subtrahend;

    public UnsignedIntegerSubtractModification() {

    }

    public UnsignedIntegerSubtractModification(Integer bi) {
        this(bi != null ? UnsignedInteger.fromIntBits(bi) : null);
    }

    public UnsignedIntegerSubtractModification(UnsignedInteger bi) {
        this.subtrahend = bi;
    }

    @Override
    protected UnsignedInteger modifyImplementationHook(final UnsignedInteger input) {
        return (input == null) ? UnsignedInteger.ZERO.minus(subtrahend) : input.minus(subtrahend);
    }

    public UnsignedInteger getSubtrahend() {
        return subtrahend;
    }

    public void setSubtrahend(Integer subtrahend) {
        setSubtrahend(subtrahend != null ? UnsignedInteger.fromIntBits(subtrahend) : null);
    }

    public void setSubtrahend(UnsignedInteger subtrahend) {
        this.subtrahend = subtrahend;
    }

    @Override
    public VariableModification<UnsignedInteger> getModifiedCopy() {
        UnsignedInteger modification = UnsignedInteger.fromIntBits(new Random().nextInt(MAX_SUBTRACT_MODIFIER));
        return new UnsignedIntegerSubtractModification(subtrahend.plus(modification));
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.subtrahend);
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
        final UnsignedIntegerSubtractModification other = (UnsignedIntegerSubtractModification) obj;
        if (!Objects.equals(this.subtrahend, other.subtrahend)) {
            return false;
        }
        return true;
    }
}
