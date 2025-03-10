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
 * A modification that applies a bitwise XOR operation to a ModifiableBigInteger.
 *
 * <p>This modification performs a bitwise XOR (exclusive OR) operation between the original
 * BigInteger value and a specified XOR mask when applied. It can be used to selectively flip 
 * specific bits in BigInteger values at runtime.
 *
 * <p>XOR operations are particularly valuable for security testing because:
 *
 * <ul>
 *   <li>They allow selective bit flipping, which can target specific bits in a value
 *   <li>They're reversible (applying the same XOR mask twice restores the original value)
 *   <li>They create predictable and controlled changes to binary data
 *   <li>They can be used to simulate data corruption or protocol tampering
 * </ul>
 *
 * <p>BigInteger XOR operations are especially useful for testing cryptographic implementations
 * where large integers are used, such as:
 * 
 * <ul>
 *   <li>RSA key parameters and signatures
 *   <li>Diffie-Hellman and other public key protocol values
 *   <li>Large hash values or arbitrary-precision integers
 *   <li>Custom cryptographic algorithms using large numbers
 * </ul>
 *
 * <p>Unlike integer or long XOR operations, BigInteger XOR can be applied to integers of any
 * size, making it suitable for testing with extremely large numeric values.
 *
 * @see ModifiableBigInteger
 * @see IntegerXorModification
 * @see LongXorModification
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

    /**
     * Creates a deep copy of this modification.
     *
     * @return A new instance with the same XOR mask
     */
    @Override
    public BigIntegerXorModification createCopy() {
        return new BigIntegerXorModification(this);
    }

    /**
     * Modifies the input by applying a bitwise XOR operation with the configured XOR mask.
     *
     * <p>This method uses BigInteger's native xor method which performs the operation on all bits
     * regardless of the size of the values. The operation is performed bit by bit according to
     * the standard XOR truth table (1⊕0=1, 0⊕1=1, 0⊕0=0, 1⊕1=0).
     *
     * <p>The resulting value will have a bit length equal to the maximum bit length of the input
     * and the XOR mask, with leading zeros as needed.
     *
     * @param input The BigInteger value to modify
     * @return The result of XORing the input with the configured mask, or null if the input is null
     */
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

    /**
     * Computes a hash code for this modification. The hash code is based on the XOR mask.
     *
     * @return The hash code value
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(xor);
        return hash;
    }

    /**
     * Checks if this modification is equal to another object. Two BigIntegerXorModification
     * instances are considered equal if they have the same XOR mask.
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
        BigIntegerXorModification other = (BigIntegerXorModification) obj;
        return Objects.equals(xor, other.xor);
    }

    /**
     * Returns a string representation of this modification.
     *
     * @return A string containing the modification type and XOR mask
     */
    @Override
    public String toString() {
        return "BigIntegerXorModification{" + "xor=" + xor + '}';
    }
}
