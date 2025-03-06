/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable;

import static de.rub.nds.modifiablevariable.util.StringUtil.backslashEscapeString;

import de.rub.nds.modifiablevariable.util.ArrayConverter;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Abstract base class for all variable modifications.
 *
 * <p>A VariableModification represents a transformation that can be applied to a value of type E.
 * Each concrete implementation defines a specific way to modify values, such as addition, XOR
 * operations, or explicit value replacements.
 *
 * <p>Modifications can be chained together and applied to values at runtime, making it possible to
 * dynamically alter the behavior of variables during program execution.
 *
 * @param <E> The type of value this modification operates on
 */
@XmlTransient
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class VariableModification<E> implements Serializable {

    /** Logger for debugging modification applications */
    protected static final Logger LOGGER = LogManager.getLogger(VariableModification.class);

    /** Default constructor. */
    protected VariableModification() {
        super();
    }

    /**
     * Copy constructor.
     *
     * @param other The modification to copy
     */
    protected VariableModification(VariableModification<E> other) {
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
     * <p>This method delegates to the implementation-specific {@link #modifyImplementationHook}
     * method and handles debugging of the modification operation.
     *
     * @param input The value to modify
     * @return The modified value
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
     * @param input The value to modify
     * @return The modified value
     */
    protected abstract E modifyImplementationHook(E input);

    /**
     * Logs debug information about the modification being applied.
     *
     * <p>This method logs the modification class name, calling function, and the new value after
     * modification. The method optimizes performance by only computing the stack trace if debug
     * logging is enabled.
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
                            case byte[] bytes -> ArrayConverter.bytesToHexString(bytes);
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
