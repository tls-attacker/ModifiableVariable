/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.integer;

import de.rub.nds.modifiablevariable.VariableModification;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class IntegerSwapEndianModification extends VariableModification<Integer> {

    public IntegerSwapEndianModification() {
        super();
    }

    public IntegerSwapEndianModification(IntegerSwapEndianModification other) {
        super(other);
    }

    @Override
    public IntegerSwapEndianModification createCopy() {
        return new IntegerSwapEndianModification(this);
    }

    @Override
    protected Integer modifyImplementationHook(Integer input) {
        if (input == null) {
            return null;
        }
        return Integer.reverseBytes(input);
    }

    @Override
    public int hashCode() {
        return 7;
    }

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

    @Override
    public String toString() {
        return "IntegerSwapEndianModification{" + '}';
    }
}
