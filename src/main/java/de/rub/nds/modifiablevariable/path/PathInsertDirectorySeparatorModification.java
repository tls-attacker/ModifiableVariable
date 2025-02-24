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

/** Modification that inserts directory separators as path parts to the original value. */
@XmlRootElement
@XmlType(propOrder = {"insertValue", "count", "startPosition", "modificationFilter"})
public class PathInsertDirectorySeparatorModification extends VariableModification<String> {

    private static final int MAX_INSERT_MODIFIER = 32;

    @XmlJavaTypeAdapter(IllegalStringAdapter.class)
    private String insertValue;

    private int count;
    private int startPosition;

    public PathInsertDirectorySeparatorModification() {
        super();
    }

    public PathInsertDirectorySeparatorModification(int count, int startPosition) {
        super();
        this.count = count;
        this.startPosition = startPosition;
        updateInsertValue();
    }

    public PathInsertDirectorySeparatorModification(
            PathInsertDirectorySeparatorModification other) {
        super(other);
        insertValue = other.insertValue;
        count = other.count;
        startPosition = other.startPosition;
    }

    @Override
    public PathInsertDirectorySeparatorModification createCopy() {
        return new PathInsertDirectorySeparatorModification(this);
    }

    private void updateInsertValue() {
        insertValue = "/".repeat(Math.max(0, count));
    }

    @Override
    protected String modifyImplementationHook(String input) {
        return PathUtil.insertValueAsPathPart(input, insertValue, startPosition);
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
        updateInsertValue();
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
        PathInsertDirectorySeparatorModification other =
                (PathInsertDirectorySeparatorModification) obj;
        if (startPosition != other.startPosition) {
            return false;
        }
        return Objects.equals(count, other.count);
    }

    @Override
    public String toString() {
        return "PathInsertDirectorySeparatorModification{"
                + "insertValue='"
                + insertValue
                + '\''
                + ", count="
                + count
                + ", startPosition="
                + startPosition
                + '}';
    }
}
