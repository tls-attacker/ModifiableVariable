/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.singlebyte;

import de.rub.nds.modifiablevariable.VariableModification;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.Objects;
import java.util.Random;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ByteXorModification extends VariableModification<Byte> {

    private static final int MAX_XOR_MODIFIER = 16;

    private Byte xor;

    public ByteXorModification() {}

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
        hash = 31 * hash + xor;
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
        return Objects.equals(this.xor, other.xor);
    }
}
