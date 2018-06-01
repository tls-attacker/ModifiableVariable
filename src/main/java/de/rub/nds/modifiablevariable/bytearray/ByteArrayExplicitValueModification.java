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
import java.util.Arrays;
import java.util.Random;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement
@XmlType(propOrder = { "explicitValue", "modificationFilter", "postModification" })
public class ByteArrayExplicitValueModification extends VariableModification<byte[]> {

    private final static int MAX_EXPLICIT_VALUE = 256;

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

    @Override
    public VariableModification<byte[]> getModifiedCopy() {
        Random r = new Random();
        if (explicitValue.length == 0) {
            return this;
        }
        int index = r.nextInt(explicitValue.length);
        byte[] newValue = Arrays.copyOf(explicitValue, explicitValue.length);
        newValue[index] = (byte) r.nextInt(MAX_EXPLICIT_VALUE);
        return new ByteArrayExplicitValueModification(newValue);
    }
}
