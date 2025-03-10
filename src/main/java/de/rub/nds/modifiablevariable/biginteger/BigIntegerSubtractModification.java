/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.biginteger;

import de.rub.nds.modifiablevariable.VariableModification;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.math.BigInteger;
import java.util.Objects;

/**
 * A modification that subtracts a specified value from a BigInteger.
 *
 * <p>This modification takes the original BigInteger value and subtracts a specified subtrahend
 * using the {@link BigInteger#subtract(BigInteger)} method. This is useful for testing protocol
 * implementations where decreasing numeric values can help discover boundary condition
 * vulnerabilities or improper range validation.
 *
 * <p>Key testing scenarios where this modification is valuable include:
 *
 * <ul>
 *   <li>Testing protocol handling of decreased cryptographic parameters (key sizes, moduli, etc.)
 *   <li>Exploring edge cases around zero or negative values in protocols expecting positive numbers
 *   <li>Verifying proper handling of numeric wraparound in constrained systems
 *   <li>Checking boundary validation at integer limits
 * </ul>
 *
 * <p>Example usage:
 *
 * <pre>{@code
 * // Create a modification that subtracts 5
 * BigIntegerSubtractModification mod = new BigIntegerSubtractModification(BigInteger.valueOf(5));
 *
 * // Apply to a variable
 * ModifiableBigInteger var = new ModifiableBigInteger();
 * var.setOriginalValue(BigInteger.valueOf(42));
 * var.setModification(mod);
 *
 * // Results in 37
 * BigInteger result = var.getValue();
 * }</pre>
 *
 * <p>This class is serializable through JAXB annotations, allowing it to be used in XML
 * configurations for testing.
 */
@XmlRootElement
public class BigIntegerSubtractModification extends VariableModification<BigInteger> {

    /** The value to subtract from the original BigInteger */
    private BigInteger subtrahend;

    /** Default constructor for serialization. */
    @SuppressWarnings("unused")
    private BigIntegerSubtractModification() {
        super();
    }

    /**
     * Creates a new modification with the specified subtrahend.
     *
     * @param subtrahend The BigInteger value to subtract from the original value
     * @throws NullPointerException if subtrahend is null
     */
    public BigIntegerSubtractModification(BigInteger subtrahend) {
        super();
        this.subtrahend = Objects.requireNonNull(subtrahend, "Subtrahend must not be null");
    }

    /**
     * Copy constructor for creating a deep copy of an existing modification.
     *
     * @param other The modification to copy
     */
    public BigIntegerSubtractModification(BigIntegerSubtractModification other) {
        super(other);
        subtrahend = other.subtrahend;
    }

    /**
     * Creates a deep copy of this modification.
     *
     * @return A new instance with the same subtrahend
     */
    @Override
    public BigIntegerSubtractModification createCopy() {
        return new BigIntegerSubtractModification(this);
    }

    /**
     * Implements the modification by subtracting the subtrahend from the input.
     *
     * <p>This method performs the subtraction operation on the input BigInteger using {@link
     * BigInteger#subtract(BigInteger)}. If the input is null, it returns null to preserve
     * null-safety.
     *
     * <p>Note that the result can be negative if the subtrahend is larger than the input value,
     * which is often useful for testing boundary conditions.
     *
     * @param input The original BigInteger value
     * @return The result of subtracting the subtrahend from the input, or null if input is null
     */
    @Override
    protected BigInteger modifyImplementationHook(BigInteger input) {
        if (input == null) {
            return null;
        }
        return input.subtract(subtrahend);
    }

    /**
     * Gets the value being subtracted (the subtrahend).
     *
     * @return The subtrahend
     */
    public BigInteger getSubtrahend() {
        return subtrahend;
    }

    /**
     * Sets the value to subtract (the subtrahend).
     *
     * @param subtrahend The new subtrahend
     */
    public void setSubtrahend(BigInteger subtrahend) {
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
     * <p>Two BigIntegerSubtractModification objects are considered equal if they have the same
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
        BigIntegerSubtractModification other = (BigIntegerSubtractModification) obj;
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
        return "BigIntegerSubtractModification{" + "subtrahend=" + subtrahend + '}';
    }
}
