/**
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Copyright 2014-2017 Ruhr University Bochum / Hackmanit GmbH
 *
 * Licensed under Apache License 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.biginteger;

import de.rub.nds.modifiablevariable.VariableModification;
import java.math.BigInteger;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author Janis Fliegenschmidt - janis.fliegenschmidt@rub.de
 */
@XmlRootElement
@XmlType(propOrder = { "modificationFilter", "postModification" })
public class BigIntegerInteractiveModification extends VariableModification<BigInteger> {

    private InteractiveBigIntegerModification modification;

    protected BigIntegerInteractiveModification() {
        this.modification = BigIntegerModificationFactory.getStandardInteractiveModification();
    }

    protected BigIntegerInteractiveModification(InteractiveBigIntegerModification modification) {
        this.modification = modification;
    }

    @Override
    protected BigInteger modifyImplementationHook(final BigInteger input) {
        return this.modification.modify(input);
    }

    public interface InteractiveBigIntegerModification {

        BigInteger modify(BigInteger oldVal);
    }
}
