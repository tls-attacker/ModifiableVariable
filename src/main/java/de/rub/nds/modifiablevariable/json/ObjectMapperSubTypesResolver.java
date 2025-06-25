/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.json;

import com.fasterxml.classmate.ResolvedType;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import com.fasterxml.jackson.databind.introspect.AnnotatedClassResolver;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.victools.jsonschema.generator.CustomDefinition;
import com.github.victools.jsonschema.generator.CustomDefinitionProviderV2;
import com.github.victools.jsonschema.generator.FieldScope;
import com.github.victools.jsonschema.generator.MethodScope;
import com.github.victools.jsonschema.generator.SchemaGenerationContext;
import com.github.victools.jsonschema.generator.SchemaKeyword;
import com.github.victools.jsonschema.generator.SubtypeResolver;
import com.github.victools.jsonschema.generator.TypeContext;
import com.github.victools.jsonschema.generator.TypeScope;
import com.github.victools.jsonschema.generator.impl.AttributeCollector;
import com.github.victools.jsonschema.module.jackson.JacksonOption;
import com.github.victools.jsonschema.module.jackson.JsonIdentityReferenceDefinitionProvider;
import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;
import org.apache.commons.lang3.NotImplementedException;

/**
 * Look-up of subtypes from a {@link ObjectMapper} instance. Based on {@link
 * com.github.victools.jsonschema.module.jackson.JsonSubTypesResolver}.
 */
public class ObjectMapperSubTypesResolver implements SubtypeResolver, CustomDefinitionProviderV2 {

    private final CustomDefinition.DefinitionType wrappingSubtypeDefinitionType;
    private final boolean shouldInlineNestedSubtypes;
    private final Optional<JsonIdentityReferenceDefinitionProvider> identityReferenceProvider;
    private final ObjectMapper objectMapper;

    /**
     * Constructor expecting list of enabled module options. <br>
     * Currently, only the {@link JacksonOption#ALWAYS_REF_SUBTYPES} is considered here. Other
     * relevant options are handled by the module class.
     *
     * @param options module options to derive differing behavior from
     */
    public ObjectMapperSubTypesResolver(
            ObjectMapper objectMapper, Collection<JacksonOption> options) {
        this.objectMapper = objectMapper;
        this.wrappingSubtypeDefinitionType =
                options.contains(JacksonOption.ALWAYS_REF_SUBTYPES)
                        ? CustomDefinition.DefinitionType.ALWAYS_REF
                        : CustomDefinition.DefinitionType.STANDARD;
        this.shouldInlineNestedSubtypes =
                options.contains(JacksonOption.INLINE_TRANSFORMED_SUBTYPES);
        if (options.contains(JacksonOption.JSONIDENTITY_REFERENCE_ALWAYS_AS_ID)) {
            this.identityReferenceProvider =
                    Optional.of(new JsonIdentityReferenceDefinitionProvider());
        } else {
            this.identityReferenceProvider = Optional.empty();
        }
    }

    /**
     * Check whether to skip the subtype handling for a particular type (e.g. when {@code
     * JacksonOption.JSONIDENTITY_REFERENCE_ALWAYS_AS_ID} applies instead).
     *
     * @param declaredType type for which a potential subtype should be resolved (or not)
     * @param context applicable type context, that offers convenience methods, e.g., for the
     *     annotation look-up
     * @return whether to skip the subtype resolution for the given type
     */
    private boolean skipSubtypeResolution(ResolvedType declaredType, TypeContext context) {
        return this.identityReferenceProvider
                .flatMap(
                        idRefProvider ->
                                idRefProvider.getIdentityReferenceType(declaredType, context))
                .isPresent();
    }

