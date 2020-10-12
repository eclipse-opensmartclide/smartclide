/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import de.atb.context.infrastructure.ServiceInfo;
import de.atb.context.modules.broker.process.services.PESFlowSpecs;
import de.atb.context.tools.datalayer.models.OutputDataModel;
import de.atb.context.tools.ontology.Configuration;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Giovanni
 */
@WebService(name = "ProsecoService", targetNamespace = "http://diversity-project.eu/")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT)
public interface IService extends IPrimitiveService {

    @WebMethod(operationName = "configureService")
    public <T extends Configuration> boolean configureService(@WebParam(name = "Configuration") T configuration) throws ContextFault;

    @WebMethod(operationName = "getReposInfo")
    public ServiceInfo getReposInfo() throws ContextFault;

    @WebMethod(operationName = "setupRuntimeSpecs")
    public boolean setupRuntimeSpecs(@WebParam(name = "host") String host, @WebParam(name = "port") int port, @WebParam(name = "classname") String className,
                                     @WebParam(name = "dataOutputIds") ArrayList<String> dataOutputIds, @WebParam(name = "pesId") String pesId,
                                     @WebParam(name = "flowSpecs") HashMap<String, PESFlowSpecs> flowSpecs, @WebParam(name = "serviceId") String serviceId, @WebParam(name = "outModel") OutputDataModel outModel) throws ContextFault;

    @WebMethod(operationName = "setNotifierClient")
    public boolean setNotifierClient(@WebParam(name = "host") String host,
                                     @WebParam(name = "port") int port, @WebParam(name = "classname") String className) throws ContextFault;
    @WebMethod(operationName = "runtimeInvoke")
    public boolean runtimeInvoke(@WebParam(name = "flowId") String flowId) throws ContextFault;

}
