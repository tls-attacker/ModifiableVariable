/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable;

import static org.junit.jupiter.api.Assertions.*;

import de.rub.nds.modifiablevariable.ModifiableVariableProperty.Encoding;
import de.rub.nds.modifiablevariable.ModifiableVariableProperty.Purpose;
import de.rub.nds.modifiablevariable.integer.ModifiableInteger;
import de.rub.nds.modifiablevariable.util.ModifiableVariableAnalyzer;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class ModifiableVariablePropertyTest {

    // Test class with annotated fields
    private static class TestClass {
        @ModifiableVariableProperty(purpose = Purpose.LENGTH, minLength = 1, maxLength = 4)
        private ModifiableInteger length;

        @ModifiableVariableProperty(purpose = Purpose.COUNT, expectedLength = 2)
        private ModifiableInteger count;

        @ModifiableVariableProperty(purpose = Purpose.SIGNATURE, encoding = Encoding.ASN1_DER)
        private ModifiableInteger signatureWithEncoding;

        @ModifiableVariableProperty private ModifiableInteger defaultProperty;

        @ModifiableVariableProperty(
                purpose = Purpose.KEY_MATERIAL,
                encoding = Encoding.X509,
                description = "Server's public key for ECDH",
                minLength = 32,
                maxLength = 65)
        private ModifiableInteger enhancedProperty;

        @ModifiableVariableProperty(purpose = Purpose.CONSTANT, expectedLength = 2)
        private ModifiableInteger protocolVersion;

        @ModifiableVariableProperty(purpose = Purpose.RANDOM, expectedLength = 32)
        private ModifiableInteger randomValue;

        @ModifiableVariableProperty(purpose = Purpose.PADDING, minLength = 0, maxLength = 255)
        private ModifiableInteger paddingLength;

        // Unannotated ModifiableVariable field for testing
        private ModifiableInteger unannotatedField;
    }

    @Test
    public void testLengthProperty() throws NoSuchFieldException {
        Field lengthField = TestClass.class.getDeclaredField("length");
        ModifiableVariableProperty annotation =
                lengthField.getAnnotation(ModifiableVariableProperty.class);

        assertNotNull(annotation);
        assertEquals(Purpose.LENGTH, annotation.purpose());
        assertEquals(Encoding.NONE, annotation.encoding());
        assertEquals(1, annotation.minLength());
        assertEquals(4, annotation.maxLength());
        assertEquals(-1, annotation.expectedLength());
    }

    @Test
    public void testCountProperty() throws NoSuchFieldException {
        Field countField = TestClass.class.getDeclaredField("count");
        ModifiableVariableProperty annotation =
                countField.getAnnotation(ModifiableVariableProperty.class);

        assertNotNull(annotation);
        assertEquals(Purpose.COUNT, annotation.purpose());
        assertEquals(Encoding.NONE, annotation.encoding());
        assertEquals(-1, annotation.minLength());
        assertEquals(-1, annotation.maxLength());
        assertEquals(2, annotation.expectedLength());
    }

    @Test
    public void testSignatureWithEncodingProperty() throws NoSuchFieldException {
        Field signatureField = TestClass.class.getDeclaredField("signatureWithEncoding");
        ModifiableVariableProperty annotation =
                signatureField.getAnnotation(ModifiableVariableProperty.class);

        assertNotNull(annotation);
        assertEquals(Purpose.SIGNATURE, annotation.purpose());
        assertEquals(Encoding.ASN1_DER, annotation.encoding());
    }

    @Test
    public void testDefaultProperty() throws NoSuchFieldException {
        Field defaultField = TestClass.class.getDeclaredField("defaultProperty");
        ModifiableVariableProperty annotation =
                defaultField.getAnnotation(ModifiableVariableProperty.class);

        assertNotNull(annotation);
        assertEquals(Purpose.NONE, annotation.purpose());
        assertEquals(Encoding.NONE, annotation.encoding());
        assertEquals(-1, annotation.minLength());
        assertEquals(-1, annotation.maxLength());
        assertEquals(-1, annotation.expectedLength());
    }

    @Test
    public void testEnhancedProperty() throws NoSuchFieldException {
        Field enhancedField = TestClass.class.getDeclaredField("enhancedProperty");
        ModifiableVariableProperty annotation =
                enhancedField.getAnnotation(ModifiableVariableProperty.class);

        assertNotNull(annotation);
        assertEquals(Purpose.KEY_MATERIAL, annotation.purpose());
        assertEquals(Encoding.X509, annotation.encoding());
        assertEquals("Server's public key for ECDH", annotation.description());
        assertEquals(32, annotation.minLength());
        assertEquals(65, annotation.maxLength());
        assertEquals(-1, annotation.expectedLength());
    }

    @Test
    public void testNewPurposeEnums() throws NoSuchFieldException {
        Field versionField = TestClass.class.getDeclaredField("protocolVersion");
        ModifiableVariableProperty versionAnnotation =
                versionField.getAnnotation(ModifiableVariableProperty.class);

        assertNotNull(versionAnnotation);
        assertEquals(Purpose.CONSTANT, versionAnnotation.purpose());
        assertEquals(2, versionAnnotation.expectedLength());

        Field randomField = TestClass.class.getDeclaredField("randomValue");
        ModifiableVariableProperty randomAnnotation =
                randomField.getAnnotation(ModifiableVariableProperty.class);

        assertNotNull(randomAnnotation);
        assertEquals(Purpose.RANDOM, randomAnnotation.purpose());
        assertEquals(32, randomAnnotation.expectedLength());

        Field paddingField = TestClass.class.getDeclaredField("paddingLength");
        ModifiableVariableProperty paddingAnnotation =
                paddingField.getAnnotation(ModifiableVariableProperty.class);

        assertNotNull(paddingAnnotation);
        assertEquals(Purpose.PADDING, paddingAnnotation.purpose());
        assertEquals(0, paddingAnnotation.minLength());
        assertEquals(255, paddingAnnotation.maxLength());
    }

    @Test
    public void testModifiableVariableAnalyzer() {
        List<Field> annotatedFields =
                ModifiableVariableAnalyzer.getAnnotatedFields(TestClass.class);
        assertEquals(8, annotatedFields.size()); // 8 annotated fields in TestClass

        Map<Purpose, List<Field>> byPurpose =
                ModifiableVariableAnalyzer.groupFieldsByPurpose(TestClass.class);
        assertTrue(byPurpose.containsKey(Purpose.LENGTH));
        assertTrue(byPurpose.containsKey(Purpose.KEY_MATERIAL));
        assertTrue(byPurpose.containsKey(Purpose.CONSTANT));
        assertTrue(byPurpose.containsKey(Purpose.RANDOM));
        assertTrue(byPurpose.containsKey(Purpose.PADDING));

        Map<String, List<Field>> byLengthConstraints =
                ModifiableVariableAnalyzer.groupFieldsByLengthConstraints(TestClass.class);
        assertTrue(byLengthConstraints.containsKey("Fixed length (2 bytes)"));
        assertTrue(byLengthConstraints.containsKey("Fixed length (32 bytes)"));
        assertTrue(byLengthConstraints.containsKey("Variable length (1-4 bytes)"));

        List<Field> lengthFields =
                ModifiableVariableAnalyzer.getFieldsByPurpose(TestClass.class, Purpose.LENGTH);
        assertEquals(1, lengthFields.size());
        assertEquals("length", lengthFields.get(0).getName());

        List<Field> x509Fields =
                ModifiableVariableAnalyzer.getFieldsByEncoding(TestClass.class, Encoding.X509);
        assertEquals(1, x509Fields.size());
        assertEquals("enhancedProperty", x509Fields.get(0).getName());

        List<Field> withLengthConstraints =
                ModifiableVariableAnalyzer.getFieldsWithLengthConstraints(TestClass.class);
        assertEquals(
                6, withLengthConstraints.size()); // All except defaultProperty and unannotatedField

        List<Field> expectedLength32 =
                ModifiableVariableAnalyzer.getFieldsByExpectedLength(TestClass.class, 32);
        assertEquals(1, expectedLength32.size());
        assertEquals("randomValue", expectedLength32.get(0).getName());

        List<String> unannotated =
                ModifiableVariableAnalyzer.getUnannotatedModifiableVariables(TestClass.class);
        assertEquals(1, unannotated.size());
        assertEquals("unannotatedField", unannotated.get(0));
    }

    @Test
    public void testAnalysisReport() {
        String report = ModifiableVariableAnalyzer.generatePropertyAnalysisReport(TestClass.class);
        assertNotNull(report);
        assertTrue(report.contains("ModifiableVariableProperty Analysis Report"));
        assertTrue(report.contains("TestClass"));
        assertTrue(
                report.contains(
                        "Total ModifiableVariable fields: 9")); // 8 annotated + 1 unannotated
        assertTrue(report.contains("Annotated fields: 8"));
        assertTrue(report.contains("Unannotated fields: 1"));
        assertTrue(report.contains("Fields by Purpose:"));
        assertTrue(report.contains("Fields by Length Constraints:"));
        assertTrue(report.contains("Fixed length (32 bytes): randomValue"));
        assertTrue(report.contains("Variable length (1-4 bytes): length"));
        assertTrue(report.contains("unannotatedField"));
    }
}
