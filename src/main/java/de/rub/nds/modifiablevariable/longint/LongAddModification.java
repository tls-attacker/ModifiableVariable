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
 * A modification that adds a constant value to a ModifiableLong.
 *
 * <p>This modification adds a specified long value (summand) to the input value when applied. It
 * can be used to increment or decrement long values at runtime.
 *
 * @see ModifiableLong
 */
@XmlRootElement
public class LongAddModification extends VariableModification<Long> {

    /** The value to add to the original long */
    private Long summand;

    /** Default constructor for serialization. */
    @SuppressWarnings("unused")
    private LongAddModification() {
        super();
    }

    /**
     * Creates a new addition modification with the specified summand.
     *
     * @param summand The value to add to the original long
     */
    public LongAddModification(long summand) {
        super();
        this.summand = summand;
    }

    /**
     * Copy constructor.
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
     * Modifies the input by adding the summand.
     *
     * <p>Note that this operation may cause long overflow if the sum of the input and the summand
     * exceeds Long.MAX_VALUE or falls below Long.MIN_VALUE. In such cases, the result will wrap
     * around according to Java's long arithmetic.
     *
     * @param input The long value to modify
     * @return The result of adding the summand to the input, or null if the input is null
     */
    @Override
    protected Long modifyImplementationHook(Long input) {
        if (input == null) {
            return null;
        }
        return input + summand;
    }

    /**
     * Gets the summand used for the addition.
     *
     * @return The value that will be added to the original long
     */
    public Long getSummand() {
        return summand;
    }

    /**
     * Sets the summand for the addition.
     *
     * @param summand The value that will be added to the original long
     */
    public void setSummand(Long summand) {
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
        hash = 31 * hash + Objects.hashCode(summand);
        return hash;
    }

    /**
     * Checks if this modification is equal to another object. Two LongAddModification instances are
     * considered equal if they have the same summand.
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
        LongAddModification other = (LongAddModification) obj;
        return Objects.equals(summand, other.summand);
    }

    /**
     * Returns a string representation of this modification.
     *
     * @return A string containing the modification type and summand
     */
    @Override
    public String toString() {
        return "LongAddModification{" + "summand=" + summand + '}';
    }
}
