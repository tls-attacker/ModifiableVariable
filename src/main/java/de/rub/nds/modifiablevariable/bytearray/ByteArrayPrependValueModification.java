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
public class ByteArrayPrependValueModification extends VariableModification<byte[]> {

    @XmlJavaTypeAdapter(UnformattedByteArrayAdapter.class)
    private byte[] bytesToPrepend;

    public ByteArrayPrependValueModification() {
        super();
    }

    public ByteArrayPrependValueModification(byte[] bytesToPrepend) {
        super();
        this.bytesToPrepend = bytesToPrepend;
    }

    public ByteArrayPrependValueModification(ByteArrayPrependValueModification other) {
        super(other);
        bytesToPrepend = other.bytesToPrepend != null ? other.bytesToPrepend.clone() : null;
    }

    @Override
    public ByteArrayPrependValueModification createCopy() {
        return new ByteArrayPrependValueModification(this);
    }

    @Override
    protected byte[] modifyImplementationHook(byte[] input) {
        if (input == null) {
            return null;
        }
        return ArrayConverter.concatenate(bytesToPrepend, input);
    }

    public byte[] getBytesToPrepend() {
        return bytesToPrepend;
    }

    public void setBytesToPrepend(byte[] bytesToPrepend) {
        this.bytesToPrepend = bytesToPrepend;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Arrays.hashCode(bytesToPrepend);
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
        ByteArrayPrependValueModification other = (ByteArrayPrependValueModification) obj;
        return Arrays.equals(bytesToPrepend, other.bytesToPrepend);
    }

    @Override
    public String toString() {
        return "ByteArrayPrependValueModification{bytesToPrepend="
                + ArrayConverter.bytesToHexString(bytesToPrepend)
                + "}";
    }
}
