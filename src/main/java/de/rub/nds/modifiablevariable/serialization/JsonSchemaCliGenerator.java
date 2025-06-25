/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.serialization;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.victools.jsonschema.generator.*;
import java.io.File;
import java.lang.reflect.InvocationTargetException;

public class JsonSchemaCliGenerator {

    /**
     * A command line tool to generate a JSON schema for a given class using Jackson and victools.
     *
     * @param args args[0] is the fully qualified class name for which to generate the schema,
     *     args[1] is the output file path, and any additional arguments are class names of
     *     additional Jackson modules to register.
     */
    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(ModifiableVariableModule.getFieldVisibilityChecker());

        // Register additional modules (if any) passed as command line arguments
        for (int i = 2; i < args.length; i++) {
            try {
                mapper.registerModule(
                        (Module) Class.forName(args[i]).getConstructor().newInstance());
            } catch (InstantiationException
                    | IllegalAccessException
                    | InvocationTargetException
                    | NoSuchMethodException
                    | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        // Prepare the schema generator
        SchemaGeneratorConfigBuilder builder =
                new SchemaGeneratorConfigBuilder(
                        mapper, SchemaVersion.DRAFT_2020_12, OptionPreset.PLAIN_JSON);
        builder.with(new ReflectiveJacksonSchemaModule());
        SchemaGeneratorConfig config = builder.build();
        SchemaGenerator generator = new SchemaGenerator(config);
        JsonNode jsonSchema;

        // Generate the schema for the specified class
        try {
            jsonSchema = generator.generateSchema(Class.forName(args[0]));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        // Output the generated schema to the specified file
        File outputFile = new File(args[1]);
        //noinspection AssertWithSideEffects
        assert outputFile.exists() || outputFile.mkdirs();
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(outputFile, jsonSchema);
        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to write JSON schema to file: " + outputFile.getAbsolutePath(), e);
        }
    }
}
