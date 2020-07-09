package pt.uninova.context.services.infrastructure;

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
import pt.uninova.context.infrastructure.ConnectedServices;
import pt.uninova.context.services.interfaces.IPrimitiveService;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

/**
 *
 * @author Giovanni
 */
@WebService(name = "DeployerService", targetNamespace = "http://atb-bremen.eu/")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT)
public interface IDeployerService extends IPrimitiveService {

	@WebMethod(operationName = "get")
	public ConnectedServices getConnectedServices() throws ContextFault;

	@WebMethod(operationName = "unregister")
	public boolean unregisterRegistry() throws ContextFault;

}