/**
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Copyright 2014-2022 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 *
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.modifiablevariable.util;

import static org.junit.jupiter.api.Assertions.*;

import de.rub.nds.modifiablevariable.HoldsModifiableVariable;
import de.rub.nds.modifiablevariable.biginteger.ModifiableBigInteger;
import de.rub.nds.modifiablevariable.bytearray.ModifiableByteArray;
import de.rub.nds.modifiablevariable.integer.ModifiableInteger;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

public class ModifiableVariableAnalyzerTest {

    /**
     * Test of getAllModifiableVariableFields method, of class ModifiableVariableAnalyzer.
     *
     */
    @Test
    public void testGetAllModifiableVariableFields() {
        SimpleClassWithModVariables chm = new SimpleClassWithModVariables();
        String[] fieldNames = { "bi", "array", "i" };
        List<Field> fields = ModifiableVariableAnalyzer.getAllModifiableVariableFields(chm);
        for (String fn : fieldNames) {
            assertTrue(containsFieldName(fn, fields));
        }
        assertFalse(containsFieldName("somename", fields));
    }

    /**
     * Test of getRandomModifiableVariableField method, of class ModifiableVariableAnalyzer.
     */
    @Disabled("Not yet implemented")
    @Test
    public void testGetRandomModifiableVariableField() {
    }

    @Test
    public void testGetAllModifiableVariableHolders() {
        SimpleClassWithModVariables test1 = new SimpleClassWithModVariables();
        test1.bi = new ModifiableBigInteger();
        test1.x = Integer.valueOf("1");
        assertEquals(1, ModifiableVariableAnalyzer.getAllModifiableVariableHoldersRecursively(test1).size());
        test1.test = new SimpleClassWithModVariables();
        assertEquals(2, ModifiableVariableAnalyzer.getAllModifiableVariableHoldersRecursively(test1).size());
    }

    @Test
    public void testGetAllModifiableVariableFieldsRecursively() {
        SimpleClassWithModVariables test1 = new SimpleClassWithModVariables();
        test1.bi = new ModifiableBigInteger();
        test1.x = Integer.valueOf("1");
        List<ModifiableVariableField> fields =
            ModifiableVariableAnalyzer.getAllModifiableVariableFieldsRecursively(test1);
        assertEquals(3, fields.size());
        test1.test = new SimpleClassWithModVariables();
        fields = ModifiableVariableAnalyzer.getAllModifiableVariableFieldsRecursively(test1);
        assertEquals(6, fields.size());
    }

    @Test
    public void testGetAllModifiableVariableFieldsRecursivelyWithList() {
        SimpleClassWithModVariablesList test1 = new SimpleClassWithModVariablesList();
        test1.bi = new ModifiableBigInteger();
        test1.list = new LinkedList<>();
        test1.list.add(new SimpleClassWithModVariables());
        test1.list.add(new SimpleClassWithModVariables());
        List<ModifiableVariableField> fields =
            ModifiableVariableAnalyzer.getAllModifiableVariableFieldsRecursively(test1);
        assertEquals(9, fields.size());
    }

    @Test
    public void testGetAllModifiableVariableFieldsRecursivelyOrder() {
        SimpleClassWithModVariables test1 = new SimpleClassWithModVariables();
        test1.bi = new ModifiableBigInteger();
        test1.x = Integer.valueOf("1");
        test1.test = new SimpleClassWithModVariables();
        List<ModifiableVariableField> fields1 =
            ModifiableVariableAnalyzer.getAllModifiableVariableFieldsRecursively(test1);
        SimpleClassWithModVariables test2 = new SimpleClassWithModVariables();
        test2.test = new SimpleClassWithModVariables();
        List<ModifiableVariableField> fields2 =
            ModifiableVariableAnalyzer.getAllModifiableVariableFieldsRecursively(test2);
        assertEquals(6, fields1.size());
        assertEquals(6, fields2.size());
        for (int i = 0; i < fields1.size(); i++) {
            String name1 = fields1.get(i).getField().getName();
            String name2 = fields2.get(i).getField().getName();
            assertEquals(name1, name2);
        }
    }

    private boolean containsFieldName(String name, List<Field> list) {
        for (Field f : list) {
            if (f.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    private static class SimpleClassWithModVariables {
        Integer x;
        ModifiableBigInteger bi;
        ModifiableByteArray array;
        ModifiableInteger i;
        @HoldsModifiableVariable
        SimpleClassWithModVariables test;
    }

    private static class SimpleClassWithModVariablesList {
        Integer x;
        ModifiableBigInteger bi;
        ModifiableByteArray array;
        ModifiableInteger i;

        @HoldsModifiableVariable
        SimpleClassWithModVariables test;
        @HoldsModifiableVariable
        List<SimpleClassWithModVariables> list;
    }

}
