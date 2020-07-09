/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.uninova.context.services.infrastructure.datalayer;

/*-
 * #%L
 * ATB Context Extraction Core Lib
 * %%
 * Copyright (C) 2016 - 2020 ATB
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */


import de.atb.context.services.faults.ContextFault;
import pt.uninova.context.infrastructure.Node;
import pt.uninova.context.infrastructure.ServiceInfo;
import pt.uninova.context.modules.broker.status.ontology.StatusVocabulary;
import pt.uninova.context.services.interfaces.IRepositoryService;

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

