/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.bytearray;

import de.rub.nds.modifiablevariable.VariableModification;
import de.rub.nds.modifiablevariable.util.ArrayConverter;
import de.rub.nds.modifiablevariable.util.UnformattedByteArrayAdapter;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Arrays;
import java.util.Objects;

/**
 * A modification that replaces the original byte array with an explicitly defined value.
 *
 * <p>This modification ignores the original value and always returns a predefined byte array
 * specified at initialization or via setter. It can be used to inject specific byte sequences
 * regardless of the original content.
 *
 * @see ModifiableByteArray
 */
@XmlRootElement
public class ByteArrayExplicitValueModification extends VariableModification<byte[]> {

    /** The explicit byte array that will replace the original value */
    @XmlJavaTypeAdapter(UnformattedByteArrayAdapter.class)
    private byte[] explicitValue;

    /** Default constructor for serialization. */
    @SuppressWarnings("unused")
    private ByteArrayExplicitValueModification() {
        super();
    }

    /**
     * Creates a new explicit value modification with the specified value.
     *
     * @param explicitValue The byte array that will replace the original value
     */
    public ByteArrayExplicitValueModification(byte[] explicitValue) {
        super();
        this.explicitValue =
                Objects.requireNonNull(explicitValue, "ExplicitValue must not be null");
    }

    /**
     * Copy constructor.
     *
     * @param other The modification to copy
     */
    public ByteArrayExplicitValueModification(ByteArrayExplicitValueModification other) {
        super();
            explicitValue = other.explicitValue.clone();
    }

    /**
     * Creates a deep copy of this modification.
     *
     * @return A new instance with a clone of the explicit value
     */
    @Override
    public ByteArrayExplicitValueModification createCopy() {
        return new ByteArrayExplicitValueModification(this);
    }

    /**
     * Modifies the input by replacing it with the explicit value.
     *
     * <p>This method ignores the input value and always returns a clone of the explicit value. A
     * defensive copy is returned to prevent modification of the internal value.
     *
     * @param input The original byte array (ignored except for null check)
     * @return A clone of the explicit value, or null if input was null
     */
    @Override
    protected byte[] modifyImplementationHook(byte[] input) {
        if (input == null) {
            return null;
        }
        return explicitValue.clone();
    }

    /**
     * Gets the explicit value that will replace the original value.
     *
     * @return The explicit byte array
     */
    public byte[] getExplicitValue() {
        return explicitValue;
    }

    /**
     * Sets the explicit value that will replace the original value.
     *
     * @param explicitValue The new explicit byte array to use
     */
    public void setExplicitValue(byte[] explicitValue) {
        this.explicitValue =
                Objects.requireNonNull(explicitValue, "ExplicitValue must not be null");
    }

    /**
     * Computes a hash code for this modification. The hash code is based on the explicit value.
     *
     * @return The hash code value
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Arrays.hashCode(explicitValue);
        return hash;
    }

    /**
     * Checks if this modification is equal to another object. Two
     * ByteArrayExplicitValueModification instances are considered equal if they have the same
     * explicit value (compared by content).
     *
     * @param obj The object to compare with
     * @return true if the objects are equal, false otherwise
     */
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
        ByteArrayExplicitValueModification other = (ByteArrayExplicitValueModification) obj;
        return Arrays.equals(explicitValue, other.explicitValue);
    }

    /**
     * Returns a string representation of this modification.
     *
     * @return A string containing the modification type and explicit value as hex
     */
    @Override
    public String toString() {
        return "ByteArrayExplicitValueModification{"
                + "explicitValue="
                + ArrayConverter.bytesToHexString(explicitValue)
                + '}';
    }
}
