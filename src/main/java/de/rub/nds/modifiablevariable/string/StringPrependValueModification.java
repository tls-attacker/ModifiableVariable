/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.string;

import static de.rub.nds.modifiablevariable.util.StringUtil.backslashEscapeString;

import de.rub.nds.modifiablevariable.VariableModification;
import de.rub.nds.modifiablevariable.util.IllegalStringAdapter;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Objects;

/**
 * Modification that prepends a string to the original value.
 *
 * <p>This class modifies a string by adding specified content at the beginning of the original
 * string. It's useful in security testing for manipulating string-based protocol fields,
 * particularly when testing parser robustness and input validation.
 *
 * <p>Example use cases:
 *
 * <ul>
 *   <li>Testing for SQL injection by prepending SQL syntax to field values
 *   <li>Testing for XSS vulnerabilities by prepending script tags to form inputs
 *   <li>Adding protocol headers or magic bytes to test parser behavior
 *   <li>Testing boundary conditions by prepending large or special character strings
 *   <li>Manipulating protocol handshakes by modifying string fields
 * </ul>
 *
 * <p>Usage example:
 *
 * <pre>
 *   ModifiableString variable = new ModifiableString();
 *   variable.setOriginalValue("example");
 *   StringPrependValueModification modification = new StringPrependValueModification("test_");
 *   variable.setModification(modification);
 *   String result = variable.getValue(); // result will be "test_example"
 * </pre>
 */
@XmlRootElement
public class StringPrependValueModification extends VariableModification<String> {

    /** The string value to be prepended to the original string */
    @XmlJavaTypeAdapter(IllegalStringAdapter.class)
    private String prependValue;

    /** Default constructor for serialization. */
    @SuppressWarnings("unused")
    private StringPrependValueModification() {
        super();
    }

    /**
     * Constructor with a specified prepend value.
     *
     * @param prependValue .*
     * @throws NullPointerException if prependValue is null
     */
    public StringPrependValueModification(String prependValue) {
        super();
        this.prependValue = Objects.requireNonNull(prependValue, "PrependValue must not be null");
    }

    /**
     * Copy constructor.
     *
     * @param other The StringPrependValueModification to copy from
     */
    public StringPrependValueModification(StringPrependValueModification other) {
        super(other);
        prependValue = other.prependValue;
    }

    /**
     * Creates a copy of this modification.
     *
     * @return A new StringPrependValueModification instance with the same properties
     */
    @Override
    public StringPrependValueModification createCopy() {
        return new StringPrependValueModification(this);
    }

    /**
     * Implements the string prepend modification.
     *
     * @param input The original string value to be modified
     * @return The modified string value (prependValue + original), or null if input is null
     */
    @Override
    protected String modifyImplementationHook(String input) {
        if (input == null) {
            return null;
        }
        return prependValue + input;
    }

    /**
     * Gets the string value used for prepending.
     *
     * @return The prepend value
     */
    public String getPrependValue() {
        return prependValue;
    }

    /**
     * Sets the string value to be prepended to the original value.
     *
     * @param prependValue .*
     * @throws NullPointerException if prependValue is null
     */
    public void setPrependValue(String prependValue) {
        this.prependValue = Objects.requireNonNull(prependValue, "PrependValue must not be null");
    }

    /**
     * Computes the hash code for this object.
     *
     * @return A hash code value for this object
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(prependValue);
        return hash;
    }

    /**
     * Compares this StringPrependValueModification with another object for equality.
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
        StringPrependValueModification other = (StringPrependValueModification) obj;
        return Objects.equals(prependValue, other.prependValue);
    }

    /**
     * Returns a string representation of this modification. The prepend value is escaped to make
     * non-printable characters visible.
     *
     * @return A string containing the modification type and escaped prepend value
     */
    @Override
    public String toString() {
        return "StringPrependValueModification{"
                + "prependValue='"
                + backslashEscapeString(prependValue)
                + '\''
                + '}';
    }
}
