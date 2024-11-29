/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.bytearray;

import de.rub.nds.modifiablevariable.VariableModification;
import de.rub.nds.modifiablevariable.util.ArrayConverter;
import de.rub.nds.modifiablevariable.util.UnformattedByteArrayAdapter;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Arrays;
import java.util.Random;

@XmlRootElement
@XmlType(propOrder = {"explicitValue", "modificationFilter"})
@XmlAccessorType(XmlAccessType.FIELD)
public class ByteArrayExplicitValueModification extends VariableModification<byte[]> {

    private static final int MAX_EXPLICIT_VALUE = 256;

    @XmlJavaTypeAdapter(UnformattedByteArrayAdapter.class)
    private byte[] explicitValue;

    public ByteArrayExplicitValueModification() {
        super();
    }

    public ByteArrayExplicitValueModification(byte[] explicitValue) {
        super();
        this.explicitValue = explicitValue;
    }

    @Override
    protected byte[] modifyImplementationHook(byte[] input) {
        return explicitValue.clone();
    }

    public byte[] getExplicitValue() {
        return explicitValue;
    }

    public void setExplicitValue(byte[] explicitValue) {
        this.explicitValue = explicitValue;
    }

    @Override
    public String toString() {
        return "ByteArrayExplicitValueModification{"
                + "explicitValue="
                + ArrayConverter.bytesToHexString(explicitValue)
                + '}';
    }

    @Override
    public VariableModification<byte[]> getModifiedCopy() {
        Random r = new Random();
        if (explicitValue.length == 0) {
            return this;
        }
        int index = r.nextInt(explicitValue.length);
        byte[] newValue = Arrays.copyOf(explicitValue, explicitValue.length);
        newValue[index] = (byte) r.nextInt(MAX_EXPLICIT_VALUE);
        return new ByteArrayExplicitValueModification(newValue);
    }

    @Override
    public VariableModification<byte[]> createCopy() {
        return new ByteArrayExplicitValueModification(
                explicitValue != null ? explicitValue.clone() : null);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Arrays.hashCode(explicitValue);
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
        ByteArrayExplicitValueModification other = (ByteArrayExplicitValueModification) obj;
        return Arrays.equals(explicitValue, other.explicitValue);
    }
}
