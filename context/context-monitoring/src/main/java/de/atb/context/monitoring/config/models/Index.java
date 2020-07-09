package de.atb.context.monitoring.config.models;

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


import org.simpleframework.xml.Attribute;

/**
 * Index
 *
 * @author scholze
 * @version $LastChangedRevision: 143 $
 */
public class Index {

    /**
     * Represents the Id of an Index. This is used for mapping keys, to lookup
     * Indexes for a certain Monitoring and Interpreter component etc.
     */
    @Attribute
    protected String id;

    /**
     * Represents the physical location of the index (usually this is a file
     * path).
     */
    @Attribute
    protected String location;

    /**
     * Gets the Id of the Index.
     *
     * @return the Id of the Index.
     */
    public final String getId() {
        return this.id;
    }

    /**
     * Sets the Id of the Index.
     *
     * @param id the Id of the Index.
     */
    public final void setId(final String id) {
        this.id = id;
    }

    /**
     * Gets the physical location of the Index. This should be the path, where
     * the Index is actually stored.
     *
     * @return the physical location of the Index.
     */
    public final String getLocation() {
        return this.location;
    }

    /**
     * Sets the physical location of the Index. This should be the path, where
     * the Index is actually stored.
     *
     * @param location the physical location of the Index.
     */
    public final void setLocation(final String location) {
        this.location = location;
    }

}