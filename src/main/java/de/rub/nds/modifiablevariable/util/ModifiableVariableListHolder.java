/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.util;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Represents a modifiable variable holder (an object containing at least one ModifiableVariable
 * field), containing a list of its ModifiableVariable fields
 */
public class ModifiableVariableListHolder {

    private Object object;

    private List<Field> fields;

    public ModifiableVariableListHolder() {
        super();
    }

    public ModifiableVariableListHolder(Object o, List<Field> fields) {
        super();
        object = o;
        this.fields = fields;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }
}
