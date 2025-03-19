/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.bool;

import de.rub.nds.modifiablevariable.VariableModification;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * A modification that inverts (toggles) a ModifiableBoolean value.
 *
 * <p>This modification inverts the input Boolean value when applied, turning true to false and
 * false to true. It can be used to flip boolean flags at runtime
 *
 * @see ModifiableBoolean
 * @see BooleanExplicitValueModification
 */
@XmlRootElement
public class BooleanToggleModification extends VariableModification<Boolean> {

    /** Default constructor */
    public BooleanToggleModification() {
        super();
    }

    /**
     * Copy constructor.
     *
     * @param other The modification to copy
     */
    public BooleanToggleModification(BooleanToggleModification other) {
        super();
    }

    /**
     * Creates a deep copy of this modification.
     *
     * @return A new instance of BooleanToggleModification
     */
    @Override
    public BooleanToggleModification createCopy() {
        return new BooleanToggleModification(this);
    }

    /**
     * Modifies the input by inverting its boolean value.
     *
     * <p>If the input is true, returns false.
     *
     * <p>If the input is false, returns true.
     *
     * <p>If the input is null, returns null.
     *
     * @param input The Boolean value to invert
     * @return The inverted Boolean value, or null if the input is null
     */
    @Override
    protected Boolean modifyImplementationHook(Boolean input) {
        if (input == null) {
            return null;
        }
        return !input;
    }

    /**
     * Computes a hash code for this modification.
     *
     * <p>Since this modification has no configurable parameters, all instances are functionally
     * identical and return the same hash code.
     *
     * @return A constant hash code
     */
    @Override
    public int hashCode() {
        return 7;
    }

    /**
     * Checks if this modification is equal to another object.
     *
     * <p>Since this modification has no configurable parameters, it equals any other instance of
     * the same class.
     *
     * @param obj The object to compare with
     * @return true if the object is a BooleanToggleModification, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        return getClass() == obj.getClass();
    }

    /**
     * Returns a string representation of this modification.
     *
     * @return A string identifying this modification type
     */
    @Override
    public String toString() {
        return "BooleanToggleModification{" + '}';
    }
}
