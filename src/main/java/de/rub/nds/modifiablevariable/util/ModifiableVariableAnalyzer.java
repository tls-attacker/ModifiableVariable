/**
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Copyright 2014-2022 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 *
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.modifiablevariable.util;

import de.rub.nds.modifiablevariable.HoldsModifiableVariable;
import de.rub.nds.modifiablevariable.ModifiableVariable;
import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 */
public class ModifiableVariableAnalyzer {

    private static final Logger LOGGER = LogManager.getLogger(ModifiableVariableAnalyzer.class);

    /**
     * Lists all the modifiable variables declared in the given class
     *
     * @param  object
     *                Analyzed object
     * @return        A list of modifiable variable fields for the given object
     */
    public static List<Field> getAllModifiableVariableFields(Object object) {
        return ReflectionHelper.getFieldsUpTo(object.getClass(), null, ModifiableVariable.class);
    }

    /**
     * Returns a random field representing a modifiable variable in the given class
     *
     * @param  object
     *                Analyzed object
     * @return        A random field representing a modifiable variable
     */
    public static Field getRandomModifiableVariableField(Object object) {
        List<Field> fields = getAllModifiableVariableFields(object);
        int randomField = RandomHelper.getRandom().nextInt(fields.size());
        return fields.get(randomField);
    }

    /**
     * Returns true if the given object contains a modifiable variable
     *
     * @param  object
     *                Analyzed object
     * @return        True if the object contains a modifiable variable.
     */
    public static boolean isModifiableVariableHolder(Object object) {
        List<Field> fields = getAllModifiableVariableFields(object);
        return !fields.isEmpty();
    }

    /**
     * Returns a list of all ModifiableVariableFields (object-field representations) for a given object.
     *
     * @param  object
     *                Analyzed object
     * @return        A list of objects with their modifiable variable fields (only objects with modifiable variables
     *                are selected)
     */
    public static List<ModifiableVariableField> getAllModifiableVariableFieldsRecursively(Object object) {
        List<ModifiableVariableListHolder> holders = getAllModifiableVariableHoldersRecursively(object);
        List<ModifiableVariableField> fields = new LinkedList<>();
        for (ModifiableVariableListHolder holder : holders) {
            for (Field f : holder.getFields()) {
                fields.add(new ModifiableVariableField(holder.getObject(), f));
            }
        }
        return fields;
    }

    /**
     * Returns a list of all the modifiable variable holders in the object, including this instance.
     *
     * @param  object
     *                Analyzed object
     * @return        A list of objects with their modifiable variable fields (only objects with modifiable variables
     *                are selected)
     */
    public static List<ModifiableVariableListHolder> getAllModifiableVariableHoldersRecursively(Object object) {
        List<ModifiableVariableListHolder> holders = new LinkedList<>();
        List<Field> modFields = getAllModifiableVariableFields(object);
        if (!modFields.isEmpty()) {
            holders.add(new ModifiableVariableListHolder(object, modFields));
        }
        List<Field> allFields = ReflectionHelper.getFieldsUpTo(object.getClass(), null, null);
        for (Field f : allFields) {
            try {
                HoldsModifiableVariable holdsVariable = f.getAnnotation(HoldsModifiableVariable.class);
                f.setAccessible(true);
                Object possibleHolder = f.get(object);
                if (possibleHolder != null && holdsVariable != null) {
                    if (possibleHolder instanceof List) {
                        holders.addAll(getAllModifiableVariableHoldersFromList((List) possibleHolder));
                    } else if (possibleHolder.getClass().isArray()) {
                        holders.addAll(getAllModifiableVariableHoldersFromArray((Object[]) possibleHolder));
                    } else {
                        holders.addAll(getAllModifiableVariableHoldersRecursively(possibleHolder));
                    }
                }
            } catch (IllegalAccessException | IllegalArgumentException ex) {
                LOGGER.debug("Accessing field {} of type {} not possible: {}", f.getName(), f.getType(), ex.toString());
            }
        }
        return holders;
    }

    /**
     * @param  list
     *              Analyzed list
     * @return      A list of objects with their modifiable variable fields (only objects with modifiable variables are
     *              selected)
     */
    public static List<ModifiableVariableListHolder> getAllModifiableVariableHoldersFromList(List<Object> list) {
        List<ModifiableVariableListHolder> result = new LinkedList<>();
        for (Object o : list) {
            if (o != null) {
                result.addAll(getAllModifiableVariableHoldersRecursively(o));
            } else {
                LOGGER.warn("Found null getAllModifiableVariableHoldersFromList");
            }
        }
        return result;
    }

    /**
     * @param  array
     *               Analyzed array of objects
     * @return       A list of objects with their modifiable variable fields (only objects with modifiable variables are
     *               selected)
     */
    public static List<ModifiableVariableListHolder> getAllModifiableVariableHoldersFromArray(Object[] array) {
        List<ModifiableVariableListHolder> result = new LinkedList<>();
        for (Object o : array) {
            result.addAll(getAllModifiableVariableHoldersRecursively(o));
        }
        return result;
    }
}
