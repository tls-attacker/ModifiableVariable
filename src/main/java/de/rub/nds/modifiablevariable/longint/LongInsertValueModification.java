/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.longint;

import de.rub.nds.modifiablevariable.VariableModification;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import java.util.Objects;

@XmlRootElement
@XmlType(propOrder = {"insertValue", "startPosition", "modificationFilter"})
public class LongInsertValueModification extends VariableModification<Long> {

    private static final int MAX_VALUE_MODIFIER = 256;
    private static final int MAX_POSITION_MODIFIER = 32;

    private Long insertValue;
    private int startPosition;

    public LongInsertValueModification() {
        super();
    }

    public LongInsertValueModification(Long insertValue, int startPosition) {
        super();
        this.insertValue = insertValue;
        this.startPosition = startPosition;
    }

    public LongInsertValueModification(LongInsertValueModification other) {
        super(other);
        insertValue = other.insertValue;
        startPosition = other.startPosition;
    }

    @Override
    public LongInsertValueModification createCopy() {
        return new LongInsertValueModification(this);
    }

    @Override
    protected Long modifyImplementationHook(Long input) {
        if (input == null) {
            input = 0L;
        }

        int insertValueLength = Long.SIZE - Long.numberOfLeadingZeros(insertValue);

        // Wrap around long size
        int insertPosition = startPosition % Long.SIZE;
        if (startPosition < 0) {
            insertPosition += Long.SIZE - 1;
        }

        long mask = (1L << insertPosition) - 1;

        return (input >> insertPosition << insertValueLength | insertValue) << insertPosition
                | mask & input;
    }

    public Long getInsertValue() {
        return insertValue;
    }

    public void setInsertValue(Long insertValue) {
        this.insertValue = insertValue;
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
        hash = 31 * hash + Objects.hashCode(insertValue);
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
        LongInsertValueModification other = (LongInsertValueModification) obj;
        if (startPosition != other.startPosition) {
            return false;
        }
        return Objects.equals(insertValue, other.insertValue);
    }

    @Override
    public String toString() {
        return "LongInsertValueModification{"
                + "insertValue="
                + insertValue
                + ", startPosition="
                + startPosition
                + '}';
    }
}
