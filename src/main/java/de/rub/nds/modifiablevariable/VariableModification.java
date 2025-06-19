/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable;

import static de.rub.nds.modifiablevariable.util.StringUtil.backslashEscapeString;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import de.rub.nds.modifiablevariable.biginteger.*;
import de.rub.nds.modifiablevariable.bool.BooleanExplicitValueModification;
import de.rub.nds.modifiablevariable.bool.BooleanToggleModification;
import de.rub.nds.modifiablevariable.bytearray.*;
import de.rub.nds.modifiablevariable.integer.*;
import de.rub.nds.modifiablevariable.longint.*;
import de.rub.nds.modifiablevariable.singlebyte.ByteAddModification;
import de.rub.nds.modifiablevariable.singlebyte.ByteExplicitValueModification;
import de.rub.nds.modifiablevariable.singlebyte.ByteSubtractModification;
import de.rub.nds.modifiablevariable.singlebyte.ByteXorModification;
import de.rub.nds.modifiablevariable.string.*;
import de.rub.nds.modifiablevariable.util.DataConverter;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Abstract base class for all variable modifications.
 *
 * <p>A VariableModification represents a transformation that can be applied to a value of type E at
 * runtime. Each concrete implementation defines a specific way to modify values, such as addition,
 * XOR operations, explicit value replacements, or other transformations.
 *
 * <p>The modification framework is designed for security testing and protocol analysis, allowing
 * runtime manipulation of protocol fields to:
 *
 * <ul>
 *   <li>Test for boundary conditions and edge cases
 *   <li>Modify protocol messages to explore security vulnerabilities
 *   <li>Simulate malformed or unexpected protocol data
 *   <li>Create test cases with precise control over field values
 * </ul>
 *
 * <p>This class follows the Template Method pattern, with the main {@link #modify} method providing
 * common functionality (such as debugging) while delegating the actual modification implementation
 * to subclasses through the {@link #modifyImplementationHook} method.
 *
 * <p>All modifications are designed to be immutable and preserve the original input values,
 * creating new objects with the modified data rather than modifying the inputs in-place.
 *
 * @param <E> The type of value this modification operates on
 */
@XmlTransient
@XmlAccessorType(XmlAccessType.FIELD)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "@type")
@JsonSubTypes({
    @JsonSubTypes.Type(name = "BigIntegerAdd", value = BigIntegerAddModification.class),
    @JsonSubTypes.Type(
            name = "BigIntegerExplicitValue",
            value = BigIntegerExplicitValueModification.class),
    @JsonSubTypes.Type(name = "BigIntegerMultiply", value = BigIntegerMultiplyModification.class),
    @JsonSubTypes.Type(name = "BigIntegerShiftLeft", value = BigIntegerShiftLeftModification.class),
    @JsonSubTypes.Type(
            name = "BigIntegerShiftRight",
            value = BigIntegerShiftRightModification.class),
    @JsonSubTypes.Type(name = "BigIntegerSubtract", value = BigIntegerSubtractModification.class),
    @JsonSubTypes.Type(name = "BigIntegerXor", value = BigIntegerXorModification.class),
    @JsonSubTypes.Type(
            name = "BooleanExplicitValue",
            value = BooleanExplicitValueModification.class),
    @JsonSubTypes.Type(name = "BooleanToggle", value = BooleanToggleModification.class),
    @JsonSubTypes.Type(
            name = "ByteArrayAppendValue",
            value = ByteArrayAppendValueModification.class),
    @JsonSubTypes.Type(name = "ByteArrayDelete", value = ByteArrayDeleteModification.class),
    @JsonSubTypes.Type(name = "ByteArrayDuplicate", value = ByteArrayDuplicateModification.class),
    @JsonSubTypes.Type(
            name = "ByteArrayExplicitValue",
            value = ByteArrayExplicitValueModification.class),
    @JsonSubTypes.Type(
            name = "ByteArrayInsertValue",
            value = ByteArrayInsertValueModification.class),
    @JsonSubTypes.Type(
            name = "ByteArrayPrependValue",
            value = ByteArrayPrependValueModification.class),
    @JsonSubTypes.Type(name = "ByteArrayShuffle", value = ByteArrayShuffleModification.class),
    @JsonSubTypes.Type(name = "ByteArrayXor", value = ByteArrayXorModification.class),
    @JsonSubTypes.Type(name = "IntegerAdd", value = IntegerAddModification.class),
    @JsonSubTypes.Type(
            name = "IntegerExplicitValue",
            value = IntegerExplicitValueModification.class),
    @JsonSubTypes.Type(name = "IntegerMultiply", value = IntegerMultiplyModification.class),
    @JsonSubTypes.Type(name = "IntegerShiftLeft", value = IntegerShiftLeftModification.class),
    @JsonSubTypes.Type(name = "IntegerShiftRight", value = IntegerShiftRightModification.class),
    @JsonSubTypes.Type(name = "IntegerSubtract", value = IntegerSubtractModification.class),
    @JsonSubTypes.Type(name = "IntegerSwapEndian", value = IntegerSwapEndianModification.class),
    @JsonSubTypes.Type(name = "IntegerXor", value = IntegerXorModification.class),
    @JsonSubTypes.Type(name = "LongAdd", value = LongAddModification.class),
    @JsonSubTypes.Type(name = "LongExplicitValue", value = LongExplicitValueModification.class),
    @JsonSubTypes.Type(name = "LongMultiply", value = LongMultiplyModification.class),
    @JsonSubTypes.Type(name = "LongShiftLeft", value = LongShiftLeftModification.class),
    @JsonSubTypes.Type(name = "LongShiftRight", value = LongShiftRightModification.class),
    @JsonSubTypes.Type(name = "LongSubtract", value = LongSubtractModification.class),
    @JsonSubTypes.Type(name = "LongSwapEndian", value = LongSwapEndianModification.class),
    @JsonSubTypes.Type(name = "LongXor", value = LongXorModification.class),
    @JsonSubTypes.Type(name = "ByteAdd", value = ByteAddModification.class),
    @JsonSubTypes.Type(name = "ByteExplicitValue", value = ByteExplicitValueModification.class),
    @JsonSubTypes.Type(name = "ByteSubtract", value = ByteSubtractModification.class),
    @JsonSubTypes.Type(name = "ByteXor", value = ByteXorModification.class),
    @JsonSubTypes.Type(name = "StringAppendValue", value = StringAppendValueModification.class),
    @JsonSubTypes.Type(name = "StringDelete", value = StringDeleteModification.class),
    @JsonSubTypes.Type(name = "StringExplicitValue", value = StringExplicitValueModification.class),
    @JsonSubTypes.Type(name = "StringInsertValue", value = StringInsertValueModification.class),
    @JsonSubTypes.Type(name = "StringPrependValue", value = StringPrependValueModification.class),
})
@JsonAutoDetect(
        fieldVisibility = JsonAutoDetect.Visibility.ANY,
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        isGetterVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE)
public abstract class VariableModification<E> implements Serializable {

    /** Logger for debugging modification applications */
    protected static final Logger LOGGER = LogManager.getLogger(VariableModification.class);

    /** Default constructor. */
    protected VariableModification() {
        super();
    }

    /**
     * Creates a deep copy of this modification.
     *
     * @return A new instance with the same modification parameters
     */
    public abstract VariableModification<E> createCopy();

    /**
     * Applies this modification to the provided input value.
     *
     * <p>This method is the main entry point for applying modifications to values. It follows the
     * Template Method pattern by:
     *
     * <ol>
     *   <li>Delegating the actual modification to the implementation-specific {@link
     *       #modifyImplementationHook} method
     *   <li>Handling debug logging of the modification operation through the {@link #debug} method
     *   <li>Returning the modified value to the caller
     * </ol>
     *
     * <p>This design centralizes common functionality while allowing subclasses to focus solely on
     * implementing their specific modification logic.
     *
     * @param input The value to modify
     * @return The modified value, or null if the input was null (null-safety is maintained)
     */
    public E modify(E input) {
        E modifiedValue = modifyImplementationHook(input);
        debug(modifiedValue);
        return modifiedValue;
    }

    /**
     * Abstract hook method that each concrete modification must implement to define how the input
     * value is transformed.
     *
     * <p>This hook method is part of the Template Method pattern and is called by {@link #modify}
     * to perform the actual modification logic. Subclasses must implement this method to:
     *
     * <ul>
     *   <li>Apply their specific transformation to the input value
     *   <li>Handle null inputs appropriately (typically by returning null)
     *   <li>Preserve immutability by not modifying the input value in-place
     *   <li>Return a new object containing the modified data
     * </ul>
     *
     * <p>Each implementation is expected to maintain null-safety by returning null if the input is
     * null, rather than throwing exceptions. This ensures consistent behavior across all
     * modification types.
     *
     * @param input The value to modify
     * @return The modified value, or null if the input was null
     */
    protected abstract E modifyImplementationHook(E input);

    /**
     * Logs debug information about the modification being applied.
     *
     * <p>This method logs the modification class name, calling function, and the new value after
     * modification.
     *
     * @param value The modified value to be logged
     */
    protected void debug(E value) {
        if (LOGGER.isDebugEnabled()) {
            StackTraceElement[] stack = Thread.currentThread().getStackTrace();
            int index = 0;
            for (int i = 0; i < stack.length; i++) {
                if (stack[i].toString().contains("ModifiableVariable.getValue")) {
                    index = i + 1;
                }
            }
            if (value == null) {
                LOGGER.debug(
                        "Using {} in function:\n  {}\n  New value is unset",
                        getClass().getSimpleName(),
                        stack[index]);
            } else {
                String valueString =
                        switch (value) {
                            case byte[] bytes -> DataConverter.bytesToHexString(bytes);
                            case String s -> backslashEscapeString(s);
                            default -> value.toString();
                        };
                LOGGER.debug(
                        "Using {} in function:\n  {}\n  New value: {}",
                        getClass().getSimpleName(),
                        stack[index],
                        valueString);
            }
        }
    }
}
