/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.bytearray;

import de.rub.nds.modifiablevariable.ModifiableVariable;
import de.rub.nds.modifiablevariable.VariableModification;
import de.rub.nds.modifiablevariable.util.ArrayConverter;
import de.rub.nds.modifiablevariable.util.UnformattedByteArrayAdapter;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Arrays;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class ModifiableByteArray extends ModifiableVariable<byte[]> {

    private byte[] originalValue;

    public ModifiableByteArray() {
        super();
    }

    @SuppressWarnings("IncompleteCopyConstructor")
    public ModifiableByteArray(ModifiableByteArray other) {
        super(other);
        originalValue = other.originalValue != null ? other.originalValue.clone() : null;
        assertEquals = other.assertEquals != null ? other.assertEquals.clone() : null;
    }

    @Override
    protected void createRandomModification() {
        VariableModification<byte[]> vm =
                ByteArrayModificationFactory.createRandomModification(originalValue);
        setModification(vm);
    }

    @Override
    public ModifiableByteArray createCopy() {
        return new ModifiableByteArray(this);
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
        if (isOriginalValueModified()) {
            result.append("Actual byte value is: ");
            result.append(ArrayConverter.bytesToHexString(this));
            result.append("\nOriginal value was: ");
            result.append(ArrayConverter.bytesToHexString(getOriginalValue()));
        } else {
            result.append("Original byte value is: ");
            result.append(ArrayConverter.bytesToHexString(getOriginalValue()));
        }
        return result.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ModifiableByteArray)) {
            return false;
        }

        ModifiableByteArray that = (ModifiableByteArray) obj;

        return Arrays.equals(getValue(), that.getValue());
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + Arrays.hashCode(getValue());
        return result;
    }
}
