/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;

/**
 * A Jackson module for the ModifiableVariable library. It registers serializers and deserializers
 * required for modifiable variables. Make sure to include it using {@code
 * ObjectMapper.registerModule(new ModifiableVariableModule())} in your Jackson configuration before
 * serializing or deserializing modifiable variables.
 */
public class ModifiableVariableModule extends SimpleModule {

    private static final String MODULE_NAME = "ModifiableVariableModule";

    /** Default constructor that sets the module name and version. */
    public ModifiableVariableModule() {
        super(MODULE_NAME, new Version(1, 0, 0, null, "de.rub.nds", "modifiable-variable"));
        addSerializer(byte[].class, new UnformattedByteArraySerializer());
        addDeserializer(byte[].class, new UnformattedByteArrayDeserializer());
    }

    public static class UnformattedByteArraySerializer extends StdSerializer<byte[]> {
        public UnformattedByteArraySerializer() {
            super(byte[].class);
        }

        @Override
        public void serialize(byte[] value, JsonGenerator gen, SerializerProvider serializers)
                throws IOException {
            gen.writeString(ArrayConverter.bytesToRawHexString(value));
        }
    }

    public static class UnformattedByteArrayDeserializer extends StdDeserializer<byte[]> {
        public UnformattedByteArrayDeserializer() {
            super(byte[].class);
        }

        @Override
        public byte[] deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            return ArrayConverter.hexStringToByteArray(p.getText());
        }
    }
}
