/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.util;

import de.rub.nds.modifiablevariable.ModifiableVariable;
import java.lang.reflect.Field;

/**
 * A wrapper class that pairs an object with one of its ModifiableVariable fields.
 *
 * <p>This class is used to represent the relationship between an object and a specific
 * ModifiableVariable field within it.
 *
 * <p>The class provides methods to access both the containing object and the field, as well as a
 * convenience method to directly access the ModifiableVariable instance.
 */
public class ModifiableVariableField {

    /** The object containing the ModifiableVariable field */
    private Object object;

    /** The Field object representing the ModifiableVariable */
    private Field field;

    /**
     * Constructor that creates a ModifiableVariableField with the specified object and field.
     *
     * @param o The object containing the ModifiableVariable field
     * @param field The Field object representing the ModifiableVariable
     */
    public ModifiableVariableField(Object o, Field field) {
        super();
        object = o;
        this.field = field;
    }

    /**
     * Gets the object containing the ModifiableVariable field.
     *
     * @return The containing object
     */
    public Object getObject() {
        return object;
    }

    /**
     * Sets the object containing the ModifiableVariable field.
     *
     * @param object The new containing object
     */
    public void setObject(Object object) {
        this.object = object;
    }

    /**
     * Gets the Field object representing the ModifiableVariable.
     *
     * @return The Field object
     */
    public Field getField() {
        return field;
    }

    /**
     * Sets the Field object representing the ModifiableVariable.
     *
     * @param field The new Field object
     */
    public void setField(Field field) {
        this.field = field;
    }

    /**
     * Gets the actual ModifiableVariable instance from the object.
     *
     * <p>This method uses reflection to access the field in the object, making the field accessible
     * if necessary.
     *
     * @return The ModifiableVariable instance
     * @throws IllegalArgumentException If the field is not accessible or does not exist
     * @throws IllegalAccessException If the field cannot be accessed
     */
    public ModifiableVariable<?> getModifiableVariable()
            throws IllegalArgumentException, IllegalAccessException {
        field.setAccessible(true);
        return (ModifiableVariable<?>) field.get(object);
    }
}
