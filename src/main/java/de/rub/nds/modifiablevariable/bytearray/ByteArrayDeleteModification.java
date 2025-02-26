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
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.Arrays;

@XmlRootElement
public class ByteArrayDeleteModification extends VariableModification<byte[]> {

    private int count;

    private int startPosition;

    public ByteArrayDeleteModification() {
        super();
    }

    public ByteArrayDeleteModification(int startPosition, int count) {
        super();
        this.startPosition = startPosition;
        this.count = count;
    }

    public ByteArrayDeleteModification(ByteArrayDeleteModification other) {
        super(other);
        count = other.count;
        startPosition = other.startPosition;
    }

    @Override
    public ByteArrayDeleteModification createCopy() {
        return new ByteArrayDeleteModification(this);
    }

    @Override
    protected byte[] modifyImplementationHook(byte[] input) {
        if (input == null) {
            input = new byte[0];
        }
        if (input.length == 0) {
            return input;
        }

        // Wrap around and also allow to delete at the end of the original value
        int deleteStartPosition = startPosition % input.length;
        if (startPosition < 0) {
            deleteStartPosition += input.length - 1;
        }

        // If the end position overflows, it is fixed at the end of the byte array
        int deleteEndPosition = deleteStartPosition + Math.max(0, count);
        if (deleteEndPosition > input.length) {
            deleteEndPosition = input.length;
        }

        byte[] ret1 = Arrays.copyOf(input, deleteStartPosition);
        if (deleteEndPosition < input.length) {
            byte[] ret2 = Arrays.copyOfRange(input, deleteEndPosition, input.length);
            return ArrayConverter.concatenate(ret1, ret2);
        }
        return ret1;
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
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + count;
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
        ByteArrayDeleteModification other = (ByteArrayDeleteModification) obj;
        if (count != other.count) {
            return false;
        }
        return startPosition == other.startPosition;
    }

    @Override
    public String toString() {
        return "ByteArrayDeleteModification{"
                + "count="
                + count
                + ", startPosition="
                + startPosition
                + '}';
    }
}
