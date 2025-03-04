/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class SuppressingBooleanAdapterTest {

    @Test
    public void testSuppressingTrueBooleanAdapter() throws Exception {
        SuppressingBooleanAdapter adapter = new SuppressingTrueBooleanAdapter();

        // Test getValueToSuppress
        assertEquals(Boolean.TRUE, adapter.getValueToSuppress());

        // Test unmarshal
        assertEquals(Boolean.TRUE, adapter.unmarshal(null));
        assertEquals(Boolean.TRUE, adapter.unmarshal("true"));
        assertEquals(Boolean.FALSE, adapter.unmarshal("false"));
        assertEquals(Boolean.FALSE, adapter.unmarshal("anything"));

        // Test marshal
        assertNull(adapter.marshal(Boolean.TRUE));
        assertNull(adapter.marshal(null));
        assertEquals("false", adapter.marshal(Boolean.FALSE));
    }

    @Test
    public void testSuppressingFalseBooleanAdapter() throws Exception {
        SuppressingBooleanAdapter adapter = new SuppressingFalseBooleanAdapter();

        // Test getValueToSuppress
        assertEquals(Boolean.FALSE, adapter.getValueToSuppress());

        // Test unmarshal
        assertEquals(Boolean.FALSE, adapter.unmarshal(null));
        assertEquals(Boolean.TRUE, adapter.unmarshal("true"));
        assertEquals(Boolean.FALSE, adapter.unmarshal("false"));
        assertEquals(Boolean.FALSE, adapter.unmarshal("anything"));

        // Test marshal
        assertNull(adapter.marshal(Boolean.FALSE));
        assertNull(adapter.marshal(null));
        assertEquals("true", adapter.marshal(Boolean.TRUE));
    }
}
