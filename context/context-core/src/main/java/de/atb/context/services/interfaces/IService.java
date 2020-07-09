/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.uninova.context.services.interfaces;

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
import pt.uninova.context.infrastructure.ServiceInfo;
import pt.uninova.context.modules.broker.process.services.PESFlowSpecs;
import pt.uninova.context.tools.datalayer.models.OutputDataModel;
import pt.uninova.context.tools.ontology.Configuration;

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
