package de.atb.context.persistence.common;

/*
 * #%L
 * ATB Context Extraction Core Lib
 * %%
 * Copyright (C) 2020 ATB – Institut für angewandte Systemtechnik Bremen GmbH
 * %%
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 * #L%
 */

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import de.atb.context.common.util.ApplicationScenario;
import de.atb.context.common.util.BusinessCase;
import de.atb.context.common.util.IApplicationScenarioProvider;
import de.atb.context.context.util.OntologyNamespace;
import de.atb.context.persistence.processors.IPersistencePostProcessor;
import de.atb.context.persistence.processors.IPersistencePreProcessor;
import de.atb.context.persistence.processors.IPersistenceProcessor;
import org.mindswap.pellet.jena.PelletReasonerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Repository
 *
 * @param <T> Param
 * @author scholze
 * @version $LastChangedRevision: 703 $
 */
public abstract class Repository<T extends IApplicationScenarioProvider>
        implements IPersistenceUnit<T> {

    private static final Logger logger = LoggerFactory
            .getLogger(Repository.class);

    protected final String basicLocation;

    protected Map<ApplicationScenario, List<IPersistenceProcessor<T>>> postProcessors = new HashMap<>(
            0);
    protected Map<ApplicationScenario, List<IPersistenceProcessor<T>>> preProcessors = new HashMap<>(
            0);

    protected Repository(final String baseLocation) {
        this.basicLocation = baseLocation;
        createShutdownHook();
    }

    protected final void createShutdownHook() {
        final Thread shutdownHook = new Thread(() -> shuttingDown(), "Shutdown Hook for repository at '" + basicLocation + "'");
        Runtime.getRuntime().addShutdownHook(shutdownHook);
    }

    public final synchronized <T extends Model> T createDefaultModel(
            final Class<T> clazz, final BusinessCase bc, final String modelUri,
            final boolean useReasoner) {
        final T model = createDefaultModel(clazz, bc);
        model.read(modelUri);
        return model;
    }

    public final synchronized <T extends Model> T createDefaultModel(
            final Class<T> clazz, final BusinessCase bc,
            final boolean useReasoner) {
        final String modelUri = OntologyNamespace.getOntologyLocation(bc);
        final T model = createDefaultModel(clazz, bc);
        model.read(modelUri);
        return model;
    }

    public final synchronized <T extends Model> T createDefaultModel(
            final Class<T> clazz, final OntologyNamespace ns,
            final boolean useReasoner) {
        final BusinessCase bc = ns.getBusinessCase();
        if (bc != null) {
            final T model = createDefaultModel(clazz, bc);
            model.read(ns.getAbsoluteUri());
            return model;
        } else {
            throw new IllegalArgumentException("OntologyNamespace entitiy '"
                    + ns + "' must provide a BusinessCase, not null!");
        }
    }

    @SuppressWarnings("unchecked")
    public final synchronized <T extends Model> T createDefaultModel(
            final BusinessCase bc, final Model model, final boolean useReasoner) {
        return (T) createDefaultModel((Class<T>) model.getClass(), bc).add(
                model);
    }

    public final synchronized <T extends Model> T createDefaultModel(
            final Class<T> clazz, final BusinessCase bc) {
        return initializeDefaultModel(clazz, bc, this.basicLocation, false);
    }

    protected final synchronized boolean implementsInterface(
            final Class<?> clazz, final Class<?> iface) {
        for (final Class<?> hasToImplement : clazz.getInterfaces()) {
            if (hasToImplement == iface) {
                return true;
            }
        }
        return false;
    }

    protected final synchronized String getBasicLocation() {
        return this.basicLocation;
    }

    protected final synchronized String getLocationForBusinessCase(
            final BusinessCase bc) {
        return Repository.getLocationForBusinessCase(this.basicLocation, bc);
    }

    protected static synchronized String getLocationForBusinessCase(
            final String baseUri, final BusinessCase bc) {
        return String.format("%s%s%s%s%s", ".", File.separator, baseUri,
                File.separator, String.valueOf(bc));
    }

    protected abstract void shuttingDown();

    @SuppressWarnings("unchecked")
    protected <T extends Model> T initializeDefaultModel(final Class<T> clazz,
                                                         final BusinessCase bc, final String modelLocation,
                                                         final boolean useReasoner) { // TODO this should maybe replace by a call to DRM API
        final String modelDirStr = Repository.getLocationForBusinessCase(
                modelLocation, bc);
        final File modelDir = new File(modelDirStr);
        if (!modelDir.exists() && !modelDir.mkdirs()) {
            throw new IllegalArgumentException("Directory '" + modelDir
                    + "' does not exist and cannot be created.");
        }
        T toReturn = null;
        if ((clazz == OntModel.class)
                || implementsInterface(clazz, OntModel.class)) {
            OntModel ontModel = ModelFactory
                    .createOntologyModel(OntModelSpec.OWL_DL_MEM);
            if (useReasoner) {
                ontModel = ModelFactory
                        .createOntologyModel(PelletReasonerFactory.THE_SPEC);
                ontModel.prepare();
            }
            toReturn = (T) ontModel;
        } else if ((clazz == Model.class)
                || implementsInterface(clazz, Model.class)) {
            toReturn = (T) ModelFactory.createDefaultModel();
        } else {
            throw new IllegalArgumentException(
                    "Clazz must be one of com.hp.hpl.jena.ontology.OntModel or com.hp.hpl.jena.rdf.Model!");
        }
        return toReturn;
    }

    protected abstract boolean reset(BusinessCase bc);

    protected abstract Dataset getDataSource(BusinessCase bc);

    @Override
    public final void triggerPreProcessors(
            final ApplicationScenario appScenario, final T object) {
        triggerProcessors(appScenario, object,
                this.preProcessors.get(appScenario), "Pre");
    }

    @Override
    public final void triggerPostProcessors(
            final ApplicationScenario appScenario, final T object) {
        triggerProcessors(appScenario, object,
                this.postProcessors.get(appScenario), "Post");
    }

    protected final void triggerProcessors(
            final ApplicationScenario appScenario, final T object,
            final List<IPersistenceProcessor<T>> processors,
            final String processorType) {
        final boolean debug = Repository.logger.isDebugEnabled();
        if (processors != null) {
            Repository.logger.debug("Triggering Persistence %s-Processors for '%s'", processorType, appScenario);
            if (debug) {
                Repository.logger.debug(String.format(
                        "Triggering %1$d Persistence " + processorType
                                + "-Processor(s) for '" + appScenario + "'",
                        Integer.valueOf(processors.size())));
            }
            if (!processors.isEmpty()) {
                for (final IPersistenceProcessor<T> processor : processors) {
                    if (debug) {
                        Repository.logger.debug("Triggering Persistence %s-Processor '%s' for '%s'", processorType, processor.getId(), appScenario);
                    }
                    processor.process(object);
                }
            }
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see de.atb.context.persistence.common.IPersistenceUnit#
     * addIPersistencePreProssecor
     * (de.atb.context.persistence.processors.IPersistencePreProcessor)
     */
    @Override
    public final synchronized boolean addPersistencePreProcessor(
            final ApplicationScenario scenario,
            final IPersistencePreProcessor<T> preProcessor) {
        return addPersistenceProcessor(scenario, preProcessors, preProcessor);
    }

    /*
     * (non-Javadoc)
     *
     * @see de.atb.context.persistence.common.IPersistenceUnit#
     * addIPersistencePostProssecor
     * (de.atb.context.persistence.processors.IPersistencePostProcessor)
     */
    @Override
    public final synchronized boolean addPersistencePostProcessor(
            final ApplicationScenario scenario,
            final IPersistencePostProcessor<T> postProcessor) {
        return addPersistenceProcessor(scenario, postProcessors, postProcessor);
    }

    protected final synchronized boolean addPersistenceProcessor(
            final ApplicationScenario scenario,
            final Map<ApplicationScenario, List<IPersistenceProcessor<T>>> map,
            final IPersistenceProcessor<T> processor) {
        List<IPersistenceProcessor<T>> list = map.get(scenario);
        boolean exists = false;
        if (list == null) {
            list = new ArrayList<>();
            list.add(processor);
        } else {
            for (final IPersistenceProcessor<T> p : list) {
                if ((p.getId() != null) && p.getId().equals(processor.getId())) {
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                list.add(processor);
            }
        }
        map.put(scenario, list);
        return !exists;
    }

    /*
     * (non-Javadoc)
     *
     * @see de.atb.context.persistence.common.IPersistenceUnit#
     * removeIPersistencePreProssecor
     * (de.atb.context.persistence.processors.IPersistencePreProcessor)
     */
    @Override
    public final synchronized boolean removePersistencePreProcessor(
            final ApplicationScenario scenario,
            final IPersistencePreProcessor<T> preProcessor) {
        return removePersistenceProcessor(scenario, preProcessors,
                preProcessor.getId());

    }

    @Override
    public final synchronized boolean removePersistencePreProcessor(
            final ApplicationScenario scenario, final String id) {
        return removePersistenceProcessor(scenario, preProcessors, id);

    }

    /*
     * (non-Javadoc)
     *
     * @see de.atb.context.persistence.common.IPersistenceUnit#
     * removeIPersistencePostProssecor
     * (de.atb.context.persistence.processors.IPersistencePostProcessor)
     */
    @Override
    public final synchronized boolean removePersistencePostProcessor(
            final ApplicationScenario scenario,
            final IPersistencePostProcessor<T> postProcessor) {
        return removePersistenceProcessor(scenario, postProcessors,
                postProcessor.getId());
    }

    @Override
    public final synchronized boolean removePersistencePostProcessor(
            final ApplicationScenario scenario, final String id) {
        return removePersistenceProcessor(scenario, postProcessors, id);
    }

    protected final synchronized boolean removePersistenceProcessor(
            final ApplicationScenario scenario,
            final Map<ApplicationScenario, List<IPersistenceProcessor<T>>> map,
            final String id) {
        final List<IPersistenceProcessor<T>> list = map.get(scenario);
        boolean removed = false;
        if (list != null) {
            for (int i = list.size() - 1; i > -1; i--) {
                final IPersistenceProcessor<T> p = list.get(i);
                if ((p.getId() != null) && p.getId().equals(id)) {
                    list.remove(i);
                    removed = true;
                    break;
                }
            }
            map.put(scenario, list);
        }
        return removed;
    }

}
