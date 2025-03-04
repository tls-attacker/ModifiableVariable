# CLAUDE.md - ModifiableVariable Project Guide

## Build Commands

- Build: `mvn clean install`
- Run all tests: `mvn test`
- Run single test: `mvn test -Dtest=ClassName#methodName` (e.g., `mvn test -Dtest=ModifiableVariableTest#testRandomBigIntegerModification`)
- Run with code coverage: `mvn clean install -P coverage`
- Format code: `mvn spotless:apply`

## Code Style

- Uses Google Java Style (AOSP variant) for formatting
- Indentation: 4 spaces (no tabs)
- Line endings: GIT_ATTRIBUTES
- License header required on all files

## Imports

- Unused imports should be removed
- Import order is managed by spotless

## Type System

- Java 21 is required
- Generic types should be properly specified (e.g., `ModifiableVariable<E>`)
- JAXB annotations used for XML serialization

## Naming Conventions

- CamelCase for class names
- camelCase for methods and variables
- Classes implementing modifications follow pattern: `[Type][Operation]Modification`
- Factory classes follow pattern: `[Type]ModificationFactory`

## Error Handling

- Proper exception handling with appropriate exception types
- FileConfigurationException for configuration-related errors

