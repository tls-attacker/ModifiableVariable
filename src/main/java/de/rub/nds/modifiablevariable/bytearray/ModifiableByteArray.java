/**
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Copyright 2014-2017 Ruhr University Bochum / Hackmanit GmbH
 *
 * Licensed under Apache License 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.bytearray;

import de.rub.nds.modifiablevariable.ModifiableVariable;
import de.rub.nds.modifiablevariable.VariableModification;
import de.rub.nds.modifiablevariable.util.ByteArrayAdapter;
import java.io.Serializable;
import java.util.Arrays;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * 
 * @author Juraj Somorovsky - juraj.somorovsky@rub.de
 */
@XmlRootElement
@XmlSeeAlso({ ByteArrayDeleteModification.class, ByteArrayExplicitValueModification.class,
        ByteArrayInsertModification.class, ByteArrayXorModification.class, ByteArrayDuplicateModification.class })
@XmlType(propOrder = { "originalValue", "modification", "assertEquals" })
public class ModifiableByteArray extends ModifiableVariable<byte[]> implements Serializable {

    private byte[] originalValue;

    @Override
    protected void createRandomModification() {
        VariableModification<byte[]> vm = ByteArrayModificationFactory.createRandomModification(originalValue);
        setModification(vm);
    }

    @XmlJavaTypeAdapter(ByteArrayAdapter.class)
    @Override
    public byte[] getOriginalValue() {
        return originalValue;
    }

    @Override
    public void setOriginalValue(byte[] originalValue) {
        this.originalValue = originalValue;
    }

    @XmlJavaTypeAdapter(ByteArrayAdapter.class)
    public byte[] getAssertEquals() {
        return assertEquals;
    }

    public void setAssertEquals(byte[] assertEquals) {
        this.assertEquals = assertEquals;
    }

    @Override
    public boolean isOriginalValueModified() {
        return originalValue != null && !Arrays.equals(originalValue, getValue());
    }

    @Override
    public boolean validateAssertions() {
        boolean valid = true;
        if (assertEquals != null) {
            if (!Arrays.equals(assertEquals, getValue())) {
                valid = false;
            }
        }
        return valid;
    }
}