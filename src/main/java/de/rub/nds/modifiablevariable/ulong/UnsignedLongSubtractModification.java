/**
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Copyright 2014-2022 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 *
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.modifiablevariable.ulong;

import com.google.common.primitives.UnsignedLong;
import de.rub.nds.modifiablevariable.VariableModification;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Objects;
import java.util.Random;

@XmlRootElement
@XmlType(propOrder = { "subtrahend", "modificationFilter" })
public class UnsignedLongSubtractModification extends VariableModification<UnsignedLong> {

    private static final int MAX_SUBTRACT_MODIFIER = 256;

    private UnsignedLong subtrahend;

    public UnsignedLongSubtractModification() {

    }

    public UnsignedLongSubtractModification(Long bi) {
        this(bi != null ? UnsignedLong.fromLongBits(bi) : null);
    }

    public UnsignedLongSubtractModification(UnsignedLong bi) {
        this.subtrahend = bi;
    }

    @Override
    protected UnsignedLong modifyImplementationHook(final UnsignedLong input) {
        return (input == null) ? UnsignedLong.ZERO.minus(subtrahend) : input.minus(subtrahend);
    }

    public UnsignedLong getSubtrahend() {
        return subtrahend;
    }

    public void setSubtrahend(Long subtrahend) {
        setSubtrahend(subtrahend != null ? UnsignedLong.fromLongBits(subtrahend) : null);
    }

    public void setSubtrahend(UnsignedLong subtrahend) {
        this.subtrahend = subtrahend;
    }

    @Override
    public VariableModification<UnsignedLong> getModifiedCopy() {
        UnsignedLong modification = UnsignedLong.fromLongBits(new Random().nextInt(MAX_SUBTRACT_MODIFIER));
        return new UnsignedLongSubtractModification(subtrahend.plus(modification));
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.subtrahend);
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
        final UnsignedLongSubtractModification other = (UnsignedLongSubtractModification) obj;
        if (!Objects.equals(this.subtrahend, other.subtrahend)) {
            return false;
        }
        return true;
    }
}
