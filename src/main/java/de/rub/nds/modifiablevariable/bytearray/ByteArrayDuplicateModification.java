/**
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Copyright 2014-2017 Ruhr University Bochum / Hackmanit GmbH
 *
 * Licensed under Apache License 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.bytearray;

import de.rub.nds.modifiablevariable.VariableModification;
import de.rub.nds.modifiablevariable.util.ArrayConverter;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author Juraj Somorovsky - juraj.somorovsky@rub.de
 */
@XmlRootElement
@XmlType(propOrder = { "modificationFilter", "postModification" })
public class ByteArrayDuplicateModification extends VariableModification<byte[]> {

    public ByteArrayDuplicateModification() {

    }

    @Override
    protected byte[] modifyImplementationHook(byte[] input) {
        if (input == null) {
            input = new byte[0];
        }
        return ArrayConverter.concatenate(input, input);
    }
}
