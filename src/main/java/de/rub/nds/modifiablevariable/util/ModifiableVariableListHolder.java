/**
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 *
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */
package de.rub.nds.modifiablevariable.util;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Represents a modifiable variable holder (an object containing at least one ModifiableVariable field), containing a
 * list of its ModifiableVariable fields
 * 
 */
public class ModifiableVariableListHolder {

    private Object object;

    private List<Field> fields;

    public ModifiableVariableListHolder() {

    }

    public ModifiableVariableListHolder(Object o, List<Field> f) {
        this.object = o;
        this.fields = f;
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
