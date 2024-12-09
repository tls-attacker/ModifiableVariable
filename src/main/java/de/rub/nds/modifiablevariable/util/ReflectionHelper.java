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

public final class ReflectionHelper {

    private ReflectionHelper() {
        super();
    }

    /**
     * Source: <a
     * href="http://stackoverflow.com/questions/17451506/list-all-private-fields-of-a-java-object">...</a>
     * Retrieves all fields (all access levels) from all classes up the class hierarchy starting
     * with {@code startClass} stopping with and not including {@code exclusiveParent}. Generally
     * {@code Object.class} should be passed as {@code exclusiveParent}.
     *
     * @param startClass the class whose fields should be retrieved
     * @param exclusiveParent if not null, the base class of startClass whose fields should not be
     *     retrieved.
     * @param filterClass class that should be used as a type filter
     * @return list of fields included in the class and its ancestors
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

    /** Takes a list of fields and returns only fields which are subclasses of the filterClass */
    private static List<Field> filterFieldList(List<Field> fields, Class<?> filterClass) {
        List<Field> filteredFields = new LinkedList<>();

        for (Field field : fields) {
            if (filterClass == null || filterClass.isAssignableFrom(field.getType())) {
                filteredFields.add(field);
            }
        }

        return filteredFields;
    }

    public static List<Object> getValuesFromFieldList(Object object, List<Field> fields)
            throws IllegalAccessException {
        List<Object> list = new LinkedList<>();

        for (Field field : fields) {
            field.setAccessible(true);
            list.add(field.get(object));
        }

        return list;
    }

    public static Type[] getParameterizedTypes(Object object) {
        Type superclassType = object.getClass().getGenericSuperclass();

        if (!ParameterizedType.class.isAssignableFrom(superclassType.getClass())) {
            return null;
        }

        return ((ParameterizedType) superclassType).getActualTypeArguments();
    }
}
