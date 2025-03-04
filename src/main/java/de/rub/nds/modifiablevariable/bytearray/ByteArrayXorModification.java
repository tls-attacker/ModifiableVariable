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
public class ByteArrayXorModification extends VariableModification<byte[]> {

    @XmlJavaTypeAdapter(UnformattedByteArrayAdapter.class)
    private byte[] xor;

    private int startPosition;

    public ByteArrayXorModification() {
        super();
    }

    public ByteArrayXorModification(byte[] xor, int startPosition) {
        super();
        this.xor = xor;
        this.startPosition = startPosition;
    }

    public ByteArrayXorModification(ByteArrayXorModification other) {
        super(other);
        xor = other.xor != null ? other.xor.clone() : null;
        startPosition = other.startPosition;
    }

    @Override
    public ByteArrayXorModification createCopy() {
        return new ByteArrayXorModification(this);
    }

    @Override
    protected byte[] modifyImplementationHook(byte[] input) {
        if (input == null) {
            input = new byte[0];
        }
        if (input.length == 0) {
            return input;
        }
        byte[] result = input.clone();

        // Wrap around and also allow to xor at the end of the original value
        int xorPosition = startPosition % input.length;
        if (startPosition < 0) {
            xorPosition += input.length - 1;
        }
        int endPosition = xorPosition + xor.length;
        if (endPosition > result.length) {
            // Fix the end position to the length of the original value
            // This may not match the expected behavior of a user
            // But for fuzzing purpose, that's fine
            endPosition = result.length;
        }

        for (int i = 0; i < endPosition - xorPosition; ++i) {
            result[xorPosition + i] = (byte) (input[xorPosition + i] ^ xor[i]);
        }
        return result;
    }

    public byte[] getXor() {
        return xor;
    }

    public void setXor(byte[] xor) {
        this.xor = xor;
    }

    public int getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(int startPosition) {
        this.startPosition = startPosition;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Arrays.hashCode(xor);
        hash = 31 * hash + startPosition;
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
        ByteArrayXorModification other = (ByteArrayXorModification) obj;
        if (startPosition != other.startPosition) {
            return false;
        }
        return Arrays.equals(xor, other.xor);
    }

    @Override
    public String toString() {
        return "ByteArrayXorModification{"
                + "xor="
                + ArrayConverter.bytesToHexString(xor)
                + ", startPosition="
                + startPosition
                + '}';
    }
}
