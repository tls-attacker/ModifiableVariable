/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.singlebyte;

import de.rub.nds.modifiablevariable.VariableModification;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

/**
 * A modification that replaces a byte variable with an explicit value.
 *
 * <p>This class completely replaces the original byte value with a new, explicitly defined value,
 * regardless of what the original value was. It's particularly useful in security testing for
 * forcing specific byte values in protocol messages.
 *
 * <p>Example use cases:
 *
 * <ul>
 *   <li>Setting specific flag values in protocol headers
 *   <li>Testing boundary values (0x00, 0x7F, 0xFF) in protocol fields
 *   <li>Replacing version indicators with specific values to test compatibility
 *   <li>Setting control bytes to specific values to trigger certain behaviors
 * </ul>
 *
 * <p>Usage example:
 *
 * <pre>
 *   ModifiableByte variable = new ModifiableByte();
 *   variable.setOriginalValue((byte) 0x01);
 *   ByteExplicitValueModification modification = new ByteExplicitValueModification((byte) 0xFF);
 *   variable.setModification(modification);
 *   byte result = variable.getValue(); // result will be (byte) 0xFF
 * </pre>
 */
@XmlRootElement
public class ByteExplicitValueModification extends VariableModification<Byte> {

    /** The value that will replace the original byte */
    protected Byte explicitValue;

    /** Default constructor for serialization. */
    @SuppressWarnings("unused")
    private ByteExplicitValueModification() {
        super();
    }

    /**
     * Constructor with a specified explicit value.
     *
     * @param explicitValue The byte value that will replace the original value
     */
    public ByteExplicitValueModification(Byte explicitValue) {
        super();
        this.explicitValue = explicitValue;
    }

    /**
     * Copy constructor.
     *
     * @param other The ByteExplicitValueModification to copy from
     */
    public ByteExplicitValueModification(ByteExplicitValueModification other) {
        super(other);
        explicitValue = other.explicitValue;
    }

    /**
     * Creates a copy of this modification.
     *
     * @return A new ByteExplicitValueModification instance with the same properties
     */
    @Override
    public ByteExplicitValueModification createCopy() {
        return new ByteExplicitValueModification(this);
    }

    /**
     * Implements the byte explicit value modification.
     *
     * @param input The original byte value to be replaced (ignored except for null check)
     * @return The explicit value that replaces the original value, or null if input is null
     */
    @Override
    protected Byte modifyImplementationHook(Byte input) {
        if (input == null) {
            return null;
        }
        return explicitValue;
    }

    /**
     * Gets the explicit value that will replace the original value.
     *
     * @return The explicit byte value
     */
    public Byte getExplicitValue() {
        return explicitValue;
    }

    /**
     * Sets the explicit value that will replace the original value.
     *
     * @param explicitValue The new byte value to use
     */
    public void setExplicitValue(Byte explicitValue) {
        this.explicitValue = explicitValue;
    }

    /**
     * Computes the hash code for this object.
     *
     * @return A hash code value for this object
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + explicitValue;
        return hash;
    }

    /**
     * Compares this ByteExplicitValueModification with another object for equality.
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
        ByteExplicitValueModification other = (ByteExplicitValueModification) obj;
        return Objects.equals(explicitValue, other.explicitValue);
    }

    /**
     * Returns a string representation of this modification.
     *
     * @return A string containing the modification type and explicit value
     */
    @Override
    public String toString() {
        return "ByteExplicitValueModification{" + "explicitValue=" + explicitValue + '}';
    }
}
