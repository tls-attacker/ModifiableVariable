/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.string;

import static de.rub.nds.modifiablevariable.util.StringUtil.backslashEscapeString;

import de.rub.nds.modifiablevariable.VariableModification;
import de.rub.nds.modifiablevariable.util.IllegalStringAdapter;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Objects;
import java.util.Random;

/** Modification that inserts a string to the original value. */
@XmlRootElement
@XmlType(propOrder = {"insertValue", "startPosition", "modificationFilter"})
public class StringInsertValueModification extends VariableModification<String> {

    private static final int MAX_EXPLICIT_VALUE = 256;

    private static final int MAX_INSERT_MODIFIER = 32;

    @XmlJavaTypeAdapter(IllegalStringAdapter.class)
    private String insertValue;

    private int startPosition;

    public StringInsertValueModification() {
        super();
    }

    public StringInsertValueModification(String insertValue, int startPosition) {
        super();
        this.insertValue = insertValue;
        this.startPosition = startPosition;
    }

    public StringInsertValueModification(StringInsertValueModification other) {
        super(other);
        insertValue = other.insertValue;
        startPosition = other.startPosition;
    }

    @Override
    public StringInsertValueModification createCopy() {
        return new StringInsertValueModification(this);
    }

    @Override
    protected String modifyImplementationHook(String input) {
        if (input == null) {
            return null;
        }
        // Wrap around and also allow to insert at the end of the original value
        int insertPosition = startPosition % (input.length() + 1);
        if (startPosition < 0) {
            insertPosition += input.length();
        }

        return new StringBuilder(input).insert(insertPosition, insertValue).toString();
    }

    public String getInsertValue() {
        return insertValue;
    }

    public void setInsertValue(String insertValue) {
        this.insertValue = insertValue;
    }

    public int getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(int startPosition) {
        this.startPosition = startPosition;
    }

    @Override
    public VariableModification<String> getModifiedCopy() {
        Random r = new Random();

        if (r.nextBoolean()) {
            int index = r.nextInt(insertValue.length());
            char randomChar = (char) r.nextInt(MAX_EXPLICIT_VALUE);
            StringBuilder modifiedString = new StringBuilder(insertValue);
            modifiedString.setCharAt(index, randomChar);
            return new StringInsertValueModification(modifiedString.toString(), startPosition);
        } else {
            int modifier = r.nextInt(MAX_INSERT_MODIFIER);
            if (r.nextBoolean()) {
                modifier *= -1;
            }
            modifier = startPosition + modifier;
            if (modifier <= 0) {
                modifier = 1;
            }
            return new StringInsertValueModification(insertValue, modifier);
        }
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
        StringInsertValueModification other = (StringInsertValueModification) obj;
        if (startPosition != other.startPosition) {
            return false;
        }
        return Objects.equals(insertValue, other.insertValue);
    }

    @Override
    public String toString() {
        return "StringInsertValueModification{"
                + "insertValue='"
                + backslashEscapeString(insertValue)
                + '\''
                + ", startPosition="
                + startPosition
                + '}';
    }
}
