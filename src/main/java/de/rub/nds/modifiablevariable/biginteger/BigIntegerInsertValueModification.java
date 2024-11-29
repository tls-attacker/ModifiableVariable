/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.biginteger;

import de.rub.nds.modifiablevariable.VariableModification;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import java.math.BigInteger;
import java.util.Objects;
import java.util.Random;

@XmlRootElement
@XmlType(propOrder = {"insertValue", "startPosition", "modificationFilter"})
@XmlAccessorType(XmlAccessType.FIELD)
public class BigIntegerInsertValueModification extends VariableModification<BigInteger> {

    private static final int MAX_INSERT_LENGTH = 8;
    private static final int MAX_POSITION_MODIFIER = 32;

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
    public VariableModification<BigInteger> getModifiedCopy() {
        Random r = new Random();

        if (r.nextBoolean()) {
            return new BigIntegerInsertValueModification(
                    insertValue.add(new BigInteger(MAX_INSERT_LENGTH, r)), startPosition);
        } else {
            int modifier = r.nextInt(MAX_POSITION_MODIFIER);
            if (r.nextBoolean()) {
                modifier *= -1;
            }
            modifier = startPosition + modifier;
            if (modifier <= 0) {
                modifier = 1;
            }
            return new BigIntegerInsertValueModification(insertValue, modifier);
        }
    }

    @Override
    public VariableModification<BigInteger> createCopy() {
        return new BigIntegerInsertValueModification(insertValue, startPosition);
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
        BigIntegerInsertValueModification other = (BigIntegerInsertValueModification) obj;
        if (startPosition != other.startPosition) {
            return false;
        }
        return Objects.equals(insertValue, other.insertValue);
    }
}
