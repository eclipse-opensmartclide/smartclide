package de.atb.context.services;

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


import de.atb.context.services.faults.ContextFault;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.atb.context.common.util.ApplicationScenario;
import de.atb.context.common.util.IApplicationScenarioProvider;
import de.atb.context.monitoring.rdf.RdfHelper;
import de.atb.context.persistence.common.IPersistenceUnit;
import de.atb.context.persistence.processors.IPersistencePostProcessor;
import de.atb.context.persistence.processors.IPersistencePreProcessor;
import de.atb.context.services.interfaces.IPersistenceService;

/**
 * PersistenceUnitService
 * $Id
 *
 * @author scholze
 * @version $LastChangedRevision: 143 $
 */
public abstract class PersistenceUnitService<Type extends IApplicationScenarioProvider, Repos extends IPersistenceUnit<Type>> implements
    IPersistenceService {

    private static final Logger logger = LoggerFactory.getLogger(PersistenceUnitService.class);

    protected Repos repos;

    protected PersistenceUnitService(final Repos persistenceStore) {
        repos = persistenceStore;
    }

    /*
     * (non-Javadoc)
     *
     * @see de.atb.context.services.interfaces.IPersistenceService#
     * addPersistencePreProcessor
     * (de.atb.context.common.util.ApplicationScenario, java.lang.String,
     * java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public final boolean addPersistencePreProcessor(final ApplicationScenario applicationScenario, final String id, final String className)
        throws ContextFault {
        try {
            logger.debug("Creating Persistence Pre-Processor of type '" + className + "' with id '" + id + "'");
            Class<IPersistencePreProcessor<Type>> clazz = (Class<IPersistencePreProcessor<Type>>) Class.forName(className);
            IPersistencePreProcessor<Type> processor = clazz.newInstance();
            processor.setId(id);
            boolean success = repos.addPersistencePreProcessor(applicationScenario, processor);
            if (success) {
                logger.debug("Added Persistence Pre-Processor of type '" + className + "' with id '" + id + "' to '" + applicationScenario
                    + "'");
            } else {
                logger.debug("Persistence Pre-Processor of type '" + className + "' with id '" + id + "' for '" + applicationScenario
                    + "' was already registered! Not added again.");

            }
            return success;
        } catch (ClassCastException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            logger.error(e.getMessage(), e);
            throw new ContextFault(e.getMessage(), e);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see de.atb.context.services.interfaces.IPersistenceService#
     * addPersistencePostProcessor
     * (de.atb.context.common.util.ApplicationScenario, java.lang.String,
     * java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public final boolean addPersistencePostProcessor(final ApplicationScenario applicationScenario, final String id, final String className)
        throws ContextFault {
        try {
            logger.debug("Creating Persistence Post-Processor of type '" + className + "' with id '" + id + "'");
            Class<IPersistencePostProcessor<Type>> clazz = (Class<IPersistencePostProcessor<Type>>) Class.forName(className);
            IPersistencePostProcessor<Type> processor = clazz.newInstance();
            processor.setId(id);
            boolean success = repos.addPersistencePostProcessor(applicationScenario, processor);
            if (success) {
                logger.debug("Added Persistence Post-Processor of type '" + className + "' with id '" + id + "' to '" + applicationScenario
                    + "'");
            } else {
                logger.debug("Persistence Post-Processor of type '" + className + "' with id '" + id + "' for '" + applicationScenario
                    + "' was already registered! Not added again.");
            }
            return success;
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            logger.error(e.getMessage(), e);
            throw new ContextFault(e.getMessage(), e);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see de.atb.context.services.interfaces.IPersistenceService#
     * removePersistencePreProcessor
     * (de.atb.context.common.util.ApplicationScenario, java.lang.String,
     * java.lang.String)
     */
    @Override
    public final boolean removePersistencePreProcessor(final ApplicationScenario applicationScenario, final String id, final String className)
        throws ContextFault {
        try {
            boolean success = repos.removePersistencePreProcessor(applicationScenario, id);
            if (success) {
                logger.debug("Removed Persistence Pre-Processor of type '" + className + "' with id '" + id + "' from '"
                    + applicationScenario + "'");
            } else {
                logger.debug("Persistence Pre-Processor of type '" + className + "' with id '" + id + "' for '" + applicationScenario
                    + "' was not registered! Could not be deleted.");
            }
            return success;
        } catch (Throwable t) {
            logger.error(t.getMessage(), t);
            throw new ContextFault(t.getMessage(), t);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see de.atb.context.services.interfaces.IPersistenceService#
     * removePersistencePreProcessor
     * (de.atb.context.common.util.ApplicationScenario, java.lang.String)
     */
    @Override
    public final boolean removePersistencePreProcessor(final ApplicationScenario applicationScenario, final String id) throws ContextFault {
        try {
            boolean success = repos.removePersistencePreProcessor(applicationScenario, id);
            if (success) {
                logger.debug("Removed Persistence Pre-Processor with id '" + id + "' from '" + applicationScenario + "'");
            } else {
                logger.debug("Persistence Pre-Processor with id '" + id + "' for '" + applicationScenario
                    + "' was not registered! Could not be deleted.");
            }
            return success;
        } catch (Throwable t) {
            logger.error(t.getMessage(), t);
            throw new ContextFault(t.getMessage(), t);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see de.atb.context.services.interfaces.IPersistenceService#
     * removePersistencePostProcessor
     * (de.atb.context.common.util.ApplicationScenario, java.lang.String,
     * java.lang.String)
     */
    @Override
    public final boolean removePersistencePostProcessor(final ApplicationScenario applicationScenario, final String id, final String className)
        throws ContextFault {
        try {
            boolean success = repos.removePersistencePostProcessor(applicationScenario, id);
            if (success) {
                logger.debug("Removed Persistence Post-Processor of type '" + className + "' with id '" + id + "' from '"
                    + applicationScenario + "'");
            } else {
                logger.debug("Persistence Post-Processor of type '" + className + "' with id '" + id + "' for '" + applicationScenario
                    + "' was not registered! Could not be deleted.");
            }
            return success;
        } catch (Throwable t) {
            logger.error(t.getMessage(), t);

            throw new ContextFault(t.getMessage(), t);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see de.atb.context.services.interfaces.IPersistenceService#
     * removePersistencePostProcessor
     * (de.atb.context.common.util.ApplicationScenario, java.lang.String)
     */
    @Override
    public final boolean removePersistencePostProcessor(final ApplicationScenario applicationScenario, final String id) throws ContextFault {
        try {
            boolean success = repos.removePersistencePostProcessor(applicationScenario, id);
            if (success) {
                logger.debug("Removed Persistence Post-Processor  with id '" + id + "' from '" + applicationScenario + "'");
            } else {
                logger.debug("Persistence Post-Processor with id '" + id + "' for '" + applicationScenario
                    + "' was not registered! Could not be deleted.");
            }
            return success;
        } catch (Throwable t) {
            logger.error(t.getMessage(), t);
            throw new ContextFault(t.getMessage(), t);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see de.atb.context.services.interfaces.IPersistenceService#
     * triggerPreProcessors(de.atb.context.common.util.ApplicationScenario,
     * java.lang.String, java.lang.String)
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public final void triggerPreProcessors(final ApplicationScenario scenario, final String objectString, final String objectClassName) throws ContextFault {
        try {
            Class clazzP;
            clazzP = Class.forName(objectClassName);
            Type bean = (Type) RdfHelper.createMonitoringData(objectString, clazzP);
            repos.triggerPreProcessors(scenario, bean);
        } catch (Throwable t) {
            logger.error(t.getMessage(), t);
            throw new ContextFault(t.getMessage(), t);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see de.atb.context.services.interfaces.IPersistenceService#
     * triggerPostProcessors
     * (de.atb.context.common.util.ApplicationScenario, java.lang.String,
     * java.lang.String)
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public final void triggerPostProcessors(final ApplicationScenario scenario, final String objectString, final String objectClassName) throws ContextFault {
        try {
            Class clazzP;
            clazzP = Class.forName(objectClassName);
            Type bean = (Type) RdfHelper.createMonitoringData(objectString, clazzP);
            repos.triggerPostProcessors(scenario, bean);
        } catch (Throwable t) {
            logger.error(t.getMessage(), t);
            throw new ContextFault(t.getMessage(), t);
        }
    }

}
