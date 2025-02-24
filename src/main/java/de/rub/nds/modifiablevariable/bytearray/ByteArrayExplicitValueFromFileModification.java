/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.bytearray;

import de.rub.nds.modifiablevariable.util.ArrayConverter;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import java.util.Arrays;

@XmlRootElement
@XmlType(propOrder = "index")
public class ByteArrayExplicitValueFromFileModification extends ByteArrayExplicitValueModification {
    private int index;

    public ByteArrayExplicitValueFromFileModification() {
        super();
    }

    public ByteArrayExplicitValueFromFileModification(int index, byte[] explicitValue) {
        super(explicitValue);
        this.index = index;
    }

    public ByteArrayExplicitValueFromFileModification(
            ByteArrayExplicitValueFromFileModification other) {
        super(other);
        index = other.index;
    }

    @Override
    public ByteArrayExplicitValueFromFileModification createCopy() {
        return new ByteArrayExplicitValueFromFileModification(this);
    }

    public int getIndex() {
        return index;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + index;
        hash = 31 * hash + Arrays.hashCode(explicitValue);
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
        ByteArrayExplicitValueFromFileModification other =
                (ByteArrayExplicitValueFromFileModification) obj;
        if (index != other.index) {
            return false;
        }
        return Arrays.equals(explicitValue, other.explicitValue);
    }

    @Override
    public String toString() {
        return "ByteArrayExplicitValueFromFileModification{"
                + "index="
                + index
                + ", explicitValue="
                + ArrayConverter.bytesToHexString(explicitValue)
                + '}';
    }
}