    /*
     * Looking-up declared subtypes for encountered supertype in general.
     */
    @Override
    public List<ResolvedType> findSubtypes(
            ResolvedType declaredType, SchemaGenerationContext context) {
        if (this.skipSubtypeResolution(declaredType, context.getTypeContext())) {
            return null;
        }

        // Check whether the type has a {@link JsonTypeInfo} annotation.
        TypeContext typeContext = context.getTypeContext();
        ResolvedType typeWithTypeInfo =
                typeContext.getTypeWithAnnotation(
                        declaredType,
                        JsonTypeInfo.class,
                        ReflectiveJacksonSchemaModule.NESTED_ANNOTATION_CHECK);
        if (!declaredType.equals(typeWithTypeInfo)) {
            return null;
        }

        AnnotatedClass annotatedClass =
                AnnotatedClassResolver.resolveWithoutSuperTypes(
                        objectMapper.getDeserializationConfig(), declaredType.getErasedType());
        Collection<NamedType> registeredSubtypes =
                objectMapper
                        .getSubtypeResolver()
                        .collectAndResolveSubtypesByTypeId(
                                objectMapper.getDeserializationConfig(), annotatedClass);

        return registeredSubtypes.stream()
                .map(NamedType::getType)
                .map(typeContext::resolve)
                // Do not include the declared type itself in the subtype list
                .filter(resolvedType -> !resolvedType.equals(declaredType))
                // Do not resolve to interfaces or abstract types
                .filter(
                        resolvedType ->
                                !resolvedType.getErasedType().isInterface()
                                        && !Modifier.isAbstract(
                                                resolvedType.getErasedType().getModifiers()))
                // Do not include subtypes that do not have the same type bindings as the declared
                // type
                .filter(
                        resolvedType ->
                                filterSubtypesWithMatchingTypeBindings(declaredType, resolvedType))
                .sorted(Comparator.comparing(a -> a.getErasedType().getName()))
                .collect(Collectors.toList());
    }

    /**
     * Filter subtypes to ensure that they have the same type bindings as the declared type.
     *
     * @param declaredType the supertype for which subtypes are being resolved
     * @param resolvedType the resolved subtype to check against the declared type
     * @return true if the subtype has the same type bindings as the declared type, false otherwise
     */
    public boolean filterSubtypesWithMatchingTypeBindings(
            ResolvedType declaredType, ResolvedType resolvedType) {
        List<ResolvedType> resolvedTypeParameters =
                resolvedType.typeParametersFor(declaredType.getErasedType());
        if (resolvedTypeParameters == null) {
            // If the resolved type does not have the declared type as a parent, return false
            return false;
        }
        return declaredType.getTypeBindings().isEmpty()
                || resolvedTypeParameters.equals(
                        declaredType.getTypeBindings().getTypeParameters());
    }

    /*
     * Providing custom schema definition for subtype.
     */
    @Override
    public CustomDefinition provideCustomSchemaDefinition(
            ResolvedType javaType, SchemaGenerationContext context) {
        if (javaType == null) {
            // since 4.37.0: not for void methods
            return null;
        }
        final TypeContext typeContext = context.getTypeContext();
        ResolvedType typeWithTypeInfo =
                typeContext.getTypeWithAnnotation(
                        javaType,
                        JsonTypeInfo.class,
                        ReflectiveJacksonSchemaModule.NESTED_ANNOTATION_CHECK);
        if (typeWithTypeInfo == null
                || javaType.equals(typeWithTypeInfo)
                || this.skipSubtypeResolution(javaType, typeContext)) {
            // no @JsonTypeInfo annotation found or the given javaType is the super type, that
            // should be replaced
            return null;
        }
        Class<?> erasedTypeWithTypeInfo = typeWithTypeInfo.getErasedType();
        final List<Annotation> annotationsList =
                Arrays.asList(erasedTypeWithTypeInfo.getAnnotations());
        JsonTypeInfo typeInfoAnnotation =
                typeContext.getAnnotationFromList(
                        JsonTypeInfo.class,
                        annotationsList,
                        ReflectiveJacksonSchemaModule.NESTED_ANNOTATION_CHECK);
        TypeScope scope = typeContext.createTypeScope(javaType);
        ObjectNode definition = this.createSubtypeDefinition(scope, typeInfoAnnotation, context);
        if (definition == null) {
            return null;
        }
        return new CustomDefinition(
                definition,
                this.wrappingSubtypeDefinitionType,
                CustomDefinition.AttributeInclusion.NO);
    }

