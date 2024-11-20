/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.string;

import de.rub.nds.modifiablevariable.VariableModification;
import de.rub.nds.modifiablevariable.util.IllegalStringAdapter;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.util.Objects;
import java.util.Random;

/** Modification that appends a string to the original value. */
@XmlRootElement
@XmlType(propOrder = {"insertValue", "modificationFilter"})
public class StringInsertValueModification extends VariableModification<String> {

    private static final int MAX_EXPLICIT_VALUE = 256;

    private static final int MAX_INSERT_MODIFIER = 32;

    @XmlJavaTypeAdapter(IllegalStringAdapter.class)
    private String insertValue;

    private int startPosition;

    public StringInsertValueModification() {}

    public StringInsertValueModification(final String insertValue, int startPosition) {
        this.insertValue = insertValue;
        this.startPosition = startPosition;
    }

    @Override
    protected String modifyImplementationHook(final String input) {
        int start = startPosition;
        if (start < 0) {
            start += input.length();
            if (start < 0) {
                LOGGER.debug("Trying to insert from too negative start position. start = {}", startPosition);
                return input;
            }
        }
        if (start > input.length()) {
            LOGGER.debug("Trying to insert behind the string. String Length:{} Insert Position:{}", input.length(), startPosition);
            return input;
        }

        return new StringBuilder(input).insert(start, insertValue).toString();
    }

    public String getInsertValue() {
        return this.insertValue;
    }

    public void setInsertValue(final String insertValue) {
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
        hash = 63 * hash + Objects.hashCode(this.insertValue);
        hash = 63 * hash + this.startPosition;
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
        final StringInsertValueModification other = (StringInsertValueModification) obj;
        if (this.startPosition != other.startPosition) {
            return false;
        }
        return Objects.equals(this.insertValue, other.getInsertValue());
    }
}
