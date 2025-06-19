/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable;

import com.fasterxml.jackson.annotation.*;
import de.rub.nds.modifiablevariable.biginteger.ModifiableBigInteger;
import de.rub.nds.modifiablevariable.bool.ModifiableBoolean;
import de.rub.nds.modifiablevariable.bytearray.ModifiableByteArray;
import de.rub.nds.modifiablevariable.integer.ModifiableInteger;
import de.rub.nds.modifiablevariable.length.ModifiableLengthField;
import de.rub.nds.modifiablevariable.longint.ModifiableLong;
import de.rub.nds.modifiablevariable.singlebyte.ModifiableByte;
import de.rub.nds.modifiablevariable.string.ModifiableString;
import jakarta.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The base abstract class for modifiable variables that provides runtime value modification
 * capabilities. ModifiableVariable serves as the foundation for all modifiable types in the
 * library.
 *
 * <p>This class implements the concept of runtime value modifications, where variables can be
 * modified through a chain of {@link VariableModification} operations before being accessed. Each
 * subclass represents a specific data type that can be modified at runtime.
 *
 * <p>The class is defined as transient to allow proper XML serialization with propOrder definition
 * in subclasses. See: <a
 * href="http://blog.bdoughan.com/2011/06/ignoring-inheritance-with-xmltransient.html">Ignoring
 * Inheritance with XmlTransient</a> for details.
 *
 * @param <E> The type of value this modifiable variable holds (e.g., Integer, String, byte[])
 */
@XmlTransient
@XmlAccessorType(XmlAccessType.FIELD)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "@type")
@JsonSubTypes({
    @JsonSubTypes.Type(name = "BigInteger", value = ModifiableBigInteger.class),
    @JsonSubTypes.Type(name = "Boolean", value = ModifiableBoolean.class),
    @JsonSubTypes.Type(name = "ByteArray", value = ModifiableByteArray.class),
    @JsonSubTypes.Type(name = "Integer", value = ModifiableInteger.class),
    @JsonSubTypes.Type(name = "LengthField", value = ModifiableLengthField.class),
    @JsonSubTypes.Type(name = "Long", value = ModifiableLong.class),
    @JsonSubTypes.Type(name = "Byte", value = ModifiableByte.class),
    @JsonSubTypes.Type(name = "String", value = ModifiableString.class),
})
@JsonAutoDetect(
        fieldVisibility = JsonAutoDetect.Visibility.ANY,
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        isGetterVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE)
public abstract class ModifiableVariable<E> implements Serializable {

    /** The list of modifications that will be applied to the original value when accessed */
    @XmlElementWrapper
    @XmlAnyElement(lax = true)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private LinkedList<VariableModification<E>> modifications;

    /** The expected value for assertion validation */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    protected E assertEquals;

    /** Default constructor that creates an empty modifiable variable. */
    protected ModifiableVariable() {
        super();
    }

    /**
     * Copy constructor that creates a new modifiable variable with the same modifications and
     * assertions.
     *
     * <p>Note: Subclasses must ensure proper copying of the respecitve assertEquals field.
     *
     * @param other The modifiable variable to copy
     */
    protected ModifiableVariable(ModifiableVariable<E> other) {
        super();
        if (other.modifications != null) {
            modifications = new LinkedList<>();
            for (VariableModification<E> item : other.modifications) {
                modifications.add(item != null ? item.createCopy() : null);
            }
        }
        // Warning: Make sure to copy assertEquals in subclass correctly
        assertEquals = other.assertEquals;
    }

    /**
     * Sets multiple modifications, replacing any previously set modifications.
     *
     * @param modifications The list of modifications to apply in sequence
     */
    public void setModifications(List<VariableModification<E>> modifications) {
        this.modifications = new LinkedList<>(modifications);
    }

    /**
     * Sets multiple modifications, replacing any previously set modifications.
     *
     * @param modifications The variable arguments list of modifications to apply in sequence
     */
    @SafeVarargs
    public final void setModifications(VariableModification<E>... modifications) {
        this.modifications = new LinkedList<>(List.of(modifications));
    }

    /**
     * Removes all modifications from this variable. After calling this method, getValue() will
     * return the original value.
     */
    public void clearModifications() {
        modifications = null;
    }

    /**
     * Adds a modification to the end of this variable's modification chain. The modification will
     * be applied after all previously added modifications.
     *
     * @param modification The modification to add
     */
    public void addModification(VariableModification<E> modification) {
        if (modification != null) {
            if (modifications == null) {
                modifications = new LinkedList<>();
            }
            modifications.add(modification);
        }
    }

    /**
     * Returns all modifications that are set for this modifiable variable.
     *
     * @return The list of modifications or null if no modifications are set
     */
    public LinkedList<VariableModification<E>> getModifications() {
        return modifications;
    }

    /**
     * Returns the modified value of this variable. The original value will be modified by applying
     * all registered modifications in sequence.
     *
     * @return The modified value after applying all modifications
     */
    public E getValue() {
        return getModifiedValue();
    }

    /**
     * Internal implementation method to compute the modified value. Applies all registered
     * modifications to the original value in sequence.
     *
     * @return The value after applying all modifications
     */
    private E getModifiedValue() {
        E resultValue = getOriginalValue();
        if (modifications != null) {
            for (VariableModification<E> modification : modifications) {
                resultValue = modification.modify(resultValue);
            }
        }
        return resultValue;
    }

    /**
     * Returns the original, unmodified value of this variable.
     *
     * @return The original value
     */
    public abstract E getOriginalValue();

    /**
     * Sets the original value of this variable.
     *
     * @param originalValue The new original value
     */
    public abstract void setOriginalValue(E originalValue);

    /**
     * Creates a deep copy of this modifiable variable.
     *
     * @return A new instance with the same original value, modifications, and assertion values
     */
    public abstract ModifiableVariable<E> createCopy();

    /**
     * Checks if the modified value differs from the original value.
     *
     * @return true if the modified value is different from the original, false otherwise
     * @throws IllegalStateException If the original value is not set
     */
    public abstract boolean isOriginalValueModified();

    /**
     * Validates whether the modified value matches the expected value (if set).
     *
     * @return true if no assertions are set or if all assertions pass, false otherwise
     */
    public abstract boolean validateAssertions();

    /**
     * Checks if this variable has an assertion value set.
     *
     * @return true if an assertEquals value is set, false otherwise
     */
    public boolean containsAssertion() {
        return assertEquals != null;
    }

    /**
     * Helper method for toString() implementations in subclasses. Provides a string representation
     * of modifications and assertions.
     *
     * @return A string containing the modifications and assertions
     */
    protected String innerToString() {
        StringBuilder result = new StringBuilder();
        if (modifications != null) {
            result.append(", modifications=[")
                    .append(
                            modifications.stream()
                                    .map(Object::toString)
                                    .collect(Collectors.joining(", ")))
                    .append("]");
        }

        if (assertEquals != null) {
            result.append(", assertEquals=").append(assertEquals);
        }

        return result.toString();
    }
}
