/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.singlebyte;

import de.rub.nds.modifiablevariable.VariableModification;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@XmlRootElement
public class ByteXorModification extends VariableModification<Byte> {

    private static final int MAX_XOR_MODIFIER = 16;

    private Byte xor;

    public ByteXorModification() {
        super();
    }

    public ByteXorModification(Byte xor) {
        super();
        this.xor = xor;
    }

    public ByteXorModification(ByteXorModification other) {
        super(other);
        xor = other.xor;
    }

    @Override
    public ByteXorModification createCopy() {
        return new ByteXorModification(this);
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
        ByteXorModification other = (ByteXorModification) obj;
        return Objects.equals(xor, other.xor);
    }

    @Override
    public String toString() {
        return "ByteXorModification{" + "xor=" + xor + '}';
    }
}
