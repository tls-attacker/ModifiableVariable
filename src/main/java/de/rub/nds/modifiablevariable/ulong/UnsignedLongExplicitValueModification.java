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
@XmlType(propOrder = { "explicitValue", "modificationFilter" })
public class UnsignedLongExplicitValueModification extends VariableModification<UnsignedLong> {

    private static final int MAX_EXPLICIT_VALUE = 256;

    private UnsignedLong explicitValue;

    public UnsignedLongExplicitValueModification() {

    }

    public UnsignedLongExplicitValueModification(Long bi) {
        this(bi != null ? UnsignedLong.fromLongBits(bi) : null);
    }

    public UnsignedLongExplicitValueModification(UnsignedLong bi) {
        this.explicitValue = bi;
    }

    @Override
    protected UnsignedLong modifyImplementationHook(final UnsignedLong input) {
        return explicitValue;
    }

    public UnsignedLong getExplicitValue() {
        return explicitValue;
    }

    public void setExplicitValue(Long explicitValue) {
        setExplicitValue(explicitValue != null ? UnsignedLong.fromLongBits(explicitValue) : null);
    }

    public void setExplicitValue(UnsignedLong explicitValue) {
        this.explicitValue = explicitValue;
    }

    @Override
    public VariableModification<UnsignedLong> getModifiedCopy() {
        Random r = new Random();
        UnsignedLong modification = UnsignedLong.fromLongBits(r.nextInt(MAX_EXPLICIT_VALUE));
        if (r.nextBoolean()) {
            return new UnsignedLongExplicitValueModification(explicitValue.plus(modification));
        } else {
            return new UnsignedLongExplicitValueModification(explicitValue.minus(modification));
        }
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 43 * hash + Objects.hashCode(this.explicitValue);
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
        final UnsignedLongExplicitValueModification other = (UnsignedLongExplicitValueModification) obj;
        if (!Objects.equals(this.explicitValue, other.explicitValue)) {
            return false;
        }
        return true;
    }
}
