/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.path;

import de.rub.nds.modifiablevariable.VariableModification;
import de.rub.nds.modifiablevariable.util.IllegalStringAdapter;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Objects;
import java.util.Random;

/** Modification that appends a string to the original value. */
@XmlRootElement
@XmlType(propOrder = {"insertValue", "startPosition", "modificationFilter"})
public class PathInsertValueModification extends VariableModification<String> {

    private static final int MAX_EXPLICIT_VALUE = 256;

    private static final int MAX_INSERT_MODIFIER = 32;

    @XmlJavaTypeAdapter(IllegalStringAdapter.class)
    private String insertValue;

    private int startPosition;

    public PathInsertValueModification() {
        super();
    }

    public PathInsertValueModification(String insertValue, int startPosition) {
        super();
        this.insertValue = insertValue;
        this.startPosition = startPosition;
    }

    public PathInsertValueModification(PathInsertValueModification other) {
        super(other);
        insertValue = other.insertValue;
        startPosition = other.startPosition;
    }

    @Override
    public PathInsertValueModification createCopy() {
        return new PathInsertValueModification(this);
    }

    @Override
    protected String modifyImplementationHook(String input) {
        if (input == null) {
            return null;
        }

        if (input.isEmpty()) {
            return insertValue;
        }
        String[] pathParts = input.split("/");
        boolean leadingSlash = pathParts[0].isEmpty();

        // Wrap around and also allow to insert at the end of the original value
        int insertPosition;
        if (leadingSlash) {
            // If the path starts with a slash, skip the first empty path part.
            insertPosition = startPosition % pathParts.length;
            if (startPosition < 0) {
                insertPosition += pathParts.length - 1;
            }
            insertPosition++;
        } else {
            insertPosition = startPosition % (pathParts.length + 1);
            if (startPosition < 0) {
                insertPosition += pathParts.length;
            }
        }

        if (insertPosition == 0 && leadingSlash) {
            pathParts[insertPosition] = "/" + insertValue;
        } else if (insertPosition == pathParts.length) {
            pathParts[insertPosition - 1] = pathParts[insertPosition - 1] + "/" + insertValue;
        } else {
            pathParts[insertPosition] = insertValue + "/" + pathParts[insertPosition];
        }
        if (input.endsWith("/")) {
            pathParts[pathParts.length - 1] += "/";
        }
        return String.join("/", pathParts);
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
            return new PathInsertValueModification(modifiedString.toString(), startPosition);
        } else {
            int modifier = r.nextInt(MAX_INSERT_MODIFIER);
            if (r.nextBoolean()) {
                modifier *= -1;
            }
            modifier = startPosition + modifier;
            if (modifier <= 0) {
                modifier = 1;
            }
            return new PathInsertValueModification(insertValue, modifier);
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
        PathInsertValueModification other = (PathInsertValueModification) obj;
        if (startPosition != other.startPosition) {
            return false;
        }
        return Objects.equals(insertValue, other.insertValue);
    }
}
