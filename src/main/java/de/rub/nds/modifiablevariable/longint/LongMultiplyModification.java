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
 * A modification that multiplies a ModifiableLong by a constant factor.
 *
 * <p>This modification takes the original long value and multiplies it by a specified factor when
 * applied. It can be used to scale long values at runtime.
 * 
 * @see ModifiableLong
 */
@XmlRootElement
public class LongMultiplyModification extends VariableModification<Long> {

    /** The factor to multiply by */
    private long factor;

    /** Default constructor for serialization. */
    @SuppressWarnings("unused")
    private LongMultiplyModification() {
        super();
    }

    /**
     * Creates a new modification with the specified multiplication factor.
     *
     * @param factor The long value to multiply the original value by
     */
    public LongMultiplyModification(long factor) {
        super();
        this.factor = factor;
    }

    /**
     * Copy constructor for creating a deep copy of an existing modification.
     *
     * @param other The modification to copy
     */
    public LongMultiplyModification(LongMultiplyModification other) {
        super(other);
        factor = other.factor;
    }

    /**
     * Creates a deep copy of this modification.
     *
     * @return A new instance with the same multiplication factor
     */
    @Override
    public LongMultiplyModification createCopy() {
        return new LongMultiplyModification(this);
    }

    /**
     * Implements the modification by multiplying the input by the factor.
     *
     * <p>This method performs the multiplication operation on the input long using the {@code *}
     * operator. If the input is null, it returns null to preserve null-safety.
     *
     * <p>Note that this operation may result in overflow if the result exceeds the range of {@link
     * Long}, which can be useful for testing boundary conditions and overflow handling.
     *
     * @param input The original long value
     * @return The result of multiplying the input by the factor, or null if input is null
     */
    @Override
    protected Long modifyImplementationHook(Long input) {
        if (input == null) {
            return null;
        }
        return input * factor;
    }

    /**
     * Gets the factor used for multiplication.
     *
     * @return The multiplication factor
     */
    public long getFactor() {
        return factor;
    }

    /**
     * Sets the factor to use for multiplication.
     *
     * @param factor The new multiplication factor
     */
    public void setFactor(long factor) {
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
     * <p>Two LongMultiplyModification objects are considered equal if they have the same
     * multiplication factor.
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
        LongMultiplyModification other = (LongMultiplyModification) obj;
        return Objects.equals(factor, other.factor);
    }

    /**
     * Returns a string representation of this modification.
     *
     * <p>The string includes the class name and factor value.
     *
     * @return A string representation of this object
     */
    @Override
    public String toString() {
        return "LongMultiplyModification{" + "factor=" + factor + '}';
    }
}
