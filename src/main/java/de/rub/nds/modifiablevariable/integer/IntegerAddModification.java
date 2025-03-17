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
 * A modification that adds a constant value to a ModifiableInteger.
 *
 * <p>This modification adds a specified integer value (summand) to the input value when applied. It
 * can be used to increment or decrement integer values at runtime.
 *
 * @see ModifiableInteger
 */
@XmlRootElement
public class IntegerAddModification extends VariableModification<Integer> {

    /** The value to add to the original integer */
    private int summand;

    /** Default constructor for serialization. */
    @SuppressWarnings("unused")
    private IntegerAddModification() {
        super();
    }

    /**
     * Creates a new addition modification with the specified summand.
     *
     * @param summand The value to add to the original integer
     */
    public IntegerAddModification(int summand) {
        super();
        this.summand = summand;
    }

    /**
     * Copy constructor.
     *
     * @param other The modification to copy
     */
    public IntegerAddModification(IntegerAddModification other) {
        super(other);
        summand = other.summand;
    }

    /**
     * Creates a deep copy of this modification.
     *
     * @return A new instance with the same summand
     */
    @Override
    public IntegerAddModification createCopy() {
        return new IntegerAddModification(this);
    }

    /**
     * Modifies the input by adding the summand.
     *
     * <p>Note that this operation may cause integer overflow if the sum of the input and the
     * summand exceeds Integer.MAX_VALUE or falls below Integer.MIN_VALUE. In such cases, the result
     * will wrap around according to Java's integer arithmetic.
     *
     * @param input The integer value to modify
     * @return The result of adding the summand to the input, or null if the input is null
     */
    @Override
    protected Integer modifyImplementationHook(Integer input) {
        if (input == null) {
            return null;
        }
        return input + summand;
    }

    /**
     * Gets the summand used for the addition.
     *
     * @return The value that will be added to the original integer
     */
    public int getSummand() {
        return summand;
    }

    /**
     * Sets the summand for the addition.
     *
     * @param summand The value that will be added to the original integer
     */
    public void setSummand(int summand) {
        this.summand = summand;
    }

    /**
     * Computes a hash code for this modification. The hash code is based on the summand value.
     *
     * @return The hash code value
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + summand;
        return hash;
    }

    /**
     * Checks if this modification is equal to another object. Two IntegerAddModification instances
     * are considered equal if they have the same summand.
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
        IntegerAddModification other = (IntegerAddModification) obj;
        return Objects.equals(summand, other.summand);
    }

    /**
     * Returns a string representation of this modification.
     *
     * @return A string containing the modification type and summand
     */
    @Override
    public String toString() {
        return "IntegerAddModification{" + "summand=" + summand + '}';
    }
}
