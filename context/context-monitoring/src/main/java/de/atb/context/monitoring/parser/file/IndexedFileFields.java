package de.atb.context.monitoring.parser.file;

/*
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


import de.atb.context.monitoring.index.FieldTypeFactory;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Store;

import de.atb.context.monitoring.index.IFieldable;
import org.apache.lucene.document.StringField;

/**
 * Fields
 * $Id
 *
 * @author scholze
 * @version $LastChangedRevision: 143 $
 */
public enum IndexedFileFields implements IFieldable {

    FileName("fileName"),

    FilePath("filePath"),

    FileSize("fileSize"),

    Hash("fileHash"),

    Hidden("hidden"),

    ;

    private String name;

    IndexedFileFields(final String name) {
        this.name = name;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * IFieldable#get(org.apache.lucene
     * .document.Document)
     */
    public Field get(final Document document) {
        return (Field) document.getField(this.name);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * IFieldable#getString(org.apache.
     * lucene.document.Document)
     */
    public String getString(final Document document) {
        return get(document).stringValue();
    }

    /*
     * (non-Javadoc)
     *
     * @see IFieldable#getName()
     */
    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * IFieldable#create(java.lang.String)
     */
    @Override
    public Field createField(final String value) {
        return new Field(getName(), value, StringField.TYPE_STORED);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * IFieldable#create(java.lang.String,
     * org.apache.lucene.document.Field.Store)
     */
    @Override
    public Field createField(final String value, final Store store) {
        return new Field(getName(), value, StringField.TYPE_STORED);
    }
}
