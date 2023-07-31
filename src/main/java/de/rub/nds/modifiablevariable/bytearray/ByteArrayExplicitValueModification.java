/**
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 *
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */
package de.rub.nds.modifiablevariable.bytearray;

import de.rub.nds.modifiablevariable.VariableModification;
import de.rub.nds.modifiablevariable.util.ArrayConverter;
import de.rub.nds.modifiablevariable.util.UnformattedByteArrayAdapter;
import java.util.Arrays;
import java.util.Random;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement
@XmlType(propOrder = { "explicitValue", "modificationFilter" })
@XmlAccessorType(XmlAccessType.FIELD)
public class ByteArrayExplicitValueModification extends VariableModification<byte[]> {

    private static final int MAX_EXPLICIT_VALUE = 256;

    @XmlJavaTypeAdapter(UnformattedByteArrayAdapter.class)
    private byte[] explicitValue;

    public ByteArrayExplicitValueModification() {

    }

    public ByteArrayExplicitValueModification(byte[] explicitValue) {
        this.explicitValue = explicitValue;
    }

    @Override
    protected byte[] modifyImplementationHook(final byte[] input) {
        byte[] newCopy = explicitValue.clone();
        LOGGER.debug(ArrayConverter.bytesToHexString(newCopy));
        return newCopy;
    }

    public byte[] getExplicitValue() {
        return explicitValue;
    }

    public void setExplicitValue(byte[] explicitValue) {
        this.explicitValue = explicitValue;
    }

    @Override
    public String toString() {
        return "ByteArrayExplicitValueModification{" + "explicitValue=" + ArrayConverter.bytesToHexString(explicitValue)
            + '}';
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

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Arrays.hashCode(this.explicitValue);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ByteArrayExplicitValueModification other = (ByteArrayExplicitValueModification) obj;
        if (!Arrays.equals(this.explicitValue, other.explicitValue)) {
            return false;
        }
        return true;
    }
}
