/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.path;

import de.rub.nds.modifiablevariable.VariableModification;
import de.rub.nds.modifiablevariable.util.ArrayConverter;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import java.util.Arrays;
import java.util.Objects;

/** Modification that deletes path parts from the original value. */
@XmlRootElement
@XmlType(propOrder = {"count", "startPosition", "modificationFilter"})
public class PathDeleteModification extends VariableModification<String> {

    private static final int MAX_MODIFIER_LENGTH = 32;

    private int count;

    private int startPosition;

    public PathDeleteModification() {
        super();
    }

    public PathDeleteModification(int startPosition, int count) {
        super();
        this.count = count;
        this.startPosition = startPosition;
    }

    public PathDeleteModification(PathDeleteModification other) {
        super(other);
        count = other.count;
        startPosition = other.startPosition;
    }

    @Override
    public PathDeleteModification createCopy() {
        return new PathDeleteModification(this);
    }

    @Override
    protected String modifyImplementationHook(String input) {
        if (input == null) {
            return null;
        }
        if (input.isEmpty()) {
            return input;
        }
        String[] pathParts = input.split("/");
        if (pathParts.length == 0) {
            // It is just a single slash
            if (count == 0) {
                return input;
            }
            return "";
        }
        boolean leadingSlash = pathParts[0].isEmpty();

        // Wrap around and also allow to delete at the end of the original value
        int deleteStartPosition =
                PathUtil.getPathPartPosition(startPosition, leadingSlash, pathParts);
        // If the end position overflows, it is fixed at the end of the path
        int deleteEndPosition = deleteStartPosition + count;
        if (deleteEndPosition > pathParts.length) {
            deleteEndPosition = pathParts.length;
        }

        String[] partsBefore = Arrays.copyOf(pathParts, deleteStartPosition);
        String[] resultParts;
        if (deleteEndPosition < pathParts.length) {
            String[] partsAfter =
                    Arrays.copyOfRange(pathParts, deleteEndPosition, pathParts.length);
            resultParts = ArrayConverter.concatenate(partsBefore, partsAfter);
        } else {
            resultParts = partsBefore;
        }

        if (input.endsWith("/") && resultParts.length > 0) {
            resultParts[resultParts.length - 1] += "/";
        }
        return String.join("/", resultParts);
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
        PathDeleteModification other = (PathDeleteModification) obj;
        if (startPosition != other.startPosition) {
            return false;
        }
        return Objects.equals(count, other.count);
    }

    @Override
    public String toString() {
        return "PathDeleteModification{"
                + "count="
                + count
                + ", startPosition="
                + startPosition
                + '}';
    }
}
