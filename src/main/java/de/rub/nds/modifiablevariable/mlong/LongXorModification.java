/**
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 *
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */
package de.rub.nds.modifiablevariable.mlong;

import de.rub.nds.modifiablevariable.VariableModification;
import java.util.Objects;
import java.util.Random;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(propOrder = { "xor", "modificationFilter" })
public class LongXorModification extends VariableModification<Long> {

    private static final int MAX_XOR_MODIFIER = 256;

    private Long xor;

    public LongXorModification() {

    }

    public LongXorModification(Long bi) {
        this.xor = bi;
    }

    @Override
    protected Long modifyImplementationHook(final Long input) {
        return (input == null) ? xor : input ^ xor;
    }

    public Long getXor() {
        return xor;
    }

    public void setXor(Long xor) {
        this.xor = xor;
    }

    @Override
    public VariableModification<Long> getModifiedCopy() {
        Random r = new Random();
        if (r.nextBoolean()) {
            return new LongXorModification(xor + new Random().nextInt(MAX_XOR_MODIFIER));
        } else {
            return new LongXorModification(xor - new Random().nextInt(MAX_XOR_MODIFIER));
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
        final LongXorModification other = (LongXorModification) obj;
        if (!Objects.equals(this.xor, other.xor)) {
            return false;
        }
        return true;
    }
}
