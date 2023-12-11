/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable;

import static de.rub.nds.modifiablevariable.util.StringUtil.backslashEscapeString;

import de.rub.nds.modifiablevariable.filter.AccessModificationFilter;
import de.rub.nds.modifiablevariable.util.ArrayConverter;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElements;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@XmlRootElement
@XmlTransient
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class VariableModification<E> {

    protected static final Logger LOGGER = LogManager.getLogger(VariableModification.class);

    /**
     * In specific cases it is possible to filter out some modifications based on given rules.
     * ModificationFilter is responsible for validating if the modification can be executed.
     */
    @XmlElements(
            value = {
                @XmlElement(
                        type = AccessModificationFilter.class,
                        name = "AccessModificationFilter")
            })
    private ModificationFilter modificationFilter = null;

    public E modify(E input) {
        E modifiedValue = modifyImplementationHook(input);
        if ((modificationFilter == null) || (modificationFilter.filterModification() == false)) {
            debug(modifiedValue);
            return modifiedValue;
        } else {
            return input;
        }
    }

    protected abstract E modifyImplementationHook(E input);

    public abstract VariableModification<E> getModifiedCopy();

    /**
     * Debugging modified variables. Getting stack trace can be time consuming, thus we use
     * isDebugEnabled() function
     *
     * @param value variable modification that is going to be debugged
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
            String valueString;
            if (value instanceof byte[]) {
                valueString = ArrayConverter.bytesToHexString((byte[]) value);
            } else if (value instanceof String) {
                valueString = backslashEscapeString((String) value);
            } else {
                valueString = value.toString();
            }
            LOGGER.debug(
                    "Using {} in function:\n  {}\n  New value: {}",
                    this.getClass().getSimpleName(),
                    stack[index],
                    valueString);
        }
    }

    public ModificationFilter getModificationFilter() {
        return modificationFilter;
    }

    public void setModificationFilter(ModificationFilter modificationFilter) {
        this.modificationFilter = modificationFilter;
    }
}
