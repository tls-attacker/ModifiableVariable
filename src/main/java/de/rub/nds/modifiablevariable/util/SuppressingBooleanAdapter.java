/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.util;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import java.util.Objects;

public abstract class SuppressingBooleanAdapter extends XmlAdapter<String, Boolean> {

    public abstract Boolean getValueToSuppress();

    @Override
    public Boolean unmarshal(String v) {
        if (v == null) {
            return getValueToSuppress();
        } else {
            return Boolean.parseBoolean(v);
        }
    }

    @Override
    public String marshal(Boolean v) {
        if (Objects.equals(v, getValueToSuppress()) || v == null) {
            return null;
        }
        return v.toString();
    }
}
