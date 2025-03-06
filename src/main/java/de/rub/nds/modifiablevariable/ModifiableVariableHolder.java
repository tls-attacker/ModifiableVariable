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
 * <p>This abstract class provides functionality for managing and accessing modifiable
 * variables within a containing class. It allows for runtime reflection-based access
 * to fields of type {@link ModifiableVariable} and provides methods for:
 * <ul>
 *   <li>Discovering all modifiable variables in the class</li>
 *   <li>Selecting random modifiable variables for testing</li>
 *   <li>Resetting all modifiable variables</li>
 *   <li>Creating string representations</li>
 * </ul>
 * 
 * <p>This class is the backbone of the modifiable variable framework, as it allows
 * for systematic manipulation and inspection of protocol data structures that
 * contain modifiable variables.
 * 
 * <p>Classes that extend this base class can be serialized to XML using JAXB.
 */
@XmlType(name = "ModVarHolder")
public abstract class ModifiableVariableHolder implements Serializable {

    /** Logger for this class */
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Lists all fields that are of type ModifiableVariable declared in this class and its superclasses.
     * 
     * <p>This method uses reflection to identify all fields that extend ModifiableVariable.
     *
     * @return A list of Field objects representing all ModifiableVariable fields in the class
     */
    public List<Field> getAllModifiableVariableFields() {
        return ReflectionHelper.getFieldsUpTo(getClass(), null, ModifiableVariable.class);
    }

    /**
     * Returns a randomly selected ModifiableVariable field from this class.
     * 
     * <p>This method is particularly useful for testing and fuzzing, where random
     * manipulation of fields is needed.
     *
     * @param random The random number generator to use for the selection
     * @return A randomly selected Field object representing a ModifiableVariable
     */
    public Field getRandomModifiableVariableField(Random random) {
        List<Field> fields = getAllModifiableVariableFields();
        int randomField = random.nextInt(fields.size());
        return fields.get(randomField);
    }

    /**
     * Returns a list of all ModifiableVariableHolder instances in this object.
     * 
     * <p>The default implementation only includes this instance, but subclasses can
     * override this method to include nested ModifiableVariableHolder objects.
     *
     * @return A list containing this instance and, potentially, nested ModifiableVariableHolder objects
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
    public ModifiableVariableHolder getRandomModifiableVariableHolder(Random random) {
        List<ModifiableVariableHolder> holders = getAllModifiableVariableHolders();
        int randomHolder = random.nextInt(holders.size());
        return holders.get(randomHolder);
    }

    /**
     * Resets all ModifiableVariable fields in this object to their default state.
     * 
     * <p>For each ModifiableVariable field:
     * <ul>
     *   <li>If the field has modifications, its original value is set to null</li>
     *   <li>If the field has no modifications, the field itself is set to null</li>
     * </ul>
     * 
     * <p>This method is useful for returning an object to a clean state before
     * applying new modifications.
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
     * Creates a detailed string representation of this object and all its fields.
     * 
     * <p>This method returns a hierarchical, formatted string that includes the values
     * of all fields, including nested ModifiableVariableHolder objects. The format
     * is particularly useful for debugging and logging.
     *
     * @return A formatted string representation of this object
     */
    public String getExtendedString() {
        return getClass().getSimpleName() + "{\n" + getExtendedString(1) + "}\n";
    }

    /**
     * Helper method that creates a detailed string representation of this object with indentation.
     * 
     * <p>This method handles the recursion and formatting for the getExtendedString() method,
     * with special handling for different field types:
     * <ul>
     *   <li>byte[] fields are converted to hexadecimal strings</li>
     *   <li>ModifiableVariableHolder fields are recursively expanded</li>
     *   <li>Other fields use their toString() method</li>
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
