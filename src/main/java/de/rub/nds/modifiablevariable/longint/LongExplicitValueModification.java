/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.longint;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.rub.nds.modifiablevariable.VariableModification;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

/**
 * A modification that replaces the original value with an explicitly defined value.
 *
 * <p>This modification ignores the original value of a {@link ModifiableLong} and always returns a
 * predefined long value specified at initialization or via setter.
 *
 * @see ModifiableLong
 * @see de.rub.nds.modifiablevariable.integer.IntegerExplicitValueModification
 */
@XmlRootElement
public class LongExplicitValueModification extends VariableModification<Long> {

    /** The explicit value that will replace the original value */
    @JsonProperty(required = true)
    protected long explicitValue;

    /** Default constructor for serialization. */
    @SuppressWarnings("unused")
    private LongExplicitValueModification() {
        super();
    }

    /**
     * Creates a new modification with the specified explicit value.
     *
     * @param explicitValue The value that will replace the original value
     */
    public LongExplicitValueModification(long explicitValue) {
        super();
        this.explicitValue = explicitValue;
    }

    /**
     * Copy constructor for creating a deep copy of an existing modification.
     *
     * @param other The modification to copy
     */
    public LongExplicitValueModification(LongExplicitValueModification other) {
        super();
        explicitValue = other.explicitValue;
    }

    /**
     * Creates a deep copy of this modification.
     *
     * @return A new instance with the same explicit value
     */
    @Override
    public LongExplicitValueModification createCopy() {
        return new LongExplicitValueModification(this);
    }

    /**
     * Implements the modification by replacing the input with the explicit value.
     *
     * <p>This method simply returns the explicit value, completely ignoring the input parameter
     * (except for null checks). This provides a straightforward way to override values in a
     * protocol message.
     *
     * <p>If the input is null, it preserves null-safety by returning null.
     *
     * @param input The original Long value (ignored except for null check)
     * @return The explicit value, or null if input was null
     */
    @Override
    protected Long modifyImplementationHook(Long input) {
        if (input == null) {
            return null;
        }
        return explicitValue;
    }

    /**
     * Gets the explicit value that will replace the original value.
     *
     * @return The explicit value
     */
    public long getExplicitValue() {
        return explicitValue;
    }

    /**
     * Sets the explicit value that will replace the original value.
     *
     * @param explicitValue The new explicit value
     */
    public void setExplicitValue(long explicitValue) {
        this.explicitValue = explicitValue;
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
        hash = 31 * hash + Objects.hashCode(explicitValue);
        return hash;
    }

    /**
     * Compares this modification with another object for equality.
     *
     * <p>Two LongExplicitValueModification objects are considered equal if they have the same
     * explicit value.
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
        LongExplicitValueModification other = (LongExplicitValueModification) obj;
        return Objects.equals(explicitValue, other.explicitValue);
    }

    /**
     * Returns a string representation of this modification.
     *
     * <p>The string includes the class name and explicit value.
     *
     * @return A string representation of this object
     */
    @Override
    public String toString() {
        return "LongExplicitValueModification{" + "explicitValue=" + explicitValue + '}';
    }
}
