package de.atb.context.monitoring.parser;

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


import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Store;

import de.atb.context.monitoring.index.IFieldable;
import org.apache.lucene.document.StringField;

/**
 * IndexedFields
 * $Id
 *
 * @author scholze
 * @version $LastChangedRevision: 143 $
 */
public enum IndexedFields implements IFieldable {

    /**
     * The name of the field where the indexer version is stored.
     */
    IndexerVersion("SelfLearning-INDEXER-Version"),

    /**
     * The name of the field where the last Modification Date of an indexed item
     * is stored.
     */
    LastModified("lastModified"),

    /**
     * The name of the field where the index's id of an indexed item is stored.
     */
    IndexId("indexId"),

    /**
     * The name of the field pointing to URI where the indexable resource is
     * stored.
     */
    Uri("uri"),

    /**
     * The name of the field pointing to DateTime where the indexable resource
     * was monitored.
     */
    MonitoredAt("monitoredAt"),

    ;

    private String name;

    IndexedFields(final String name) {
        this.name = name;
    }

    public static Field createField(final IFieldable fieldable, final String value) {
        return fieldable.createField(value);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * IFieldable#get(org.apache.lucene
     * .document.Document)
     */
    @Override
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
    @Override
    public String getString(final Document document) {
        return get(document).stringValue();
    }

    /*
     * (non-Javadoc)
     *
     * @see IFieldable#getName()
     */
    @Override
    public String getName() {
        return this.name;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Enum#toString()
     */
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
