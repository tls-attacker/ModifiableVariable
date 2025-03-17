/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.util;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Utility class providing reflection-based helper methods.
 *
 * <p>This class contains static methods for common reflection operations used throughout the
 * modifiable variable framework, such as:
 *
 * <ul>
 *   <li>Finding fields in a class hierarchy
 *   <li>Filtering fields by type
 *   <li>Extracting values from fields
 *   <li>Working with generic type parameters
 * </ul>
 *
 * <p>The methods in this class make it easier to work with reflection in a consistent way, reducing
 * code duplication and centralizing reflection-related logic.
 *
 * <p>This class cannot be instantiated and all methods are static.
 */
public final class ReflectionHelper {

    /** Private constructor to prevent instantiation of this utility class. */
    private ReflectionHelper() {
        super();
    }

    /**
     * Retrieves all fields from a class and its superclasses, optionally filtering by type.
     *
     * <p>This method traverses the class hierarchy starting from the specified class and collects
     * all fields (regardless of access level) from each class in the hierarchy. The traversal stops
     * when it reaches (but does not include) the specified exclusive parent class.
     *
     * <p>The fields can be optionally filtered by type, including only those that are assignable
     * from the specified filter class. If the filter class is null, all fields are included.
     *
     * <p>Source: <a
     * href="http://stackoverflow.com/questions/17451506/list-all-private-fields-of-a-java-object">
     * Stack Overflow: List all private fields of a Java object</a>
     *
     * @param startClass The class from which to start collecting fields
     * @param exclusiveParent The parent class at which to stop collecting fields (exclusive),
     *     typically Object.class
     * @param filterClass The class to use as a type filter, or null to include all fields
     * @return A list of Field objects from the class hierarchy, optionally filtered by type
     */
    public static List<Field> getFieldsUpTo(
            Class<?> startClass, Class<?> exclusiveParent, Class<?> filterClass) {
        List<Field> currentClassFields;

        currentClassFields =
                filterFieldList(Arrays.asList(startClass.getDeclaredFields()), filterClass);

        Class<?> parentClass = startClass.getSuperclass();

        if (parentClass != null && !parentClass.equals(exclusiveParent)) {
            List<Field> parentClassFields =
                    getFieldsUpTo(parentClass, exclusiveParent, filterClass);

            currentClassFields.addAll(parentClassFields);
        }

        return currentClassFields;
    }

    /**
     * Filters a list of fields to include only those whose type is assignable from the specified
     * class.
     *
     * <p>This helper method is used to filter fields by their type. If the filter class is null,
     * all fields are included in the result.
     *
     * @param fields The list of fields to filter
     * @param filterClass The class to use as a filter, or null to include all fields
     * @return A list containing only the fields whose type is assignable from the filter class
     */
    private static List<Field> filterFieldList(List<Field> fields, Class<?> filterClass) {
        List<Field> filteredFields = new LinkedList<>();

        for (Field field : fields) {
            if (filterClass == null || filterClass.isAssignableFrom(field.getType())) {
                filteredFields.add(field);
            }
        }

        return filteredFields;
    }

    /**
     * Extracts the values of the specified fields from an object.
     *
     * <p>This method uses reflection to access each field in the list and retrieve its value from
     * the specified object. The fields are made accessible before retrieval.
     *
     * @param object The object from which to retrieve field values
     * @param fields The list of fields whose values should be retrieved
     * @return A list of values corresponding to the specified fields
     * @throws IllegalAccessException If any field cannot be accessed
     */
    public static List<Object> getValuesFromFieldList(Object object, List<Field> fields)
            throws IllegalAccessException {
        List<Object> list = new LinkedList<>();

        for (Field field : fields) {
            field.setAccessible(true);
            list.add(field.get(object));
        }

        return list;
    }

    /**
     * Retrieves the actual type arguments of an object's generic superclass.
     *
     * <p>This method is useful for working with generic types at runtime, allowing access to the
     * specific type parameters used in a subclass of a generic class.
     *
     * <p>For example, if a class extends {@code ArrayList<String>}, this method would return an
     * array containing {@code String.class}.
     *
     * @param object The object whose generic type parameters should be retrieved
     * @return An array of Type objects representing the actual type arguments, or null if the
     *     object's superclass is not a parameterized type
     */
    public static Type[] getParameterizedTypes(Object object) {
        Type superclassType = object.getClass().getGenericSuperclass();

        if (!ParameterizedType.class.isAssignableFrom(superclassType.getClass())) {
            return null;
        }

        return ((ParameterizedType) superclassType).getActualTypeArguments();
    }
}
