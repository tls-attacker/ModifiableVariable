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
import de.rub.nds.modifiablevariable.integer.IntegerSubtractModification;
import java.util.Random;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(propOrder = { "subtrahend", "modificationFilter", "postModification" })
public class LongSubtractModification extends VariableModification<Long> {

    private final static int MAX_SUBTRACT_MODIFIER = 256;

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

}