    /**
     * Determine the appropriate type identifier according to {@link JsonTypeInfo#use()}.
     *
     * @param javaType specific subtype to identify
     * @param typeInfoAnnotation annotation for determining what kind of identifier to use
     * @return type identifier (or {@code null} if no supported value could be found)
     */
    private String getTypeIdentifier(ResolvedType javaType, JsonTypeInfo typeInfoAnnotation) {
        Class<?> erasedTargetType = javaType.getErasedType();
        // Retrieve corresponding NamedType from the subtype resolver in the Jackson object mapper
        return switch (typeInfoAnnotation.use()) {
            case NAME ->
                    // TODO: Implement lookup of JsonTypeInfo.Id.NAME from object mapper
                    throw new NotImplementedException(
                            "Lookup of JsonTypeInfo.Id.NAME from object mapper is not yet supported");
            case SIMPLE_NAME -> erasedTargetType.getSimpleName();
            case CLASS -> erasedTargetType.getName();
            default -> null;
        };
    }

    /**
     * Create the custom schema definition for the given subtype, considering the {@link
     * JsonTypeInfo#include()} setting.
     *
     * @param scope targeted subtype
     * @param typeInfoAnnotation annotation for looking up the type identifier and determining the
     *     kind of inclusion/serialization
     * @param context generation context
     * @return created custom definition (or {@code null} if no supported subtype resolution
     *     scenario could be detected
     */
    private ObjectNode createSubtypeDefinition(
            TypeScope scope, JsonTypeInfo typeInfoAnnotation, SchemaGenerationContext context) {
        ResolvedType javaType = scope.getType();
        final String typeIdentifier = this.getTypeIdentifier(javaType, typeInfoAnnotation);
        if (typeIdentifier == null) {
            return null;
        }
        ObjectNode attributesToInclude = this.getAttributesToInclude(scope, context);
        final ObjectNode definition = context.getGeneratorConfig().createObjectNode();
        SubtypeDefinitionDetails subtypeDetails =
                new SubtypeDefinitionDetails(
                        javaType, attributesToInclude, context, typeIdentifier, definition);
        switch (typeInfoAnnotation.include()) {
            case WRAPPER_ARRAY:
                createSubtypeDefinitionForWrapperArrayTypeInfo(subtypeDetails);
                break;
            case WRAPPER_OBJECT:
                this.createSubtypeDefinitionForWrapperObjectTypeInfo(subtypeDetails);
                break;
            case PROPERTY:
            case EXISTING_PROPERTY:
                this.createSubtypeDefinitionForPropertyTypeInfo(subtypeDetails, typeInfoAnnotation);
                break;
            default:
                return null;
        }
        return definition;
    }

    private void createSubtypeDefinitionForWrapperArrayTypeInfo(SubtypeDefinitionDetails details) {
        details.getDefinition()
                .put(
                        details.getKeyword(SchemaKeyword.TAG_TYPE),
                        details.getKeyword(SchemaKeyword.TAG_TYPE_ARRAY));
        ArrayNode itemsArray =
                details.getDefinition()
                        .withArray(details.getKeyword(SchemaKeyword.TAG_PREFIX_ITEMS));
        itemsArray
                .addObject()
                .put(
                        details.getKeyword(SchemaKeyword.TAG_TYPE),
                        details.getKeyword(SchemaKeyword.TAG_TYPE_STRING))
                .put(details.getKeyword(SchemaKeyword.TAG_CONST), details.getTypeIdentifier());
        if (details.getAttributesToInclude() == null
                || details.getAttributesToInclude().isEmpty()) {
            itemsArray.add(
                    this.createNestedSubtypeSchema(details.getJavaType(), details.getContext()));
        } else {
            itemsArray
                    .addObject()
                    .withArray(details.getKeyword(SchemaKeyword.TAG_ALLOF))
                    .add(
                            this.createNestedSubtypeSchema(
                                    details.getJavaType(), details.getContext()))
                    .add(details.getAttributesToInclude());
        }
    }

