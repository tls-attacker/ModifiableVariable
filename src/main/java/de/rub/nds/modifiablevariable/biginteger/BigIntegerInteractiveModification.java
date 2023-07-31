/**
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 *
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.modifiablevariable.biginteger;

import de.rub.nds.modifiablevariable.VariableModification;
import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(propOrder = { "modificationFilter" })
@XmlAccessorType(XmlAccessType.FIELD)
public class BigIntegerInteractiveModification extends VariableModification<BigInteger> {

    @XmlTransient
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

    @Override
    public VariableModification<BigInteger> getModifiedCopy() {
        throw new UnsupportedOperationException("This method is not supported for interactive modifications");
    }

}
