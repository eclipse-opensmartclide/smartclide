/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.atb.context.services.infrastructure.datalayer;

/*-
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


import de.atb.context.services.faults.ContextFault;
import de.atb.context.infrastructure.Node;
import de.atb.context.infrastructure.ServiceInfo;
import de.atb.context.modules.broker.status.ontology.StatusVocabulary;
import de.atb.context.services.interfaces.IRepositoryService;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.util.List;

/**
 * @author Giovanni
 */
@WebService(name = "ServiceRegistryRepositoryService", targetNamespace = "http://diversity-project.eu/")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT)
public interface IServiceRegistryRepositoryService extends
        IRepositoryService {
    @WebMethod(operationName = "getConnectedServices")
    public List<Node> getConnectedServices();

    @WebMethod(operationName = "updateServicesStatus")
    public boolean updateServicesStatus(
            @WebParam(name = "IDs") List<String> ids,
            @WebParam(name = "status") StatusVocabulary status);

    @WebMethod(operationName = "getServicesByType")
    public List<ServiceInfo> getServicesByType(
            @WebParam(name = "ServiceType") String serviceType);

    @WebMethod(operationName = "getFreeServicesByType")
    public List<ServiceInfo> getFreeServicesByType(
            @WebParam(name = "ServiceType") String serviceType);

    @WebMethod(operationName = "updateSinlgeStatusById")
    public boolean updateSingleStatusById(
            @WebParam(name = "ID") String id,
            @WebParam(name = "status") StatusVocabulary status);

    @WebMethod(operationName = "setStatusByIds")
    public boolean setStatusByIds(
            @WebParam(name = "ids") List<String> listId,
            @WebParam(name = "status") StatusVocabulary status);

    @WebMethod(operationName = "updateSinlgeStatusByLocation")
    public boolean updateSingleStatusByLocation(
            @WebParam(name = "location") String location,
            @WebParam(name = "status") StatusVocabulary status);

    @WebMethod(operationName = "getElement")
    public Object get(@WebParam(name = "ElementId") String elementId) throws ContextFault;
}

