/**
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Copyright 2014-2017 Ruhr University Bochum / Hackmanit GmbH
 *
 * Licensed under Apache License 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.singlebyte;

import de.rub.nds.modifiablevariable.VariableModification;
import java.util.Objects;
import java.util.Random;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(propOrder = { "xor", "modificationFilter", "postModification" })
public class ByteXorModification extends VariableModification<Byte> {

    private final static int MAX_XOR_MODIFIER = 16;

    private Byte xor;

    public ByteXorModification() {

    }

    public ByteXorModification(Byte bi) {
        this.xor = bi;
    }

    @Override
    protected Byte modifyImplementationHook(Byte input) {
        if (input == null) {
            input = 0;
        }
        return (byte) (input ^ xor);
    }

    public Byte getXor() {
        return xor;
    }

    public void setXor(Byte xor) {
        this.xor = xor;
    }

    @Override
    public VariableModification<Byte> getModifiedCopy() {
        Random r = new Random();
        if (r.nextBoolean()) {
            return new ByteXorModification((byte) (xor + r.nextInt(MAX_XOR_MODIFIER)));
        } else {
            return new ByteXorModification((byte) (xor - r.nextInt(MAX_XOR_MODIFIER)));
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.xor);
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
        final ByteXorModification other = (ByteXorModification) obj;
        if (!Objects.equals(this.xor, other.xor)) {
            return false;
        }
        return true;
    }
}
