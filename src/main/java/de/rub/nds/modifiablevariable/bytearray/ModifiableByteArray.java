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
import de.rub.nds.modifiablevariable.util.ArrayConverter;
import de.rub.nds.modifiablevariable.util.UnformattedByteArrayAdapter;
import java.io.Serializable;
import java.util.Arrays;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class ModifiableByteArray extends ModifiableVariable<byte[]> implements Serializable {

    public ModifiableByteArray() {
    }

    private byte[] originalValue;

    @Override
    protected void createRandomModification() {
        VariableModification<byte[]> vm = ByteArrayModificationFactory.createRandomModification(originalValue);
        setModification(vm);
    }

    @Override
    @XmlJavaTypeAdapter(UnformattedByteArrayAdapter.class)
    public byte[] getOriginalValue() {
        return originalValue;
    }

    @Override
    public void setOriginalValue(byte[] originalValue) {
        this.originalValue = originalValue;
    }

    @XmlJavaTypeAdapter(UnformattedByteArrayAdapter.class)
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

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        if (this.isOriginalValueModified()) {
            result.append("Actual byte value is: ");
            result.append(ArrayConverter.bytesToHexString(this));
            result.append("\nOriginal value was: ");
            result.append(ArrayConverter.bytesToHexString(this.getOriginalValue()));
        } else {
            result.append("Original byte value is: ");
            result.append(ArrayConverter.bytesToHexString(this.getOriginalValue()));
        }
        return result.toString();

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ModifiableByteArray)) {
            return false;
        }

        ModifiableByteArray that = (ModifiableByteArray) o;

        return Arrays.equals(getValue(), that.getValue());
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + Arrays.hashCode(getValue());
        return result;
    }
}
