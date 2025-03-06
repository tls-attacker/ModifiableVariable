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
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Arrays;

@XmlRootElement
public class ByteArrayExplicitValueModification extends VariableModification<byte[]> {

    @XmlJavaTypeAdapter(UnformattedByteArrayAdapter.class)
    protected byte[] explicitValue;

    public ByteArrayExplicitValueModification() {
        super();
    }

    public ByteArrayExplicitValueModification(byte[] explicitValue) {
        super();
        this.explicitValue = explicitValue;
    }

    public ByteArrayExplicitValueModification(ByteArrayExplicitValueModification other) {
        super(other);
        explicitValue = other.explicitValue != null ? other.explicitValue.clone() : null;
    }

    @Override
    public ByteArrayExplicitValueModification createCopy() {
        return new ByteArrayExplicitValueModification(this);
    }

    @Override
    protected byte[] modifyImplementationHook(byte[] input) {
        if (input == null) {
            throw new NullPointerException("original value must not be null");
        }
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
