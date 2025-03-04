/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.util;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

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
