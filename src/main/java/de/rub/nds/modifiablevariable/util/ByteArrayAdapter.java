/**
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Copyright 2014-2017 Ruhr University Bochum / Hackmanit GmbH
 *
 * Licensed under Apache License 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.util;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 *
 * @author Juraj Somorovsky - juraj.somorovsky@rub.de
 */
public class ByteArrayAdapter extends XmlAdapter<ByteArrayXmlType, byte[]> {

    @Override
    public byte[] unmarshal(ByteArrayXmlType xmlType) throws Exception {
        StringBuilder sb = new StringBuilder();
        for (String s : xmlType.getBytes()) {
            sb.append(s);
        }
        String value = sb.toString().replaceAll("\\s", "");
        return ArrayConverter.hexStringToByteArray(value);
    }

    @Override
    public ByteArrayXmlType marshal(byte[] value) throws Exception {
        if (value == null) {
            return null;
        }

        String byteString = ArrayConverter.bytesToHexString(value, true, false);
        ByteArrayXmlType xmlType = new ByteArrayXmlType();

        BufferedReader br = new BufferedReader(new StringReader(byteString));
        List<String> byteList = new ArrayList<>();
        for (String line = br.readLine(); line != null; line = br.readLine()) {
            byteList.add(line);
        }
        br.close();

        xmlType.setBytes(byteList);
        return xmlType;
    }
}
