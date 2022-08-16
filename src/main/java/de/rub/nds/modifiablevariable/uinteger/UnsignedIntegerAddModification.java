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
@XmlType(propOrder = { "summand", "modificationFilter" })
public class UnsignedIntegerAddModification extends VariableModification<UnsignedInteger> {

    private static final int MAX_ADD_MODIFIER = 256;

    private UnsignedInteger summand;

    public UnsignedIntegerAddModification() {

    }

    public UnsignedIntegerAddModification(Integer bi) {
        this(bi != null ? UnsignedInteger.fromIntBits(bi) : null);
    }

    public UnsignedIntegerAddModification(UnsignedInteger bi) {
        this.summand = bi;
    }

    @Override
    protected UnsignedInteger modifyImplementationHook(UnsignedInteger input) {
        return (input == null) ? summand : input.plus(summand);
    }

    public UnsignedInteger getSummand() {
        return summand;
    }

    public void setSummand(UnsignedInteger summand) {
        this.summand = summand;
    }

    @Override
    public VariableModification<UnsignedInteger> getModifiedCopy() {
        UnsignedInteger modification = UnsignedInteger.fromIntBits(new Random().nextInt(MAX_ADD_MODIFIER));
        return new UnsignedIntegerAddModification(summand.plus(modification));
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + Objects.hashCode(this.summand);
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
        final UnsignedIntegerAddModification other = (UnsignedIntegerAddModification) obj;
        if (!Objects.equals(this.summand, other.summand)) {
            return false;
        }
        return true;
    }
}