    private void createSubtypeDefinitionForWrapperObjectTypeInfo(SubtypeDefinitionDetails details) {
        details.getDefinition()
                .put(
                        details.getKeyword(SchemaKeyword.TAG_TYPE),
                        details.getKeyword(SchemaKeyword.TAG_TYPE_OBJECT));
        ObjectNode propertiesNode =
                details.getDefinition().putObject(details.getKeyword(SchemaKeyword.TAG_PROPERTIES));
        ObjectNode nestedSubtypeSchema =
                this.createNestedSubtypeSchema(details.getJavaType(), details.getContext());
        if (details.getAttributesToInclude() == null
                || details.getAttributesToInclude().isEmpty()) {
            propertiesNode.set(details.getTypeIdentifier(), nestedSubtypeSchema);
        } else {
            propertiesNode
                    .putObject(details.getTypeIdentifier())
                    .withArray(details.getKeyword(SchemaKeyword.TAG_ALLOF))
                    .add(nestedSubtypeSchema)
                    .add(details.getAttributesToInclude());
        }
        details.getDefinition()
                .withArray(details.getKeyword(SchemaKeyword.TAG_REQUIRED))
                .add(details.getTypeIdentifier());
    }

    private void createSubtypeDefinitionForPropertyTypeInfo(
            SubtypeDefinitionDetails details, JsonTypeInfo typeInfoAnnotation) {
        final String propertyName =
                Optional.ofNullable(typeInfoAnnotation.property())
                        .filter(name -> !name.isEmpty())
                        .orElseGet(() -> typeInfoAnnotation.use().getDefaultPropertyName());
        ObjectNode additionalPart =
                details.getDefinition()
                        .withArray(details.getKeyword(SchemaKeyword.TAG_ALLOF))
                        .add(
                                this.createNestedSubtypeSchema(
                                        details.getJavaType(), details.getContext()))
                        .addObject();
        if (details.getAttributesToInclude() != null
                && !details.getAttributesToInclude().isEmpty()) {
            additionalPart.setAll(details.getAttributesToInclude());
        }
        additionalPart
                .put(
                        details.getKeyword(SchemaKeyword.TAG_TYPE),
                        details.getKeyword(SchemaKeyword.TAG_TYPE_OBJECT))
                .putObject(details.getKeyword(SchemaKeyword.TAG_PROPERTIES))
                .putObject(propertyName)
                .put(details.getKeyword(SchemaKeyword.TAG_CONST), details.getTypeIdentifier());
        if (!details.getJavaType().getErasedType().equals(typeInfoAnnotation.defaultImpl())) {
            additionalPart
                    .withArray(details.getKeyword(SchemaKeyword.TAG_REQUIRED))
                    .add(propertyName);
        }
    }

    private ObjectNode createNestedSubtypeSchema(
            ResolvedType javaType, SchemaGenerationContext context) {
        if (this.shouldInlineNestedSubtypes) {
            return context.createStandardDefinition(javaType, this);
        }
        return context.createStandardDefinitionReference(javaType, this);
    }

    private ObjectNode getAttributesToInclude(TypeScope scope, SchemaGenerationContext context) {
        ObjectNode attributesToInclude;
        if (scope instanceof FieldScope) {
            attributesToInclude =
                    AttributeCollector.collectFieldAttributes((FieldScope) scope, context);
        } else if (scope instanceof MethodScope) {
            attributesToInclude =
                    AttributeCollector.collectMethodAttributes((MethodScope) scope, context);
        } else {
            attributesToInclude = null;
        }
        return attributesToInclude;
    }

    private static class SubtypeDefinitionDetails {
        private final ResolvedType javaType;
        private final ObjectNode attributesToInclude;
        private final SchemaGenerationContext context;
        private final String typeIdentifier;
        private final ObjectNode definition;

        SubtypeDefinitionDetails(
                ResolvedType javaType,
                ObjectNode attributesToInclude,
                SchemaGenerationContext context,
                String typeIdentifier,
                ObjectNode definition) {
            this.javaType = javaType;
            this.attributesToInclude = attributesToInclude;
            this.context = context;
            this.typeIdentifier = typeIdentifier;
            this.definition = definition;
        }

        ResolvedType getJavaType() {
            return this.javaType;
        }

        ObjectNode getAttributesToInclude() {
            return this.attributesToInclude;
        }

        SchemaGenerationContext getContext() {
            return this.context;
        }

        String getTypeIdentifier() {
            return this.typeIdentifier;
        }

        ObjectNode getDefinition() {
            return this.definition;
        }

        String getKeyword(SchemaKeyword keyword) {
            return this.context.getKeyword(keyword);
        }
    }
}
