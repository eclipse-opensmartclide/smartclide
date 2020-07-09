package de.atb.context.monitoring.rdf.util;

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


import com.hp.hpl.jena.rdf.model.Model;

/**
 * IResourceable
 * $Id
 *
 * @author scholze
 * @version $LastChangedRevision: 143 $
 */
public interface IMonitoringDataResource {

    /**
     * Returns the name of this resource within its namespace.
     *
     * @return The name of this property within its namespace.
     */
    String getLocalName();

    /**
     * Return the URI of the resource (in relation to the given model), or
     * <code>null</code> if it's a bnode or does not exist in the given model.
     *
     * @param model The ontology model to get the URI of the resource from
     *              (containing namespaces and namespace prefixes).
     * @return the URI of the resource (in relation to the given model), or
     * <code>null</code> if it's a bnode or does not exist in the given
     * model.
     */
    String getUri(Model model);

    /**
     * Returns the namespace prefix associated with this resource.
     *
     * @return the namespace prefix associated with this resource.
     */
    String getNameSpacePrefix();

    /**
     * Returns the namespace associated with this resource (in relation to the
     * given model) or <code>null</code> if there is no namespace for the
     * Resource within the given ontology model.
     *
     * @param model The ontology model to get the namespace of the resource from
     *              (containing namespace prefixes).
     * @return the namespace associated with this resource (in relation to the
     * given model) or <code>null</code> if there is no namespace for
     * the Resource within the given ontology model.
     */
    String getNameSpace(Model model);
}
