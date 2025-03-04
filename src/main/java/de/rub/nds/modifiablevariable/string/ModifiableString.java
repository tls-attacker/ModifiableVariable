/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.string;

import static de.rub.nds.modifiablevariable.util.StringUtil.backslashEscapeString;

import de.rub.nds.modifiablevariable.ModifiableVariable;
import de.rub.nds.modifiablevariable.util.IllegalStringAdapter;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.nio.charset.StandardCharsets;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class ModifiableString extends ModifiableVariable<String> {

    protected String originalValue;

    public ModifiableString() {
        super();
    }

    public ModifiableString(String originalValue) {
        super();
        this.originalValue = originalValue;
    }

    public ModifiableString(ModifiableString other) {
        super(other);
        originalValue = other.originalValue;
    }

    @Override
    public ModifiableString createCopy() {
        return new ModifiableString(this);
    }

    @XmlJavaTypeAdapter(IllegalStringAdapter.class)
    public String getAssertEquals() {
        return assertEquals;
    }

    public void setAssertEquals(String assertEquals) {
        this.assertEquals = assertEquals;
    }

    @Override
    public boolean isOriginalValueModified() {
        return originalValue != null && originalValue.compareTo(getValue()) != 0;
    }

    public byte[] getByteArray(int size) {
        return getValue().getBytes(StandardCharsets.ISO_8859_1);
    }

    @Override
    public boolean validateAssertions() {
        boolean valid = true;
        if (assertEquals != null) {
            if (assertEquals.compareTo(getValue()) != 0) {
                valid = false;
            }
        }
        return valid;
    }

    @Override
    @XmlJavaTypeAdapter(IllegalStringAdapter.class)
    public String getOriginalValue() {
        return originalValue;
    }

    @Override
    public void setOriginalValue(String originalValue) {
        this.originalValue = originalValue;
    }

    @Override
    public String toString() {
        return "ModifiableString{"
                + "originalValue='"
                + backslashEscapeString(originalValue)
                + '\''
                + innerToString()
                + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ModifiableString that)) {
            return false;
        }

        return getValue() != null ? getValue().equals(that.getValue()) : that.getValue() == null;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (getValue() != null ? getValue().hashCode() : 0);
        return result;
    }
}
