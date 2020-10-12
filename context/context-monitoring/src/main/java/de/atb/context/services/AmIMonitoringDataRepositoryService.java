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

/*
 * @(#)MonitoringDataRepositoryService.java
 *
 * $Id: AmIMonitoringDataRepositoryService.java 665 2016-11-03 23:44:11Z scholze $
 *
 * $Rev:: 239                  $ 	last change revision
 * $Date:: 2015-09-24 14:10:36#$	last change date
 * $Author:: scholze             $	last change author
 *
 * Copyright 2011-15 Sebastian Scholze (ATB). All rights reserved.
 *
 */


import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import de.atb.context.monitoring.models.IMonitoringDataModel;
import de.atb.context.persistence.monitoring.MonitoringDataRepository;
import de.atb.context.common.util.ApplicationScenario;
import de.atb.context.common.util.BusinessCase;
import de.atb.context.common.util.TimeFrame;
import de.atb.context.services.faults.ContextFault;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import de.atb.context.services.infrastructure.response.InvokeResponse;
import de.atb.context.services.interfaces.Input;
import de.atb.context.services.interfaces.Output;
import de.atb.context.services.manager.ServiceManager;
import de.atb.context.tools.datalayer.models.OutputDataModel;
import de.atb.context.tools.ontology.Configuration;

import javax.xml.ws.AsyncHandler;
import javax.xml.ws.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

/**
 * MonitoringDataRepositoryService
 * $Id
 *
 * @author scholze
 * @version $LastChangedRevision: 239 $
 */
