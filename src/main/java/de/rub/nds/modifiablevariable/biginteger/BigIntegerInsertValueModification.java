/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.biginteger;

import de.rub.nds.modifiablevariable.VariableModification;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import java.math.BigInteger;
import java.util.Objects;

@XmlRootElement
@XmlType(propOrder = {"insertValue", "startPosition", "modificationFilter"})
public class BigIntegerInsertValueModification extends VariableModification<BigInteger> {

    private BigInteger insertValue;
    private int startPosition;

    public BigIntegerInsertValueModification() {
        super();
    }

    public BigIntegerInsertValueModification(BigInteger insertValue, int startPosition) {
        super();
        this.insertValue = insertValue;
        this.startPosition = startPosition;
    }

    public BigIntegerInsertValueModification(BigIntegerInsertValueModification other) {
        super(other);
        insertValue = other.insertValue;
        startPosition = other.startPosition;
    }

    @Override
    public BigIntegerInsertValueModification createCopy() {
        return new BigIntegerInsertValueModification(this);
    }

    @Override
    protected BigInteger modifyImplementationHook(BigInteger input) {
        if (input == null) {
            input = BigInteger.ZERO;
        }

        int originalValueLength = input.bitLength();
        int insertValueLength = insertValue.bitLength();

        // Wrap around and also allow to insert at the end of the original value
        int insertPosition = startPosition % (originalValueLength + 1);
        if (startPosition < 0) {
            insertPosition += originalValueLength;
        }

        BigInteger mask = BigInteger.valueOf((1L << insertPosition) - 1);

        return input.shiftRight(insertPosition)
                .shiftLeft(insertValueLength)
                .or(insertValue)
                .shiftLeft(insertPosition)
                .or(mask.and(input));
    }

    public BigInteger getInsertValue() {
        return insertValue;
    }

    public void setInsertValue(BigInteger insertValue) {
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
        BigIntegerInsertValueModification other = (BigIntegerInsertValueModification) obj;
        if (startPosition != other.startPosition) {
            return false;
        }
        return Objects.equals(insertValue, other.insertValue);
    }

    @Override
    public String toString() {
        return "BigIntegerInsertValueModification{"
                + "insertValue="
                + insertValue
                + ", startPosition="
                + startPosition
                + '}';
    }
}
