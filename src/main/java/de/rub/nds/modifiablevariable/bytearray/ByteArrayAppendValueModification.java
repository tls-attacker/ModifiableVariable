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
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Arrays;

@XmlRootElement
@XmlType(propOrder = {"bytesToAppend", "modificationFilter"})
public class ByteArrayAppendValueModification extends VariableModification<byte[]> {

    @XmlJavaTypeAdapter(UnformattedByteArrayAdapter.class)
    private byte[] bytesToAppend;

    public ByteArrayAppendValueModification() {
        super();
    }

    public ByteArrayAppendValueModification(byte[] bytesToAppend) {
        super();
        this.bytesToAppend = bytesToAppend;
    }

    public ByteArrayAppendValueModification(ByteArrayAppendValueModification other) {
        super(other);
        bytesToAppend = other.bytesToAppend != null ? other.bytesToAppend.clone() : null;
    }

    @Override
    public ByteArrayAppendValueModification createCopy() {
        return new ByteArrayAppendValueModification(this);
    }

    @Override
    protected byte[] modifyImplementationHook(byte[] input) {
        if (input == null) {
            input = new byte[0];
        }

        return ArrayConverter.concatenate(input, bytesToAppend);
    }

    public byte[] getBytesToAppend() {
        return bytesToAppend;
    }

    public void setBytesToAppend(byte[] bytesToAppend) {
        this.bytesToAppend = bytesToAppend;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Arrays.hashCode(bytesToAppend);
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
        ByteArrayAppendValueModification other = (ByteArrayAppendValueModification) obj;
        return Arrays.equals(bytesToAppend, other.bytesToAppend);
    }

    @Override
    public String toString() {
        return "ByteArrayInsertModification{bytesToAppend="
                + ArrayConverter.bytesToHexString(bytesToAppend)
                + "}";
    }
}
