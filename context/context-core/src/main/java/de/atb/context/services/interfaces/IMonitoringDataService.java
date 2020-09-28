package de.atb.context.services.interfaces;

/*
 * #%L
 * ATB Context Extraction Core Lib
 * %%
 * Copyright (C) 2020 ATB
 * %%
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the Eclipse
 * Public License, v. 2.0 are satisfied: GNU General Public License, version 2
 * with the GNU Classpath Exception which is
 * available at https://www.gnu.org/software/classpath/license.html.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 * #L%
 */

import de.atb.context.common.util.ApplicationScenario;
import de.atb.context.services.faults.ContextFault;
import de.atb.context.services.interfaces.IService;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

/**
 * @author scholze
 *
 */
@WebService(name = "MonitoringDataService", targetNamespace = "http://diversity-project.eu")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT)
public interface IMonitoringDataService extends IService {
	@WebMethod(operationName = "config")
	void config(
            @WebParam(name = "application-scenario") ApplicationScenario appScenario,
            @WebParam(name = "Mode") String mode,
            @WebParam(name = "Algorithm") String algorithm) throws ContextFault;

	@WebMethod(operationName = "Proactvie_config")
	void proactiveConfig(
            @WebParam(name = "application-scenario") ApplicationScenario appScenario)
					throws ContextFault;

	@WebMethod(operationName = "informAboutReferenceContext")
	boolean informAboutReferenceContext(
            @WebParam(name = "application-scenario") ApplicationScenario appScenario,
            @WebParam(name = "identifier") String identifier)
					throws ContextFault;

	@WebMethod(operationName = "informAboutContextChange")
	boolean informAboutContextChange(
            @WebParam(name = "application-scenario") ApplicationScenario appScenario,
            @WebParam(name = "identifier") String identifier)
					throws ContextFault;

	@WebMethod(operationName = "informAboutMonitoredData")
	boolean informAboutMonitoredData(
            @WebParam(name = "application-scenario") ApplicationScenario appScenario,
            @WebParam(name = "class-name") String className,
            @WebParam(name = "identifier") String identifier)
					throws ContextFault;

}
