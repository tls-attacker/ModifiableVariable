/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.string;

import de.rub.nds.modifiablevariable.VariableModification;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import java.util.Objects;
import java.util.Random;

/** Modification that deletes part of a string from the original value. */
@XmlRootElement
@XmlType(propOrder = {"count", "startPosition", "modificationFilter"})
public class StringDeleteModification extends VariableModification<String> {

    private static final int MAX_MODIFIER_LENGTH = 32;

    private int count;

    private int startPosition;

    public StringDeleteModification() {
        super();
    }

    public StringDeleteModification(int startPosition, int count) {
        super();
        this.startPosition = startPosition;
        this.count = count;
    }

    public StringDeleteModification(StringDeleteModification other) {
        super(other);
        count = other.count;
        startPosition = other.startPosition;
    }

    @Override
    public StringDeleteModification createCopy() {
        return new StringDeleteModification(this);
    }

    @Override
    protected String modifyImplementationHook(String input) {
        if (input == null) {
            return null;
        }
        if (input.isEmpty()) {
            return input;
        }

        // Wrap around and also allow to delete at the end of the original value
        int deleteStartPosition = startPosition % input.length();
        if (startPosition < 0) {
            deleteStartPosition += input.length() - 1;
        }

        // If the end position overflows, it is fixed at the end of the string
        int deleteEndPosition = deleteStartPosition + Math.max(0, count);
        if (deleteEndPosition > input.length()) {
            deleteEndPosition = input.length();
        }

        return new StringBuilder(input).delete(deleteStartPosition, deleteEndPosition).toString();
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
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
            int modifier = r.nextInt(MAX_MODIFIER_LENGTH);
            if (r.nextBoolean()) {
                modifier *= -1;
            }
            modifier = count + modifier;
            if (modifier <= 0) {
                modifier = 1;
            }
            return new StringDeleteModification(modifier, startPosition);
        } else {
            int modifier = r.nextInt(MAX_MODIFIER_LENGTH);
            if (r.nextBoolean()) {
                modifier *= -1;
            }
            modifier = startPosition + modifier;
            if (modifier <= 0) {
                modifier = 1;
            }
            return new StringDeleteModification(count, modifier);
        }
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
        StringDeleteModification other = (StringDeleteModification) obj;
        if (startPosition != other.startPosition) {
            return false;
        }
        return Objects.equals(count, other.count);
    }

    @Override
    public String toString() {
        return "StringDeleteModification{"
                + "count="
                + count
                + ", startPosition="
                + startPosition
                + '}';
    }
}
