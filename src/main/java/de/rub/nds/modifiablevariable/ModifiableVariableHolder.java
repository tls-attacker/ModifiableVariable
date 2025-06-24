/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable;

import de.rub.nds.modifiablevariable.util.ArrayConverter;
import de.rub.nds.modifiablevariable.util.ReflectionHelper;
import de.rub.nds.modifiablevariable.validation.ModifiableVariableValidator;
import de.rub.nds.modifiablevariable.validation.ValidationResult;
import jakarta.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Base class for objects that contain modifiable variables.
 *
 * <p>This abstract class provides functionality for managing and accessing modifiable variables
 * within a containing class. It allows for runtime reflection-based access to fields of type {@link
 * ModifiableVariable} and provides methods for:
 *
 * <ul>
 *   <li>Discovering all modifiable variables in the class
 *   <li>Selecting random modifiable variables for testing
 *   <li>Resetting all modifiable variables
 *   <li>Creating string representations
 * </ul>
 *
 * <p>This class is the backbone of the modifiable variable framework, as it allows for systematic
 * manipulation and inspection of protocol data structures that contain modifiable variables.
 *
 * <p>Classes that extend this base class can be serialized to XML using JAXB.
 */
@XmlType(name = "ModVarHolder")
public abstract class ModifiableVariableHolder implements Serializable {

    /** Logger for this class */
    private static final Logger LOGGER = LogManager.getLogger();

    /** Creates a new ModifiableVariableHolder.+ */
    protected ModifiableVariableHolder() {
        // Default constructor deliberately left empty
    }

    /**
     * Lists all fields that are of type ModifiableVariable declared in this class and its
     * superclasses.
     *
     * <p>This method uses reflection to identify all fields that extend ModifiableVariable.
     *
     * @return A list of Field objects representing all ModifiableVariable fields in the class
     */
    public final List<Field> getAllModifiableVariableFields() {
        return ReflectionHelper.getFieldsUpTo(getClass(), null, ModifiableVariable.class);
    }

    /**
     * Returns a randomly selected ModifiableVariable field from this class.
     *
     * <p>This method is particularly useful for testing and fuzzing, where random manipulation of
     * fields is needed.
     *
     * @param random The random number generator to use for the selection
     * @return A randomly selected Field object representing a ModifiableVariable
     */
    public final Field getRandomModifiableVariableField(Random random) {
        List<Field> fields = getAllModifiableVariableFields();
        int randomField = random.nextInt(fields.size());
        return fields.get(randomField);
    }

    /**
     * Returns a list of all ModifiableVariableHolder instances in this object.
     *
     * <p>The default implementation only includes this instance, but subclasses can override this
     * method to include nested ModifiableVariableHolder objects.
     *
     * @return A list containing this instance and, potentially, nested ModifiableVariableHolder
     *     objects
     */
    public List<ModifiableVariableHolder> getAllModifiableVariableHolders() {
        List<ModifiableVariableHolder> holders = new LinkedList<>();
        holders.add(this);
        return holders;
    }

    /**
     * Returns a randomly selected ModifiableVariableHolder from this object.
     *
     * <p>If this class contains nested ModifiableVariableHolder objects and overrides
     * getAllModifiableVariableHolders(), this method could return one of those nested holders.
     *
     * @param random The random number generator to use for the selection
     * @return A randomly selected ModifiableVariableHolder
     */
    public final ModifiableVariableHolder getRandomModifiableVariableHolder(Random random) {
        List<ModifiableVariableHolder> holders = getAllModifiableVariableHolders();
        int randomHolder = random.nextInt(holders.size());
        return holders.get(randomHolder);
    }

    /**
     * Resets all ModifiableVariable fields in this object to their default state.
     *
     * <p>For each ModifiableVariable field:
     *
     * <ul>
     *   <li>If the field has modifications, its original value is set to null
     *   <li>If the field has no modifications, the field itself is set to null
     * </ul>
     *
     * <p>This method is useful for returning an object to a clean state before applying new
     * modifications.
     */
    public void reset() {
        List<Field> fields = getAllModifiableVariableFields();
        for (Field field : fields) {
            field.setAccessible(true);

            ModifiableVariable<?> mv = null;
            try {
                mv = (ModifiableVariable<?>) field.get(this);
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                LOGGER.warn("Could not retrieve ModifiableVariables");
                LOGGER.debug(ex);
            }
            if (mv != null) {
                if (mv.getModifications() != null) {
                    mv.setOriginalValue(null);
                } else {
                    try {
                        field.set(this, null);
                    } catch (IllegalArgumentException | IllegalAccessException ex) {
                        LOGGER.warn("Could not strip ModifiableVariable without Modification");
                    }
                }
            }
        }
    }

