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

/**
 * A modification that reverses the byte order (endianness) of an integer.
 *
 * <p>This modification changes the endianness of the original integer value by reversing the order
 * of its bytes. It converts between little-endian and big-endian representations, which is useful
 * for testing protocol implementations that might misinterpret byte ordering.
 *
 * <p>For example, the decimal value 16909060 is represented as:
 *
 * <ul>
 *   <li>0x01020304 in big-endian format
 *   <li>0x04030201 in little-endian format
 * </ul>
 *
 * <p>This modification converts between these two representations, allowing testing of code that
 * might be sensitive to endianness issues.
 *
 * <p>Example usage:
 *
 * <pre>{@code
 * // Create a modification that swaps byte order
 * IntegerSwapEndianModification mod = new IntegerSwapEndianModification();
 *
 * // Apply to a variable
 * ModifiableInteger var = new ModifiableInteger();
 * var.setOriginalValue(0x01020304); // Decimal 16909060
 * var.setModification(mod);
 *
 * // Results in 0x04030201 (Decimal 67305985)
 * Integer result = var.getValue();
 * }</pre>
 *
 * <p>This class is serializable through JAXB annotations, allowing it to be used in XML
 * configurations for testing.
 *
 * <p>Note that this modification is stateless as it has no configuration parameters. All instances
 * of this class behave identically and are considered equal.
 */
@XmlRootElement
public class IntegerSwapEndianModification extends VariableModification<Integer> {

    /**
     * Default constructor.
     *
     * <p>This modification is stateless so no parameters are needed.
     */
    public IntegerSwapEndianModification() {
        super();
    }

    /**
     * Copy constructor for creating a deep copy of an existing modification.
     *
     * <p>Since this modification is stateless, all instances are equivalent.
     *
     * @param other The modification to copy
     */
    public IntegerSwapEndianModification(IntegerSwapEndianModification other) {
        super(other);
    }

    /**
     * Creates a deep copy of this modification.
     *
     * <p>Since this modification is stateless, all instances are equivalent.
     *
     * @return A new instance of this modification
     */
    @Override
    public IntegerSwapEndianModification createCopy() {
        return new IntegerSwapEndianModification(this);
    }

    /**
     * Implements the modification by swapping the byte order of the input integer.
     *
     * <p>This method uses {@link Integer#reverseBytes(int)} to change the endianness of the input
     * value. If the input is null, it returns null to preserve null-safety.
     *
     * @param input The original integer value
     * @return The integer with reversed byte order, or null if input was null
     */
    @Override
    protected Integer modifyImplementationHook(Integer input) {
        if (input == null) {
            return null;
        }
        return Integer.reverseBytes(input);
    }

    /**
     * Computes a hash code for this modification.
     *
     * <p>Since this modification is stateless, a constant hash code is returned.
     *
     * @return A constant hash code value for all instances of this class
     */
    @Override
    public int hashCode() {
        return 7;
    }

    /**
     * Compares this modification with another object for equality.
     *
     * <p>Since this modification is stateless, all instances of this class are considered equal to
     * each other.
     *
     * @param obj The object to compare with
     * @return {@code true} if the object is also an IntegerSwapEndianModification, {@code false}
     *     otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        return getClass() == obj.getClass();
    }

    /**
     * Returns a string representation of this modification.
     *
     * <p>Since this modification is stateless, the string only includes the class name.
     *
     * @return A string representation of this object
     */
    @Override
    public String toString() {
        return "IntegerSwapEndianModification{" + '}';
    }
}
