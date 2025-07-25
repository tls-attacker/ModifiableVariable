/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.length;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.rub.nds.modifiablevariable.bytearray.ModifiableByteArray;
import de.rub.nds.modifiablevariable.integer.ModifiableInteger;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

/**
 * A specialized modifiable integer that represents the length of a byte array.
 *
 * <p>This class extends {@link ModifiableInteger} but instead of storing its own original value, it
 * dynamically calculates its original value based on the length of a referenced byte array. This
 * makes it suitable for representing length fields in protocols where the length needs to be
 * synchronized with the actual content.
 *
 * <p>Note that attempting to set the original value directly will throw an
 * UnsupportedOperationException, as the original value is derived from the referenced byte array.
 *
 * <p>Two ModifiableLengthField instances are considered equal if they have the same modified value
 * and reference the same ModifiableByteArray. The hash code is computed based on both the modified
 * value and the referenced byte array.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ModifiableLengthField extends ModifiableInteger {

    /** The byte array whose length this field represents */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(required = true)
    private final ModifiableByteArray ref;

    /** Default constructor for serialization. */
    @SuppressWarnings("unused")
    private ModifiableLengthField() {
        super();
        ref = null;
    }

    /**
     * Constructor that creates a ModifiableLengthField for the specified byte array.
     *
     * @param ref The byte array whose length this field will represent
     */
    public ModifiableLengthField(ModifiableByteArray ref) {
        super();
        this.ref = Objects.requireNonNull(ref);
    }

    /**
     * Copy constructor.
     *
     * <p>Note that this creates a new ModifiableLengthField that references the same
     * ModifiableByteArray as the original, not a copy of the byte array.
     *
     * @param other The ModifiableLengthField to copy
     */
    public ModifiableLengthField(ModifiableLengthField other) {
        super(other);
        ref = other.ref;
    }

    /**
     * Creates a deep copy of this ModifiableLengthField.
     *
     * @return A new ModifiableLengthField instance referencing the same byte array
     */
    @Override
    public ModifiableLengthField createCopy() {
        return new ModifiableLengthField(this);
    }

    /**
     * Gets the original value of this length field, which is dynamically calculated as the length
     * of the referenced byte array. If the referenced byte array is null, this method will return
     * null.
     *
     * @return The current length of the referenced byte array
     */
    @Override
    public Integer getOriginalValue() {
        if (ref.getValue() == null) {
            return null;
        }
        return ref.getValue().length;
    }

    /**
     * This method is not supported for ModifiableLengthField, as the original value is always
     * derived from the referenced byte array.
     *
     * @param originalValue This parameter is ignored
     * @throws UnsupportedOperationException Always thrown when this method is called
     */
    @Override
    public void setOriginalValue(Integer originalValue) {
        throw new UnsupportedOperationException(
                "Cannot set original Value of ModifiableLengthField");
    }

    /**
     * Returns a string representation of this ModifiableLengthField.
     *
     * @return A string containing the referenced byte array and modifications
     */
    @Override
    public String toString() {
        return "ModifiableLengthField{" + "ref=" + ref + super.toString() + "} ";
    }

    /**
     * Checks if this ModifiableLengthField is equal to another object. Two ModifiableLengthField
     * instances are considered equal if they have the same modified value and reference a byte
     * array with the same content.
     *
     * @param obj The object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ModifiableLengthField that)) {
            return false;
        }
        // First check if the values are equal
        boolean valuesEqual =
                getValue() == null ? that.getValue() == null : getValue().equals(that.getValue());
        // Then check if they reference the same byte array
        boolean refsEqual = ref.equals(that.ref);
        return valuesEqual && refsEqual;
    }

    /**
     * Computes a hash code for this ModifiableLengthField. The hash code is based on both the
     * modified value and the referenced byte array.
     *
     * @return The hash code value
     */
    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (getValue() != null ? getValue().hashCode() : 0);
        result = 31 * result + ref.hashCode();
        return result;
    }
}
