/**
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Copyright 2014-2022 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 *
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.modifiablevariable;

import de.rub.nds.modifiablevariable.filter.AccessModificationFilter;
import jakarta.xml.bind.annotation.XmlSeeAlso;

/**
 * It is possible to filter modifications only for specific number of data accesses or specific data. For example, only
 * the first data access returns a modified value. This can be achieved using a ModificationFilter object.
 *
 */
@XmlSeeAlso({ AccessModificationFilter.class })
public abstract class ModificationFilter {

    public abstract boolean filterModification();
}
