/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.longint;

import de.rub.nds.modifiablevariable.VariableModification;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

/**
 * A modification that adds a specified value to a long integer.
 *
 * <p>This modification takes the original long value and adds a specified summand to it. It's
 * useful for testing protocol implementations with incremented or decremented values, which can
 * help identify issues with boundary checking, numeric validation, or arithmetic processing.
 *
 * <p>Example usage:
 *
 * <pre>{@code
 * // Create a modification that adds 100 to the original value
 * LongAddModification mod = new LongAddModification(100L);
 *
 * // Apply to a variable
 * ModifiableLong var = new ModifiableLong();
 * var.setOriginalValue(5000L);
 * var.setModification(mod);
 *
 * // Results in 5100L (5000 + 100)
 * Long result = var.getValue();
 * }</pre>
 *
 * <p>This class is serializable through JAXB annotations, allowing it to be used in XML
 * configurations for testing.
 *
 * <p>Note that long addition may result in overflow if the result exceeds the range of {@link Long}
 * (±2^63).
 */
@XmlRootElement
public class LongAddModification extends VariableModification<Long> {

    /** The value to add to the original long value */
    private Long summand;

    /**
     * Default constructor for JAXB deserialization.
     *
     * <p>When using this constructor, the summand must be set via {@link #setSummand(Long)} before
     * applying the modification.
     */
    public LongAddModification() {
        super();
    }

    /**
     * Creates a new modification with the specified summand.
     *
     * <p>This constructor sets the value that will be added to the original long when the
     * modification is applied.
     *
     * @param summand The value to add to the original long
     */
    public LongAddModification(Long summand) {
        super();
        this.summand = summand;
    }

    /**
     * Copy constructor for creating a deep copy of an existing modification.
     *
     * @param other The modification to copy
     */
    public LongAddModification(LongAddModification other) {
        super(other);
        summand = other.summand;
    }

    /**
     * Creates a deep copy of this modification.
     *
     * @return A new instance with the same summand
     */
    @Override
    public LongAddModification createCopy() {
        return new LongAddModification(this);
    }

    /**
     * Implements the modification by adding the summand to the input value.
     *
     * <p>This method adds the summand specified during initialization or via {@link
     * #setSummand(Long)} to the input long. If the input is null, it returns null to preserve
     * null-safety.
     *
     * <p>Note that this operation may result in overflow if the result exceeds the range of {@link
     * Long}.
     *
     * @param input The original long value
     * @return The result of adding the summand to the input, or null if input was null
     */
    @Override
    protected Long modifyImplementationHook(Long input) {
        if (input == null) {
            return null;
        }
        return input + summand;
    }

    /**
     * Gets the summand that will be added to the original value.
     *
     * @return The summand
     */
    public Long getSummand() {
        return summand;
    }

    /**
     * Sets the summand that will be added to the original value.
     *
     * @param summand The new summand to use
     */
    public void setSummand(Long summand) {
        this.summand = summand;
    }

    /**
     * Computes a hash code for this modification.
     *
     * <p>The hash code is based solely on the summand.
     *
     * @return A hash code value for this object
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(summand);
        return hash;
    }

    /**
     * Compares this modification with another object for equality.
     *
     * <p>Two LongAddModification objects are considered equal if they have the same summand.
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
        LongAddModification other = (LongAddModification) obj;
        return Objects.equals(summand, other.summand);
    }

    /**
     * Returns a string representation of this modification.
     *
     * <p>The string includes the class name and the summand.
     *
     * @return A string representation of this object
     */
    @Override
    public String toString() {
        return "LongAddModification{" + "summand=" + summand + '}';
    }
}
