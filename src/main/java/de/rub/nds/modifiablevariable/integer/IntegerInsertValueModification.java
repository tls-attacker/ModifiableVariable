/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.integer;

import de.rub.nds.modifiablevariable.VariableModification;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import java.util.Objects;

@XmlRootElement
@XmlType(propOrder = {"insertValue", "startPosition", "modificationFilter"})
public class IntegerInsertValueModification extends VariableModification<Integer> {

    private Integer insertValue;

    private int startPosition;

    public IntegerInsertValueModification() {
        super();
    }

    public IntegerInsertValueModification(Integer insertValue, int startPosition) {
        super();
        this.insertValue = insertValue;
        this.startPosition = startPosition;
    }

    public IntegerInsertValueModification(IntegerInsertValueModification other) {
        super(other);
        insertValue = other.insertValue;
        startPosition = other.startPosition;
    }

    @Override
    public IntegerInsertValueModification createCopy() {
        return new IntegerInsertValueModification(this);
    }

    @Override
    protected Integer modifyImplementationHook(Integer input) {
        if (input == null) {
            input = 0;
        }

        int insertValueLength = Integer.SIZE - Integer.numberOfLeadingZeros(insertValue);

        // Wrap around integer size
        int insertPosition = startPosition % Integer.SIZE;
        if (startPosition < 0) {
            insertPosition += Integer.SIZE - 1;
        }

        int mask = (1 << insertPosition) - 1;

        return (input >> insertPosition << insertValueLength | insertValue) << insertPosition
                | mask & input;
    }

    public Integer getInsertValue() {
        return insertValue;
    }

    public void setInsertValue(Integer insertValue) {
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
        hash = 31 * hash + insertValue;
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
        IntegerInsertValueModification other = (IntegerInsertValueModification) obj;
        if (startPosition != other.startPosition) {
            return false;
        }
        return Objects.equals(insertValue, other.insertValue);
    }

    @Override
    public String toString() {
        return "IntegerInsertValueModification{"
                + "insertValue="
                + insertValue
                + ", startPosition="
                + startPosition
                + '}';
    }
}