public class AmIMonitoringDataRepositoryService<Type extends IMonitoringDataModel<?, ?>> extends
    PersistenceUnitService<Type, MonitoringDataRepository<Type>> implements IAmIMonitoringDataRepositoryService<Type> {

    private static final Logger logger = LoggerFactory.getLogger(AmIMonitoringDataRepositoryService.class);

    @SuppressWarnings("unchecked")
    public AmIMonitoringDataRepositoryService() {
        super((MonitoringDataRepository<Type>) MonitoringDataRepository.getInstance());
        ApplicationScenario.getInstance();
    }

    @Override
    public void setOutputIds(ArrayList<String> arrayList) throws ContextFault {

    }

    @Override
    public String invokeForData(String s) throws ContextFault {
        return null;
    }

    @Override
    public void setOutputModel(OutputDataModel outputDataModel) throws ContextFault {

    }

    @Override
    public boolean setupRepos(String s, int i, String s1, String s2, OutputDataModel outputDataModel, ArrayList<String> arrayList, String s3) throws ContextFault {
        return false;
    }

    @Override
    public boolean startPES() throws ContextFault {
        return false;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * de.atb.context.services.interfaces.IMonitoringDataRepositoryService
     * #persist(java.lang.String, java.lang.String,
     * de.atb.context.common.util.ApplicationScenario)
     */
    @SuppressWarnings("unchecked")
    @Override
    public final synchronized void persist(final String rdfString, final String clazz, final ApplicationScenario applicationScenario) throws ContextFault {
        Class<Type> clazzP;
        try {
            clazzP = (Class<Type>) Class.forName(clazz);
            this.repos.persist(rdfString, clazzP, applicationScenario);
        } catch (ClassNotFoundException e) {
            logger.error(e.getMessage(), e);
            throw new ContextFault(e);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * de.atb.context.services.interfaces.IMonitoringDataRepositoryService
     * #getMonitoringData(de.atb.context.common.util.BusinessCase,
     * java.lang.String, java.lang.String)
     */
    @Override
    @SuppressWarnings("unchecked")
    public final synchronized String getMonitoringData(final BusinessCase businessCase, final String clazz, final String identifier) throws ContextFault {
        Class<Type> cl;
        try {
            cl = (Class<Type>) Class.forName(clazz);
            Type model = this.repos.getMonitoringData(businessCase, cl, identifier);
            if (model != null) {
                return model.toRdfString();
            }
        } catch (ClassNotFoundException e) {
            logger.error(e.getMessage(), e);
            throw new ContextFault(e);
        }
        return "";
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * de.atb.context.services.interfaces.IMonitoringDataRepositoryService
     * #getMonitoringData(de.atb.context.common.util.BusinessCase,
     * java.lang.String, int)
     */
    @SuppressWarnings("unchecked")
    @Override
    public final synchronized List<String> getMonitoringData(final BusinessCase businessCase, final String clazz, final int count) throws ContextFault {
        Class<Type> cl;
        List<String> stringModels = new ArrayList<>();
        try {
            cl = (Class<Type>) Class.forName(clazz);
            List<Type> models = this.repos.getMonitoringData(businessCase, cl, count);
            for (Type model : models) {
                stringModels.add(model.toRdfString());
            }
        } catch (ClassNotFoundException e) {
            logger.error(e.getMessage(), e);
            throw new ContextFault(e);
        }
        return stringModels;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * de.atb.context.services.interfaces.IMonitoringDataRepositoryService
     * #getMonitoringData(de.atb.context.common.util.BusinessCase,
     * java.lang.String, de.atb.context.common.util.TimeFrame)
     */
    @SuppressWarnings("unchecked")
    @Override
    public final synchronized List<String> getMonitoringData(final BusinessCase businessCase, final String clazz, final TimeFrame timeFrame)
        throws ContextFault {
        Class<Type> cl;
        List<String> stringModels = new ArrayList<>();
        try {
            cl = (Class<Type>) Class.forName(clazz);
            List<Type> models = this.repos.getMonitoringData(businessCase, cl, timeFrame);
            for (Type model : models) {
                stringModels.add(model.toRdfString());
            }
        } catch (ClassNotFoundException e) {
            logger.error(e.getMessage(), e);
            throw new ContextFault(e);
        }
        return stringModels;

    }

    /*
     * (non-Javadoc)
     *
     * @see
     * de.atb.context.persistence.IMonitoringDataRepository#getMonitoringData
     * (de.atb.context.common.util.BusinessCase, java.lang.Class, int)
     */
    @Override
    public final synchronized List<String> getMonitoringData(final ApplicationScenario applicationScenario, final String clazz, final int count)
        throws ContextFault {
        return getMonitoringData(applicationScenario.getBusinessCase(), clazz, count);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * de.atb.context.persistence.IMonitoringDataRepository#getMonitoringData
     * (de.atb.context.common.util.BusinessCase, java.lang.Class,
     * de.atb.context.common.util.TimeFrame)
     */
    @Override
    public final synchronized List<String> getMonitoringData(final ApplicationScenario applicationScenario, final String clazz, final TimeFrame timeFrame)
        throws ContextFault {
        return getMonitoringData(applicationScenario.getBusinessCase(), clazz, timeFrame);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * de.atb.context.persistence.IMonitoringDataRepository#getMonitoringData
     * (de.atb.context.common.util.BusinessCase, java.lang.Class,
     * java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public final synchronized String getMonitoringData(final ApplicationScenario applicationScenario, final String clazz, final String identifier)
        throws ContextFault {
        Class<Type> cl;
        try {
            cl = (Class<Type>) Class.forName(clazz);
            Type model = this.repos.getMonitoringData(applicationScenario, cl, identifier);
            if (model != null) {
                return model.toRdfString();
            }
        } catch (ClassNotFoundException e) {
            logger.error(e.getMessage(), e);
            throw new ContextFault(e);
        }
        return "";
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * de.atb.context.services.IMonitoringDataRepositoryService#clearDirectory
     * (de.atb.context.common.util.BusinessCase)
     */
    @Override
    public final synchronized boolean reset(final BusinessCase businessCase) {
        if (this.repos != null) {
            return this.repos.reset(businessCase);
        }
        return false;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * de.atb.context.services.interfaces.IMonitoringDataRepositoryService
     * #getLastIds(de.atb.context.common.util.ApplicationScenario,
     * java.lang.String, int)
     */
    @Override
    public final synchronized List<String> getLastIds(final ApplicationScenario applicationScenario, final String clazz, final int count) throws ContextFault {
        return getLastIds(applicationScenario.getBusinessCase(), clazz, count);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * de.atb.context.services.interfaces.IMonitoringDataRepositoryService
     * #getLastIds(de.atb.context.common.util.ApplicationScenario,
     * java.lang.String, de.atb.context.common.util.TimeFrame)
     */
    @Override
    public final synchronized List<String> getLastIds(final ApplicationScenario applicationScenario, final String clazz, final TimeFrame timeFrame)
        throws ContextFault {
        return getLastIds(applicationScenario.getBusinessCase(), clazz, timeFrame);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * de.atb.context.persistence.IContextRepository#executeSparqlSelectQuery
     * (de.atb.context.common.util.BusinessCase, java.lang.String)
     */
    @Override
    public final synchronized String executeSparqlSelectQuery(final BusinessCase businessCase, final String query) throws ContextFault {
        ResultSet results = repos.executeSparqlSelectQuery(businessCase, query);
        return ResultSetFormatter.asXMLString(results);
    }

    /*
     * (non-Javadoc)
     *
     * @see de.atb.context.services.ISelfLearningService#start()
     */
    @Override
    public void start() throws ContextFault {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /*
     * (non-Javadoc)
     *
     * @see de.atb.context.services.ISelfLearningService#stop()
     */
    @Override
    public void stop() throws ContextFault {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /*
     * (non-Javadoc)
     *
     * @see de.atb.context.services.ISelfLearningService#restart()
     */
    @Override
    public void restart() throws ContextFault {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /*
     * (non-Javadoc)
     *
     * @see de.atb.context.services.ISelfLearningService#ping()
     */
    @Override
    public final String ping() throws ContextFault {
        return ServiceManager.PING_RESPONSE;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * de.atb.context.services.interfaces.IMonitoringDataRepositoryService
     * #executeSparqlUpdateQuery(de.atb.context.common.util.BusinessCase,
     * java.lang.String)
     */
    @Override
    public final void executeSparqlUpdateQuery(final BusinessCase businessCase, final String query) throws ContextFault {
        repos.executeSparqlUpdateQueries(businessCase, query);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * de.atb.context.services.interfaces.IMonitoringDataRepositoryService
     * #executeSparqlUpdateQueries(de.atb.context.common.util.BusinessCase,
     * java.lang.String[])
     */
    @Override
    public final void executeSparqlUpdateQueries(final BusinessCase businessCase, final String... queries) throws ContextFault {
        repos.executeSparqlUpdateQueries(businessCase, queries);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * de.atb.context.services.interfaces.IMonitoringDataRepositoryService
     * #getLastIds(de.atb.context.common.util.BusinessCase,
     * java.lang.String, int)
     */
    @SuppressWarnings("unchecked")
    @Override
    public final List<String> getLastIds(final BusinessCase businessCase, final String clazz, final int count) throws ContextFault {
        List<String> ids;
        Class<Type> cl;
        try {
            cl = (Class<Type>) Class.forName(clazz);
            ids = repos.getLastIds(businessCase, cl, count);
        } catch (ClassNotFoundException e) {
            logger.error(e.getMessage(), e);
            throw new ContextFault(e);
        }
        return ids;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * de.atb.context.services.interfaces.IMonitoringDataRepositoryService
     * #getLastIds(de.atb.context.common.util.BusinessCase,
     * java.lang.String, de.atb.context.common.util.TimeFrame)
     */
    @SuppressWarnings("unchecked")
    @Override
    public final List<String> getLastIds(final BusinessCase businessCase, final String clazz, final TimeFrame timeFrame) throws ContextFault {
        List<String> ids;
        Class<Type> cl;
        try {
            cl = (Class<Type>) Class.forName(clazz);
            ids = repos.getLastIds(businessCase, cl, timeFrame);
        } catch (ClassNotFoundException e) {
            logger.error(e.getMessage(), e);
            throw new ContextFault(e);
        }
        return ids;
    }

    public final <T extends Configuration> boolean configureService(final T Configuration) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public final Output invokeS(final Input input) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public final Output invokeA(final Input input) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public final Future<?> invokeAAsync(final Input input,
                                        final AsyncHandler<InvokeResponse> asyncHandler) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public final Response<InvokeResponse> invokeAAsync(final Input input) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /* (non-Javadoc)
     * @see de.atb.context.services.interfaces.IRepositoryService#store(java.lang.Object)
     */
    @Override
    public String store(Object Element) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /* (non-Javadoc)
     * @see de.atb.context.services.interfaces.IRepositoryService#remove(java.lang.Object)
     */
    @Override
    public boolean remove(Object Element) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void shutdown() {
        super.repos.shutdown();
    }

}
