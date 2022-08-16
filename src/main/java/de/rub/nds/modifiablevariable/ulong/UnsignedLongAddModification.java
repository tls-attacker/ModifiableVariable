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
@XmlType(propOrder = { "summand", "modificationFilter" })
public class UnsignedLongAddModification extends VariableModification<UnsignedLong> {

    private static final int MAX_ADD_MODIFIER = 32;

    private UnsignedLong summand;

    public UnsignedLongAddModification() {

    }

    public UnsignedLongAddModification(Long bi) {
        this(bi != null ? UnsignedLong.fromLongBits(bi) : null);
    }

    public UnsignedLongAddModification(UnsignedLong bi) {
        this.summand = bi;
    }

    @Override
    protected UnsignedLong modifyImplementationHook(final UnsignedLong input) {
        return (input == null) ? summand : input.plus(summand);
    }

    public UnsignedLong getSummand() {
        return summand;
    }

    public void setSummand(Long summand) {
        setSummand(summand != null ? UnsignedLong.fromLongBits(summand) : null);
    }

    public void setSummand(UnsignedLong summand) {
        this.summand = summand;
    }

    @Override
    public VariableModification<UnsignedLong> getModifiedCopy() {
        UnsignedLong modification = UnsignedLong.fromLongBits(new Random().nextInt(MAX_ADD_MODIFIER));
        return new UnsignedLongAddModification(summand.plus(modification));
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + Objects.hashCode(this.summand);
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
        final UnsignedLongAddModification other = (UnsignedLongAddModification) obj;
        if (!Objects.equals(this.summand, other.summand)) {
            return false;
        }
        return true;
    }

}
