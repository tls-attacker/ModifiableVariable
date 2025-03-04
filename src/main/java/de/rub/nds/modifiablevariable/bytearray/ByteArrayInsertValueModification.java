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
public class ByteArrayInsertValueModification extends VariableModification<byte[]> {

    @XmlJavaTypeAdapter(UnformattedByteArrayAdapter.class)
    private byte[] bytesToInsert;

    private int startPosition;

    public ByteArrayInsertValueModification() {
        super();
    }

    public ByteArrayInsertValueModification(byte[] bytesToInsert, int startPosition) {
        super();
        this.bytesToInsert = bytesToInsert;
        this.startPosition = startPosition;
    }

    public ByteArrayInsertValueModification(ByteArrayInsertValueModification other) {
        super(other);
        bytesToInsert = other.bytesToInsert != null ? other.bytesToInsert.clone() : null;
        startPosition = other.startPosition;
    }

    @Override
    public ByteArrayInsertValueModification createCopy() {
        return new ByteArrayInsertValueModification(this);
    }

    @Override
    protected byte[] modifyImplementationHook(byte[] input) {
        if (input == null) {
            input = new byte[0];
        }

        // Wrap around and also allow to insert at the end of the original value
        int insertPosition = startPosition % (input.length + 1);
        if (startPosition < 0) {
            insertPosition += input.length;
        }

        byte[] ret1 = Arrays.copyOf(input, insertPosition);
        if (insertPosition < input.length) {
            byte[] ret2 = Arrays.copyOfRange(input, insertPosition, input.length);
            return ArrayConverter.concatenate(ret1, bytesToInsert, ret2);
        }
        return ArrayConverter.concatenate(ret1, bytesToInsert);
    }

    public byte[] getBytesToInsert() {
        return bytesToInsert;
    }

    public void setBytesToInsert(byte[] bytesToInsert) {
        this.bytesToInsert = bytesToInsert;
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
        hash = 31 * hash + Arrays.hashCode(bytesToInsert);
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
        ByteArrayInsertValueModification other = (ByteArrayInsertValueModification) obj;
        if (startPosition != other.startPosition) {
            return false;
        }
        return Arrays.equals(bytesToInsert, other.bytesToInsert);
    }

    @Override
    public String toString() {
        return "ByteArrayInsertModification{"
                + "bytesToInsert="
                + ArrayConverter.bytesToHexString(bytesToInsert)
                + ", startPosition="
                + startPosition
                + '}';
    }
}
