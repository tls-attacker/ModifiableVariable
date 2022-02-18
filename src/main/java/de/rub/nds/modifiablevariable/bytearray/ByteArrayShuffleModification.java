/**
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Copyright 2014-2022 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 *
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.modifiablevariable.bytearray;

import java.util.Arrays;
import java.util.Random;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import de.rub.nds.modifiablevariable.VariableModification;
import de.rub.nds.modifiablevariable.util.ArrayConverter;
import de.rub.nds.modifiablevariable.util.UnformattedByteArrayAdapter;

/**
 * Shuffles the byte array, using a pre-defined array of array pointers (#shuffle). Array pointers are currently defined
 * as bytes, since we are modifying rather smaller arrays.
 *
 */
@XmlRootElement
@XmlType(propOrder = { "shuffle", "modificationFilter" })
@XmlAccessorType(XmlAccessType.FIELD)
public class ByteArrayShuffleModification extends VariableModification<byte[]> {

    private static final int MAX_MODIFIER_VALUE = 256;

    @XmlJavaTypeAdapter(UnformattedByteArrayAdapter.class)
    private byte[] shuffle;

    public ByteArrayShuffleModification() {

    }

    public ByteArrayShuffleModification(byte[] shuffle) {
        this.shuffle = shuffle;
    }

    @Override
    protected byte[] modifyImplementationHook(final byte[] input) {
        if (input == null) {
            return input;
        }
        byte[] result = input.clone();
        int size = input.length;
        if (size != 0) {
            for (int i = 0; i < shuffle.length - 1; i += 2) {
                int p1 = (shuffle[i] & 0xff) % size;
                int p2 = (shuffle[i + 1] & 0xff) % size;
                byte tmp = result[p1];
                result[p1] = result[p2];
                result[p2] = tmp;
            }
        }
        return result;
    }

    public byte[] getShuffle() {
        return shuffle;
    }

    public void setShuffle(byte[] shuffle) {
        this.shuffle = shuffle;
    }

    @Override
    public VariableModification<byte[]> getModifiedCopy() {
        Random r = new Random();
        int index = r.nextInt(shuffle.length);
        byte[] newValue = Arrays.copyOf(shuffle, shuffle.length);
        newValue[index] = (byte) r.nextInt(MAX_MODIFIER_VALUE);
        return new ByteArrayShuffleModification(shuffle);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + Arrays.hashCode(this.shuffle);
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
        final ByteArrayShuffleModification other = (ByteArrayShuffleModification) obj;
        if (!Arrays.equals(this.shuffle, other.shuffle)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ByteArrayShuffleModification{" + "shuffle=" + ArrayConverter.bytesToHexString(shuffle) + '}';
    }

}
