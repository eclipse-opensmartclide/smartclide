package de.atb.context.services.infrastructure;

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
import de.atb.context.infrastructure.ConnectedServices;
import de.atb.context.services.interfaces.IPrimitiveService;

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
