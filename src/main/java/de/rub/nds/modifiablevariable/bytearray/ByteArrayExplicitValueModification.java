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

/**
 * A modification that replaces the original byte array with an explicitly defined value.
 *
 * <p>This modification ignores the original value of a {@link ModifiableByteArray} and always
 * returns a predefined byte array specified at initialization or via setter. It's useful for
 * testing scenarios where a specific byte sequence needs to be injected regardless of the original
 * value.
 *
 * <p>Example usage:
 *
 * <pre>{@code
 * // Create a modification that always returns a specific byte array
 * byte[] fixedValue = new byte[]{0x01, 0x02, 0x03};
 * ByteArrayExplicitValueModification mod = new ByteArrayExplicitValueModification(fixedValue);
 *
 * // Apply to a variable
 * ModifiableByteArray var = new ModifiableByteArray();
 * var.setOriginalValue(new byte[]{0xFF, 0xFF, 0xFF});
 * var.setModification(mod);
 *
 * // Will always return the explicit value ({0x01, 0x02, 0x03}), not the original value
 * byte[] result = var.getValue();
 * }</pre>
 *
 * <p>This class is serializable through JAXB annotations, allowing it to be used in XML
 * configurations for testing. The explicit value is adapted using {@link
 * UnformattedByteArrayAdapter} to provide a compact hexadecimal representation.
 *
 * <p>Note that a defensive copy of the explicit value is returned when the modification is applied,
 * ensuring that the original explicit value cannot be modified through the returned reference.
 */
@XmlRootElement
public class ByteArrayExplicitValueModification extends VariableModification<byte[]> {

    /** The explicit byte array that will replace the original value */
    @XmlJavaTypeAdapter(UnformattedByteArrayAdapter.class)
    protected byte[] explicitValue;

    /**
     * Default constructor for JAXB deserialization.
     *
     * <p>When using this constructor, the explicit value must be set via {@link
     * #setExplicitValue(byte[])} before applying the modification.
     */
    public ByteArrayExplicitValueModification() {
        super();
    }

    /**
     * Creates a new modification with the specified explicit value.
     *
     * <p>This constructor sets the byte array that will replace the original value when the
     * modification is applied.
     *
     * @param explicitValue The byte array that will replace the original value
     */
    public ByteArrayExplicitValueModification(byte[] explicitValue) {
        super();
        this.explicitValue = explicitValue;
    }

    /**
     * Copy constructor for creating a deep copy of an existing modification.
     *
     * <p>This constructor creates a new instance with a clone of the explicit value from the
     * provided modification.
     *
     * @param other The modification to copy
     */
    public ByteArrayExplicitValueModification(ByteArrayExplicitValueModification other) {
        super(other);
        explicitValue = other.explicitValue != null ? other.explicitValue.clone() : null;
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
     * Implements the modification by returning the explicit value.
     *
     * <p>This method ignores the input value and always returns a clone of the explicit value set
     * during initialization or via {@link #setExplicitValue(byte[])}. If the input is null, it
     * returns null to preserve null-safety.
     *
     * <p>A defensive copy (clone) of the explicit value is returned to prevent the caller from
     * modifying the explicit value through the returned reference.
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
     * <p>Note that this method returns a direct reference to the internal explicit value, not a
     * defensive copy. Callers should be careful not to modify the returned array.
     *
     * @return The explicit byte array
     */
    public byte[] getExplicitValue() {
        return explicitValue;
    }

    /**
     * Sets the explicit value that will replace the original value.
     *
     * <p>Note that this method stores a direct reference to the provided byte array, not a
     * defensive copy. Callers should be careful not to modify the array after passing it to this
     * method.
     *
     * @param explicitValue The new explicit byte array to use
     */
    public void setExplicitValue(byte[] explicitValue) {
        this.explicitValue = explicitValue;
    }

    /**
     * Returns a string representation of this modification.
     *
     * <p>The string includes the class name and the explicit value as a hexadecimal string.
     *
     * @return A string representation of this object
     */
    @Override
    public String toString() {
        return "ByteArrayExplicitValueModification{"
                + "explicitValue="
                + ArrayConverter.bytesToHexString(explicitValue)
                + '}';
    }

    /**
     * Computes a hash code for this modification.
     *
     * <p>The hash code is based solely on the explicit value.
     *
     * @return A hash code value for this object
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Arrays.hashCode(explicitValue);
        return hash;
    }

    /**
     * Compares this modification with another object for equality.
     *
     * <p>Two ByteArrayExplicitValueModification objects are considered equal if they have the same
     * explicit value (compared by content, not reference).
     *
     * @param obj The object to compare with
     * @return {@code true} if the objects are equal, {@code false} otherwise
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
}
