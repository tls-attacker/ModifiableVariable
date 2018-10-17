/**
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Copyright 2014-2017 Ruhr University Bochum / Hackmanit GmbH
 *
 * Licensed under Apache License 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.mlong;

import de.rub.nds.modifiablevariable.VariableModification;
import de.rub.nds.modifiablevariable.integer.IntegerAddModification;
import java.util.Objects;
import java.util.Random;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(propOrder = {"summand", "modificationFilter", "postModification"})
public class LongAddModification extends VariableModification<Long> {

    private final static int MAX_ADD_MODIFIER = 32;

    private Long summand;

    public LongAddModification() {

    }

    public LongAddModification(Long bi) {
        this.summand = bi;
    }

    @Override
    protected Long modifyImplementationHook(final Long input) {
        return (input == null) ? summand : input + summand;
    }

    public Long getSummand() {
        return summand;
    }

    public void setSummand(Long summand) {
        this.summand = summand;
    }

    @Override
    public VariableModification<Long> getModifiedCopy() {
        return new LongAddModification(summand + new Random().nextInt(MAX_ADD_MODIFIER));
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
        final LongAddModification other = (LongAddModification) obj;
        if (!Objects.equals(this.summand, other.summand)) {
            return false;
        }
        return true;
    }

}
