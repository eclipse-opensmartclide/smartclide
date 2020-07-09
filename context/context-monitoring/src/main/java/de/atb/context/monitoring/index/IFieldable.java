package de.atb.context.monitoring.index;

/*
 * #%L
 * ATB Context Monitoring Core Services
 * %%
 * Copyright (C) 2015 - 2020 ATB
 * %%
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the Eclipse
 * Public License, v. 2.0 are satisfied: GNU General Public License, version 2
 * with the GNU Classpath Exception which is
 * available at https://www.gnu.org/software/classpath/license.html.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 * #L%
 */


import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;

/**
 * IFieldable
 *
 * @author scholze
 * @version $LastChangedRevision: 143 $
 */
public interface IFieldable {

    /**
     * Returns a field with the name of the fieldable element if any exist in
     * the given document, or <code>null</code>. If multiple fields exists with
     * this name, this method returns the first value added.
     *
     * @param document the document to get the field with the name of the fieldable
     *                 element from.
     * @return a field with the name of the fieldable element if any exist in
     * the given document.
     */
    Field get(Document document);

    /**
     * /** Create a field by using the fieldables name and specify the value. By
     * default the value will be saved in the index and not analyzed. Term
     * vectors will not be stored in the index.
     * <p>
     * This is equivalent to calling:
     *
     * <pre>
     * new Field(this.getName(), value, Field.Store.YES, Field.Index.NOT_ANALYZED);
     * </pre>
     *
     * @param value The string to process
     * @return a new Field.
     * @throws NullPointerException     if name or value is <code>null</code>
     * @throws IllegalArgumentException if the field is neither stored nor indexed
     */
    Field createField(String value);

    /**
     * Create a field by using the fieldables name and specify the value and how
     * it is stored in the index. By default the value will not be analyzed and
     * term vectors will not be stored in the index.
     * <p>
     * This is equivalent to calling:
     *
     * <pre>
     * new Field(this.getName(), value, store, Field.Index.NOT_ANALYZED);
     * </pre>
     *
     * @param value The string to process
     * @param store Whether <code>value</code> should be stored in the index
     * @return a new Field.
     * @throws NullPointerException     if name or value is <code>null</code>
     * @throws IllegalArgumentException if the field is neither stored nor indexed
     */
    Field createField(String value, Store store);

    /**
     * Create a field by specifying its value and how it will be saved in the
     * index. Term vectors will not be stored in the index.
     *
     * <p>
     * This is equivalent to calling:
     *
     * <pre>
     * new Field(this.getName(), value, store, index);
     * </pre>
     *
     * @param value The string to process
     * @param store Whether <code>value</code> should be stored in the index
     * @param index Whether the field should be indexed, and if so, if it should
     *              be tokenized before indexing
     * @return a new Field.
     * @throws NullPointerException     if name or value is <code>null</code>
     * @throws IllegalArgumentException if the field is neither stored nor indexed
     */
    Field createField(String value, Store store, Index index);

    /**
     * Returns the string value of a field with the name of the fieldable
     * element if any exist in the given document, or <code>null</code>.
     *
     * @param document the document to get the string value of the field with the
     *                 name of the fieldable element from.
     * @return the string value of the field with the name of the fieldable
     * element if any exist in the given document.
     */
    String getString(Document document);

    /**
     * Gets the name of the field.
     *
     * @return the name of the field.
     */
    String getName();
}
