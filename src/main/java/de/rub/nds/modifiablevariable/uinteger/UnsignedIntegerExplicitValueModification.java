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
@XmlType(propOrder = { "explicitValue", "modificationFilter" })
public class UnsignedIntegerExplicitValueModification extends VariableModification<UnsignedInteger> {

    private static final int MAX_VALUE_MODIFIER = 256;

    private UnsignedInteger explicitValue;

    public UnsignedIntegerExplicitValueModification() {

    }

    public UnsignedIntegerExplicitValueModification(Integer bi) {
        this(bi != null ? UnsignedInteger.fromIntBits(bi) : null);
    }

    public UnsignedIntegerExplicitValueModification(UnsignedInteger bi) {
        this.explicitValue = bi;
    }

    @Override
    protected UnsignedInteger modifyImplementationHook(final UnsignedInteger input) {
        return explicitValue;
    }

    public UnsignedInteger getExplicitValue() {
        return explicitValue;
    }

    public void setExplicitValue(UnsignedInteger explicitValue) {
        this.explicitValue = explicitValue;
    }

    @Override
    public VariableModification<UnsignedInteger> getModifiedCopy() {
        Random r = new Random();
        UnsignedInteger modification = UnsignedInteger.fromIntBits(r.nextInt(MAX_VALUE_MODIFIER));
        if (r.nextBoolean()) {
            return new UnsignedIntegerExplicitValueModification(explicitValue.plus(modification));
        } else {
            return new UnsignedIntegerExplicitValueModification(explicitValue.minus(modification));
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.explicitValue);
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
        final UnsignedIntegerExplicitValueModification other = (UnsignedIntegerExplicitValueModification) obj;
        if (!Objects.equals(this.explicitValue, other.explicitValue)) {
            return false;
        }
        return true;
    }
}
