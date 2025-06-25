/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.bool;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.rub.nds.modifiablevariable.ModifiableVariable;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * A modifiable variable implementation for Boolean values.
 *
 * <p>This class extends {@link ModifiableVariable} to provide runtime modification capabilities for
 * Boolean values.
 *
 * @see ModifiableVariable
 * @see BooleanExplicitValueModification
 * @see BooleanToggleModification
 */
@XmlRootElement
public class ModifiableBoolean extends ModifiableVariable<Boolean> {

    /** The original Boolean value before any modifications */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean originalValue;

    /** Default constructor that creates an empty ModifiableBoolean with no original value. */
    public ModifiableBoolean() {
        super();
    }

    /**
     * Constructor that creates a ModifiableBoolean with the specified original value.
     *
     * @param originalValue The original Boolean value
     */
    public ModifiableBoolean(Boolean originalValue) {
        super();
        this.originalValue = originalValue;
    }

    /**
     * Copy constructor that creates a new ModifiableBoolean with the same original value and
     * modifications as the provided instance.
     *
     * @param other The ModifiableBoolean to copy
     */
    public ModifiableBoolean(ModifiableBoolean other) {
        super(other);
        originalValue = other.originalValue;
    }

    /**
     * Creates a deep copy of this ModifiableBoolean.
     *
     * @return A new ModifiableBoolean instance with the same properties
     */
    @Override
    public ModifiableBoolean createCopy() {
        return new ModifiableBoolean(this);
    }

    /**
     * Gets the original, unmodified Boolean value.
     *
     * @return The original value
     */
    @Override
    public Boolean getOriginalValue() {
        return originalValue;
    }

    /**
     * Sets the original Boolean value.
     *
     * @param originalValue The new original value
     */
    @Override
    public void setOriginalValue(Boolean originalValue) {
        this.originalValue = originalValue;
    }

    /**
     * Checks if the modified value differs from the original value.
     *
     * @return true if the value has been modified, false otherwise
     * @throws IllegalStateException if the original value is null
     */
    @Override
    public boolean isOriginalValueModified() {
        if (getOriginalValue() == null) {
            throw new IllegalStateException("Original value must not be null");
        } else {
            return originalValue.compareTo(getValue()) != 0;
        }
    }

    /**
     * Validates whether the modified value matches the expected value (if set).
     *
     * @return true if no assertion is set or if the current value equals the expected value
     */
    @Override
    public boolean validateAssertions() {
        if (assertEquals != null) {
            return assertEquals.compareTo(getValue()) == 0;
        }
        return true;
    }

    /**
     * Returns a string representation of this ModifiableBoolean.
     *
     * @return A string containing the original value and modifications
     */
    @Override
    public String toString() {
        return "ModifiableBoolean{" + "originalValue=" + originalValue + innerToString() + '}';
    }

    /**
     * Checks if this ModifiableBoolean is equal to another object. Two ModifiableBoolean instances
     * are considered equal if they have the same modified value.
     *
     * @param obj The object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ModifiableBoolean that)) {
            return false;
        }

        Boolean thisValue = getValue();
        Boolean thatValue = that.getValue();

        return thisValue != null ? thisValue.equals(thatValue) : thatValue == null;
    }

    /**
     * Computes a hash code for this ModifiableBoolean. The hash code is based on the modified value
     * rather than the original value.
     *
     * @return The hash code value
     */
    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (getValue() != null ? getValue().hashCode() : 0);
        return result;
    }
}
