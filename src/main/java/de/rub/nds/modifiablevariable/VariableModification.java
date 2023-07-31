/**
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 *
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */
package de.rub.nds.modifiablevariable;

import de.rub.nds.modifiablevariable.filter.AccessModificationFilter;
import de.rub.nds.modifiablevariable.util.ArrayConverter;
import de.rub.nds.modifiablevariable.util.UnformattedByteArrayAdapter;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@XmlRootElement
@XmlTransient
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class VariableModification<E> {

    protected static final Logger LOGGER = LogManager.getLogger(VariableModification.class);

    /**
     * In specific cases it is possible to filter out some modifications based on given rules. ModificationFilter is
     * responsible for validating if the modification can be executed.
     */
    @XmlElements(value = { @XmlElement(type = AccessModificationFilter.class, name = "AccessModificationFilter") })
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
     * Debugging modified variables. Getting stack trace can be time consuming, thus we use isDebugEnabled() function
     *
     * @param value
     *              variable modification that is going to be debugged
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
            if (value.getClass().getSimpleName().equals("byte[]")) {
                valueString = ArrayConverter.bytesToHexString((byte[]) value);
            } else {
                valueString = value.toString();
            }
            LOGGER.debug("Using {} in function:\n  {}\n  New value: {}", this.getClass().getSimpleName(), stack[index],
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
