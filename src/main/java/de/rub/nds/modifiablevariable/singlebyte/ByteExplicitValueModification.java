/**
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Copyright 2014-2017 Ruhr University Bochum / Hackmanit GmbH
 *
 * Licensed under Apache License 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.singlebyte;

import de.rub.nds.modifiablevariable.VariableModification;
import de.rub.nds.modifiablevariable.mlong.LongExplicitValueModification;
import java.util.Random;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(propOrder = { "explicitValue", "modificationFilter", "postModification" })
public class ByteExplicitValueModification extends VariableModification<Byte> {

    private Byte explicitValue;

    public ByteExplicitValueModification() {

    }

    public ByteExplicitValueModification(Byte bi) {
        this.explicitValue = bi;
    }

    @Override
    protected Byte modifyImplementationHook(final Byte input) {
        return explicitValue;
    }

    public Byte getExplicitValue() {
        return explicitValue;
    }

    public void setExplicitValue(Byte explicitValue) {
        this.explicitValue = explicitValue;
    }

    @Override
    public VariableModification<Byte> getModifiedCopy() {
        Random r = new Random();
        if (r.nextBoolean()) {
            return new ByteExplicitValueModification((byte) (explicitValue + r.nextInt(16)));
        } else {
            return new ByteExplicitValueModification((byte) (explicitValue - r.nextInt(16)));
        }
    }
}
