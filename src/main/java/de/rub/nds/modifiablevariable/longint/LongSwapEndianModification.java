/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.longint;

import de.rub.nds.modifiablevariable.VariableModification;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LongSwapEndianModification extends VariableModification<Long> {

    public LongSwapEndianModification() {
        super();
    }

    public LongSwapEndianModification(LongSwapEndianModification other) {
        super(other);
    }

    @Override
    public LongSwapEndianModification createCopy() {
        return new LongSwapEndianModification(this);
    }

    @Override
    protected Long modifyImplementationHook(Long input) {
        if (input == null) {
            return null;
        }
        return Long.reverseBytes(input);
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
        return "LongSwapEndianModification{" + '}';
    }
}
