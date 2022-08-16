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
@XmlType(propOrder = { "xor", "modificationFilter" })
public class UnsignedLongXorModification extends VariableModification<UnsignedLong> {

    private static final int MAX_XOR_MODIFIER = 256;

    private UnsignedLong xor;

    public UnsignedLongXorModification() {

    }

    public UnsignedLongXorModification(Long bi) {
        this(bi != null ? UnsignedLong.fromLongBits(bi) : null);
    }

    public UnsignedLongXorModification(UnsignedLong bi) {
        this.xor = bi;
    }

    @Override
    protected UnsignedLong modifyImplementationHook(final UnsignedLong input) {
        return (input == null) ? xor : UnsignedLong.fromLongBits(input.longValue() ^ xor.longValue());
    }

    public UnsignedLong getXor() {
        return xor;
    }

    public void setXor(Long xor) {
        setXor(xor != null ? UnsignedLong.fromLongBits(xor) : null);
    }

    public void setXor(UnsignedLong xor) {
        this.xor = xor;
    }

    @Override
    public VariableModification<UnsignedLong> getModifiedCopy() {
        Random r = new Random();
        UnsignedLong modification = UnsignedLong.fromLongBits(new Random().nextInt(MAX_XOR_MODIFIER));
        if (r.nextBoolean()) {
            return new UnsignedLongXorModification(xor.plus(modification));
        } else {
            return new UnsignedLongXorModification(xor.minus(modification));
        }
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.xor);
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
        final UnsignedLongXorModification other = (UnsignedLongXorModification) obj;
        if (!Objects.equals(this.xor, other.xor)) {
            return false;
        }
        return true;
    }
}
