package de.atb.context.services.interfaces;

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
