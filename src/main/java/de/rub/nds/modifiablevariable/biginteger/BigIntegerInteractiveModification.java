/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.biginteger;

import de.rub.nds.modifiablevariable.VariableModification;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import jakarta.xml.bind.annotation.XmlType;
import java.math.BigInteger;

@XmlRootElement
@XmlType(propOrder = "modificationFilter")
public class BigIntegerInteractiveModification extends VariableModification<BigInteger> {

    @XmlTransient private final InteractiveBigIntegerModification modification;

    protected BigIntegerInteractiveModification() {
        super();
        modification = BigIntegerModificationFactory.getStandardInteractiveModification();
    }

    protected BigIntegerInteractiveModification(InteractiveBigIntegerModification modification) {
        super();
        this.modification = modification;
    }

    @Override
    protected BigInteger modifyImplementationHook(BigInteger input) {
        return modification.modify(input);
    }

    public interface InteractiveBigIntegerModification {

        BigInteger modify(BigInteger oldVal);
    }

    @Override
    public VariableModification<BigInteger> createCopy() {
        throw new UnsupportedOperationException(
                "This method is not supported for interactive modifications");
    }
}
