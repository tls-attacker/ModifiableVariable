/**
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Copyright 2014-2022 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 *
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.modifiablevariable.bytearray;

import static de.rub.nds.modifiablevariable.util.ArrayConverter.bytesToHexString;

import de.rub.nds.modifiablevariable.VariableModification;
import de.rub.nds.modifiablevariable.util.ArrayConverter;
import java.util.Arrays;
import java.util.Random;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(propOrder = { "count", "startPosition", "modificationFilter" })
@XmlAccessorType(XmlAccessType.FIELD)
public class ByteArrayDeleteModification extends VariableModification<byte[]> {

    private static final int MAX_MODIFIER_LENGTH = 32;

    private int count;

    private int startPosition;

    public ByteArrayDeleteModification() {

    }

    public ByteArrayDeleteModification(int startPosition, int count) {
        this.startPosition = startPosition;
        this.count = count;
    }

    @Override
    protected byte[] modifyImplementationHook(byte[] input) {
        if (input == null) {
            input = new byte[0];
        }
        int start = startPosition;
        if (start < 0) {
            start += input.length;
            if (start < 0) {
                LOGGER.debug("Trying to delete from too negative Startposition. start = " + (start - input.length));
                return input;
            }
        }
        final int endPosition = start + count;
        if ((endPosition) > input.length) {
            LOGGER.debug(String.format("Bytes %d..%d cannot be deleted from {%s} of length %d", start, endPosition,
                bytesToHexString(input), input.length));
            return input;
        }
        if (count <= 0) {
            LOGGER.debug("You must delete at least one byte. count = " + count);
            return input;
        }
        byte[] ret1 = Arrays.copyOf(input, start);
        byte[] ret2 = null;
        if ((endPosition) < input.length) {
            ret2 = Arrays.copyOfRange(input, endPosition, input.length);
        }
        return ArrayConverter.concatenate(ret1, ret2);
    }

    public int getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(int startPosition) {
        this.startPosition = startPosition;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public VariableModification<byte[]> getModifiedCopy() {
        Random r = new Random();
        int modifier = r.nextInt(MAX_MODIFIER_LENGTH);
        if (r.nextBoolean()) {
            modifier *= -1;
        }
        if (r.nextBoolean()) {
            modifier = startPosition + modifier;
            if (modifier <= 0) {
                modifier = 0;
            }
            return new ByteArrayDeleteModification(modifier, count);
        } else {
            modifier = startPosition + count;
            if (modifier <= 0) {
                modifier = 1;
            }
            return new ByteArrayDeleteModification(startPosition, modifier);
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + this.count;
        hash = 89 * hash + this.startPosition;
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
        final ByteArrayDeleteModification other = (ByteArrayDeleteModification) obj;
        if (this.count != other.count) {
            return false;
        }
        if (this.startPosition != other.startPosition) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ByteArrayDeleteModification{" + "count=" + count + ", startPosition=" + startPosition + '}';
    }

}