    /**
     * Validates all ModifiableVariable fields that have ModifiableVariableProperty annotations.
     *
     * <p>This method uses reflection to find all fields with ModifiableVariableProperty annotations
     * and validates them against their constraints. It also recursively validates any nested
     * ModifiableVariableHolder objects marked with the HoldsModifiableVariable annotation.
     *
     * @return A combined ValidationResult for all annotated fields
     */
    public ValidationResult validatePropertyAnnotations() {
        return ModifiableVariableValidator.validateObject(this);
    }

    /**
     * Validates all assertions set on ModifiableVariable fields in this object.
     *
     * <p>This method checks all ModifiableVariable fields and calls their validateAssertions()
     * method. It also recursively validates assertions in nested ModifiableVariableHolder objects
     * marked with the HoldsModifiableVariable annotation.
     *
     * @return true if all assertions pass or no assertions are set, false otherwise
     */
    public boolean validateAssertions() {
        List<Field> fields = getAllModifiableVariableFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                ModifiableVariable<?> mv = (ModifiableVariable<?>) field.get(this);
                if (mv != null && mv.containsAssertion() && !mv.validateAssertions()) {
                    return false;
                }
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                LOGGER.warn("Could not validate assertions for field: " + field.getName());
                LOGGER.debug(ex);
                return false;
            }
        }

        // Also check nested ModifiableVariableHolder objects
        List<Field> allFields = ReflectionHelper.getFieldsUpTo(getClass(), null, null);
        for (Field field : allFields) {
            if (field.isAnnotationPresent(HoldsModifiableVariable.class)) {
                field.setAccessible(true);
                try {
                    Object value = field.get(this);
                    if (value instanceof ModifiableVariableHolder) {
                        if (!((ModifiableVariableHolder) value).validateAssertions()) {
                            return false;
                        }
                    }
                } catch (IllegalArgumentException | IllegalAccessException ex) {
                    LOGGER.warn("Could not validate nested holder: " + field.getName());
                    LOGGER.debug(ex);
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Creates a detailed string representation of this object and all its fields.
     *
     * <p>This method returns a hierarchical, formatted string that includes the values of all
     * fields, including nested ModifiableVariableHolder objects. The format is particularly useful
     * for debugging and detailed logging.
     *
     * @return A formatted string representation of this object
     */
    public String getExtendedString() {
        return getClass().getSimpleName() + "{\n" + getExtendedString(1) + "}\n";
    }

    /**
     * Helper method that creates a detailed string representation of this object with indentation.
     *
     * <p>This method handles the recursion and formatting for the getExtendedString() method, with
     * special handling for different field types:
     *
     * <ul>
     *   <li>byte[] fields are converted to hexadecimal strings
     *   <li>ModifiableVariableHolder fields are recursively expanded
     *   <li>Other fields use their toString() method
     * </ul>
     *
     * @param depth The current indentation depth
     * @return A formatted string representing this object's fields
     */
    protected String getExtendedString(int depth) {
        StringBuilder stringBuilder = new StringBuilder();
        List<Field> fields = ReflectionHelper.getFieldsUpTo(getClass(), null, null);
        for (Field field : fields) {
            field.setAccessible(true);
            // skip static
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            Object tempObject = null;
            try {
                tempObject = field.get(this);
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                LOGGER.warn("Could not retrieve ModifiableVariables");
                LOGGER.debug(ex);
            }
            if (tempObject != null) {
                if (tempObject instanceof byte[] temp) {
                    stringBuilder.append("\t".repeat(Math.max(0, depth)));
                    stringBuilder.append(field.getName());
                    stringBuilder.append(": ");
                    stringBuilder.append(ArrayConverter.bytesToHexString(temp));
                    stringBuilder.append("\n");
                }
                if (tempObject instanceof ModifiableVariableHolder) {
                    stringBuilder.append("\t".repeat(Math.max(0, depth)));
                    stringBuilder.append(field.getName());
                    stringBuilder.append(":");
                    stringBuilder.append(tempObject.getClass().getSimpleName());
                    stringBuilder.append("{\n");
                    stringBuilder.append(
                            ((ModifiableVariableHolder) tempObject).getExtendedString(depth + 1));
                    stringBuilder.append("\t".repeat(Math.max(0, depth)));
                    stringBuilder.append("}\n");
                } else {
                    stringBuilder.append("\t".repeat(Math.max(0, depth)));
                    stringBuilder.append(field.getName());
                    stringBuilder.append(": ");
                    stringBuilder.append(tempObject);
                    stringBuilder.append("\n");
                }
            } else {
                stringBuilder.append("\t".repeat(Math.max(0, depth)));
                stringBuilder.append(field.getName());
                stringBuilder.append(": null\n");
            }
        }
        return stringBuilder.toString();
    }
}
