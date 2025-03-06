/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for fields that contain ModifiableVariable instances or ModifiableVariableHolder
 * objects.
 *
 * <p>This annotation marks fields within a class that reference modifiable variables or objects
 * that contain modifiable variables. It is used for reflection-based discovery and manipulation of
 * modifiable variables throughout a complex object hierarchy.
 *
 * <p>By marking fields with this annotation, the framework can identify which fields should be
 * included in operations like:
 *
 * <ul>
 *   <li>Variable discovery
 *   <li>Recursive manipulation
 *   <li>Assertion validation
 *   <li>Serialization/deserialization
 * </ul>
 *
 * <p>For example, in a TLS protocol implementation, protocol message classes might contain fields
 * that represent various protocol fields. By annotating these fields, the framework can
 * automatically discover and manipulate them during testing.
 *
 * <p>This annotation is retained at runtime and can only be applied to fields.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface HoldsModifiableVariable {}
