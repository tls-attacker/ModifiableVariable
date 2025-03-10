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
 * A modification that applies an XOR operation to a BigInteger value.
 *
 * <p>This modification performs a bitwise XOR operation between a specified BigInteger value and
 * the original BigInteger. The XOR operation is commonly used for bit manipulation in cryptographic
 * protocols and data masking.
 *
 * <p>XOR operations are particularly useful for testing because they can:
 *
 * <ul>
 *   <li>Flip specific bits in a number (using a mask with 1s in positions to flip)
 *   <li>Leave certain bits unchanged (using a mask with 0s in positions to preserve)
 *   <li>Be used to apply masks or transformations that can help identify implementation issues
 * </ul>
 *
 * <p>Example usage:
 *
 * <pre>{@code
 * // Create a modification that XORs with 0xFF (flips the lowest 8 bits)
 * BigIntegerXorModification mod = new BigIntegerXorModification(BigInteger.valueOf(0xFF));
 *
 * // Apply to a variable
 * ModifiableBigInteger var = new ModifiableBigInteger();
 * var.setOriginalValue(BigInteger.valueOf(0x1234));
 * var.setModification(mod);
 *
 * // Results in 0x12CB (0x1234 XOR 0xFF)
 * BigInteger result = var.getValue();
 * }</pre>
 *
 * <p>This class is serializable through JAXB annotations, allowing it to be used in XML
 * configurations for testing.
 */
@XmlRootElement
public class BigIntegerXorModification extends VariableModification<BigInteger> {

    /** The BigInteger value to XOR with the original value */
    private BigInteger xor;

    /** Default constructor for serialization. */
    @SuppressWarnings("unused")
    private BigIntegerXorModification() {
        super();
    }

    /**
     * Creates a new modification with the specified XOR value.
     *
     * <p>This constructor sets the BigInteger value that will be XORed with the original value when
     * the modification is applied.
     *
     * @param xor The BigInteger to XOR with the original value
     * @throws NullPointerException if xor is null
     */
    public BigIntegerXorModification(BigInteger xor) {
        super();
        this.xor = Objects.requireNonNull(xor, "Xor must not be null");
    }

    /**
     * Copy constructor for creating a deep copy of an existing modification.
     *
     * <p>This constructor creates a new instance with the same XOR value as the provided
     * modification.
     *
     * @param other The modification to copy
     */
    public BigIntegerXorModification(BigIntegerXorModification other) {
        super(other);
        xor = other.xor;
    }

    @Override
    public BigIntegerXorModification createCopy() {
        return new BigIntegerXorModification(this);
    }

    @Override
    protected BigInteger modifyImplementationHook(BigInteger input) {
        if (input == null) {
            return null;
        }
        return input.xor(xor);
    }

    /**
     * Gets the BigInteger value that will be XORed with the original value.
     *
     * @return The BigInteger XOR value
     */
    public BigInteger getXor() {
        return xor;
    }

    /**
     * Sets the BigInteger value that will be XORed with the original value.
     *
     * @param xor The new BigInteger value to use for XOR operations
     * @throws NullPointerException if xor is null
     */
    public void setXor(BigInteger xor) {
        this.xor = Objects.requireNonNull(xor, "Xor must not be null");
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(xor);
        return hash;
    }

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
        BigIntegerXorModification other = (BigIntegerXorModification) obj;
        return Objects.equals(xor, other.xor);
    }

    @Override
    public String toString() {
        return "BigIntegerXorModification{" + "xor=" + xor + '}';
    }
}
