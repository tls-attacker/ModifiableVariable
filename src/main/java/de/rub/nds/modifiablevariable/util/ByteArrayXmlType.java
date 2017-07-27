/*
 * Copyright 2017 Lucas Hartmann <lucas.hartmann@rub.de>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.rub.nds.modifiablevariable.util;

import java.util.List;
import java.util.Objects;

/**
 * Used to let JAXB generate prettier XML output for byte arrays.
 */
public class ByteArrayXmlType {

    protected List<String> bytes = null;

    public ByteArrayXmlType() {
    }

    public List<String> getBytes() {
        return bytes;
    }

    public void setBytes(List<String> byteList) {
        bytes = byteList;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.bytes);
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
        final ByteArrayXmlType other = (ByteArrayXmlType) obj;
        if (!Objects.equals(this.bytes, other.bytes)) {
            return false;
        }
        return true;
    }
}
