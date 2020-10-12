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

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

/**
 *
 * @author Giovanni
 */
@WebService(name = "PrimitiveService", targetNamespace = "http://atb-bremen.de/")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT)
public interface IPrimitiveService {

	@WebMethod(operationName = "startWebService")
    void start() throws ContextFault;

	@WebMethod(operationName = "stopWebService")
    void stop() throws ContextFault;

	@WebMethod(operationName = "restartWebService")
    void restart() throws ContextFault;

	@WebMethod(operationName = "pingWebService")
    String ping() throws ContextFault;

}
