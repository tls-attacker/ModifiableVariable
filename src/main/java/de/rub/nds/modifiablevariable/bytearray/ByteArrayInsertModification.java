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
@XmlType(propOrder = { "bytesToInsert", "startPosition", "modificationFilter", "postModification" })
public class ByteArrayInsertModification extends VariableModification<byte[]> {

    private byte[] bytesToInsert;

    private int startPosition;

    public ByteArrayInsertModification() {

    }

    public ByteArrayInsertModification(byte[] bytesToInsert, int startPosition) {
        this.bytesToInsert = bytesToInsert;
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
            if (start < 0) {
                LOGGER.debug("Trying to insert from too negative Startposition. start = " + startPosition);
                return input;
            }
        }
        if (startPosition > input.length) {
            LOGGER.debug("Trying to insert behind the Array. ArraySize:" + input.length + " Insert Position:"
                    + startPosition);
            return input;
        }
        byte[] ret1 = Arrays.copyOf(input, start);
        byte[] ret3 = null;
        if ((start) < input.length) {
            ret3 = Arrays.copyOfRange(input, start, input.length);
        }
        return ArrayConverter.concatenate(ret1, bytesToInsert, ret3);
    }

    @XmlJavaTypeAdapter(ByteArrayAdapter.class)
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
            newValue[index] = (byte) r.nextInt(256);
            return new ByteArrayInsertModification(newValue, startPosition);
        } else {
            byte[] newValue = Arrays.copyOf(bytesToInsert, bytesToInsert.length);
            int modifier = r.nextInt(32);
            if (r.nextBoolean()) {
                modifier *= -1;
            }
            modifier = startPosition + modifier;
            if (modifier <= 0) {
                modifier = 1;
            }
            return new ByteArrayInsertModification(newValue, modifier);
        }
    }
}
