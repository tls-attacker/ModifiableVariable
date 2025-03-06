/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.integer;

import de.rub.nds.modifiablevariable.VariableModification;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

/**
 * A modification that multiplies an integer value by a specified factor.
 * 
 * <p>This modification takes the original integer value and multiplies it by a
 * specified factor. It's useful for testing protocol implementations with
 * scaled values, which can help identify issues with field validation, boundary
 * conditions, or numeric processing.
 * 
 * <p>Example usage:
 * <pre>{@code
 * // Create a modification that multiplies by 2
 * IntegerMultiplyModification mod = new IntegerMultiplyModification(2);
 * 
 * // Apply to a variable
 * ModifiableInteger var = new ModifiableInteger();
 * var.setOriginalValue(10);
 * var.setModification(mod);
 * 
 * // Results in 20 (10 * 2)
 * Integer result = var.getValue();
 * }</pre>
 * 
 * <p>This class is serializable through JAXB annotations, allowing it to be
 * used in XML configurations for testing.
 * 
 * <p>Note that integer multiplication may result in overflow if the result exceeds
 * the range of {@link Integer} (±2^31).
 */
@XmlRootElement
public class IntegerMultiplyModification extends VariableModification<Integer> {

    /** The factor by which to multiply the original integer value */
    private Integer factor;

    /**
     * Default constructor for JAXB deserialization.
     * 
     * <p>When using this constructor, the multiplication factor must be set
     * via {@link #setFactor(Integer)} before applying the modification.
     */
    public IntegerMultiplyModification() {
        super();
    }

    /**
     * Creates a new modification with the specified multiplication factor.
     * 
     * <p>This constructor sets the factor by which the original integer
     * will be multiplied when the modification is applied.
     *
     * @param factor The factor to multiply the original value by
     */
    public IntegerMultiplyModification(Integer factor) {
        super();
        this.factor = factor;
    }

    /**
     * Copy constructor for creating a deep copy of an existing modification.
     *
     * @param other The modification to copy
     */
    public IntegerMultiplyModification(IntegerMultiplyModification other) {
        super(other);
        factor = other.factor;
    }

    /**
     * Creates a deep copy of this modification.
     *
     * @return A new instance with the same multiplication factor
     */
    @Override
    public IntegerMultiplyModification createCopy() {
        return new IntegerMultiplyModification(this);
    }

    /**
     * Implements the modification by multiplying the input by the factor.
     * 
     * <p>This method multiplies the input integer by the factor specified during
     * initialization or via {@link #setFactor(Integer)}. If the input is null,
     * it returns null to preserve null-safety.
     * 
     * <p>Note that this operation may result in integer overflow if the result
     * exceeds the range of {@link Integer}.
     *
     * @param input The original integer value
     * @return The result of multiplying the input by the factor, or null if input was null
     */
    @Override
    protected Integer modifyImplementationHook(Integer input) {
        if (input == null) {
            return null;
        }
        return input * factor;
    }

    /**
     * Gets the factor by which the original value will be multiplied.
     *
     * @return The multiplication factor
     */
    public Integer getFactor() {
        return factor;
    }

    /**
     * Sets the factor by which the original value will be multiplied.
     *
     * @param factor The new multiplication factor
     */
    public void setFactor(Integer factor) {
        this.factor = factor;
    }

    /**
     * Computes a hash code for this modification.
     * 
     * <p>The hash code is based solely on the multiplication factor.
     *
     * @return A hash code value for this object
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(factor);
        return hash;
    }

    /**
     * Compares this modification with another object for equality.
     * 
     * <p>Two IntegerMultiplyModification objects are considered equal if
     * they have the same multiplication factor.
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
        IntegerMultiplyModification other = (IntegerMultiplyModification) obj;
        return Objects.equals(factor, other.factor);
    }

    /**
     * Returns a string representation of this modification.
     * 
     * <p>The string includes the class name and the multiplication factor.
     *
     * @return A string representation of this object
     */
    @Override
    public String toString() {
        return "IntegerMultiplyModification{" + "factor=" + factor + '}';
    }
}
