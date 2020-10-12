package de.atb.context.monitoring.index;

/*-
 * #%L
 * ATB Context Monitoring Core Services
 * %%
 * Copyright (C) 2015 - 2020 ATB – Institut für angewandte Systemtechnik Bremen GmbH
 * %%
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 * #L%
 */

import org.apache.lucene.document.FieldType;

public class FieldTypeFactory {

    private static StoredNotAnalyzedFieldType snaft = new StoredNotAnalyzedFieldType();
    private static StoredNotIndexedFieldType snift = new StoredNotIndexedFieldType();


    public static FieldType getStoredNotAnalyzedFieldType() {
        return snaft;
    }

    public static FieldType getStoredNotIndexedFieldType() {
        return snift;
    }

}
