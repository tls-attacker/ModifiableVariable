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
@XmlType(propOrder = {"bytesToAppend", "modificationFilter"})
@XmlAccessorType(XmlAccessType.FIELD)
public class ByteArrayAppendValueModification extends VariableModification<byte[]> {

    private static final int MAX_EXPLICIT_VALUE = 256;

    @XmlJavaTypeAdapter(UnformattedByteArrayAdapter.class)
    private byte[] bytesToAppend;

    public ByteArrayAppendValueModification() {}

    public ByteArrayAppendValueModification(byte[] bytesToAppend) {
        this.bytesToAppend = bytesToAppend;
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
    public VariableModification<byte[]> getModifiedCopy() {
        Random r = new Random();

        int index = r.nextInt(bytesToAppend.length);
        byte[] newValue = Arrays.copyOf(bytesToAppend, bytesToAppend.length);
        newValue[index] = (byte) r.nextInt(MAX_EXPLICIT_VALUE);
        return new ByteArrayAppendValueModification(newValue);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 86 * hash + Arrays.hashCode(this.bytesToAppend);
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
        final ByteArrayAppendValueModification other = (ByteArrayAppendValueModification) obj;
        return Arrays.equals(this.bytesToAppend, other.bytesToAppend);
    }

    @Override
    public String toString() {
        return String.format("ByteArrayInsertModification{bytesToAppend=%s}",
            ArrayConverter.bytesToHexString(bytesToAppend));
    }
}
