/**
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Copyright 2014-2023 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 *
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.modifiablevariable.util;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 *
 */
public class UnformattedByteArrayAdapter extends XmlAdapter<String, byte[]> {

    @Override
    public byte[] unmarshal(String value) {

        value = value.replaceAll("\\s", "");
        return ArrayConverter.hexStringToByteArray(value);
    }

    @Override
    public String marshal(byte[] value) {
        return ArrayConverter.bytesToHexString(value, false, false);
    }
}
