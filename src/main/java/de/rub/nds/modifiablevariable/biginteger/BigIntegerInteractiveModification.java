/**
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Copyright 2014-2022 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 *
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.modifiablevariable.biginteger;

import de.rub.nds.modifiablevariable.VariableModification;
import java.math.BigInteger;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import jakarta.xml.bind.annotation.XmlType;

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
