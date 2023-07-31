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
@XmlType(propOrder = { "subtrahend", "modificationFilter" })
public class LongSubtractModification extends VariableModification<Long> {

    private static final int MAX_SUBTRACT_MODIFIER = 256;

    private Long subtrahend;

    public LongSubtractModification() {

    }

    public LongSubtractModification(Long bi) {
        this.subtrahend = bi;
    }

    @Override
    protected Long modifyImplementationHook(final Long input) {
        return (input == null) ? -subtrahend : input - subtrahend;
    }

    public Long getSubtrahend() {
        return subtrahend;
    }

    public void setSubtrahend(Long subtrahend) {
        this.subtrahend = subtrahend;
    }

    @Override
    public VariableModification<Long> getModifiedCopy() {
        return new LongSubtractModification(subtrahend + new Random().nextInt(MAX_SUBTRACT_MODIFIER));
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
        final LongSubtractModification other = (LongSubtractModification) obj;
        if (!Objects.equals(this.subtrahend, other.subtrahend)) {
            return false;
        }
        return true;
    }
}
