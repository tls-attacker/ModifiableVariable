/**
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Copyright 2014-2022 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 *
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.modifiablevariable.util;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import org.apache.commons.text.StringEscapeUtils;

/**
 *
 */
public class IllegalStringAdapter extends XmlAdapter<String, String> {

    @Override
    public String unmarshal(String value) {
        return StringEscapeUtils.unescapeJava(value);
    }

    @Override
    public String marshal(String value) {
        return StringEscapeUtils.escapeJava(value);
    }
}
