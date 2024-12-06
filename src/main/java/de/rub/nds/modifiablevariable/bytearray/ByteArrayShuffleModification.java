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

/**
 * Shuffles the byte array, using a pre-defined array of array pointers (#shuffle). Array pointers
 * are currently defined as bytes, since we are modifying rather smaller arrays.
 */
@XmlRootElement
@XmlType(propOrder = {"shuffle", "modificationFilter"})
@XmlAccessorType(XmlAccessType.FIELD)
public class ByteArrayShuffleModification extends VariableModification<byte[]> {

    private static final int MAX_MODIFIER_VALUE = 256;

    @XmlJavaTypeAdapter(UnformattedByteArrayAdapter.class)
    private byte[] shuffle;

    public ByteArrayShuffleModification() {
        super();
    }

    public ByteArrayShuffleModification(byte[] shuffle) {
        super();
        this.shuffle = shuffle;
    }

    public ByteArrayShuffleModification(ByteArrayShuffleModification other) {
        super(other);
        shuffle = other.shuffle != null ? other.shuffle.clone() : null;
    }

    @Override
    public ByteArrayShuffleModification createCopy() {
        return new ByteArrayShuffleModification(this);
    }

    @Override
    protected byte[] modifyImplementationHook(byte[] input) {
        if (input == null) {
            return input;
        }
        byte[] result = input.clone();
        int size = input.length;
        if (size > 255) {
            for (int i = 0; i < shuffle.length - 3; i += 4) {
                // Combine two consecutive bytes to form 16-bit values
                int p1 = ((shuffle[i] & 0xff) << 8 | shuffle[i + 1] & 0xff) % size;
                int p2 = ((shuffle[i + 2] & 0xff) << 8 | shuffle[i + 3] & 0xff) % size;
                byte tmp = result[p1];
                result[p1] = result[p2];
                result[p2] = tmp;
            }
        } else if (size > 0) {
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
        return new ByteArrayShuffleModification(newValue);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Arrays.hashCode(shuffle);
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
        ByteArrayShuffleModification other = (ByteArrayShuffleModification) obj;
        return Arrays.equals(shuffle, other.shuffle);
    }

    @Override
    public String toString() {
        return "ByteArrayShuffleModification{"
                + "shuffle="
                + ArrayConverter.bytesToHexString(shuffle)
                + '}';
    }
}
