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

@XmlType(name = "ModVarHolder")
public abstract class ModifiableVariableHolder implements Serializable {

    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Lists all the modifiable variables declared in the class
     *
     * @return List of all modifiableVariables declared in this class
     */
    public List<Field> getAllModifiableVariableFields() {
        return ReflectionHelper.getFieldsUpTo(getClass(), null, ModifiableVariable.class);
    }

    /**
     * Returns a random field representing a modifiable variable from this class
     *
     * @param random The RandomNumber generator that should be used
     * @return A random ModifiableVariableField
     */
    public Field getRandomModifiableVariableField(Random random) {
        List<Field> fields = getAllModifiableVariableFields();
        int randomField = random.nextInt(fields.size());
        return fields.get(randomField);
    }

    /**
     * Returns a list of all the modifiable variable holders in the object, including this instance
     *
     * @return All ModifiableVariableHolders
     */
    public List<ModifiableVariableHolder> getAllModifiableVariableHolders() {
        List<ModifiableVariableHolder> holders = new LinkedList<>();
        holders.add(this);
        return holders;
    }

    /**
     * Returns a random modifiable variable holder
     *
     * @param random The RandomNumberGenerator that should be used
     * @return A Random ModifiableVariableHolder
     */
    public ModifiableVariableHolder getRandomModifiableVariableHolder(Random random) {
        List<ModifiableVariableHolder> holders = getAllModifiableVariableHolders();
        int randomHolder = random.nextInt(holders.size());
        return holders.get(randomHolder);
    }

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

    public String getExtendedString() {
        return getClass().getSimpleName() + "{\n" + getExtendedString(1) + "}\n";
    }

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
                if (tempObject instanceof byte[]) {
                    byte[] temp = (byte[]) tempObject;
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
