/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
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
 * Utility class for analyzing and discovering modifiable variables in arbitrary objects.
 *
 * <p>This class provides static methods to find and inspect modifiable variables within objects,
 * including the ability to recursively scan complex object hierarchies. It uses reflection to
 * locate fields that are either of type ModifiableVariable or are annotated with {@link
 * HoldsModifiableVariable}.
 *
 * <p>The analyzer is particularly useful for:
 *
 * <ul>
 *   <li>Testing frameworks that need to discover all modifiable variables
 *   <li>Security analysis tools that need to examine protocol message structures
 *   <li>Serialization/deserialization utilities
 * </ul>
 *
 * <p>This class cannot be instantiated and all methods are static.
 */
public final class ModifiableVariableAnalyzer {

    /** Logger for this class */
    private static final Logger LOGGER = LogManager.getLogger(ModifiableVariableAnalyzer.class);

    /** Private constructor to prevent instantiation of this utility class. */
    private ModifiableVariableAnalyzer() {
        super();
    }

    /**
     * Finds all fields of type ModifiableVariable in the given object.
     *
     * <p>This method uses reflection to discover all fields in the object's class hierarchy that
     * extend ModifiableVariable. It does not include fields from nested objects.
     *
     * @param object The object to analyze
     * @return A list of Field objects representing all ModifiableVariable fields in the object
     */
    public static List<Field> getAllModifiableVariableFields(Object object) {
        return ReflectionHelper.getFieldsUpTo(object.getClass(), null, ModifiableVariable.class);
    }

    /**
     * Selects a random ModifiableVariable field from the given object.
     *
     * <p>This method is useful for randomized testing where a random field needs to be selected for
     * modification. It uses the {@link RandomHelper} to ensure deterministic behavior in tests.
     *
     * @param object The object from which to select a random ModifiableVariable field
     * @return A randomly selected Field object representing a ModifiableVariable
     * @throws IndexOutOfBoundsException if the object has no ModifiableVariable fields
     */
    public static Field getRandomModifiableVariableField(Object object) {
        List<Field> fields = getAllModifiableVariableFields(object);
        int randomField = RandomHelper.getRandom().nextInt(fields.size());
        return fields.get(randomField);
    }

    /**
     * Determines whether an object contains any ModifiableVariable fields.
     *
     * <p>This method checks if the object has at least one field that extends ModifiableVariable.
     *
     * @param object The object to check
     * @return true if the object contains at least one ModifiableVariable field, false otherwise
     */
    public static boolean isModifiableVariableHolder(Object object) {
        List<Field> fields = getAllModifiableVariableFields(object);
        return !fields.isEmpty();
    }

    /**
     * Recursively finds all ModifiableVariable fields in an object and its nested objects.
     *
     * <p>This method creates a flat list of ModifiableVariableField objects, which pair the
     * containing object with its ModifiableVariable field. This representation makes it easier to
     * manipulate the fields directly.
     *
     * <p>The method traverses the entire object graph, following fields annotated with {@link
     * HoldsModifiableVariable}, and including collection and array elements.
     *
     * @param object The root object to analyze
     * @return A list of ModifiableVariableField objects representing all ModifiableVariable fields
     *     in the object hierarchy
     */
    public static List<ModifiableVariableField> getAllModifiableVariableFieldsRecursively(
            Object object) {
        List<ModifiableVariableListHolder> holders =
                getAllModifiableVariableHoldersRecursively(object);
        List<ModifiableVariableField> fields = new LinkedList<>();
        for (ModifiableVariableListHolder holder : holders) {
            for (Field field : holder.getFields()) {
                fields.add(new ModifiableVariableField(holder.getObject(), field));
            }
        }
        return fields;
    }

    /**
     * Recursively finds all objects that contain ModifiableVariable fields.
     *
     * <p>This method traverses the object graph, starting from the given object, and identifies all
     * objects that contain ModifiableVariable fields. For each such object, it creates a
     * ModifiableVariableListHolder that pairs the object with its ModifiableVariable fields.
     *
     * <p>The traversal follows these rules:
     *
     * <ul>
     *   <li>If the object itself has ModifiableVariable fields, it's included in the result
     *   <li>Any field annotated with {@link HoldsModifiableVariable} is recursively explored
     *   <li>If a field is a List or array, each element is recursively explored
     * </ul>
     *
     * @param object The root object to analyze
     * @return A list of ModifiableVariableListHolder objects, each containing an object and its
     *     ModifiableVariable fields
     */
    public static List<ModifiableVariableListHolder> getAllModifiableVariableHoldersRecursively(
            Object object) {
        List<ModifiableVariableListHolder> holders = new LinkedList<>();
        List<Field> modFields = getAllModifiableVariableFields(object);
        if (!modFields.isEmpty()) {
            holders.add(new ModifiableVariableListHolder(object, modFields));
        }
        List<Field> allFields = ReflectionHelper.getFieldsUpTo(object.getClass(), null, null);
        for (Field field : allFields) {
            try {
                HoldsModifiableVariable holdsVariable =
                        field.getAnnotation(HoldsModifiableVariable.class);
                field.setAccessible(true);
                Object possibleHolder = field.get(object);
                if (possibleHolder != null && holdsVariable != null) {
                    if (possibleHolder instanceof List) {
                        @SuppressWarnings("unchecked")
                        List<Object> castedList = (List<Object>) possibleHolder;
                        holders.addAll(getAllModifiableVariableHoldersFromList(castedList));
                    } else if (possibleHolder.getClass().isArray()) {
                        holders.addAll(
                                getAllModifiableVariableHoldersFromArray(
                                        (Object[]) possibleHolder));
                    } else {
                        holders.addAll(getAllModifiableVariableHoldersRecursively(possibleHolder));
                    }
                }
            } catch (IllegalAccessException | IllegalArgumentException ex) {
                LOGGER.debug(
                        "Accessing field {} of type {} not possible: {}",
                        field.getName(),
                        field.getType(),
                        ex.toString());
            }
        }
        return holders;
    }

    /**
     * Finds all ModifiableVariable holders within a list of objects.
     *
     * <p>This helper method recursively processes each non-null element in the list to find objects
     * that contain ModifiableVariable fields.
     *
     * @param list The list of objects to analyze
     * @return A list of ModifiableVariableListHolder objects, each containing an object and its
     *     ModifiableVariable fields
     */
    public static List<ModifiableVariableListHolder> getAllModifiableVariableHoldersFromList(
            List<Object> list) {
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
     * Finds all ModifiableVariable holders within an array of objects.
     *
     * <p>This helper method recursively processes each element in the array to find objects that
     * contain ModifiableVariable fields.
     *
     * @param array The array of objects to analyze
     * @return A list of ModifiableVariableListHolder objects, each containing an object and its
     *     ModifiableVariable fields
     */
    public static List<ModifiableVariableListHolder> getAllModifiableVariableHoldersFromArray(
            Object[] array) {
        List<ModifiableVariableListHolder> result = new LinkedList<>();
        for (Object o : array) {
            result.addAll(getAllModifiableVariableHoldersRecursively(o));
        }
        return result;
    }
}
