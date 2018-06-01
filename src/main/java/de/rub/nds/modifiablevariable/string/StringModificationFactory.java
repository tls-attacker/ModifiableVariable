/*
 * Copyright 2017 Robert Merget <robert.merget@rub.de>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.rub.nds.modifiablevariable.string;

import de.rub.nds.modifiablevariable.VariableModification;
import de.rub.nds.modifiablevariable.util.RandomHelper;

/**
 *
 */
public class StringModificationFactory {

    private static final int MAX_BYTE_LENGTH = 1000;

    public static VariableModification<String> explicitValue(final String value) {
        return new StringExplicitValueModification(value);
    }

    public static VariableModification<String> createRandomModification() {
        int i = RandomHelper.getRandom().nextInt(1000);
        byte[] randomBytes = new byte[i];
        RandomHelper.getRandom().nextBytes(randomBytes);
        return explicitValue(new String(randomBytes));
    }
}
