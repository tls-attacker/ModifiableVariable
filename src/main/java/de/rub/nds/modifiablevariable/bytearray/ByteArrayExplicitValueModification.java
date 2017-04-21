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
import de.rub.nds.modifiablevariable.util.ByteArrayAdapter;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * @author Juraj Somorovsky - juraj.somorovsky@rub.de
 */
@XmlRootElement
@XmlType(propOrder = { "explicitValue", "modificationFilter", "postModification" })
public class ByteArrayExplicitValueModification extends VariableModification<byte[]> {

    private byte[] explicitValue;

    public ByteArrayExplicitValueModification() {

    }

    public ByteArrayExplicitValueModification(byte[] explicitValue) {
        this.explicitValue = explicitValue;
    }

    @Override
    protected byte[] modifyImplementationHook(final byte[] input) {
        return explicitValue.clone();
    }

    @XmlJavaTypeAdapter(ByteArrayAdapter.class)
    public byte[] getExplicitValue() {
        return explicitValue;
    }

    public void setExplicitValue(byte[] explicitValue) {
        this.explicitValue = explicitValue;
    }

    @Override
    public String toString() {
        return "ByteArrayExplicitValueModification{" + "explicitValue="
                + ArrayConverter.bytesToHexString(explicitValue) + '}';
    }

}
