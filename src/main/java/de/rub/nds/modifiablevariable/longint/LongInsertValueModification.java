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
import java.util.Random;

@XmlRootElement
@XmlType(propOrder = {"insertValue", "startPosition", "modificationFilter"})
public class LongInsertValueModification extends VariableModification<Long> {

    private static final int MAX_VALUE_MODIFIER = 256;
    private static final int MAX_POSITION_MODIFIER = 32;

    private Long insertValue;
    private int startPosition;

    public LongInsertValueModification() {}

    public LongInsertValueModification(Long insertValue, int startPosition) {
        this.insertValue = insertValue;
        this.startPosition = startPosition;
    }

    @Override
    protected Long modifyImplementationHook(Long input) {
        if (input == null) {
            input = 0L;
        }

        int insertValueLength = Long.SIZE - Long.numberOfLeadingZeros((insertValue));

        // Wrap around long size
        int insertPosition = startPosition % Long.SIZE;
        if (startPosition < 0) {
            insertPosition += Long.SIZE - 1;
        }

        long mask = ((1L << insertPosition) - 1);

        return (((input >> insertPosition) << insertValueLength) | insertValue) << insertPosition
                | (mask & input);
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
    public VariableModification<Long> getModifiedCopy() {
        Random r = new Random();

        if (r.nextBoolean()) {
            return new LongInsertValueModification(
                    insertValue + r.nextInt(MAX_VALUE_MODIFIER), startPosition);
        } else {
            int modifier = r.nextInt(MAX_POSITION_MODIFIER);
            if (r.nextBoolean()) {
                modifier *= -1;
            }
            modifier = startPosition + modifier;
            if (modifier <= 0) {
                modifier = 1;
            }
            return new LongInsertValueModification(insertValue, modifier);
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + insertValue.hashCode();
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
        final LongInsertValueModification other = (LongInsertValueModification) obj;
        if (this.startPosition != other.startPosition) {
            return false;
        }
        return Objects.equals(this.insertValue, other.insertValue);
    }
}
