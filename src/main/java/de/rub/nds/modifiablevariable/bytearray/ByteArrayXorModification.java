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
import de.rub.nds.modifiablevariable.util.ArrayConverter;
import de.rub.nds.modifiablevariable.util.ByteArrayAdapter;
import java.util.Arrays;
import java.util.Random;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement
@XmlType(propOrder = { "xor", "startPosition", "modificationFilter", "postModification" })
public class ByteArrayXorModification extends VariableModification<byte[]> {

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
            LOGGER.debug(String.format(
                    "Input {%s} of length %d cannot be xored with {%s} of length %d with start position %d",
                    ArrayConverter.bytesToHexString(input), input.length, ArrayConverter.bytesToHexString(xor),
                    xor.length, startPosition));
            return input;
        }
        for (int i = 0; i < xor.length; ++i) {
            result[start + i] = (byte) (input[start + i] ^ xor[i]);
        }
        return result;
    }

    @XmlJavaTypeAdapter(ByteArrayAdapter.class)
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
    protected VariableModification<byte[]> getModifiedCopy() {
        Random r = new Random();
        if (r.nextBoolean()) {
            int index = r.nextInt(xor.length);
            byte[] newValue = Arrays.copyOf(xor, xor.length);
            newValue[index] = (byte) r.nextInt(256);
            return new ByteArrayXorModification(newValue, startPosition);
        } else {
            byte[] newValue = Arrays.copyOf(xor, xor.length);
            int modifier = r.nextInt(32);
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
}
