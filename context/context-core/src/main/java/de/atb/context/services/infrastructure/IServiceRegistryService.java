/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.atb.context.services.infrastructure;

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
import de.atb.context.infrastructure.Node;
import de.atb.context.infrastructure.Nodes;
import de.atb.context.services.config.models.SWService;
import de.atb.context.services.interfaces.IPrimitiveService;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

/**
 *
 * @author Giovanni
 */
@WebService(name = "ServiceRegistryService", targetNamespace = "http://atb-bremen.de/")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT)
public interface IServiceRegistryService extends IPrimitiveService {

	@WebMethod(operationName = "register")
    boolean registerSWServices(@WebParam(name = "node") Node node) throws ContextFault;

	@WebMethod(operationName = "unregister")
    boolean unregisterSWServices(@WebParam(name = "node") Node node) throws ContextFault;

	@WebMethod(operationName = "get")
    Nodes getConnectedServices() throws ContextFault;

	@WebMethod(operationName = "getReposData")
    SWService getReposData() throws ContextFault;
}
