/**
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Copyright 2014-2022 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 *
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.modifiablevariable.filter;

import de.rub.nds.modifiablevariable.ModificationFilter;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Filters modification executions for specific accesses, starting with 1.
 * 
 * For example, if one defines accessNumbers = {1,3} and executes four times getValue() function on a modifiable
 * variable, the modification is executed only during the second and fourth getValue() method invocation.
 * 
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class AccessModificationFilter extends ModificationFilter {

    private int accessCounter;

    /**
     * accesses when the modification will be filtered (will not be executed)
     */
    private int[] accessNumbers;

    public AccessModificationFilter() {
        accessCounter = 1;
    }

    public AccessModificationFilter(final int[] accessNumbers) {
        accessCounter = 1;
        this.accessNumbers = accessNumbers;
    }

    @Override
    public boolean filterModification() {
        boolean filter = contains(accessNumbers, accessCounter);
        accessCounter++;
        return filter;
    }

    private boolean contains(int[] numbers, int number) {
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
