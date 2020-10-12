package de.atb.context.monitoring.rdf.util;

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


import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * $Id
 *
 * @param <T>
 */
public interface IMonitoringPropertyProvider<T extends Property> {

    /**
     * Gets the Property from the given Ontology.
     *
     * @param model The Ontology to retrieve the property from.
     * @return the Property from the given Ontology.
     */
    T getProperty(Model model);

    <O> O getValue(Resource resource);
}
