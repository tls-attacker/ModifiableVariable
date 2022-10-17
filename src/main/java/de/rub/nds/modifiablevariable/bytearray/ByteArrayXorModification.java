/**
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Copyright 2014-2022 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 *
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.modifiablevariable.bytearray;

import de.rub.nds.modifiablevariable.VariableModification;
import de.rub.nds.modifiablevariable.util.ArrayConverter;
import de.rub.nds.modifiablevariable.util.UnformattedByteArrayAdapter;
import java.util.Arrays;
import java.util.Random;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement
@XmlType(propOrder = { "xor", "startPosition", "modificationFilter" })
@XmlAccessorType(XmlAccessType.FIELD)
public class ByteArrayXorModification extends VariableModification<byte[]> {

    private static final int MAX_MODIFIER_VALUE = 256;

    private static final int MAX_XOR_MODIFIER = 32;

    @XmlJavaTypeAdapter(UnformattedByteArrayAdapter.class)
    private byte[] xor;

    private int startPosition;

    public ByteArrayXorModification() {

    }

    public ByteArrayXorModification(byte[] xor, int startPosition) {
        this.xor = xor;
        this.startPosition = startPosition;
    }

    @Override
    protected byte[] modifyImplementationHook(byte[] input) {
        if (input == null) {
            input = new byte[0];
        }
        byte[] result = input.clone();
        int start = startPosition;
        if (start < 0) {
            start += input.length;
        }
        final int end = start + xor.length;
        if (end > result.length) {
            // result = new byte[end];
            // System.arraycopy(input, 0, result, 0, input.length);
            LOGGER.debug("Input {{}} of length {} cannot be xor-ed with {{}} of length {} with start position {}",input, input.length, xor, xor.length, startPosition);
            return input;
        }
        for (int i = 0; i < xor.length; ++i) {
            result[start + i] = (byte) (input[start + i] ^ xor[i]);
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
    public VariableModification<byte[]> getModifiedCopy() {
        Random r = new Random();
        if (r.nextBoolean()) {
            int index = r.nextInt(xor.length);
            byte[] newValue = Arrays.copyOf(xor, xor.length);
            newValue[index] = (byte) r.nextInt(MAX_MODIFIER_VALUE);
            return new ByteArrayXorModification(newValue, startPosition);
        } else {
            byte[] newValue = Arrays.copyOf(xor, xor.length);
            int modifier = r.nextInt(MAX_XOR_MODIFIER);
            if (r.nextBoolean()) {
                modifier *= -1;
            }
            modifier = startPosition + modifier;
            if (modifier <= 0) {
                modifier = 1;
            }
            return new ByteArrayXorModification(newValue, modifier);
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Arrays.hashCode(this.xor);
        hash = 97 * hash + this.startPosition;
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
        final ByteArrayXorModification other = (ByteArrayXorModification) obj;
        if (this.startPosition != other.startPosition) {
            return false;
        }
        if (!Arrays.equals(this.xor, other.xor)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ByteArrayXorModification{" + "xor=" + ArrayConverter.bytesToHexString(xor) + ", startPosition="
            + startPosition + '}';
    }

}
