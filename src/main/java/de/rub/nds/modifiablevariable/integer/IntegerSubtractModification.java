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
 * A modification that subtracts a specified value from an integer.
 *
 * <p>This modification takes the original integer value and subtracts a specified subtrahend using
 * the {@code -} operator. This is useful for testing protocol implementations where decreasing
 * numeric values can help discover boundary condition vulnerabilities or improper range validation.
 *
 * <p>Key testing scenarios where this modification is valuable include:
 *
 * <ul>
 *   <li>Testing protocol handling of decreased values (lengths, counts, etc.)
 *   <li>Exploring edge cases around zero or negative values in protocols expecting positive numbers
 *   <li>Testing integer underflow conditions
 *   <li>Checking boundary validation at integer limits (MIN_VALUE, MAX_VALUE)
 * </ul>
 *
 * <p>Example usage:
 *
 * <pre>{@code
 * // Create a modification that subtracts 5
 * IntegerSubtractModification mod = new IntegerSubtractModification(5);
 *
 * // Apply to a variable
 * ModifiableInteger var = new ModifiableInteger();
 * var.setOriginalValue(42);
 * var.setModification(mod);
 *
 * // Results in 37
 * Integer result = var.getValue();
 * }</pre>
 *
 * <p>This class is serializable through JAXB annotations, allowing it to be used in XML
 * configurations for testing.
 */
@XmlRootElement
public class IntegerSubtractModification extends VariableModification<Integer> {

    /** The value to subtract from the original integer */
    private Integer subtrahend;

    /**
     * Default constructor for JAXB deserialization.
     *
     * <p>When using this constructor, the subtrahend must be set via {@link
     * #setSubtrahend(Integer)} before applying the modification.
     */
    public IntegerSubtractModification() {
        super();
    }

    /**
     * Creates a new modification with the specified subtrahend.
     *
     * @param subtrahend The integer value to subtract from the original value
     */
    public IntegerSubtractModification(Integer subtrahend) {
        super();
        this.subtrahend = subtrahend;
    }

    /**
     * Copy constructor for creating a deep copy of an existing modification.
     *
     * @param other The modification to copy
     */
    public IntegerSubtractModification(IntegerSubtractModification other) {
        super(other);
        subtrahend = other.subtrahend;
    }

    /**
     * Creates a deep copy of this modification.
     *
     * @return A new instance with the same subtrahend
     */
    @Override
    public IntegerSubtractModification createCopy() {
        return new IntegerSubtractModification(this);
    }

    /**
     * Implements the modification by subtracting the subtrahend from the input.
     *
     * <p>This method performs the subtraction operation on the input integer using the {@code -}
     * operator. If the input is null, it returns null to preserve null-safety.
     *
     * <p>Note that the result can be negative if the subtrahend is larger than the input value,
     * which is often useful for testing boundary conditions. Also, integer underflow can occur if
     * the result is less than Integer.MIN_VALUE, which can be useful for testing numeric wraparound
     * handling.
     *
     * @param input The original integer value
     * @return The result of subtracting the subtrahend from the input, or null if input is null
     */
    @Override
    protected Integer modifyImplementationHook(Integer input) {
        if (input == null) {
            return null;
        }
        return input - subtrahend;
    }

    /**
     * Gets the value being subtracted (the subtrahend).
     *
     * @return The subtrahend
     */
    public Integer getSubtrahend() {
        return subtrahend;
    }

    /**
     * Sets the value to subtract (the subtrahend).
     *
     * @param subtrahend The new subtrahend
     */
    public void setSubtrahend(Integer subtrahend) {
        this.subtrahend = subtrahend;
    }

    /**
     * Computes a hash code for this modification.
     *
     * <p>The hash code is based solely on the subtrahend value.
     *
     * @return A hash code value for this object
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(subtrahend);
        return hash;
    }

    /**
     * Compares this modification with another object for equality.
     *
     * <p>Two IntegerSubtractModification objects are considered equal if they have the same
     * subtrahend.
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
        IntegerSubtractModification other = (IntegerSubtractModification) obj;
        return Objects.equals(subtrahend, other.subtrahend);
    }

    /**
     * Returns a string representation of this modification.
     *
     * <p>The string includes the class name and subtrahend value.
     *
     * @return A string representation of this object
     */
    @Override
    public String toString() {
        return "IntegerSubtractModification{" + "subtrahend=" + subtrahend + '}';
    }
}
