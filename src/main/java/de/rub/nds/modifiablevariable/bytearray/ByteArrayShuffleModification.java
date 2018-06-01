/**
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Copyright 2014-2017 Ruhr University Bochum / Hackmanit GmbH
 *
 * Licensed under Apache License 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.bytearray;

import de.rub.nds.modifiablevariable.VariableModification;
import de.rub.nds.modifiablevariable.util.ByteArrayAdapter;
import java.util.Arrays;
import java.util.Random;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Shuffles the byte array, using a pre-defined array of array pointers
 * (#shuffle). Array pointers are currently defined as bytes, since we are
 * modifying rather smaller arrays.
 *
 */
@XmlRootElement
@XmlType(propOrder = { "shuffle", "modificationFilter", "postModification" })
public class ByteArrayShuffleModification extends VariableModification<byte[]> {

    private final static int MAX_MODIFIER_VALUE = 256;

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

    @XmlJavaTypeAdapter(ByteArrayAdapter.class)
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
}
