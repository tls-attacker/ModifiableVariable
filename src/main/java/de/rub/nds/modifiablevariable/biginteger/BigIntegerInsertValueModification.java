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

    public BigIntegerInsertValueModification() {}

    public BigIntegerInsertValueModification(BigInteger bi, int startPosition) {
        this.insertValue = bi;
        this.startPosition = startPosition;
    }

    @Override
    protected BigInteger modifyImplementationHook(BigInteger input) {
        if (input == null) {
            input = BigInteger.ZERO;
        }
        int originalValueLength = input.bitLength();
        int insertValueLength = insertValue.bitLength();
        int insertPosition = startPosition;
        if (startPosition > originalValueLength) {
            insertPosition = originalValueLength;
        } else if (startPosition < 0) {
            insertPosition = 0;
        }
        BigInteger mask = BigInteger.valueOf((1L << insertPosition) - 1);

        return input.shiftRight(insertPosition)
                .shiftLeft(insertValueLength)
                .and(insertValue)
                .shiftLeft(insertPosition)
                .add(mask.and(input));
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
    public int hashCode() {
        int hash = 7;
        hash = 58 * hash + Objects.hashCode(this.insertValue);
        hash = 58 * hash + Objects.hashCode(this.startPosition);
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
        final BigIntegerInsertValueModification other = (BigIntegerInsertValueModification) obj;
        if (this.startPosition != other.startPosition) {
            return false;
        }
        return Objects.equals(this.insertValue, other.insertValue);
    }
}
