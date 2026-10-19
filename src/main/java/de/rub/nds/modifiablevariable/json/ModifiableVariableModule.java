/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import de.rub.nds.modifiablevariable.ModifiableVariable;
import de.rub.nds.modifiablevariable.VariableModification;
import de.rub.nds.modifiablevariable.util.DataConverter;
import org.reflections.Reflections;
import tools.jackson.core.JsonGenerator;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.deser.std.StdDeserializer;
import tools.jackson.databind.introspect.VisibilityChecker;
import tools.jackson.databind.module.SimpleModule;
import tools.jackson.databind.ser.std.StdSerializer;

/**
 * A Jackson module for the ModifiableVariable library. It registers serializers and deserializers
 * required for modifiable variables. Make sure to include it using {@code
 * JsonMapper.builder().addModule(new ModifiableVariableModule())} in your Jackson configuration
 * before serializing or deserializing modifiable variables.
 */
public class ModifiableVariableModule extends SimpleModule {

    private static final String MODULE_NAME = "ModifiableVariableModule";

    /** Default constructor that sets the module name and version. */
    public ModifiableVariableModule() {
        super(MODULE_NAME);
        // Serializers
        addSerializer(byte[].class, new UnformattedByteArraySerializer());
        // Deserializers
        addDeserializer(byte[].class, new UnformattedByteArrayDeserializer());
        // Subtypes
        Reflections reflections = new Reflections("de.rub.nds.modifiablevariable");
        reflections.getSubTypesOf(ModifiableVariable.class).forEach(this::registerSubtypes);
        reflections.getSubTypesOf(VariableModification.class).forEach(this::registerSubtypes);
    }

    public static class UnformattedByteArraySerializer extends StdSerializer<byte[]> {
        public UnformattedByteArraySerializer() {
            super(byte[].class);
        }

        @Override
        public void serialize(byte[] value, JsonGenerator gen, SerializationContext serializers) {
            gen.writeString(DataConverter.bytesToRawHexString(value));
        }
    }

    public static class UnformattedByteArrayDeserializer extends StdDeserializer<byte[]> {
        public UnformattedByteArrayDeserializer() {
            super(byte[].class);
        }

        @Override
        public byte[] deserialize(JsonParser p, DeserializationContext ctxt) {
            return DataConverter.hexStringToByteArray(p.getString());
        }
    }

    public static VisibilityChecker getFieldVisibilityChecker() {
        return VisibilityChecker.defaultInstance()
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withIsGetterVisibility(JsonAutoDetect.Visibility.NONE);
    }
}
