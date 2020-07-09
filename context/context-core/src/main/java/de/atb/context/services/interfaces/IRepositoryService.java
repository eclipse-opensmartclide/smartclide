package de.atb.context.services.interfaces;

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
import de.atb.context.tools.datalayer.models.OutputDataModel;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.util.ArrayList;

/**
 *
 * @author Giovanni
 */
@WebService(name = "RepositoryService", targetNamespace = "http://atb-bremen.de/")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT)
public interface IRepositoryService/*<T extends FlowController>*/ extends IPrimitiveService {

    @WebMethod(operationName = "storeElement")
    String store(@WebParam(name = "Element") Object Element) throws ContextFault;

    @WebMethod(operationName = "removeElement")
    boolean remove(@WebParam(name = "Element") Object Element) throws ContextFault;

    @WebMethod(operationName = "getElementbyID")
    String invokeForData(@WebParam(name = "ElementId") String ElementId) throws ContextFault;

    @WebMethod(operationName = "setOutputModel")
    void setOutputModel(@WebParam(name = "OutputModel") OutputDataModel model) throws ContextFault;

    @WebMethod(operationName = "setOutputIds")
    void setOutputIds(@WebParam(name = "OutputIds") ArrayList<String> outputIds) throws ContextFault;

    @WebMethod(operationName = "setupRepos")
    boolean setupRepos(@WebParam(name = "host") String host, @WebParam(name = "port") int port, @WebParam(name = "classname") String className,
                       @WebParam(name = "pesId") String pesId, @WebParam(name = "model") OutputDataModel model, @WebParam(name = "outIds") ArrayList<String> outIds,
                       @WebParam(name = "serviceId") String serviceId)   throws ContextFault;

    /*@WebMethod(operationName = "setupReposWithController")
    public boolean setupRepos(@WebParam(name = "pesId") String pesId, @WebParam(name = "model") OutputDataModel model, @WebParam(name = "flowIds") List<String> outIds, @WebParam(name = "controller") T controller)  throws ContextFault;;
*/
    @WebMethod(operationName = "startPES")
    boolean startPES()  throws ContextFault;

}

/*
   
*/
