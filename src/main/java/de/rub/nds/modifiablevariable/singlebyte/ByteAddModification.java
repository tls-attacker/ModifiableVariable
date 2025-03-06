/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.singlebyte;

import de.rub.nds.modifiablevariable.VariableModification;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

/**
 * A modification that adds a fixed value to a byte variable.
 * <p>
 * This class modifies a byte by adding a specific summand to it. It's useful in security testing
 * for exploring edge cases and boundary conditions in protocol handling.
 * <p>
 * Example use cases:
 * <ul>
 *   <li>Testing overflow behavior by adding values close to byte limits</li>
 *   <li>Modifying version bytes in protocols to test compatibility</li>
 *   <li>Incrementing counter or flag bytes to test sequence handling</li>
 * </ul>
 * <p>
 * Usage example:
 * <pre>
 *   ModifiableByte variable = new ModifiableByte();
 *   variable.setOriginalValue((byte) 10);
 *   ByteAddModification modification = new ByteAddModification((byte) 5);
 *   variable.setModification(modification);
 *   byte result = variable.getValue(); // result will be (byte) 15
 * </pre>
 */
@XmlRootElement
public class ByteAddModification extends VariableModification<Byte> {

    /** The value to be added to the original byte */
    private Byte summand;

    /**
     * Default constructor for serialization.
     */
    public ByteAddModification() {
        super();
    }

    /**
     * Constructor with a specified summand value.
     *
     * @param summand The byte value to add to the original value
     */
    public ByteAddModification(Byte summand) {
        super();
        this.summand = summand;
    }

    /**
     * Copy constructor.
     *
     * @param other The ByteAddModification to copy from
     */
    public ByteAddModification(ByteAddModification other) {
        super(other);
        summand = other.summand;
    }

    /**
     * Creates a copy of this modification.
     *
     * @return A new ByteAddModification instance with the same properties
     */
    @Override
    public ByteAddModification createCopy() {
        return new ByteAddModification(this);
    }

    /**
     * Implements the byte addition modification.
     * 
     * @param input The original byte value to be modified
     * @return The modified byte value (original + summand), or null if input is null
     */
    @Override
    protected Byte modifyImplementationHook(Byte input) {
        if (input == null) {
            return null;
        }
        return (byte) (input + summand);
    }

    /**
     * Gets the summand value used for the addition.
     *
     * @return The summand value
     */
    public Byte getSummand() {
        return summand;
    }

    /**
     * Sets the summand value to be used for the addition.
     *
     * @param summand The byte value to add to the original value
     */
    public void setSummand(Byte summand) {
        this.summand = summand;
    }

    /**
     * Computes the hash code for this object.
     *
     * @return A hash code value for this object
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + summand;
        return hash;
    }

    /**
     * Compares this ByteAddModification with another object for equality.
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
        ByteAddModification other = (ByteAddModification) obj;
        return Objects.equals(summand, other.summand);
    }

    /**
     * Returns a string representation of this modification.
     *
     * @return A string containing the modification type and summand value
     */
    @Override
    public String toString() {
        return "ByteAddModification{" + "summand=" + summand + '}';
    }
}
