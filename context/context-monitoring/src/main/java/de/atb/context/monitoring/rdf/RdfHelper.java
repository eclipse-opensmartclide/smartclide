package de.atb.context.monitoring.rdf;

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


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

import de.atb.context.monitoring.config.models.DataSource;
import de.atb.context.monitoring.models.IMonitoringDataModel;
import de.atb.context.monitoring.rdf.util.MonitoringDataNamespace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import thewebsemantic.Bean2RDF;
import thewebsemantic.RDF2Bean;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

/**
 * RdfHelper
 * $Id
 *
 * @author scholze
 * @version $LastChangedRevision: 143 $
 */
public abstract class RdfHelper<Type> {

    private static final Logger logger = LoggerFactory.getLogger(RdfHelper.class);

    public static <T, D extends DataSource> Model createRdfModel(final IMonitoringDataModel<T, D> container) {
        Model model = createGenericRdfModel(container);
        Bean2RDF writer = new Bean2RDF(model);
        writer.saveDeep(container);
        return model;
    }

    @SuppressWarnings("unchecked")
    public static <T, D extends DataSource> T createMonitoringData(final Model model, final Class<? extends IMonitoringDataModel<T, D>> clazz) {
        RDF2Bean reader = new RDF2Bean(model);
        Collection<? extends IMonitoringDataModel<T, D>> models = reader.loadDeep(clazz);
        if ((models != null) && (models.size() > 0)) {
            IMonitoringDataModel<T, D> object = models.iterator().next();
            object.initialize();
            return (T) object;
        }
        return null;
    }

    public static <T, D extends DataSource> T createMonitoringData(final String rdfModel, final Class<? extends IMonitoringDataModel<T, D>> clazz) {
        Model model = ModelFactory.createDefaultModel();
        InputStream is = new ByteArrayInputStream(rdfModel.getBytes());
        model.read(is, null);
        try {
            is.close();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return createMonitoringData(model, clazz);
    }

    protected static <T, D extends DataSource> Model createGenericRdfModel(final IMonitoringDataModel<T, D> container) {
        Model model = ModelFactory.createDefaultModel();
        for (MonitoringDataNamespace ns : MonitoringDataNamespace.values()) {
            logger.debug(ns.getLocalName());
            logger.debug(ns.getUri());

            model.setNsPrefix(ns.getLocalName(), ns.getUri());
        }
        return model;
    }
}
