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
@XmlType(propOrder = {"bytesToInsert", "startPosition", "modificationFilter"})
@XmlAccessorType(XmlAccessType.FIELD)
public class ByteArrayInsertValueModification extends VariableModification<byte[]> {

    private static final int MAX_EXPLICIT_VALUE = 256;

    private static final int MAX_POSITION_MODIFIER = 32;

    @XmlJavaTypeAdapter(UnformattedByteArrayAdapter.class)
    private byte[] bytesToInsert;

    private int startPosition;

    public ByteArrayInsertValueModification() {}

    public ByteArrayInsertValueModification(byte[] bytesToInsert, int startPosition) {
        this.bytesToInsert = bytesToInsert;
        this.startPosition = startPosition;
    }

    @Override
    protected byte[] modifyImplementationHook(byte[] input) {
        if (input == null) {
            input = new byte[0];
        }

        int start = startPosition;
        if (start < 0) {
            start = 0;
        }
        if (start > input.length) {
            start = input.length;
        }
        byte[] ret1 = Arrays.copyOf(input, start);
        byte[] ret3 = null;
        if (start < input.length) {
            ret3 = Arrays.copyOfRange(input, start, input.length);
        }
        return ArrayConverter.concatenate(ret1, bytesToInsert, ret3);
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
    public VariableModification<byte[]> getModifiedCopy() {
        Random r = new Random();

        if (r.nextBoolean()) {
            int index = r.nextInt(bytesToInsert.length);
            byte[] newValue = Arrays.copyOf(bytesToInsert, bytesToInsert.length);
            newValue[index] = (byte) r.nextInt(MAX_EXPLICIT_VALUE);
            return new ByteArrayInsertValueModification(newValue, startPosition);
        } else {
            byte[] newValue = Arrays.copyOf(bytesToInsert, bytesToInsert.length);
            int modifier = r.nextInt(MAX_POSITION_MODIFIER);
            if (r.nextBoolean()) {
                modifier *= -1;
            }
            modifier = startPosition + modifier;
            if (modifier <= 0) {
                modifier = 1;
            }
            return new ByteArrayInsertValueModification(newValue, modifier);
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Arrays.hashCode(this.bytesToInsert);
        hash = 83 * hash + this.startPosition;
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
        final ByteArrayInsertValueModification other = (ByteArrayInsertValueModification) obj;
        if (this.startPosition != other.startPosition) {
            return false;
        }
        return Arrays.equals(this.bytesToInsert, other.bytesToInsert);
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
