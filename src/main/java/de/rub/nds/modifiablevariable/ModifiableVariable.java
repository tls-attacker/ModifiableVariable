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
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElements;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.Objects;

/**
 * The base abstract class for modifiable variables, including the getValue function.The class needs
 * to be defined transient to allow propOrder definition in subclasses, see: <a
 * href="http://blog.bdoughan.com/2011/06/ignoring-inheritance-with-xmltransient.html">...</a>
 *
 * @param <E>
 */
@XmlRootElement
@XmlTransient
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class ModifiableVariable<E> implements Serializable {

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
        @XmlElement(type = PathAppendValueModification.class, name = "PathAppendValueModification"),
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
    private VariableModification<E> modification;

    private Boolean createRandomModification;

    protected E assertEquals;

    protected ModifiableVariable() {
        super();
    }

    protected ModifiableVariable(ModifiableVariable<E> other) {
        super();
        createRandomModification = other.createRandomModification;
        modification = other.modification != null ? other.modification.createCopy() : null;
        // Warning: Make sure to copy assertEquals in subclass correctly
        assertEquals = other.assertEquals;
    }

    public void setModification(VariableModification<E> modification) {
        this.modification = modification;
    }

    @XmlTransient
    public VariableModification<E> getModification() {
        return modification;
    }

    public E getValue() {
        if (Objects.equals(createRandomModification, Boolean.TRUE)) {
            createRandomModification();
            createRandomModification = false;
        }

        if (modification != null) {
            return modification.modify(getOriginalValue());
        }
        return getOriginalValue();
    }

    public void reduceToOriginalValue(boolean evenWithNullOriginalValue) {
        if (evenWithNullOriginalValue || getOriginalValue() != null) {
            setOriginalValue(getValue());
        }
    }

    public abstract E getOriginalValue();

    public abstract void setOriginalValue(E originalValue);

    protected abstract void createRandomModification();

    protected abstract ModifiableVariable<E> createCopy();

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
}
