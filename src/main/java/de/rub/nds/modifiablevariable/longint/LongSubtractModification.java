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
 * A modification that subtracts a constant value from a ModifiableLong.
 *
 * <p>This modification subtracts a specified long value (subtrahend) from the input value when
 * applied. It can be used to decrement long values at runtime.
 *
 * @see ModifiableLong
 * @see LongAddModification
 */
@XmlRootElement
public class LongSubtractModification extends VariableModification<Long> {

    /** The value to subtract from the original long */
    @JsonProperty(required = true)
    private long subtrahend;

    /** Default constructor for serialization. */
    @SuppressWarnings("unused")
    private LongSubtractModification() {
        super();
    }

    /**
     * Creates a new modification with the specified subtrahend.
     *
     * @param subtrahend The long value to subtract from the original value
     */
    public LongSubtractModification(long subtrahend) {
        super();
        this.subtrahend = subtrahend;
    }

    /**
     * Copy constructor for creating a deep copy of an existing modification.
     *
     * @param other The modification to copy
     */
    public LongSubtractModification(LongSubtractModification other) {
        super();
        subtrahend = other.subtrahend;
    }

    /**
     * Creates a deep copy of this modification.
     *
     * @return A new instance with the same subtrahend
     */
    @Override
    public LongSubtractModification createCopy() {
        return new LongSubtractModification(this);
    }

    /**
     * Implements the modification by subtracting the subtrahend from the input.
     *
     * <p>This method performs the subtraction operation on the input long using the {@code -}
     * operator. If the input is null, it returns null to preserve null-safety.
     *
     * <p>Note that this operation may result in underflow if the result is less than {@link
     * Long#MIN_VALUE}, which can be useful for testing boundary conditions and underflow handling.
     *
     * @param input The original long value
     * @return The result of subtracting the subtrahend from the input, or null if input is null
     */
    @Override
    protected Long modifyImplementationHook(Long input) {
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
    public long getSubtrahend() {
        return subtrahend;
    }

    /**
     * Sets the value to subtract (the subtrahend).
     *
     * @param subtrahend The new subtrahend
     */
    public void setSubtrahend(long subtrahend) {
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
     * <p>Two LongSubtractModification objects are considered equal if they have the same
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
        LongSubtractModification other = (LongSubtractModification) obj;
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
        return "LongSubtractModification{" + "subtrahend=" + subtrahend + '}';
    }
}
