/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.string;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import de.rub.nds.modifiablevariable.VariableModification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StringModificationTest {

    private ModifiableString modifiableString;
    private String originalValue = "TestValue";

    @BeforeEach
    public void setUp() {
        modifiableString = new ModifiableString();
        modifiableString.setOriginalValue(originalValue);
    }

    @Test
    public void testAppendModification() {
        String appendValue = "Appended";
        VariableModification<String> modifier = StringModificationFactory.appendValue(appendValue);
        modifiableString.setModifications(modifier);

        assertEquals(originalValue + appendValue, modifiableString.getValue());
    }

    @Test
    public void testPrependModification() {
        String prependValue = "Prepended";
        VariableModification<String> modifier =
                StringModificationFactory.prependValue(prependValue);
        modifiableString.setModifications(modifier);

        assertEquals(prependValue + originalValue, modifiableString.getValue());
    }

    @Test
    public void testExplicitValueModification() {
        String explicitValue = "CompletelyDifferent";
        VariableModification<String> modifier =
                StringModificationFactory.explicitValue(explicitValue);
        modifiableString.setModifications(modifier);

        assertEquals(explicitValue, modifiableString.getValue());
        assertNotEquals(originalValue, modifiableString.getValue());
    }

    @Test
    public void testAppendWithEmptyString() {
        String appendValue = "";
        VariableModification<String> modifier = StringModificationFactory.appendValue(appendValue);
        modifiableString.setModifications(modifier);

        assertEquals(originalValue, modifiableString.getValue());
    }

    @Test
    public void testPrependWithEmptyString() {
        String prependValue = "";
        VariableModification<String> modifier =
                StringModificationFactory.prependValue(prependValue);
        modifiableString.setModifications(modifier);

        assertEquals(originalValue, modifiableString.getValue());
    }

    @Test
    public void testAppendToEmptyString() {
        modifiableString.setOriginalValue("");
        String appendValue = "JustAppended";
        VariableModification<String> modifier = StringModificationFactory.appendValue(appendValue);
        modifiableString.setModifications(modifier);

        assertEquals(appendValue, modifiableString.getValue());
    }

    @Test
    public void testPrependToEmptyString() {
        modifiableString.setOriginalValue("");
        String prependValue = "JustPrepended";
        VariableModification<String> modifier =
                StringModificationFactory.prependValue(prependValue);
        modifiableString.setModifications(modifier);

        assertEquals(prependValue, modifiableString.getValue());
    }

    @Test
    public void testModifierEquality() {
        String appendValue = "SomeValue";
        StringAppendValueModification modifier1 = new StringAppendValueModification(appendValue);
        StringAppendValueModification modifier2 = new StringAppendValueModification(appendValue);

        assertEquals(modifier1, modifier2);
        assertEquals(modifier1.hashCode(), modifier2.hashCode());

        StringAppendValueModification modifier3 =
                new StringAppendValueModification("DifferentValue");
        assertNotEquals(modifier1, modifier3);
        assertNotEquals(modifier1.hashCode(), modifier3.hashCode());
    }
}
