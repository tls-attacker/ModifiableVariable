/**
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Copyright 2014-2022 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 *
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.modifiablevariable.bytearray;

import de.rub.nds.modifiablevariable.VariableModification;
import de.rub.nds.modifiablevariable.util.ArrayConverter;
import de.rub.nds.modifiablevariable.util.UnformattedByteArrayAdapter;
import java.util.Arrays;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement
@XmlType(propOrder = { "prependPayload", "payload", "appendPayload", "insert", "insertPosition", "modificationFilter" })
public class ByteArrayPayloadModification extends VariableModification<byte[]> {

    @XmlJavaTypeAdapter(UnformattedByteArrayAdapter.class)
    private byte[] prependPayload = new byte[] {};

    @XmlJavaTypeAdapter(UnformattedByteArrayAdapter.class)
    private byte[] payload = new byte[] {};

    @XmlJavaTypeAdapter(UnformattedByteArrayAdapter.class)
    private byte[] appendPayload = new byte[] {};

    private boolean insert = false;

    private int insertPosition = 0;

    public ByteArrayPayloadModification() {

    }

    public ByteArrayPayloadModification(byte[] payload) {
        this.payload = payload;
    }

    public ByteArrayPayloadModification(byte[] payload, int insertPosition) {
        this.payload = payload;
        this.insert = true;
        this.insertPosition = insertPosition;
    }

    @Override
    protected byte[] modifyImplementationHook(byte[] input) {
        byte[] completePayload = getCompletePayload();

        if (!insert) {
            return completePayload;
        }

        ByteArrayInsertModification insertMod = new ByteArrayInsertModification(completePayload, this.insertPosition);
        return insertMod.modify(input);
    }

    public byte[] getPrependPayload() {
        return prependPayload;
    }

    public void setPrependPayload(byte[] prependPayload) {
        this.prependPayload = prependPayload;
    }

    public byte[] getPayload() {
        return payload;
    }

    public void setPayload(byte[] payload) {
        this.payload = payload;
    }

    public byte[] getAppendPayload() {
        return appendPayload;
    }

    public void setAppendPayload(byte[] appendPayload) {
        this.appendPayload = appendPayload;
    }

    public boolean isInsert() {
        return insert;
    }

    public void setInsert(boolean insert) {
        this.insert = insert;
    }

    public int getInsertPosition() {
        return insertPosition;
    }

    public void setInsertPosition(int insertPosition) {
        this.insertPosition = insertPosition;
    }

    public byte[] getCompletePayload() {
        return ArrayConverter.concatenate(prependPayload, payload, appendPayload);
    }

    @Override
    public VariableModification<byte[]> getModifiedCopy() {
        ByteArrayPayloadModification mod = new ByteArrayPayloadModification(payload);
        mod.setAppendPayload(appendPayload);
        mod.setPrependPayload(prependPayload);

        if (this.insert) {
            mod.setInsert(true);
            mod.setInsertPosition(insertPosition);
            return mod;
        }

        return mod;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Arrays.hashCode(this.prependPayload);
        hash = 59 * hash + Arrays.hashCode(this.payload);
        hash = 59 * hash + Arrays.hashCode(this.appendPayload);
        hash = 59 * hash + (this.insert ? 1 : 0);
        hash = 59 * hash + this.insertPosition;
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
        final ByteArrayPayloadModification other = (ByteArrayPayloadModification) obj;
        if (this.insert != other.insert) {
            return false;
        }
        if (this.insertPosition != other.insertPosition) {
            return false;
        }
        if (!Arrays.equals(this.prependPayload, other.prependPayload)) {
            return false;
        }
        if (!Arrays.equals(this.payload, other.payload)) {
            return false;
        }
        if (!Arrays.equals(this.appendPayload, other.appendPayload)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ByteArrayPayloadModification{" + "prependPayload=" + ArrayConverter.bytesToHexString(prependPayload)
            + ", payload=" + ArrayConverter.bytesToHexString(payload) + ", appendPayload="
            + ArrayConverter.bytesToHexString(appendPayload) + ", insert=" + insert + ", insertPosition="
            + insertPosition + '}';
    }

}
