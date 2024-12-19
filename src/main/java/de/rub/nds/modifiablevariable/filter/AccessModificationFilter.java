/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.filter;

import de.rub.nds.modifiablevariable.ModificationFilter;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * Filters modification executions for specific accesses, starting with 1.
 *
 * <p>For example, if one defines accessNumbers = {1,3} and executes four times getValue() function
 * on a modifiable variable, the modification is executed only during the second and fourth
 * getValue() method invocation.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class AccessModificationFilter extends ModificationFilter {

    private int accessCounter;

    /** accesses when the modification will be filtered (will not be executed) */
    private int[] accessNumbers;

    public AccessModificationFilter() {
        super();
        accessCounter = 1;
    }

    public AccessModificationFilter(int[] accessNumbers) {
        super();
        accessCounter = 1;
        this.accessNumbers = accessNumbers;
    }

    public AccessModificationFilter(AccessModificationFilter other) {
        super(other);
        accessCounter = other.accessCounter;
        accessNumbers = other.accessNumbers != null ? other.accessNumbers.clone() : null;
    }

    @Override
    public AccessModificationFilter createCopy() {
        return new AccessModificationFilter(this);
    }

    @Override
    public boolean filterModification() {
        boolean filter = contains(accessNumbers, accessCounter);
        accessCounter++;
        return filter;
    }

    private static boolean contains(int[] numbers, int number) {
        for (int i : numbers) {
            if (i == number) {
                return true;
            }
        }
        return false;
    }

    public int[] getAccessNumbers() {
        return accessNumbers;
    }

    public void setAccessNumbers(int[] accessNumbers) {
        this.accessNumbers = accessNumbers;
    }
}
