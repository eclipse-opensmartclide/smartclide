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


import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import de.atb.context.monitoring.models.IMonitoringDataModel;
import de.atb.context.services.faults.ContextFault;
import de.atb.context.services.interfaces.IRepositoryService;
import de.atb.context.common.util.ApplicationScenario;
import de.atb.context.common.util.BusinessCase;
import de.atb.context.common.util.TimeFrame;
import de.atb.context.services.interfaces.IPersistenceService;

/**
 * IMonitoringDataRepositoryService
 * $Id
 *
 * @author scholze
 * @version $LastChangedRevision: 239 $
 */
@WebService(name = "AmIMonitoringDataRepositoryService", targetNamespace = "http://atb-bremen.de")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT)
public interface IAmIMonitoringDataRepositoryService<Type extends IMonitoringDataModel<?, ?>> extends IRepositoryService,
    IPersistenceService {

    @WebMethod(operationName = "resetByBC")
    boolean reset(@WebParam(name = "business-case") BusinessCase businessCase) throws ContextFault;

    @WebMethod(operationName = "persistMonitoringDataString")
    void persist(@WebParam(name = "monitoring-data") String rdfString, @WebParam(name = "clazz") String clazz,
                 @WebParam(name = "application-scenario") ApplicationScenario applicationScenario) throws ContextFault;

    @WebMethod(operationName = "getMonitoringDataByBCAndCount")
    List<String> getMonitoringData(@WebParam(name = "business-case") BusinessCase businessCase,
                                   @WebParam(name = "clazz") String clazz, @WebParam(name = "count") int count) throws ContextFault;

    @WebMethod(operationName = "getMonitoringDataByBCAndTime")
    List<String> getMonitoringData(@WebParam(name = "business-case") BusinessCase businessCase,
                                   @WebParam(name = "clazz") String clazz, @WebParam(name = "time") TimeFrame timeFrame) throws ContextFault;

    @WebMethod(operationName = "getMonitoringDataByBCAndId")
    String getMonitoringData(@WebParam(name = "business-case") BusinessCase businessCase, @WebParam(name = "clazz") String clazz,
                             @WebParam(name = "identifier") String identifier) throws ContextFault;

    @WebMethod(operationName = "getMonitoringDataByASAndCount")
    List<String> getMonitoringData(@WebParam(name = "application-scenario") ApplicationScenario applicationScenario,
                                   @WebParam(name = "clazz") String clazz, @WebParam(name = "count") int count) throws ContextFault;

    @WebMethod(operationName = "getMonitoringDataByASAndTime")
    List<String> getMonitoringData(@WebParam(name = "application-scenario") ApplicationScenario applicationScenario,
                                   @WebParam(name = "clazz") String clazz, @WebParam(name = "time") TimeFrame timeFrame) throws ContextFault;

    @WebMethod(operationName = "getMonitoringDataByASAndId")
    String getMonitoringData(@WebParam(name = "application-scenario") ApplicationScenario applicationScenario,
                             @WebParam(name = "clazz") String clazz, @WebParam(name = "identifier") String identifier) throws ContextFault;

    @WebMethod(operationName = "getLastIdsByBCAndCount")
    List<String> getLastIds(@WebParam(name = "business-case") BusinessCase businessCase, @WebParam(name = "clazz") String clazz,
                            @WebParam(name = "count") int count) throws ContextFault;

    @WebMethod(operationName = "getLastIdsByBCAndTime")
    List<String> getLastIds(@WebParam(name = "business-case") BusinessCase businessCase, @WebParam(name = "clazz") String clazz,
                            @WebParam(name = "time") TimeFrame timeFrame) throws ContextFault;

    @WebMethod(operationName = "getLastIdsByASAndCount")
    List<String> getLastIds(@WebParam(name = "application-scenario") ApplicationScenario applicationScenario,
                            @WebParam(name = "clazz") String clazz, @WebParam(name = "count") int count) throws ContextFault;

    @WebMethod(operationName = "getLastIdsByASAndTime")
    List<String> getLastIds(@WebParam(name = "application-scenario") ApplicationScenario applicationScenario,
                            @WebParam(name = "clazz") String clazz, @WebParam(name = "time") TimeFrame timeFrame) throws ContextFault;

    @WebMethod(operationName = "executeSparqlSelectQuery")
    String executeSparqlSelectQuery(@WebParam(name = "business-case") BusinessCase businessCase,
                                    @WebParam(name = "query") String query) throws ContextFault;

    @WebMethod(operationName = "executeSparqlUpdateQuery")
    void executeSparqlUpdateQuery(@WebParam(name = "business-case") BusinessCase businessCase, @WebParam(name = "query") String query)
        throws ContextFault;

    @WebMethod(operationName = "executeSparqlUpdateQueries")
    void executeSparqlUpdateQueries(@WebParam(name = "business-case") BusinessCase businessCase,
                                    @WebParam(name = "query") String... queries) throws ContextFault;

    @WebMethod(operationName = "shutdown")
    void shutdown();
}
