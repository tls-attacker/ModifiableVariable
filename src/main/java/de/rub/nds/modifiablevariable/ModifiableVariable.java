/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable;

import de.rub.nds.modifiablevariable.biginteger.*;
import de.rub.nds.modifiablevariable.bool.BooleanExplicitValueModification;
import de.rub.nds.modifiablevariable.bool.BooleanToggleModification;
import de.rub.nds.modifiablevariable.bytearray.*;
import de.rub.nds.modifiablevariable.integer.*;
import de.rub.nds.modifiablevariable.longint.*;
import de.rub.nds.modifiablevariable.path.*;
import de.rub.nds.modifiablevariable.singlebyte.*;
import de.rub.nds.modifiablevariable.string.*;
import jakarta.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * The base abstract class for modifiable variables, including the getValue function. The class
 * needs to be defined transient to allow propOrder definition in subclasses, see: <a
 * href="http://blog.bdoughan.com/2011/06/ignoring-inheritance-with-xmltransient.html">docs</a>
 *
 * @param <E>
 */
@XmlTransient
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class ModifiableVariable<E> implements Serializable {

    @XmlElementWrapper
    @XmlElements({
        @XmlElement(type = BigIntegerXorModification.class, name = "BigIntegerXorModification"),
        @XmlElement(
                type = BigIntegerSubtractModification.class,
                name = "BigIntegerSubtractModification"),
        @XmlElement(
                type = BigIntegerShiftRightModification.class,
                name = "BigIntegerShiftRightModification"),
        @XmlElement(
                type = BigIntegerShiftLeftModification.class,
                name = "BigIntegerShiftLeftModification"),
        @XmlElement(
                type = BigIntegerExplicitValueFromFileModification.class,
                name = "BigIntegerExplicitValueFromFileModification"),
        @XmlElement(
                type = BigIntegerExplicitValueModification.class,
                name = "BigIntegerExplicitValueModification"),
        @XmlElement(type = BigIntegerAddModification.class, name = "BigIntegerAddModification"),
        @XmlElement(
                type = BigIntegerInteractiveModification.class,
                name = "BigIntegerInteractiveModification"),
        @XmlElement(
                type = BigIntegerMultiplyModification.class,
                name = "BigIntegerMultiplyModification"),
        @XmlElement(
                type = BigIntegerAppendValueModification.class,
                name = "BigIntegerAppendValueModification"),
        @XmlElement(
                type = BigIntegerInsertValueModification.class,
                name = "BigIntegerInsertValueModification"),
        @XmlElement(
                type = BigIntegerPrependValueModification.class,
                name = "BigIntegerPrependValueModification"),
        @XmlElement(type = BooleanToggleModification.class, name = "BooleanToggleModification"),
        @XmlElement(
                type = BooleanExplicitValueModification.class,
                name = "BooleanExplicitValueModification"),
        @XmlElement(type = ByteArrayXorModification.class, name = "ByteArrayXorModification"),
        @XmlElement(
                type = ByteArrayShuffleModification.class,
                name = "ByteArrayShuffleModification"),
        @XmlElement(
                type = ByteArrayAppendValueModification.class,
                name = "ByteArrayAppendValueModification"),
        @XmlElement(
                type = ByteArrayInsertValueModification.class,
                name = "ByteArrayInsertValueModification"),
        @XmlElement(
                type = ByteArrayPrependValueModification.class,
                name = "ByteArrayPrependValueModification"),
        @XmlElement(
                type = ByteArrayExplicitValueFromFileModification.class,
                name = "ByteArrayExplicitValueFromFileModification"),
        @XmlElement(
                type = ByteArrayExplicitValueModification.class,
                name = "ByteArrayExplicitValueModification"),
        @XmlElement(
                type = ByteArrayDuplicateModification.class,
                name = "ByteArrayDuplicateModification"),
        @XmlElement(type = ByteArrayDeleteModification.class, name = "ByteArrayDeleteModification"),
        @XmlElement(
                type = IntegerSwapEndianModification.class,
                name = "IntegerSwapEndianModification"),
        @XmlElement(type = IntegerXorModification.class, name = "IntegerXorModification"),
        @XmlElement(type = IntegerSubtractModification.class, name = "IntegerSubtractModification"),
        @XmlElement(type = IntegerMultiplyModification.class, name = "IntegerMultiplyModification"),
        @XmlElement(
                type = IntegerShiftRightModification.class,
                name = "IntegerShiftRightModification"),
        @XmlElement(
                type = IntegerShiftLeftModification.class,
                name = "IntegerShiftLeftModification"),
        @XmlElement(
                type = IntegerExplicitValueFromFileModification.class,
                name = "IntegerExplicitValueFromFileModification"),
        @XmlElement(
                type = IntegerExplicitValueModification.class,
                name = "IntegerExplicitValueModification"),
        @XmlElement(type = IntegerAddModification.class, name = "IntegerAddModification"),
        @XmlElement(
                type = IntegerAppendValueModification.class,
                name = "IntegerAppendValueModification"),
        @XmlElement(
                type = IntegerInsertValueModification.class,
                name = "IntegerInsertValueModification"),
        @XmlElement(
                type = IntegerPrependValueModification.class,
                name = "IntegerPrependValueModification"),
        @XmlElement(type = LongXorModification.class, name = "LongXorModification"),
        @XmlElement(type = LongSwapEndianModification.class, name = "LongSwapEndianModification"),
        @XmlElement(type = LongSubtractModification.class, name = "LongSubtractModification"),
        @XmlElement(
                type = LongExplicitValueFromFileModification.class,
                name = "LongExplicitValueFromFileModification"),
        @XmlElement(
                type = LongExplicitValueModification.class,
                name = "LongExplicitValueModification"),
        @XmlElement(type = LongAppendValueModification.class, name = "LongAppendValueModification"),
        @XmlElement(type = LongInsertValueModification.class, name = "LongInsertValueModification"),
        @XmlElement(
                type = LongPrependValueModification.class,
                name = "LongPrependValueModification"),
        @XmlElement(type = LongMultiplyModification.class, name = "LongMultiplyModification"),
        @XmlElement(type = LongShiftLeftModification.class, name = "LongShiftLeftModification"),
        @XmlElement(type = LongShiftRightModification.class, name = "LongShiftRightModification"),
        @XmlElement(type = LongAddModification.class, name = "LongAddModification"),
        @XmlElement(type = ByteXorModification.class, name = "ByteXorModification"),
        @XmlElement(type = ByteSubtractModification.class, name = "ByteSubtractModification"),
        @XmlElement(type = ByteAddModification.class, name = "ByteAddModification"),
        @XmlElement(
                type = ByteExplicitValueFromFileModification.class,
                name = "ByteExplicitValueFromFileModification"),
        @XmlElement(
                type = ByteExplicitValueModification.class,
                name = "ByteExplicitValueModification"),
        @XmlElement(
                type = StringPrependValueModification.class,
                name = "StringPrependValueModification"),
        @XmlElement(
                type = StringAppendValueModification.class,
                name = "StringAppendValueModification"),
        @XmlElement(
                type = StringExplicitValueFromFileModification.class,
                name = "StringExplicitValueFromFileModification"),
        @XmlElement(
                type = StringExplicitValueModification.class,
                name = "StringExplicitValueModification"),
        @XmlElement(
                type = StringInsertValueModification.class,
                name = "StringInsertValueModification"),
        @XmlElement(type = StringDeleteModification.class, name = "StringDeleteModification"),
        @XmlElement(
                type = PathExplicitValueFromFileModification.class,
                name = "PathExplicitValueFromFileModification"),
        @XmlElement(
                type = PathExplicitValueModification.class,
                name = "PathExplicitValueModification"),
        @XmlElement(type = PathAppendValueModification.class, name = "PathAppendValueModification"),
        @XmlElement(
                type = PathInsertDirectorySeparatorModification.class,
                name = "PathInsertDirectorySeparatorModification"),
        @XmlElement(type = PathDeleteModification.class, name = "PathDeleteModification"),
        @XmlElement(
                type = PathInsertDirectoryTraversalModification.class,
                name = "PathInsertDirectoryTraversalValueModification"),
        @XmlElement(type = PathInsertValueModification.class, name = "PathInsertValueModification"),
        @XmlElement(
                type = PathPrependValueModification.class,
                name = "PathPrependValueModification"),
        @XmlElement(
                type = PathToggleRootModification.class,
                name = "PathToggleRootValueModification"),
    })
    private LinkedList<VariableModification<E>> modifications;

    private Boolean createRandomModification;

    // @XmlElements({
    //     @XmlElement(type = byte[].class),
    //     @XmlElement(type = String.class),
    //     @XmlElement(type = Boolean.class),
    //     @XmlElement(type = Byte.class),
    //     @XmlElement(type = Integer.class),
    //     @XmlElement(type = Long.class),
    //     @XmlElement(type = BigInteger.class)
    // })
    protected E assertEquals;

    protected ModifiableVariable() {
        super();
    }

    protected ModifiableVariable(ModifiableVariable<E> other) {
        super();
        if (other.modifications != null) {
            modifications = new LinkedList<>();
            for (VariableModification<E> item : other.modifications) {
                modifications.add(item != null ? item.createCopy() : null);
            }
        }
        createRandomModification = other.createRandomModification;
        // Warning: Make sure to copy assertEquals in subclass correctly
        assertEquals = other.assertEquals;
    }

    /** Set a single modification, all previously set modifications are removed */
    public void setModification(VariableModification<E> modification) {
        if (modification != null) {
            modifications = new LinkedList<>();
            modifications.add(modification);
        } else {
            modifications = null;
        }
    }

    /** Adds a modification to this modifiable variable */
    public void addModification(VariableModification<E> modification) {
        if (modification != null) {
            if (modifications == null) {
                modifications = new LinkedList<>();
            }
            modifications.add(modification);
        }
    }

    /**
     * Returns the first modification that was set for this modifiable variable, if one exists.
     *
     * <p>Use {@code getModifications()} to get all modifications
     */
    @XmlTransient
    public VariableModification<E> getModification() {
        if (modifications == null || modifications.isEmpty()) {
            return null;
        }
        return modifications.getFirst();
    }

    /** Returns all modifications that are set for this modifiable variable */
    public LinkedList<VariableModification<E>> getModifications() {
        return modifications;
    }

    public E getValue() {
        if (Objects.equals(createRandomModification, Boolean.TRUE)) {
            setRandomModification();
            createRandomModification = false;
        }

        return getModifiedValue();
    }

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
     * Sets the original value to the value changed by the modification. The modification is then
     * set to null, to reduce the modifiable variable to the original value.
     *
     * <p>createRandomModification is ignored
     */
    public void reduceToOriginalValue(boolean evenWithNullOriginalValue) {
        if (evenWithNullOriginalValue || getOriginalValue() != null) {
            if (modifications != null) {
                setOriginalValue(getModifiedValue());
                modifications = null;
            }
        }
    }

    public abstract E getOriginalValue();

    public abstract void setOriginalValue(E originalValue);

    /** Set a single random modification, all previously set modifications are removed */
    public abstract void setRandomModification();

    public abstract ModifiableVariable<E> createCopy();

    public void createRandomModificationAtRuntime() {
        createRandomModification = true;
    }

    public abstract boolean isOriginalValueModified();

    public abstract boolean validateAssertions();

    public boolean containsAssertion() {
        return assertEquals != null;
    }

    public Boolean isCreateRandomModification() {
        return Objects.requireNonNullElse(createRandomModification, false);
    }

    public String innerToString() {
        StringBuilder result = new StringBuilder();
        if (modifications != null) {
            result.append(", modifications=[")
                    .append(
                            modifications.stream()
                                    .map(Object::toString)
                                    .collect(Collectors.joining(", ")))
                    .append("]");
        }

        if (createRandomModification != null) {
            result.append(", createRandomModification=").append(createRandomModification);
        }
        if (assertEquals != null) {
            result.append(", assertEquals=").append(assertEquals);
        }

        return result.toString();
    }
}
