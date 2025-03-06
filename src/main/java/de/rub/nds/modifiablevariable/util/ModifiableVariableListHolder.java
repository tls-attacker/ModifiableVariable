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
 * A holder class that pairs an object with a list of its ModifiableVariable fields.
 * 
 * <p>This class is used to represent the relationship between an object and all of its
 * ModifiableVariable fields. It's particularly useful when working with the results of
 * reflection-based analysis, as it preserves the context needed to access and manipulate
 * the fields.
 * 
 * <p>Unlike {@link ModifiableVariableField}, which represents a single field in an object,
 * this class represents all ModifiableVariable fields in an object, making it useful for
 * operations that need to work with all modifiable fields in an object.
 */
public class ModifiableVariableListHolder {

    /** The object containing the ModifiableVariable fields */
    private Object object;

    /** The list of Field objects representing the ModifiableVariables in the object */
    private List<Field> fields;

    /**
     * Default constructor that creates an empty ModifiableVariableListHolder.
     */
    public ModifiableVariableListHolder() {
        super();
    }

    /**
     * Constructor that creates a ModifiableVariableListHolder with the specified object and fields.
     *
     * @param o The object containing the ModifiableVariable fields
     * @param fields The list of Field objects representing ModifiableVariables in the object
     */
    public ModifiableVariableListHolder(Object o, List<Field> fields) {
        super();
        object = o;
        this.fields = fields;
    }

    /**
     * Gets the object containing the ModifiableVariable fields.
     *
     * @return The containing object
     */
    public Object getObject() {
        return object;
    }

    /**
     * Sets the object containing the ModifiableVariable fields.
     *
     * @param object The new containing object
     */
    public void setObject(Object object) {
        this.object = object;
    }

    /**
     * Gets the list of Field objects representing the ModifiableVariables in the object.
     *
     * @return The list of Field objects
     */
    public List<Field> getFields() {
        return fields;
    }

    /**
     * Sets the list of Field objects representing the ModifiableVariables in the object.
     *
     * @param fields The new list of Field objects
     */
    public void setFields(List<Field> fields) {
        this.fields = fields;
    }
}
