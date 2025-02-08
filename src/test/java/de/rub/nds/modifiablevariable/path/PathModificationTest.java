/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class PathModificationTest {

    @Test
    public void testInsert() {
        ModifiablePath nullPath = new ModifiablePath();
        nullPath.setModification(new PathInsertValueModification("test", 2));
        assertNull(nullPath.getValue());

        ModifiablePath emptyPath = new ModifiablePath("");
        emptyPath.setModification(new PathInsertValueModification("test", 0));
        assertEquals("test", emptyPath.getValue());
        emptyPath.setModification(new PathInsertValueModification("test", 10));
        assertEquals("test", emptyPath.getValue());

        ModifiablePath simplePathLeadingSlash = new ModifiablePath("/this/is/a/path");
        simplePathLeadingSlash.setModification(new PathInsertValueModification("test", 0));
        assertEquals("/test/this/is/a/path", simplePathLeadingSlash.getValue());
        simplePathLeadingSlash.setModification(new PathInsertValueModification("test", 4));
        assertEquals("/this/is/a/path/test", simplePathLeadingSlash.getValue());
        simplePathLeadingSlash.setModification(new PathInsertValueModification("test", 5));
        assertEquals("/test/this/is/a/path", simplePathLeadingSlash.getValue());
        simplePathLeadingSlash.setModification(new PathInsertValueModification("test", 6));
        assertEquals("/this/test/is/a/path", simplePathLeadingSlash.getValue());
        simplePathLeadingSlash.setModification(new PathInsertValueModification("test", 11));
        assertEquals("/this/test/is/a/path", simplePathLeadingSlash.getValue());

        ModifiablePath simplePathLeadingAndTrailingSlash = new ModifiablePath("/this/is/a/path/");
        simplePathLeadingAndTrailingSlash.setModification(
                new PathInsertValueModification("test", 0));
        assertEquals("/test/this/is/a/path/", simplePathLeadingAndTrailingSlash.getValue());
        simplePathLeadingAndTrailingSlash.setModification(
                new PathInsertValueModification("test", 4));
        assertEquals("/this/is/a/path/test/", simplePathLeadingAndTrailingSlash.getValue());
        simplePathLeadingAndTrailingSlash.setModification(
                new PathInsertValueModification("test", 5));
        assertEquals("/test/this/is/a/path/", simplePathLeadingAndTrailingSlash.getValue());
        simplePathLeadingAndTrailingSlash.setModification(
                new PathInsertValueModification("test", 6));
        assertEquals("/this/test/is/a/path/", simplePathLeadingAndTrailingSlash.getValue());
        simplePathLeadingAndTrailingSlash.setModification(
                new PathInsertValueModification("test", 11));
        assertEquals("/this/test/is/a/path/", simplePathLeadingAndTrailingSlash.getValue());

        ModifiablePath simplePath = new ModifiablePath("this/is/a/path");
        simplePath.setModification(new PathInsertValueModification("test", 0));
        assertEquals("test/this/is/a/path", simplePath.getValue());
        simplePath.setModification(new PathInsertValueModification("test", 4));
        assertEquals("this/is/a/path/test", simplePath.getValue());
        simplePath.setModification(new PathInsertValueModification("test", 5));
        assertEquals("test/this/is/a/path", simplePath.getValue());
        simplePath.setModification(new PathInsertValueModification("test", 6));
        assertEquals("this/test/is/a/path", simplePath.getValue());
        simplePath.setModification(new PathInsertValueModification("test", 11));
        assertEquals("this/test/is/a/path", simplePath.getValue());

        ModifiablePath simplePathTrailingSlash = new ModifiablePath("this/is/a/path/");
        simplePathTrailingSlash.setModification(new PathInsertValueModification("test", 0));
        assertEquals("test/this/is/a/path/", simplePathTrailingSlash.getValue());
        simplePathTrailingSlash.setModification(new PathInsertValueModification("test", 4));
        assertEquals("this/is/a/path/test/", simplePathTrailingSlash.getValue());
        simplePathTrailingSlash.setModification(new PathInsertValueModification("test", 5));
        assertEquals("test/this/is/a/path/", simplePathTrailingSlash.getValue());
        simplePathTrailingSlash.setModification(new PathInsertValueModification("test", 6));
        assertEquals("this/test/is/a/path/", simplePathTrailingSlash.getValue());
        simplePathTrailingSlash.setModification(new PathInsertValueModification("test", 11));
        assertEquals("this/test/is/a/path/", simplePathTrailingSlash.getValue());

        ModifiablePath rootPath = new ModifiablePath("/");
        rootPath.setModification(new PathInsertValueModification("test", 0));
        assertEquals("/test/", rootPath.getValue());
        rootPath.setModification(new PathInsertValueModification("test", 2));
        assertEquals("/test/", rootPath.getValue());
        rootPath.setModification(new PathInsertValueModification("test", 5));
        assertEquals("/test/", rootPath.getValue());
    }

    @Test
    public void testDelete() {
        ModifiablePath nullPath = new ModifiablePath();
        nullPath.setModification(new PathDeleteModification(0, 1));
        assertNull(nullPath.getValue());

        ModifiablePath emptyPath = new ModifiablePath("");
        emptyPath.setModification(new PathDeleteModification(0, 1));
        assertEquals("", emptyPath.getValue());
        emptyPath.setModification(new PathDeleteModification(1, 10));
        assertEquals("", emptyPath.getValue());

        ModifiablePath simplePathLeadingSlash = new ModifiablePath("/this/is/a/path");
        simplePathLeadingSlash.setModification(new PathDeleteModification(0, 0));
        assertEquals("/this/is/a/path", simplePathLeadingSlash.getValue());
        simplePathLeadingSlash.setModification(new PathDeleteModification(4, 4));
        assertEquals("", simplePathLeadingSlash.getValue());
        simplePathLeadingSlash.setModification(new PathDeleteModification(4, 1));
        assertEquals("/is/a/path", simplePathLeadingSlash.getValue());
        simplePathLeadingSlash.setModification(new PathDeleteModification(5, 1));
        assertEquals("/this/a/path", simplePathLeadingSlash.getValue());
        simplePathLeadingSlash.setModification(new PathDeleteModification(6, 1));
        assertEquals("/this/is/path", simplePathLeadingSlash.getValue());
        simplePathLeadingSlash.setModification(new PathDeleteModification(11, 11));
        assertEquals("/this/is/a", simplePathLeadingSlash.getValue());

        ModifiablePath simplePathLeadingAndTrailingSlash = new ModifiablePath("/this/is/a/path/");
        simplePathLeadingAndTrailingSlash.setModification(new PathDeleteModification(0, 0));
        assertEquals("/this/is/a/path/", simplePathLeadingAndTrailingSlash.getValue());
        simplePathLeadingAndTrailingSlash.setModification(new PathDeleteModification(4, 4));
        assertEquals("/", simplePathLeadingAndTrailingSlash.getValue());
        simplePathLeadingAndTrailingSlash.setModification(new PathDeleteModification(4, 1));
        assertEquals("/is/a/path/", simplePathLeadingAndTrailingSlash.getValue());
        simplePathLeadingAndTrailingSlash.setModification(new PathDeleteModification(5, 1));
        assertEquals("/this/a/path/", simplePathLeadingAndTrailingSlash.getValue());
        simplePathLeadingAndTrailingSlash.setModification(new PathDeleteModification(6, 1));
        assertEquals("/this/is/path/", simplePathLeadingAndTrailingSlash.getValue());
        simplePathLeadingAndTrailingSlash.setModification(new PathDeleteModification(11, 11));
        assertEquals("/this/is/a/", simplePathLeadingAndTrailingSlash.getValue());

        ModifiablePath simplePath = new ModifiablePath("this/is/a/path");
        simplePath.setModification(new PathDeleteModification(0, 0));
        assertEquals("this/is/a/path", simplePath.getValue());
        simplePath.setModification(new PathDeleteModification(4, 4));
        assertEquals("", simplePath.getValue());
        simplePath.setModification(new PathDeleteModification(4, 1));
        assertEquals("is/a/path", simplePath.getValue());
        simplePath.setModification(new PathDeleteModification(5, 1));
        assertEquals("this/a/path", simplePath.getValue());
        simplePath.setModification(new PathDeleteModification(6, 1));
        assertEquals("this/is/path", simplePath.getValue());
        simplePath.setModification(new PathDeleteModification(11, 11));
        assertEquals("this/is/a", simplePath.getValue());

        ModifiablePath simplePathTrailingSlash = new ModifiablePath("this/is/a/path/");
        simplePathTrailingSlash.setModification(new PathDeleteModification(0, 0));
        assertEquals("this/is/a/path/", simplePathTrailingSlash.getValue());
        simplePathTrailingSlash.setModification(new PathDeleteModification(4, 4));
        assertEquals("", simplePathTrailingSlash.getValue());
        simplePathTrailingSlash.setModification(new PathDeleteModification(4, 1));
        assertEquals("is/a/path/", simplePathTrailingSlash.getValue());
        simplePathTrailingSlash.setModification(new PathDeleteModification(5, 1));
        assertEquals("this/a/path/", simplePathTrailingSlash.getValue());
        simplePathTrailingSlash.setModification(new PathDeleteModification(6, 1));
        assertEquals("this/is/path/", simplePathTrailingSlash.getValue());
        simplePathTrailingSlash.setModification(new PathDeleteModification(11, 11));
        assertEquals("this/is/a/", simplePathTrailingSlash.getValue());

        ModifiablePath rootPath = new ModifiablePath("/");
        rootPath.setModification(new PathDeleteModification(0, 0));
        assertEquals("/", rootPath.getValue());
        rootPath.setModification(new PathDeleteModification(2, 2));
        assertEquals("", rootPath.getValue());
        rootPath.setModification(new PathDeleteModification(5, 5));
        assertEquals("", rootPath.getValue());
    }
}
